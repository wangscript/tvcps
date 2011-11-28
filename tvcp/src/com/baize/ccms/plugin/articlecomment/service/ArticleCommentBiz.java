/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.plugin.articlecomment.service;

import java.util.List;
import java.util.Map;

import com.baize.ccms.plugin.articlecomment.domain.ArticleCommentsReplace;
import com.baize.ccms.plugin.articlecomment.web.form.ArticleCommentForm;
import com.baize.ccms.plugin.rss.web.form.RssForm;
import com.baize.ccms.sys.GlobalConfig;
import com.baize.common.core.dao.Pagination;
import com.baize.common.core.service.BaseService;
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
 * @since 2009-10-20 下午03:57:43
 * @history（历次修订内容、修订人、修订时间等） 
 */
 
public class ArticleCommentBiz extends BaseService {
	/**注入文章评论service*/
	private ArticleCommentService articleCommentService;
	@Override
	public void performTask(RequestEvent requestEvent,
			ResponseEvent responseEvent) throws Exception {
		String dealMethod = requestEvent.getDealMethod();
		Map<Object, Object> requestParam = requestEvent.getReqMapParam();
		Map<Object, Object> responseParam = responseEvent.getRespMapParam();
		// 网站id
		String siteId = requestEvent.getSiteid();
		String sessionId = requestEvent.getSessionID();
		ArticleCommentForm form = (ArticleCommentForm) requestParam.get("commentForm");
		//获取文章评论设置
		if(dealMethod.equals("AttributeSet")){
			articleCommentService.getCommentForForm(form,siteId);
			//保存文章评论设置
		}else if(dealMethod.equals("saveComment")){
			articleCommentService.setCommentForForm(form,sessionId,siteId);
			//替换设置
		}else if(dealMethod.equals("replaceSetList")){
			Pagination p=(Pagination)requestParam.get("pagination");
			Pagination p2=articleCommentService.getPagination(p, siteId);
			if(p2.getData().size() == 0){
				p2.currPage = p2.currPage - 1;
				p2 =articleCommentService.getPagination(p, siteId);
			}
			responseParam.put("pagination", p2);
			//添加过滤词
		}else if(dealMethod.equals("addReplace")){
			String mess=articleCommentService.addReplaceWord(form,siteId,sessionId);
			form.setInfoMessage(mess);
			//显示详细过滤词
		}else if(dealMethod.equals("detailReplace")){
			ArticleCommentsReplace a = articleCommentService.findByReplaceId(form.getIds());
			form.setReplace(a);
			//保存编辑后的过滤词
		}else if(dealMethod.equals("saveReplace")){
			String mess=articleCommentService.modifyReplace(form);
			form.setInfoMessage(mess);
			//删除过滤词
		}else if(dealMethod.equals("deleteReplace")){
			articleCommentService.deleteReplace(form.getIds());
			//样式详细
		}else if(dealMethod.equals("detailSytle")){
			String content = articleCommentService.getStyle(siteId);
			form.setStyleContent(content);
			//保存样式
		}else if(dealMethod.equals("editStyle")){
			articleCommentService.setStyle(form.getStyleContent(),siteId);
			//已审核
		} else if(dealMethod.equals("Onaudited")){
			Pagination p=(Pagination)requestParam.get("pagination");
			Pagination p2=articleCommentService.getCommentOnauditededPagination(p, "1");
			responseParam.put("pagination", p2);
			//未审核
		} else if(dealMethod.equals("Offaudited")){
			Pagination p=(Pagination)requestParam.get("pagination");
			Pagination p2=articleCommentService.getCommentOnauditededPagination(p, "0");
			responseParam.put("pagination", p2);
			//开始审核
		}else if(dealMethod.equals("audited")){
			String ids = form.getIds();
			String flag = form.getObjContent();
			articleCommentService.modifyArticleCommentDeleteByAuditid(ids, flag, siteId, sessionId);
			//回收站列表
		} else if(dealMethod.equals("Ondelete")){
			Pagination p=(Pagination)requestParam.get("pagination");
			Pagination p2=articleCommentService.getCommentOndeletePagination(p);
			responseParam.put("pagination", p2);
			//彻底删除
		}else if(dealMethod.equals("delete")){
			String ids= form.getIds();
			articleCommentService.deleteCommentById(ids);
			//置顶
		}else if(dealMethod.equals("isToped")){
			String ids= form.getIds();
			String isTop = form.getObjContent();
			articleCommentService.modifyArticleCommentTop(ids, isTop, siteId, sessionId);
			//精华
		}else if(dealMethod.equals("isEssence")){
			articleCommentService.modifyArticleCommentDeleteByEssence(form.getIds(), form.getObjContent(), siteId, sessionId);
			//还原或放入回收站
		}else if(dealMethod.equals("isDeleted")){
			String id = form.getIds();
			String isDel=form.getObjContent();
			articleCommentService.modifyArticleCommentDelete(id, isDel, siteId, sessionId);
			//显示评论详细
		}else if(dealMethod.equals("commentDetail")){
			String id = form.getIds();
			form.setComment(articleCommentService.getArticleCommentById(id));
		//发布文章评论目录
		}else if(dealMethod.equals("publishArticleComment")){
			articleCommentService.publishArticleCommentDir(siteId);
		}	
		responseParam.put("commentForm", form);

	}

	@Override
	public ResponseEvent validateData(RequestEvent arg0) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param articleCommentService the articleCommentService to set
	 */
	public void setArticleCommentService(ArticleCommentService articleCommentService) {
		this.articleCommentService = articleCommentService;
	}

}
