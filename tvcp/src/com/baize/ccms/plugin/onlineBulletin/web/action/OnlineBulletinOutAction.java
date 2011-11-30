/**
 * project：通用内容管理系统
 * Company:   
 */
package com.j2ee.cms.plugin.onlineBulletin.web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.j2ee.cms.plugin.onlineBulletin.domain.OnlineBulletin;
import com.j2ee.cms.plugin.onlineBulletin.service.OnlineBulletinService;
import com.j2ee.cms.plugin.onlineBulletin.web.form.OnlineBulletinForm;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CPS</p>
 * <p>版权: Copyright (c) 2009   
 * @author 郑荣华
 * @version 1.0
 * @since 2009-12-14 下午05:45:39
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class OnlineBulletinOutAction extends DispatchAction {
	private OnlineBulletinService onlineBulletinService;

	public ActionForward findOnlineBulletin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OnlineBulletinForm onlineBulletinForm = (OnlineBulletinForm)form;
		String bulletinId = request.getParameter("bulletinId");
		OnlineBulletin onlineBulletin = onlineBulletinService.findOnlineBulletin(bulletinId);
		onlineBulletinForm.setWinName(onlineBulletin.getWindowName());
		onlineBulletinForm.setTitle(onlineBulletin.getWindowName());
		onlineBulletinForm.setContent(onlineBulletin.getContext());
		return mapping.findForward("findOnlineSuccess");
	}
	
	/**
	 * @param onlineBulletinService the onlineBulletinService to set
	 */
	public void setOnlineBulletinService(OnlineBulletinService onlineBulletinService) {
		this.onlineBulletinService = onlineBulletinService;
	}
	
}
