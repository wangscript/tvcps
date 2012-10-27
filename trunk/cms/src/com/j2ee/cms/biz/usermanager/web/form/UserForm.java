/**
 * <p>
 * cps 信息管理系统
 * </p>
 * <p>
 * UserForm.java Create on Jan 19, 2009 9:33:41 AM
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008； 
 * </p>
 * <p>
 * Company:  
 * </p>
 */
package com.j2ee.cms.biz.usermanager.web.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.j2ee.cms.biz.usermanager.domain.Organization;
import com.j2ee.cms.biz.usermanager.domain.User;
import com.j2ee.cms.common.core.web.GeneralForm;

/**
 * 
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 用户管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-9-6 下午08:46:54
 * @history（历次修订内容、修订人、修订时间等）
 */

public class UserForm extends GeneralForm{
	
	private static final long serialVersionUID = 6685956108056593979L;
	/**
	 * 用户实体
	 */
	private User user = new User();
	private Organization organization = new Organization();
	private Organization parent = new Organization();
	
	private String[][] userData;
	
	private List list = new ArrayList();
	
	private String ids;
	private String pid;
	//用户的主键ID
	private String id;	
	
 
	private String itemid;
	/** 设置权限所选择的操作ID */
	private String operationid;
	/** 角色ID */
	private String roleid;
	/** 页面上所有栏目节点的集合 */
	private String strColumn ;	
	/** 设置下级栏目是否继承上级栏目的权限 */
	private String setChild;
	/** 所有角色的集合 */
	private List allrolelist = new ArrayList();
	/** 所有已选择的角色的集合 */
	private List chooserolelist = new ArrayList();
	
	/** 所有角色的集合 */
	private Object allData[];
	/** 所有已选择的角色的集合 */
	private Object chooseData[];
	/** 角色页面所有选择的id */
	private String checkedTreeIds;
	
	/**设置用户角色权限时系统管理员的ID*/
	private String systemcheckvalue;
	
	private String loginName;
	
	private String userIds;
	
	/** 操作类型 */
	private String operationType;
	/** 网站集合 */
	private List siteList;
	/** 网站ID */
	private String siteId;
	/** 页面选择的网站ID */
	private String changeSiteId;
	/**树的url */
	private String treeUrl;
	
	/**功能单元树的url */
	private String pluginTreeUrl;
	/**类型,现在有两种类型，一种是功能单元，一种是系统设置 */
	private String type;
	
	private List systemSetList;
	/**系统设置菜单id */
	private String setSystemId;
	/**功能单元菜单id */
	private String setPluginId;
	
	private String isSetRole;
	
	/** 当前在线用户 */
	private List<Object> currentLineUserList = new ArrayList<Object>();
	
	/** 当前在线用户数目*/
	private int currentLineCount;
	
	/** 是否继承自上级权限*/	
	private boolean columnExtends;
	@Override
	protected void validateData(ActionMapping mapping, HttpServletRequest request) {
		
	}
	public UserForm(){
		if (this.getUser() == null ) 
			this.setUser(new User()); 
		if (this.getOrganization() == null ) 
			this.setOrganization(new Organization()); 
		if (this.getParent() == null ) 
			this.setParent(new Organization()); 

	}
	/**
	 * @return user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user 要设置的 user
	 */
	public void setUser(User user) {
		this.user = user;
	}

	public String[][] getUserData() {
		return userData;
	}

