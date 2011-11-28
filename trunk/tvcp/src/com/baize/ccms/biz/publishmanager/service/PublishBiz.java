/**
 * project：通用内容管理系统
 * Company: 南京瀚沃信息科技有限责任公司
 */
package com.baize.ccms.biz.publishmanager.service;

import java.util.Map;

import com.baize.ccms.biz.sitemanager.domain.Site;
import com.baize.ccms.search.util.GlobalFunc;
import com.baize.common.core.dao.Pagination;
import com.baize.common.core.service.BaseService;
import com.baize.common.core.util.FileUtil;
import com.baize.common.core.web.event.RequestEvent;
import com.baize.common.core.web.event.ResponseEvent;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 发布管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司</p>
 * @author <a href="mailto:sean_yang@163.com">杨信</a>
 * @version 1.0
 * @since 2009-6-20 上午10:40:28
 * @history（历次修订内容、修订人、修订时间等）
 */
public class PublishBiz extends BaseService {
	
	/** 注入发布Service */
	private PublishService publishService;

	@Override
	public void performTask(RequestEvent requestEvent, ResponseEvent responseEvent)
			throws Exception {
		String dealMethod = requestEvent.getDealMethod();
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		String siteId = requestEvent.getSiteid();
		String sessionID = requestEvent.getSessionID();
		// 查看发布列表
		if (dealMethod.equals("")) {
			Pagination pagination = (Pagination) requestParam.get("pagination");
			pagination = publishService.findArticlePublishListPagination(pagination, siteId);
			responseParam.put("pagination", pagination);
			
		// 发布
		} else if (dealMethod.equals("publish")) {
			log.info("发布");
			String publishAll = (String) requestParam.get("publishAll");
			String columnIds = (String) requestParam.get("columnIds");
			String publishType = (String) requestParam.get("publishType");
			boolean publishTemplate = (Boolean) requestParam.get("publishTemplate");
			if(publishAll.equals("yes")) {
				columnIds = publishService.publishAll(siteId, sessionID);
			}
			responseParam.put("publishType", publishType);
			publishService.publish(columnIds, publishType, publishTemplate, siteId, sessionID);
			
		}else if(dealMethod.equals("deleteBuildArtilces")){
			//删除发布列表某几条数据			
			String ids = (String)requestParam.get("ids");
			publishService.deleteArticlePublishListByIds(ids);
			
		} else if (dealMethod.equals("clear")){
			//清空
			publishService.deleteAll();
			
		} else if(dealMethod.equals("index")){
			// 创建索引
			log.debug("创建索引");
			String publishAll = (String) requestParam.get("publishAll");
			String columnIds = (String) requestParam.get("columnIds");
			String publishType = (String) requestParam.get("publishType");
			if(publishAll.equals("yes")) {
				// 创建整站索引
				publishService.createAllSiteIndex(siteId);
			} else {
				// 创建栏目和文章索引
				publishService.createColumnAndArticleIndex(columnIds, siteId);
			}
			responseParam.put("publishType", publishType);
		}else if(dealMethod.equals("publishArticles")){
			//发布列表页面的发布	 
			String articleIds = (String)requestParam.get("articleIds");
			publishService.publishArticleByArticleId(articleIds, siteId);
		}
	}

	@Override
	public ResponseEvent validateData(RequestEvent requestEvent)
			throws Exception {
		return null;
	}

	/**
	 * @param publishService the publishService to set
	 */
	public void setPublishService(PublishService publishService) {
		this.publishService = publishService;
	}

}
