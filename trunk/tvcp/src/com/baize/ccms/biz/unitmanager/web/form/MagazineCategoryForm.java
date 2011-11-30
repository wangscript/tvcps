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
import com.j2ee.cms.biz.unitmanager.label.CommonLabel;
import com.j2ee.cms.biz.unitmanager.label.MagazineCategoryLabel;
import com.j2ee.cms.biz.unitmanager.label.TitleLinkLabel;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CPS</p>
 * <p>版权: Copyright (c) 2009  
 * @author 郑荣华
 * @version 1.0
 * @since 2009-9-8 上午10:45:45
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class MagazineCategoryForm extends UnitForm {

	private static final long serialVersionUID = -9189921437615139524L;

	/** 期刊分类样式 */
	private String  magazineCategoryStyle;
	
	/** 内容来源 */
	private String contentSource;
	
	/** 指定栏目 **/
	private String fixedColumn;
	
	/** 信息分类 */
	private String infoCategory;
	
	/** 标题字数 */
	private int titleSize;
	
	/** 标题前缀 */
	private String titlePrefix;
	
	/** 标题后缀 */
	private String titleSuffix;
	
	/** 标题前缀图片 */
	private String titlePrefixPic;
	
	/** 标题后缀图片 */
	private String titleSuffixPic;
	
	/** 所有单元样式*/
	private List<TemplateUnitStyle> templateUnitStyleList;
	
	/** 所有字段属性*/
	private List<ArticleAttribute> articleAttributeList;
	
	/** html内容*/
	private String htmlContent;
	
	/** xml配置文件路径 **/
	private String configPath;
	
	/** html标签 */
	private String htmlCode;
	
	/** 单元标签*/
	private String unitCode;
	
	/**  字段标签*/
	private String fieldCode;
	
	/** 所有公用*/
	private Map commonMap = CommonLabel.allLabels;
	
	/** 所有标题单元标签*/
	private Map titleMap = TitleLinkLabel.map;
	
	/** 期刊分类标签 */
	private Map magazineCategoryMap = MagazineCategoryLabel.map;
	
	/** 栏目id */
	private String columnId;
	
	/** 所有枚举的集合 */
	private Object allData[];
	
	/** 所有已选择的枚举的集合 */
	private Object chooseData[];
	
	/** 文章属性值 */
	private String articleAttributeFieldName;
	
	/** 文章属性名 */
	private String articleAttributeName;
	
	/** 预览样式 */
	private String viewImg;
	
	@Override
	protected void validateData(ActionMapping arg0, HttpServletRequest arg1) {

	}

	/**
	 * @return the magazineCategoryStyle
	 */
	public String getMagazineCategoryStyle() {
		return magazineCategoryStyle;
	}

	/**
	 * @param magazineCategoryStyle the magazineCategoryStyle to set
	 */
	public void setMagazineCategoryStyle(String magazineCategoryStyle) {
		this.magazineCategoryStyle = magazineCategoryStyle;
	}

	/**
	 * @return the contentSource
	 */
	public String getContentSource() {
		return contentSource;
	}

	/**
	 * @param contentSource the contentSource to set
	 */
	public void setContentSource(String contentSource) {
		this.contentSource = contentSource;
	}

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
	 * @return the infoCategory
	 */
	public String getInfoCategory() {
		return infoCategory;
	}

	/**
	 * @param infoCategory the infoCategory to set
	 */
	public void setInfoCategory(String infoCategory) {
		this.infoCategory = infoCategory;
	}

	/**
	 * @return the titleCount
	 */
	public int getTitleSize() {
		return titleSize;
	}

	/**
	 * @param titleCount the titleCount to set
	 */
	public void setTitleSize(int titleSize) {
		this.titleSize = titleSize;
	}

	/**
	 * @return the titlePrefix
	 */
	public String getTitlePrefix() {
		return titlePrefix;
	}

	/**
	 * @param titlePrefix the titlePrefix to set
	 */
	public void setTitlePrefix(String titlePrefix) {
		this.titlePrefix = titlePrefix;
	}

	/**
	 * @return the titleSuffix
	 */
	public String getTitleSuffix() {
		return titleSuffix;
	}

	/**
	 * @param titleSuffix the titleSuffix to set
	 */
	public void setTitleSuffix(String titleSuffix) {
		this.titleSuffix = titleSuffix;
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
	 * @return the titleMap
	 */
	public Map getTitleMap() {
		return titleMap;
	}

	/**
	 * @param titleMap the titleMap to set
	 */
	public void setTitleMap(Map titleMap) {
		this.titleMap = titleMap;
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
	 * @return the magazineCategoryMap
	 */
	public Map getMagazineCategoryMap() {
		return magazineCategoryMap;
	}

	/**
	 * @param magazineCategoryMap the magazineCategoryMap to set
	 */
	public void setMagazineCategoryMap(Map magazineCategoryMap) {
		this.magazineCategoryMap = magazineCategoryMap;
	}

	/**
	 * @return the titlePrefixPic
	 */
	public String getTitlePrefixPic() {
		return titlePrefixPic;
	}

	/**
	 * @param titlePrefixPic the titlePrefixPic to set
	 */
	public void setTitlePrefixPic(String titlePrefixPic) {
		this.titlePrefixPic = titlePrefixPic;
	}

	/**
	 * @return the titleSuffixPic
	 */
	public String getTitleSuffixPic() {
		return titleSuffixPic;
	}

	/**
	 * @param titleSuffixPic the titleSuffixPic to set
	 */
	public void setTitleSuffixPic(String titleSuffixPic) {
		this.titleSuffixPic = titleSuffixPic;
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
	 * @return the allData
	 */
	public Object[] getAllData() {
		return allData;
	}

	/**
	 * @param allData the allData to set
	 */
	public void setAllData(Object[] allData) {
		this.allData = allData;
	}

	/**
	 * @return the chooseData
	 */
	public Object[] getChooseData() {
		return chooseData;
	}

	/**
	 * @param chooseData the chooseData to set
	 */
	public void setChooseData(Object[] chooseData) {
		this.chooseData = chooseData;
	}

	/**
	 * @return the articleAttributeFieldName
	 */
	public String getArticleAttributeFieldName() {
		return articleAttributeFieldName;
	}

	/**
	 * @param articleAttributeFieldName the articleAttributeFieldName to set
	 */
	public void setArticleAttributeFieldName(String articleAttributeFieldName) {
		this.articleAttributeFieldName = articleAttributeFieldName;
	}

	/**
	 * @return the articleAttributeName
	 */
	public String getArticleAttributeName() {
		return articleAttributeName;
	}

	/**
	 * @param articleAttributeName the articleAttributeName to set
	 */
	public void setArticleAttributeName(String articleAttributeName) {
		this.articleAttributeName = articleAttributeName;
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
