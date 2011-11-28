/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.plugin.onlinesurvey.service;

import java.util.ArrayList;
import java.util.Map;

import com.baize.ccms.plugin.onlinesurvey.domain.OnlineSurveyContent;
import com.baize.ccms.plugin.onlinesurvey.web.form.OnlineSurveyContentForm;
import com.baize.common.core.dao.Pagination;
import com.baize.common.core.service.BaseService;
import com.baize.common.core.util.StringUtil;
import com.baize.common.core.web.event.RequestEvent;
import com.baize.common.core.web.event.ResponseEvent;

/**
 * <p>
 * 标题: 网上调查业务处理类
 * </p>
 * <p>
 * 描述: 网上调查,删除,添加，修改等功能
 * </p>
 * <p>
 * 模块:网上调查
 * </p>
 * <p>
 * 版权: Copyright (c) 2009南京百泽信息科技有限责任公司
 * 
 * @author 包坤涛
 * @version 1.0
 * @since 2009-10-31 下午02:26:33
 * @history（历次修订内容、修订人、修订时间等） 
*/
 

public class OnlineSurveyContentBiz extends BaseService {

	/** 注入网上调查问题业务对象 **/
	private OnlineSurveyContentService onlineSurveyContentService;

	@SuppressWarnings("unchecked")
	@Override
	public void performTask(RequestEvent requestEvent,
			ResponseEvent responseEvent) throws Exception {
		String dealMethod = requestEvent.getDealMethod();
		Map<Object, Object> requestParam = requestEvent.getReqMapParam();
		Map<Object, Object> responseParam = responseEvent.getRespMapParam();
		String siteId = requestEvent.getSiteid();
		String categoryId = (String) requestParam.get("categoryId");
		String nodeId = (String) requestParam.get("nodeId");
		
		//网上调查问题分页
		if(dealMethod.equals("")){
			log.info("查询调查问题分页");
			Pagination pagination = (Pagination) requestParam.get("pagination");
			responseParam.put("pagination", onlineSurveyContentService.findOnlineQuestionPage(pagination, nodeId));
			
		//问题表单（添加、修改）	
		}else if(dealMethod.equals("detail")){
			log.info("问题表单");
			String questionId = (String) requestParam.get("questionId");
			//修改调查问题页面
			if(!StringUtil.isEmpty(questionId)){
				OnlineSurveyContent onlineSurveyContent = onlineSurveyContentService.findOnlineContent(questionId);
				responseParam.put("onlineSurveyContent", onlineSurveyContent);
			}
			responseParam.put("questionId", questionId);
			
		//添加调查问题	
		}else if(dealMethod.equals("add")){
			log.info("添加调查问题");
			OnlineSurveyContent onlineSurveyContent = (OnlineSurveyContent) requestParam.get("onlineSurveyContent");
			onlineSurveyContentService.addOnlineSurveyContent(onlineSurveyContent);
		 
		//修改调查问题	
		}else if(dealMethod.equals("modify")){
			log.info("修改调查问题");
			String questionId = (String) requestParam.get("questionId");
			OnlineSurveyContent onlineSurveyContent = (OnlineSurveyContent) requestParam.get("onlineSurveyContent");
			onlineSurveyContentService.modifyOnlineSurveyContent(onlineSurveyContent, questionId);
			
		//删除调查问题	
		}else if(dealMethod.equals("delete")){
			log.info("删除调查问题");
			String questionId = (String) requestParam.get("questionId");
			onlineSurveyContentService.deleteOnlineSurveyContentByQuestionId(questionId);
		
		//初始化样式设置	
		}else if(dealMethod.equals("detailSytle")){
			ArrayList<String> arrayList = onlineSurveyContentService.getStyle(siteId);
			responseParam.put("arrayList", arrayList);
		
		//保存样式
    	}else if(dealMethod.equals("editStyle")){
    		 OnlineSurveyContentForm form = (OnlineSurveyContentForm) requestParam.get("form");
    		 onlineSurveyContentService.setStyle(form, siteId);
    		 
    	//查找问题	
		}else if(dealMethod.equals("findQuestionList")){
			Pagination pagination = (Pagination) requestParam.get("pagination");
			pagination = onlineSurveyContentService.findQuestionByThemeIdPage(pagination, nodeId);
			responseParam.put("pagination", pagination);
		}
			
		/*
		// 网上调查问题内容添加
		}else if (dealMethod.equals("detail")) {
			// 获取参数
			OnlineSurveyContent onlineSurveyContent = (OnlineSurveyContent) requestParam.get("onlineSurveyContent");
			OnlineSurvey onlineSurvey = new OnlineSurvey();
			onlineSurvey.setId(idContent);
			onlineSurveyContent.setOnlineSurvey(onlineSurvey);
			// 添加的网上调查问题名称
//			String message = onlineSurveyContentService.addOnlineSurveyContentService(onlineSurveyContent);
//			responseParam.put("message", message);
			responseParam.put("idContent", idContent);
	    //网上调查问题名称内容分页
	    } else if (dealMethod.equals("onlineQueryAnswer")) {
			responseParam.put("idContent", idContent);
			// 获取查询分页内容对象
			pagination = (Pagination) requestParam.get("pagination");
//			responseParam.put("pagination", onlineSurveyContentService.findOnlineQueryAnswer(pagination, idContent));
			log.info("查询调查内容分页成功.");
			//网上调查问题名称内容修改
		} else if (dealMethod.equals("onlineSurveyContentUpdate")) {
			// 获取修改对象idContent
			idContent = (String) requestParam.get("idContent");
			// 获取对应的网上调查问题名称表的id
			id = (String) requestParam.get("id");
			// 创建网上调查问题具体内容实体
			OnlineSurveyContent onlineSurveyContent = new OnlineSurveyContent();
			// 创建网上调查问题具体内容所对应的对象
			onlineSurveyContent = onlineSurveyContentService.findOnlineContent(idContent);
			// 网上调查问题具体内容所对应的对象存入responseParam对象中
			responseParam.put("onlineSurveyContent", onlineSurveyContent);
			// 修改对象存入responseParam对象中
			responseParam.put("idContent", idContent);
			// 所在类型id
			responseParam.put("id", id);
		} else if (dealMethod.equals("onlineSurveyContentUpdateed")) {
			// 获取修改对象idContent
			idContent = (String) requestParam.get("idContent");
			// 获取对应的网上调查问题名称表的id
			id = (String) requestParam.get("id");
			OnlineSurveyContent onlineSurveyContent = (OnlineSurveyContent) requestParam.get("onlineSurveyContent");
			// 获取修改对象idContent
			responseParam.put("idContent", idContent);
			// 获取对应的网上调查问题名称表的id
			responseParam.put("id", id);
			// 获取修改对象idContent
//			String message = onlineSurveyContentService.modifyOnlineSurveyContent(onlineSurveyContent, idContent);
//			responseParam.put("message", message);
//			 网上调查问题名称表删除
		} else if (dealMethod.equals("delete")) {
			// 获取修改对象idContent
			idContent = (String) requestParam.get("idContent");
			id = (String) requestParam.get("id");
			if (idContent != null && !idContent.equals("")) {
				// 用于删除的网上调查问题名称表
//				String message = onlineSurveyContentService.deleteOnlineSurveyService(idContent);
				// 将修改对象idContent存入responseParam中
				responseParam.put("idContent", idContent);
				// 将修改对象的类型id存入responseParam中
				responseParam.put("id", id);
				// 将消息对象存入responseParam中
//				responseParam.put("message", message);
			}
			//网上调查问题分页
		} else if (dealMethod.equals("onlineAnswerQuestions")) {
			// 所在类型id
			id = (String) requestParam.get("id");
			responseParam.put("id", id);
			// System.out.println(id);
			// 获取查询分页内容对象
			pagination = (Pagination) requestParam.get("pagination");
			// 存储分页对象
			responseParam.put("pagination", onlineSurveyContentService.finOnlineAnswerQuestions(pagination, id));
			// responseParam.put("pagination",
			// onlineSurveyService.finOnlineAnswerQuestions(pagination, id));
			log.info("查询调查内容分页成功.");
		}else  if (dealMethod.equals("onlineStyle")) {
			 //获取对应的网站id
			 String   siteID   =  requestEvent.getSiteid();
			OnlineSurveyContentForm	form = (OnlineSurveyContentForm) requestParam.get("form");
			onlineSurveyContentService.setStyle(form,siteID);
			//保存样式
		}else if(dealMethod.equals("detailSytle")){
			 String   siteID   =  requestEvent.getSiteid();
			OnlineSurveyContentForm	form = (OnlineSurveyContentForm) requestParam.get("form");
			ArrayList<String> arrayList=onlineSurveyContentService.getStyle(siteID);
			responseParam.put("arrayList", arrayList);
			//保存样式
	    	} else if(dealMethod.equals("editStyle")){
	    		 String   siteID   =  requestEvent.getSiteid();
	    	 OnlineSurveyContentForm	form = (OnlineSurveyContentForm) requestParam.get("form");
	    	// onlineSurveyContentService.setStyle((String)form.getStyleContent(),siteID);
	    	 onlineSurveyContentService.setStyle(form,siteID);
		    	 
		}*/
		responseParam.put("nodeId", nodeId);
		responseParam.put("categoryId", categoryId);
	}

	@Override
	public ResponseEvent validateData(RequestEvent arg0) throws Exception {
		return null;
	}

	public OnlineSurveyContentService getOnlineSurveyContentService() {
		return onlineSurveyContentService;
	}

	public void setOnlineSurveyContentService(OnlineSurveyContentService onlineSurveyContentService) {
		this.onlineSurveyContentService = onlineSurveyContentService;
	}

}
