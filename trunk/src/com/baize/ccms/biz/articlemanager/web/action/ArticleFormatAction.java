/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.biz.articlemanager.web.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.baize.ccms.biz.articlemanager.domain.ArticleFormat;
import com.baize.ccms.biz.articlemanager.web.form.ArticleFormatForm;
import com.baize.common.core.dao.Pagination;
import com.baize.common.core.web.GeneralAction;
import com.baize.common.core.web.event.RequestEvent;
import com.baize.common.core.web.event.ResponseEvent;

/**
 * <p>标题: 格式Action</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 文章管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-3-31 上午09:45:25
 * @history（历次修订内容、修订人、修订时间等）
 */
public class ArticleFormatAction extends GeneralAction {
	
	private String dealMethod = "";

	@Override
	protected void doFormFillment(ActionForm actionForm, ResponseEvent responseEvent, String userIndr) {
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		ArticleFormatForm formatForm = (ArticleFormatForm) actionForm;
		
		// 显示列表
		if (dealMethod.equals("")) {
			formatForm.setPagination((Pagination) responseParam.get("pagination"));
			
		// 显示明细
		} else if (dealMethod.equals("detail")) {
			ArticleFormat format = (ArticleFormat) responseParam.get("format");
			formatForm.setFormat(format);
			
			this.setRedirectPage("detail", userIndr);
			
		// 设置属性
		} else if (dealMethod.equals("attribute")) {
			
		// 添加格式
		} else if (dealMethod.equals("add")) {
			String infoMessage = "";
			infoMessage = (String) responseParam.get("infoMessage");
			formatForm.setInfoMessage(infoMessage);
			this.setRedirectPage("detail", userIndr);
	
		// 删除格式	
		} else if(dealMethod.equals("delete")) {
			String infoMessage = "";
			infoMessage = (String) responseParam.get("infoMessage");
			formatForm.setInfoMessage(infoMessage);
			this.setRedirectPage("delete", userIndr);
		 
		// 修改格式	
		} else if (dealMethod.equals("modify")) {
			String infoMessage = "";
			infoMessage = (String) responseParam.get("infoMessage");
			formatForm.setInfoMessage(infoMessage);
			this.setRedirectPage("detail", userIndr);
			
			//导出
		} else if (dealMethod.equals("export")) {
			String message = (String) responseParam.get("message");
			this.setRedirectPage("webClient", userIndr);
			
			// 导入格式
		} else 	if(dealMethod.equals("import")) {
			String message = (String) responseParam.get("message");
			formatForm.setInfoMessage(message);
			this.setRedirectPage("import", userIndr);
			
		}
		
	}

	@Override
	protected void init(String userIndr) throws Exception {
		this.setRedirectPage("success", userIndr);
	}

	@Override
	protected void performTask(ActionForm actionForm, RequestEvent requestEvent, String userIndr) throws Exception {
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		ArticleFormatForm formatForm = (ArticleFormatForm) actionForm;
		this.dealMethod = formatForm.getDealMethod();
		ArticleFormat format = null;
		HttpServletRequest request = (HttpServletRequest) requestEvent.getReqMapParam().get("HttpServletRequest");
		
		// 显示列表
		if (dealMethod.equals("")) {
			
			formatForm.setQueryKey("findFormatPage");
		
		// 显示明细
		} else if (dealMethod.equals("detail")) {
			
			requestParam.put("id", formatForm.getId());
			
		// 添加格式
		} else if (dealMethod.equals("add")) {
			
			requestParam.put("format", formatForm.getFormat());
			
		// 删除格式
		} else if (dealMethod.equals("delete")) {
			
			requestParam.put("formatIds", formatForm.getIds());
			
		// 设置属性
		} else if (dealMethod.equals("attribute")) {
			
			
		// 修改格式
		} else if (dealMethod.equals("modify")) {
			
			format = formatForm.getFormat();
			requestParam.put("format", format);
			
			//导出
		} else if (dealMethod.equals("export")) {
			String exportFormatIds = formatForm.getExportFormatIds();
			if(exportFormatIds == null || exportFormatIds.equals("") || exportFormatIds.equals("null")) {
				exportFormatIds = null;
			}
			requestParam.put("exportFormatIds", exportFormatIds);
			requestParam.put("request", request);
			
			// 导入
		} else 	if(dealMethod.equals("import")) {
			String path = formatForm.getPath();
	         requestParam.put("path", path);
			
		} 
		
	}
	
}
