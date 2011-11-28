package com.baize.ccms.biz.configmanager.web.action;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;

import com.baize.ccms.biz.configmanager.web.form.OperationForm;
import com.baize.ccms.biz.usermanager.domain.Operation;
import com.baize.common.core.dao.Pagination;
import com.baize.common.core.web.GeneralAction;
import com.baize.common.core.web.event.RequestEvent;
import com.baize.common.core.web.event.ResponseEvent;

/**
 * <p>标题: 系统参数Action</p>
 * <p>描述: 管理系统参数的不同操作，封装请求对象</p>
 * <p>模块: 系统参数管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 魏仲芹
 * @version 1.0
 * @since 2009-4-8 上午09:24:08
 * @history（历次修订内容、修订人、修订时间等）
 */
public class OperationAction extends GeneralAction {
	
	private String dealMethod = "";

	@Override
	protected void doFormFillment(ActionForm actionForm, ResponseEvent responseEvent, String userIndr) {
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		
		OperationForm form=(OperationForm)actionForm;
		// 根据dealMethod判断调用哪个方法
		if (dealMethod.equals("")) {
			log.info("显示操作列表");
			form.setPagination((Pagination) responseParam.get("pagination"));
		} 
		if (dealMethod.equals("insert")) {			
			//	this.generalXml(list);			
				log.info("增加操作成功!");
				form.setInfoMessage("增加操作成功");
				//设置处理成功页面转向
				this.setRedirectPage("detailsuccess", userIndr);
		} 
		if (dealMethod.equals("delete")) {
			//	this.generalXml(list);
				log.info("删除操作成功!");
				form.setInfoMessage("删除操作成功");
				this.setRedirectPage("return", userIndr);
			}
			if (dealMethod.equals("detail")) {		
				Operation operation = (Operation)responseParam.get("operation");
				form.setOperation(operation);
				this.setRedirectPage("detailsuccess", userIndr);
				log.info("显示操作详细信息");
			} 
			if (dealMethod.equals("modify")) {					
				log.info("修改操作详细信息");
				form.setInfoMessage("修改操作成功");
				this.setRedirectPage("detailsuccess", userIndr);
			}
	}

	@Override
	protected void init(String userIndr) throws Exception {
		// TODO Auto-generated method stub
		this.setRedirectPage("success", userIndr);
	}

	@Override
	protected void performTask(ActionForm actionForm, RequestEvent requestEvent, String userIndr) throws Exception {
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		
		OperationForm form=(OperationForm)actionForm;
		this.dealMethod = form.getDealMethod();
		//String dealMethod=form.getDealMethod();
	//	ResponseEvent responseEvent=null;
		//Map<Object,Object> reqParam = new HashMap<Object,Object>();
		//Map map = new HashMap();	
		Operation operation=new Operation();
		log.info("OperationAction----dealMethod======"+dealMethod);
		if(dealMethod.equals("")){
			form.setQueryKey("findOperationPage");
		}
		if (dealMethod.equals("insert")) {
			//organization.setLinkAddress("userManager.do");
				operation=form.getOperation();
				requestParam.put("operation", operation);				
		}
		if (dealMethod.equals("detail")) {
			String id = form.getId();		
			requestParam.put("id", id);
		}
		if (dealMethod.equals("modify")) {	
			operation=form.getOperation();
			operation.setId(form.getId());
			requestParam.put("operation", operation);	
		}
		if (dealMethod.equals("delete")) {			
			requestParam.put("ids", form.getIds());			
						
		}
		
	//	return responseEvent;
	}
}
