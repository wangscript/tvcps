/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.usermanager.web.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;

import com.baize.ccms.biz.usermanager.domain.Menu;
import com.baize.ccms.biz.usermanager.web.form.MenuForm;
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
 * @since 2009-5-18 下午03:22:36
 * @history（历次修订内容、修订人、修订时间等） 
 */
public final  class MenuAction extends GeneralAction {

	private String dealMethod = "";
	@Override
	protected void doFormFillment(ActionForm actionForm, ResponseEvent responseEvent, String userIndr) {
		MenuForm form = (MenuForm) actionForm;
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		
		if(dealMethod.equals("order")) {
			// 重新排序菜单
			String infoMessage = (String)responseParam.get("infoMessage");
			if(infoMessage != null){
				form.setInfoMessage(infoMessage);
			}else{
				form.setInfoMessage("保存成功");
			}
			
			this.setRedirectPage("listMenu", userIndr);		
			
		} else if(dealMethod.equals("findMenu")) {
			// 查找菜单
			//已选菜单
			List<Menu> menuList = (List<Menu>) responseParam.get("menulist");
			//未选
			List<Menu> json_list = (List<Menu>) responseParam.get("menulistNoChoose");
			form.setList(menuList);
			form.setJson_list(json_list);
			this.setRedirectPage("listMenu", userIndr);
		
		} else if(dealMethod.equals("getTree")) {
			//根据权限获取左侧的树
			form.setJson_list((List)responseParam.get("treelist"));
			this.setRedirectPage("tree", userIndr);		
		}
	}

	@Override
	protected void performTask(ActionForm actionForm, RequestEvent requestEvent, String userIndr) throws Exception {
		MenuForm form = (MenuForm) actionForm;
		this.dealMethod = form.getDealMethod();
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		if(dealMethod.equals("order")) {
			// 重新排序菜单
			String menuIds = form.getOrdersMenu();
			requestParam.put("menuIds", menuIds);
		} else if(dealMethod.equals("getTree")) {
			requestParam.put("treeId", form.getTreeId());
		}
	}

	@Override
	protected void init(String arg0) throws Exception {
	}
}
