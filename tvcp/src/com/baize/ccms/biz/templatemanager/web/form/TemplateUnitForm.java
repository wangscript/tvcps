/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.templatemanager.web.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.j2ee.cms.biz.columnmanager.domain.Column;
import com.j2ee.cms.biz.sitemanager.domain.Site;
import com.j2ee.cms.biz.templatemanager.domain.TemplateCategory;
import com.j2ee.cms.biz.templatemanager.domain.TemplateUnit;
import com.j2ee.cms.biz.templatemanager.domain.TemplateUnitCategory;
import com.j2ee.cms.common.core.web.GeneralForm;

/**
 * <p>标题: 模板单元表单</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 模板管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-5-13 上午09:39:05
 * @history（历次修订内容、修订人、修订时间等）
 */
public class TemplateUnitForm extends GeneralForm {

	private static final long serialVersionUID = -5072588001284871984L;

	/** 将模板类别id和模板实例id，name，url拼成字符串 **/
	private String templateInstanceStr;
	/** 模板类别列表 **/
	private List<TemplateCategory> templateCategoryList = new ArrayList<TemplateCategory>();
	/** 栏目 **/
	private Column column = new Column();
	/** 网站 **/
	private Site site = new Site();
	/** 要选择的模板类型 **/
	private String templateType;
	
	/** 模板输出的html内容 */
	private String htmlContent;
	
	/** 单元ID */
	private String unitId;
	
	/** 模板实例ID */
	private String instanceId;
	
	/** 单元类别ID */
	private String categoryId;
	
	/** 栏目ID */
	private String columnId;
	
	/** 文章ID */
	private String articleId;
	
	/** 配置URL */
	private String configUrl;
	
	/** 当前单元类别 */
	private TemplateUnitCategory unitCategory;
	
	/** 模板单元 */
	private List<TemplateUnit> templateUnits;
	
	/** 模板单元类别 */
	private List<TemplateUnitCategory> templateUnitCategories;
	
	/** 栏目列表 */
	private List<Column> columnList = new ArrayList<Column>();
	
	/** 单元设置路径 */
	private String unitSetPath;
	
	/** 栏目下面模板实例 */
	private String templateInstance;
	
	/** 实例名称 */
    private String instanceName;
    
    /** 模板实例id */
    private String templateInstanceIdValue;
    
    /** 模板是否被设置 */
    private String isTemplateSeted;
    
    /** 将模板类别id和模板id，name，url拼成字符串*/
    private String templateStr;
    
    /** 是否是在模版设置时添加模版实例 */
	private String templateSet;
	
	/** 该栏目是否有模板设置权限 */
	private String columnTemplateSet; 
    
	/**
	 * @return the instanceName
	 */
	public String getInstanceName() {
		return instanceName;
	}

	/**
	 * @param instanceName the instanceName to set
	 */
	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	/**
	 * @return the templateInstance
	 */
	public String getTemplateInstance() {
		return templateInstance;
	}

	/**
	 * @param templateInstance the templateInstance to set
	 */
	public void setTemplateInstance(String templateInstance) {
		this.templateInstance = templateInstance;
	}

	/**
	 * @return the column
	 */
	public Column getColumn() {
		return column;
	}

	/**
	 * @param column the column to set
	 */
	public void setColumn(Column column) {
		this.column = column;
	}

	/**
	 * @return the site
	 */
	public Site getSite() {
		return site;
	}

	/**
	 * @param site the site to set
	 */
	public void setSite(Site site) {
		this.site = site;
	}

	/**
	 * @return the templateCategoryList
	 */
	public List<TemplateCategory> getTemplateCategoryList() {
		return templateCategoryList;
	}

	/**
	 * @param templateCategoryList the templateCategoryList to set
	 */
	public void setTemplateCategoryList(List<TemplateCategory> templateCategoryList) {
		this.templateCategoryList = templateCategoryList;
	}

	/**
	 * @return the templateInstanceStr
	 */
	public String getTemplateInstanceStr() {
		return templateInstanceStr;
	}

	/**
	 * @param templateInstanceStr the templateInstanceStr to set
	 */
	public void setTemplateInstanceStr(String templateInstanceStr) {
		this.templateInstanceStr = templateInstanceStr;
	}
	
	@Override
	protected void validateData(ActionMapping arg0, HttpServletRequest arg1) {
	}

	/**
	 * @return the htmlContent
	 */
	public String getHtmlContent() {
		return htmlContent;
	}

