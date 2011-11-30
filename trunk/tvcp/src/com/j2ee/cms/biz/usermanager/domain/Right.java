/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.usermanager.domain;

import java.io.Serializable;
/**
 * 
 * <p>标题: 权力</p>
 * <p>描述: 定义用户直接拥有的权限，属于对角色的细分</p>
 * <p>模块: 用户管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-10-19 下午07:47:50
 * @history（历次修订内容、修订人、修订时间等）
 */
public class Right implements Serializable {

	private static final long serialVersionUID = 6291935610760976354L;

	/** 唯一标识 */
	private String id;
	
	/** 用户 */
	private User user;
	
	/** 权限 */
	private Authority authority;
	
	public Right() {
		// null
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the authority
	 */
	public Authority getAuthority() {
		return authority;
	}

	/**
	 * @param authority the authority to set
	 */
	public void setAuthority(Authority authority) {
		this.authority = authority;
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
}
