/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.templatemanager.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.j2ee.cms.biz.columnmanager.dao.ColumnDao;
import com.j2ee.cms.biz.columnmanager.domain.Column;
import com.j2ee.cms.biz.configmanager.dao.SystemLogDao;
import com.j2ee.cms.biz.publishmanager.service.remotepublish.client.Sender;
import com.j2ee.cms.biz.sitemanager.dao.SiteDao;
import com.j2ee.cms.biz.sitemanager.domain.Site;
import com.j2ee.cms.biz.templatemanager.dao.TemplateDao;
import com.j2ee.cms.biz.templatemanager.dao.TemplateInstanceDao;
import com.j2ee.cms.biz.templatemanager.dao.TemplateUnitDao;
import com.j2ee.cms.biz.templatemanager.domain.Template;
import com.j2ee.cms.biz.templatemanager.domain.TemplateInstance;
import com.j2ee.cms.biz.templatemanager.domain.TemplateUnit;
import com.j2ee.cms.biz.templatemanager.service.TemplateInstanceService;
import com.j2ee.cms.biz.usermanager.domain.User;
import com.j2ee.cms.sys.GlobalConfig;
import com.j2ee.cms.sys.SiteResource;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.util.CollectionUtil;
import com.j2ee.cms.common.core.util.FileUtil;
import com.j2ee.cms.common.core.util.SqlUtil;
import com.j2ee.cms.common.core.util.StringUtil;


/**
 * <p>标题: 模板实例的业务实现类</p>
 * <p>描述: 对模板实例业务的具体实现</p>
 * <p>模块: 模板管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-4-9 下午03:59:51
 * @history（历次修订内容、修订人、修订时间等）
 */
public class TemplateInstanceServiceImpl implements  TemplateInstanceService{
	
	private final Logger log = Logger.getLogger(TemplateInstanceServiceImpl.class);
	
	/** 注入模板实例数据访问对象 */
	private TemplateInstanceDao templateInstanceDao;
	/** 注入模板单元Dao */
	private TemplateUnitDao templateUnitDao;
	/** 注入模板dao **/
	private TemplateDao templateDao;
	/** 注入网站dao **/
	private SiteDao siteDao;
	/** 注入栏目dao **/
	private ColumnDao columnDao;
	/**注入日志dao*/
	private SystemLogDao systemLogDao;

	/**
	 * 修改模板实例
	 * @param templateInstance 要修改的模板实例
	 */
	public String modifyTemplateInstance(TemplateInstance templateInstance, String siteId, String sessionID) {
		String infoMessage = "";
		String name = templateInstance.getName();
		String[] param = {"templateInstanceName", "templateId"};
		Object[] value = {name, templateInstance.getTemplate().getId()};
		List list = templateInstanceDao.findByNamedQuery("findTemplateInstanceByTemplateNameAndTemplateId", param, value);
		if(!CollectionUtil.isEmpty(list)) {
			infoMessage = "修改失败,此模板实例名称已经存在";
		} else {
			infoMessage = "修改成功";
			templateInstanceDao.update(templateInstance);
		}
		
		// 写入日志文件
		String categoryName = "模板管理（实例管理）->更名";
		systemLogDao.addLogData(categoryName, siteId, sessionID, name);
		
		return infoMessage;
	}
	
	/**
	 * 按照模板id查找模板实例
	 * @param templateid 模板id
	 * @return			 返回模板实例列表
	 */
	public List<TemplateInstance> findTemplateInstanceByTemplateId(String templateId, String siteId, String sessionID, boolean isUpSystemAdmin, boolean isSiteAdmin ) {
		// 系统管理员及以上
		if(isUpSystemAdmin) {
//			return templateInstanceDao.findByNamedQuery("findTemplateInstanceByTemplateId", "templateId", templateId);
			String[] params = {"templateId", "siteId"};
			Object[] values = {templateId, siteId};
			return templateInstanceDao.findByNamedQuery("findTemplateInstanceByTemplateIdAndSiteId", params, values);
		
		// 网站管理员	
		} else if(isSiteAdmin) {
			String[] params = {"templateId", "siteId"};
			Object[] values = {templateId, siteId};
			return templateInstanceDao.findByNamedQuery("findTemplateInstanceByTemplateIdAndSiteId", params, values);
		
		// 普通用户
		} else {
			String[] params = {"templateId", "siteId", "creatorId"};
			Object[] values = {templateId, siteId, sessionID};
			return templateInstanceDao.findByNamedQuery("findTemplateInstanceByTemplateIdAndSiteIdAndCreatorId", params, values);
		}
	}
	
