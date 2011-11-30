/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.unitmanager.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.j2ee.cms.biz.articlemanager.dao.ArticleAttributeDao;
import com.j2ee.cms.biz.columnmanager.dao.ColumnDao;
import com.j2ee.cms.biz.columnmanager.domain.Column;
import com.j2ee.cms.biz.configmanager.dao.SystemLogDao;
import com.j2ee.cms.biz.templatemanager.dao.TemplateUnitCategoryDao;
import com.j2ee.cms.biz.templatemanager.dao.TemplateUnitDao;
import com.j2ee.cms.biz.templatemanager.dao.TemplateUnitStyleDao;
import com.j2ee.cms.biz.templatemanager.domain.TemplateUnit;
import com.j2ee.cms.biz.templatemanager.domain.TemplateUnitCategory;
import com.j2ee.cms.biz.templatemanager.domain.TemplateUnitStyle;
import com.j2ee.cms.biz.unitmanager.service.LatestInfoService;
import com.j2ee.cms.biz.unitmanager.web.form.LatestInfoForm;
import com.j2ee.cms.sys.GlobalConfig;
import com.j2ee.cms.sys.SiteResource;
import com.j2ee.cms.common.core.util.CollectionUtil;
import com.j2ee.cms.common.core.util.FileUtil;
import com.j2ee.cms.common.core.util.IDFactory;
import com.j2ee.cms.common.core.util.SqlUtil;
import com.j2ee.cms.common.core.util.StringUtil;
import com.j2ee.cms.common.core.util.XmlUtil;

/**
 * 
 * <p>标题: —— 最新信息业务逻辑处理类</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 模板单元管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-6-3 上午10:38:55
 * @history（历次修订内容、修订人、修订时间等）
 */
public class LatestInfoServiceImpl implements LatestInfoService {
	private static final Logger log = Logger.getLogger(LatestInfoServiceImpl.class);
	
	/** 注入模板单元dao */
	private TemplateUnitDao templateUnitDao;
	/** 栏目DAO */
	private ColumnDao columnDao;
	/** 文章属性dao */
	private ArticleAttributeDao articleAttributeDao;
	/** 模板单元样式dao */
	private TemplateUnitStyleDao templateUnitStyleDao;
	/**注入模板单元类别dao**/
	private TemplateUnitCategoryDao templateUnitCategoryDao;
	/** 注入日志dao*/
	private SystemLogDao systemLogDao;
	
	/**
	 * 查找最新信息的配置信息
	 */
	public void findConfig(LatestInfoForm latestInfoForm, String siteId) {	
		//单元ID
		String unitId = latestInfoForm.getUnit_unitId();
		//栏目ID
		String categoryId = latestInfoForm.getUnit_categoryId();
		String str1[] = {"unitId", "CategoryId"};
		Object obj1[] = {unitId, categoryId};
		List tempUnitList = templateUnitDao.findByNamedQuery("findTemplateUnitByUnitIdAndUnitCategory", str1, obj1);
		if(tempUnitList != null && tempUnitList.size() > 0 ){
			TemplateUnit unit = (TemplateUnit)tempUnitList.get(0);
			String htmlContent = unit.getHtml();			
			String filePath = unit.getConfigFile();
			if(filePath != null && !filePath.equals("")){
				this.setXmlData( GlobalConfig.appRealPath + filePath, latestInfoForm);
			}else {
				String haveconfigFilePath = GlobalConfig.appRealPath + TemplateUnit.UNIT_CONFIG_DIR + "/latest_info.xml";
				latestInfoForm = this.setXmlData(haveconfigFilePath, latestInfoForm);
			}	
			if(htmlContent != null){
			 	latestInfoForm.setHtmlContent(htmlContent);			
			}		
			latestInfoForm.setUnit_css(unit.getCss());
		}else{
			String configFile = GlobalConfig.appRealPath + TemplateUnit.UNIT_CONFIG_DIR + "/latest_info.xml";
			this.setXmlData(configFile, latestInfoForm);
		}	
	}
	
	public List<TemplateUnitStyle> findTemplateUnitStyleByCategoryIdAndSiteId(String unit_categoryId,String siteId){
		String str[] = {"unit_categoryId","siteId"};
		Object obj[] = {unit_categoryId,siteId};
		List templateUnitStyleList = templateUnitStyleDao.findByNamedQuery("findTemplateUnitStyleByCategoryIdAndSiteId", str, obj);
		if(templateUnitStyleList != null && templateUnitStyleList.size() > 0){
			return templateUnitStyleList;
		}else{
			return new ArrayList<TemplateUnitStyle>();
		}		
	}
	
