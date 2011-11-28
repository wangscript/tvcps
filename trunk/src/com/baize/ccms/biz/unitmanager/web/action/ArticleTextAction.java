/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.unitmanager.web.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.baize.ccms.biz.unitmanager.web.form.ArticleTextForm;
import com.baize.ccms.sys.GlobalConfig;
import com.baize.common.core.util.StringUtil;
import com.baize.common.core.web.GeneralAction;
import com.baize.common.core.web.event.RequestEvent;
import com.baize.common.core.web.event.ResponseEvent;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CCMS</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 郑荣华
 * @version 1.0
 * @since 2009-7-23 上午11:03:54
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class ArticleTextAction extends GeneralAction {

	private String dealMethod = "";
	
	@Override
	protected void doFormFillment(ActionForm actionForm, ResponseEvent responseEvent,
			String userIndr) throws Exception {
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		ArticleTextForm form = (ArticleTextForm) actionForm;
		// 查找配置文件
		if(dealMethod.equals("findConfig")) {
			form = (ArticleTextForm) responseParam.get("form");
			
		// 保存文章正文的数据	
		} else if(dealMethod.equals("saveConfig") || dealMethod.equals("saveSiteConfig")) {
			form.setInfoMessage("保存设置成功!");
			log.debug("==============="+form.getInfoMessage());
			this.setRedirectPage("savesuccess", userIndr);
		}
	}

	@Override
	protected void init(String userIndr) throws Exception {
		this.setRedirectPage("success", userIndr);
	}

	@Override
	protected void performTask(ActionForm actionForm, RequestEvent requestEvent, String userIndr)
			throws Exception {
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		HttpServletRequest request = (HttpServletRequest) requestParam.get("HttpServletRequest");
		ArticleTextForm form = (ArticleTextForm) actionForm;
		this.dealMethod = form.getDealMethod();
		// 查找配置文件
		if(dealMethod.equals("findConfig")) {
			requestParam.put("form", form);
			
		// 保存文章正文的数据	
		} else if(dealMethod.equals("saveConfig") || dealMethod.equals("saveSiteConfig")) {
			requestParam.put("form", form);
		}
	}
}
