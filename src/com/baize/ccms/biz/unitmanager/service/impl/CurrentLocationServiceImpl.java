/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.biz.unitmanager.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.baize.ccms.biz.articlemanager.dao.ArticleAttributeDao;
import com.baize.ccms.biz.columnmanager.dao.ColumnDao;
import com.baize.ccms.biz.configmanager.dao.SystemLogDao;
import com.baize.ccms.biz.templatemanager.dao.TemplateUnitCategoryDao;
import com.baize.ccms.biz.templatemanager.dao.TemplateUnitDao;
import com.baize.ccms.biz.templatemanager.dao.TemplateUnitStyleDao;
import com.baize.ccms.biz.templatemanager.domain.TemplateUnit;
import com.baize.ccms.biz.templatemanager.domain.TemplateUnitCategory;
import com.baize.ccms.biz.templatemanager.domain.TemplateUnitStyle;
import com.baize.ccms.biz.unitmanager.service.CurrentLocationService;
import com.baize.ccms.biz.unitmanager.web.form.CurrentLocationForm;
import com.baize.ccms.sys.GlobalConfig;
import com.baize.common.core.util.IDFactory;
import com.baize.common.core.util.StringUtil;
import com.baize.common.core.util.XmlUtil;

/**
 * <p>标题: —— 当前位置业务逻辑处理类</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 模板单元管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-6-15 上午10:12:36
 * @history（历次修订内容、修订人、修订时间等） 
 */

