/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.usermanager.service;

import java.util.List;

import com.j2ee.cms.biz.usermanager.domain.Resource;
import com.j2ee.cms.common.core.dao.Pagination;

/**
 * 
 * <p>标题: —— 资源业务逻辑处理类最高接口</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 用户管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-3-4 上午10:02:45
 * @history（历次修订内容、修订人、修订时间等）
 */

public interface ResourceService {
	/**
	 *添加数据 
	 */
	public void addResource(Resource Resource);
	
	/**
	 * 删除数据
	 */
	public void deleteResource(String id);
	
	/**
	 * 修改数据
	 */
	public void modifyResource(Resource Resource);
	
	/**
	 * 查询数据
	 */
	public Resource findResourceByKey(String id);
	
	/**
	 * 查询所有
	 * @return
	 */
	public List<Resource> findAll();
	
	/**
	 * 查找资源数据
	 * @param pagination 分页对象
	 * @return
	 */
	public Pagination findResourceData(Pagination pagination);
	
	/**
	 * 根据资源类型，资源标识查询所有数据
	 * @return
	 */
	public List<Resource> findResourceByTypeAndIdentifier(Resource resource);
	
	/**
	 * 根据资源类型，资源标识和网站ID查询所有数据
	 * @return List
	 */
	public List<Resource> findResourceByTypeAndSiteId(String siteId);
	
}
