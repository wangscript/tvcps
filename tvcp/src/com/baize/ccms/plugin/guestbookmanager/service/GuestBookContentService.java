/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.plugin.guestbookmanager.service;

import java.util.List;

import com.baize.ccms.plugin.guestbookmanager.domain.GuestBookCategory;
import com.baize.ccms.plugin.guestbookmanager.domain.GuestBookContent;
import com.baize.ccms.plugin.guestbookmanager.web.form.GuestBookForm;
import com.baize.common.core.dao.Pagination;

/**
 * <p>标题: —— 留言接口</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CCMS</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司</p>
 * <p>网址：http://www.baizeweb.com
 * @author 曹名科
 * @version 1.0
 * @since 2009-11-7 下午02:19:34
 * @history（历次修订内容、修订人、修订时间等） 
 */
public interface GuestBookContentService {
	/**
	 * 添加留言
	 * @param content 留言对象
	 */
	String addGuestBookContent(GuestBookForm content);
	/**
	 * 获取类别列表
	 * @return
	 */
	List<GuestBookCategory> getGuestCategoryList(String siteId);
	/**
	 * 获取所有留言数量
	 * @param siteId
	 * @return
	 */
	String getAllCount(String siteId);
	/**
	 * 前台ajax留言分页(查询所有)
	 * @param pa
	 * @param siteId
	 * @return
	 */
	Pagination getpaginationContent(Pagination pa,String siteId);
	/**
	 * 获取替换样式后，全部留言内容
	 * @param siteId 网站ID
	 * @param pa
	 * @return
	 */
	String getContent(String siteId,Pagination pa);
	/**
	 * 获取替换样式后，根据条件搜索到得留言内容
	 * @param siteId
	 * @param pa
	 * @param categoryName 搜索类别
	 * @param keyword 关键字
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	String getContentByCondition(String siteId, Pagination pa,
			String categoryName, String keyword, String startTime,
			String endTime);
	/**
	 * 获取替换样式后，根据类别搜索到得留言内容
	 * @param siteId
	 * @param pa
	 * @param categoryName 类别名称
	 * @return
	 */
	String getContentByCategory(String siteId, Pagination pa,
			String categoryName);
	/**
	 *  前台ajax留言分页(根据类别分页)
	 * @return
	 * @param pa
	 * @param siteId
	 * @param categoryName 类别名称
	 * @return
	 */
	Pagination findByCategory(Pagination pa, String siteId,
				String categoryName);
	/**
	 * 前台ajax留言分页(根据搜索条件分页)
	 * @param pa 分页对象
	 * @param siteId 网站ID
	 * @param categoryName 搜索对象
	 * @param keyword 关键字
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	Pagination getpaginationContentBySelect(Pagination pa,
			String siteId, String categoryName, String keyword,
			String startTime, String endTime);
	

}
