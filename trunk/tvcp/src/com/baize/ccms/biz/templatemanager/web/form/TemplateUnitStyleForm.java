/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.templatemanager.web.form;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.j2ee.cms.biz.templatemanager.domain.TemplateUnitStyle;
import com.j2ee.cms.biz.unitmanager.label.ArticleTextLabel;
import com.j2ee.cms.biz.unitmanager.label.ColumnLinkLabel;
import com.j2ee.cms.biz.unitmanager.label.CommonLabel;
import com.j2ee.cms.biz.unitmanager.label.CurrentLocationLabel;
import com.j2ee.cms.biz.unitmanager.label.LatestInfoLabel;
import com.j2ee.cms.biz.unitmanager.label.PictureNewsLabel;
import com.j2ee.cms.biz.unitmanager.label.TitleLinkLabel;
import com.j2ee.cms.common.core.web.GeneralForm;

/**
 * 
 * <p>标题: —— 模板单元表单</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 用户管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-6-6 下午05:59:39
 * @history（历次修订内容、修订人、修订时间等）
 */
public class TemplateUnitStyleForm extends GeneralForm {

	private static final long serialVersionUID = -5846350120692650626L;

	/** 模板单元样式*/
	private TemplateUnitStyle  templateUnitStyle;
	/** 类别ID */
	private String  categoryId;
	/** 模板类别名称 */
	private String categoryName;
	/** 样式ID */
	private String styleId;
	/** 生成的html代码 */
	private String htmlContent;
	
	/** 栏目链接单元标签 */
	private Map columnLinkMap = ColumnLinkLabel.map;
	/** 标题链接单元标签 */
	private Map titleLinkMap = TitleLinkLabel.map;
	/** 当前位置单元标签 */
	private Map currentLocationMap = CurrentLocationLabel.map;
	/** 最新信息单元标签*/
	private Map latestInfoMap = LatestInfoLabel.map;
	/** 图片新闻单元标签 */
	private Map pictureNewsMap = PictureNewsLabel.map;
	/** 文章正文单元标签 */
	private Map articleTextMap = ArticleTextLabel.map;
	/** 所有公用单元标签 */
	private Map commonMap = CommonLabel.allLabels;
	
	/** 所有样式名称**/
	private String styleNameStr;
	
	/** 样式预览地址 */
	private String stylePreview;
	
	/** 样式具体内容 */
	private String styleContent;
	
	@Override 
	protected void validateData(ActionMapping arg0, HttpServletRequest arg1) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * @return the templateUnitStyle
	 */
	public TemplateUnitStyle getTemplateUnitStyle() {
		return templateUnitStyle;
	}
	/**
	 * @param templateUnitStyle the templateUnitStyle to set
	 */
	public void setTemplateUnitStyle(TemplateUnitStyle templateUnitStyle) {
		this.templateUnitStyle = templateUnitStyle;
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
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}
	/**
	 * @param categoryName the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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
	 * @return the columnLinkMap
	 */
	public Map getColumnLinkMap() {
		return columnLinkMap;
	}
	/**
	 * @param columnLinkMap the columnLinkMap to set
	 */
	public void setColumnLinkMap(Map columnLinkMap) {
		this.columnLinkMap = columnLinkMap;
	}
	/**
	 * @return the titleLinkMap
	 */
	public Map getTitleLinkMap() {
		return titleLinkMap;
	}
	/**
	 * @param titleLinkMap the titleLinkMap to set
	 */
	public void setTitleLinkMap(Map titleLinkMap) {
		this.titleLinkMap = titleLinkMap;
	}
	/**
	 * @return the currentLocationMap
	 */
	public Map getCurrentLocationMap() {
		return currentLocationMap;
	}
	/**
	 * @param currentLocationMap the currentLocationMap to set
	 */
	public void setCurrentLocationMap(Map currentLocationMap) {
		this.currentLocationMap = currentLocationMap;
	}
	/**
	 * @return the latestInfoMap
	 */
	public Map getLatestInfoMap() {
		return latestInfoMap;
	}
	/**
	 * @param latestInfoMap the latestInfoMap to set
	 */
	public void setLatestInfoMap(Map latestInfoMap) {
		this.latestInfoMap = latestInfoMap;
	}
	/**
	 * @return the pictureNewsMap
	 */
	public Map getPictureNewsMap() {
		return pictureNewsMap;
	}
	/**
	 * @param pictureNewsMap the pictureNewsMap to set
	 */
	public void setPictureNewsMap(Map pictureNewsMap) {
		this.pictureNewsMap = pictureNewsMap;
	}
	/**
	 * @return the articleTextMap
	 */
	public Map getArticleTextMap() {
		return articleTextMap;
	}
	/**
	 * @param articleTextMap the articleTextMap to set
	 */
	public void setArticleTextMap(Map articleTextMap) {
		this.articleTextMap = articleTextMap;
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
	 * @param styleNameStr the styleNameStr to set
	 */
	public void setStyleNameStr(String styleNameStr) {
		this.styleNameStr = styleNameStr;
	}
	/**
	 * @return the styleNameStr
	 */
	public String getStyleNameStr() {
		return styleNameStr;
	}
	/**
	 * @return the stylePreview
	 */
	public String getStylePreview() {
		return stylePreview;
	}
	/**
	 * @param stylePreview the stylePreview to set
	 */
	public void setStylePreview(String stylePreview) {
		this.stylePreview = stylePreview;
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

}