	/**
	 * @param htmlContent the htmlContent to set
	 */
	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}

	/**
	 * @return the unitId
	 */
	public String getUnitId() {
		return unitId;
	}

	/**
	 * @param unitId the unitId to set
	 */
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	/**
	 * @return the instanceId
	 */
	public String getInstanceId() {
		return instanceId;
	}

	/**
	 * @param instanceId the instanceId to set
	 */
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	/**
	 * @return the templateUnits
	 */
	public List<TemplateUnit> getTemplateUnits() {
		return templateUnits;
	}

	/**
	 * @param templateUnits the templateUnits to set
	 */
	public void setTemplateUnits(List<TemplateUnit> templateUnits) {
		this.templateUnits = templateUnits;
	}

	/**
	 * @return the templateUnitCategories
	 */
	public List<TemplateUnitCategory> getTemplateUnitCategories() {
		return templateUnitCategories;
	}

	/**
	 * @param templateUnitCategories the templateUnitCategories to set
	 */
	public void setTemplateUnitCategories(
			List<TemplateUnitCategory> templateUnitCategories) {
		this.templateUnitCategories = templateUnitCategories;
	}

	/**
	 * @return the categoryId
	 */
	public String getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
	/**
	 * @return the unitCategory
	 */
	public TemplateUnitCategory getUnitCategory() {
		return unitCategory;
	}

	/**
	 * @param unitCategory the unitCategory to set
	 */
	public void setUnitCategory(TemplateUnitCategory unitCategory) {
		this.unitCategory = unitCategory;
	}

	/**
	 * @return the configUrl
	 */
	public String getConfigUrl() {
		return configUrl;
	}

	/**
	 * @param configUrl the configUrl to set
	 */
	public void setConfigUrl(String configUrl) {
		this.configUrl = configUrl;
	}

	/**
	 * @return the unitSetPath
	 */
	public String getUnitSetPath() {
		return unitSetPath;
	}

	/**
	 * @param unitSetPath the unitSetPath to set
	 */
	public void setUnitSetPath(String unitSetPath) {
		this.unitSetPath = unitSetPath;
	}

	/**
	 * @return the templateType
	 */
	public String getTemplateType() {
		return templateType;
	}

	/**
	 * @param templateType the templateType to set
	 */
	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	/**
	 * @return the columnId
	 */
	public String getColumnId() {
		return columnId;
	}

	/**
	 * @param columnId the columnId to set
	 */
	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	/**
	 * @return the columnList
	 */
	public List<Column> getColumnList() {
		return columnList;
	}

	/**
	 * @param columnList the columnList to set
	 */
	public void setColumnList(List<Column> columnList) {
		this.columnList = columnList;
	}

	/**
	 * @return the templateInstanceIdValue
	 */
	public String getTemplateInstanceIdValue() {
		return templateInstanceIdValue;
	}

	/**
	 * @param templateInstanceIdValue the templateInstanceIdValue to set
	 */
	public void setTemplateInstanceIdValue(String templateInstanceIdValue) {
		this.templateInstanceIdValue = templateInstanceIdValue;
	}

	/**
	 * @return the isTemplateSeted
	 */
	public String getIsTemplateSeted() {
		return isTemplateSeted;
	}

	/**
	 * @param isTemplateSeted the isTemplateSeted to set
	 */
	public void setIsTemplateSeted(String isTemplateSeted) {
		this.isTemplateSeted = isTemplateSeted;
	}

	/**
	 * @return the templateStr
	 */
	public String getTemplateStr() {
		return templateStr;
	}

	/**
	 * @param templateStr the templateStr to set
	 */
	public void setTemplateStr(String templateStr) {
		this.templateStr = templateStr;
	}

	/**
	 * @return the templateSet
	 */
	public String getTemplateSet() {
		return templateSet;
	}

	/**
	 * @param templateSet the templateSet to set
	 */
	public void setTemplateSet(String templateSet) {
		this.templateSet = templateSet;
	}

	/**
	 * @return the articleId
	 */
	public String getArticleId() {
		return articleId;
	}

	/**
	 * @param articleId the articleId to set
	 */
	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	/**
	 * @return the isTemplateSet
	 */
	public String getColumnTemplateSet() {
		return columnTemplateSet;
	}

	/**
	 * @param isTemplateSet the isTemplateSet to set
	 */
	public void setColumnTemplateSet(String columnTemplateSet) {
		this.columnTemplateSet = columnTemplateSet;
	}

}
