/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.unitmanager.web.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.j2ee.cms.biz.unitmanager.web.form.OnlineSurverySetForm;
import com.j2ee.cms.common.core.web.GeneralAction;
import com.j2ee.cms.common.core.web.event.RequestEvent;
import com.j2ee.cms.common.core.web.event.ResponseEvent;
/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CPS</p>
 * <p>版权: Copyright (c) 2009  
 * @author  包坤涛
 * @version 1.0
 * @since 2009-6-1 下午05:40:47
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class OnlineSurverySetAction extends GeneralAction {

	private String dealMethod = "";
	
	@Override
	protected void doFormFillment(ActionForm actionForm, ResponseEvent responseEvent, String userIndr) {
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		OnlineSurverySetForm form = (OnlineSurverySetForm) actionForm;
		form = (OnlineSurverySetForm) responseParam.get("form");   
		
		if(dealMethod.equals("findConfig")){
			this.setRedirectPage("onlineSurverySet", userIndr);
		
		}else if(dealMethod.equals("detail")){
			this.setRedirectPage("onlineSurverySet", userIndr);
		
		}else if(dealMethod.equals("findCategory")){
			this.setRedirectPage("onlineSurverySet", userIndr);
			
		//查找主题
		}else if(dealMethod.equals("findTheme")){
			String themes = (String) responseParam.get("themes");
			form.setThemes(themes);
			this.setRedirectPage("findTheme", userIndr);
		
		//保存	
		}else if(dealMethod.equals("save")){
			form.setThemes("保存成功");
			this.setRedirectPage("findTheme", userIndr);
		}     
	}

	@Override
	protected void performTask(ActionForm actionForm, RequestEvent requestEvent, String userIndr) throws Exception {
		OnlineSurverySetForm form = (OnlineSurverySetForm) actionForm;
		this.dealMethod = form.getDealMethod();
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		HttpServletRequest request = (HttpServletRequest) requestParam.get("HttpServletRequest");
		
		//查询样式设置
		if(dealMethod.equals("findConfig")){
			
		//保存样式设置
		}else if(dealMethod.equals("detail")){
		
		}else if(dealMethod.equals("findCategory")){
			String infoMessage=request.getParameter("message");
			form.setInfoMessage(infoMessage);
        	String	categoryId = request.getParameter("categoryId");
        	requestParam.put("categoryId", categoryId);
		
		}else if(dealMethod.equals("findchooseType")){
			
		//查找主题
		}else if(dealMethod.equals("findTheme")){
			String category = form.getCategory();
			requestParam.put("category", category);
			
		//保存	
		}else if(dealMethod.equals("save")){
			
		}
		requestParam.put("form", form);
	}
  
	@Override
	protected void init(String arg0) throws Exception {
		
	}
}