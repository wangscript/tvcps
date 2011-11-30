/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.plugin.guestbookmanager.service;

import com.j2ee.cms.plugin.guestbookmanager.domain.GuestBookSensitiveWord;
import com.j2ee.cms.common.core.dao.Pagination;

/**
 * <p>标题: —— 留言板业务处理类最高接口</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 插件管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 曹名科
 * @version 1.0
 * @since 2009-5-18 下午04:20:28
 * @history（历次修订内容、修订人、修订时间等） 
 */

public interface GuestBookService {
	/**
	 * 添加敏感词
	 * @param sensitiveWord 敏感词
	 * @param siteId
	 * @return
	 */
	String addSensitiveWord(String sensitiveWord,String siteId);
	/**
	 * 敏感词分页
	 * @param pa 分页对象
	 * @param siteId
	 * @return
	 */
	Pagination getWordPagination(Pagination pa,String siteId);
	/**
	 * 修改敏感词
	 * @param ids ID
	 * @param sensitiveWord 敏感词
	 * @param siteId
	 * @return
	 */
	String modifySensitiveWord(String ids,String sensitiveWord,String siteId);
	/**
	 * 根据ID删除敏感词
	 * @param ids
	 */
	void deleteSensitiveWord(String ids);
	/**
	 * 获取敏感词详细
	 * @param id
	 * @return
	 */
	 GuestBookSensitiveWord getSensitiveWordById(String id);
	 /**
	  * 获取所有敏感词
	  * @param siteId 网站ID
	  * @return
	  */
	 String getAllSensitiveWordBySiteId(String siteId);
	 
	 /**
	  * 发布留言本目录 
	  * @param siteId
	  */
	 void publishGuestBookDir(String siteId);
}