	public void saveConfigContent(LatestInfoForm latestInfoForm, String filePath, String siteId, String sessionID) {
		String unitId = latestInfoForm.getUnit_unitId();
		String categoryId = latestInfoForm.getUnit_categoryId();
		String scriptStyle = latestInfoForm.getHtmlContent();
		TemplateUnit unit = templateUnitDao.getAndClear(unitId);
		if (unit != null) {
			TemplateUnitCategory templateUnitCategory = new TemplateUnitCategory();
			templateUnitCategory.setId(categoryId);
			unit.setTemplateUnitCategory(templateUnitCategory);
			unit.setHtml(scriptStyle);
			unit.setConfigFile(filePath);
			unit.setCss(latestInfoForm.getUnit_css());
			/*unit.setScript("<link rel=\"stylesheet\" type=\"text/css\" href=\"/script/latestInfo/Pager.css\"/>"+
					       "<script type=\"text/javascript\" src=\"/script/latestInfo/latestInfo.js\"></script>"+
					       "<script type=\"text/javascript\" src=\"/script/latestInfo/jquery.pager.js\"></script>");*/
			String columnIds = "";
			columnIds = findLatestInfoColumnIds(filePath, siteId);
			if(!StringUtil.isEmpty(columnIds) && !columnIds.equals("null")){
				unit.setColumnIds(columnIds);
			}
			templateUnitDao.saveOrUpdate(unit);
		}
		
		// 写入日志文件
		String categoryName = "模板设置(最新信息)->保存";
		String param = unit.getName();
		systemLogDao.addLogData(categoryName, siteId, sessionID, param);
	}

	public String saveConfigXml(LatestInfoForm latestInfoForm) {
		String unitId = latestInfoForm.getUnit_unitId();
		//新xml文件的保存路径
		String newFilePath = "";
		String filePath = "";
		TemplateUnit templateUnit = templateUnitDao.getAndClear(unitId);
		if(templateUnit != null){
			String configFilePath = templateUnit.getConfigFile();
			String configDir = templateUnit.getConfigDir();
			String haveconfigFilePath = GlobalConfig.appRealPath+TemplateUnit.UNIT_CONFIG_DIR+"/latest_info.xml";
			filePath = haveconfigFilePath;
			if(configFilePath != null && !configFilePath.equals("")){
				newFilePath = GlobalConfig.appRealPath+configFilePath;
			}else{
				newFilePath = GlobalConfig.appRealPath+configDir +"/"+ IDFactory.getId()+".xml";
			}
		}
		this.setXmlData(filePath, newFilePath, latestInfoForm);
		return newFilePath;
	}

	
	public List findTemplateUnitByUnitId(String id) {
		List list = new ArrayList();
		TemplateUnit templateUnit = templateUnitDao.getAndClear(id);
		String name = templateUnit.getName();
		List templateUnitList = templateUnitDao.findByNamedQuery("findTemplateUnitByUnitName","name",name);	
		if(templateUnitList != null && templateUnitList.size() > 0){
			for(int i = 0 ; i < templateUnitList.size() ; i++){
				TemplateUnit allTemplateUnit = new TemplateUnit();
				allTemplateUnit = (TemplateUnit)templateUnitList.get(i);
				list.add(allTemplateUnit);
			}					
		}
		return list;
	}
	/**
	 * 设置xml的值	
	 * @param filePath xml文件的路径
	 * @param newFilePath 新xml文件的路径
	 * @param latestInfoForm 最新信息form对象
	 */
	private void setXmlData(String filePath,String newFilePath,LatestInfoForm latestInfoForm){
		log.debug("latestInfoForm.getRow()==================="+latestInfoForm.getRow());
		XmlUtil xmlUtil = XmlUtil.getInstance(filePath);
		xmlUtil.setNodeCDATAText("j2ee.cms/latest-info/unitType",latestInfoForm.getUnitType());
		xmlUtil.setNodeCDATAText("j2ee.cms/latest-info/chooseColumn",latestInfoForm.getChooseColumn());
		xmlUtil.setNodeCDATAText("j2ee.cms/latest-info/allColumn",latestInfoForm.getAllColumn());
		xmlUtil.setNodeCDATAText("j2ee.cms/latest-info/selectCol",latestInfoForm.getSelectCol());
		xmlUtil.setNodeCDATAText("j2ee.cms/latest-info/page",latestInfoForm.getPage());
		xmlUtil.setNodeCDATAText("j2ee.cms/latest-info/col",latestInfoForm.getCol());
		xmlUtil.setNodeCDATAText("j2ee.cms/latest-info/row",latestInfoForm.getRow());	
		xmlUtil.setNodeCDATAText("j2ee.cms/latest-info/count",latestInfoForm.getCount());
		xmlUtil.setNodeCDATAText("j2ee.cms/latest-info/pageCount",latestInfoForm.getPageCount());
		xmlUtil.setNodeCDATAText("j2ee.cms/latest-info/titleWordCount",latestInfoForm.getTitleWordCount());
		xmlUtil.setNodeCDATAText("j2ee.cms/latest-info/htmlContent",latestInfoForm.getHtmlContent());	
		xmlUtil.setNodeCDATAText("j2ee.cms/latest-info/titleEnd",latestInfoForm.getTitleEnd());
		xmlUtil.setNodeCDATAText("j2ee.cms/latest-info/titleEndPic",latestInfoForm.getTitleEndPic());
		xmlUtil.setNodeCDATAText("j2ee.cms/latest-info/titleHead",latestInfoForm.getTitleHead());
		xmlUtil.setNodeCDATAText("j2ee.cms/latest-info/titleHeadPic",latestInfoForm.getTitleHeadPic());
		xmlUtil.setNodeCDATAText("j2ee.cms/latest-info/moreLinkColumn",latestInfoForm.getMoreLinkColumn());
		String columnIdAndName = latestInfoForm.getMoreLinkColumn();
		String url = "";
		if(!StringUtil.isEmpty(columnIdAndName) && !columnIdAndName.equals("null")) {
			String columnId = columnIdAndName.split("##")[0];
			Column column = columnDao.getAndClear(columnId);
			if(column != null) {
				url = column.getUrl();
			}
		}
		xmlUtil.setNodeCDATAText("j2ee.cms/latest-info/moreLinkColumnUrl",url);
		xmlUtil.setNodeCDATAText("j2ee.cms/latest-info/moreLink",latestInfoForm.getMoreLink());
		xmlUtil.setNodeCDATAText("j2ee.cms/latest-info/moreLinkPic",latestInfoForm.getMoreLinkPic());
		xmlUtil.setNodeCDATAText("j2ee.cms/latest-info/pageSite",latestInfoForm.getPageSite());
		xmlUtil.save(newFilePath);
	}
	
