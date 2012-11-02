/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.articlemanager.web.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.j2ee.cms.biz.articlemanager.domain.Article;
import com.j2ee.cms.biz.articlemanager.domain.ArticleAttach;
import com.j2ee.cms.biz.articlemanager.domain.ArticleAttribute;
import com.j2ee.cms.biz.articlemanager.domain.ArticleFormat;
import com.j2ee.cms.biz.articlemanager.web.form.ArticleForm;
import com.j2ee.cms.biz.columnmanager.domain.Column;
import com.j2ee.cms.biz.sitemanager.domain.Site;
import com.j2ee.cms.biz.usermanager.domain.User;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.util.StringUtil;
import com.j2ee.cms.common.core.web.GeneralAction;
import com.j2ee.cms.common.core.web.event.RequestEvent;
import com.j2ee.cms.common.core.web.event.ResponseEvent;
import com.j2ee.cms.sys.Tools;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 文章管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-3-19 上午10:14:49
 * @history（历次修订内容、修订人、修订时间等）
 */
public class ArticleAction extends GeneralAction {
	
	private String dealMethod = "";
	@Override
	protected void doFormFillment(ActionForm actionForm,
			ResponseEvent responseEvent, String userIndr) throws Exception {
		ArticleForm form = (ArticleForm) actionForm;
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		String columnId = (String) responseParam.get("columnId");
		form.setColumnId(columnId);
		String articleAuditedRights = (String) responseParam.get("articleAuditedRights");
		form.setArticleAuditedRights(articleAuditedRights);
		String columnAudited = (String) responseParam.get("columnAudited");
		form.setColumnAudited(columnAudited);
//		String formatId = (String) responseParam.get("formatId");
//		form.setFormatId(formatId);
		
		form.setMaxPicCount(Integer.parseInt(Tools.getConfPropertyValueByParam("maxPicCount")));
		form.setMaxMediaCount(Integer.parseInt(Tools.getConfPropertyValueByParam("maxMediaCount")));
		form.setMaxAttachCount(Integer.parseInt(Tools.getConfPropertyValueByParam("maxAttachCount")));
		
		// 显示列表
		if (dealMethod.equals("")) {
			form.setPagination((Pagination) responseParam.get("pagination"));
			// 转向所有文章页
			if(columnId == null) {
				this.setRedirectPage("allArticles", userIndr);
			}
		// 显示文章录入表单
		} else if (dealMethod.equals("getForm")) {
			List<ArticleAttribute> attributes = (List<ArticleAttribute>) responseParam.get("attributes");
	 
			form.setAttributeList(attributes);
			setRedirectPage("form", userIndr);
			
		// 查询所有格式
		} else if (dealMethod.equals("getFormats")) {
/*			String initUrl = (String) responseParam.get("initUrl");
			List<ArticleFormat> formats = (List<ArticleFormat>) responseParam.get("formats");
			if(formats != null && formats.size() > 0 ){
				String deaultFormat = formats.get(0).getId();
				form.setIsDefault(deaultFormat);
			}
			form.setFormats(formats);
			String enumerationId = (String) responseParam.get("enumerationId");
			String enumInfoStr = (String) responseParam.get("enumInfoStr");*/
			// 作者来源
		    form.setGeneralSystemSetList((String)responseParam.get("generalSystemSetList"));
		    form.setGeneralSystemSetOrgin((String)responseParam.get("generalSystemSetOrgin"));
		    form.setFormatName((String)responseParam.get("formatName"));
		    form.setFormatId((String)responseParam.get("formatId"));
		    Article article = (Article)responseParam.get("article");
		    if(article != null){
		    	form.setArticle(article);
		    }
		/*    form.setEnumerationId(enumerationId);
			form.setEnumInfoStr(enumInfoStr);
			form.setInitUrl(initUrl);*/
			this.setRedirectPage("detail", userIndr);
			
		// 添加文章	
		} else if (dealMethod.equals("add")) {
			form.setInfoMessage("添加成功");
	 
			form.setPagination((Pagination) responseParam.get("pagination"));
			
		// 删除文章
		} else if (dealMethod.equals("delete")) {
			form.setInfoMessage("删除成功");
			form.setPagination((Pagination) responseParam.get("pagination"));
			String isFromAll = (String) responseParam.get("isFromAll");
			if(isFromAll.equals("yes")) {
				this.setRedirectPage("allArticles", userIndr);
			} 
			
		// 修改文章	
		} else if (dealMethod.equals("modify")) {
			form.setInfoMessage("修改成功");
			form.setPagination((Pagination) responseParam.get("pagination"));
			
		// 审核文章
		} else if (dealMethod.equals("audit")) {
			log.info("审核文章");
			form.setInfoMessage("审核成功");
			form.setPagination((Pagination) responseParam.get("pagination"));
			
		// 审核并保存
		} else if (dealMethod.equals("auditAndSave")) {
			log.info("审核并保存文章");
			form.setInfoMessage("审核并保存成功");
			form.setPagination((Pagination) responseParam.get("pagination"));
			//this.setRedirectPage("addSucess", userIndr);
			
		// 文章明细	
		} else if (dealMethod.equals("detail")) {
			Article article = (Article) responseParam.get("article");
			List<ArticleAttribute> attributeList = (List<ArticleAttribute>) responseParam.get("attributes");
	 
			form.setArticle(article);
			form.setCreatorId(article.getCreator().getId());
			form.setSiteId(article.getSite().getId());
			form.setAttributeList(attributeList);
 
			form.setColumnId(article.getColumn().getId());
			if (article.getAuditor() != null) {
				form.setAuditorId(article.getAuditor().getId());
			}
			form.setOrders(article.getOrders());
			if(article.getReferedArticle() != null) {
				form.setRefId(article.getReferedArticle().getId());
			}
			if(article.isRef()) {
				form.setIsref(1);
			} else {
				form.setIsref(0);
			}
		    form.setFormatName((String)responseParam.get("formatName"));
		    form.setFormatId((String)responseParam.get("formatId"));
		    
		    form.setPicList((List<ArticleAttach>)responseParam.get("picList"));
		    form.setMediaList((List<ArticleAttach>)responseParam.get("mediaList"));
		    form.setAttachList((List<ArticleAttach>)responseParam.get("attachList"));
			this.setRedirectPage("detail", userIndr);
			
		// 显示回收站里的文章	
		} else if (dealMethod.equals("recycle")) {
			form.setPagination((Pagination) responseParam.get("pagination"));
			this.setRedirectPage("recycle", userIndr);
			
		// 还原删除的文章	
		} else if (dealMethod.equals("revert")) {
			form.setPagination((Pagination) responseParam.get("pagination"));
			this.setRedirectPage("recycle", userIndr);
			
		// 清除文章 
		} else if (dealMethod.equals("clear")) {
			form.setPagination((Pagination) responseParam.get("pagination"));
			this.setRedirectPage("recycle", userIndr);
			
		// 呈送文章
		} else if(dealMethod.equals("present")) {
			form.setInfoMessage("呈送文章完成");
			this.setRedirectPage("operateSuccess", userIndr);
			
		// 转移文章	
		} else if(dealMethod.equals("move")) {
			form.setInfoMessage("转移文章完成");
			this.setRedirectPage("operateSuccess", userIndr);
			
		// 排序文章
		} else if(dealMethod.equals("sort")) {
			form.setInfoMessage("排序完成");
			this.setRedirectPage("sort", userIndr);
			
		// 查找排序文章
		} else if(dealMethod.equals("findSortArticle")) {
			form = (ArticleForm) responseParam.get("form");
			form.setColumnId(columnId);
			List<Article> list = (List<Article>) responseParam.get("articlelist");
			form.setJson_list(list);
			this.setRedirectPage("sort", userIndr);
			
		// 预览
		} else if (dealMethod.equals("preview")) {
			String forwardPath = (String) responseParam.get("forwardPath");
			this.setForwardPath(forwardPath);
			
		//查询当前呈送文章所在栏目的格式名称	
		} else if (dealMethod.equals("findPresentFormat")) {
			String sameFormatColumns = (String) responseParam.get("sameFormatColumns");
			String presentArticleIds = (String) responseParam.get("presentArticleIds");
			String presentFormatName = (String) responseParam.get("presentFormatName");
			String presentFormatId = (String) responseParam.get("presentFormatId");
			form.setSameFormatColumns(sameFormatColumns);
			form.setPresentFormatId(presentFormatId);
			form.setPresentFormatName(presentFormatName);
			form.setPresentArticleIds(presentArticleIds);
			this.setRedirectPage("findPresentFormat", userIndr);
			
		//查询当前转移文章所在栏目的格式名称	
		} else if (dealMethod.equals("findMoveFormat")) {
			String moveArticleIds = (String) responseParam.get("moveArticleIds");
			String presentFormatName = (String) responseParam.get("presentFormatName");
			String presentFormatId = (String) responseParam.get("presentFormatId");
			form.setPresentFormatId(presentFormatId);
			form.setPresentFormatName(presentFormatName);
			form.setPresentArticleIds(moveArticleIds);
			this.setRedirectPage("findMoveFormat", userIndr);
			
		//文章置顶	
		} else if (dealMethod.equals("setTop")) {
			form.setPagination((Pagination) responseParam.get("pagination"));
			// 转向所有文章页
			if(columnId == null) {
				this.setRedirectPage("allArticles", userIndr);
			}
			
		//文章保存并继续添加	(新增加)
		} else if (dealMethod.equals("saveAndAdd")) {
			form.setInfoMessage("保存并继续添加文章成功");
			List<ArticleFormat> formats = (List<ArticleFormat>) responseParam.get("formats");
			form.setFormats(formats);
			form.setArticle(null);
			this.setRedirectPage("detail", userIndr);
			
		//文章保存并继续添加	(修改)
		} else if (dealMethod.equals("modifyAndAdd")) {
			form.setInfoMessage("保存并继续添加文章成功");
			List<ArticleFormat> formats = (List<ArticleFormat>) responseParam.get("formats");
			form.setFormats(formats);
			form.setArticle(null);
			this.setRedirectPage("detail", userIndr);
			
		//发布	
		} else if (dealMethod.equals("publish")) {
			form.setPagination((Pagination) responseParam.get("pagination"));
			// 转向所有文章页
			if(columnId == null) {
				this.setRedirectPage("allArticles", userIndr);
			}
			
		//导出
		} else if (dealMethod.equals("export")) {
			String message = (String) responseParam.get("message");
			this.setRedirectPage("webClient", userIndr);
			
		//导入文章
		} else 	if(dealMethod.equals("import")) {
			String message = (String) responseParam.get("message");
			form.setInfoMessage(message);
			this.setRedirectPage("import", userIndr);
			
		// 撤稿
		} else if (dealMethod.equals("draft")) {
			form.setInfoMessage("撤稿成功");
			String isFromAll = (String) responseParam.get("isFromAll");
		//	if(isFromAll.equals("yes")) {
				this.setRedirectPage("allArticles", userIndr);
		//	} 
		}
	}

