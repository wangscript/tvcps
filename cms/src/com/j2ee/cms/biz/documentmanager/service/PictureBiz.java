/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.documentmanager.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.j2ee.cms.biz.documentmanager.domain.DocumentCategory;
import com.j2ee.cms.biz.documentmanager.domain.PictureWatermark;
import com.j2ee.cms.biz.documentmanager.domain.TextWatermark;
import com.j2ee.cms.biz.documentmanager.domain.Watermark;
import com.j2ee.cms.biz.documentmanager.web.form.DocumentForm;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.service.BaseService;
import com.j2ee.cms.common.core.web.event.RequestEvent;
import com.j2ee.cms.common.core.web.event.ResponseEvent;

/**
 * <p>
 * 标题: 图片的biz层
 * </p>
 * <p>
 * 描述: 图片的处理层，用于响应图片的action
 * </p>
 * <p>
 * 模块: 文档管理
 * </p>
 * <p>
 * 版权: Copyright (c) 2009  
 * 
 * @author 郑荣华
 * @version 1.0
 * @since 2009-3-24 下午02:36:32
 * @history（历次修订内容、修订人、修订时间等） 
 */
 

public class PictureBiz extends BaseService {

	/** 注入文档业务对象 **/
	private DocumentService documentService;

	/** 注入水印业务对象 */
	private WaterMarkService waterMarkService;