	/**
	 * 获取xml的值来设置form 	
	 * @param filePath xml文件的路径
	 * @param latestInfoForm 最新信息form对象
	 * @return LatestInfoForm 最新信息form对象
	 */
	private LatestInfoForm setXmlData(String filePath ,LatestInfoForm latestInfoForm){
		XmlUtil xmlUtil = XmlUtil.getInstance(filePath);
		latestInfoForm.setUnitType((xmlUtil.getNodeText("j2ee.cms/latest-info/unitType")));
		latestInfoForm.setChooseColumn((xmlUtil.getNodeText("j2ee.cms/latest-info/chooseColumn")));	
		latestInfoForm.setAllColumn((xmlUtil.getNodeText("j2ee.cms/latest-info/allColumn")));
		latestInfoForm.setSelectCol((xmlUtil.getNodeText("j2ee.cms/latest-info/selectCol")));
		latestInfoForm.setPage((xmlUtil.getNodeText("j2ee.cms/latest-info/page")));
		latestInfoForm.setRow((xmlUtil.getNodeText("j2ee.cms/latest-info/row")));
		latestInfoForm.setCol((xmlUtil.getNodeText("j2ee.cms/latest-info/col")));
		latestInfoForm.setCount((xmlUtil.getNodeText("j2ee.cms/latest-info/count")));
		latestInfoForm.setPageCount((xmlUtil.getNodeText("j2ee.cms/latest-info/pageCount")));
		latestInfoForm.setTitleWordCount((xmlUtil.getNodeText("j2ee.cms/latest-info/titleWordCount")));
		latestInfoForm.setHtmlContent((xmlUtil.getNodeText("j2ee.cms/latest-info/htmlContent")));
		latestInfoForm.setTitleEnd((xmlUtil.getNodeText("j2ee.cms/latest-info/titleEnd")));
		latestInfoForm.setTitleEndPic((xmlUtil.getNodeText("j2ee.cms/latest-info/titleEndPic")));
		latestInfoForm.setTitleHead((xmlUtil.getNodeText("j2ee.cms/latest-info/titleHead")));
		latestInfoForm.setTitleHeadPic((xmlUtil.getNodeText("j2ee.cms/latest-info/titleHeadPic")));
		latestInfoForm.setMoreLinkColumn((xmlUtil.getNodeText("j2ee.cms/latest-info/moreLinkColumn")));
		latestInfoForm.setMoreLink((xmlUtil.getNodeText("j2ee.cms/latest-info/moreLink")));
		latestInfoForm.setMoreLinkPic((xmlUtil.getNodeText("j2ee.cms/latest-info/moreLinkPic")));
		latestInfoForm.setPageSite((xmlUtil.getNodeText("j2ee.cms/latest-info/pageSite")));
		return latestInfoForm;
	}
	
