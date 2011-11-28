package com.house.biz.user.service;

import java.util.List;

import com.house.biz.entity.AdminUserEntity;
import com.house.biz.entity.VillageEntity;
import com.house.biz.user.dao.AdminUserDao;
import com.house.core.service.GenericServiceImpl;
import com.house.core.util.IDFactory;
/**
 * 
 * <p>标题: —— 管理员用户表业务操作实现类</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 通用平台</p>
 * <p>版权: Copyright (c) 2011 娄伟峰
 * @author 娄伟峰
 * @version 1.0
 * @since 2011-11-8 下午05:20:27
 * @history（历次修订内容、修订人、修订时间等）
 */

public class AdminUserServiceImpl extends GenericServiceImpl<AdminUserEntity,String> implements AdminUserService{
	
	private AdminUserDao  adminUserDao;

	/**
	 * 设置子类dao
	 */
	public void setDao() {
		this.genericDao = adminUserDao;
	}

	@Override
	public AdminUserEntity queryAdminUserByParm(String loginName,
			String passWord) {
		// TODO Auto-generated method stub
		AdminUserEntity adminUserEntity = new AdminUserEntity();
		adminUserEntity.setLoginName(loginName);
		adminUserEntity.setPassword(passWord);
		List<AdminUserEntity> list = adminUserDao.queryObjectsByObject(adminUserEntity);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public String saveUser(AdminUserEntity adminUserEntity) throws Exception {
		if(adminUserEntity.getAdminuser_id() != null && !adminUserEntity.getAdminuser_id().equals("") ){
			return updateObject(adminUserEntity);
		}else{
			adminUserEntity.setAdminuser_id(IDFactory.getId());     
			return saveObject(adminUserEntity);
		}
	}
	

	
	
	
	
	/**
	 * @param adminUserDao the adminUserDao to set
	 */
	public void setAdminUserDao(AdminUserDao adminUserDao) {
		this.adminUserDao = adminUserDao;
	}

}
