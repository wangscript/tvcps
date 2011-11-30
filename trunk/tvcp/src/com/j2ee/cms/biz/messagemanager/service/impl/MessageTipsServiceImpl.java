/**
 * project：通用内容管理系统
 * Company:  
*/

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 通用平台</p>
 * <p>版权: Copyright (c) 2009 
 * @author 杨信
 * @version 1.0
 * @since 2009-6-30 下午02:38:03 
 * @history（历次修订内容、修订人、修订时间等） 
*/

package com.j2ee.cms.biz.messagemanager.service.impl;

import org.springframework.jdbc.core.JdbcTemplate;

import com.j2ee.cms.biz.messagemanager.service.MessageTipsService;
import com.j2ee.cms.common.core.dao.Pagination;

/**
 * @author Administrator
 *
 */
public class MessageTipsServiceImpl implements MessageTipsService {

	private JdbcTemplate jdbcTemplate;
	/**
	 * 查询未读信息条数
	 * @param pagination 分页对象
	 * @param id 当前用户id
	 * @return 信息条数
	 */
	public int findMessages(Pagination pagination, String id) {
		String sql = "SELECT count(*) as count FROM msg_site_messages WHERE receiver_id='" + id 
				   + "' AND flag=1 AND readed=0 ";
	//	System.out.println("---------" + sql);
		int num = jdbcTemplate.queryForInt(sql);
		return num;
	}
	
	/**
	 * 查询未读信息条数
	 * @param id 当前用户id
	 * @return 信息条数
	 */
	public int findMessages(String id) {
		String sql = "SELECT count(*) as count FROM msg_site_messages WHERE receiver_id='" + id 
				   + "' AND flag=1 AND readed=0 ";
	//	System.out.println("---------" + sql);
		int num = jdbcTemplate.queryForInt(sql);
		return num;
	}
	
	/**
	 * @param jdbcTemplate the jdbcTemplate to set
	 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
