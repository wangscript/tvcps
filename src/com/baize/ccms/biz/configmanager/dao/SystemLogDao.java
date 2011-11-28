/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.configmanager.dao;

import com.baize.ccms.biz.configmanager.domain.SystemLog;
import com.baize.common.core.dao.GenericDao;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CCMS</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 郑荣华
 * @version 1.0
 * @since 2009-9-5 上午09:50:25
 * @history（历次修订内容、修订人、修订时间等） 
 */
public interface SystemLogDao extends GenericDao<SystemLog, String> {
	
	/**
	 * 添加日志数据
	 * @param categoryName 类别名称
	 * @param siteId       网站id
	 * @param userId       操作者id
	 * @param param        操作的数据
	 */
	void addLogData(String categoryName, String siteId, String userId, String param); 
}
