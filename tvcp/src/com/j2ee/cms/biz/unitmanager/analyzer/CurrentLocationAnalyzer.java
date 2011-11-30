/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.unitmanager.analyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.j2ee.cms.biz.articlemanager.dao.ArticleDao;
import com.j2ee.cms.biz.articlemanager.domain.Article;
import com.j2ee.cms.biz.columnmanager.dao.ColumnDao;
import com.j2ee.cms.biz.sitemanager.dao.SiteDao;
import com.j2ee.cms.biz.sitemanager.domain.Site;
import com.j2ee.cms.biz.templatemanager.dao.TemplateUnitDao;
import com.j2ee.cms.biz.templatemanager.domain.TemplateUnit;
import com.j2ee.cms.biz.unitmanager.label.ArticleTextLabel;
import com.j2ee.cms.biz.unitmanager.label.CommonLabel;
import com.j2ee.cms.biz.unitmanager.label.CurrentLocationLabel;
import com.j2ee.cms.sys.GlobalConfig;
import com.j2ee.cms.sys.SiteResource;
import com.j2ee.cms.common.core.util.CollectionUtil;
import com.j2ee.cms.common.core.util.StringUtil;
import com.j2ee.cms.common.core.util.XmlUtil;

/**
 * <p>标题: —— 当前位置解析类</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 模板单元管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-6-15 上午10:05:10
 * @history（历次修订内容、修订人、修订时间等） 
 */

public class CurrentLocationAnalyzer  implements TemplateUnitAnalyzer{

	/** 日志 */
	private static final Logger log = Logger.getLogger(CurrentLocationAnalyzer.class);
	/** 栏目dao */
	private ColumnDao columnDao;
	/** 注入模板单元dao */
	private TemplateUnitDao templateUnitDao;	
	/** 网站dao */
	private SiteDao siteDao;
	/** 注入文章dao */
	private ArticleDao articleDao;


	public String getHtml(String unitId, String articleId, String columnId, String siteId,  Map<String,String> commonLabel) {
		String html = this.findHtmlPath(unitId);
		html = StringUtil.trimEnter(html);
		TemplateUnit unit = templateUnitDao.getAndClear(unitId);
		String configFilePath = unit.getConfigFile();
		//获取到当前模板实例的xml配置文件路径
		String filePath = GlobalConfig.appRealPath+ configFilePath;
		String str = this.getObjName(html, columnId, filePath,siteId);
		
		return str;
	}
	/**
	 * 递归获取所有的子栏目名称集合
	 * @param list 所有名称的集合
	 * @param columnId 栏目ID
	 * @return 返回所有的子栏目名称
	 */
	public List findObjName(List list ,String columnId){
		 if(columnId != null){
	//		 String sql = "SELECT obj.id,obj.name,obj.parent.id FROM " + className +" obj WHERE obj.id = "+id+" ORDER BY obj.id" ;	
			 List objList =columnDao.findByNamedQuery("findColumnUseCurrentLocation", "columnId", columnId);
			 if(objList != null){
				for(int i = 0 ; i < objList.size(); i++){
					Map map = new HashMap();
					Object[] obj = (Object[])objList.get(i);
					map.put(0,obj[1]);
					String columnType = String.valueOf(obj[4]);
					if(columnType.equals("single")){
						String columnid = String.valueOf(obj[0]);
						if(!StringUtil.isEmpty(columnid)){
							List<Article> list1 = articleDao.findByNamedQuery("findAuditedArticlesByColumnId", "columnId", columnid, 0, 1);
							if(!CollectionUtil.isEmpty(list1)){
								map.put(1, list1.get(0).getUrl());
							}
						}
					}else{
						map.put(1, obj[3]);
					}
					list.add(map);
					String objparentid = String.valueOf(obj[2]);
					if(objparentid != null && !String.valueOf(objparentid).equals("")){
						list = findObjName(list,objparentid);
					}
				}			
			 } 
		 }		
		return list;
	}
	