public class CurrentLocationServiceImpl implements CurrentLocationService{
	private static final Logger log = Logger.getLogger(CurrentLocationServiceImpl.class);
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

	
	public void findConfig(CurrentLocationForm currentLocationForm, String siteId) {	
		//单元ID
		String unitId = currentLocationForm.getUnit_unitId();
		String categoryId = currentLocationForm.getUnit_categoryId();
		String str1[] = {"unitId", "CategoryId"};
		Object obj1[] = {unitId, categoryId};
		List tempUnitList = templateUnitDao.findByNamedQuery("findTemplateUnitByUnitIdAndUnitCategory", str1, obj1);
		if(tempUnitList != null && tempUnitList.size() > 0 ){
			TemplateUnit unit = (TemplateUnit)tempUnitList.get(0);
			String htmlContent = unit.getHtml();			
			String filePath = unit.getConfigFile();
			if(filePath != null && !filePath.equals("")){
				this.setXmlData( GlobalConfig.appRealPath + filePath, currentLocationForm);
			} else {
				String haveconfigFilePath = GlobalConfig.appRealPath + TemplateUnit.UNIT_CONFIG_DIR + "/current_location.xml";
				this.setXmlData(haveconfigFilePath, currentLocationForm);
			}
			String img = "";
			String viewStyle = currentLocationForm.getViewStyle();
			String viewImg = "";
			if(viewStyle != null && !viewStyle.equals("")) {
				/*String str[] = viewStyle.split("##");
				if(str != null && str.length > 1) {
					img = str[1];
				}*/
		//		img = viewStyle;
				TemplateUnitStyle templateUnitStyle = (TemplateUnitStyle)templateUnitStyleDao.getAndNonClear(viewStyle);
				viewImg = templateUnitStyle.getDisplayEffect();
			}else{
				viewImg = "";
			}
			currentLocationForm.setViewImg(viewImg);
			currentLocationForm.setHtmlContent(htmlContent);
			currentLocationForm.setUnit_css(unit.getCss());
		}else{
			String configFile = GlobalConfig.appRealPath + TemplateUnit.UNIT_CONFIG_DIR + "/current_location.xml";
			this.setXmlData(configFile, currentLocationForm);
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
	
	public void saveConfigContent(CurrentLocationForm currentLocationForm, String filePath, String siteId, String sessionID) {
		String unitId = currentLocationForm.getUnit_unitId();
		String categoryId = currentLocationForm.getUnit_categoryId();
		String htmlContent =currentLocationForm.getHtmlContent();
		TemplateUnit unit = templateUnitDao.getAndClear(unitId);
		if (unit != null) {
			TemplateUnitCategory templateUnitCategory = new TemplateUnitCategory();
			templateUnitCategory.setId(categoryId);
			unit.setTemplateUnitCategory(templateUnitCategory);
			unit.setHtml(htmlContent);
			unit.setConfigFile(filePath);
			unit.setCss(currentLocationForm.getUnit_css());
			String columnIds = "";
			columnIds = getColumnId(currentLocationForm.getUnit_columnId(), filePath);
			if(!StringUtil.isEmpty(columnIds) && !columnIds.equals("null")){
				unit.setColumnIds(columnIds);
			}
			templateUnitDao.saveOrUpdate(unit);
		}
		
		// 写入日志文件
		String categoryName = "模板设置(当前位置)->保存";
		String param = unit.getName();
		systemLogDao.addLogData(categoryName, siteId, sessionID, param);
	}

	public String saveConfigXml(CurrentLocationForm currentLocationForm) {
		String unitId = currentLocationForm.getUnit_unitId();
		String categoryId = currentLocationForm.getUnit_categoryId();
		log.debug("unitId===================="+unitId);
		//新xml文件的保存路径
		String newFilePath = "";
		String filePath = "";
		TemplateUnit templateUnit = templateUnitDao.getAndClear(unitId);
		if(templateUnit != null){
			String configFilePath = templateUnit.getConfigFile();
			String configDir = templateUnit.getConfigDir();
			String haveconfigFilePath = GlobalConfig.appRealPath+TemplateUnit.UNIT_CONFIG_DIR+"/current_location.xml";
			filePath = haveconfigFilePath;
		
			if(configFilePath != null && !configFilePath.equals("")){
			//	filePath = GlobalConfig.appRealPath+configFilePath;
				newFilePath = GlobalConfig.appRealPath+configFilePath;
			}else{
				newFilePath = GlobalConfig.appRealPath+configDir +"/"+ IDFactory.getId()+".xml";
			}
		}
		this.setXmlData(filePath, newFilePath, currentLocationForm);
		return newFilePath;
	}

	
	public List findTemplateUnitByUnitId(String id){
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
	 * @param currentLocationForm 标题链接form对象
	 */
	private void setXmlData(String filePath,String newFilePath,CurrentLocationForm currentLocationForm){
		XmlUtil xmlUtil = XmlUtil.getInstance(filePath);		
		xmlUtil.setNodeCDATAText("baize/current_location/viewStyle",currentLocationForm.getViewStyle());
		xmlUtil.setNodeCDATAText("baize/current_location/contextFrom",currentLocationForm.getContextFrom());
		xmlUtil.setNodeCDATAText("baize/current_location/columnName",currentLocationForm.getColumnName());
		xmlUtil.setNodeCDATAText("baize/current_location/titleLimit",currentLocationForm.getTitleLimit());
		xmlUtil.save(newFilePath);
	}
	/**
	 * 获取xml的值来设置form 	
	 * @param filePath xml文件的路径
	 * @param currentLocationForm 标题链接form对象
	 * @return CurrentLocationForm 标题链接form对象
	 */
	private CurrentLocationForm setXmlData(String filePath ,CurrentLocationForm currentLocationForm){
		XmlUtil xmlUtil = XmlUtil.getInstance(filePath);
		currentLocationForm.setViewStyle(xmlUtil.getNodeText("baize/current_location/viewStyle"));
		currentLocationForm.setContextFrom(xmlUtil.getNodeText("baize/current_location/contextFrom"));
		currentLocationForm.setColumnName(xmlUtil.getNodeText("baize/current_location/columnName"));
		currentLocationForm.setTitleLimit(xmlUtil.getNodeText("baize/current_location/titleLimit"));		
		return currentLocationForm;
	}
	
	private String getColumnId(String columnId, String filePath){
		filePath = GlobalConfig.appRealPath + filePath;
		XmlUtil xmlUtil = XmlUtil.getInstance(filePath);
		//内容来源
		String contextFrom = xmlUtil.getNodeText("baize/current_location/contextFrom");
		//栏目名称
		String columnName = xmlUtil.getNodeText("baize/current_location/columnName");
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
	 * @return the articleAttributeDao
	 */
	public ArticleAttributeDao getArticleAttributeDao() {
		return articleAttributeDao;
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
	 * @return the templateUnitCategoryDao
	 */
	public TemplateUnitCategoryDao getTemplateUnitCategoryDao() {
		return templateUnitCategoryDao;
	}


	/**
	 * @param templateUnitCategoryDao the templateUnitCategoryDao to set
	 */
	public void setTemplateUnitCategoryDao(
			TemplateUnitCategoryDao templateUnitCategoryDao) {
		this.templateUnitCategoryDao = templateUnitCategoryDao;
	}


	/**
	 * @return the columnDao
	 */
	public ColumnDao getColumnDao() {
		return columnDao;
	}


	/**
	 * @param systemLogDao the systemLogDao to set
	 */
	public void setSystemLogDao(SystemLogDao systemLogDao) {
		this.systemLogDao = systemLogDao;
	}
}
