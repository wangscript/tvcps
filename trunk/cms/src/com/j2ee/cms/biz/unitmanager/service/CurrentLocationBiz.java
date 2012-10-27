/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.unitmanager.service;

import java.util.List;
import java.util.Map;

import com.j2ee.cms.biz.templatemanager.domain.TemplateUnit;
import com.j2ee.cms.biz.templatemanager.domain.TemplateUnitStyle;
import com.j2ee.cms.biz.unitmanager.web.form.CurrentLocationForm;
import com.j2ee.cms.sys.GlobalConfig;
import com.j2ee.cms.common.core.service.BaseService;
import com.j2ee.cms.common.core.web.event.RequestEvent;
import com.j2ee.cms.common.core.web.event.ResponseEvent;

/**
 * <p>标题: —— 当前位置业务流转类</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 用户管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-6-15 上午10:12:11
 * @history（历次修订内容、修订人、修订时间等） 
 */

public class CurrentLocationBiz  extends BaseService{
	/** 注入当前位置业务对象 **/
	private CurrentLocationService currentLocationService;
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
			log.info("查找当前位置配置");
			CurrentLocationForm currentLocationForm = (CurrentLocationForm)requestParam.get("form");
			//查询普通配置
			currentLocationService.findConfig(currentLocationForm,siteId);
			//查询模板单元样式
			List<TemplateUnitStyle> templateUnitStyleList = currentLocationService.findTemplateUnitStyleByCategoryIdAndSiteId(
					currentLocationForm.getUnit_categoryId(), siteId);
			currentLocationForm.setTemplateUnitStyleList(templateUnitStyleList);
			responseParam.put("currentLocationForm", currentLocationForm);
			// 保存配置
		} else if (dealMethod.equals("saveConfig")) {
			CurrentLocationForm currentLocationForm = (CurrentLocationForm)requestParam.get("form");		
			//保存配置信息到xml文件
			String filePath = currentLocationService.saveConfigXml(currentLocationForm);
			log.debug("filePath==================="+filePath);
			filePath = filePath.replace(GlobalConfig.appRealPath, "");
			//保存生成的代码到数据库
			currentLocationService.saveConfigContent(currentLocationForm, filePath, siteId, sessionID);
		}else if (dealMethod.equals("saveSiteConfig")) {
			CurrentLocationForm currentLocationForm = (CurrentLocationForm)requestParam.get("form");	
			String unitId = currentLocationForm.getUnit_unitId();
			List list = currentLocationService.findTemplateUnitByUnitId(unitId);
			for(int i = 0 ; i < list.size() ; i++){
				TemplateUnit allTemplateUnit = new TemplateUnit();
				allTemplateUnit = (TemplateUnit)list.get(i);
				currentLocationForm.setUnit_unitId(allTemplateUnit.getId());
				//保存配置信息到xml文件
				String filePath = currentLocationService.saveConfigXml(currentLocationForm);
				log.debug("filePath==================="+filePath);
				filePath = filePath.replace(GlobalConfig.appRealPath, "");
				//保存生成的代码到数据库
				currentLocationService.saveConfigContent(currentLocationForm, filePath, siteId, sessionID);
			}
			
		}
	}

	@Override
	public ResponseEvent validateData(RequestEvent requestEvent)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param currentLocationService the currentLocationService to set
	 */
	public void setCurrentLocationService(
			CurrentLocationService currentLocationService) {
		this.currentLocationService = currentLocationService;
	}

}
