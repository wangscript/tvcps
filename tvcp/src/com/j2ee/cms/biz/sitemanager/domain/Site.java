/**
 * project：通用内容管理系统
 * Company:   
*/
package com.j2ee.cms.biz.sitemanager.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.j2ee.cms.biz.templatemanager.domain.TemplateInstance;
import com.j2ee.cms.biz.usermanager.domain.User;

/**
 * <p>标题: 网站</p>
 * <p>描述: cps中信息承载的最大实体，又主网站和子网站之分</p>
 * <p>模块: 网站管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 杨信
 * @version 1.0
 * @since 2009-3-2 上午10:17:06
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class Site implements Serializable {

	private static final long serialVersionUID = -3535667784348625334L;
	
	/** 网站Map（用于缓存） - key:id   value:site对象*/
	public static Map<String,Site> siteMap = new HashMap<String,Site>();
	
	/** 本地发布 */
	public static final String PUBLISH_LOCAL = "local";
	
	/** socket远程发布 */
	public static final String PUBLISH_REMOTE = "remote";
	
	/** ftp远程发布 */
	public static final String PUBLISH_FTP = "ftp";

	/** 唯一标识符 */
	private String id;
	
	/** 网站名称 */
	private String name;
	
	/** 域名 */
	private String domainName;
	
	/** 生成的页面后缀 */
	private String urlSuffix;
	
	/** 描述 */
	private String description;
	
	/** 网站访问地址 */
	private String url;
	
	/** 创建时间 */
	private Date createTime;
	
	/** 父网站 */
	private Site parent;
	
	/** 子网站 */
	private Set<Site> children;
	
	/** 创建者 */
	private User creator;
	
	/** 主页标题 */
	private String homePageTitle;
	
	/** 页面编码 */
	private String pageEncoding;
	
	/** 发布方式(local/remote) */
	private String publishWay;
	
	/** 若为本地发布，则代表本地发布目录 */
	private String publishDir;
	
	/** 远程接受机IP（远程发布才拥有的属性 ）*/
	private String remoteIP;
	
	/** 远程接收机端口（远程发布才拥有的属性 ） */
	private String remotePort;
	
	/** 是否已删除 */
	private boolean deleted;
	
	/** 栏目页模板 */
	private TemplateInstance columnTemplate;
	
	/** 文章页模板 */
	private TemplateInstance articleTemplate;
	
	/** 网站首页模板 */
	private TemplateInstance homeTemplate;
	
	/** ftp上传时的用户名 */
	private String ftpUserName;
	
	/** ftp上传时的密码 */
	private String ftpPassWord;
	
	/** ftp上传的远程目录 */
	private String ftpFilePath;

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

	public Site getParent() {
		return parent;
	}

	public void setParent(Site parent) {
		this.parent = parent;
	}

	public Set<Site> getChildren() {
		return children;
	}

	public void setChildren(Set<Site> children) {
		this.children = children;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	 * @return the homePageTitle
	 */
	public String getHomePageTitle() {
		return homePageTitle;
	}

	/**
	 * @param homePageTitle the homePageTitle to set
	 */
	public void setHomePageTitle(String homePageTitle) {
		this.homePageTitle = homePageTitle;
	}

	/**
	 * @return the pageEncoding
	 */
	public String getPageEncoding() {
		return pageEncoding;
	}

	/**
	 * @param pageEncoding the pageEncoding to set
	 */
	public void setPageEncoding(String pageEncoding) {
		this.pageEncoding = pageEncoding;
	}

	/**
	 * @return the homeTemplate
	 */
	public TemplateInstance getHomeTemplate() {
		return homeTemplate;
	}

	/**
	 * @param homeTemplate the homeTemplate to set
	 */
	public void setHomeTemplate(TemplateInstance homeTemplate) {
		this.homeTemplate = homeTemplate;
	}

	/**
	 * @return the domainName
	 */
	public String getDomainName() {
		return domainName;
	}

	/**
	 * @param domainName the domainName to set
	 */
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	/**
	 * @return the urlSuffix
	 */
	public String getUrlSuffix() {
		return urlSuffix;
	}

	/**
	 * @param urlSuffix the urlSuffix to set
	 */
	public void setUrlSuffix(String urlSuffix) {
		this.urlSuffix = urlSuffix;
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
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	public String getPublishWay() {
		return publishWay;
	}

	public void setPublishWay(String publishWay) {
		this.publishWay = publishWay;
	}

	public String getPublishDir() {
		return publishDir;
	}

	public void setPublishDir(String publishDir) {
		this.publishDir = publishDir;
	}

	public String getRemoteIP() {
		return remoteIP;
	}

	public void setRemoteIP(String remoteIP) {
		this.remoteIP = remoteIP;
	}

	public String getRemotePort() {
		return remotePort;
	}

	public void setRemotePort(String remotePort) {
		this.remotePort = remotePort;
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
	 * @return the ftpUserName
	 */
	public String getFtpUserName() {
		return ftpUserName;
	}

	/**
	 * @param ftpUserName the ftpUserName to set
	 */
	public void setFtpUserName(String ftpUserName) {
		this.ftpUserName = ftpUserName;
	}

	/**
	 * @return the ftpPassWord
	 */
	public String getFtpPassWord() {
		return ftpPassWord;
	}

	/**
	 * @param ftpPassWord the ftpPassWord to set
	 */
	public void setFtpPassWord(String ftpPassWord) {
		this.ftpPassWord = ftpPassWord;
	}

	/**
	 * @return the ftpFilePath
	 */
	public String getFtpFilePath() {
		return ftpFilePath;
	}

	/**
	 * @param ftpFilePath the ftpFilePath to set
	 */
	public void setFtpFilePath(String ftpFilePath) {
		this.ftpFilePath = ftpFilePath;
	}

}
