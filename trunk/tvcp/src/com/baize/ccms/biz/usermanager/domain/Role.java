/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.usermanager.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.j2ee.cms.biz.sitemanager.domain.Site;

/**
 * 
 * <p>标题: —— 角色</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 用户管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-10-19 下午07:03:09
 * @history（历次修订内容、修订人、修订时间等）
 */
public class Role implements Serializable {
	
	private static final long serialVersionUID = -402258533535232793L;

	/** 唯一标识 */
	private String id;
	
	/** 角色名 */
	private String name;

	/** 描述 */
	private String description;
	
	/** 是否为缺省角色 */
	private boolean defaulted;
	
	/** 创建时间 */
	private Date createTime;
	
	/** 当前网站 */
	private Site site;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	 * @return the site
	 */
	public Site getSite() {
		return site;
	}

	/**
	 * @param site the site to set
	 */
	public void setSite(Site site) {
		this.site = site;
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
	 * @return the defaulted
	 */
	public boolean isDefaulted() {
		return defaulted;
	}

	/**
	 * @param defaulted the defaulted to set
	 */
	public void setDefaulted(boolean defaulted) {
		this.defaulted = defaulted;
	}
	
}
