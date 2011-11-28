/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.documentmanager.web.action;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.baize.ccms.biz.documentmanager.domain.DocumentCategory;
import com.baize.ccms.biz.documentmanager.domain.PictureWatermark;
import com.baize.ccms.biz.documentmanager.domain.TextWatermark;
import com.baize.ccms.biz.documentmanager.domain.Watermark;
import com.baize.ccms.biz.documentmanager.web.form.DocumentForm;
import com.baize.common.core.dao.Pagination;
import com.baize.common.core.util.StringUtil;
import com.baize.common.core.web.GeneralAction;
import com.baize.common.core.web.event.RequestEvent;
import com.baize.common.core.web.event.ResponseEvent;

/**
 * <p>
 * 标题: 图片的action
 * </p>
 * <p>
 * 描述: 图片的action，用于处理和接受图片的有关信息
 * </p>
 * <p>
 * 模块: 文档管理
 * </p>
 * <p>
 * 版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * 
 * @author 郑荣华
 * @version 1.0
 * @since 2009-3-23 下午06:18:57
 * @history（历次修订内容、修订人、修订时间等） 
 */
 

public class PictureAction extends GeneralAction {

	private String dealMethod = "";

	@Override
	protected void doFormFillment(ActionForm actionForm,
			ResponseEvent responseEvent, String userIndr) {
		Map<Object, Object> responseParam = responseEvent.getRespMapParam();
		DocumentForm form = (DocumentForm) actionForm;
		// 获取节点
		String nodeid = (String) responseParam.get("nodeid");
		form.setNodeId(nodeid);
		String nodeNameStr = (String) responseParam.get("nodeNameStr");
		form.setNodeNameStr(nodeNameStr);
		int columnLink = (Integer) responseParam.get("columnLink");
		form.setColumnLink(columnLink);
		// 显示图片列表
		if (dealMethod.equals("")) {
			Pagination pagination = (Pagination) responseParam.get("pagination");
			form.setPagination(pagination);
			this.setRedirectPage("pictureSuccess", userIndr);

			// 上传图片
		} else if (dealMethod.equals("upload")) {
			int isScaleImage = (Integer) responseParam.get("isScaleImage");
			String articlePicture = (String) responseParam
					.get("articlePicture");
			form.setArticlePicture(articlePicture);
			form.setIsScaleImage(isScaleImage);
			String infoMessage = "";
			infoMessage = (String) responseParam.get("infoMessage");
			form.setInfoMessage(infoMessage);
			this.setRedirectPage("uploadPicMsg", userIndr);
			// 删除图片
		} else if (dealMethod.equals("delete")) {
			String infoMessage = "";
			infoMessage = (String) responseParam.get("infoMessage");
			form.setInfoMessage(infoMessage);
			
			//下面为测试用
			Pagination pagination = (Pagination) responseParam.get("pagination");
			int currPage = pagination.currPage;
			int maxPage = pagination.maxPage;
			if(currPage>maxPage){
				pagination.currPage = maxPage;
				int size = pagination.getData().size();
			}
			form.setPagination(pagination);
			this.setRedirectPage("deletePictureSuccess", userIndr);
			// 图片高级编辑器使用
		} else if (dealMethod.equals("uploadPicList")) {
			String categoryid = (String) responseParam.get("categoryid");
			form.setCategoryId(categoryid);
			List<DocumentCategory> catgoryList = (List) responseParam
					.get("categoryList");
			form.setPagination((Pagination) responseParam.get("pagination"));
			form.setNodeId((String) responseParam.get("nodeId"));
			form.setList(catgoryList);
			this.setRedirectPage("uploadPicList", userIndr);
			// 生成缩略图片并显示
		} else if (dealMethod.equals("scaleImage")) {
			form.setInfoMessage(responseParam.get("scaleImgPath").toString());
			this.setRedirectPage("uploadPicMsg", userIndr);
			// 在缩略图中删除图片
		} else if (dealMethod.equals("deletePicture")) {
			String infoMessage = "";
			infoMessage = (String) responseParam.get("infoMessage");
			form.setInfoMessage(infoMessage);
			this.setRedirectPage("deleteScaleImageSuccess", userIndr);

			// 获取水印信息放入上传图片页面,文章管理
		} else if (dealMethod.equals("getColsWater")) {
			form.setListPicPath((List) responseParam.get("listPicPath"));
			form.setListWaterFont((List) responseParam.get("listWaterName"));
			form.setWatermark((Watermark) responseParam.get("TxtDefault"));
			PictureWatermark p = (PictureWatermark) responseParam.get("pWater");
			TextWatermark t = (TextWatermark) responseParam.get("tWater");
			if (p == null) {
				form.setTextwatermark(t);
			} else if (t == null) {
				form.setPicwatermark(p);
			}
			HttpServletRequest request = (HttpServletRequest) responseParam.get("request");
			Map map = (Map)request.getSession().getAttribute("map");
			if(map != null){
				String check = (String)map.get("check");
				form.setCheck(check);
			}
			this.setRedirectPage("uploadPictureSuccess", userIndr);

			// 显示相应的水印,文章管理
		} else if (dealMethod.equals("getPicColsWater")) {
			String isScaleImage = (String) responseParam.get("isScaleImage");
			int isScale = StringUtil.parseInt(isScaleImage);
			form.setIsScaleImage(isScale);
			form.setListPicPath((List) responseParam.get("listPicPath"));
			form.setListWaterFont((List) responseParam.get("listWaterName"));
			form.setWatermark((Watermark) responseParam.get("TxtDefault"));
			PictureWatermark p = (PictureWatermark) responseParam.get("pWater");
			TextWatermark t = (TextWatermark) responseParam.get("tWater");

			if (p == null) {
				form.setTextwatermark(t);
			} else if (t == null) {
				form.setPicwatermark(p);
			}
			this.setRedirectPage("uploadPictureSuccess", userIndr);
			// 下拉列表
		} else if (dealMethod.equals("changWater")) {
			String p = responseParam.get("allpicwater").toString();
			String t = responseParam.get("allwaterfont").toString();
			// form.setListPicPath((List) responseParam.get("listPicPath"));
			// form.setListWaterFont((List) responseParam.get("listWaterName"));
			form.setPic(p);
			form.setWaterfont(t);
			/*
			 * if(p!=null){ form.setWatermark(p); } if(t!=null){
			 * form.setWatermark(t); } if(p==null){ form.setTextwatermark(t);
			 * }else if(t==null){ form.setPicwatermark(p); }
			 */
			this.setRedirectPage("selectChangeMsg", userIndr);
			// this.setRedirectPage("uploadPictureSuccess", userIndr);
		}
	}

