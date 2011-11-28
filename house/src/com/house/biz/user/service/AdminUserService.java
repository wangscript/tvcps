package com.house.biz.user.service;

import com.house.biz.entity.AdminUserEntity;
import com.house.biz.entity.PersonStatusEntity;
import com.house.core.service.GenericService;
/**
 * 
 * <p>标题: —— 管理员用户操作业务类</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 通用平台</p>
 * <p>版权: Copyright (c) 2011 娄伟峰
 * @author 娄伟峰
 * @version 1.0
 * @since 2011-11-8 下午03:40:06
 * @history（历次修订内容、修订人、修订时间等）
 */
public interface AdminUserService extends GenericService<AdminUserEntity,String>{
	/**
	 * 根据用户名密码查询用户
	 * @param loginName
	 * @param passWord
	 * @return AdminUserEntity 管理员用户实体对象
	 */
	public AdminUserEntity queryAdminUserByParm(String loginName, String passWord);
	
	/**
	 * 保存用户信息
	 * @param personStatusEntity
	 */
	public String saveUser(AdminUserEntity adminUserEntity)throws Exception;
}
