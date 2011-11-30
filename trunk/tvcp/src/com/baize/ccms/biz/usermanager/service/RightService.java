/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.usermanager.service;

import java.util.List;

import com.j2ee.cms.biz.usermanager.domain.Right;

/**
 * <p>标题: —— 权力业务类最高接口</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 用户管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-3-24 下午03:13:49
 * @history（历次修订内容、修订人、修订时间等） 
 */

public interface RightService {
	/**
	 *添加数据 
	 *@param right 权力对象
	 */
	void addRight(Right right);
	
	/**
	 * 根据主键删除数据
	 * @param id 主键
	 */
	void deleteRightByKey(String id);
	
	/**
	 * 根据多个主键删除数据
	 * @param ids 主键字符串
	 */
	void deleteRightByIds(String ids);
	
	/**
	 * 修改数据
	 * @param right 权力对象
	 */
	void modifyRight(Right right);
	
	/**
	 * 根据主键查询数据
	 * @param id 主键
	 * @return Right
	 */
	Right findRightByKey(String id);
	
	/**
	 * 查询所有权力数据
	 * @return List 权力集合
	 */
	List<Right> findAll();	
	
	/**
	 * 根据栏目类型，栏目ID，用户ID，查询权力表 
	 * @param itemType 栏目类型
	 * @param itemId 栏目ID
	 * @param userId 用户ID
	 * @param siteId 网站ID
	 * @return List 
	 */
	List<Right> findRightByUserIdAndItemIdAndItemTypeAndSiteId(String itemType,
			String itemId,String userId,String siteId);
	
   /**
	 * 根据资源id查找权利表
	 * @param resourceId 资源id
	 * @return 返回权力对象
	 */
	List<Right> findRightByResourceId(String resourceId);
	
   /**
	 * 根据用户id查找权利表
	 * @param userId 用户id
	 * @return 返回权力对象
	 */
	List<Right> findRightByUserId(String userId);
	
}
