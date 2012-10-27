/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.unitmanager.web.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.j2ee.cms.biz.unitmanager.web.form.StaticUnitForm;
import com.j2ee.cms.sys.GlobalConfig;
import com.j2ee.cms.common.core.util.StringUtil;
import com.j2ee.cms.common.core.web.GeneralAction;
import com.j2ee.cms.common.core.web.event.RequestEvent;
import com.j2ee.cms.common.core.web.event.ResponseEvent;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 单元管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-6-1 上午12:19:57
 * @history（历次修订内容、修订人、修订时间等）
 */
public class StaticUnitAction extends GeneralAction {
	private String dealMethod = "";
	@Override
	protected void doFormFillment(ActionForm actionForm, ResponseEvent responseEvent, String userIndr) {
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		StaticUnitForm form = (StaticUnitForm) actionForm;
		String staticContent = (String) responseParam.get("staticContent");
		if(staticContent == "" || staticContent == null) {
			staticContent = " ";
		}
		form.setStaticContent(staticContent);
		// 查找配置
		if(dealMethod.equals("findConfig")) {
			
		// 保存文章正文的数据	
		} else if(dealMethod.equals("saveConfig") || dealMethod.equals("saveSiteConfig")) {
			form.setInfoMessage("保存设置成功!");
		}
	}

	@Override
	protected void init(String userIndr) throws Exception {
		this.setRedirectPage("success", userIndr);
	}

	@Override
	protected void performTask(ActionForm actionForm, RequestEvent requestEvent, String userIndr) throws Exception {		 
	
		StaticUnitForm form = (StaticUnitForm) actionForm;
		this.dealMethod = form.getDealMethod();
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		HttpServletRequest request = (HttpServletRequest) requestParam.get("HttpServletRequest");
		String unitId = form.getUnit_unitId();
		String categoryId = form.getUnit_categoryId();
		
		// 查找配置
		if (dealMethod.equals("findConfig")) {
			
		// 保存配置
		} else if (dealMethod.equals("saveConfig") || dealMethod.equals("saveSiteConfig")) {
			requestParam.put("form", form);
		} 
		requestParam.put("unitId", unitId);
		requestParam.put("categoryId", categoryId);
	}

}
