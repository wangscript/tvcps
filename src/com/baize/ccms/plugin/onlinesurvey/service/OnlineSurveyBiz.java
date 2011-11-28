/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.plugin.onlinesurvey.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baize.ccms.biz.sitemanager.domain.Site;
import com.baize.ccms.biz.usermanager.domain.User;
import com.baize.ccms.plugin.onlinesurvey.domain.OnlineSurvey;
import com.baize.common.core.dao.Pagination;
import com.baize.common.core.service.BaseService;
import com.baize.common.core.util.DateUtil;
import com.baize.common.core.util.StringUtil;
import com.baize.common.core.web.event.RequestEvent;
import com.baize.common.core.web.event.ResponseEvent;

/**
 * <p>标题: 网上调业务处理类</p>
 * <p>描述: 网上调的查看,删除等功能</p>
 * <p>模块:网上调</p>
 * <p>版权: Copyright (c) 2009南京百泽网络科技有限公司
 * @author 包坤涛
 * @version 1.0
 * @since 2009-6-14 下午02:26:33 
 * @history（历次修订内容、修订人、修订时间等） 
*/

public class OnlineSurveyBiz extends BaseService {

	/**注入消息业务对象**/
	private OnlineSurveyService onlineSurveyService;
	
	@SuppressWarnings("unchecked")
	@Override
	public void performTask(RequestEvent requestEvent, ResponseEvent responseEvent)
		throws Exception {
		String dealMethod = requestEvent.getDealMethod();
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
	    Map<Object,Object> responseParam = responseEvent.getRespMapParam();
	    String sessionId = requestEvent.getSessionID();
	    String siteId = requestEvent.getSiteid();
	    //调查类别id
	    String categoryId = (String) requestParam.get("categoryId");
	    String nodeId = (String) requestParam.get("nodeId");
	    
	    
		//获取当前用户id
		String id = (String)requestParam.get("id");
		String idm = (String)requestParam.get("idm");
		responseParam.put("idm",idm);
		if (null != categoryId){
			if (categoryId.equals("4")){
				categoryId = "2";
			}        
		}
		 
		//获取网上调查分页 
		if (dealMethod.equals("")){
			log.debug("开始查询调查内容分页数据");
		    Pagination pagination = (Pagination) requestParam.get("pagination");
			responseParam.put("pagination", onlineSurveyService.finOnlineSurveyData(pagination, categoryId));
			log.info("查询调查内容分页成功");
		
		//网上调查首页
		}else if(dealMethod.equals("indexonline")){
			log.debug("网上调查首页");
			List<OnlineSurvey> list = onlineSurveyService.findOnlineSurvey("f020001");
			List<OnlineSurvey> list1 = onlineSurveyService.findOnlineSurvey("f020002");
			List<OnlineSurvey> onlineSurveys = this.list(list);
			List<OnlineSurvey> onlineSurveysList = this.list(list1);
			responseParam.put("onlineSurveys", onlineSurveys);
			responseParam.put("onlineSurveysList", onlineSurveysList);
			 
		//查找调查类别
		}else if(dealMethod.equals("onlinedetail")){
			if(!StringUtil.isEmpty(nodeId)){
				OnlineSurvey onlineSurvey = onlineSurveyService.findEntitlyOnline(nodeId); 
			    responseParam.put("onlineSurvey", onlineSurvey );
			}
			
		//添加调查	
		}else if(dealMethod.equals("add")){
			OnlineSurvey onlineSurvey = (OnlineSurvey) requestParam.get("onlineSurvey");
		    User user = new User();
		    user.setId(sessionId);
		    Site site = new Site();
		    site.setId(siteId);
		    onlineSurvey.setUser(user);
		    onlineSurvey.setCategory(categoryId);
		    onlineSurvey.setCreateTime(new Date());
		    onlineSurvey.setSite(site);
			onlineSurveyService.addOnlineSurvey(onlineSurvey);
		
		//修改调查	
		}else if(dealMethod.equals("modify")){
			OnlineSurvey onlineSurvey = (OnlineSurvey) requestParam.get("onlineSurvey");
			onlineSurveyService.modifyOnlineSurvey(onlineSurvey, nodeId);
			
		//删除网上调查主题
		}else if (dealMethod.equals("delete")) {
	        onlineSurveyService.deleteOnlineSurvey(nodeId);
	        
        // 发布在线调查目录    
		}else if(dealMethod.equals("publishSurvery")){
			onlineSurveyService.publishGuestBookDir(siteId);
		}	
		responseParam.put("categoryId", categoryId);
		responseParam.put("nodeId", nodeId);   
	        
	/*		
 		}else if(dealMethod.equals("detail")){
			 // 获取网上调查实体对象
 			OnlineSurvey onlineSurvey = (OnlineSurvey) requestParam.get("onlineSurvey");
		     //用户对象
		     User user=new User();
		     //网站对象
		     Site  site=new Site();
		     //网站id
		     site.setId(siteId);
		     //用户sessionid
		     user.setId(sessionId);
		     //存储用户
		     onlineSurvey.setUser(user);
		     //存储对应的树的nodeId  id
		     onlineSurvey.setCategory(categoryId);
		     //存储对应的时间
		     onlineSurvey.setCreateTime(new Date());
		     //存储对应网站id
		     onlineSurvey.setSite(site);
		     //添加网上调查实体对象反会值为"1"
//			 String message = onlineSurveyService.addOnlineSurveyService(onlineSurvey);
		     //服务器对象发出对应的树的nodeId  id
			 responseParam.put("categoryId", categoryId);
//		     responseParam.put("message", message);
		     
		// 数据 系统设置跳转
	    }else if(dealMethod.equals("onlineAnswer")){
		    log.debug("开始查询调查内容分页数据!");
			Pagination pagination = (Pagination) requestParam.get("pagination");
			responseParam.put("categoryId", categoryId);
			responseParam.put("pagination", onlineSurveyService.finOnlineSurveyData(pagination, categoryId));
			log.info("查询调查内容分页成功.");
			
		}else if(dealMethod.equals("detailanswer")){
			//获取服务器发送网上调查实体
			OnlineSurvey onlineSurvey = (OnlineSurvey) requestParam.get("onlineSurvey");
		    //用户
		    User user=new User();
		    //网站
		    Site  site=new Site();
		    //网站id
		    site.setId(siteId);
		    //用户sessiongid
		    user.setId(sessionId);
		    //保存用户
		    onlineSurvey.setUser(user);
		    //保存树节点id
		    onlineSurvey.setCategory(categoryId);
		    onlineSurvey.setCreateTime(new Date());
		    onlineSurvey.setSite(site);
		    //对网上调查实体保存
//		    String message = onlineSurveyService.addOnlineSurveyService(onlineSurvey);
		    responseParam.put("categoryId", categoryId);
//		    responseParam.put("message", message);
		    
		 }else if(dealMethod.equals("onlineAnswerQuestions")){
			responseParam.put("id", id);
			Pagination pagination = (Pagination) requestParam.get("pagination");
			responseParam.put("pagination", onlineSurveyService.finOnlineAnswerQuestions(pagination, id));
			log.info("查询调查内容分页成功.");	
			
		}else if(dealMethod.equals("onlineSurveyUpdate")){
			//获取客户端发送index.jsp每行分页每行对应的索引id
			String updateID = (String) requestParam.get("updateID");
			//查找需要修改的对象
			OnlineSurvey onlineSurvey = onlineSurveyService.findEntitlyOnline(updateID); 
		    //向服务器发送网上调查实体
		    responseParam.put("onlineSurvey",onlineSurvey );
			responseParam.put("updateID",updateID );
			
		//网上调查实体修改
		}else if(dealMethod.equals("updateOlineSurvey")){   
			  //获取客户端发送index.jsp每行分页每行对应的索引id
			String updateID = (String) requestParam.get("updateId");
			//获取网上调查实体
			OnlineSurvey onlineSurvey = (OnlineSurvey) requestParam.get("onlineSurvey");
			//获取客户端发送id树节点的node id
	 		categoryId = (String) requestParam.get("categoryId");  
			//向服务器发送node id
			responseParam.put("categoryId",categoryId);
			  //获取客户端发送index.jsp每行分页每行对应的索引id进行修改
//	        String	message=onlineSurveyService.modifyOnlineSurvey( onlineSurvey,updateID);
//	        responseParam.put("message",message);  
	        
	     //网上调查实体id删除
		 }else if (dealMethod.equals("delete")) {
	         id = (String) requestParam.get("id");
	         if (id != null && !id.equals("")) {
				// 用于删除作者时使用
	        	 onlineSurveyService.deleteOnlineSurvey(id);
			 }
			 responseParam.put("categoryId", categoryId);
		
		// 发布在线调查目录    
		}else if(dealMethod.equals("publishSurvery")){
			onlineSurveyService.publishGuestBookDir(siteId);
		}*/
		
	}
	
