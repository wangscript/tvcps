/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.messagemanager.web.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;

import com.j2ee.cms.biz.messagemanager.domain.SiteMessage;
import com.j2ee.cms.biz.messagemanager.web.form.SiteMessageForm;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.util.DateUtil;
import com.j2ee.cms.common.core.web.GeneralAction;
import com.j2ee.cms.common.core.web.event.RequestEvent;
import com.j2ee.cms.common.core.web.event.ResponseEvent;

/**
 * <p>标题: 消息Action</p>
 * <p>描述: 管理消息的不同操作，封装请求对象</p>
 * <p>模块: 消息管理</p>
 * <p>版权: Copyright (c) 2009 
 * @author 杨信
 * @version 1.0
 * @since 2009-5-18 上午11:28:03 
 * @history（历次修订内容、修订人、修订时间等） 
*/

public class SiteMessageAction extends GeneralAction {
	
	private String dealMethod = "";

	@SuppressWarnings("unchecked")
		@Override
	protected void doFormFillment(ActionForm actionForm, ResponseEvent responseEvent, String userIndr) {
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		SiteMessageForm form = (SiteMessageForm) actionForm;
		 //显示所有用户
		if(dealMethod.equals("transforward")) {
			log.info("传值,跳转到写信页面");
			String contacterName = (String) responseParam.get("contacterName");
//			String content = (String) responseParam.get("content");
//			String title = (String) responseParam.get("title");
			
			form.setContacterName(contacterName);
			String strOperationId = (String) responseParam.get("strOperationId");
			form.setIdstr(strOperationId);
			String userfulContacterStr = (String) responseParam.get("userfulContacterStr");
			form.setUserfulContacterStr(userfulContacterStr);
			
//			form.setContent(content);
//			form.setTitle(title);
			
			this.setRedirectPage("success", userIndr);
			
			//用于显示收件人名称字符串
		} else  if(dealMethod.equals("addContacterNameStr")) {
			log.info("用于显示收件人名称");
			//查询常用联系人
			String userfulContacterStr = (String) responseParam.get("userfulContacterStr");
			form.setUserfulContacterStr(userfulContacterStr);
		//	String contacterName = (String) responseParam.get("contacterName");
			//收件人ids,即勾选的收件人
			String strOperationId = (String) responseParam.get("ids");
		//	form.setContacterName(contacterName);
			form.setIdstr(strOperationId);
			form.setInfoMessage("添加收件人");
//			String content = (String) responseParam.get("content");
//			String title = (String) responseParam.get("title");
//	
// 			form.setContent(content);
// 			form.setTitle(title);
			this.setRedirectPage("addContacterSuccess", userIndr);
			
			//发送消息
		} else  if(dealMethod.equals("send")) {
			log.info("发送消息");
			String infoMessage = (String) (responseParam.get("infoMessage"));
			form.setInfoMessage(infoMessage);
			//定义一个标志，作为弹出"发送成功"的标志
			String id = "1";
			if(form.getFlags() == 2) {
				form.setId(id);
				if(!infoMessage.equals("发送失败")) {
					form.setInfoMessage("回复成功");
				}
			//	this.setRedirectPage("replyMessageSuccess", userIndr);
			} else {
				//重设一下跳转标志
				form.setFlags(1);
				form.setId(id);
				if(!infoMessage.equals("发送成功")) {
					form.setInfoMessage("发送成功");
				}
			//	this.setRedirectPage("sendMessageSuccess", userIndr);
			}
			this.setRedirectPage("success", userIndr);
			
			//删除消息
		} else  if(dealMethod.equals("delete")) {
			String nodeId = (String) (responseParam.get("nodeId"));
			if(nodeId.equals("1")){
				this.setRedirectPage("deleteReceiveMessageSuccess", userIndr);
			} else{
				this.setRedirectPage("deleteSendMessageSuccess", userIndr);
			}
			
			//查看消息详细内容
		} else  if(dealMethod.equals("showContent")) {
			String readed = (String) responseParam.get("readed");
			String nodeId = (String) responseParam.get("nodeId");
			String ids = (String) responseParam.get("ids");
			List list = (List) responseParam.get("list");
			form.setList(list);
			String title;
			String contacterName;
			Date createTime;
			String content;
			for(int j = 0 ; j < list.size() ; j++){
				Object[] name = (Object[])list.get(j);
				if(name != null){
					title = String.valueOf(name[0]);
					contacterName = String.valueOf(name[1]);
					content = String.valueOf(name[2]);
					createTime = (Date) name[3];
					String dateTime = (DateUtil.toString(createTime)) ;
					form.setTitle(title);
					form.setContacterName(contacterName);
					form.setDateTime(dateTime);
					form.setContent(content);
				}	  
			}
			//消息id
			form.setIds(ids);
			form.setReaded(readed);
			if(nodeId.equals("1")){
				this.setRedirectPage("showReceiveMessageContentSuccess", userIndr);
			} else{
				this.setRedirectPage("showSendMessageContentSuccess", userIndr);
			}
			log.info("查看消息内容成功");	
			
			//获取树的信息
		} else  if (dealMethod.equals("getTree")) {
			form.setJson_list((List)responseParam.get("treelist"));
			this.setRedirectPage("tree", userIndr);
			
			//目录树---发送消息
		} else  if(dealMethod.equals("sendMessage")) {
			//查询常用联系人
			String userfulContacterStr = (String) responseParam.get("userfulContacterStr");
			form.setUserfulContacterStr(userfulContacterStr);
			form.setTitle("");
			form.setContent("");
			this.setRedirectPage("success", userIndr);
			
			//收件箱列表,0代表根目录
		} else  if(dealMethod.equals("receiveMessageBox") || dealMethod.equals("0")) {
			log.info("查看收件箱的消息");
			Pagination pagination  = (Pagination)responseParam.get("pagination");
			form.setPagination(pagination);
			this.setRedirectPage("findReceiveBoxSuccess", userIndr);
				
			//发件箱列表
		} else  if(dealMethod.equals("sendMessageBox")) {
			log.info("查看发件箱的消息");
			Pagination pagination  = (Pagination)responseParam.get("pagination");
			form.setPagination(pagination);
			this.setRedirectPage("findSendBoxSuccess", userIndr);
			
			//转发消息
		} else  if (dealMethod.equals("forward")) {
			log.info("转发消息");
			//查询常用联系人
			String userfulContacterStr = (String) responseParam.get("userfulContacterStr");
			form.setUserfulContacterStr(userfulContacterStr);
			List list = (List) responseParam.get("list");
			form.setList(list);
			String title;
			String content;
			for(int j = 0 ; j < list.size() ; j++) {
				Object[] name = (Object[])list.get(j);
				if(name != null){
					title = String.valueOf(name[0]);
					content = String.valueOf(name[2]);
					form.setTitle(title);
					form.setContent(content);
				}	  
			}
			form.setFlags(2);
			this.setRedirectPage("success", userIndr);
			
			//回复消息
		} else  if(dealMethod.equals("reply")) {
			 log.info("回复消息"); 
			//查询常用联系人
			 String userfulContacterStr = (String) responseParam.get("userfulContacterStr");
			 form.setUserfulContacterStr(userfulContacterStr);
			 String contacterName = (String) responseParam.get("contacterName");
			 //收件人id
			 String strOperationId = (String) responseParam.get("id");
			 form.setContacterName(contacterName);
			 form.setStrOperationId(strOperationId);
			 form.setFlags(2);
			 form.setTitle("");
			 form.setContent("");
			 this.setRedirectPage("addContacter", userIndr);
			
			//管理常用联系人
		 } else  if(dealMethod.equals("showUsefulContacter")) {
			 log.info("管理常用联系人");
			 Pagination pagination  = (Pagination)responseParam.get("pagination");
			 form.setPagination(pagination);
			 this.setRedirectPage("findUsefulContacterSuccess", userIndr);
			 
			//删除常用联系人
		 } else  if(dealMethod.equals("deleteUsefulContacter")) {
			 log.info("删除常用联系人");
			 this.setRedirectPage("deleteUsefulContacterSuccess", userIndr);
			 
			//通过常用联系人添加收件人
		 } else  if(dealMethod.equals("addContacterName")) {
			 log.info("通过常用联系人添加收件人姓名"); 
			//查询常用联系人
			String userfulContacterStr = (String) responseParam.get("userfulContacterStr");
			form.setUserfulContacterStr(userfulContacterStr);
			 String contacterName = (String) responseParam.get("contacterName");
			 String idstr = (String) responseParam.get("ids");
			 form.setContacterName(contacterName);
			 form.setIdstr(idstr);
			 this.setRedirectPage("addContacter", userIndr);
			 
			 //查询机构下的用户
		} else if(dealMethod.equals("findContacter")) {
			log.info("查询机构下的用户");
			List list = (List) responseParam.get("list");
			form.setList(list);
			this.setRedirectPage("userList", userIndr);
		}else if(dealMethod.equals("reback")){
			String userfulContacterStr = (String) responseParam.get("userfulContacterStr");
			form.setUserfulContacterStr(userfulContacterStr);
			HttpServletRequest request = (HttpServletRequest) responseParam.get("request");
			HttpSession session = request.getSession();
			Map map = (Map)session.getAttribute("map");
			if(map != null){
				form.setTitle((String)map.get("title"));
				form.setContent((String)map.get("content"));
				form.setContacterName((String)map.get("names"));
				form.setStrOperationId((String)map.get("ids"));
			}
			
			this.setRedirectPage("success", userIndr);
		}  
	}                        

