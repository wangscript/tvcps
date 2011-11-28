/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.biz.usermanager.service.impl;

import java.util.List;

import com.baize.ccms.biz.usermanager.dao.RightDao;
import com.baize.ccms.biz.usermanager.domain.Authority;
import com.baize.ccms.biz.usermanager.domain.Right;
import com.baize.ccms.biz.usermanager.service.RightService;

/**
 * <p>标题: —— 权力业务实现类</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 用户管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-3-24 下午03:14:07
 * @history（历次修订内容、修订人、修订时间等） 
 */

public class RightServiceImpl implements RightService{
	/** 注入权力DAO */
	private RightDao rightDao;
	
	/**
	 *添加数据 
	 *@param right 权力对象
	 */
	public void addRight(Right right){
		rightDao.save(right);
	}
	
	/**
	 * 根据主键删除数据
	 * @param id 主键
	 */
	public void deleteRightByKey(String id){
		rightDao.deleteByKey(id);
	}
	
	/**
	 * 根据多个主键删除数据
	 * @param ids 主键字符串
	 */
	public void deleteRightByIds(String ids){
		rightDao.deleteByIds(ids);
	}
	
	/**
	 * 修改数据
	 * @param right 权力对象
	 */
	public void modifyRight(Right right){
		rightDao.update(right);
	}
	
	/**
	 * 根据主键查询数据
	 * @param id 主键
	 * @return Right
	 */
	public Right findRightByKey(String id){
		return rightDao.getAndClear(id);
	}
	
	/**
	 * 查询所有权力数据
	 * @return List 权力集合
	 */
	public List<Right> findAll(){
		return rightDao.findAll();
	}

	/**
	 * 根据栏目类型，栏目ID，用户ID，查询权力表 
	 * @param itemType 栏目类型
	 * @param itemId 栏目ID
	 * @param userId 用户ID
	 * @param siteId 网站ID
	 * @return List 
	 */
	public List<Right> findRightByUserIdAndItemIdAndItemTypeAndSiteId(String itemType,
			String itemId,String userId,String siteId){
		String[] str = {"itemType","itemId","userId","siteId"};
		Object[] obj = {itemType,itemId,userId,siteId};
		return rightDao.findByNamedQuery("findRightByUserIdAndItemIdAndItemTypeAndSiteId", str, obj);
	}
	
	
	/** 根据资源id查找权利表
	 * @param resourceId 资源id
	 * @return List 返回权力对象
	 */
	public List<Right> findRightByResourceId(String resourceId) {
		return rightDao.findByNamedQuery("findRightByResourceId","resourceId",resourceId);
	}

	
   /**
	 * 根据用户id查找权利表
	 * @param userId 用户id
	 * @return 返回权力对象
	 */
	public List<Right> findRightByUserId(String userId){
		return rightDao.findByNamedQuery("findRightByUserId","userId",userId);
	}

	/**
	 * @param rightDao the rightDao to set
	 */
	public void setRightDao(RightDao rightDao) {
		this.rightDao = rightDao;
	}

	
}
