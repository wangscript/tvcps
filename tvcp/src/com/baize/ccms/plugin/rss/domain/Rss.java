/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.plugin.rss.domain;

import java.io.Serializable;
import java.util.Date;
import com.baize.ccms.biz.sitemanager.domain.Site;

/**
 * <p>
 * 标题: —— Rss实体.
 * </p>
 * <p>
 * 描述: —— Rss实体.
 * </p>
 * <p>
 * 模块: CCMS
 * </p>
 * <p>
 * 版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * </p>
 * <p>
 * 网址：http://www.baizeweb.com
 * 
 * @author 曹名科
 * @version 1.0
 * @since 2009-10-16 下午05:40:00
 * @history（历次修订内容、修订人、修订时间等） 
 */
 
public class Rss implements Serializable {
	private static final long serialVersionUID = -1411258207293724789L;
	/** ID. */
	private String id;
	/** 创建时间. */
	private Date createTime;
	/** 保存路径. */
	private String xmlSavePath;
	/**标识是多栏目还是单栏目*/
	private String columnIdentifier;
	/** 网站对象. */
	private Site sites;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
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
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the xmlSavePath
	 */
	public String getXmlSavePath() {
		return xmlSavePath;
	}

	/**
	 * @param xmlSavePath
	 *            the xmlSavePath to set
	 */
	public void setXmlSavePath(String xmlSavePath) {
		this.xmlSavePath = xmlSavePath;
	}

	/**
	 * @return the sites
	 */
	public Site getSites() {
		return sites;
	}

	/**
	 * @param sites
	 *            the sites to set
	 */
	public void setSites(Site sites) {
		this.sites = sites;
	}

	public void setColumnIdentifier(String columnIdentifier) {
		this.columnIdentifier = columnIdentifier;
	}

	public String getColumnIdentifier() {
		return columnIdentifier;
	}


}
