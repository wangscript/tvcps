/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.unitmanager.service;

import com.j2ee.cms.biz.unitmanager.web.form.StaticUnitForm;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 单元管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-6-1 上午12:29:02
 * @history（历次修订内容、修订人、修订时间等）
 */
public interface StaticUnitService {
	
	/**
	 * 保存配置
	 * @param unitId 单元ID
	 * @param categoryId 单元类别ID
	 * @param content 单元内容
	 * @param siteId
	 * @param sessionID
	 */
	void saveConfig(String unitId, String categoryId, String content, String siteId, String sessionID);
	
	/**
	 * 查找配置
	 * @param unitId
	 */
	String findConfig(String unitId, String categoryId);
	
	/**
	 * 站内保存
	 * @param form
	 * @param unitId
	 * @param siteId
	 * @param sessionID
	 */
	void saveSiteConfig(StaticUnitForm form, String unitId, String siteId, String sessionID);

}
