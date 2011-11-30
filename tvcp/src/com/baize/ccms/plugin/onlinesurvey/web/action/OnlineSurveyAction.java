/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.plugin.onlinesurvey.web.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.j2ee.cms.plugin.onlinesurvey.domain.OnlineSurvey;
import com.j2ee.cms.plugin.onlinesurvey.web.form.OnlineSurveyForm;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.util.DateUtil;
import com.j2ee.cms.common.core.util.StringUtil;
import com.j2ee.cms.common.core.web.GeneralAction;
import com.j2ee.cms.common.core.web.event.RequestEvent;
import com.j2ee.cms.common.core.web.event.ResponseEvent;


/**
 * <p>标题: 信件Action</p>
 * <p>描述: 网上调查操作，封装请求对象</p>
 * <p>模块: 网上调查</p>
 * <p>版权: Copyright (c) 2009 
 * @author 包坤涛
 * @version 1.0
 * @since 2009-6-14 下午02:32:22 
 * @history（历次修订内容、修订人、修订时间等） 
*/

public class OnlineSurveyAction extends GeneralAction {

	private String dealMethod = "";
	
	@SuppressWarnings("unchecked")
	@Override
	protected void doFormFillment(ActionForm actionForm, ResponseEvent responseEvent,
			String userIndr) throws Exception {
		OnlineSurveyForm form = (OnlineSurveyForm) actionForm;
		Map<Object, Object> responseParam = responseEvent.getRespMapParam();
		String categoryId = (String) responseParam.get("categoryId");
		//向界面发送对因的树的nodeId  id
		form.setCategoryId(categoryId);
		String nodeId = (String) responseParam.get("nodeId");
		form.setNodeId(nodeId);
		
		
		//服务器消息对象
		String message = (String) responseParam.get("message");
		String id = (String) responseParam.get("id");
		form.setIdm((String) responseParam.get("idm"));
		
		//网上调查问题查询分页  
		if (dealMethod.equals("")){
			form.setPagination((Pagination) responseParam.get("pagination"));
			if(!StringUtil.isEmpty(categoryId)){
				if(categoryId.equals("f020002")){
					this.setRedirectPage("answeronline", userIndr);
				}else if(categoryId.equals("f020001")){
					this.setRedirectPage("normalonline", userIndr);	
				}
			}
			
		//网上调查首页
		}else if(dealMethod.equals("indexonline")){
			List<OnlineSurvey>  list= (List<OnlineSurvey>)responseParam.get("onlineSurveys");
			List<OnlineSurvey>  listOnlineSurvey= (List<OnlineSurvey>)responseParam.get("onlineSurveysList");	
			form.setList(list);
			form.setListOnlineSurvey(listOnlineSurvey);
			this.setRedirectPage("indexonline", userIndr);
		
		//查找调查类别
		}else if(dealMethod.equals("onlinedetail")){
			if(!StringUtil.isEmpty(nodeId)){
				OnlineSurvey onlineSurvey = (OnlineSurvey) responseParam.get("onlineSurvey");
				form.setEndTime(DateUtil.toString(onlineSurvey.getStopTime()));
				form.setOnlineSurvey(onlineSurvey);
			}
			this.setRedirectPage("onlinedetail", userIndr);
			
		//添加调查	
		}else if(dealMethod.equals("add")){
			form.setInfoMessage("添加成功");
			this.setRedirectPage("onlinedetail", userIndr);
		
		//修改调查	
		}else if(dealMethod.equals("modify")){
			form.setInfoMessage("修改成功");
			this.setRedirectPage("onlinedetail", userIndr);
			
		//网上问题调查删除
		}else if (dealMethod.equals("delete")) {
			form.setInfoMessage("删除成功");
			if(!StringUtil.isEmpty(categoryId)){
				if(categoryId.equals("f020002")){
					this.setRedirectPage("answeronline", userIndr);
				}else if(categoryId.equals("f020001")){
					this.setRedirectPage("normalonline", userIndr);	
				}
			}
			
		//发布在线调查目录
		}else if(dealMethod.equals("publishSurvery")){
			form.setInfoMessage("发布在线调查目录成功");
			this.setRedirectPage("publishSuccess", userIndr);
		}
			/*
		} else if(dealMethod.equals("detail")){
			form.setInfoMessage(message);
			this.setRedirectPage("detail", userIndr);
	    
		//页面onLine_detail.jsp添加方法	
	    } else if(dealMethod.equals("add")){
		   this.setRedirectPage("detail", userIndr);
		  
	    //显示调查内容  
	    } else if(dealMethod.equals("onlineAnswer")){
			log.debug("显示调查内容");
			form.setPagination((Pagination) responseParam.get("pagination"));
			this.setRedirectPage("answerQuestion", userIndr);
			
	    //网上调查问题添加
	    } else if(dealMethod.equals("addAnswer")){
			this.setRedirectPage("addAnswer", userIndr);
		
		 //网上调查问题添加实体操作
	     }else if(dealMethod.equals("detailanswer")){
		   //获取对应的树的nodeId  id 	
		   form.setInfoMessage(message);
		   this.setRedirectPage("addAnswer", userIndr);
		
		 //在线问题调查
		 }else if(dealMethod.equals("onlineAnswerQuestions")){
		    form.setId(id);
		    form.setPagination((Pagination) responseParam.get("pagination"));
		    this.setRedirectPage("onlineAnswerQuestions", userIndr);
		
		}else if(dealMethod.equals("concretesurveydetail")){
		    this.setRedirectPage("concretesurveydetail", userIndr);
		
		//从index.jsp提交过来要求修改的
		}else if(dealMethod.equals("onlineSurveyUpdate")){
		  //index.jsp每行分页每行对应的索引id
		  String updateID = (String) responseParam.get("updateID");
		  //为网上调查问题名称对应的form设置id
		  form.setUpdateID(updateID);
		  //网上调查问题名称实体发送到index.jsp
	      form.setOnlineSurvey((OnlineSurvey)responseParam.get("onlineSurvey"));
	      Date date = form.getOnlineSurvey().getStopTime();
	      form.setEndTime(DateUtil.toString(date));
		  this.setRedirectPage("concretesurveyupdate", userIndr);
		
		//修改网上问题调查	
		}else if(dealMethod.equals("updateOlineSurvey")){
		   message = (String) responseParam.get("message");
		   String updateID = (String) responseParam.get("updateID");
		   form.setUpdateID(updateID);
		   form.setInfoMessage(message);
		   this.setRedirectPage("concretesurveyupdate", userIndr);
		
		//网上问题调查删除
		}else if (dealMethod.equals("delete")) {
		  form.setInfoMessage("1");
		  this.setRedirectPage("success", userIndr);
		
		//发布在线调查目录
		}else if(dealMethod.equals("publishSurvery")){
			form.setInfoMessage("发布在线调查目录成功");
			this.setRedirectPage("publishSuccess", userIndr);
		}*/
	}
	
