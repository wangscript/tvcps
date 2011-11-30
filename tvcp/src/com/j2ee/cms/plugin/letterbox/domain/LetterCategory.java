/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.plugin.letterbox.domain;

import java.io.Serializable;

/**
 * <p>标题: 信件类别</p>
 * <p>描述: 信件类别标示信件属于哪种类型</p>
 * <p>模块: 信件箱</p>
 * <p>版权: Copyright (c) 2009  </p>
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-6-13 下午04:26:59
 * @history（历次修订内容、修订人、修订时间等）
 */
public class LetterCategory implements Serializable {
	
	private static final long serialVersionUID = 4987660630334303002L;
	
	/** 唯一标识 */
	private String id;
	
	/** 类别名称 */
	private String name;

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
