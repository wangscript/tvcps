/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.biz.documentmanager.domain;

import java.io.Serializable;
import java.util.Date;

import com.baize.ccms.biz.sitemanager.domain.Site;
import com.baize.ccms.biz.usermanager.domain.User;

/**
 * <p>标题: 文档类别</p>
 * <p>描述: 对文档进行分门别类</p>
 * <p>模块: 文档管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-3-26 上午10:00:46
 * @history（历次修订内容、修订人、修订时间等）
 */
public class DocumentCategory implements Serializable {

	private static final long serialVersionUID = 5549307353959532787L;

	/** 唯一标识 */
	private String id;
	
	/** 列别名称 */
	private String name;
	
	/** 描述 */
	private String description;
	
	/** 创建者 */
	private User creator;
	
	/** 创建时间 */
	private Date createTime;
	
	/** 当前网站 */
	private Site site;

	/**
	 * @return the id  获得类别的id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set 设置类别的id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name 获得类别的名字
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set 设置类别的名字
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description 获得类别的描述
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set 设置类别的描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the creator 获得文档的添加者
	 */
	public User getCreator() {
		return creator;
	}

	/**
	 * @param creator the creator to set 设置文档的添加者
	 */
	public void setCreator(User creator) {
		this.creator = creator;
	}

	/**
	 * @return the createTime 获得文档的添加日期
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set 设置文档的添加日期
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the site 获得网站
	 */
	public Site getSite() {
		return site;
	}

	/**
	 * @param site the site to set 设置网站
	 */
	public void setSite(Site site) {
		this.site = site;
	}
}
