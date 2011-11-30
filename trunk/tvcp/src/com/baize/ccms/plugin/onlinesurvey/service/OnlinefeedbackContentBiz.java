/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.plugin.onlinesurvey.service;

import java.util.Date;
import java.util.Map;

import com.j2ee.cms.biz.sitemanager.domain.Site;
import com.j2ee.cms.biz.usermanager.domain.User;
import com.j2ee.cms.plugin.onlinesurvey.domain.OnlineSurvey;
import com.j2ee.cms.plugin.onlinesurvey.domain.OnlinefeedbackContent;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.service.BaseService;
import com.j2ee.cms.common.core.web.event.RequestEvent;
import com.j2ee.cms.common.core.web.event.ResponseEvent;

/**
 * <p>标题: 网上调业务处理类</p>
 * <p>描述: 网上调的查看,删除等功能</p>
 * <p>模块:网上调</p>
 * <p>版权: Copyright (c) 2009 
 * @author 包坤涛
 * @version 1.0
 * @since 2009-6-14 下午02:26:33 
 * @history（历次修订内容、修订人、修订时间等） 
*/

public class OnlinefeedbackContentBiz extends BaseService {

	/**注入消息业务对象**/
	OnlinefeedbackContentService onlinefeedbackContentService;
	
	public void performTask(RequestEvent requestEvent, ResponseEvent responseEvent)
			throws Exception {
		String dealMethod = requestEvent.getDealMethod();
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
	    Map<Object,Object> responseParam = responseEvent.getRespMapParam();
	    String questionId = (String) requestParam.get("questionId");
	    
	    //网上调查问题反馈详情 
	    if(dealMethod.equals("")){
	    	log.debug("开始查询反馈分页数据");
	    	Pagination pagination = (Pagination) requestParam.get("pagination");
	    	pagination = onlinefeedbackContentService.findFeedbackContentPage(pagination, questionId);
	    	responseParam.put("pagination", pagination);
	    	log.info("查询调查反馈成功");
	    	
    	//删除信息反馈
		}else if(dealMethod.equals("delete")){
			log.info("删除调查反馈信息");
			String feedbackIds = (String) requestParam.get("feedbackIds");
			onlinefeedbackContentService.deleteFeedBackByFeedbackIds(feedbackIds);
			log.info("删除调查反馈信息成功");
	    }
	    responseParam.put("questionId", questionId);
	}

	@Override
	public ResponseEvent validateData(RequestEvent arg0) throws Exception {
		return null;
	}

	public void setOnlinefeedbackContentService(OnlinefeedbackContentService onlinefeedbackContentService) {
		this.onlinefeedbackContentService = onlinefeedbackContentService;
	}
}
