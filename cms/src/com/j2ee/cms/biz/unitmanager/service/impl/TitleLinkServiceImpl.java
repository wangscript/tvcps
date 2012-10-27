/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.unitmanager.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.j2ee.cms.biz.articlemanager.dao.ArticleAttributeDao;
import com.j2ee.cms.biz.articlemanager.domain.ArticleAttribute;
import com.j2ee.cms.biz.articlemanager.domain.ArticleFormat;
import com.j2ee.cms.biz.columnmanager.dao.ColumnDao;
import com.j2ee.cms.biz.columnmanager.domain.Column;
import com.j2ee.cms.biz.configmanager.dao.SystemLogDao;
import com.j2ee.cms.biz.templatemanager.dao.TemplateUnitCategoryDao;
import com.j2ee.cms.biz.templatemanager.dao.TemplateUnitDao;
import com.j2ee.cms.biz.templatemanager.dao.TemplateUnitStyleDao;
import com.j2ee.cms.biz.templatemanager.domain.TemplateUnit;
import com.j2ee.cms.biz.templatemanager.domain.TemplateUnitCategory;
import com.j2ee.cms.biz.templatemanager.domain.TemplateUnitStyle;
import com.j2ee.cms.biz.unitmanager.service.TitleLinkService;
import com.j2ee.cms.biz.unitmanager.web.form.TitleLinkForm;
import com.j2ee.cms.sys.GlobalConfig;
import com.j2ee.cms.common.core.util.IDFactory;
import com.j2ee.cms.common.core.util.StringUtil;
import com.j2ee.cms.common.core.util.XmlUtil;

/**
 * 
 * <p>标题: —— 标题连接业务逻辑处理类</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 模板单元管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-6-3 上午10:38:55
 * @history（历次修订内容、修订人、修订时间等）
 */
public class TitleLinkServiceImpl implements TitleLinkService {
	private static final Logger log = Logger.getLogger(TitleLinkServiceImpl.class);
	
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

