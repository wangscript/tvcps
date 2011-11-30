/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.plugin.rss.web.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.j2ee.cms.plugin.rss.web.form.RssForm;
import com.j2ee.cms.common.core.web.GeneralAction;
import com.j2ee.cms.common.core.web.event.RequestEvent;
import com.j2ee.cms.common.core.web.event.ResponseEvent;

/**
 * <p>
 * 标题: —— 后台RSS action
 * </p>
 * <p>
 * 描述: —— RSS订阅
 * </p>
 * <p>
 * 模块: CPS插件
 * </p>
 * <p>
 * 版权: Copyright (c) 2009  
 * </p>
 * <p>
 * 网址：http://www.j2ee.cmsweb.com
 * 
 * @author 曹名科
 * @version 1.0
 * @since 2009-10-16 下午03:30:16
 * @history（历次修订内容、修订人、修订时间等） 
 */
 

public class RssInnerAction extends GeneralAction {

	private String dealMethod = "";

	@Override
	protected void doFormFillment(ActionForm actionForm,
			ResponseEvent responseEvent, String userIndr) throws Exception {
		Map<Object, Object> responseParam = responseEvent.getRespMapParam();
		
		HttpServletRequest request = (HttpServletRequest) responseParam.get("HttpServletRequest");
		@SuppressWarnings("unused")
		RssForm form = (RssForm) actionForm;
		if (dealMethod.equals("")) {
			form = (RssForm) responseParam.get("rssForm");
			this.setRedirectPage("saveSuccess", userIndr);
		} else if (dealMethod.equals("save")) {
			form = (RssForm) actionForm;
			
			form.setList((List)responseParam.get("list"));
			this.setRedirectPage("saveSuccess", userIndr);
		
		// 发布rss目录
		} else if(dealMethod.equals("publishRss")){
			form = (RssForm) responseParam.get("rssForm");
			form.setInfoMessage("发布Rss目录成功");
			this.setRedirectPage("saveSuccess", userIndr);
		}

	}

	@Override
	protected void init(String arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void performTask(ActionForm actionForm,
			RequestEvent requestEvent, String userIndr) throws Exception {
		Map<Object, Object> requestParam = requestEvent.getReqMapParam();
		HttpServletRequest request = (HttpServletRequest) requestParam
				.get("HttpServletRequest");
		RssForm form = (RssForm) actionForm;
		this.dealMethod = form.getDealMethod();
		requestParam.put("ressForm", form);
		if (dealMethod.equals("")) {
			
		} else if (dealMethod.equals("save")) {
			requestParam.put("appName", request.getContextPath());
		
		// 发布rss目录
		} else if(dealMethod.equals("publishRss")){
			
		}
	}

}
