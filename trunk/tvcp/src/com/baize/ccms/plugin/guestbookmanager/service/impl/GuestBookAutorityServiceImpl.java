/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.plugin.guestbookmanager.service.impl;

import java.util.List;

import com.baize.ccms.biz.usermanager.dao.UserDao;
import com.baize.ccms.biz.usermanager.domain.User;
import com.baize.ccms.plugin.guestbookmanager.dao.GuestBookAutorityDao;
import com.baize.ccms.plugin.guestbookmanager.domain.GuestBookAutority;
import com.baize.ccms.plugin.guestbookmanager.service.GuestBookAutorityService;
import com.baize.common.core.dao.Pagination;
import com.baize.common.core.util.SqlUtil;

/**
 * <p>标题: —— 分发权限设置</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CCMS</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司</p>
 * <p>网址：http://www.baizeweb.com
 * @author 曹名科
 * @version 1.0
 * @since 2009-11-2 下午08:27:24
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class GuestBookAutorityServiceImpl implements GuestBookAutorityService{
	/**用户DAO*/
	private UserDao userDao;
	/**用户分发权限设置dao*/
	private GuestBookAutorityDao autorityDao;
	
	public Pagination getAutorityUser(Pagination pa1){
		Pagination pa=autorityDao.getPagination(pa1);
		return pa;
	}
	public void addAutoriyUser(String userIds){
		
		String id[]=userIds.split(",");
		for (int i = 0; i < id.length; i++) {
			if(!isExistsAutoryUser(id[i])){
				GuestBookAutority ga = new GuestBookAutority();
				User users = new User();
				users.setId(id[i]);
				ga.setUsers(users);
				autorityDao.save(ga);
			}
		}
		
	}
	public  boolean isExistsAutoryUser(String id){
		List list=autorityDao.findByNamedQuery("findAutoryExist","userId",id);
		if(list!=null&&list.size()>0){
			return true;
		}
		return false;
	}
	public void deleteAutoriyUser(String ids){
		autorityDao.deleteByIds(SqlUtil.toSqlString(ids));
	}
	public Pagination getAllUser(Pagination pa1) {
		Pagination pa=userDao.getPagination(pa1);
		return pa;
	}
	/**
	 * @param autorityDao the autorityDao to set
	 */
	public void setAutorityDao(GuestBookAutorityDao autorityDao) {
		this.autorityDao = autorityDao;
	}

	/**
	 * @param userDao the userDao to set
	 */
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}
