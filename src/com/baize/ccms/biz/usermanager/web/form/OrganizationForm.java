  /**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.biz.usermanager.web.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.baize.ccms.biz.usermanager.domain.Organization;
import com.baize.common.core.web.GeneralForm;

/**
 * <p>标题: —— 机构管理form</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 用户管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-2-16 下午03:13:24
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class OrganizationForm extends GeneralForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2585433256800429859L;
	private Organization organization = new Organization();
	private Organization parent = new Organization();
	private List list = new ArrayList();
	private String pid;
	private String pname;
	private String ids;
	private String id;
	private String orgNameList;
	private String orgIds;
	private String checkid;
	private String orgNames;
	//临时参数
	private String temp;
	/**用于转发和回复时得到的消息信息*/



	/**
	 * @return ids
	 */
	public String getIds() {
		return ids;
	}

	/**
	 * @param ids 要设置的 ids
	 */
	public void setIds(String ids) {
		this.ids = ids;
	}

	/**
	 * @return organization
	 */
	public Organization getOrganization() {
		return organization;
	}

	/**
	 * @param organization 要设置的 organization
	 */
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@Override
	protected void validateData(ActionMapping mapping, HttpServletRequest request) {
		// TODO 自动生成方法存根
		
	}

	/**
	 * @return list
	 */
	public List getList() {
		return list;
	}

	/**
	 * @param list 要设置的 list
	 */
	public void setList(List list) {
		this.list = list;
	}


	/**
	 * @return parent
	 */
	public Organization getParent() {
		return parent;
	}

	/**
	 * @param parent 要设置的 parent
	 */
	public void setParent(Organization parent) {
		this.parent = parent;
	}

	/**
	 * @return the orgNameList
	 */
	public String getOrgNameList() {
		return orgNameList;
	}

	/**
	 * @param orgNameList the orgNameList to set
	 */
	public void setOrgNameList(String orgNameList) {
		this.orgNameList = orgNameList;
	}

	/**
	 * @return the pname
	 */
	public String getPname() {
		return pname;
	}

	/**
	 * @param pname the pname to set
	 */
	public void setPname(String pname) {
		this.pname = pname;
	}

	/**
	 * @return the orgIds
	 */
	public String getOrgIds() {
		return orgIds;
	}

	/**
	 * @param orgIds the orgIds to set
	 */
	public void setOrgIds(String orgIds) {
		this.orgIds = orgIds;
	}

	/**
	 * @return the checkid
	 */
	public String getCheckid() {
		return checkid;
	}

	/**
	 * @param checkid the checkid to set
	 */
	public void setCheckid(String checkid) {
		this.checkid = checkid;
	}

	/**
	 * @return the orgNames
	 */
	public String getOrgNames() {
		return orgNames;
	}

	/**
	 * @param orgNames the orgNames to set
	 */
	public void setOrgNames(String orgNames) {
		this.orgNames = orgNames;
	}

	/**
	 * @return the pid
	 */
	public String getPid() {
		return pid;
	}

	/**
	 * @param pid the pid to set
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}

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
	 * @return the temp
	 */
	public String getTemp() {
		return temp;
	}

	/**
	 * @param temp the temp to set
	 */
	public void setTemp(String temp) {
		this.temp = temp;
	}

}
