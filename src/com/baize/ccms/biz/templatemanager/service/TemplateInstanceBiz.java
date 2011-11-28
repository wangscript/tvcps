/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.biz.templatemanager.service;

import java.util.List;
import java.util.Map;

import com.baize.ccms.biz.templatemanager.domain.TemplateInstance;
import com.baize.common.core.dao.Pagination;
import com.baize.common.core.service.BaseService;
import com.baize.common.core.web.event.RequestEvent;
import com.baize.common.core.web.event.ResponseEvent;

/**
 * <p>标题: 模板实例业务类</p>
 * <p>描述: 负责处理模板实例的一些操作</p>
 * <p>模块: 模板管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-4-9 下午04:00:58
 * @history（历次修订内容、修订人、修订时间等）
 */
public class TemplateInstanceBiz extends BaseService {

	/** 注入模板实例服务类 */
	private TemplateInstanceService templateInstanceService;
	
	@Override
	public void performTask(RequestEvent requestEvent, ResponseEvent responseEvent)
			throws Exception {
		String dealMethod = requestEvent.getDealMethod();
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		String sessionID = requestEvent.getSessionID();
		String siteId = requestEvent.getSiteid();
		String templateId = (String) requestParam.get("templateId");
		String nodeId = (String) requestParam.get("nodeId");
		
		// 查找模板实例
		if(dealMethod.equals("")) {
			log.info("模板实例分页");
			Pagination pagination = (Pagination) requestParam.get("pagination");
			pagination = templateInstanceService.templateInstancePagination(pagination, templateId, siteId, sessionID, this.isUpSystemAdmin, this.isSiteAdmin);
			List<TemplateInstance> list =  templateInstanceService.findTemplateInstanceByTemplateId(templateId, siteId, sessionID, this.isUpSystemAdmin, this.isSiteAdmin);
			// 获得实例的id和被使用次数
			String idsAndUsedNum = "";
			for(int i = 0; i < list.size(); i++) {
				TemplateInstance instance = list.get(i);
				if(idsAndUsedNum == "") {
					idsAndUsedNum += instance.getId() + ":" + instance.getUsedNum();
				} else {
					idsAndUsedNum += "::" + instance.getId() + ":" + instance.getUsedNum();
				}
			}
			responseParam.put("idsAndUsedNum", idsAndUsedNum);
			responseParam.put("pagination", pagination);
			log.info("模板实例分页完成");
		
		// 查找要更名的模板实例	 
		} else if(dealMethod.equals("templateInstance")) {
			log.info("获得模板实例");
			String templateInstanceId = (String) requestParam.get("templateInstanceId");
			TemplateInstance templateInstance = templateInstanceService.findTemplateInstanceById(templateInstanceId);
			responseParam.put("templateInstance", templateInstance);
			log.info("获得模板实例完成");
		
		// 添加模板实例	
		} else if(dealMethod.equals("add")) {
			log.info("添加模板实例");
			StringBuffer url = (StringBuffer) requestParam.get("url");
			TemplateInstance templateInstance = (TemplateInstance) requestParam.get("templateInstance");
			String templateSet = (String) requestParam.get("templateSet");
			String infoMessage = "";
			infoMessage = templateInstanceService.addTemplateInstance(templateInstance, templateId, siteId, url, sessionID);
			responseParam.put("templateInstance", templateInstance);
			responseParam.put("infoMessage", infoMessage);
			responseParam.put("templateSet", templateSet);
			log.info("添加模板实例完成");
		
		// 删除模板实例
		} else if(dealMethod.equals("delete")) {
			log.info("删除模板实例");
			String ids = (String) requestParam.get("ids");
			String infoMessage = "";
			// 调用删除目录操作方法
			infoMessage = templateInstanceService.deleteTemplateInstance(ids, siteId, sessionID);
			responseParam.put("infoMessage", infoMessage);
			log.info("删除模板实例完成");
		
		// 	修改模板实例名称
		} else if(dealMethod.equals("modify")) {
			log.info("修改模板实例名称");
			TemplateInstance templateInstance = (TemplateInstance) requestParam.get("templateInstance");
			TemplateInstance newTemplateInstance = templateInstanceService.findTemplateInstanceById(templateInstance.getId());
			newTemplateInstance.setName(templateInstance.getName());
			String infoMessage = "";
			infoMessage = templateInstanceService.modifyTemplateInstance(newTemplateInstance, siteId, sessionID);
			responseParam.put("infoMessage", infoMessage);
			log.info("修改模板实例名称完成");
		
		// 查找绑定的栏目	
		} else if(dealMethod.equals("findBind")) {
			log.debug("查找绑定的栏目");
			String bind = (String) requestParam.get("bind");
			String templateInstanceId = (String) requestParam.get("templateInstanceId");
			String bindedIds = templateInstanceService.findBindedColumnIds(bind, templateInstanceId, siteId);
			responseParam.put("bindedIds", bindedIds);
			responseParam.put("bind", bind);
			responseParam.put("templateInstanceId", templateInstanceId);
		
		// 绑定栏目或者文章	
		} else if(dealMethod.equals("bind")) {
			log.debug("绑定栏目或者文章");
			String bind = (String) requestParam.get("bind");
			String bindedIds = (String) requestParam.get("bindedIds");
			String templateInstanceId =(String) requestParam.get("templateInstanceId");
			String canceledIds = (String) requestParam.get("canceledIds");
			templateInstanceService.modifyBindColumnOrArticle(bind, bindedIds, templateInstanceId, canceledIds);
			responseParam.put("templateInstanceId", templateInstanceId);
			log.debug("绑定结束");
		
		// 取消绑定栏目或者文章
		} else if(dealMethod.equals("cancelBind")) {
			log.debug("取消绑定");
			String bind = (String) requestParam.get("bind");
			String bindedIds = (String) requestParam.get("bindedIds");
			String templateInstanceId =(String) requestParam.get("templateInstanceId");
			templateInstanceService.cancelBindColumnOrArticle(bind, bindedIds, templateInstanceId);
			responseParam.put("templateInstanceId", templateInstanceId);
			log.debug("取消绑定成功");
		}
		responseParam.put("nodeId", nodeId);
		responseParam.put("templateId", templateId);
	}

	
	@Override
	public ResponseEvent validateData(RequestEvent request) throws Exception {
		return null;
	}

	/**
	 * @return the templateInstanceService
	 */
	public TemplateInstanceService getTemplateInstanceService() {
		return templateInstanceService;
	}

	/**
	 * @param templateInstanceService the templateInstanceService to set
	 */
	public void setTemplateInstanceService(
			TemplateInstanceService templateInstanceService) {
		this.templateInstanceService = templateInstanceService;
	}

}
