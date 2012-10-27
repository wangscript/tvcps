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
import com.j2ee.cms.biz.unitmanager.label.ArticleTextLabel;
import com.j2ee.cms.biz.unitmanager.label.CommonLabel;
import com.j2ee.cms.biz.unitmanager.label.TitleLinkLabel;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CPS</p>
 * <p>版权: Copyright (c) 2009  
 * @author 郑荣华
 * @version 1.0
 * @since 2009-7-23 上午11:03:09
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class ArticleTextForm extends UnitForm {

	private static final long serialVersionUID = -7490517072590538508L;

	/** 文章正文显示样式 **/
	private String articleTextStyle;
	/**  文章评论 **/
	private String articleTextComment;
	/** 文章评论是否图形 **/
	private String articleTextCommentPic;
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
	/** 每页显示字数 */
	private int pageSize;
	/** 所有文章正文单元标签*/
	private Map map = ArticleTextLabel.map;
	/** 所有标题链接单元标签**/
	private Map titleMap = TitleLinkLabel.map;
	/** 所有公用*/
	private Map commonMap = CommonLabel.allLabels;
	
	@Override
	protected void validateData(ActionMapping arg0, HttpServletRequest arg1) {

	}

	/**
	 * @return the articleTextStyle
	 */
	public String getArticleTextStyle() {
		return articleTextStyle;
	}

	/**
	 * @param articleTextStyle the articleTextStyle to set
	 */
	public void setArticleTextStyle(String articleTextStyle) {
		this.articleTextStyle = articleTextStyle;
	}

	/**
	 * @return the articleTextComment
	 */
	public String getArticleTextComment() {
		return articleTextComment;
	}

	/**
	 * @param articleTextComment the articleTextComment to set
	 */
	public void setArticleTextComment(String articleTextComment) {
		this.articleTextComment = articleTextComment;
	}

	/**
	 * @return the articleTextCommentPic
	 */
	public String getArticleTextCommentPic() {
		return articleTextCommentPic;
	}

	/**
	 * @param articleTextCommentPic the articleTextCommentPic to set
	 */
	public void setArticleTextCommentPic(String articleTextCommentPic) {
		this.articleTextCommentPic = articleTextCommentPic;
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
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
