/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.configmanager.service;

import java.util.List;

import com.j2ee.cms.biz.configmanager.domain.ModuleCategory;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CPS</p>
 * <p>版权: Copyright (c) 2009  
 * @author 郑荣华
 * @version 1.0
 * @since 2009-9-5 上午10:09:29
 * @history（历次修订内容、修订人、修订时间等） 
 */
public interface ModuleCategoryService {
	
	/**
	 * 按照状态查找模块类别
	 * @param flag   显示是否要记录日志
	 * @return
	 */
	List<ModuleCategory> findModuleCategoryByStatus(boolean flag);
	
	/**
	 * 修改模块类别状态
	 * @param selectIds     修改为不记录    
	 * @param notSelectIds  修改为记录
	 */
	void modifyModuleCategoryStatus(String selectIds, String notSelectIds);
}
