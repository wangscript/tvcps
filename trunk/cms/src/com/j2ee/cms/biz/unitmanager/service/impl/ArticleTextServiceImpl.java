/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.unitmanager.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.j2ee.cms.biz.articlemanager.dao.ArticleAttributeDao;
import com.j2ee.cms.biz.articlemanager.dao.ArticleFormatDao;
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
import com.j2ee.cms.biz.unitmanager.service.ArticleTextService;
import com.j2ee.cms.biz.unitmanager.web.form.ArticleTextForm;
import com.j2ee.cms.sys.GlobalConfig;
import com.j2ee.cms.common.core.util.FileUtil;
import com.j2ee.cms.common.core.util.IDFactory;
import com.j2ee.cms.common.core.util.StringUtil;
import com.j2ee.cms.common.core.util.XmlUtil;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CPS</p>
 * <p>版权: Copyright (c) 2009  
 * @author 郑荣华
 * @version 1.0
 * @since 2009-7-23 上午11:06:46
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class ArticleTextServiceImpl implements ArticleTextService {
	
	private static final Logger log = Logger.getLogger(ArticleTextServiceImpl.class);
	/** 注入模板单元dao */
	private TemplateUnitDao templateUnitDao;
	/** 注入栏目dao **/
	private ColumnDao columnDao;
	/** 注入文章属性dao **/
	private ArticleAttributeDao articleAttributeDao;
	/** 注入模板单元样式dao **/
	private TemplateUnitStyleDao templateUnitStyleDao;
	/**注入模板单元类别dao**/
	private TemplateUnitCategoryDao templateUnitCategoryDao;
	/** 文章格式dao */
	private ArticleFormatDao articleFormatDao;
	/** 注入日志dao*/
	private SystemLogDao systemLogDao;
	
	/**
	 * 查找配置文件
	 * @param form  栏目链接表单对象
	 */
	public void findConfig(ArticleTextForm form) {
		//单元ID
		String unitId = form.getUnit_unitId();
		//栏目ID
		String columnId = form.getUnit_columnId();
		//查询栏目表数据
		Column column = columnDao.getAndNonClear(columnId);
		List<ArticleAttribute> articleAttributeList = new ArrayList<ArticleAttribute>();
		if(column != null){
			//根据栏目对象查询出文章格式
			ArticleFormat articleFormat = column.getArticleFormat();
			if(articleFormat != null) {
				//文章格式ID
				String articleFormatId = articleFormat.getId();
				articleAttributeList = articleAttributeDao.findByNamedQuery("findAttributesByFormatId", "formatId", articleFormatId);
			}
		} else {
			boolean defaultFormat = true;
			ArticleFormat articleFormat = new ArticleFormat(); 
			List list = articleFormatDao.findByNamedQuery("findArticleFormatByDefaultFormat", "defaultFormat", defaultFormat);
			if(list != null && list.size() > 0) {
				articleFormat = (ArticleFormat) list.get(0);
				String articleFormatId = articleFormat.getId();
				articleAttributeList = articleAttributeDao.findByNamedQuery("findAttributesByFormatId", "formatId", articleFormatId);
			}
		}
		//页面显示列表使用的属性名(字段标签)
		form.setArticleAttributeList(articleAttributeList);
		String categoryId = form.getUnit_categoryId();
		String str1[] = {"unitId", "CategoryId"};
		Object obj1[] = {unitId, categoryId};
		List tempUnitList = templateUnitDao.findByNamedQuery("findTemplateUnitByUnitIdAndUnitCategory", str1, obj1);
		if(tempUnitList != null && tempUnitList.size() > 0 ){
			TemplateUnit unit = (TemplateUnit)tempUnitList.get(0);
			String htmlContent = unit.getHtml();			
			String filePath = unit.getConfigFile();
			if(filePath != null && !filePath.equals("")) {
				form = this.setXmlData(GlobalConfig.appRealPath + filePath, form);
			} else {
				String haveconfigFilePath = GlobalConfig.appRealPath + TemplateUnit.UNIT_CONFIG_DIR + "\\article_text.xml";
				form = this.setXmlData(haveconfigFilePath, form);
			}
			form.setHtmlContent(htmlContent);
			String unit_css = "";
			// 单元类别不为空
			if(unit.getTemplateUnitCategory() != null) {
				if(!StringUtil.isEmpty(unit.getCss())) {
					unit_css = unit.getCss();
				} else {
					String templteUnitCategoryId = unit.getTemplateUnitCategory().getId();
					TemplateUnitCategory unitCategory = templateUnitCategoryDao.getAndClear(templteUnitCategoryId);
					unit_css = unitCategory.getCss();
				}
			} else {
				String templteUnitCategoryId = form.getUnit_categoryId();
				TemplateUnitCategory unitCategory = templateUnitCategoryDao.getAndClear(templteUnitCategoryId);
				unit_css = unitCategory.getCss();
			}
			form.setUnit_css(unit_css);
		}else{
			String configFile = GlobalConfig.appRealPath + TemplateUnit.UNIT_CONFIG_DIR+"/article_text.xml";
			this.setXmlData(configFile, form);
		}
		String viewStyle = form.getArticleTextStyle();
		form.setStyleId(viewStyle);
		String viewImg = "";
		if(viewStyle != null && !viewStyle.equals("") && !viewStyle.equals("0")) {
			TemplateUnitStyle templateUnitStyle = (TemplateUnitStyle)templateUnitStyleDao.getAndNonClear(viewStyle);
			viewImg = templateUnitStyle.getDisplayEffect();
		}else{
			viewImg = "";
		}
		form.setArticleTextStyle(viewImg);
	}
	
	/**
	 * 获取xml的值来设置form 	
	 * @param filePath xml文件的路径
	 * @param titleLinkForm 标题链接form对象
	 * @return TitleLinkForm 标题链接form对象
	 */
	private ArticleTextForm setXmlData(String filePath, ArticleTextForm form) {
		XmlUtil xmlUtil = XmlUtil.getInstance(filePath);
		// 获取xml文件中的字段信息
		String style = xmlUtil.getNodeText("/j2ee.cms/article-text/article-text-style");
		String comment = xmlUtil.getNodeText("/j2ee.cms/article-text/article-text-comment");
		String commentPic = xmlUtil.getNodeText("/j2ee.cms/article-text/article-text-comment-pic");
		String pageSize = xmlUtil.getNodeText("/j2ee.cms/article-text/article-text-pageSize");
		int size = StringUtil.parseInt(pageSize);
		// 向表单中加入栏目链接的各种信息
		form.setArticleTextStyle(style);
		form.setArticleTextComment(comment);
		form.setArticleTextCommentPic(commentPic);
		form.setPageSize(size);
		return form;
	}
	
	/**
	 * 按照类别id和网站id查询模板单元样式
	 * @param unit_categoryId
	 * @param siteId
	 * @return
	 */
	public List<TemplateUnitStyle> findTemplateUnitStyleByCategoryIdAndSiteId(String unit_categoryId, String siteId) {
		String[] param = {"unit_categoryId", "siteId"};
		Object[] value = {unit_categoryId, siteId};
		List<TemplateUnitStyle> templateUnitStyleList = templateUnitStyleDao.findByNamedQuery("findTemplateUnitStyleByCategoryIdAndSiteId", param, value);
		if(templateUnitStyleList != null && templateUnitStyleList.size() > 0){
			return templateUnitStyleList;
		}else{
			return new ArrayList<TemplateUnitStyle>();
		}
	}
	
	/**
	 * 保存配置文件
	 * @param titleLinkForm
	 * @return
	 */
	public String saveConfigXml(ArticleTextForm form) {
		String unitId = form.getUnit_unitId();
		TemplateUnit templateUnit = templateUnitDao.getAndClear(unitId);
		String configFilePath = templateUnit.getConfigFile();
		String configDir = templateUnit.getConfigDir();
		String haveconfigFilePath = GlobalConfig.appRealPath + TemplateUnit.UNIT_CONFIG_DIR + "/article_text.xml";
		String filePath = haveconfigFilePath;
		//新xml文件的保存路径
		String newFilePath = "";
		// 模板单元的配置文件存在则先删除
		if(configFilePath != null && !configFilePath.equals("")) {
			FileUtil.delete(GlobalConfig.appRealPath + configFilePath);
		}
		newFilePath = GlobalConfig.appRealPath + configDir + "/" + IDFactory.getId() + ".xml";
		this.setXmlData(filePath, newFilePath, form);
		return newFilePath;
	}
	
	/**
	 * 设置xml的值	
	 * @param filePath        xml文件的路径
	 * @param newFilePath     新xml文件的路径
	 * @param articleTextForm 文章正文form对象
	 */
	private void setXmlData(String filePath, String newFilePath, ArticleTextForm articleTextForm) {
		XmlUtil xmlUtil = XmlUtil.getInstance(filePath);		
		xmlUtil.setNodeCDATAText("j2ee.cms/article-text/article-text-style", articleTextForm.getArticleTextStyle());
		xmlUtil.setNodeCDATAText("j2ee.cms/article-text/article-text-comment", articleTextForm.getArticleTextComment());
		xmlUtil.setNodeCDATAText("j2ee.cms/article-text/article-text-comment-pic", articleTextForm.getArticleTextCommentPic());
		xmlUtil.setNodeCDATAText("j2ee.cms/article-text/article-text-pageSize", String.valueOf(articleTextForm.getPageSize()));
		xmlUtil.save(newFilePath);
	}
	
	/**
	 * 保存配置内容
	 * @param titleLinkForm
	 * @param filePath
	 */
	public void saveConfigContent(ArticleTextForm form, String filePath, String siteId, String sessionID) {
		String unitId = form.getUnit_unitId();
		String categoryId = form.getUnit_categoryId();
		log.debug("htmContent======"+form.getHtmlContent());
		String htmlContent = form.getHtmlContent();
		TemplateUnit unit = templateUnitDao.getAndClear(unitId);
		if (unit != null) {
			TemplateUnitCategory templateUnitCategory = new TemplateUnitCategory();
			templateUnitCategory.setId(categoryId);
			unit.setTemplateUnitCategory(templateUnitCategory);
			unit.setHtml(htmlContent);
			unit.setConfigFile(filePath);
			unit.setCss(form.getUnit_css());
			//unit.setScript("<script type=\"text/javascript\" src=\"/script/analyzeArticleText.js\"></script>");
			String columnIds = form.getUnit_columnId();
			if(!StringUtil.isEmpty(columnIds) && !columnIds.equals("null")){
				unit.setColumnIds(columnIds);
			}
			templateUnitDao.saveOrUpdate(unit);
		}
		
		// 写入日志文件
		String categoryName = "模板设置(文章正文)->保存";
		String param = unit.getName();
		systemLogDao.addLogData(categoryName, siteId, sessionID, param);
	}
	
	/**
	 * 站内保存
	 * @param form    文章正文表单对象
	 * @param unitId  单元id
	 */
	public void saveSiteConfig(ArticleTextForm form, String unitId, String siteId, String sessionID) {
		List<TemplateUnit> list = new ArrayList<TemplateUnit>();
		list = this.findTemplateUnitByUnitName(unitId);
		for(int i = 0; i < list.size(); i++) {
			TemplateUnit newTemplateUnit = new TemplateUnit();
			newTemplateUnit = (TemplateUnit) list.get(i);
			form.setUnit_unitId(newTemplateUnit.getId());
			//保存配置信息到xml文件
			String filePath = this.saveConfigXml(form);
			filePath = filePath.replace(GlobalConfig.appRealPath, "");
			//保存生成的代码到数据库
			this.saveConfigContent(form, filePath, siteId, sessionID);
		}
	}
	
	/**
	 * 按照单元名字查找模板单元
	 * @param unitId   要站内保存的某个单元id（通过此id获得要的单元名字）
	 * @return
	 */
	private List<TemplateUnit> findTemplateUnitByUnitName(String unitId) {
		List<TemplateUnit> list = new ArrayList<TemplateUnit>();
		TemplateUnit templateUnit = templateUnitDao.getAndClear(unitId);
		// 要查找的单元名字
		String name = templateUnit.getName();
		list = templateUnitDao.findByNamedQuery("findTemplateUnitByUnitName", "name", name);	
		return list;
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
	 * @return the columnDao
	 */
	public ColumnDao getColumnDao() {
		return columnDao;
	}
	/**
	 * @param columnDao the columnDao to set
	 */
	public void setColumnDao(ColumnDao columnDao) {
		this.columnDao = columnDao;
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
	 * @return the articleFormatDao
	 */
	public ArticleFormatDao getArticleFormatDao() {
		return articleFormatDao;
	}

	/**
	 * @param articleFormatDao the articleFormatDao to set
	 */
	public void setArticleFormatDao(ArticleFormatDao articleFormatDao) {
		this.articleFormatDao = articleFormatDao;
	}

	/**
	 * @param systemLogDao the systemLogDao to set
	 */
	public void setSystemLogDao(SystemLogDao systemLogDao) {
		this.systemLogDao = systemLogDao;
	}
	
}
