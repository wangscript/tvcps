/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.biz.usermanager.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * <p>标题: 机构</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 通用平台</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since Feb 15, 2009 3:05:04 PM
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class Organization implements Serializable {

	private static final long serialVersionUID = -772280492420191659L;

	/** 唯一标识 */
	private String id;
	
	/** 机构名称 */
	private String name;
	
	/** 机构描述 */
	private String description;
	
	/** 是否删除 */
	private boolean deleted;

	/** 父机构 * --- 1 */
	private Organization parent ;
	
	/** 子机构 1 --- */
	private Set<Organization> children;
	
	/** 是否是叶子节点 */
	private boolean leaf;
	
	/** 是否选中 */
	private boolean checked;
	
	/** 链接地址 */
	private String linkAddress;
	
	/** 创建时间 */
	private Date createTime = new Date();
	
	
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

	public String getLinkAddress() {
		return linkAddress;
	}

	public void setLinkAddress(String linkAddress) {
		this.linkAddress = linkAddress;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Organization getParent() {
		return parent;
	}

	public void setParent(Organization parent) {
		this.parent = parent;
	}

	public Set<Organization> getChildren() {
		return children;
	}

	public void setChildren(Set<Organization> children) {
		this.children = children;
	}

	/**
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description 要设置的 description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the leaf
	 */
	public boolean isLeaf() {
		return leaf;
	}

	/**
	 * @param leaf the leaf to set
	 */
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	/**
	 * @return the checked
	 */
	public boolean isChecked() {
		return checked;
	}

	/**
	 * @param checked the checked to set
	 */
	public void setChecked(boolean checked) {
		this.checked = checked;
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
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}



	
}
