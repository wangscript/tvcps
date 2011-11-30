/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.messagemanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.j2ee.cms.biz.messagemanager.domain.SiteMessage;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.service.BaseService;
import com.j2ee.cms.common.core.web.event.RequestEvent;
import com.j2ee.cms.common.core.web.event.ResponseEvent;

/**
 * <p>
 * 标题: 消息业务处理类
 * </p>
 * <p>
 * 描述: 消息添加、删除、发送等功能
 * </p>
 * <p>
 * 模块: 消息管理
 * </p>
 * <p>
 * 版权: Copyright (c) 2009 
 * 
 * @author 杨信
 * @version 1.0
 * @since 2009-5-18 上午10:51:15
 * @history（历次修订内容、修订人、修订时间等）
 */

public class SiteMessageBiz extends BaseService {

	/** 注入消息业务对象 **/
	private SiteMessageService siteMessageService;
	SiteMessage message = new SiteMessage();

	@SuppressWarnings("unchecked")
	@Override
	public void performTask(RequestEvent requestEvent,
			ResponseEvent responseEvent) throws Exception {
		String dealMethod = requestEvent.getDealMethod();
		Map<Object, Object> requestParam = requestEvent.getReqMapParam();
		Map<Object, Object> responseParam = responseEvent.getRespMapParam();
		// 获取当前用户id
		String id = requestEvent.getSessionID();
		// 获取网站Id
		String siteId = requestEvent.getSiteid();

		// 显示所有用户
		if (dealMethod.equals("transforward")) {
			log.info("传值,跳转到写信页面");
		//	String contacterName = (String) requestParam.get("contacterName");
			String strOperationId = (String) requestParam.get("strOperationId");
			
//			String content = (String) requestParam.get("content");
//			String title = (String) requestParam.get("title");
			
			String userfulContacterStr = siteMessageService.findUsefulContacter(id);
			//收件人姓名字符串
			String contacterName = siteMessageService.findContacterNameStr(strOperationId);
			responseParam.put("contacterName", contacterName);
			responseParam.put("strOperationId", strOperationId);
			responseParam.put("userfulContacterStr", userfulContacterStr);
			
//			responseParam.put("content", content);
//			responseParam.put("title", title);

			// 获取收件人姓名字符串
		} else if (dealMethod.equals("addContacterNameStr")) {
			log.info("显示收件人姓名字符串");
			//收件人ids
			String ids = (String) requestParam.get("ids");
			//是否保存至常用联系人
			String check = (String) requestParam.get("check");
			//收件人姓名字符串
		//	String contacterName = siteMessageService.findContacterNameStr(ids);
			if (check.equals("0")) {
				log.info("不保存到常用联系人");
			} else {
				siteMessageService.saveContacter(ids, id);
			}
		//	responseParam.put("contacterName", contacterName);
			
//			responseParam.put("ids", ids);
//			String content = (String)requestParam.get("content");
//			String title = (String) requestParam.get("title");
//			responseParam.put("content", content);
//			responseParam.put("title", title);
			// 发送消息
		} else if (dealMethod.equals("send")) {
			log.info("发送信息");
			String idstr = (String) requestParam.get("idstr");
			message = (SiteMessage) requestParam.get("message");
			responseParam.put("idstr", idstr);
			// 查找常用联系人
			String userfulContacterStr = siteMessageService.findUsefulContacter(id);
			
			responseParam.put("userfulContacterStr", userfulContacterStr);
			String infoMessage = siteMessageService.addMessage(idstr, id, message);
		
			responseParam.put("infoMessage", infoMessage);

			// 删除消息
		} else if (dealMethod.equals("delete")) {
			log.info("删除消息");
			String nodeId = (String) requestParam.get("nodeId");
			String ids = (String) requestParam.get("ids");
			siteMessageService.deleteMessages(ids);
			responseParam.put("nodeId", nodeId);

			// 查看消息详细内容
		} else if (dealMethod.equals("showContent")) {
			log.info("显示消息内容");
			String nodeId = (String) requestParam.get("nodeId");
			String ids = (String) requestParam.get("ids");
			//判断消息是否为已读
			String readed = siteMessageService.hasReaded(ids);
			List list = siteMessageService.findContent(ids, nodeId);
			siteMessageService.modify(ids);
			responseParam.put("list", list);
			responseParam.put("nodeId", nodeId);
			responseParam.put("ids", ids);
			responseParam.put("readed", readed);

			// 加载树
		} else if (dealMethod.equals("getTree")) {
			log.info("加载树的信息");
			// 获得树的treeid
			String treeid = (String) requestParam.get("treeid");
			List<Object> list = new ArrayList<Object>();
			list = siteMessageService.getTreeList(treeid);
			responseParam.put("treelist", list);
			log.info("加载树的信息成功");

			// 目录树---发送消息
		} else if (dealMethod.equals("sendMessage")) {
			log.info("发送消息");
			// 查找常用联系人
			String userfulContacterStr = siteMessageService.findUsefulContacter(id);
			responseParam.put("userfulContacterStr", userfulContacterStr);

			// 收件箱列表
		} else if (dealMethod.equals("receiveMessageBox")
				|| dealMethod.equals("0")) {
			log.info("显示收件箱消息");
			Pagination pagination = (Pagination) requestParam.get("pagination");
			pagination = siteMessageService
			.findReceiveBoxMessage(pagination, id);
			if(pagination.getData().size() == 0){
				pagination.currPage = pagination.currPage-1;
				pagination = siteMessageService
				.findReceiveBoxMessage(pagination, id);
			}
			responseParam.put("pagination", pagination);

			// 发件箱列表
		} else if (dealMethod.equals("sendMessageBox")) {
			log.info("显示发件箱消息");
			Pagination pagination = (Pagination) requestParam.get("pagination");
			pagination = siteMessageService
			.findSendBoxMessage(pagination, id);
			if(pagination.getData().size()==0){
				pagination.currPage = pagination.currPage-1;
				pagination = siteMessageService
				.findSendBoxMessage(pagination, id);
			}
			responseParam.put("pagination", pagination);

			// 转发消息
		} else if (dealMethod.equals("forward")) {
			log.info("转发消息");
			// 查找常用联系人
			String userfulContacterStr = siteMessageService.findUsefulContacter(id);
			responseParam.put("userfulContacterStr", userfulContacterStr);
			String ids = (String) requestParam.get("ids");
			List list = siteMessageService.findContent(ids, "1");
			responseParam.put("list", list);
			requestParam.put("ids", ids);

			// 回复消息
		} else if (dealMethod.equals("reply")) {
			log.info("回复消息");
			// 查找常用联系人
			String userfulContacterStr = siteMessageService.findUsefulContacter(id);
			responseParam.put("userfulContacterStr", userfulContacterStr);
			//消息id
			String ids = (String) requestParam.get("ids");
			String contacterName = siteMessageService.findReplyName(ids);
			//收件人id，覆盖消息id
			ids = siteMessageService.findReplyIds(ids);
			responseParam.put("contacterName", contacterName);
			responseParam.put("id", ids);

			// 管理常用联系人
		} else if (dealMethod.equals("showUsefulContacter")) {
			log.info("管理常用联系人");
			Pagination pagination = (Pagination) requestParam.get("pagination");
			pagination = siteMessageService.findAllUsefulContacter(pagination, id);
			responseParam.put("pagination", pagination);

			// 删除常用联系人
		} else if (dealMethod.equals("deleteUsefulContacter")) {
			log.info("删除常用联系人");
			String ids = (String) requestParam.get("ids");
			siteMessageService.deleteUsefulContacter(ids, id);

			// 通过常用联系人添加收件人
		} else if (dealMethod.equals("addContacterName")) {
			log.info("通过常用联系人添加收件人姓名");
			// 查找常用联系人
			String userfulContacterStr = siteMessageService.findUsefulContacter(id);
			responseParam.put("userfulContacterStr", userfulContacterStr);
			String ids = (String) requestParam.get("contacerId");
			String contacterName = siteMessageService.findContacterNameStr(ids);
			responseParam.put("contacterName", contacterName);
			responseParam.put("ids", ids);
			
			//查询机构下的用户
		} else if(dealMethod.equals("findContacter")) {
 			 log.info("查询机构下的用户");
			 String orgId = (String) requestParam.get("orgId");
			 
			 List list = new ArrayList();
			 if(orgId == null) {
				 //查询系统管理员和网站管理员
				 list = siteMessageService.findSysAdminAndSiteAdmin(siteId);
			 } else {

				 list = siteMessageService.findUserByOrgId(orgId);
			 }
			 responseParam.put("list", list);
		}else if(dealMethod.equals("reback")){
			 HttpServletRequest request = (HttpServletRequest)requestParam.get("HttpServletRequest");
			 responseParam.put("request", request);
			 log.info("返回发送消息");
			 // 查找常用联系人
			 String userfulContacterStr = siteMessageService.findUsefulContacter(id);
			 responseParam.put("userfulContacterStr", userfulContacterStr);
		 }	
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.j2ee.cms.common.core.service.BaseService#validateData(com.j2ee.cms.common
	 * .core.web.event.RequestEvent)
	 */
	@Override
	public ResponseEvent validateData(RequestEvent requestEvent)
			throws Exception {
		return null;
	}

	/**
	 * @param siteMessageService
	 *            the siteMessageService to set
	 */
	public void setSiteMessageService(SiteMessageService siteMessageService) {
		this.siteMessageService = siteMessageService;
	}
}