	public void setUserData(String[][] userData) {
		this.userData = userData;
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
	 * @return pid
	 */
	public String getPid() {
		return pid;
	}

	/**
	 * @param pid 要设置的 pid
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}
	 
	/**
	 * @return the itemid
	 */
	public String getItemid() {
		return itemid;
	}
	/**
	 * @param itemid the itemid to set
	 */
	public void setItemid(String itemid) {
		this.itemid = itemid;
	}
	/**
	 * @return the operationid
	 */
	public String getOperationid() {
		return operationid;
	}
	/**
	 * @param operationid the operationid to set
	 */
	public void setOperationid(String operationid) {
		this.operationid = operationid;
	}
	/**
	 * @return the roleid
	 */
	public String getRoleid() {
		return roleid;
	}
	/**
	 * @param roleid the roleid to set
	 */
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	/**
	 * @return the strColumn
	 */
	public String getStrColumn() {
		return strColumn;
	}
	/**
	 * @param strColumn the strColumn to set
	 */
	public void setStrColumn(String strColumn) {
		this.strColumn = strColumn;
	}
	/**
	 * @return the setChild
	 */
	public String getSetChild() {
		return setChild;
	}
	/**
	 * @param setChild the setChild to set
	 */
	public void setSetChild(String setChild) {
		this.setChild = setChild;
	}
	/**
	 * @return the allrolelist
	 */
	public List getAllrolelist() {
		return allrolelist;
	}
	/**
	 * @param allrolelist the allrolelist to set
	 */
	public void setAllrolelist(List allrolelist) {
		this.allrolelist = allrolelist;
	}
	/**
	 * @return the chooserolelist
	 */
	public List getChooserolelist() {
		return chooserolelist;
	}
	/**
	 * @param chooserolelist the chooserolelist to set
	 */
	public void setChooserolelist(List chooserolelist) {
		this.chooserolelist = chooserolelist;
	}
	/**
	 * @return the allData
	 */
	public Object[] getAllData() {
		return allData;
	}
	/**
	 * @param allData the allData to set
	 */
	public void setAllData(Object[] allData) {
		this.allData = allData;
	}
	/**
	 * @return the chooseData
	 */
	public Object[] getChooseData() {
		return chooseData;
	}
	/**
	 * @param chooseData the chooseData to set
	 */
	public void setChooseData(Object[] chooseData) {
		this.chooseData = chooseData;
	}
	/**
	 * @return the checkedTreeIds
	 */
	public String getCheckedTreeIds() {
		return checkedTreeIds;
	}
	/**
	 * @param checkedTreeIds the checkedTreeIds to set
	 */
	public void setCheckedTreeIds(String checkedTreeIds) {
		this.checkedTreeIds = checkedTreeIds;
	}
	/**
	 * @return the systemcheckvalue
	 */
	public String getSystemcheckvalue() {
		return systemcheckvalue;
	}
	/**
	 * @param systemcheckvalue the systemcheckvalue to set
	 */
	public void setSystemcheckvalue(String systemcheckvalue) {
		this.systemcheckvalue = systemcheckvalue;
	}
	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}
	/**
	 * @param loginName the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
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
	 * @return the userIds
	 */
	public String getUserIds() {
		return userIds;
	}
	/**
	 * @param userIds the userIds to set
	 */
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
	/**
	 * @return the operationType
	 */
	public String getOperationType() {
		return operationType;
	}
	/**
	 * @param operationType the operationType to set
	 */
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	/**
	 * @return the siteList
	 */
	public List getSiteList() {
		return siteList;
	}
	/**
	 * @param siteList the siteList to set
	 */
	public void setSiteList(List siteList) {
		this.siteList = siteList;
	}
	/**
	 * @return the siteId
	 */
	public String getSiteId() {
		return siteId;
	}
	/**
	 * @param siteId the siteId to set
	 */
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	/**
	 * @return the changeSiteId
	 */
	public String getChangeSiteId() {
		return changeSiteId;
	}
	/**
	 * @param changeSiteId the changeSiteId to set
	 */
	public void setChangeSiteId(String changeSiteId) {
		this.changeSiteId = changeSiteId;
	}
	/**
	 * @return the treeUrl
	 */
	public String getTreeUrl() {
		return treeUrl;
	}
	/**
	 * @param treeUrl the treeUrl to set
	 */
	public void setTreeUrl(String treeUrl) {
		this.treeUrl = treeUrl;
	}
	/**
	 * @return the systemSetList
	 */
	public List getSystemSetList() {
		return systemSetList;
	}
	/**
	 * @param systemSetList the systemSetList to set
	 */
	public void setSystemSetList(List systemSetList) {
		this.systemSetList = systemSetList;
	}
	/**
	 * @return the pluginTreeUrl
	 */
	public String getPluginTreeUrl() {
		return pluginTreeUrl;
	}
	/**
	 * @param pluginTreeUrl the pluginTreeUrl to set
	 */
	public void setPluginTreeUrl(String pluginTreeUrl) {
		this.pluginTreeUrl = pluginTreeUrl;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the setSystemId
	 */
	public String getSetSystemId() {
		return setSystemId;
	}
	/**
	 * @param setSystemId the setSystemId to set
	 */
	public void setSetSystemId(String setSystemId) {
		this.setSystemId = setSystemId;
	}
	/**
	 * @return the setPluginId
	 */
	public String getSetPluginId() {
		return setPluginId;
	}
	/**
	 * @param setPluginId the setPluginId to set
	 */
	public void setSetPluginId(String setPluginId) {
		this.setPluginId = setPluginId;
	}
	/**
	 * @return the isSetRole
	 */
	public String getIsSetRole() {
		return isSetRole;
	}
	/**
	 * @param isSetRole the isSetRole to set
	 */
	public void setIsSetRole(String isSetRole) {
		this.isSetRole = isSetRole;
	}
	/**
	 * @return the currentLineUserList
	 */
	public List<Object> getCurrentLineUserList() {
		return currentLineUserList;
	}
	/**
	 * @param currentLineUserList the currentLineUserList to set
	 */
	public void setCurrentLineUserList(List<Object> currentLineUserList) {
		this.currentLineUserList = currentLineUserList;
	}
	/**
	 * @return the currentLineCount
	 */
	public int getCurrentLineCount() {
		return currentLineCount;
	}
	/**
	 * @param currentLineCount the currentLineCount to set
	 */
	public void setCurrentLineCount(int currentLineCount) {
		this.currentLineCount = currentLineCount;
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
