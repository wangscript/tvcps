/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.plugin.letterbox.domain;

import java.io.Serializable;
import java.util.Date;

import com.baize.ccms.biz.usermanager.domain.Organization;
import com.baize.ccms.biz.usermanager.domain.User;

/**
 * <p>标题: 回信</p>
 * <p>描述: 回信包含的主要内容</p>
 * <p>模块: 信件箱</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司</p>
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-6-13 下午04:27:27
 * @history（历次修订内容、修订人、修订时间等）
 */
public class LetterReply implements Serializable {
	private static final long serialVersionUID = 2579334969111309501L;
	
	/** 唯一标识 */
	private String id;
	
	/** 信件 */
	private Letter letter;
	
	/** 内容 */
	private String content;
	
	/** 回复人 */
	private User replyor;
	
	/** 回复机构 */
	private Organization organization;
	
	/** 回复日期 */
	private Date replyDate;

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
	 * @return the replyor
	 */
	public User getReplyor() {
		return replyor;
	}

	/**
	 * @param replyor the replyor to set
	 */
	public void setReplyor(User replyor) {
		this.replyor = replyor;
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
	 * @return the replyDate
	 */
	public Date getReplyDate() {
		return replyDate;
	}

	/**
	 * @param replyDate the replyDate to set
	 */
	public void setReplyDate(Date replyDate) {
		this.replyDate = replyDate;
	}

	/**
	 * @return the letter
	 */
	public Letter getLetter() {
		return letter;
	}

	/**
	 * @param letter the letter to set
	 */
	public void setLetter(Letter letter) {
		this.letter = letter;
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
	
}
