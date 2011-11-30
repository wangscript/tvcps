/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.plugin.rss.web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.j2ee.cms.plugin.rss.service.RssService;

/**
 * <p>
 * 标题: —— 前台RSSaction
 * </p>
 * <p>
 * 描述: —— 用于实现RSS订阅
 * </p>
 * <p>
 * 模块: CPS插件
 * </p>
 * <p>
 * 版权: Copyright (c) 2009  
 * </p>
 * <p>
 * 网址：http://www.j2ee.cmsweb.com
 * 
 * @author 曹名科
 * @version 1.0
 * @since 2009-10-16 下午03:28:56
 * @history（历次修订内容、修订人、修订时间等） 
 */
 

public class RssOuterAction extends DispatchAction {
	/** 注入rssService */
	private RssService rssService;

	public ActionForward getList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String ids=request.getParameter("columnId");
		if(ids!=null&ids.length()>0){
			List<String> lis=rssService.getRssList(ids, "");
			request.setAttribute("list", lis);
			return mapping.findForward("rssList");
		}else{
			return mapping.findForward("rss_set.jsp");
		}
	}

	public ActionForward getRssList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String siteId = (String) request.getParameter("siteId");
		String appName = (String) request.getParameter("appName");
		if(siteId != null) {
			request.getRemoteAddr();
			String rss = rssService.getOutRssList(siteId, appName);
			request.setAttribute("rss", rss);
			return mapping.findForward("outRssSuccess");
		}else{
			return mapping.findForward("rss_set.jsp");
		}
	}
	
	
	public void setRssService(RssService rssService) {
		this.rssService = rssService;
	}
}
