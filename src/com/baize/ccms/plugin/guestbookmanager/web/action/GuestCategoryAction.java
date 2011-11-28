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
 * 
 * <p>
 * 标题: —— 留言类别管理ACTION
 * </p>
 * <p>
 * 描述: —— 简要描述类的职责、实现方式、使用注意事项等
 * </p>
 * <p>
 * 模块: CCMS
 * </p>
 * <p>
 * 版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * </p>
 * <p>
 * 网址：http://www.baizeweb.com
 * 
 * @author 曹名科
 * @version 1.0
 * @since 2009-11-3 下午02:33:14
 * @history（历次修订内容、修订人、修订时间等）
 */
 

public class GuestCategoryAction extends GeneralAction {
	private String dealMethod = "";

	@Override
	protected void doFormFillment(ActionForm actionForm,
			ResponseEvent responseEvent, String userIndr) throws Exception {
		Map<Object, Object> responseParam = responseEvent.getRespMapParam();
		HttpServletRequest request = (HttpServletRequest) responseParam
				.get("HttpServletRequest");
		GuestBookForm form = (GuestBookForm) actionForm;
		form = (GuestBookForm) responseParam.get("guestBookForm");
		Pagination p = (Pagination) responseParam.get("pagination");
		if(null!=p)
		form.setPagination(p);
		if (dealMethod.equals("")) {
			this.setRedirectPage("contentlist", userIndr);
			// 未回复
		} else if (dealMethod.equals("replaying")) {
			this.setRedirectPage("contentlist", userIndr);
			// 已回复
		} else if (dealMethod.equals("replayed")) {
			this.setRedirectPage("contentlist", userIndr);
			// 未审核
		} else if (dealMethod.equals("auditing")) {
			this.setRedirectPage("contentlist", userIndr);
			// 已审核
		} else if (dealMethod.equals("audited")) {
			this.setRedirectPage("contentlist", userIndr);
			// 不处理
		} else if (dealMethod.equals("nonoice")) {
			this.setRedirectPage("contentlist", userIndr);
			// 删除留言
		} else if (dealMethod.equals("deleteContent")) {
			this.setRedirectPage("return", userIndr);
			// 类别分页
		} else if (dealMethod.equals("categoryList")) {
			this.setRedirectPage("categorylist", userIndr);
			// 类别详细
		} else if (dealMethod.equals("categoryDetail")) {
			this.setRedirectPage("detailcategory", userIndr);
			// 保存类别
		} else if (dealMethod.equals("categorySave")) {
			this.setRedirectPage("wordmsg", userIndr);
			// 删除类别
		} else if (dealMethod.equals("categoryDelete")) {
		
			this.setRedirectPage("categorylist", userIndr);
			//显示添加类别页面
		} else if (dealMethod.equals("showCategory")) {
			this.setRedirectPage("addCategory", userIndr);//跳转到添加类别页面
			// 添加类别
		} else if (dealMethod.equals("addCategory")) {
			this.setRedirectPage("wordmsg", userIndr);
			// 留言详细
		} else if (dealMethod.equals("contentDetail")) {
			this.setRedirectPage("contentDetail", userIndr);
			// 审核留言
		} else if (dealMethod.equals("auditContent")) {
			this.setRedirectPage("wordmsg", userIndr);
			// 审核并回复
		} else if (dealMethod.equals("auditAndReplay")) {
			this.setRedirectPage("wordmsg", userIndr);
			// 保存
		} else if (dealMethod.equals("saveContent")) {
			this.setRedirectPage("wordmsg", userIndr);
			// 留言置未审核
		} else if (dealMethod.equals("noaudit")) {
			this.setRedirectPage("wordmsg", userIndr);
			// 不处理
		} else if (dealMethod.equals("handle")) {
			this.setRedirectPage("wordmsg", userIndr);
			// 获取用户列表
		} else if (dealMethod.equals("showAllUser")) {
			this.setRedirectPage("userlist", userIndr);
			// 开始将留言分发给指定用户
		} else if (dealMethod.equals("dispenseUser")) {
			this.setRedirectPage("wordmsg", userIndr);
			
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
		// 全部
		if (dealMethod.equals("")) {
		//	System.out.println("action当前登录的人员权限："+this.isUpSiteAdmin);
			if (this.isUpSiteAdmin) {
				form.setQueryKey("findAllContent");
			} else {
				form.setQueryKey("findAllContentByUserId");
			}
			// 未回复
		} else if (dealMethod.equals("replaying")) {
			if (this.isUpSiteAdmin) {
				form.setQueryKey("findContentByreplyStatus");
			} else {
				form.setQueryKey("findContentByreplyStatusByUserId");
			}

			// 已回复
		} else if (dealMethod.equals("replayed")) {
			if (this.isUpSiteAdmin) {
				form.setQueryKey("findContentByreplyStatus");
			} else {
				form.setQueryKey("findContentByreplyStatusByUserId");
			}
			// 未审核
		} else if (dealMethod.equals("auditing")) {
			if (this.isUpSiteAdmin) {
				form.setQueryKey("findContentByAudit");
			} else {
				form.setQueryKey("findContentByAuditByUserId");
			}
			// 已审核
		} else if (dealMethod.equals("audited")) {
			if (this.isUpSiteAdmin) {
				form.setQueryKey("findContentByAudit");
			} else {
				form.setQueryKey("findContentByAuditByUserId");
			}
			// 不处理
		} else if (dealMethod.equals("nonoice")) {
			if (this.isUpSiteAdmin) {
				form.setQueryKey("findContentByAudit");
			} else {
				form.setQueryKey("findContentByAuditByUserId");
			}
			// 删除留言
		} else if (dealMethod.equals("deleteContent")) {
			// 类别分页
		} else if (dealMethod.equals("categoryList")) {
			form.setQueryKey("findCategoryPage");
			// 类别详细
		} else if (dealMethod.equals("categoryDetail")) {
			// 保存类别
		} else if (dealMethod.equals("categorySave")) {
			// 删除类别
		} else if (dealMethod.equals("categoryDelete")) {
			form.setQueryKey("findCategoryPage");
			//显示添加类别页面
		} else if (dealMethod.equals("showCategory")) {
			// 添加类别
		} else if (dealMethod.equals("addCategory")) {
			// 留言详细
		} else if (dealMethod.equals("contentDetail")) {

			// 审核留言
		} else if (dealMethod.equals("auditContent")) {
			// 审核并回复
		} else if (dealMethod.equals("auditAndReplay")) {
			// 保存
		} else if (dealMethod.equals("saveContent")) {
			// 留言置未审核
		} else if (dealMethod.equals("noaudit")) {
			// 不处理
		} else if (dealMethod.equals("handle")) {
			// 获取用户列表
		} else if (dealMethod.equals("showAllUser")) {
			form.setQueryKey("findAllUser");
			// 开始将留言分发给指定用户
		} else if (dealMethod.equals("dispenseUser")) {
			
		}
		requestParam.put("pagination", form.getPagination());
	}

}
