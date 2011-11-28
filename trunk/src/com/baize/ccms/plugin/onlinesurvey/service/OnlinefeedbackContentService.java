/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.plugin.onlinesurvey.service;

import com.baize.common.core.dao.Pagination;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类网上调查包含的内容</p>
 * <p>模块: 网上调查信息反馈</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司</p>
 * <p>网址：http://www.baizeweb.com
 * @author 包坤涛
 * @version 1.0
 * @since 2009-10-19 下午02:46:26
 * @history（历次修订内容、修订人、修订时间等） 
 */
public interface OnlinefeedbackContentService {
	
	/**
	 * 网上调查反馈分页
	 * @param pagination 分页对象
	 * @param questionId 问题id
	 * @return Pagination
	 */
	Pagination findFeedbackContentPage(Pagination pagination, String questionId);
	
	/**
	 * 删除信息反馈内容
	 * @param feedbackIds  要删除的反馈ids
	 */
	void deleteFeedBackByFeedbackIds(String feedbackIds);
}