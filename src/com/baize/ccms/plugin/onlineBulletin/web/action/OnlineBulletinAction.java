/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.plugin.onlineBulletin.web.action;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.baize.ccms.plugin.onlineBulletin.domain.OnlineBulletin;
import com.baize.ccms.plugin.onlineBulletin.web.form.OnlineBulletinForm;
import com.baize.common.core.dao.Pagination;
import com.baize.common.core.util.DateUtil;
import com.baize.common.core.web.GeneralAction;
import com.baize.common.core.web.event.RequestEvent;
import com.baize.common.core.web.event.ResponseEvent;

/**
 * <p>
 * 标题: 信件Action
 * </p>
 * <p>
 * 描述: 网上公告操作，封装请求对象
 * </p>
 * <p>
 * 模块: 网上公告
 * </p>
 * <p>
 * 版权: Copyright (c) 2009南京百泽网络科技有限公司
 * 
 * @author 包坤涛
 * @version 1.0
 * @since 2009-6-14 下午02:32:22
 * @history（历次修订内容、修订人、修订时间等） 
*/
 
public class OnlineBulletinAction extends GeneralAction {

	private String dealMethod = "";

	@SuppressWarnings("unchecked")
	@Override
	protected void doFormFillment(ActionForm actionForm,ResponseEvent responseEvent, String userIndr) throws Exception {
		Map<Object, Object> responseParam = responseEvent.getRespMapParam();
		OnlineBulletinForm form = (OnlineBulletinForm) actionForm;
		// 网上公告显示
		if (dealMethod.equals("list")) {
			form.setPagination((Pagination) responseParam.get("pagination"));
			this.setRedirectPage("list", userIndr);
			// 网上公告信息添加
		} else if (dealMethod.equals("detail")) {
			String message = (String) responseParam.get("message");
			form.setInfoMessage(message);
			this.setRedirectPage("detail", userIndr);
			// 网上公告信息删除
		} else if (dealMethod.equals("delete")) {
			String message = (String) responseParam.get("message");
			form.setInfoMessage(message);
			this.setRedirectPage("list", userIndr);
			// 网上公告信息修改
		}else if (dealMethod.equals("update")) {
			OnlineBulletin onlineBulletin=(OnlineBulletin) responseParam.get("onlineBulletin");
			form.setOnlineBulletin(onlineBulletin);
			String endTime = (String) responseParam.get("endTime");
			form.setEndTime(endTime);
			form.setInfoMessage("1122");
			this.setRedirectPage("update", userIndr);
		}else if (dealMethod.equals("updated")) {     
			String message = (String) responseParam.get("message");
			form.setInfoMessage(message);
			this.setRedirectPage("update", userIndr);
			
		// 发布网上公告目录	
		}else if(dealMethod.equals("publishBulletin")){
			form.setInfoMessage("发布网上公告目录成功");
			this.setRedirectPage("publishSuccess", userIndr);
		
		// 查找网上公告绑定的栏目
		}else if(dealMethod.equals("findBindColumn")){
			String columnIds = (String) responseParam.get("columnIds");
			String bulletinId = (String) responseParam.get("bulletinId");
			OnlineBulletin onlineBulletin = new OnlineBulletin();
			onlineBulletin.setColumnIds(columnIds);
			form.setOnlineBulletin(onlineBulletin);
			form.setBulletinId(bulletinId);
			this.setRedirectPage("findBindSuccess", userIndr);
		
		// 绑定栏目	
		} else if(dealMethod.equals("bindColumn")){
			form.setInfoMessage("绑定成功");
			this.setRedirectPage("bindSuccess", userIndr);
		}
	}

	@Override
	protected void performTask(ActionForm actionForm,RequestEvent requestEvent, String userIndr) throws Exception {
		Map<Object, Object> requestParam = requestEvent.getReqMapParam();
		OnlineBulletinForm form = (OnlineBulletinForm) actionForm;
		this.dealMethod = form.getDealMethod();
		HttpServletRequest request = (HttpServletRequest) requestParam.get("HttpServletRequest");
		
		if (dealMethod.equals("list")) {
			// 网上公告内容显示
			form.setQueryKey("findOnlineBulletinFormPage");
			requestParam.put("form", form);
			requestParam.put("pagination", form.getPagination());
			
		} else if (dealMethod.equals("detail")) {
			// 网上公告内容添加
			OnlineBulletin onlineBulletin = form.getOnlineBulletin();
			requestParam.put("onlineBulletin", onlineBulletin);
		} else if (dealMethod.equals("delete")) {
			// 
			String id = form.getId();
			requestParam.put("id", id);
		} else if (dealMethod.equals("update")) {
			String id = form.getId();
			requestParam.put("id", id);
		}else if (dealMethod.equals("updated")) {
			OnlineBulletin onlineBulletin = form.getOnlineBulletin();
			String endTime = form.getEndTime();
			Date date = DateUtil.toDate(endTime);
			onlineBulletin.setEndTime(date);
			requestParam.put("onlineBulletin", onlineBulletin);
			
		// 发布网上公告目录	
		}else if(dealMethod.equals("publishBulletin")){
		
		// 查找网上公告绑定的栏目
		}else if(dealMethod.equals("findBindColumn")){
			String bulletinId = form.getBulletinId();
			requestParam.put("bulletinId", bulletinId);
		
		// 绑定栏目	
		} else if(dealMethod.equals("bindColumn")){
			OnlineBulletin onlineBulletin = form.getOnlineBulletin();
			String bulletinId = form.getBulletinId();
			requestParam.put("bulletinId", bulletinId);
			requestParam.put("onlineBulletin", onlineBulletin);
		}
	}

	@Override
	// 初始化方法
	protected void init(String arg0) throws Exception {

	}
}
