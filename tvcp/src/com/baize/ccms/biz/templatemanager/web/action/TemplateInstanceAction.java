/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.templatemanager.web.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.j2ee.cms.biz.templatemanager.domain.TemplateInstance;
import com.j2ee.cms.biz.templatemanager.web.form.TemplateInstanceForm;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.web.GeneralAction;
import com.j2ee.cms.common.core.web.event.RequestEvent;
import com.j2ee.cms.common.core.web.event.ResponseEvent;

/**
 * <p>标题: 模板实例action</p>
 * <p>描述: 模板实例获得页面的数据和跳转页面</p>
 * <p>模块: 模板管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-4-9 下午04:13:23
 * @history（历次修订内容、修订人、修订时间等）
 */
public class TemplateInstanceAction extends GeneralAction {
	
	private String dealMethod = "";

	@Override
	protected void doFormFillment(ActionForm actionForm, ResponseEvent responseEvent, String userIndr) {
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		TemplateInstanceForm form = (TemplateInstanceForm) actionForm;
		String templateId = (String) responseParam.get("templateId");
		form.setTemplateId(templateId);
		String nodeId = (String) responseParam.get("nodeId");
		form.setNodeId(nodeId);
		
		// 查找模板实例
		if (dealMethod.equals("")) {
			Pagination pagination = (Pagination) responseParam.get("pagination");
			form.setPagination(pagination);
			List list = pagination.getData();
	        for(int i = 0 ; i < list.size(); i++) {
	        	Object[] object = (Object[]) list.get(i);
	        	// 获得模板实例名称
	        	String name = (String) object[1];
	        	//获取模板实例地址
	        	String url = (String)object[4];
	        	object[1] = "<a href=\"" + url + "\" target=\"_blank\">" + name + "</a>";
	        }
	        String idsAndUsedNum = (String) responseParam.get("idsAndUsedNum");
	        form.setIdAndUsedNum(idsAndUsedNum);
			this.setRedirectPage("success", userIndr);
		
		// 查找要更名的模板实例	 
		} else if(dealMethod.equals("templateInstance")) {
			TemplateInstance templateInstance = (TemplateInstance) responseParam.get("templateInstance");
			form.setTemplateInstance(templateInstance);
			this.setRedirectPage("detail", userIndr);
			
		// 添加模板实例	
		} else if(dealMethod.equals("add")) {
			String infoMessage = "";
			infoMessage = (String) responseParam.get("infoMessage");
			form.setInfoMessage(infoMessage);
			String templateSet = (String) responseParam.get("templateSet").toString();
			if(templateSet.equals("1")) {
				TemplateInstance templateInstance = (TemplateInstance) responseParam.get("templateInstance");
				form.setTemplateInstanceId(templateInstance.getId());
				this.setRedirectPage("addInstanceSuccess", userIndr);
			} else {
				this.setRedirectPage("detail", userIndr);
			}
		
		// 删除模板实例
		}else if(dealMethod.equals("delete")) {
			String infoMessage = "";
			infoMessage = (String) responseParam.get("infoMessage");
			form.setInfoMessage(infoMessage);
			this.setRedirectPage("deleteSuccess", userIndr);
		
		// 修改模板实例名称
		} else if(dealMethod.equals("modify")) {
			String infoMessage = "";
			infoMessage = (String) responseParam.get("infoMessage");
			form.setInfoMessage(infoMessage);
			this.setRedirectPage("detail", userIndr);
		
		// 查找绑定的栏目	
		} else if(dealMethod.equals("findBind")) {
			String bindedIds = (String) responseParam.get("bindedIds");
			String bind = (String) responseParam.get("bind");
			String templateInstanceId = (String) responseParam.get("templateInstanceId");
			form.setBind(bind);
			form.setTemplateInstanceId(templateInstanceId);
			form.setBindedIds(bindedIds);
			this.setRedirectPage("bind", userIndr);
		
		// 绑定栏目或者文章	
		} else if(dealMethod.equals("bind")) {
			form.setInfoMessage("绑定成功");
			String templateInstanceId = (String) responseParam.get("templateInstanceId");
			form.setTemplateInstanceId(templateInstanceId);
			this.setRedirectPage("bindSuccess", userIndr);
		
		// 取消绑定	
		} else if(dealMethod.equals("cancelBind")) {
			form.setInfoMessage("取消绑定成功");
			String templateInstanceId = (String) responseParam.get("templateInstanceId");
			form.setTemplateInstanceId(templateInstanceId);
			this.setRedirectPage("bindSuccess", userIndr);
		}
	}

