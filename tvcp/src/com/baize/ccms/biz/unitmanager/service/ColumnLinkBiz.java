/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.unitmanager.service;

import java.util.List;
import java.util.Map;

import com.baize.ccms.biz.templatemanager.domain.TemplateUnitStyle;
import com.baize.ccms.biz.unitmanager.web.form.ColumnLinkForm;
import com.baize.ccms.sys.GlobalConfig;
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
 * @since 2009-6-1 下午05:41:26
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class ColumnLinkBiz extends BaseService {

	/** 注入栏目链接业务对象 **/
	private ColumnLinkService columnLinkService;
	@Override
	public void performTask(RequestEvent requestEvent, ResponseEvent responseEvent)
	throws Exception {
		String dealMethod = requestEvent.getDealMethod();
		String siteId = requestEvent.getSiteid();
		String sessionID = requestEvent.getSessionID();
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		// 查找配置
		if (dealMethod.equals("findConfig")) {
			log.info("查找栏目链接配置");
			ColumnLinkForm form = (ColumnLinkForm) requestParam.get("form");
			//查询普通配置
			columnLinkService.findConfig(form);
			//查询模板单元样式
			List<TemplateUnitStyle> templateUnitStyleList = columnLinkService.findTemplateUnitStyleByCategoryIdAndSiteId(form.getUnit_categoryId(), siteId);
			form.setTemplateUnitStyleList(templateUnitStyleList);
			responseParam.put("form", form);
			log.info("查找栏目链接配置完成");
			
		// 保存栏目链接的数据		
		} else if(dealMethod.equals("saveConfig")) {
			ColumnLinkForm form = (ColumnLinkForm) requestParam.get("form");
			String filePath = columnLinkService.saveConfigXml(form);
			filePath = filePath.replace(GlobalConfig.appRealPath, "");
			//保存生成的代码到数据库
			columnLinkService.saveConfigContent(form, filePath, siteId, sessionID);

		// 站内保存
		} else if (dealMethod.equals("saveSiteConfig")) {
			ColumnLinkForm form = (ColumnLinkForm) requestParam.get("form");	
			String unitId = form.getUnit_unitId();
			// 调用站内保存方法
			columnLinkService.saveSiteConfig(form, unitId, siteId, sessionID);
		}
	}

	@Override
	public ResponseEvent validateData(RequestEvent requestEvent)
			throws Exception {
		return null;
	}

	/**
	 * @param columnLinkService the columnLinkService to set
	 */
	public void setColumnLinkService(ColumnLinkService columnLinkService) {
		this.columnLinkService = columnLinkService;
	}

}
