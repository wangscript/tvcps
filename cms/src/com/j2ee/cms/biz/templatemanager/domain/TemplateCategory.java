/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.templatemanager.domain;

import java.io.Serializable;
import java.util.Date;

import com.j2ee.cms.biz.sitemanager.domain.Site;
import com.j2ee.cms.biz.usermanager.domain.User;

/**
 * <p>标题: 模板类别</p>
 * <p>描述: 模板类别信息展示的框架</p>
 * <p>模块: 模板管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-4-27 下午03:59:43
 * @history（历次修订内容、修订人、修订时间等） 
 */

public class TemplateCategory  implements Serializable {

	private static final long serialVersionUID = 4208307365896111355L;

	/** 唯一标识符 */
	private String id;
	
	/** 模板类别名称 */
	private String name;
	
	/** 创建人 */
	private User creator;
	
	/** 所属网站 */
	private Site site;
	
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
	 * @return the creator
	 */
	public User getCreator() {
		return creator;
	}

	/**
	 * @param creator the creator to set
	 */
	public void setCreator(User creator) {
		this.creator = creator;
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
}
