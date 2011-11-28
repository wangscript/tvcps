/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.documentmanager.web.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;

import com.baize.ccms.biz.documentmanager.domain.DocumentCategory;
import com.baize.ccms.biz.documentmanager.web.form.DocumentForm;
import com.baize.ccms.sys.GlobalConfig;
import com.baize.common.core.dao.Pagination;
import com.baize.common.core.util.StringUtil;
import com.baize.common.core.web.GeneralAction;
import com.baize.common.core.web.event.RequestEvent;
import com.baize.common.core.web.event.ResponseEvent;

/**
 * <p>标题: flash的action</p>
 * <p>描述: flash的action处理，用于处理和接受flash的有关信息</p>
 * <p>模块: 文档管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 郑荣华
 * @version 1.0
 * @since 2009-3-25 下午04:08:18
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class FlashAction extends GeneralAction {
	
	private String dealMethod = "";

	@Override
	protected void doFormFillment(ActionForm actionForm, ResponseEvent responseEvent, String userIndr) {
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		DocumentForm form = (DocumentForm) actionForm;
		String nodeid = (String) responseParam.get("nodeid");
		form.setNodeId(nodeid);
		String nodeNameStr = (String) responseParam.get("nodeNameStr");
		form.setNodeNameStr(nodeNameStr);
		String articleFlash = (String) responseParam.get("articleFlash");
		form.setArticleFlash(articleFlash);
		//显示flash列表
		if (dealMethod.equals("")) {
			form.setPagination((Pagination) responseParam.get("pagination"));
			this.setRedirectPage("flashSuccess", userIndr);
		
		//上传flash	
		}else if (dealMethod.equals("upload")) {
			//判断是否是从高级编辑器上传flash
			int isScaleFlash = (Integer) responseParam.get("isScaleFlash");
			form.setIsScaleFlash(isScaleFlash);
			String infoMessage = ""; 
			infoMessage = (String) responseParam.get("infoMessage");
			form.setInfoMessage(infoMessage);
			this.setRedirectPage("uploadFlaMsg", userIndr);
			
		//flash高级编辑器使用
		}else if(dealMethod.equals("insertFlashList")) {
			log.info("显示flash");
			String categoryid = (String)responseParam.get("categoryid");
			form.setCategoryId(categoryid);
			List<DocumentCategory> catgoryList = (List) responseParam.get("categoryList");
			form.setPagination((Pagination) responseParam.get("pagination"));
			form.setList(catgoryList);
			this.setRedirectPage("insertFlashList", userIndr);
		
		//删除flash
		}else if(dealMethod.equals("delete")) {
			String infoMessage = "";
			infoMessage = (String) responseParam.get("infoMessage");
			form.setInfoMessage(infoMessage);
			this.setRedirectPage("deleteFlashSuccess", userIndr);
			
		//高级编辑器中删除flash
		}else	if(dealMethod.equals("deleteFlash")) {
			String infoMessage = "";
			infoMessage = (String) responseParam.get("infoMessage");
			form.setInfoMessage(infoMessage);
			this.setRedirectPage("deleteFlash", userIndr);
		}
	}

	@Override
	protected void performTask(ActionForm actionForm, RequestEvent requestEvent, String userIndr) throws Exception {
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		HttpServletRequest request = (HttpServletRequest) requestParam.get("HttpServletRequest");
		
		DocumentForm form = (DocumentForm) actionForm;
		this.dealMethod = form.getDealMethod();
		//获得节点
		String nodeid = form.getNodeId();
		if(nodeid == null || nodeid.equals("0") || nodeid.equals("") || nodeid.equals("null")) {
			nodeid = null;
		}
		String articleFlash = form.getArticleFlash();
		if(articleFlash == null || articleFlash.equals("")) {
			articleFlash = null;
		}
		//显示flash列表
		if(dealMethod.equals("")) {
			form.setQueryKey("findFlashByCategoryIdPage");
			requestParam.put("pagination", form.getPagination());
			
		//上传flash	
		}else	if(dealMethod.equals("upload")) {
			// 是否是从高级编辑器页面上传
			int isScaleFlash = form.getIsScaleFlash();
			//设置访问路径
			StringBuffer url = request.getRequestURL();
			requestParam.put("url", url);
			requestParam.put("form", form);
			requestParam.put("isScaleFlash", isScaleFlash);
			
		// 高级编辑器显示flash列表	
		} else if(dealMethod.equals("insertFlashList")) {
			log.info("显示flash1");
			// 是否是从高级编辑器页面上传
			int isScaleFlash = form.getIsScaleFlash();
			form.setQueryKey("findFlashByCategoryIdPage");
			requestParam.put("pagination", form.getPagination());
			requestParam.put("isScaleFlash", isScaleFlash);
		    
		//删除flash
		}else	if(dealMethod.equals("delete")) {
			String ids = form.getIds();
			requestParam.put("ids", ids);
			
		//高级编辑器中删除flash
		}else	if(dealMethod.equals("deleteFlash")) {
			String ids = form.getIds();
			log.info(ids);
			requestParam.put("ids", ids);
		}
		requestParam.put("articleFlash", articleFlash);
		requestParam.put("nodeid", nodeid);
	}
	
	@Override
	protected void init(String userIndr) throws Exception {
	//	this.setRedirectPage("success", userIndr);
	}

}
