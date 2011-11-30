/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.documentmanager.service;

import java.util.List;
import java.util.Map;

import com.j2ee.cms.biz.documentmanager.domain.DocumentCategory;
import com.j2ee.cms.biz.documentmanager.web.form.DocumentForm;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.service.BaseService;
import com.j2ee.cms.common.core.web.event.RequestEvent;
import com.j2ee.cms.common.core.web.event.ResponseEvent;

/**
 * <p>标题: flash的biz层</p>
 * <p>描述: 这里是flash的biz层，用于响应action并进行处理</p>
 * <p>模块: 文档管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 郑荣华
 * @version 1.0
 * @since 2009-3-25 下午04:06:48
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class FlashBiz extends BaseService {

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
		String articleFlash = (String) requestParam.get("articleFlash");
		//取得指定flash的信息
		if(dealMethod.equals("")) {
			log.info("flash分页信息");
			Pagination pagination = (Pagination) requestParam.get("pagination");
			pagination = documentService.findDocumentListByCategoryId(pagination, "categoryid", nodeid);
			int size = pagination.getData().size();
			if(size == 0){
				pagination.currPage = pagination.currPage-1;
				pagination = documentService.findDocumentListByCategoryId(pagination, "categoryid", nodeid);
			}
			responseParam.put("pagination", pagination);
			log.info("flash分页完成");
		
		//上传flash	
		}else if (dealMethod.equals("upload")) {
            log.info("添加flash信息");	
            int isScaleFlash = (Integer) requestParam.get("isScaleFlash");
            StringBuffer url = (StringBuffer) requestParam.get("url");
            DocumentForm form = (DocumentForm) requestParam.get("form"); 
            String infoMessage = "";
            infoMessage = documentService.proccessFlashUpload(sessionID, siteId, nodeid, url.toString(), form, documentService);
            responseParam.put("infoMessage", infoMessage);
            responseParam.put("isScaleFlash", isScaleFlash);
            log.info("添加flash信息完成");
		
        //删除指定的flash   
		}else if(dealMethod.equals("delete")) {
			log.info("删除flash信息");
			String ids = (String) requestParam.get("ids");
			String infoMessage = "";
			String categoryName = "flash类别->删除flash";
			infoMessage = documentService.deleteDocument(ids, siteId, sessionID, categoryName);
			responseParam.put("infoMessage", infoMessage);
			log.info("删除flash信息完成");
			
		// 高级编辑器flash列表	
		} else if(dealMethod.equals("insertFlashList")) {
			log.info("显示flash");
			int isScaleFlash = (Integer) requestParam.get("isScaleFlash");
			Pagination pagination = (Pagination) requestParam.get("pagination");
			List<DocumentCategory> categoryList = documentService.findFlashCategory(siteId); 
			String categoryid = "";
			// 存在文档
			if(categoryList.size() > 0) {
				if(nodeid.equals("f005")) {
					DocumentCategory flashCategory = categoryList.get(0);
					pagination = documentService.findDocumentListByCategoryId(pagination, "categoryid", flashCategory.getId());
					categoryid = flashCategory.getId();
				} else {
					pagination = documentService.findDocumentListByCategoryId(pagination, "categoryid", nodeid);
					categoryid = nodeid;
				}
				responseParam.put("categoryList", categoryList);
			} 
			//pagination = documentService.findDocumentList(pagination);
			responseParam.put("categoryid", categoryid);
			responseParam.put("pagination", pagination);
			responseParam.put("isScaleFlash", isScaleFlash);
			
		//高级编辑器中删除flash
		}else	if(dealMethod.equals("deleteFlash")) {
			String ids = (String) requestParam.get("ids");
			log.info(ids);
			String infoMessage = "";
			String categoryName = "flash类别->删除flash";
			infoMessage = documentService.deleteDocument(ids, siteId, sessionID, categoryName);
			responseParam.put("infoMessage", infoMessage);
		}
		responseParam.put("articleFlash", articleFlash);
		responseParam.put("nodeid", nodeid);
		responseParam.put("nodeNameStr", nodeNameStr);
	}
	
	@Override
	public ResponseEvent validateData(RequestEvent arg0) throws Exception {
		return null;
	}

	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}
	
}
