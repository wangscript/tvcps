/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.configmanager.web.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.j2ee.cms.biz.configmanager.web.form.SystemLogForm;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.web.GeneralAction;
import com.j2ee.cms.common.core.web.event.RequestEvent;
import com.j2ee.cms.common.core.web.event.ResponseEvent;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CPS</p>
 * <p>版权: Copyright (c) 2009  
 * @author 郑荣华
 * @version 1.0
 * @since 2009-9-5 上午10:03:57
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class SystemLogAction extends GeneralAction {

	private String dealMethod = "";
	
	@Override
	protected void doFormFillment(ActionForm actionForm, ResponseEvent responseEvent, String userIndr) throws Exception {
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		SystemLogForm form = (SystemLogForm) actionForm;
		// 查询所有日志
		if(dealMethod.equals("")) {									
			Pagination pagination = (Pagination) responseParam.get("pagination");
			form.setPagination(pagination);
			
		// 删除日志
		} else if(dealMethod.equals("delete")) {
			form.setInfoMessage("删除日志成功");
			
			Pagination pagination = (Pagination) responseParam.get("pagination");
			form.setPagination(pagination);
			
		// 导出日志	
		} else if(dealMethod.equals("export")) {
			form.setInfoMessage("导出日志成功");
			this.setRedirectPage("exportLogsSuccess", userIndr);
			
		// 下载日志
		} else if(dealMethod.equals("savaExportLogs")) {
			this.setRedirectPage("webClient", userIndr);
		
		// 清空日志	
		} else if(dealMethod.equals("clear")) {
			form.setInfoMessage("清空日志成功");
		}
	}

	@Override
	protected void performTask(ActionForm actionForm, RequestEvent requestEvent, String userIndr)
			throws Exception {
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		HttpServletRequest request = (HttpServletRequest) requestParam.get("HttpServletRequest");
		SystemLogForm form = (SystemLogForm) actionForm;
		this.dealMethod = form.getDealMethod();
		// 查询所有日志
		if(dealMethod.equals("")) {
			form.setQueryKey("findLogsBySiteIdPage");
			requestParam.put("pagination", form.getPagination());
		
		// 删除日志
		} else if(dealMethod.equals("delete")) {
			form.setQueryKey("findLogsBySiteIdPage");
			requestParam.put("pagination", form.getPagination());
			
			String deletedLogIds = form.getDeletedLogIds();
			requestParam.put("deletedIds", deletedLogIds);
			
		// 导出日志	
		} else if(dealMethod.equals("export")) {
			requestParam.put("form", form);
			
		// 下载日志
		} else if(dealMethod.equals("savaExportLogs")) {
			requestParam.put("request", request);
			
		// 清空日志	
		} else if(dealMethod.equals("clear")) {
			
		}
	}

	@Override
	protected void init(String arg0) throws Exception {
		this.setRedirectPage("success", userIndr);
	}
}
