package com.house.biz.user.action;

import com.house.biz.entity.AdminUserEntity;
import com.house.biz.user.service.AdminUserService;
import com.house.core.action.GenericAction;
import com.house.core.common.Constant;

/**
 * 
 * <p>标题: —— 管理员用户管理</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 通用平台</p>
 * <p>版权: Copyright (c) 2011 娄伟峰
 * @author 娄伟峰
 * @version 1.0
 * @since 2011-11-11 下午02:22:30
 * @history（历次修订内容、修订人、修订时间等）
 */
@SuppressWarnings("serial")
public class AdminUserAction extends GenericAction{
	
	private AdminUserService adminUserService;
	private AdminUserEntity adminUserEntity = new AdminUserEntity();
	
	
	/**
	 * 分页查询操作员用户显示列表
	 * @return
	 * @throws Exception
	 */
	public String queryAdminUser() throws Exception{
		adminUserEntity.setAdminuser_id((String)(this.getHttpSession().getAttribute(Constant.USER_ID)));
		pagination = adminUserService.queryObjectsByPaginationAndObject(adminUserEntity, pagination);	
		return SUCCESS;
	}

	/**
	 * 保存操作员用户信息
	 * @return
	 */
	public String saveUser()throws Exception{
		adminUserEntity.setAdminuser_id(getRequest().getParameter("id"));
		addActionMessage(adminUserService.saveUser(adminUserEntity));
		return SUCCESS;
	}
	
	/**
	 * 根据页面选择的ID删除操作员用户记录
	 * @return
	 * @throws Exception
	 */
	public String deleteAdminUserByIds() throws Exception{
		addActionMessage(adminUserService.deleteObjectByIds(strChecked));
		return SUCCESS;
	}
	
	/**
	 * 根据主键ID查询操作员用户
	 * @return
	 */
	public String findAdminUserById()throws Exception{
		adminUserEntity = adminUserService.findObjectById(strChecked);
		return SUCCESS;
	}
	
	public AdminUserEntity getAdminUserEntity() {
		return adminUserEntity;
	}

	public void setAdminUserEntity(AdminUserEntity adminUserEntity) {
		this.adminUserEntity = adminUserEntity;
	}

	public void setAdminUserService(AdminUserService adminUserService) {
		this.adminUserService = adminUserService;
	}

}