	/**
	 * 模板实例分页
	 * @param pagination 分页对象
	 * @param templateId 模板id
	 * @return           返回模板实例分页对象
	 */
	public Pagination templateInstancePagination(Pagination pagination, String templateId, String siteId, String sessionID, boolean isUpSystemAdmin, boolean isSiteAdmin) {
		// 系统管理员及以上
		if(isUpSystemAdmin) {
//			return templateInstanceDao.getPagination(pagination, "templateId", templateId);
			String[] params = {"templateId", "siteId"};
			Object[] values = {templateId, siteId};
			return templateInstanceDao.getPagination(pagination, params, values);
		
		// 网站管理员	
		} else if(isSiteAdmin) {
			String[] params = {"templateId", "siteId"};
			Object[] values = {templateId, siteId};
			return templateInstanceDao.getPagination(pagination, params, values);
		
		// 普通用户
		} else {
			String[] params = {"templateId", "siteId", "creatorId"};
			Object[] values = {templateId, siteId, sessionID};
			return templateInstanceDao.getPagination(pagination, params, values);
		}
	}
	
	/**
	 * 修改网站
	 * @param site 要修改的网站对象
	 */
	private void  modifySite(Site site) {
		siteDao.update(site);
	}
	
	/**
	 * 按照模板实例id查找模板实例
	 * @param templateInstanceId 模板实例id
	 * @return                   返回模板实例对象
	 */
	public TemplateInstance findTemplateInstanceById(String templateInstanceId) {
		return templateInstanceDao.getAndClear(templateInstanceId);
	}
	
