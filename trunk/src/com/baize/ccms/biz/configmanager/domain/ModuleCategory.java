/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.configmanager.domain;

import java.io.Serializable;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CCMS</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 郑荣华
 * @version 1.0
 * @since 2009-9-5 上午09:13:28
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class ModuleCategory implements Serializable {

	private static final long serialVersionUID = 5696403673331375086L;
	
	/** 唯一标识符 */
	private String id;
	
	/** 模块类别名称 */
	private String name;
	
	/** 操作状态 */
	private boolean status;

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
	 * @return the categoryName
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param categoryName the categoryName to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the status
	 */
	public boolean isStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}
	



}