	/**
	 * 显示页面的数据
	 * @param src html内容
	 * @param columnId 栏目ID
	 * @param filePath xml文件的路径
	 * @return 页面显示的数据
	 */
	public String getObjName(String src,String columnId,String filePath, String siteId){
		StringBuffer sb = new StringBuffer();
		XmlUtil xmlUtil = XmlUtil.getInstance(filePath);
		String titleLimit = xmlUtil.getNodeText("j2ee.cms/current_location/titleLimit");
		String sign = "";
		log.debug("siteId======="+siteId);
		Site site = siteDao.getAndClear(siteId);
		String currentLocation[] = src.split(CurrentLocationLabel.CURRENTLOCATION);
		if(currentLocation != null && currentLocation.length >= 2){
			sign = currentLocation[1];
		}
		Pattern labelPatttern = Pattern.compile(CommonLabel.REGEX_LABEL);
		Matcher labelMatcher = labelPatttern.matcher(src);
		String label = "";
		while(labelMatcher.find()) {
			label = labelMatcher.group();
			if(label.equals(CurrentLocationLabel.CURRENTLOCATION)){
				String newColumnId = this.getColumnId(columnId, filePath);
				List list = new ArrayList();
				list = findObjName(list ,newColumnId );
				String strName = "";
				if(list.size() > 0){
					for(int i  = list.size()-1 ; i >=0 ; i--){
						Map map = (HashMap)list.get(i);
						String strColumn = String.valueOf(map.get(0));
						if(strColumn != null && strColumn.length() >= StringUtil.parseInt(titleLimit)){
							strColumn = strColumn.substring(0, StringUtil.parseInt(titleLimit));
						}	
						if(map.get(1) != null) {
							strName = StringUtil.trim(strName + sign + "<a href=\""+SiteResource.getUrl(map.get(1).toString(), true)+"\">"+strColumn+"</a>");
						} else {
							strName = StringUtil.trim(strName + sign + "<a href=\"#\">"+ strColumn +"</a>");
						}
					}
				}
				labelMatcher.appendReplacement(sb, strName);
				
			}else  if(label.equals(CommonLabel.SITE_NAME)){
				// 网站名称	
				String siteName = site.getName();
				if(siteName != null ){
					labelMatcher.appendReplacement(sb, siteName);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
				
			}else if(label.equals(CommonLabel.SITE_LINK)) {
				// 网站链接
				String siteUrl = site.getUrl();
				if(siteUrl != null ){
					labelMatcher.appendReplacement(sb, SiteResource.getUrl(siteUrl, true));
				} else {
					labelMatcher.appendReplacement(sb, "#");
				}
			
			// 应用名	
			} else if(label.equals(CommonLabel.APP_NAME)){
				String appName = GlobalConfig.appName;
				labelMatcher.appendReplacement(sb, appName);
			
			// 网站id	
			} else if(label.equals(CommonLabel.SITE_ID)){
				labelMatcher.appendReplacement(sb, siteId);
			
			}
		}
		labelMatcher.appendTail(sb);
		String str = sb.toString();		
		str = StringUtil.reverse(str);
		str = str.replaceFirst(sign.trim(), "");
		str = StringUtil.reverse(str);
		return str;
	}
	
	private String findHtmlPath(String unitId){
		TemplateUnit templateUnit = templateUnitDao.getAndNonClear(unitId);
		String html = templateUnit.getHtml();
		return html;
	}
	
	private String getColumnId(String columnId, String filePath){
		XmlUtil xmlUtil = XmlUtil.getInstance(filePath);
		//内容来源
		String contextFrom = xmlUtil.getNodeText("j2ee.cms/current_location/contextFrom");
		//栏目名称
		String columnName = xmlUtil.getNodeText("j2ee.cms/current_location/columnName");
		String testColumnId = null;
		String strColumn[] = columnName.split("##");
		if(strColumn != null && strColumn.length == 2){
			testColumnId = strColumn[0];
		}
		String newColumnId = null;
		if(contextFrom.equals("1")){
			//当前栏目
			newColumnId = columnId;
		}else if(contextFrom.equals("2")){
			//指定栏目
			newColumnId = testColumnId;
		}
		return newColumnId;
	}
	/**
	 * @param columnDao the columnDao to set
	 */
	public void setColumnDao(ColumnDao columnDao) {
		this.columnDao = columnDao;
	}
	/**
	 * @param templateUnitDao the templateUnitDao to set
	 */
	public void setTemplateUnitDao(TemplateUnitDao templateUnitDao) {
		this.templateUnitDao = templateUnitDao;
	}
	/**
	 * @param siteDao the siteDao to set
	 */
	public void setSiteDao(SiteDao siteDao) {
		this.siteDao = siteDao;
	}
	/**
	 * @param articleDao the articleDao to set
	 */
	public void setArticleDao(ArticleDao articleDao) {
		this.articleDao = articleDao;
	}
}
