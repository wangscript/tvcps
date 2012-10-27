/**
 * <p>
 * cps_2 信息管理系统
 * </p>
 * <p>
 * UserAction.java Create on Jan 19, 2009 9:33:34 AM
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008； 
 * </p>
 * <p>
 * Company:  
 * </p>
 */
package com.j2ee.cms.biz.usermanager.web.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts.action.ActionForm;

import com.j2ee.cms.biz.usermanager.domain.Organization;
import com.j2ee.cms.biz.usermanager.domain.User;
import com.j2ee.cms.biz.usermanager.web.form.UserForm;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.web.GeneralAction;
import com.j2ee.cms.common.core.web.event.RequestEvent;
import com.j2ee.cms.common.core.web.event.ResponseEvent;

/**
 * 
 * <p>标题: —— 用户管理action类</p>
 * <p>描述: —— 负责处理页面请求，如显示用户，删除，修改，增加用户</p>
 * <p>模块: 用户管理</p>
 * <p>版权: Copyright (c) 2009 
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-2-23 下午04:40:19
 * @history（历次修订内容、修订人、修订时间等）
 */
public final  class UserAction extends GeneralAction{
	private String dealMethod = "";
	@SuppressWarnings("unchecked")
	@Override
	protected void doFormFillment(ActionForm actionForm, ResponseEvent responseEvent, String userIndr) {
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		
		UserForm form = (UserForm) actionForm;		
		if (dealMethod.equals("")) {	
			log.debug("显示用户列表");
			form.setPagination((Pagination) responseParam.get("pagination"));
			form.setNodeId((String) responseParam.get("nodeid"));
			form.setUserIds((String) responseParam.get("userIds"));
			this.setRedirectPage("success", userIndr);
		}else if (dealMethod.equals("insert")) {			
			log.debug("增加用户成功!");
			form.setInfoMessage("增加用户成功");
			this.setRedirectPage("addsuccess", userIndr);
			
		}else if (dealMethod.equals("delete")) {
			log.debug("删除用户成功!");
			form.setDealMethod("delete");
			this.setRedirectPage("return", userIndr);
			
		}else if (dealMethod.equals("detail")) {	
			log.debug("显示用户详细信息");		
			form.setDealMethod("detail");
			User user = (User)responseParam.get("user");
			form.setUser(user);
			this.setRedirectPage("addsuccess", userIndr);
			
		}else if (dealMethod.equals("modify")) {					
			log.debug("修改用户详细信息");
			form.setInfoMessage("修改用户详细信息");
			this.setRedirectPage("addsuccess", userIndr);
			
		}else if (dealMethod.equals("findRole")) {
			log.debug("显示用户角色权限页面数据");
			//获取用户ID
			String userid =String.valueOf(responseParam.get("id"));		
			//将用户ID放到form传到页面
			form.setId(userid);	
			
			Object alldataobj[] = (Object[])responseParam.get("allRoleData");
			Object choosedataobj[] = (Object[])responseParam.get("chooseRoleData");
			form.setAllData(alldataobj);
			form.setChooseData(choosedataobj);
			//查询设置权限页面所需要的数据
			this.setRedirectPage("findRole", userIndr);
			
		}else if (dealMethod.equals("findColumn")) {			
			//获取用户ID
			String userid = (String) responseParam.get("id");	
			//将用户ID放到form传到页面
			form.setId(userid); 
			List siteList = (List) responseParam.get("siteList");
			String siteId = (String)responseParam.get("siteId");
			String treeUrl = (String)responseParam.get("treeUrl");
			form.setSiteId(siteId);
			//将用户ID放到form传到页面
 		
			form.setSiteList(siteList);
			form.setTreeUrl(treeUrl);
			//查询设置权限页面所需要的数据
			this.setRedirectPage("findColumn", userIndr);			
		}
		else if(dealMethod.equals("operationlist")) {
			//显示操作列表
			Object[] allData = (Object[]) responseParam.get("alloperationlist");			
			Object[] chooseData = (Object[]) responseParam.get("chooseoperationlist");		
			boolean columnExtends = Boolean.parseBoolean(String.valueOf(responseParam.get("columnExtends")));
			//将页面需要的list对象放到form
			form.setAllData(allData);
			form.setChooseData(chooseData);
			String roleid = String.valueOf(responseParam.get("roleid"));
			String siteId = String.valueOf(responseParam.get("siteId"));
			form.setRoleid(roleid);
			form.setSiteId(siteId);	 
			form.setColumnExtends(columnExtends);
			this.setRedirectPage("operationdetail", userIndr);
			
		}else if(dealMethod.equals("setRoleSave")) {			
			form.setInfoMessage("设置角色权限成功");
			this.setRedirectPage("saverolemsg", userIndr);
		}else if(dealMethod.equals("setColumnSave")) {		 
			form.setInfoMessage("设置权限成功");
			this.setRedirectPage("saverolemsg", userIndr);
		}else if(dealMethod.equals("getColumnTree")){	
			//显示栏目树
			List list = (List) responseParam.get("columnList");
			form.setJson_list(list);
			form.setJson_attrnames(new String[]{"id","text","url","leaf","checked","formatid"});
			this.setRedirectPage("tree", userIndr);
		}else if (dealMethod.equals("showDetail")){
			//显示新增对话框
			Organization org = (Organization)responseParam.get("organization");
			Organization organization = new Organization();
			if(org != null ){
				organization.setId(org.getId());
				organization.setName(org.getName());
			}
			String nodeid = (String)responseParam.get("nodeid");
			form.setNodeId(nodeid);
			form.setOrganization(organization);
			form.setDealMethod("showDetail");
			this.setRedirectPage("showdetail", userIndr);
		}else if(dealMethod.equals("checkLoginName")) {					
			boolean check = Boolean.parseBoolean(String.valueOf(responseParam.get("check")));
			if(check == true){
				form.setInfoMessage("0");
			}else{
				form.setInfoMessage("1");
			}
			this.setRedirectPage("saverolemsg", userIndr);
			
		}else if (dealMethod.equals("personalInfoDetail")){
 			//当前用户信息
			form.setDealMethod("detail");
			User user = (User)responseParam.get("user");
			form.setUser(user);
			this.setRedirectPage("findInfoSuccess", userIndr);
			
		}else if (dealMethod.equals("modifyPersonalInfo")){
 			//修改当前用户信息
			form.setInfoMessage("修改信息成功");
			this.setRedirectPage("modifyInfoSuccess", userIndr);
		}  else if(dealMethod.equals("currentLineUser")) {
 			//  当前在线用户
 			List<Object> list = new ArrayList<Object>();
 			list = (List<Object>) responseParam.get("list"); 
 			form.setCurrentLineCount(list.size());
 			form.setCurrentLineUserList(list);
 			this.setRedirectPage("currentLineUsers", userIndr);
 		}
	}
	
