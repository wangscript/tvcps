/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.unitmanager.web.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.j2ee.cms.biz.unitmanager.web.form.MagazineCategoryForm;
import com.j2ee.cms.biz.unitmanager.web.form.TitleLinkForm;
import com.j2ee.cms.sys.GlobalConfig;
import com.j2ee.cms.common.core.util.StringUtil;
import com.j2ee.cms.common.core.web.GeneralAction;
import com.j2ee.cms.common.core.web.event.RequestEvent;
import com.j2ee.cms.common.core.web.event.ResponseEvent;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CPS</p>
 * <p>版权: Copyright (c) 2009  
 * @author 郑荣华
 * @version 1.0
 * @since 2009-9-8 上午10:57:03
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class MagazineCategoryAction extends GeneralAction {

	private String dealMethod = "";
	
	@Override
	protected void doFormFillment(ActionForm actionForm, ResponseEvent responseEvent, String userIndr) throws Exception {
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		MagazineCategoryForm form = (MagazineCategoryForm) actionForm;
		
		// 查找配置文件
		if(dealMethod.equals("findConfig")) {
			form = (MagazineCategoryForm) responseParam.get("form");
			
		// 保存配置
		} else if(dealMethod.equals("saveConfig")) {
			form.setInfoMessage("保存设置成功!");
			this.setRedirectPage("savesuccess", userIndr);
		
		// 查找信息分类		
		} else if(dealMethod.equals("findInfoCategory")) {
			Object alldataobj[] = (Object[])responseParam.get("allData");
			Object choosedataobj[] = (Object[])responseParam.get("chooseData");
			form.setAllData(alldataobj);
			form.setChooseData(choosedataobj);
			this.setRedirectPage("chooseInfoCategory", userIndr);
			
		// 查找指定栏目的属性		
		} else if(dealMethod.equals("findFieldCode")) {
			form = (MagazineCategoryForm) responseParam.get("magazineCategoryForm");
			this.setRedirectPage("findAttributeSuccess", userIndr);
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
		
		MagazineCategoryForm form = (MagazineCategoryForm) actionForm;
		this.dealMethod = form.getDealMethod();
		// 查找配置文件
		if(dealMethod.equals("findConfig")) {
			requestParam.put("form", form);
			
		// 保存配置
		} else if(dealMethod.equals("saveConfig")) {
			requestParam.put("form", form);
			
		// 查找信息分类	
		} else if(dealMethod.equals("findInfoCategory")) {
			String columnId = form.getColumnId();
			String unitId = form.getUnit_unitId();
			requestParam.put("columnId", columnId);
			requestParam.put("unitId", unitId);
			
		// 查找指定栏目的属性	
		} else if(dealMethod.equals("findFieldCode")) {
			requestParam.put("form", form);
		}
	}

}
