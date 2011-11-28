/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.plugin.guestbookmanager.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>标题: —— 留言回复</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CCMS 留言本</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司</p>
 * <p>网址：http://www.baizeweb.com
 * @author 曹名科
 * @version 1.0
 * @since 2009-10-30 上午10:58:08
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class GuestBookRevert implements Serializable{
	private static final long serialVersionUID = 9214194095579339570L;
	/** 唯一标识 */
	private String id;
	/** 回复内容 */
	private String revertContent;
	/**回复时间*/
	private Date revertTime;
	/**留言内容对象*/
	private GuestBookContent guestBookContent;
	/**用于转换时间字段*/
	private String toParseDate;
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
	 * @return the revertContent
	 */
	public String getRevertContent() {
		return revertContent;
	}
	/**
	 * @param revertContent the revertContent to set
	 */
	public void setRevertContent(String revertContent) {
		this.revertContent = revertContent;
	}
	/**
	 * @return the revertTime
	 */
	public Date getRevertTime() {
		return revertTime;
	}
	/**
	 * @param revertTime the revertTime to set
	 */
	public void setRevertTime(Date revertTime) {
		this.revertTime = revertTime;
	}
	/**
	 * @return the guestBookContent
	 */
	public GuestBookContent getGuestBookContent() {
		return guestBookContent;
	}
	/**
	 * @param guestBookContent the guestBookContent to set
	 */
	public void setGuestBookContent(GuestBookContent guestBookContent) {
		this.guestBookContent = guestBookContent;
	}
	/**
	 * @return the toParseDate
	 */
	public String getToParseDate() {
		return toParseDate;
	}
	/**
	 * @param toParseDate the toParseDate to set
	 */
	public void setToParseDate(String toParseDate) {
		this.toParseDate = toParseDate;
	}

}
