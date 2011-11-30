/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.configmanager.web.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.j2ee.cms.common.core.web.GeneralForm;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CPS</p>
 * <p>版权: Copyright (c) 2009  
 * @author 郑荣华
 * @version 1.0
 * @since 2009-9-5 上午09:58:02
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class SystemLogForm extends GeneralForm {

	private static final long serialVersionUID = -6714913980149050072L;

	/** 要删除的日志id */
	private String deletedLogIds;
	
	/** 关键字 */
	private String keyWords;
	
	/** 范围 */
	private String extent;
	
	/** 开始时间 */
	private String startTime;
	
	/** 结束时间 */
	private String endTime;
	
	@Override
	protected void validateData(ActionMapping arg0, HttpServletRequest arg1) {

	}

	/**
	 * @return the deletedLogIds
	 */
	public String getDeletedLogIds() {
		return deletedLogIds;
	}

	/**
	 * @param deletedLogIds the deletedLogIds to set
	 */
	public void setDeletedLogIds(String deletedLogIds) {
		this.deletedLogIds = deletedLogIds;
	}

	/**
	 * @return the keyWords
	 */
	public String getKeyWords() {
		return keyWords;
	}

	/**
	 * @param keyWords the keyWords to set
	 */
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	/**
	 * @return the extent
	 */
	public String getExtent() {
		return extent;
	}

	/**
	 * @param extent the extent to set
	 */
	public void setExtent(String extent) {
		this.extent = extent;
	}

	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}
