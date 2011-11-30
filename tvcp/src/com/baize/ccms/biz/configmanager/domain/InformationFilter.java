/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.configmanager.domain;

import java.io.Serializable;
import java.util.Date;

import com.j2ee.cms.biz.columnmanager.domain.Column;
import com.j2ee.cms.biz.sitemanager.domain.Site;
import com.j2ee.cms.biz.usermanager.domain.User;

/**
 * <p>
 * 标题: 文章
 * </p>
 * <p>
 * 描述: 用于网站显示的基本元素
 * </p>
 * <p>
 * 模块: 文章管理
 * </p>
 * <p>
 * 版权: Copyright (c) 2009  
 * 
 * @author 杨信
 * @version 1.0
 * @since 2009-3-10 下午01:44:55
 * @history（历次修订内容、修订人、修订时间等） 
 */
 

public class InformationFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4742906202369089257L;
	private String id;
	// 替换前字段1
	private String field1;
	// 替换前字段2
	private String field2;
	// 替换后字段1
	private String replaceField1;
	// 替换后字段2
	private String replaceField2;
	// 状态
	private Boolean status;
	/*
	 * //过滤类别 private String filterCategoryId;
	 */
	// 创建时间
	private Date createTime;
	// 过滤类别
	// 用户
	private User user;


	// 当前网站
	private Site site;

	FilterCategory filterCategory = new FilterCategory();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getField1() {
		return field1;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

	public String getField2() {
		return field2;
	}

	public void setField2(String field2) {
		this.field2 = field2;
	}

	public String getReplaceField1() {
		return replaceField1;
	}

	public void setReplaceField1(String replaceField1) {
		this.replaceField1 = replaceField1;
	}

	public String getReplaceField2() {
		return replaceField2;
	}

	public void setReplaceField2(String replaceField2) {
		this.replaceField2 = replaceField2;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public FilterCategory getFilterCategory() {
		return filterCategory;
	}

	public void setFilterCategory(FilterCategory filterCategory) {
		this.filterCategory = filterCategory;
	}
	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
