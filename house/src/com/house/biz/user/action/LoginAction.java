package com.house.biz.user.action;

import javax.servlet.http.HttpSession;

import com.house.biz.entity.AdminUserEntity;
import com.house.biz.user.service.AdminUserService;
import com.house.core.action.GenericAction;
import com.house.core.common.Constant;
import com.house.core.sys.GlobalConfig;

/**
 * 
 * <p>标题: —— 登陆ACTION</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 通用平台</p>
 * <p>版权: Copyright (c) 2011 娄伟峰
 * @author 娄伟峰
 * @version 1.0
 * @since 2011-11-7 下午05:09:43
 * @history（历次修订内容、修订人、修订时间等）
 */
public class LoginAction extends GenericAction{

	/**
	 * 管理员用户服务类
	 */
	private AdminUserService adminUserService;
	/**
	 * 用户名
	 */
	private String loginName;
	/**
	 * 密码
	 */
	private String passWord;
	
	
	/**
	 * 管理员用户登陆
	 * @return String 
	 */
	public String login(){
		AdminUserEntity adminUserEntity = adminUserService.queryAdminUserByParm(loginName, passWord);
		if(adminUserEntity != null){
			this.getHttpSession().setAttribute(Constant.LOGINNAME, adminUserEntity.getLoginName());
			this.getHttpSession().setAttribute(Constant.USER_ID, adminUserEntity.getAdminuser_id());
			this.getHttpSession().setAttribute(Constant.USER_NAME, adminUserEntity.getUser_name());
			return SUCCESS;
		}else{
			addActionMessage(GlobalConfig.getConfProperty("100001"));			
			return LOGIN;
		}
		
	}
	
	/**
	 * 注销
	 * @return
	 */
	public String logOut(){
		HttpSession session = getHttpSession();
		session.removeAttribute(Constant.LOGINNAME);
		session.removeAttribute(Constant.USER_ID);
		session.removeAttribute(Constant.USER_NAME);
		session.invalidate();
		return SUCCESS;
	}

	/**
	 * @param adminUserService the adminUserService to set
	 */
	public void setAdminUserService(AdminUserService adminUserService) {
		this.adminUserService = adminUserService;
	}

	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * @param loginName the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * @return the passWord
	 */
	public String getPassWord() {
		return passWord;
	}

	/**
	 * @param passWord the passWord to set
	 */
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
}