	public void findConfig(TitleLinkForm titleLinkForm, String siteId) {	
		//单元ID
		String unitId = titleLinkForm.getUnit_unitId();
		/*List list = ArticleFormat.ATTRIBUTES_DEFAULT;
		for(int i = 0; i < list.size(); i++) {
			articleAttributeList.add(list.get(i));
		}*/
		String categoryId = titleLinkForm.getUnit_categoryId();
		List articleAttributeList = new ArrayList();
	//	TemplateUnit unit = templateUnitDao.getAndClear(unitId);	
		String str1[] = {"unitId","CategoryId"};
		Object obj1[] = {unitId,categoryId};
		List tempUnitList = templateUnitDao.findByNamedQuery("findTemplateUnitByUnitIdAndUnitCategory", str1, obj1);
	
	//	if (unit != null) {
		if(tempUnitList != null && tempUnitList.size() > 0 ){
			TemplateUnit unit = (TemplateUnit)tempUnitList.get(0);
			String htmlContent = unit.getHtml();		
			
			String filePath = unit.getConfigFile();
			String columnId = "";
			if(filePath != null && !filePath.equals("")) {
				this.setXmlData( GlobalConfig.appRealPath+filePath, titleLinkForm);
				columnId = this.getColumnId(titleLinkForm.getUnit_columnId(), siteId, filePath);
			} else {
				String haveconfigFilePath = GlobalConfig.appRealPath+TemplateUnit.UNIT_CONFIG_DIR+"/title_link.xml";
				this.setXmlData( haveconfigFilePath, titleLinkForm);
				columnId = titleLinkForm.getUnit_columnId();
			}
			if(titleLinkForm.getContextFrom() != null){
				 if(titleLinkForm.getContextFrom().equals("3")){
					XmlUtil xmlUtil = XmlUtil.getInstance(GlobalConfig.appRealPath+filePath);
					String columnName = xmlUtil.getNodeText("j2ee.cms/title-link/moreColumnName");
					// 指定一个栏目
					if(columnName.split("##")[0].split(",").length == 1) {
						columnId = columnName.split("##")[0];
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
				 }else{
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
			}
		
			//页面显示列表使用的属性名
			titleLinkForm.setArticleAttributeList(articleAttributeList);
			
			String img = "";
			String viewStyle = titleLinkForm.getViewStyle();
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
		
			titleLinkForm.setViewImg(viewImg);
			titleLinkForm.setHtmlContent(htmlContent);

			titleLinkForm.setUnit_css(unit.getCss());
		}else{
			String columnId = "";
			columnId = titleLinkForm.getUnit_columnId();
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
			titleLinkForm.setArticleAttributeList(articleAttributeList);
			
//			TemplateUnitCategory templateUnitCategory = templateUnitCategoryDao.getAndNonClear(categoryId);
			String configFile = GlobalConfig.appRealPath + TemplateUnit.UNIT_CONFIG_DIR+"/title_link.xml";
			this.setXmlData( configFile, titleLinkForm);
		}
	}

	/**
	 * 获取页面所选择的栏目
	 * @param columnId
	 * @param siteId
	 * @param filePath
	 * @return
	 */
	private String getColumnId(String columnId, String siteId, String filePath){
		XmlUtil xmlUtil = XmlUtil.getInstance(GlobalConfig.appRealPath+filePath);
		//内容来源
		String contextFrom = xmlUtil.getNodeText("j2ee.cms/title-link/contextFrom");
		//栏目名称
		String columnName = xmlUtil.getNodeText("j2ee.cms/title-link/moreColumnName");
		String fixedColumnId = "";
		String strColumn[] = columnName.split("##");
		if(strColumn != null && strColumn.length == 2){
			fixedColumnId = strColumn[0];
		}
		String newColumnId = "";
		Column column = new Column();
		if(!columnId.equals("0") && !columnId.equals("") && columnId != null) {
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
	
	public void saveConfigContent(TitleLinkForm titleLinkForm,String filePath, String siteId, String sessionID) {
		String unitId = titleLinkForm.getUnit_unitId();
		String categoryId = titleLinkForm.getUnit_categoryId();
	
		String htmlContent = titleLinkForm.getHtmlContent();
		TemplateUnit unit = templateUnitDao.getAndClear(unitId);
		if (unit != null) {
			TemplateUnitCategory templateUnitCategory = new TemplateUnitCategory();
			templateUnitCategory.setId(categoryId);
			unit.setTemplateUnitCategory(templateUnitCategory);
			unit.setHtml(htmlContent);
			unit.setConfigFile(filePath);
			unit.setCss(titleLinkForm.getUnit_css());
			String columnIds = "";
			columnIds = getColumnId(titleLinkForm.getUnit_columnId(), siteId, filePath);
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

	public String saveConfigXml(TitleLinkForm titleLinkForm) {
		String unitId = titleLinkForm.getUnit_unitId();
		String categoryId = titleLinkForm.getUnit_categoryId();
		log.debug("unitId===================="+unitId);
		//新xml文件的保存路径
		String newFilePath = "";
		String filePath = "";
		TemplateUnit templateUnit = templateUnitDao.getAndClear(unitId);
		if(templateUnit != null){
			String configFilePath = templateUnit.getConfigFile();
			String configDir = templateUnit.getConfigDir();
			String haveconfigFilePath = GlobalConfig.appRealPath+TemplateUnit.UNIT_CONFIG_DIR+"\\title_link.xml";
			filePath = haveconfigFilePath;
		
			if(configFilePath != null && !configFilePath.equals("")){
			//	filePath = GlobalConfig.appRealPath+configFilePath;
				newFilePath = GlobalConfig.appRealPath+configFilePath;
			}else{
				newFilePath = GlobalConfig.appRealPath+configDir +"/"+ IDFactory.getId()+".xml";
			}
		}
	
		
		this.setXmlData(filePath, newFilePath, titleLinkForm);
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
	private void setXmlData(String filePath,String newFilePath,TitleLinkForm titleLinkForm){
		XmlUtil xmlUtil = XmlUtil.getInstance(filePath);		
		xmlUtil.setNodeCDATAText("j2ee.cms/title-link/viewStyle",titleLinkForm.getViewStyle());
		xmlUtil.setNodeCDATAText("j2ee.cms/title-link/contextFrom",titleLinkForm.getContextFrom());
		xmlUtil.setNodeCDATAText("j2ee.cms/title-link/columnName",titleLinkForm.getColumnName());
		xmlUtil.setNodeCDATAText("j2ee.cms/title-link/moreColumnName",titleLinkForm.getMoreColumnName());
		xmlUtil.setNodeCDATAText("j2ee.cms/title-link/start",titleLinkForm.getStart());
		xmlUtil.setNodeCDATAText("j2ee.cms/title-link/col",titleLinkForm.getCol());
		xmlUtil.setNodeCDATAText("j2ee.cms/title-link/row",titleLinkForm.getRow());
		xmlUtil.setNodeCDATAText("j2ee.cms/title-link/titleLimit",titleLinkForm.getTitleLimit());
		xmlUtil.setNodeCDATAText("j2ee.cms/title-link/moreLink",titleLinkForm.getMoreLink());
		xmlUtil.setNodeCDATAText("j2ee.cms/title-link/moreLinkPic",titleLinkForm.getMoreLinkPic());
		xmlUtil.setNodeCDATAText("j2ee.cms/title-link/titleHead",titleLinkForm.getTitleHead());
		xmlUtil.setNodeCDATAText("j2ee.cms/title-link/titleHeadPic",titleLinkForm.getTitleHeadPic());
		xmlUtil.setNodeCDATAText("j2ee.cms/title-link/titleHeadValidity",titleLinkForm.getTitleHeadValidity());
		xmlUtil.setNodeCDATAText("j2ee.cms/title-link/titleEnd",titleLinkForm.getTitleEnd());
		xmlUtil.setNodeCDATAText("j2ee.cms/title-link/titleEndValidity",titleLinkForm.getTitleEndValidity());
		xmlUtil.setNodeCDATAText("j2ee.cms/title-link/titleEndPic",titleLinkForm.getTitleEndPic());
		xmlUtil.setNodeCDATAText("j2ee.cms/title-link/lineStyle",titleLinkForm.getLineStyle());
		xmlUtil.setNodeCDATAText("j2ee.cms/title-link/lineGroup",titleLinkForm.getLineGroup());
		xmlUtil.setNodeCDATAText("j2ee.cms/title-link/oddColor",titleLinkForm.getOddColor());
		xmlUtil.setNodeCDATAText("j2ee.cms/title-link/evenColor",titleLinkForm.getEvenColor());
		xmlUtil.setNodeCDATAText("j2ee.cms/title-link/strongTitle",titleLinkForm.getStrongTitle());
		xmlUtil.save(newFilePath);
	}
	/**
	 * 获取xml的值来设置form 	
	 * @param filePath xml文件的路径
	 * @param titleLinkForm 标题链接form对象
	 * @return TitleLinkForm 标题链接form对象
	 */
	private TitleLinkForm setXmlData(String filePath ,TitleLinkForm titleLinkForm){
		XmlUtil xmlUtil = XmlUtil.getInstance(filePath);
		titleLinkForm.setViewStyle(xmlUtil.getNodeText("j2ee.cms/title-link/viewStyle"));
		titleLinkForm.setContextFrom(xmlUtil.getNodeText("j2ee.cms/title-link/contextFrom"));
		titleLinkForm.setColumnName(xmlUtil.getNodeText("j2ee.cms/title-link/columnName"));
		titleLinkForm.setMoreColumnName(xmlUtil.getNodeText("j2ee.cms/title-link/moreColumnName"));
		titleLinkForm.setStart(xmlUtil.getNodeText("j2ee.cms/title-link/start"));
		titleLinkForm.setCol(xmlUtil.getNodeText("j2ee.cms/title-link/col"));
		titleLinkForm.setRow(xmlUtil.getNodeText("j2ee.cms/title-link/row"));
		titleLinkForm.setTitleLimit(xmlUtil.getNodeText("j2ee.cms/title-link/titleLimit"));
		titleLinkForm.setMoreLink(xmlUtil.getNodeText("j2ee.cms/title-link/moreLink"));
		titleLinkForm.setMoreLinkPic(xmlUtil.getNodeText("j2ee.cms/title-link/moreLinkPic"));
		titleLinkForm.setTitleHead(xmlUtil.getNodeText("j2ee.cms/title-link/titleHead"));
		titleLinkForm.setTitleHeadPic(xmlUtil.getNodeText("j2ee.cms/title-link/titleHeadPic"));
		titleLinkForm.setTitleHeadValidity(xmlUtil.getNodeText("j2ee.cms/title-link/titleHeadValidity"));		
		titleLinkForm.setTitleEnd(xmlUtil.getNodeText("j2ee.cms/title-link/titleEnd"));
		titleLinkForm.setTitleEndValidity(xmlUtil.getNodeText("j2ee.cms/title-link/titleEndValidity"));
		titleLinkForm.setTitleEndPic(xmlUtil.getNodeText("j2ee.cms/title-link/titleEndPic"));
		titleLinkForm.setLineStyle(xmlUtil.getNodeText("j2ee.cms/title-link/lineStyle"));
		titleLinkForm.setLineGroup(xmlUtil.getNodeText("j2ee.cms/title-link/lineGroup"));
		titleLinkForm.setOddColor(xmlUtil.getNodeText("j2ee.cms/title-link/oddColor"));
		titleLinkForm.setEvenColor(xmlUtil.getNodeText("j2ee.cms/title-link/evenColor"));
		titleLinkForm.setStrongTitle(xmlUtil.getNodeText("j2ee.cms/title-link/strongTitle"));
		return titleLinkForm;
	}
	
	/**
	 * 按照栏目id查找属性
	 */
	public void findArticleAttributeByColumnId(TitleLinkForm titleLinkForm) {
		List articleAttributeList = new ArrayList();
		String columnId = "";
		columnId = titleLinkForm.getNodeId();
		String type = titleLinkForm.getContextFrom();
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
		titleLinkForm.setArticleAttributeFieldName(articleAttributeFieldName);
		titleLinkForm.setArticleAttributeName(articleAttributeName);
	}
	
	
	public void findStyleByStyleId(TitleLinkForm titleLinkForm,String siteId){
		String unit_categoryId = titleLinkForm.getUnit_categoryId();		
		String styleId = titleLinkForm.getStyleId();
		String str[] = {"styleId","unit_categoryId","siteId"};
		Object obj[] = {styleId,unit_categoryId,siteId};
		List templateUnitStyleList = templateUnitStyleDao.findByNamedQuery("findTemplateUnitStyleByStyleIdAndCategoryIdAndSiteId", str, obj);
		if(templateUnitStyleList != null && templateUnitStyleList.size() > 0){
			TemplateUnitStyle templateUnitStyle = (TemplateUnitStyle)templateUnitStyleList.get(0);
			String content = templateUnitStyle.getContent();			
			String displayEffect = templateUnitStyle.getDisplayEffect();
			titleLinkForm.setStylePreview(displayEffect);
			titleLinkForm.setStyleContent(content);
		} 
		
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
