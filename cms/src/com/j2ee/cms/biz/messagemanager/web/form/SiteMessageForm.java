/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.messagemanager.web.form;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import com.j2ee.cms.biz.messagemanager.domain.SiteMessage;
import com.j2ee.cms.biz.usermanager.domain.User;
import com.j2ee.cms.common.core.web.GeneralForm;

/**
 * <p>标题: 消息表单</p>
 * <p>描述: 消息的表单数据，以便页面和方法中调用</p>
 * <p>模块: 消息管理</p>
 * <p>版权: Copyright (c) 2009 
 * @author 杨信
 * @version 1.0
 * @since 2009-5-18 上午11:32:05 
 * @history（历次修订内容、修订人、修订时间等） 
*/

public class SiteMessageForm extends GeneralForm {
	
	private static final long serialVersionUID = -5662807453631912339L;
	
	/** 消息id **/
	private String id;
	
	/** 消息名字 **/
	private String name;
	
	/** 消息头 **/
	private String title;
	
	/** 时间**/
    private String dateTime;
	
	Date createTime;
	
	/** 消息内容 **/
	private String content;
	
	/** 创建消息对象**/
	private SiteMessage sitemessage = new SiteMessage();
	
    private List<String[]> list;
    
    
    /** 收件人姓名组成的字符串**/
    private String contacterName;
    
    /** 收件人的idstr**/
    private String idstr;
    
    /** 定义一个标志flags,用来标记是从哪里跳转到发送页面的；
     *  "1"代表从发件箱本身发送消息后跳转的；
     *  "2"代表回复后跳转；
     *  "3"代表转发后跳转；
     * **/
    int flags;
    
    /** 未读信息条数**/
    int messageNum;
	
    /** 标记，是否添加到常用联系人**/
    String check;
    
    private List<User> listUser = new ArrayList<User>();
    
    /** 常用联系人的id和名字组成的字符串(选择收件人时用)**/
    private String str;
    /** 常用联系人的id和名字组成的字符串(点击左侧树进入写信页面时用)**/
    private String userfulContacterStr;
    
    /** 常用联系人的id,在添加收件人时用**/
    private String contacterId;
    
    /** 机构id**/
    private String orgId;
    
    /** 选择的收件人ids**/
    private String strOperationId;
    
    /** 消息是否已读**/
    private String readed;
    
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
	 * @return the serialVersionUID
	 */
	public static long getSerialVersionUID() {
		return serialVersionUID;
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
	 * @return the listUser
	 */
	public List<User> getListUser() {
		return listUser;
	}

	/**
	 * @param listUser the listUser to set
	 */
	public void setListUser(List<User> listUser) {
		this.listUser = listUser;
	}

	/**
	 * @return the check
	 */
	public String getCheck() {
		return check;
	}

	/**
	 * @param check the check to set
	 */
	public void setCheck(String check) {
		this.check = check;
	}

	/**
	 * @return the messageNum
	 */
	public int getMessageNum() {
		return messageNum;
	}

	/**
	 * @param messageNum the messageNum to set
	 */
	public void setMessageNum(int messageNum) {
		this.messageNum = messageNum;
	}

	/**
	 * @return the flags
	 */
	public int getFlags() {
		return flags;
	}

	/**
	 * @param flags the flags to set
	 */
	public void setFlags(int flags) {
		this.flags = flags;
	}

	public List<String[]> getList() {
		return list;
	}

	public void setList(List<String[]> list) {
		this.list = list;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the sitemessage
	 */
	public SiteMessage getSitemessage() {
		return sitemessage;
	}

	/**
	 * @param sitemessage the sitemessage to set
	 */
	public void setSitemessage(SiteMessage sitemessage) {
		this.sitemessage = sitemessage;
	}

	/* (non-Javadoc)
	 * @see com.j2ee.cms.common.core.web.GeneralForm#validateData(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected void validateData(ActionMapping arg0, HttpServletRequest arg1) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @return the contacterName
	 */
	public String getContacterName() {
		return contacterName;
	}

	/**
	 * @param contacterName the contacterName to set
	 */
	public void setContacterName(String contacterName) {
		this.contacterName = contacterName;
	} 
	
	/**
	 * @param getIds the idstr to get
	 */
	public String getIdstr() {
		return idstr;
	}
	
	/**
	 * @param getIds the idstr to set
	 */
	public void setIdstr(String idstr) {
		this.idstr = idstr;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param str the str to set
	 */
	public void setStr(String str) {
		this.str = str;
	}

	/**
	 * @return the str
	 */
	public String getStr() {
		return str;
	}

	/**
	 * @param contacterId the contacterId to set
	 */
	public void setContacterId(String contacterId) {
		this.contacterId = contacterId;
	}

	/**
	 * @return the contacterId
	 */
	public String getContacterId() {
		return contacterId;
	}

	/**
	 * @param dateTime the dateTime to set
	 */
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	/**
	 * @return the dateTime
	 */
	public String getDateTime() {
		return dateTime;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setStrOperationId(String strOperationId) {
		this.strOperationId = strOperationId;
	}

	public String getStrOperationId() {
		return strOperationId;
	}

	public void setUserfulContacterStr(String userfulContacterStr) {
		this.userfulContacterStr = userfulContacterStr;
	}

	public String getUserfulContacterStr() {
		return userfulContacterStr;
	}

	public void setReaded(String readed) {
		this.readed = readed;
	}

	public String getReaded() {
		return readed;
	}

}
