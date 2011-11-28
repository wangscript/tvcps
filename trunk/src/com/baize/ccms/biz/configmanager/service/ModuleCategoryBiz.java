/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.configmanager.service;

import java.util.List;
import java.util.Map;

import com.baize.ccms.biz.configmanager.domain.ModuleCategory;
import com.baize.common.core.service.BaseService;
import com.baize.common.core.web.event.RequestEvent;
import com.baize.common.core.web.event.ResponseEvent;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CCMS</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 郑荣华
 * @version 1.0
 * @since 2009-9-5 上午10:07:08
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class ModuleCategoryBiz extends BaseService {

	/** 注入模块类别业务对象 */
	private ModuleCategoryService moduleCategoryService;
	
	@Override
	public void performTask(RequestEvent requestEvent, ResponseEvent responseEvent)	throws Exception {
		String dealMethod = requestEvent.getDealMethod();
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		
		// 查询模块类别
		if(dealMethod.equals("")) {
			// 要记录日志的模块
			List<ModuleCategory> selectedList =  moduleCategoryService.findModuleCategoryByStatus(true);
			// 不要记录日志的模块
			List<ModuleCategory> notSelectedList =  moduleCategoryService.findModuleCategoryByStatus(false);
			responseParam.put("selectedList", selectedList);
			responseParam.put("notSelectedList", notSelectedList);
			
		// 取消要记录日志的模块类别	
		} else if(dealMethod.equals("modifyModule")) {
			String selectIds = (String) requestParam.get("selectIds");
			String notSelectIds = (String) requestParam.get("notSelectIds");
			moduleCategoryService.modifyModuleCategoryStatus(selectIds, notSelectIds);
		}
	}

	@Override
	public ResponseEvent validateData(RequestEvent requestEvent)
			throws Exception {
		return null;
	}

	/**
	 * @param moduleCategoryService the moduleCategoryService to set
	 */
	public void setModuleCategoryService(ModuleCategoryService moduleCategoryService) {
		this.moduleCategoryService = moduleCategoryService;
	}

}
