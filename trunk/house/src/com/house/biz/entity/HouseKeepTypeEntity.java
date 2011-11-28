
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
public class HouseKeepTypeEntity extends GenericEntity implements java.io.Serializable{
	/**
	 * 家政公司类别ID
	 */
	private String houseKeepTypeId;          
	/**
	 * 类别名称
	 */
	private String typeName;
	/**
	 * 类别创建时间
	 */
	private Date createTime;	
	/**
	 * 家政公司类别描述
	 */
	private String description;
	
	
	
	public String getHouseKeepTypeId() {
		return houseKeepTypeId;
	}
	public void setHouseKeepTypeId(String houseKeepTypeId) {
		this.houseKeepTypeId = houseKeepTypeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	






	

}
