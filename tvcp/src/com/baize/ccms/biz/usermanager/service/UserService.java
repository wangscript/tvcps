/**
 * <p>
 * ccms_2 信息管理系统
 * </p>
 * <p>
 * UserService.java Create on Jan 19, 2009 9:31:15 AM
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008；南京百泽网络科技有限公司
 * </p>
 * <p>
 * Company: 南京百泽网络科技有限公司
 * </p>
 */
package com.baize.ccms.biz.usermanager.service;

import java.util.List;
import java.util.Map;

import com.baize.ccms.biz.columnmanager.domain.Column;
import com.baize.ccms.biz.usermanager.domain.Assignment;
import com.baize.ccms.biz.usermanager.domain.Organization;
import com.baize.ccms.biz.usermanager.domain.User;
import com.baize.common.core.dao.Pagination;

/**
 * <p>标题: —— 用户业务逻辑处理类接口</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 用户管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 娄伟峰
 * @version 1.0
 * @since Feb 6, 2009 12:17:02 PM
 * @history（历次修订内容、修订人、修订时间等） 
 */
public interface UserService {

	/**
	 * 删除数据
	 */
	public void deleteUser(String id);
	

	
	/**
	 * 查询数据
	 */
	public 	User findUserByKey(String id);
	
	/**
	 * 查询所有用户
	 * @return
	 */
	public List<User> findAll();

	
	/**
	 * 查找用户数据
	 * @param pagination 分页对象
	 * @return
	 */
	public Pagination findUserData(Pagination pagination,String userId);
	
	/**
	 * 根据机构查找用户数据
	 * @param pagination 分页对象
	 * @return
	 */
	public Pagination findUserDataByOrgId(Pagination pagination,String nodeid,String userId);
	
	/**
	 * 根据ID字符串删除数据
	 * @param ids ID集合
	 * @param siteId
	 * @param sessionID
	 */
	void deleteUserByIds(String ids, String siteId, String sessionID);
	
	/**
	 * 按照网站id或者栏目id查找栏目树(根据不同的角色来判断显示内容)
	 * @param siteId 	    网站id
	 * @param columnId 	    栏目id
	 * @param sessionId     当前登陆用户的id
	 * @return			    返回List
	 */
	List findColumnTreeBySiteIdAndColumnId(String siteId, String columnId, String sessionId, boolean isUpSystemAdmin, boolean isSiteAdmin);

	
	/**
	 * 查找数据
	 * @param id 栏目id
	 * @return 栏目list
	 */
	List<Column> findColumnData(String id);
	
	
	
	
	
	
	
	/**
	 * 添加用户
	 * @param organizationId  组织id
	 * @param user            用户对象
	 * @param siteId          网站id
	 * @param sessionID
	 */
	void addUser(String organizationId, User user, String siteId, String sessionID);
	
	/**
	 * 修改用户
	 * @param user    用户对象
	 * @param siteId  网站id
	 * @param sessionID
	 */
	void modifyUser(User user, String siteId, String sessionID);
	
	/**
	 * 查询角色
	 * @param id               用户id
	 * @param isUpSystemAdmin  
	 * @param isSiteAdmin
	 * @param siteId           网站id
	 * @param userId           当前登陆用户的id
	 * @return                 返回查询的角色数据和被选择的角色数据
	 */
	Map findRole(String id, boolean isUpSystemAdmin, boolean isSiteAdmin, String siteId, String userId);
	
	/**
	 * 获得操作列表
	 * @param itemid   栏目节点ID
	 * @param userid   用户ID
	 * @param siteId   网站ID
	 * @return
	 */
	Map findoperationlist(String itemid, String userid, String siteId);
	
	/**
	 * 删除分配
	 * @param assignmentList
	 */
	void deleteAssignment(List assignmentList) ;
	
	/**
	 * 添加分配 
	 * @param checkedTreeIds 角色设置页面所选择的角色的ID
	 * @param userid 用户的ID
	 * @param operatorid 操作的ID
	 * @param systemcheckvalue 系统管理员的角色ID
	 */
	void addAssignment(String checkedTreeIds,String userid,String operatorid,String systemcheckvalue);
	
	/**
	 * 权限设置保存业务方法
	 * @param operId 操作人ID
	 * @param siteId 网站ID
	 * @param operationId 操作ID
	 * @param itemId 节点ID
	 * @param setChild 是否设置下级栏目继承自上级栏目
	 * @param userid 用户ID
	 */
	void addsetSave(String operId, String siteId, String operationId, String itemId, String setChild, String userid);
	
	/**
	 * 按照id查找组织
	 * @param id
	 * @return
	 */
	Organization findOrganizationByKey(String id) ;
	
	/**
	 * 按照用户id查找分配表中角色id
	 * @param userid
	 * @return
	 */
	List<Assignment> findRoleIdByUserId(String userid);
	
	/**
	 * 保存权力
	 * @param resourceList 资源集合
	 * @param operationStr 操作集合
	 * @param userId 用户ID
	 */
	void addRights(List resourceList,String[] operationStr,String userId,String type);
	
	/**
	 * 根据登录名查询用户对象
	 * @param loginName 登录名
	 * @return boolean 用户对象是否存在
	 */
	boolean findUserByLoginName(String loginName);
	
	/**
	 * 根据机构ID和网站ID查询数据
	 * @param pagination 分页对象
	 * @param nodeid 机构ID
	 * @param siteId 网站ID
	 * @return Pagination 分页对象
	 */
	Pagination findUserDataByOrgIdAndSiteId(Pagination pagination,String nodeid,String siteId,String userId) ;
	
	/**
	 * 根据网站ID查询分页数据
	 * @param pagination 分页对象
	 * @param siteIds 网站ID
	 * @return Pagination 分页对象
	 */
	Pagination findUserDataAndSiteId(Pagination pagination,String siteIds,String userId) ;
	
	/** 根据所有的网站ID查询所有的网站管理员 */
	String findAllSiteAdminBySiteIds();
	
	/**
	 * 删除权力表数据
	 * @param siteId 网站ID
	 * @param userId 设置的用户的ID
	 * @param checkedTreeIds 选择的角色
	 * @param systemcheckvalue 系统管理员角色
	 */
	void deleteRight(String siteId,String userId,String checkedTreeIds,String systemcheckvalue);
	
	/**
	 * 根据网站ID集合查询多个网站对象
	 * @param siteIds 网站ID集合
	 * @param siteId 当前网站ID
	 * @param user 用户对象
	 * @param temp 判断是栏目权限使用还是功能权限使用 1为功能权限，2为栏目权限
	 * @param isUpSystemAdmin 是否是超级管理员或者系统管理员
	 * @return List 网站集合
	 */
    List findSiteListBySiteIds(List siteIds,String siteId,User user,String temp,boolean isUpSystemAdmin);
    

 
   /**
    * 添加日志文件
    * @param siteId
    * @param sessionID
    * @param categoryName
    * @param setUserId
    */
    void addLogs(String siteId, String sessionID, String categoryName, String setUserId);
    
    /**
     * 查找当前在线用户
     * @param list
     * @return
     */
    List<Object> getCurrentLineUser(List<Object> list);
    
 
}
