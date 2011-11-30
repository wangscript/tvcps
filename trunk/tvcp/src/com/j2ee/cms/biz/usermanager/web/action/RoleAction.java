
  /**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.usermanager.web.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.j2ee.cms.biz.usermanager.domain.Role;
import com.j2ee.cms.biz.usermanager.web.form.RoleForm;
import com.j2ee.cms.sys.GlobalConfig;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.util.StringUtil;
import com.j2ee.cms.common.core.web.GeneralAction;
import com.j2ee.cms.common.core.web.event.RequestEvent;
import com.j2ee.cms.common.core.web.event.ResponseEvent;
import com.j2ee.cms.common.core.web.ui.Node;

/**
 * <p>标题: —— 角色action处理类</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 用户管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-2-25 上午11:30:56
 * @history（历次修订内容、修订人、修订时间等） 
 */
public final  class RoleAction  extends GeneralAction {
	private String dealMethod = "";
	@Override
	protected void doFormFillment(ActionForm actionForm, ResponseEvent responseEvent, String userIndr) {
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();		// TODO 自动生成方法存根
		RoleForm form = (RoleForm) actionForm;		
		Role role = new Role();
		List list = new ArrayList();			
		if (dealMethod.equals("")) {		
			log.debug("显示角色列表");
			Pagination pagination = (Pagination) responseParam.get("pagination");
			form.setPagination(pagination);
			form.setRoleIds((String) responseParam.get("roleIds"));
			String siteId = (String)responseParam.get("siteId");
			form.setSiteId(siteId);
			this.setRedirectPage("success", userIndr);
			
		}else if (dealMethod.equals("insert")) {			
			log.debug("增加角色成功!");		
			form.setInfoMessage("增加角色成功！");
			String siteId = (String)responseParam.get("siteId");
			form.setSiteId(siteId);
			this.setRedirectPage("addsuccess", userIndr);
			
		}else if (dealMethod.equals("delete")) {
			log.debug("删除角色成功!");			
			String use = (String)responseParam.get("use");
			String siteId = (String)responseParam.get("siteId");		
			if(use != null && use.equals("true")){
				form.setUse("true");
			}else{
				form.setUse("false");
			}
			form.setDealMethod("delete");
			form.setSiteId(siteId);
			Pagination pagination = (Pagination) responseParam.get("pagination");
			form.setPagination(pagination);
			form.setSiteId(siteId);
			this.setRedirectPage("success", userIndr);
			
		}else if (dealMethod.equals("findAuthorityData")) {				
			//查询菜单权限
			Object[] allData = (Object[])responseParam.get("allData");
			Object[] chooseData = (Object[])responseParam.get("chooseData");
			String roleId = (String)responseParam.get("roleId");
			form.setRoleid(roleId);
			form.setAllData(allData);
			form.setChooseData(chooseData);			
			this.setRedirectPage("setauthority", userIndr);	
			
		}else if (dealMethod.equals("detail")) {	
			log.debug("显示角色详细信息");
			role = (Role)responseParam.get("role");
			String userSiteId = (String)responseParam.get("userSiteId");
			String siteId = (String)responseParam.get("siteId");
			form.setTempSiteId(siteId);
			form.setSiteId(userSiteId);
			form.setRole(role);
			List siteList = (List)responseParam.get("siteList");
			form.setList(siteList);
			this.setRedirectPage("addsuccess", userIndr);
			
		}else if (dealMethod.equals("modify")) {					
			log.debug("修改角色详细信息");
			String siteId = (String)responseParam.get("siteId");
			form.setSiteId(siteId);
			form.setInfoMessage("修改角色成功！");
			this.setRedirectPage("addsuccess", userIndr);	
			
		}else if (dealMethod.equals("setSave")) {	
			//权限设置保存
		 
			form.setRoleid(String.valueOf(responseParam.get("roleid")));		
		//	this.setRedirectPage("operationdetail", userIndr);		
			
		}else if(dealMethod.equals("getSiteId")){		
			form.setSiteName(String.valueOf(responseParam.get("sitename")));
			this.setRedirectPage("getsitetree", userIndr);
			
		}else if(dealMethod.equals("showDetail")){		
			//显示增加数据窗口，获取网站名
		//	String siteName = String.valueOf(responseParam.get("sitename"));
			List siteList = (List)responseParam.get("siteList");
			form.setList(siteList);
			String siteId = (String)responseParam.get("siteId");
			form.setTempSiteId(siteId);
		//	form.setSiteName(siteName);
			this.setRedirectPage("showdetail", userIndr);
			
		}else if(dealMethod.equals("checkRoleName")){
			boolean checkRole = Boolean.parseBoolean(String.valueOf(responseParam.get("checkRole")));
			if(checkRole == true){
				form.setInfoMessage("0");
			}else{
				form.setInfoMessage("1");
			}
			this.setRedirectPage("roleMessage", userIndr);
		}else if(dealMethod.equals("getSiteTree")){		
			//获取左侧的网站树
			list = (List) responseParam.get("siteList");
			form.setJson_list(list);
			form.setJson_attrnames(new String[]{"id","text","url","leaf"});
			this.setRedirectPage("tree", userIndr);
		}
	}

