/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.usermanager.domain;

import java.io.Serializable;

/**
 * <p>标题: 操作</p>
 * <p>描述: 对资源的操作，对资源是多对多，对权限是一对多</p>
 * <p>模块: 通用平台</p>
 * <p>版权: Copyright (c) 2009  
 * @author 杨信
 * @version 1.0
 * @since 2009-2-23 下午02:03:23
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class Operation implements Serializable {
	
	private static final long serialVersionUID = 4113478337319818200L;
	
	/** 栏目类型 */
	public static final String TYPE_COLUMN = "0";
	
	/** 文章类型 */
	public static final String TYPE_ARTICLE = "1";
	
	/** 模板设置类型 */
	public static final String TYPE_TEMPLATESET = "2";
	
	/** 无任何操作类型 */
	public static final String TYPE_COLUMN_NONE = "9";
 

	
	/** 唯一标识符 */
	private String id;
	
	/** 操作名称 */
	private String name;
	
	/** 操作类型 */
	private String types;
	
	/** 描述 */
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	 * @return the types
	 */
	public String getTypes() {
		return types;
	}

	/**
	 * @param types the types to set
	 */
	public void setTypes(String types) {
		this.types = types;
	}


}