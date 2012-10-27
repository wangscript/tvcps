/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.templatemanager.service;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.j2ee.cms.biz.columnmanager.domain.Column;
import com.j2ee.cms.biz.sitemanager.domain.Site;
import com.j2ee.cms.biz.templatemanager.domain.TemplateCategory;
import com.j2ee.cms.biz.templatemanager.domain.TemplateUnit;
import com.j2ee.cms.biz.templatemanager.domain.TemplateUnitCategory;
import com.j2ee.cms.sys.GlobalConfig;
import com.j2ee.cms.common.core.service.BaseService;
import com.j2ee.cms.common.core.util.StringUtil;
import com.j2ee.cms.common.core.web.event.RequestEvent;
import com.j2ee.cms.common.core.web.event.ResponseEvent;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 模板管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-5-13 下午02:18:48
 * @history（历次修订内容、修订人、修订时间等）
 */
public class TemplateUnitBiz extends BaseService {
	
	/** 注入模板单元服务类 */
	private TemplateUnitService templateUnitService;
	
	/** 所有模板单元类别 */
	private static List<TemplateUnitCategory> templateUnitCategories;
	
	/** 存放需要复制的单元的ID */
	private static Map unitsCache = new Hashtable();

	@Override
	public void performTask(RequestEvent requestEvent, ResponseEvent responseEvent)
			throws Exception {
		String dealMethod = requestEvent.getDealMethod();
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		String sessionID =requestEvent.getSessionID();
		String siteId = requestEvent.getSiteid();
		String nodeId = (String) requestParam.get("nodeId");
		// 设置模板
		if(dealMethod.equals("")) {
			log.info("设置模板");
			List columnList = new ArrayList();
			String columnTemplateSet = "yes";
			if(!this.isUpSiteAdmin){
				columnTemplateSet =  templateUnitService.findColumnTemplateSet(nodeId, siteId, sessionID);
			}
			// 网站模板信息
			if(nodeId == null) {
				Site site = templateUnitService.findSiteBySiteId(siteId);
				responseParam.put("site", site);
				
			// 栏目模板信息
			} else {
				Column column = templateUnitService.findColumnTemplate(nodeId);
				responseParam.put("column", column);
			}
			columnList = templateUnitService.findColumnTempalte(nodeId, siteId, sessionID, isUpSiteAdmin);
			responseParam.put("columnTemplateSet", columnTemplateSet);
			responseParam.put("columnList", columnList);
			
		// 选择模板	
		} else if(dealMethod.equals("chooseTemplate")) {
			log.info("选择模板");
			String isTemplateSeted = (String) requestParam.get("isTemplateSeted");
			String templateInstance = (String) requestParam.get("templateInstance");
			String templateInstanceIdValue = (String) requestParam.get("templateInstanceIdValue");
			List<TemplateCategory> templateCategoryList = templateUnitService.findTemplateCategory(siteId, sessionID, isUpSystemAdmin, isSiteAdmin);
			String templateInstanceStr = "";
			templateInstanceStr = templateUnitService.chooseTemplate(templateCategoryList, siteId, sessionID, this.isUpSystemAdmin, this.isSiteAdmin);
			responseParam.put("templateInstanceStr", templateInstanceStr);
			responseParam.put("templateInstanceIdValue", templateInstanceIdValue);
			responseParam.put("templateCategoryList", templateCategoryList);
			responseParam.put("templateInstance", templateInstance);
			responseParam.put("isTemplateSeted", isTemplateSeted);
			
		// 重新选择模板实例	
		} else if(dealMethod.equals("update")) {
			log.info("更新模板");
			String isTemplateSeted = (String) requestParam.get("isTemplateSeted");
			String templateInstance = (String) requestParam.get("templateInstance");
			String templateType = (String) requestParam.get("templateType");
			String templateInstanceId = (String) requestParam.get("templateInstanceId");
			String templateInstanceIdValue = (String) requestParam.get("templateInstanceIdValue");
			String instanceName = "";
			instanceName = templateUnitService.modifyTemplateChoose(templateType, nodeId, siteId, templateInstanceId, sessionID);
			responseParam.put("templateInstanceIdValue", templateInstanceIdValue);
			responseParam.put("instanceName", instanceName);
			responseParam.put("templateInstance", templateInstance);
			responseParam.put("templateInstanceId", templateInstanceId);
			responseParam.put("isTemplateSeted", isTemplateSeted);
			
			String templateSet = (String) requestParam.get("templateSet");
			responseParam.put("templateSet", templateSet);
		
		// 取消模板选择	
		} else if(dealMethod.equals("cancelTemplate")) {
			log.info("取消模板");
			String templateInstanceId = (String) requestParam.get("templateInstanceId");
			String templateType = (String) requestParam.get("templateType");
			templateUnitService.modifyCancelTemplate(siteId, templateType, nodeId, templateInstanceId, sessionID);
			// 网站模板信息
			if(nodeId == null) {
				Site site = templateUnitService.findSiteBySiteId(siteId);
				responseParam.put("site", site);
				
			// 栏目模板信息
			} else {
				Column column = templateUnitService.findColumnTemplate(nodeId);
				responseParam.put("column", column);
			}
			
		// 模板设置
		} else if (dealMethod.equals("templateSet")) {
			log.info("模板设置");
			String columnId = (String) requestParam.get("columnId");
			String templateId = (String) requestParam.get("templateId");
			String templateType = (String) requestParam.get("templateType");
			String htmlContent = templateUnitService.getReplacementTemplate(templateType, templateId, columnId, siteId);
			responseParam.put("columnId", columnId);
			responseParam.put("forwardPath", templateUnitService.getForwardPath());
			responseParam.put("htmlContent", htmlContent);
			
		// 获取单元表单
		} else if (dealMethod.equals("getUnitForm")) {
			log.info("获取单元表单");
			
			String unitId = (String) requestParam.get("unitId");
			String columnId = (String) requestParam.get("columnId");
			String instanceId = (String) requestParam.get("instanceId");
			//单元设置的类型
			String templateType = (String) requestParam.get("templateType");
			// 第一次初始化
		//	if (CollectionUtils.isEmpty(templateUnitCategories)) {
				if(templateType.equals("2") || templateType.equals("4")){
					//如果是文章页类别则查询所有的
					templateUnitCategories = templateUnitService.findAllUnitCategories();
				}else{
					//首页和栏目页查询不包括文章的类别
					templateUnitCategories = templateUnitService.findTemplateUnitCategoryNotArticle("t7");
				}				
		//	}
			
	
			
			// 当前模板单元
			TemplateUnit templateUnit = templateUnitService.findTemplateUnitByUnitId(unitId);
			TemplateUnitCategory unitCagtegory = templateUnit.getTemplateUnitCategory();
			String configUrl = "";
			String action = "";
			String categoryId = "";
			if (unitCagtegory != null) {
				categoryId = unitCagtegory.getId();
				action = unitCagtegory.getConfigUrl();
			} else {
				for (TemplateUnitCategory unitCategory : templateUnitCategories) {
					if ("静态单元".equals(unitCategory.getName())) {
						categoryId = unitCategory.getId();
						action = "staticUnit.do";
					}
				}
			}
			
			configUrl = "/" + GlobalConfig.appName + "/" + action
						 + "?dealMethod=findConfig&unit_categoryId=" + categoryId
						 + "&unit_unitId=" + unitId
						 + "&unit_columnId=" + columnId;
				
			List<TemplateUnit> templateUnits = templateUnitService.findTemplateUnitsByInstaceId(instanceId);
			
			responseParam.put("categoryId", categoryId);
			responseParam.put("configUrl", configUrl);
			responseParam.put("templateUnits", templateUnits);
			responseParam.put("templateUnitCategories", templateUnitCategories);
			
		// 预览
		} else if (dealMethod.equals("preview")) {
			log.info("预览");
			String articleId = (String) requestParam.get("articleId");
			String columnId = (String) requestParam.get("columnId");
			String templateInstanceId = (String) requestParam.get("templateInstanceId");
			String path = templateUnitService.getPreviewPage(templateInstanceId, articleId, columnId, siteId);
			responseParam.put("columnId", columnId);
			responseParam.put("forwardPath", path);
			
		// 撤销
		} else if (dealMethod.equals("cancel")) {
			String unitId = (String) requestParam.get("unitId");
			templateUnitService.cancelUnitSet(unitId);
		//	 unitsCache = new Hashtable();
			responseParam.put("forwardPath", templateUnitService.getForwardPath());
			
		// 复制
		} else if (dealMethod.equals("copy")) {
			String unitId = (String)requestParam.get("unitId");
			if (!StringUtil.isEmpty(unitId)) {
				 unitsCache = new Hashtable();
				unitsCache.put("unitId", (String)requestParam.get("unitId"));
			}
			
		// 粘贴
		} else if (dealMethod.equals("paste")) {
			String fromUnitId = (String) unitsCache.get("unitId");
			String toUnitId = (String) requestParam.get("toUnitId");
			templateUnitService.copyUnitSet(fromUnitId, toUnitId);
			
		} else if (dealMethod.equals("addTemplate")) {
			log.info("新增模板实例并选择实例");
			String type = (String) requestParam.get("type");
			// 查找模版类别
			List<TemplateCategory> templateCategoryList = templateUnitService.findTemplateCategory(siteId, sessionID, isUpSystemAdmin, isSiteAdmin);
			String templateStr = "";
			templateStr = templateUnitService.findTemplateCategoryAndTemplate(templateCategoryList, siteId, sessionID, this.isUpSystemAdmin, this.isSiteAdmin);
			responseParam.put("templateStr", templateStr);
			responseParam.put("templateCategoryList", templateCategoryList);
			responseParam.put("type", type);
			
			String isTemplateSeted = (String) requestParam.get("isTemplateSeted");
			responseParam.put("isTemplateSeted", isTemplateSeted);
			String templateInstanceIdValue = (String) requestParam.get("templateInstanceIdValue");
			responseParam.put("templateInstanceIdValue", templateInstanceIdValue);
			String templateInstance = (String) requestParam.get("templateInstance");
			responseParam.put("templateInstance", templateInstance);
			
		}
		
		responseParam.put("nodeId", nodeId);
	}
	
	@Override
	public ResponseEvent validateData(RequestEvent request) throws Exception {
		return null;
	}

	/**
	 * @param templateUnitService the templateUnitService to set
	 */
	public void setTemplateUnitService(TemplateUnitService templateUnitService) {
		this.templateUnitService = templateUnitService;
	}


}
