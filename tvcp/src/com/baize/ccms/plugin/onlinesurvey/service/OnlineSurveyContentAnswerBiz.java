/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.plugin.onlinesurvey.service;

import java.io.File;
import java.util.Map;

import com.j2ee.cms.plugin.onlinesurvey.domain.OnlineSurveyContent;
import com.j2ee.cms.plugin.onlinesurvey.domain.OnlineSurveyContentAnswer;
import com.j2ee.cms.sys.GlobalConfig;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.service.BaseService;
import com.j2ee.cms.common.core.util.FileUtil;
import com.j2ee.cms.common.core.util.IDFactory;
import com.j2ee.cms.common.core.util.StringUtil;
import com.j2ee.cms.common.core.web.event.RequestEvent;
import com.j2ee.cms.common.core.web.event.ResponseEvent;

/**
 * <p>标题:网上调查处理类</p>
 * <p>描述: 网上调查查看,删除,回复等功能</p>
 * <p>模块: 网上调查</p>
 * <p>版权: Copyright (c) 2009 
 * @author 包坤涛
 * @version 1.0
 * @since 2009-6-14 下午02:26:33 
 * @history（历次修订内容、修订人、修订时间等） 
*/

public class OnlineSurveyContentAnswerBiz extends BaseService {

	/**注入消息业务对象**/
	private OnlineSurveyContentAnswerService onlineSurveyContentAnswerService;
	
