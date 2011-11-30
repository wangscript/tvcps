/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.plugin.letterbox.domain;

import java.io.Serializable;
import java.util.Date;

import com.j2ee.cms.biz.usermanager.domain.Member;
import com.j2ee.cms.biz.usermanager.domain.Organization;

/**
 * <p>标题: 信件</p>
 * <p>描述: 外网用户写信包含的内容</p>
 * <p>模块: 信件箱</p>
 * <p>版权: Copyright (c) 2009  </p>
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-6-13 下午04:24:10
 * @history（历次修订内容、修订人、修订时间等）
 */
public class Letter implements Serializable {
	
	private static final long serialVersionUID = 2579334964111309501L;
	
	/** 未处理 */
	public static final int LETTER_STATUS_UNDEAL = 1;
	
	/** 待处理 */
	public static final int LETTER_STATUS_WAITDEAL = 2;
	
	/** 已处理 */
	public static final int LETTER_STATUS_DEALED = 3;

	/** 唯一标识 */
	private String id;
	
	/** 标题 */
	private String title;
	
	/** 内容 */
	private String content;
	
	/** 是否公开 */
	private boolean opened;
	
	/** 信件状态(参见Letter中LETTER_STATUS) */
	private int letterStatus;
	
	/** 机构  */
	private Organization organization;
	
	/** 类别 */
	private LetterCategory letterCategory;
	
	/** 会员 */
	private Member member;
	
	/** 提交日期 */
	private Date submitDate;
	
	/** 用户IP */
	private String userIP;
	
	/** 回执编号 */
	private String replyCode;
	
	/** 移动电话 */
	private String mobileTel;
	
	/** 用户名称 */
	private String userName;
	
	/** 家庭电话 */
	private String homeTel;

	/** 居住地 */
	private String residence;
	
	/** email */
	private String email;
	
	/** 是否已被转办 */
	private boolean transfered;

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
	 * @return the letterCategory
	 */
	public LetterCategory getLetterCategory() {
		return letterCategory;
	}

	/**
	 * @param letterCategory the letterCategory to set
	 */
	public void setLetterCategory(LetterCategory letterCategory) {
		this.letterCategory = letterCategory;
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
	 * @return the organization
	 */
	public Organization getOrganization() {
		return organization;
	}

	/**
	 * @param organization the organization to set
	 */
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	/**
	 * @return the member
	 */
	public Member getMember() {
		return member;
	}

	/**
	 * @param member the member to set
	 */
	public void setMember(Member member) {
		this.member = member;
	}

	/**
	 * @return the submitDate
	 */
	public Date getSubmitDate() {
		return submitDate;
	}

	/**
	 * @param submitDate the submitDate to set
	 */
	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	/**
	 * @return the replyCode
	 */
	public String getReplyCode() {
		return replyCode;
	}

	/**
	 * @param replyCode the replyCode to set
	 */
	public void setReplyCode(String replyCode) {
		this.replyCode = replyCode;
	}

	/**
	 * @return the mobileTel
	 */
	public String getMobileTel() {
		return mobileTel;
	}

	/**
	 * @param mobileTel the mobileTel to set
	 */
	public void setMobileTel(String mobileTel) {
		this.mobileTel = mobileTel;
	}

	/**
	 * @return the homeTel
	 */
	public String getHomeTel() {
		return homeTel;
	}

	/**
	 * @param homeTel the homeTel to set
	 */
	public void setHomeTel(String homeTel) {
		this.homeTel = homeTel;
	}

	/**
	 * @return the residence
	 */
	public String getResidence() {
		return residence;
	}

	/**
	 * @param residence the residence to set
	 */
	public void setResidence(String residence) {
		this.residence = residence;
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
	 * @return the userIP
	 */
	public String getUserIP() {
		return userIP;
	}

	/**
	 * @param userIP the userIP to set
	 */
	public void setUserIP(String userIP) {
		this.userIP = userIP;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the letterStatus
	 */
	public int getLetterStatus() {
		return letterStatus;
	}

	/**
	 * @param letterStatus the letterStatus to set
	 */
	public void setLetterStatus(int letterStatus) {
		this.letterStatus = letterStatus;
	}

	/**
	 * @return the transfered
	 */
	public boolean isTransfered() {
		return transfered;
	}

	/**
	 * @param transfered the transfered to set
	 */
	public void setTransfered(boolean transfered) {
		this.transfered = transfered;
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

	public void setOpened(boolean opened) {
		this.opened = opened;
	}

	public boolean isOpened() {
		return opened;
	}
	
}
