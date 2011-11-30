/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.plugin.guestbookmanager.web.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;

import com.j2ee.cms.plugin.guestbookmanager.domain.GuestBookCategory;
import com.j2ee.cms.plugin.guestbookmanager.domain.GuestBookContent;
import com.j2ee.cms.plugin.guestbookmanager.domain.GuestBookRevert;
import com.j2ee.cms.plugin.guestbookmanager.domain.GuestBookSensitiveWord;
import com.j2ee.cms.common.core.web.GeneralForm;

/**
 * 
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CPS</p>
 * <p>版权: Copyright (c) 2009  </p>
 * <p>网址：http://www.j2ee.cmsweb.com
 * @author 曹名科
 * @version 1.0
 * @since 2009-11-2 下午02:56:47
 * @history（历次修订内容、修订人、修订时间等）
 */

public class GuestBookForm extends GeneralForm {
	private static final long serialVersionUID = -1795394568545326506L;
	/**过滤词对象*/
	private GuestBookSensitiveWord word = new GuestBookSensitiveWord();
	/**开放类型*/
	private String openType;
	/**是否审核*/
	private String isAudit;
	/**有敏感词是是否发布*/
	private String isPublish;
	/**存放从数据库中查询的敏感词*/
	private String sensitiveword;
	/**定时发布的时间*/
	private String openHour;
	private String openMinute;
	private String openHour1;
	private String openMinute1;
	/**留言数量*/
	private String leaveCount;
	/**留言达到数量的提示*/
	private String leaveMsg;
	/**样式内容*/
	private String styleContent;
	/**存储类别名称*/
	private String categoryName;
	/**类别对象*/
	private GuestBookCategory guestCategory = new GuestBookCategory();
	/**留言对象*/
	private GuestBookContent  guestContent = new GuestBookContent();
	/**留言回复对象*/
	private GuestBookRevert reply = new GuestBookRevert();
	/**回复留言内容*/
	private String replayContent;
	/**留言回复时间*/
	private String repalyCreateTime;
	/**分发ID，当分发时将ID保存在页面之间传递*/
	private String dispenseId;
	/**存储权限*/
	private String isPopedom;
	/**留言类别*/
	private String guestBookCategory;
	/**前台用于存储siteId*/
	private String sites;
	/**前台用于存储实际数据库中的留言数量*/
	private String factCount;
	/**验证码*/
	private String rand;
	/**当验证码错误时的提示消息*/
	private String mess;
	/**类别列表*/
	private List<GuestBookCategory> bookCategory = new ArrayList<GuestBookCategory>();
	/**搜索条件开始时间	 */
	private String starTime;
	/**搜索条件结束时间*/
	private String endTime;
	/**关键字*/
	private String keyword;
	/**搜索选项*/
	private String condition;
	/**
	 * @return the replayContent
	 */
	public String getReplayContent() {
		return replayContent;
	}

	/**
	 * @return the bookCategory
	 */
	public List<GuestBookCategory> getBookCategory() {
		return bookCategory;
	}

	/**
	 * @param bookCategory the bookCategory to set
	 */
	public void setBookCategory(List<GuestBookCategory> bookCategory) {
		this.bookCategory = bookCategory;
	}

	/**
	 * @param replayContent the replayContent to set
	 */
	public void setReplayContent(String replayContent) {
		this.replayContent = replayContent;
	}

	/**
	 * @return the openType
	 */
	public String getOpenType() {
		return openType;
	}

	/**
	 * @param openType the openType to set
	 */
	public void setOpenType(String openType) {
		this.openType = openType;
	}

	/**
	 * @return the isAudit
	 */
	public String getIsAudit() {
		return isAudit;
	}

	/**
	 * @param isAudit the isAudit to set
	 */
	public void setIsAudit(String isAudit) {
		this.isAudit = isAudit;
	}

	/**
	 * @return the isPublish
	 */
	public String getIsPublish() {
		return isPublish;
	}

	/**
	 * @param isPublish the isPublish to set
	 */
	public void setIsPublish(String isPublish) {
		this.isPublish = isPublish;
	}

	/**
	 * @param word the word to set
	 */
	public void setWord(GuestBookSensitiveWord word) {
		this.word = word;
	}

	/**
	 * @return the word
	 */
	public GuestBookSensitiveWord getWord() {
		return word;
	}

	@Override
	protected void validateData(ActionMapping arg0, HttpServletRequest arg1) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @return the openHour
	 */
	public String getOpenHour() {
		return openHour;
	}

	/**
	 * @param openHour the openHour to set
	 */
	public void setOpenHour(String openHour) {
		this.openHour = openHour;
	}

	/**
	 * @return the openMinute
	 */
	public String getOpenMinute() {
		return openMinute;
	}

