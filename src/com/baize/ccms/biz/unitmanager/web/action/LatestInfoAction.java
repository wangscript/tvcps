/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.biz.unitmanager.web.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.baize.ccms.biz.unitmanager.web.form.LatestInfoForm;
import com.baize.ccms.sys.GlobalConfig;
import com.baize.common.core.util.StringUtil;
import com.baize.common.core.web.GeneralAction;
import com.baize.common.core.web.event.RequestEvent;
import com.baize.common.core.web.event.ResponseEvent;

/**
 * 
 * <p>标题: —— 最新信息action</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 模板单元管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-6-3 上午10:22:10
 * @history（历次修订内容、修订人、修订时间等）
 */
public class LatestInfoAction extends GeneralAction {

	private String dealMethod = "";
	@Override
	protected void doFormFillment(ActionForm actionForm, ResponseEvent responseEvent, String userIndr) {
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		LatestInfoForm latestInfoForm = (LatestInfoForm) actionForm;	
		
		// 查找配置
		if (dealMethod.equals("findConfig")) {
			latestInfoForm = (LatestInfoForm) responseParam.get("latestInfoForm");
			
		// 保存配置
		} else if (dealMethod.equals("saveConfig")) {
			latestInfoForm.setInfoMessage("保存设置成功!");
			this.setRedirectPage("savesuccess", userIndr);
		}
	}

	@Override
	protected void performTask(ActionForm actionForm, RequestEvent requestEvent, String userIndr) throws Exception {		
		LatestInfoForm form = (LatestInfoForm) actionForm;	
		this.dealMethod = form.getDealMethod();
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		
		// 查找配置
		if (dealMethod.equals("findConfig")) {
	
		// 保存配置
		} else if (dealMethod.equals("saveConfig") ) {
			
		}
		requestParam.put("form", form);
	}

	@Override
	protected void init(String userIndr) throws Exception {
		this.setRedirectPage("success", userIndr);
	}


}
