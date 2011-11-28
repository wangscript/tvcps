/**
 * project：通用内容管理系统
 * Company: 南京瀚沃信息科技有限责任公司
*/
package com.baize.ccms.plugin.letterbox.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.baize.ccms.biz.usermanager.domain.Organization;
import com.baize.ccms.biz.usermanager.domain.User;
import com.baize.ccms.plugin.letterbox.domain.Letter;
import com.baize.ccms.plugin.letterbox.domain.LetterCategory;
import com.baize.ccms.plugin.letterbox.domain.LetterReply;
import com.baize.ccms.plugin.letterbox.domain.TransferRecord;
import com.baize.common.core.dao.Pagination;
import com.baize.common.core.service.BaseService;
import com.baize.common.core.web.event.RequestEvent;
import com.baize.common.core.web.event.ResponseEvent;

/**
 * <p>标题: 信件业务处理类</p>
 * <p>描述: 信件的查看,删除,回复等功能</p>
 * <p>模块: 信件箱</p>
 * <p>版权: Copyright (c) 2009南京瀚沃信息科技有限责任公司
 * @author 杨信
 * @version 1.0
 * @since 2009-6-14 下午02:26:33 
 * @history（历次修订内容、修订人、修订时间等） 
*/

public class LetterBiz extends BaseService {

