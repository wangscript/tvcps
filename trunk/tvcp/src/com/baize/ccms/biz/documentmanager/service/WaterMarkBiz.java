/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.documentmanager.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.baize.ccms.biz.documentmanager.domain.TextWatermark;
import com.baize.ccms.biz.documentmanager.domain.Watermark;
import com.baize.ccms.biz.documentmanager.web.form.WaterMarkForm;
import com.baize.common.core.dao.Pagination;
import com.baize.common.core.service.BaseService;
import com.baize.common.core.web.event.RequestEvent;
import com.baize.common.core.web.event.ResponseEvent;

/**
 * <p>
 * 标题: —— 要求能简洁地表达出类的功能和职责
 * </p>
 * <p>
 * 描述: —— 简要描述类的职责、实现方式、使用注意事项等
 * </p>
 * <p>
 * 模块: CCMS
 * </p>
 * <p>
 * 版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * </p>
 * <p>
 * 网址：http://www.baizeweb.com
 * 
 * @author 曹名科
 * @version 1.0
 * @since 2009-9-1 下午06:07:08
 * @history（历次修订内容、修订人、修订时间等） 
 */
 

public class WaterMarkBiz extends BaseService {
	/** 水印注入. */
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
		if (dealMethod.equals("")) {
			Pagination pagination = (Pagination) requestParam.get("pagination");
			pagination = waterMarkService.findDocumentListByCategoryId(
					pagination, siteId,this.isUpSystemAdmin);
			responseParam.put("pagination", pagination);
			// 通过查找，显示水印文字详细
		} else if (dealMethod.equals("findWaterFont")) { // 查找水印字体
			String id = requestParam.get("waterid").toString();
			TextWatermark watermark = waterMarkService.findByWaterMarkId(id);
			responseParam.put("watermark", watermark);
			log.info("根据id获取水印信息");

			// 编辑文字水印
		} else if (dealMethod.equals("editWaterFont")) {
			WaterMarkForm waterMark = (WaterMarkForm) requestParam
					.get("editwaterMarkForm");
			String ids = requestParam.get("ids").toString();
			waterMarkService.modifyWaterFont(waterMark, ids);
			log.info("编辑水印");

			// 删除文字水印
		} else if (dealMethod.equals("deleteWaterFont")) {
			String ids = requestParam.get("ids").toString();
			waterMarkService.deleteWaterFontMark(ids);
			log.info("删除文字水印成功");
			// 删除图片水印
		} else if (dealMethod.equals("delPicWater")) {
			String ids = requestParam.get("ids").toString();
			waterMarkService.deletePicWaterMark(ids);
			log.info("删除图片水印成功");
			// 添加文字水印
		} else if (dealMethod.equals("addWaterFont")) {
			TextWatermark waterMark = (TextWatermark) requestParam
					.get("addwaterMarkForm");
			String mess = waterMarkService.addWaterFont(waterMark, siteId,
					sessionID);
			responseParam.put("infoMessage", mess);
			log.info("添加水印文字成功");

			// 文字水印列表
		} else if (dealMethod.equals("textWater")) {
			Pagination pagination = (Pagination) requestParam.get("pagination");
			pagination = waterMarkService.findDocumentListByCategoryId(
					pagination, siteId,this.isUpSystemAdmin);
			if(pagination.getData().size()==0){
				pagination.currPage= pagination.currPage-1;
				pagination = waterMarkService.findDocumentListByCategoryId(
						pagination, siteId,this.isUpSystemAdmin);
			}
			responseParam.put("pagination", pagination);
			log.info("获取所有文字信息");

			// 图片水印列表
		} else if (dealMethod.equals("picWater")) {
			Pagination pagination = (Pagination) requestParam.get("pagination");
			pagination = waterMarkService.findDocumentListByCategoryId(
					pagination, siteId,this.isUpSystemAdmin);
			if(pagination.getData().size()==0){
				pagination.currPage = pagination.currPage-1;
				pagination = waterMarkService.findDocumentListByCategoryId(
						pagination, siteId,this.isUpSystemAdmin);
			}
			responseParam.put("pagination", pagination);
			log.info("获取所有图片信息");

			// 默认设置
		} else if (dealMethod.equals("defaulted")) {
			
			List listWaterName = waterMarkService.findByWaterName(siteId,this.isUpSystemAdmin);//查询文字列表
			List listPicPath = waterMarkService.findByPicPath(siteId,this.isUpSystemAdmin);//查询图片列表
			Watermark w = waterMarkService.findByFontDefault(siteId,this.isUpSystemAdmin);//查询默认设置的选中的那个
			responseParam.put("TxtDefault", w);
			responseParam.put("listWaterName", listWaterName);
			responseParam.put("listPicPath", listPicPath);
			log.info("获取一个文字列表");

			// 添加图片(上传)
		} else if (dealMethod.equals("addPicWater")) {
			WaterMarkForm waterMarkForm = (WaterMarkForm) requestParam.get("addPicForm");
			String meg = waterMarkService.uploadImg(waterMarkForm, siteId, sessionID);
			responseParam.put("infoMessage", meg);
			log.info(meg);
			// 保存、设置
		} else if (dealMethod.equals("savedefaulted")) {
			String id = requestParam.get("ids").toString();
			
			String mess = waterMarkService.modifyWaterDefault(id,siteId,this.isUpSystemAdmin);
			responseParam.put("infoMessage", mess);
			log.info(mess);
		}
	}

	@Override
	public ResponseEvent validateData(RequestEvent arg0) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void setWaterMarkService(WaterMarkService waterMarkService) {
		this.waterMarkService = waterMarkService;
	}
}
