/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.usermanager.domain;

import java.util.Date;

/**
 * <p>标题: 会员</p>
 * <p>描述: 前台注册用户</p>
 * <p>模块: 用户管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司</p>
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-6-13 下午05:54:25
 * @history（历次修订内容、修订人、修订时间等）
 */
public class Member {
	
	/** 实体唯一标识 */
	private String id;

	/** 登录用户名 */
	private String loginName;
	
	/** 登录密码 */
	private String password;

	/** 用户名称 */
	private String name;
	
	/** 移动电话 */
	private String mobileTel;
	
	/** 家庭电话 */
	private String homeTel;
	
	/** 居住地 */
	private String residence;
	
	/** 注册日期 */
	private Date registerDate;
	
	/** 个人主页 */
	private String personHomePage;
	
	/** email */
	private String email;
	
	/** 是否删除 */
	private boolean deleted;

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
	 * @return the mobileTel
	 */
	public String getMobileTel() {
		return mobileTel;
	}

	/**
	 * @param mobileTel the mobileTel to set
	 */
	public void setMobileTel(String mobileTel) {
		this.mobileTel = mobileTel;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the homeTel
	 */
	public String getHomeTel() {
		return homeTel;
	}

	/**
	 * @param homeTel the homeTel to set
	 */
	public void setHomeTel(String homeTel) {
		this.homeTel = homeTel;
	}

	/**
	 * @return the registerDate
	 */
	public Date getRegisterDate() {
		return registerDate;
	}

	/**
	 * @param registerDate the registerDate to set
	 */
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	/**
	 * @return the personHomePage
	 */
	public String getPersonHomePage() {
		return personHomePage;
	}

	/**
	 * @param personHomePage the personHomePage to set
	 */
	public void setPersonHomePage(String personHomePage) {
		this.personHomePage = personHomePage;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the deleted
	 */
	public boolean isDeleted() {
		return deleted;
	}

	/**
	 * @param deleted the deleted to set
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	/**
	 * @return the residence
	 */
	public String getResidence() {
		return residence;
	}

	/**
	 * @param residence the residence to set
	 */
	public void setResidence(String residence) {
		this.residence = residence;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

}
