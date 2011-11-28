/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.plugin.onlineBulletin.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.baize.ccms.plugin.onlineBulletin.domain.OnlineBulletin;
import com.baize.common.core.web.GeneralForm;

/**
 * <p>标题: 网上调查</p>
 * <p>描述: 网上调查，以便页面和方法中调用</p>
 * <p>模块: 网上调查</p>
 * <p>版权: Copyright (c) 2009南京百泽网络科技有限公司
 * @author 包坤涛
 * @version 1.0
 * @since 2009-6-14 下午02:35:25 
 * @history（历次修订内容、修订人、修订时间等） 
*/
public class OnlineBulletinForm extends GeneralForm {
	
	private static final long serialVersionUID = -5632827958636915339L;
	/**网上公告*/
	private OnlineBulletin onlineBulletin = new OnlineBulletin();
	/**网上公告用于删除*/
	private String  id;
	/** 截止日期（字符串形式）*/
	private String endTime;
	/** 首页 */
	private String homePage;
	/** 绑定的公告id */
	private String bulletinId;
	/** 公告标题 */
	private String title;
	/** 公告内容 */
	private String content;
	/** 弹出窗口名称*/
	private String winName;
	
	/**验证方法*/
	@Override
	protected void validateData(ActionMapping arg0, HttpServletRequest arg1) {
	}

		
	public OnlineBulletin getOnlineBulletin() {
		return onlineBulletin;
	}

	public void setOnlineBulletin(OnlineBulletin onlineBulletin) {
		this.onlineBulletin = onlineBulletin;
	}		
		
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


	/**
	 * @return the homePage
	 */
	public String getHomePage() {
		return homePage;
	}


	/**
	 * @param homePage the homePage to set
	 */
	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}


	/**
	 * @return the bulletinId
	 */
	public String getBulletinId() {
		return bulletinId;
	}


	/**
	 * @param bulletinId the bulletinId to set
	 */
	public void setBulletinId(String bulletinId) {
		this.bulletinId = bulletinId;
	}


	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}


	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}


	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}


	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}


	/**
	 * @return the winName
	 */
	public String getWinName() {
		return winName;
	}


	/**
	 * @param winName the winName to set
	 */
	public void setWinName(String winName) {
		this.winName = winName;
	}
	



}
