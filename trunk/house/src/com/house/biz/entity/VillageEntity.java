
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
public class VillageEntity extends GenericEntity implements java.io.Serializable{
	/**
	 * 小区代码ID
	 */
	private String villageId;          
	/**
	 * 小区名称
	 */
	private String villageName;
	/**
	 * 小区创建时间
	 */
	private Date createTime;	
	/**
	 * 说明
	 */
	private String description;
	
	/**
	 * 管理员用户
	 */
	private AdminUserEntity adminUserEntity;
	
	
	

	public String getVillageId() {
		return villageId;
	}
	public void setVillageId(String villageId) {
		this.villageId = villageId;
	}

	public String getVillageName() {
		return villageName;
	}
	public void setVillageName(String villageName) {
		this.villageName = villageName;
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
