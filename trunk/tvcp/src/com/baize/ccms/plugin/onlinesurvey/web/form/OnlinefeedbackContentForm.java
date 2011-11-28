/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.plugin.onlinesurvey.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.baize.ccms.plugin.onlinesurvey.domain.OnlinefeedbackContent;
import com.baize.common.core.web.GeneralForm;

/**
 * <p>标题: 网上调查</p>
 * <p>描述: 网上调查，意见反馈</p>
 * <p>模块: 网上调查</p>
 * <p>版权: Copyright (c) 2009南京百泽网络科技有限公司
 * @author 包坤涛
 * @version 1.0
 * @since 2009-6-14 下午02:35:25 
 * @history（历次修订内容、修订人、修订时间等） 
*/


public class OnlinefeedbackContentForm extends GeneralForm {

	private static final long serialVersionUID = -5632827958636715039L;

	/** 问题id */
	private String questionId;
	/** 信息反馈对象 */
	OnlinefeedbackContent  onlinefeedbackContent = new OnlinefeedbackContent();
	/** 要删除的反馈ids */
	private String feedbackIds;
	
	/**
	 * @return the onlinefeedbackContent
	 */
	public OnlinefeedbackContent getOnlinefeedbackContent() {
		return onlinefeedbackContent;
	}

	/**
	 * @param onlinefeedbackContent the onlinefeedbackContent to set
	 */
	public void setOnlinefeedbackContent(OnlinefeedbackContent onlinefeedbackContent) {
		this.onlinefeedbackContent = onlinefeedbackContent;
	}

	@Override
	protected void validateData(ActionMapping arg0, HttpServletRequest arg1) {
		
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
	 * @return the feedbackIds
	 */
	public String getFeedbackIds() {
		return feedbackIds;
	}

	/**
	 * @param feedbackIds the feedbackIds to set
	 */
	public void setFeedbackIds(String feedbackIds) {
		this.feedbackIds = feedbackIds;
	}
}