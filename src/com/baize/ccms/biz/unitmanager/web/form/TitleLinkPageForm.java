/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.unitmanager.web.form;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.baize.ccms.biz.articlemanager.domain.ArticleAttribute;
import com.baize.ccms.biz.templatemanager.domain.TemplateUnitStyle;
import com.baize.ccms.biz.unitmanager.label.CommonLabel;
import com.baize.ccms.biz.unitmanager.label.TitleLinkPageLabel;

/**
 * 
 * <p>标题: —— 标题链接form</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 用户管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-6-3 上午10:23:23
 * @history（历次修订内容、修订人、修订时间等）
 */
public class TitleLinkPageForm extends UnitForm {

	private static final long serialVersionUID = -9009324407444652540L;

	/** 样式 */
	private String viewStyle;
	/** 内容来源*/
	private String contextFrom;
	/** 指定栏目 */
	private String fixedColumn;
	/** 每页信息数 */
	private String pageInfoCount;
	/** 标题字数   */
	private String titleLimit;
	/** 摘要字数 */
	private String briefLimit;
	/** 标题前缀   */
	private String titleHead;
	/** 是否选择前缀图形 */
	private String titleHeadPic;
	/** 前缀有效期   */
	private String titleHeadValidity;
	/** 标题后缀   */
	private String titleEnd;
	/** 是否选择后缀图形   */
	private String titleEndPic;
	/** 后缀有效期   */
	private String titleEndValidity;
	/** html标签 */
	private String htmlCode;
	/** 单元标签*/
	private String unitCode;
	/**  字段标签*/
	private String fieldCode;
	/** html内容*/
	private String htmlContent;
	/** 图片预览 */
	private String viewImg;
	/** 所有字段属性*/
	private List<ArticleAttribute> articleAttributeList;
	/** 所有单元样式*/
	private List<TemplateUnitStyle> templateUnitStyleList;
	/** 标题链接分页标签 */
	private Map titleLinkPageMap = TitleLinkPageLabel.map;
	/** 所有公用*/
	private Map commonMap = CommonLabel.allLabels;
	/** 文章属性值 */
	private String articleAttributeFieldName;
	/** 文章属性名 */
	private String articleAttributeName;
	/** 分页位置 */
	private String pageSite;
	
	@Override
	protected void validateData(ActionMapping arg0, HttpServletRequest arg1) {
	}

	/**
	 * @return the viewStyle
	 */
	public String getViewStyle() {
		return viewStyle;
	}

	/**
	 * @param viewStyle the viewStyle to set
	 */
	public void setViewStyle(String viewStyle) {
		this.viewStyle = viewStyle;
	}

	/**
	 * @return the contextFrom
	 */
	public String getContextFrom() {
		return contextFrom;
	}

	/**
	 * @param contextFrom the contextFrom to set
	 */
	public void setContextFrom(String contextFrom) {
		this.contextFrom = contextFrom;
	}


	/**
	 * @return the titleLimit
	 */
	public String getTitleLimit() {
		return titleLimit;
	}

	/**
	 * @param titleLimit the titleLimit to set
	 */
	public void setTitleLimit(String titleLimit) {
		this.titleLimit = titleLimit;
	}

	/**
	 * @return the titleHead
	 */
	public String getTitleHead() {
		return titleHead;
	}

	/**
	 * @param titleHead the titleHead to set
	 */
	public void setTitleHead(String titleHead) {
		this.titleHead = titleHead;
	}

	/**
	 * @return the titleHeadValidity
	 */
	public String getTitleHeadValidity() {
		return titleHeadValidity;
	}

	/**
	 * @param titleHeadValidity the titleHeadValidity to set
	 */
	public void setTitleHeadValidity(String titleHeadValidity) {
		this.titleHeadValidity = titleHeadValidity;
	}


	/**
	 * @return the titleEnd
	 */
	public String getTitleEnd() {
		return titleEnd;
	}

	/**
	 * @param titleEnd the titleEnd to set
	 */
	public void setTitleEnd(String titleEnd) {
		this.titleEnd = titleEnd;
	}

	/**
	 * @return the titleEndValidity
	 */
	public String getTitleEndValidity() {
		return titleEndValidity;
	}

	/**
	 * @param titleEndValidity the titleEndValidity to set
	 */
	public void setTitleEndValidity(String titleEndValidity) {
		this.titleEndValidity = titleEndValidity;
	}

	/**
	 * @return the titleHeadPic
	 */
	public String getTitleHeadPic() {
		return titleHeadPic;
	}

	/**
	 * @param titleHeadPic the titleHeadPic to set
	 */
	public void setTitleHeadPic(String titleHeadPic) {
		this.titleHeadPic = titleHeadPic;
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
	 * @return the titleEndPic
	 */
	public String getTitleEndPic() {
		return titleEndPic;
	}

	/**
	 * @param titleEndPic the titleEndPic to set
	 */
	public void setTitleEndPic(String titleEndPic) {
		this.titleEndPic = titleEndPic;
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
	 * @return the pageInfoCount
	 */
	public String getPageInfoCount() {
		return pageInfoCount;
	}

	/**
	 * @param pageInfoCount the pageInfoCount to set
	 */
	public void setPageInfoCount(String pageInfoCount) {
		this.pageInfoCount = pageInfoCount;
	}

	/**
	 * @return the briefLimit
	 */
	public String getBriefLimit() {
		return briefLimit;
	}

	/**
	 * @param briefLimit the briefLimit to set
	 */
	public void setBriefLimit(String briefLimit) {
		this.briefLimit = briefLimit;
	}

	/**
	 * @return the titleLinkPageMap
	 */
	public Map getTitleLinkPageMap() {
		return titleLinkPageMap;
	}

	/**
	 * @param titleLinkPageMap the titleLinkPageMap to set
	 */
	public void setTitleLinkPageMap(Map titleLinkPageMap) {
		this.titleLinkPageMap = titleLinkPageMap;
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
	 * @return the pageSite
	 */
	public String getPageSite() {
		return pageSite;
	}

	/**
	 * @param pageSite the pageSite to set
	 */
	public void setPageSite(String pageSite) {
		this.pageSite = pageSite;
	}
}