	@Override
	protected void performTask(ActionForm actionForm,
			RequestEvent requestEvent, String userIndr) throws Exception {
		Map<Object, Object> requestParam = requestEvent.getReqMapParam();
		HttpServletRequest request = (HttpServletRequest) requestParam
				.get("HttpServletRequest");

		DocumentForm form = (DocumentForm) actionForm;
		this.dealMethod = form.getDealMethod();
		// 获得节点
		String nodeid = form.getNodeId();
		if (nodeid == null || nodeid.equals("0") || nodeid.equals("")
				|| nodeid.equals("null")) {
			nodeid = null;
		}
		String articlePicture = form.getArticlePicture();
		if (articlePicture == null || articlePicture.equals("null")
				|| articlePicture.equals("")) {
			articlePicture = null;
		}
		int columnLink = form.getColumnLink();
		// 显示图片列表及显示高级编辑器添加图片
		if (dealMethod.equals("")) {
			if (isUpSystemAdmin) {
				form.setQueryKey("findPictureByCategoryIdPage");
			} else {
				form.setQueryKey("findPictureByCategoryIdBySiteIdPage");
			}
			Pagination pagination = form.getPagination();
			pagination.getData();
			requestParam.put("pagination", form.getPagination());
			requestParam.put("pathName", request.getContextPath());// 获取当前程序名称
			// 添加图片
		} else if (dealMethod.equals("upload")) {
			// 是否是从高级编辑器页面上传

			int isScaleImage = form.getIsScaleImage();
			// 设置访问路径
			StringBuffer url = request.getRequestURL();
			requestParam.put("pathName", request.getContextPath());
			requestParam.put("isScaleImage", isScaleImage);
			requestParam.put("url", url);
			requestParam.put("form", form);

			// 删除图片
		} else if (dealMethod.equals("delete")) {
			String ids = form.getIds();
			requestParam.put("ids", ids);
			//获取pagination的页面基本信息
			form.setQueryKey("findPictureByCategoryIdPage");
			Pagination pagination = form.getPagination();
			int size = ids.split(",").length;
			requestParam.put("size", size);
			requestParam.put("pagination", form.getPagination());
			requestParam.put("pathName", request.getContextPath());
			// 图片列表
		} else if (dealMethod.equals("uploadPicList")) {
			form.setQueryKey("findPictureByCategoryIdPage");
			requestParam.put("pathName", request.getContextPath());
			requestParam.put("pagination", form.getPagination());

			// 生成缩略图
		} else if (dealMethod.equals("scaleImage")) {
			requestParam.put("pathName", request.getContextPath());
			String w = request.getParameter("imgW");
			String h = request.getParameter("imgH");
			String imgurl = request.getParameter("imgurl");
			String ids = request.getParameter("imgid");
			form.setSuolvheight(h);
			form.setSuolvwidth(w);
			form.setPicurl(imgurl);
			form.setIds(ids);
		//	System.out.println("开始生成"+imgurl+" id"+ids);			
			requestParam.put("scaleBre", form);

			// 在缩略图中删除图片
		} else if (dealMethod.equals("deletePicture")) {
			String ids = form.getIds();
			requestParam.put("ids", ids);
			// 获取水印信息放入上传图片页面
		} else if (dealMethod.equals("getColsWater")) {

		} else if (dealMethod.equals("getPicColsWater")) {
			String isScaleImage = "" + form.getIsScaleImage();
			requestParam.put("isScaleImage", isScaleImage);
			// ajax 下拉列表改变时调用这个
		} else if (dealMethod.equals("changWater")) {
			String id = request.getParameter("fontNameId");

			requestParam.put("changId", id);

		}

		requestParam.put("columnLink", columnLink);
		requestParam.put("articlePicture", articlePicture);
		requestParam.put("nodeid", nodeid);
	}

	@Override
	protected void init(String userIndr) throws Exception {
		// this.setRedirectPage("success", userIndr);

	}
}
