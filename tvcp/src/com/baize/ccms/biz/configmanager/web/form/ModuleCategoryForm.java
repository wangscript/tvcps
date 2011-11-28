/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.configmanager.web.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.baize.ccms.biz.configmanager.domain.ModuleCategory;
import com.baize.common.core.web.GeneralForm;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CCMS</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 郑荣华
 * @version 1.0
 * @since 2009-9-5 上午09:55:54
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class ModuleCategoryForm extends GeneralForm {

	private static final long serialVersionUID = -6329306863738883335L;
	
	/** 要记录日志的模块 */
	List<ModuleCategory> selectedList = new ArrayList<ModuleCategory>();
	
	/** 不要记录日志的模块*/
	List<ModuleCategory> notSelectedList = new ArrayList<ModuleCategory>();
	
	/** 要记录的模块类别ids */
	public String selectedIds;
	
	/** 不要记录的模块类别ids */
	public String notSelectIds;
	
	@Override
	protected void validateData(ActionMapping arg0, HttpServletRequest arg1) {

	}

	/**
	 * @return the selectedList
	 */
	public List<ModuleCategory> getSelectedList() {
		return selectedList;
	}



	/**
	 * @param selectedList the selectedList to set
	 */
	public void setSelectedList(List<ModuleCategory> selectedList) {
		this.selectedList = selectedList;
	}



	/**
	 * @return the notSelectedList
	 */
	public List<ModuleCategory> getNotSelectedList() {
		return notSelectedList;
	}



	/**
	 * @param notSelectedList the notSelectedList to set
	 */
	public void setNotSelectedList(List<ModuleCategory> notSelectedList) {
		this.notSelectedList = notSelectedList;
	}

	/**
	 * @return the selectedIds
	 */
	public String getSelectedIds() {
		return selectedIds;
	}

	/**
	 * @param selectedIds the selectedIds to set
	 */
	public void setSelectedIds(String selectedIds) {
		this.selectedIds = selectedIds;
	}

	/**
	 * @return the notSelectIds
	 */
	public String getNotSelectIds() {
		return notSelectIds;
	}

	/**
	 * @param notSelectIds the notSelectIds to set
	 */
	public void setNotSelectIds(String notSelectIds) {
		this.notSelectIds = notSelectIds;
	}

}
