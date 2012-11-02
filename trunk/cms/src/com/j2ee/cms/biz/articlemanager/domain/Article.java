/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.articlemanager.domain;

import java.io.Serializable;
import java.util.Date;

import com.j2ee.cms.biz.columnmanager.domain.Column;
import com.j2ee.cms.biz.sitemanager.domain.Site;
import com.j2ee.cms.biz.usermanager.domain.User;

/**        
 * <p>标题: 文章</p>
 * <p>描述: 用于网站显示的基本元素</p>
 * <p>模块: 文章管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 杨信
 * @version 1.0
 * @since 2009-3-10 下午01:44:55
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class Article implements Serializable {

	private static final long serialVersionUID = -6841515710963882374L;
	
	/** 未发布 */
	public static final String PUBLISH_STATE_UNPUBLISHEED = "0";
	
	/** 发布中  */
	public static final String PUBLISH_STATE_PUBLISHING = "2";
	
	/** 已发布 */
	public static final String PUBLISH_STATE_PUBLISHED = "1";
	
	/** 已撤稿 */
	public static final String PUBLISH_STATE_DRAFT = "3";
	
	/** 唯一标识符 */
	private String id;
	
	/** 所属栏目 */
	private Column column;
	
	/** 当前网站 */
	private Site site;
 
	
	/** 是否被删除（是否进入回收站） */
	private boolean deleted;
	
	/** 是否审核 */
	private boolean audited;
	
	/** 发布状态(详细查看该类中：PUBLISH_STATE) */
	private String publishState = PUBLISH_STATE_UNPUBLISHEED;
	
	/** 文章标题 */
	private String title;
	
	/** 副标题 */
	private String subtitle;
	
	/** 引题 */
	private String leadingTitle;
	
	/** 信息来源 */
	private String infoSource;
	
	/** 文章页访问地址 */
	private String url;
	
	/** 点击数 */
	private int hits;
	
	/** 作者 */
	private String author;
	
	/** 摘要 */
	private String brief;
	
	/** 关键字 */
	private String keyword;
	
	/** 文章创建者（录入人） */
	private User creator;
	
	/** 审核人 */
	private User auditor;
	
	/** 显示顺序 */
	private int orders;
	
	/** 创建时间 */
	private Date createTime = new Date();
	
	/** 显示时间 */
	private Date displayTime = new Date();
	
	/** 发布时间 */
	private Date publishTime;
	
	/** 失效时间 */
	private Date invalidTime;
	
	/** 审核时间 */
	private Date auditTime;
	
	/** 引用的文章 */
	private Article referedArticle;
	
	/** 是否是引用文章 */
	private boolean ref;
	
	/** 是否置顶 */
	private boolean toped;
	
	/** 初始化地址**/
	private String initUrl;
	
	/** 关键词过滤开关**/
	private boolean keyFilter;
	
 
//	private String text;
	/**
	 * 文章内容
	 */
	private String textArea;
	/**
	 * 图片
	 */
//	private String pic;
	/**
	 * 附件
	 */
//	private String attach;
	/**
	 * 媒体
	 */
//	private String media;
 

	public Article() {
		// null
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Column getColumn() {
		return column;
	}

	public void setColumn(Column column) {
		this.column = column;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

 

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public boolean isAudited() {
		return audited;
	}

	public void setAudited(boolean audited) {
		this.audited = audited;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getLeadingTitle() {
		return leadingTitle;
	}

	public void setLeadingTitle(String leadingTitle) {
		this.leadingTitle = leadingTitle;
	}

	public String getInfoSource() {
		return infoSource;
	}

	public void setInfoSource(String infoSource) {
		this.infoSource = infoSource;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public User getAuditor() {
		return auditor;
	}

	public void setAuditor(User auditor) {
		this.auditor = auditor;
	}

	public int getOrders() {
		return orders;
	}

	public void setOrders(int orders) {
		this.orders = orders;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getDisplayTime() {
		return displayTime;
	}

	public void setDisplayTime(Date displayTime) {
		this.displayTime = displayTime;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public Date getInvalidTime() {
		return invalidTime;
	}

	public void setInvalidTime(Date invalidTime) {
		this.invalidTime = invalidTime;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public Article getReferedArticle() {
		return referedArticle;
	}

	public void setReferedArticle(Article referedArticle) {
		this.referedArticle = referedArticle;
	}

	public boolean isRef() {
		return ref;
	}

	public void setRef(boolean ref) {
		this.ref = ref;
	}

	public String getPublishState() {
		return publishState;
	}

	public void setPublishState(String publishState) {
		this.publishState = publishState;
	}

	public boolean isToped() {
		return toped;
	}

	public void setToped(boolean toped) {
		this.toped = toped;
	}

	public String getInitUrl() {
		return initUrl;
	}

	public void setInitUrl(String initUrl) {
		this.initUrl = initUrl;
	}

	public boolean isKeyFilter() {
		return keyFilter;
	}

	public void setKeyFilter(boolean keyFilter) {
		this.keyFilter = keyFilter;
	}

	public String getTextArea() {
		return textArea;
	}

	public void setTextArea(String textArea) {
		this.textArea = textArea;
	}
}
