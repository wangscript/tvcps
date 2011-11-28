/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.biz.unitmanager.service;

import java.util.Map;

import com.baize.ccms.biz.unitmanager.web.form.StaticUnitForm;
import com.baize.common.core.service.BaseService;
import com.baize.common.core.web.event.RequestEvent;
import com.baize.common.core.web.event.ResponseEvent;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 单元管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-6-1 上午12:28:52
 * @history（历次修订内容、修订人、修订时间等）
 */
public class StaticUnitBiz extends BaseService {
	
	/** 注入静态单元服务类 */
	private StaticUnitService staticUnitService;

	@Override
	public void performTask(RequestEvent requestEvent, ResponseEvent responseEvent)
	throws Exception {
		String dealMethod = requestEvent.getDealMethod();
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		String unitId = (String) requestParam.get("unitId");
		String categoryId = (String) requestParam.get("categoryId");
		String siteId = requestEvent.getSiteid();
		String sessionID = requestEvent.getSessionID();
		String staticContent = "";
			
		// 保存配置
		if (dealMethod.equals("saveConfig")) {
			log.info("保存静态单元配置");
			StaticUnitForm form = (StaticUnitForm) requestParam.get("form");
			staticContent = form.getStaticContent();
			staticUnitService.saveConfig(unitId, categoryId, staticContent, siteId, sessionID);
			
		// 查找配置
		} else if (dealMethod.equals("findConfig")) {
			log.info("查找静态单元配置");
			staticContent = staticUnitService.findConfig(unitId, categoryId);
			
		// 站内保存	
		} else if(dealMethod.equals("saveSiteConfig")) {
			log.info("站内保存静态单元信息");
			StaticUnitForm form = (StaticUnitForm) requestParam.get("form");
			staticContent = form.getStaticContent();
			staticUnitService.saveSiteConfig(form, unitId, siteId, sessionID);
		}
		responseParam.put("staticContent", staticContent);
	}

	@Override
	public ResponseEvent validateData(RequestEvent requestEvent) throws Exception {
		return null;
	}

	/**
	 * @param staticUnitService the staticUnitService to set
	 */
	public void setStaticUnitService(StaticUnitService staticUnitService) {
		this.staticUnitService = staticUnitService;
	}

}
