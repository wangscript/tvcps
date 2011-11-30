/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.plugin.letterbox.web.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.j2ee.cms.biz.usermanager.domain.Organization;
import com.j2ee.cms.biz.usermanager.domain.User;
import com.j2ee.cms.plugin.letterbox.domain.Letter;
import com.j2ee.cms.plugin.letterbox.domain.LetterCategory;
import com.j2ee.cms.plugin.letterbox.domain.LetterReply;
import com.j2ee.cms.plugin.letterbox.domain.TransferRecord;

import com.j2ee.cms.common.core.web.GeneralForm;

/**
 * <p>标题: 信件表单</p>
 * <p>描述: 信件的表单数据，以便页面和方法中调用</p>
 * <p>模块: 信件箱</p>
 * <p>版权: Copyright (c) 2009 
 * @author 杨信
 * @version 1.0
 * @since 2009-6-14 下午02:35:25 
 * @history（历次修订内容、修订人、修订时间等） 
*/


public class LetterForm extends GeneralForm {

	private static final long serialVersionUID = -5662807958636915339L;
	
	private Letter letter = new Letter();
	
	private LetterReply letterReply = new LetterReply();
	
	private Organization organization = new Organization();
	
	private User user = new User();
	
	private TransferRecord transferRecord = new TransferRecord();
	
	private LetterCategory letterCategory = new LetterCategory();
	
	private String id;
	
	/** 信件标题**/
	private String title;
	
	/** 信件内容**/
	private String content;
	
	/** 回复内容**/
	private String replyContent;
	
	/** 回复部门**/
	private String replyOrg;
	
	/** 回复时间**/
	private String replyDate;
	
	/** 部门id**/
	private String organizationId;
	
	/** 转办前的部门id**/
	private String fromOrgId;
	
	/** 转办后的部门id**/
	private String toOrgId;
	
	/** 转办前的部门名称**/
	private String orgFromName;
	
	/** 转办后的部门名称**/
	private String orgToName;
	
	/** 转办人id**/
	private String transferUserId;
	
	/** 信件类别id**/
	private String letterCategoryId;
	
	/** 信件类别名称**/
	private String letterCategoryName;
	
	/** 信件是否公开**/
	private String openStr;
	
	/** 会员id**/
	private String memberId;
	
	/** 信件id,转办时用**/
	private String letterId;
	
	/** 要写信件中的部门名称**/
	private String orgName;
	
	/** 回执编码**/
	private String replyCode;
	
	/** 信件类别名称,前台界面用**/
	private String categoryName;
	
	/** 机构list**/
	@SuppressWarnings("unchecked")
	private List orgList = new ArrayList();
	
	/**
	 * @return the categoryList
	 */
	public List getCategoryList() {
		return categoryList;
	}

	/**
	 * @param categoryList the categoryList to set
	 */
	public void setCategoryList(List categoryList) {
		this.categoryList = categoryList;
	}

	private List categoryList = new ArrayList();
	
	/** 信件状态**/
	private int letterStatus;
	
	/** 页面跳转标志**/
	private int flag;
	
	private String writeDate;
	
	/** 查看转办记录时用**/
	@SuppressWarnings("unchecked")
	private List list = new ArrayList();
	
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
	 * @return the replyContent
	 */
	public String getReplyContent() {
		return replyContent;
	}

	/**
	 * @param replyContent the replyContent to set
	 */
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	/**
	 * @return the replyOrg
	 */
	public String getReplyOrg() {
		return replyOrg;
	}

	/**
	 * @param replyOrg the replyOrg to set
	 */
	public void setReplyOrg(String replyOrg) {
		this.replyOrg = replyOrg;
	}

	/**
	 * @return the replyDate
	 */
	public String getReplyDate() {
		return replyDate;
	}

