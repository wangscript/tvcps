
  /**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.usermanager.web.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.j2ee.cms.biz.usermanager.domain.Menu;
import com.j2ee.cms.biz.usermanager.domain.Role;
import com.j2ee.cms.common.core.web.GeneralForm;

/**
 * <p>标题: —— 角色ACTION处理类</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 用户管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-2-25 下午02:26:58
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class RoleForm extends GeneralForm{


	/**
	 * 
	 */
	private static final long serialVersionUID = -8396302933722922797L;

	/** 角色对象 */
	private Role role = new Role();
	
	/** 菜单对象 */
	private Menu menu = new Menu();

	/** ids页面穿过来的ID */
	private String ids;
	
	/** 页面显示的list */
	private List list = new ArrayList();
	
	/** 权限操作页面栏目显示的list */
	private List columnList  = new ArrayList();
	
	/** 页面显示的所有的操作list */
	private List alloperationlist = new ArrayList();
	
	/** 权限操作页面所选择的菜单list */
	private List choosemenulist  = new ArrayList();
	
	/** 权限操作页面所选择的操作list */
	private List chooseoperationlist  = new ArrayList();

	/** role表的主键id */
	private String id;	
	
	/** 栏目节点ID */
	private String itemid;
	
	/** 设置权限所选择的菜单ID */
	private String strmenuid;
	/** 设置权限所选择的操作ID */
	private String operationid;
	/** 角色ID */
	private String roleid;
	/** 页面上所有栏目节点的集合 */
	private String strcolumn ;

	/** 页面的操作，根据此操作返回页面的弹出框消息*/
	private String op;
	
	/** 设置下级栏目是否继承上级栏目的权限 */
	private String setChild;
	
	/** 网站名 */
	private String siteName;
	
	/** 网站ID */
	private String siteId;
	
	/**当前角色名 */
	private String roleName;
	
	/** 这个角色是否已经被使用 */
	private String use;
	
	/** 所有的角色ID */
	private String roleIds;
	
	/** 操作类型 */
	private String operationType;
	
	private String tempSiteId;
	/** 所有菜单 */
	private Object[] allData;
	/** 选择的菜单 */
	private Object[] chooseData;
	
	/**
	 * @return the op
	 */
	public String getOp() {
		return op;
	}

	/**
	 * @param op the op to set
	 */
	public void setOp(String op) {
		this.op = op;
	}

	/**
	 * @return role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * @param role 要设置的 role
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	
	@Override
	protected void validateData(ActionMapping mapping, HttpServletRequest request) {
		// TODO 自动生成方法存根
		
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
	 * @return menu
	 */
	public Menu getMenu() {
		return menu;
	}

	/**
	 * @param menu 要设置的 menu
	 */
	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	/**
	 * @return the columnList
	 */
	public List getColumnList() {
		return columnList;
	}

	/**
	 * @param columnList the columnList to set
	 */
	public void setColumnList(List columnList) {
		this.columnList = columnList;
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
	 * @return the choosemenulist
	 */
	public List getChoosemenulist() {
		return choosemenulist;
	}

	/**
	 * @param choosemenulist the choosemenulist to set
	 */
	public void setChoosemenulist(List choosemenulist) {
		this.choosemenulist = choosemenulist;
	}

	/**
	 * @return the chooseoperationlist
	 */
	public List getChooseoperationlist() {
		return chooseoperationlist;
	}

	/**
	 * @param chooseoperationlist the chooseoperationlist to set
	 */
	public void setChooseoperationlist(List chooseoperationlist) {
		this.chooseoperationlist = chooseoperationlist;
	}

	/**
	 * @return the alloperationlist
	 */
	public List getAlloperationlist() {
		return alloperationlist;
	}

	/**
	 * @param alloperationlist the alloperationlist to set
	 */
	public void setAlloperationlist(List alloperationlist) {
		this.alloperationlist = alloperationlist;
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
	 * @return the strcolumn
	 */
	public String getStrcolumn() {
		return strcolumn;
	}

	/**
	 * @param strcolumn the strcolumn to set
	 */
	public void setStrcolumn(String strcolumn) {
		this.strcolumn = strcolumn;
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
	 * @return the siteName
	 */
	public String getSiteName() {
		return siteName;
	}

	/**
	 * @param siteName the siteName to set
	 */
	public void setSiteName(String siteName) {
		this.siteName = siteName;
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
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
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
	 * @return the use
	 */
	public String getUse() {
		return use;
	}

	/**
	 * @param use the use to set
	 */
	public void setUse(String use) {
		this.use = use;
	}

	/**
	 * @return the strmenuid
	 */
	public String getStrmenuid() {
		return strmenuid;
	}

	/**
	 * @param strmenuid the strmenuid to set
	 */
	public void setStrmenuid(String strmenuid) {
		this.strmenuid = strmenuid;
	}

	/**
	 * @return the roleIds
	 */
	public String getRoleIds() {
		return roleIds;
	}

	/**
	 * @param roleIds the roleIds to set
	 */
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
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
	 * @return the tempSiteId
	 */
	public String getTempSiteId() {
		return tempSiteId;
	}

	/**
	 * @param tempSiteId the tempSiteId to set
	 */
	public void setTempSiteId(String tempSiteId) {
		this.tempSiteId = tempSiteId;
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



}