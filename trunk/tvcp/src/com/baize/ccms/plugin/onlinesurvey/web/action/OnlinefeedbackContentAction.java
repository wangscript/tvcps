/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.plugin.onlinesurvey.web.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.j2ee.cms.plugin.onlinesurvey.domain.OnlinefeedbackContent;
import com.j2ee.cms.plugin.onlinesurvey.web.form.OnlineSurveyForm;
import com.j2ee.cms.plugin.onlinesurvey.web.form.OnlinefeedbackContentForm;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.web.GeneralAction;
import com.j2ee.cms.common.core.web.event.RequestEvent;
import com.j2ee.cms.common.core.web.event.ResponseEvent;


/**
 * <p>标题: 信件Action</p>
 * <p>描述: 网上调查操作，封装请求对象</p>
 * <p>模块: 网上调查</p>
 * <p>版权: Copyright (c) 2009 
 * @author 包坤涛
 * @version 1.0
 * @since 2009-6-14 下午02:32:22 
 * @history（历次修订内容、修订人、修订时间等） 
*/

public class OnlinefeedbackContentAction extends GeneralAction {

	private String dealMethod = "";
	
	@SuppressWarnings("unchecked")
	@Override
	protected void doFormFillment(ActionForm actionForm, ResponseEvent responseEvent, String userIndr) 
			throws Exception {
		OnlinefeedbackContentForm form = (OnlinefeedbackContentForm) actionForm;
		Map<Object, Object> responseParam = responseEvent.getRespMapParam();
		String questionId = (String) responseParam.get("questionId");
		form.setQuestionId(questionId);
		
		//网上调查问题查询   
		if (dealMethod.equals("")) {
			form.setPagination((Pagination) responseParam.get("pagination"));
			this.setRedirectPage("list", userIndr);
		
		//删除信息反馈
		}else if(dealMethod.equals("delete")){
			form.setInfoMessage("删除信息反馈成功");
			this.setRedirectPage("list", userIndr);
		} 
	}
	
	@Override
	protected void performTask(ActionForm actionForm, RequestEvent requestEvent, String userIndr )
			throws Exception {
		Map<Object, Object> requestParam = requestEvent.getReqMapParam();
		OnlinefeedbackContentForm  form =(OnlinefeedbackContentForm)actionForm;
	    this.dealMethod = form.getDealMethod();
		String questionId = form.getQuestionId();
		
		//网上调查问题反馈详情
		if(dealMethod.equals("")){
			form.setQueryKey("findFeedbackContentPage");
			requestParam.put("pagination", form.getPagination());
		
		//删除信息反馈
		}else if(dealMethod.equals("delete")){
			String feedbackIds = form.getFeedbackIds();
			requestParam.put("feedbackIds", feedbackIds);
		}
		requestParam.put("questionId", questionId);
	}

	@Override
	protected void init(String arg0) throws Exception {

	}
}
