/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.articlemanager.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司</p>
 * <p>网址：http://www.baizeweb.com
 * @author <a href="mailto:sean_yang@163.com">杨信</a>
 * @version 1.0
 * @since 2009-9-2 上午11:34:58
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class EnumerationBiz extends BaseService {
	
	private EnumerationService enumerationService;

	@Override
	public void performTask(RequestEvent requestEvent,
			ResponseEvent responseEvent) throws Exception {
		String dealMethod = requestEvent.getDealMethod();
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		String siteId = requestEvent.getSiteid();
		String userId = requestEvent.getSessionID();
		
		if (dealMethod.equals("")) {
			log.info("显示枚举列表");
			Pagination pagination = (Pagination) requestParam.get("pagination");
			pagination = enumerationService.findEnumerationPagination(pagination);
			if(pagination.getData().size() == 0){
				pagination.currPage = pagination.currPage - 1;
				pagination = enumerationService.findEnumerationPagination(pagination);
			}
			responseParam.put("pagination", pagination);
			
		} else if (dealMethod.equals("add")) {
			log.info("跳转到添加页面");
			//查找所有枚举名称字符串
			String allEnumNameStr = enumerationService.findAllEnumNameStr();
			responseParam.put("allEnumNameStr", allEnumNameStr);
			
		} else if (dealMethod.equals("delete")) {
			log.info("删除枚举类别");
			String ids = (String) requestParam.get("enumerationIds");
			String message = "";
			message = enumerationService.deleteEnumerationByIds(ids, siteId, userId);
			responseParam.put("message", message);
			log.debug(message);
			
		} else if (dealMethod.equals("detail")) {
			log.info("查看枚举信息");
			String enumerationId = (String) requestParam.get("enumerationId");
			//查找枚举名称
			String enumerationName = enumerationService.findEnumerationNameById(enumerationId);
			//查找所有枚举名称字符串
			String allEnumNameStr = enumerationService.findAllEnumNameStr(enumerationId);
			
			//查找枚举值
			String enumValuesStr = enumerationService.findEnumValuesStrById(enumerationId);
			responseParam.put("enumerationName", enumerationName);
			responseParam.put("allEnumNameStr", allEnumNameStr);
			responseParam.put("enumValuesStr", enumValuesStr);
			responseParam.put("enumerationId", enumerationId);
			
			
		} else if (dealMethod.equals("findEnumValues")) {
			log.info("查找枚举值");
			String enumerationId = (String) requestParam.get("enumerationId");
	//		String enumerationName = (String) requestParam.get("enumerationName");
	//		Enumeration enumeration = enumerationService.findEnumerationById(enumerationId);
	//		responseParam.put("enumeration", enumeration);
			
		} else if (dealMethod.equals("addEnumValues")) {
			log.info("添加枚举信息");
			//查找所有枚举名称字符串
			String allEnumNameStr = enumerationService.findAllEnumNameStr();
			String enumerationName = (String) requestParam.get("enumerationName");
			String enumValuesStr = (String) requestParam.get("enumValuesStr");
			String message = enumerationService.addEnum(enumerationName, enumValuesStr, userId, siteId);
			responseParam.put("message", message);
			responseParam.put("allEnumNameStr", allEnumNameStr);
			
		} else if (dealMethod.equals("modifyEnumValues")) {
			log.info("修改枚举信息");
			String enumerationId = (String) requestParam.get("enumerationId");
			String enumerationName = (String) requestParam.get("enumerationName");
			String enumValuesStr = (String) requestParam.get("enumValuesStr");
			String message = enumerationService.modifyEnum(enumerationName, enumValuesStr, enumerationId, siteId, userId);
			responseParam.put("message", message);
			
			//导出
		} else if (dealMethod.equals("export")) {
			String exportEnumIds = (String) requestParam.get("exportEnumIds");
			
			// 新建一个临时的导出文件路径
			String path = SiteResource.getSiteDir(siteId, false) + "/article.xml";
			String message = enumerationService.exportEnumerations(exportEnumIds, path, siteId, userId);
			HttpServletRequest req = (HttpServletRequest) requestParam.get("request");
			req.setAttribute(WebClientServlet.DEAL_CODE, WebClientServlet.DEAL_CODE_DOWNLOAD);
			req.setAttribute(WebClientServlet.DEAL_CODE_DOWNLOAD_FILEPATH, path);
			req.setAttribute(WebClientServlet.DEAL_CODE_DOWNLOAD_LOCALNAME, "枚举");
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
			message = enumerationService.importEnumerationsXml(path, siteId, userId);
			responseParam.put("message", message);
			
			
		}

	}

	@Override
	public ResponseEvent validateData(RequestEvent requestEvent)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void setEnumerationService(EnumerationService enumerationService) {
		this.enumerationService = enumerationService;
	}

}
