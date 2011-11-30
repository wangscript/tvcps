/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.articlemanager.service;

import com.j2ee.cms.biz.articlemanager.domain.ArticleFormat;
import com.j2ee.cms.common.core.dao.Pagination;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 文章管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 杨信
 * @version 1.0
 * @since 2009-3-12 下午03:26:36
 * @history（历次修订内容、修订人、修订时间等） 
 */
public interface ArticleFormatService {

	/**
	 * 添加格式
	 * @param format
	 * @param siteId
	 * @param sessionID
	 * @return
	 */
	String addFormat(ArticleFormat format, String siteId, String sessionID);
	
	/**
	 * 删除格式
	 * @param format
	 */
	void deleteFormat(ArticleFormat format);
	
	/**
	 * 修改格式 
	 * @param format
	 * @param siteId
	 * @param sessionID
	 * @return
	 */
	String modifyFormat(ArticleFormat format, String siteId, String sessionID);
	
	/**
	 * 批量删除格式
	 * @param ids
	 * @param siteId
	 * @param sessionID
	 * @return 影响的记录数
	 */
	String deleteFormatByIds(String ids, String siteId, String sessionID);
	
	/**
	 * 通过格式id查找格式
	 * @param id
	 * @return
	 */
	ArticleFormat findFormatById(String id);
	
	/**
	 * 查找格式分页对象
	 * @param pagination
	 * @return
	 */
	Pagination findFormatPagination(Pagination pagination);
	
	/**
	 * 导出格式
	 * 
	 * @param exportFormatIds
	 *            导出的格式ids
	 * @param path
	 *            导出格式的路径
	 * @return message
	 */
	String exportFormats(String exportFormatIds, String path);
	
	/**
	 * 导入格式
	 * 
	 * @param xmlpath
	 *            文件路径
	 * @param siteId
	 * @param creatorId
	 * @return
	 */
	String importFormatsXml(String xmlpath, String siteId, String creatorId);
}
