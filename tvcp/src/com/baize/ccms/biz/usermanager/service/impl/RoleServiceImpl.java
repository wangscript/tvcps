  /**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.biz.usermanager.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.baize.ccms.biz.columnmanager.dao.ColumnDao;
import com.baize.ccms.biz.columnmanager.domain.Column;
import com.baize.ccms.biz.configmanager.dao.SystemLogDao;
import com.baize.ccms.biz.sitemanager.dao.SiteDao;
import com.baize.ccms.biz.sitemanager.domain.Site;
import com.baize.ccms.biz.usermanager.dao.AssignmentDao;
import com.baize.ccms.biz.usermanager.dao.AuthorityDao;
import com.baize.ccms.biz.usermanager.dao.MenuAuthorityDao;
import com.baize.ccms.biz.usermanager.dao.MenuDao;
import com.baize.ccms.biz.usermanager.dao.MenuFunctionDao;
import com.baize.ccms.biz.usermanager.dao.OperationDao;
import com.baize.ccms.biz.usermanager.dao.ResourceDao;
import com.baize.ccms.biz.usermanager.dao.RoleDao;
import com.baize.ccms.biz.usermanager.dao.UserDao;
import com.baize.ccms.biz.usermanager.domain.Assignment;
import com.baize.ccms.biz.usermanager.domain.Menu;
import com.baize.ccms.biz.usermanager.domain.MenuAuthority;
import com.baize.ccms.biz.usermanager.domain.MenuFunction;
import com.baize.ccms.biz.usermanager.domain.Role;
import com.baize.ccms.biz.usermanager.domain.User;
import com.baize.ccms.biz.usermanager.service.RoleService;
import com.baize.common.core.dao.Pagination;
import com.baize.common.core.util.DateUtil;
import com.baize.common.core.util.SqlUtil;
import com.baize.common.core.util.StringUtil;

/**
 * <p>标题: —— 角色业务逻辑处理类.</p>
 * <p>描述: —— 角色表业务逻辑处理类</p>
 * <p>模块: 用户管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-2-25 上午11:23:36
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class RoleServiceImpl implements RoleService {
	private static final Logger log = Logger.getLogger(RoleServiceImpl.class);
	/** 注入用户角色DAO. */
	private RoleDao roleDao;
	/** 注入栏目DAO */
	private ColumnDao columnDao;
	/** 注入网站DAO */
	private SiteDao siteDao;	
	/** 注入资源dao */
	private ResourceDao resourceDao;
	/** 注入权限DAO */
	private AuthorityDao authorityDao; 
	/** 操作dao */
	private OperationDao operationDao;
	/** 注入菜单dao. */
	private MenuDao menuDao;
	/** 注入分配角色DAO*/
	private AssignmentDao assignmentDao;

	/** 注入用户DAO*/
	private UserDao userDao; 
	
	/** 菜单具体功能dao */
	private MenuFunctionDao menuFunctionDao;
	
	/** 菜单角色权限dao */
	private MenuAuthorityDao menuAuthorityDao;
	/** 注入日志dao */
	private SystemLogDao systemLogDao;
	
	public void addRoles(Role role) {
		// TODO 自动生成方法存根
		roleDao.save(role);
	}

	public void deleteRole(String id) {
		// TODO 自动生成方法存根
		roleDao.deleteByKey(id);
	}
	
	public void deleteRoleByIds(String ids, String siteId, String sessionID) {
		String newIds = ids;
		// TODO 自动生成方法存根
		ids = SqlUtil.toSqlString(ids);
		List assList = assignmentDao.findByDefine("findAssignmentByDeleteRoleid", "roleId", ids);
		String assIds = "";
		for(int i = 0 ; i < assList.size() ; i++){
			Assignment ass = (Assignment)assList.get(i);
			assIds = assIds + "," +SqlUtil.toSqlString(ass.getId());
		}
		if(assIds.startsWith(",")){
			assIds = assIds.replaceFirst(",", "");
		}
		if(assIds != null && !assIds.equals("")){
			assignmentDao.deleteByIds(assIds);
		}
		 
		// 先写入日志文件再删除
		String categoryName = "角色管理->删除";
		String param = "";
		String[] str = newIds.split(",");
		if(!StringUtil.isEmpty(newIds)) {
			Role logRole = null;
			for(int i = 0; i < str.length; i++) {
				logRole = roleDao.getAndClear(str[i]);
				param = logRole.getName();
				systemLogDao.addLogData(categoryName, siteId, sessionID, param);
			}
		}		
		// 删除角色
		if(ids != null && !ids.equals("")){
			roleDao.deleteByIds(ids);
		}		
	}

	public Role findRoleByKey(String id) {
		// TODO 自动生成方法存根
		return roleDao.getAndClear(id);
	}
	
	public void modifyRole(Role role) {
		// TODO 自动生成方法存根
		roleDao.saveOrUpdate(role);
	}
	
	/** 返回分页对象 */
	public Pagination findRoleData(Pagination pagination, String siteid){
		return roleDao.getPagination(pagination,"siteid",siteid);
	}
	
 
	public Site findSiteBySiteId(String siteId){
		if(siteId != null){
			return siteDao.getAndClear(siteId);
		}else{
			List siteList = siteDao.findByNamedQuery("findParentSiteIsNull");
			String id = (String) siteList.get(0);			
			return siteDao.getAndClear(id);
		}
		
	}
	
	public List<Site> findAllSite(String siteId,boolean isUpSystemAdmin){
		if(isUpSystemAdmin){
//			return siteDao.findAll();
			return siteDao.findByNamedQuery("findSiteByDeleted");
		}else{
			Site site =  siteDao.getAndClear(siteId);
			List list = new ArrayList();
			list.add(site);
			return list;
		}		
		
	}
	
	public Site findSiteBySiteName(String siteName){
		List list = siteDao.findByNamedQuery("findSiteByName", "siteName", siteName);
		Site choosesite = new Site();
		if(list.size() > 0){
			Site site = (Site)list.get(0);
			String siteid = site.getId();
			choosesite = siteDao.getAndClear(siteid);
		}
		return choosesite;
	}

 
	public Menu findMenuDataById(final String id) {
		return menuDao.getAndClear(id);
	}
	
	
	public List findMenuAuthorityByRoleIdAndSiteId(String roleId,String siteId){
		//查询出所有的菜单
		List menuList = menuDao.findAll();		
				//将查询出的数据组装成treeCheckbox标签所需要的数据格式
		MenuFunction menuFunction = null;
		Object[] tempObj = null;
		MenuAuthority menuAuthority = null;
		Menu menu = null;
		Object[] allData = new Object[menuList.size()];
		Object[] chooseData = new Object[menuList.size()];		
		List tempList = null;
		for(int i = 0 ; i < menuList.size() ; i++){			
			menu = (Menu)menuList.get(i);
			String menuId = menu.getId();
			//处理所有菜单
			//查询出菜单具体功能权限
			List menuFunctionList = menuFunctionDao.findByNamedQuery("findMenuFunctionByMenuId", "menuId", menuId);
			tempList = new ArrayList();
			if(menuFunctionList.size() > 0){
				for(int j = 0 ; j < menuFunctionList.size() ; j++){
					menuFunction = (MenuFunction)menuFunctionList.get(j);
					tempObj = new Object[4];
					tempObj[0] = menuFunction.getId();
					tempObj[1] = menuFunction.getFunctionName();
					tempObj[2] = menuFunction.getMenu().getId();
					tempObj[3] = menuFunction.getMenu().getName();
					tempList.add(tempObj);
				}
			}else{
				tempObj = new Object[4];
				tempObj[0] = "";
				tempObj[1] = "";
				tempObj[2] = menu.getId();
				tempObj[3] = menu.getName();
				tempList.add(tempObj);
			}
			allData[i] = tempList;
			
			//处理已选择的菜单功能权限
			String str[] = {"roleId","menuId"};
			Object obj[] = {roleId,menuId};
			List menuAuthorityList = menuAuthorityDao.findByNamedQuery("findMenuAuthorityByRoleIdAndMenuId", str, obj);
			
			tempList = new ArrayList();
			for(int j = 0 ; j < menuAuthorityList.size() ; j++){
				menuAuthority = (MenuAuthority)menuAuthorityList.get(j);
				tempObj = new Object[4];
				tempObj[0] = menuAuthority.getMenuFunction().getId();
				tempObj[1] = menuAuthority.getMenuFunction().getFunctionName();
				tempObj[2] = menuAuthority.getMenuFunction().getMenu().getId();
				tempObj[3] = menuAuthority.getMenuFunction().getMenu().getName();
				tempList.add(tempObj);
			}
			chooseData[i] = tempList;			
			menuAuthorityDao.clearCache();
		}
		List newList = new ArrayList();
		newList.add(allData);
		newList.add(chooseData);
		return newList;
	}
	 
	public void addSetMenuAuthority(String userId,String siteId,String menuIds,String roleId){	
		String menuAuthorityIds = "";		 
		//查询出这个角色所拥有的菜单权限
		List tempmenuAuthorityList = menuAuthorityDao.findByNamedQuery("findMenuAuthorityByRoleId","roleId",roleId);
		for(int i = 0 ; i < tempmenuAuthorityList.size() ; i++){
			MenuAuthority menuAuthority = (MenuAuthority)tempmenuAuthorityList.get(i);
			menuAuthorityIds = menuAuthorityIds + "," + SqlUtil.toSqlString(menuAuthority.getId());
		}
		menuAuthorityIds = StringUtil.replaceFirst(menuAuthorityIds,",");
		//删除原来的设置
		if(!menuAuthorityIds.equals("")){
			menuAuthorityDao.deleteByIds(menuAuthorityIds);
		}
		//根据传过来的menuId(格式为f001#m001,f002#m002),设置某个角色的相应的权限
		String strMenuId[] = StringUtil.split(menuIds, ","); 
		for(int j = 0 ; j < strMenuId.length ; j++){
			if(StringUtil.contains(strMenuId[j],"#")){
				String tempMenuFunction[] = StringUtil.split(strMenuId[j],"#");
				if(tempMenuFunction != null && tempMenuFunction.length > 0){
					String menuAuthorityId = tempMenuFunction[0];
					MenuAuthority menuAuthority = new MenuAuthority();
					MenuFunction menuFunction = new MenuFunction();
					menuFunction.setId(menuAuthorityId);
					menuAuthority.setMenuFunction(menuFunction);
					Role role = new Role();
					role.setId(roleId);
					menuAuthority.setRole(role);
					//将新的功能权限保存到菜单权限这个表里
					menuAuthorityDao.save(menuAuthority);
					menuAuthorityDao.cleanCache();
					//菜单具体功能ID				
					String menuId = tempMenuFunction[1];	
				}
			}
		}
		menuAuthorityDao.cleanCache();
		menuAuthorityDao.clearCache();
		// 写入日志文件
		String categoryName = "";
		/*if(operationType.equals("column")) {
			categoryName = "角色管理(栏目及菜单权限)->保存";
		} else if(operationType.equals("article")) {
			categoryName = "角色管理(文章权限)->保存";
		} 
		*/
	/*	if(role != null){
			categoryName = "角色管理(栏目及菜单权限)->保存";
			String param = role.getName();
			systemLogDao.addLogData(categoryName, siteId, userId, param);
		}*/
	}
	 

	public void modifyUser(String userId,String siteId,String roleId){
		//根据角色ID查询有几个用户设置了这个角色
		List assignmentList = assignmentDao.findByNamedQuery("findAssignmentByRoleid","roleId",roleId);
		MenuAuthority menuAuthority = null;
		MenuFunction menuFunction = null;
		Role role = null;
	 
 
		Assignment assignment = null;
		boolean isSystemAdmin = false;
		for(int i = 0 ; i < assignmentList.size() ; i++){
			//存放一个用户的其他角色的菜单的ID
			String useMenuIds = "";
			assignment = (Assignment)assignmentList.get(i);
			User user = assignment.getUser();
			String oneUserId = user.getId();
			role = assignment.getRole();
			//一个用户可能有多个角色，看哪个角色具体有什么权限
			String str[] = {"id","siteid"};
			Object obj[] = {oneUserId,siteId};
			List roleList = assignmentDao.findByNamedQuery("findRoleIdByUserIdAndSiteId", str, obj);
			for(int j = 0 ; j < roleList.size() ; j++){
				assignment = (Assignment)roleList.get(j);
				//获取这个用户所拥有的角色ID
				String oneRoleId = assignment.getRole().getId();
				String oneRoleName = assignment.getRole().getName();
				if(!oneRoleName.equals("网站管理员") && !oneRoleName.equals("系统管理员")){
					List menuAuthorityList = menuAuthorityDao.findByNamedQuery("findMenuAuthorityByRoleId", "roleId", oneRoleId);
					for(int t = 0 ; t < menuAuthorityList.size() ; t++){
						menuAuthority = (MenuAuthority)menuAuthorityList.get(t);
						menuFunction = menuAuthority.getMenuFunction();
						if(menuFunction != null && menuFunction.getMenu() != null){
							useMenuIds = useMenuIds + "," + menuFunction.getId() + "#" + menuFunction.getMenu().getId();
						}
					}
				}else if(oneRoleName.equals("网站管理员")){					 
					List menuFunctionList = menuFunctionDao.findAll();					
					for(int t = 0 ; t < menuFunctionList.size() ; t++){
						menuFunction = (MenuFunction)menuFunctionList.get(t);
						useMenuIds = useMenuIds + "," + menuFunction.getId() + "#" + menuFunction.getMenu().getId();
					}
				}else if(oneRoleName.equals("系统管理员")){						
					isSystemAdmin = true;					
				}				
			}
			//去除里面重复的(其他角色的菜单IDS)
			useMenuIds = StringUtil.clearRepeat(useMenuIds);
			//处理完的其他角色的菜单IDS
			useMenuIds = StringUtil.replaceFirst(useMenuIds, ",");
			//原来这个用户的菜单IDS
			String userMenuIds = user.getMenuIds();
			//这个用户排序完的菜单IDS
			String userOrdersMenuIds = user.getChooseMenuIds();
/*			if(StringUtil.contains(userMenuIds,"*")){
				String strUserMenuIds[] = StringUtil.split(userMenuIds, "*");
				for(int j = 0 ; j < strUserMenuIds.length ; j++ ){
					String strTemp = strUserMenuIds[j];
					//根据网站ID判断如果是对这个网站设置的权限
					if(strTemp != null && !strTemp.equals("") && (strTemp.split(",")[0]).equals(siteId)){
						String tempMenuIds = useMenuIds;
						tempMenuIds = StringUtil.clearRepeat(tempMenuIds);
						String lastMenuIds = ((strTemp.split(",")[0]) + "," +tempMenuIds);
						userMenuIds = userMenuIds.replace(strTemp, lastMenuIds);
						user.setMenuIds(userMenuIds);
						//修改这个用户的菜单
						userDao.update(user);
					}
				}
			}else{
				//根据网站ID判断如果是对这个网站设置的权限
				if(userMenuIds != null && !userMenuIds.equals("") && (userMenuIds.split(",")[0]).equals(siteId)){
					String tempMenuIds = useMenuIds;
					tempMenuIds = StringUtil.clearRepeat(tempMenuIds);
					String lastMenuIds = ((userMenuIds.split(",")[0]) + "," +tempMenuIds);
					userMenuIds = userMenuIds.replace(userMenuIds, lastMenuIds);
					user.setMenuIds(userMenuIds);
					//修改这个用户的菜单
					userDao.update(user);
				}
			}*/
			List siteList = user.getSiteIds();
			String siteIds = "";
			for(int t = 0 ; t < siteList.size(); t++){
				siteIds = siteIds + "," +siteList.get(t);
			}
			siteIds = StringUtil.replaceFirst(siteIds,",");
			userMenuIds = StringUtil.replaceFirst(userMenuIds, ",");
			if(userMenuIds == null || userMenuIds.equals("null") || userMenuIds.equals("")){
				userMenuIds = "";
			}
			if(userMenuIds != null && !userMenuIds.equals("") && !userMenuIds.equals("null")){			
				if(StringUtil.contains(userMenuIds,"*")){
					String strUserMenuIds[] = StringUtil.split(userMenuIds, "[*]");
					for(int j = 0 ; j < strUserMenuIds.length ; j++ ){
						String strTemp = strUserMenuIds[j];
						//根据网站ID判断如果是对这个网站设置的权限
						if(strTemp != null && !strTemp.equals("") && (strTemp.split(",")[0]).equals(siteId)){
							String tempMenuIds = useMenuIds;
							tempMenuIds = StringUtil.clearRepeat(tempMenuIds);
							tempMenuIds = StringUtil.replaceFirst(tempMenuIds, ",");
							String lastMenuIds = ((strTemp.split(",")[0]) + "," +tempMenuIds);
							userMenuIds = userMenuIds.replace(strTemp, lastMenuIds);
							user.setMenuIds(userMenuIds);										 
						}else{
							String tempMenuIds = useMenuIds;
							tempMenuIds = StringUtil.clearRepeat(tempMenuIds);
							tempMenuIds = StringUtil.replaceFirst(tempMenuIds, ",");
							userMenuIds = userMenuIds + "*" + (siteId + "," +tempMenuIds);
							user.setMenuIds(userMenuIds);		
						}
						
					}
				}
				else{
					//根据网站ID判断如果是对这个网站设置的权限					
					String tempMenuIds = useMenuIds;
					tempMenuIds = StringUtil.clearRepeat(tempMenuIds);		
					tempMenuIds = StringUtil.replaceFirst(tempMenuIds, ",");
					String lastMenuIds = (siteId + "," +tempMenuIds);
					userMenuIds = userMenuIds.replace(userMenuIds, lastMenuIds);
					user.setMenuIds(userMenuIds);								 							 
				}
			}
			
			//根据角色设置排序完的菜单
			userOrdersMenuIds = StringUtil.replaceFirst(userOrdersMenuIds, ",");
			if(userOrdersMenuIds == null || userOrdersMenuIds.equals("null") || userOrdersMenuIds.equals("")){
				userOrdersMenuIds = "";
			}
			if(userOrdersMenuIds != null && !userOrdersMenuIds.equals("") && !userOrdersMenuIds.equals("null")){			
				if(StringUtil.contains(userOrdersMenuIds,"*")){
					String strUserOrdersMenuIds[] = StringUtil.split(userOrdersMenuIds, "[*]");
					for(int j = 0 ; j < strUserOrdersMenuIds.length ; j++ ){
						String strTemp = strUserOrdersMenuIds[j];
						//根据网站ID判断如果是对这个网站设置的权限
						if(strTemp != null && !strTemp.equals("") && (strTemp.split(",")[0]).equals(siteId)){
							String tempMenuIds = useMenuIds;
							tempMenuIds = StringUtil.clearRepeat(tempMenuIds);
							tempMenuIds = StringUtil.replaceFirst(tempMenuIds, ",");
							String lastMenuIds = ((strTemp.split(",")[0]) + "," +tempMenuIds);
							userOrdersMenuIds = userOrdersMenuIds.replace(strTemp, lastMenuIds);
							user.setChooseMenuIds(userOrdersMenuIds);										 
						}else{
							String tempMenuIds = useMenuIds;
							tempMenuIds = StringUtil.clearRepeat(tempMenuIds);
							tempMenuIds = StringUtil.replaceFirst(tempMenuIds, ",");
							userOrdersMenuIds = userOrdersMenuIds + "*" + (siteId + "," +tempMenuIds);
							user.setChooseMenuIds(userOrdersMenuIds);		
						}
						
					}
				}else{
					//根据网站ID判断如果是对这个网站设置的权限					
					String tempMenuIds = useMenuIds;
					tempMenuIds = StringUtil.clearRepeat(tempMenuIds);		
					tempMenuIds = StringUtil.replaceFirst(tempMenuIds, ",");
					String lastMenuIds = (siteId + "," +tempMenuIds);
					userOrdersMenuIds = userOrdersMenuIds.replace(userOrdersMenuIds, lastMenuIds);
					user.setChooseMenuIds(userOrdersMenuIds);								 							 
				}
			}
			String temp = "";
			if(isSystemAdmin){
				//如果是系统管理员
				useMenuIds = "";
				List menuFunctionList = menuFunctionDao.findAll();				
				for(int t = 0 ; t < menuFunctionList.size() ; t++){
					menuFunction = (MenuFunction)menuFunctionList.get(t);
					useMenuIds = useMenuIds + "," + menuFunction.getId() + "#" + menuFunction.getMenu().getId();
				}
				List tempSiteList = siteDao.findByNamedQuery("findSiteByDeleted");
				
				for(int t = 0 ; t < tempSiteList.size() ; t++){
					if(!temp.equals("")){
						temp = temp + "*" + ((Site)tempSiteList.get(t)).getId() + useMenuIds ;		
					}else{
						temp = ((Site)tempSiteList.get(t)).getId() + useMenuIds ;		
					}
												
				}
			}
			
			
			siteIds = siteIds + "," +siteId;
			siteIds = StringUtil.replaceFirst(siteIds,",");
			siteIds = StringUtil.clearRepeat(siteIds);
			siteIds = StringUtil.replaceFirst(siteIds,",");
			String strSiteIds[] = siteIds.split(",");
			List newSiteList = new ArrayList();
			for(int n = 0 ; n < strSiteIds.length ; n++){
				newSiteList.add(strSiteIds[n]);
			}
			temp = StringUtil.replaceFirst(temp, "[*]");
			if(temp != null && !temp.equals("")){
				user.setMenuIds(temp);
				user.setChooseMenuIds(temp);
			}
			user.setSiteIds(newSiteList);
			//修改这个用户的菜单
			userDao.update(user);
		//	}
		}
	}
 
	 
	
	
	/**
	 * 递归获取所有的子栏目ID
	 * @param columnList 第一级栏目的集合
	 * @param columnids 栏目ID集合
	 * @return 返回所有的子栏目ID集合，以逗号隔开
	 */
	private void getColumnId(StringBuffer columnids, String columnId,String siteId){	
		String str[] = {"pid","siteId"};
		Object obj[] = {columnId,siteId};
		List columnList = columnDao.findByNamedQuery("findColumnByParentIdAndSiteId", str, obj);
		Column column = null;
		if (columnList != null && columnList.size() > 0) {
			for (int i = 0 ; i < columnList.size(); i++ ) {
			//	Object[] obj = (Object[])columnList.get(i);
			//	for(int j = 0 ; j < obj.length ; j++){
					column = (Column)columnList.get(i);
					if(column != null && column.getId() != null){
						String colId = column.getId();
						String columnid = column.getId();			
						columnids.append("," + columnid);
						// 递归
						getColumnId(columnids, colId,siteId);							
					}		
			//	}
					
			}
		}
	}

	
 
	

	
	public boolean findRoleByNameAndSiteId(String roleName,String siteId){
		String str[] = {"roleName","siteId"};
		Object obj[] = {roleName,siteId};
		
		List roleList = roleDao.findByNamedQuery("findRoleByNameAndSiteId", str, obj);
		boolean temp = false;
		if(roleList != null && roleList.size() > 0){
			temp = true;
		}
		return temp;
	}
	
	public boolean findAssignmentByRoleId(String roleId){
		List addignmentList = assignmentDao.findByNamedQuery("findAssignmentByRoleid", "roleId", roleId);
		List menuAuthList = menuAuthorityDao.findByNamedQuery("findMenuAuthorityByRoleId", "roleId", roleId);
		boolean temp = false;
		if((addignmentList != null && addignmentList.size() > 0) || (menuAuthList != null && menuAuthList.size() > 0)){
			temp = true;
		}
		return temp;
	}
	
	
	public List findAssignmentListByRoleId(String roleId){
		List addignmentList = assignmentDao.findByNamedQuery("findAssignmentByRoleid", "roleId", roleId);
		return addignmentList;
	}
	
	public String findSiteIdByRoleId(String roleId){
		Role role =  roleDao.getAndNonClear(roleId);
		String siteId = role.getSite().getId();
		return siteId;
	}
	
	public List findSiteTree(String treeId,String siteId,boolean isUpSystemAdmin){
		List list = siteDao.findByNamedQuery("findFirstLevelSiteTreeByPid");
		Site site = null;
		String hasSiteId = "";
		List treeList = new ArrayList();
		if(treeId != null && treeId.equals("0")){
			if(list != null &&  list.size() > 0){
				site = (Site)list.get(0);
				hasSiteId = site.getId();
			}
			if(hasSiteId.equals(siteId)){
				List newList = siteDao.findByNamedQuery("findSiteTreeByPid","pid",siteId);
				for(int i = 0 ; i < newList.size() ; i++){					
					site = (Site)newList.get(i);
					Object[] obj = new Object[4];
					obj[0] = site.getId();
					obj[1] = site.getName();
					obj[2] = "";
					obj[3] = true;
					treeList.add(obj);
				}				
			}
		}
		return treeList;
	
	}
	
	public String findAllSiteAdminBySites() {
		// TODO Auto-generated method stub
		String strRoleIds = "";
		List roleList = roleDao.findByDefine("findSiteAdminRoleByRoleName","roleName","'网站管理员','系统管理员'");
		for(int i = 0 ; i < roleList.size() ; i++){
			Role role = (Role)roleList.get(i);
			String roleId = role.getId();
			strRoleIds = strRoleIds + "," +roleId;
		}
		if(strRoleIds.startsWith(",")){
			strRoleIds = strRoleIds.replaceFirst(",", "");
		}
		return strRoleIds;
		
	}
	
	/**
	 * @param columnDao the columnDao to set
	 */
	public void setColumnDao(ColumnDao columnDao) {
		this.columnDao = columnDao;
	}

	/**
	 * @param siteDao the siteDao to set
	 */
	public void setSiteDao(SiteDao siteDao) {
		this.siteDao = siteDao;
	}
	

	/**
	 * @param roleDao 要设置的 roleDao
	 */
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}


	/**
	 * @param resourceDao the resourceDao to set
	 */
	public void setResourceDao(ResourceDao resourceDao) {
		this.resourceDao = resourceDao;
	}



	/**
	 * @param authorityDao the authorityDao to set
	 */
	public void setAuthorityDao(AuthorityDao authorityDao) {
		this.authorityDao = authorityDao;
	}


	/**
	 * @param operationDao the operationDao to set
	 */
	public void setOperationDao(OperationDao operationDao) {
		this.operationDao = operationDao;
	}


	/**
	 * @param menuDao the menuDao to set
	 */
	public void setMenuDao(MenuDao menuDao) {
		this.menuDao = menuDao;
	}


	/**
	 * @param assignmentDao the assignmentDao to set
	 */
	public void setAssignmentDao(AssignmentDao assignmentDao) {
		this.assignmentDao = assignmentDao;
	}




	/**
	 * @param userDao the userDao to set
	 */
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}




	
	/**
     * 写入日志文件
     * @param roleName
     * @param siteId
     * @param sessionID
     * @param categoryName
     */
    public void addLogs(String roleName, String siteId, String sessionID, String categoryName) {
    	
    	systemLogDao.addLogData(categoryName, siteId, sessionID, roleName);
    }

	/**
	 * @param systemLogDao the systemLogDao to set
	 */
	public void setSystemLogDao(SystemLogDao systemLogDao) {
		this.systemLogDao = systemLogDao;
	}


	/**
	 * @param menuFunctionDao the menuFunctionDao to set
	 */
	public void setMenuFunctionDao(MenuFunctionDao menuFunctionDao) {
		this.menuFunctionDao = menuFunctionDao;
	}


	/**
	 * @param menuAuthorityDao the menuAuthorityDao to set
	 */
	public void setMenuAuthorityDao(MenuAuthorityDao menuAuthorityDao) {
		this.menuAuthorityDao = menuAuthorityDao;
	}

}
