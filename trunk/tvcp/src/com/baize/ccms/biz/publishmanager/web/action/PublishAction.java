/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.publishmanager.web.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.j2ee.cms.biz.publishmanager.web.form.PublishForm;
import com.j2ee.cms.sys.GlobalConfig;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.util.StringUtil;
import com.j2ee.cms.common.core.web.GeneralAction;
import com.j2ee.cms.common.core.web.event.RequestEvent;
import com.j2ee.cms.common.core.web.event.ResponseEvent;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 发布管理</p>
 * <p>版权: Copyright (c) 2009  </p>
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-6-18 上午10:35:13
 * @history（历次修订内容、修订人、修订时间等）
 */
public class PublishAction extends GeneralAction {
	
	private String dealMethod = "";

	@Override
	protected void doFormFillment(ActionForm actionForm, ResponseEvent responseEvent, String userIndr) {
		PublishForm form = (PublishForm) actionForm;
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		// 查看发布列表
		if (dealMethod.equals("")) {   
			Pagination pagination = (Pagination) responseParam.get("pagination");
			form.setPagination(pagination);
		          
		// 发布	
		} else if (dealMethod.equals("publish")) {
			form.setInfoMessage("发布成功");
			form.setPublishType((String)responseParam.get("publishType"));
			this.setRedirectPage("publishSuccess", userIndr);

		//删除成功转向或清空成功转向	
		} else if (dealMethod.equals("deleteBuildArtilces") || dealMethod.equals("clear")) {
			this.setRedirectPage("return", userIndr);
			
		//创建索引			
		} else if(dealMethod.equals("index")){
			form.setInfoMessage("创建索引成功");
			this.setRedirectPage("publishSuccess", userIndr);
		}else if(dealMethod.equals("publishArticles")){
			//发布列表页面的发布
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
		HttpServletRequest request = (HttpServletRequest) requestParam.get("HttpServletRequest");
		
		PublishForm form = (PublishForm) actionForm;
		this.dealMethod = form.getDealMethod();
		
		// 查看发布列表
		if (dealMethod.equals("")){
			form.setQueryKey("findAllPublishArticleBySiteIdPage");
			
		// 发布
		} else if (dealMethod.equals("publish")){
			String publishAll = form.getPublishAll();
			String columnIds = form.getColumnIds();
			String publishType = form.getPublishType();
			boolean publishTemplate = form.isPublishTemplate();
			requestParam.put("columnIds", columnIds);
			requestParam.put("publishType", publishType);
			requestParam.put("publishAll", publishAll);
			requestParam.put("publishTemplate", publishTemplate);
			
		//删除发布列表某几条数据
		} else if (dealMethod.equals("deleteBuildArtilces")){
			String ids = form.getIds();
			requestParam.put("ids", ids);

		//清空	
		} else if (dealMethod.equals("clear")){
			
		//创建索引	
		} else if(dealMethod.equals("index")){
			String publishAll = form.getPublishAll();
			String columnIds = form.getColumnIds();
			String publishType = form.getPublishType();
			requestParam.put("columnIds", columnIds);
			requestParam.put("publishType", publishType);
			requestParam.put("publishAll", publishAll);
		}else if(dealMethod.equals("publishArticles")){
			//发布列表页面的发布
			String articleIds = form.getIds();
			requestParam.put("articleIds", articleIds);
		}
	}
}
