/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.publishmanager.web.action;

import java.util.Map;

import org.apache.struts.action.ActionForm;

import com.j2ee.cms.biz.publishmanager.web.form.BuildForm;
import com.j2ee.cms.biz.publishmanager.web.form.PublishForm;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.web.GeneralAction;
import com.j2ee.cms.common.core.web.event.RequestEvent;
import com.j2ee.cms.common.core.web.event.ResponseEvent;

/**
 * 
 * <p>标题: —— 生成列表action</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 发布管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-11-11 下午02:13:18
 * @history（历次修订内容、修订人、修订时间等）
 */
public class BuildAction extends GeneralAction {
	
	private String dealMethod = "";

	@Override
	protected void doFormFillment(ActionForm actionForm, ResponseEvent responseEvent, String userIndr) {
		BuildForm form = (BuildForm) actionForm;
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		
		if (dealMethod.equals("")) {
			//查看生成列表
			Pagination pagination = (Pagination) responseParam.get("pagination");
			form.setPagination(pagination);		
		 	
		} else if (dealMethod.equals("deleteBuildArtilces") || dealMethod.equals("clear")) {
			//删除成功转向或清空成功转向
			this.setRedirectPage("return", userIndr);
		} 
		
	}

	@Override
	protected void init(String userIndr) throws Exception {
		this.setRedirectPage("success", userIndr);
	}

	@Override
	protected void performTask(ActionForm actionForm, RequestEvent requestEvent, String userIndr) throws Exception {
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();	
		BuildForm form = (BuildForm) actionForm;
		this.dealMethod = form.getDealMethod();
		if (dealMethod.equals("")) {
			// 查看发布列表
			form.setQueryKey("findAllBuildArticleBySiteIdPage");
		 
		} else if (dealMethod.equals("deleteBuildArtilces")){
			//删除生成列表某几条数据
			String ids = form.getIds();
			requestParam.put("ids", ids);
		} else if (dealMethod.equals("clear")){
			//清空
		}
	}

}
