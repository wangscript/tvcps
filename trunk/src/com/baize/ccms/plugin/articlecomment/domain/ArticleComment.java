/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.plugin.articlecomment.domain;

import java.io.Serializable;
import java.util.Date;

import com.baize.ccms.biz.articlemanager.domain.Article;
import com.baize.ccms.biz.usermanager.domain.User;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CCMS</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司</p>
 * <p>网址：http://www.baizeweb.com
 * @author 曹名科
 * @version 1.0
 * @since 2009-10-20 上午11:43:58
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class ArticleComment implements Serializable{
	private static final long serialVersionUID = 3391694959959292526L;
	/**ID*/
	private String id;
	/**评论人*/
	private String authorName;
	/**评论内容*/
	private String content;
	/**是否审核*/
	private boolean audit;
	/**是否精华*/
	private boolean essence;
	/**是否删除*/
	private boolean deleted;
	/**置顶状态*/
	private boolean toped;
	/**评论人IP*/
	private String ip;
	/**置顶*/
	private int orders;
	/**发表时间*/
	private Date createTime;
	/**文章导航*/
	private Article article;
	/**支持数*/
	private int supporter;
	/**反对数*/
	private int ironfoe; 
	/**用户表*/
	private User user;
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
	 * @return the authorName
	 */
	public String getAuthorName() {
		return authorName;
	}
	/**
	 * @param authorName the authorName to set
	 */
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return the audit
	 */
	public boolean isAudit() {
		return audit;
	}
	/**
	 * @param audit the audit to set
	 */
	public void setAudit(boolean audit) {
		this.audit = audit;
	}
	/**
	 * @return the essence
	 */
	public boolean isEssence() {
		return essence;
	}
	/**
	 * @param essence the essence to set
	 */
	public void setEssence(boolean essence) {
		this.essence = essence;
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
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
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
	 * @return the article
	 */
	public Article getArticle() {
		return article;
	}
	/**
	 * @param article the article to set
	 */
	public void setArticle(Article article) {
		this.article = article;
	}
	/**
	 * @param toped the toped to set
	 */
	public void setToped(boolean toped) {
		this.toped = toped;
	}
	/**
	 * @return the toped
	 */
	public boolean isToped() {
		return toped;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @return the supporter
	 */
	public int getSupporter() {
		return supporter;
	}
	/**
	 * @param supporter the supporter to set
	 */
	public void setSupporter(int supporter) {
		this.supporter = supporter;
	}
	/**
	 * @return the ironfoe
	 */
	public int getIronfoe() {
		return ironfoe;
	}
	/**
	 * @param ironfoe the ironfoe to set
	 */
	public void setIronfoe(int ironfoe) {
		this.ironfoe = ironfoe;
	}
	
	

}
