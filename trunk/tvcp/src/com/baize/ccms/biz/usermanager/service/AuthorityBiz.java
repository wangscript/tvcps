  /**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.usermanager.service;

import com.j2ee.cms.common.core.service.BaseService;
import com.j2ee.cms.common.core.web.event.RequestEvent;
import com.j2ee.cms.common.core.web.event.ResponseEvent;

/**
 * <p>标题: 权限业务处理方法</p>
 * <p>描述: 一个权限对应多个授权，一个权限里又包含多个资源和操作</p>
 * <p>模块: 用户管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-2-25 下午02:45:15
 * @history（历次修订内容、修订人、修订时间等） 
 */
public final  class AuthorityBiz extends BaseService{

	/** 注入权限service */
	private AuthorityService authorityService;


	@Override
	public ResponseEvent validateData(RequestEvent req) throws Exception {
		// TODO 自动生成方法存根
		return null;
	}

	/**
	 * @param authorityService 要设置的 authorityService
	 */
	public void setAuthorityService(AuthorityService authorityService) {
		this.authorityService = authorityService;
	}

	@Override
	public void performTask(RequestEvent requestEvent,
			ResponseEvent responseEvent) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
