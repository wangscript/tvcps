/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.unitmanager.web.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.j2ee.cms.biz.unitmanager.web.form.TitleLinkForm;
import com.j2ee.cms.sys.GlobalConfig;
import com.j2ee.cms.common.core.util.StringUtil;
import com.j2ee.cms.common.core.web.GeneralAction;
import com.j2ee.cms.common.core.web.event.RequestEvent;
import com.j2ee.cms.common.core.web.event.ResponseEvent;

/**
 * 
 * <p>标题: —— 标题链接action</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 模板单元管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-6-3 上午10:22:10
 * @history（历次修订内容、修订人、修订时间等）
 */
public class TitleLinkAction extends GeneralAction {
	private String dealMethod = "";
	@Override
	protected void doFormFillment(ActionForm actionForm, ResponseEvent responseEvent, String userIndr) {
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		TitleLinkForm titleLinkForm = (TitleLinkForm) actionForm;	
		// 查找配置
		if (dealMethod.equals("findConfig")) {
			titleLinkForm = (TitleLinkForm)responseParam.get("titleLinkForm");
		
		// 保存配置
		} else if (dealMethod.equals("saveConfig") || dealMethod.equals("saveSiteConfig")) {
			titleLinkForm.setInfoMessage("保存设置成功!");
			this.setRedirectPage("savesuccess", userIndr);
		
		// 查找指定栏目的属性		
		} else if(dealMethod.equals("findFieldCode")) {
			titleLinkForm = (TitleLinkForm)responseParam.get("titleLinkForm");
			this.setRedirectPage("findAttributeSuccess", userIndr);
		}/*else if(dealMethod.equals("findStyleById")){
			//根据样式管理的ID查询样式的数据
			titleLinkForm = (TitleLinkForm)responseParam.get("titleLinkForm");
			this.setRedirectPage("findStyle", userIndr);
		}*/
	}

	@Override
	protected void performTask(ActionForm actionForm, RequestEvent requestEvent, String userIndr) throws Exception {		 
		TitleLinkForm form = (TitleLinkForm) actionForm;	
		this.dealMethod = form.getDealMethod();
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		HttpServletRequest request = (HttpServletRequest) requestParam.get("HttpServletRequest");
		// 查找配置
		if (dealMethod.equals("findConfig")) {
			requestParam.put("form", form);

		// 保存配置	
		} else if (dealMethod.equals("saveConfig") || dealMethod.equals("saveSiteConfig")) {
			requestParam.put("form", form);
		
		// 查找指定栏目的属性	
		} else if(dealMethod.equals("findFieldCode")) {
			requestParam.put("form", form);
		}/*else if(dealMethod.equals("findStyleById")){
			//根据样式管理的ID查询样式的数据
			requestParam.put("form", form);
		}*/
	}

	@Override
	protected void init(String userIndr) throws Exception {
		this.setRedirectPage("success", userIndr);
	}
}