	@Override
	protected void init(String userIndr) throws Exception {
		// TODO 自动生成方法存根
		
	}

	@Override
	protected void performTask(ActionForm actionForm, RequestEvent requestEvent, String userIndr) throws Exception {	
		// TODO 自动生成方法存根
	
		RoleForm form = (RoleForm) actionForm;	
		this.dealMethod = form.getDealMethod();
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		
		Role role = new Role();
		log.debug("RoleAction----dealMethod======"+dealMethod);
		String ids = form.getIds();

		//初始化页面显示
		if (dealMethod.equals("")) {		
			String treeId = form.getTreeId();
			if(treeId != null && (treeId.equals("") || treeId.equals("0"))){
			/*	if(isUpSystemAdmin){
					form.setQueryKey("findAllRolePage");
				}else{*/
					form.setQueryKey("findRolePage");
				//}				
			}else{
				form.setQueryKey("findRolePage");
			}
			requestParam.put("treeId", treeId);		
			requestParam.put("pagination", form.getPagination());		
			
		}else if (dealMethod.equals("insert")) {	
			//保存角色数据
			role = form.getRole();
			Date date = new Date();
			//获取当前时间
			role.setCreateTime(date);
			String siteName = form.getSiteName();
			requestParam.put("role", role);					
			requestParam.put("siteName", siteName);		
			
		}else if (dealMethod.equals("delete")) {	
			String siteId = form.getSiteId();
			requestParam.put("siteId",siteId);
			//删除角色数据
			requestParam.put("ids", ids);
			
			
			String treeId = form.getTreeId();
			form.setQueryKey("findRolePage");
			requestParam.put("treeId", treeId);		
			requestParam.put("pagination", form.getPagination());
			
		}else if (dealMethod.equals("findAuthorityData")) {
			//查询菜单权限设置页面所需要的数据
			//获取这个角色的ID
			String roleid = String.valueOf(form.getRoleid());			
			requestParam.put("roleid", roleid);					
		}else if (dealMethod.equals("detail")) {
			//显示某个角色的详细信息
			log.debug("显示某个角色的详细信息");	
			String id = form.getId();		
			String siteId = form.getSiteId();
			requestParam.put("id", id);
			requestParam.put("siteId", siteId);	
			
		}else if (dealMethod.equals("modify")) {	
			//修改角色数据
			role = form.getRole();				
			Date date = new Date();
			//获取当前时间
		//	role.setCreateTime(date);
			requestParam.put("role", role);	
			String siteName = form.getSiteName();	
			requestParam.put("siteName", siteName);	
			log.debug("修改角色");	
			
		}else if (dealMethod.equals("setSave")) {	
			//菜单权限设置保存		 
			String menuId = form.getStrmenuid();
			String roleId = form.getRoleid();
			//菜单ID集合
			requestParam.put("menuId", menuId);
			requestParam.put("roleId", roleId);	
		}else if(dealMethod.equals("showDetail")){		
			//显示增加数据窗口，获取网站名
			String siteId = form.getSiteId();
			requestParam.put("siteId", siteId);
			
		}else if(dealMethod.equals("checkRoleName")){
			//检测角色名
			String roleName = form.getRoleName();
			String siteId = form.getSiteId();
			requestParam.put("roleName", roleName);
			requestParam.put("siteId", siteId);
		}else if(dealMethod.equals("getSiteTree")){		
			//获取左侧的网站树
			String treeId = form.getTreeId();
			requestParam.put("treeId", treeId);
		}
	
	}
}