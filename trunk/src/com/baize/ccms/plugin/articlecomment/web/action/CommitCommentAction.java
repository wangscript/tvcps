/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.plugin.articlecomment.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.baize.ccms.plugin.articlecomment.service.ArticleCommentService;
import com.baize.ccms.plugin.articlecomment.web.form.ArticleCommentForm;
import com.baize.common.core.dao.Pagination;

/**
 * <p>
 * 标题: —— 要求能简洁地表达出类的功能和职责
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
 * @since 2009-10-20 下午03:27:33
 * @history（历次修订内容、修订人、修订时间等） 
 */
 

public class CommitCommentAction extends DispatchAction {
	/**注入文章评论service*/
	private ArticleCommentService articleCommentService;
	private String articleId="";
	/**
	 * 提交评论
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward commitComment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ArticleCommentForm commentform = (ArticleCommentForm)form;
		commentform.getComment().setIp(getIpAddr(request));
		String mess=articleCommentService.commitComment(commentform, articleId);
		request.setAttribute("mess", mess);
		commentform.setInfoMessage(mess);
		return mapping.findForward("return");
	}
	/**
	 * 评论分页
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward commentList(ActionMapping mapping, 
            ActionForm actionForm, 
            HttpServletRequest request, 
            HttpServletResponse response) 
			throws Exception { 
		
		ArticleCommentForm form = (ArticleCommentForm)actionForm;
		Pagination pagination = form.getPagination();
		Pagination page = articleCommentService.getCommentPagination(pagination);
		String articleId = request.getParameter("articleId");
		if(null!=articleId&&!articleId.equals("")){
			this.articleId = articleId;
		}
		String content=articleCommentService.getAnalyStyle(pagination,form,this.articleId);
		form.setPagination(page);
		if(null!=request.getAttribute("mess")){//获取提示信息
			form.setInfoMessage(request.getAttribute("mess").toString());
		}
		request.setAttribute("content", content);//将内容放入页面
		return mapping.findForward("articleTemple");   

	} 
	
	/**
	 * 第二次评论分页 ，(由于是用ajax分页)
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward commentList2(ActionMapping mapping, 
            ActionForm actionForm, 
            HttpServletRequest request, 
            HttpServletResponse response) 
			throws Exception { 
		
		ArticleCommentForm form = (ArticleCommentForm)actionForm;
		Pagination pagination = form.getPagination();
		Pagination page = articleCommentService.getCommentPagination(pagination);
		String content=articleCommentService.getAnalyStyle(pagination,form,articleId);
		form.setPagination(page);
		request.setAttribute("content", content);
		return mapping.findForward("commentpagination");   

	}
	/**
	 * 投票支持
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward commentSupport(ActionMapping mapping, 
            ActionForm actionForm, 
            HttpServletRequest request, 
            HttpServletResponse response) 
			throws Exception { 
		ArticleCommentForm form = (ArticleCommentForm)actionForm;
		String id=request.getParameter("commentId");//评论ID
		String mess=articleCommentService.addSuport(id,articleId);
		form.setInfoMessage(mess);
		return mapping.findForward("clickmsg");   

	} 
	/**
	 * 投票反对
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward commentIronfoe(ActionMapping mapping, 
            ActionForm actionForm, 
            HttpServletRequest request, 
            HttpServletResponse response) 
			throws Exception { 
		ArticleCommentForm form = (ArticleCommentForm)actionForm;
		String id=request.getParameter("commentId");//评论ID
		String mess=articleCommentService.addIronfoe(id);
		form.setInfoMessage(mess);
		return mapping.findForward("clickmsg");   

	}
	/**
	 * 获取真实的IP
	 * @param request 请求对象
	 * @return IP
	 */
	public String getIpAddr(HttpServletRequest request) {
	       String ip = request.getHeader("x-forwarded-for");
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	           ip = request.getHeader("Proxy-Client-IP");
	       }
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	           ip = request.getHeader("WL-Proxy-Client-IP");
	       }
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	           ip = request.getRemoteAddr();
	       }
	       return ip;
	   }
	/**
	 * @param articleCommentService the articleCommentService to set
	 */
	public void setArticleCommentService(ArticleCommentService articleCommentService) {
		this.articleCommentService = articleCommentService;
	}
}
