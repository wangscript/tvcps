/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.plugin.letterbox.web.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.baize.ccms.plugin.letterbox.domain.LetterCategory;
import com.baize.common.core.web.GeneralForm;

/**
 * <p>标题: 信件类别表单</p>
 * <p>描述: 信件类别的表单数据，以便页面和方法中调用</p>
 * <p>模块: 信件箱</p>
 * <p>版权: Copyright (c) 2009南京百泽网络科技有限公司
 * @author 杨信
 * @version 1.0
 * @since 2009-6-13 下午04:03:37 
 * @history（历次修订内容、修订人、修订时间等） 
*/

public class LetterCategoryForm extends GeneralForm {

	private static final long serialVersionUID = -5662807958631912339L;
	private LetterCategory letterCategory = new LetterCategory();
	
	/** 类别id**/
	private String id;
	
	/** 类别名称**/
	private String name;
	
	/** 机构名称**/
	private String orgName;

	/** 机构id**/
	private String orgId;
	
	/** 跳转的标记**/
	private int flag;
	
	/** 所有信件名称字符串**/
	private String categoryName;
	
	/** 信件类别id**/
	private String categoryId;
	
	/** 弹出消息**/
	private String message;
	
	/** 所有信件类别的集合 */
	private List<LetterCategory> list = new ArrayList<LetterCategory>();
	
	@Override
	protected void validateData(ActionMapping arg0, HttpServletRequest arg1) {
		// TODO Auto-generated method stub
		
	}


	/**
	 * @param letterCategory the letterCategory to set
	 */
	public void setLetterCategory(LetterCategory letterCategory) {
		this.letterCategory = letterCategory;
	}


	/**
	 * @return the letterCategory
	 */
	public LetterCategory getLetterCategory() {
		return letterCategory;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
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


	/**
	 * @param list the list to set
	 */
	public void setList(List<LetterCategory> list) {
		this.list = list;
	}


	/**
	 * @return the list
	 */
	public List<LetterCategory> getList() {
		return list;
	}


	/**
	 * @param orgName the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}


	/**
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}


	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}


	/**
	 * @return the orgId
	 */
	public String getOrgId() {
		return orgId;
	}


	/**
	 * @param flag the flag to set
	 */
	public void setFlag(int flag) {
		this.flag = flag;
	}


	/**
	 * @return the flag
	 */
	public int getFlag() {
		return flag;
	}


	/**
	 * @param categoryName the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}


	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}


	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}


	/**
	 * @return the categoryId
	 */
	public String getCategoryId() {
		return categoryId;
	}


	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	
}