	@Override
	protected void performTask(ActionForm actionForm, RequestEvent requestEvent, String userIndr) throws Exception {
		log.debug("dealMethod=============performTask================="+dealMethod);
		// 用户表单
		UserForm form = (UserForm) actionForm;	
		this.dealMethod = form.getDealMethod();
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		User user = new User();		
		String ids = form.getIds();
		Organization organization = new Organization();
		String nodeid = form.getNodeId();
		if(nodeid == null || nodeid.equals("0") || nodeid.equals("")) {
			nodeid = null;
		}
		
		if (dealMethod.equals("")) {
			//查询初始化页面所需要的数据	
		//	form.setQueryKey("findUserPage");
			
			String nodename = form.getNodeName();
			if(nodeid != null) {
				if(isUpSystemAdmin == true){
					form.setQueryKey("findUserPageByOrgId");
				}else {
					form.setQueryKey("findUserPageByOrgIdAndSiteId");
				}
				
			}else{
				if(isUpSystemAdmin == true){
					form.setQueryKey("findUserPage");
				}else{
					form.setQueryKey("findUserPageBySiteId");
				}
			}
			
			requestParam.put("nodeid", nodeid);
			requestParam.put("nodename", nodename);
			
		}else if (dealMethod.equals("insert")) {
			//添加用户
			log.debug("添加用户");
			user = form.getUser();
			log.debug("form.getOrganization().getId()==="+form.getOrganization().getId());
			Date date = new Date();
			user.setRegisterDate(date);
			requestParam.put("user", user);			
			requestParam.put("organizationId", form.getOrganization().getId());			
			
		} else if (dealMethod.equals("delete")) {
			//删除用户
			requestParam.put("ids", ids);					
			log.debug("删除用户");
			
		} else if (dealMethod.equals("modify")) {
			//修改用户详细信息数据
			log.debug("修改用户详细信息数据");
		//	int id = form.getId();			
		
			user = form.getUser();
		//	user.setId(id);
			if(form.getOrganization() != null && form.getOrganization().getId() != null){
				organization.setId(form.getOrganization().getId());
				user.setOrganization(organization);
			}	
			requestParam.put("user", user);	
			log.debug("修改用户");			
			
		} else if (dealMethod.equals("detail")) {
			//显示某个用户的详细信息
			log.debug("显示某个用户的详细信息");
			String id = form.getId();		
			requestParam.put("id", id);			
			
		} else if (dealMethod.equals("findRole")) {	
			//查询设置权限页面所需要的数据
			log.debug("查询角色权限设置页面所需要的数据");			
			//获取用户ID
			String id = form.getId();		
			requestParam.put("id", id);	
			
		}else if (dealMethod.equals("findColumn")) {	
			//查询设置权限页面所需要的数据
			log.debug("查询栏目权限设置页面所需要的数据");					
			//获取用户ID
			String id = form.getId();	
			if(id == null || id.equals("0") || id.equals("null") || id.equals("")) {
				id = null;
			} 
			String siteId = form.getSiteId();		 
			requestParam.put("siteId", siteId);
			requestParam.put("id", id); 
		 
		} else if(dealMethod.equals("operationlist")) {
			//根据角色和栏目节点ID查询所有的栏目和文章操作
			log.debug("根据角色和栏目节点ID查询所有的操作");
			//根据角色和栏目节点ID查询所有的操作
			String itemid = form.getItemid();			
			//需要设置的用户ID
			String userid = form.getId();
			String siteId = form.getSiteId();			 
			if(itemid == null || itemid.equals("0") || itemid.equals("")) {
				itemid = null;
			}		 
			requestParam.put("itemid", itemid);			
			requestParam.put("userid", userid); 
			requestParam.put("siteId", siteId);
			
		} else if(dealMethod.equals("setRoleSave")) {
			//保存用户栏目权限
			log.debug("保存用户角色权限");
			//所有选择的网站ID和角色ID			 
			String checkedTreeIds = form.getCheckedTreeIds();
			//用户ID
			String userId = String.valueOf(form.getId());
			String systemcheckvalue = form.getSystemcheckvalue();
			if( systemcheckvalue == null 
					|| systemcheckvalue.equals("") 
					|| systemcheckvalue.equals("null")
					|| systemcheckvalue.equals("0") ) {
				systemcheckvalue = null;
			}
			
			requestParam.put("checkedTreeIds", checkedTreeIds);
			requestParam.put("userId", userId);
			requestParam.put("systemcheckvalue", systemcheckvalue);	
			
		}else if(dealMethod.equals("setColumnSave")) {
			//保存用户栏目权限
			log.debug("保存用户栏目权限");
			String operationid = form.getOperationid();
			String itemid = form.getItemid();		
			String siteId = form.getSiteId();
			if(itemid == null || itemid.equals("") || itemid.equals("0") ) {
				itemid = null;
			}
			String userid = form.getId();
			//设置下级栏目是否继承上级栏目的权限
			String setChild = form.getSetChild();	
			log.debug("setChild===================="+setChild);
		 
			//操作ID集合
			requestParam.put("operationid", operationid); 	
			//节点ID
			requestParam.put("itemid", itemid);			
			requestParam.put("setChild", setChild);
			requestParam.put("userid", userid);
			requestParam.put("siteId", siteId);
			
		}else if(dealMethod.equals("getColumnTree")){		
			//显示栏目树
			String treeId = form.getTreeId();
			String userId = form.getId();
			if(treeId == null || treeId.equals("0") || treeId.equals("")) {
				treeId = null;
			}
			String siteId = form.getSiteId();
			requestParam.put("treeId", treeId);
			requestParam.put("userId", userId);
			requestParam.put("siteId", siteId);
			
		}else if (dealMethod.equals("showDetail")){
 			//显示新增对话框
 			requestParam.put("nodeid", nodeid);
 			
 		}else if (dealMethod.equals("checkLoginName")){
 			//检验登录名是否已经被注册
 			String loginName = form.getLoginName();
 			requestParam.put("loginName", loginName);
 			
 		}else if (dealMethod.equals("personalInfoDetail")){
 			//当前用户信息
 			
 		}else if (dealMethod.equals("modifyPersonalInfo")){
 			//修改当前用户信息
 			User person = form.getUser();
 			if(form.getOrganization() != null && form.getOrganization().getId() != null){
				organization.setId(form.getOrganization().getId());
				person.setOrganization(organization);
			}	
			requestParam.put("person", person);	
 		
 		} else if(dealMethod.equals("currentLineUser")) {
 			// 当前在线用户
 			form.setQueryKey("findCurrentLineUser");
 			requestParam.put("pagination", form.getPagination());
 		}
	}
	
	@Override
	protected void init(String userIndr) throws Exception {
	}
	

}
