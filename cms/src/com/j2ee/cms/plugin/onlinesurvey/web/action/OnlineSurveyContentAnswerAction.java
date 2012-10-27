/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.plugin.onlinesurvey.web.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.j2ee.cms.plugin.onlinesurvey.domain.OnlineSurveyContent;
import com.j2ee.cms.plugin.onlinesurvey.domain.OnlineSurveyContentAnswer;
import com.j2ee.cms.plugin.onlinesurvey.web.form.OnlineSurveyContentAnswerForm;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.util.StringUtil;
import com.j2ee.cms.common.core.web.GeneralAction;
import com.j2ee.cms.common.core.web.event.RequestEvent;
import com.j2ee.cms.common.core.web.event.ResponseEvent;


/**
 * <p>标题: 信件Action</p>
 * <p>描述:调查问题答案对象</p>
 * <p>模块:网上问题调查/p>
 * <p>版权: Copyright (c) 2009 
 * @author 包坤涛
 * @version 1.0
 * @since 2009-6-14 下午02:32:22 
 * @history（历次修订内容、修订人、修订时间等） 
*/

public class OnlineSurveyContentAnswerAction extends GeneralAction {

	private String dealMethod = "";
	@SuppressWarnings("unchecked")
	@Override
	protected void doFormFillment(ActionForm actionForm, ResponseEvent responseEvent, 	String userIndr) throws Exception {
		OnlineSurveyContentAnswerForm   form = (OnlineSurveyContentAnswerForm)actionForm;
		Map<Object, Object> responseParam = responseEvent.getRespMapParam();
		String categoryId = (String) responseParam.get("categoryId");
		String nodeId = (String) responseParam.get("nodeId");
		String questionId = (String) responseParam.get("questionId");
		form.setCategoryId(categoryId);
		form.setNodeId(nodeId);
		form.setQuestionId(questionId);
		
		//调查问题答案查找分页
        if(dealMethod.equals("")){
        	form.setPagination((Pagination) responseParam.get("pagination"));
        	this.setRedirectPage("list", userIndr);
        	
        //问题答案表单 
	    }else if(dealMethod.equals("detail")){
	    	String answerId = (String) responseParam.get("answerId");
	    	form.setAnswerId(answerId);
	    	if(!StringUtil.isEmpty(answerId)){
	    		OnlineSurveyContentAnswer onlineSurveyContentAnswer = (OnlineSurveyContentAnswer)responseParam.get("onlineSurveyContentAnswer");
	    		form.setOnlineSurveyContentAnswer(onlineSurveyContentAnswer);
	    	}
	    	this.setRedirectPage("detail", userIndr);
	    	
	    //添加问题答案
	    }else if(dealMethod.equals("add")){
	    	form.setInfoMessage("添加答案成功");
	    	this.setRedirectPage("detail", userIndr);
	    	
	    //修改问题答案
	    }else if(dealMethod.equals("modify")){
	    	form.setInfoMessage("修改答案成功");
	    	this.setRedirectPage("detail", userIndr);
	    	 
	    //删除问题答案	 
	    }else if(dealMethod.equals("delete")){
	    	form.setInfoMessage("删除答案成功");
	    	this.setRedirectPage("list", userIndr);

		//调查结果列表	
	    }else if(dealMethod.equals("answerList")){
        	form.setPagination((Pagination) responseParam.get("pagination"));
        	this.setRedirectPage("answerList", userIndr);		
		
        //柱状图图表详情	
	    }else if(dealMethod.equals("chart")){
	    	String path = (String) responseParam.get("path");
        	form.setPath(path);
        	this.setRedirectPage("chart", userIndr);
        	
        //饼状图图表详情
		}else if(dealMethod.equals("piechart")){
			String path = (String) responseParam.get("path");
        	form.setPath(path);
        	this.setRedirectPage("chart", userIndr);
	    }
        
		/*String message = (String) responseParam.get("message");
		String idClass = (String) responseParam.get("idClass");
		String idDelete = (String) responseParam.get("idDelete");
		form.setIdClass(idClass);
		form.setIdDelete(idDelete);
		form.setCategoryId((String) responseParam.get("categoryId"));
		String kk=(String) responseParam.get("id");
	    form.setId((String) responseParam.get("id"));
    	  //调查问题答案查找
        if (dealMethod.equals("findAll")){
        	form.setPagination((Pagination) responseParam.get("pagination"));
        	String id = (String) responseParam.get("id");
        	String categoryId = (String) responseParam.get("categoryId");
        	form.setId(id);
    		form.setCategoryId(categoryId);
    		form.setInfoMessage(message);     
        	this.setRedirectPage("findAllanswer", userIndr);
        	//调查问题答案添加
        } else if(dealMethod.equals("onlineAnswer")){
        	 String  categoryId=(String) responseParam.get("categoryId");
        	    form.setInfoMessage(message) ;
        	
        	   this.setRedirectPage("onlineAnswer", userIndr);
        	 //调查问题答调查问题答案修改
        } else if(dealMethod.equals("onlineSurveyContentUpdate")){   
        	OnlineSurveyContentAnswer onlineSurveyContentAnswer=new OnlineSurveyContentAnswer();
        	onlineSurveyContentAnswer=(OnlineSurveyContentAnswer)responseParam.get("onlineSurveyContentAnswer");
        	String id= (String)responseParam.get("id");
        	String ids=(String)responseParam.get("ids");
        	String  categoryId=(String) responseParam.get("categoryId");
        	form.setOnlineSurveyContentAnswer(onlineSurveyContentAnswer);
            form.setId(id);
            form.setIds(ids);
            form.setCategoryId(categoryId);
            
			this.setRedirectPage("onlineSurveyContentAnswerUpdate", userIndr);
		    //调查问题答调查问题答案修改完
		 } else if(dealMethod.equals("onlineSurveyContentUpdateed")){
		      String  id=(String)responseParam.get("id");	 
			 form.setId(id);
			 form.setInfoMessage(message);
			 form.setCategoryId((String)responseParam.get("categoryId"));
		   this.setRedirectPage("onlineSurveyContentAnswerUpdate", userIndr);
		   //调查问题答调查问题答案删除
		 } else if (dealMethod.equals("delete")) {
	    	 //删除类型id
			 String  categoryId=(String) responseParam.get("categoryId");
			//被删除的对象id
			 String  id=(String) responseParam.get("id");
	    	 form.setCategoryId(categoryId);
	    	 form.setInfoMessage("1");
	    	 this.setRedirectPage("findAllanswer", userIndr);
	     } else if(dealMethod.equals("answerList")){
	        	form.setPagination((Pagination) responseParam.get("pagination"));
	        	String id = (String) responseParam.get("id");
	        	form.setId(id);
	        this.setRedirectPage("answerList", userIndr);
	        }else if(dealMethod.equals("images")){
	        	String path=(String)responseParam.get("path");
	        	form.setPath(path);
	          this.setRedirectPage("images", userIndr);	
	        }   */                     
	}
	@Override
	protected void performTask(ActionForm actionForm, RequestEvent requestEvent, String userIndr )
			throws Exception {
		 Map<Object, Object> requestParam = requestEvent.getReqMapParam();
	     HttpServletRequest request = (HttpServletRequest)requestParam.get("HttpServletRequest");
	     this.dealMethod = request.getParameter("dealMethod");
	     OnlineSurveyContentAnswerForm form = (OnlineSurveyContentAnswerForm)actionForm;
	     //调查类别id
	     String categoryId = form.getCategoryId();
	     //主题id
	     String nodeId = form.getNodeId();
	     //问题id
	     String questionId = form.getQuestionId();
	     
	     //调查问题答案查找分页
	     if(dealMethod.equals("")){
	    	 form.setQueryKey("onlineSurveyContentAnswerPage");
	    	 requestParam.put("pagination", form.getPagination());
	    	
	     //问题答案表单 
	     }else if(dealMethod.equals("detail")){
	    	 String answerId = form.getAnswerId();
	    	 if(StringUtil.isEmpty(answerId)){
	    		 answerId = "";
	    	 }	  
	    	 requestParam.put("answerId", answerId);
	     
	     //添加问题答案
	     }else if(dealMethod.equals("add")){
	    	 OnlineSurveyContentAnswer onlineSurveyContentAnswer = form.getOnlineSurveyContentAnswer();
	    	 requestParam.put("onlineSurveyContentAnswer", onlineSurveyContentAnswer);
	    	
	     //修改问题答案
	     }else if(dealMethod.equals("modify")){
	    	 String answerId = form.getAnswerId();
	    	 OnlineSurveyContentAnswer onlineSurveyContentAnswer = form.getOnlineSurveyContentAnswer();
	    	 requestParam.put("onlineSurveyContentAnswer", onlineSurveyContentAnswer);
	    	 requestParam.put("answerId", answerId);
	    	 
	     //删除问题答案	 
	     }else if(dealMethod.equals("delete")){
	    	 String answerId = form.getAnswerId();
	    	 requestParam.put("answerId", answerId);
	    	
	     //调查结果
		 }else if(dealMethod.equals("answerList")){
			 form.setQueryKey("onlineResultPage");
			 requestParam.put("pagination", form.getPagination());
		 
		 //柱状图图表详情
		 }else if(dealMethod.equals("chart")){
		 
		 //饼状图图表详情
		 }else if(dealMethod.equals("piechart")){
		 
		 }
	     requestParam.put("categoryId", categoryId);
	     requestParam.put("nodeId", nodeId);
	     requestParam.put("questionId", questionId);	 
	     
	     /*String graphURL = request.getContextPath();
	     String id=request.getParameter("id");
	     String idDelete=request.getParameter("idDelete");
	     String idClass=request.getParameter("idClass");
	     requestParam.put("idClass", idClass);
	     OnlineSurveyContentAnswer   onlineSurveyContentAnswer = form.getOnlineSurveyContentAnswer();
	     */
	    /*	 
	     }else if (dealMethod.equals("findAll")){        
	    	 form.setQueryKey("onlineSurveyContentFindAnswerPage");
	    	 requestParam.put("pagination", form.getPagination());
	    	 requestParam.put("id", id);
	    	 
	     //调查问题答案查找	  
	     } else if(dealMethod.equals("onlineAnswer")){
	    	 id=form.getId();
		  requestParam.put("onlineSurveyContentAnswer", onlineSurveyContentAnswer);
		  requestParam.put("id", id);
        	//调查问题答案修改	
         } else if(dealMethod.equals("onlineSurveyContentUpdate")){
            if(null==idClass){
            	idClass=form.getIdClass();	 
        	 }
	        String ids=request.getParameter("ids");
        	 id  =request.getParameter("id");
        	 requestParam.put("id", id);
        	 requestParam.put("ids", ids);
	         requestParam.put("onlineSurveyContentAnswer", onlineSurveyContentAnswer);
	     } else if(dealMethod.equals("onlineSurveyContentUpdateed")){
	          id=form.getId();
	          requestParam.put("id", id);
	           String ids=form.getIds();
	          requestParam.put("ids", ids);
	           requestParam.put("onlineSurveyContentAnswer", onlineSurveyContentAnswer);  
	     } else if (dealMethod.equals("delete")) {
	    	 //删除类型id
	    	 //被删除的对象id
	    	 id  =request.getParameter("id");
	    	 requestParam.put("idDelete", idDelete);
		     requestParam.put("id", id);
	     }else if(dealMethod.equals("answerList")){
	    	  form.setQueryKey("answerList");
			  requestParam.put("pagination", form.getPagination());
			  requestParam.put("id", id);
	     }else if(dealMethod.equals("images")){
	    	  
	     }*/
        
        
	}
	@Override
	protected void init(String arg0) throws Exception {
		// TODO Auto-generated method stub	
	}
}
