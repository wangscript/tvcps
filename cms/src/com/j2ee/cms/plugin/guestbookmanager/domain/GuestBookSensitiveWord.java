/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.plugin.guestbookmanager.domain;

import java.io.Serializable;
import java.util.Date;

import com.j2ee.cms.biz.sitemanager.domain.Site;

/**
 * <p>标题: ——留言本敏感词</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CPS</p>
 * <p>版权: Copyright (c) 2009  </p>
 * <p>网址：http://www.j2ee.cmsweb.com
 * @author 曹名科
 * @version 1.0
 * @since 2009-10-30 上午11:12:01
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class GuestBookSensitiveWord implements Serializable{
	private static final long serialVersionUID = 2052583999602344991L;
	private String id;
	/**敏感词*/
	private String sensitiveWord;
	/**创建时间*/
	private Date createTime; 
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
	 * @return the sensitiveWord
	 */
	public String getSensitiveWord() {
		return sensitiveWord;
	}
	/**
	 * @param sensitiveWord the sensitiveWord to set
	 */
	public void setSensitiveWord(String sensitiveWord) {
		this.sensitiveWord = sensitiveWord;
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
	 * @return the sites
	 */
	public Site getSites() {
		return sites;
	}
	/**
	 * @param sites the sites to set
	 */
	public void setSites(Site sites) {
		this.sites = sites;
	}

}
