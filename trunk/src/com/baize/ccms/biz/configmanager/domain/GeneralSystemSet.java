/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.configmanager.domain;

import java.io.Serializable;
import java.util.Date;

import com.baize.ccms.biz.columnmanager.domain.Column;
import com.baize.ccms.biz.sitemanager.domain.Site;
import com.baize.ccms.biz.usermanager.domain.User;

/**
 * <p>标题: 文章</p>
 * <p>描述: 用于网站显示的基本元素</p>
 * <p>模块: 文章管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 杨信
 * @version 1.0
 * @since 2009-3-10 下午01:44:55
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class GeneralSystemSet implements Serializable {

	private static final long serialVersionUID = -6841515714963882374L;

	/** 唯一标识符 */
	private String id;
 
	/** 系统参数类别-**/
	private String  setContent;
	/** 是否设置为 默认**/
    private  Boolean defaultSet;
    /** 创建时间 */
     private Date createTime;
	/** 参数设置的**/
     private  ReneralSystemSetCategory  reneralSystemSetCategory;
     /** 系数参数类别**/
     private String  url; 
 	// 用户
 	private User user;
 	// 当前网站
 	private Site site;
     
	

	public ReneralSystemSetCategory getReneralSystemSetCategory() {
		return reneralSystemSetCategory;
	}
	public void setReneralSystemSetCategory(
			ReneralSystemSetCategory reneralSystemSetCategory) {
		this.reneralSystemSetCategory = reneralSystemSetCategory;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getSetContent() {
		return setContent;
	}
	public void setSetContent(String setContent) {
		this.setContent = setContent;
	}
	public Boolean getDefaultSet() {
		return defaultSet;
	}
	public void setDefaultSet(Boolean defaultSet) {
		this.defaultSet = defaultSet;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
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
