/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.usermanager.domain;

import java.io.Serializable;

/**
 * <p>标题: —— 菜单具体功能表</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 用户管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-10-19 下午07:06:09
 * @history（历次修订内容、修订人、修订时间等） 
 */

public class MenuFunction  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6491135194758817991L;
	
	/** 唯一标识 */
	private String id;
	
	/** 具体功能名称 */
	private String functionName;
	
	/** 菜单对象 */
	private Menu menu;

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
	 * @return the functionName
	 */
	public String getFunctionName() {
		return functionName;
	}

	/**
	 * @param functionName the functionName to set
	 */
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	/**
	 * @return the menu
	 */
	public Menu getMenu() {
		return menu;
	}

	/**
	 * @param menu the menu to set
	 */
	public void setMenu(Menu menu) {
		this.menu = menu;
	}

}
