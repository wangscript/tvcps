/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.plugin.guestbookmanager.domain;

import java.io.Serializable;

import com.j2ee.cms.biz.usermanager.domain.User;

/**
 * <p>标题: —— 留言本分发权限管理</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CPS</p>
 * <p>版权: Copyright (c) 2009  </p>
 * <p>网址：http://www.j2ee.cmsweb.com
 * @author 曹名科
 * @version 1.0
 * @since 2009-10-30 上午11:13:00
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class GuestBookAutority implements Serializable{
	private static final long serialVersionUID = -7240162333427932311L;
	private String id;
	private User users;
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
	 * @return the users
	 */
	public User getUsers() {
		return users;
	}
	/**
	 * @param users the users to set
	 */
	public void setUsers(User users) {
		this.users = users;
	}
}