	public void performTask(RequestEvent requestEvent, ResponseEvent responseEvent)
		throws Exception {
		
		String dealMethod = requestEvent.getDealMethod();
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		String categoryId = (String) requestParam.get("categoryId");
		String nodeId = (String) requestParam.get("nodeId");
		String questionId = (String) requestParam.get("questionId");
		
        //调查问题答案分页
        if(dealMethod.equals("")){
        	log.debug("调查问题答案分页");
        	Pagination pagination = (Pagination) requestParam.get("pagination");
        	pagination = onlineSurveyContentAnswerService.findOnlineQueryAnswerPage(pagination, questionId);
    		responseParam.put("pagination", pagination);
    		
        //问题答案表单 
	    }else if(dealMethod.equals("detail")){
	    	log.debug("问题答案表单 ");
		   	String answerId = (String) requestParam.get("answerId");
		   	//查找问题答案详细
		   	if(!StringUtil.isEmpty(answerId)){
		   		OnlineSurveyContentAnswer onlineSurveyContentAnswer = onlineSurveyContentAnswerService.findOnlineContentAnswer(answerId);
		   		responseParam.put("onlineSurveyContentAnswer", onlineSurveyContentAnswer);
		   	}
		   	responseParam.put("answerId", answerId);

		//添加问题答案
	    }else if(dealMethod.equals("add")){
	    	log.debug("添加问题答案");
	    	OnlineSurveyContentAnswer onlineSurveyContentAnswer = (OnlineSurveyContentAnswer) requestParam.get("onlineSurveyContentAnswer");
	    	OnlineSurveyContent onlineSurveyContent = new OnlineSurveyContent();
	    	onlineSurveyContent.setId(questionId);
	    	onlineSurveyContentAnswer.setOnlineSurveyContent(onlineSurveyContent);
	    	onlineSurveyContentAnswerService.addOnlineSurveyContentAnswer(onlineSurveyContentAnswer);
	    	
	    //修改问题答案
	    }else if(dealMethod.equals("modify")){
	    	log.debug("修改问题答案");
	    	String answerId = (String) requestParam.get("answerId");
	    	OnlineSurveyContentAnswer onlineSurveyContentAnswer = (OnlineSurveyContentAnswer) requestParam.get("onlineSurveyContentAnswer");
	    	onlineSurveyContentAnswerService.modifyOnlineSurveyContentAnswer(onlineSurveyContentAnswer, answerId);
	    	 
	    //删除问题答案	 
	    }else if(dealMethod.equals("delete")){
	    	log.debug("删除问题答案");
	    	String answerId = (String) requestParam.get("answerId");
	    	onlineSurveyContentAnswerService.deleteOnlineSurveyAnswer(answerId); 
	    
	    //调查结果	
	    }else if(dealMethod.equals("answerList")){
			Pagination pagination = (Pagination) requestParam.get("pagination");
			pagination = onlineSurveyContentAnswerService.findOnlineResultPage(pagination);
			responseParam.put("pagination", pagination);	
		
		//图表详情
		}else if(dealMethod.equals("chart")){
			String id = IDFactory.getId();
			String path = GlobalConfig.appRealPath + "/plugin/onlineSurvey_manager/"+id+".png";  //"/images/onlineSurvey/f.png";
			//先删除所有存在的png图片
			String dir = GlobalConfig.appRealPath+"/plugin/onlineSurvey_manager";
			File file = new File(dir);
			File[] files = file.listFiles();
			for(int i = 0; i < files.length; i++){
				if(files[i].getName().endsWith(".png")){
					files[i].delete();
				}
			}
			onlineSurveyContentAnswerService.imageChart3DToJpg("网上调查统计图", "网上调查", "网上反馈量", path, questionId);
			responseParam.put("path", "/"+ GlobalConfig.appName +"/plugin/onlineSurvey_manager/"+id+".png");
			
		//饼状图图表详情
		}else if(dealMethod.equals("piechart")){
			String id = IDFactory.getId();
			String path = GlobalConfig.appRealPath + "/plugin/onlineSurvey_manager/"+id+".png";  //"/images/onlineSurvey/f.png";
			//先删除所有存在的png图片
			String dir = GlobalConfig.appRealPath+"/plugin/onlineSurvey_manager";
			File file = new File(dir);
			File[] files = file.listFiles();
			for(int i = 0; i < files.length; i++){
				if(files[i].getName().endsWith(".png")){
					files[i].delete();
				}
			}
			onlineSurveyContentAnswerService.imagepieChart3DToJpg("网上调查统计图", "网上调查", "网上反馈量", path, questionId);
			responseParam.put("path", "/"+ GlobalConfig.appName +"/plugin/onlineSurvey_manager/"+id+".png");
			
	    }        
        responseParam.put("categoryId", categoryId); 
		responseParam.put("nodeId", nodeId);
		responseParam.put("questionId", questionId);
        

		/*//获取当前用户id  String id = requestEvent.getSessionID();
		String  idContent=(String)requestParam.get("idContent");
        String  id=(String)requestParam.get("id");
        String  idClass=(String)requestParam.get("idClass");
        String  idDelete=(String)requestParam.get("idDelete");
        responseParam.put("idClass", idClass);
        responseParam.put("id", id);*/
    	/*	
        //查找 网上调查问题具体内容表答案
        }else if (dealMethod.equals("findAll")){  
			pagination = (Pagination) requestParam.get("pagination");
	//		responseParam.put("pagination", onlineSurveyContentAnswerService.findOnlineQueryAnswer(pagination, id));
			responseParam.put("id", id);
				                
		//查找在线答案		  
	  } else if (dealMethod.equals("onlineAnswer")){
          id=(String)requestParam.get("id");
	      categoryId=(String)requestParam.get("categoryId");
	      responseParam.put("categoryId",categoryId);
	      OnlineSurveyContentAnswer  onlineSurveyContentAnswer=(OnlineSurveyContentAnswer)requestParam.get("onlineSurveyContentAnswer");
	   if (null!=onlineSurveyContentAnswer.getAnswer()&&(!onlineSurveyContentAnswer.getAnswer().equals(""))){
		  OnlineSurveyContent  onlineSurveyContent=new OnlineSurveyContent();
		  onlineSurveyContent.setId(id);
	      onlineSurveyContentAnswer.setOnlineSurveyContent(onlineSurveyContent);
	      String message= onlineSurveyContentAnswerService.addOnlineSurveyContentService(onlineSurveyContentAnswer);
	      responseParam.put("message", message);
	  }
		// 修改网上调查问题具体内容
	 } else if (dealMethod.equals("onlineSurveyContentUpdate")) {
	     id=(String)requestParam.get("id");
	     String ids=(String)requestParam.get("ids");
	     OnlineSurveyContentAnswer  onlineSurveyContentAnswer=new OnlineSurveyContentAnswer();
	     onlineSurveyContentAnswer= onlineSurveyContentAnswerService.findOnlineContent(id);
	     responseParam.put("onlineSurveyContentAnswer", onlineSurveyContentAnswer);
	     responseParam.put("id", id);
	     responseParam.put("ids",ids);
	     responseParam.put("categoryId",categoryId);
	  //网上调查问题具体内容
	 } else if (dealMethod.equals("onlineSurveyContentUpdateed")){
		 id=(String)requestParam.get("id");
		 OnlineSurveyContentAnswer	  onlineSurveyContentAnswer=(OnlineSurveyContentAnswer)requestParam.get("onlineSurveyContentAnswer");
         responseParam.put("id", id); 
         String message=onlineSurveyContentAnswerService.modifyOnlineSurveyContentAnswer( onlineSurveyContentAnswer,id);
         responseParam.put("message", message); 
      //根据类型id进行删除   
     } else if (dealMethod.equals("delete")) {
    	 //删除类型id
         	categoryId= (String) requestParam.get("categoryId");
    	 //删除对象id
         	idDelete = (String) requestParam.get("idDelete");
        if (idDelete != null && !idDelete.equals("")) {
			// 用于删除作者时使用
          onlineSurveyContentAnswerService.deleteOnlineSurveyService(idDelete);   	 
		}
    	responseParam.put("idDelete", idDelete);
		responseParam.put("id", id);
		responseParam.put("categoryId", categoryId);
		//问答案调查显示
		}else if(dealMethod.equals("answerList")){
		pagination = (Pagination) requestParam.get("pagination");
		pagination=onlineSurveyContentAnswerService.findOnlineQueryAnswer(pagination);
		responseParam.put("pagination", onlineSurveyContentAnswerService.findOnlineQueryAnswer(pagination));
		responseParam.put("id", id);
		
		 
		 }else if(dealMethod.equals("images")){
			 System.out.println(GlobalConfig.appRealPath);
			 String path=GlobalConfig.appRealPath+"/images/onlineSurvey/f.png";
			 responseParam.put("path", path);	 
			 onlineSurveyContentAnswerService.imageChart3DToJpg("网上调查统计图", "网上调查", 
		                "网上反馈量", path);
		}    */                     
	}
	@Override
	public ResponseEvent validateData(RequestEvent arg0) throws Exception {
		return null;
	}

	public OnlineSurveyContentAnswerService getOnlineSurveyContentAnswerService() {
		return onlineSurveyContentAnswerService;
	}

	public void setOnlineSurveyContentAnswerService(
			OnlineSurveyContentAnswerService onlineSurveyContentAnswerService) {
		this.onlineSurveyContentAnswerService = onlineSurveyContentAnswerService;
	}
}
