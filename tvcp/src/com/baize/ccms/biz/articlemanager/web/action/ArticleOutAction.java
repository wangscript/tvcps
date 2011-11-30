/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.articlemanager.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.j2ee.cms.biz.articlemanager.service.ArticleService;

/**
 * <p>标题: —— 在外网显示及增加文章访问次数</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 文章管理</p>
 * <p>版权: Copyright (c) 2010  
 * @author 娄伟峰
 * @version 1.0
 * @since 2010-1-22 下午02:44:56
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class ArticleOutAction extends DispatchAction{
	/** 注入文章服务类 */
	private ArticleService articleService;
	/**
	 * 查询点击数
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward findArticleHits(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	 
		return mapping.findForward("findArticleHitsSuccess");
	}

	
	/**
	 * 增加文章点击数
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addArticleHits(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	 
		return mapping.findForward("addArticleHitsSuccess");
	}

	/**
	 * @param articleService the articleService to set
	 */
	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}
}
