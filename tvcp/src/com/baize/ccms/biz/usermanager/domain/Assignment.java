/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.usermanager.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>标题: 分配</p>
 * <p>描述: 将角色分配给用户，一个用户对应多个分配</p>
 * <p>模块: 通用平台</p>
 * <p>版权: Copyright (c) 2009  
 * @author 杨信
 * @version 1.0
 * @since 2009-2-23 下午01:31:46
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class Assignment implements Serializable {

	private static final long serialVersionUID = -1619967576957607146L;

	/** 唯一标识 */
	private String id;
	
	/** 用户 */
	private User user;
	
	/** 角色 */
	private Role role;
	
	/** 操作人 */
	private User operator;
	
	/** 操作时间 */
	private Date operateTime;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public User getOperator() {
		return operator;
	}

	public void setOperator(User operator) {
		this.operator = operator;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
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