	@Override
	protected void init(String userIndr) throws Exception {
		setRedirectPage("success", userIndr);
	}
	
	@Override
	protected void performTask(ActionForm actionForm,
		RequestEvent requestEvent, String userIndr) throws Exception {
		HttpServletRequest request = (HttpServletRequest) requestEvent.getReqMapParam().get("HttpServletRequest");
		ArticleForm articleForm = (ArticleForm) actionForm;
		this.dealMethod = articleForm.getDealMethod();
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		Article article = null;
		String userId = requestEvent.getSessionID();
		String columnId = articleForm.getColumnId();
		if(columnId == null || columnId.equals("0") || columnId.equals("") || columnId.equals("null")) {
			columnId = null;
		}
//		String formatId = articleForm.getFormatId();
//		formatId = "0".equals(formatId) ? null : formatId;
		
		// 网站管理员和以上
		if(this.isUpSiteAdmin) {
			if (columnId == null) {	
				// 查询所有
				articleForm.setQueryKey("findAllArticlePage");
			} else {	
				// 查询某个栏目下
				articleForm.setQueryKey("findArticlePageByColumnId");
			}
		} else {
			/*if(columnId == null) {
				// 按照用户名和网站以及未被删除的文章
				articleForm.setQueryKey("findArticleByCreatorIdAndSiteIdPage");
			} else {
				// 按照用户名和网站以及栏目、未被删除
				articleForm.setQueryKey("findArticleByCreatorIdAndSiteIdAndColumnIdPage");
			}*/
			articleForm.setQueryKey("findArticleByColumnIdsAndSiteIdAndDeletedPage");
		}
		/*
		if (columnId == null) {	
			// 查询所有
			articleForm.setQueryKey("findAllArticlePage");
		} else {	
			// 查询某个栏目下
			articleForm.setQueryKey("findArticlePageByColumnId");
		}*/
		
		// 显示列表
		if (dealMethod.equals("")) {
			requestParam.put("isfromAll", articleForm.getIsfromAll());
		// 获取录入表单
		} else if (dealMethod.equals("getForm")) {
	 
			
		
		// 显示所有格式
		} else if (dealMethod.equals("getFormats")) {
			String articleId = articleForm.getArticleDetailId();
			requestParam.put("articleId", articleId);
			requestParam.put("formatId", articleForm.getFormatId());
			
		// 添加文章
		} else if (dealMethod.equals("add")) {
			article = articleForm.getArticle();
			Column column = new Column();
			column.setId(articleForm.getColumnId());
			article.setColumn(column);	 
			requestParam.put("article", article);
			
			this.processArticleAttach(request, requestParam);
		// 删除文章
		} else if (dealMethod.equals("delete")) {
			String isFromAll = articleForm.getIsfromAll();
			requestParam.put("isFromAll", isFromAll);
			requestParam.put("articleIds", articleForm.getIds());
			
		// 修改文章
		} else if (dealMethod.equals("modify")) {
			article = articleForm.getArticle();
			Column column = new Column();
			column.setId(articleForm.getColumnId());
			 
			Site site = new Site();
			site.setId(articleForm.getSiteId());
			User creator = new User();
			creator.setId(articleForm.getCreatorId());
			
			if(!StringUtil.isEmpty(articleForm.getAuditorId())) {
				User auditor = new User();
				auditor.setId(articleForm.getAuditorId());
				article.setAuditor(auditor);
			}
			article.setCreator(creator);
			article.setColumn(column);
		 
			article.setSite(site);
			
			int orders = articleForm.getOrders();
			article.setOrders(orders);
			
			String refId = articleForm.getRefId();
			if(refId == null || refId.equals("0") || refId.equals("null") || refId.equals("")) {
				refId = null;
			} else {
				Article refArticle = new Article();
				refArticle.setId(refId);
				article.setReferedArticle(refArticle);
			}
			
			if(articleForm.getIsref() == 0) {
				article.setRef(false);
			} else {
				article.setRef(true);
			}
			this.processArticleAttach(request, requestParam);
			requestParam.put("article", article);
			
		// 文章明细
		} else if (dealMethod.equals("detail")) {
			String articleId = articleForm.getArticle().getId();
			requestParam.put("articleId", articleId);
			requestParam.put("columnId", columnId);
			
		// 显示回收站里的文章
		} else if (dealMethod.equals("recycle")) {
			
		// 还原删除的文章
		} else if (dealMethod.equals("revert")) {
			requestParam.put("articleIds", articleForm.getIds());
			
		// 清除文章
		} else if (dealMethod.equals("clear")) {
			requestParam.put("articleIds", articleForm.getIds());
			
		// 审核文章
		} else if (dealMethod.equals("audit")) {
			requestParam.put("articleIds", articleForm.getIds());
		
		// 呈送文章
		} else if(dealMethod.equals("present")) {
			int presentMethod = articleForm.getPresentMethod();
			// 要呈送到的栏目id
			String columnIds = articleForm.getIds();
			// 要呈送的文章id
			String presentArticleIds = articleForm.getPresentArticleIds();
			requestParam.put("columnIds", columnIds);
			requestParam.put("presentArticleIds", presentArticleIds);
			requestParam.put("presentMethod", presentMethod);
			
		// 转移文章	
		} else if(dealMethod.equals("move")) {
			String moveArticleIds = articleForm.getMoveArticleIds();
			String nodeId = articleForm.getNodeId();
			if(nodeId == null || nodeId.equals("0") || nodeId.equals("") || nodeId.equals("null")) {
				nodeId = null;
			}
			requestParam.put("nodeId", nodeId);
			requestParam.put("moveArticleIds", moveArticleIds);
			
		// 排序文章
		} else if(dealMethod.equals("sort")) {
			String sortArticleIds = articleForm.getSortArticleIds();
			requestParam.put("sortArticleIds", sortArticleIds);
			
		// 查找排序文章
		} else if(dealMethod.equals("findSortArticle")) {
			requestParam.put("articleForm", articleForm);
			
		// 审核并保存
		} else if (dealMethod.equals("auditAndSave")) {
			article = articleForm.getArticle();
			Column column = new Column();
			column.setId(articleForm.getColumnId());	 
			Site site = new Site();
			site.setId(articleForm.getSiteId());
			User creator = new User();
			creator.setId(articleForm.getCreatorId());
			
			User auditor = new User();
			auditor.setId(userId);
			article.setAuditor(auditor);
			article.setAudited(true);
			article.setAuditTime(new Date());
			article.setCreator(creator);
			article.setColumn(column);
	 
			article.setSite(site);
			
			int orders = articleForm.getOrders();
			article.setOrders(orders);
			
			String refId = articleForm.getRefId();
			if(refId == null || refId.equals("0") || refId.equals("null") || refId.equals("")) {
				refId = null;
			} else {
				Article refArticle = new Article();
				refArticle.setId(refId);
				article.setReferedArticle(refArticle);
			}
			
			if(articleForm.getIsref() == 0) {
				article.setRef(false);
			} else {
				article.setRef(true);
			}
			
//			log.debug("columnId============"+article.getColumn().getId());
//			log.debug("sietid====="+article.getSite().getId());
//			log.debug("creatorId==========="+article.getCreator().getId());
			this.processArticleAttach(request, requestParam);
			
			
			requestParam.put("article", article);
			
		} else if (dealMethod.equals("preview")) {
			String isfromAll = articleForm.getIsfromAll();
			requestParam.put("isfromAll", isfromAll);
			requestParam.put("articleId", articleForm.getArticleId());
//			columnId = articleForm.getColumnId();
//			if (columnId == null || "null".equals(columnId) || "0".equals(columnId)) {
//				columnId = null;
//			}
			
		//查询当前呈送文章所在栏目的格式名称	
		} else if (dealMethod.equals("findPresentFormat")) {
			String presentArticleIds = articleForm.getPresentArticleIds();
			requestParam.put("presentArticleIds", presentArticleIds);
			
		//查询当前转移文章所在栏目的格式名称	
		} else if (dealMethod.equals("findMoveFormat")) {
			String moveArticleIds = articleForm.getMoveArticleIds();
			requestParam.put("moveArticleIds", moveArticleIds);
			
		//文章置顶	
		} else if (dealMethod.equals("setTop")) {
			String articleId = articleForm.getArticleId();
			String isToped = articleForm.getIsToped();
			requestParam.put("articleId", articleId);
			requestParam.put("isToped", isToped);
			
		//文章保存并继续添加    (新增加)
		} else if (dealMethod.equals("saveAndAdd")) {
			//保存文章
			article = articleForm.getArticle();
			Column column = new Column();
			column.setId(articleForm.getColumnId());
			article.setColumn(column);
	  
			requestParam.put("article", article);
			this.processArticleAttach(request, requestParam);
			
		//文章保存并继续添加	(修改)
		} else if (dealMethod.equals("modifyAndAdd")) {
			//修改文章	
			article = articleForm.getArticle();
			Column column = new Column();
			column.setId(articleForm.getColumnId());
		 
			Site site = new Site();
			site.setId(articleForm.getSiteId());
			User creator = new User();
			creator.setId(articleForm.getCreatorId());
			
			if(!StringUtil.isEmpty(articleForm.getAuditorId())) {
				User auditor = new User();
				auditor.setId(articleForm.getAuditorId());
				article.setAuditor(auditor);
			}
			
			article.setCreator(creator);
			article.setColumn(column);
			article.setSite(site);
			
			int orders = articleForm.getOrders();
			article.setOrders(orders);
			
			String refId = articleForm.getRefId();
			if(refId == null || refId.equals("0") || refId.equals("null") || refId.equals("")) {
				refId = null;
			} else {
				Article refArticle = new Article();
				refArticle.setId(refId);
				article.setReferedArticle(refArticle);
			}
			
			if(articleForm.getIsref() == 0) {
				article.setRef(false);
			} else {
				article.setRef(true);
			}
			requestParam.put("article", article);
			//继续添加
	 
			
		//发布
		} else if (dealMethod.equals("publish")) {
			requestParam.put("articleIds", articleForm.getIds());
			
		//导出
		} else if (dealMethod.equals("export")) {
			String exportArticleIds = articleForm.getExportArticleIds();
			if(exportArticleIds == null || exportArticleIds.equals("") || exportArticleIds.equals("null")) {
				exportArticleIds = null;
			}
			requestParam.put("exportArticleIds", exportArticleIds);
			requestParam.put("request", request);
			
		// 导入文章
		} else 	if(dealMethod.equals("import")) {
			String path = articleForm.getPath();
	         requestParam.put("path", path);
			          
	      // 撤稿
		} else if (dealMethod.equals("draft")) {
			String isFromAll = articleForm.getIsfromAll();
			requestParam.put("isFromAll", isFromAll);
			requestParam.put("articleIds", articleForm.getIds());
		}
		
		requestParam.put("columnId", columnId);
	}
	
	
	private void processArticleAttach(HttpServletRequest request, Map<Object,Object> requestParam){
	    List<String> picAttachList = new ArrayList<String>();
        List<String> mediaAttachList = new ArrayList<String>();
        List<String> attachAttachList = new ArrayList<String>();
        Integer maxPicCount = Integer.parseInt(Tools.getConfPropertyValueByParam("maxPicCount"));
        Integer maxMediaCount = Integer.parseInt(Tools.getConfPropertyValueByParam("maxMediaCount"));
        Integer maxAttachCount = Integer.parseInt(Tools.getConfPropertyValueByParam("maxAttachCount"));
        for(int i = 0; i < maxPicCount; i++){
            String path = request.getParameter("pic"+i);
            if(path != null && !path.equals("")){
                picAttachList.add(path);
            }
        }
        for(int i = 0; i < maxMediaCount; i++){
            String path = request.getParameter("media"+i);
            if(path != null && !path.equals("")){
                mediaAttachList.add(path);
            }
        }
        for(int i = 0; i < maxAttachCount; i++){
            String path = request.getParameter("attach"+i);
            if(path != null && !path.equals("")){
                attachAttachList.add(path);
            }
        }
        requestParam.put("picAttachList", picAttachList);
        requestParam.put("mediaAttachList", mediaAttachList);
        requestParam.put("attachAttachList", attachAttachList);
        
	}
}
