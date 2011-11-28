  /**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.biz.usermanager.service.impl;

import java.util.List;

import com.baize.ccms.biz.usermanager.dao.AuthorityDao;
import com.baize.ccms.biz.usermanager.domain.Authority;
import com.baize.ccms.biz.usermanager.service.AuthorityService;

/**
 * <p>标题: —— 权限业务类</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 用户管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-2-25 下午02:43:34
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class AuthorityServiceImpl implements AuthorityService {

	/** 注入权限DAO */
	private AuthorityDao authorityDao; 
	
	/**
	 * 保存权限数据
	 * @param authority 权限对象	 * 
	 */
	public void addAuthority(Authority authority) {
		// TODO 自动生成方法存根
		authorityDao.save(authority);
	}
	
	/**
	 * 根据主键删除数据
	 * @param id 主键ID
	 */
	public void deleteAuthority(String id) {
		// TODO 自动生成方法存根
		authorityDao.deleteByKey(id);
	}
	
	/**
	 * 查询所有数据
	 * @return List
	 */
	public List<Authority> findAll() {
		// TODO 自动生成方法存根
		return null;
	}

	/**
	 * 修改数据
	 * @param authority 权限对象
	 */	
	public void modifyAuthority(Authority authority) {
		// TODO 自动生成方法存根
		
	}
	
	/**
	 * 根据操作ID和资源ID查询权限 
	 * @param resourceId 资源ID
	 * @param operationId 操作ID
	 * @return List 
	 */
	public List<Authority> findAuthorityByResourceidAndOperationid(String resourceId,String operationId){
		String[] str = {"resourceId","operationId"};
		Object[] obj = {resourceId,operationId};
		return authorityDao.findByNamedQuery("findAuthorityByResourceidAndOperationid", str, obj);
	}


	/**
	 * 根据资源ID查询权限 
	 * @param resourceId 资源ID
	 * @return List 
	 */
	public List<Authority> findAuthorityByResourceid(String resourceId){
		return authorityDao.findByNamedQuery("findAuthorityByResourceid", "resourceId", resourceId);
	}
	
	/**
	 * @param authorityDao 要设置的 authorityDao
	 */
	public void setAuthorityDao(AuthorityDao authorityDao) {
		this.authorityDao = authorityDao;
	}

}
