/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.unitmanager.web.form;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.baize.ccms.biz.unitmanager.label.CommonLabel;
import com.baize.ccms.biz.unitmanager.label.OnlineSurverySetLabel;
import com.baize.ccms.plugin.onlinesurvey.domain.OnlineSurveyContent;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CCMS</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 包坤涛
 * @version 1.0
 * @since 2009-6-1 下午05:43:33
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class OnlineSurverySetForm extends UnitForm {

	private static final long serialVersionUID = -2936633294108893089L;
	
	/** 调查类别（一般调查、问卷调查） */
	private String category;
	/** 调查主题 */
	private String theme;
	/** 调查问题*/
	private String question;
	/** 问卷列表(列数) */
	private String answerCount;
	/** 反馈框行数大小*/
	private String rowCount;
	/** 反馈框列数大小 */
	private String colCount;
	/** 列表默认条数 */
	private String defaultCount;
	/** 主题列表 */
	private List<OnlineSurveyContent> themeList = new ArrayList<OnlineSurveyContent>();
	/** 问卷列表显示的列数 */
	private String answerRow;
	/** 文本框高度 */
	private String height;
	/** 文本框宽度 */
	private String width;
	/** 主题列表 */
	private String themes;
	/** 所有网上调查单元标签 */
	private Map map = OnlineSurverySetLabel.map;
	/** 所有公用 */
	private Map commonMap = CommonLabel.allLabels;
	/** 效果源码 */
	private String htmlContent;
	/** 嵌入源码 */
	private String script;
	/** 主题Id */
	private String themeId;
	/** 问题id */
	private String questionId;
	/** 应用名 */
	private String appName;
	/** 更多栏目绑定 */
	private String more;
	
	@Override
	protected void validateData(ActionMapping arg0, HttpServletRequest arg1) {
		
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the theme
	 */
	public String getTheme() {
		return theme;
	}

	/**
	 * @param theme the theme to set
	 */
	public void setTheme(String theme) {
		this.theme = theme;
	}

	/**
	 * @return the rowCount
	 */
	public String getRowCount() {
		return rowCount;
	}

	/**
	 * @param rowCount the rowCount to set
	 */
	public void setRowCount(String rowCount) {
		this.rowCount = rowCount;
	}

	/**
	 * @return the colCount
	 */
	public String getColCount() {
		return colCount;
	}

	/**
	 * @param colCount the colCount to set
	 */
	public void setColCount(String colCount) {
		this.colCount = colCount;
	}

	/**
	 * @return the defaultCount
	 */
	public String getDefaultCount() {
		return defaultCount;
	}

	/**
	 * @param defaultCount the defaultCount to set
	 */
	public void setDefaultCount(String defaultCount) {
		this.defaultCount = defaultCount;
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
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * @param question the question to set
	 */
	public void setQuestion(String question) {
		this.question = question;
	}

	/**
	 * @return the answerCount
	 */
	public String getAnswerCount() {
		return answerCount;
	}

	/**
	 * @param answerCount the answerCount to set
	 */
	public void setAnswerCount(String answerCount) {
		this.answerCount = answerCount;
	}

	/**
	 * @return the themeList
	 */
	public List<OnlineSurveyContent> getThemeList() {
		return themeList;
	}

	/**
	 * @param themeList the themeList to set
	 */
	public void setThemeList(List<OnlineSurveyContent> themeList) {
		this.themeList = themeList;
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
	 * @return the answerRow
	 */
	public String getAnswerRow() {
		return answerRow;
	}

	/**
	 * @param answerRow the answerRow to set
	 */
	public void setAnswerRow(String answerRow) {
		this.answerRow = answerRow;
	}

	/**
	 * @return the height
	 */
	public String getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(String height) {
		this.height = height;
	}

	/**
	 * @return the width
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	/**
	 * @return the themes
	 */
	public String getThemes() {
		return themes;
	}

	/**
	 * @param themes the themes to set
	 */
	public void setThemes(String themes) {
		this.themes = themes;
	}

	/**
	 * @return the script
	 */
	public String getScript() {
		return script;
	}

	/**
	 * @param script the script to set
	 */
	public void setScript(String script) {
		this.script = script;
	}

	/**
	 * @return the themeId
	 */
	public String getThemeId() {
		return themeId;
	}

	/**
	 * @param themeId the themeId to set
	 */
	public void setThemeId(String themeId) {
		this.themeId = themeId;
	}

	/**
	 * @return the questionId
	 */
	public String getQuestionId() {
		return questionId;
	}

	/**
	 * @param questionId the questionId to set
	 */
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	/**
	 * @return the appName
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * @param appName the appName to set
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}

	/**
	 * @return the more
	 */
	public String getMore() {
		return more;
	}

	/**
	 * @param more the more to set
	 */
	public void setMore(String more) {
		this.more = more;
	}

}
