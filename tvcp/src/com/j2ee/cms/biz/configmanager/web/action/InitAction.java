/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.configmanager.web.action;

import java.util.List;
import java.util.Map;

import org.apache.struts.action.ActionForm;

import com.j2ee.cms.biz.configmanager.web.form.InitForm;
import com.j2ee.cms.common.core.web.GeneralAction;
import com.j2ee.cms.common.core.web.event.RequestEvent;
import com.j2ee.cms.common.core.web.event.ResponseEvent;

/**
 * <p>标题: 系统设置Action</p>
 * <p>描述: 控制系统左侧树的生成</p>
 * <p>模块: 系统设置</p>
 * <p>版权: Copyright (c) 2009 
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-7-15 下午04:12:33 
 * @history（历次修订内容、修订人、修订时间等） 
*/

public class InitAction extends GeneralAction {
	
	private String dealMethod = "";
	@Override
	protected void doFormFillment(ActionForm actionForm, ResponseEvent responseEvent, String userIndr) {
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		InitForm form = (InitForm) actionForm;		
		if (dealMethod.equals("getTree")) {
			//根据权限获取左侧的树
			form.setJson_list((List)responseParam.get("treelist"));
			this.setRedirectPage("tree", userIndr);		
		}else if(dealMethod.equals("load")){
			//点击进入系统设置模块时查询出右测需要显示的 url
			String rightFrameUrl = (String)responseParam.get("rightFrameUrl");
			form.setRightFrameUrl(rightFrameUrl);
			this.setRedirectPage("success", userIndr);		
		}
	}
	
	@Override
	protected void performTask(ActionForm actionForm, RequestEvent requestEvent, String userIndr) throws Exception {
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		
		InitForm form = (InitForm) actionForm;
		this.dealMethod = form.getDealMethod();		
		if(dealMethod.equals("getTree")) {
			//获取系统设置左侧树
			String treeid = form.getTreeId();
			requestParam.put("treeid", treeid);	 
		}else if(dealMethod.equals("load")){
			//点击进入系统设置模块时查询出右测需要显示的 url
		}
	}
	
	
	/* (non-Javadoc)
	 * @see com.j2ee.cms.common.core.web.GeneralAction#init(java.lang.String)
	 */
	@Override
	protected void init(String userIndr) throws Exception {

	}
}