	public OnlineSurveyService getOnlineSurveyService() {
		return onlineSurveyService;
	}
	
	public void setOnlineSurveyService(OnlineSurveyService onlineSurveyService) {
		this.onlineSurveyService = onlineSurveyService;
	}
	
	@Override
	public ResponseEvent validateData(RequestEvent arg0) throws Exception {
		return null;
	}
		
	 /**
     * 网上调查类别
     * @param    list     网上类别 id
     * @param    onlineSurvey  网上类别
     * return arraylist 网上调查类别集合
     */
	 public  List<OnlineSurvey>  list(List<OnlineSurvey> list){
		 ArrayList<OnlineSurvey> arraylist = new ArrayList<OnlineSurvey>();
		 for(int i = 0; i < list.size(); i++){
			 OnlineSurvey  onlineSurveyEntitly = (OnlineSurvey)list.get(i);
			 if(null != onlineSurveyEntitly.getStopTime()){
				 String time = DateUtil.toString(onlineSurveyEntitly.getStopTime());
				 onlineSurveyEntitly.setDescription(time);
			 }
			 if(null != onlineSurveyEntitly.getName()){
				 String name = onlineSurveyEntitly.getName().replaceAll("\"", "");
				 onlineSurveyEntitly.setName(name);
			 }
			 arraylist.add(onlineSurveyEntitly);
		}
		return arraylist;
	}
}
