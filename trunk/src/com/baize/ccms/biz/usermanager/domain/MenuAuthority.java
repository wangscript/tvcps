/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.biz.usermanager.domain;

import java.io.Serializable;

/**
 * <p>标题: —— 菜单角色权限表</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 用户管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-10-19 下午07:04:41
 * @history（历次修订内容、修订人、修订时间等） 
 */

public class MenuAuthority  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 980641596021463918L;
	
	/** 唯一标识 */
	private String id;
	
	/** 角色对象 */
	private Role role;
	
	/** 菜单具体功能权限对象 */
	private MenuFunction menuFunction;

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
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * @return the menuFunction
	 */
	public MenuFunction getMenuFunction() {
		return menuFunction;
	}

	/**
	 * @param menuFunction the menuFunction to set
	 */
	public void setMenuFunction(MenuFunction menuFunction) {
		this.menuFunction = menuFunction;
	}
	

}
