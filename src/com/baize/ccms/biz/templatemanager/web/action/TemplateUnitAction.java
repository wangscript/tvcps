/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.biz.templatemanager.web.action;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.baize.ccms.biz.columnmanager.domain.Column;
import com.baize.ccms.biz.sitemanager.domain.Site;
import com.baize.ccms.biz.templatemanager.domain.TemplateCategory;
import com.baize.ccms.biz.templatemanager.domain.TemplateUnit;
import com.baize.ccms.biz.templatemanager.domain.TemplateUnitCategory;
import com.baize.ccms.biz.templatemanager.web.form.TemplateUnitForm;
import com.baize.common.core.web.GeneralAction;
import com.baize.common.core.web.event.RequestEvent;
import com.baize.common.core.web.event.ResponseEvent;

/**
 * <p>标题: 模板单元Action</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 模板管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-5-13 上午09:39:33
 * @history（历次修订内容、修订人、修订时间等）
 */
public class TemplateUnitAction extends GeneralAction {
	
	private String dealMethod = "";

	@Override
	protected void doFormFillment(ActionForm actionForm, ResponseEvent responseEvent, String userIndr) {
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		TemplateUnitForm form = (TemplateUnitForm) actionForm;
		String nodeId = (String) responseParam.get("nodeId");
		form.setNodeId(nodeId);
		form.setInfoMessage("");
		
		// 设置模板 
		if(dealMethod.equals("")) {
			String columnTemplateSet = (String) responseParam.get("columnTemplateSet");
			if(nodeId == null) {
				Site site = (Site) responseParam.get("site");
				form.setColumn(null);
				form.setSite(site);
			} else {
				Column column = (Column) responseParam.get("column");
				form.setSite(null);
				form.setColumn(column);
			}
			List<Column> columnList = (List<Column>) responseParam.get("columnList");
			if(columnList != null && columnList.size() > 0) {
				form.setColumnList(columnList);
			} else {
				form.setColumnList(null);
			}
			form.setColumnTemplateSet(columnTemplateSet);
			this.setRedirectPage("listTemplate", userIndr);
		
		// 选择模板	
		} else if(dealMethod.equals("chooseTemplate")) {
			List<TemplateCategory> templateCategoryList = (List<TemplateCategory>) responseParam.get("templateCategoryList");
			String templateInstanceStr = (String) responseParam.get("templateInstanceStr");
			String templateInstance = (String) responseParam.get("templateInstance");
			String templateInstanceIdValue = (String) responseParam.get("templateInstanceIdValue");
			String isTemplateSeted = (String) responseParam.get("isTemplateSeted");
			form.setTemplateInstanceIdValue(templateInstanceIdValue);
			form.setTemplateInstance(templateInstance);
			form.setTemplateCategoryList(templateCategoryList);
			form.setTemplateInstanceStr(templateInstanceStr);
			form.setIsTemplateSeted(isTemplateSeted);
			this.setRedirectPage("chooseTemplate", userIndr);
			
		// 重新选择模板实例	
		} else if(dealMethod.equals("update")) {
			form.setInfoMessage("修改成功");
			String isTemplateSeted = (String) responseParam.get("isTemplateSeted");
			String instanceName = (String) responseParam.get("instanceName");
			String templateInstance = (String) responseParam.get("templateInstance");
			String templateInstanceIdValue = (String) responseParam.get("templateInstanceIdValue");
			String templateInstanceId = (String) responseParam.get("templateInstanceId");
			form.setTemplateInstanceIdValue(templateInstanceIdValue);
			form.setIds(templateInstanceId);
			form.setTemplateInstance(templateInstance);
			form.setInstanceName(instanceName);
			form.setIsTemplateSeted(isTemplateSeted);
			
			String templateSet = (String) responseParam.get("templateSet");
			if(templateSet.equals("1")) {
				this.setRedirectPage("addInstanceSuccess", userIndr);
			} else {
				this.setRedirectPage("chooseTemplate", userIndr);
			}
		
		// 撤销模板选择	
		} else if(dealMethod.equals("cancelTemplate")) {
			if(nodeId == null) {
				Site site = (Site) responseParam.get("site");
				form.setColumn(null);
				form.setSite(site);
			} else {
				Column column = (Column) responseParam.get("column");
				form.setSite(null);
				form.setColumn(column);
			}
			form.setInfoMessage("撤销成功");
			this.setRedirectPage("cancelTemplate", userIndr);
		
		// 模板设置	
		} else if (dealMethod.equals("templateSet")) {
			String htmlContent = (String) responseParam.get("htmlContent");
			form.setHtmlContent(htmlContent);
			String columnId = (String) responseParam.get("columnId");
			form.setColumnId(columnId);
			this.setForwardPath((String) responseParam.get("forwardPath"));
			this.setRedirectPage("templateSet", userIndr);
			
		// 预览
		} else if (dealMethod.equals("preview")) {
				
			String columnId = (String) responseParam.get("columnId");
			form.setColumnId(columnId);
			this.setForwardPath((String) responseParam.get("forwardPath"));
			this.setRedirectPage("templateSet", userIndr);
			
		
		// 获得单元表单 	
		} else if (dealMethod.equals("getUnitForm")) {
			// 当前实例拥有的所有单元
			List<TemplateUnit> templateUnits = (List<TemplateUnit>) responseParam.get("templateUnits");
			// 所有单元类别
			List<TemplateUnitCategory> categories = (List<TemplateUnitCategory>) responseParam.get("templateUnitCategories");
			String configUrl = (String) responseParam.get("configUrl");
			TemplateUnitCategory unitCategory = (TemplateUnitCategory) responseParam.get("unitCategory");
			String categoryId = (String) responseParam.get("categoryId");			
			form.setCategoryId(categoryId);
			form.setTemplateUnits(templateUnits);
			form.setTemplateUnitCategories(categories);
			form.setUnitCategory(unitCategory);
			form.setConfigUrl(configUrl);
			this.setRedirectPage("unitSet", userIndr);
		// 撤销
		} else if (dealMethod.equals("cancel")) {
			this.setForwardPath("/templateUnit.do?dealMethod=templateSet");
//			this.setRedirectPage("templateSet", userIndr);
			
		// 复制
		} else if (dealMethod.equals("copy")) {
			
			form.setInfoMessage("copySuccess");
		//	this.setRedirectPage("ajaxMsg", userIndr);
			this.setForwardPath("/templateUnit.do?dealMethod=templateSet");
			
		// 粘贴 
		} else if (dealMethod.equals("paste")) {
			
			this.setForwardPath("/templateUnit.do?dealMethod=templateSet");
		
		// 新增模版实例
		} else if(dealMethod.equals("addTemplate")) {
			String type = (String) responseParam.get("type");
			form.setTemplateType(type);
			List<TemplateCategory> templateCategoryList = (List<TemplateCategory>) responseParam.get("templateCategoryList");
			String templateStr = (String) responseParam.get("templateStr");
			form.setTemplateCategoryList(templateCategoryList);
			form.setTemplateStr(templateStr);
			String templateInstance = (String) responseParam.get("templateInstance");
			String templateInstanceIdValue = (String) responseParam.get("templateInstanceIdValue");
			String isTemplateSeted = (String) responseParam.get("isTemplateSeted");
			form.setTemplateInstanceIdValue(templateInstanceIdValue);
			form.setTemplateInstance(templateInstance);
			form.setIsTemplateSeted(isTemplateSeted);
			this.setRedirectPage("addTemplate", userIndr);
		}
	}

