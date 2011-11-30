/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.templatemanager.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.j2ee.cms.biz.templatemanager.domain.Template;
import com.j2ee.cms.biz.templatemanager.web.form.TemplateForm;
import com.j2ee.cms.sys.GlobalConfig;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.service.BaseService;
import com.j2ee.cms.common.core.web.WebClientServlet;
import com.j2ee.cms.common.core.web.event.RequestEvent;
import com.j2ee.cms.common.core.web.event.ResponseEvent;

/**
 * <p>标题: 模板业务类</p>
 * <p>描述: 这里是用于响应请求做一些功能的具体处理，在调用templateService的一些方法</p>
 * <p>模块: 模板管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-4-9 下午03:58:53
 * @history（历次修订内容、修订人、修订时间等）
 */
public class TemplateBiz extends BaseService {
	
	/** 注入模板服务类 */
	private TemplateService templateService;

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
		
		// 模板分页
		if(dealMethod.equals("")) {
			log.info("模板分页");
			Pagination pagination = (Pagination) requestParam.get("pagination");
			pagination = templateService.templatePage(pagination, nodeId, siteId, sessionID, this.isUpSystemAdmin, this.isSiteAdmin);
			responseParam.put("pagination", pagination);
			log.info("模板分页成功");
			
		// 删除模板
		} else  if(dealMethod.equals("delete")) {
			log.info("删除模板");
			String ids = (String) requestParam.get("ids");
			String infoMessage = "";
			// 调用删除目录操作方法
			infoMessage = templateService.deleteTemplate(ids, siteId, sessionID);
			responseParam.put("infoMessage", infoMessage);
			log.info(infoMessage);
			
		// 导入模板	
		} else  if(dealMethod.equals("import")) {
			log.info("导入模板");
			TemplateForm form = (TemplateForm) requestParam.get("form");
			//设置访问路径
			StringBuffer url = (StringBuffer) requestParam.get("url");
			// 调用模板导入操作方法
			String infoMessage = templateService.templateImport(form, url, siteId, nodeId, sessionID, templateService);
			responseParam.put("infoMessage", infoMessage);
			log.info(infoMessage);
			
		// 详细模板
		} else  if(dealMethod.equals("detail")) {
			log.info("查询模板详细");
			String templateId = (String) requestParam.get("templateId");
			String categoryName = "";
			Template template = templateService.getTemplateByTemplateId(templateId, siteId, sessionID, categoryName);
			responseParam.put("template", template);
			log.info("查询模板详细成功");
			
		// 修改模板
		} else  if(dealMethod.equals("update")) {
			log.info("更新模板");
			String infoMessage = "";
			TemplateForm form = (TemplateForm) requestParam.get("form");
			infoMessage = templateService.modifyTemplate(form, siteId, sessionID, nodeId);
			responseParam.put("infoMessage",  infoMessage);
			log.info(infoMessage);
		
		// 粘贴模板
		}  else  if(dealMethod.equals("paste")) {
			log.info("粘贴模板");
			// 获得要粘贴到的模板类别id
			String id = (String) requestParam.get("id");
			StringBuffer url = (StringBuffer) requestParam.get("url");
			String infoMessage = "";
			// 获得要粘贴的ids
			String pasteIds = (String) requestParam.get("pasteIds");
			infoMessage = templateService.pasteTemplate(id, pasteIds, siteId, url, sessionID, templateService);
			
			responseParam.put("infoMessage", infoMessage);
			log.info(infoMessage);
			
		// 预览模板	
		} else  if(dealMethod.equals("showModel")) {
			String id = (String) requestParam.get("templateId");
			String categoryName = "模板管理（模板管理）->模板预览";
			Template template = templateService.getTemplateByTemplateId(id, siteId, sessionID, categoryName);
			Pagination pagination = (Pagination) requestParam.get("pagination");
			pagination = templateService.templatePage(pagination, nodeId, siteId, sessionID, this.isUpSystemAdmin, this.isSiteAdmin);
			responseParam.put("pagination", pagination);
			responseParam.put("url", template.getUrl());
		
		// 下载模板页	
		} else  if(dealMethod.equals("downLoadTemplate")) {
			String id = (String) requestParam.get("id");
			String categoryName = "模板管理（模板管理）->页面下载";
			Template template = templateService.getTemplateByTemplateId(id, siteId, sessionID, categoryName);
			String path = GlobalConfig.appRealPath + template.getLocalPath();
			String name = template.getName();
			// 下载框的显示
			HttpServletRequest req = (HttpServletRequest) requestParam.get("request");
			req.setAttribute(WebClientServlet.DEAL_CODE, WebClientServlet.DEAL_CODE_DOWNLOAD);
			req.setAttribute(WebClientServlet.DEAL_CODE_DOWNLOAD_FILEPATH, path);
			req.setAttribute(WebClientServlet.DEAL_CODE_DOWNLOAD_LOCALNAME, name);
			req.setAttribute(WebClientServlet.DEAL_CODE_DOWNLOAD_CHARSET, "utf-8");
		
		// 本地下载(下载zip包)
		} else  if(dealMethod.equals("downLoadLocalTemplate")) {
			String id = (String) requestParam.get("id");
			String categoryName = "模板管理（模板管理）->模板下载";
			Template template = templateService.getTemplateByTemplateId(id, siteId, sessionID, categoryName);
			String name = template.getName();
			// 获得下载的zip包的本地路径
			String path = templateService.findTemplateLocalPath(template);
			HttpServletRequest req = (HttpServletRequest) requestParam.get("request");
			req.setAttribute(WebClientServlet.DEAL_CODE, WebClientServlet.DEAL_CODE_DOWNLOAD);
			req.setAttribute(WebClientServlet.DEAL_CODE_DOWNLOAD_FILEPATH, path);
			req.setAttribute(WebClientServlet.DEAL_CODE_DOWNLOAD_LOCALNAME, name);
			req.setAttribute(WebClientServlet.DEAL_CODE_DOWNLOAD_CHARSET, "utf-8");
			//编辑模板
		}else if(dealMethod.equals("editTemplate")){
			String tempPath = requestParam.get("tempPath").toString();
			String tempContent = templateService.editTempFile(tempPath);
			responseParam.put("tempContent", tempContent);
			responseParam.put("id", requestParam.get("ids"));
			responseParam.put("temppath", tempPath);
			//保存模板
		}else if(dealMethod.equals("saveTemp")){
			String tempPath=requestParam.get("tempPath").toString();
			String tempContent=requestParam.get("tempContent").toString();
			String id = requestParam.get("ids").toString();
			
			String mess = templateService.saveTempFile(tempPath, tempContent,id,sessionID,siteId);
			responseParam.put("mess", mess);			
		}
		responseParam.put("nodeId", nodeId);
	}

	@Override
	public ResponseEvent validateData(RequestEvent request) throws Exception {
		return null;
	}

	/**
	 * @return the templateService
	 */
	public TemplateService getTemplateService() {
		return templateService;
	}

	/**
	 * @param templateService the templateService to set
	 */
	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}

}
