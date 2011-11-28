/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.articlemanager.web.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.baize.ccms.biz.articlemanager.domain.Enumeration;
import com.baize.ccms.biz.articlemanager.web.form.EnumerationForm;
import com.baize.common.core.dao.Pagination;
import com.baize.common.core.web.GeneralAction;
import com.baize.common.core.web.event.RequestEvent;
import com.baize.common.core.web.event.ResponseEvent;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CCMS</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司</p>
 * <p>网址：http://www.baizeweb.com
 * @author <a href="mailto:sean_yang@163.com">杨信</a>
 * @version 1.0
 * @since 2009-9-2 上午11:37:17
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class EnumerationAction extends GeneralAction {

	private String dealMethod = "";
	
	@SuppressWarnings("unchecked")
	@Override
	protected void doFormFillment(ActionForm actionForm,
			ResponseEvent responseEvent, String userIndr) throws Exception {
		EnumerationForm form = (EnumerationForm) actionForm;
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();

		if (dealMethod.equals("")) {
			log.info("显示枚举列表");
			Pagination pagination = (Pagination) responseParam.get("pagination");
			form.setPagination(pagination);
			this.setRedirectPage("success", userIndr);
		
		} else if (dealMethod.equals("add")) {
			log.info("跳转到添加页面");
			String allEnumNameStr = (String) responseParam.get("allEnumNameStr");
			form.setAllEnumNameStr(allEnumNameStr);
			this.setRedirectPage("detail", userIndr);
			
		} else if (dealMethod.equals("delete")) {
			log.info("删除枚举类别");
			String message = "";
			message = (String) responseParam.get("message");
			form.setInfoMessage(message);
			this.setRedirectPage("delete", userIndr);
			
		} else if (dealMethod.equals("detail")) {
			log.info("查看枚举信息");	
			String enumValuesStr = (String) responseParam.get("enumValuesStr");
			String allEnumNameStr = (String) responseParam.get("allEnumNameStr");
			String enumerationName = (String) responseParam.get("enumerationName");
			String enumerationId = (String) responseParam.get("enumerationId");
			form.setEnumerationName(enumerationName);
			form.setAllEnumNameStr(allEnumNameStr);
			form.setEnumValuesStr(enumValuesStr);
			form.setEnumerationId(enumerationId);
			form.setInfoMessage("查看枚举信息");
			this.setRedirectPage("detail", userIndr);
			
		} else if (dealMethod.equals("modify")) {
			log.info("修改枚举类别");
			String message = (String) responseParam.get("message");
			form.setInfoMessage(message);
			this.setRedirectPage("detail", userIndr);
			
		} else if (dealMethod.equals("findEnumValues")) {
			log.info("查找枚举值");
			Enumeration enumeration = (Enumeration) responseParam.get("enumeration");
			List list = enumeration.getEnumValues();
			form.setList(list);
			form.setEnumeration(enumeration);
			this.setRedirectPage("findEnumValues", userIndr);
			
		} else if (dealMethod.equals("addEnumValues")) {
			log.info("添加枚举信息");
			String allEnumNameStr = (String) responseParam.get("allEnumNameStr");
			String message = (String) responseParam.get("message");
			form.setAllEnumNameStr(allEnumNameStr);
			form.setInfoMessage(message);
			this.setRedirectPage("addOrModifyEnumValuesSuccess", userIndr);
			
		} else if (dealMethod.equals("modifyEnumValues")) {
			log.info("修改枚举信息");
			String message = (String) responseParam.get("message");
			form.setInfoMessage(message);
			this.setRedirectPage("addOrModifyEnumValuesSuccess", userIndr);
			
			//导出
		} else if (dealMethod.equals("export")) {
			String message = (String) responseParam.get("message");
			this.setRedirectPage("webClient", userIndr);
			
			// 导入枚举
		} else 	if(dealMethod.equals("import")) {
			String message = (String) responseParam.get("message");
			form.setInfoMessage(message);
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
		
		HttpServletRequest request = (HttpServletRequest) requestEvent.getReqMapParam().get("HttpServletRequest");
		EnumerationForm enumerationForm = (EnumerationForm) actionForm;
		this.dealMethod = enumerationForm.getDealMethod();
		
		
		if (dealMethod.equals("")) {
			log.info("显示枚举列表");
			enumerationForm.setQueryKey("findEnumerationPage");
		
		} else if (dealMethod.equals("add")) {
			log.info("跳转到添加页面");
			
			
		} else if (dealMethod.equals("delete")) {
			log.info("删除枚举类别");
			requestParam.put("enumerationIds", enumerationForm.getIds());
			
		} else if (dealMethod.equals("detail")) {
			log.info("查看枚举信息");
			String enumerationId = enumerationForm.getEnumerationId();
			requestParam.put("enumerationId", enumerationId);
			
		} else if (dealMethod.equals("findEnumValues")) {
			log.info("查找枚举值");
	//		String enumerationName = enumerationForm.getEnumerationName();
			String enumerationId = enumerationForm.getEnumerationId();
	//		requestParam.put("enumerationName", enumerationName);
			requestParam.put("enumerationId", enumerationId);
			
		} else if (dealMethod.equals("addEnumValues")) {
			log.info("添加枚举信息");
			String enumerationName = enumerationForm.getEnumerationName();
			String enumValuesStr = enumerationForm.getEnumValues();
			requestParam.put("enumerationName", enumerationName);
			requestParam.put("enumValuesStr", enumValuesStr);
			
		} else if (dealMethod.equals("modifyEnumValues")) {
			log.info("修改枚举信息");
			String enumerationName = enumerationForm.getEnumerationName();
			String enumValuesStr = enumerationForm.getEnumValues();
			String enumerationId = enumerationForm.getEnumerationId();
			requestParam.put("enumerationId", enumerationId);
			requestParam.put("enumerationName", enumerationName);
			requestParam.put("enumValuesStr", enumValuesStr);
			
			//导出
		} else if (dealMethod.equals("export")) {
			String exportEnumIds = enumerationForm.getExportEnumIds();
			if(exportEnumIds == null || exportEnumIds.equals("") || exportEnumIds.equals("null")) {
				exportEnumIds = null;
			}
			requestParam.put("exportEnumIds", exportEnumIds);
			requestParam.put("request", request);
			
			// 导入
		} else 	if(dealMethod.equals("import")) {
			String path = enumerationForm.getPath();
	         requestParam.put("path", path);
			
		}

	}

}