	@Override
	protected void performTask(ActionForm actionForm, RequestEvent requestEvent, String userIndr) throws Exception {
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		HttpServletRequest request = (HttpServletRequest) requestParam.get("HttpServletRequest");
		TemplateInstanceForm form = (TemplateInstanceForm) actionForm;
		this.dealMethod = form.getDealMethod();
		String templateId = form.getTemplateId();
		String nodeId = form.getNodeId();
		if(nodeId == null || nodeId.equals("null") || nodeId.equals("") || nodeId.equals("0")) {
			nodeId = null;
		}
		// 查找模板实例
		if (dealMethod.equals("")) {
			if(this.isUpSystemAdmin) {
				form.setQueryKey("findTemplateInstanceByTemplateIdAndSiteIdPage");
			} else if(this.isSiteAdmin) {
				form.setQueryKey("findTemplateInstanceByTemplateIdAndSiteIdPage");
			} else {
				form.setQueryKey("findTemplateInstanceByTemplateIdAndSiteIdAndCreatorIdPage");
			}
			requestParam.put("pagination", form.getPagination());
		
		// 查找要更名的模板实例	 	
		} else if(dealMethod.equals("templateInstance")) {
			String templateInstanceId = form.getTemplateInstanceId();
			requestParam.put("templateInstanceId", templateInstanceId);
		
		// 添加模板实例	
		} else if(dealMethod.equals("add")){
			TemplateInstance templateInstance = form.getTemplateInstance();
			String templateSet = form.getTemplateSet();
			if(templateSet.equals("1")) {
				String name = form.getTemplateInstanceName();
				templateInstance.setName(name);
				templateId = form.getTemplateId();
				templateInstance.setUsedNum(0);
			}
			requestParam.put("templateSet", templateSet);
			requestParam.put("url", request.getRequestURL());
			requestParam.put("templateInstance", templateInstance);
		
		// 删除模板实例	
		}else if(dealMethod.equals("delete")) {
			String ids = form.getIds();
			requestParam.put("ids", ids);
		
		// 修改模板实例名称	
		} else if(dealMethod.equals("modify")) {
			requestParam.put("templateInstance", form.getTemplateInstance());
			
		// 查找绑定的栏目	
		} else if(dealMethod.equals("findBind")) {
			requestParam.put("bind", form.getBind());
			requestParam.put("templateInstanceId", form.getTemplateInstanceId());
			
		// 绑定栏目或者文章	
		} else if(dealMethod.equals("bind")) {
			requestParam.put("canceledIds", form.getCanceledIds());
			requestParam.put("bindedIds", form.getBindedIds());
			requestParam.put("bind", form.getBind());
			requestParam.put("templateInstanceId", form.getTemplateInstanceId());
			
		// 取消绑定	
		} else if(dealMethod.equals("cancelBind")) {
			requestParam.put("bindedIds", form.getBindedIds());
			requestParam.put("bind", form.getBind());
			requestParam.put("templateInstanceId", form.getTemplateInstanceId());
		}
 		requestParam.put("nodeId", nodeId);
		requestParam.put("templateId", templateId);
	}

	@Override
	protected void init(String userIndr) throws Exception {
	}
}
