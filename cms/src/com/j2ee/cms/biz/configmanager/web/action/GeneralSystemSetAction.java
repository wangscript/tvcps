/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.configmanager.web.action;

import java.util.Date;
import java.util.Map;

import org.apache.struts.action.ActionForm;

import com.j2ee.cms.biz.articlemanager.domain.ArticleFormat;
import com.j2ee.cms.biz.columnmanager.domain.Column;
import com.j2ee.cms.biz.configmanager.domain.GeneralSystemSet;
import com.j2ee.cms.biz.configmanager.web.form.GeneralSystemSetForm;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.util.DateUtil;
import com.j2ee.cms.common.core.web.GeneralAction;
import com.j2ee.cms.common.core.web.event.RequestEvent;
import com.j2ee.cms.common.core.web.event.ResponseEvent;

/**
 * <p>
 * 标题: 系统安装程序Action
 * </p>
 * <p>
 * 描述: 数据的操作及生成xml文档
 * </p>
 * <p>
 * 模块: 系统安装程序
 * </p>
 * <p>
 * 版权: Copyright (c) 2009 
 * 
 * @author 杨信
 * @version 1.0
 * @since 2009-7-15 下午04:12:33
 * @history（历次修订内容、修订人、修订时间等） 
*/
 

public final class GeneralSystemSetAction extends GeneralAction {

	private String dealMethod = "";

	@Override
	protected void doFormFillment(ActionForm actionForm,
			ResponseEvent responseEvent, String userIndr) {
		GeneralSystemSetForm form = (GeneralSystemSetForm) actionForm;
		GeneralSystemSet generalSystemSet = new GeneralSystemSet();
		Map<Object, Object> responseParam = responseEvent.getRespMapParam();
		String ww = (String) responseParam.get("categoryId");

		if (dealMethod.equals("")) {
			// 显示作者列表
			log.debug("显示作者列表");
			form.setPagination((Pagination) responseParam.get("pagination"));
			form.setCategoryId((String) responseParam.get("categoryId"));
			String categoryId = (String) responseParam.get("categoryId");
			this.setRedirectPage("success", userIndr);

		} else if (dealMethod.equals("add")) {
			// 添加作者内容
			log.debug("添加作者内容");
			String message = (String) responseParam.get("message");
			form.setCategoryId((String) responseParam.get("categoryId"));
			form.setInfoMessage(message);
			this.setRedirectPage("detail", userIndr);

		} else if (dealMethod.equals("detail")
				|| dealMethod.equals("linkdetail")
				|| dealMethod.equals("originupdated")) {
			// 判断跳转方向
			// 得到categoryId节点值
			String categoryId = (String) responseParam.get("categoryId");
			form.setCategoryId(categoryId);
			generalSystemSet = (GeneralSystemSet) responseParam
					.get("generalSystemSet");
			form.setGeneralSystemSet(generalSystemSet);
			// 如果是detail方法就跳到作者加载页面
			if (dealMethod.equals("detail")) {
				this.setRedirectPage("detail", userIndr);
			}
			// 如果是linkdetail方法就跳到链接加载页面
			if (dealMethod.equals("linkdetail")) {
				this.setRedirectPage("linkdetail", userIndr);
			}
			// /如果是origindetail方法就跳到列表加载页面
			// 列表更新页面
			if (dealMethod.equals("originupdated")) {

				this.setRedirectPage("originupdated", userIndr);
			}
		} else if (dealMethod.equals("update")) {
			// 修改方法
			String categoryId = (String) responseParam.get("categoryId");
			form.setCategoryId(categoryId);
			String message = (String) responseParam.get("message");
			form.setInfoMessage(message);
			this.setRedirectPage("detail", userIndr);
		} else if (dealMethod.equals("delete")
				|| dealMethod.equals("deleteOrign")
				|| dealMethod.equals("deleteLink")) {
			// 三钟类型删除方法
			String categoryId = (String) responseParam.get("categoryId");
			form.setCategoryId(categoryId);
			int currPage = (Integer) responseParam.get("currPage");
			form.setCurrPage(currPage);
			// 对作者进行删除
			if (dealMethod.equals("delete")) {
				form.setDealMethod("");
				this.setRedirectPage("deleteSuccess", userIndr);
			}
			// 对列表进行删除
			if (dealMethod.equals("deleteOrign")) {
				// form.setCategoryId(categoryId);
				form.setDealMethod("origin");
				this.setRedirectPage("deleteSuccess", userIndr);
			}
			// 对链接进行删除
			if (dealMethod.equals("deleteLink")) {
				form.setDealMethod("link");
				this.setRedirectPage("deleteSuccess", userIndr);
			}
			// 来源设置列表显示
		} else if (dealMethod.equals("origin")) {
			log.debug("来源设置列表");
			form.setPagination((Pagination) responseParam.get("pagination"));
			form.setCategoryId((String) responseParam.get("categoryId"));
			this.setRedirectPage("originList", userIndr);

			// 图片设置
		} else if (dealMethod.equals("picture")) {
			log.debug("图片设置	");
			form.setCategoryId((String) responseParam.get("categoryId"));
			String number = (String) responseParam.get("number");
			if (number.equals("1")) {
				log.debug("图片设置	启动成功");
				form.setInfoMessage("1");
			} else {
				log.debug("图片设置禁用成功");
				form.setInfoMessage("2");
			}
			// 图片跳转
			this.setRedirectPage("picture", userIndr);
			// 热点连接
		} else if (dealMethod.equals("link")) {
			log.debug("热点连接");
			form.setPagination((Pagination) responseParam.get("pagination"));
			form.setCategoryId((String) responseParam.get("categoryId"));
			this.setRedirectPage("link", userIndr);
			// 热点连接添加
		} else if (dealMethod.equals("addLink")) {
			log.debug("热点连接添加");
			String cate = (String) responseParam.get("categoryId");
			form.setCategoryId((String) responseParam.get("categoryId"));
			String message = (String) responseParam.get("message");
			form.setInfoMessage(message);
			this.setRedirectPage("addLink", userIndr);
			// 热点连接修改
		} else if (dealMethod.equals("linkUpdate")) {
			String categoryId = (String) responseParam.get("categoryId");
			form.setCategoryId(categoryId);
			String message = (String) responseParam.get("message");
			form.setInfoMessage(message);
			this.setRedirectPage("linkdetail", userIndr);
			// 来源内容
		} else if (dealMethod.equals("origindetail")) {
			log.debug("来源内容");

			String message = (String) responseParam.get("message");
			form.setCategoryId((String) responseParam.get("categoryId"));
			String categoryId = (String) responseParam.get("categoryId");
			form.setInfoMessage(message);
			this.setRedirectPage("origindetail", userIndr);
			// 来源内容修改
		} else if (dealMethod.equals("originupdate")) {
			log.debug("来源内容修改");
			String categoryId = (String) responseParam.get("categoryId");
			form.setCategoryId(categoryId);
			String message = (String) responseParam.get("message");
			form.setInfoMessage(message);
			this.setRedirectPage("origindetail", userIndr);

		}
	}

