package com.baize.ccms.biz.usermanager.web.event;

import java.util.ArrayList;
import java.util.Calendar;

import com.baize.common.core.web.event.ResponseEvent;

/**
 * 
 * 利用responseEvent的构造函数来写新生成的sessionid 
 * 利用requestEvetn的setRepCode来保存是否通过验证
 * ccms通用信息管理系统 
 * package: com.baize.ccms.biz.usermanager.web.event
 * File: UserLoginResEvent.java 创建时间:2009-1-12上午10:03:42
 * Title: 标题（要求能简洁地表达出类的功能和职责）
 * Description: 用来返回客户端验证登陆信息的response对象
 * Copyright: Copyright (c) 2009 南京百泽网络科技有限公司
 * Company: 南京百泽网络科技有限公司
 * 模块: 平台架构
 * @author  娄伟峰
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */

public class UserLoginResEvent extends ResponseEvent {

	private Calendar currentTime = null;

	private ArrayList menuTreeList = null;

	private String userid = null;
	
	private String siteId = null;
	
	private String roleName = null;

	private java.util.HashMap czrydmInfoMap;

	public Calendar getCurrentTime() {
		return this.currentTime;
	}

	public void setCurrentTime(Calendar time) {
		this.currentTime = time;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public ArrayList getMenuTreeList() {
		return this.menuTreeList;
	}

	public void setMenuTreeList(ArrayList menuTreeList) {
		this.menuTreeList = menuTreeList;
	}

	public java.util.HashMap getCzrydmInfoMap() {
		return czrydmInfoMap;
	}

	public void setCzrydmInfoMap(java.util.HashMap czrydmInfoMap) {
		this.czrydmInfoMap = czrydmInfoMap;
	}

	/**
	 * loginResEvent构造函数
	 * 
	 * @param sessionID
	 * @param channelType
	 */
	public UserLoginResEvent(String sessionID, String channelType,String siteId,String roleName,String loginName,ArrayList menuTreeList) {
		super(sessionID, channelType,siteId,roleName,loginName);
		this.menuTreeList = menuTreeList;
	}

	public UserLoginResEvent(String sessionID, String channelType,String siteId,String roleName,String loginName) {
		super(sessionID, channelType,siteId,roleName,loginName);		
	}
	public UserLoginResEvent() {
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
