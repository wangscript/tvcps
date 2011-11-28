/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.plugin.guestbookmanager.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.baize.ccms.biz.usermanager.web.form.LoginForm;
import com.baize.ccms.plugin.guestbookmanager.service.GuestBookContentService;
import com.baize.ccms.plugin.guestbookmanager.service.impl.GuestBookAttributeServiceImpl;
import com.baize.ccms.plugin.guestbookmanager.web.form.GuestBookForm;
import com.baize.common.core.dao.Pagination;

/**
 * <p>
 * 标题: —— 前台ACTION,主要用于前台的留言操作
 * </p>
 * <p>
 * 描述: —— 简要描述类的职责、实现方式、使用注意事项等
 * </p>
 * <p>
 * 模块: CCMS
 * </p>
 * <p>
 * 版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * </p>
 * <p>
 * 网址：http://www.baizeweb.com
 * 
 * @author 曹名科
 * @version 1.0
 * @since 2009-11-7 下午02:26:36
 * @history（历次修订内容、修订人、修订时间等） 
 */
 

public class GuestOuterAction extends DispatchAction {
	/** 注入前台留言服务类 */
	private GuestBookContentService contentService;
	/** 属性设置服务类 . */
	private GuestBookAttributeServiceImpl attributeSet;
	private String siteId = "";

	/**
	 * @param attributeSet
	 *            the attributeSet to set
	 */
	public void setAttributeSet(GuestBookAttributeServiceImpl attributeSet) {
		this.attributeSet = attributeSet;
	}

	/**
	 * @param contentService
	 *            the contentService to set
	 */
	public void setContentService(GuestBookContentService contentService) {
		this.contentService = contentService;
	}

	/**
	 * 获取真实的IP
	 * 
	 * @param request
	 *            请求对象
	 * @return IP
	 */
	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 添加留言
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addContent(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		GuestBookForm form = (GuestBookForm) actionForm;
		form.getGuestContent().setIp(getIpAddr(request));
		form.setSites(siteId);
		HttpSession session = request.getSession(true);
		String sessionrand = String.valueOf(session.getAttribute("rand"));// 获取验证码
		String rand = String.valueOf(form.getRand());
		if (sessionrand.equals(rand)) {// 验证码正确
			String mess = contentService.addGuestBookContent(form);
			form.setInfoMessage(mess);// 显示添加留言
		} else {// 验证码不正确
			form.setMess("0");
			getAllContentJspInfo(mapping, actionForm, request, response);// 调用初始化页面信息
		}
		return mapping.findForward("showAddContent");
	}

	/**
	 * 验证验证码输入是否正确
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward checkRand(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		GuestBookForm form = (GuestBookForm) actionForm;
		HttpSession session = request.getSession(true);
		String sessionrand = String.valueOf(session.getAttribute("rand"));// 获取验证码
		String rand = request.getParameter("rand");
		if (sessionrand.equals(rand)) {// 验证码正确
			form.setMess("1");
		} else {
			form.setMess("0");
		}
		return mapping.findForward("randmsg");
	}

	/**
	 * 根据指定条件查询分页
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward conditionPageList(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		GuestBookForm form = (GuestBookForm) actionForm;
		HttpSession session = request.getSession();
		session.setAttribute("categoryList", contentService
				.getGuestCategoryList(this.siteId));// 将类别列表放入页面
		String condition = request.getParameter("condition");// 指定按选择的类别搜索
		String keyword = request.getParameter("keyword");// 搜索的关键字
		String startTime = request.getParameter("starTime");// 开始时间
		String endTime = request.getParameter("endTime");// 结束时间
		
		Pagination p = contentService.getpaginationContentBySelect(form
				.getPagination(), this.siteId, condition, keyword, startTime,
				endTime);
	//	System.out.println("根据指定条件分页，总记录：" + p.maxRowCount);
		String content = contentService.getContentByCondition(this.siteId, form
				.getPagination(), condition, keyword, startTime, endTime);
		request.setAttribute("content", content);
		form.setPagination(p);
		/**将页面的值传回到页面去*/
		request.setAttribute("condition", condition);
		request.setAttribute("keyword", keyword);
		request.setAttribute("startTime", startTime);
		request.setAttribute("endTime", endTime);
		return mapping.findForward("outerContentList");
	}

	/**
	 * 根据类别查询分页页面
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward categoryPageList(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		GuestBookForm form = (GuestBookForm) actionForm;
		String categoryName = request.getParameter("guestBookCategory");
		HttpSession session = request.getSession();
		session.setAttribute("categoryList", contentService.getGuestCategoryList(this.siteId));// 将类别列表放入页面
		Pagination p = contentService.findByCategory(form.getPagination(),	this.siteId, categoryName);
//		System.out.println("根据类别分页，总记录：" + p.maxRowCount);
		String content = contentService.getContentByCategory(this.siteId, form.getPagination(), categoryName);
		request.setAttribute("content", content);
		form.setPagination(p);
		request.setAttribute("guestBookCategory",categoryName);
		return mapping.findForward("outerContentList");
	}

	/**
	 * 留言分页页面
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward showContentList(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		GuestBookForm form = (GuestBookForm) actionForm;
		String siteId = request.getParameter("siteId");
		if (null != siteId && !siteId.equals("")) {
			this.siteId = siteId;
		}
		HttpSession session = request.getSession();
		session.setAttribute("categoryList", contentService.getGuestCategoryList(this.siteId));// 将类别列表放入页面
		Pagination p = contentService.getpaginationContent(form.getPagination(), this.siteId);
		String content = contentService.getContent(this.siteId, form.getPagination());
	//	System.out.println("根据全部，总记录：" + p.maxRowCount);
		request.setAttribute("content", content);
		form.setPagination(p);
		return mapping.findForward("outerContentList");
	}

	/**
	 * 留言分页页面 第二次
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward showContentList1(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		GuestBookForm form = (GuestBookForm) actionForm;
		Pagination p = contentService.getpaginationContent(
				form.getPagination(), siteId);
		form.setPagination(p);
		String content = contentService
				.getContent(siteId, form.getPagination());
		request.setAttribute("content", content);
		return mapping.findForward("outerContentList1");
	}

	/**
	 * 跳转到留言页面
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward showAddContent(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		getAllContentJspInfo(mapping, actionForm, request, response);
		return mapping.findForward("showAddContent");
	}

	/**
	 * 用于将添加留言的页面初始化信息在页面显示（例如：类别列表，是否超过限制的留言数量等。。。）
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 */
	private void getAllContentJspInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		GuestBookForm form = (GuestBookForm) actionForm;
		attributeSet.getAttributeSetForm(form, siteId);// 获取属性设置的值
		form.setFactCount(contentService.getAllCount(siteId));// 获取所有留言数量
		HttpSession session = request.getSession();
		session.setAttribute("categoryList", contentService
				.getGuestCategoryList(this.siteId));// 将类别列表放入页面
	}
}
