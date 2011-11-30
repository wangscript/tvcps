/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.templatemanager.web.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.j2ee.cms.biz.templatemanager.domain.TemplateCategory;
import com.j2ee.cms.biz.templatemanager.web.form.TemplateCategoryForm;
import com.j2ee.cms.sys.GlobalConfig;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.util.StringUtil;
import com.j2ee.cms.common.core.web.GeneralAction;
import com.j2ee.cms.common.core.web.event.RequestEvent;
import com.j2ee.cms.common.core.web.event.ResponseEvent;

/**
 * <p>标题: 模板类别的Action</p>
 * <p>描述: 主要是获得表单的一些处理以及向页面输出信息</p>
 * <p>模块: 模板管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 郑荣华
 * @version 1.0
 * @since 2009-4-27 下午06:53:00
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class TemplateCategoryAction extends GeneralAction {
	
	private String dealMethod = "";

	
	@Override
	protected void doFormFillment(ActionForm actionForm, ResponseEvent responseEvent, String userIndr) {
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		TemplateCategoryForm form = (TemplateCategoryForm) actionForm;
		String nodeId = (String) responseParam.get("nodeId");
		form.setNodeId(nodeId);
		
		// 模板分页
		if(dealMethod.equals("")) {
			Pagination pagination = (Pagination) responseParam.get("pagination");
			form.setPagination(pagination);
			this.setRedirectPage("success", userIndr);
		
		// 添加模板类别
		} else  if(dealMethod.equals("add")) {
			String infoMessage = "";
			infoMessage = (String) responseParam.get("infoMessage");
			form.setInfoMessage(infoMessage);
			this.setRedirectPage("detail", userIndr);
			
		// 删除模板类别
		} else  if(dealMethod.equals("delete")) {
			String infoMessage = (String) responseParam.get("infoMessage");
			form.setInfoMessage(infoMessage);
			this.setRedirectPage("deleteSuccess", userIndr);
			
		// 详细模板类别
		} else  if(dealMethod.equals("detail")) {
			TemplateCategory templateCategory = (TemplateCategory) responseParam.get("templateCategory");
			form.setTemplateCategory(templateCategory);
			this.setRedirectPage("detail", userIndr);
			
		// 修改模板类别
		} else  if(dealMethod.equals("modify")) {
			String infoMessage = "";
			infoMessage = (String) responseParam.get("infoMessage"); 
			form.setInfoMessage(infoMessage);	
			this.setRedirectPage("detail", userIndr);
			
		// 加载树
		} else  if(dealMethod.equals("getTree")) {
			List treeList = (List) responseParam.get("treeList");
			form.setJson_list(treeList);
			this.setRedirectPage("tree", userIndr);
		}

	}

	@Override
	protected void performTask(ActionForm actionForm, RequestEvent requestEvent, String userIndr) throws Exception {
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		HttpServletRequest request = (HttpServletRequest) requestParam.get("HttpServletRequest");
		
		TemplateCategoryForm form = (TemplateCategoryForm) actionForm;
		this.dealMethod = form.getDealMethod();
		String nodeId = form.getNodeId();
		if(nodeId == null || nodeId.equals("0") || nodeId.equals("") || nodeId.equals("null")) {
			nodeId = null;
		}
		// 模板分页
		if(dealMethod.equals("")) {
			if(isUpSystemAdmin == true) {
				form.setQueryKey("findTemplateCategoryBySiteIdPage");
			} else if(isSiteAdmin == true) {
				form.setQueryKey("findTemplateCategoryBySiteIdPage");
			} else {
				form.setQueryKey("findTemplateCategoryBySiteIdAndCreatorIdPage");
			}
			requestParam.put("pagination", form.getPagination());

		// 添加模板类别
		} else  if(dealMethod.equals("add")) {
			requestParam.put("templateCategory", form.getTemplateCategory());
			
		// 删除模板类别
		} else  if(dealMethod.equals("delete")) {
			String ids = form.getIds();
			requestParam.put("ids", ids);
			
		// 详细模板类别
		} else  if(dealMethod.equals("detail")) {
			String id = form.getIds();
			requestParam.put("id", id);
			
		// 修改模板类别
		} else  if(dealMethod.equals("modify")) {
			TemplateCategory templateCategory = form.getTemplateCategory();
			String siteId = form.getSiteid();
			String creatorId = form.getCreatorid();
			requestParam.put("siteid", siteId);
			requestParam.put("creatorid", creatorId);
			requestParam.put("templateCategory", templateCategory);
			
		// 加载树
		} else  if(dealMethod.equals("getTree")) {
			String treeid = form.getTreeId();
			if(treeid == null || treeid.equals("0") || treeid.equals("") || treeid.equals("null")) {
				treeid = null;
			}
			requestParam.put("treeid", treeid);
		}
		requestParam.put("nodeId", nodeId);

	}
	
	@Override
	protected void init(String arg0) throws Exception {
	//	this.setRedirectPage("success", userIndr);
	}

}
