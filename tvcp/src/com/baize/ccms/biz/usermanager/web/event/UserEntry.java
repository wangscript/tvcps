package com.j2ee.cms.biz.usermanager.web.event;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
/**
 * 
 * cps通用信息管理系统 
 * package: com.j2ee.cms.biz.usermanager.web.event
 * File: UserEntry.java 创建时间:2009-1-12上午10:27:40
 * Title: 标题（要求能简洁地表达出类的功能和职责）
 * Description: 描述（简要描述类的职责、实现方式、使用注意事项等）
 * Copyright: Copyright (c) 2009  
 * Company:  
 * 模块: 平台架构
 * @author  娄伟峰
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */

public class UserEntry {
 
    /**
     * 代表用户的名称，只作显示使用。
     */
    private String userLogonID;

    /**
     * 用户的渠道类型。
     */
    private String channelType;

    /**
     * 用户登录的时间
     */
    private Date loginTime;

    /**
     * 用户登录后生成的sessionid
     */
    private String sessionid;
    
    /**网站ID */
    private String siteId;
    
    /** 角色名 */
    private String roleName;
    
    /**
     * 用户信息的对象，保存用户的登录的ID，登录时间、渠道类型和会话ID。通过构造这个用户的对象。
     * @param userLoginID
     * @param channelType
     * @param siteId
     * @param loginTime
     * @param sessionid
     * @param roleName 角色名
     */
    public UserEntry(String userLogonID, String channelType,String siteId, Date loginTime,
                     String sessionid,String roleName) {
        this.userLogonID = userLogonID;
        this.channelType = channelType;
        this.siteId = siteId;
        this.loginTime = loginTime;
        this.sessionid = sessionid;
        this.roleName = roleName;
    }
    public UserEntry() {
        
    }

    public String getUserLogonID(){
        return userLogonID;
    }

    public void setUserLogonID(String userLogonID){
        this.userLogonID = userLogonID;
    }

    public String getChannelType(){
        return channelType;
    }

    public void setChannelType(String channelType){
        this.channelType = channelType;
    }



    /**
	 * @return the loginTime
	 */
	public Date getLoginTime() {
		return loginTime;
	}
	/**
	 * @param loginTime the loginTime to set
	 */
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	public String getSessionid(){
        return sessionid;
    }

    public void setSessionid(String sessionid){
        this.sessionid = sessionid;
    }
	/**
	 * @return the siteId
	 */
	public String getSiteId() {
		return siteId;
	}
	/**
	 * @param siteId the siteId to set
	 */
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}
	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}


}