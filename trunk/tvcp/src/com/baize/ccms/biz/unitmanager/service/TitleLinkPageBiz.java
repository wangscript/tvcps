/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.unitmanager.service;

import java.util.List;
import java.util.Map;

import com.baize.ccms.biz.templatemanager.domain.TemplateUnit;
import com.baize.ccms.biz.templatemanager.domain.TemplateUnitStyle;
import com.baize.ccms.biz.unitmanager.web.form.PictureNewsForm;
import com.baize.ccms.biz.unitmanager.web.form.TitleLinkPageForm;
import com.baize.ccms.sys.GlobalConfig;
import com.baize.common.core.service.BaseService;
import com.baize.common.core.web.event.RequestEvent;
import com.baize.common.core.web.event.ResponseEvent;

/**
 * <p>标题: —— 标题连接业务流转类</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CCMS</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-6-1 下午05:41:26
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class TitleLinkPageBiz extends BaseService {

	/** 注入标题链接业务对象 **/
	private TitleLinkPageService titleLinkPageService;
	@Override
	public void performTask(RequestEvent requestEvent, ResponseEvent responseEvent)
	throws Exception {
		String dealMethod = requestEvent.getDealMethod();
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		String siteId = requestEvent.getSiteid();
		String sessionID = requestEvent.getSessionID();
		// 查找配置
		if (dealMethod.equals("findConfig")) {
			log.info("查找栏目链接配置");
			TitleLinkPageForm titleLinkPageForm = (TitleLinkPageForm) requestParam.get("form");
			//查询普通配置
			titleLinkPageService.findConfig(titleLinkPageForm,siteId);
			//查询模板单元样式
			List<TemplateUnitStyle> templateUnitStyleList = titleLinkPageService.findTemplateUnitStyleByCategoryIdAndSiteId(
					titleLinkPageForm.getUnit_categoryId(), siteId);
			titleLinkPageForm.setTemplateUnitStyleList(templateUnitStyleList);
			responseParam.put("titleLinkPageForm", titleLinkPageForm);
			
		// 保存配置
		} else if (dealMethod.equals("saveConfig")) {
			TitleLinkPageForm titleLinkPageForm = (TitleLinkPageForm)requestParam.get("form");		
			//保存配置信息到xml文件
			String filePath = titleLinkPageService.saveConfigXml(titleLinkPageForm);
			filePath = filePath.replace(GlobalConfig.appRealPath, "");
			//保存生成的代码到数据库
			titleLinkPageService.saveConfigContent(titleLinkPageForm, filePath, siteId, sessionID);
		
		// 站内保存
		} else if (dealMethod.equals("saveSiteConfig")) {
			TitleLinkPageForm titleLinkPageForm = (TitleLinkPageForm)requestParam.get("form");	
			String unitId = titleLinkPageForm.getUnit_unitId();
			List list = titleLinkPageService.findTemplateUnitByUnitId(unitId);
			for(int i = 0 ; i < list.size() ; i++){
				TemplateUnit allTemplateUnit = new TemplateUnit();
				allTemplateUnit = (TemplateUnit)list.get(i);
				titleLinkPageForm.setUnit_unitId(allTemplateUnit.getId());
				//保存配置信息到xml文件
				String filePath = titleLinkPageService.saveConfigXml(titleLinkPageForm);
				filePath = filePath.replace(GlobalConfig.appRealPath, "");
				//保存生成的代码到数据库
				titleLinkPageService.saveConfigContent(titleLinkPageForm, filePath, siteId, sessionID);
			}					
			
		// 查找指定栏目的属性	
		} else if(dealMethod.equals("findFieldCode")) {
			TitleLinkPageForm titleLinkPageForm = (TitleLinkPageForm) requestParam.get("form");	
			titleLinkPageService.findArticleAttributeByColumnId(titleLinkPageForm);
			responseParam.put("titleLinkPageForm", titleLinkPageForm);
		} 
	}

	@Override
	public ResponseEvent validateData(RequestEvent requestEvent)
			throws Exception {
		return null;
	}

	/**
	 * @param titleLinkService the titleLinkService to set
	 */
	public void setTitleLinkPageService(TitleLinkPageService titleLinkPageService) {
		this.titleLinkPageService = titleLinkPageService;
	}


}
