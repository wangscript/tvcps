/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.biz.configmanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.baize.common.core.service.BaseService;
import com.baize.common.core.web.event.RequestEvent;
import com.baize.common.core.web.event.ResponseEvent;


/**
 * <p>标题: —— 系统设置功能初始化业务流转层</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 系统设置</p>
 * <p>版权: Copyright (c) 2009南京百泽网络科技有限公司
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-7-17 上午11:45:59 
 * @history（历次修订内容、修订人、修订时间等） 
*/

public class InitBiz extends BaseService {

	/**  注入初始化业务层  **/
	private InitService initService;	
	@Override
	public void performTask(RequestEvent requestEvent, ResponseEvent responseEvent)
			throws Exception {
		String dealMethod = requestEvent.getDealMethod();
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		String userId = requestEvent.getSessionID();
		String siteId = requestEvent.getSiteid();
	    if(dealMethod.equals("getTree")) {
	    	//获取树
			log.info("加载树的信息");
			// 获得树的treeid
			String treeid = (String) requestParam.get("treeid");
			List<Object> list = new ArrayList<Object>();
			list = initService.getTreeList(treeid,userId,siteId, isUpSiteAdmin);
			responseParam.put("treelist", list);
			log.info("加载树的信息成功");
		}else if(dealMethod.equals("load")){
			//点击进入系统设置模块时查询出右测需要显示的 url
			String rightFrameUrl = initService.findRightFrameUrlByUserId(userId,siteId, isUpSiteAdmin);
			responseParam.put("rightFrameUrl", rightFrameUrl);
		}
	}

	
	/* (non-Javadoc)
	 * @see com.baize.common.core.service.BaseService#validateData(com.baize.common.core.web.event.RequestEvent)
	 */
	@Override
	public ResponseEvent validateData(RequestEvent requestEvent)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	/**
	 * @param initService the initService to set
	 */
	public void setInitService(InitService initService) {
		this.initService = initService;
	}


	/**
	 * @return the initService
	 */
	public InitService getInitService() {
		return initService;
	}
}
