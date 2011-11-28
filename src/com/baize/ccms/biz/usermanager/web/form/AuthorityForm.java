  /**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.biz.usermanager.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.baize.ccms.biz.usermanager.domain.Authority;
import com.baize.common.core.web.GeneralForm;

/**
 * <p>标题: —— 权限form</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 用户管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-2-25 下午02:59:11
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class AuthorityForm extends GeneralForm{
	
	private Authority authority = new Authority();

	@Override
	protected void validateData(ActionMapping mapping, HttpServletRequest request) {
		// TODO 自动生成方法存根
		
	}

}
