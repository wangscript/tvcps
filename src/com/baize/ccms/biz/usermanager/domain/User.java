/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.biz.usermanager.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>标题: 用户</p>
 * <p>描述: 操作ccms的用户</p>
 * <p>模块: 用户管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-2-16 下午04:30:13
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class User implements Serializable {

	private static final long serialVersionUID = -7228481863046964310L;

	/** 实体唯一标识 */
	private String id;

	/** 系统登录用户名 */
	private String loginName;

	/** 移动电话 */
	private String mobileTel;
	
	/** 用户名称 */
	private String name;
	
	/** 登录密码 */
	private String password;
	
	/** 家庭电话 */
	private String homeTel;
	
	/** 办公电话 */
	private String officeTel;
	
	/** 注册日期 */
	private Date registerDate;
	
	/** 个人主页 */
	private String personHomePage;
	
	/** email */
	private String email;
	
	/** 用户所在机构 */
	private Organization organization;
	
	/** 职务 */
	private String position;
	
	/** 用户所对应的菜单的显示顺序 
	现在菜单ID以*号分割，每个网站的菜单单独分开，如网站1的菜单id是2001，菜单的id分别为f001#m1,f002#m2.
	网站2的菜单id是2002，菜单的id分别为f003#m1,foo4#m2,则在数据库中保存的格式为2001,f001#m1,f002#m2*f003#m1,f004#m2
	 * 
	 */
	private String menuIds;
	
	/** 用户所拥有的网站 */
	private List<String> siteIds;
	
	/** 是否删除 */
	private boolean deleted;
	/** 系统功能权限，包括系统设置和功能单元左侧树的权限 */
	private String systemFunction;
	
	/**  已选菜单 */
	private String chooseMenuIds;
    
	
	public User() {
		// Default constructor needed by Hibernate
	}
	
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getMobileTel() {
		return mobileTel;
	}

	public void setMobileTel(String mobileTel) {
		this.mobileTel = mobileTel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHomeTel() {
		return homeTel;
	}

	public void setHomeTel(String homeTel) {
		this.homeTel = homeTel;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public String getPersonHomePage() {
		return personHomePage;
	}

	public void setPersonHomePage(String personHomePage) {
		this.personHomePage = personHomePage;
	}
	


	public String getOfficeTel() {
		return officeTel;
	}

	public void setOfficeTel(String officeTel) {
		this.officeTel = officeTel;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email 要设置的 email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

 

	/**
	 * @return the siteIds
	 */
	public List<String> getSiteIds() {
		return siteIds;
	}

	/**
	 * @param siteIds the siteIds to set
	 */
	public void setSiteIds(List<String> siteIds) {
		this.siteIds = siteIds;
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

	/**
	 * @return the menuIds
	 */
	public String getMenuIds() {
		return menuIds;
	}

	/**
	 * @param menuIds the menuIds to set
	 */
	public void setMenuIds(String menuIds) {
		this.menuIds = menuIds;
	}

	/**
	 * @return the systemFunction
	 */
	public String getSystemFunction() {
		return systemFunction;
	}

	/**
	 * @param systemFunction the systemFunction to set
	 */
	public void setSystemFunction(String systemFunction) {
		this.systemFunction = systemFunction;
	}

	public String getChooseMenuIds() {
		return chooseMenuIds;
	}

	public void setChooseMenuIds(String chooseMenuIds) {
		this.chooseMenuIds = chooseMenuIds;
	}

}
