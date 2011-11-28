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
 * <p>标题: 水印</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 文档管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司</p>
 * <p>网址：http://www.baizeweb.com
 * @author <a href="mailto:sean_yang@163.com">杨信</a>
 * @version 1.0
 * @since 2009-9-1 上午11:53:03
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class Watermark implements Serializable {
	
	private static final long serialVersionUID = -8258973111269901510L;

	/** 唯一标识符 */
	private String id;
	
	/** 水印名称 */
	private String name;
	
	/** 透明度 */
	private String transparency;
	
	/** 水印位置 */
	private String position;
	
	/** 边距 */
	private String boder;
	
	/** 创建时间 */
	private Date createTime;
	
	/** 创建者 */
	private User creator;
	
	/**　是否默认的 */
	private boolean defaulted;
	
	/** 当前网站 */
	private Site site;

	public boolean isDefaulted() {
		return defaulted;
	}

	public void setDefaulted(boolean defaulted) {
		this.defaulted = defaulted;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getBoder() {
		return boder;
	}

	public void setBoder(String boder) {
		this.boder = boder;
	}

	public String getTransparency() {
		return transparency;
	}

	public void setTransparency(String transparency) {
		this.transparency = transparency;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
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
	
}
