/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.biz.messagemanager.domain;

import java.io.Serializable;

import com.baize.ccms.biz.usermanager.domain.User;

/**
 * <p>标题: 联系人</p>
 * <p>描述: 中间对象</p>
 * <p>模块: 消息管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-5-19 上午09:41:56
 * @history（历次修订内容、修订人、修订时间等）
 */
public class Contact implements Serializable {
	
	private static final long serialVersionUID = -3079694408600668255L;

	/** 唯一标识符 */
	private String id;
	
	/** 发件人 */
	private User sender;
	
	/** 是否常用联系人 */
	private boolean often;
	
	/** 收件人 */
	private User receiver;

	/**
	 * @return the receiver
	 */
	public User getReceiver() {
		return receiver;
	}

	/**
	 * @param receiver the receiver to set
	 */
	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	/**
	 * @return the sender
	 */
	public User getSender() {
		return sender;
	}

	/**
	 * @param sender the sender to set
	 */
	public void setSender(User sender) {
		this.sender = sender;
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
	 * @return the often
	 */
	public boolean isOften() {
		return often;
	}

	/**
	 * @param often the often to set
	 */
	public void setOften(boolean often) {
		this.often = often;
	}
	
	
	
}
