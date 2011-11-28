/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.biz.articlemanager.web.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.baize.ccms.biz.articlemanager.domain.ArticleAttribute;
import com.baize.ccms.biz.articlemanager.domain.ArticleFormat;
import com.baize.ccms.biz.articlemanager.domain.Enumeration;
import com.baize.ccms.biz.articlemanager.web.form.ArticleAttributeForm;
import com.baize.ccms.sys.GlobalConfig;
import com.baize.common.core.dao.Pagination;
import com.baize.common.core.util.CollectionUtil;
import com.baize.common.core.util.StringUtil;
import com.baize.common.core.web.GeneralAction;
import com.baize.common.core.web.event.RequestEvent;
import com.baize.common.core.web.event.ResponseEvent;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 文章管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-4-7 下午02:22:17
 * @history（历次修订内容、修订人、修订时间等）
 */
public class ArticleAttributeAction extends GeneralAction {
	
	private String dealMethod = "";

	@SuppressWarnings("unchecked")
		@Override
	protected void doFormFillment(ActionForm actionForm, ResponseEvent responseEvent, String userIndr) {
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		ArticleAttributeForm form = (ArticleAttributeForm) actionForm;
		
		//是不是默认格式的属性
		String fromDefault = (String) responseParam.get("fromDefault");
		form.setFromDefault(fromDefault);
		
		// 显示列表
		if (dealMethod.equals("")) {
			
			//默认的属性ids
			String defaultAttrIds = (String) responseParam.get("defaultAttrIds");
			Pagination pagination = (Pagination) responseParam.get("pagination");
			form.setPagination(pagination);
			String formatName = (String) responseParam.get("formatName");
			form.setFormatName(formatName);
			form.setDefaultAttrIds(defaultAttrIds);
			String formatFields = "";
			
			List data = pagination.getData();
			if (!CollectionUtil.isEmpty(data)) {
				String[] array = (String[]) data.get(0);
				formatFields = array[6];
			}
			// 将格式id和格式字段传到属性列表页
			form.setFormatFields(formatFields);
		
		// 添加属性	
		} else if (dealMethod.equals("add")) {
			String infoMessage = "";
			infoMessage = (String) responseParam.get("infoMessage");
			form.setInfoMessage(infoMessage);
			this.setRedirectPage("detail", userIndr);
			
		// 修改属性	
		} else if (dealMethod.equals("modify")) {
			String infoMessage = "";
			infoMessage = (String) responseParam.get("infoMessage");
			form.setAjaxMsg(infoMessage);
			this.setRedirectPage("ajaxmsg", userIndr);
			
		// 删除属性	
		} else if(dealMethod.equals("delete")) {
			form.setInfoMessage("删除属性成功");
			this.setRedirectPage("delete", userIndr);
			
		// 显示属性detail	
		} else if (dealMethod.equals("detail")) {
			String attributeNameStr = (String) responseParam.get("attributeNameStr");
			String formatId = (String) responseParam.get("formatId");
			String formatFields = (String) responseParam.get("formatFields");
			String formatName = (String) responseParam.get("formatName");
			String enumInfoStr = (String) responseParam.get("enumInfoStr");
			form.setFormatId(formatId);
			form.setEnumInfoStr(enumInfoStr);
			form.setFormatFields(formatFields);
			form.setFormatName(formatName);
			form.setAttributeNameStr(attributeNameStr);
			this.setRedirectPage("detail", userIndr);
			
		// 获得排序属性信息
		} else if (dealMethod.equals("findSortAttribute")) {
			String attributeInfoStr = (String) responseParam.get("attributeInfoStr");
			String formatId = (String) responseParam.get("formatId");
			form.setFormatId(formatId);
			form.setAttributeInfoStr(attributeInfoStr);
			form.setInfoMessage("排序");
			this.setRedirectPage("sortAttribute", userIndr);
			
		// 排序
		} else if (dealMethod.equals("sort")) {
			String message = (String) responseParam.get("message");
			String formatId = (String) responseParam.get("formatId");
			form.setFormatId(formatId);
			form.setInfoMessage(message);
			this.setRedirectPage("sortAttribute", userIndr);
			
		// 批量删除
		} else if (dealMethod.equals("deleteAttributes")) {
			String message = (String) responseParam.get("message");
			String formatId = (String) responseParam.get("formatId");
			form.setFormatId(formatId);
			form.setInfoMessage(message);
			this.setRedirectPage("deleteSuccess", userIndr);
		}
	}

	@Override
	protected void init(String userIndr) throws Exception {
		this.setRedirectPage("success", userIndr);
	}
	
	@Override
	protected void performTask(ActionForm actionForm, RequestEvent requestEvent, String userIndr) throws Exception {
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		ArticleAttributeForm form = (ArticleAttributeForm) actionForm;
		this.dealMethod = form.getDealMethod();
		ArticleAttribute attribute = null;
		
		//是不是默认格式的属性
		String fromDefault = form.getFromDefault();
		requestParam.put("fromDefault", fromDefault);
		
		// 显示列表页
		if (dealMethod.equals("")) {
			String formatId = form.getFormatId();
			requestParam.put("formatId", formatId);
			form.setQueryKey("findAttributePage");
			
		// 添加属性	
		} else if (dealMethod.equals("add")) {
			
			attribute = form.getAttribute();
			//设置orders为0
			attribute.setOrders(0);
			String enumerationId = form.getEnumerationId();
			String enumerationName = form.getEnumerationName();
			if(!enumerationId.equals("") && enumerationId != null) {
				Enumeration enumeration = new Enumeration();
				enumeration.setId(enumerationId);
				enumeration.setName(enumerationName);
				//保存枚举类别
				attribute.setEnumeration(enumeration);
				//设置有效值为任何字符
				attribute.setValidValue("all");
			}
			String formatId = form.getFormatId();
			String formatName = form.getFormatName();
			requestParam.put("formatName", formatName);
			String fields = form.getFormatFields();
			
			ArticleFormat format = new ArticleFormat();
			format.setId(formatId);
			format.setFields(fields);
			attribute.setArticleFormat(format);
			requestParam.put("attribute", attribute);
		
		// 删除属性	
		} else if (dealMethod.equals("delete")) {
			attribute = form.getAttribute();
			requestParam.put("attribute", attribute);
			
		// 修改属性	
		} else if (dealMethod.equals("modify")) {
			attribute = form.getAttribute();
			requestParam.put("attribute", attribute);
			
		// 显示属性detail	
		} else if (dealMethod.equals("detail")) {
			String formatId = form.getFormatId();
			String formatFields = form.getFormatFields();
			String formatName = form.getFormatName();
			requestParam.put("formatId", formatId);
			requestParam.put("formatFields", formatFields);
			requestParam.put("formatName", formatName);
			
		// 获得排序属性信息
		} else if (dealMethod.equals("findSortAttribute")) {
			String formatId = form.getFormatId();
			requestParam.put("formatId", formatId);
			
		// 排序
		} else if (dealMethod.equals("sort")) {
			String attributeIdStr = form.getAttributeIdStr();
			String formatId = form.getFormatId();
			requestParam.put("formatId", formatId);
			requestParam.put("attributeIdStr", attributeIdStr);
			
		// 批量删除
		} else if (dealMethod.equals("deleteAttributes")) {
			String ids = form.getIds();
			String formatId = form.getFormatId();
			requestParam.put("formatId", formatId);
			requestParam.put("ids", ids);
		}
	}

}
