/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.documentmanager.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.j2ee.cms.biz.documentmanager.domain.TextWatermark;
import com.j2ee.cms.biz.documentmanager.domain.Watermark;
import com.j2ee.cms.biz.documentmanager.web.form.WaterMarkForm;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.web.GeneralAction;
import com.j2ee.cms.common.core.web.event.RequestEvent;
import com.j2ee.cms.common.core.web.event.ResponseEvent;

/**
 * <p>
 * 标题: —— 要求能简洁地表达出类的功能和职责
 * </p>
 * <p>
 * 描述: —— 简要描述类的职责、实现方式、使用注意事项等
 * </p>
 * <p>
 * 模块: CPS
 * </p>
 * <p>
 * 版权: Copyright (c) 2009  
 * </p>
 * <p>
 * 网址：http://www.j2ee.cmsweb.com
 * 
 * @author 曹名科
 * @version 1.0
 * @since 2009-9-1 下午04:12:56
 * @history（历次修订内容、修订人、修订时间等） 
 */
 

public class WaterMarkAction extends GeneralAction {
	private String dealMethod = "";

	@Override
	protected void doFormFillment(ActionForm actionForm,
			ResponseEvent responseEvent, String userIndr) throws Exception {
		Map<Object, Object> responseParam = responseEvent.getRespMapParam();
		WaterMarkForm form = (WaterMarkForm) actionForm;
		
		if (dealMethod.equals("")) { //为空就直接跳转到文字水印列表
			form.setPagination((Pagination) responseParam.get("pagination"));
			form.setInfoMessage("文字水印列表");
			
			this.setRedirectPage("waterFontList", userIndr);
			
			
		} else if (dealMethod.equals("deleteWaterFont")) { //删除文字水印
			this.setRedirectPage("deleteFontSuccess", userIndr);

			
		}else if(dealMethod.equals("delPicWater")){ //删除图片水印
			this.setRedirectPage("deletePictureSuccess", userIndr);
			
			
		}else if (dealMethod.equals("findWaterFont")) { //查找文字水印
			form.setTextwatermark((TextWatermark) responseParam
					.get("watermark"));
			this.setRedirectPage("waterFontdetail", userIndr);

			
		} else if (dealMethod.equals("editWaterFont")) { //编辑文字水印
			form.setTextwatermark((TextWatermark) responseParam
					.get("editwaterMarkForm"));
			form.setInfoMessage("修改成功");
			this.setRedirectPage("waterFontList", userIndr);

		} else if (dealMethod.equals("addWaterFont")) { //添加文字水印
			
			String infoMessage = (String) responseParam.get("infoMessage");
			form.setInfoMessage(infoMessage);
			log.debug(infoMessage);
			this.setRedirectPage("uploadPicMsg2", userIndr);

			
		} else if (dealMethod.equals("textWater")) { //文字水印分页列表
	
			form.setPagination((Pagination) responseParam.get("pagination"));
			this.setRedirectPage("waterFontList", userIndr);

			
		} else if (dealMethod.equals("picWater")) { //图片水印分页列表

			form.setPagination((Pagination) responseParam.get("pagination"));
			this.setRedirectPage("waterpiclist", userIndr);

		} else if (dealMethod.equals("deleteWaterFont")) {
			this.setRedirectPage("deletePictureSuccess", userIndr);
			
			
		} else if (dealMethod.equals("defaulted")) { //默认设置
			form.setListPicPath((List) responseParam.get("listPicPath"));
			form.setListWaterFont((List) responseParam.get("listWaterName"));
			form.setWatermark((Watermark)responseParam.get("TxtDefault"));
			
			this.setRedirectPage("defaultwater", userIndr);
			
		}else if (dealMethod.equals("addPicWater")) {//添加上传图片
			String infoMessage = (String) responseParam.get("infoMessage");
			log.info(">>>>>>>>>>>>"+infoMessage);
			form.setInfoMessage(infoMessage);
			this.setRedirectPage("uploadPicMsg2", userIndr);
			
			//保存默认设置
		}else if(dealMethod.equals("savedefaulted")){
			String infoMessage = (String) responseParam.get("infoMessage");
			form.setInfoMessage(infoMessage);
			this.setRedirectPage("modifyDefaultSuccess", userIndr);
		}
	}

	@Override
	protected void init(String arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void performTask(ActionForm actionForm,
			RequestEvent requestEvent, String userIndr) throws Exception {
		Map<Object, Object> requestParam = requestEvent.getReqMapParam();
		HttpServletRequest request = (HttpServletRequest) requestParam
				.get("HttpServletRequest");

		WaterMarkForm form = (WaterMarkForm) actionForm;
		this.dealMethod = form.getDealMethod();
		// 查询所有水印文字列表
		if (dealMethod.equals("")) {
			if(this.isUpSystemAdmin){
				form.setQueryKey("findAllWaterMarkPage");
				requestParam.put("pagination", form.getPagination());
			}else{
				form.setQueryKey("findWaterMarkPageBySiteId");
				requestParam.put("pagination", form.getPagination());
			}

			// 根据id查找水印信息
		} else if (dealMethod.equals("findWaterFont")) {
			requestParam.put("waterid", form.getIds());

			// 根据id删除信息
		} else if (dealMethod.equals("deleteWaterFont")) {
			log.info("开始删除"+form.getIds());
			requestParam.put("ids", form.getIds());
		}else if(dealMethod.equals("delPicWater")){
			log.info("开始删除"+form.getIds());
			requestParam.put("ids", form.getIds());
			// 添加文字水印
		} else if (dealMethod.equals("addWaterFont")) {

			TextWatermark wm = form.getTextwatermark();
			log.info("要添加的文字是：》》》》》》》》》》》》》》》》》。" + wm.getName());
			requestParam.put("addwaterMarkForm", wm);

			// 编辑水印
		} else if (dealMethod.equals("editWaterFont")) {
			TextWatermark wm = form.getTextwatermark();
			String id = wm.getId();
			requestParam.put("ids", id);
			requestParam.put("editwaterMarkForm", form);

			// 获取文字水印列表
		} else if (dealMethod.equals("textWater")) {
			if(this.isUpSystemAdmin){
				form.setQueryKey("findAllWaterMarkPage");
				requestParam.put("pagination", form.getPagination());
			}else{
				form.setQueryKey("findWaterMarkPageBySiteId");
				requestParam.put("pagination", form.getPagination());
			}

			// 获取图片水印列表
		} else if (dealMethod.equals("picWater")) {
			if(this.isUpSystemAdmin){
				form.setQueryKey("findAllPicWaterMarkPage");
				requestParam.put("pagination", form.getPagination());
			}else{
				form.setQueryKey("findPicWaterMarkPageBySiteId");
				requestParam.put("pagination", form.getPagination());
			}

			// 默认设置
		} else if (dealMethod.equals("defaulted")) {
			requestParam.put("defaultForm", form);

			// 保存默认设置
		} else if (dealMethod.equals("savedefaulted")) {
			String check = (String)request.getParameter("check");
			Map map = (Map)request.getSession().getAttribute("map");
			if(map == null){
				map = new HashMap();
				request.getSession().setAttribute("map", map);
			}
			map.put("check", check);
			requestParam.put("ids", form.getIds());
			// 上传图片
		} else if (dealMethod.equals("addPicWater")) {
			requestParam.put("addPicForm", form);
		}
	}

}
