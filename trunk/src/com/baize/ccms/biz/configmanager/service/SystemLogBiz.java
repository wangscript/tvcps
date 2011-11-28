/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.configmanager.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.baize.ccms.biz.configmanager.web.form.SystemLogForm;
import com.baize.ccms.sys.SiteResource;
import com.baize.common.core.dao.Pagination;
import com.baize.common.core.service.BaseService;
import com.baize.common.core.util.FileUtil;
import com.baize.common.core.web.WebClientServlet;
import com.baize.common.core.web.event.RequestEvent;
import com.baize.common.core.web.event.ResponseEvent;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CCMS</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 郑荣华
 * @version 1.0
 * @since 2009-9-5 上午10:08:36
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class SystemLogBiz extends BaseService {
	
	/** 注入日志业务对象 */
	private SystemLogService systemLogService;

	@Override
	public void performTask(RequestEvent requestEvent, ResponseEvent responseEvent) throws Exception {
		String dealMethod = requestEvent.getDealMethod();
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		String siteId = requestEvent.getSiteid();
		
		// 查询所有日志
		if(dealMethod.equals("")) {									
			Pagination pagination = (Pagination) requestParam.get("pagination");
			pagination = systemLogService.findLogBySiteIdPage(siteId, pagination);
			responseParam.put("pagination", pagination);
		
		// 删除日志
		} else if(dealMethod.equals("delete")) {
			
			String deletedIds = (String) requestParam.get("deletedIds");
			systemLogService.deleteLogsByIds(deletedIds);
			
			Pagination pagination = (Pagination) requestParam.get("pagination");
			pagination = systemLogService.findLogBySiteIdPage(siteId, pagination);
			int size = pagination.getData().size();
			if(size == 0){
				pagination.currPage = pagination.currPage-1;
				pagination = systemLogService.findLogBySiteIdPage(siteId, pagination);
			}
			responseParam.put("pagination", pagination);
			
		// 导出日志	
		} else if(dealMethod.equals("export")) {
			SystemLogForm form = (SystemLogForm) requestParam.get("form");
			String excelPath = SiteResource.getSiteDir(siteId, false) + "/logs.xls";
			if(FileUtil.isExist(excelPath)) {
				FileUtil.delete(excelPath);
			}
			systemLogService.exportLogsToExcel(form, siteId, excelPath);
			
		// 下载日志
		} else if(dealMethod.equals("savaExportLogs")) {
			String excelPath = SiteResource.getSiteDir(siteId, false) + "/logs.xls";
	    	HttpServletRequest req = (HttpServletRequest) requestParam.get("request");
			req.setAttribute(WebClientServlet.DEAL_CODE, WebClientServlet.DEAL_CODE_DOWNLOAD);
			req.setAttribute(WebClientServlet.DEAL_CODE_DOWNLOAD_FILEPATH, excelPath);
			req.setAttribute(WebClientServlet.DEAL_CODE_DOWNLOAD_LOCALNAME, "日志");
			req.setAttribute(WebClientServlet.DEAL_CODE_DOWNLOAD_CHARSET, "utf-8");
			
		// 清空日志	
		} else if(dealMethod.equals("clear")) {
			systemLogService.deleteAllLogsBySiteId(siteId);
		}
	}

	@Override
	public ResponseEvent validateData(RequestEvent requestEvent)
			throws Exception {
		return null;
	}

	/**
	 * @param logManagerService the logManagerService to set
	 */
	public void setSystemLogService(SystemLogService systemLogService) {
		this.systemLogService = systemLogService;
	}

}