	/**
	 * 处理模板实例的添加
	 * @param templateInstance  		模板实例
	 * @param templateId				模板id
	 * @param siteId					网站id
	 * @param url						当前项目的访问地址
	 * @param creatorid					用户的id
	 */
	public String addTemplateInstance(TemplateInstance templateInstance, String templateId, String siteid, StringBuffer url, String creatorid) {
		String infoMessage = "";
		String name = templateInstance.getName();
		String[] param = {"templateInstanceName", "templateId"};
		Object[] value = {name, templateId};
		List list = templateInstanceDao.findByNamedQueryAndClear("findTemplateInstanceByTemplateNameAndTemplateId", param, value);
		if(list != null && list.size() > 0) {
			infoMessage = "添加失败,此模板实例名称已经存在";
			return infoMessage;
		} 
		long currentDate = System.currentTimeMillis();
		Template template = templateDao.getAndClear(templateId);
		String url2 = StringUtil.delete(url.toString(), "/templateInstance.do");
		String path = SiteResource.getTemplateInstanceDir(siteid, false);
		String localPath = GlobalConfig.appRealPath + template.getLocalPath();
		File newfile = new File(localPath);
		if(!newfile.exists()) {
			infoMessage = "此模板不存在";
			return infoMessage;
		}
		// 获得模板的目录
		String zipFilePath = FileUtil.getFileDir(localPath);
		// 新建一个目录
		String newFolder = path + "/" + currentDate;
		FileUtil.makeDirs(newFolder);
		FileUtil.copy(zipFilePath, newFolder, false);
		
		// 获得要添加的模板的目录
		File file = new File(newFolder);
		// 获取解压后的文件
		File[] files = file.listFiles();
		// 添加模板实例
		User creator = new User();
		Site site = new Site();
		site.setId(siteid);
		for(int i = 0; i < files.length; i++) {
			// 获得复制的html文件
			if(FileUtil.isHtml(files[i].getName())) {
				String suffix = StringUtil.strRChar(files[i].getName(), '.');
				File newFile = new File(newFolder + File.separator + currentDate + "." + suffix);
				files[i].renameTo(newFile);
				creator.setId(creatorid);
				templateInstance.setCreator(creator);
				templateInstance.setTemplate(template);
				templateInstance.setCreateTime(new Date());
				templateInstance.setFileName(currentDate + "." + suffix);
				String outFile = SiteResource.getTemplate_instancePath(siteid,currentDate)+ currentDate + "." + suffix;
				
				templateInstance.setLocalPath(outFile);
		
				templateInstance.setUrl(url2 + SiteResource.getTemplate_instancePath(siteid,currentDate)+ currentDate + "." + suffix);
				templateInstance.setSite(site);
				templateInstanceDao.saveAndClear(templateInstance);
				
				// 拷贝设置页面
				FileUtil.copy(SiteResource.getTemplate_setPath(), GlobalConfig.appRealPath + SiteResource.getTemplate_instancePath(siteid,currentDate));
				// 新建配置路径
				String configDir = SiteResource.getTemplate_instanceConfPath(siteid,currentDate);
				FileUtil.makeDirs(GlobalConfig.appRealPath + configDir);
				// 替换模板为可设置模板
				replaceTemplate(localPath, GlobalConfig.appRealPath + outFile, configDir, templateInstance);
				break;
			}
		}
		
		// 写入日志文件
		infoMessage = "添加模板实例成功";
		String categoryName = "模板管理（实例管理）->添加";
		systemLogDao.addLogData(categoryName, siteid, creatorid, name);
		
		return infoMessage;
	}
	
	/**
	 * 处理模板实例的删除
	 * @param ids    要删除的模板实例的ids
	 * @param siteId 网站id
	 * @return		 返回是否删除成功
	 */
	public String deleteTemplateInstance(String ids, String siteid, String sessionID) {
		String infoMessage = "";
		String[] strid = ids.split(",");
		TemplateInstance templateInstance = null;
		Site site = null;
		for(String templateInstanceId : strid) {
			templateInstance = templateInstanceDao.getAndClear(templateInstanceId);
			// 删除模板实例时也要将选择它的模板实例置空
			// 处理网站
			site = siteDao.getAndClear(siteid);
			if(site.getHomeTemplate() != null) {
				if(site.getHomeTemplate().getId().equals(templateInstanceId)) {
					site.setHomeTemplate(null);
					modifySite(site);
				}
			} 
			if(site.getArticleTemplate() != null) {
				if(site.getArticleTemplate().getId().equals(templateInstanceId)) {
					site.setArticleTemplate(null);
					modifySite(site);
				}
			}
			if(site.getColumnTemplate() != null) {
				if(site.getColumnTemplate().getId().equals(templateInstanceId)) {
					site.setColumnTemplate(null);
					modifySite(site);
				}
			}
			// 处理栏目
			List<Object> list = (List) columnDao.findByNamedQuery("findColumnByTemplateInstanceId", "templateInstanceId", templateInstanceId);
			Column column = null;
			for(int i = 0; i < list.size(); i++) {
				Object[] object = new Object[3];
				object = (Object[]) list.get(i);
				String columnId = String.valueOf(object[0]);
				column = columnDao.getAndClear(columnId);
				if(object[1] != null) {
					String instanceid = String.valueOf(object[1]);
					if(instanceid.equals(templateInstanceId)) {
						column.setArticleTemplate(null);		
					}
				}
				if(object[2] != null) {
					String instanceid = String.valueOf(object[2]); 
					if(instanceid.equals(templateInstanceId)) {
						column.setColumnTemplate(null);
					}
				}
				columnDao.update(column); 
			}
			String localPath = GlobalConfig.appRealPath + templateInstance.getLocalPath();
			// 将路径以"/"隔开
			String[] str = localPath.split("/");
			// 获得最后的目录
			String folder = File.separator + str[str.length-1];
			String newFolder = StringUtil.delete(localPath, folder);
			// 调用模板的删除方法
			File file = new File(newFolder);
			if(file.exists()) {
				FileUtil.delete(newFolder);
			} 
			
			// 写入日志文件
			String categoryName = "模板管理（实例管理）->删除";
			String param = templateInstance.getName();
			systemLogDao.addLogData(categoryName, siteid, sessionID, param);
			
			
			templateUnitDao.bulkUpdate("deleteTemplateUnitByTemplateInstanceId", "id", templateInstanceId);
			templateInstanceDao.deleteByKey(templateInstanceId);
			
		}
		infoMessage = "删除模板实例成功";
		return infoMessage;
	} 
	
