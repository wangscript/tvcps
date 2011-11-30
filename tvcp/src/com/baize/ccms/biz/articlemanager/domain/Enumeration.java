/**
 * project：通用内容管理系统
 * Company:   
*/
package com.j2ee.cms.biz.articlemanager.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.j2ee.cms.biz.usermanager.domain.User;

/**
 * <p>标题: 枚举类型</p>
 * <p>描述: 用于丰富文章格式中字段属性的类型</p>
 * <p>模块: 文章管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-4-6 下午03:19:35
 * @history（历次修订内容、修订人、修订时间等）
 */
public class Enumeration implements Serializable {
	
	private static final long serialVersionUID = 1706949464880108339L;

	/** 唯一标识符 */
	private String id;
	
	/** 枚举名称 */
	private String name;
	
	/** 枚举值列表 */
	private List enumValues;
	
	/** 创建者 */
	private User creator;
	
	/** 创建时间 */
	private Date createTime = new Date();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public List getEnumValues() {
		return enumValues;
	}

	public void setEnumValues(List enumValues) {
		this.enumValues = enumValues;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
