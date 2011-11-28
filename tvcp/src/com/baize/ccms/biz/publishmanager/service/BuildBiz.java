/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.publishmanager.service;

import java.util.Map;

import com.baize.common.core.dao.Pagination;
import com.baize.common.core.service.BaseService;
import com.baize.common.core.web.event.RequestEvent;
import com.baize.common.core.web.event.ResponseEvent;

/**
 * 
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 发布管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-11-11 下午02:21:36
 * @history（历次修订内容、修订人、修订时间等）
 */

public class BuildBiz extends BaseService {
	
	/** 注入生成Service */
	private BuildService buildService;

	@Override
	public void performTask(RequestEvent requestEvent, ResponseEvent responseEvent)
			throws Exception {
		String dealMethod = requestEvent.getDealMethod();
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		String siteId = requestEvent.getSiteid();
		String sessionID = requestEvent.getSessionID();
		
		if (dealMethod.equals("")) {
			//查看生成列表
			Pagination pagination = (Pagination) requestParam.get("pagination");
			pagination = buildService.findArticleBuildListPagination(pagination, siteId);
			responseParam.put("pagination", pagination);
		 	
		}else if(dealMethod.equals("deleteBuildArtilces")){
			//删除生成列表某几条数据			
			String ids = (String)requestParam.get("ids");
			buildService.deleteArticleBuildListByIds(ids);
		} else if (dealMethod.equals("clear")){
			//清空
			buildService.deleteAll();
		}
	}

	@Override
	public ResponseEvent validateData(RequestEvent requestEvent)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param buildService the buildService to set
	 */
	public void setBuildService(BuildService buildService) {
		this.buildService = buildService;
	}

 

}
