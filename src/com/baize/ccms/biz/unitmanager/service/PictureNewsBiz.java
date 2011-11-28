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
import com.baize.ccms.sys.GlobalConfig;
import com.baize.common.core.service.BaseService;
import com.baize.common.core.web.event.RequestEvent;
import com.baize.common.core.web.event.ResponseEvent;

/**
 * <p>标题: —— 图片新闻业务流转类</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CCMS</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-6-1 下午05:41:26
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class PictureNewsBiz extends BaseService {

	/** 注入图片新闻业务对象 **/
	private PictureNewsService pictureNewsService;
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
			log.info("查找图片新闻配置");
			PictureNewsForm pictureNewsForm = (PictureNewsForm)requestParam.get("form");
			//查询普通配置
			pictureNewsService.findConfig(pictureNewsForm,siteId);
			//查询模板单元样式
			List<TemplateUnitStyle> templateUnitStyleList = pictureNewsService.findTemplateUnitStyleByCategoryIdAndSiteId(
					pictureNewsForm.getUnit_categoryId(), siteId);
			pictureNewsForm.setTemplateUnitStyleList(templateUnitStyleList);
			responseParam.put("pictureNewsForm", pictureNewsForm);
			// 保存配置
		} else if (dealMethod.equals("saveConfig")) {
			PictureNewsForm pictureNewsForm = (PictureNewsForm)requestParam.get("form");		
			//保存配置信息到xml文件
			String filePath = pictureNewsService.saveConfigXml(pictureNewsForm);
			log.debug("filePath==================="+filePath);
			filePath = filePath.replace(GlobalConfig.appRealPath, "");
			//保存生成的代码到数据库
			pictureNewsService.saveConfigContent(pictureNewsForm, filePath, siteId, sessionID);
		}else if (dealMethod.equals("saveSiteConfig")) {
			PictureNewsForm pictureNewsForm = (PictureNewsForm)requestParam.get("form");	
			String unitId = pictureNewsForm.getUnit_unitId();
			List list = pictureNewsService.findTemplateUnitByUnitId(unitId);
			for(int i = 0 ; i < list.size() ; i++){
				TemplateUnit allTemplateUnit = new TemplateUnit();
				allTemplateUnit = (TemplateUnit)list.get(i);
				pictureNewsForm.setUnit_unitId(allTemplateUnit.getId());
				//保存配置信息到xml文件
				String filePath = pictureNewsService.saveConfigXml(pictureNewsForm);
				log.debug("filePath==================="+filePath);
				filePath = filePath.replace(GlobalConfig.appRealPath, "");
				//保存生成的代码到数据库
				pictureNewsService.saveConfigContent(pictureNewsForm, filePath, siteId, sessionID);
			}
			
		// 查找指定栏目的属性	
		} else if(dealMethod.equals("findFieldCode")) {
			PictureNewsForm pictureNewsForm = (PictureNewsForm) requestParam.get("form");	
			pictureNewsService.findArticleAttributeByColumnId(pictureNewsForm);
			responseParam.put("pictureNewsForm", pictureNewsForm);
		}
	}

	@Override
	public ResponseEvent validateData(RequestEvent requestEvent)
			throws Exception {
		return null;
	}

	/**
	 * @param pictureNewsService the pictureNewsService to set
	 */
	public void setPictureNewsService(PictureNewsService pictureNewsService) {
		this.pictureNewsService = pictureNewsService;
	}



}
