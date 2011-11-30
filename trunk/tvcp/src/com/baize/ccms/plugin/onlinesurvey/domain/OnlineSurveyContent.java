/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.plugin.onlinesurvey.domain;

import java.io.Serializable;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述网上调查实体类</p>
 * <p>模块: CPS</p>
 * <p>版权: Copyright (c) 2009  </p>
 * <p>网址：http://www.j2ee.cmsweb.com
 * @author 包坤涛
 * @version 1.0
 * @since 2009-10-21 下午06:38:29
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class OnlineSurveyContent  implements Serializable {

	private static final long serialVersionUID = -2289500659838588870L;
	
	/**主键*/
	private String id;
	/**具体调查问题名称*/
	private String name;
	 /**是否显示*/
	private boolean viewed;
	/**是否带反馈意见*/
	private boolean feedback;
	/**显示样式*/
	private String style;
	/**是否必填*/
	private boolean required;
	/**答案显示列数*/
	private String colCount;
	/**大问题的ID*/
	private  OnlineSurvey onlineSurvey;
	/**反馈意见内容*/
    private String feedbackContent;
    /**排序用*/
	private String orders;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isViewed() {
		return viewed;
	}
	public void setViewed(boolean view) {
		this.viewed = view;
	}
	public boolean isFeedback() {
		return feedback;
	}
	public void setFeedback(boolean feedback) {
		this.feedback = feedback;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	public String getColCount() {
		return colCount;
	}
	public void setColCount(String colCount) {
		this.colCount = colCount;
	}
	public String getFeedbackContent() {
		return feedbackContent;
	}
	public void setFeedbackContent(String feedbackContent) {
		this.feedbackContent = feedbackContent;
	}
	public String getOrders() {
		return orders;
	}
	public void setOrders(String orders) {
		this.orders = orders;
	}
	public OnlineSurvey getOnlineSurvey() {
		return onlineSurvey;
	}
	public void setOnlineSurvey(OnlineSurvey onlineSurvey) {
		this.onlineSurvey = onlineSurvey;
	}
}