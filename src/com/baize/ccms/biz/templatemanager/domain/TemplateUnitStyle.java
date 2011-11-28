/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.templatemanager.domain;

import java.io.Serializable;
import java.util.Date;

import com.baize.ccms.biz.sitemanager.domain.Site;
import com.baize.ccms.biz.usermanager.domain.User;

/**
 * <p>标题: 样式</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 样式管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司</p>
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-6-5 上午10:29:38
 * @history（历次修订内容、修订人、修订时间等）
 */
public class TemplateUnitStyle implements Serializable {

	private static final long serialVersionUID = 2659640449017797662L;
	
	/** 唯一标识符 */
	private String id;
	
	/** 样式名称 */
	private String name;
	
	/** 样式内容 */
	private String content;
	
	/** 显示效果 */
	private String displayEffect;
	
	/** 对应单元类别 */
	private TemplateUnitCategory templateUnitCategory;
	
	/** 当前网站 */
	private Site site;
	
	/** 创建者 */
	private User creator;

	/** 创建时间 */
	private Date createTime;
	
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
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the displayEffect
	 */
	public String getDisplayEffect() {
		return displayEffect;
	}

	/**
	 * @param displayEffect the displayEffect to set
	 */
	public void setDisplayEffect(String displayEffect) {
		this.displayEffect = displayEffect;
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
	 * @return the templateUnitCategory
	 */
	public TemplateUnitCategory getTemplateUnitCategory() {
		return templateUnitCategory;
	}

	/**
	 * @param templateUnitCategory the templateUnitCategory to set
	 */
	public void setTemplateUnitCategory(TemplateUnitCategory templateUnitCategory) {
		this.templateUnitCategory = templateUnitCategory;
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

}
