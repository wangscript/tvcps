/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.templatemanager.web.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.j2ee.cms.biz.templatemanager.domain.Template;
import com.j2ee.cms.biz.templatemanager.web.form.TemplateForm;
import com.j2ee.cms.sys.GlobalConfig;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.util.StringUtil;
import com.j2ee.cms.common.core.web.GeneralAction;
import com.j2ee.cms.common.core.web.event.RequestEvent;
import com.j2ee.cms.common.core.web.event.ResponseEvent;

/**
 * <p>标题: 模板管理中Action</p>
 * <p>描述: 主要是获得表单的一些处理以及向页面输出信息</p>
 * <p>模块: 模板管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-4-9 下午03:53:17
 * @history（历次修订内容、修订人、修订时间等）
 */
public class TemplateAction extends GeneralAction {
	
	private String dealMethod = "";

	/* (non-Javadoc)
	 * @see com.j2ee.cms.common.core.web.GeneralAction#doFormFillment(org.apache.struts.action.ActionForm, com.j2ee.cms.common.core.web.event.ResponseEvent, java.lang.String)
	 */
	@Override
	protected void doFormFillment(ActionForm actionForm, ResponseEvent responseEvent, String userIndr) {
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		TemplateForm form = (TemplateForm) actionForm;
		String nodeId = (String) responseParam.get("nodeId");
		form.setNodeId(nodeId);
		// 查询模板分页
		if(dealMethod.equals("")) {
			Pagination pagination = (Pagination) responseParam.get("pagination");
			form.setPagination(pagination);
			this.setRedirectPage("success", userIndr);
			
		// 导入模板	
		} else  if(dealMethod.equals("import")) {
			String infoMessage = (String) responseParam.get("infoMessage");
			form.setInfoMessage(infoMessage);
			this.setRedirectPage("importSuccess", userIndr);
			
		// 删除模板	
		} else  if(dealMethod.equals("delete")) {
			String infoMessage = (String) responseParam.get("infoMessage");
			form.setInfoMessage(infoMessage);
			this.setRedirectPage("deleteSuccess", userIndr);
			
		// 更新模板
		} else  if(dealMethod.equals("update")) {
			String infoMessage = (String) responseParam.get("infoMessage");
			form.setInfoMessage(infoMessage);
			this.setRedirectPage("importSuccess", userIndr);
			
		// 粘贴模板
		} else  if(dealMethod.equals("paste")) {
			String infoMessage = (String) responseParam.get("infoMessage");
			form.setInfoMessage(infoMessage);
			this.setRedirectPage("pastesuccess", userIndr);
			
		// 模板详细
		} else  if(dealMethod.equals("detail")) {
			Template template = (Template) responseParam.get("template");
			form.setTemplate(template);
		
		// 预览模板	
		} else  if(dealMethod.equals("showModel")) {
			String url = (String) responseParam.get("url");
			form.setUrl(url);
			Pagination pagination = (Pagination) responseParam.get("pagination");
			form.setPagination(pagination);
			this.setRedirectPage("success", userIndr);
			
		//下载模板页
		} else  if(dealMethod.equals("downLoadTemplate")) {
			setRedirectPage("webClient", userIndr);
		
		// 本地下载(下载zip包)
		} else  if(dealMethod.equals("downLoadLocalTemplate")) {
			setRedirectPage("webClient", userIndr);
			//模板编辑
		} else if(dealMethod.equals("editTemplate")){
			String tempContent=responseParam.get("tempContent").toString();
			String tempPath = responseParam.get("temppath").toString();
			String id = responseParam.get("id").toString();
			form.setIds(id);
			form.setTempContent(tempContent);
			form.setTempPath(tempPath);
			this.setRedirectPage("editTemplate", userIndr);
			//保存模板
		}else if(dealMethod.equals("saveTemp")){
			String mess = responseParam.get("mess").toString();
			form.setInfoMessage(mess);
			this.setRedirectPage("importSuccess", userIndr);
		}
	}

	@Override
	protected void performTask(ActionForm actionForm, RequestEvent requestEvent, String userIndr) throws Exception {
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		HttpServletRequest request = (HttpServletRequest) requestParam.get("HttpServletRequest");
		TemplateForm form = (TemplateForm) actionForm;
		this.dealMethod = form.getDealMethod();
		String nodeId = form.getNodeId();
		if(nodeId == null || nodeId.equals("0") || nodeId.equals("") || nodeId.equals("") || nodeId.equals("null")) {
			nodeId = null;
		}
		// 查询模板分页
		if(dealMethod.equals("")) {
			if(this.isUpSystemAdmin) {
				form.setQueryKey("findTemplateByTemplateCategoryIdAndSiteIdPage");
			} else if(this.isSiteAdmin) {
				form.setQueryKey("findTemplateByTemplateCategoryIdAndSiteIdPage");
			} else {
				form.setQueryKey("findTemplateByTemplateCategoryIdAndSiteIdAndCreatorIdPage");
			}
			requestParam.put("pagination", form.getPagination());
			
		// 导入模板	
		} else  if(dealMethod.equals("import")) {
			requestParam.put("form", form);
			requestParam.put("url", request.getRequestURL());
			
		// 更新模板
		} else  if(dealMethod.equals("update")) {
			requestParam.put("form", form);
			requestParam.put("url", request.getRequestURL());
			
		// 删除模板	
		} else  if(dealMethod.equals("delete")) {
			String ids = form.getIds();
			requestParam.put("ids", ids);
		
		// 模板详细	
		} else  if(dealMethod.equals("detail")) {
			String templateId = form.getIds();
			requestParam.put("templateId", templateId);
			
		// 粘贴模板
		} else  if(dealMethod.equals("paste")) {
			// 获得要粘贴到的模板类别id
			String ids = form.getIds();
			// 获得要粘贴的ids
			String pasteIds = form.getPasteIds();
			requestParam.put("url", request.getRequestURL());
			requestParam.put("id", ids);
			requestParam.put("pasteIds", pasteIds);
			
		// 预览模板	
		} else  if(dealMethod.equals("showModel")) {
			// 获得要打开的链接 的模板的id
			String templateId = form.getIds();
			requestParam.put("templateId", templateId);
			form.setQueryKey("findTemplateByTemplateCategoryIdPage");
			requestParam.put("pagination", form.getPagination());
			
		// 	下载模板页
		} else  if(dealMethod.equals("downLoadTemplate")) {
			// 获得要下载的模板的id
			String ids = form.getIds();
			requestParam.put("id", ids);
			requestParam.put("request", request);
		
		// 本地下载(下载zip包)
		} else  if(dealMethod.equals("downLoadLocalTemplate")) {
			// 获得要下载的模板的id
			String ids = form.getIds();
			requestParam.put("id", ids);
			requestParam.put("request", request);
		//编辑模板
		}else if(dealMethod.equals("editTemplate")){
			String tempPath=request.getParameter("tempPath");
			String ids = request.getParameter("ids");
			requestParam.put("tempPath", tempPath);
			requestParam.put("ids", ids);
		//保存模板
		}else if(dealMethod.equals("saveTemp")){
			String tempPath=form.getTempPath();
			String tempContent=form.getTempContent();
			String id = form.getIds();
			requestParam.put("tempPath", tempPath);
			requestParam.put("tempContent", tempContent);
			requestParam.put("ids", id);
		}
		requestParam.put("nodeId", nodeId);
	}

	@Override
	protected void init(String userIndr) throws Exception {
		this.setRedirectPage("detail", userIndr);
	}
}
