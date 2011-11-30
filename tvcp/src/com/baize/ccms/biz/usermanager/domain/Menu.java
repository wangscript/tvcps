  /**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.usermanager.domain;

import java.io.Serializable;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 通用平台</p>
 * <p>版权: Copyright (c) 2009  
 * @author 杨信
 * @version 1.0
 * @since 2009-2-16 上午09:21:45
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class Menu implements Serializable {
	
	private static final long serialVersionUID = -1728048433112912564L;

	/** 唯一标识 */
	private String id;
	
	/** 菜单唯一标识号，用于区分不同菜单 */
	private String identifier;
	
	/** 菜单名称 */
	private String name;
	
	/** 描述 */
	private String description;
	
	/** 菜单对应的首页，该页包含树和右侧页面 */
	private String indexPage;
	
	/** 右侧内容页面 */
	private String contentPage;
	
	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;

	}


	public void setDescription(String description) {
		this.description = description;

	}

	/**
	 * @return the indexPage
	 */
	public String getIndexPage() {
		return indexPage;
	}

	/**
	 * @param indexPage the indexPage to set
	 */
	public void setIndexPage(String indexPage) {
		this.indexPage = indexPage;
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

	public String getContentPage() {
		return contentPage;
	}

	public void setContentPage(String contentPage) {
		this.contentPage = contentPage;
	}

}
