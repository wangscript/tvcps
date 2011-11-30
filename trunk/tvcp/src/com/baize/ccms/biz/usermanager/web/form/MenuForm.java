/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.usermanager.web.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.j2ee.cms.biz.usermanager.domain.Menu;
import com.j2ee.cms.common.core.web.GeneralForm;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CPS</p>
 * <p>版权: Copyright (c) 2009  
 * @author 郑荣华
 * @version 1.0
 * @since 2009-5-18 下午03:44:13
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class MenuForm extends GeneralForm {

	private static final long serialVersionUID = 248388440885096403L;
	private String idss;
	public String getIdss() {
		return idss;
	}

	public void setIdss(String idss) {
		this.idss = idss;
	}

	/** 排序后的菜单ids **/
	private String ordersMenu;
	/** 菜单列表 **/
	private List<Menu> list = new ArrayList<Menu>();
	/**
	 * @return the list
	 */
	public List<Menu> getList() {
		return list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(List<Menu> list) {
		this.list = list;
	}

	/**
	 * @return the ordersMenu
	 */
	public String getOrdersMenu() {
		return ordersMenu;
	}

	/**
	 * @param ordersMenu the ordersMenu to set
	 */
	public void setOrdersMenu(String ordersMenu) {
		this.ordersMenu = ordersMenu;
	}

	@Override
	protected void validateData(ActionMapping arg0, HttpServletRequest arg1) {

	}

}