	/**
	 * 替换模板的html文件为系统可设置的模板
	 * @param inFile 模板对应的html文件
	 * @param outFile 替换后的html文件位置 
	 * @param configDir 单元配置路径
	 * @param templateInstance 模板实例
	 */
	private void replaceTemplate(String inFile, String outFile, String configDir, TemplateInstance templateInstance) {
		String html = FileUtil.read(inFile);
//		String script = FileUtil.read(GlobalConfig.appRealPath + "/public/config/script.js");
//		script = "var unitsWin;\n" +
//				 "function openSetWin(unitId,colId) {" + 
//		 			 "var instanceId = document.getElementById(\"instanceId\").value;" +
//		 			 "var url = \"/"+GlobalConfig.appName+"/templateUnit.do?dealMethod=getUnitForm&unitId=\" + unitId + \"&instanceId=\" + instanceId + \"&columnId=\" + colId;" +
//					 "unitsWin = showWindow(\"unitsWin\",\"单元设置\",url,280, 20,870,620);" +
//				 "}";
//		String hidden = FileUtil.read(GlobalConfig.appRealPath + "/public/config/hidden.txt");
//		hidden = "<input type=\"hidden\" id=\"siteId\" name=\"siteId\" value=\"\">\n" +
//				 "<input type=\"hidden\" id=\"instanceId\" name=\"instanceId\" value=\""+templateInstance.getId()+"\">\n" +
//				 "<input type=\"hidden\" id=\"columnId\" name=\"columnId\" value=\"\">\n";
		
		StringBuilder buf = new StringBuilder();
		String pre = "<!--{%[";
		String middleBegin = "]--><div class=\"_unitcls\" style=\"border:1px dashed red;cursor:pointer;font:12px italic;color:#FF0000;HEIGHT\" onclick=\"openSetWin(";
		String middleEnd = ");\">";
		String suffix = "</div><!--%}-->";
		StringBuffer temp = new StringBuffer();
		String unitRegex = TemplateUnit.REGEX_ORIGINAL;
		// <span style="height:100px;">导航</span>
		String unitTextRegex = TemplateUnit.REGEX_SPAN;//"<\\s*span.*\\s*>([^<]*)</\\s*span\\s*>";
		
//		String headRegex = "</head[^>]*>";
//		String bodyRegex = "<body[^<]*>";
//		String css = "<link rel=\"stylesheet\" type=\"text/css\" href=\"/"+ GlobalConfig.appName +"/css/dhtmlx.css\"" + "/>\n" +
//					 "<link rel=\"stylesheet\" type=\"text/css\" href=\"/"+ GlobalConfig.appName +"/images/window/skins/dhtmlxwindows_dhx_blue.css\"" + "/>\n" +
//					 "<link rel=\"stylesheet\" type=\"text/css\" href=\"/"+ GlobalConfig.appName +"/images/grid/skins/dhtmlxgrid_dhx_blue.css\"" + "/>\n" + 
//					 "<link rel=\"stylesheet\" type=\"text/css\" href=\"/"+ GlobalConfig.appName +"/images/layout/skins/dhtmlxlayout_dhx_blue.css\"" + "/>\n" + 
//					 "<link rel=\"stylesheet\" type=\"text/css\" href=\"/"+ GlobalConfig.appName +"/css/pagination.css\"" + "/>\n" + 
//					 "<link rel=\"stylesheet\" type=\"text/css\" href=\"/"+ GlobalConfig.appName +"/css/ajaxpagination.css\"" + "/>\n" + 
//					 "<link rel=\"stylesheet\" type=\"text/css\" href=\"/"+ GlobalConfig.appName +"/script/ext/resources/css/ext-all.css\"" + "/>\n";
//		
//		String js = "<script type=\"text/javascript\" src=\"/"+ GlobalConfig.appName +"/script/dhtmlx.js\">" + "</script>\n" +
//					"<script type=\"text/javascript\" src=\"/"+ GlobalConfig.appName +"/script/jquery.js\">" + "</script>\n" +
//					"<script type=\"text/javascript\" src=\"/"+ GlobalConfig.appName +"/script/jquery.form.js\">" + "</script>\n" +
//					"<script type=\"text/javascript\" src=\"/"+ GlobalConfig.appName +"/script/global.js\">" + "</script>\n" +
//					"<script type=\"text/javascript\" src=\"/"+ GlobalConfig.appName +"/script/util.js\">" + "</script>\n" +
//					"<script type=\"text/javascript\" src=\"/"+ GlobalConfig.appName +"/script/ext/adapter/ext/ext-base.js\">" + "</script>\n" +
//					"<script type=\"text/javascript\" src=\"/"+ GlobalConfig.appName +"/script/ext/ext-all-debug.js\">" + "</script>\n" +
//					"<script type=\"text/javascript\" src=\"/"+ GlobalConfig.appName +"/script/fckeditor/fckeditor.js\">" + "</script>\n" +
//					"<script type=\"text/javascript\" src=\"/"+ GlobalConfig.appName +"/script/My97DatePicker/WdatePicker.js\">" + "</script>\n" +
//					"<script type=\"text/javascript\" src=\"/"+ GlobalConfig.appName +"/script/jquery.pagination.js\">" + "</script>\n" +
//					"<script type=\"text/javascript\">\n" + script + "\n</script>";
//		html = html.replaceFirst(headRegex, 
//				css + js + "</head>");
//		html = html.replaceFirst(bodyRegex, "<body>\n" + hidden);
		
		Pattern unitPatn = Pattern.compile(unitRegex);
		Pattern unitTextPatn = Pattern.compile(unitTextRegex);
		Matcher unitMatcher = unitPatn.matcher(html);
		String unitStr = "";
		String unitName = "";
		String unitHeight = "";
		while (unitMatcher.find()) {
			// 添加单元到数据库
			TemplateUnit unit = new TemplateUnit();
			unitStr = unitMatcher.group(1);
			String middleStr = middleBegin;
			
			Matcher textMatcher = unitTextPatn.matcher(unitStr);
			
			while (textMatcher.find()) {
				unitHeight = textMatcher.group(1).trim();
				middleStr = middleStr.replaceAll("HEIGHT", unitHeight);
				unitName = textMatcher.group(2).trim();
			}
			unit.setName(unitName);
			unit.setTemplateInstance(templateInstance);
			unit.setConfigDir(configDir);
			unit.setSite(templateInstance.getSite());
			templateUnitDao.save(unit);
			
			// 向div中添加单元id
			middleStr = middleStr.replaceFirst("<div", "<div uid=\""+unit.getId()+"\"");
			
			// 替换单元脚本
			unitMatcher.appendReplacement(temp, 
					buf.append(pre)
					.append(unit.getId())
					.append(middleStr)
					.append("'"+unit.getId()+"'")
					.append(",'0'")
					.append(middleEnd)
					.append("{" + unitName + "}->未设")
					.append(suffix)
					.toString());
			buf.delete(0, buf.length());
		}
		unitMatcher.appendTail(temp);
		FileUtil.write(outFile, temp.toString());
	}
	
