/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.configmanager.service;

import java.util.Map;

import com.j2ee.cms.biz.configmanager.domain.GeneralSystemSet;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.service.BaseService;
import com.j2ee.cms.common.core.web.event.RequestEvent;
import com.j2ee.cms.common.core.web.event.ResponseEvent;

/**
 * <p>
 * 标题: —— 机构详细业务逻辑处理方法
 * </p>
 * <p>
 * 描述: —— 简要描述类的职责、实现方式、使用注意事项等
 * </p>
 * <p>
 * 模块: 用户管理
 * </p>
 * <p>
 * 版权: Copyright (c) 2009  
 * 
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-2-16 下午03:05:55
 * @history（历次修订内容、修订人、修订时间等） 
 */
 

public  class GeneralSystemSetBiz extends BaseService {
	// 作者服务层类
	private GeneralSystemSetService generalSystemSetService;
	@Override
	public void performTask(RequestEvent requestEvent,
			ResponseEvent responseEvent) throws Exception {
		Pagination pagination;
		// 获取sessionID
		String sessionID = requestEvent.getSessionID();
		// 网站id
		String siteId = requestEvent.getSiteid();
		String dealMethod = requestEvent.getDealMethod();
		Map<Object, Object> requestParam = requestEvent.getReqMapParam();
		Map<Object, Object> responseParam = responseEvent.getRespMapParam();
		// 创建系统设置对象
		GeneralSystemSet generalSystemSet;
		
		// 开始系统设置数据并分页!
		if (dealMethod.equals("")) {
			log.debug("开始查询系统设置分页数据!");
			pagination = (Pagination) requestParam.get("pagination");
			String categoryId = (String) requestParam.get("categoryId");
			responseParam.put("categoryId", categoryId);
			int currPage = (Integer)requestParam.get("currPage");
			int rowsPerPage = (Integer)requestParam.get("rowsPerPage");
			if(currPage == 0){
				pagination.currPage = 1;
				pagination.rowsPerPage = 15;
			}else{
				pagination.currPage = currPage;
				pagination.rowsPerPage = rowsPerPage;
			}
			pagination = generalSystemSetService.findGeneralSystemSetData(pagination, categoryId);
			if(pagination.getData().size() == 0){
				pagination.currPage = pagination.currPage-1;
				pagination = generalSystemSetService.findGeneralSystemSetData(pagination, categoryId);
			}
			responseParam.put("pagination", pagination);
			log.info("查询系统设置分页成功.");
			
		// 对统设置数据添加
		} else if (dealMethod.equals("add")) {
			// 获取参数
			generalSystemSet = (GeneralSystemSet) requestParam
					.get("generalSystemSet");
			// 保存页面数据
			String separator = (String) requestParam.get("separator");
			// 判断是否需要覆盖
			String overDefault = (String) requestParam.get("overDefault");
			// 获取节点的ID
			String categoryId = (String) requestParam.get("categoryId");
			responseParam.put("overDefault", overDefault);
			responseParam.put("categoryId", categoryId);
			String message = generalSystemSetService.addgeneralSystemSetService( generalSystemSet,separator, categoryId,  overDefault,sessionID,siteId); 
			responseParam.put("message", message);
			
		// 数据 系统设置跳转
		} else if (dealMethod.equals("detail")	|| dealMethod.equals("linkdetail")	|| dealMethod.equals("originupdated")) {
			String authorId = (String) requestParam.get("authorId");
			String categoryId = (String) requestParam.get("categoryId");
			responseParam.put("categoryId", categoryId);
			generalSystemSet = new GeneralSystemSet();
			if (authorId != null && !authorId.equals("")) {
				// 查询作者设置详细信息，用于修改作者时使用
				generalSystemSet = generalSystemSetService.findGeneralSystemSetByKey(authorId);
			}
			responseParam.put("generalSystemSet", generalSystemSet);
			
		// 数据 系统设置更新
		} else if (dealMethod.equals("update")) {
			generalSystemSet = (GeneralSystemSet) requestParam.get("generalSystemSet");
			String message = "1";
			String categoryId = (String) requestParam.get("categoryId");
			String overDefault = (String) requestParam.get("overDefault");
			if (null != generalSystemSet) {
				message = generalSystemSetService.modifyAuthorSystem(generalSystemSet, categoryId, overDefault);
			}
			responseParam.put("categoryId", categoryId);
			responseParam.put("message", message);
		
		// 删除作者
		} else if (dealMethod.equals("delete")	|| dealMethod.equals("deleteOrign")	|| dealMethod.equals("deleteLink")) {
			String authorId = (String) requestParam.get("authorId");
			String categoryId = (String) requestParam.get("categoryId");
			responseParam.put("categoryId", categoryId);
			int currPage = (Integer)requestParam.get("currPage");
			int rowsPerPage = (Integer)requestParam.get("rowsPerPage");
			responseParam.put("currPage",currPage);
			responseParam.put("rowsPerPage", rowsPerPage);
			if (authorId != null && !authorId.equals("")) {
				// 用于删除作者时使用
				generalSystemSetService.deleteGenralSystemSetServic(authorId);
			}
			
		// 来源设置
		} else if (dealMethod.equals("origin")) {
			String categoryId = (String) requestParam.get("categoryId");
			if (categoryId != null && !categoryId.equals("")) {
				pagination = (Pagination) requestParam.get("pagination");
				int currPage = (Integer)requestParam.get("currPage");
				int rowsPerPage = (Integer)requestParam.get("rowsPerPage");
				if(currPage == 0){
					pagination.currPage = 1;
					pagination.rowsPerPage = 15;
				}else{
					pagination.currPage = currPage;
					pagination.rowsPerPage = rowsPerPage;
				}
				pagination = generalSystemSetService.findGeneralSystemSetData(pagination, categoryId);
				if(pagination.getData().size() == 0){
					pagination.currPage = pagination.currPage-1;
					pagination = generalSystemSetService.findGeneralSystemSetData(pagination, categoryId);
				}
				responseParam.put("categoryId", categoryId);
				responseParam.put("pagination", pagination);
			}
		
		// 图片设置
		} else if (dealMethod.equals("picture")) {
			log.debug("图片设置	");
			// 获取参数
			if (null != requestParam.get("generalSystemSet")) {
				generalSystemSet = (GeneralSystemSet) requestParam.get("generalSystemSet");
				String categoryId = (String) requestParam.get("categoryId");
				responseParam.put("categoryId", categoryId);
				if (true) {
					Boolean message = generalSystemSetService.addPectureGeneralSystemSetService(generalSystemSet, categoryId);
					if (message) {
						responseParam.put("number", "1");
					} else {
						responseParam.put("number", "0");
					}
				}
				responseParam.put("message", true);
			} else {
				responseParam.put("picture", "12");
			}
			
		// 传过来的树节点id
		} else if (dealMethod.equals("link")) {
			String categoryId = (String) requestParam.get("categoryId");
			if (categoryId != null && !categoryId.equals("")) {
				pagination = (Pagination) requestParam.get("pagination");
				int currPage = (Integer)requestParam.get("currPage");
				int rowsPerPage = (Integer)requestParam.get("rowsPerPage");
				if(currPage == 0){
					pagination.currPage = 1;
					pagination.rowsPerPage = 15;
				}else{
					pagination.currPage = currPage;
					pagination.rowsPerPage = rowsPerPage;
				}
				pagination = generalSystemSetService.findGeneralSystemSetData(pagination, categoryId);
				if(pagination.getData().size() == 0){
					pagination.currPage = pagination.currPage-1;
					pagination = generalSystemSetService.findGeneralSystemSetData(pagination, categoryId);
				}
				responseParam.put("categoryId", categoryId);
				responseParam.put("pagination", pagination);
			}
			
		// 添加热点链接
		} else if (dealMethod.equals("addLink")) {
			// 获取参数
			generalSystemSet = (GeneralSystemSet) requestParam.get("generalSystemSet");
			String categoryId = (String) requestParam.get("categoryId");
			responseParam.put("categoryId", categoryId);
			if ("1" == (String) requestParam.get("key")) {
				String message = generalSystemSetService.addLinkgeneralSystemSetService(generalSystemSet,categoryId,sessionID,siteId);
				responseParam.put("message", message);
			} else {
				responseParam.put("addLink", "12");
			}
			
		// 热点链接更新
		} else if (dealMethod.equals("linkUpdate")) {
			String message = "1";
			generalSystemSet = (GeneralSystemSet) requestParam
					.get("generalSystemSet");
			String categoryId = (String) requestParam.get("categoryId");
			if ("1" == (String) requestParam.get("key2")) {
				message = generalSystemSetService.modifyGenralSystemSetServic(
						generalSystemSet, categoryId);
			} else {
				responseParam.put("updateLink", "21");
			}
			// String categoryId = (String) requestParam.get("categoryId");
			responseParam.put("categoryId", categoryId);
			// 修改作者信息成功;
			responseParam.put("message", message);

		} else if (dealMethod.equals("origindetail")) {
			// 获取参数
			generalSystemSet = (GeneralSystemSet) requestParam
					.get("generalSystemSet");
			// 保存页面数据
			String separator = (String) requestParam.get("separator");
			// 判断是否需要覆盖
			String overDefault = (String) requestParam.get("overDefault");
			// 获取节点的ID
			String categoryId = (String) requestParam.get("categoryId");
			responseParam.put("overDefault", overDefault);
			responseParam.put("categoryId", categoryId);
		//	String message = generalSystemSetService
				//	.addgeneralSystemSetService(generalSystemSet, separator,
				//			categoryId, overDefault);
		    String message = generalSystemSetService	
					.addgeneralSystemSetService( generalSystemSet,separator, categoryId,  overDefault,sessionID,siteId);
			responseParam.put("message", message);
			// 列表更新
		} else if (dealMethod.equals("originupdate")) {
			//kkkk
			generalSystemSet = (GeneralSystemSet) requestParam
					.get("generalSystemSet");
			String message = "1";
			String categoryId = (String) requestParam.get("categoryId");
			String overDefault = (String) requestParam.get("overDefault");
			if (null != generalSystemSet) {
				message = generalSystemSetService.modifyAuthorSystem(
						generalSystemSet, categoryId, overDefault);
			}
			responseParam.put("categoryId", categoryId);
			responseParam.put("message", message);
		} else if (dealMethod.equals("origindetailAdd")) {
			// 获取参数
			generalSystemSet = (GeneralSystemSet) requestParam
					.get("generalSystemSet");
			// 保存页面数据
			String separator = (String) requestParam.get("separator");
			// 获取节点的ID
			String categoryId = (String) requestParam.get("categoryId");
			responseParam.put("categoryId", categoryId);
			// 判断是否需要覆盖
			String overDefault = (String) requestParam.get("overDefault");
			// 获取节点的ID
			responseParam.put("overDefault", overDefault);
		   // generalSystemSetService
					//.addgeneralSystemSetSerice(generalSystemSet, separator,
					//		categoryId, overDefault);
		generalSystemSetService.addgeneralSystemSetService( generalSystemSet,separator, categoryId,  overDefault,sessionID,siteId);

		}

	}

	@Override
	public ResponseEvent validateData(RequestEvent req) throws Exception {
		// TODO 自动生成方法存根
		return null;
	}

	public void setGeneralSystemSetService(
			GeneralSystemSetService generalSystemSetService) {
		this.generalSystemSetService = generalSystemSetService;
	}

}
