/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.plugin.onlinesurvey.web.action;

import java.util.ArrayList;
import java.util.Map;

import org.apache.struts.action.ActionForm;

import com.j2ee.cms.plugin.onlinesurvey.domain.OnlineSurvey;
import com.j2ee.cms.plugin.onlinesurvey.domain.OnlineSurveyContent;
import com.j2ee.cms.plugin.onlinesurvey.web.form.OnlineSurveyContentForm;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.util.StringUtil;
import com.j2ee.cms.common.core.web.GeneralAction;
import com.j2ee.cms.common.core.web.event.RequestEvent;
import com.j2ee.cms.common.core.web.event.ResponseEvent;


/**
 * <p>标题: 网上调查Action</p>
 * <p>描述: 网上调查的不同操作，封装请求对象</p>
 * <p>模块: 网上调查</p>
 * <p>版权: Copyright (c) 2009 
 * @author 包坤涛
 * @version 1.0
 * @since 2009-6-14 下午02:32:22 
 * @history（历次修订内容、修订人、修订时间等） 
*/

public class OnlineSurveyContentAction extends GeneralAction {

	private String dealMethod = "";
	
	/* (non-Javadoc)
	 * @see com.j2ee.cms.common.core.web.GeneralAction#doFormFillment(org.apache.struts.action.ActionForm, com.j2ee.cms.common.core.web.event.ResponseEvent, java.lang.String)
	 */
	@Override
	protected void doFormFillment(ActionForm actionForm, ResponseEvent responseEvent,
			String userIndr) throws Exception {
		OnlineSurveyContentForm form = (OnlineSurveyContentForm) actionForm;
		Map<Object, Object> responseParam = responseEvent.getRespMapParam();
		String categoryId = (String) responseParam.get("categoryId");
		form.setCategoryId(categoryId);
		String nodeId = (String) responseParam.get("nodeId");
		form.setNodeId(nodeId);
		
		form.setIdm((String) responseParam.get("idm"));
		
		//网上调查问题分页
		if(dealMethod.equals("")){
			Pagination  pagination = (Pagination) responseParam.get("pagination");
			form.setPagination(pagination);
			this.setRedirectPage("onlineQuestions", userIndr);
			
		//问题表单（添加、修改）	
		}else if(dealMethod.equals("detail")){
			String questionId = (String) responseParam.get("questionId");
			form.setQuestionId(questionId);
			if(!StringUtil.isEmpty(questionId)){
				OnlineSurveyContent onlineSurveyContent = (OnlineSurveyContent) responseParam.get("onlineSurveyContent");
				form.setOnlineSurveyContent(onlineSurveyContent);
			}
			this.setRedirectPage("detail", userIndr);
		
		//添加问题
		}else if(dealMethod.equals("add")){
			form.setInfoMessage("添加问题成功");
			this.setRedirectPage("detail", userIndr);
			
		//修改问题	
		}else if(dealMethod.equals("modify")){
			form.setInfoMessage("修改问题成功");
			this.setRedirectPage("detail", userIndr);
			
		//删除问题
		}else if(dealMethod.equals("delete")){
			form.setInfoMessage("删除问题成功");
			this.setRedirectPage("onlineQuestions", userIndr);
			
		//初始化样式设置	
		}else if (dealMethod.equals("detailSytle")) {
			ArrayList<String> arrayList = (ArrayList<String>) responseParam.get("arrayList");
	    	if(null != arrayList.get(0) &&(!arrayList.get(0).equals(""))){
	    		String stylecontent = (String)arrayList.get(0);   
	    		form.setStyleContent(stylecontent);
	    	}
	    	if(null != arrayList.get(1) &&(!arrayList.get(1).equals(""))){
	    		String listStyle = (String)arrayList.get(1);   
	    		form.setListStyle(listStyle);
	    	}
	    	if(null != arrayList.get(2) &&(!arrayList.get(2).equals(""))){
	    		String  row = (String)arrayList.get(2);   
	    		form.setColCount(row);
	    	}
	    	if(null != arrayList.get(3) &&(!arrayList.get(3).equals(""))){
	    		String  width = (String)arrayList.get(3);
	    		form.setTextwidth(width);
	    	}
	    	if(null != arrayList.get(4) &&(!arrayList.get(4).equals(""))){
	    		String  height = (String)arrayList.get(4);
	    		form.setTexthight(height);
	    	}
		    this.setRedirectPage("onlineStyle", userIndr);
		
		//保存样式    
		} else if (dealMethod.equals("editStyle")) {
			form.setInfoMessage("样式成功保存成功");
			this.setRedirectPage("onlineStyle", userIndr);
			
		//查找问题分页	
		}else if(dealMethod.equals("findQuestionList")){
			Pagination pagination = (Pagination) responseParam.get("pagination");
			form.setPagination(pagination);
			this.setRedirectPage("findQuestionList", userIndr);
		}
			
			
		/*	
		//网上调查问题具体内容添加方法	
		}else if (dealMethod.equals("detail")){
			 //网上调查问题名称表的id进行插入
			  String idContent= (String)responseParam.get("idContent");
			  String message= (String)responseParam.get("message");
			  //返回网上调查问题名称表的id
//			  form.setIdContent(idContent);
			  //信息网上调查消息
			  form.setInfoMessage(message);
			  this.setRedirectPage("detail", userIndr);
		  } else if(dealMethod.equals("onlineSurveyContentUpdate")){
			 //网上调查问题名称表
			  OnlineSurveyContent   onlineSurveyContent=new OnlineSurveyContent();
			  //网上调查问题名称表
			  onlineSurveyContent =(OnlineSurveyContent)responseParam.get("onlineSurveyContent");
			  //获取id客户端id
			  String id= (String)responseParam.get("id");
			  //获取
			  String idContent= (String)responseParam.get("idContent");
			  form.setOnlineSurveyContent(onlineSurveyContent);
//			  form.setIdContent(idContent);
			  form.setId(id);
			  this.setRedirectPage("onlineSurveyContentUpdate", userIndr);
			  //网上调查问题名称修改
		 } else if(dealMethod.equals("onlineSurveyContentUpdateed")){
			  String idContent= (String)responseParam.get("idContent");
//		    	 form.setIdContent(idContent);
		    	 String id= (String)responseParam.get("id");
		    	 String message= (String)responseParam.get("message");
		         form.setId(id);
		         form.setInfoMessage(message);
			  this.setRedirectPage("onlineSurveyContentUpdate", userIndr);
			  //网上调查问题名称删除
		 } else if (dealMethod.equals("delete")) {
			     String idContent= (String)responseParam.get("idContent");
			     String id= (String)responseParam.get("id");
		    	 form.setId(id);
		    	 String message= (String)responseParam.get("message");
		    	 form.setInfoMessage(message);
		    	 this.setRedirectPage("onlineAnswerQuestions", userIndr);  	 
		 }else if(dealMethod.equals("concretesurveydetail")){
						this.setRedirectPage("concretesurveydetail", userIndr);
		    	 //网上调查问题分页
		 } else if(dealMethod.equals("onlineAnswerQuestions")){
		        Pagination  pagination = (Pagination) responseParam.get("pagination");
				System.out.println( responseParam.get("id"));
				form.setId((String)responseParam.get("id"));
				form.setPagination((Pagination) responseParam.get("pagination"));
				this.setRedirectPage("onlineAnswerQuestions", userIndr);  
				//在线样式
    	} else if (dealMethod.equals("onlineStyle")) {
		        this.setRedirectPage("onlineStyle", userIndr);
		        // 保存样式        
	     }else if (dealMethod.equals("detailSytle")) {
	    	   ArrayList<String> arrayList=(ArrayList<String>)responseParam.get("arrayList");
	    	   if(null!=arrayList.get(0) &&(!arrayList.get(0).equals(""))){
	    		   String  stylecontent=(String)arrayList.get(0);   
	    		   form.setStyleContent(stylecontent);
	    	   }
	    	   if(null!=arrayList.get(1) &&(!arrayList.get(1).equals(""))){
	    		   String  row=(String)arrayList.get(1);   
	    		   form.setColCount(row);
	    	   }
	    	   if(null!=arrayList.get(2) &&(!arrayList.get(2).equals(""))){
	    		   String  width=(String)arrayList.get(2);
	    		   form.setTextwidth(width);
	    	   }
	    	   if(null!=arrayList.get(3) &&(!arrayList.get(3).equals(""))){
	    		   String  height=(String)arrayList.get(3);
	    		   form.setTexthight(height);
	    	   }
		        this.setRedirectPage("onlineStyle", userIndr);
		} else if (dealMethod.equals("editStyle")) {
			form.setInfoMessage("保存成功");
			this.setRedirectPage("onlineStyle", userIndr);
		}*/
	}
	@Override
	protected void performTask(ActionForm actionForm, RequestEvent requestEvent, String userIndr )
			throws Exception {
		Map<Object, Object> requestParam = requestEvent.getReqMapParam();
		OnlineSurveyContentForm form = (OnlineSurveyContentForm)actionForm;
		this.dealMethod = form.getDealMethod();  
		// 获得调查主题的id
		String nodeId = form.getNodeId();
		if(StringUtil.isEmpty(nodeId)){
			nodeId = "";
		}
		// 调查类别id
		String categoryId = form.getCategoryId();
		
		//网上调查问题分页
		if(dealMethod.equals("")){
			form.setQueryKey("findonlineAnswerQuestionsPage");
			requestParam.put("pagination", form.getPagination());
			
		//问题表单详细页面（添加、修改）	
		}else if(dealMethod.equals("detail")){
			String questionId = form.getQuestionId();
			requestParam.put("questionId", questionId);
			
		//添加在线调查问题	
		}else if(dealMethod.equals("add")){
			OnlineSurveyContent onlineSurveyContent = form.getOnlineSurveyContent();
			OnlineSurvey onlineSurvey = new OnlineSurvey();
			onlineSurvey.setId(nodeId);
			onlineSurveyContent.setOnlineSurvey(onlineSurvey);
			requestParam.put("onlineSurveyContent", onlineSurveyContent);
			
		//修改在线调查问题	
		}else if(dealMethod.equals("modify")){
			String questionId = form.getQuestionId();
			OnlineSurveyContent onlineSurveyContent = form.getOnlineSurveyContent();
			OnlineSurvey onlineSurvey = new OnlineSurvey();
			onlineSurvey.setId(nodeId);
			onlineSurveyContent.setOnlineSurvey(onlineSurvey);
			requestParam.put("onlineSurveyContent", onlineSurveyContent);
			requestParam.put("questionId", questionId);
		
		//删除问题	
		}else if(dealMethod.equals("delete")){
			String questionId = form.getQuestionId();
			requestParam.put("questionId", questionId);
		
		//初始化样式设置
		}else if (dealMethod.equals("detailSytle")){
			//requestParam.put("form", form);
			
		// 保存样式
		}else if(dealMethod.equals("editStyle")){
			requestParam.put("form", form);
		
		//查找问题分页	
		}else if(dealMethod.equals("findQuestionList")){
			form.setQueryKey("findonlineSurveyContentByOnlineSurveryIdPage");
			requestParam.put("pagination", form.getPagination());
		}
		
	/*	//网上调查问题添加
		}else if(dealMethod.equals("detail")){
			requestParam.put("idContent", idContent);
			requestParam.put("onlineSurveyContent", onlineSurveyContent);
    	  
		//网上调查问题分页	  
	    }else if(dealMethod.equals("onlineQueryAnswer")){ 
		   requestParam.put("idContent", idContent); 
       	   form.setQueryKey("findonlineQueryAnswer");
	       requestParam.put("pagination", form.getPagination());
	       
		//网上调查问题修改
		}else if(dealMethod.equals("onlineSurveyContentUpdate")){
    	   request = (HttpServletRequest)requestParam.get("HttpServletRequest");
           idContent  =request.getParameter("updateID");
           String  id  =request.getParameter("id");
           requestParam.put("idContent", idContent);
           requestParam.put("id", id);
           requestParam.put("onlineSurveyContent", onlineSurveyContent);
		
        //网上调查问题内容修改
		}else if(dealMethod.equals("onlineSurveyContentUpdateed")){
			//获取客户端对象
    	    request = (HttpServletRequest)requestParam.get("HttpServletRequest");
    	    //获取
    	    idContent  =request.getParameter("idContent");
    	    String id=form.getId();
    	    requestParam.put("id", id);
    	    requestParam.put("idContent", idContent);
    	    requestParam.put("onlineSurveyContent", onlineSurveyContent);
        
    	//网上调查内容删除  
		}else if(dealMethod.equals("delete")) {
			requestParam.put("idContent", idContent);
			String  id=form.getId();
			requestParam.put("id", id);
		
		//网上调查问题分页	
		}else if(dealMethod.equals("onlineAnswerQuestions")){
		    request = (HttpServletRequest)requestParam.get("HttpServletRequest");
		    String id=request.getParameter("id");
		    requestParam.put("id", id);		
		    form.setQueryKey("findonlineAnswerQuestionsPage");
		    requestParam.put("pagination", form.getPagination());
		
		//在线样式
		}else if(dealMethod.equals("onlineStyle")) {
			requestParam.put("form", form);
		
		}else if (dealMethod.equals("detailSytle")) {
			requestParam.put("form", form);
			// 保存样式
		}else if(dealMethod.equals("editStyle")){
			requestParam.put("form", form);
		}*/
		requestParam.put("categoryId", categoryId);
		requestParam.put("nodeId", nodeId);
	}

	@Override
	protected void init(String arg0) throws Exception {
		
	}
}