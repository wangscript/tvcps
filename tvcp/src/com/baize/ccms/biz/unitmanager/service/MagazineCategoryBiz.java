/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.unitmanager.service;

import java.util.List;
import java.util.Map;

import com.baize.ccms.biz.templatemanager.domain.TemplateUnitStyle;
import com.baize.ccms.biz.unitmanager.web.form.MagazineCategoryForm;
import com.baize.ccms.biz.unitmanager.web.form.TitleLinkForm;
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
 * @since 2009-9-8 上午11:01:05
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class MagazineCategoryBiz extends BaseService {

	/** 注入期刊分类业务对象 .*/
	private MagazineCategoryService magazineCategoryService;
	
	@Override
	public void performTask(RequestEvent requestEvent, ResponseEvent responseEvent)	throws Exception {
		String dealMethod = requestEvent.getDealMethod();
		String siteId = requestEvent.getSiteid();
		String sessionID = requestEvent.getSessionID();
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		// 查找配置文件
		if(dealMethod.equals("findConfig")) {
			log.info("查找期刊分类配置");
			MagazineCategoryForm form = (MagazineCategoryForm) requestParam.get("form");
			//查询普通配置
			magazineCategoryService.findConfig(form);
			//查询模板单元样式
			List<TemplateUnitStyle> templateUnitStyleList = magazineCategoryService.findTemplateUnitStyleByCategoryIdAndSiteId(form.getUnit_categoryId(), siteId);
			form.setTemplateUnitStyleList(templateUnitStyleList);
			responseParam.put("form", form);
			log.info("查找期刊分类配置完成");	
		}else if(dealMethod.equals("saveConfig")){
			// 保存配置
			MagazineCategoryForm form = (MagazineCategoryForm) requestParam.get("form");
			String filePath = magazineCategoryService.saveConfigXml(form);
			filePath = filePath.replace(GlobalConfig.appRealPath, "");
			//保存生成的代码到数据库
			magazineCategoryService.saveConfigContent(form, filePath, siteId, sessionID);
			
		// 查找信息分类	
		} else if(dealMethod.equals("findInfoCategory")) {
			String columnId = (String) requestParam.get("columnId");
			String unitId = (String) requestParam.get("unitId");
			Map map = magazineCategoryService.findInfoCategory(columnId, unitId);
			responseParam.put("allData", map.get("allEnumData"));
			responseParam.put("chooseData", map.get("chooseEnumData"));
		
		// 查找指定栏目的属性	
		} else if(dealMethod.equals("findFieldCode")) {
			MagazineCategoryForm magazineCategoryForm = (MagazineCategoryForm) requestParam.get("form");	
			magazineCategoryService.findArticleAttributeByColumnId(magazineCategoryForm);
			responseParam.put("magazineCategoryForm", magazineCategoryForm);
		}
	}

	@Override
	public ResponseEvent validateData(RequestEvent requestEvent) throws Exception {
		return null;
	}

	/**
	 * @param magazineCategoryService the magazineCategoryService to set
	 */
	public void setMagazineCategoryService(
			MagazineCategoryService magazineCategoryService) {
		this.magazineCategoryService = magazineCategoryService;
	}

}
