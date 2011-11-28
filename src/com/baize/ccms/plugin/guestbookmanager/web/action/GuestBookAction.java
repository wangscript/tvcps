/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.plugin.guestbookmanager.web.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;
import com.baize.ccms.plugin.guestbookmanager.web.form.GuestBookForm;
import com.baize.common.core.dao.Pagination;
import com.baize.common.core.web.GeneralAction;
import com.baize.common.core.web.event.RequestEvent;
import com.baize.common.core.web.event.ResponseEvent;

/**
 * <p>标题: —— 留言管理</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CCMS</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司</p>
 * <p>网址：http://www.baizeweb.com
 * @author 曹名科
 * @version 1.0
 * @since 2009-11-2 下午02:57:18
 * @history（历次修订内容、修订人、修订时间等）
 */
 

public class GuestBookAction extends GeneralAction {
	private String dealMethod = "";

	@Override
	protected void doFormFillment(ActionForm actionForm,
			ResponseEvent responseEvent, String userIndr) throws Exception {
		Map<Object, Object> responseParam = responseEvent.getRespMapParam();
		HttpServletRequest request = (HttpServletRequest) responseParam
				.get("HttpServletRequest");
		GuestBookForm form = (GuestBookForm) actionForm;
		form = (GuestBookForm) responseParam.get("guestBookForm");
		// 敏感词分页
		if (dealMethod.equals("wordList")) {
			Pagination pagination = (Pagination) responseParam
					.get("pagination");
			form.setPagination(pagination);
			this.setRedirectPage("wordlist", userIndr);
		
		// 添加敏感词
		} else if (dealMethod.equals("addWord")) {
			this.setRedirectPage("wordmsg", userIndr);
		
		//编辑敏感词
		}else if(dealMethod.equals("editWord")){
			this.setRedirectPage("wordmsg", userIndr);

		//显示添加敏感词页面
		}else if(dealMethod.equals("showWord")){
			this.setRedirectPage("wordetail", userIndr);

		//删除敏感词
		}else if(dealMethod.equals("deleteWord")){
			this.setRedirectPage("return", userIndr);
		
		//获取敏感词详细
		}else if(dealMethod.equals("wordetail")){
			this.setRedirectPage("wordetail", userIndr);
		
		//属性设置详细
		}else if(dealMethod.equals("propertyDetail")){
			this.setRedirectPage("propertyset", userIndr);
		
		//保存修改的设置
		}else if(dealMethod.equals("savepropertySet")){
			this.setRedirectPage("propertyset", userIndr);
		
		//获取样式
		}else if(dealMethod.equals("getStyle")){
			this.setRedirectPage("style", userIndr);
		
		//保存样式
		}else if(dealMethod.equals("saveStyle")){
			this.setRedirectPage("style", userIndr);
		
		//已分发权限的用户列表
		}else if(dealMethod.equals("autorityList")){
			Pagination p = (Pagination)responseParam.get("pagination");
			form.setPagination(p);
			this.setRedirectPage("autoritylist", userIndr);
		
		//显示要被分发的用户列表
		}else if(dealMethod.equals("autorityDetail")){
			Pagination p = (Pagination)responseParam.get("pagination");
			form.setPagination(p);
			this.setRedirectPage("userlist", userIndr);

		//获取所选择的用户
		}else if(dealMethod.equals("getSelectUser")){
			this.setRedirectPage("wordmsg", userIndr);

		//删除分发用户
		}else if(dealMethod.equals("deleteAutority")){
			this.setRedirectPage("returnAutory", userIndr);
			
		// 发布留言本目录
		} else if(dealMethod.equals("publishGuestBook")){
			form.setInfoMessage("发布留言本目录成功");
			this.setRedirectPage("propertyset", userIndr);
		}
	}

	@Override
	protected void init(String arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void performTask(ActionForm actionForm,
			RequestEvent requestEvent, String userIndr) throws Exception {
		Map<Object, Object> requestParam = requestEvent.getReqMapParam();
		HttpServletRequest request = (HttpServletRequest) requestParam
				.get("HttpServletRequest");
		GuestBookForm form = (GuestBookForm) actionForm;
		this.dealMethod = form.getDealMethod();
		requestParam.put("guestBookForm", form);
		//敏感词分页
		if (dealMethod.equals("wordList")) {
			form.setQueryKey("findAllSensitiveWordPage");

		// 添加敏感词	
		}else if (dealMethod.equals("addWord")) {
		
		//编辑敏感词
		}else if(dealMethod.equals("editWord")){
		
		//删除敏感词
		}else if(dealMethod.equals("deleteWord")){
		
		//获取敏感词详细
		}else if(dealMethod.equals("wordetail")){
		
		//属性设置详细
		}else if(dealMethod.equals("propertyDetail")){
			
		//保存修改的设置
		}else if(dealMethod.equals("savepropertySet")){
		
		//获取样式
		}else if(dealMethod.equals("getStyle")){
		
		//保存样式
		}else if(dealMethod.equals("saveStyle")){
		
		//已分发权限的用户列表
		}else if(dealMethod.equals("autorityList")){
			form.setQueryKey("findAllAutoryUserByDelete");
		
		//显示要被分发的用户列表
		}else if(dealMethod.equals("autorityDetail")){
			form.setQueryKey("findAllUser");
		
		//获取选择的用户
		}else if(dealMethod.equals("getSelectUser")){
		
		//删除分发用户
		}else if(dealMethod.equals("deleteAutority")){
		
		//显示添加敏感词页面
		}else if(dealMethod.equals("showWord")){
			
		// 发布留言本目录
		} else if(dealMethod.equals("publishGuestBook")){
			
		}
		requestParam.put("pagination", form.getPagination());
	}
}
