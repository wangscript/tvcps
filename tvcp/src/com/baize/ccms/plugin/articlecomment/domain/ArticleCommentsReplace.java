/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.plugin.articlecomment.domain;

import java.io.Serializable;
import java.util.Date;
import com.j2ee.cms.biz.sitemanager.domain.Site;
import com.j2ee.cms.biz.usermanager.domain.User;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CPS</p>
 * <p>版权: Copyright (c) 2009  </p>
 * <p>网址：http://www.j2ee.cmsweb.com
 * @author 曹名科
 * @version 1.0
 * @since 2009-10-20 上午11:43:58
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class ArticleCommentsReplace implements Serializable{
	private static final long serialVersionUID = 3391694959959292526L;
	/**ID*/
	private String id;
	/**需要过滤的词*/
	private String filterWord;
	/**过滤后的词*/
	private String replaceWord;
	/**创建时间*/
	private Date createTime;
	/**创建人*/
	private User user;
	/**网站对象*/
	private Site site;
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
	 * @return the filterWord
	 */
	public String getFilterWord() {
		return filterWord;
	}
	/**
	 * @param filterWord the filterWord to set
	 */
	public void setFilterWord(String filterWord) {
		this.filterWord = filterWord;
	}
	/**
	 * @return the replaceWord
	 */
	public String getReplaceWord() {
		return replaceWord;
	}
	/**
	 * @param replaceWord the replaceWord to set
	 */
	public void setReplaceWord(String replaceWord) {
		this.replaceWord = replaceWord;
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
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * @return the site
	 */
	public Site getSite() {
		return site;
	}
	/**
	 * @param site the site to set
	 */
	public void setSite(Site site) {
		this.site = site;
	}
	

}
