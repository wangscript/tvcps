/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.templatemanager.web.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.j2ee.cms.biz.templatemanager.domain.TemplateUnitStyle;
import com.j2ee.cms.biz.templatemanager.web.form.TemplateUnitStyleForm;
import com.j2ee.cms.biz.unitmanager.web.form.TitleLinkForm;
import com.j2ee.cms.sys.GlobalConfig;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.util.StringUtil;
import com.j2ee.cms.common.core.web.GeneralAction;
import com.j2ee.cms.common.core.web.event.RequestEvent;
import com.j2ee.cms.common.core.web.event.ResponseEvent;

/**
 * 
 * <p>标题: —— 模板单元样式action处理类</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 模板管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-6-6 下午05:46:15
 * @history（历次修订内容、修订人、修订时间等）
 */

public class TemplateUnitStyleAction extends GeneralAction {
	
	private String dealMethod = "";


	@Override
	protected void doFormFillment(ActionForm actionForm, ResponseEvent responseEvent, String userIndr) {
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
	
		TemplateUnitStyleForm form = (TemplateUnitStyleForm) actionForm;	
		if (dealMethod.equals("list")) {
			//查找样式列表
			String categoryId = String.valueOf(responseParam.get("categoryId"));
			form.setPagination((Pagination) responseParam.get("pagination"));
			form.setCategoryName(String.valueOf(responseParam.get("categoryName")));
			form.setCategoryId(categoryId);
			
		}else if (dealMethod.equals("detail")) {
			//样式详细信息页面
			String categoryId = String.valueOf(responseParam.get("categoryId"));	
			form.setCategoryId(categoryId);
			form.setCategoryName(String.valueOf(responseParam.get("categoryName")));
			form.setStyleId(String.valueOf(responseParam.get("styleId")));
			//所有样式名称
			String styleNameStr = String.valueOf(responseParam.get("styleNameStr"));
			form.setStyleNameStr(styleNameStr);
			TemplateUnitStyle templateUnitStyle = (TemplateUnitStyle)responseParam.get("templateUnitStyle");
			form.setTemplateUnitStyle(templateUnitStyle);
			form.setInfoMessage("");
			this.setRedirectPage("detail", userIndr);

		}else if (dealMethod.equals("add")) {
			//增加样式--修改样式
			//查找样式列表
			String categoryId = String.valueOf(responseParam.get("categoryId"));
			form.setPagination((Pagination) responseParam.get("pagination"));
			form.setCategoryName(String.valueOf(responseParam.get("categoryName")));
			form.setCategoryId(categoryId);
			form.setInfoMessage("添加或修改成功");
			this.setRedirectPage("detail", userIndr);

		}else if (dealMethod.equals("delete")) {
			//删除样式
			String categoryId = String.valueOf(responseParam.get("categoryId"));
			form.setPagination((Pagination) responseParam.get("pagination"));
			form.setCategoryName(String.valueOf(responseParam.get("categoryName")));
			form.setCategoryId(categoryId);
		}else if(dealMethod.equals("findStyleById")){
			//根据样式管理的ID查询样式的数据
			form = (TemplateUnitStyleForm)responseParam.get("templateUnitStyleForm");
			this.setRedirectPage("findStyle", userIndr);
		}
	}
	
	@Override
	protected void performTask(ActionForm actionForm, RequestEvent requestEvent, String userIndr) throws Exception {
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		HttpServletRequest request = (HttpServletRequest) requestParam.get("HttpServletRequest");
		TemplateUnitStyleForm form = (TemplateUnitStyleForm) actionForm;	
		this.dealMethod = form.getDealMethod();
		
		if (dealMethod.equals("list")) {
			//查找样式列表
			String categoryId = form.getCategoryId();
			form.setQueryKey("findTemplateUnitStylePage");
			requestParam.put("categoryId", categoryId);
			requestParam.put("pagination", form.getPagination());

		}else if (dealMethod.equals("detail")) {
			//样式详细信息页面
			String styleId = form.getStyleId();
			String categoryId = form.getCategoryId();
			String categoryName = form.getCategoryName();
		//	categoryName = new String(categoryName.getBytes("iso-8859-1"),"utf-8");
			requestParam.put("styleId", styleId);
			requestParam.put("categoryId", categoryId);
			requestParam.put("categoryName", categoryName);
			
		}else if (dealMethod.equals("add")) {
			//增加样式--修改样式
			TemplateUnitStyle templateUnitStyle = form.getTemplateUnitStyle();
			//模板单元类型ID
			String categoryId = form.getCategoryId();
			//html代码
			String htmlContent = form.getHtmlContent();
			String styleId = form.getStyleId();
			form.setQueryKey("findTemplateUnitStylePage");
			requestParam.put("templateUnitStyle", templateUnitStyle);
			requestParam.put("categoryId", categoryId);
			requestParam.put("htmlContent", htmlContent);
			requestParam.put("styleId", styleId);

		}else if (dealMethod.equals("delete")) {
			//删除样式
			String ids = form.getIds();
			//查找样式列表
			String categoryId = form.getCategoryId();
			form.setQueryKey("findTemplateUnitStylePage");
			requestParam.put("categoryId", categoryId);
			requestParam.put("ids", ids);
		}else if(dealMethod.equals("findStyleById")){
			//根据样式管理的ID查询样式的数据
			requestParam.put("templateUnitStyleForm", form);
		}
		
	}
	
	@Override
	protected void init(String arg0) throws Exception {
		this.setRedirectPage("success", userIndr);
	}
}
