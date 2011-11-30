/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.configmanager.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.j2ee.cms.biz.configmanager.domain.FilterCategory;
import com.j2ee.cms.common.core.web.GeneralForm;

/**
 * <p>标题: 系统安装程序表单</p>
 * <p>描述: 系统安装程序表单数据</p>
 * <p>模块: 系统安装程序</p>
 * <p>版权: Copyright (c) 2009 
 * @author 包坤涛
 * @version 1.0
 * @since 2009-9-04 下午03:15:06 
 * @history（历次修订内容、修订人、修订时间等） 
*/

public class FilterCategoryForm extends GeneralForm{
	
	private static final long serialVersionUID = -5662847453233312349L;
	private FilterCategory filterCategory = new FilterCategory();
	
	
	public FilterCategory getFilterCategory() {
		return filterCategory;
	}


	public void setFilterCategory(FilterCategory filterCategory) {
		this.filterCategory = filterCategory;
	}


	@Override
	protected void validateData(ActionMapping arg0, HttpServletRequest arg1) {
		// TODO Auto-generated method stub
		
	}
  
 
 
	
}