	/***
	 * 查找被绑定的栏目(或者文章的栏目)ids
	 * @param bind
	 * @param templateInstanceId
	 * @param siteId
	 * @return
	 */
	public String findBindedColumnIds(String bind, String templateInstanceId, String siteId) {
		String bindedIds = "";
		String[] params = {"templateInstanceId", "siteId"};
		Object[] values = {templateInstanceId, siteId};
		List<Column> list = new ArrayList<Column>();
		// 绑定栏目
		if(bind.equals("column")) {
			list = columnDao.findByNamedQuery("findColumnByColumnTemplateInstanceIdAndSiteId", params, values);
			
		// 绑定栏目下的文章	
		} else if(bind.equals("article")) {
			list = columnDao.findByNamedQuery("findColumnByArticleTemplateInstanceIdAndSiteId", params, values);
		}
		if(!CollectionUtil.isEmpty(list)) {
			for(int i = 0; i < list.size(); i++) {
				if(!StringUtil.isEmpty(bindedIds)) {
					bindedIds += "," + list.get(i).getId();
				} else {
					bindedIds = list.get(i).getId();
				}
			}
		}
		return bindedIds;
	}
	
	/**
	 * 绑定栏目或者文章
	 * @param bind
	 * @param bindedIds
	 * @param templateInstanceId
	 * @param canceledIds    重新选择后即将要取消绑定的栏目
	 */
	public void modifyBindColumnOrArticle(String bind, String bindedIds, String templateInstanceId, String canceledIds) {
		log.debug("bind==================================="+bind);
		log.debug("bindedIds============================="+bindedIds);
		log.debug("templateInstanceId===================="+templateInstanceId);
		log.debug("templateInstanceId===================="+canceledIds);
		TemplateInstance templateInstance = new TemplateInstance();
		templateInstance = templateInstanceDao.getAndClear(templateInstanceId);
		templateInstance.setId(templateInstanceId);
		if(!StringUtil.isEmpty(bindedIds) && !bindedIds.equals("null")) {
			String[] params = {"columnIds", "templateInstanceId"};
			
			int usedTemplateInstance = 0;
			Column column = null;
			String[] str = bindedIds.split(",");
			// 提取单信息栏目id(单信息栏目不需要绑定栏目页模版)
			String singleColumnIds = "";
			// 绑定栏目
			if(bind.equals("column")) {
				for(int i = 0; i < str.length; i++) {
					column = columnDao.getAndNonClear(str[i]);
					// 如果是多信息栏目
					if(column.getColumnType().equals("multi")){
						if(column.getColumnTemplate() != null) {
							String newInstanceId = column.getColumnTemplate().getId();
							if(!newInstanceId.equals(templateInstanceId)) {
								usedTemplateInstance++;
								TemplateInstance newInstance = templateInstanceDao.getAndClear(newInstanceId);
								newInstance.setUsedNum(newInstance.getUsedNum()-1);
								templateInstanceDao.updateAndClear(newInstance);
							} 
						} else {
							usedTemplateInstance++;
						}
					}else{
						if(singleColumnIds.equals("")){
							singleColumnIds = column.getId();
						}else{
							singleColumnIds += column.getId();
						}
					}
					columnDao.clearCache();
				}
				if(!singleColumnIds.equals("")){
					String[] str1 = singleColumnIds.split(",");
					for(int j = 0; j < str1.length; j++){
						bindedIds = bindedIds.replace(str1[j], "");
					}
					bindedIds = bindedIds.replaceAll(",,", ",");
					if(bindedIds.lastIndexOf(",") == bindedIds.length()-1){
						bindedIds = bindedIds.substring(0, bindedIds.length()-1);
					}
				}
				String newIds = SqlUtil.toSqlString(bindedIds);
				String[] values = {newIds, templateInstanceId};
				columnDao.updateByDefine("bindColumnTemplateInstanceByColumnIds", params, values);
			
			// 绑定文章	
			} else if(bind.equals("article")) {
				String newIds = SqlUtil.toSqlString(bindedIds);
				String[] values = {newIds, templateInstanceId};
				for(int i = 0; i < str.length; i++) {
					column = columnDao.getAndNonClear(str[i]);
					if(column.getArticleTemplate() != null) {
						String newInstanceId = column.getArticleTemplate().getId();
						if(!newInstanceId.equals(templateInstanceId)) {
							usedTemplateInstance++;
							TemplateInstance newInstance = templateInstanceDao.getAndClear(newInstanceId);
							newInstance.setUsedNum(newInstance.getUsedNum()-1);
							templateInstanceDao.updateAndClear(newInstance);
						}
					} else {
						usedTemplateInstance++;
					}
					columnDao.clearCache();
				}
				columnDao.updateByDefine("bindColumnArticleTemplateInstanceByColumnIds", params, values);
			}
			templateInstance.setUsedNum(templateInstance.getUsedNum() + usedTemplateInstance);
			templateInstanceDao.updateAndClear(templateInstance);
		}
		this.cancelBindColumnOrArticle(bind, canceledIds, templateInstanceId);
	}
	
