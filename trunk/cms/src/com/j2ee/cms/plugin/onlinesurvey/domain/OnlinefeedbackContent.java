/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.plugin.onlinesurvey.domain;

import java.io.Serializable;

/**
 * <p>标题: 网上调查</p>
 * <p>描述: 网上调查反馈内容</p>
 * <p>模块: 网上调查</p>
 * <p>版权: Copyright (c) 2009  </p>
 * @author <a href="mailto:xinyang921@gmail.com">包坤涛</a>
 * @version 1.0
 * @since 2009-6-13 下午04:24:10
 * @history（历次修订内容、修订人、修订时间等）
 */
public class OnlinefeedbackContent implements Serializable {
	
	private static final long serialVersionUID = 224578496018130950L;
	/**主建*/ 
	private String id;
	/** 反馈内容 */
	private String feedbackContent;
	/**网上调查问题具体内容表*/
	private OnlineSurveyContent onlineSurveyContent;
	    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFeedbackContent() {
		return feedbackContent;
	}
	public void setFeedbackContent(String feedbackContent) {
		this.feedbackContent = feedbackContent;
	}
	public OnlineSurveyContent getOnlineSurveyContent() {
		return onlineSurveyContent;
	}
	public void setOnlineSurveyContent(OnlineSurveyContent onlineSurveyContent) {
		this.onlineSurveyContent = onlineSurveyContent;
	}
}