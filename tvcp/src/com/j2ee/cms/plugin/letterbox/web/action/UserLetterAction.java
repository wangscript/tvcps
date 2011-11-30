/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.plugin.letterbox.web.action;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.j2ee.cms.biz.unitmanager.analyzer.TitleLinkAnalyzer;
import com.j2ee.cms.biz.usermanager.domain.Member;
import com.j2ee.cms.biz.usermanager.domain.Organization;
import com.j2ee.cms.plugin.guestbookmanager.web.form.GuestBookForm;
import com.j2ee.cms.plugin.letterbox.domain.Letter;
import com.j2ee.cms.plugin.letterbox.domain.LetterCategory;
import com.j2ee.cms.plugin.letterbox.service.LetterService;
import com.j2ee.cms.plugin.letterbox.web.form.LetterForm;
import com.j2ee.cms.common.core.dao.Pagination;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 通用平台</p>
 * <p>版权: Copyright (c) 2009 
 * @author 杨信
 * @version 1.0
 * @since 2009-6-16 下午05:38:38 
 * @history（历次修订内容、修订人、修订时间等） 
*/

public class UserLetterAction extends DispatchAction {

	private LetterService letterService;
	 
	
	/**
	 * 增加数据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward add(ActionMapping mapping, 
            ActionForm actionForm, 
            HttpServletRequest request, 
            HttpServletResponse response) 
			throws Exception { 
		
		LetterForm form = (LetterForm)actionForm;
		Letter letter = null;
		letter = form.getLetter();
		
		Organization organization = new Organization();
		organization.setId(form.getOrganizationId());
		letter.setOrganization(organization);
		
		LetterCategory letterCategory = new LetterCategory();
		letterCategory.setId(form.getLetterCategoryId());
		letter.setLetterCategory(letterCategory);
		
		Date date = new Date();
		letter.setSubmitDate(date);
		
//		Member member = new Member();
//		member.setId(form.getMemberId());
//		letter.setMember(member);
		
		//信件是否公开
		String openStr = form.getOpenStr();
		String replyCode = letterService.addLetter(letter, openStr);
		form.setReplyCode(replyCode);
		form.setFlag(1);
		return mapping.findForward("success");     
	}

	/**
	 * 显示数据列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward list(ActionMapping mapping, 
            ActionForm actionForm, 
            HttpServletRequest request, 
            HttpServletResponse response) 
			throws Exception { 
		
		LetterForm form = (LetterForm)actionForm;
		Pagination pagination = new Pagination();
		pagination = form.getPagination();
		Pagination newPagination = new Pagination();
		newPagination = form.getPagination();
		
		Pagination page = letterService.queryList(newPagination);
		List list = page.getData();
		form.setPagination(page);
		form.setList(list);
		//查找信件类别
		List categoryList = letterService.findAllCategory(pagination);
		form.setCategoryList(categoryList);
		
		return mapping.findForward("list");   

	} 
	
	@SuppressWarnings("unchecked")
	public ActionForward list_msg(ActionMapping mapping, 
            ActionForm actionForm, 
            HttpServletRequest request, 
            HttpServletResponse response) 
			throws Exception { 
		
		LetterForm form = (LetterForm)actionForm;
		Pagination pagination = form.getPagination();
	
		Pagination page = letterService.queryList(pagination);
		List list = page.getData();
		form.setPagination(page);
		form.setList(list);
		return mapping.findForward("list_msg");   

	} 
	
	/**
	 * 显示部门列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward showOrgList(ActionMapping mapping, 
            ActionForm actionForm, 
            HttpServletRequest request, 
            HttpServletResponse response) 
			throws Exception { 
		
		LetterForm form = (LetterForm)actionForm;
		Pagination pagination = form.getPagination();
		List orgList = letterService.findOrgList(pagination);
		form.setOrgList(orgList);
		return mapping.findForward("forward");   

	} 

	/**
	 * 显示要写信件
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward showLetterDetail(ActionMapping mapping, 
            ActionForm actionForm, 
            HttpServletRequest request, 
            HttpServletResponse response) 
			throws Exception { 
		
		LetterForm form = (LetterForm)actionForm;
		Pagination pagination = new Pagination();
		pagination = form.getPagination();
		Pagination newPagination = new Pagination();
		newPagination = form.getPagination();
		
		//查找信件类别
		List list = letterService.findAllCategory(newPagination);
		form.setList(list);
		//部门id
		String ids = (String) form.getIds();
		String orgName = letterService.findOrgNameById(pagination, ids);
		
		form.setOrgName(orgName);
		form.setOrganizationId(ids);
		
		return mapping.findForward("writeLetter");
	}
	
	/**
	 * 根据回执编号查询信件列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward findByReplyCode(ActionMapping mapping, 
            ActionForm actionForm, 
            HttpServletRequest request, 
            HttpServletResponse response) 
			throws Exception { 
		
		LetterForm form = (LetterForm)actionForm;
		Pagination pagination = new Pagination();
		pagination = form.getPagination();
		Pagination newPagination = new Pagination();
		newPagination = form.getPagination();
		
		String replyCode = form.getReplyCode();
		Pagination page = letterService.findLetterByReplyCode(pagination, replyCode);
		List list = page.getData();
		form.setList(list);
		form.setPagination(page);
		//查询信件类别
		List categoryList = letterService.findAllCategory(newPagination);
		form.setCategoryList(categoryList);
		return mapping.findForward("list");
	}
	
	/**
	 * 根据信件类别查询信件列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward findByCategory(ActionMapping mapping, 
            ActionForm actionForm, 
            HttpServletRequest request, 
            HttpServletResponse response) 
			throws Exception { 
		
		LetterForm form = (LetterForm)actionForm;
		Pagination pagination = form.getPagination();
		
		String categoryName = form.getCategoryName();
		Pagination page = letterService.findLetterByCategory(pagination, categoryName);
		List list = page.getData();
		form.setList(list);
		form.setPagination(page);
		//查询信件类别
		List categoryList = letterService.findAllCategory(pagination);
		form.setCategoryList(categoryList);
		return mapping.findForward("list");
	}
	
	/**
	 * 显示用户信件具体内容
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward showUserLetter(ActionMapping mapping, 
            ActionForm actionForm, 
            HttpServletRequest request, 
            HttpServletResponse response) 
			throws Exception { 
	
		LetterForm form = (LetterForm)actionForm;
		Pagination pagination = form.getPagination();
		//信件id
		String ids = form.getIds();
		//信件状态
		int letterStatus = form.getLetterStatus();
		List list = letterService.findUserLetterDetail(pagination, ids, letterStatus);
		String opened = String.valueOf(list.get(0));
		//信件内容可以公开
		if(opened.equals("1") || opened.equals("true")) {
			form.setTitle(String.valueOf(list.get(1)));
			form.setContent(String.valueOf(list.get(2)));
			if(letterStatus == Letter.LETTER_STATUS_DEALED) {
				form.setReplyContent(String.valueOf(list.get(3)));
				form.setReplyDate(String.valueOf(list.get(4)));
				form.setReplyOrg(String.valueOf(list.get(5)));
			}
			return mapping.findForward("showUserLetterDetailSuccess");
		} else {
			return mapping.findForward("notAllowedShowLetterDetail");
		}
	}
	
	/**
	 * @param letterService the letterService to set
	 */
	public void setLetterService(LetterService letterService) {
		this.letterService = letterService;
	} 
}
