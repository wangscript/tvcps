/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.plugin.onlineBulletin.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.baize.ccms.biz.sitemanager.domain.Site;
import com.baize.ccms.biz.usermanager.domain.User;
import com.baize.ccms.plugin.onlineBulletin.domain.OnlineBulletin;
import com.baize.common.core.dao.Pagination;
import com.baize.common.core.service.BaseService;
import com.baize.common.core.util.DateUtil;
import com.baize.common.core.web.event.RequestEvent;
import com.baize.common.core.web.event.ResponseEvent;

/**
 * <p>
 * 标题: 网上调业务处理类
 * </p>
 * <p>
 * 描述: 网上调的查看,删除等功能
 * </p>
 * <p>
 * 模块:网上调
 * </p>
 * <p>
 * 版权: Copyright (c) 2009南京百泽网络科技有限公司
 * 
 * @author 包坤涛
 * @version 1.0
 * @since 2009-6-14 下午02:26:33
 * @history（历次修订内容、修订人、修订时间等） 
*/
 

public class OnlineBulletinBiz extends BaseService {

	/** 注入消息业务对象 **/
	private OnlineBulletinService onlineBulletinService;

	@SuppressWarnings("unchecked")
	@Override
	public void performTask(RequestEvent requestEvent,
			ResponseEvent responseEvent) throws Exception {
		// 分页对象
		Pagination pagination;
		String dealMethod = requestEvent.getDealMethod();
		Map<Object, Object> requestParam = requestEvent.getReqMapParam();
		Map<Object, Object> responseParam = responseEvent.getRespMapParam();
		String siteID = requestEvent.getSiteid();
		String  sessionID = requestEvent.getSessionID();
		if (dealMethod.equals("list")) {
			
			log.debug("开始查询调查内容分页数据!");
			// 获取request网上调查问题分页对象
			pagination = (Pagination) requestParam.get("pagination");
			pagination.queryKey = "findOnlineBulletinFormPage";
			responseParam.put("pagination", onlineBulletinService.finOnlineBulletinServiceData(pagination, siteID));
			log.info("查询调查内容分页成功.");
		} else if (dealMethod.equals("detail")) {
			OnlineBulletin onlineBulletin = (OnlineBulletin) requestParam.get("onlineBulletin");
			// 网站对象
			Site site = new Site();
			// 网站id
			site.setId(siteID);
			 //用户对象
  	        User user=new User();
    	    //用户sessionid
    	    user.setId(sessionID);
    	    //存储用户
    	    onlineBulletin.setUser(user);
			onlineBulletin.setSite(site);
			if (null != onlineBulletin&& (!onlineBulletin.equals("") && (null != onlineBulletin.getName()))) {
				// 添加网上调查实体对象反会值为"1"
				String message = onlineBulletinService.addOnlineBulletinService(onlineBulletin);
				responseParam.put("message", message);
			}
		} else if (dealMethod.equals("delete")) {
			    String id = (String) requestParam.get("id");
				if (id != null && !id.equals("")) {
					// 用于删除网上调查问题查询时使用
					String message = onlineBulletinService.deleteOnlineBulletinService(id);
					responseParam.put("message", message);
				}
		}else if (dealMethod.equals("update")) {
			 String id = (String) requestParam.get("id");
			 OnlineBulletin	 onlineBulletin = onlineBulletinService.findOnlineBulletin(id);
			 Date endTime = onlineBulletin.getEndTime();
			 SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 String date = s.format(endTime);
			 responseParam.put("endTime", date);
			 responseParam.put("onlineBulletin", onlineBulletin);
			 
		}else if (dealMethod.equals("updated")) {                            
			OnlineBulletin onlineBulletin = (OnlineBulletin) requestParam.get("onlineBulletin");
			// modifyOnlineSurveyContentAnswer
			String  message = onlineBulletinService.modifyOnlineBulletinService(onlineBulletin);
			responseParam.put("message",message);
			
		// 发布网上公告目录	
		}else if(dealMethod.equals("publishBulletin")){
			onlineBulletinService.publishBulletin(siteID);
			
		// 查找网上公告绑定的栏目
		}else if(dealMethod.equals("findBindColumn")){
			String bulletinId = (String)requestParam.get("bulletinId");
			String columnIds = onlineBulletinService.findBindColumnIds(bulletinId);
			responseParam.put("columnIds", columnIds);
			responseParam.put("bulletinId", bulletinId);
			
		// 绑定栏目	
		} else if(dealMethod.equals("bindColumn")){
			OnlineBulletin onlineBulletin = (OnlineBulletin)requestParam.get("onlineBulletin");
			String columnIds = onlineBulletin.getColumnIds();
			String bulletinId = (String)requestParam.get("bulletinId");
			onlineBulletinService.modifyBindColumn(columnIds, bulletinId);
		}
	}

	public ResponseEvent validateData(RequestEvent arg0) throws Exception {

		return null;
	}

	/**
	 * @return the onlineBulletinService
	 */
	public OnlineBulletinService getOnlineBulletinService() {
		return onlineBulletinService;
	}

	/**
	 * @param onlineBulletinService
	 *            the onlineBulletinService to set
	 */
	public void setOnlineBulletinService(
			OnlineBulletinService onlineBulletinService) {
		this.onlineBulletinService = onlineBulletinService;
	}

}
