/**
 * project：通用内容管理系统
 * Company: 南京瀚沃信息科技有限责任公司
 */
package com.baize.ccms.biz.publishmanager.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.baize.common.core.web.GeneralForm;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 发布管理</p>
 * <p>版权: Copyright (c) 2009 南京瀚沃信息科技有限责任公司</p>
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-6-18 上午10:35:43
 * @history（历次修订内容、修订人、修订时间等）
 */
public class PublishForm extends GeneralForm {

	private static final long serialVersionUID = -8066063383789354734L;

	/** 需要发布的栏目 */
	private String columnIds;
	
	/** 需要发布的类型 （column:栏目页,article:文章页）*/
	private String publishType;
	
	/** 是否整站发布*/
	private String publishAll;
	
	/** 是否发布模板 */
	private boolean publishTemplate;
	
	@Override
	protected void validateData(ActionMapping mapping,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @return the columnIds
	 */
	public String getColumnIds() {
		return columnIds;
	}

	/**
	 * @param columnIds the columnIds to set
	 */
	public void setColumnIds(String columnIds) {
		this.columnIds = columnIds;
	}

	/**
	 * @return the publishType
	 */
	public String getPublishType() {
		return publishType;
	}

	/**
	 * @param publishType the publishType to set
	 */
	public void setPublishType(String publishType) {
		this.publishType = publishType;
	}

	/**
	 * @return the publishAll
	 */
	public String getPublishAll() {
		return publishAll;
	}

	/**
	 * @param publishAll the publishAll to set
	 */
	public void setPublishAll(String publishAll) {
		this.publishAll = publishAll;
	}

	public boolean isPublishTemplate() {
		return publishTemplate;
	}

	public void setPublishTemplate(boolean publishTemplate) {
		this.publishTemplate = publishTemplate;
	}

}
