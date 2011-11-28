  /**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.biz.usermanager.service;

import java.util.List;

import com.baize.ccms.biz.columnmanager.domain.Column;
import com.baize.ccms.biz.sitemanager.domain.Site;
import com.baize.ccms.biz.usermanager.domain.Menu;
import com.baize.ccms.biz.usermanager.domain.Operation;
import com.baize.ccms.biz.usermanager.domain.Role;
import com.baize.common.core.dao.Pagination;

/**
 * <p>标题: —— 角色业务逻辑处理最高接口</p>
 * <p>描述: —— 角色表业务逻辑处理最高接口</p>
 * <p>模块: 用户管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-2-25 上午11:18:14
 * @history（历次修订内容、修订人、修订时间等） 
 */
public interface RoleService {
	/**
	 *添加数据 
	 *@param role  角色对象
	 */
	void addRoles(Role role);
	
	/**
	 * 删除数据
	 * @param id 主键ID
	 */
	void deleteRole(String id);
	/**
	 * 根据多个主键删除数据
	 * @param ids
	 * @param siteId
	 * @param sessionID
	 */
	void deleteRoleByIds(String ids, String siteId, String sessionID) ;
	
	/**
	 * 修改数据
	 * @param role 角色对象 
	 */
	void modifyRole(Role role);
	
	/**
	 * 查询数据
	 * @param id 主键
	 * @return Role
	 */
	Role findRoleByKey(String id);
	

	
	/** 返回分页对象 
	 * @param pagination 分页对象
	 * @param siteid 网站ID
	 * @return Pagination 返回分页对象
	 */
	Pagination findRoleData(Pagination pagination,String siteid);
	
	/**
	 * 
	 * @param siteId 网站ID
	 * @return Site  网站对象
	 */
	Site findSiteBySiteId(String siteId);
	
	/**
	 * 查询所有的网站
	 * @param siteId 网站ID
	 * @param isUpSystemAdmin 系统管理员以上
	 * @return List 多个网站对象
	 */
	List<Site> findAllSite(String siteId,boolean isUpSystemAdmin);
	
	
	/**
	 * 根据网站名称查询
	 * @param siteName 网站名称
	 * @return Site  网站对象
	 */
	Site findSiteBySiteName(String siteName);
	
	/**
	 * 权限设置保存业务方法
	 * @param userId 用户ID
	 * @param siteId 网站ID
	 * @param menuId 菜单ID 
	 * @param roleId 角色ID
	 */
	void addSetMenuAuthority(String userId,String siteId,String menuId,String roleId);
		

    
    /**
     * 根据菜单主键查询菜单数据
     * @param id 菜单唯一标识
     * @return  List 
     */
    Menu findMenuDataById(String id);
   
     /**
     * 根据网站id和角色名查询角色
     * @param roleName 角色名
     * @param siteId 网站ID
     * @return 是否存在这个角色
     */
    boolean findRoleByNameAndSiteId(String roleName,String siteId);
    /**
     * 根据角色ID查询分配表
     * @param roleId 角色ID
     * @return 是否存在这个角色已经被使用的情况
     */
    boolean findAssignmentByRoleId(String roleId);
    

    /**
     * 查询所有的网站
     * @return 网站ID集合
     */
    String findAllSiteAdminBySites();  
    
    /**
     * 写入日志文件
     * @param roleName
     * @param siteId
     * @param sessionID
     * @param categoryName
     */
    void addLogs(String roleName, String siteId, String sessionID, String categoryName);
    
    /**
     * 查询网站树形结构
     * @param treeId 节点ID
     * @param siteId 当前网站的ID
     * @param isUpSystemAdmin 是否是系统管理员以上级别登陆的
     * @return List 树结构的LIST
     */
    List findSiteTree(String treeId,String siteId,boolean isUpSystemAdmin);
    
    /**
     * 根据角色ID和网站ID查询菜单权限
     * @param roleId 角色ID
     * @param siteId 网站ID 
     */
    List findMenuAuthorityByRoleIdAndSiteId(String roleId,String siteId);
    
    /**
     * 根据修改的角色权限修改用户 
     * @param userId 用户ID
     * @param siteId 网站ID
     * @param roleId 角色ID
     */
	void modifyUser(String userId,String siteId,String roleId);
	
	String findSiteIdByRoleId(String roleId);
}