	@Override
	public void performTask(RequestEvent requestEvent,
			ResponseEvent responseEvent) throws Exception {
		String dealMethod = requestEvent.getDealMethod();
		Map<Object, Object> requestParam = requestEvent.getReqMapParam();
		Map<Object, Object> responseParam = responseEvent.getRespMapParam();
		// 获取sessionID
		String sessionID = requestEvent.getSessionID();
		// 网站id
		String siteId = requestEvent.getSiteid();
		// 获取节点
		String nodeid = (String) requestParam.get("nodeid");
		// 查询当前节点名称
		String nodeNameStr = documentService.findNodeNameByNodeId(nodeid);
		String articlePicture = (String) requestParam.get("articlePicture");
		int columnLink = (Integer) requestParam.get("columnLink");

		// 查询图片的信息及显示高级编辑器添加图片
		if (dealMethod.equals("")) {
			log.info("图片分页信息");
			Pagination pagination = (Pagination) requestParam.get("pagination");
			String pathName = requestParam.get("pathName").toString();
			if (isUpSystemAdmin) {
				pagination = documentService.findDocumentListByCategoryId(pagination, "categoryid", nodeid);
			} else {
				pagination = documentService.findDocumentListByCategoryIdBySiteId(pagination, "categoryid", nodeid, siteId);
			}
			int size = pagination.getData().size();
			if(size == 0){
				pagination.currPage = pagination.currPage-1;
				if (isUpSystemAdmin) {
					pagination = documentService.findDocumentListByCategoryId(pagination, "categoryid", nodeid);
				} else {
					pagination = documentService.findDocumentListByCategoryIdBySiteId(pagination, "categoryid", nodeid, siteId);
				}
			}
			pagination = documentService.proccessPicturePagination(pagination, pathName);
			responseParam.put("pagination", pagination);
			log.info("图片分页完成");

		// 上传图片
		} else if (dealMethod.equals("upload")) {
			log.info("添加图片信息");
			int isScaleImage = (Integer) requestParam.get("isScaleImage");
			StringBuffer url = (StringBuffer) requestParam.get("url");
			DocumentForm form = (DocumentForm) requestParam.get("form");
			String infoMessage = "";
			infoMessage = documentService.proccessPictureUpload(sessionID,
					siteId, nodeid, url.toString(), form, documentService);

			responseParam.put("infoMessage", infoMessage);
			responseParam.put("isScaleImage", isScaleImage);
			log.info("处理添加图片完成");

		// 删除指定的图片
		} else if (dealMethod.equals("delete")) {
			int size = (Integer) requestParam.get("size");
			Pagination pagination = (Pagination) requestParam.get("pagination");
			String pathName = requestParam.get("pathName").toString();
			pagination = documentService.findDocumentListByCategoryId(pagination, "categoryid", nodeid);
			
			log.info("删除图片信息");
			String ids = (String) requestParam.get("ids");
			String infoMessage = "";
			String categoryName = "图片类别->删除图片";
			infoMessage = documentService.deletePic(ids, siteId,
					sessionID, categoryName);
			responseParam.put("infoMessage", infoMessage);
			log.info("删除图片信息完成");
			
			if(size>=pagination.getData().size()){
				pagination.currPage = pagination.currPage-1;
				pagination = documentService.findDocumentListByCategoryId(pagination, "categoryid", nodeid);
			}
			pagination = documentService.proccessPicturePagination(pagination, pathName);
			responseParam.put("pagination", pagination);
		// 图片列表
		} else if (dealMethod.equals("uploadPicList")) {
			log.info("图片列表");
			Pagination pagination = (Pagination) requestParam.get("pagination");
			List<DocumentCategory> categoryList = documentService
					.findPictureCategory(siteId);
			String categoryid = "";
			// 存在文档
			if (categoryList.size() > 0 && nodeid != null) {
				if (nodeid.equals("f004")) {
					DocumentCategory pictureCategory = categoryList.get(0);
					pagination = documentService.findDocumentListByCategoryId(
							pagination, "categoryid", pictureCategory.getId());
					categoryid = pictureCategory.getId();
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
			log.info("图片列表完成");

		// 在缩略图中删除图片
		} else if (dealMethod.equals("deletePicture")) {
			log.info("在缩略图 页面删除图片信息");
			String ids = (String) requestParam.get("ids");
			String infoMessage = "";
			String categoryName = "图片类别->删除图片";
			infoMessage = documentService.deleteDocument(ids, siteId,
					sessionID, categoryName);
			responseParam.put("infoMessage", infoMessage);
			log.info("在缩略图 页面删除图片信息完成");
			
		// 获取水印信息放入上传图片页面，在文档管理用
		} else if (dealMethod.equals("getColsWater")) {
			HttpServletRequest request = (HttpServletRequest) requestParam.get("HttpServletRequest");
			responseParam.put("request", request);
			List listWaterName = waterMarkService.findByWaterName(siteId,
					this.isUpSystemAdmin);// 获取文字水印列表
			List listPicPath = waterMarkService.findByPicPath(siteId,
					this.isUpSystemAdmin);// 获取图片水印下拉列表
			Watermark w = waterMarkService.findByFontDefault(siteId,
					this.isUpSystemAdmin); // 获取选中的那个

			TextWatermark tw = waterMarkService.findByTextWater(siteId,
					this.isUpSystemAdmin);// 获取选中默认设置的文字
			PictureWatermark p = waterMarkService.findByPicWater(siteId,
					this.isUpSystemAdmin);// 获取选中默认设置的图片
			responseParam.put("pWater", p);
			responseParam.put("tWater", tw);
			responseParam.put("TxtDefault", w);
			responseParam.put("listWaterName", listWaterName);
			responseParam.put("listPicPath", listPicPath);
			log.info("获取水印文字和图片列表完成");

		// 上传图片时选择相应的水印 ,在文章管理中用
		} else if (dealMethod.equals("getPicColsWater")) {
			String isScaleImage = (String) requestParam.get("isScaleImage");
			List listWaterName = waterMarkService.findByWaterName(siteId,
					this.isUpSystemAdmin);// 获取文字水印列表
			List listPicPath = waterMarkService.findByPicPath(siteId,
					this.isUpSystemAdmin);// 获取图片水印下拉列表
			Watermark w = waterMarkService.findByFontDefault(siteId,
					this.isUpSystemAdmin); // 获取选中的那个

			TextWatermark tw = waterMarkService.findByTextWater(siteId,
					this.isUpSystemAdmin);// 获取选中默认设置的文字
			PictureWatermark p = waterMarkService.findByPicWater(siteId,
					this.isUpSystemAdmin);// 获取选中默认设置的图片
			responseParam.put("pWater", p);
			responseParam.put("tWater", tw);
			responseParam.put("TxtDefault", w);
			responseParam.put("listWaterName", listWaterName);
			responseParam.put("listPicPath", listPicPath);
			responseParam.put("isScaleImage", isScaleImage);

		// 图片缩放
		} else if (dealMethod.equals("scaleImage")) {
			DocumentForm form = (DocumentForm) requestParam.get("scaleBre");
			String pathName = requestParam.get("pathName").toString();
			String mess =documentService.scaleImg(form,pathName);
			responseParam.put("scaleImgPath", mess);
			
		// 改变下拉列表的值 用ajax实现
		} else if (dealMethod.equals("changWater")) {
			String id = requestParam.get("changId").toString();
			String tw = waterMarkService.findByWaterAll(id);
			String p = waterMarkService.findByPicAll(id);
			// List listWaterName = waterMarkService.findByWaterName(siteId);//
			// 获取文字水印列表
			// List listPicPath = waterMarkService.findByPicPath(siteId);//
			// 获取图片水印下拉列表
			// responseParam.put("listWaterName", listWaterName);
			// responseParam.put("listPicPath", listPicPath);
			responseParam.put("allpicwater", p);
			responseParam.put("allwaterfont", tw);
		}
		responseParam.put("columnLink", columnLink);
		responseParam.put("articlePicture", articlePicture);
		responseParam.put("nodeid", nodeid);
		responseParam.put("nodeNameStr", nodeNameStr);
	}

	@Override
	public ResponseEvent validateData(RequestEvent requestEvent)
			throws Exception {
		return null;
	}

	/**
	 * @param documentService
	 */
	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}

	public void setWaterMarkService(WaterMarkService waterMarkService) {
		this.waterMarkService = waterMarkService;
	}
}
