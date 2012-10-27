/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.plugin.articlecomment.web.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.j2ee.cms.plugin.articlecomment.domain.ArticleComment;
import com.j2ee.cms.plugin.articlecomment.domain.ArticleCommentsReplace;
import com.j2ee.cms.common.core.web.GeneralForm;

/**
 * <p>
 * 标题: —— 要求能简洁地表达出类的功能和职责
 * </p>
 * <p>
 * 描述: —— 简要描述类的职责、实现方式、使用注意事项等
 * </p>
 * <p>
 * 模块: CPS
 * </p>
 * <p>
 * 版权: Copyright (c) 2009  
 * </p>
 * <p>
 * 网址：http://www.j2ee.cmsweb.com
 * 
 * @author 曹名科
 * @version 1.0
 * @since 2009-10-20 下午03:32:42
 * @history（历次修订内容、修订人、修订时间等） 
 */
 
public class ArticleCommentForm extends GeneralForm {
	private static final long serialVersionUID = 7740120586663332383L;
	/** 开放类型 0为关闭，1为开放. */
	private String openType;
	/** 是否审核 1为是，0为否. */
	private String isLook;
	/** ip显示样式 0为显示，1为不显示，2隐藏末尾. */
	private String ipStyle;
	/** 当有过滤词时 0为回收站，1为替换发布，2为禁止发布. */
	private String haveReplace;
	/** 过滤词取词范围 0为系统内，1为评论内. */
	private String replaceArea;
	/** 每页显示评论数 . */
	private String rowCommentCount;
	/** 文章最大评论数 . */
	private String articleCommentCount;
	/** 自动荐为精华 . */
	private String creamCount;
	/** 评论过期 . */
	private String timeOutSet;
	/**样式ID*/
	private String styleId;
	/**样式名字*/
	private String styleName;
	/**样式内容*/
	private String styleContent;
	/**在文章评论时标记,1表示TRUE,0*/
	private String objContent;
	/**删除标记*/
	private String delTag="";
	/**替换对象*/
	private ArticleCommentsReplace replace = new ArticleCommentsReplace();
	/**文章评论对象*/
	private ArticleComment comment = new ArticleComment();
	/**
	 * @return the comment
	 */
	public ArticleComment getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(ArticleComment comment) {
		this.comment = comment;
	}



	/**
	 * @return the openType
	 */
	public String getOpenType() {
		return openType;
	}

	/**
	 * @param openType the openType to set
	 */
	public void setOpenType(String openType) {
		this.openType = openType;
	}

	/**
	 * @return the isLook
	 */
	public String getIsLook() {
		return isLook;
	}

	/**
	 * @param isLook the isLook to set
	 */
	public void setIsLook(String isLook) {
		this.isLook = isLook;
	}

	/**
	 * @return the ipStyle
	 */
	public String getIpStyle() {
		return ipStyle;
	}

	/**
	 * @param ipStyle the ipStyle to set
	 */
	public void setIpStyle(String ipStyle) {
		this.ipStyle = ipStyle;
	}

	/**
	 * @return the haveReplace
	 */
	public String getHaveReplace() {
		return haveReplace;
	}

	/**
	 * @param haveReplace the haveReplace to set
	 */
	public void setHaveReplace(String haveReplace) {
		this.haveReplace = haveReplace;
	}

	/**
	 * @return the replaceArea
	 */
	public String getReplaceArea() {
		return replaceArea;
	}

	/**
	 * @param replaceArea the replaceArea to set
	 */
	public void setReplaceArea(String replaceArea) {
		this.replaceArea = replaceArea;
	}

	/**
	 * @return the rowCommentCount
	 */
	public String getRowCommentCount() {
		return rowCommentCount;
	}

	/**
	 * @param rowCommentCount the rowCommentCount to set
	 */
	public void setRowCommentCount(String rowCommentCount) {
		this.rowCommentCount = rowCommentCount;
	}

	/**
	 * @return the articleCommentCount
	 */
	public String getArticleCommentCount() {
		return articleCommentCount;
	}

	/**
	 * @param articleCommentCount the articleCommentCount to set
	 */
	public void setArticleCommentCount(String articleCommentCount) {
		this.articleCommentCount = articleCommentCount;
	}

	/**
	 * @return the creamCount
	 */
	public String getCreamCount() {
		return creamCount;
	}

	/**
	 * @param creamCount the creamCount to set
	 */
	public void setCreamCount(String creamCount) {
		this.creamCount = creamCount;
	}

	/**
	 * @return the timeOutSet
	 */
	public String getTimeOutSet() {
		return timeOutSet;
	}

	/**
	 * @param timeOutSet the timeOutSet to set
	 */
	public void setTimeOutSet(String timeOutSet) {
		this.timeOutSet = timeOutSet;
	}

	@Override
	protected void validateData(ActionMapping arg0, HttpServletRequest arg1) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param replace the replace to set
	 */
	public void setReplace(ArticleCommentsReplace replace) {
		this.replace = replace;
	}

	/**
	 * @return the replace
	 */
	public ArticleCommentsReplace getReplace() {
		return replace;
	}

	/**
	 * @return the styleId
	 */
	public String getStyleId() {
		return styleId;
	}

	/**
	 * @param styleId the styleId to set
	 */
	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}

	/**
	 * @return the styleName
	 */
	public String getStyleName() {
		return styleName;
	}

	/**
	 * @param styleName the styleName to set
	 */
	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}

	/**
	 * @return the styleContent
	 */
	public String getStyleContent() {
		return styleContent;
	}

	/**
	 * @param styleContent the styleContent to set
	 */
	public void setStyleContent(String styleContent) {
		this.styleContent = styleContent;
	}


	/**
	 * @param objContent the objContent to set
	 */
	public void setObjContent(String objContent) {
		this.objContent = objContent;
	}

	/**
	 * @return the objContent
	 */
	public String getObjContent() {
		return objContent;
	}

	/**
	 * @param delTag the delTag to set
	 */
	public void setDelTag(String delTag) {
		this.delTag = delTag;
	}

	/**
	 * @return the delTag
	 */
	public String getDelTag() {
		return delTag;
	}

}
