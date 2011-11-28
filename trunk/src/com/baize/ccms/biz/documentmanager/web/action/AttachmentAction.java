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
 * <p>标题: 附件action</p>
 * <p>描述: 附件的action用于处理和接受附件信息</p>
 * <p>模块: 文档管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 郑荣华
 * @version 1.0
 * @since 2009-3-25 下午04:23:57
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class AttachmentAction extends GeneralAction {

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
		
		//显示附件并分页成功
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
	        	object[1] = "<a href=\"" + url + "\" target=\"_blank\" onclick=\"g()\">" + name + "</a>";
	        }
			this.setRedirectPage("attachmentSuccess", userIndr);
		
		//上传附件成功
		}else if (dealMethod.equals("upload")) {			
			int isScaleImage = (Integer) responseParam.get("isScaleImage");
			form.setIsScaleImage(isScaleImage);
			String infoMessage = "";
			infoMessage = (String) responseParam.get("infoMessage");
			form.setInfoMessage(infoMessage);
			this.setRedirectPage("uploadAttMsg", userIndr);
		
		//删除附件成功
		}else if(dealMethod.equals("delete")) {
			String infoMessage = "";
			infoMessage = (String) responseParam.get("infoMessage");
			form.setInfoMessage(infoMessage);
			this.setRedirectPage("deleteAttachmentSuccess", userIndr);
		
		//附件高级编辑器使用
		} else if(dealMethod.equals("uploadAttachmentList")) {
			String categoryid = (String) responseParam.get("categoryid");
			form.setCategoryId(categoryid);
			List<DocumentCategory> catgoryList = (List) responseParam.get("categoryList");
			form.setPagination((Pagination) responseParam.get("pagination"));
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
			this.setRedirectPage("uploadAttachmentList", userIndr);
		
		// 在缩略图中删除附件	
		} else if(dealMethod.equals("deleteAttachment")) {
			String infoMessage = "";
			infoMessage = (String) responseParam.get("infoMessage");
			form.setInfoMessage(infoMessage);
			this.setRedirectPage("deleteScaleImageSuccess", userIndr);
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
		
		//显示附件
        if(dealMethod.equals("")) {
			form.setQueryKey("findAttachmentByCategoryIdPage");
			requestParam.put("pagination", form.getPagination());
			
		//上传附件
		}else	if(dealMethod.equals("upload")) {
			// 是否是从高级编辑器页面上传
			int isScaleImage = form.getIsScaleImage();
			//设置访问路径
			StringBuffer url = request.getRequestURL();
			requestParam.put("isScaleImage", isScaleImage);
			requestParam.put("url", url);
			requestParam.put("form", form);
		    
		//删除附件
		}else	if(dealMethod.equals("delete")) {
			String ids=form.getIds();
			requestParam.put("ids", ids);
	
		// 附件列表	
		} else if(dealMethod.equals("uploadAttachmentList")) {
			form.setQueryKey("findAttachmentByCategoryIdPage");
			requestParam.put("pagination", form.getPagination());
		
		// 在缩略图中删除附件	
		} else if(dealMethod.equals("deleteAttachment")) {
			String ids = form.getIds();
			requestParam.put("ids", ids);
		}
        requestParam.put("nodeid", nodeid);
	}
	
	@Override
	protected void init(String userIndr) throws Exception {
		this.setRedirectPage("success", userIndr);
	}
}
