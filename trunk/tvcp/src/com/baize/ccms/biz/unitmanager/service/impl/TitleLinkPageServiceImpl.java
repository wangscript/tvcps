/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.biz.unitmanager.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.baize.ccms.biz.articlemanager.dao.ArticleAttributeDao;
import com.baize.ccms.biz.articlemanager.domain.ArticleAttribute;
import com.baize.ccms.biz.articlemanager.domain.ArticleFormat;
import com.baize.ccms.biz.columnmanager.dao.ColumnDao;
import com.baize.ccms.biz.columnmanager.domain.Column;
import com.baize.ccms.biz.configmanager.dao.SystemLogDao;
import com.baize.ccms.biz.templatemanager.dao.TemplateUnitCategoryDao;
import com.baize.ccms.biz.templatemanager.dao.TemplateUnitDao;
import com.baize.ccms.biz.templatemanager.dao.TemplateUnitStyleDao;
import com.baize.ccms.biz.templatemanager.domain.TemplateUnit;
import com.baize.ccms.biz.templatemanager.domain.TemplateUnitCategory;
import com.baize.ccms.biz.templatemanager.domain.TemplateUnitStyle;
import com.baize.ccms.biz.unitmanager.service.TitleLinkPageService;
import com.baize.ccms.biz.unitmanager.web.form.PictureNewsForm;
import com.baize.ccms.biz.unitmanager.web.form.TitleLinkPageForm;
import com.baize.ccms.sys.GlobalConfig;
import com.baize.common.core.util.DateUtil;
import com.baize.common.core.util.IDFactory;
import com.baize.common.core.util.StringUtil;
import com.baize.common.core.util.XmlUtil;

/**
 * 
 * <p>标题: —— 标题连接业务逻辑处理类</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 模板单元管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-6-3 上午10:38:55
 * @history（历次修订内容、修订人、修订时间等）
 */
public class TitleLinkPageServiceImpl implements TitleLinkPageService {
	private static final Logger log = Logger.getLogger(TitleLinkPageServiceImpl.class);
	
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

