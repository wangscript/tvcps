/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.plugin.guestbookmanager.service;

import java.util.Map;

import com.j2ee.cms.plugin.guestbookmanager.web.form.GuestBookForm;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CPS</p>
 * <p>版权: Copyright (c) 2009  </p>
 * <p>网址：http://www.j2ee.cmsweb.com
 * @author 曹名科
 * @version 1.0
 * @since 2009-11-2 下午02:12:22
 * @history（历次修订内容、修订人、修订时间等） 
 */
public interface GuestBookAttributeService {
	/**
	 * 获取属性设置
	 * @param form
	 * @param siteId
	 */
	void getAttributeSetForm(GuestBookForm form, String siteId);
	/**
	 * 写文件
	 * @param siteId 网站ID
	 * @param form form
	 * @param sessionId 用户ID
	 * @return
	 */
	String writerAttribute(String siteId, GuestBookForm form,
			String sessionId);
	/**
	 * 获取留言样式
	 * @param siteId
	 * @return
	 */
	String getGuestBookStyle(String siteId);
	/**
	 * 保存设置的样式
	 * @param styleContent
	 * @param siteId
	 * @return
	 */
	String setGuestBookStyle(String styleContent,String siteId);
}
