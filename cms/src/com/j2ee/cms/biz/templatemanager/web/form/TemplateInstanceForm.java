/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.templatemanager.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.j2ee.cms.biz.templatemanager.domain.TemplateInstance;
import com.j2ee.cms.common.core.web.GeneralForm;

/**
 * <p>标题: 模板实例表单</p>
 * <p>描述: 模板实例表单的一些属性</p>
 * <p>模块: 模板管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-4-9 下午04:14:02
 * @history（历次修订内容、修订人、修订时间等）
 */
public class TemplateInstanceForm extends GeneralForm {

	private static final long serialVersionUID = -7469126947934978839L;
   
	/** 模板实例 **/
	private TemplateInstance templateInstance = new TemplateInstance();
	
	/** 模板id **/
	private String templateId;
	
	/** 模板实例id **/
	private String templateInstanceId;
	
	/** 模板实例的id和被调用次数组成的字符串 **/
	private String idAndUsedNum;
	
	/** 模版实例名称 */
	private String templateInstanceName;
	
	/** 是否是在模版设置时添加模版实例 */
	private String templateSet;
	
	/** 判断模版实例绑定的是哪个栏目 */
	private String bind;
	
	/** 被绑定的栏目ids（被绑定的栏目或者被绑定的文章的栏目） */
	private String bindedIds;
	
	/** 要被取消的绑定的栏目 */
	private String canceledIds;
	/**
	 * @return the idAndUsedNum
	 */
	public String getIdAndUsedNum() {
		return idAndUsedNum;
	}

	/**
	 * @param idAndUsedNum the idAndUsedNum to set
	 */
	public void setIdAndUsedNum(String idAndUsedNum) {
		this.idAndUsedNum = idAndUsedNum;
	}

	/**
	 * @return the templateInstanceId
	 */
	public String getTemplateInstanceId() {
		return templateInstanceId;
	}

	/**
	 * @param templateInstanceId the templateInstanceId to set
	 */
	public void setTemplateInstanceId(String templateInstanceId) {
		this.templateInstanceId = templateInstanceId;
	}

	/**
	 * @return the templateId
	 */
	public String getTemplateId() {
		return templateId;
	}

	/**
	 * @param templateId the templateId to set
	 */
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	/**
	 * @return the templateInstance
	 */
	public TemplateInstance getTemplateInstance() {
		return templateInstance;
	}
	
	/**
	 * @param templateInstance the templateInstance to set
	 */
	public void setTemplateInstance(TemplateInstance templateInstance) {
		this.templateInstance = templateInstance;
	}
	
	@Override
	protected void validateData(ActionMapping mapping,
			HttpServletRequest request) {
		
	}

	/**
	 * @return the templateInstanceName
	 */
	public String getTemplateInstanceName() {
		return templateInstanceName;
	}

	/**
	 * @param templateInstanceName the templateInstanceName to set
	 */
	public void setTemplateInstanceName(String templateInstanceName) {
		this.templateInstanceName = templateInstanceName;
	}

	/**
	 * @return the templateSet
	 */
	public String getTemplateSet() {
		return templateSet;
	}

	/**
	 * @param templateSet the templateSet to set
	 */
	public void setTemplateSet(String templateSet) {
		this.templateSet = templateSet;
	}

	/**
	 * @return the bind
	 */
	public String getBind() {
		return bind;
	}

	/**
	 * @param bind the bind to set
	 */
	public void setBind(String bind) {
		this.bind = bind;
	}

	/**
	 * @return the bindedIds
	 */
	public String getBindedIds() {
		return bindedIds;
	}

	/**
	 * @param bindedIds the bindedIds to set
	 */
	public void setBindedIds(String bindedIds) {
		this.bindedIds = bindedIds;
	}

	/**
	 * @return the canceledIds
	 */
	public String getCanceledIds() {
		return canceledIds;
	}

	/**
	 * @param canceledIds the canceledIds to set
	 */
	public void setCanceledIds(String canceledIds) {
		this.canceledIds = canceledIds;
	}

}