	/**注入消息业务对象**/
	private LetterService letterService;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void performTask(RequestEvent requestEvent, ResponseEvent responseEvent)
	throws Exception {
		String dealMethod = requestEvent.getDealMethod();
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		
		//获取当前用户id
		String id = requestEvent.getSessionID();
		String siteId = requestEvent.getSiteid();
		Letter letter = new Letter();
		LetterReply letterReply = new LetterReply();
		User user = new User();
		LetterCategory letterCategory = new LetterCategory();
		Organization organization = new Organization();
		
		 //加载树	
		if(dealMethod.equals("getTree")) {
			log.info("加载树的信息");
			//获得树的treeid
			String treeid = (String) requestParam.get("treeid");
			List<Object> list = new ArrayList<Object>();
			list = letterService.getTreeList(treeid);
			responseParam.put("treelist", list);
			log.info("加载树的信息成功");
			
		 //显示待受理信件
		} else if(dealMethod.equals("waitForAccept")) {
			log.info("显示待受理信件");
			Pagination pagination = (Pagination) requestParam.get("pagination");
			responseParam.put("pagination",letterService.findWaittingAcceptLetters(pagination));
			
		 //显示待处理信件
		} else if(dealMethod.equals("waitForDealwith")) {
			log.info("显示待处理信件");
			Pagination pagination = (Pagination) requestParam.get("pagination");
			responseParam.put("pagination",letterService.findWaittingDealwithLetters(pagination));
			
		 //显示已处理信件
		} else if(dealMethod.equals("done")) {
			log.info("显示已处理信件");
			Pagination pagination = (Pagination) requestParam.get("pagination");
			responseParam.put("pagination",letterService.findDoneLetters(pagination));
			
		 //显示全部信件，包括待受理,待处理,已处理
		} else if(dealMethod.equals("all")) {
			log.info("显示全部信件");
			Pagination pagination = (Pagination) requestParam.get("pagination");
			responseParam.put("pagination",letterService.findAllLetters(pagination));
			
		 //回复信件前的数据处理
		} else if(dealMethod.equals("beforReply")) {
			log.info("回复信件前的数据处理");	
			String ids = (String) requestParam.get("ids");
			letter = letterService.findLetterByKey(ids);
			letterCategory = letterService.findLetterCategoryByKey(ids);
			//获取转办前后的部门
			List list = letterService.findOrgName(ids);
			responseParam.put("letter",letter);
			responseParam.put("letterCategory", letterCategory);
			responseParam.put("list", list);
			
		 //回复信件
		} else if(dealMethod.equals("reply")) {
			log.info("回复信件");
			//信件id
			String idStr = String.valueOf(requestParam.get("letterId")) ;
			letterReply = (LetterReply) requestParam.get("letterReply");
			//保存回复人
			String userId = id;
			user.setId(userId);
			letterReply.setReplyor(user);
			//修改信件状态为已回复
			letterService.modifyLetterStatus(idStr,Letter.LETTER_STATUS_DEALED);
			letterService.saveReplyLetter(letterReply);
			
		 //从待受理列表删除信件
		} else if(dealMethod.equals("deleteAcceptLetter")) {
			log.info("从待受理列表删除信件");
			String ids = (String) requestParam.get("ids");
			letterService.deleteAcceptList(ids);
			
		 //从待处理列表删除信件
		} else if(dealMethod.equals("deleteDealwithLetter")) {
			log.info("从待处理列表删除信件");
			String ids = (String) requestParam.get("ids");
			letterService.deleteDealwithList(ids);
			
		 //从已处理列表删除信件
		} else if(dealMethod.equals("deleteDoneLetter")) {
			log.info("从已处理列表删除信件");
			String ids = (String) requestParam.get("ids");
			letterService.deleteDoneList(ids);
			
		 //从全部信件列表删除信件
		} else if(dealMethod.equals("deleteAllLetter")) {
			log.info("从全部信件列表删除信件");
			String ids = (String) requestParam.get("ids");
			letterService.deleteAllList(ids);
			
		 //显示部门列表
		} else if(dealMethod.equals("showOrgList")) {
			log.info("显示部门列表");
			String letterId = (String) requestParam.get("letterId");
			Pagination pagination = (Pagination) requestParam.get("pagination");
			pagination = letterService.findOrganization(pagination);
			responseParam.put("pagination", pagination);
			responseParam.put("letterId", letterId);
			
		 //受理信件
		} else if(dealMethod.equals("accept")) {
			log.info("受理信件");	
			String ids = (String) requestParam.get("ids");
			//设置信件状态为已受理
			letterService.modifyLetterStatus(ids, Letter.LETTER_STATUS_WAITDEAL);
			
		 //生成转办信件表单
		} else if(dealMethod.equals("transferLetter")) {
			log.info("生成转办信件表单");
			//ids为转办后的部门id
			String organizationId = (String) requestParam.get("organizationId");
			String letterId = (String) requestParam.get("letterId");
			//查询转办后的部门
			organization = letterService.findOrganizationByKey(organizationId);
			//通过查找信件找到转办前部门
			letter = letterService.findLetterByKey(letterId);
			//查找转办人,id为当前用户的id
			user = letterService.findTransferUserByKey(id);
			responseParam.put("organization",organization);
			responseParam.put("letter",letter);
			responseParam.put("user",user);
			
	     //确认转办并保存记录
		} else if(dealMethod.equals("transferRecord")) {
			log.info("确认转办并保存记录");
			String letterId = (String) requestParam.get("letterId");
			String toOrgId = (String) requestParam.get("toOrgId");
			letterService.modifyTransfered(toOrgId, letterId);
			TransferRecord transferRecord = (TransferRecord) requestParam.get("transferRecord");
			letterService.addTransferRecord(transferRecord);
			
		 //查看转办记录
		} else if(dealMethod.equals("viewTransferRecord")) {
			log.info("查看转办记录");
			String letterId = (String) requestParam.get("letterId");
			List list = letterService.findTransferRecord(letterId);
			responseParam.put("list",list);
			
		 //查看用户信件内容
		} else if(dealMethod.equals("showLetter")) {
			log.info("查看用户信件内容");
			//ids要查看信件的id
			String ids = (String) requestParam.get("ids");
			organization = letterService.findOrganizationById(ids);
			letter = letterService.findLetterByKey(ids);
			letterCategory = letterService.findLetterCategoryByKey(ids);
			responseParam.put("organization",organization);
			responseParam.put("letter",letter);
			responseParam.put("letterCategory", letterCategory);
			
		 //在受理列表中显示信件类别
		} else  if(dealMethod.equals("showCategory")) {
			log.info("在受理列表中显示信件类别");
			String letterId = (String) requestParam.get("letterId");
			Pagination pagination = (Pagination) requestParam.get("pagination");
			pagination = letterService.findCategoryList(pagination);
			responseParam.put("pagination", pagination);
			responseParam.put("letterId", letterId);
			
		 //在受理列表中修改信件类别
		} else  if(dealMethod.equals("modifyCatagory")) {
			log.info("在受理列表中修改信件类别");
			String letterId = (String) requestParam.get("letterId");
			String ids = (String) requestParam.get("ids");
			//修改类别
			letterService.modifyLetterCategory(ids, letterId);
			//修改后查询信件内容
			organization = letterService.findOrganizationById(letterId);
			letter = letterService.findLetterByKey(letterId);
			letterCategory = letterService.findLetterCategoryByKey(letterId);
			responseParam.put("organization",organization);
			responseParam.put("letter",letter);
			responseParam.put("letterCategory", letterCategory);
			
		 //查看已处理的信件内容
		} else  if(dealMethod.equals("showDealedLetter")) {
			log.info("查看已处理的信件内容");
			String letterId = (String) requestParam.get("letterId");
			//信件内容
			letter = letterService.findLetterByKey(letterId);
			letterCategory = letterService.findLetterCategoryByKey(letterId);
			//获取转办前后的部门
			List list = letterService.findOrgName(letterId);
			responseParam.put("letter",letter);
			responseParam.put("letterCategory", letterCategory);
			responseParam.put("list", list);
			//回复内容
			String replyContent = letterService.findReplyContent(letterId);
			responseParam.put("replyContent", replyContent);
		
		// 发布互动信箱目录	
		} else if(dealMethod.equals("publishLetter")){
			letterService.publishLetterDir(siteId);
		}
	}
	
	public void setLetterService(LetterService letterService) {
		this.letterService = letterService;
	}
	
	@Override
	public ResponseEvent validateData(RequestEvent requestEvent)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
