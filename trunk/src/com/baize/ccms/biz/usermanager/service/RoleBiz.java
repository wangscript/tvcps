 /**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.biz.usermanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.baize.ccms.biz.sitemanager.domain.Site;
import com.baize.ccms.biz.usermanager.domain.Menu;
import com.baize.ccms.biz.usermanager.domain.Operation;
import com.baize.ccms.biz.usermanager.domain.Resource;
import com.baize.ccms.biz.usermanager.domain.Role;
import com.baize.common.core.dao.Pagination;
import com.baize.common.core.service.BaseService;
import com.baize.common.core.util.CollectionUtil;
import com.baize.common.core.util.SqlUtil;
import com.baize.common.core.util.StringUtil;
import com.baize.common.core.web.event.RequestEvent;
import com.baize.common.core.web.event.ResponseEvent;


/**
 * <p>标题: —— 角色业务逻辑处理类.</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 用户管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-2-25 上午11:28:28
 * @history（历次修订内容、修订人、修订时间等） 
 */

public final  class RoleBiz extends BaseService {
	/** 注入角色业务对象. */
	private RoleService roleService;

	@Override
	public void performTask(RequestEvent requestEvent, ResponseEvent responseEvent)
	throws Exception {
		String dealMethod = requestEvent.getDealMethod();
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		// TODO 自动生成方法存根
//		 获取sessionID
		String sessionID = requestEvent.getSessionID();
		
		//网站ID
		String siteId = requestEvent.getSiteid();	
		String userId = sessionID;
		//查询全部数据，供页面列表显示用		
		Role role = new Role();		
		if (dealMethod.equals("")) {	
			//分页显示
			Pagination pagination = (Pagination) requestParam.get("pagination");	
			String treeId = (String)requestParam.get("treeId");
			if(treeId != null && (treeId.equals("") || treeId.equals("0"))){
				/*if(isUpSystemAdmin){
					responseParam.put("pagination",roleService.findAllRolePageData(pagination));
				}else{*/
					responseParam.put("pagination",roleService.findRoleData(pagination,siteId));
				//}
			}else{
				responseParam.put("pagination",roleService.findRoleData(pagination,treeId));
			}
			String setsiteId = "";
			if(treeId == null || treeId.equals("") || treeId.equals("0")){
				setsiteId = siteId;
			}else{
				setsiteId = treeId;
			}
			String roleIds = roleService.findAllSiteAdminBySites();
			responseParam.put("roleIds", roleIds);
			responseParam.put("siteId", setsiteId);
			
			
		}else if (dealMethod.equals("insert")) {
			// 获取参数
			role = (Role) requestParam.get("role");	
			String chooseSiteName = String.valueOf(requestParam.get("siteName"));
			Site site = new Site();
			site = roleService.findSiteBySiteName(chooseSiteName);
			role.setSite(site);
			// 保存页面数据
			roleService.addRoles(role);
			
			// 调用日志写入方法
			String categoryName = "角色管理->添加";
			roleService.addLogs(role.getName(), siteId, sessionID, categoryName);
			String setSiteId = "";
			if(site != null ){
				setSiteId = site.getId();
			}
			responseParam.put("siteId", setSiteId);
			
		}else if (dealMethod.equals("modify")) {
			log.debug("修改角色");
			
			role = (Role) requestParam.get("role");
			Role newrole = roleService.findRoleByKey(role.getId());
			String chooseSiteName = String.valueOf(requestParam.get("siteName"));
			Site site = new Site();
			site = roleService.findSiteBySiteName(chooseSiteName);
			newrole.setSite(site);
			newrole.setDescription(role.getDescription());
			newrole.setName(role.getName());
			roleService.modifyRole(newrole);
			String setSiteId = "";
			if(site != null ){
				setSiteId = site.getId();
			}
			responseParam.put("siteId", setSiteId);
			
			// 调用日志写入方法
			String categoryName = "角色管理->修改";
			roleService.addLogs(newrole.getName(), siteId, sessionID, categoryName);
			
		}else if (dealMethod.equals("delete")) {
			String ids = (String) requestParam.get("ids");		
			String oldsiteId = (String) requestParam.get("siteId");	
			String setsiteId = "";
			if(oldsiteId == null || oldsiteId.equals("") || oldsiteId.equals("0")){
				setsiteId = siteId;
			}else{
				setsiteId = oldsiteId;
			}
			responseParam.put("siteId",setsiteId);	
			String strid[] = ids.split(",");
			String strIds = "";
			int x = 1;
			for(int i = 0 ; i < strid.length; i++){
				//根据主键删除数据
				boolean temp = roleService.findAssignmentByRoleId(strid[i]);
				if(temp == true){
					x = 2;
					responseParam.put("use","true" );
				}	
			//	strIds = strIds + "," + SqlUtil.toSqlString(strid[i]);
			}
			if(x == 1){
				strIds = StringUtil.replaceFirst(strIds,",");
				responseParam.put("use","false" );
				if(ids != null && !ids.equals("")){
					roleService.deleteRoleByIds(ids, siteId, sessionID);
				}				
			}
			
			
			Pagination pagination = (Pagination) requestParam.get("pagination");
			pagination = roleService.findRoleData(pagination,siteId);
			if(pagination.getData().size() == 0){
				pagination.currPage = pagination.currPage - 1;
				pagination = roleService.findRoleData(pagination,siteId);
			}
			responseParam.put("pagination",pagination);
			log.debug("删除角色");		
			
		}else if (dealMethod.equals("findAuthorityData")) {		
			//查询权限设置页面所需要的数据
			//获取角色ID
			String roleId = String.valueOf(requestParam.get("roleid"));		
			List menuList = roleService.findMenuAuthorityByRoleIdAndSiteId(roleId, siteId);
			responseParam.put("allData", (Object[])menuList.get(0));
			responseParam.put("chooseData", (Object[])menuList.get(1));
			responseParam.put("roleId", roleId);
		}else if (dealMethod.equals("setSave")) {			
			log.debug("保存权限设置");
			String menuId = String.valueOf(requestParam.get("menuId"));
	 	
			String roleId = String.valueOf(requestParam.get("roleId"));			
			String strSiteId = roleService.findSiteIdByRoleId(roleId);
			responseParam.put("roleId", roleId);		 
			//执行设置权限方法
			roleService.addSetMenuAuthority(userId, strSiteId, menuId,roleId);
			roleService.modifyUser(userId, strSiteId, roleId);
			
		}else if (dealMethod.equals("detail")) {
			String id = (String) requestParam.get("id");
			String tempSiteId = (String) requestParam.get("siteId");
			log.debug("获取角色表主键id==========="+id);
			role = roleService.findRoleByKey(id);			
			responseParam.put("role",role);	
			List siteList = roleService.findAllSite(siteId,isUpSystemAdmin);
			String userSiteId = role.getSite().getId();
			//所有网站的集合
			responseParam.put("siteList", siteList);
			responseParam.put("userSiteId", userSiteId);
			responseParam.put("siteId", tempSiteId);
		}else if(dealMethod.equals("getSiteId")){				
			Site site = new Site();			
			site = roleService.findSiteBySiteId(siteId);
			responseParam.put("sitename", site.getName());
		}else if(dealMethod.equals("showDetail")){
			//显示增加数据窗口，获取网站名
			Site site = new Site();
			List siteList = roleService.findAllSite(siteId,isUpSystemAdmin);
			String oldsiteId = String.valueOf(requestParam.get("siteId"));
			String setsiteId = "";
			if(oldsiteId == null || oldsiteId.equals("") || oldsiteId.equals("0")){
				setsiteId = siteId;
			}else{
				setsiteId = oldsiteId;
			}
			//所有网站的集合
			responseParam.put("siteList", siteList);
			responseParam.put("siteId", setsiteId);
		}else if(dealMethod.equals("checkRoleName")){
			//回去当前角色名称
			String roleName = String.valueOf(requestParam.get("roleName"));
			String tempSiteId = String.valueOf(requestParam.get("siteId"));
			if(tempSiteId == null || tempSiteId.equals("") || tempSiteId.equals("null")){
				tempSiteId = siteId;
			}
			boolean checkRole = roleService.findRoleByNameAndSiteId(roleName, tempSiteId);
			responseParam.put("checkRole", checkRole);
		}else if(dealMethod.equals("getSiteTree")){		
			//获取左侧的网站树
			String treeId = (String) requestParam.get("treeId");
			List siteList = roleService.findSiteTree(treeId,siteId,isUpSystemAdmin);
			responseParam.put("siteList", siteList);
		}
		
	}

	@Override
	public ResponseEvent validateData(RequestEvent req) throws Exception {
		// TODO 自动生成方法存根
		return null;
	}

	/**
	 * @param roleService 要设置的 roleService
	 */
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
}

