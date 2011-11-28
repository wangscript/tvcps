
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
public class ServicePersonEntity extends GenericEntity implements java.io.Serializable{
	
	/**
	 * 服务人员ID。
	 */
	private String 	servicePersonId;
	
	private String 	recommendInfoId;
	/**
	 * 服务人员状态。
	 */
	private PersonStatusEntity personStatusEntity;
//	private String 	serviceStatusId;
	
	/**
	 * 服务人员联系方式。
	 */
	private String 	contactMethod;
	
	/**
	 * 钟点工名字。
	 */
	private String 	servicePersonName;
	
	/**
	 * 服务人员年龄。
	 */
	private String 	age;
	
	/**
	 * 籍贯地址。
	 */
	private String 	nativePlace;
	
	/**
	 * 备注。
	 */
	private String 	comment1;
	private String 	comment2;
	
	
	public String getServicePersonId() {
		return servicePersonId;
	}
	public void setServicePersonId(String servicePersonId) {
		this.servicePersonId = servicePersonId;
	}
	public String getRecommendInfoId() {
		return recommendInfoId;
	}
	public void setRecommendInfoId(String recommendInfoId) {
		this.recommendInfoId = recommendInfoId;
	}
	public String getContactMethod() {
		return contactMethod;
	}
	public void setContactMethod(String contactMethod) {
		this.contactMethod = contactMethod;
	}
	public String getServicePersonName() {
		return servicePersonName;
	}
	public void setServicePersonName(String servicePersonName) {
		this.servicePersonName = servicePersonName;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getNativePlace() {
		return nativePlace;
	}
	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}
	public String getComment2() {
		return comment2;
	}
	public void setComment2(String comment2) {
		this.comment2 = comment2;
	}
	public PersonStatusEntity getPersonStatusEntity() {
		return personStatusEntity;
	}
	public void setPersonStatusEntity(PersonStatusEntity personStatusEntity) {
		this.personStatusEntity = personStatusEntity;
	}
	public String getComment1() {
		return comment1;
	}
	public void setComment1(String comment1) {
		this.comment1 = comment1;
	}

}
