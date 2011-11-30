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
import com.j2ee.cms.biz.unitmanager.label.TitleLinkLabel;

/**
 * 
 * <p>标题: —— 标题链接form</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 用户管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-6-3 上午10:23:23
 * @history（历次修订内容、修订人、修订时间等）
 */
public class TitleLinkForm extends UnitForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9009324407444652540L;
	/** 显示样式 
	 *  1-标题控制字数并含&quot;更多&quot; 
	 *  2-标题控制字数不含&quot;更多&quot;
	 *	3-标题控制字数带&quot;右对齐日期&quot;不含&quot;更多&quot;
	 *	4-标题控制字数带&quot;跟标题日期&quot;含&quot;更多&quot;
	 *	5-标题不控制字数并含&quot;更多&quot; 
	 * */
	private String viewStyle;
	/** 内容来源
	 * 1-当前栏目
	 * 2-当前栏目的父栏目 	
	 * 3-指定栏目
	 * 4-指定栏目的父栏目
	 * */
	private String contextFrom;
	/** 指定栏目的父栏目使用   */
	private String columnName;
	
	/** 指定栏目(多信息栏目)   */
	private String moreColumnName;
	/** 信息起始   */
	private String start;
	/** 信息显示几列   */
	private String col;
	/** 信息显示几行   */
	private String row;
	/** 标题字数   */
	private String titleLimit;
	/** 更多   */
	private String moreLink;
	/** 是否选择更多图形   */
	private String moreLinkPic;
	/** 标题前缀   */
	private String titleHead;
	/** 是否选择前缀图形 */
	private String titleHeadPic;
	/** 前缀有效期   */
	private String titleHeadValidity;
	/** 标题后缀   */
	private String titleEnd;
	/** 后缀有效期   */
	private String titleEndValidity;
	/** 是否选择后缀图形   */
	private String titleEndPic;
	/** 底线样式 dotted：………；dashed：-----；solid：───────  */
	private String lineStyle;
	/** 底线隔行   */
	private String lineGroup;
	/** 奇行背景色   */
	private String oddColor;
	/** 偶行背景色   */
	private String evenColor;
	/** 指定行加粗   */
	private String strongTitle;
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
	/** 所有标题单元标签*/
	private Map map = TitleLinkLabel.map;
	/** 所有公用*/
	private Map commonMap = CommonLabel.allLabels;
    /** 文章属性值 */
	private String articleAttributeFieldName;
	/** 文章属性名 */
	private String articleAttributeName;
	
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
	 * @return the columnName
	 */
	public String getColumnName() {
		return columnName;
	}

	/**
	 * @param columnName the columnName to set
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	/**
	 * @return the start
	 */
	public String getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(String start) {
		this.start = start;
	}

	/**
	 * @return the col
	 */
	public String getCol() {
		return col;
	}

	/**
	 * @param col the col to set
	 */
	public void setCol(String col) {
		this.col = col;
	}

	/**
	 * @return the row
	 */
	public String getRow() {
		return row;
	}

	/**
	 * @param row the row to set
	 */
	public void setRow(String row) {
		this.row = row;
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
	 * @return the moreLink
	 */
	public String getMoreLink() {
		return moreLink;
	}

	/**
	 * @param moreLink the moreLink to set
	 */
	public void setMoreLink(String moreLink) {
		this.moreLink = moreLink;
	}

	/**
	 * @return the moreLinkPic
	 */
	public String getMoreLinkPic() {
		return moreLinkPic;
	}

	/**
	 * @param moreLinkPic the moreLinkPic to set
	 */
	public void setMoreLinkPic(String moreLinkPic) {
		this.moreLinkPic = moreLinkPic;
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
	 * @return the lineStyle
	 */
	public String getLineStyle() {
		return lineStyle;
	}

	/**
	 * @param lineStyle the lineStyle to set
	 */
	public void setLineStyle(String lineStyle) {
		this.lineStyle = lineStyle;
	}

	/**
	 * @return the lineGroup
	 */
	public String getLineGroup() {
		return lineGroup;
	}

	/**
	 * @param lineGroup the lineGroup to set
	 */
	public void setLineGroup(String lineGroup) {
		this.lineGroup = lineGroup;
	}

	/**
	 * @return the oddColor
	 */
	public String getOddColor() {
		return oddColor;
	}

	/**
	 * @param oddColor the oddColor to set
	 */
	public void setOddColor(String oddColor) {
		this.oddColor = oddColor;
	}

	/**
	 * @return the evenColor
	 */
	public String getEvenColor() {
		return evenColor;
	}

	/**
	 * @param evenColor the evenColor to set
	 */
	public void setEvenColor(String evenColor) {
		this.evenColor = evenColor;
	}

	/**
	 * @return the strongTitle
	 */
	public String getStrongTitle() {
		return strongTitle;
	}

	/**
	 * @param strongTitle the strongTitle to set
	 */
	public void setStrongTitle(String strongTitle) {
		this.strongTitle = strongTitle;
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
	 * @return the moreColumnName
	 */
	public String getMoreColumnName() {
		return moreColumnName;
	}

	/**
	 * @param moreColumnName the moreColumnName to set
	 */
	public void setMoreColumnName(String moreColumnName) {
		this.moreColumnName = moreColumnName;
	}


	 
}
