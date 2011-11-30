/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.configmanager.dao.impl;

import java.util.Date;
import java.util.List;

import com.j2ee.cms.biz.configmanager.dao.SystemLogDao;
import com.j2ee.cms.biz.configmanager.dao.ModuleCategoryDao;
import com.j2ee.cms.biz.configmanager.domain.SystemLog;
import com.j2ee.cms.biz.configmanager.domain.ModuleCategory;
import com.j2ee.cms.biz.sitemanager.domain.Site;
import com.j2ee.cms.biz.usermanager.domain.User;
import com.j2ee.cms.sys.GlobalConfig;
import com.j2ee.cms.common.core.dao.GenericDaoImpl;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CPS</p>
 * <p>版权: Copyright (c) 2009  
 * @author 郑荣华
 * @version 1.0
 * @since 2009-9-5 上午09:51:14
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class SystemLogDaoImpl extends GenericDaoImpl<SystemLog, String> implements SystemLogDao {

	/** 注入模快类别dao */
	private ModuleCategoryDao moduleCategoryDao;
	
	/**
	 * 添加日志数据
	 * @param categoryName 类别名称
	 * @param siteId       网站id
	 * @param userId       操作者id
	 * @param param        操作的数据
	 */
	public void addLogData(String categoryName, String siteId, String userId, String param) {
		List<ModuleCategory> list = moduleCategoryDao.findByNamedQuery("findModuleCategoryByName", "name", categoryName);
		if((list != null && list.size() > 0)) {
			ModuleCategory moduleCategory = list.get(0);
			// 被选中则记录日志 
			if(moduleCategory.isStatus()) {
				SystemLog logManager = new SystemLog();
				Site site = new Site();
				site.setId(siteId);
				logManager.setSite(site);
				User operator = new User();
				operator.setId(userId);
				logManager.setOperator(operator);
				logManager.setModuleCategory(moduleCategory);
				logManager.setOperationTime(new Date());
				String ip = String.valueOf(GlobalConfig.ip.get(userId));
				logManager.setIp(ip);
				String operationContent = categoryName.split("->")[1] + "：" + param;
				logManager.setOperationContent(operationContent);
				this.saveAndClear(logManager);
			}
		}
	}

	/**
	 * @param moduleCategoryDao the moduleCategoryDao to set
	 */
	public void setModuleCategoryDao(ModuleCategoryDao moduleCategoryDao) {
		this.moduleCategoryDao = moduleCategoryDao;
	}

}
