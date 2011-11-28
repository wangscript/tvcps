/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.templatemanager.service;

import java.util.List;
import java.util.Map;

import com.baize.ccms.biz.sitemanager.domain.Site;
import com.baize.ccms.biz.templatemanager.domain.TemplateCategory;
import com.baize.ccms.biz.usermanager.domain.User;
import com.baize.common.core.dao.Pagination;
import com.baize.common.core.service.BaseService;
import com.baize.common.core.web.event.RequestEvent;
import com.baize.common.core.web.event.ResponseEvent;

/**
 * <p>标题: 模板类别业务类</p>
 * <p>描述: 这里是用于响应请求做一些功能的具体处理，在调用templateCategoryService的一些方法</p>
 * <p>模块: 模板管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 郑荣华
 * @version 1.0
 * @since 2009-4-27 下午06:52:22
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class TemplateCategoryBiz extends BaseService {

	/** 注入模板类别业务对象 **/
	private  TemplateCategoryService templateCategoryService;

	@Override
	public void performTask(RequestEvent requestEvent, ResponseEvent responseEvent)
			throws Exception {
		String dealMethod = requestEvent.getDealMethod();
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		// 获取当前登陆用户的id
		String sessionID = requestEvent.getSessionID();
		// 获取网站Id
		String siteId = requestEvent.getSiteid();
		
		String nodeId = (String) requestParam.get("nodeId");
		
		//处理模板类别分页
		if(dealMethod.equals("")) {
			log.info("模板类别分页");
			Pagination pagination = (Pagination) requestParam.get("pagination");
			pagination = templateCategoryService.templateCategoryPage(pagination, siteId, sessionID, isUpSystemAdmin, isSiteAdmin);
			if(pagination.getData().size() == 0){
				pagination.currPage = pagination.currPage - 1;
				pagination = templateCategoryService.templateCategoryPage(pagination, siteId, sessionID, isUpSystemAdmin, isSiteAdmin); 
			}
			responseParam.put("pagination", pagination);
			log.info("模板类别分页成功");
		
		// 添加模板类别
		} else  if(dealMethod.equals("add")) {
			log.info("添加模板类别");
			TemplateCategory templateCategory = (TemplateCategory) requestParam.get("templateCategory");
			String infoMessage = "";
			infoMessage = templateCategoryService.addTemplateCategory(siteId, sessionID, templateCategory);
			responseParam.put("infoMessage", infoMessage);
			log.info("添加模板类别处理完成");
			
		// 删除模板类别
		} else  if(dealMethod.equals("delete")) {
			log.info("删除模板类别");
			String ids = (String) requestParam.get("ids");
			String infoMessage = "";
			// 删除模板类别
			infoMessage =  templateCategoryService.deleteTemplateCategory(ids, siteId, sessionID);
			responseParam.put("infoMessage", infoMessage);
			log.info(infoMessage);
			
		// 详细模板类别
		} else  if(dealMethod.equals("detail")) {
			log.info("查询模板类别详细");
			String id = (String) requestParam.get("id");
			TemplateCategory templateCategory = templateCategoryService.findTemplateCategoryByTemplateCategoryId(id);
			User creator = new User();
			creator.setId(sessionID);
			templateCategory.setCreator(creator);
			Site site = new Site();
			site.setId(siteId);
			templateCategory.setSite(site);
			responseParam.put("templateCategory", templateCategory);
			log.info("查询模板类别详细成功");
			
		// 修改模板类别
		} else  if(dealMethod.equals("modify")) {
			log.info("修改模板类别");
			TemplateCategory templateCategory = (TemplateCategory) requestParam.get("templateCategory");
			String infoMessage = "";
			infoMessage = templateCategoryService.modifyTemplateCategory(templateCategory, siteId, sessionID);
			responseParam.put("infoMessage", infoMessage);
			log.info("修改模板类别完成");
			
		// 加载模板类别树
		} else  if(dealMethod.equals("getTree")) {
			log.info("加载模板类别树");
			String treeid = (String) requestParam.get("treeid");
			List<Object> treeList = templateCategoryService.getTreeList(treeid, siteId, sessionID, isUpSystemAdmin, isSiteAdmin);
			responseParam.put("treeList", treeList);
			log.info("加载模板类别树成功");
		}
		responseParam.put("nodeId", nodeId);
	}
	
	@Override
	public ResponseEvent validateData(RequestEvent arg0) throws Exception {
		return null;
	}

	/**
	 * @return the templateCategoryService
	 */
	public TemplateCategoryService getTemplateCategoryService() {
		return templateCategoryService;
	}

	/**
	 * @param templateCategoryService the templateCategoryService to set
	 */
	public void setTemplateCategoryService(
			TemplateCategoryService templateCategoryService) {
		this.templateCategoryService = templateCategoryService;
	}
}
