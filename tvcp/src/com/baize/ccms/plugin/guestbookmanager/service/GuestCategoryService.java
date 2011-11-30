/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.plugin.guestbookmanager.service;

import java.util.List;

import com.j2ee.cms.plugin.guestbookmanager.domain.GuestBookCategory;
import com.j2ee.cms.plugin.guestbookmanager.domain.GuestBookContent;
import com.j2ee.cms.plugin.guestbookmanager.domain.GuestBookRevert;
import com.j2ee.cms.plugin.guestbookmanager.web.form.GuestBookForm;
import com.j2ee.cms.common.core.dao.Pagination;

/**
 * <p>标题: —— 留言类别管理服务类</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CPS</p>
 * <p>版权: Copyright (c) 2009  </p>
 * <p>网址：http://www.j2ee.cmsweb.com
 * @author 曹名科
 * @version 1.0
 * @since 2009-11-3 下午02:08:05
 * @history（历次修订内容、修订人、修订时间等）
 */

public interface GuestCategoryService {
	/**
	 * 留言分页
	 * @param p
	 * @return
	 */
	Pagination getPagination(Pagination p, String siteId,boolean isManager,String userId);
	/**
	 * 根据审核状态分页
	 * @param p
	 * @param status
	 * @return
	 */
	Pagination getPaginationByAudit(Pagination p,String status,String siteId,boolean isManager,String userId);
	/**
	 * 根据回复状态分页
	 * @param p
	 * @param status
	 * @return
	 */
	Pagination getPaginationByReplay(Pagination p,String status,String siteId,boolean isManager,String userId);
	/**
	 * 删除留言
	 * @param ids
	 */
	void deleteGuestContent(String ids);
	/**
	 * 添加类别
	 * @param form
	 * @param siteId
	 */
	String addCategory(GuestBookForm form, String siteId);
	/**
	 * 修改类别
	 * @param ids
	 */
	String modifyCategory(String ids,GuestBookForm form);
	/**
	 * 根据ID获取类别
	 * @param id
	 * @return
	 */
	GuestBookCategory getCategoryById(String id);
	/**
	 * 类别分页
	 * @param p
	 * @return
	 */
	Pagination getCategoryPagination(Pagination p,String siteId);
	/**
	 * 获取留言详细
	 * @param id
	 * @return
	 */
	void getGuestBookContentById(GuestBookForm form);
	/**
	 * 审核留言
	 * @param id
	 */
	String modifyauditGuestBookContent(String id);
	/**
	 * 审核并回复,(将状态改变为已审核，已回复,添加留言)
	 * @param form
	 * @param id
	 * @param userId 回复人
	 */
	String modifyauditAndReplayGuestBookContent(GuestBookForm form,String userId);
	/**
	 * 不处理
	 * @param id
	 */
	String modifynoHandleGuestBookContent(String id);
	/**
	 * 保存留言
	 * @param form
	 * @param id
	 */
	String saveGuestBookContent(GuestBookForm form,String userId);
	/**
	 * 留言置未审
	 * @param form
	 * @param id
	 */
	String modifybackAuditGuestBookContent(String id);
	/**
	 * 获取用列表
	 * @param pa1
	 * @return
	 */
	Pagination getAllUser(Pagination pa1);
	/**
	 * 将留言分发给用户
	 * @param contentId 要分发的留言ID
	 * @param userId 分发给的用户ID
	 * @return
	 */
	String modifyContent(String contentId,String userId);
	/**
	 * 根据siteId获取所有类别
	 * @param siteId
	 * @return
	 */
	String getAllCategoryName(String siteId);
	/**
	 * 删除类别
	 * @param ids
	 * @return
	 */
	String deleteCategoryById(String ids);
}
