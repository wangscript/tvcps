/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.plugin.onlinesurvey.service.impl;

import com.j2ee.cms.plugin.onlinesurvey.dao.OnlinefeedbackContentDao;
import com.j2ee.cms.plugin.onlinesurvey.service.OnlinefeedbackContentService;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.util.SqlUtil;

/**
 * <p>标题: 网上调查业务类</p>
 * <p>描述: 网上调查的内容,答案，等业务</p>
 * <p>模块: 网上调查</p>
 * <p>版权: Copyright (c) 2009 
 * @author 包坤涛
 * @version 1.0
 * @since 2009-6-14 下午02:30:23 
 * @history（历次修订内容、修订人、修订时间等） 
 */

public class OnlinefeedbackContentServiceImpl implements OnlinefeedbackContentService {

	/** 网上调查问题反馈注入 */
	private	OnlinefeedbackContentDao  onlinefeedbackContentDao;
    
	public Pagination findFeedbackContentPage(Pagination pagination, String questionId) {
		return onlinefeedbackContentDao.getPagination(pagination, "questionId", questionId);
	}

	public void deleteFeedBackByFeedbackIds(String feedbackIds){
		feedbackIds = SqlUtil.toSqlString(feedbackIds);
		onlinefeedbackContentDao.deleteByIds(feedbackIds);
	}
	
	
	public void setOnlinefeedbackContentDao(OnlinefeedbackContentDao onlinefeedbackContentDao) {
		this.onlinefeedbackContentDao = onlinefeedbackContentDao;
	}
}