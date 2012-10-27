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
 * <p>
 * 标题: 附件的biz
 * </p>
 * <p>
 * 描述: 附件的biz层，用于响应附件的action
 * </p>
 * <p>
 * 模块: 文档管理
 * </p>
 * <p>
 * 版权: Copyright (c) 2009  
 * 
 * @author 郑荣华
 * @version 1.0
 * @since 2009-3-25 下午04:24:51
 * @history（历次修订内容、修订人、修订时间等） 
 */
 
public class JsBiz extends BaseService {

	/** 注入文档业务对象 **/
	private DocumentService documentService;

	@Override
	public void performTask(RequestEvent requestEvent,
			ResponseEvent responseEvent) throws Exception {
		String dealMethod = requestEvent.getDealMethod();
		Map<Object, Object> requestParam = requestEvent.getReqMapParam();
		Map<Object, Object> responseParam = responseEvent.getRespMapParam();
		// 获取sessionID
		String sessionID = requestEvent.getSessionID();
		// 获取频道类型
		String channelType = requestEvent.getChannelType();
		// 网站id
		String siteId = requestEvent.getSiteid();
		// 获取节点
		String nodeid = (String) requestParam.get("nodeid");
		// 查询当前节点名称
		String nodeNameStr = documentService.findNodeNameByNodeId(nodeid);

		// 查询附件信息
		if (dealMethod.equals("")) {
			log.info("js脚本分页信息");
			Pagination pagination = (Pagination) requestParam.get("pagination");
			pagination = documentService.findDocumentListByCategoryId(
					pagination, "categoryid", nodeid);
			
			int size = pagination.getData().size();
			if(size == 0){
				pagination.currPage = pagination.currPage-1;
				pagination = documentService.findDocumentListByCategoryId(
						pagination, "categoryid", nodeid);
			}
			responseParam.put("pagination", pagination);
			log.info("js脚本分页完成");

			// 添加脚本信息
		} else if (dealMethod.equals("upload")) {
			log.info("上传js脚本");
			int isScaleImage = (Integer) requestParam.get("isScaleImage");
			StringBuffer url = (StringBuffer) requestParam.get("url");
			DocumentForm form = (DocumentForm) requestParam.get("form");
			String infoMessage = "";
			infoMessage = documentService.proccessJsFileUpload(sessionID,
					siteId, nodeid, url.toString(), form, documentService);
			responseParam.put("infoMessage", infoMessage);
			responseParam.put("isScaleImage", isScaleImage);
			log.info("添加脚本信息完成");

			// 删除脚本
		} else if (dealMethod.equals("delete")) {
			log.info("删除js脚本");
			String ids = (String) requestParam.get("ids");
			String infoMessage = "";
			String categoryName = "js类别->删除js";
			infoMessage = documentService.deleteDocument(ids, siteId,
					sessionID, categoryName);
			responseParam.put("infoMessage", infoMessage);

			// 附件列表
		} else if (dealMethod.equals("uploadScriptList")) {
			log.info("附件列表");
			Pagination pagination = (Pagination) requestParam.get("pagination");
			List<DocumentCategory> categoryList = documentService
					.findJsCategory(siteId);
			String categoryid = "";
			List<Document> list = new ArrayList<Document>();
			// 存在文档
			if (categoryList.size() > 0) {
				DocumentCategory attachmentCategory = categoryList.get(0);
				if (nodeid.equals("f006") ||nodeid.equals("f007")) {
					pagination = documentService.findDocumentListByCategoryId(
							pagination, "categoryid", attachmentCategory
									.getId());
					categoryid = attachmentCategory.getId();
				} else {
					pagination = documentService.findDocumentListByCategoryId(
							pagination, "categoryid", nodeid);
					categoryid = nodeid;
				}
				list = documentService.findJsListByCategoryId(categoryid);
			}
			responseParam.put("categoryList", categoryList);
			responseParam.put("list", list);
			responseParam.put("categoryid", categoryid);
			responseParam.put("pagination", pagination);

			log.info("附件列表完成");

			// 在缩略图中删除脚本
		} else if (dealMethod.equals("deleteAttachment")) {
			log.info("在缩略图 页面删除附件信息");
			String ids = (String) requestParam.get("ids");
			String infoMessage = "";
			String categoryName = "js类别->删除js";
			infoMessage = documentService.deleteDocument(ids, siteId,
					sessionID, categoryName);
			responseParam.put("infoMessage", infoMessage);
			log.info("在缩略图 页面删除附件信息完成");

			// 高级编辑中上传脚本
		} else if (dealMethod.equals("uploadInEditor")) {
			StringBuffer url = (StringBuffer) requestParam.get("url");
			DocumentForm form = (DocumentForm) requestParam.get("form");
			String infoMessage = "";
			infoMessage = documentService.proccessJsFileUpload(sessionID,
					siteId, nodeid, url.toString(), form, documentService);
			responseParam.put("infoMessage", infoMessage);

			// 高级编辑中分页显示脚本
		} else if (dealMethod.equals("findJsPaginationInEditor")) {
			Pagination pagination = (Pagination) requestParam.get("pagination");
			List<DocumentCategory> categoryList = documentService
					.findJsCategory(siteId);
			String categoryid = "";
			// 存在文档
			if (categoryList.size() > 0 && nodeid != null) {
				if (nodeid.equals("f007")) {
					DocumentCategory jsCategory = categoryList.get(0);
					pagination = documentService.findDocumentListByCategoryId(
							pagination, "categoryid", jsCategory.getId());
					categoryid = jsCategory.getId();
				} else {
					pagination = documentService.findDocumentListByCategoryId(
							pagination, "categoryid", nodeid);
					categoryid = nodeid;
				}
			}
			String pathName = requestParam.get("pathName").toString();
			pagination = documentService.proccessPicturePagination(pagination,
					pathName);
			responseParam.put("categoryList", categoryList);
			responseParam.put("categoryid", categoryid);
			responseParam.put("nodeId", nodeid);
			responseParam.put("pagination", pagination);
			log.info("js脚本分页完成");

			// 高级编辑中删除脚本
		} else if (dealMethod.equals("deleteInEditor")) {
			String ids = (String) requestParam.get("ids");
			String infoMessage = "";
			String categoryName = "js类别->删除js";
			infoMessage = documentService.deleteDocument(ids, siteId,
					sessionID, categoryName);
			responseParam.put("infoMessage", infoMessage);
			// 编辑脚本
		} else if (dealMethod.equals("editjs")) {
			String path = requestParam.get("jspath").toString();
			String jsContent = documentService.editJsFile(path);
			responseParam.put("jsContent", jsContent);
			responseParam.put("jspath", path);
			//保存脚本
		}else if(dealMethod.equals("savejs")){
			String path =requestParam.get("jsPath").toString();
			String content=requestParam.get("jsContent").toString();
			String mess = documentService.saveJsFile(path, content,siteId,sessionID);
			responseParam.put("mess", mess);
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
