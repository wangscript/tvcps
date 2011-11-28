/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.plugin.guestbookmanager.service;

import com.baize.common.core.dao.Pagination;

/**
 * <p>标题: —— 分发权限设置</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CCMS</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司</p>
 * <p>网址：http://www.baizeweb.com
 * @author 曹名科
 * @version 1.0
 * @since 2009-11-2 下午08:27:42
 * @history（历次修订内容、修订人、修订时间等） 
 */
public interface GuestBookAutorityService {
	/**
	 * 获取分发权限的用户列表
	 * @param pa1 分页对象
	 * @return
	 */
	Pagination getAutorityUser(Pagination pa1);
	/**
	 * 显示所有用户，供分发权限设置
	 * @param pa1
	 * @return
	 */
	Pagination getAllUser(Pagination pa1);
	/**
	 * 将选择的用户添加到分发留言权限数据库中
	 * @param userId
	 */
	void addAutoriyUser(String userId);
	/**
	 * 删除设置的分发留言权限
	 * @param ids
	 */
	void deleteAutoriyUser(String ids);
	/**
	 * 判断用户是否已经分发
	 * @param id
	 * @return
	 */
	 boolean isExistsAutoryUser(String userId);

}
