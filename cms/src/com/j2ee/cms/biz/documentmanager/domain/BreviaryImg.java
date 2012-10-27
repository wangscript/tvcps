/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.documentmanager.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>标题: —— 缩略图</p>
 * <p>描述: —— 实现图片缩放功能</p>
 * <p>模块: CPS文档管理模块</p>
 * <p>版权: Copyright (c) 2009  </p>
 * <p>网址：http://www.j2ee.cmsweb.com
 * @author 曹名科
 * @version 1.0
 * @since 2009-9-29 上午11:37:21
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class BreviaryImg implements Serializable {
	private static final long serialVersionUID = 4401071303969644004L;
	/**缩略图id.*/
	private String id;
	/**缩略图名称.*/
	private String imgName;
	/**缩略图地址.*/
	private String imgPath;
	/**创建时间.*/
	private Date createTime;
	/**文档对象.*/
	private Document documents;
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
	 * @return the imgName
	 */
	public String getImgName() {
		return imgName;
	}
	/**
	 * @param imgName the imgName to set
	 */
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	/**
	 * @return the imgPath
	 */
	public String getImgPath() {
		return imgPath;
	}
	/**
	 * @param imgPath the imgPath to set
	 */
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
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
	 * @return the documents
	 */
	public Document getDocuments() {
		return documents;
	}
	/**
	 * @param documents the documents to set
	 */
	public void setDocuments(Document documents) {
		this.documents = documents;
	}
	
}
