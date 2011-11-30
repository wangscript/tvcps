/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.usermanager.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.j2ee.cms.biz.columnmanager.dao.ColumnDao;
import com.j2ee.cms.biz.columnmanager.domain.Column;
import com.j2ee.cms.biz.configmanager.dao.SystemLogDao;
import com.j2ee.cms.biz.sitemanager.dao.SiteDao;
import com.j2ee.cms.biz.sitemanager.domain.Site;
import com.j2ee.cms.biz.usermanager.dao.AssignmentDao;
import com.j2ee.cms.biz.usermanager.dao.AuthorityDao;
import com.j2ee.cms.biz.usermanager.dao.MenuAuthorityDao;
import com.j2ee.cms.biz.usermanager.dao.MenuDao;
import com.j2ee.cms.biz.usermanager.dao.MenuFunctionDao;
import com.j2ee.cms.biz.usermanager.dao.OperationDao;
import com.j2ee.cms.biz.usermanager.dao.OrganizationDao;
import com.j2ee.cms.biz.usermanager.dao.ResourceDao;
import com.j2ee.cms.biz.usermanager.dao.RightDao;
import com.j2ee.cms.biz.usermanager.dao.RoleDao;
import com.j2ee.cms.biz.usermanager.dao.UserDao;
import com.j2ee.cms.biz.usermanager.domain.Assignment;
import com.j2ee.cms.biz.usermanager.domain.Authority;
import com.j2ee.cms.biz.usermanager.domain.MenuAuthority;
import com.j2ee.cms.biz.usermanager.domain.MenuFunction;
import com.j2ee.cms.biz.usermanager.domain.Operation;
import com.j2ee.cms.biz.usermanager.domain.Organization;
import com.j2ee.cms.biz.usermanager.domain.Resource;
import com.j2ee.cms.biz.usermanager.domain.Right;
import com.j2ee.cms.biz.usermanager.domain.Role;
import com.j2ee.cms.biz.usermanager.domain.User;
import com.j2ee.cms.biz.usermanager.service.UserService;
import com.j2ee.cms.sys.GlobalConfig;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.util.SqlUtil;
import com.j2ee.cms.common.core.util.StringUtil;
import com.j2ee.cms.common.core.web.event.RequestEvent;
import com.j2ee.cms.common.core.web.event.ResponseEvent;

