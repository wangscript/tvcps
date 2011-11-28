/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.plugin.guestbookmanager.service;

import java.util.Map;

import com.baize.ccms.plugin.guestbookmanager.domain.GuestBookContent;
import com.baize.ccms.plugin.guestbookmanager.domain.GuestBookRevert;
import com.baize.ccms.plugin.guestbookmanager.domain.GuestBookSensitiveWord;
import com.baize.ccms.plugin.guestbookmanager.web.form.GuestBookForm;
import com.baize.common.core.dao.Pagination;
import com.baize.common.core.service.BaseService;
import com.baize.common.core.util.DateUtil;
import com.baize.common.core.web.event.RequestEvent;
import com.baize.common.core.web.event.ResponseEvent;

/**
 * <p>
 * 标题: —— 留言板类别管理业务处理类最高接口
 * </p>
 * <p>
 * 描述: —— 简要描述类的职责、实现方式、使用注意事项等
 * </p>
 * <p>
 * 模块: 插件管理
 * </p>
 * <p>
 * 版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * 
 * @author 曹名科
 * @version 1.0
 * @since 2009-5-18 下午04:20:28
 * @history（历次修订内容、修订人、修订时间等） 
 */
 

public class GuestCategoryBiz extends BaseService {
	/** 注入类别DAO */
	private GuestCategoryService guestCategoryService;
	/**注入分发权限设置service*/
	private GuestBookAutorityService  autorityService;

	/**
	 * @param autorityService the autorityService to set
	 */
	public void setAutorityService(GuestBookAutorityService autorityService) {
		this.autorityService = autorityService;
	}

	/**
	 * @param guestCategoryService
	 *            the guestCategoryService to set
	 */
	public void setGuestCategoryService(
			GuestCategoryService guestCategoryService) {
		this.guestCategoryService = guestCategoryService;
	}