	@Override
	protected void performTask(ActionForm actionForm, RequestEvent requestEvent, String userIndr) throws Exception {
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		SiteMessageForm form = (SiteMessageForm) actionForm;
		this.dealMethod = form.getDealMethod();
		SiteMessage message = new SiteMessage();
		
		 if(dealMethod.equals("transforward")) {
			log.info("传值,跳转到写信页面");
			String contacterName = form.getContacterName();
			String strOperationId = form.getStrOperationId();
			requestParam.put("contacterName", contacterName);
			requestParam.put("strOperationId", strOperationId);
			
//			String title1 = form.getTitle();
//			if(title1 == null){
//				title1 = "";
//			}
//			requestParam.put("content", new String(form.getContent().getBytes("ISO-8859-1"),"utf-8"));
//			requestParam.put("title", new String(title1.getBytes("ISO-8859-1"),"utf-8"));
			
			//用于显示收件人名称
		 } else  if(dealMethod.equals("addContacterNameStr")) {
			log.info("显示收件人名称");
			//收件人ids
			String ids=form.getStrOperationId();
			//获取添加常用联系人标志
			String check = form.getCheck();
			if(check == "" || check == null) {
				check ="0";
			}
			form.setQueryKey("findUserName");
			requestParam.put("ids", ids);
			requestParam.put("check", check);
//			String content = new String(form.getContent().getBytes("ISO-8859-1"),"utf-8");
//			String title1 = form.getTitle();
//			if(title1 == null){
//				title1 = "";
//			}
//			String title = new String(title1.getBytes("ISO-8859-1"),"utf-8");
//			requestParam.put("content", content);
//			requestParam.put("title", title);
			
		 } else  if(dealMethod.equals("send")) {
			log.info("发送消息");
			String title = form.getTitle();
			String content = form.getContent();
			String idstr = form.getStrOperationId();
			message.setTitle(title);
			message.setContent(content);
			requestParam.put("message", message);
			requestParam.put("idstr", idstr);
			
			//删除消息
		 } else  if(dealMethod.equals("delete")) {
			 log.info("删除消息");
			 //nodeId用来标记是收件箱(1)还是发件箱(2)的消息
			 String nodeId = form.getNodeId();
			 String ids=form.getIds();
			 requestParam.put("ids", ids);
			 requestParam.put("nodeId", nodeId);
			 
			//显示消息详细内容
		 } else  if(dealMethod.equals("showContent")) {
			 log.info("显示消息具体内容");
			 //nodeId用来标记是收件箱(1)还是发件箱(2)的消息
			 String nodeId = form.getNodeId();
			 String ids=form.getIds();
			 requestParam.put("ids", ids);
			 requestParam.put("nodeId", nodeId);
			
			//获取树
		 } else  if(dealMethod.equals("getTree")) {
			String treeid1 = form.getTreeId();
			requestParam.put("treeid", treeid1);
			
			//目录树---发送消息
		 } else  if(dealMethod.equals("sendMessage")) {
			 log.info("发送消息"); 
			 
			//目录树---收件箱,0代表根目录
		 } else  if(dealMethod.equals("receiveMessageBox") || dealMethod.equals("0")) {
			 log.info("收件箱消息列表");
			 form.setQueryKey("findReceiveBoxMessagesByPage");
			 
			//目录树---发件箱
		 } else  if(dealMethod.equals("sendMessageBox")) {
			 log.info("发件箱消息列表");
			 form.setQueryKey("findSendBoxMessagesByPage");
			 
			//转发消息
		 } else  if (dealMethod.equals("forward")) {
			 log.info("转发消息");
			 String ids=form.getIds();
			 requestParam.put("ids", ids); 
			 
			//回复消息
		 } else  if(dealMethod.equals("reply")) {
			 log.info("回复消息"); 
			 String ids=form.getIds();
			 requestParam.put("ids", ids);
			
			//管理常用联系人
		 } else  if(dealMethod.equals("showUsefulContacter")) {
			 log.info("管理常用联系人");
			 //请求管理后，返回时能够得到文本框里面的值并放入session中     如果不需要请删除此处
			 HttpServletRequest request = (HttpServletRequest) requestParam.get("HttpServletRequest");
		 		String message2 = request.getParameter("message");
		 		if(message2 != null&&message2.trim().length()>0){
		 			String[] messages = message2.split("splits");
					if(messages != null && messages.length == 3){
						String title = messages[0];
						String content = messages[1];
						String names = messages[2];
						String ids = messages[3];
						String tt[] = title.split("spaces");
						title = "";
						for(int i=0;i<tt.length;i++){
							title += tt[i]+" ";
						}
						String ct[] = content.split("spaces");
						content = "";
						for(int j=0;j<ct.length;j++){
							content += ct[j]+" ";
						}
						HttpSession session = request.getSession();
						Map map = (Map)session.getAttribute("map");
						if(map == null){
							map = new HashMap();
							session.setAttribute("map", map);
						}
						map.put("title", title);
						map.put("content", content);
						map.put("names", names);
						map.put("ids", ids);
					}				
		 		}
		
			 form.setQueryKey("findUsefulContacterById");
			 
			//删除常用联系人
		 } else  if(dealMethod.equals("deleteUsefulContacter")) {
			 log.info("删除常用联系人");
			 String ids=form.getIds();
			 requestParam.put("ids", ids);
			 
			//通过常用联系人添加收件人
		 } else  if(dealMethod.equals("addContacterName")) {
			 log.info("通过常用联系人添加收件人姓名"); 
			 String contacerId=form.getContacterId();
			 requestParam.put("contacerId", contacerId);
			 
			//查询机构下的用户
		 } else if(dealMethod.equals("findContacter")){
			 log.info("查询机构下的用户");
			 String orgId = form.getOrgId();
			 if(orgId == null || orgId.equals("") || orgId.equals("0")) {
				 orgId = null;
			 }
			 requestParam.put("orgId", orgId);
		 } else if(dealMethod.equals("reback")){
			 log.info("发送消息"); 
		 }		
		 requestParam.put("pagination", form.getPagination());
	}
	
	@Override
	protected void init(String userIndr) throws Exception {
		this.setRedirectPage("success", userIndr);
	}
}
