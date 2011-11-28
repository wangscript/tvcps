/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.plugin.articlecomment.web.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.baize.ccms.plugin.articlecomment.web.form.ArticleCommentForm;
import com.baize.common.core.dao.Pagination;
import com.baize.common.core.web.GeneralAction;
import com.baize.common.core.web.event.RequestEvent;
import com.baize.common.core.web.event.ResponseEvent;

/**
 * <p>
 * 标题: —— 要求能简洁地表达出类的功能和职责
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
 * @since 2009-10-20 下午03:27:33
 * @history（历次修订内容、修订人、修订时间等） 
 */
 

public class ArticleCommentAction extends GeneralAction {
	private String dealMethod = "";

	@Override
	protected void doFormFillment(ActionForm actionForm,
			ResponseEvent responseEvent, String userIndr) throws Exception {
		Map<Object, Object> responseParam = responseEvent.getRespMapParam();
		@SuppressWarnings("unused")
		HttpServletRequest request = (HttpServletRequest) responseParam
				.get("HttpServletRequest");
		ArticleCommentForm form = (ArticleCommentForm) actionForm;
		form = (ArticleCommentForm) responseParam.get("commentForm");
		// 属性设置页面
		if (dealMethod.equals("AttributeSet")) {
			this.setRedirectPage("attributeSet", userIndr);
			
		// 保存属性设置
		} else if (dealMethod.equals("saveComment")) {
			this.setRedirectPage("attributeSet", userIndr);
			
		// 替换设置			
		} else if (dealMethod.equals("replaceSetList")) {
			form.setPagination((Pagination) responseParam.get("pagination"));
			this.setRedirectPage("replaceSetList", userIndr);

		// 添加过滤词
		} else if (dealMethod.equals("addReplace")) {
			this.setRedirectPage("replaceMsg", userIndr);
		
		// 显示过滤词详细
		} else if (dealMethod.equals("detailReplace")) {
			this.setRedirectPage("replaceSet", userIndr);
		
		// 保存编辑后的过滤词
		} else if (dealMethod.equals("saveReplace")) {
			this.setRedirectPage("replaceMsg", userIndr);
		
		// 删除过滤词
		} else if (dealMethod.equals("deleteReplace")) {
			this.setRedirectPage("return", userIndr);
		
		// 样式详细
		} else if (dealMethod.equals("detailSytle")) {
			this.setRedirectPage("styleset", userIndr);
		
		// 保存样式
		} else if (dealMethod.equals("editStyle")) {
			form.setInfoMessage("保存成功");
			this.setRedirectPage("styleset", userIndr);
		
		//已审核分页
		} else if(dealMethod.equals("Onaudited")){
			form.setPagination((Pagination) responseParam.get("pagination"));
			this.setRedirectPage("auditedList", userIndr);
		
		//未审核分页
		} else if(dealMethod.equals("Offaudited")){
			form.setPagination((Pagination) responseParam.get("pagination"));
			this.setRedirectPage("auditingList", userIndr);
		
		//开始审核(审核有2个页面，用于标记进行页面跳转)
		}else if(dealMethod.equals("audited")){
			if(form.getDelTag().equals("cancelAudi")){
				this.setRedirectPage("returnAudited", userIndr);
			}else{
				this.setRedirectPage("returnAuditeing", userIndr);
			}
		//回收站列表
		} else if(dealMethod.equals("Ondelete")){
			form.setPagination((Pagination) responseParam.get("pagination"));
			this.setRedirectPage("deleteList", userIndr);
		//彻底删除
		}else if(dealMethod.equals("delete")){
			this.setRedirectPage("returnRevert", userIndr);
		//置顶	
		}else if(dealMethod.equals("isToped")){
			this.setRedirectPage("returnAudited", userIndr);
		//精华
		}else if(dealMethod.equals("isEssence")){
			this.setRedirectPage("returnAudited", userIndr);
			
		//修改删除（还原或放入回收站,有3个地方用到修改，所以用一个删除标记一下，用于跳转页面）
		}else if(dealMethod.equals("isDeleted")){
			if(form.getDelTag().equals("audited")){//是从审核页面删除
				this.setRedirectPage("returnAudited", userIndr);
			}else if(form.getDelTag().equals("Revert")){//从回收站还原
				this.setRedirectPage("returnRevert", userIndr);
			}else{//未审核页面删除
				this.setRedirectPage("returnAuditeing", userIndr);
			}
			
		//显示评论详细
		}else if(dealMethod.equals("commentDetail")){
			this.setRedirectPage("articleDetail", userIndr);
		
		//发布文章评论目录
		}else if(dealMethod.equals("publishArticleComment")){
			form.setInfoMessage("发布文章评论目录成功");
			this.setRedirectPage("publishSuccess", userIndr);
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
		ArticleCommentForm form = (ArticleCommentForm) actionForm;
		this.dealMethod = form.getDealMethod();
		requestParam.put("commentForm", form);
		// 获取文章评论设置
		if (dealMethod.equals("AttributeSet")) {
			// 保存文章评论设置
		} else if (dealMethod.equals("saveComment")) {

		} else if (dealMethod.equals("replaceSetList")) {// 替换设置
			form.setQueryKey("findCommentPageBySiteId");
			requestParam.put("pagination", form.getPagination());
			// 添加过滤词
		} else if (dealMethod.equals("addReplace")) {

		} else if (dealMethod.equals("detailReplace")) {
			// 保存编辑后的过滤词
		} else if (dealMethod.equals("saveReplace")) {

			//删除替换
		} else if (dealMethod.equals("deleteReplace")) {
			// 样式详细
		} else if (dealMethod.equals("detailSytle")) {
			
			// 保存样式
		} else if (dealMethod.equals("editStyle")) {
			
			//根据审核条件查询分页
		} else if(dealMethod.equals("Onaudited")||dealMethod.equals("Offaudited")){
			form.setQueryKey("findAllArticleCommentByAuditPage");
			requestParam.put("pagination", form.getPagination());
			//开始审核
		}else if(dealMethod.equals("audited")){
		//根据删除条件分页
		} else if(dealMethod.equals("Ondelete")){
			form.setQueryKey("findAllArticleCommentByDeletePage");
			requestParam.put("pagination", form.getPagination());
			//彻底删除
		}else if(dealMethod.equals("delete")){
			
			//置顶
		}else if(dealMethod.equals("isToped")){
			
			//精华
		}else if(dealMethod.equals("isEssence")){
			
			//还原或放入回收站
		}else if(dealMethod.equals("isDeleted")){
		
			//显示评论详细
		}else if(dealMethod.equals("commentDetail")){
			
			//发布文章评论目录
		}else if(dealMethod.equals("publishArticleComment")){
			
		}
	}

}
