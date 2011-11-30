/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.unitmanager.web.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.j2ee.cms.biz.unitmanager.web.form.ColumnLinkForm;
import com.j2ee.cms.sys.GlobalConfig;
import com.j2ee.cms.common.core.util.StringUtil;
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
 * @since 2009-6-1 下午05:40:47
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class ColumnLinkAction extends GeneralAction {

	private String dealMethod = "";
	@Override
	protected void doFormFillment(ActionForm actionForm, ResponseEvent responseEvent, String userIndr) {
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		ColumnLinkForm form = (ColumnLinkForm) actionForm;
		
		// 查找配置文件
		if(dealMethod.equals("findConfig")) {
			form = (ColumnLinkForm) responseParam.get("form");
			this.setRedirectPage("success", userIndr);
		
		// 保存栏目链接的数据	
		} else if(dealMethod.equals("saveConfig") || dealMethod.equals("saveSiteConfig")) {
			form.setInfoMessage("保存设置成功!");
			this.setRedirectPage("saveSuccess", userIndr);
		}
	}

	@Override
	protected void performTask(ActionForm actionForm, RequestEvent requestEvent, String userIndr) throws Exception {		
		ColumnLinkForm form = (ColumnLinkForm) actionForm;
		this.dealMethod = form.getDealMethod();
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		HttpServletRequest request = (HttpServletRequest) requestParam.get("HttpServletRequest");
		// 查找配置文件
		if(dealMethod.equals("findConfig")) {
			requestParam.put("form", form);
			
		// 保存栏目链接的数据	
		} else if(dealMethod.equals("saveConfig") || dealMethod.equals("saveSiteConfig")) {
			requestParam.put("form", form);
		}
	}

	@Override
	protected void init(String arg0) throws Exception {
	}

}
