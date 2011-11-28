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
 * <p>标题: 转办记录</p>
 * <p>描述: 信件由一个部门转至另一个部门</p>
 * <p>模块: 信件箱</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司</p>
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-6-15 上午10:19:02
 * @history（历次修订内容、修订人、修订时间等）
 */
public class TransferRecord implements Serializable {

	private static final long serialVersionUID = -3946220626794149637L;
	
	/** 唯一标识符 */
	private String id;
	
	/** 转办的信件 */
	private Letter letter;
	
	/** 信件来源部门 */
	private Organization fromOrg;
	
	/** 信件转至部门 */
	private Organization toOrg;
	
	/** 备注 */
	private String note;
	
	/** 转办人 */
	private User transferUser;
	
	/** 转办日期 */
	private Date transferDate;

	/**
	 * @return the transferDate
	 */
	public Date getTransferDate() {
		return transferDate;
	}

	/**
	 * @param transferDate the transferDate to set
	 */
	public void setTransferDate(Date transferDate) {
		this.transferDate = transferDate;
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
	 * @return the transferUser
	 */
	public User getTransferUser() {
		return transferUser;
	}

	/**
	 * @param transferUser the transferUser to set
	 */
	public void setTransferUser(User transferUser) {
		this.transferUser = transferUser;
	}

	/**
	 * @return the fromOrg
	 */
	public Organization getFromOrg() {
		return fromOrg;
	}

	/**
	 * @param fromOrg the fromOrg to set
	 */
	public void setFromOrg(Organization fromOrg) {
		this.fromOrg = fromOrg;
	}

	/**
	 * @return the toOrg
	 */
	public Organization getToOrg() {
		return toOrg;
	}

	/**
	 * @param toOrg the toOrg to set
	 */
	public void setToOrg(Organization toOrg) {
		this.toOrg = toOrg;
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
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
