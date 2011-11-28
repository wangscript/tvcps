/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.biz.usermanager.domain;

import java.io.Serializable;

/**
 * <p>标题: 权限</p>
 * <p>描述: 一个权限对应多个授权，一个权限里又包含多个资源和操作</p>
 * <p>模块: 通用平台</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since Feb 15, 2009 3:12:51 PM
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class Authority implements Serializable {

	private static final long serialVersionUID = -7356403312621469711L;

	/** 唯一标识 */
	private String id;
	
	/** 资源 */
	private Resource resource;
	
	/** 操作 */
	private Operation operation;
	
	/** 设置下级栏目是否继承自上级栏目权限 */
	private boolean columnExtends;

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
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
	 * @return the columnExtends
	 */
	public boolean isColumnExtends() {
		return columnExtends;
	}

	/**
	 * @param columnExtends the columnExtends to set
	 */
	public void setColumnExtends(boolean columnExtends) {
		this.columnExtends = columnExtends;
	}
	
}
