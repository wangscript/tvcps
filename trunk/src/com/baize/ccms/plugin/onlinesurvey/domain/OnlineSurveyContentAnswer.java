/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.plugin.onlinesurvey.domain;

import java.io.Serializable;
import java.util.Date;

import com.baize.ccms.biz.sitemanager.domain.Site;
import com.baize.ccms.biz.usermanager.domain.User;

/**
 * <p>
 * 标题: 信件
 * </p>
 * <p>
 * 描述: 网上调查包含的内容
 * </p>
 * <p>
 * 模块: 网上调查
 * </p>
 * <p>
 * 版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * </p>
 * 
 * @author <a href="mailto:xinyang921@gmail.com">包坤涛</a>
 * @version 1.0
 * @since 2009-6-13 下午04:24:10
 * @history（历次修订内容、修订人、修订时间等）
 */
 

public class OnlineSurveyContentAnswer implements Serializable {

	private static final long serialVersionUID = 225576496910130950L;
	
	/** 主建 */
	private String id;
	/** 调查问题答案 */
	private String answer;
	/** 调查投票数 */
	private String voteCount;
	/** 网上调查问题具体内容Id */
	private OnlineSurveyContent onlineSurveyContent;

	public String getId() {
		return id;
	}
	public String getAnswer() {
		return answer;
	}
	public String getVoteCount() {
		return voteCount;
	}
	public OnlineSurveyContent getOnlineSurveyContent() {
		return onlineSurveyContent;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public void setVoteCount(String voteCount) {
		this.voteCount = voteCount;
	}
	public void setOnlineSurveyContent(OnlineSurveyContent onlineSurveyContent) {
		this.onlineSurveyContent = onlineSurveyContent;
	}
}