/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.configmanager.domain;

import java.io.Serializable;
import java.util.Date;

import com.baize.ccms.biz.sitemanager.domain.Site;
import com.baize.ccms.biz.usermanager.domain.User;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CCMS</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 郑荣华
 * @version 1.0
 * @since 2009-9-5 上午09:17:13
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class SystemLog implements Serializable {

	private static final long serialVersionUID = 9067404638687758858L;
	
	/** 唯一标识符 */
	private String id;
	
	/** 模块类别id */
	private ModuleCategory moduleCategory;
	
	/** 操作者 */
	private User operator;
	
	/** 操作时间 */
	private Date operationTime;
	
	/** 用户 IP */
	private String ip;

	/** 操作内容 */
	private String operationContent;
	
	
	
	/** 当前网站 */ 
	private Site site;

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
	 * @return the moduleCategory
	 */
	public ModuleCategory getModuleCategory() {
		return moduleCategory;
	}

	/**
	 * @param moduleCategory the moduleCategory to set
	 */
	public void setModuleCategory(ModuleCategory moduleCategory) {
		this.moduleCategory = moduleCategory;
	}

	/**
	 * @return the operator
	 */
	public User getOperator() {
		return operator;
	}

	/**
	 * @param operator the operator to set
	 */
	public void setOperator(User operator) {
		this.operator = operator;
	}

	/**
	 * @return the operationTime
	 */
	public Date getOperationTime() {
		return operationTime;
	}

	/**
	 * @param operationTime the operationTime to set
	 */
	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}

	/**
	 * @return the iP
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip the iP to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return the operationContent
	 */
	public String getOperationContent() {
		return operationContent;
	}

	/**
	 * @param operationContent the operationContent to set
	 */
	public void setOperationContent(String operationContent) {
		this.operationContent = operationContent;
	}

	/**
	 * @return the site
	 */
	public Site getSite() {
		return site;
	}

	/**
	 * @param site the site to set
	 */
	public void setSite(Site site) {
		this.site = site;
	}
}
