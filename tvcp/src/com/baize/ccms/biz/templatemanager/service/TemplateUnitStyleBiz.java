/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.templatemanager.service;

import java.util.Map;

import com.j2ee.cms.biz.templatemanager.domain.TemplateUnitStyle;
import com.j2ee.cms.biz.templatemanager.web.form.TemplateUnitStyleForm;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.service.BaseService;
import com.j2ee.cms.common.core.util.StringUtil;
import com.j2ee.cms.common.core.web.event.RequestEvent;
import com.j2ee.cms.common.core.web.event.ResponseEvent;

/**
 * 
 * <p>标题: —— 模板单元样式业务流转类</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 模板管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-6-6 下午05:45:54
 * @history（历次修订内容、修订人、修订时间等）
 */
public class TemplateUnitStyleBiz extends BaseService  {
	
	/** 注入单元样式服务类 */
	private TemplateUnitStyleService templateUnitStyleService;

	@Override
	public void performTask(RequestEvent requestEvent, ResponseEvent responseEvent)
			throws Exception {
		String dealMethod = requestEvent.getDealMethod();
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		// 网站id
		String siteId = requestEvent.getSiteid();
		String userId = requestEvent.getSessionID();
		if (dealMethod.equals("list")) {
			//查找样式列表
			Pagination pagination = (Pagination) requestParam.get("pagination");
			String categoryId = String.valueOf(requestParam.get("categoryId"));
			pagination = templateUnitStyleService.findTemplateUnitStylePage(pagination, categoryId, siteId);
			//分页显示数据
			responseParam.put("pagination", pagination);
			//模板类别名称
			responseParam.put("categoryName",templateUnitStyleService.findTemplateUnitCategoryNameByKey(categoryId));
			log.info("查询单元样式分页成功.");
			responseParam.put("categoryId",categoryId);

		}else if (dealMethod.equals("detail")) {
			//样式详细信息页面
			String styleId = String.valueOf(requestParam.get("styleId"));
			String categoryId = String.valueOf(requestParam.get("categoryId"));
			String categoryName = String.valueOf(requestParam.get("categoryName"));
			TemplateUnitStyle templateUnitStyle = templateUnitStyleService.findTemplateUnitStyleByKey(styleId);
			
			//获取所有样式名称
			String styleNameStr = templateUnitStyleService.findStyleNameStr(categoryId);
			responseParam.put("styleNameStr",styleNameStr);
			responseParam.put("categoryId",categoryId);
			responseParam.put("categoryName",categoryName);
			responseParam.put("templateUnitStyle",templateUnitStyle);
			responseParam.put("styleId",styleId);
		}else if (dealMethod.equals("add")) {
			//增加样式---修改样式
			TemplateUnitStyle templateUnitStyle = (TemplateUnitStyle)requestParam.get("templateUnitStyle");
			String categoryId = String.valueOf(requestParam.get("categoryId"));
			String styleId = String.valueOf(requestParam.get("styleId"));
			String htmlContent = String.valueOf(requestParam.get("htmlContent"));
			if(!StringUtil.isEmpty(styleId) && !styleId.equals("0")){ 
				//修改数据
				templateUnitStyleService.modifyTemplateUnitStyle(templateUnitStyle, categoryId, htmlContent, userId, siteId, styleId);
			}else{
				//保存数据
				templateUnitStyleService.addTemplateUnitStyle(templateUnitStyle, categoryId, htmlContent, userId, siteId);
			}
			//查找样式列表
			Pagination pagination = (Pagination) requestParam.get("pagination");	
			//模板类别名称
			responseParam.put("categoryName",templateUnitStyleService.findTemplateUnitCategoryNameByKey(categoryId));
			log.info("查询单元样式分页成功.");
			responseParam.put("categoryId",categoryId);
			//分页显示数据
			responseParam.put("pagination",templateUnitStyleService.findTemplateUnitStylePage(pagination, categoryId, siteId));
		
		}else if (dealMethod.equals("delete")) {
			//删除样式
			String ids = (String)(requestParam.get("ids"));
			String categoryId = (String) requestParam.get("categoryId");
			templateUnitStyleService.deleteTemplateUnitStyleByIds(ids, siteId, userId, categoryId);
			//查找样式列表
			Pagination pagination = (Pagination) requestParam.get("pagination");
			//分页显示数据
			responseParam.put("pagination",templateUnitStyleService.findTemplateUnitStylePage(pagination, categoryId, siteId));
			//模板类别名称
			responseParam.put("categoryName",templateUnitStyleService.findTemplateUnitCategoryNameByKey(categoryId));
			log.info("查询单元样式分页成功.");
			responseParam.put("categoryId",categoryId);
		}else if(dealMethod.equals("findStyleById")){
			//根据样式管理的ID查询样式的数据
			TemplateUnitStyleForm templateUnitStyleForm = (TemplateUnitStyleForm) requestParam.get("templateUnitStyleForm");
			templateUnitStyleService.findStyleByStyleId(templateUnitStyleForm, siteId);
			responseParam.put("templateUnitStyleForm", templateUnitStyleForm);
		}
	}

	@Override
	public ResponseEvent validateData(RequestEvent requestEvent)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param templateUnitStyleService the templateUnitStyleService to set
	 */
	public void setTemplateUnitStyleService(
			TemplateUnitStyleService templateUnitStyleService) {
		this.templateUnitStyleService = templateUnitStyleService;
	}

}
