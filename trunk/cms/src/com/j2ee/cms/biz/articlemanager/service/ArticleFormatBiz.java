/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.articlemanager.service;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.j2ee.cms.biz.articlemanager.domain.ArticleFormat;
import com.j2ee.cms.biz.usermanager.domain.User;
import com.j2ee.cms.sys.SiteResource;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.service.BaseService;
import com.j2ee.cms.common.core.util.FileUtil;
import com.j2ee.cms.common.core.web.WebClientServlet;
import com.j2ee.cms.common.core.web.event.RequestEvent;
import com.j2ee.cms.common.core.web.event.ResponseEvent;

/**
 * <p>标题: 格式业务类</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 文章管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-3-30 下午04:30:28
 * @history（历次修订内容、修订人、修订时间等）
 */
public class ArticleFormatBiz extends BaseService {
	
	/** 注入文章格式服务类 */
	private ArticleFormatService articleFormatService;

	@Override
	public void performTask(RequestEvent requestEvent, ResponseEvent responseEvent)
			throws Exception {
		String dealMethod = requestEvent.getDealMethod();
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		String siteId = requestEvent.getSiteid();
		String sessionID = requestEvent.getSessionID();
		   
		// 显示列表
		if (dealMethod.equals("")) {
			log.info("显示格式列表");
			
			Pagination pagination = (Pagination) requestParam.get("pagination");
			pagination = articleFormatService.findFormatPagination(pagination);
			if(pagination.getData().size() == 0){
				pagination.currPage = pagination.currPage - 1;
				pagination = articleFormatService.findFormatPagination(pagination);
			}
			responseParam.put("pagination", pagination);
			
		// 显示明细
		} else if (dealMethod.equals("detail")) {
			log.info("显示格式明细");
			
			String id = (String) requestParam.get("id");
			ArticleFormat format = articleFormatService.findFormatById(id);
			responseParam.put("format", format);
			
		// 添加格式
		} else if (dealMethod.equals("add")) {
			log.info("添加格式");
			
			ArticleFormat format = (ArticleFormat) requestParam.get("format");
			User user = new User();
			user.setId(requestEvent.getSessionID());
			format.setCreator(user);
			format.setCreateTime(new Date());
			//format.setFields(ArticleFormat.FIELD_NAMES_DEFAULT);
			String infoMessage = "";
			infoMessage = articleFormatService.addFormat(format, siteId, sessionID);
			responseParam.put("infoMessage", infoMessage);
			
		// 删除格式
		} else if (dealMethod.equals("delete")) {
			log.info("删除格式");
			
			String ids = (String) requestParam.get("formatIds");
			String infoMessage = "";
			infoMessage = articleFormatService.deleteFormatByIds(ids, siteId, sessionID);
			responseParam.put("infoMessage", infoMessage);
			
		// 设置属性
		} else if (dealMethod.equals("attribute")) {
			log.info("设置属性");
			
		// 修改格式
		} else if (dealMethod.equals("modify")) {
			log.info("修改格式");
			String infoMessage = "";
			ArticleFormat format = (ArticleFormat) requestParam.get("format");
			infoMessage = articleFormatService.modifyFormat(format, siteId, sessionID);
			responseParam.put("infoMessage", infoMessage);
			
			//导出
		} else if (dealMethod.equals("export")) {
			String exportFormatIds = (String) requestParam.get("exportFormatIds");
			
			// 新建一个临时的导出文件路径
			String path = SiteResource.getSiteDir(siteId, false) + "/article.xml";
			String message = articleFormatService.exportFormats(exportFormatIds, path);
			HttpServletRequest req = (HttpServletRequest) requestParam.get("request");
			req.setAttribute(WebClientServlet.DEAL_CODE, WebClientServlet.DEAL_CODE_DOWNLOAD);
			req.setAttribute(WebClientServlet.DEAL_CODE_DOWNLOAD_FILEPATH, path);
			req.setAttribute(WebClientServlet.DEAL_CODE_DOWNLOAD_LOCALNAME, "格式");
			req.setAttribute(WebClientServlet.DEAL_CODE_DOWNLOAD_CHARSET, "utf-8");
			
			responseParam.put("message", message);
			// 删除临时的导出文件
			if(FileUtil.isExist(path)) {
				FileUtil.delete(path);
			}
			
			// 导入格式	
		} else 	if(dealMethod.equals("import")) {
			log.info("导入格式");
			// 获取格式路径
			String path = (String) requestParam.get("path");
			String message = "";
			message = articleFormatService.importFormatsXml(path, siteId, sessionID);
			responseParam.put("message", message);
			
		} 
	}

	@Override
	public ResponseEvent validateData(RequestEvent request) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param articleFormatService the articleFormatService to set
	 */
	public void setArticleFormatService(ArticleFormatService articleFormatService) {
		this.articleFormatService = articleFormatService;
	}

}
