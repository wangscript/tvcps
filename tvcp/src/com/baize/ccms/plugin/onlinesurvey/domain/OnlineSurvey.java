/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.plugin.onlinesurvey.domain;

import java.io.Serializable;
import java.util.Date;

import com.baize.ccms.biz.sitemanager.domain.Site;
import com.baize.ccms.biz.usermanager.domain.User;

/**
 * <p>标题: 网上调查</p>
 * <p>描述: 网上调查包含的内容</p>
 * <p>模块: 网上调查</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司</p>
 * @author <a href="mailto:xinyang921@gmail.com">包坤涛</a>
 * @version 1.0
 * @since 2009-6-13 下午04:24:10
 * @history（历次修订内容、修订人、修订时间等）
 */
public class OnlineSurvey implements Serializable {
	
	private static final long serialVersionUID = 224578496018130950L;
	
	/**主建*/ 
	private String id;
	/**大的调查问题名称*/
	private String name;
	/**描述*/
	private String description;
	/**创建时间*/
	private  Date  createTime; 
	/** 用户*/
	private User user;
	/**当前网站*/
	private Site site;
	/**截止时间*/
	private  Date  stopTime;
	/**所属类别*/              
	private  String category;
	
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}		
	public Date getStopTime() {
		return stopTime;
	}
	public void setStopTime(Date stopTime) {
		this.stopTime = stopTime;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Site getSite() {
		return site;
	}
	public void setSite(Site site) {
		this.site = site;
	}
}