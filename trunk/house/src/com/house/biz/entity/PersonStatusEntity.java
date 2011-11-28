
package com.house.biz.entity;

import java.util.Date;

import com.house.core.entity.GenericEntity;

/**
 * <p>标题: —— 小区代码表</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 用户管理</p>
 * <p>版权: Copyright (c) 2011 娄伟峰
 * @author 娄伟峰
 * @version 1.0
 * @since 2011-11-8 上午10:41:09
 * @history（历次修订内容、修订人、修订时间等） 
 */

@SuppressWarnings("serial")
public class PersonStatusEntity extends GenericEntity implements java.io.Serializable{
	/**
	 * 服务人员状态ID
	 */
	private String serviceStatusId;          
	/**
	 * 状态名称
	 */
	private String statusName;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 简介
	 */
	private String description;
	/**
	 * 管理员用户
	 */
	private AdminUserEntity adminUserEntity;
	
	
	
	public String getServiceStatusId() {
		return serviceStatusId;
	}
	public void setServiceStatusId(String serviceStatusId) {
		this.serviceStatusId = serviceStatusId;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the adminUserEntity
	 */
	public AdminUserEntity getAdminUserEntity() {
		return adminUserEntity;
	}
	/**
	 * @param adminUserEntity the adminUserEntity to set
	 */
	public void setAdminUserEntity(AdminUserEntity adminUserEntity) {
		this.adminUserEntity = adminUserEntity;
	}
	
	

	




	

}
