/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.plugin.letterbox.web.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts.action.ActionForm;

import com.j2ee.cms.biz.usermanager.domain.Organization;
import com.j2ee.cms.biz.usermanager.domain.User;
import com.j2ee.cms.plugin.letterbox.domain.Letter;
import com.j2ee.cms.plugin.letterbox.domain.LetterCategory;
import com.j2ee.cms.plugin.letterbox.domain.LetterReply;
import com.j2ee.cms.plugin.letterbox.domain.TransferRecord;
import com.j2ee.cms.plugin.letterbox.web.form.LetterForm;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.util.DateUtil;
import com.j2ee.cms.common.core.web.GeneralAction;
import com.j2ee.cms.common.core.web.event.RequestEvent;
import com.j2ee.cms.common.core.web.event.ResponseEvent;

/**
 * <p>标题: 信件Action</p>
 * <p>描述: 管理信件的不同操作，封装请求对象</p>
 * <p>模块: 信件箱</p>
 * <p>版权: Copyright (c) 2009 
 * @author 杨信
 * @version 1.0
 * @since 2009-6-14 下午02:32:22 
 * @history（历次修订内容、修订人、修订时间等） 
*/

public class LetterAction extends GeneralAction {

	private String dealMethod = "";
	@SuppressWarnings("unchecked")
	@Override
	protected void doFormFillment(ActionForm actionForm, ResponseEvent responseEvent, String userIndr) {
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		LetterForm form = (LetterForm) actionForm;
		
		 //加载树	
		if(dealMethod.equals("getTree")) {
			log.info("加载树的信息");
			form.setJson_list((List)responseParam.get("treelist"));
			this.setRedirectPage("tree", userIndr);
			
		 //显示待受理信件
		} else if(dealMethod.equals("waitForAccept")) {
			log.info("显示待受理信件");
			Pagination pagination  = (Pagination)responseParam.get("pagination");
			form.setPagination(pagination);
			this.setRedirectPage("showWaittingAcceptLetter", userIndr);
			
		 //显示待处理信件
		} else if(dealMethod.equals("waitForDealwith")) {
			log.info("显示待处理信件");
			Pagination pagination  = (Pagination)responseParam.get("pagination");
			form.setPagination(pagination);
			this.setRedirectPage("showWaittingDealwithLetter", userIndr);
			
		 //显示已处理信件
		} else if(dealMethod.equals("done")) {
			log.info("显示已处理信件");
			Pagination pagination  = (Pagination)responseParam.get("pagination");
			form.setPagination(pagination);
			this.setRedirectPage("findDoneLetterSuccess", userIndr);
			
		 //显示全部信件，包括待受理,待处理,已处理
		} else if(dealMethod.equals("all")) {
			log.info("显示全部信件");
			Pagination pagination  = (Pagination)responseParam.get("pagination");
			form.setPagination(pagination);
			this.setRedirectPage("findAllLetterSuccess", userIndr);
			
		 //回复信件前的数据处理
		} else if(dealMethod.equals("beforReply")) {
			log.info("回复信件前的数据处理");
			Letter letter = (Letter) responseParam.get("letter");
			LetterCategory letterCategory = (LetterCategory) responseParam.get("letterCategory");
			//获得转办前后的部门
			List list = (List) responseParam.get("list");
			String orgFromName = (String) list.get(1);
			String orgToName = (String) list.get(3);
			//信件是否公开
			String openStr;
			if(letter.isOpened()) {
				openStr = "是";
			} else {
				openStr = "否";
			}
			String writeDate = (DateUtil.toString(letter.getSubmitDate())) ;
			form.setWriteDate(writeDate);
			form.setOpenStr(openStr);
			form.setOrgFromName(orgFromName);
			form.setOrgToName(orgToName);
			form.setLetter(letter);
			form.setLetterCategory(letterCategory);
			this.setRedirectPage("beforReplySuccess", userIndr);
			
		 //回复信件
		} else if(dealMethod.equals("reply")) {
			log.info("回复信件");
			this.setRedirectPage("replySuccess", userIndr);
			
		 //从待受理列表删除信件
		} else if(dealMethod.equals("deleteAcceptLetter")) {
			log.info("从待受理列表删除信件");
			this.setRedirectPage("deleteAcceptLetterSuccess", userIndr);
			
		 //从待处理列表删除信件
		} else if(dealMethod.equals("deleteDealwithLetter")) {
			log.info("从待处理列表删除信件");
			this.setRedirectPage("deleteDealwithLetterSuccess", userIndr);
			
		 //从已处理列表删除信件
		} else if(dealMethod.equals("deleteDoneLetter")) {
			log.info("从已处理列表删除信件");
			this.setRedirectPage("deleteDoneLetterSuccess", userIndr);
			
		 //从全部信件列表删除信件
		} else if(dealMethod.equals("deleteAllLetter")) {
			log.info("从全部信件列表删除信件");
			this.setRedirectPage("deleteAllLetterSuccess", userIndr);
			
		 //显示部门列表
		} else if(dealMethod.equals("showOrgList")) {
			log.info("显示部门列表");
			String letterId = (String) responseParam.get("letterId");
			form.setLetterId(letterId);
			form.setPagination((Pagination) responseParam.get("pagination"));
			this.setRedirectPage("showDepartmentSuccess", userIndr);
			
		 //受理信件
		} else if(dealMethod.equals("accept")) {
			log.info("受理信件");
			this.setRedirectPage("acceptSuccess", userIndr);
			log.info("信件受理成功!");
			
		 //生成转办信件表单
		} else if(dealMethod.equals("transferLetter")) {
			log.info("生成转办信件表单");
			Organization organization = (Organization) responseParam.get("organization");
			Letter letter = (Letter) responseParam.get("letter");
			User user = (User) responseParam.get("user");
			form.setOrganization(organization);
			form.setLetter(letter);
			form.setUser(user);
			this.setRedirectPage("transferSuccess", userIndr);
			
		 //确认转办并保存记录
		} else if(dealMethod.equals("transferRecord")) {
			log.info("确认转办并保存记录");
			this.setRedirectPage("transferRecordSuccess", userIndr);
			
		 //查看转办记录
		} else if(dealMethod.equals("viewTransferRecord")) {
			log.info("查看转办记录");
			List list = (List) responseParam.get("list");
			form.setList(list);
			this.setRedirectPage("findRecordSuccess", userIndr);
			
		 //查看用户信件内容
		} else if(dealMethod.equals("showLetter")) {
			log.info("查看用户信件内容");
			Organization organization = (Organization) responseParam.get("organization");
			Letter letter = (Letter) responseParam.get("letter");
			LetterCategory letterCategory = (LetterCategory) responseParam.get("letterCategory");
			//信件是否公开
			String openStr;
			if(letter.isOpened()) {
				openStr = "是";
			} else {
				openStr = "否";
			}
			String writeDate = (DateUtil.toString(letter.getSubmitDate())) ;
			form.setWriteDate(writeDate);
			form.setOpenStr(openStr);
			form.setLetter(letter);
			form.setOrganization(organization);
			form.setLetterCategory(letterCategory);
			this.setRedirectPage("showLetterDetail", userIndr);
			
		 //在受理列表中显示信件类别
		} else  if(dealMethod.equals("showCategory")) {
			log.info("在受理列表中显示信件类别");
			String letterId = (String) responseParam.get("letterId");
			Pagination pagination = (Pagination) responseParam.get("pagination");
			form.setPagination(pagination);
			form.setLetterId(letterId);
			this.setRedirectPage("showCategorySuccess", userIndr);
			
		 //在受理列表中修改信件类别
		} else  if(dealMethod.equals("modifyCatagory")) {
			log.info("在受理列表中修改信件类别");
			this.setRedirectPage("modifyCategorySuccess", userIndr);
			
		 //查看已处理的信件内容
		} else  if(dealMethod.equals("showDealedLetter")) {
			log.info("查看已处理的信件内容");
			Letter letter = (Letter) responseParam.get("letter");
			LetterCategory letterCategory = (LetterCategory) responseParam.get("letterCategory");
			String replyContent = (String) responseParam.get("replyContent");
			//获得转办前后的部门
			List list = (List) responseParam.get("list");
			String orgFromName = (String) list.get(1);
			String orgToName = (String) list.get(3);
			//信件是否公开
			String openStr;
			if(letter.isOpened()) {
				openStr = "是";
			} else {
				openStr = "否";
			}
			String writeDate = (DateUtil.toString(letter.getSubmitDate())) ;
			form.setWriteDate(writeDate);
			form.setOpenStr(openStr);
			form.setOrgFromName(orgFromName);
			form.setOrgToName(orgToName);
			form.setLetter(letter);
			form.setLetterCategory(letterCategory);
			form.setReplyContent(replyContent);
			this.setRedirectPage("findDealedLetterSuccess", userIndr);
		
		// 发布互动信箱目录	
		} else if(dealMethod.equals("publishLetter")){
			form.setInfoMessage("发布互动信箱目录成功");
			this.setRedirectPage("publishSuccess", userIndr);
		}
	}
	
