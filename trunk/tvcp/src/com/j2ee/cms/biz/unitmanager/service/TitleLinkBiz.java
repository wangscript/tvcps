/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.unitmanager.service;

import java.util.List;
import java.util.Map;

import com.j2ee.cms.biz.templatemanager.domain.TemplateUnit;
import com.j2ee.cms.biz.templatemanager.domain.TemplateUnitStyle;
import com.j2ee.cms.biz.unitmanager.web.form.TitleLinkForm;
import com.j2ee.cms.sys.GlobalConfig;
import com.j2ee.cms.common.core.service.BaseService;
import com.j2ee.cms.common.core.web.event.RequestEvent;
import com.j2ee.cms.common.core.web.event.ResponseEvent;

/**
 * <p>标题: —— 标题连接业务流转类</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CPS</p>
 * <p>版权: Copyright (c) 2009  
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-6-1 下午05:41:26
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class TitleLinkBiz extends BaseService {

	/** 注入标题链接业务对象 **/
	private TitleLinkService titleLinkService;
	@Override
	public void performTask(RequestEvent requestEvent, ResponseEvent responseEvent)
	throws Exception {
		String dealMethod = requestEvent.getDealMethod();
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		String siteId = requestEvent.getSiteid();
		String sessionID = requestEvent.getSessionID();
		TitleLinkForm titleLinkForm = (TitleLinkForm)requestParam.get("form");
		// 查找配置
		if (dealMethod.equals("findConfig")) {
			log.info("查找栏目链接配置");		
			//查询普通配置
			titleLinkService.findConfig(titleLinkForm,siteId);
			//查询模板单元样式
			List<TemplateUnitStyle> templateUnitStyleList = titleLinkService.findTemplateUnitStyleByCategoryIdAndSiteId(
					titleLinkForm.getUnit_categoryId(), siteId);
			titleLinkForm.setTemplateUnitStyleList(templateUnitStyleList);
			responseParam.put("titleLinkForm", titleLinkForm);		
		} else if (dealMethod.equals("saveConfig")) {
			// 保存配置
			//保存配置信息到xml文件
			String filePath = titleLinkService.saveConfigXml(titleLinkForm);
			log.debug("filePath==================="+filePath);
			filePath = filePath.replace(GlobalConfig.appRealPath, "");
			//保存生成的代码到数据库
			titleLinkService.saveConfigContent(titleLinkForm, filePath, siteId, sessionID);	
		} else if (dealMethod.equals("saveSiteConfig")) {	
			// 站内保存
			String unitId = titleLinkForm.getUnit_unitId();
			List list = titleLinkService.findTemplateUnitByUnitId(unitId);
			for(int i = 0 ; i < list.size() ; i++){
				TemplateUnit allTemplateUnit = new TemplateUnit();
				allTemplateUnit = (TemplateUnit)list.get(i);
				titleLinkForm.setUnit_unitId(allTemplateUnit.getId());
				//保存配置信息到xml文件
				String filePath = titleLinkService.saveConfigXml(titleLinkForm);
				log.debug("filePath==================="+filePath);
				filePath = filePath.replace(GlobalConfig.appRealPath, "");
				//保存生成的代码到数据库
				titleLinkService.saveConfigContent(titleLinkForm, filePath, siteId, sessionID);
			}
			
		// 查找指定栏目的属性	
		} else if(dealMethod.equals("findFieldCode")) {			
			titleLinkService.findArticleAttributeByColumnId(titleLinkForm);
			responseParam.put("titleLinkForm", titleLinkForm);
		}/*else if(dealMethod.equals("findStyleById")){
			//根据样式管理的ID查询样式的数据
			titleLinkService.findStyleByStyleId(titleLinkForm, siteId);
			responseParam.put("titleLinkForm", titleLinkForm);
		}*/
	}

	@Override
	public ResponseEvent validateData(RequestEvent requestEvent)
			throws Exception {
		return null;
	}

	/**
	 * @param titleLinkService the titleLinkService to set
	 */
	public void setTitleLinkService(TitleLinkService titleLinkService) {
		this.titleLinkService = titleLinkService;
	}


}
