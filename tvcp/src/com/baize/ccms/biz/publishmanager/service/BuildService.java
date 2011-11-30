/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.publishmanager.service;

import com.j2ee.cms.common.core.dao.Pagination;

/**
 * 
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 生成管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-11-11 下午02:23:19
 * @history（历次修订内容、修订人、修订时间等）
 */

public interface BuildService {
	
	 
	/**
	 * 查找文章生成列表页
	 * @param pagination
	 * @return
	 */
	Pagination findArticleBuildListPagination(Pagination pagination, String siteId); 
	
	/**
	 * 根据多个主键删除生成列表数据
	 * @param ids 主键
	 */
	void deleteArticleBuildListByIds(String ids);
	
	/**
	 * 删除所有生成列表的数据
	 */
	void deleteAll();

}