	@Override
	protected void performTask(ActionForm actionForm, RequestEvent requestEvent, String userIndr) throws Exception {		 
		
		LetterForm form = (LetterForm) actionForm;
		this.dealMethod = form.getDealMethod();
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();		
		Letter letter = new Letter();
		LetterReply letterReply = new LetterReply();
		Organization org = new Organization();
		Date date = new Date();
		
		 //加载树	
		if(dealMethod.equals("getTree")) {
			log.info("加载树的信息");
			String treeid = form.getTreeId();
			requestParam.put("treeid", treeid);
			
		 //显示待受理信件
		} else if(dealMethod.equals("waitForAccept")) {
			log.info("显示待受理信件");
			form.setQueryKey("findAcceptLetterByPage");
			
		 //显示待处理信件
		} else if(dealMethod.equals("waitForDealwith")) {
			log.info("显示待处理信件");
			form.setQueryKey("findDealwithLetterByPage");
			
		 //显示已处理信件
		} else if(dealMethod.equals("done")) {
			log.info("显示已处理信件");
			form.setQueryKey("findDoneLetterByPage");
			
		 //显示全部信件，包括待受理,待处理,已处理
		} else if(dealMethod.equals("all")) {
			log.info("显示全部信件");
			form.setQueryKey("findAllLetterByPage");
			
		 //回复信件前的数据处理
		} else if(dealMethod.equals("beforReply")) {
			log.info("回复信件前的数据处理");
			//信件id
			String ids = (String) form.getIds();
			requestParam.put("ids", ids);
			
		 //回复信件
		} else if(dealMethod.equals("reply")) {
			log.info("回复信件");
			//回复内容
			letterReply.setContent(form.getReplyContent());
			String letterId = form.getLetterId();
			letter.setId(letterId);
			letterReply.setLetter(letter);
			letterReply.setReplyDate(date);
			org.setId(form.getOrganizationId());
			letterReply.setOrganization(org);
			
			requestParam.put("letterId", letterId);
			requestParam.put("letterReply", letterReply);
			
		 //从待受理列表删除信件
		} else if(dealMethod.equals("deleteAcceptLetter")) {
			log.info("从待受理列表删除信件");
			String ids=form.getIds();
			requestParam.put("ids", ids);
			
		 //从待处理列表删除信件
		} else if(dealMethod.equals("deleteDealwithLetter")) {
			log.info("从待处理列表删除信件");
			String ids=form.getIds();
			requestParam.put("ids", ids);
			
		 //从已处理列表删除信件
		} else if(dealMethod.equals("deleteDoneLetter")) {
			log.info("从已处理列表删除信件");
			String ids=form.getIds();
			requestParam.put("ids", ids);
			
		 //从全部信件列表删除信件
		} else if(dealMethod.equals("deleteAllLetter")) {
			log.info("从全部信件列表删除信件");
			String ids=form.getIds();
			requestParam.put("ids", ids);
			
		 //受理信件
		} else if(dealMethod.equals("accept")) {
			log.info("受理信件");
			String ids=form.getIds();
			requestParam.put("ids", ids);
			
		 //显示部门列表
		} else if(dealMethod.equals("showOrgList")) {
			log.info("显示部门列表");
			//页面跳转标志
			int flag = form.getFlag();
			if(flag == 1) {
				//获取要转办的信件id
				String letterId=form.getIds();
				requestParam.put("letterId", letterId);
				form.setQueryKey("findOrgPage");
			} else {
				String letterId = form.getLetterId();
				requestParam.put("letterId", letterId);
				form.setQueryKey("findOrgPage");
			}
			
		 //生成转办信件表单
		} else if(dealMethod.equals("transferLetter")) {
			log.info("生成转办信件表单");
			//获取要转办的信件id
			String letterId = form.getLetterId();
			String organizationId = form.getOrganizationId();
			requestParam.put("letterId", letterId);
			requestParam.put("organizationId", organizationId);
			
		 //确认转办并保存记录
		} else if(dealMethod.equals("transferRecord")) {
			log.info("确认转办并保存记录");
			TransferRecord transferRecord = null;
			transferRecord = form.getTransferRecord();
			
			Date transferDate = new Date();
			transferRecord.setTransferDate(transferDate);
			//设置转办前部门
			Organization orgFrom = new Organization();
			orgFrom.setId(form.getFromOrgId());
			transferRecord.setFromOrg(orgFrom);
			//设置转办后部门
			Organization orgTo = new Organization();
			orgTo.setId(form.getToOrgId());
			transferRecord.setToOrg(orgTo);
			//设置转办人
			User user = new User();
			user.setId(form.getTransferUserId());
			transferRecord.setTransferUser(user);
			//设置信件
			letter.setId(form.getLetterId());
			transferRecord.setLetter(letter);
			//获得信件id,用来修改转办标记
			String letterId = form.getLetterId();
			String toOrgId = form.getToOrgId();
			requestParam.put("letterId", letterId);
			requestParam.put("toOrgId", toOrgId);
			requestParam.put("transferRecord", transferRecord);
			
		 //查看转办记录
		} else if(dealMethod.equals("viewTransferRecord")) {
			log.info("查看转办记录");
			String letterId = form.getLetterId();
			requestParam.put("letterId", letterId);
			
		 //查看用户信件内容
		} else if(dealMethod.equals("showLetter")) {
			log.info("查看用户信件内容");
			//信件id
			String ids = form.getIds();
			requestParam.put("ids", ids);
			
		 //在受理列表中显示信件类别
		} else  if(dealMethod.equals("showCategory")) {
			log.info("在受理列表中显示信件类别");
			String letterId = form.getIds();
			requestParam.put("letterId", letterId);
			form.setQueryKey("findCategoryByList");
			
		 //在受理列表中修改信件类别
		} else  if(dealMethod.equals("modifyCatagory")) {
			log.info("在受理列表中修改信件类别");
			String letterId = form.getLetterId();
			String ids = form.getIds();
			requestParam.put("letterId", letterId);
			requestParam.put("ids", ids);
			
		 //查看已处理的信件内容
		} else  if(dealMethod.equals("showDealedLetter")) {
			log.info("查看已处理的信件内容");
			//信件id
			String letterId = form.getIds();
			requestParam.put("letterId", letterId);
		
		// 发布互动信箱目录	
		} else if(dealMethod.equals("publishLetter")){
			
		}
	}
	
	@Override
	protected void init(String arg0) throws Exception {
		
	}

}
