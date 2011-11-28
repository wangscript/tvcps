/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.configmanager.service;

import java.util.Map;

import com.baize.ccms.biz.configmanager.domain.GeneralSystemSet;
import com.baize.ccms.biz.configmanager.domain.InformationFilter;
import com.baize.common.core.dao.Pagination;
import com.baize.common.core.service.BaseService;
import com.baize.common.core.web.event.RequestEvent;
import com.baize.common.core.web.event.ResponseEvent;

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
 * 版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * 
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-2-16 下午03:05:55
 * @history（历次修订内容、修订人、修订时间等） 
 */
 
public final class InformationFilterBiz extends BaseService {
	// 作者服务层类
	private InformationFilterService informationFilterService;

	@Override
	public void performTask(RequestEvent requestEvent,
			ResponseEvent responseEvent) throws Exception {
		Pagination pagination;
		String dealMethod = requestEvent.getDealMethod();
		Map<Object, Object> requestParam = requestEvent.getReqMapParam();
		Map<Object, Object> responseParam = responseEvent.getRespMapParam();
		// 获取sessionID
		String sessionID = requestEvent.getSessionID();
		// 网站id
		String siteId = requestEvent.getSiteid();
		// 创建系统设置对象
		InformationFilter informationFilter;
		// this.isUpSystemAdmin;
		// 开始系统设置数据并分页!
		if (dealMethod.equals("")) {
			log.debug("开始查询机构分页数据!");
			pagination = (Pagination) requestParam.get("pagination");
			String categoryId = (String) requestParam.get("categoryId");
			responseParam.put("categoryId", categoryId);
			// 如果是系统管理员全部查询
			if (this.isUpSystemAdmin) {
				pagination = informationFilterService.findInformationFilterData(pagination);
				
			} else {
				/** 普通用户查寻自己栏目下过滤内容 */
				pagination = informationFilterService.findInformationSessionIdFilterData(
						pagination,sessionID, siteId);
			}
			if(pagination.getData().size()==0){
				pagination.currPage = pagination.currPage-1;
				if (this.isUpSystemAdmin) {
					pagination = informationFilterService.findInformationFilterData(pagination);
					
				} else {
					/** 普通用户查寻自己栏目下过滤内容 */
					pagination = informationFilterService.findInformationSessionIdFilterData(
							pagination,sessionID, siteId);
				}
			}
			responseParam.put("pagination", pagination);
			// log.info("查询机构分页成功.")；
			// 对统设置数据添加
		} else if (dealMethod.equals("detail")) {
			String categoryId = (String) requestParam.get("categoryId");
			responseParam.put("categoryId", categoryId);

		} else if (dealMethod.equals("add")) {
			informationFilter = (InformationFilter) requestParam
					.get("informationFilter");
			String categoryId = (String) requestParam.get("categoryId");
			responseParam.put("categoryId", categoryId);
			String message = informationFilterService
					.addinformationFilterService(informationFilter, siteId,
							sessionID);
			responseParam.put("message", message);
		} else if (dealMethod.equals("delete")) {
			String authorId = (String) requestParam.get("authorId");
			String categoryId = (String) requestParam.get("categoryId");
			responseParam.put("categoryId", categoryId);
			if (authorId != null && !authorId.equals("")) {
				// 用于删除作者时使用
				informationFilterService
						.deleteInformationFilterService(authorId);
			}
			// 来源设置
		} else if (dealMethod.equals("update")) {

			// informationFilter
			informationFilter = (InformationFilter) requestParam
					.get("informationFilter");
			informationFilterService
					.modifyInformationFilterServic(informationFilter);
			String categoryId = (String) requestParam.get("categoryId");
			responseParam.put("categoryId", categoryId);
			String message = "修改作者信息成功";
			responseParam.put("message", "1");
		} else if (dealMethod.equals("updateOne")) {

			String authorId = (String) requestParam.get("authorId");
			String categoryId = (String) requestParam.get("categoryId");
			responseParam.put("categoryId", categoryId);
			informationFilter = new InformationFilter();
			if (authorId != null && !authorId.equals("")) {

				informationFilter = informationFilterService
						.findInformationFilterByKey(authorId);
			}

			responseParam.put("informationFilter", informationFilter);

		}
	}

	public InformationFilterService getInformationFilterService() {
		return informationFilterService;
	}

	public void setInformationFilterService(
			InformationFilterService informationFilterService) {
		this.informationFilterService = informationFilterService;
	}

	@Override
	public ResponseEvent validateData(RequestEvent req) throws Exception {
		// TODO 自动生成方法存根
		return null;
	}

}
