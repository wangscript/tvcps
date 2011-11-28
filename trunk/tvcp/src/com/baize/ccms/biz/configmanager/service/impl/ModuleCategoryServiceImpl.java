/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.configmanager.service.impl;

import java.util.List;

import com.baize.ccms.biz.configmanager.dao.ModuleCategoryDao;
import com.baize.ccms.biz.configmanager.domain.ModuleCategory;
import com.baize.ccms.biz.configmanager.service.ModuleCategoryService;
import com.baize.common.core.util.SqlUtil;
import com.baize.common.core.util.StringUtil;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CCMS</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 郑荣华
 * @version 1.0
 * @since 2009-9-5 上午10:12:06
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class ModuleCategoryServiceImpl implements ModuleCategoryService {
	
	/** 注入模块类别dao */
	private ModuleCategoryDao moduleCategoryDao;

	
	/**
	 * 按照状态查找模块类别
	 * @param flag   显示是否要记录日志
	 * @return
	 */
	public List<ModuleCategory> findModuleCategoryByStatus(boolean flag) {
		if(flag) {
			return moduleCategoryDao.findByNamedQuery("findModuleCategoryByStatus", "status", true);
		} else {
			return moduleCategoryDao.findByNamedQuery("findModuleCategoryByStatus", "status", false);
		}
	}
	
	/**
	 * 修改模块类别状态
	 * @param selectIds     修改为不记录    
	 * @param notSelectIds  修改为记录
	 */
	public void modifyModuleCategoryStatus(String selectIds, String notSelectIds) {
		String[] params = {"status", "ids"};
		if(!StringUtil.isEmpty(selectIds)) {
			selectIds = SqlUtil.toSqlString(selectIds);
			String[] sleValues = {"0", selectIds};
			moduleCategoryDao.updateByDefine("updateModuleStatus", params, sleValues);
		}
		if(!StringUtil.isEmpty(notSelectIds)) {
			notSelectIds = SqlUtil.toSqlString(notSelectIds);
			String[] notSelValues = {"1", notSelectIds};
			moduleCategoryDao.updateByDefine("updateModuleStatus", params, notSelValues);
		}
	}
	
	/**
	 * @param moduleCategoryDao the moduleCategoryDao to set
	 */
	public void setModuleCategoryDao(ModuleCategoryDao moduleCategoryDao) {
		this.moduleCategoryDao = moduleCategoryDao;
	}
}
