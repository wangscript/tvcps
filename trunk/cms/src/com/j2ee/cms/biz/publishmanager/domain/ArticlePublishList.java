/**
 * project：通用内容管理系统
 * Company:   
 */
package com.j2ee.cms.biz.publishmanager.domain;

import java.io.Serializable;
import java.util.Date;

import com.j2ee.cms.biz.articlemanager.domain.Article;
import com.j2ee.cms.biz.articlemanager.domain.ArticleFormat;
import com.j2ee.cms.biz.columnmanager.domain.Column;
import com.j2ee.cms.biz.sitemanager.domain.Site;
import com.j2ee.cms.biz.usermanager.domain.User;

/**
 * <p>标题: 文章发布列表</p>
 * <p>描述: 用于网站显示的基本元素</p>
 * <p>模块: 文章管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 杨信
 * @version 1.0
 * @since 2009-3-10 下午01:44:55
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class ArticlePublishList implements Serializable {

	private static final long serialVersionUID = -6841515710963882374L;

	/** 唯一标识符 */
	private String id;
	
	/** 所属栏目 */
	private Column column;
	
	/** 当前网站 */
	private Site site;
	
	/** 文章格式 */
	private ArticleFormat articleFormat;
	
	/** 是否被删除（是否进入回收站） */
	private boolean deleted;
	
	/** 是否审核 */
	private boolean audited;
	
	/** 是否已发布 */
	private boolean published;
	
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
	private Date displayTime;
	
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
	
	/** 发布状态（成功true还是失败false,默认为空）*/
	private String status; 
	
	
	/**
	 * 文章内容
	 */
	private String textArea;
	/**
	 * 图片
	 */
	private String pic;
	/**
	 * 附件
	 */
	private String attach;
	/**
	 * 媒体
	 */
	private String media;
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
	 * @return the column
	 */
	public Column getColumn() {
		return column;
	}
	/**
	 * @param column the column to set
	 */
	public void setColumn(Column column) {
		this.column = column;
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
	/**
	 * @return the articleFormat
	 */
	public ArticleFormat getArticleFormat() {
		return articleFormat;
	}
	/**
	 * @param articleFormat the articleFormat to set
	 */
	public void setArticleFormat(ArticleFormat articleFormat) {
		this.articleFormat = articleFormat;
	}
	/**
	 * @return the deleted
	 */
	public boolean isDeleted() {
		return deleted;
	}
	/**
	 * @param deleted the deleted to set
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	/**
	 * @return the audited
	 */
	public boolean isAudited() {
		return audited;
	}
	/**
	 * @param audited the audited to set
	 */
	public void setAudited(boolean audited) {
		this.audited = audited;
	}
	/**
	 * @return the published
	 */
	public boolean isPublished() {
		return published;
	}
	/**
	 * @param published the published to set
	 */
	public void setPublished(boolean published) {
		this.published = published;
	}
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
	 * @return the subtitle
	 */
	public String getSubtitle() {
		return subtitle;
	}
	/**
	 * @param subtitle the subtitle to set
	 */
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	/**
	 * @return the leadingTitle
	 */
	public String getLeadingTitle() {
		return leadingTitle;
	}
	/**
	 * @param leadingTitle the leadingTitle to set
	 */
	public void setLeadingTitle(String leadingTitle) {
		this.leadingTitle = leadingTitle;
	}
	/**
	 * @return the infoSource
	 */
	public String getInfoSource() {
		return infoSource;
	}
	/**
	 * @param infoSource the infoSource to set
	 */
	public void setInfoSource(String infoSource) {
		this.infoSource = infoSource;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the hits
	 */
	public int getHits() {
		return hits;
	}
	/**
	 * @param hits the hits to set
	 */
	public void setHits(int hits) {
		this.hits = hits;
	}
	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}
	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	/**
	 * @return the brief
	 */
	public String getBrief() {
		return brief;
	}
	/**
	 * @param brief the brief to set
	 */
	public void setBrief(String brief) {
		this.brief = brief;
	}
	/**
	 * @return the keyword
	 */
	public String getKeyword() {
		return keyword;
	}
	/**
	 * @param keyword the keyword to set
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	/**
	 * @return the creator
	 */
	public User getCreator() {
		return creator;
	}
	/**
	 * @param creator the creator to set
	 */
	public void setCreator(User creator) {
		this.creator = creator;
	}
	/**
	 * @return the auditor
	 */
	public User getAuditor() {
		return auditor;
	}
	/**
	 * @param auditor the auditor to set
	 */
	public void setAuditor(User auditor) {
		this.auditor = auditor;
	}
	/**
	 * @return the orders
	 */
	public int getOrders() {
		return orders;
	}
	/**
	 * @param orders the orders to set
	 */
	public void setOrders(int orders) {
		this.orders = orders;
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
	 * @return the displayTime
	 */
	public Date getDisplayTime() {
		return displayTime;
	}
	/**
	 * @param displayTime the displayTime to set
	 */
	public void setDisplayTime(Date displayTime) {
		this.displayTime = displayTime;
	}
	/**
	 * @return the publishTime
	 */
	public Date getPublishTime() {
		return publishTime;
	}
	/**
	 * @param publishTime the publishTime to set
	 */
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	/**
	 * @return the invalidTime
	 */
	public Date getInvalidTime() {
		return invalidTime;
	}
	/**
	 * @param invalidTime the invalidTime to set
	 */
	public void setInvalidTime(Date invalidTime) {
		this.invalidTime = invalidTime;
	}
	/**
	 * @return the auditTime
	 */
	public Date getAuditTime() {
		return auditTime;
	}
	/**
	 * @param auditTime the auditTime to set
	 */
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	/**
	 * @return the referedArticle
	 */
	public Article getReferedArticle() {
		return referedArticle;
	}
	/**
	 * @param referedArticle the referedArticle to set
	 */
	public void setReferedArticle(Article referedArticle) {
		this.referedArticle = referedArticle;
	}
	/**
	 * @return the ref
	 */
	public boolean isRef() {
		return ref;
	}
	/**
	 * @param ref the ref to set
	 */
	public void setRef(boolean ref) {
		this.ref = ref;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the textArea
	 */
	public String getTextArea() {
		return textArea;
	}
	/**
	 * @param textArea the textArea to set
	 */
	public void setTextArea(String textArea) {
		this.textArea = textArea;
	}
	/**
	 * @return the pic
	 */
	public String getPic() {
		return pic;
	}
	/**
	 * @param pic the pic to set
	 */
	public void setPic(String pic) {
		this.pic = pic;
	}
	/**
	 * @return the attach
	 */
	public String getAttach() {
		return attach;
	}
	/**
	 * @param attach the attach to set
	 */
	public void setAttach(String attach) {
		this.attach = attach;
	}
	/**
	 * @return the media
	 */
	public String getMedia() {
		return media;
	}
	/**
	 * @param media the media to set
	 */
	public void setMedia(String media) {
		this.media = media;
	}
	

}
