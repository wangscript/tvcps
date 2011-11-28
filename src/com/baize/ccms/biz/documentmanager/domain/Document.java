/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.biz.documentmanager.domain;

import java.io.Serializable;
import java.util.Date;

import com.baize.ccms.biz.sitemanager.domain.Site;
import com.baize.ccms.biz.usermanager.domain.User;

/**
 * <p>标题: 文档</p>
 * <p>描述: 包括图片、视频、附件、...</p>
 * <p>模块: 文档管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-3-23 上午09:43:12
 * @history（历次修订内容、修订人、修订时间等）
 */
public class Document implements Serializable {
	
	private static final long serialVersionUID = 2428060856026884124L;
	
	/** 上传后文档的路径正则表达式 */
	public static final String REGX_UPLOAD_PATH = "upload/[^<>.]+\\.[a-zA-Z]{2,4}";

	/** 唯一标识符 */
	private String id;
	
	/** 文档名称 */
	private String name;
	
	/** 文档类别 */
	private DocumentCategory documentCategory;
	
	/** 文件名称 */
	private String fileName;
	
	/** 文件后缀 */
	private String fileSuffix;
	
	/** 描述 */
	private String description;
	
	/** 可以直接访问到的全名地址 */
	private String url; 
	
	/** 本地相对于应用的相对路径 */
	private String localPath;
	/**图片目录路径*/
	private String picDirectoryPath;

	/** 创建者 */
	private User creator;
	
	/** 文件大小 */
	private String fileSize;
	
	/** 创建时间 */
	private Date createTime;
	
	/** 当前网站 */
	private Site site;
	
	/** 是否被已被删除 */
	private boolean deleted;
	
	/** 文档类别鉴别器*/
	private String type;
	

	/**
	 * @return the type 获得文档的类型
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set 设置文档的类别
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the id 获得文档的id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set 设置文档的id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name 获得文档的名字
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set 设置文档的名字
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the fileName 获得文档的全名
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set 设置文档的全名
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the url 获得文档的地址
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set  设置文档的地址
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the localPath 获得文档的本地地址
	 */
	public String getLocalPath() {
		return localPath;
	}

	/**
	 * @param localPath the localPath to set 设置文档的本地地址
	 */
	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	/**
	 * @return the creator 获得文档的创建者
	 */
	public User getCreator() {
		return creator;
	}

	/**
	 * @param creator the creator to set 设置文档的创建者
	 */
	public void setCreator(User creator) {
		this.creator = creator;
	}

	/**
	 * @return the fileSize 获得文档的大小
	 */
	public String getFileSize() {
		return fileSize;
	}

	/**
	 * @param fileSize the fileSize to set 设置文档的大小
	 */
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	/**
	 * @return the site 获得文档属于的网站
	 */
	public Site getSite() {
		return site;
	}

	/**
	 * @param site the site to set 设置文档属于的网站
	 */
	public void setSite(Site site) {
		this.site = site;
	}

	/**
	 * @return the deleted 获得文档是否删除
	 */
	public boolean isDeleted() {
		return deleted;
	}

	/**
	 * @param deleted the deleted to set 设置文档是否删除
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	/**
	 * @return the description 获得文档的描述
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set 设置文档的描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the createTime 获得文档创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set 设置文档的创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * @return the picDirectoryPath
	 */
	public String getPicDirectoryPath() {
		return picDirectoryPath;
	}

	/**
	 * @param picDirectoryPath the picDirectoryPath to set
	 */
	public void setPicDirectoryPath(String picDirectoryPath) {
		this.picDirectoryPath = picDirectoryPath;
	}
	/**
	 * @return the documentCategory
	 */
	public DocumentCategory getDocumentCategory() {
		return documentCategory;
	}

	/**
	 * @param documentCategory the documentCategory to set
	 */
	public void setDocumentCategory(DocumentCategory documentCategory) {
		this.documentCategory = documentCategory;
	}

	/**
	 * @return the fileSuffix
	 */
	public String getFileSuffix() {
		return fileSuffix;
	}

	/**
	 * @param fileSuffix the fileSuffix to set
	 */
	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
	}
	
}
