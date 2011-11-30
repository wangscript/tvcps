package com.j2ee.cms.biz.usermanager.web.event;

import com.j2ee.cms.common.core.web.event.RequestEvent;


/**
 * 
 * cps通用信息管理系统--验证登陆使用的requestEvent对象 
 * package: com.j2ee.cms.biz.usermanager.event
 * File: UserLoginReqEvent.java 创建时间:2009-1-12上午09:42:17
 * Title: 标题（要求能简洁地表达出类的功能和职责）
 * Description: 描述（简要描述类的职责、实现方式、使用注意事项等）
 * Copyright: Copyright (c) 2009  
 * Company:  
 * 模块: 平台架构
 * @author  娄伟峰
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class UserLoginReqEvent extends RequestEvent {
	/**
	 * 登陆名
	 */
    private String loginName; 
    /**
     * 用户密码
     */
    private String password; 
    /**
     * sso登陆时取到的登陆用户名
     */
    private String remoteUserid ;
    /**
     * 处理规则
     */
    private String handleCode ;
    /**
     * 登录类型--普通登录
     */
    private String loginType = LOGIN_TYPE_FRONT;
    /**
     * 登录类型--前台登录    
     */
    public static final String LOGIN_TYPE_FRONT="frontLogin";
    /**
     * 登录类型--后台登录
     */
    public static final String LOGIN_TYPE_BACK="backLogin";

    public String getRemoteUserid() {
		return remoteUserid;
	}

	public void setRemoteUserid(String remoteUserid) {
		this.remoteUserid = remoteUserid;
	}

    /**
     * loginReqEvent构造函数
     * @param channelType
     * @param sessionID
     */
    public UserLoginReqEvent( String channelType, String sessionID,String siteId,String roleName,String loginName) {
        super( channelType, sessionID,siteId,roleName,loginName);
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

	public String getHandleCode() {
		return handleCode;
	}

	public void setHandleCode(String handleCode) {
		this.handleCode = handleCode;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
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

}