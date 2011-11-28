/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.biz.columnmanager.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import com.baize.ccms.biz.articlemanager.domain.ArticleFormat;
import com.baize.ccms.biz.sitemanager.domain.Site;
import com.baize.ccms.biz.templatemanager.domain.TemplateInstance;
import com.baize.ccms.biz.usermanager.domain.User;

/**
 * <p>标题: 栏目</p>
 * <p>描述: 用于存放文章的虚拟文件夹，后面考虑可能不止放文件</p>
 * <p>模块: 通用平台</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 杨信
 * @version 1.0
 * @since 2009-2-26 上午11:37:30
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class Column implements Serializable {

	private static final long serialVersionUID = 2798479418163921866L;

	/** 手动发布 */
	public static final String PUBLISH_WAY_COMMON = "0";
	
	/** 自动发布  */
	public static final String PUBLISH_WAY_AUTO = "1";
	
	/** 定时发布 */
	public static final String PUBLISH_WAY_TIME = "2";
	
	/** 唯一标识符 */
	private String id;
	
	/** 当前网站 */
	private Site site;
	
	/** 是否已删除 */
	private boolean deleted;
	
	/** 是否需要审核 */
	private boolean audited;
	
	/** 栏目名称 */
	private String name;
	
	/** 栏目页访问地址 */
	private String url;
	
	/** 描述 */
	private String description;
	
	/** 自我展示页 */
	private String selfShowPage;
	
	/** 是否在前台显示 */
	private boolean showInFront;
	
	/** 默认绑定格式 */
	private ArticleFormat articleFormat;
	
	/** 显示顺序 */
	private int orders;

	/** 创建人 */
	private User creator;
	
	/** 创建时间 */
	private Date createTime;

	/** 更新时间 */
	private Date updateTime;
	
	/** 父节点 */
	private Column parent;
	
	/** 子栏目 */
	private Set<Column> children;
	
	/** 链接地址 */
	private String linkAddress;
	
	/** 是否是叶子节点 */
	private boolean leaf;
	
	/** 是否选中 */
	private boolean checked;
	
	/** 栏目初始地址 */
	private String initUrl;
	
	/** 栏目页模板 */
	private TemplateInstance columnTemplate;
	
	/** 文章页模板 */
	private TemplateInstance articleTemplate;
	
	/** 栏目信息积分 */
	private String infoScore;
	
	/** 栏目图片积分 */
	private String picScore;
	
	/** 栏目同步->发送开关**/
	private boolean sendMenu;
	
	/** 栏目同步->接收开关**/
	private boolean receiveMenu;
	
	/** 栏目同步->可否修改**/
	private boolean allowModify;
	
	/** 栏目同步->同步栏目**/
	private String refColumnIds;
	
	/** 从父站引用的栏目**/
	private String parentSiteColumnId;
	
	/** 发布方式**/
	private String publishWay;
	
	/** 定时发布时长**/
	private int timeSetting;
	
	/** 栏目类型 多信息栏目multi 单信息栏目single */
	private String columnType;
	
	
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

	public String getSelfShowPage() {
		return selfShowPage;
	}

	public void setSelfShowPage(String selfShowPage) {
		this.selfShowPage = selfShowPage;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public boolean isShowInFront() {
		return showInFront;
	}

	public void setShowInFront(boolean showInFront) {
		this.showInFront = showInFront;
	}

	public Column getParent() {
		return parent;
	}

	public void setParent(Column parent) {
		this.parent = parent;
	}

	public Set<Column> getChildren() {
		return children;
	}

	public void setChildren(Set<Column> children) {
		this.children = children;
	}

	public String getLinkAddress() {
		return linkAddress;
	}

	public void setLinkAddress(String linkAddress) {
		this.linkAddress = linkAddress;
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
	 * @return the order
	 */

	public int getOrders() {
		return orders;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrders(int orders) {
		this.orders = orders;
	}

	/**
	 * @return the leaf
	 */
	public boolean isLeaf() {
		return leaf;
	}

	/**
	 * @param leaf the leaf to set
	 */
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	/**
	 * @return the columnTemplate
	 */
	public TemplateInstance getColumnTemplate() {
		return columnTemplate;
	}
	/**
	 * @param columnTemplate the columnTemplate to set
	 */
	public void setColumnTemplate(TemplateInstance columnTemplate) {
		this.columnTemplate = columnTemplate;
	}
	/**
	 * @return the articleTemplate
	 */
	public TemplateInstance getArticleTemplate() {
		return articleTemplate;
	}
	/**
	 * @param articleTemplate the articleTemplate to set
	 */
	public void setArticleTemplate(TemplateInstance articleTemplate) {
		this.articleTemplate = articleTemplate;
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
	 * @return the checked
	 */
	public boolean isChecked() {
		return checked;
	}

	/**
	 * @param checked the checked to set
	 */
	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	public static void main(String[] args) {
		System.out.println(UUID.randomUUID());
		System.out.println(UUID.randomUUID());
		System.out.println(UUID.randomUUID());
		System.out.println(UUID.randomUUID());
		System.out.println(UUID.randomUUID());
		
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public void setInitUrl(String initUrl) {
		this.initUrl = initUrl;
	}

	public String getInitUrl() {
		return initUrl;
	}

	public void setInfoScore(String infoScore) {
		this.infoScore = infoScore;
	}

	public String getInfoScore() {
		return infoScore;
	}

	public void setPicScore(String picScore) {
		this.picScore = picScore;
	}

	public String getPicScore() {
		return picScore;
	}


	public void setAllowModify(boolean allowModify) {
		this.allowModify = allowModify;
	}

	public boolean isAllowModify() {
		return allowModify;
	}

	public void setRefColumnIds(String refColumnIds) {
		this.refColumnIds = refColumnIds;
	}

	public String getRefColumnIds() {
		return refColumnIds;
	}

	public void setParentSiteColumnId(String parentSiteColumnId) {
		this.parentSiteColumnId = parentSiteColumnId;
	}

	public String getParentSiteColumnId() {
		return parentSiteColumnId;
	}

	public void setSendMenu(boolean sendMenu) {
		this.sendMenu = sendMenu;
	}

	public boolean isSendMenu() {
		return sendMenu;
	}

	public void setReceiveMenu(boolean receiveMenu) {
		this.receiveMenu = receiveMenu;
	}

	public boolean isReceiveMenu() {
		return receiveMenu;
	}

	public void setPublishWay(String publishWay) {
		this.publishWay = publishWay;
	}

	public String getPublishWay() {
		return publishWay;
	}

	public void setTimeSetting(int timeSetting) {
		this.timeSetting = timeSetting;
	}

	public int getTimeSetting() {
		return timeSetting;
	}

	/**
	 * @return the columnType
	 */
	public String getColumnType() {
		return columnType;
	}

	/**
	 * @param columnType the columnType to set
	 */
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}


}
