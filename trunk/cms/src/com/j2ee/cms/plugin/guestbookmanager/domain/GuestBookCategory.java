/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.plugin.guestbookmanager.domain;

import java.io.Serializable;

import com.j2ee.cms.biz.sitemanager.domain.Site;

/**
 * <p>标题: —— 留言类别</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CPS</p>
 * <p>版权: Copyright (c) 2009  </p>
 * <p>网址：http://www.j2ee.cmsweb.com
 * @author 曹名科
 * @version 1.0
 * @since 2009-10-30 上午11:12:38
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class GuestBookCategory implements Serializable{
	private static final long serialVersionUID = 2531500270015625811L;
	/**ID主键*/
	private String id;
	/**类别名称*/
	private String categoryName;
	/**网站对象*/
	private Site sites;
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
	 * @return the 类别名称
	 */
	public String getCategoryName() {
		return categoryName;
	}
	/**
	 * @param 类别名称 the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	/**
	 * @return the 网站对象
	 */
	public Site getSites() {
		return sites;
	}
	/**
	 * @param 网站对象 the 网站对象 to set
	 */
	public void setSites(Site sites) {
		this.sites = sites;
	}
}
