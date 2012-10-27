/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.usermanager.domain;

import java.io.Serializable;

import com.j2ee.cms.biz.sitemanager.domain.Site;

/**
 * <p>标题: 资源</p>
 * <p>描述: 栏目、菜单等需要拥有权限控制的对象的抽象表示，和操作是多对多的关系
 *          和权限是一对多的关系</p>
 * <p>模块: 通用平台</p>
 * <p>版权: Copyright (c) 2009  
 * @author 杨信
 * @version 1.0
 * @since 2009-2-23 下午02:03:15
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class Resource implements Serializable {

	private static final long serialVersionUID = -7327392284216556261L;
	
	/** 栏目类型 */
	public static final String TYPE_COLUMN = "1";
	
	/** 唯一标识符 */
	private String id;
	
	/** 资源的标识号，用于和资源类型组成唯一标识，如栏目ID，菜单ID等 */
	private String identifier;
	
	/** 资源类型，引用Resource的静态常量 */
	private String type;
	
	/** 当前网站 */
	private Site site;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
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

	/**
	 * @return the identifier
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * @param identifier the identifier to set
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

}
