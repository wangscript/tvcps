  /**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.usermanager.service;

import java.util.List;

import com.j2ee.cms.biz.usermanager.domain.Authority;

/**
 * <p>标题: —— 权限管理业务逻辑调用类最高接口</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 用户管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-2-25 下午02:41:41
 * @history（历次修订内容、修订人、修订时间等） 
 */
public interface AuthorityService {
	/**
	 *添加数据 
	 *@param authority 权限对象
	 */
	void addAuthority(Authority authority);
	
	/**
	 * 根据主键删除数据
	 * @param id 主键ID
	 */
	void deleteAuthority(String id);
	
	/**
	 * 修改数据
	 * @param authority 权限对象
	 */
	void modifyAuthority(Authority authority);
	
	/**
	 * 查询所有数据
	 * @return List
	 */
	List<Authority> findAll();
	
	/**
	 * 根据操作ID和资源ID查询权限 
	 * @param resourceId 资源ID
	 * @param operationId 操作ID
	 * @return List 
	 */
	List<Authority> findAuthorityByResourceidAndOperationid(String resourceId,String operationId);
	
	/**
	 * 根据资源ID查询权限 
	 * @param resourceId 资源ID
	 * @return List 
	 */
	List<Authority> findAuthorityByResourceid(String resourceId);
}