	private String findLatestInfoColumnIds(String configFile, String siteId){
		String columnIds = "";
		XmlUtil xmlUtil = XmlUtil.getInstance(GlobalConfig.appRealPath + configFile);
		String unitType = xmlUtil.getNodeText("/j2ee.cms/latest-info/unitType");
		String allColumn = xmlUtil.getNodeText("/j2ee.cms/latest-info/allColumn");
		String selectCol = xmlUtil.getNodeText("/j2ee.cms/latest-info/selectCol");
		String chooseColumn = xmlUtil.getNodeText("/j2ee.cms/latest-info/chooseColumn");
		// 指定栏目（只有一个）
		if(unitType.equals("1")) {
			if(!StringUtil.isEmpty(chooseColumn)) {
				String[] columnIdAndColumnName = chooseColumn.split("##");
				columnIds = columnIdAndColumnName[0].toString();
			}
			
		// 其他栏目	
		} else {
			// 包含所有
			if(selectCol.equals("0")) {
				List<Column> list = columnDao.findByNamedQuery("findColumnByCurrentSite", "siteId", siteId);
				if(list != null && list.size() > 0) {
					for(int i = 0; i < list.size(); i++) {
						if(!columnIds.equals("")) {
							columnIds += "," + list.get(i).getId();
						} else {
							columnIds = list.get(i).getId();
						}
					}
				}
				
			// 包含所选	
			} else if(selectCol.equals("1")) {
				if(!StringUtil.isEmpty(allColumn)) {
					String[] columnIdAndColumnName = allColumn.split("##");
					columnIds = columnIdAndColumnName[0].toString();
				}
				
			// 不包含所选	
			} else {
				String tempIds = "";
				if(!StringUtil.isEmpty(allColumn)) {
					String[] columnIdAndColumnName = allColumn.split("##");
					tempIds = columnIdAndColumnName[0].toString();
				}
				if(!tempIds.equals("")) {
					tempIds =  SqlUtil.toSqlString(tempIds);
					String[] params1 = {"siteId", "ids"};
					String[] values1 = {SqlUtil.toSqlString(siteId), tempIds};
					List<Column> list = columnDao.findByDefine("findColumnAndColumnIdsNotInFixedIdsAndSiteId", params1, values1);
					if(!CollectionUtil.isEmpty(list)) {
						for(int i = 0; i < list.size(); i++) {
							if(!columnIds.equals("")) {
								columnIds += "," + list.get(i).getId();
							} else {
								columnIds = list.get(i).getId();
							}
						}
					}
				}
			}
		} 
		return columnIds;
	}
	
	/**
	 * @param templateUnitDao the templateUnitDao to set
	 */
	public void setTemplateUnitDao(TemplateUnitDao templateUnitDao) {
		this.templateUnitDao = templateUnitDao;
	}

	/**
	 * @param columnDao the columnDao to set
	 */
	public void setColumnDao(ColumnDao columnDao) {
		this.columnDao = columnDao;
	}


	/**
	 * @param articleAttributeDao the articleAttributeDao to set
	 */
	public void setArticleAttributeDao(ArticleAttributeDao articleAttributeDao) {
		this.articleAttributeDao = articleAttributeDao;
	}


	/**
	 * @return the templateUnitStyleDao
	 */
	public TemplateUnitStyleDao getTemplateUnitStyleDao() {
		return templateUnitStyleDao;
	}


	/**
	 * @param templateUnitStyleDao the templateUnitStyleDao to set
	 */
	public void setTemplateUnitStyleDao(TemplateUnitStyleDao templateUnitStyleDao) {
		this.templateUnitStyleDao = templateUnitStyleDao;
	}

	/**
	 * @param templateUnitCategoryDao the templateUnitCategoryDao to set
	 */
	public void setTemplateUnitCategoryDao(
			TemplateUnitCategoryDao templateUnitCategoryDao) {
		this.templateUnitCategoryDao = templateUnitCategoryDao;
	}


	/**
	 * @param systemLogDao the systemLogDao to set
	 */
	public void setSystemLogDao(SystemLogDao systemLogDao) {
		this.systemLogDao = systemLogDao;
	}

}
