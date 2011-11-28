/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.biz.articlemanager.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.baize.ccms.biz.articlemanager.domain.Article;
import com.baize.ccms.biz.articlemanager.domain.ArticleAttribute;
import com.baize.ccms.biz.articlemanager.domain.ArticleFormat;
import com.baize.ccms.biz.articlemanager.web.form.ArticleForm;
import com.baize.ccms.biz.sitemanager.domain.Site;
import com.baize.ccms.biz.usermanager.domain.User;
import com.baize.ccms.sys.SiteResource;
import com.baize.common.core.dao.Pagination;
import com.baize.common.core.service.BaseService;
import com.baize.common.core.util.FileUtil;
import com.baize.common.core.util.StringUtil;
import com.baize.common.core.web.WebClientServlet;
import com.baize.common.core.web.event.RequestEvent;
import com.baize.common.core.web.event.ResponseEvent;

/**
 * <p>标题: 文章业务类</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 文章管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-3-30 下午03:47:39
 * @history（历次修订内容、修订人、修订时间等）
 */
public class ArticleBiz extends BaseService {
	/** 注入文章服务类 */
	private ArticleService articleService;
	@Override
	public void performTask(RequestEvent requestEvent, ResponseEvent responseEvent)
			throws Exception {
		String dealMethod = requestEvent.getDealMethod();
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		String siteId = requestEvent.getSiteid();
		String userId = requestEvent.getSessionID();
		String columnId = (String) requestParam.get("columnId");
		//查看当前用户是否拥有文章审核权限
		String articleAuditedRights = articleService.findUserAuditedRights(userId, siteId, columnId);
		boolean columnAudited = false;
		if(!StringUtil.isEmpty(columnId) && !columnId.equals("0")) {
			//确认文章所在栏目是否审核
			columnAudited = articleService.sureColumnHasAudited(columnId);
		}
		// 显示列表
		if (dealMethod.equals("")) {
			log.info("显示文章列表");
			responseParam.put("isfromAll", (String)requestParam.get("isfromAll"));
			list(userId, siteId, columnId, false, requestParam, responseParam);
			
		// 添加文章
		} else if (dealMethod.equals("add")) {
			log.info("添加文章");
			Article article = (Article) requestParam.get("article");
			article.setAudited(columnAudited);
			if(columnAudited){
				article.setAuditTime(new Date());
				User auditor = new User();
				auditor.setId(userId);
				article.setAuditor(auditor);
			}
			User creator = new User();
			creator.setId(requestEvent.getSessionID());
			article.setCreator(creator);
			Site site = new Site();
			site.setId(siteId);
			article.setSite(site);
			article.setRef(false);
			Map<String, String> map = articleService.addArticle(article, this.isUpSystemAdmin);
			// 判断是否是定时发布
			String timeSettingArticleIds = map.get("timeSettingArticleIds");
			String timeSetting = map.get("timeSetting");
			articleService.proccessTimeArticle(timeSetting, timeSettingArticleIds, articleService);
			list(userId, siteId, article.getColumn().getId(), false, requestParam, responseParam);
			responseParam.put("formatId", article.getArticleFormat().getId());
			
		// 删除文章
		} else if (dealMethod.equals("delete")) {
			log.info("删除文章");
			String isFromAll = (String) requestParam.get("isFromAll");
			articleService.deleteArticleByIds((String) requestParam.get("articleIds"), siteId, userId);
			list(userId, siteId, columnId, false, requestParam, responseParam);
			responseParam.put("isFromAll", isFromAll);
		
		// 获取录入表单
		} else if (dealMethod.equals("getForm")) {
			log.info("获取文章录入表单");
			String formatId = (String) requestParam.get("formatId");
			List<ArticleAttribute> attributes = articleService.findAttributesByFormatId(formatId);
			ArticleFormat format = articleService.findFormatByFormatId(formatId);
			log.debug("==="+format.getId());
			responseParam.put("attributes", attributes);
			responseParam.put("format", format);
		
		// 查询所有格式
		} else if (dealMethod.equals("getFormats")) {
			log.info("查询所有格式");
			String formatId = (String) requestParam.get("formatId");
			String articleId = (String) requestParam.get("articleId");
			String initUrl = articleService.findArticleInitUrl(articleId);
			
			//查找格式属性枚举类别Id
			String enumerationId = articleService.findEnumerationIdByFormatId(formatId);
			//枚举信息字符串,形式为：id1,name1,#value11,value12,value13::id2,name2#value21,value22...
			String enumInfoStr = articleService.findEnumInfoInArticleService();
			if (ArticleFormat.isNeedUpdated()) {
				ArticleFormat.setArticleFormats(articleService.findAllFormats());
				ArticleFormat.setNeedUpdated(false);
			}
			if (formatId != null) {
				List<ArticleFormat> list = ArticleFormat.getArticleFormats();
				if (list.size() > 0) {
					formatId = list.get(0).getId();
				}
			}
			responseParam.put("formats", ArticleFormat.getArticleFormats());
			responseParam.put("enumerationId", enumerationId);
			responseParam.put("enumInfoStr", enumInfoStr);
			responseParam.put("initUrl", initUrl);
			
			
			String  generalSystemSetList =articleService.findGeneralSystemSetList();
			String  generalSystemSetOrgin=articleService.findGeneralSystemSetOrgin();
	    	responseParam.put("generalSystemSetList", generalSystemSetList);
	    	responseParam.put("generalSystemSetOrgin", generalSystemSetOrgin);

		// 文章明细
		} else if (dealMethod.equals("detail")) {
			log.info("查询文章明细");
			String articleId = (String) requestParam.get("articleId");
			Article article = articleService.findArticleById(articleId);
			List<ArticleAttribute> attributes = articleService.findAttributesByFormatId(article.getArticleFormat().getId());
			ArticleFormat format = articleService.findFormatByFormatId(article.getArticleFormat().getId());
			//查询引用的文章能否修改
			responseParam.put("article", article);
			responseParam.put("attributes", attributes);
			responseParam.put("format", format);
			
		//  修改文章
		} else if (dealMethod.equals("modify")) {
			log.info("修改文章");
			Article article = (Article) requestParam.get("article");
			article.setAudited(false);
			articleService.modifyArticleAndRefArticle(article, siteId, userId);
			list(userId, siteId, columnId, false, requestParam, responseParam);
			
		// 显示删除的文章分页
		} else if (dealMethod.equals("recycle")) {
			log.info("查询删除的文章");
			list(userId, siteId, columnId, true, requestParam, responseParam);
			
		// 还原删除的文章
		} else if (dealMethod.equals("revert")) {
			log.info("还原删除的文章");
			articleService.revertArticleByIds((String) requestParam.get("articleIds"), siteId, userId);
			list(userId, siteId, columnId, true, requestParam, responseParam);
			
		// 清除文章
		} else if (dealMethod.equals("clear")) {
			log.info("清除文章");
			articleService.clearArticleByIds((String) requestParam.get("articleIds"), siteId, userId);
			list(userId, siteId, columnId, true, requestParam, responseParam);
			
		// 审核文章
		} else if (dealMethod.equals("audit")) {
			Map<String, String> map = articleService.auditArticleByIds((String) requestParam.get("articleIds"), siteId, userId);
			// 判断是否是定时发布
			String timeSettingArticleIds = map.get("timeSettingArticleIds");
			String timeSetting = map.get("timeSetting");
			articleService.proccessTimeArticle(timeSetting, timeSettingArticleIds, articleService);
			list(userId, siteId, columnId, false, requestParam, responseParam);

		// 呈送文章
		} else if(dealMethod.equals("present")){
			log.info("呈送文章");
			int presentMethod = (Integer) requestParam.get("presentMethod");
			String columnIds = (String) requestParam.get("columnIds");
			String presentArticleIds = (String) requestParam.get("presentArticleIds");
			Map<String, String> map = articleService.presentArticle(columnIds, presentArticleIds, presentMethod, articleService, siteId, userId);
			// 判断是否是定时发布
			String timeSettingArticleIds = map.get("timeSettingArticleIds");
			String timeSetting = map.get("timeSetting");
			articleService.proccessTimeArticle(timeSetting, timeSettingArticleIds, articleService);
			
			log.info("呈送文章完成");
		
		// 转移文章	
		} else if(dealMethod.equals("move")) {
			log.info("转移文章");
			String nodeId = (String) requestParam.get("nodeId");
			String moveArticleIds = (String) requestParam.get("moveArticleIds");
			Map<String, String> map = articleService.moveArticle(moveArticleIds, nodeId, articleService, siteId, userId);
			// 判断是否是定时发布
			String timeSettingArticleIds = map.get("timeSettingArticleIds");
			String timeSetting = map.get("timeSetting");
			articleService.proccessTimeArticle(timeSetting, timeSettingArticleIds, articleService);
			
			log.info("转移文章完成");
			
		// 排序文章
		} else if(dealMethod.equals("sort")) {
			String sortArticleIds = (String) requestParam.get("sortArticleIds");
			articleService.sortArticle(sortArticleIds, articleService, siteId, userId);
			
		// 查找排序文章
		} else if(dealMethod.equals("findSortArticle")) {
			List list = new ArrayList();
			ArticleForm articleForm = (ArticleForm) requestParam.get("articleForm");
			list = articleService.findSortArticle(articleForm, columnId, siteId, this.isUpSiteAdmin, userId);
			responseParam.put("form", articleForm);
			responseParam.put("articlelist", list);
			
		// 审核并保存
		} else if (dealMethod.equals("auditAndSave")) {
			log.info("审核并保存文章");
			Article article = (Article) requestParam.get("article");
			String articleId = article.getId();
			Map<String, String> map = new HashMap<String, String>();
			String categoryName = "";
			article.setAudited(true);
			article.setAuditTime(new Date());
			User auditor = new User();
			auditor.setId(userId);
			article.setAuditor(auditor);
			if(articleId.equals("") || articleId == null || articleId.equals("0") || articleId.equals("null")) {
				article.setId(null);
				User creator = new User();
				creator.setId(userId);
				article.setCreator(creator);
				Site site = new Site();
				site.setId(siteId);
				article.setSite(site);
				article.setRef(false);
				map = articleService.addArticle(article, this.isUpSystemAdmin);
				categoryName = "文章管理->添加并审核";
			} else {
				map = articleService.modifyArticleAndRefArticle(article, siteId, userId);
				categoryName = "文章管理->修改并审核";
			}
			
			// 判断是否是定时发布
			String timeSettingArticleIds = map.get("timeSettingArticleIds");
			String timeSetting = map.get("timeSetting");
			articleService.proccessTimeArticle(timeSetting, timeSettingArticleIds, articleService);
			
			articleService.addArticleLogs(siteId, userId, categoryName, article.getTitle());
			list(userId, siteId, columnId, false, requestParam, responseParam);
			
		// 预览
		} else if (dealMethod.equals("preview")) {
			log.info("预览");
			String articleId = (String) requestParam.get("articleId");
			String isfromAll = (String) requestParam.get("isfromAll");
			String forwardPath = "";
			if(isfromAll.equals("yes")) {
				String colId = articleService.getColumnIdByArticleId(articleId);
				forwardPath = articleService.getPreviewPage(articleId, colId, siteId, userId);
			} else {
				forwardPath = articleService.getPreviewPage(articleId, columnId, siteId, userId);
			}
			responseParam.put("forwardPath", forwardPath);
			
		//查询当前呈送文章所在栏目的格式名称	
		} else if (dealMethod.equals("findPresentFormat")) {
			String presentArticleIds = (String) requestParam.get("presentArticleIds");
			String presentFormatName = articleService.findpresentFormatNameByColumnId(columnId);
			String presentFormatId = articleService.findpresentFormatIdByColumnId(columnId);
			//查找格式相同的栏目
			String sameFormatColumns = articleService.findSameFormatColumns(columnId, siteId);
			responseParam.put("presentFormatId", presentFormatId);
			responseParam.put("presentFormatName", presentFormatName);
			responseParam.put("presentArticleIds", presentArticleIds);
			responseParam.put("sameFormatColumns", sameFormatColumns);
			
		//查询当前转移文章所在栏目的格式名称	
		} else if (dealMethod.equals("findMoveFormat")) {
			String moveArticleIds = (String) requestParam.get("moveArticleIds");
			String presentFormatName = articleService.findpresentFormatNameByColumnId(columnId);
			String presentFormatId = articleService.findpresentFormatIdByColumnId(columnId);
			responseParam.put("presentFormatId", presentFormatId);
			responseParam.put("presentFormatName", presentFormatName);
			responseParam.put("moveArticleIds", moveArticleIds);
			
		//文章置顶	
		} else if (dealMethod.equals("setTop")) {
			String articleId = (String) requestParam.get("articleId");
			//文章是否置顶
			String isToped =  (String) requestParam.get("isToped");
			int topArticleOrders = articleService.findMaxArticleOrder();
			articleService.modifyArticleTop(articleId, topArticleOrders, isToped, siteId, userId);
			list(userId, siteId, columnId, false, requestParam, responseParam);
			
		//文章保存并继续添加	(新增加)
		} else if (dealMethod.equals("saveAndAdd")) {
			//保存文章
			Article article = (Article) requestParam.get("article");
			article.setAudited(columnAudited);
			if(columnAudited){
				article.setAuditTime(new Date());
				User auditor = new User();
				auditor.setId(userId);
				article.setAuditor(auditor);
			}
			User creator = new User();
			creator.setId(requestEvent.getSessionID());
			article.setCreator(creator);
			Site site = new Site();
			site.setId(siteId);
			article.setSite(site);
			article.setRef(false);
			
			Map<String, String> map = articleService.addArticle(article, this.isUpSystemAdmin);
			// 判断是否是定时发布
			String timeSettingArticleIds = map.get("timeSettingArticleIds");
			String timeSetting = map.get("timeSetting");
			articleService.proccessTimeArticle(timeSetting, timeSettingArticleIds, articleService);
			
			//继续添加文章    
			String formatId = (String) requestParam.get("formatId");
			if (ArticleFormat.isNeedUpdated()) {
				ArticleFormat.setArticleFormats(articleService.findAllFormats());
				ArticleFormat.setNeedUpdated(false);
			}
			if (formatId != null) {
				List<ArticleFormat> list = ArticleFormat.getArticleFormats();
				if (list.size() > 0) {
					formatId = list.get(0).getId();
				}
			}
			responseParam.put("formats", ArticleFormat.getArticleFormats());
			
			
		//文章保存并继续添加	(修改)
		} else if (dealMethod.equals("modifyAndAdd")) {
			//修改文章
			Article article = (Article) requestParam.get("article");
			article.setAudited(false);
			article.setAuditor(null);
			article.setAuditTime(null);
			articleService.modifyArticleAndRefArticle(article, siteId, userId);
			//继续添加文章    
			String formatId = (String) requestParam.get("formatId");
			if (ArticleFormat.isNeedUpdated()) {
				ArticleFormat.setArticleFormats(articleService.findAllFormats());
				ArticleFormat.setNeedUpdated(false);
			}
			if (formatId != null) {
				List<ArticleFormat> list = ArticleFormat.getArticleFormats();
				if (list.size() > 0) {
					formatId = list.get(0).getId();
				}
			}
			responseParam.put("formats", ArticleFormat.getArticleFormats());
			
		// 发布
		} else if (dealMethod.equals("publish")) {
			String articleIds = (String) requestParam.get("articleIds");
			articleService.publish(articleIds);
			list(userId, siteId, columnId, false, requestParam, responseParam);
			
		//导出
		} else if (dealMethod.equals("export")) {
			String exportArticleIds = (String) requestParam.get("exportArticleIds");
			// 新建一个临时的导出文件路径
			String path = SiteResource.getSiteDir(siteId, false) + "/article.xml";
			String message = articleService.exportArticles(exportArticleIds, path);
			HttpServletRequest req = (HttpServletRequest) requestParam.get("request");
			req.setAttribute(WebClientServlet.DEAL_CODE, WebClientServlet.DEAL_CODE_DOWNLOAD);
			req.setAttribute(WebClientServlet.DEAL_CODE_DOWNLOAD_FILEPATH, path);
			req.setAttribute(WebClientServlet.DEAL_CODE_DOWNLOAD_LOCALNAME, "文章");
			req.setAttribute(WebClientServlet.DEAL_CODE_DOWNLOAD_CHARSET, "utf-8");
			responseParam.put("message", message);
			// 删除临时的导出文件
			if(FileUtil.isExist(path)) {
				FileUtil.delete(path);
			}
			
		// 导入文章	
		}else if(dealMethod.equals("import")) {
			log.info("导入文章");
			// 获取文章路径
			String path = (String) requestParam.get("path");
			Map<String, String> map = articleService.importArticlesXml(path, columnId, siteId, userId, this.isUpSystemAdmin);
			if(map.get("infoMessage").equals("导入成功")){
				// 判断是否是定时发布
				String timeSettingArticleIds = map.get("timeSettingArticleIds");
				String timeSetting = map.get("timeSetting");
				articleService.proccessTimeArticle(timeSetting, timeSettingArticleIds, articleService);
			}
			responseParam.put("message", map.get("infoMessage"));
			
		//撤稿
		} else if (dealMethod.equals("draft")) {
			log.info("撤稿");
			String isFromAll = (String) requestParam.get("isFromAll");
			articleService.draftArticleByIds((String) requestParam.get("articleIds"), articleService);
			list(userId, siteId, columnId, false, requestParam, responseParam);
			responseParam.put("isFromAll", isFromAll);
		}
		
		responseParam.put("columnId", columnId);
		responseParam.put("articleAuditedRights", articleAuditedRights);
		if(columnAudited) {
			responseParam.put("columnAudited", "true");
		} else {
			responseParam.put("columnAudited", "false");
		}
	}
	
	/**
	 * 查询文章列表
	 * @param siteId 网站ID
	 * @param columnId 栏目ID
	 * @param deleted 是否是删除的文章
	 */
	private void list(String userId, String siteId, String columnId, boolean deleted, Map<Object,Object> requestParam, Map<Object,Object> responseParam) {
		Pagination pagination = (Pagination) requestParam.get("pagination");
		pagination = articleService.findArticlePagination(pagination, columnId, deleted, siteId, this.isUpSiteAdmin, userId);
		
		if(pagination.getData().size() == 0){
			pagination.currPage = pagination.currPage-1;
			pagination = articleService.findArticlePagination(pagination, columnId, deleted, siteId, this.isUpSiteAdmin, userId);
		}
		
		responseParam.put("pagination", pagination);
	}
	

	@Override
	public ResponseEvent validateData(RequestEvent reuqest) throws Exception {
		return null;
	}

	/**
	 * @param articleService the articleService to set
	 */
	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}

}