	@Override
	public void performTask(RequestEvent requestEvent,
			ResponseEvent responseEvent) throws Exception {
		String dealMethod = requestEvent.getDealMethod();
		Map<Object, Object> requestParam = requestEvent.getReqMapParam();
		Map<Object, Object> responseParam = responseEvent.getRespMapParam();
		// 网站id
		String siteId = requestEvent.getSiteid();
		GuestBookForm form = (GuestBookForm) requestParam.get("guestBookForm");
		String sessionId = requestEvent.getSessionID();
		boolean flag=autorityService.isExistsAutoryUser(sessionId);
		//用于判断登录用户是否有权限显示:分发和未分发按钮
			if(this.isUpSiteAdmin){
				form.setIsPopedom("yes");
			}else if(flag){
				form.setIsPopedom("yes");
			}else {
				form.setIsPopedom("no");
			}
			// 全部
		if (dealMethod.equals("")) {
			Pagination p = (Pagination) requestParam.get("pagination");
		//	System.out.println("biz当前登录的人员权限："+this.isUpSiteAdmin);
			Pagination p1 = guestCategoryService.getPagination(p, siteId,this.isUpSiteAdmin,sessionId);
			if(p1.getData().size() == 0){
				p1.currPage = p1.currPage - 1;
				p1 =guestCategoryService.getPagination(p, siteId,this.isUpSiteAdmin,sessionId);
			}
			responseParam.put("pagination", p1);
			// 未回复
		} else if (dealMethod.equals("replaying")) {
			Pagination p = (Pagination) requestParam.get("pagination");
			Pagination p1 = guestCategoryService.getPaginationByReplay(p,
					"0", siteId,this.isUpSiteAdmin,sessionId);
			if(p1.getData().size() == 0){
				p1.currPage = p1.currPage - 1;
				p1 =guestCategoryService.getPaginationByReplay(p,
						"0", siteId,this.isUpSiteAdmin,sessionId);
			}
			responseParam.put("pagination", p1);
			// 已回复
		} else if (dealMethod.equals("replayed")) {
			Pagination p = (Pagination) requestParam.get("pagination");
			Pagination p1 = guestCategoryService.getPaginationByReplay(p,
					"1", siteId,this.isUpSiteAdmin,sessionId);
			if(p1.getData().size() == 0){
				p1.currPage = p1.currPage - 1;
				p1 = guestCategoryService.getPaginationByReplay(p,
						"1", siteId,this.isUpSiteAdmin,sessionId);
			}
			responseParam.put("pagination", p1);
			// 未审核
		} else if (dealMethod.equals("auditing")) {
			Pagination p = (Pagination) requestParam.get("pagination");
			Pagination p1 = guestCategoryService.getPaginationByAudit(p, "0",
					siteId,this.isUpSiteAdmin,sessionId);
			if(p1.getData().size() == 0){
				p1.currPage = p1.currPage - 1;
				p1 =guestCategoryService.getPaginationByAudit(p, "0",
						siteId,this.isUpSiteAdmin,sessionId);
			}
			responseParam.put("pagination", p1);
			// 已审核
		} else if (dealMethod.equals("audited")) {
			Pagination p = (Pagination) requestParam.get("pagination");
			Pagination p1 = guestCategoryService.getPaginationByAudit(p, "1",
					siteId,this.isUpSiteAdmin,sessionId);
			if(p1.getData().size() == 0){
				p1.currPage = p1.currPage - 1;
				p1 =guestCategoryService.getPaginationByAudit(p, "1",
						siteId,this.isUpSiteAdmin,sessionId);
			}
			responseParam.put("pagination", p1);
			// 不处理
		} else if (dealMethod.equals("nonoice")) {
			Pagination p = (Pagination) requestParam.get("pagination");
			Pagination p1 = guestCategoryService.getPaginationByAudit(p, "2",
					siteId,this.isUpSiteAdmin,sessionId);
			if(p1.getData().size() == 0){
				p1.currPage = p1.currPage - 1;
				p1 =guestCategoryService.getPaginationByAudit(p, "2",
						siteId,this.isUpSiteAdmin,sessionId);
			}
			responseParam.put("pagination", p1);
			// 删除留言
		} else if (dealMethod.equals("deleteContent")) {
			guestCategoryService.deleteGuestContent(form.getIds());
			// 类别分页
		} else if (dealMethod.equals("categoryList")) {
			Pagination p = (Pagination) requestParam.get("pagination");
			Pagination p2 = guestCategoryService.getCategoryPagination(p,
					siteId);
			responseParam.put("pagination", p2);
			// 类别详细
		} else if (dealMethod.equals("categoryDetail")) {
			String category = guestCategoryService.getAllCategoryName(siteId);//将已经存在的类别查询出来，在页面比较
			form.setCategoryName(category);
			form.setGuestCategory(guestCategoryService.getCategoryById(form
					.getIds()));
			// 保存类别
		} else if (dealMethod.equals("categorySave")) {
			String mess = guestCategoryService.modifyCategory(form.getIds(),
					form);
			form.setInfoMessage(mess);
			// 删除类别
		} else if (dealMethod.equals("categoryDelete")) {
			String mess=guestCategoryService.deleteCategoryById(form.getIds());
			Pagination p = (Pagination) requestParam.get("pagination");
			Pagination p2 = guestCategoryService.getCategoryPagination(p,
					siteId);
			if(p2.getData().size() == 0){
				p2.currPage = p2.currPage - 1;
				p2 = guestCategoryService.getCategoryPagination(p,
						siteId);
			}
			responseParam.put("pagination", p2);
			
			form.setInfoMessage(mess);
			
			//显示添加类别页面
		} else if (dealMethod.equals("showCategory")) {
			String category = guestCategoryService.getAllCategoryName(siteId);//将已经存在的类别查询出来，在页面比较
			form.setCategoryName(category);
			// 添加类别
		} else if (dealMethod.equals("addCategory")) {
			String mess = guestCategoryService.addCategory(form, siteId);
			form.setInfoMessage(mess);
			// 留言详细
		} else if (dealMethod.equals("contentDetail")) {
			guestCategoryService.getGuestBookContentById(form);
			// 审核留言
		} else if (dealMethod.equals("auditContent")) {
			String mess=guestCategoryService.modifyauditGuestBookContent(form.getIds());
			form.setInfoMessage(mess);
			// 审核并回复
		} else if (dealMethod.equals("auditAndReplay")) {
			String mess=guestCategoryService.modifyauditAndReplayGuestBookContent(form,sessionId);
			form.setInfoMessage(mess);
			// 保存
		} else if (dealMethod.equals("saveContent")) {
			String mess=guestCategoryService.saveGuestBookContent(form,sessionId);
			form.setInfoMessage(mess);
			// 留言置未审核
		} else if (dealMethod.equals("noaudit")) {
			String mess=guestCategoryService.modifybackAuditGuestBookContent(form.getIds());
			form.setInfoMessage(mess);
			// 不处理
		} else if (dealMethod.equals("handle")) {
			String mess=guestCategoryService.modifynoHandleGuestBookContent(form.getIds());
			form.setInfoMessage(mess);
			//获取用户列表
		}else if(dealMethod.equals("showAllUser")){
			Pagination p = (Pagination) requestParam.get("pagination");
			Pagination p1 =	guestCategoryService.getAllUser(p);
			if(p1.getData().size() == 0){
				p1.currPage = p1.currPage - 1;
				p1 =guestCategoryService.getAllUser(p);
			}
			responseParam.put("pagination", p1);
			//开始将留言分发给指定用户
		}else if(dealMethod.equals("dispenseUser")){
			String dispenId = form.getIds();
			String uid = form.getDispenseId();
			String mess=guestCategoryService.modifyContent(dispenId, uid);
			form.setInfoMessage(mess);
		}
		responseParam.put("guestBookForm", form);
	}

	@Override
	public ResponseEvent validateData(RequestEvent arg0) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