	public void findConfig(TitleLinkPageForm titleLinkPageForm, String siteId) {	
		//单元ID
		String unitId = titleLinkPageForm.getUnit_unitId();
		String categoryId = titleLinkPageForm.getUnit_categoryId();
		String str1[] = {"unitId", "CategoryId"};
		Object obj1[] = {unitId, categoryId};
		List tempUnitList = templateUnitDao.findByNamedQuery("findTemplateUnitByUnitIdAndUnitCategory", str1, obj1);
		//页面显示列表使用的属性名
		List articleAttributeList = new ArrayList();
		
		if(tempUnitList != null && tempUnitList.size() > 0 ){
			TemplateUnit unit = (TemplateUnit) tempUnitList.get(0);
			String htmlContent = unit.getHtml();			
			String filePath = unit.getConfigFile();
			String columnId = "";
			if(filePath != null && !filePath.equals("")) {
				this.setXmlData(GlobalConfig.appRealPath + filePath, titleLinkPageForm);
				columnId = this.getColumnId(titleLinkPageForm.getUnit_columnId(), siteId, filePath); 
			} else {
				String haveconfigFilePath = GlobalConfig.appRealPath+TemplateUnit.UNIT_CONFIG_DIR+"/title_link_page.xml";
				this.setXmlData( haveconfigFilePath, titleLinkPageForm);
				columnId = titleLinkPageForm.getUnit_columnId();
			}		
			
			if(!StringUtil.isEmpty(columnId) && !columnId.equals("0") && !columnId.equals("null")) {
				//查询栏目表数据
				Column column = columnDao.getAndNonClear(columnId);
				if(column != null) {
					//根据栏目对象查询出文章格式
					ArticleFormat articleFormat = column.getArticleFormat();
					if(articleFormat != null) {
						//文章格式ID
						String articleFormatId = articleFormat.getId();
						articleAttributeList = articleAttributeDao.findByNamedQuery("findAttributesByFormatId", "formatId", articleFormatId);
					}
				}
			}
			//页面显示列表使用的属性名
			titleLinkPageForm.setArticleAttributeList(articleAttributeList);
			
			String img = "";
			String viewStyle = titleLinkPageForm.getViewStyle();
			if(viewStyle != null && !viewStyle.equals("")) {
				String str[] = viewStyle.split("##");
				if(str != null && str.length > 1) {
					img = str[1];
				}
			}
			titleLinkPageForm.setViewImg(img);
			titleLinkPageForm.setHtmlContent(htmlContent);
			titleLinkPageForm.setUnit_css(unit.getCss());
		} else {
			String columnId = "";
			columnId = titleLinkPageForm.getUnit_columnId();
			//查询栏目表数据
			Column column = columnDao.getAndNonClear(columnId);
			if(column != null) {
				//根据栏目对象查询出文章格式
				ArticleFormat articleFormat = column.getArticleFormat();
				if(articleFormat != null) {
					//文章格式ID
					String articleFormatId = articleFormat.getId();
					articleAttributeList = articleAttributeDao.findByNamedQuery("findAttributesByFormatId", "formatId", articleFormatId);
				}
			}
			//页面显示列表使用的属性名
			titleLinkPageForm.setArticleAttributeList(articleAttributeList);
			
			String configFile = GlobalConfig.appRealPath + TemplateUnit.UNIT_CONFIG_DIR+"/title_link_page.xml";
			this.setXmlData( configFile, titleLinkPageForm);
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
	
	public void saveConfigContent(TitleLinkPageForm titleLinkPageForm,String filePath, String siteId, String sessionID) {
		String unitId = titleLinkPageForm.getUnit_unitId();
		String categoryId = titleLinkPageForm.getUnit_categoryId();
		String htmlContent = titleLinkPageForm.getHtmlContent();
		TemplateUnit unit = templateUnitDao.getAndClear(unitId);
		if (unit != null) {
			TemplateUnitCategory templateUnitCategory = new TemplateUnitCategory();
			templateUnitCategory.setId(categoryId);
			unit.setTemplateUnitCategory(templateUnitCategory);
			unit.setHtml(htmlContent);
			unit.setConfigFile(filePath);
			unit.setCss(titleLinkPageForm.getUnit_css());
			String columnIds = getColumnId(titleLinkPageForm.getUnit_columnId(), siteId, filePath);
			if(!StringUtil.isEmpty(columnIds) && !columnIds.equals("null")){
				unit.setColumnIds(columnIds);
			}
			templateUnitDao.saveOrUpdate(unit);
		}
		
		// 写入日志
		String categoryName = "模板设置(标题链接)->保存";
		String param = unit.getName();
		systemLogDao.addLogData(categoryName, siteId, sessionID, param);
	}

	public String saveConfigXml(TitleLinkPageForm titleLinkPageForm) {
		String unitId = titleLinkPageForm.getUnit_unitId();
		//新xml文件的保存路径
		String newFilePath = "";
		String filePath = "";
		TemplateUnit templateUnit = templateUnitDao.getAndClear(unitId);
		if(templateUnit != null){
			String configFilePath = templateUnit.getConfigFile();
			String configDir = templateUnit.getConfigDir();
			String haveconfigFilePath = GlobalConfig.appRealPath+TemplateUnit.UNIT_CONFIG_DIR+"/title_link_page.xml";
			filePath = haveconfigFilePath;
			if(configFilePath != null && !configFilePath.equals("")){
				newFilePath = GlobalConfig.appRealPath+configFilePath;
			}else{
				newFilePath = GlobalConfig.appRealPath+configDir +"/"+ IDFactory.getId()+".xml";
			}
		}
		this.setXmlData(filePath, newFilePath, titleLinkPageForm);
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
	 * @param titleLinkForm 标题链接form对象
	 */
	private void setXmlData(String filePath, String newFilePath, TitleLinkPageForm titleLinkPageForm){
		String prefixDate = DateUtil.getAddDaysDateFormat(DateUtil.toStringTrim(new Date(), "yyyy-MM-dd"), StringUtil.parseInt(titleLinkPageForm.getTitleHeadValidity()));
		String suffixDate = DateUtil.getAddDaysDateFormat(DateUtil.toStringTrim(new Date(), "yyyy-MM-dd"), StringUtil.parseInt(titleLinkPageForm.getTitleEndValidity()));
		XmlUtil xmlUtil = XmlUtil.getInstance(filePath);		
		xmlUtil.setNodeCDATAText("baize/title-link-page/viewStyle",titleLinkPageForm.getViewStyle());
		xmlUtil.setNodeCDATAText("baize/title-link-page/contextFrom",titleLinkPageForm.getContextFrom());
		xmlUtil.setNodeCDATAText("baize/title-link-page/fixedColumn",titleLinkPageForm.getFixedColumn());
		xmlUtil.setNodeCDATAText("baize/title-link-page/pageInfoCount",titleLinkPageForm.getPageInfoCount()); 
		xmlUtil.setNodeCDATAText("baize/title-link-page/titleLimit",titleLinkPageForm.getTitleLimit());
		xmlUtil.setNodeCDATAText("baize/title-link-page/briefLimit",titleLinkPageForm.getBriefLimit());
		xmlUtil.setNodeCDATAText("baize/title-link-page/titleHead",titleLinkPageForm.getTitleHead());
		xmlUtil.setNodeCDATAText("baize/title-link-page/titleHeadPic",titleLinkPageForm.getTitleHeadPic());
		xmlUtil.setNodeCDATAText("baize/title-link-page/titleHeadValidity",titleLinkPageForm.getTitleHeadValidity());
		xmlUtil.setNodeCDATAText("baize/title-link-page/titleEnd",titleLinkPageForm.getTitleEnd());
		xmlUtil.setNodeCDATAText("baize/title-link-page/titleEndValidity",titleLinkPageForm.getTitleEndValidity());
		xmlUtil.setNodeCDATAText("baize/title-link-page/titleEndPic",titleLinkPageForm.getTitleEndPic());
		xmlUtil.setNodeCDATAText("baize/title-link-page/htmlContent",titleLinkPageForm.getHtmlContent());
		xmlUtil.setNodeCDATAText("baize/title-link-page/prefixDate", prefixDate);
		xmlUtil.setNodeCDATAText("baize/title-link-page/suffixDate", suffixDate);
		xmlUtil.setNodeCDATAText("baize/title-link-page/pageSite", titleLinkPageForm.getPageSite());
		xmlUtil.save(newFilePath);
	}
	/**
	 * 获取xml的值来设置form 	
	 * @param filePath xml文件的路径
	 * @param titleLinkPageForm 标题链接form对象
	 * @return titleLinkPageForm 标题链接form对象
	 */
	private TitleLinkPageForm setXmlData(String filePath, TitleLinkPageForm titleLinkPageForm){
		XmlUtil xmlUtil = XmlUtil.getInstance(filePath);
		titleLinkPageForm.setViewStyle(xmlUtil.getNodeText("baize/title-link-page/viewStyle"));
		titleLinkPageForm.setContextFrom(xmlUtil.getNodeText("baize/title-link-page/contextFrom"));
		titleLinkPageForm.setFixedColumn(xmlUtil.getNodeText("baize/title-link-page/fixedColumn"));
		titleLinkPageForm.setTitleLimit(xmlUtil.getNodeText("baize/title-link-page/titleLimit"));
		titleLinkPageForm.setPageInfoCount(xmlUtil.getNodeText("baize/title-link-page/pageInfoCount"));
		titleLinkPageForm.setBriefLimit(xmlUtil.getNodeText("baize/title-link-page/briefLimit"));
		titleLinkPageForm.setTitleHead(xmlUtil.getNodeText("baize/title-link-page/titleHead"));
		titleLinkPageForm.setTitleHeadPic(xmlUtil.getNodeText("baize/title-link-page/titleHeadPic"));
		titleLinkPageForm.setTitleHeadValidity(xmlUtil.getNodeText("baize/title-link-page/titleHeadValidity"));		
		titleLinkPageForm.setTitleEnd(xmlUtil.getNodeText("baize/title-link-page/titleEnd"));
		titleLinkPageForm.setTitleEndValidity(xmlUtil.getNodeText("baize/title-link-page/titleEndValidity"));
		titleLinkPageForm.setTitleEndPic(xmlUtil.getNodeText("baize/title-link-page/titleEndPic"));
		titleLinkPageForm.setHtmlContent(xmlUtil.getNodeText("baize/title-link-page/htmlContent"));
		titleLinkPageForm.setPageSite(xmlUtil.getNodeText("baize/title-link-page/pageSite"));
		return titleLinkPageForm;
	}
	
	/**
	 * 获取页面所选择的栏目
	 * @param columnId
	 * @param siteId
	 * @param filePath
	 * @return
	 */
	private String getColumnId(String columnId, String siteId, String filePath){
		XmlUtil xmlUtil = XmlUtil.getInstance(GlobalConfig.appRealPath + filePath);
		//内容来源
		String contextFrom = xmlUtil.getNodeText("baize/title-link-page/contextFrom");
		//栏目名称
		String fixedColumn = xmlUtil.getNodeText("baize/title-link-page/fixedColumn");
		String fixedColumnId = "";
		String strColumn[] = fixedColumn.split("##");
		if(strColumn != null && strColumn.length == 2){
			fixedColumnId = strColumn[0];
		}
		String newColumnId = "";
		Column column = new Column();
		if(!StringUtil.isEmpty(columnId) && !columnId.equals("0")) {
			 column = columnDao.getAndNonClear(columnId);
		} else {
			columnId = "";
		}
		// 当前栏目
		if(contextFrom.equals("1")) {
			newColumnId = columnId;
			
		// 当前栏目的父栏目	
		}else if(contextFrom.equals("2")) {
			if(column != null && column.getParent() != null) {
				newColumnId = column.getParent().getId();
			}
			
		// 指定栏目	
		}else if(contextFrom.equals("3")) {
			newColumnId = fixedColumnId;
			
		// 指定栏目的父栏目	
		}else if(contextFrom.equals("4")) {
			Column newcolumn = columnDao.getAndClear(fixedColumnId);
			if(newcolumn.getParent() != null) {
				newColumnId = newcolumn.getParent().getId();
			}
		}
		return newColumnId;
	}
	
	/**
	 * 按照栏目id查找属性 
	 * @param titleLinkPageForm
	 */
	public void findArticleAttributeByColumnId(TitleLinkPageForm titleLinkPageForm) {
		List articleAttributeList = new ArrayList();
		String columnId = "";
		columnId = titleLinkPageForm.getNodeId();
		String type = titleLinkPageForm.getContextFrom();
		String articleAttributeFieldName = "";
		String articleAttributeName = "";
		if(!StringUtil.isEmpty(columnId)) {
			//查询栏目表数据
			Column column = new Column();
			column = columnDao.getAndNonClear(columnId);
			if(column != null) {
				// 指定栏目父栏目 、当前栏目的父栏目
				if(type.equals("4") || type.equals("2")) {
					if(column.getParent() != null) {
						columnId = column.getParent().getId();
					} else {
						columnId = "";
					}
					if(!StringUtil.isEmpty(columnId)) {
						column = columnDao.getAndNonClear(columnId);
					} else {
						column = null;
					}
				}
				if(column != null) {
					// 根据栏目对象查询出文章格式
					ArticleFormat articleFormat = column.getArticleFormat();
					if(articleFormat != null) {
						//文章格式ID
						String articleFormatId = articleFormat.getId();
						articleAttributeList = articleAttributeDao.findByNamedQuery("findAttributesByFormatId", "formatId", articleFormatId);
						if(articleAttributeList != null && articleAttributeList.size() > 0) {
							for(int i = 0; i < articleAttributeList.size(); i++) {
								ArticleAttribute articleAttribute = (ArticleAttribute) articleAttributeList.get(i);
								if(!StringUtil.isEmpty(articleAttributeFieldName)) {
									articleAttributeFieldName += ":::" + articleAttribute.getFieldName();
								} else {
									articleAttributeFieldName = articleAttribute.getFieldName();
								}
								if(!StringUtil.isEmpty(articleAttributeName)) {
									articleAttributeName += ":::" + articleAttribute.getAttributeName();
								} else {
									articleAttributeName = articleAttribute.getAttributeName();
								}
							}
						}
					}
				}
			}
		}
		titleLinkPageForm.setArticleAttributeFieldName(articleAttributeFieldName);
		titleLinkPageForm.setArticleAttributeName(articleAttributeName);
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
