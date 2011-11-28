/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.biz.usermanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.baize.ccms.biz.sitemanager.domain.Site;
import com.baize.ccms.biz.usermanager.domain.Organization;
import com.baize.ccms.biz.usermanager.domain.User;
import com.baize.common.core.dao.Pagination;
import com.baize.common.core.service.BaseService;
import com.baize.common.core.util.StringUtil;
import com.baize.common.core.web.event.RequestEvent;
import com.baize.common.core.web.event.ResponseEvent;

/**
 * 
 * <p>标题: —— 用户管理业务逻辑处理类</p>
 * <p>描述: —— 调用用户管理的service处理相应的action请求</p>
 * <p>模块: 用户管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-2-23 下午04:50:17
 * @history（历次修订内容、修订人、修订时间等）
 */
public  final  class UserBiz extends BaseService {
	
	/** 注入用户业务对象 */
	private UserService userService;

	/**
	 * 业务处理
	 */
	@SuppressWarnings("unchecked")
	public void performTask(RequestEvent requestEvent, ResponseEvent responseEvent)
			throws Exception {
		String dealMethod = requestEvent.getDealMethod();
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		String sessionID = requestEvent.getSessionID();
		log.info("sessionid========================"+sessionID);
		//网站ID
		String siteId = requestEvent.getSiteid();	
		String userId = sessionID;
		//创建用户对象
		User user = new User();
		
		if (dealMethod.equals("")) {
			//查询全部数据，供页面列表显示用
			Pagination pagination = (Pagination) requestParam.get("pagination");
			String nodeid = (String) requestParam.get("nodeid");
		//	responseParam.put("pagination",userService.findUserData(pagination,nodeid));
			responseParam.put("nodeid", nodeid);
			if(nodeid != null) {
				if(isUpSystemAdmin == true){
					pagination = userService.findUserDataByOrgId(pagination,nodeid,userId);
					if(pagination.getData().size()==0){
						pagination.currPage = pagination.currPage-1;
						pagination = userService.findUserDataByOrgId(pagination,nodeid,userId);
					}
					responseParam.put("pagination",pagination);
				}else {
					pagination = userService.findUserDataByOrgIdAndSiteId(pagination,nodeid,siteId,userId);
					if(pagination.getData().size()==0){
						pagination.currPage = pagination.currPage-1;
						pagination = userService.findUserDataByOrgIdAndSiteId(pagination,nodeid,siteId,userId);
					}
					responseParam.put("pagination",pagination);
				}
			}else{
				if(isUpSystemAdmin == true){
					pagination = userService.findUserData(pagination,userId);
					if(pagination.getData().size() == 0){
						pagination.currPage = pagination.currPage-1;
						pagination = userService.findUserData(pagination,userId);
					}
					responseParam.put("pagination", pagination);
				}else {
					pagination = userService.findUserDataAndSiteId(pagination,siteId,userId);
					if(pagination.getData().size() == 0){
						pagination.currPage = pagination.currPage - 1;
						pagination = userService.findUserDataAndSiteId(pagination,siteId,userId);
					}
					responseParam.put("pagination", pagination);
				}			
			}
			String userIds = userService.findAllSiteAdminBySiteIds();
			responseParam.put("userIds", userIds);
					
		}else if (dealMethod.equals("insert")) {
			String organizationId = String.valueOf(requestParam.get("organizationId"));
			user = (User) requestParam.get("user");
			userService.addUser(organizationId, user, siteId, sessionID);
			log.info("添加用户操作完成");	
			
		}else if (dealMethod.equals("modify")) {
			log.info("修改用户");
			user = (User) requestParam.get("user");
			userService.modifyUser(user, siteId, sessionID);
			log.info("修改用户操作完成");
					
		}else if (dealMethod.equals("delete")) {
			log.info("删除用户");
			String ids = (String) requestParam.get("ids");	
			userService.deleteUserByIds(ids, siteId, sessionID);
			log.info("删除用户操作完成");
			
		}else if (dealMethod.equals("detail")) {
			log.info("查找用户详细信息");
			String id = (String) requestParam.get("id");
			log.debug("获取用户表主键id==========="+id);
			user = userService.findUserByKey(id);			
			responseParam.put("user",user);	

		}else if (dealMethod.equals("findRole")) {
			//查询角色权限设置页面所需要的数据
			//用户ID
			String id = (String) requestParam.get("id");
			//将用户ID放到响应对象
			responseParam.put("id", id);	
			Map map = userService.findRole(id, isUpSystemAdmin, isSiteAdmin, siteId, userId);
			responseParam.put("allRoleData", map.get("allRoleData"));
			responseParam.put("chooseRoleData", map.get("chooseRoleData"));
		
		//查询栏目权限设置页面所需要的数据	
		}else if (dealMethod.equals("findColumn")) {
			//用户ID
			String setUserId = (String) requestParam.get("id"); 
			User setuser = userService.findUserByKey(setUserId);			
			String setsiteId = (String) requestParam.get("siteId");		
			String findColumn = "2"; 
			List siteList = userService.findSiteListBySiteIds(setuser.getSiteIds(),siteId,setuser,findColumn,isUpSystemAdmin);
			if(setsiteId != null && !setsiteId.equals("")){
				siteId = setsiteId;
			}else{
		  		if(siteList != null && siteList.size() > 0 ){
					Site site = (Site)siteList.get(0);
					siteId = site.getId();
				}
			}
			
			String treeUrl = "user.do?dealMethod=getColumnTree&id="+setUserId+"&siteId="+siteId+"";
			responseParam.put("siteList",siteList); 
			//将用户ID放到响应对象
			responseParam.put("id", setUserId);
			responseParam.put("siteId", siteId);
			responseParam.put("treeUrl", treeUrl);	
			
		} else if(dealMethod.equals("operationlist")) {
			//根据用户ID和栏目节点ID查询所有的操作成功			
			String itemid = (String) requestParam.get("itemid");
			//需要设置的用户ID
			String userid = String.valueOf(requestParam.get("userid"));			
			String setsiteId = String.valueOf(requestParam.get("siteId"));	
			String sSiteId = "";
			if(setsiteId != null && !setsiteId.equals("") && !setsiteId.equals("null")){
				sSiteId = setsiteId;
			}else{
				sSiteId = siteId;
			}
			Map map = userService.findoperationlist(itemid, userid, sSiteId);
			log.debug("根据角色和栏目节点ID查询所有的操作成功");
			responseParam.put("roleid", map.get("roleid"));
			responseParam.put("alloperationlist",map.get("allData"));
			//查询所有已有的操作
			responseParam.put("chooseoperationlist", map.get("chooseData"));
			responseParam.put("columnExtends", map.get("columnExtends"));
			
			responseParam.put("siteId", sSiteId);
		} else if(dealMethod.equals("setRoleSave")) {
			//保存角色权限设置
			String checkedTreeIds = (String)(requestParam.get("checkedTreeIds"));
			String userid = (String)(requestParam.get("userId"));	
			String systemcheckvalue = (String)(requestParam.get("systemcheckvalue"));
			List assignmentList = userService.findRoleIdByUserId(userid);
			userService.deleteAssignment(assignmentList);
			userService.addAssignment(checkedTreeIds, userid, userId,systemcheckvalue);
			
			
			String categoryName = "用户管理(角色权限)->保存";
			userService.addLogs(siteId, sessionID, categoryName, userid);
			log.debug("保存角色权限设置成功!");
			
		}else if(dealMethod.equals("setColumnSave")) {
			//保存栏目权限设置
			String operationId = String.valueOf(requestParam.get("operationid"));
			String itemId = String.valueOf(requestParam.get("itemid"));				
			String setChild = String.valueOf(requestParam.get("setChild"));		
			String userid = String.valueOf(requestParam.get("userid")); 
			String setsiteId = String.valueOf(requestParam.get("siteId"));	
			if(setsiteId != null && !setsiteId.equals("") && !setsiteId.equals("null")){
				siteId = setsiteId;
			}
			//执行设置权限方法
			userService.addsetSave(userId, siteId, operationId, itemId, setChild, userid);
			log.debug("保存栏目权限设置成功!");
			
			
		}else if(dealMethod.equals("getColumnTree")){
			//获取栏目树
			String columnId = (String) requestParam.get("treeId");
			String setuserId = (String) requestParam.get("userId");
			String setsiteId = (String) requestParam.get("siteId");
		    List columnList = userService.findColumnTreeBySiteIdAndColumnId(setsiteId, columnId, setuserId, isUpSystemAdmin, isSiteAdmin);
		    responseParam.put("columnList", columnList);
		
		}else if (dealMethod.equals("showDetail")){
 			//显示新增对话框
			String orgid = (String) requestParam.get("nodeid");
			Organization org = userService.findOrganizationByKey(orgid);			
			responseParam.put("organization",org);	
			responseParam.put("nodeid", orgid);
			
 		}else if(dealMethod.equals("checkLoginName")){
 			//检测登录名
 			String loginName = String.valueOf(requestParam.get("loginName"));
 			boolean check = userService.findUserByLoginName(loginName);
 			responseParam.put("check",check);
		    
 		}else if (dealMethod.equals("personalInfoDetail")){
 			//当前用户信息
 			user = userService.findUserByKey(userId);
			responseParam.put("user",user);	
			
 		}else if (dealMethod.equals("modifyPersonalInfo")){
 			//修改当前用户信息
 			user = (User) requestParam.get("person");
			userService.modifyUser(user, siteId, sessionID);
 		}else if(dealMethod.equals("currentLineUser")) {
 			// 当前在线用户
 			List<Object> list = new ArrayList<Object>();
 			list = userService.getCurrentLineUser(list);
 			responseParam.put("list", list);
 		}
	}
	
	
	@Override
	public ResponseEvent validateData(RequestEvent req) throws Exception {
		return null;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}


}
