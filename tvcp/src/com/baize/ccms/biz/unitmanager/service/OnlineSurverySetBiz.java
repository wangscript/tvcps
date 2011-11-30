/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.unitmanager.service;

import java.util.List;
import java.util.Map;

import com.j2ee.cms.biz.unitmanager.web.form.OnlineSurverySetForm;
import com.j2ee.cms.sys.GlobalConfig;
import com.j2ee.cms.common.core.service.BaseService;
import com.j2ee.cms.common.core.web.event.RequestEvent;
import com.j2ee.cms.common.core.web.event.ResponseEvent;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CPS</p>
 * <p>版权: Copyright (c) 2009  
 * @author 郑荣华
 * @version 1.0
 * @since 2009-6-1 下午05:41:26
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class OnlineSurverySetBiz extends BaseService {

	/** 注入栏目链接业务对象 **/
	private OnlineSurverySetService onlineSurverySetService;
	
	@Override
	public void performTask(RequestEvent requestEvent, ResponseEvent responseEvent)
	throws Exception {
    	String dealMethod = requestEvent.getDealMethod();
		String siteId = requestEvent.getSiteid();
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		OnlineSurverySetForm form = (OnlineSurverySetForm) requestParam.get("form");
		
		if(dealMethod.equals("findConfig")){
			log.info("网上调查设置配置");
			//查询普通配置
			form = onlineSurverySetService.findConfig(form, siteId);
			responseParam.put("form", form);
		
		}else if(dealMethod.equals("detail")){
			log.info("网上调查保存设置配置");
			onlineSurverySetService.setStyle(form, siteId);
			//查询普通配置
			form = (OnlineSurverySetForm) onlineSurverySetService.getStyle(siteId, form);
		
		}else if(dealMethod.equals("findCategory")){
			//查询普通配置
			form= (OnlineSurverySetForm) onlineSurverySetService.getStyle(siteId, form);
			responseParam.put("form", form);
			String message =(String)form.getInfoMessage();
			responseParam.put("message", message);
			String categoryId =(String)requestParam.get("categoryId");			                         
			List<OnlineSurverySetForm> list = (List<OnlineSurverySetForm>)onlineSurverySetService.findAllOnlineSurvey(categoryId);  	
			responseParam.put("list", list);
			
		//查找主题
		}else if(dealMethod.equals("findTheme")){
			String category = (String) requestParam.get("category");
			String themes = onlineSurverySetService.findThemeByCategory(category);
			responseParam.put("themes", themes);
			
		//保存	
		}else if(dealMethod.equals("save")){
			log.debug("保存在线调查单元数据");
			String filePath = onlineSurverySetService.saveConfigXml(form); 
			filePath = filePath.replace(GlobalConfig.appRealPath, "");
			onlineSurverySetService.addOnlineSurveryData(form, filePath, siteId);
		}
		responseParam.put("form", form);
	}

	@Override
	public ResponseEvent validateData(RequestEvent requestEvent)
			throws Exception {
		return null;
	}

	/**
	 * @param onlineSurverySetService the onlineSurverySetService to set
	 */
	public void setOnlineSurverySetService(OnlineSurverySetService onlineSurverySetService) {
		this.onlineSurverySetService = onlineSurverySetService;
	}
}