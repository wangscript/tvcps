/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.unitmanager.service;

import java.util.List;
import java.util.Map;

import com.j2ee.cms.biz.templatemanager.domain.TemplateUnitStyle;
import com.j2ee.cms.biz.unitmanager.web.form.LatestInfoForm;
import com.j2ee.cms.sys.GlobalConfig;
import com.j2ee.cms.common.core.service.BaseService;
import com.j2ee.cms.common.core.web.event.RequestEvent;
import com.j2ee.cms.common.core.web.event.ResponseEvent;

/**
 * <p>标题: —— 最新信息业务流转类</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CPS</p>
 * <p>版权: Copyright (c) 2009  
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-6-1 下午05:41:26
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class LatestInfoBiz extends BaseService {

	/** 注入标题链接业务对象 **/
	private LatestInfoService latestInfoService;
	@Override
	public void performTask(RequestEvent requestEvent, ResponseEvent responseEvent)
	throws Exception {
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		String dealMethod = requestEvent.getDealMethod();
		String siteId = requestEvent.getSiteid();
		String sessionID = requestEvent.getSessionID();
		// 查找配置
		if (dealMethod.equals("findConfig")) {
			log.info("查找最新信息配置");
			LatestInfoForm latestInfoForm = (LatestInfoForm)requestParam.get("form");
			//查询普通配置
			latestInfoService.findConfig(latestInfoForm,siteId);
			//查询模板单元样式
			List<TemplateUnitStyle> templateUnitStyleList = latestInfoService.findTemplateUnitStyleByCategoryIdAndSiteId(
					latestInfoForm.getUnit_categoryId(), siteId);
			latestInfoForm.setTemplateUnitStyleList(templateUnitStyleList);
			responseParam.put("latestInfoForm", latestInfoForm);
			
		// 保存配置
		} else if (dealMethod.equals("saveConfig")) {
			log.info("保存最新信息配置");
			LatestInfoForm latestInfoForm = (LatestInfoForm)requestParam.get("form");		
			//保存配置信息到xml文件
			String filePath = latestInfoService.saveConfigXml(latestInfoForm);
			log.debug("filePath==================="+filePath);
			filePath = filePath.replace(GlobalConfig.appRealPath, "");
			//保存生成的代码到数据库
			latestInfoService.saveConfigContent(latestInfoForm, filePath, siteId, sessionID);
		}
	}

	@Override
	public ResponseEvent validateData(RequestEvent requestEvent)
			throws Exception {
		return null;
	}

	/**
	 * @param latestInfoService the latestInfoService to set
	 */
	public void setLatestInfoService(LatestInfoService latestInfoService) {
		this.latestInfoService = latestInfoService;
	}

	
}