	protected void performTask(ActionForm actionForm,
			RequestEvent requestEvent, String userIndr) throws Exception {
		GeneralSystemSetForm form = (GeneralSystemSetForm) actionForm;
		this.dealMethod = form.getDealMethod();
		Map<Object, Object> requestParam = requestEvent.getReqMapParam();
		GeneralSystemSet generalSystemSet = new GeneralSystemSet();
		generalSystemSet = form.getGeneralSystemSet();
		requestParam.put("pagination", form.getPagination());
		if (dealMethod.equals("")) {
			// 显示作者列表
			int currPage = form.getCurrPage();
			int rowsPerPage = form.getRowsPerPage();
			requestParam.put("currPage",currPage);
			requestParam.put("rowsPerPage", rowsPerPage);
			String categoryId = form.getCategoryId();
			requestParam.put("categoryId", categoryId);
			// 查询作者内容
			form.setQueryKey("findGeneralSystemSetPage");	
			
		} else if (dealMethod.equals("add")) {
			// 添加作者内容
			String separator = form.getSeparator();
			generalSystemSet = form.getGeneralSystemSet();
			//传过来的树节点id
			String overDefault = form.getOverDefault();
			String categoryId = form.getCategoryId();
			requestParam.put("overDefault", overDefault);
			requestParam.put("categoryId", categoryId);
			requestParam.put("separator", separator);
			requestParam.put("generalSystemSet", generalSystemSet);
		} else if (dealMethod.equals("detail")
				|| dealMethod.equals("linkdetail")
				|| dealMethod.equals("originupdated")) {
      		//显示作者内容详细信息 
			String categoryId = form.getCategoryId();
			String authorId = form.getAuthorId();
			requestParam.put("authorId", authorId);
			requestParam.put("categoryId", categoryId);
			requestParam.put("generalSystemSet", generalSystemSet);
			// 修改作者内容
		} else if (dealMethod.equals("update")) {
			generalSystemSet = form.getGeneralSystemSet();
			String categoryId = form.getCategoryId();
			// 状态
			String overDefault = form.getOverDefault();
			requestParam.put("categoryId", categoryId);
			requestParam.put("overDefault", overDefault);
			requestParam.put("generalSystemSet", generalSystemSet);
			/*
			 * 删除
			 */
		} else if (dealMethod.equals("delete")
				|| dealMethod.equals("deleteOrign")
				|| dealMethod.equals("deleteLink")) {
			int currPage = form.getCurrPage();
			int rowsPerPage = form.getRowsPerPage();
			requestParam.put("currPage", currPage);
			requestParam.put("rowsPerPage", rowsPerPage);
			String categoryId = form.getCategoryId();
			String authorId = form.getGeneralSystemSet().getId();
			requestParam.put("categoryId", categoryId);
			requestParam.put("authorId", authorId);
		} else if (dealMethod.equals("origin")) {
			int currPage = form.getCurrPage();
			int rowsPerPage = form.getRowsPerPage();
			requestParam.put("currPage",currPage);
			requestParam.put("rowsPerPage", rowsPerPage);
			
			String categoryId = (String) form.getCategoryId();
			requestParam.put("categoryId", categoryId);
			form.setQueryKey("findGeneralSystemSetPage");
		} else if (dealMethod.equals("picture")) {
			log.debug("图片设置	");
			//传过来的树节点id
			String categoryId = form.getCategoryId();
			generalSystemSet = form.getGeneralSystemSet();
			requestParam.put("categoryId", categoryId);
			requestParam.put("generalSystemSet", generalSystemSet);
			// 热点连接
		} else if (dealMethod.equals("link")) {
			int currPage = form.getCurrPage();
			int rowsPerPage = form.getRowsPerPage();
			requestParam.put("currPage",currPage);
			requestParam.put("rowsPerPage", rowsPerPage);
			
			log.debug("热点连接");
			String categoryId = (String) form.getCategoryId();
			requestParam.put("categoryId", categoryId);
			form.setQueryKey("findLinkGeneralSystemSetPage");
			
		} else if (dealMethod.equals("addLink")) {
			//显示作者内容详细信息
			String categoryId = form.getCategoryId();
			String authorId = form.getAuthorId();
			String key = "1";
			requestParam.put("key", key);
			requestParam.put("authorId", authorId);
			requestParam.put("categoryId", categoryId);
			generalSystemSet = form.getGeneralSystemSet();
			requestParam.put("generalSystemSet", generalSystemSet);
		} else if (dealMethod.equals("linkUpdate")) {
			String key2 = "1";
			generalSystemSet = form.getGeneralSystemSet();
			requestParam.put("key2", key2);
			String categoryId = form.getCategoryId();
			String authorId = form.getAuthorId();
			requestParam.put("authorId", authorId);
			requestParam.put("categoryId", categoryId);
			requestParam.put("generalSystemSet", generalSystemSet);
			//删除列表内容
		} else if (dealMethod.equals("origindetail")) {
           //添加作者内容
			String separator = form.getSeparator();
			generalSystemSet = form.getGeneralSystemSet();
			//传过来的树节点id
			String overDefault = form.getOverDefault();
			String categoryId = form.getCategoryId();
			requestParam.put("overDefault", overDefault);
			requestParam.put("categoryId", categoryId);
			requestParam.put("separator", separator);
			requestParam.put("generalSystemSet", generalSystemSet);

		} else if (dealMethod.equals("originupdate")) {
			//列表修改
			generalSystemSet = form.getGeneralSystemSet();
			String categoryId = form.getCategoryId();
			// 状态
			String overDefault = form.getOverDefault();
			requestParam.put("categoryId", categoryId);
			requestParam.put("overDefault", overDefault);
			requestParam.put("generalSystemSet", generalSystemSet);
		}
	}

	@Override
	protected void init(String userIndr) throws Exception {

	}
}