	/**
	 * @param replyDate the replyDate to set
	 */
	public void setReplyDate(String replyDate) {
		this.replyDate = replyDate;
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
	
	@Override
	protected void validateData(ActionMapping arg0, HttpServletRequest arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public void setLetter(Letter letter) {
		this.letter = letter;
	}
	
	public Letter getLetter() {
		return letter;
	}


	/**
	 * @param letterReply the letterReply to set
	 */
	public void setLetterReply(LetterReply letterReply) {
		this.letterReply = letterReply;
	}

	/**
	 * @return the letterReply
	 */
	public LetterReply getLetterReply() {
		return letterReply;
	}

	/**
	 * @param organization the organization to set
	 */
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	/**
	 * @return the organization
	 */
	public Organization getOrganization() {
		return organization;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param transferRecord the transferRecord to set
	 */
	public void setTransferRecord(TransferRecord transferRecord) {
		this.transferRecord = transferRecord;
	}

	/**
	 * @return the transferRecord
	 */
	public TransferRecord getTransferRecord() {
		return transferRecord;
	}

	/**
	 * @param orgFromName the orgFromName to set
	 */
	public void setOrgFromName(String orgFromName) {
		this.orgFromName = orgFromName;
	}

	/**
	 * @return the orgFromName
	 */
	public String getOrgFromName() {
		return orgFromName;
	}

	/**
	 * @param orgToName the orgToName to set
	 */
	public void setOrgToName(String orgToName) {
		this.orgToName = orgToName;
	}

	/**
	 * @return the orgToName
	 */
	public String getOrgToName() {
		return orgToName;
	}

	/**
	 * @param letterCategoryName the letterCategoryName to set
	 */
	public void setLetterCategoryName(String letterCategoryName) {
		this.letterCategoryName = letterCategoryName;
	}

	/**
	 * @return the letterCategoryName
	 */
	public String getLetterCategoryName() {
		return letterCategoryName;
	}

	/**
	 * @param openStr the openStr to set
	 */
	public void setOpenStr(String openStr) {
		this.openStr = openStr;
	}

	/**
	 * @return the openStr
	 */
	public String getOpenStr() {
		return openStr;
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
	 * @param list the list to set
	 */
	@SuppressWarnings("unchecked")
	public void setList(List list) {
		this.list = list;
	}

	/**
	 * @return the list
	 */
	@SuppressWarnings("unchecked")
	public List getList() {
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
	 * @param replyCode the replyCode to set
	 */
	public void setReplyCode(String replyCode) {
		this.replyCode = replyCode;
	}

	/**
	 * @return the replyCode
	 */
	public String getReplyCode() {
		return replyCode;
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
	 * @param letterStatus the letterStatus to set
	 */
	public void setLetterStatus(int letterStatus) {
		this.letterStatus = letterStatus;
	}

	/**
	 * @return the letterStatus
	 */
	public int getLetterStatus() {
		return letterStatus;
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
	 * @return the organizationId
	 */
	public String getOrganizationId() {
		return organizationId;
	}

	/**
	 * @param organizationId the organizationId to set
	 */
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	/**
	 * @return the fromOrgId
	 */
	public String getFromOrgId() {
		return fromOrgId;
	}

	/**
	 * @param fromOrgId the fromOrgId to set
	 */
	public void setFromOrgId(String fromOrgId) {
		this.fromOrgId = fromOrgId;
	}

	/**
	 * @return the toOrgId
	 */
	public String getToOrgId() {
		return toOrgId;
	}

	/**
	 * @param toOrgId the toOrgId to set
	 */
	public void setToOrgId(String toOrgId) {
		this.toOrgId = toOrgId;
	}

	/**
	 * @return the transferUserId
	 */
	public String getTransferUserId() {
		return transferUserId;
	}

	/**
	 * @param transferUserId the transferUserId to set
	 */
	public void setTransferUserId(String transferUserId) {
		this.transferUserId = transferUserId;
	}

	/**
	 * @return the letterCategoryId
	 */
	public String getLetterCategoryId() {
		return letterCategoryId;
	}

	/**
	 * @param letterCategoryId the letterCategoryId to set
	 */
	public void setLetterCategoryId(String letterCategoryId) {
		this.letterCategoryId = letterCategoryId;
	}

	/**
	 * @return the memberId
	 */
	public String getMemberId() {
		return memberId;
	}

	/**
	 * @param memberId the memberId to set
	 */
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	/**
	 * @return the letterId
	 */
	public String getLetterId() {
		return letterId;
	}

	/**
	 * @param letterId the letterId to set
	 */
	public void setLetterId(String letterId) {
		this.letterId = letterId;
	}

	/**
	 * @param writeDate the writeDate to set
	 */
	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}

	/**
	 * @return the writeDate
	 */
	public String getWriteDate() {
		return writeDate;
	}

	public void setOrgList(List orgList) {
		this.orgList = orgList;
	}

	public List getOrgList() {
		return orgList;
	}

}
