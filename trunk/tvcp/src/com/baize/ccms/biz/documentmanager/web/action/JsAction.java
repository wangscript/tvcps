/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.documentmanager.web.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.baize.ccms.biz.documentmanager.domain.Document;
import com.baize.ccms.biz.documentmanager.domain.DocumentCategory;
import com.baize.ccms.biz.documentmanager.web.form.DocumentForm;
import com.baize.ccms.sys.GlobalConfig;
import com.baize.common.core.dao.Pagination;
import com.baize.common.core.util.StringUtil;
import com.baize.common.core.web.GeneralAction;
import com.baize.common.core.web.event.RequestEvent;
import com.baize.common.core.web.event.ResponseEvent;

/**
 * <p>标题: js脚本action</p>
 * <p>描述: js脚本的action用于处理和接受附件信息</p>
 * <p>模块: 文档管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 薛磊
 * @version 1.0
 * @since 2009-3-25 下午04:23:57
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class JsAction extends GeneralAction {

	private String dealMethod = "";
	
	@Override
	protected void doFormFillment(ActionForm actionForm, ResponseEvent responseEvent, String userIndr) {
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
	
		DocumentForm form = (DocumentForm) actionForm;
		
		//获取节点
		String nodeid = (String) responseParam.get("nodeid");
		form.setNodeId(nodeid);
		String nodeNameStr = (String) responseParam.get("nodeNameStr");
		form.setNodeNameStr(nodeNameStr);
		
		//显示js脚本并分页成功
		if (dealMethod.equals("")) {
			Pagination pagination = (Pagination) responseParam.get("pagination");
			form.setPagination(pagination);
			List list = pagination.getData();
	        for(int i = 0 ; i < list.size(); i++) {
	        	Object[] object = (Object[]) list.get(i);
	        	// 获得附件名称
	        	String name = (String) object[1];
	        	//获取附件地址
	        	String url = (String)object[6];
	        	object[1] = "<a href=\"" + url + "\" target=\"_blank\">" + name + "</a>";
	        }
			this.setRedirectPage("jsSuccess", userIndr);
		
		//上传附件成功
		}else if (dealMethod.equals("upload")) {			
			int isScaleImage = (Integer) responseParam.get("isScaleImage");
			form.setIsScaleImage(isScaleImage);
			String infoMessage = "";
			infoMessage = (String) responseParam.get("infoMessage");
			form.setInfoMessage(infoMessage);
			this.setRedirectPage("uploadJsMsg", userIndr);
		
		//删除附件成功
		}else if(dealMethod.equals("delete")) {
			String infoMessage = "";
			infoMessage = (String) responseParam.get("infoMessage");
			form.setInfoMessage(infoMessage);
			this.setRedirectPage("deleteJsSuccess", userIndr);
		
		//附件高级编辑器使用
		} else if(dealMethod.equals("uploadScriptList")) {
			String categoryid = (String) responseParam.get("categoryid");
			form.setCategoryId(categoryid);
			List<DocumentCategory> catgoryList = (List) responseParam.get("categoryList");
			Pagination pagination = (Pagination) responseParam.get("pagination");
			form.setPagination(pagination);
			form.setList(catgoryList);
			List<Document> list = (List) responseParam.get("list");
			String attachmentIds = "";
			String attachmentNames = "";
			String attachmentUrl = "";
			// 将附件查处的id,name,url用":::"区分开来，传到页面
			for(int i = 0; i < list.size(); i++) {
				Document document = list.get(i);
				if(StringUtil.isEmpty(attachmentIds)) {
					attachmentIds += String.valueOf(document.getId()); 
					attachmentNames += document.getName();
					attachmentUrl += document.getLocalPath(); 
				} else {
					attachmentIds += ":::" + String.valueOf(document.getId()); 
					attachmentNames += ":::" + document.getName();
					attachmentUrl += ":::" + document.getLocalPath(); 
				}
			}
			form.setAttachmentIds(attachmentIds);
			form.setAttachmentNames(attachmentNames);
			form.setAttachmentUrl(attachmentUrl);
			this.setRedirectPage("uploadScriptList", userIndr);
		
		// 在缩略图中删除附件	
		} else if(dealMethod.equals("deleteAttachment")) {
			String infoMessage = "";
			infoMessage = (String) responseParam.get("infoMessage");
			form.setInfoMessage(infoMessage);
			this.setRedirectPage("deleteScaleImageSuccess", userIndr);
			
		// 高级编辑其中上传脚本
		} else if(dealMethod.equals("uploadInEditor")) {
			String infoMessage = "";
			infoMessage = (String) responseParam.get("infoMessage");
			form.setInfoMessage(infoMessage);
			form.setCategoryId(nodeid);
			this.setRedirectPage("uploadInEditorSuccess", userIndr);
			
		// 高级编辑中分页显示脚本
		} else if(dealMethod.equals("findJsPaginationInEditor")) {
			String categoryid = (String) responseParam.get("categoryid");
			form.setCategoryId(categoryid);
			List<DocumentCategory> catgoryList = (List) responseParam.get("categoryList");
			form.setPagination((Pagination) responseParam.get("pagination"));
			form.setNodeId((String) responseParam.get("nodeId"));
			form.setList(catgoryList);
			this.setRedirectPage("findJsPaginationInEditorSuccess", userIndr);
			
		// 高级编辑中删除脚本
		} else if(dealMethod.equals("deleteInEditor")) {
			String infoMessage = "";
			infoMessage = (String) responseParam.get("infoMessage");
			form.setInfoMessage(infoMessage);
			this.setRedirectPage("deleteInEditorSuccess", userIndr);
			//编辑脚本
		} else if(dealMethod.equals("editjs")){
			String jsContent=responseParam.get("jsContent").toString();
			String jsPath = responseParam.get("jspath").toString();
			form.setJsContent(jsContent);
			form.setJsPath(jsPath);
			this.setRedirectPage("editjs", userIndr);
			//保存脚本
		}else if(dealMethod.equals("savejs")){
			String mess  = responseParam.get("mess").toString();
			form.setInfoMessage(mess);
			this.setRedirectPage("uploadJsMsg", userIndr);
		}
			
	}

	@Override
	protected void performTask(ActionForm actionForm, RequestEvent requestEvent, String userIndr) throws Exception {
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		HttpServletRequest request = (HttpServletRequest) requestParam.get("HttpServletRequest");
		DocumentForm form = (DocumentForm)actionForm;
		this.dealMethod = form.getDealMethod();
		//获得节点
		String nodeid = form.getNodeId();
		
		//显示js脚本
        if(dealMethod.equals("")) {
        	log.info("js脚本分页信息");
			form.setQueryKey("findJsByCategoryIdPage");
			requestParam.put("pagination", form.getPagination());
			
		//上传js脚本
		}else	if(dealMethod.equals("upload")) {
			log.info("上传js脚本");
			// 是否是从高级编辑器页面上传
			int isScaleImage = form.getIsScaleImage();
			//设置访问路径
			StringBuffer url = request.getRequestURL();
			requestParam.put("isScaleImage", isScaleImage);
			requestParam.put("url", url);
			requestParam.put("form", form);
		    
		//删除js脚本
		}else	if(dealMethod.equals("delete")) {
			log.info("删除js脚本");
			String ids=form.getIds();
			requestParam.put("ids", ids);
	
		// 附件列表	
		} else if(dealMethod.equals("uploadScriptList")) {
			form.setQueryKey("findJsByCategoryIdPage");
			requestParam.put("pagination", form.getPagination());
		// 在缩略图中删除脚本
		} else if(dealMethod.equals("deleteAttachment")) {
			String ids = form.getIds();
			requestParam.put("ids", ids);
			
		// 高级编辑中上传脚本
		} else if(dealMethod.equals("uploadInEditor")) {
			//设置访问路径
			StringBuffer url = request.getRequestURL();
			requestParam.put("url", url);
			requestParam.put("form", form);
			
		// 高级编辑中分页显示脚本
		} else if(dealMethod.equals("findJsPaginationInEditor")) {
			form.setQueryKey("findJsByCategoryIdPage");
			requestParam.put("pathName", request.getContextPath());
			requestParam.put("pagination", form.getPagination());
			
		// 高级编辑中删除脚本
		} else if(dealMethod.equals("deleteInEditor")) {
			String ids=form.getIds();
			requestParam.put("ids", ids);
		}else if(dealMethod.equals("editjs")){
			String jspath=request.getParameter("jspath");
			requestParam.put("jspath", jspath);
		}else if(dealMethod.equals("savejs")){
			String jsPath=form.getJsPath();
			String jsContent=form.getJsContent();
			requestParam.put("jsPath", jsPath);
			requestParam.put("jsContent", jsContent);
		}
        requestParam.put("nodeid", nodeid);
	}
	
	@Override
	protected void init(String userIndr) throws Exception {
		this.setRedirectPage("success", userIndr);
	}
}
