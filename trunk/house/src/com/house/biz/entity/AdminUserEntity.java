
package com.house.biz.entity;

import java.util.Date;

import com.house.core.entity.GenericEntity;

/**
 * <p>标题: —— 管理员用户实体类</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 用户管理</p>
 * <p>版权: Copyright (c) 2011 娄伟峰
 * @author 娄伟峰
 * @version 1.0
 * @since 2011-11-8 上午10:41:09
 * @history（历次修订内容、修订人、修订时间等） 
 */

@SuppressWarnings("serial")
public class AdminUserEntity extends GenericEntity implements java.io.Serializable{
	
	private String 	adminuser_id;
	private String 	loginName;
	private String 	password;
	private String 	user_name;
	private String 	email;
	private String 	phone;
	private String 	mobile_phone;
	private Date 	create_time;
	private String 	isdelete;
	private String 	desription;
	private String 	address;
	private String 	qq;
	private String 	msn;
	/**
	 * @return the adminuser_id
	 */
	public String getAdminuser_id() {
		return adminuser_id;
	}
	/**
	 * @param adminuserId the adminuser_id to set
	 */
	public void setAdminuser_id(String adminuserId) {
		adminuser_id = adminuserId;
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
	 * @return the user_name
	 */
	public String getUser_name() {
		return user_name;
	}
	/**
	 * @param userName the user_name to set
	 */
	public void setUser_name(String userName) {
		user_name = userName;
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
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return the mobile_phone
	 */
	public String getMobile_phone() {
		return mobile_phone;
	}
	/**
	 * @param mobilePhone the mobile_phone to set
	 */
	public void setMobile_phone(String mobilePhone) {
		mobile_phone = mobilePhone;
	}
	/**
	 * @return the isdelete
	 */
	public String getIsdelete() {
		return isdelete;
	}
	/**
	 * @param isdelete the isdelete to set
	 */
	public void setIsdelete(String isdelete) {
		this.isdelete = isdelete;
	}
	/**
	 * @return the desription
	 */
	public String getDesription() {
		return desription;
	}
	/**
	 * @param desription the desription to set
	 */
	public void setDesription(String desription) {
		this.desription = desription;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the qq
	 */
	public String getQq() {
		return qq;
	}
	/**
	 * @param qq the qq to set
	 */
	public void setQq(String qq) {
		this.qq = qq;
	}
	/**
	 * @return the msn
	 */
	public String getMsn() {
		return msn;
	}
	/**
	 * @param msn the msn to set
	 */
	public void setMsn(String msn) {
		this.msn = msn;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date createTime) {
		create_time = createTime;
	}
}
