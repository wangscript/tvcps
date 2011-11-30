/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.messagemanager.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.j2ee.cms.biz.messagemanager.service.MessageTipsService;
import com.j2ee.cms.biz.messagemanager.web.form.SiteMessageForm;
import com.j2ee.cms.common.core.dao.Pagination;

/**
 * <p>标题: 消息弹出框Action</p>
 * <p>描述: 管理消息的不同操作，封装请求对象</p>
 * <p>模块: 消息管理</p>
 * <p>版权: Copyright (c) 2009 
 * @author 杨信
 * @version 1.0
 * @since 2009-6-30 下午02:32:16 
 * @history（历次修订内容、修订人、修订时间等） 
*/

public class MessageTipsAction extends DispatchAction {
	
	private MessageTipsService messageTipsService;
	
	/**
	 * 显示数据列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showMessageTips(ActionMapping mapping, 
            ActionForm actionForm, 
            HttpServletRequest request, 
            HttpServletResponse response) 
			throws Exception { 
		//获取当前用户id
		
		HttpSession session = request.getSession(false);
		String id = (String) session.getAttribute("sessionid");
		SiteMessageForm form = (SiteMessageForm) actionForm;
		Pagination pagination = form.getPagination();
		
		int messageNum = messageTipsService.findMessages(pagination, id);
		form.setMessageNum(messageNum);
		return mapping.findForward("success");   
	}

	/**
	 * @param messageTipsService the messageTipsService to set
	 */
	public void setMessageTipsService(MessageTipsService messageTipsService) {
		this.messageTipsService = messageTipsService;
	}

}
