/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.configmanager.web.action;

import java.util.List;
import java.util.Map;

import org.apache.struts.action.ActionForm;

import com.baize.ccms.biz.configmanager.domain.ModuleCategory;
import com.baize.ccms.biz.configmanager.web.form.ModuleCategoryForm;
import com.baize.common.core.web.GeneralAction;
import com.baize.common.core.web.event.RequestEvent;
import com.baize.common.core.web.event.ResponseEvent;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CCMS</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 郑荣华
 * @version 1.0
 * @since 2009-9-5 上午09:58:58
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class ModuleCategoryAction extends GeneralAction {

	private String dealMethod = "";
	
	@Override
	protected void doFormFillment(ActionForm actionForm, ResponseEvent responseEvent, String userIndr) throws Exception {
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		ModuleCategoryForm form = (ModuleCategoryForm) actionForm;
		
		// 查询模块类别
		if(dealMethod.equals("")) {
			List<ModuleCategory> selectedList = (List<ModuleCategory>) responseParam.get("selectedList");
			List<ModuleCategory> notSelectedList = (List<ModuleCategory>) responseParam.get("notSelectedList");
			form.setSelectedList(selectedList);
			form.setNotSelectedList(notSelectedList);
			
		// 取消要记录日志的模块类别	
		} else if(dealMethod.equals("modifyModule")) {
			form.setInfoMessage("设置已修改");
			this.setRedirectPage("modifySetSuccess", userIndr);
		}
	}

	@Override
	protected void performTask(ActionForm actionForm, RequestEvent requestEvent, String userIndr) throws Exception {
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		ModuleCategoryForm form = (ModuleCategoryForm) actionForm;
		this.dealMethod = form.getDealMethod();
		
		// 查询模块类别
		if(dealMethod.equals("")) {
			
		// 取消要记录日志的模块类别	
		} else if(dealMethod.equals("modifyModule")) {
			String selectIds = form.getSelectedIds();
			String notSelectIds = form.notSelectIds;
			requestParam.put("selectIds", selectIds);
			requestParam.put("notSelectIds", notSelectIds);
		}
	}

	@Override
	protected void init(String userIndr) throws Exception {
		this.setRedirectPage("success", userIndr);
	}
}
