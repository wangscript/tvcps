package com.house.biz.entity;

import com.house.core.entity.GenericEntity;

public class HouseKeepingEntity extends GenericEntity implements java.io.Serializable{
	/**
	 * 家政公司ID。
	 */
	private String houseKeepingId;
	
	/**
	 * 家政公司名称。
	 */
	private String companyName;
	
	/**
	 * 家政公司电话。
	 */
	private String phone;
	
	/**
	 * 推荐价格。
	 */
	private String price;
	
	/**
	 * 公司地址。
	 */
	private String address;
	
	/**
	 * 公司网址。
	 */
	private String siteUrl;
	
	/**
	 * 公司邮箱。
	 */
	private String mail;
	
	/**
	 * 联系人。
	 */
	private String connectPerson;
	
	/**
	 * 信息来源。
	 */
	private String source;
	
	/**
	 * 备用字段1。
	 */
	private String text1;
	
	/**
	 * 备用字段2。
	 */
	private String text2;
	
	/**
	 * 备用字段3。
	 */
	private String text3;
	
	/**
	 * 家政公司类别ID。
	 */
	private HouseKeepTypeEntity houseKeepTypeEntity;
//	private String houseKeepingTypeId;
	
	/**
	 * 管理员用户。
	 */
	private AdminUserEntity adminUserEntity;
//	private String ADMINUSER_ID;

	public String getHouseKeepingId() {
		return houseKeepingId;
	}

	public void setHouseKeepingId(String houseKeepingId) {
		this.houseKeepingId = houseKeepingId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSiteUrl() {
		return siteUrl;
	}

	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getConnectPerson() {
		return connectPerson;
	}

	public void setConnectPerson(String connectPerson) {
		this.connectPerson = connectPerson;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getText1() {
		return text1;
	}

	public void setText1(String text1) {
		this.text1 = text1;
	}

	public String getText2() {
		return text2;
	}

	public void setText2(String text2) {
		this.text2 = text2;
	}

	public String getText3() {
		return text3;
	}

	public void setText3(String text3) {
		this.text3 = text3;
	}

	public HouseKeepTypeEntity getHouseKeepTypeEntity() {
		return houseKeepTypeEntity;
	}

	public void setHouseKeepTypeEntity(HouseKeepTypeEntity houseKeepTypeEntity) {
		this.houseKeepTypeEntity = houseKeepTypeEntity;
	}

	public AdminUserEntity getAdminUserEntity() {
		return adminUserEntity;
	}

	public void setAdminUserEntity(AdminUserEntity adminUserEntity) {
		this.adminUserEntity = adminUserEntity;
	}

}