/**
 * <p>标题: —— 用户业务逻辑处理类</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 用户管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 娄伟峰
 * @version 1.0
 * @since Feb 6, 2009 12:17:02 PM
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class UserServiceImpl implements UserService {
	private static final Logger log = Logger.getLogger(UserServiceImpl.class);
	/** 用户数据访问对象 */
	private UserDao userDao;
	/** 栏目DAO */
	private ColumnDao columnDao;
	/** 组织dao **/
	private OrganizationDao organizationDao;
	/** 角色dao **/
	private RoleDao roleDao;
	/** 分配dao **/
	private AssignmentDao assignmentDao; 
	/** 操作dao **/
	private OperationDao operationDao; 
	/** 权力dao **/
	private RightDao rightDao;
	/** 菜单dao **/
	private MenuDao menuDao;
	/** 资源dao **/
	private ResourceDao resourceDao;
	/** 权限dao **/
	private AuthorityDao authorityDao;
	/** 网站DAO*/
	private SiteDao siteDao;
	/**注入日志dao*/
	private SystemLogDao systemLogDao;
	
	/** 菜单具体功能dao */
	private MenuFunctionDao menuFunctionDao;
	
	/** 菜单角色权限dao */
	private MenuAuthorityDao menuAuthorityDao;
	private Resource resource;
	
	
	public ResponseEvent validateData(RequestEvent req) throws Exception {		
		return null;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}


	public void deleteUser(String id) {
		// TODO Auto-generated method stub
		User user = userDao.getAndClear(id);
		user.setDeleted(true);
		userDao.update(user);
		//userDao.deleteByKey(id);
	}
	
	public void deleteUserByIds(String ids, String siteId, String sessionID) {
		// TODO Auto-generated method stub
		User user = null;
		if(!StringUtil.isEmpty(ids)){
			String[]  str = ids.split(",");
			if(str.length == 0){
				user = userDao.getAndClear(ids);
				user.setDeleted(true);
				userDao.update(user);
			}else{
				for(int i = 0 ; i < str.length ; i++){
					user = userDao.getAndClear(str[i]);
					if(!user.getLoginName().equals("admin")) {
						user.setDeleted(true);
						userDao.update(user);
						
						
						// 写入日志
						String categoryName = "用户管理->删除";
						String param = user.getLoginName();
						systemLogDao.addLogData(categoryName, siteId, sessionID, param);
					}
				}
			}
		}
	//	userDao.deleteByIds(ids);
	}


	public User findUserByKey(String id) {
		return userDao.getAndNonClear(id);
	}




	/**
	 * 查询所有用户
	 */
	public List<User> findAll() {
		return userDao.findAll();
	}

	public Pagination findUserData(Pagination pagination,String userId) {
		return userDao.getPagination(pagination,"userId",userId);
	}
	
	public Pagination findUserDataAndSiteId(Pagination pagination,String siteIds,String userId) {
		String str[] = {"siteIds","userId"}; 
		Object obj[] = {siteIds,userId};
		return userDao.getPagination(pagination,str,obj);
	}
	
	public Pagination findUserDataByOrgId(Pagination pagination,String nodeid,String userId) {
		String str[] = {"nodeid","userId"}; 
		Object obj[] = {nodeid,userId};
		return userDao.getPagination(pagination,str,obj);
	}
	
	public Pagination findUserDataByOrgIdAndSiteId(Pagination pagination,String nodeid,String siteIds,String userId) {
		String str[] = {"nodeid","siteIds","userId"}; 
		Object obj[] = {nodeid,siteIds,userId};
		return userDao.getPagination(pagination,str,obj);
	}
	
	/**
	 * 查找数据
	 * @param id 栏目id
	 * @return 栏目list
	 */
	public List<Column> findColumnData(String id) {
		return columnDao.findByNamedQuery("findChildren", "id", id);
	}

	/**
	 * 按照网站id或者栏目id查找栏目树(根据不同的角色来判断显示内容)
	 * @param siteId 	    网站id
	 * @param columnId 	    栏目id
	 * @param sessionId     当前登陆用户的id
	 * @return			    返回List
	 */
	public List findColumnTreeBySiteIdAndColumnId(String siteId, String columnId, String sessionId, boolean isUpSystemAdmin, boolean isSiteAdmin) {
		User user  = userDao.getAndNonClear(sessionId);
		List siteList = user.getSiteIds();
	
		List columnList = new ArrayList();
		/* if(columnId == null) {
			// 如果是系统管理员，查询所有数据
			if(isUpSystemAdmin) {
//				columnList =  columnDao.findByNamedQuery("findColumnBySystemAdmin");
				columnList = columnDao.findByNamedQuery("findColumnBySiteAdmin", "siteId", siteId);
			
			// 如果是网站管理员，需要根据网站ID查询
			} else  if(isSiteAdmin) {
				columnList = columnDao.findByNamedQuery("findColumnBySiteAdmin", "siteId", siteId);
			
			// 如果是普通用户，需要根据用户ID和网站ID查询
			} else {
				String[] params = {"siteId", "creatorId"};
				Object[] values ={siteId, sessionId};
				columnList = columnDao.findByNamedQuery("findColumnByNormalUser", params, values);
			}
		} else {
//			columnList = columnDao.findByNamedQuery("findColumnTreeByColumnId", "columnId", columnId);
			// 如果是系统管理员，按照栏目id查询所有数据
			if(isUpSystemAdmin) {
//				columnList =  columnDao.findByNamedQuery("findColumnTreeByColumnId", "columnId", columnId);
				String[] params = {"siteId", "columnId"};
				Object[] values ={siteId, columnId};
				columnList = columnDao.findByNamedQuery("findColumnTreeBySiteIdAndColumnId", params, values);
			
			// 如果是网站管理员，需要根据网站ID和栏目id查询
			} else  if(isSiteAdmin) {
				String[] params = {"siteId", "columnId"};
				Object[] values ={siteId, columnId};
				columnList = columnDao.findByNamedQuery("findColumnTreeBySiteIdAndColumnId", params, values);
			
			// 如果是普通用户，需要根据栏目id和用户ID以及网站ID查询
			} else {
				String[] params = {"siteId", "columnId", "creatorId"};
				Object[] values ={siteId, columnId, sessionId};
				columnList = columnDao.findByNamedQuery("findColumnTreeBySiteIdAndColumnIdAndCreatorId", params, values);
			}
		}*/
		if(columnId == null){
			columnList = columnDao.findByNamedQuery("findParentColumnBySiteId", "siteId", siteId);
		}else{
			String[] params = {"columnId","siteId"};
			Object[] values ={columnId,siteId};
			columnList = columnDao.findByNamedQuery("findColumnTreeByColumnIdAndSiteId",params, values);
			
		}
		return columnList;
	}	

	
	
	
	/**
	 * 添加用户
	 * @param organizationId  组织id
	 * @param user            用户对象
	 * @param siteId          网站id
	 * @param sessionID
	 */
	public void addUser(String organizationId, User user, String siteId, String sessionID) {
		Organization organization = new Organization();
		if(!StringUtil.isEmpty(organizationId)){
			organization = organizationDao.getAndNonClear(organizationId); 
		}
		List siteList = new ArrayList();
	/*	CommaList commList = new CommaList();
		List clist = commList.parse(siteId);*/
	 
/*		
		String[] str = new String[1];
		str[0] = siteId;*/
		siteList.add(siteId);
		log.debug("siteList================="+siteList);
		user.setSiteIds(siteList);
		user.setOrganization(organization);
		user.setDeleted(false);
		String password = user.getPassword();
		String md5Pwd = "";
		if (password.length() == 32) {
			md5Pwd = password;
		} else {
			md5Pwd = StringUtil.encryptMD5(password);
		}
		 
		user.setPassword(md5Pwd);
		userDao.save(user);
		
		// 写入日志文件
		String categoryName = "用户管理->添加";
		String param = user.getLoginName();
		systemLogDao.addLogData(categoryName, siteId, sessionID, param);
		
	}

	/**
	 * 修改用户
	 * @param user    用户对象
	 * @param siteId  网站id
	 * @param sessionID
	 */
	public void modifyUser(User user, String siteId, String sessionID) {
	
		String userName = user.getName();
		if(userName.equals("系统管理员") || userName.equals("超级管理员")){
			User newuser = userDao.getAndNonClear(user.getId());
			String email = user.getEmail();
			String password = user.getPassword();
			String position = user.getPosition();
			newuser.setEmail(email);
			if(password.length() != 32){
				newuser.setPassword(StringUtil.encryptMD5(password));	
			}else{
				newuser.setPassword(password);
			}
			newuser.setPosition(position);
			userDao.update(newuser);
		}else if(userName.equals("网站管理员")){
			User newuser = userDao.getAndNonClear(user.getId());
			
			String email = user.getEmail();
			String password = user.getPassword();
			String position = user.getPosition();
			newuser.setEmail(email);
			if(password.length() != 32){
				newuser.setPassword(StringUtil.encryptMD5(password));	
			}else{
				newuser.setPassword(password);
			}
			if(user.getOrganization() != null){
				String orgId = user.getOrganization().getId();
				if(orgId != null && !orgId.equals("") && !orgId.equals("null")){
					newuser.setOrganization(user.getOrganization());
				}
			}
			newuser.setPosition(position);
			userDao.update(newuser);
		}else{
			User newuser = userDao.getAndNonClear(user.getId());
			newuser.setName(user.getName());
			String password = user.getPassword();
			if(password.length() != 32){
				String strpassword = StringUtil.encryptMD5(password);
				newuser.setPassword(strpassword);
			}else{
				newuser.setPassword(password);
			}
			newuser.setEmail(user.getEmail());
			newuser.setPosition(user.getPosition());
			newuser.setPersonHomePage(user.getPersonHomePage());
			newuser.setMobileTel(user.getMobileTel());
			newuser.setOfficeTel(user.getOfficeTel());
			newuser.setHomeTel(user.getHomeTel());
			newuser.setOrganization(user.getOrganization());
			
			/*user.setDeleted(false);
			user.setRegisterDate(new Date());
			List siteList = new ArrayList();
			siteList.add(siteId);
			user.setSiteIds(siteList);*/
			
			userDao.update(newuser);
		}
		
		// 写入日志文件
		String categoryName = "用户管理->修改";
		String param = user.getLoginName();
		systemLogDao.addLogData(categoryName, siteId, sessionID, param);
		
	}
	
	/**
	 * 查询角色
	 * @param id               用户id
	 * @param isUpSystemAdmin  是否是系统管理员
	 * @param isSiteAdmin      是否是网站管理员
	 * @param siteId           网站id
	 * @param userId           当前登陆用户的id
	 * @return                 返回查询的角色数据和被选择的角色数据
	 */
	public Map findRole(String id, boolean isUpSystemAdmin, boolean isSiteAdmin, String siteId, String userId) {
		Map map = new HashMap();
		//查询所有网站的角色
		List allRoleList = new ArrayList();
	//	List assignmentList = new ArrayList();
		Role role = new Role();
		//如果是系统管理员以上
		if(isUpSystemAdmin == true) {
			//查询所有网站的角色
			List siteIdList = roleDao.findByNamedQuery("findRoleSiteId");
			Object allRoleData[] = new Object[siteIdList.size()+1];
			Object chooseRoleData[] = new Object[siteIdList.size()+1];
			List choosesiteIdList = assignmentDao.findByNamedQuery("findSiteIdByUserId", "id", id);	
			List sytstemList = new ArrayList();
			sytstemList = roleDao.findByNamedQuery("findRoleName","roleName","系统管理员");
			Object[] systemRole = (Object[])sytstemList.get(0);
			//获取系统管理员的角色ID
			String systemroleid = "";
			if(systemRole != null && systemRole.length > 0){
				systemroleid = String.valueOf(systemRole[0]);
			}
			allRoleData[0] = sytstemList;
			String str[] = {"userid","roleid"};
			Object obj[] = {id,systemroleid};
			List assignmentList = assignmentDao.findByNamedQuery("findAssignmentByUseridAndRoleid",str,obj);
			if(assignmentList != null && assignmentList.size()>0){
				Assignment assignment = (Assignment)assignmentList.get(0);
				//int roleid = assignment.getRole().getId();
				chooseRoleData[0] = sytstemList;
			}else{
				chooseRoleData[0] = new ArrayList();
			}
			//所有网站id集合
			for(int i = 0; i < siteIdList.size(); i++) {			
					//所有选择的角色 的ID
					List chooseRoleList = new ArrayList();
					//根据网站ID查询
					allRoleList = roleDao.findByNamedQuery("findRoleBySiteId", "siteId", (String.valueOf(siteIdList.get(i))));
					//所有角色的集合
					allRoleData[i+1] = allRoleList;	
					//判断选择的网站ID是否在所有网站ID集合里面
					boolean bol = false;
					for(int j = 0 ; j < choosesiteIdList.size(); j++) {	
						if(String.valueOf(choosesiteIdList.get(j)).equals((String) siteIdList.get(i))) {
							bol = true;
						}
					}
					if(bol == true) {
						//查询当前所选择用户所选择的角色
						this.getRoleId(id, chooseRoleList, (String) siteIdList.get(i), userId);	
						chooseRoleData[i+1] = chooseRoleList;
					}else{
						chooseRoleData[i+1] = new ArrayList();
					}
			}
			map.put("chooseRoleData", chooseRoleData);
		    map.put("allRoleData", allRoleData);			
		} else  {
			//查询当前网站的角色
			allRoleList = roleDao.findByNamedQuery("findRoleBySiteId", "siteId", siteId);
			Object allRoleData[] = new Object[1];
			allRoleData[0] = allRoleList;				
			//所有选择的角色 的ID
			List chooseRoleList = new ArrayList();
			List siteIdList = assignmentDao.findByNamedQuery("findSiteIdByUserId", "id", id);				
			Object chooseRoleData[] = new Object[siteIdList.size()];
			for(int i = 0 ; i < siteIdList.size(); i++) {				
				//查询当前所选择用户所选择的角色
				this.getRoleId(id, chooseRoleList, (String) siteIdList.get(i), userId);	
				chooseRoleData[i] = chooseRoleList;
			}
			map.put("allRoleData", allRoleData);
		    map.put("chooseRoleData", chooseRoleData);
		}
		return map;
	}
	
	/**
	 * 按照用户id查找分配表中角色id
	 * @param userid
	 * @return
	 */
	public List<Assignment> findRoleIdByUserId(String userid) {
		return assignmentDao.findByNamedQuery("findRoleIdByUserId", "id",userid);		
	}
	
	/**
	 * 根据用户ID查询角色id
	 * @param assignmentList  分配列表
	 * @param userid          用户id
	 * @param loginUserid     登陆用户id 
	 */
	private void getRoleId(String userId, List roleidList, String siteid, String loginUserid) {
		//判断用户是否是超级系统管理员
		boolean bol = false;
		List assignmentList = new ArrayList();
		if(siteid != null) {
			assignmentList = this.findRoleIdByUserIdAndSiteid(userId, siteid);
		} else {
			assignmentList = assignmentDao.findByNamedQuery("findRoleIdByUserId", "id", userId);
		}
		Assignment assignment = null;
		Role role = null;
		Site site = null;
		//查询当前用户所有的角色
		for(int i =0 ; i < assignmentList.size(); i++ ){
			assignment= new Assignment();
			Object obj[] = new Object[4];
			assignment = (Assignment) assignmentList.get(i);
			//获取角色对象
			role = assignment.getRole();
			String roleid = role.getId();
			String roleName = role.getName();
			site = role.getSite();
			String siteId = null;
			String siteName = "";
			if(site != null){
				siteId = site.getId();			
				siteName = site.getName();
			}		
			obj[0] = roleid;
			obj[1] = roleName;
			//角色是叶子节点
			obj[2] = siteId;
			obj[3] = siteName;
			roleidList.add(obj);
		}
	}
	
	public Map findoperationlist(String columnId, String userId, String siteId) {	
		Map map = new HashMap();
		List assignMentList = assignmentDao.findByNamedQuery("findRoleIdByUserId","id",userId);
		if(assignMentList != null && assignMentList.size() > 0){
			map.put("roleid", "true");
		}else{
			map.put("roleid", "");
		}
		
		if(columnId == null || columnId.equals("")  || columnId.equals("null")){
			columnId = "0";
		}
		Object[] allData = new Object[3];
		Object[] chooseData = new Object[3];
		boolean columnExtends = false;
		boolean columnExtends1 = false;
		boolean columnExtends2 = false;
		Map articleMap =  this.getTreeCheckBoxChooseObject(columnId, userId, siteId, Operation.TYPE_ARTICLE, "articleroot", "文章管理");		
		chooseData[0] = (ArrayList)articleMap.get("tempList"); 
		columnExtends = Boolean.parseBoolean(String.valueOf(articleMap.get("columnExtends")));
		Map columnMap =  this.getTreeCheckBoxChooseObject(columnId, userId, siteId, Operation.TYPE_COLUMN, "columnroot", "栏目管理");
		chooseData[1] = (ArrayList)columnMap.get("tempList"); 
		columnExtends1 = Boolean.parseBoolean(String.valueOf(columnMap.get("columnExtends")));
		Map templateSetMap =  this.getTreeCheckBoxChooseObject(columnId, userId, siteId, Operation.TYPE_TEMPLATESET, "templatesetroot", "模板设置");
		chooseData[2] = (ArrayList)templateSetMap.get("tempList"); 
		columnExtends2 = Boolean.parseBoolean(String.valueOf(templateSetMap.get("columnExtends")));
	/*	if(columnId == null || columnId.equals("0") || columnId.equals("")){
			//如果栏目id为0，则是查询整个网站设置的权限
	
		}else{
			//查询某个栏目设置的权限
		}*/
		if((columnExtends == true) || (columnExtends1 == true) || (columnExtends2 == true)){
			columnExtends = true;
		}
		List allarticleList = this.getTreeCheckBoxAllObject(Operation.TYPE_ARTICLE, "articleroot", "文章管理");
		List allcolumnList =  this.getTreeCheckBoxAllObject(Operation.TYPE_COLUMN, "columnroot", "栏目管理");
		List alltemplateSetList =  this.getTreeCheckBoxAllObject(Operation.TYPE_TEMPLATESET, "templatesetroot", "模板设置");
		allData[0] = allarticleList;
		allData[1] = allcolumnList;
		allData[2] = alltemplateSetList;
		map.put("allData", allData);
		map.put("chooseData", chooseData);
		map.put("columnExtends", columnExtends);
		return map;
	}

	/**
	 * 获取treecheckbox标签所需要的所有的数组对象
	 * @param operationType 操作类型
	 * @return List 组装好的LIST数据
	 */
	private List getTreeCheckBoxAllObject(String operationType,String rootId,String rootName){
		Operation operation = null;
		Object tempObj[] = null;
		List tempList = new ArrayList();
		List operationList = operationDao.findByNamedQuery("findOperationByOperationType","type",operationType);
		for(int i = 0 ; i < operationList.size() ; i++){
			operation = (Operation)operationList.get(i);			
			tempObj = new Object[4];
			tempObj[0] = operation.getId();
			tempObj[1] = operation.getDescription();
			tempObj[2] = rootId;
			tempObj[3] = rootName;
			tempList.add(tempObj);
		}
		return tempList;
	}
	/**
	 * 获取treecheckbox标签所需要的已选择数组对象
	 * @param columnId 栏目ID
	 * @param userId  用户ID
	 * @param siteId 网站ID
	 * @param operationType 操作类型
	 * @param rootId 一个treecheckbox根节点的ID
	 * @param rootName 一个treecheckbox根节点的名称 
	 * @return List 组装好的LIST数据
	 */
	private Map getTreeCheckBoxChooseObject(String columnId, String userId, String siteId,String operationType,String rootId,String rootName){
		Map map = new HashMap();
		Right right = null;
		Object tempObj[] = null;
		List tempList = new ArrayList();
		String str[] = {"itemType","itemId","userId","siteId","types"};
		Object obj[] = {Resource.TYPE_COLUMN,columnId,userId,siteId,operationType};
		List dataList = rightDao.findByNamedQuery("findRightByUserIdAndItemIdAndItemTypeAndSiteIdAndOperationType",str,obj);
		Object obj2[] = {Resource.TYPE_COLUMN,columnId,userId,siteId,Operation.TYPE_COLUMN_NONE};
		List nodeDataList = rightDao.findByNamedQuery("findRightByUserIdAndItemIdAndItemTypeAndSiteIdAndOperationType",str,obj2);
		Object allDataObj[] = {Resource.TYPE_COLUMN,columnId,userId,siteId,Operation.TYPE_COLUMN_NONE};
		List allDataList = rightDao.findByNamedQuery("findRightByUserIdAndItemIdAndItemTypeAndSiteIdAndOperationNoneType",str,allDataObj);

		StringBuffer columnids = new StringBuffer();
		//先把自己加进去
		columnids.append(columnId);
		this.getChildColumnId(columnids, columnId, siteId);
		String columnIds = columnids.toString();		
		if(StringUtil.contains(columnIds, ",0")){
			columnIds = columnIds.replace(",0", "");
			columnIds = columnIds + ",0";
		}
		
		columnIds = StringUtil.replaceFirst(columnIds,",");
		//columnIds = StringUtil.clearRepeat(columnIds);
		//columnIds = StringUtil.replaceFirst(columnIds,",");
		log.debug("strColumnIds===============-=================="+columnIds);
		String strColumnIds[] = columnIds.split(",");
		boolean columnExtends = false;
		//如果对此栏目单独设置了权限
		if(dataList != null && dataList.size() > 0){
			for(int i = 0 ; i < dataList.size() ; i++){
				right = (Right)dataList.get(i);
				tempObj = new Object[4];
				tempObj[0] = right.getAuthority().getOperation().getId();
				tempObj[1] = right.getAuthority().getOperation().getDescription();
				tempObj[2] = rootId;
				tempObj[3] = rootName;
				if(!columnExtends){
					columnExtends = right.getAuthority().isColumnExtends();
					log.debug("columnExtends================"+columnExtends);
				}				
				tempList.add(tempObj);
			}
		}else if(nodeDataList != null && nodeDataList.size() > 0){
			//如果对此栏目设置了无然后操作权限
			tempList = new ArrayList();
		}else if(allDataList == null || allDataList.size() == 0){
			//如果没有对此栏目单独设置了权限，则查询他的上级栏目有没有设置下级权限继承上级权限的
			List newDataList = null;
			
			for(int i = 0 ; ((i < strColumnIds.length) && (newDataList == null)) ; i++){
				String str1[] = {"itemType","itemId","userId","siteId","types"};
				Object obj1[] = {Resource.TYPE_COLUMN,strColumnIds[i],userId,siteId,operationType};
				List rightDataList = rightDao.findByNamedQuery("findRightByUserIdAndItemIdAndItemTypeAndSiteIdAndOperationType",str1,obj1);
				
				Object allDataObj1[] = {Resource.TYPE_COLUMN,strColumnIds[i],userId,siteId,Operation.TYPE_COLUMN_NONE};
				List allDataList1 = rightDao.findByNamedQuery("findRightByUserIdAndItemIdAndItemTypeAndSiteIdAndOperationNoneType",str1,allDataObj1);
 
				if(rightDataList != null && rightDataList.size() > 0 ){
					//如果对这个栏目单独设置了权限
					for(int j = 0 ; ((j < rightDataList.size()) && (newDataList == null)); j++){
						right = (Right)rightDataList.get(j);
						if((right.getAuthority() != null) && (right.getAuthority().getId() != null) && !(right.getAuthority().isColumnExtends())){
							newDataList = new ArrayList();
						}
						boolean setChild = right.getAuthority().isColumnExtends();
						if(setChild){
							newDataList = rightDataList;
						}
					}
				}else if(allDataList1 != null && allDataList1.size() > 0){
					 
					//则没有对这个栏目设置权限，对文章或者模板设置设置了权限
					newDataList = allDataList1;
				}
				
				Object obj3[] = {Resource.TYPE_COLUMN,strColumnIds[i],userId,siteId,Operation.TYPE_COLUMN_NONE};
				List nodeRightDataList = rightDao.findByNamedQuery("findRightByUserIdAndItemIdAndItemTypeAndSiteIdAndOperationType",str1,obj3);
				if(newDataList == null && nodeRightDataList != null && nodeRightDataList.size() > 0){
					newDataList = new ArrayList();
				}
			}
			if(newDataList != null){
				for(int i = 0 ; i < newDataList.size() ; i++){
					right = (Right)newDataList.get(i);
					tempObj = new Object[4];
					tempObj[0] = right.getAuthority().getOperation().getId();
					tempObj[1] = right.getAuthority().getOperation().getDescription();
					tempObj[2] = rootId;
					tempObj[3] = rootName;
					tempList.add(tempObj);
				}
			} 
		}
		map.put("tempList", tempList);
		map.put("columnExtends", columnExtends);
		return map;
	}
	
	/**
	 * 递归获取这个栏目的父栏目ID
	 * @param columnList 第一级栏目的集合
	 * @param columnids 栏目ID集合
	 * @return 返回所有的子栏目ID集合，以逗号隔开
	 */
	private void getChildColumnId(StringBuffer columnids, String columnId,String siteId){	
		String str[] = {"siteId","columnId"};
		Object obj[] = {siteId,columnId};
		List columnList = columnDao.findByNamedQuery("findColumnBySiteIdAndCurColumnId", str, obj);
		Column column = null;
		if (columnList != null && columnList.size() > 0) {		 
			column = (Column)columnList.get(0);
			if(column.getParent() != null ){
				String columnid = column.getParent().getId();
				columnids.append("," + columnid);
				// 递归
				getChildColumnId(columnids, columnid,siteId);							
			}else{
				columnids.append(",0");
			}	
		}else{
			columnids.append(",0");
		}	
	}
	/**
	 * 根据多个操作ID查询操作名
	 * @param alloperatorid  所有的操作ID，中间以逗号隔开
	 * @return List          list里面存放多个OBJECT数组，数组大小为2，1为ID，2为name 
	 */
	private List findOperationByIds(String alloperatorid){
		List alloperationlist = new ArrayList();
		//将字符串转换成LIST
		String stralloperatorid[] = StringUtil.split(alloperatorid, ",");
		for(int i = 0 ; i < stralloperatorid.length; i++){
			Object obj[] = new Object[2];
			obj[0] = stralloperatorid[i];
			Operation operation = operationDao.getAndClear(stralloperatorid[i]);
			if(operation != null ){
				obj[1] = StringUtil.convert(operation.getDescription());	
			}
			
			alloperationlist.add(obj);			
		}
		return alloperationlist;
	}
	
 
	
	/**
	 * 按照用户id和网站id查询角色
	 * @param id        用户id
	 * @param siteid    网站id
	 * @return          返回分配列表
	 */
	private List<Assignment> findRoleIdByUserIdAndSiteid(String id,String siteid) {
	/*	String str[] = {"id","siteid"};
		Object obj[] = {id,siteid};*/
		return assignmentDao.findByNamedQuery("findRoleIdByUserId", "id", id);		
	}
	
 
	
	/**
	 * 删除分配
	 * @param assignmentList
	 */
	public void deleteAssignment(List assignmentList) {
		for(int i = 0 ; i < assignmentList.size(); i++){
			Assignment assignment = new Assignment();
			assignment = (Assignment)assignmentList.get(i);
			assignmentDao.delete(assignment);
		}		
	}
	
	/**
	 * 添加分配 
	 * @param checkedTreeIds
	 * @param userid
	 * @param operatorid
	 */
	public void addAssignment(String checkedTreeIds,String userid,String operatorid,String systemcheckvalue) {
		//获取所有 的角色ID原来格式为188#1--角色ID，#，网站ID		 
		String menuIds = "";	 
		checkedTreeIds = StringUtil.replaceFirst(checkedTreeIds, ",");
		MenuAuthority menuAuthority = null;
		User operatoruser =  new User();
		//设置操作人ID
		operatoruser.setId(operatorid);
		User user = new User();
		//设置用户ID
		user.setId(userid);
		user = userDao.getAndNonClear(userid);
		String oldUserMenuIds = user.getMenuIds();
		List siteList = user.getSiteIds();
		//新设置的权限里面的网站ID
		String siteIds = "";
		String oldSiteIds = "";
	/*	for(int t = 0 ; t < siteList.size(); t++){
			oldSiteIds = oldSiteIds + "," +siteList.get(t);
		}*/
		oldSiteIds = StringUtil.replaceFirst(oldSiteIds,",");
		if(oldUserMenuIds != null ){
			String siteIdstemp[] = oldUserMenuIds.split("[*]");
			for(int t = 0 ; t < siteIdstemp.length; t++){
				oldSiteIds = oldSiteIds + "," + (siteIdstemp[t].split(","))[0];
			}
		}
	
		Site site = null;

		String systemSiteIds = "";
		//如果是系统管理员，则查询出所有网站
		List sytemSiteList = siteDao.findByNamedQuery("findSiteByDeleted");
		for(int i = 0 ; i < sytemSiteList.size() ; i++){
			site = (Site)sytemSiteList.get(i);
			systemSiteIds = systemSiteIds + "," + site.getId();
		}
		systemSiteIds = StringUtil.replaceFirst(systemSiteIds, ",");
		if(checkedTreeIds != null && !checkedTreeIds.equals("")){
			//保存设置的非系统管理员角色
			//将传过来的单独的一个数据分割如"角色ID1#网站ID1,角色ID2#网站ID1"，以中间的逗号分割。
			String strRoleIdAndSiteid[] = StringUtil.split(checkedTreeIds, ",");			
			Role role = null;			
			//将此次设置的权限保存到数据库
			for(int i = 0 ; i < strRoleIdAndSiteid.length; i++){				
				if(StringUtil.contains(strRoleIdAndSiteid[i], "#")){
					String strRoleId[] = StringUtil.split(strRoleIdAndSiteid[i],"#"); 
					if(strRoleId != null && strRoleId.length > 0){
		 				//根据角色ID查询出角色对象
						role = roleDao.getAndNonClear(strRoleId[0]);							 
						Assignment assignment = new Assignment();
						assignment.setOperator(operatoruser);					
						assignment.setUser(user);
						assignment.setOperateTime(new Date());
						assignment.setRole(role);	
						//保存数据
						assignmentDao.save(assignment);
					}
				}
			}
			Assignment assignment = new Assignment();
			//查询设置的角色，然后对用户设置相应的权限，需要修改用的menuIds,siteIds,chooseMenuIds
			for(int i = 0 ; i < strRoleIdAndSiteid.length; i++){	
				String userMenuIds = oldUserMenuIds;
				//存放一个用户的其他角色的菜单的ID
				String useMenuIds = "";
				if(StringUtil.contains(strRoleIdAndSiteid[i], "#")){
					String strRoleId[] = StringUtil.split(strRoleIdAndSiteid[i],"#"); 
					if(strRoleId != null && strRoleId.length > 0){			
						//一个用户可能有多个角色，看哪个角色具体有什么权限
						String str[] = {"id","siteid"};
						Object obj[] = {userid,strRoleId[1]};
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
									useMenuIds = useMenuIds + "," + menuAuthority.getMenuFunction().getId() + "#" + menuAuthority.getMenuFunction().getMenu().getId();
								}
								siteIds = siteIds + "," + assignment.getRole().getSite().getId(); 
							}else if(oneRoleName.equals("网站管理员")){
								siteIds = siteIds + "," + assignment.getRole().getSite().getId(); 
								List menuFunctionList = menuFunctionDao.findAll();
								MenuFunction menuFunction = null;
								for(int t = 0 ; t < menuFunctionList.size() ; t++){
									menuFunction = (MenuFunction)menuFunctionList.get(t);
									useMenuIds = useMenuIds + "," + menuFunction.getId() + "#" + menuFunction.getMenu().getId();
								}
							}else if(oneRoleName.equals("系统管理员")){							
								siteIds = systemSiteIds;															
							}
						}
					//	System.out.println("useMenuIds============================="+useMenuIds);
						//去除里面重复的(其他角色的菜单IDS)
						useMenuIds = StringUtil.clearRepeat(useMenuIds);
						//处理完的其他角色的菜单IDS
						useMenuIds = StringUtil.replaceFirst(useMenuIds, ",");
						
						userMenuIds = StringUtil.replaceFirst(userMenuIds, ",");
						String userOrdersMenuIds = user.getChooseMenuIds();
						userOrdersMenuIds = StringUtil.replaceFirst(userOrdersMenuIds, ",");
						if(userMenuIds == null || userMenuIds.equals("null") || userMenuIds.equals("")){
							userMenuIds = "";
						}
						if(userMenuIds != null && !userMenuIds.equals("") && !userMenuIds.equals("null")){			
							if(StringUtil.contains(userMenuIds,"*")){
								String strUserMenuIds[] = userMenuIds.split("[*]");
								boolean tt = false;
								for(int j = 0 ; j < strUserMenuIds.length ; j++ ){
									String strTemp = strUserMenuIds[j];
									//根据网站ID判断如果是对这个网站设置的权限
									if(strTemp != null && !strTemp.equals("") && (strTemp.split(",")[0]).equals(strRoleId[1])){
										String tempMenuIds = useMenuIds;
										tempMenuIds = StringUtil.clearRepeat(tempMenuIds);
										tempMenuIds = StringUtil.replaceFirst(tempMenuIds, ",");
										String lastMenuIds = ((strTemp.split(",")[0]) + "," +tempMenuIds);
										userMenuIds = userMenuIds.replace(strTemp, lastMenuIds);
										user.setMenuIds(userMenuIds);		
										tt = true;
									}									
								}
								if(!tt){
									String tempMenuIds = useMenuIds;
									tempMenuIds = StringUtil.clearRepeat(tempMenuIds);
									tempMenuIds = StringUtil.replaceFirst(tempMenuIds, ",");
									userMenuIds = userMenuIds + "*" + ((strRoleId[1].split(",")[0]) + "," +tempMenuIds);
									user.setMenuIds(userMenuIds);		
								}
							}else{
								//根据网站ID判断如果是对这个网站设置的权限					
								String tempMenuIds = useMenuIds;
								tempMenuIds = StringUtil.clearRepeat(tempMenuIds);		
								tempMenuIds = StringUtil.replaceFirst(tempMenuIds, ",");
								String lastMenuIds = ( strRoleId[1] + "," +tempMenuIds);
								if(userMenuIds != null && !userMenuIds.equals("")){					
									if((userMenuIds.split(",")[0]).equals(lastMenuIds.split(",")[0])){
										userMenuIds = userMenuIds.replace(userMenuIds, lastMenuIds);
									}else{
										userMenuIds = userMenuIds + "*" + lastMenuIds;
									}
									
								}else{
									userMenuIds = lastMenuIds;
								}								
								user.setMenuIds(userMenuIds);								 							 
							}
						}else{
							user.setMenuIds(strRoleId[1] + "," +useMenuIds);
						}
						
						if(userOrdersMenuIds == null || userOrdersMenuIds.equals("null") || userOrdersMenuIds.equals("")){
							userOrdersMenuIds = "";
						}
						if(userOrdersMenuIds != null && !userOrdersMenuIds.equals("") && !userOrdersMenuIds.equals("null")){			
							if(StringUtil.contains(userOrdersMenuIds,"[*]")){
								String strUserOrdersMenuIds[] = StringUtil.split(userOrdersMenuIds, "*");
								boolean tt = false;
								for(int j = 0 ; j < strUserOrdersMenuIds.length ; j++ ){
									String strTemp = strUserOrdersMenuIds[j];
									//根据网站ID判断如果是对这个网站设置的权限
									if(strTemp != null && !strTemp.equals("") && (strTemp.split(",")[0]).equals(strRoleId[1])){
										String tempMenuIds = useMenuIds;
										tempMenuIds = StringUtil.clearRepeat(tempMenuIds);
										tempMenuIds = StringUtil.replaceFirst(tempMenuIds, ",");
										String lastMenuIds = ((strTemp.split(",")[0]) + "," +tempMenuIds);
										userOrdersMenuIds = userOrdersMenuIds.replace(strTemp, lastMenuIds);
										user.setChooseMenuIds(userOrdersMenuIds);			
										tt = true;
									}									
								}
								if(!tt){
									String tempMenuIds = useMenuIds;
									tempMenuIds = StringUtil.clearRepeat(tempMenuIds);
									tempMenuIds = StringUtil.replaceFirst(tempMenuIds, ",");
									userOrdersMenuIds = userOrdersMenuIds + "*" + ((strRoleId[1].split(",")[0]) + "," +tempMenuIds);
									user.setChooseMenuIds(userOrdersMenuIds);			
								}
							}else{
								//根据网站ID判断如果是对这个网站设置的权限					
								String tempMenuIds = useMenuIds;
								tempMenuIds = StringUtil.clearRepeat(tempMenuIds);		
								tempMenuIds = StringUtil.replaceFirst(tempMenuIds, ",");
								String lastMenuIds = ( strRoleId[1] + "," +tempMenuIds);
								if(userOrdersMenuIds != null && !userOrdersMenuIds.equals("")){					
									//userOrdersMenuIds = userOrdersMenuIds.replace(userOrdersMenuIds, lastMenuIds);
									if((userOrdersMenuIds.split(",")[0]).equals(lastMenuIds.split(",")[0])){
										userOrdersMenuIds = userOrdersMenuIds.replace(userOrdersMenuIds, lastMenuIds);
									}else{
										userOrdersMenuIds = userOrdersMenuIds + "*" + lastMenuIds;
									}
								}else{
									userOrdersMenuIds = lastMenuIds;
								}							
								user.setChooseMenuIds(userOrdersMenuIds);								 							 
							}
						}else{
							user.setChooseMenuIds(strRoleId[1] + "," +useMenuIds);
						}
					
						siteIds = siteIds + "," +strRoleId[1];
						siteIds = StringUtil.replaceFirst(siteIds,",");
						siteIds = StringUtil.clearRepeat(siteIds);
						siteIds = StringUtil.replaceFirst(siteIds,",");
						String strSiteIds[] = siteIds.split(",");
						List newSiteList = new ArrayList();
				
						for(int n = 0 ; n < strSiteIds.length ; n++){
							newSiteList.add(strSiteIds[n]);							
						}				
						user.setSiteIds(newSiteList);
						//修改这个用户的菜单
						userDao.update(user);
					}
				}				
			}			
		//处理掉没有对这几个网站设置的权限
			user = userDao.getAndNonClear(userid);
			String newUserMenuIds = user.getMenuIds();
			String newChooseMenuIds = user.getChooseMenuIds();
			//根据原来的网站IDS判断哪些不在原来里面
			String strOldIds[] = oldSiteIds.split(",");			
			for(int i = 0 ; i < strOldIds.length ; i++){
				if(!StringUtil.contains(siteIds, strOldIds[i])){
					if(StringUtil.contains(newUserMenuIds, strOldIds[i])){
						//如果这个网站ID，在新设置的里面
						String strNewUserMenuIds[] = newUserMenuIds.split("[*]");
						for(int j = 0 ; j < strNewUserMenuIds.length ; j++){
							if(StringUtil.contains(strNewUserMenuIds[j],strOldIds[i])){
								newUserMenuIds = newUserMenuIds.replace(strNewUserMenuIds[j], "");
							}
						}
					}
					if(StringUtil.contains(newChooseMenuIds, strOldIds[i])){
						//如果这个网站ID，在新设置的里面
						String strNewUserMenuIds[] = newChooseMenuIds.split("[*]");
						for(int j = 0 ; j < strNewUserMenuIds.length ; j++){
							if(StringUtil.contains(strNewUserMenuIds[j],strOldIds[i])){
								newChooseMenuIds = newChooseMenuIds.replace(strNewUserMenuIds[j], "");
							}
						}
					}		
				}
						
			}
			newUserMenuIds = StringUtil.replaceFirst(newUserMenuIds, "[*]");
			newChooseMenuIds = StringUtil.replaceFirst(newChooseMenuIds, "[*]");
			user.setChooseMenuIds(newChooseMenuIds);
			user.setMenuIds(newUserMenuIds);
			userDao.updateAndClear(user);
			
		}
		//保存系统管理角色权限
		if(systemcheckvalue != null && !systemcheckvalue.equals("")) {		
			List menuList = menuDao.findAll();
			Role systemRole = null;
		
			systemRole = roleDao.getAndNonClear(systemcheckvalue);
			Assignment assignment = new Assignment();		 
			assignment.setOperator(operatoruser);
		//	user = new User();
			//设置用户ID
		//	user.setId(userid);
			assignment.setUser(user);
			assignment.setOperateTime(new Date());
			assignment.setRole(systemRole);
			//保存数据
			assignmentDao.saveAndClear(assignment);	
			assignmentDao.cleanCache();
			String strSiteIds[] = siteIds.split(",");
			List newSiteList = new ArrayList();
			for(int n = 0 ; n < strSiteIds.length ; n++){
				newSiteList.add(strSiteIds[n]);
			}
			String temp = "";
			//如果是系统管理员
			List menuFunctionList = menuFunctionDao.findAll();
			MenuFunction menuFunction = null;
			String useMenuIds = "";
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
			temp = StringUtil.replaceFirst(temp, "[*]");
			if(temp != null && !temp.equals("")){
				user.setMenuIds(temp);
				user.setChooseMenuIds(temp);
			}
			user.setSiteIds(newSiteList);
			userDao.update(user);
		}
		
		if((checkedTreeIds == null || checkedTreeIds.equals("")) &&( systemcheckvalue == null || systemcheckvalue.equals(""))){
			//如果没有选择任何角色，则删除所有的角色
			user.setMenuIds("");
			user.setChooseMenuIds("");
			List temp = new ArrayList();
			temp.add("");
			user.setSiteIds(temp);
			userDao.update(user);
		}
		 
	}
	
	/**
	 * 权限设置保存业务方法
	 * @param operId 操作人ID
	 * @param siteId 网站ID
	 * @param operationId 操作ID
	 * @param itemId 节点ID
	 * @param setChild 是否设置下级栏目继承自上级栏目
	 * @param userid 用户ID
	 */
	public void addsetSave(String operId, String siteId, String operationIds, String columnId, String setChild, String userId) {
		User user = new User();
		user.setId(userId);
		Site site = new Site();
		site.setId(siteId);
		if(columnId == null || columnId.equals("") || columnId.equals("null")){
			columnId = "0";
		}
		
		//如果是对整个网站设置权限，并且设置了下级继承上级权限
		if((columnId != null && (columnId.equals("") || columnId.equals("0"))) && setChild != null && setChild.equals("true")){
			//如果设置了下级栏目继承上级栏目权限，则删除原来所有的
			//先查询出这个用户在这个网站所有的权限
			String str[] = {"userId","siteId"};
			Object obj[] = {userId,siteId};
			List deleteRightList = rightDao.findByNamedQuery("findRightByUserIdAndSiteId",str,obj);
			this.deleteData(deleteRightList, user, site, operationIds,columnId,true,false);
			this.addData(deleteRightList, user, site, operationIds,columnId,true,false);
		
		}else if(columnId != null && !columnId.equals("") && !columnId.equals("0") && setChild != null && setChild.equals("true")){
			//如果是对单个栏目设置权限，并且设置了下级继承上级权限
			StringBuffer sbcolumnIds = new StringBuffer();
			sbcolumnIds.append(columnId);
			//递归获取所有这个栏目的下级栏目
			this.getColumnId(sbcolumnIds, columnId, siteId);
			String strColumnIds = sbcolumnIds.toString();
			
			if(strColumnIds != null && !strColumnIds.equals("")){
				String str3[] = {"itemType","itemId","userId","siteId"};
				Object obj3[] = {Resource.TYPE_COLUMN,columnId,userId,siteId};
				List addRightList = rightDao.findByNamedQuery("findRightByUserIdAndItemIdAndItemTypeAndSiteId",str3,obj3);

				strColumnIds = StringUtil.replaceFirst(strColumnIds, ",");
				String tempColumnId[] = strColumnIds.split(",");
				for(int j = 0 ; j < tempColumnId.length ; j++){
					String newcolumnId = tempColumnId[j];
					String str[] = {"itemType","itemId","userId","siteId"};
					Object obj[] = {Resource.TYPE_COLUMN,newcolumnId,userId,siteId};
					List deleteRightList = rightDao.findByNamedQuery("findRightByUserIdAndItemIdAndItemTypeAndSiteId",str,obj);
					
					this.deleteData(deleteRightList, user, site, operationIds,columnId,true,true);
				}
				this.addData(addRightList, user, site, operationIds,columnId,true,true);
			}
			
		}else{
			//如果没有设置了下级栏目继承上级栏目权限，则删除原来的这一条记录
			//首先查询这个栏目的已有的数据
			String str[] = {"itemType","itemId","userId","siteId"};
			Object obj[] = {Resource.TYPE_COLUMN,columnId,userId,siteId};
			List deleteRightList = rightDao.findByNamedQuery("findRightByUserIdAndItemIdAndItemTypeAndSiteId", str, obj);
			this.deleteData(deleteRightList, user, site, operationIds,columnId,false,true);
			this.addData(deleteRightList, user, site, operationIds,columnId,false,true);
		}
		
		
		// 写入日志文件
		String categoryName = "";	 
		categoryName = "用户管理(栏目权限)->保存";
		String param = "";
		systemLogDao.addLogData(categoryName, siteId, userId, param);
	}
	

	/**
	 * 设置权限的具体删除和保存操作
	 * @param deleteRightList
	 * @param user
	 * @param site
	 * @param operationIds
	 */
	private void deleteData(List deleteRightList,User user,Site site,String operationIds,String columnId,boolean columnExtends,boolean noneOperation){
		Right right = null;
		Authority authority = null;
		//权力IDS
		String rightIds = "";
		//权限IDS
		String authorityIds = "";
		//资源IDS
		String resourceIds = "";
		Resource resource = null;
		for(int i = 0 ; i < deleteRightList.size() ; i++){
			right = (Right)deleteRightList.get(i);
			rightIds = rightIds + "," + SqlUtil.toSqlString(right.getId());			
			authorityIds = authorityIds + "," + SqlUtil.toSqlString(right.getAuthority().getId());			
			resourceIds = resourceIds + "," + SqlUtil.toSqlString(right.getAuthority().getResource().getId());
		 
		}
		rightIds = StringUtil.replaceFirst(rightIds, ",");
		authorityIds = StringUtil.replaceFirst(authorityIds, ",");
		resourceIds = StringUtil.replaceFirst(resourceIds, ",");
		if(rightIds != null && !rightIds.equals("")){
			//删除原来权力表数据
			rightDao.deleteByIds(rightIds);
		}		
		if(authorityIds != null && !authorityIds.equals("")){
			//删除原来权限表数据
			authorityDao.deleteByIds(authorityIds);
		}
		if(resourceIds != null && !resourceIds.equals("")){
			//删除原来资源表数据
			resourceDao.deleteByIds(resourceIds);
		}
 

	}
	private void  addData(List deleteRightList,User user,Site site,String operationIds,String columnId,boolean columnExtends,boolean noneOperation){
		
		Right right = null;
		Authority authority = null;
		//权力IDS
		String rightIds = "";
		//权限IDS
		String authorityIds = "";
		Resource resource = null;
		if(resource == null){
			resource = new Resource();
			resource.setIdentifier(columnId);
			resource.setSite(site);
			resource.setType(Resource.TYPE_COLUMN);
			resourceDao.save(resource);
		}
		operationIds = StringUtil.replaceFirst(operationIds, ",");
		if(((operationIds == null || operationIds.equals("") || operationIds.equals("null"))) && (noneOperation)){
			//如果对某个栏目设置的所有操作为空，则保存操作表里的无操作状态
			List operationList = operationDao.findByNamedQuery("findOperationByOperationType","type",Operation.TYPE_COLUMN_NONE);
			if(operationList != null && operationList.size() > 0){
				authority = new Authority();
				authority.setColumnExtends(columnExtends);
				authority.setResource(resource);
				
				authority.setOperation((Operation)operationList.get(0));
				authorityDao.save(authority);
				right = new Right();
				right.setAuthority(authority);
				right.setUser(user);
				//保存权力表
				rightDao.save(right);		
			}
		}else{
			String strOperationId[] = operationIds.split(",");
			
			for(int i = 0 ; i < strOperationId.length ; i++){
				authority = new Authority();
				authority.setColumnExtends(columnExtends);
				authority.setResource(resource);
				String operationId = strOperationId[i];
				if(operationId != null && !operationId.equals("")){
					String newOperationId = "";
					if(StringUtil.contains(operationId, "#")){
						newOperationId = (operationId.split("#")[0]);
					}		 
					authority.setOperation(operationDao.getAndNonClear(newOperationId));
					//保存权限表
					authorityDao.save(authority);
					right = new Right();
					right.setAuthority(authority);
					right.setUser(user);
					//保存权力表
					rightDao.save(right);			
				}		
			}	
		}
		
		
		
	}
	

	/**
	 * 按照资源类型和id查找资源信息
	 * @param resource
	 * @return
	 */
	public List<Resource> findResourceByTypeAndIdentifier(Resource resource) {
		String[] str = {"identifier", "siteid", "type"};
		Object[] obj = {resource.getIdentifier(), resource.getSite().getId(), resource.getType()};
		return resourceDao.findByNamedQuery("findResourceByTypeAndIdentifier", str, obj);		
	}
	
	/**
	 * 根据多个主键删除数据
	 * @param ids 主键字符串
	 */
	public void deleteRightByIds(String ids){
		if(ids != null && !ids.equals("")){
			ids = SqlUtil.toSqlString(ids);
			rightDao.deleteByIds(ids);
		}
		
	}
	
	/**
	 * 根据用户id查找权利表
	 * @param userId 用户id
	 * @return 返回权力对象
	 */
	public List<Right> findRightByUserId(String userId){
		return rightDao.findByNamedQuery("findRightByUserId", "userId", userId);
	}
	
 
 
	/**
	 * 保存权力
	 * @param resourceList 资源集合
	 * @param operationStr 操作集合
	 * @param userId 用户ID
	 */
	public void addRights(List resourceList,String[] operationStr,String userId,String type){	
		Right right = new Right();
		User user = new User();
		user.setId(userId);
		Authority authority = new Authority();
		if(resourceList.size() > 0 ){
			Resource resource = ((Resource)resourceList.get(0));
			String resourceid = resource.getId();
			List authorityOperList = null;
			for(int i = 0 ; i < operationStr.length ; i++){
				//根据资源ID查询所有的操作ID的数据
			
				authorityOperList = this.findAuthorityByResourceidAndOperationidAndOperationTypes(
									resourceid,(operationStr[i]),type);			
				//如果页面上没有选择这个资源的已经有的操作ID
				for(int x = 0; x < authorityOperList.size(); x++){
					authority = (Authority)authorityOperList.get(x);		
					right.setAuthority(authority);
					right.setUser(user);
					//保存数据至授权表
					log.debug("保存权力表数据----Right");
					rightDao.save(right);
				}	
			}
		}										
	}
	
	/**
	 *添加数据 
	 *@param right 权力对象
	 */
	public void addRight(Right right){
		rightDao.save(right);
	}
	
	/**
	 * 根据操作ID和资源ID查询权限 
	 * @param resourceId 资源ID
	 * @param operationId 操作ID
	 * @return List 
	 */
	public List<Authority> findAuthorityByResourceidAndOperationidAndOperationTypes(String resourceId,String operationId,String types){
		String[] str = {"resourceId","operationId","types"};
		Object[] obj = {resourceId,operationId,types};
		return authorityDao.findByNamedQuery("findAuthorityByResourceidAndOperationidAndOperationTypes", str, obj);
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
	
	/**
	 * 按照id查找组织
	 * @param id
	 * @return
	 */
	public Organization findOrganizationByKey(String id) {
		if(id != null) {
			return organizationDao.getAndNonClear(id);
		} else {
			return null;
		}
	}
	
	public boolean findUserByLoginName(String loginName){
		List userList = organizationDao.findByNamedQuery("findUserByLoginName", "loginName", loginName);		
		if(userList != null && userList.size() > 0){
			return true;
		}else{
			return false;
		}
	}
	
	public String findAllSiteAdminBySiteIds() {
		// TODO Auto-generated method stub
//		List siteList = siteDao.findAll();
		List siteList = siteDao.findByNamedQuery("findSiteByDeleted");
		
		Site site = null;
		String siteNames = ""; 
		for(int i = 0 ; i < siteList.size() ; i++){
			site = (Site)siteList.get(i);
			siteNames = siteNames + "," +SqlUtil.toSqlString(site.getName());
		}
		if(siteNames.startsWith(",")){
			siteNames = siteNames.replaceFirst(",", "");
		}
		User user = null;
		String userIds = "";
		List userList = userDao.findByDefine("findUserByUserName", "userName", siteNames);
		for(int i = 0 ; i < userList.size() ; i++){
			user = (User)userList.get(i);
			userIds = userIds + "," +user.getId();
			
		}
		if(userIds.startsWith(",")){
			userIds = userIds.replaceFirst(",", "");
		}
		return userIds;
	}
	
	public void deleteRight(String siteId,String userId,String checkedTreeIds,String systemcheckvalue){
		//如果页面没有选择任何角色则删除权力表所有的数据
		if((checkedTreeIds == null || checkedTreeIds.equals("") || checkedTreeIds.equals("null"))
				&& (systemcheckvalue == null || systemcheckvalue.equals("") || systemcheckvalue.equals("null"))){
			String str[] = {"userId","siteId"};
			Object obj[] = {userId,siteId};
			List list = rightDao.findByNamedQuery("findRightByUserIdAndSiteId", str, obj);
			Right right = null;
			String rightIds = "";
			for(int i = 0 ; i < list.size() ; i++){
				right = (Right)list.get(i);
				rightIds = rightIds + "," +SqlUtil.toSqlString(right.getId());
			}
			if(rightIds.startsWith(",")){
				rightIds = rightIds.replaceFirst(",", "");
			}
			rightDao.deleteByIds(rightIds);
		}else if(systemcheckvalue == null || systemcheckvalue.equals("") || systemcheckvalue.equals("null")){
			String str[] = {"id","siteid"};
			Object obj[] = {userId,siteId};
			List assList = assignmentDao.findByNamedQuery("findRoleIdByUserIdAndSiteId", str, obj);
			Assignment assignment = null;
		/*	Role role = null;
			Authorization authorization = null;
			//原来角色的操作ID
			String oldOperationIds = "";
			for(int i = 0 ; i < assList.size() ; i++){
				assignment = (Assignment)assList.get(i);
				role = assignment.getRole();
				String roleId = role.getId();
				List authorizationList = authorizationDao.findByNamedQuery("findAuthorizationByRoleId","roleId",roleId);
				for(int j = 0 ; j < authorizationList.size() ; j++){
					authorization = (Authorization)authorizationList.get(j);
					oldOperationIds = oldOperationIds + "," + SqlUtil.toSqlString(authorization.getAuthority().getOperation().getId());
				}
			}
			if(oldOperationIds.startsWith(",")){
				oldOperationIds = oldOperationIds.replaceFirst(",", "");
			}
			oldOperationIds = StringUtil.clearRepeat(oldOperationIds);
			
			//获取新设置的角色的操作ID
			String newOperationIds = "";
			String newRoleIds[] =  checkedTreeIds.split(",");
			for(int  i = 0 ; i > newRoleIds.length ; i++){
				List authorizationList = authorizationDao.findByNamedQuery("findAuthorizationByRoleId","roleId",newRoleIds[i]);
				for(int j = 0 ; j < authorizationList.size() ; j++){
					authorization = (Authorization)authorizationList.get(j);
					newOperationIds = newOperationIds + "," + SqlUtil.toSqlString(authorization.getAuthority().getOperation().getId());
				}
			}
			if(newOperationIds.startsWith(",")){
				newOperationIds = newOperationIds.replaceFirst(",", "");
			}
			newOperationIds = StringUtil.clearRepeat(newOperationIds);
			
			String stroldOperationIds[] = oldOperationIds.split(",");
			//最后处理完的字符串
			String overOperationIds = "";
			for(int i = 0 ; i < stroldOperationIds.length ; i++){
				if(!StringUtil.contains(newOperationIds, stroldOperationIds[i])){
					overOperationIds = overOperationIds + "," + stroldOperationIds[i];
				}
			}
			if(overOperationIds.startsWith(",")){
				overOperationIds = overOperationIds.replaceFirst(",", "");
			}
			String rightKey[] = {"userId","operationIds"};
			String rightValue[] = {SqlUtil.toSqlString(userId),overOperationIds};
			List rightList = new ArrayList();
			if(overOperationIds != null && !overOperationIds.equals("")){
				rightList = rightDao.findByDefine("findRightByUserIdAndOperationIds", rightKey, rightValue);
			}
			
			Right right = null;
			String rightIds = "";
			for(int i = 0 ; i < rightList.size() ; i++){
				right = (Right)rightList.get(i);
				rightIds = rightIds + "," +SqlUtil.toSqlString(right.getId());
			}
			rightIds = StringUtil.replaceFirst(rightIds, ",");
			if(!rightIds.equals("")){
				rightDao.deleteByIds(rightIds);
			}*/
			
		}
	}
	
	 public List findSiteListBySiteIds(List siteIds,String siteId,User user,String temp,boolean isUpSystemAdmin){
		 String strSiteIds = "";
		 if(isUpSystemAdmin){
			 for(int i = 0 ; i < siteIds.size() ; i++){
				 strSiteIds = strSiteIds + "," +SqlUtil.toSqlString((String)siteIds.get(i));
			 }
			 strSiteIds = StringUtil.replaceFirst(strSiteIds, ",");
		 }else{
			 strSiteIds = SqlUtil.toSqlString(siteId);
		 }
 
		return siteDao.findByDefine("findSiteBySiteIds", "siteIds", strSiteIds); 
 
	 }
	 
	 
	  
	 
	
	
	 /**
    * 添加日志文件
    * @param siteId
    * @param sessionID
    * @param categoryName
    */
    public void addLogs(String siteId, String sessionID, String categoryName, String setUserId) {
    	User user = userDao.getAndClear(setUserId);
    	String param = user.getName();
    	systemLogDao.addLogData(categoryName, siteId, sessionID, param);
    }
    
    /**
     * 查找当前在线用户
     * @param list
     * @return
     */
    public List<Object> getCurrentLineUser(List<Object> list) {
    	Map<String, String> map = new HashMap<String, String>();
    	map = GlobalConfig.ip;
    	if(!map.isEmpty()) {
    		String userIds = "";
    		for(Map.Entry<String, String> enty : map.entrySet()) {
    			String sessionID = enty.getKey();
    			String IP = enty.getValue();
    			if(StringUtil.isEmpty(userIds)) {
    				userIds = sessionID;
    			} else {
    				userIds += "," + sessionID;
    			} 
    		}
    		list = userDao.findByDefine("findCurrentLineUser", "userIds", SqlUtil.toSqlString(userIds));
    		for(int i = 0; i < list.size(); i++) {
    			Object[] object = (Object[])list.get(i);
    			String sesionID = object[0].toString();
    			String ip = map.get(sesionID);
    			object[3] = ip;
    		}
    	}
    	return list;
    }
    
	 
	/**
	 * @param organizationDao the organizationDao to set
	 */
	public void setOrganizationDao(OrganizationDao organizationDao) {
		this.organizationDao = organizationDao;
	}

	/**
	 * @param roleDao the roleDao to set
	 */
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	/**
	 * @param assignmentDao the assignmentDao to set
	 */
	public void setAssignmentDao(AssignmentDao assignmentDao) {
		this.assignmentDao = assignmentDao;
	}

 
	/**
	 * @param operationDao the operationDao to set
	 */
	public void setOperationDao(OperationDao operationDao) {
		this.operationDao = operationDao;
	}
 

	/**
	 * @param rightDao the rightDao to set
	 */
	public void setRightDao(RightDao rightDao) {
		this.rightDao = rightDao;
	}

	/**
	 * @param menuDao the menuDao to set
	 */
	public void setMenuDao(MenuDao menuDao) {
		this.menuDao = menuDao;
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
