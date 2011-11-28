/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.plugin.rss.service;

import java.util.List;
import java.util.Map;

import com.baize.ccms.plugin.rss.service.impl.RssServiceImpl;
import com.baize.ccms.plugin.rss.web.form.RssForm;
import com.baize.ccms.sys.GlobalConfig;
import com.baize.common.core.service.BaseService;
import com.baize.common.core.web.event.RequestEvent;
import com.baize.common.core.web.event.ResponseEvent;

/**
 * <p>
 * 标题: —— 要求能简洁地表达出类的功能和职责
 * </p>
 * <p>
 * 描述: —— 简要描述类的职责、实现方式、使用注意事项等
 * </p>
 * <p>
 * 模块: CCMS
 * </p>
 * <p>
 * 版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * </p>
 * <p>
 * 网址：http://www.baizeweb.com
 * 
 * @author 曹名科
 * @version 1.0
 * @since 2009-10-17 下午12:09:21
 * @history（历次修订内容、修订人、修订时间等） 
 */
 

public class RssBiz extends BaseService {
	/** 注入rssService */
	private RssService rssService;

	@Override
	public void performTask(RequestEvent requestEvent,
			ResponseEvent responseEvent) throws Exception {
		String dealMethod = requestEvent.getDealMethod();
		Map<Object, Object> requestParam = requestEvent.getReqMapParam();
		Map<Object, Object> responseParam = responseEvent.getRespMapParam();
		// 网站id
		String siteId = requestEvent.getSiteid();
		RssForm rssForm = (RssForm) requestParam.get("ressForm");
		if (dealMethod.equals("")) {
			Map<String,String> m = rssService.readRssXml(siteId);//读取RSS设置
			rssForm.setSpacingTime(m.get("rssTimeOut"));
			rssForm.setIsColumnOrMoreColumn(m.get("rssColumn"));
			responseParam.put("rssForm", rssForm);
		} else if (dealMethod.equals("save")) {
			String ids = rssForm.getIds();
			rssService.saveRssSet(rssForm,siteId);
			List<String> lis=rssService.getRssList(ids, siteId);
			rssService.isColumnsOrMoreColumns(siteId);
			responseParam.put("list", lis);
			rssService.tt(siteId);
		
		// 发布rss目录
		} else if(dealMethod.equals("publishRss")){
			rssService.publishRssDir(siteId);
			responseParam.put("rssForm", rssForm);
		}

	}

	@Override
	public ResponseEvent validateData(RequestEvent arg0) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param rssService
	 *            the rssService to set
	 */
	public void setRssService(RssService rssService) {
		this.rssService = rssService;
	}
}