	/**
	 * 取消绑定栏目或者文章
	 * @param bind
	 * @param bindedIds
	 * @param templateInstanceId
	 */
	public void cancelBindColumnOrArticle(String bind, String bindedIds, String templateInstanceId) {
		if(!StringUtil.isEmpty(bindedIds) && !bindedIds.equals("null")) {
			log.debug("bind==================================="+bind);
			log.debug("bindedIds============================="+bindedIds);
			String[] params = {"columnIds", "templateInstanceId"};
			String newIds = SqlUtil.toSqlString(bindedIds);
			String[] values = {newIds, null};
			// 取消绑定栏目
			if(bind.equals("column")) {
				columnDao.updateByDefine("bindColumnTemplateInstanceByColumnIds", params, values);
			
			// 取消绑定文章	
			} else if(bind.equals("article")) {
				columnDao.updateByDefine("bindColumnArticleTemplateInstanceByColumnIds", params, values);
			}
			if(!StringUtil.isEmpty(bindedIds)) {
				TemplateInstance templateInstance = templateInstanceDao.getAndClear(templateInstanceId);
				templateInstance.setUsedNum(templateInstance.getUsedNum()-bindedIds.split(",").length);
				templateInstanceDao.update(templateInstance);
			}
		}
	}
	
	/**
	 * @param templateInstanceDao the templateInstanceDao to set
	 */
	public void setTemplateInstanceDao(TemplateInstanceDao templateInstanceDao) {
		this.templateInstanceDao = templateInstanceDao;
	}

	/**
	 * @param templateDao the templateDao to set
	 */
	public void setTemplateDao(TemplateDao templateDao) {
		this.templateDao = templateDao;
	}
	
	/**
	 * @param siteDao the siteDao to set
	 */
	public void setSiteDao(SiteDao siteDao) {
		this.siteDao = siteDao;
	}

	/**
	 * @param columnDao the columnDao to set
	 */
	public void setColumnDao(ColumnDao columnDao) {
		this.columnDao = columnDao;
	}

	/**
	 * @return the templateUnitDao
	 */
	public TemplateUnitDao getTemplateUnitDao() {
		return templateUnitDao;
	}

	/**
	 * @param templateUnitDao the templateUnitDao to set
	 */
	public void setTemplateUnitDao(TemplateUnitDao templateUnitDao) {
		this.templateUnitDao = templateUnitDao;
	}

	/**
	 * @param systemLogDao the systemLogDao to set
	 */
	public void setSystemLogDao(SystemLogDao systemLogDao) {
		this.systemLogDao = systemLogDao;
	}


}
