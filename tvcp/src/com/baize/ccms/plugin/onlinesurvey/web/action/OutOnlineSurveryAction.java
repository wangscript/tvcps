/**
 * project：通用内容管理系统
 * Company: 南京瀚沃信息科技有限责任公司
 */
package com.baize.ccms.plugin.onlinesurvey.web.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.baize.ccms.plugin.onlinesurvey.service.OnlineSurveyService;
import com.baize.ccms.plugin.onlinesurvey.web.form.OnlineSurveyContentForm;
import com.baize.common.core.dao.Pagination;
import com.baize.common.core.util.StringUtil;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CCMS</p>
 * <p>版权: Copyright (c) 2009 南京瀚沃信息科技有限责任公司
 * @author 郑荣华
 * @version 1.0
 * @since 2009-12-25 上午11:03:46
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class OutOnlineSurveryAction extends DispatchAction {
	
	private OnlineSurveyService onlineSurveyService;

	public ActionForward getNormalDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//一般调查指定主题
		System.out.println("一般调查指定主题");
		String unitId = (String) request.getParameter("unit_unitId");
		String themeId = (String) request.getParameter("themeId");
		String questionId = (String) request.getParameter("questionId");
		String display = (String) request.getParameter("display");
		String appName = (String) request.getParameter("appName");
		String html = onlineSurveyService.getNormalDetailHtml(unitId, themeId, questionId, display, appName);
		html = unitId+"###"+html;
		request.setAttribute("out", html);
		request.setAttribute("appName", appName);
		return mapping.findForward("noramlDetailSuccess");
	}
	
	public ActionForward getNormalPagination(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//一般调查列表分页
		String unitId = (String) request.getParameter("unit_unitId");
		String themeId = (String) request.getParameter("themeId");
		String appName = (String) request.getParameter("appName");
		OnlineSurveyContentForm form = (OnlineSurveyContentForm)actionForm;
		Pagination pagination = onlineSurveyService.getNormalPagination(form.getPagination(), themeId, unitId);
		form.setPagination(pagination);
		System.out.println("根据全部，总记录：" + pagination.maxRowCount);
		//标记是一般调查
		boolean flag = true; 
		String content = onlineSurveyService.getContent(form.getPagination(), unitId, flag, appName);
		request.setAttribute("content", content);
		request.setAttribute("appName", appName);
		return mapping.findForward("noramlListPaginationSuccess");
	}
	
	public ActionForward getNormalJsp(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("一般调查详细页面");
		//详细处理页面（投票页面）  
		String questionId = (String) request.getParameter("questionId");
		String siteId = (String) request.getParameter("siteId");
		String appName = (String) request.getParameter("appName");
		String content = onlineSurveyService.getDisplayStyle(questionId, siteId);
		request.setAttribute("content", content);
		request.setAttribute("appName", appName);
		return mapping.findForward("displayDetail");
	}
	
	public ActionForward getNormalList(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//显示一般调查全部问题
		OnlineSurveyContentForm form = (OnlineSurveyContentForm)actionForm;
		String unitId = (String) request.getParameter("unit_unitId");
		String appName = (String) request.getParameter("appName");
		Pagination pagination = onlineSurveyService.getNormalPagination(form.getPagination(), "", unitId);
		form.setPagination(pagination);
		//标记更多是否显示
		boolean flag = true;
		String content = onlineSurveyService.getNormalContent(pagination, unitId, appName);
		content = unitId+"###"+content;
		request.setAttribute("out", content);
		return mapping.findForward("noramlDetailSuccess");
	}
	
	public ActionForward addOnlineSurvery(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//一般调查投票
		String questionId = (String) request.getParameter("questionIds");
		String answerIds = (String) request.getParameter("answerIds");
		String feedbackContent = (String) request.getParameter("feedbackContent");
		onlineSurveyService.addCommit(questionId, answerIds, feedbackContent);
		request.setAttribute("out", "投票成功");
		return mapping.findForward("noramlDetailSuccess");
	}
	
	public ActionForward getAnswerDetail(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//问卷调查指定主题
		//问卷调查前台页面
		String unitId = (String) request.getParameter("unit_unitId");
		String themeId = (String) request.getParameter("themeId");
		String appName = (String) request.getParameter("appName");
		String html = onlineSurveyService.getAnswerDetailHtml(unitId, themeId, appName);
		html = unitId+"###"+html;
		request.setAttribute("out", html);
		return mapping.findForward("noramlDetailSuccess");
	} 
	
	public ActionForward getAnswerJsp(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//问卷调查投票页面（详细显示投票的问题和答案）
		String themeId = (String) request.getParameter("themeId");
		String unitId = (String) request.getParameter("unitId");
		String appName = (String)request.getParameter("appName");
		String content =  onlineSurveyService.getAnswerDisplayStyle(themeId, unitId);
		if(!StringUtil.isEmpty(content) && !content.equals("###")){
			String[] str = content.split("###");
			request.setAttribute("questionId", str[0]);
			request.setAttribute("content", str[1]);
			
		}else{
			request.setAttribute("content", "");
		}
		request.setAttribute("appName", appName);
		return mapping.findForward("displayAnswerJsp");
	} 
	
	public ActionForward addAnswerOnlineSurvery(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//问卷调查投票
		String questionIds = (String) request.getParameter("questionIds");
		String answerIds = (String) request.getParameter("answerIds");
		String feedbackContent = (String) request.getParameter("feedbackContent");
		onlineSurveyService.addAnswerCommit(questionIds, answerIds, feedbackContent);
		request.setAttribute("out", "投票成功");
		return mapping.findForward("noramlDetailSuccess");
	}
	
	public ActionForward getAnswerlList(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//问卷调查主题列表
		OnlineSurveyContentForm form = (OnlineSurveyContentForm)actionForm;
		String unitId = (String) request.getParameter("unit_unitId");
		String appName = (String) request.getParameter("appName");
		Pagination pagination = onlineSurveyService.getAnswerPagination(form.getPagination(), unitId, 1);
		String content = "";
		content = onlineSurveyService.getAnswerList(pagination, unitId, appName);
		content = unitId+"###"+content;
		request.setAttribute("out", content);
		return mapping.findForward("noramlDetailSuccess");
	}
	
	public ActionForward getAnswerPagination(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//一般调查列表分页
		String unitId = (String) request.getParameter("unit_unitId");
		String appName = (String) request.getParameter("appName");
		OnlineSurveyContentForm form = (OnlineSurveyContentForm)actionForm;
		Pagination pagination = onlineSurveyService.getAnswerPagination(form.getPagination(), unitId, 0);
		form.setPagination(pagination);
		boolean flag = false;
		String content = onlineSurveyService.getContent(form.getPagination(), unitId, flag, appName);
		request.setAttribute("content", content);
		return mapping.findForward("answerListPaginationSuccess");
	}
	
	public ActionForward searchResult(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//查看调查结果
		OnlineSurveyContentForm form = (OnlineSurveyContentForm)actionForm;
		String questionIds = (String) request.getParameter("questionId"); 
		String themeId = (String) request.getParameter("themeId");
		Map map = onlineSurveyService.getResult(questionIds, themeId);
		List listQuestion = (List)map.get("question");
		List listAnswer = (List)map.get("answer");
		form.setListQuestion(listQuestion);
		form.setListAnswer(listAnswer);
		return mapping.findForward("searchSuccess");
	}
	/**
	 * @param onlineSurveyService the onlineSurveyService to set
	 */
	public void setOnlineSurveyService(OnlineSurveyService onlineSurveyService) {
		this.onlineSurveyService = onlineSurveyService;
	}
}
