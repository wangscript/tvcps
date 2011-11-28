/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.templatemanager.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.baize.ccms.biz.templatemanager.domain.TemplateCategory;
import com.baize.common.core.web.GeneralForm;

/**
 * <p>标题: 模板类别表单</p>
 * <p>描述: 模板类别的表单数据，以便页面和方法中调</p>
 * <p>模块: 模板管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 郑荣华
 * @version 1.0
 * @since 2009-4-28 上午09:49:04
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class TemplateCategoryForm extends GeneralForm {

	private static final long serialVersionUID = -9065695084621163177L;
	
	/** 创建一个模板类别实例 **/
	private TemplateCategory templateCategory = new TemplateCategory();
	/** 网站id **/
	private String siteid;
	/** 用户id **/
	private String creatorid;
	
	/**
	 * @return the siteid
	 */
	
	public String getSiteid() {
		return siteid;
	}

	/**
	 * @param siteid the siteid to set
	 */
	public void setSiteid(String siteid) {
		this.siteid = siteid;
	}

	/**
	 * @return the creatorid
	 */
	public String getCreatorid() {
		return creatorid;
	}

	/**
	 * @param creatorid the creatorid to set
	 */
	public void setCreatorid(String creatorid) {
		this.creatorid = creatorid;
	}

	/**
	 * @return the tempalteCategory
	 */
	public TemplateCategory getTemplateCategory() {
		return templateCategory;
	}

	/**
	 * @param tempalteCategory the tempalteCategory to set
	 */
	public void setTemplateCategory(TemplateCategory templateCategory) {
		this.templateCategory = templateCategory;
	}

	@Override
	protected void validateData(ActionMapping arg0, HttpServletRequest arg1) {
	}

}
