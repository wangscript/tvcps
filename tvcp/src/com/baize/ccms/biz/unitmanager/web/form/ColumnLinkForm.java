/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.unitmanager.web.form;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.j2ee.cms.biz.articlemanager.domain.ArticleAttribute;
import com.j2ee.cms.biz.templatemanager.domain.TemplateUnitStyle;
import com.j2ee.cms.biz.unitmanager.label.ColumnLinkLabel;
import com.j2ee.cms.biz.unitmanager.label.CommonLabel;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CPS</p>
 * <p>版权: Copyright (c) 2009  
 * @author 郑荣华
 * @version 1.0
 * @since 2009-6-1 下午05:43:33
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class ColumnLinkForm extends UnitForm {

	private static final long serialVersionUID = -2936633294108893089L;

	/** 栏目显示样式 **/
	private String columnStyle;
	/** 栏目显示类型 **/
	private String columnType;
	/** 信息显示行 **/
	private String columnRow;
	/** 信息显示列 **/
	private String columnCol;
	/** 栏目前缀 **/
	private String columnPrefix;
	/** 前缀有效期 **/
	private String columnPrefixDate;
	/** 前缀是否是图片 **/
	private String columnPrefixPic;
	/** 栏目后缀 **/
	private String columnSuffix;
	/** 后缀有效期 **/
	private String columnSuffixDate;
	/** 后缀是否是图片 **/
	private String columnSuffixPic;
	/** xml配置文件路径 **/
	private String configPath;
	/** 所有字段属性*/
	private List<ArticleAttribute> articleAttributeList;
	/** 所有单元样式*/
	private List<TemplateUnitStyle> templateUnitStyleList;
	/** html内容*/
	private String htmlContent;
	/** html标签 */
	private String htmlCode;
	/** 单元标签*/
	private String unitCode;
	/**  字段标签*/
	private String fieldCode;
	/** 指定栏目 **/
	private String fixedColumn;
	/** 所有栏目单元标签*/
	private Map map = ColumnLinkLabel.map;
	/** 所有公用*/
	private Map commonMap = CommonLabel.allLabels;
	/** 图片预览 */
	private String viewImg;
	
	/**
	 * @return the fixedColumn
	 */
	public String getFixedColumn() {
		return fixedColumn;
	}

	/**
	 * @param fixedColumn the fixedColumn to set
	 */
	public void setFixedColumn(String fixedColumn) {
		this.fixedColumn = fixedColumn;
	}

	/**
	 * @return the unitCode
	 */
	public String getUnitCode() {
		return unitCode;
	}

	/**
	 * @param unitCode the unitCode to set
	 */
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	/**
	 * @return the fieldCode
	 */
	public String getFieldCode() {
		return fieldCode;
	}

	/**
	 * @param fieldCode the fieldCode to set
	 */
	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}

	/**
	 * @return the articleAttributeList
	 */
	public List<ArticleAttribute> getArticleAttributeList() {
		return articleAttributeList;
	}

	/**
	 * @param articleAttributeList the articleAttributeList to set
	 */
	public void setArticleAttributeList(List<ArticleAttribute> articleAttributeList) {
		this.articleAttributeList = articleAttributeList;
	}

	/**
	 * @return the templateUnitStyleList
	 */
	public List<TemplateUnitStyle> getTemplateUnitStyleList() {
		return templateUnitStyleList;
	}

	/**
	 * @param templateUnitStyleList the templateUnitStyleList to set
	 */
	public void setTemplateUnitStyleList(
			List<TemplateUnitStyle> templateUnitStyleList) {
		this.templateUnitStyleList = templateUnitStyleList;
	}

	/**
	 * @return the configPath
	 */
	public String getConfigPath() {
		return configPath;
	}

	/**
	 * @param configPath the configPath to set
	 */
	public void setConfigPath(String configPath) {
		this.configPath = configPath;
	}

	/**
	 * @return the htmlCode
	 */
	public String getHtmlCode() {
		return htmlCode;
	}

	/**
	 * @param htmlCode the htmlCode to set
	 */
	public void setHtmlCode(String htmlCode) {
		this.htmlCode = htmlCode;
	}

	/**
	 * @return the columnStyle
	 */
	public String getColumnStyle() {
		return columnStyle;
	}

	/**
	 * @param columnStyle the columnStyle to set
	 */
	public void setColumnStyle(String columnStyle) {
		this.columnStyle = columnStyle;
	}

	/**
	 * @return the columnType
	 */
	public String getColumnType() {
		return columnType;
	}

	/**
	 * @param columnType the columnType to set
	 */
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	/**
	 * @return the columnRow
	 */
	public String getColumnRow() {
		return columnRow;
	}

	/**
	 * @param columnRow the columnRow to set
	 */
	public void setColumnRow(String columnRow) {
		this.columnRow = columnRow;
	}

	/**
	 * @return the columnCol
	 */
	public String getColumnCol() {
		return columnCol;
	}

	/**
	 * @param columnCol the columnCol to set
	 */
	public void setColumnCol(String columnCol) {
		this.columnCol = columnCol;
	}

	/**
	 * @return the columnPrefix
	 */
	public String getColumnPrefix() {
		return columnPrefix;
	}

	/**
	 * @param columnPrefix the columnPrefix to set
	 */
	public void setColumnPrefix(String columnPrefix) {
		this.columnPrefix = columnPrefix;
	}

	/**
	 * @return the columnPrefixDate
	 */
	public String getColumnPrefixDate() {
		return columnPrefixDate;
	}

	/**
	 * @param columnPrefixDate the columnPrefixDate to set
	 */
	public void setColumnPrefixDate(String columnPrefixDate) {
		this.columnPrefixDate = columnPrefixDate;
	}

	/**
	 * @return the columnPrefixPic
	 */
	public String getColumnPrefixPic() {
		return columnPrefixPic;
	}

	/**
	 * @param columnPrefixPic the columnPrefixPic to set
	 */
	public void setColumnPrefixPic(String columnPrefixPic) {
		this.columnPrefixPic = columnPrefixPic;
	}

	/**
	 * @return the columnSuffix
	 */
	public String getColumnSuffix() {
		return columnSuffix;
	}

	/**
	 * @param columnSuffix the columnSuffix to set
	 */
	public void setColumnSuffix(String columnSuffix) {
		this.columnSuffix = columnSuffix;
	}

	/**
	 * @return the columnSuffixDate
	 */
	public String getColumnSuffixDate() {
		return columnSuffixDate;
	}

	/**
	 * @param columnSuffixDate the columnSuffixDate to set
	 */
	public void setColumnSuffixDate(String columnSuffixDate) {
		this.columnSuffixDate = columnSuffixDate;
	}

	/**
	 * @return the columnSuffixPic
	 */
	public String getColumnSuffixPic() {
		return columnSuffixPic;
	}

	/**
	 * @param columnSuffixPic the columnSuffixPic to set
	 */
	public void setColumnSuffixPic(String columnSuffixPic) {
		this.columnSuffixPic = columnSuffixPic;
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
	 * @return the map
	 */
	public Map getMap() {
		return map;
	}

	/**
	 * @param map the map to set
	 */
	public void setMap(Map map) {
		this.map = map;
	}

	/**
	 * @return the commonMap
	 */
	public Map getCommonMap() {
		return commonMap;
	}

	/**
	 * @param commonMap the commonMap to set
	 */
	public void setCommonMap(Map commonMap) {
		this.commonMap = commonMap;
	}

	/**
	 * @return the viewImg
	 */
	public String getViewImg() {
		return viewImg;
	}

	/**
	 * @param viewImg the viewImg to set
	 */
	public void setViewImg(String viewImg) {
		this.viewImg = viewImg;
	}

}
