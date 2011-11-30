/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.unitmanager.service;

import java.util.List;
import java.util.Map;

import com.j2ee.cms.biz.templatemanager.domain.TemplateUnitStyle;
import com.j2ee.cms.biz.unitmanager.web.form.ArticleTextForm;
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
 * @since 2009-7-23 上午11:04:43
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class ArticleTextBiz extends BaseService {

	/** 注入文章正文业务对象 **/
	private ArticleTextService articleTextService;
	
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
			ArticleTextForm form = (ArticleTextForm) requestParam.get("form");
			//查询普通配置
			articleTextService.findConfig(form);
			//查询模板单元样式
		    List<TemplateUnitStyle> templateUnitStyleList = articleTextService.findTemplateUnitStyleByCategoryIdAndSiteId(form.getUnit_categoryId(), siteId);
			form.setTemplateUnitStyleList(templateUnitStyleList);
			responseParam.put("form", form);
			log.info("查找栏目链接配置完成");
			
		// 保存文章正文的数据		
		} else if(dealMethod.equals("saveConfig")) {
			log.info("保存文章正文");
			ArticleTextForm form = (ArticleTextForm) requestParam.get("form");
			String filePath = articleTextService.saveConfigXml(form);
			filePath = filePath.replace(GlobalConfig.appRealPath, "");
			//保存生成的代码到数据库
			articleTextService.saveConfigContent(form, filePath, siteId, sessionID);
			log.info("保存文章正文完成");
		// 站内保存
		} else if (dealMethod.equals("saveSiteConfig")) {
			log.info("站内保存文章正文");
			ArticleTextForm form = (ArticleTextForm) requestParam.get("form");	
			String unitId = form.getUnit_unitId();
			// 调用站内保存方法
			articleTextService.saveSiteConfig(form, unitId, siteId, sessionID);
			log.info("站内保存文章正文完成");
		}

	}

	@Override
	public ResponseEvent validateData(RequestEvent requestEvent)
			throws Exception {
		return null;
	}

	/**
	 * @param articleTextService the articleTextService to set
	 */
	public void setArticleTextService(ArticleTextService articleTextService) {
		this.articleTextService = articleTextService;
	}

}
