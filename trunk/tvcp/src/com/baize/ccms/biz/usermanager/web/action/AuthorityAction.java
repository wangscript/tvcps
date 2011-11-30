  /**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.usermanager.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;

import com.j2ee.cms.biz.usermanager.service.AuthorityBiz;
import com.j2ee.cms.common.core.web.GeneralAction;
import com.j2ee.cms.common.core.web.event.RequestEvent;
import com.j2ee.cms.common.core.web.event.ResponseEvent;

/**
 * <p>标题: 权限ACTION</p>
 * <p>描述: 一个权限对应多个授权，一个权限里又包含多个资源和操作</p>
 * <p>模块: 用户管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-2-25 下午02:50:04
 * @history（历次修订内容、修订人、修订时间等） 
 */
public final class AuthorityAction  extends GeneralAction{


	@Override
	protected void init(String userIndr) throws Exception {
		
	}

	@Override
	protected void doFormFillment(ActionForm actionForm,
			ResponseEvent responseEvent, String userIndr) throws Exception {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void performTask(ActionForm actionForm,
			RequestEvent requestEvent, String userIndr) throws Exception {
		// TODO Auto-generated method stub
		
	}

	


}
