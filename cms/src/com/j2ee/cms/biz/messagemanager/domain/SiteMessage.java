/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.messagemanager.domain;

import java.io.Serializable;
import java.util.Date;

import com.j2ee.cms.biz.usermanager.domain.User;

/**
 * <p>标题: 站内消息</p>
 * <p>描述: 中间对象</p>
 * <p>模块: 消息管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-5-19 上午09:22:39
 * @history（历次修订内容、修订人、修订时间等）
 */
public class SiteMessage implements Serializable {

	private static final long serialVersionUID = 960299960516693210L;

	/** 收到的消息 */
	public static final String FLAG_RECEIVE = "1";
	
	/** 发送的消息 */
	public static final String FLAG_SEND = "2";
	
	/** 唯一标识符 */
	private String id;
	
	/** 消息标题 */
	private String title;
	
	/** 是已读还是未读 */
	private boolean readed;
	
	/** 收件人 */
	private User receiver;
	
	/** 发件人 */
	private User sender;
	
	/** 消息内容 */
	private String content;
	
	/** 创建时间 */
	private Date createTime;
	

	/** 
	 * 消息标识 
	 * 收到的消息：MESSAGE_RECEIVE
	 * 发送的消息：MESSAGE_SEND
	 */
	private String flag;
	
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
	 * @return the flag
	 */
	public String getFlag() {
		return flag;
	}

	/**
	 * @param flag the flag to set
	 */
	public void setFlag(String flag) {
		this.flag = flag;
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
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the readed
	 */
	public boolean isReaded() {
		return readed;
	}

	/**
	 * @param readed the readed to set
	 */
	public void setReaded(boolean readed) {
		this.readed = readed;
	}
}