	@Override
	protected void performTask(ActionForm actionForm, RequestEvent requestEvent, String userIndr) throws Exception {
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		HttpServletRequest request = (HttpServletRequest) requestParam.get("HttpServletRequest");
		TemplateUnitForm form = (TemplateUnitForm) actionForm;
		this.dealMethod = form.getDealMethod();
		String nodeId = form.getNodeId();
		
		if(nodeId == null || nodeId.equals("0") || nodeId.equals("") || nodeId.equals("null")) {
			nodeId = null;
		}	
		String templateType = form.getTemplateType();
		// 设置模板 
		if(dealMethod.equals("")) {
			
		// 选择模板		
		} else if(dealMethod.equals("chooseTemplate")) {
			String isTemplateSeted = form.getIsTemplateSeted();
			String templateInstance = form.getTemplateInstance();
			String templateInstanceIdValue = form.getTemplateInstanceIdValue();
			requestParam.put("templateInstanceIdValue", templateInstanceIdValue);
			requestParam.put("templateInstance", templateInstance);
			requestParam.put("isTemplateSeted", isTemplateSeted);
			
		// 重新选择模板实例	
		} else if(dealMethod.equals("update")) {
			// 是否是从模版设置页面的添加出跳过来的
			String templateSet = form.getTemplateSet();
			if(templateSet == null) {
				templateSet = "0";
			}
			requestParam.put("templateSet", templateSet);
			String isTemplateSeted = form.getIsTemplateSeted();
			String templateInstanceId = form.getIds();	
			String templateInstance = form.getTemplateInstance();
			String templateInstanceIdValue = form.getTemplateInstanceIdValue();
			requestParam.put("templateInstanceIdValue", templateInstanceIdValue);
			requestParam.put("templateInstance", templateInstance);
			requestParam.put("templateType", templateType);
			requestParam.put("templateInstanceId", templateInstanceId);
			requestParam.put("isTemplateSeted", isTemplateSeted);
			
		// 取消模板选择	
		} else if(dealMethod.equals("cancelTemplate")) {
			String templateInstanceId = form.getIds();
			requestParam.put("templateInstanceId", templateInstanceId);
			requestParam.put("templateType", templateType);
		
		// 模板设置
		} else if (dealMethod.equals("templateSet")) {
			String templateId = form.getIds();
			String columnId = form.getColumnId();
			requestParam.put("templateId", templateId);
			requestParam.put("columnId", columnId);
			requestParam.put("templateType", templateType);
			
		// 获取单元表单
		} else if (dealMethod.equals("getUnitForm")) {
			String unitId = form.getUnitId();
			String instanceId = form.getInstanceId();
			String columnId = form.getColumnId();
			
			requestParam.put("unitId", unitId);
			requestParam.put("columnId", columnId);
			requestParam.put("instanceId", instanceId);
			requestParam.put("templateType", templateType);
		
		// 预览
		} else if (dealMethod.equals("preview")) {
			String instanceId = form.getInstanceId();
			String columnId = form.getColumnId();
			String articleId = form.getArticleId();
			
			requestParam.put("articleId", articleId);
			requestParam.put("columnId", columnId);
			requestParam.put("templateInstanceId", instanceId);
			
		// 撤销
		} else if (dealMethod.equals("cancel")) {
			String unitId = form.getUnitId();
			requestParam.put("unitId", unitId);
			
		// 复制
		} else if (dealMethod.equals("copy")) {
			String unitId = form.getUnitId();
			requestParam.put("unitId", unitId);
			//HttpServletRequest request = (HttpServletRequest) requestParam.get("HttpServletRequest");
			// 放入需要复制的单元ID
			//request.getSession().setAttribute("COPY_UNIT_ID", unitId);
			
		// 粘贴
		} else if (dealMethod.equals("paste")) {
			String toUnitId = form.getUnitId();
			// 获取需要复制的单元ID
			//String fromUnitId = (String) request.getSession().getAttribute("COPY_UNIT_ID");
			requestParam.put("toUnitId",toUnitId);
			//requestParam.put("fromUnitId", fromUnitId);
			
		// 新增模版实例
		} else if(dealMethod.equals("addTemplate")) {
			// 要勾选的红勾地方id
			String isTemplateSeted = form.getIsTemplateSeted();
			// 实例id要赋予的地反
			String templateInstance = form.getTemplateInstance();
			// 
			String templateInstanceIdValue = form.getTemplateInstanceIdValue();
			// 要设置的是具体哪个地方（类型）
			String type = form.getTemplateType();
			requestParam.put("type", type);
			requestParam.put("templateInstanceIdValue", templateInstanceIdValue);
			requestParam.put("templateInstance", templateInstance);
			requestParam.put("isTemplateSeted", isTemplateSeted);
		}
		
		requestParam.put("nodeId", nodeId);
	}

	@Override
	protected void init(String userIndr) throws Exception {
	}

}
