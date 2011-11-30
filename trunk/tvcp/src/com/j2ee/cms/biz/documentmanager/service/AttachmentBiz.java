/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.documentmanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.j2ee.cms.biz.documentmanager.domain.Document;
import com.j2ee.cms.biz.documentmanager.domain.DocumentCategory;
import com.j2ee.cms.biz.documentmanager.web.form.DocumentForm;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.service.BaseService;
import com.j2ee.cms.common.core.web.event.RequestEvent;
import com.j2ee.cms.common.core.web.event.ResponseEvent;

/**
 * <p>标题: 附件的biz</p>
 * <p>描述: 附件的biz层，用于响应附件的action</p>
 * <p>模块: 文档管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 郑荣华
 * @version 1.0
 * @since 2009-3-25 下午04:24:51
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class AttachmentBiz extends BaseService {

	/**注入文档业务对象**/
	private DocumentService documentService;
	
	@Override
	public void performTask(RequestEvent requestEvent, ResponseEvent responseEvent)
			throws Exception {
		String dealMethod = requestEvent.getDealMethod();
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		// 获取sessionID
		String sessionID = requestEvent.getSessionID();
		// 获取频道类型
		String channelType = requestEvent.getChannelType();
		// 网站id
		String siteId = requestEvent.getSiteid();
		//获取节点
		String nodeid = (String) requestParam.get("nodeid");
		//查询当前节点名称
		String nodeNameStr = documentService.findNodeNameByNodeId(nodeid);
		
		//查询附件信息
		if(dealMethod.equals("")) {
			log.info("附件分页信息");
			Pagination pagination = (Pagination) requestParam.get("pagination");
            pagination = documentService.findDocumentListByCategoryId(pagination, "categoryid", nodeid);
        	int size = pagination.getData().size();
			if(size == 0){
				pagination.currPage = pagination.currPage-1;
				pagination = documentService.findDocumentListByCategoryId(pagination, "categoryid", nodeid);
			}
            responseParam.put("pagination", pagination);
            log.info("附件分页完成");
			
		//添加附件信息
		}else if (dealMethod.equals("upload")) {
			log.info("添加附件信息");
			int isScaleImage = (Integer) requestParam.get("isScaleImage");
    		StringBuffer url = (StringBuffer) requestParam.get("url");
            DocumentForm form = (DocumentForm) requestParam.get("form"); 
            String infoMessage = "";
            infoMessage = documentService.proccessAttachmentUpload(sessionID, siteId, nodeid, url.toString(), form, documentService);
            responseParam.put("infoMessage", infoMessage);
            responseParam.put("isScaleImage", isScaleImage);
		    log.info("添加附件信息完成");	
            
		//删除附件
		}else if(dealMethod.equals("delete")) {
			log.info("删除附件");
			String ids = (String) requestParam.get("ids");
			String infoMessage = "";
			String categoryName = "附件类别->删除附件";
			infoMessage = documentService.deleteDocument(ids, siteId, sessionID, categoryName);
			responseParam.put("infoMessage", infoMessage);
			log.info("删除附件完成");
			
		// 附件列表
		} else if(dealMethod.equals("uploadAttachmentList")) {
			log.info("附件列表");
			Pagination pagination = (Pagination) requestParam.get("pagination");
			List<DocumentCategory> categoryList = documentService.findAttachmentCategory(siteId); 
			String categoryid = "";
			List<Document> list = new ArrayList<Document>();
			// 存在文档
			if(categoryList.size() > 0) {
				DocumentCategory attachmentCategory = categoryList.get(0);
				if(nodeid.equals("f006")) {
					pagination = documentService.findDocumentListByCategoryId(pagination, "categoryid", attachmentCategory.getId());
					categoryid = attachmentCategory.getId();
				} else {
					pagination = documentService.findDocumentListByCategoryId(pagination, "categoryid", nodeid);
					categoryid = nodeid;
				}
				list = documentService.findAttachmentListByCategoryId(categoryid);
			} 
			responseParam.put("categoryList", categoryList);
			responseParam.put("list", list);
			responseParam.put("categoryid", categoryid);
			responseParam.put("pagination", pagination);
			
			log.info("附件列表完成");
		
		// 在缩略图中删除附件
	    } else if(dealMethod.equals("deleteAttachment")) {
	    	log.info("在缩略图 页面删除附件信息");
			String ids = (String) requestParam.get("ids");
			String infoMessage = "";
			String categoryName = "附件类别->删除附件";
			infoMessage = documentService.deleteDocument(ids, siteId, sessionID, categoryName);
			responseParam.put("infoMessage", infoMessage);
			log.info("在缩略图 页面删除附件信息完成");
	    }
		responseParam.put("nodeid", nodeid);
		responseParam.put("nodeNameStr", nodeNameStr);
	}
	
	@Override
	public ResponseEvent validateData(RequestEvent arg0) throws Exception {
		return null;
	}
	
	/**
	 * @param documentService
	 */
	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}

}