	@Override
	protected void performTask(ActionForm actionForm, RequestEvent requestEvent, String userIndr )
			throws Exception {
		Map<Object, Object> requestParam = requestEvent.getReqMapParam();
		//客户段对象
		HttpServletRequest request = (HttpServletRequest)requestParam.get("HttpServletRequest");
		OnlineSurveyForm  form = (OnlineSurveyForm)actionForm;
		this.dealMethod = form.getDealMethod();
		String  categoryId  = form.getCategoryId();
		String nodeId = form.getNodeId();
		if(StringUtil.isEmpty(nodeId)){
			nodeId = "";
		}
		
		//网上调查问题查询分页 
		if(dealMethod.equals("")){
			form.setQueryKey("findOnlineSurveyFormPage");
			requestParam.put("pagination", form.getPagination());
		
		//网上调查首页 
		}else if(dealMethod.equals("indexonline")){
		
		//查找调查类别
		}else if(dealMethod.equals("onlinedetail")){
			
		//添加调查	
		}else if(dealMethod.equals("add")){
			OnlineSurvey onlineSurvey = form.getOnlineSurvey();
			requestParam.put("onlineSurvey", onlineSurvey);
		
		//修改调查	
		}else if(dealMethod.equals("modify")){
			OnlineSurvey onlineSurvey = form.getOnlineSurvey();
			requestParam.put("onlineSurvey", onlineSurvey);
			
		//删除调查	
		}else if (dealMethod.equals("delete")) {

		}
		
		/*
///////////////////////////////////////////////////////////////////
			String  idm = request.getParameter("id");	
		    form.setQueryKey("findOnlineSurveyFormPage");
		    String id = form.getId();
			OnlineSurvey  onlineSurvey = new OnlineSurvey();	
			onlineSurvey = form.getOnlineSurvey();
			///////////////////////////////////////////////////////////////////
			
	    //添加网上调查内容方法	 
		}else if(dealMethod.equals("detail")){
			requestParam.put("onlineSurvey", onlineSurvey);	
			
		}else if(dealMethod.equals("onlineAnswer")){
			
		}else if(dealMethod.equals("detailanswer")){
			requestParam.put("onlineSurvey", onlineSurvey);	
			
		}else if(dealMethod.equals("onlineAnswerQuestions")){
			requestParam.put("id", id);			                    
			form.setQueryKey("findonlineAnswerQuestionsPage"); 
			requestParam.put("pagination", form.getPagination());
			
		//网上调查实体修改  	
		}else if(dealMethod.equals("onlineSurveyUpdate")){
			//index.jsp每行分页每行对应的索引id
			String	updateID = request.getParameter("updateID");
			//客户端发送index.jsp每行分页每行对应的索引id
			requestParam.put("updateID",updateID );
		
		}else if(dealMethod.equals("updateOlineSurvey")){
			onlineSurvey = form.getOnlineSurvey();
			//form端发送index.jsp每行分页每行对应的索引id
			String  updateId = form.getUpdateID();	
			
			//客户端发送index.jsp每行分页每行对应的索引id
			requestParam.put("updateId", updateId);
			//树节点的id
			requestParam.put("categoryId", categoryId);
			//网上调查实体
			requestParam.put("onlineSurvey", onlineSurvey);
		
		//删除调查	
		}else if (dealMethod.equals("delete")) {
			requestParam.put("id", id);
		}	*/
		requestParam.put("categoryId", categoryId);
		requestParam.put("nodeId", nodeId);
	    
		
		
//	    //客户段请求对应的分页对象
//	    requestParam.put("pagination", form.getPagination());
//	    requestParam.put("idm", idm);
	}
	
	@Override
	//初始化方法
	protected void init(String arg0) throws Exception {
		
	}
}