	/**
	 * @param openMinute the openMinute to set
	 */
	public void setOpenMinute(String openMinute) {
		this.openMinute = openMinute;
	}

	/**
	 * @return the openHour1
	 */
	public String getOpenHour1() {
		return openHour1;
	}

	/**
	 * @param openHour1 the openHour1 to set
	 */
	public void setOpenHour1(String openHour1) {
		this.openHour1 = openHour1;
	}

	/**
	 * @return the openMinute1
	 */
	public String getOpenMinute1() {
		return openMinute1;
	}

	/**
	 * @param openMinute1 the openMinute1 to set
	 */
	public void setOpenMinute1(String openMinute1) {
		this.openMinute1 = openMinute1;
	}

	/**
	 * @return the leaveCount
	 */
	public String getLeaveCount() {
		return leaveCount;
	}

	/**
	 * @param leaveCount the leaveCount to set
	 */
	public void setLeaveCount(String leaveCount) {
		this.leaveCount = leaveCount;
	}

	/**
	 * @return the leaveMsg
	 */
	public String getLeaveMsg() {
		return leaveMsg;
	}

	/**
	 * @param leaveMsg the leaveMsg to set
	 */
	public void setLeaveMsg(String leaveMsg) {
		this.leaveMsg = leaveMsg;
	}

	/**
	 * @return the styleContent
	 */
	public String getStyleContent() {
		return styleContent;
	}

	/**
	 * @param styleContent the styleContent to set
	 */
	public void setStyleContent(String styleContent) {
		this.styleContent = styleContent;
	}

	/**
	 * @return the guestCategory
	 */
	public GuestBookCategory getGuestCategory() {
		return guestCategory;
	}

	/**
	 * @param guestCategory the guestCategory to set
	 */
	public void setGuestCategory(GuestBookCategory guestCategory) {
		this.guestCategory = guestCategory;
	}

	/**
	 * @return the guestContent
	 */
	public GuestBookContent getGuestContent() {
		return guestContent;
	}

	/**
	 * @param guestContent the guestContent to set
	 */
	public void setGuestContent(GuestBookContent guestContent) {
		this.guestContent = guestContent;
	}

	/**
	 * @return the dispenseId
	 */
	public String getDispenseId() {
		return dispenseId;
	}

	/**
	 * @param dispenseId the dispenseId to set
	 */
	public void setDispenseId(String dispenseId) {
		this.dispenseId = dispenseId;
	}

	/**
	 * @return the isPopedom
	 */
	public String getIsPopedom() {
		return isPopedom;
	}

	/**
	 * @param isPopedom the isPopedom to set
	 */
	public void setIsPopedom(String isPopedom) {
		this.isPopedom = isPopedom;
	}

	/**
	 * @return the repalyCreateTime
	 */
	public String getRepalyCreateTime() {
		return repalyCreateTime;
	}

	/**
	 * @param repalyCreateTime the repalyCreateTime to set
	 */
	public void setRepalyCreateTime(String repalyCreateTime) {
		this.repalyCreateTime = repalyCreateTime;
	}

	/**
	 * @return the sites
	 */
	public String getSites() {
		return sites;
	}

	/**
	 * @param sites the sites to set
	 */
	public void setSites(String sites) {
		this.sites = sites;
	}

	/**
	 * @return the guestBookCategory
	 */
	public String getGuestBookCategory() {
		return guestBookCategory;
	}

	/**
	 * @param guestBookCategory the guestBookCategory to set
	 */
	public void setGuestBookCategory(String guestBookCategory) {
		this.guestBookCategory = guestBookCategory;
	}

	/**
	 * @return the factCount
	 */
	public String getFactCount() {
		return factCount;
	}

	/**
	 * @param factCount the factCount to set
	 */
	public void setFactCount(String factCount) {
		this.factCount = factCount;
	}

	/**
	 * @return the rand
	 */
	public String getRand() {
		return rand;
	}

	/**
	 * @param rand the rand to set
	 */
	public void setRand(String rand) {
		this.rand = rand;
	}

	/**
	 * @return the mess
	 */
	public String getMess() {
		return mess;
	}

	/**
	 * @param mess the mess to set
	 */
	public void setMess(String mess) {
		this.mess = mess;
	}

	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * @param categoryName the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * @return the sensitiveword
	 */
	public String getSensitiveword() {
		return sensitiveword;
	}

	/**
	 * @param sensitiveword the sensitiveword to set
	 */
	public void setSensitiveword(String sensitiveword) {
		this.sensitiveword = sensitiveword;
	}

	/**
	 * @return the reply
	 */
	public GuestBookRevert getReply() {
		return reply;
	}

	/**
	 * @param reply the reply to set
	 */
	public void setReply(GuestBookRevert reply) {
		this.reply = reply;
	}
	
}
