/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.plugin.guestbookmanager.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>标题: —— 留言内容</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CCMS</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司</p>
 * <p>网址：http://www.baizeweb.com
 * @author 曹名科
 * @version 1.0
 * @since 2009-10-30 上午11:03:10
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class GuestBookContent implements Serializable{
	private static final long serialVersionUID = 3152467560469538910L;
	/**主键ID*/ 
	private String id;
	/**留言标题*/
	private String title;
	/**留言内容*/
	private String bookContent;
	/**email*/
	private String email;
	/**真实姓名*/
	private String bookName;
	/**联系电话*/
	private String phone;
	/**审核状态 已审核1，未审核0，不处理2，不需要审核3*/
	private String auditStatus;
	/**回复状态  已回复状态1和未回复0*/
	private String replyStatus;
	/**所属地区*/
	private String area;
	/**联系地址*/
	private String address;
	/**ip*/
	private String ip;
	/**用户ID*/
	private String userId;
	/**创建时间*/
	private Date createTime; 
	/**类别对象*/
	private GuestBookCategory guestBookCategory;
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
	 * @return the bookContent
	 */
	public String getBookContent() {
		return bookContent;
	}
	/**
	 * @param bookContent the bookContent to set
	 */
	public void setBookContent(String bookContent) {
		this.bookContent = bookContent;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the bookName
	 */
	public String getBookName() {
		return bookName;
	}
	/**
	 * @param bookName the bookName to set
	 */
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return the auditStatus
	 */
	public String getAuditStatus() {
		return auditStatus;
	}
	/**
	 * @param auditStatus the auditStatus to set
	 */
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	/**
	 * @return the replyStatus
	 */
	public String getReplyStatus() {
		return replyStatus;
	}
	/**
	 * @param replyStatus the replyStatus to set
	 */
	public void setReplyStatus(String replyStatus) {
		this.replyStatus = replyStatus;
	}
	/**
	 * @return the area
	 */
	public String getArea() {
		return area;
	}
	/**
	 * @param area the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the guestBookCategory
	 */
	public GuestBookCategory getGuestBookCategory() {
		return guestBookCategory;
	}
	/**
	 * @param guestBookCategory the guestBookCategory to set
	 */
	public void setGuestBookCategory(GuestBookCategory guestBookCategory) {
		this.guestBookCategory = guestBookCategory;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
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