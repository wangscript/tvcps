/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.configmanager.service;

import com.baize.ccms.biz.configmanager.web.form.SystemLogForm;
import com.baize.common.core.dao.Pagination;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CCMS</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 郑荣华
 * @version 1.0
 * @since 2009-9-5 上午10:09:54
 * @history（历次修订内容、修订人、修订时间等） 
 */
public interface SystemLogService {
	
	/**
	 * 按照网站id查找日志文件
	 * @param siteId
	 * @param pagination
	 * @return
	 */
	Pagination findLogBySiteIdPage(String siteId, Pagination pagination);
	
	/**
	 * 删除日志文件
	 * @param ids
	 */
	void deleteLogsByIds(String ids);
	
	/**
	 * 导出日志到excel
	 * @param form
	 * @param siteId
	 * @param excelPath
	 */
	void exportLogsToExcel(SystemLogForm form, String siteId, String excelPath);
	
	/**
	 * 按照网站id删除所有的日志
	 * @param siteId
	 */
	void deleteAllLogsBySiteId(String siteId);
}
