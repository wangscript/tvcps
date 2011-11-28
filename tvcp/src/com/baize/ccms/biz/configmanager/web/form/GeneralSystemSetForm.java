/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.biz.configmanager.web.form;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.baize.ccms.biz.configmanager.domain.GeneralSystemSet;
import com.baize.common.core.web.GeneralForm;

/**
 * <p>标题: 系统安装程序表单</p>
 * <p>描述: 系统安装程序表单数据</p>
 * <p>模块: 系统安装程序</p>
 * <p>版权: Copyright (c) 2009南京百泽网络科技有限公司
 * @author 包坤涛
 * @version 1.0
 * @since 2009-9-04 下午03:15:06 
 * @history（历次修订内容、修订人、修订时间等） 
*/

public class GeneralSystemSetForm extends GeneralForm{
	
	private static final long serialVersionUID = -5662837453631312349L;
	private GeneralSystemSet generalSystemSet = new GeneralSystemSet();
    /**分隔符**/
	private String separator;
	
	private String authorId;
	/**类别ID*/
	private String categoryId;
	
	private String overDefault;
	
	private String siteName;
	  
	private int currPage;
	
	private int rowsPerPage;




	/* (non-Javadoc)
	 * @see com.baize.common.core.web.GeneralForm#validateData(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected void validateData(ActionMapping arg0, HttpServletRequest arg1) {
		// TODO Auto-generated method stub
		
	}

	public GeneralSystemSet getGeneralSystemSet() {
		return generalSystemSet;
	}
	public void setGeneralSystemSet(GeneralSystemSet generalSystemSet) {
		this.generalSystemSet = generalSystemSet;
	}
	
	public String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
   
	
	public String getOverDefault() {
		return overDefault;
	}

	public void setOverDefault(String overDefault) {
		this.overDefault = overDefault;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public int getRowsPerPage() {
		return rowsPerPage;
	}

	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}
 
	
}
