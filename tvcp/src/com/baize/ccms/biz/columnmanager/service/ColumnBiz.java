  /**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.columnmanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.j2ee.cms.biz.articlemanager.domain.ArticleFormat;
import com.j2ee.cms.biz.columnmanager.domain.Column;
import com.j2ee.cms.biz.columnmanager.web.form.ColumnForm;
import com.j2ee.cms.sys.SiteResource;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.service.BaseService;
import com.j2ee.cms.common.core.util.FileUtil;
import com.j2ee.cms.common.core.web.WebClientServlet;
import com.j2ee.cms.common.core.web.event.RequestEvent;
import com.j2ee.cms.common.core.web.event.ResponseEvent;

/**
 * <p>标题: 栏目业务处理类</p>
 * <p>描述: 栏目增删改、excel导入导出、排序等功能</p>
 * <p>模块: 栏目管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 郑荣华
 * @version 1.0
 * @since 2009-4-2 下午03:20:54
 * @history（历次修订内容、修订人、修订时间等）
 */
public class ColumnBiz extends BaseService {

	/**  注入栏目业务层  **/
	private ColumnService columnService;	

	@SuppressWarnings("unchecked")
	@Override
	public void performTask(RequestEvent requestEvent, ResponseEvent responseEvent)
			throws Exception {
		String dealMethod = requestEvent.getDealMethod();
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
				
		// 获取当前登陆用户的id
		String sessionID = requestEvent.getSessionID();
		// 获取频道类型
		String channelType = requestEvent.getChannelType();
		// 获取网站Id
		String siteId = requestEvent.getSiteid();
		//当前网站是否为根网站
		String isRootSite = columnService.findSiteById(siteId);
		responseParam.put("isRootSite", isRootSite);
		// 获取栏目节点
		String nodeid = (String) requestParam.get("nodeid");
		// 获取节点名
		String nodeName = "";
		nodeName = (String) requestParam.get("localNodeName");
		
		// 分页显示栏目
		if(dealMethod.equals("")) {
			log.info("显示栏目列表");
			Pagination pagination = (Pagination) requestParam.get("pagination");
			pagination = columnService.findColumnPage(nodeid, pagination, siteId, sessionID, isUpSystemAdmin, isUpSiteAdmin);
			if(pagination.getData().size() == 0){
				pagination.currPage = pagination.currPage - 1;
				pagination = columnService.findColumnPage(nodeid, pagination, siteId, sessionID, isUpSystemAdmin, isUpSiteAdmin);
			}
			responseParam.put("pagination", pagination);
			log.info("显示栏目列表成功");
	
		// 添加栏目
		} else  if(dealMethod.equals("add")) {
			log.info("添加栏目");
			String separator = (String) requestParam.get("separator");
			Column column = (Column) requestParam.get("column");
			// 处理添加栏目
			String infoMessage = columnService.addColumn(separator, column, siteId, sessionID, nodeid, this.isUpSystemAdmin, this.isSiteAdmin);
			responseParam.put("infoMessage", infoMessage);
			log.info(infoMessage);
		
		// 删除栏目	
		} else  if(dealMethod.equals("delete")) {
			log.info("删除栏目");
			String ids = (String) requestParam.get("ids");	
			String infoMessage = "";
			infoMessage = columnService.deleteColumnids(ids, siteId, nodeid, sessionID, this.isUpSystemAdmin, this.isSiteAdmin);
			responseParam.put("infoMessage", infoMessage);
			log.info(infoMessage);
			
		// 修改栏目
		} else  if(dealMethod.equals("modify")) {
			log.info("修改栏目");
			Column column = (Column) requestParam.get("column");
			String parentId = (String) requestParam.get("parentId");
			String modifyparentid = (String) requestParam.get("modifyParentId");
			columnService.modifyColumn(column, parentId, modifyparentid, this.isUpSystemAdmin, this.isSiteAdmin, siteId, sessionID);
			log.info("修改栏目成功");
			
		// 栏目详细
		} else  if(dealMethod.equals("detail")) {
			log.info("栏目详细信息");
			String columnid = (String) requestParam.get("columnId");
			Column column = columnService.findColumnById(columnid);
			//同步栏目所属网站id
			String refSiteId = columnService.findRefSiteIdByColumnId(columnid);
			//当前栏目所在网站id
			String presentColumnSiteId = column.getSite().getId();
			//网站同步类型
			String siteRefType = "";
			if(refSiteId.equals(presentColumnSiteId) || refSiteId.equals("")) {
				siteRefType = "1";
				//将同步网站设为当前栏目所在网站
				refSiteId = presentColumnSiteId;
			} else {
				siteRefType = "2";
			}
			//同步栏目名称
			String columnNames = columnService.findRefColumnNames(columnid);
			Column parent = new Column();
			parent.setId(nodeid);
			String parentName = columnService.findNodeName(nodeid);
			column.setParent(parent);
			List<ArticleFormat> articleFormats = columnService.findAllArticleFormats();
			// 判断栏目下面是否有文章，如果有则不能修改格式
			int hasArticle = columnService.findArticleByColumnId(columnid);
			//查找本站外的所有网站
			List list = columnService.findRefSiteList();
			responseParam.put("list", list);
			responseParam.put("hasArticle", hasArticle);
			responseParam.put("columnNames", columnNames);
			responseParam.put("parentName", parentName);
			responseParam.put("column", column);
			responseParam.put("articleFormats", articleFormats);
			responseParam.put("refSiteId", refSiteId);
			responseParam.put("siteRefType", siteRefType);
			log.info("查询栏目详细信息成功");
			 
		// 导入栏目	
		} else 	if(dealMethod.equals("import")) {
			log.info("导入栏目");
			// 获取excel的名称
			String file = (String) requestParam.get("file");
			String infoMessage = "";
			infoMessage = columnService.importExcel(file, siteId, sessionID, nodeid, isUpSystemAdmin, isSiteAdmin, columnService);
			responseParam.put("infoMessage", infoMessage);
			log.info(infoMessage);
		
		// 导出栏目	
		} else 	if(dealMethod.equals("export")) {
			log.info("导出栏目");
			String exportColumnIds = (String) requestParam.get("exportColumnIds");
			// 获取需要导出的excel名称
			String path = SiteResource.getSiteDir(siteId, false) + "/column.xls";
			if(FileUtil.isExist(path)) {
				FileUtil.delete(path);
			}
			//执行导出方法，出入excel文件名称和网站id
			columnService.exportColumnsToExcel(path, siteId, nodeid, exportColumnIds, sessionID, isUpSystemAdmin, isSiteAdmin, columnService);
			log.info("栏目导出成功");
			
		// 粘贴栏目	
		} else  if(dealMethod.equals("paste")) {
			log.info("粘贴栏目");
			int isCopy = (Integer) requestParam.get("isCopy");
			String ids = (String) requestParam.get("ids");		
			String checkid = (String) requestParam.get("checkid");
			// 获取needcild（是否需要子结点）
			int needchild = (Integer) requestParam.get("needchild");
			//调用粘贴方法 
			String infoMessage = columnService.pasteColumn(checkid, ids, siteId, sessionID, needchild, isCopy, isUpSystemAdmin, isSiteAdmin, columnService);
			responseParam.put("infoMessage", infoMessage);
			log.info(infoMessage);
			
		// 查找栏目的子节点	
		} else 	if(dealMethod.equals("findSortColumn")) {
			log.info("查找栏目的子节点");
			ColumnForm form = (ColumnForm) requestParam.get("form");
			List<Column> childColumnList = columnService.findChildColumn(form, nodeid, siteId, sessionID, isUpSystemAdmin, isSiteAdmin, columnService);
			responseParam.put("childColumnList", childColumnList);
			responseParam.put("form", form);
			log.info("查找栏目的子节点完成");
			
		// 排序栏目	
		} else 	if(dealMethod.equals("sort")) {
			log.info("排序栏目");
			String ordersColumn = (String) requestParam.get("ordersColumn");
			columnService.orderColumn(ordersColumn, columnService, siteId, sessionID);
			log.info("排序栏目成功");
			
		// 选择父网站的栏目	
		} else 	if(dealMethod.equals("findParentSiteColumn")) {
			log.info("选择栏目");
			Pagination pagination = (Pagination) requestParam.get("pagination");
			pagination = columnService.findParentSiteColumnPage(pagination, siteId);
			String parentSiteId = columnService.findParentSiteId(siteId);
			responseParam.put("parentSiteId", parentSiteId);
			responseParam.put("pagination", pagination);
			log.info("选择栏目成功");
	 	
	 	// 获得树
		} else  if(dealMethod.equals("getTree")) {
			log.info("获得树");
			String columnId = (String) requestParam.get("treeid");
			List columnList = columnService.findColumnTree(siteId, columnId, sessionID, isUpSystemAdmin, isUpSiteAdmin);
			responseParam.put("columnList", columnList);
			log.info("获得树成功");
			
		// 获得文章管理栏目树	
		} else  if(dealMethod.equals("getArticleColumnTree")) {
			log.info("获得文章管理栏目树");
			String columnId = (String) requestParam.get("treeid");
			List columnList = columnService.findArticleColumnTree(siteId, columnId, sessionID, isUpSystemAdmin, isUpSiteAdmin);
			responseParam.put("columnList", columnList);
			
		// 获取模版设置栏目树
		}else if(dealMethod.equals("getTemplateSetTree")){	
			log.info("获取模版设置栏目树");
			String columnId = (String) requestParam.get("treeid");
			List columnList = columnService.findTemplateSetColumnTree(siteId, columnId, sessionID, isUpSystemAdmin, isUpSiteAdmin);
			responseParam.put("columnList", columnList);
			log.info("获取模版设置栏目树完成");
			
		// 检查要选择的上级栏目是否是自己的子栏目或者自己本身	
		} else  if(dealMethod.equals("checkColumn")) {
			log.info("检查栏目");
			String columnId = (String) requestParam.get("columnId");
			Column column = columnService.findColumnById(columnId);
			String columnIds = nodeid + "," + columnService.findChildColumnId(column, columnId);
			responseParam.put("columnIds", columnIds);
			
		// 加载父网站的树	
		} else  if(dealMethod.equals("parentSiteTree")) {
			String columnId = (String) requestParam.get("treeid");
			String siteid = (String) requestParam.get("siteid");
			List columnList = columnService.findParentSiteColumnTreeBySiteIdAndColumnId(siteid, columnId);
			responseParam.put("columnList", columnList);
		
		// 保存导出栏目	
	    } else  if(dealMethod.equals("savaExportColumn")) {
	    	String path = "";
	    	path = SiteResource.getSiteDir(siteId, false) + "/column.xls";
	    	HttpServletRequest req = (HttpServletRequest) requestParam.get("request");
			req.setAttribute(WebClientServlet.DEAL_CODE, WebClientServlet.DEAL_CODE_DOWNLOAD);
			req.setAttribute(WebClientServlet.DEAL_CODE_DOWNLOAD_FILEPATH, path);
			req.setAttribute(WebClientServlet.DEAL_CODE_DOWNLOAD_LOCALNAME, "栏目");
			req.setAttribute(WebClientServlet.DEAL_CODE_DOWNLOAD_CHARSET, "utf-8");
			
		// 查询所有文章格式
		} else if (dealMethod.equals("findAllFormats")) {
			List<ArticleFormat> articleFormats = columnService.findAllArticleFormats();
			//查找本站外的所有网站
			List list = columnService.findRefSiteList(siteId);
			responseParam.put("list", list);
			responseParam.put("articleFormats", articleFormats);
			responseParam.put("siteId", siteId);
			
		// 模板设置中的栏目连接->指定栏目->查找栏目树(查找所有数据)
		} else if (dealMethod.equals("findTemplateSetColumnLinkTree")) {
			log.info("获得树");
			String columnId = (String) requestParam.get("treeid");
			List columnList = columnService.findTemplateSetColumnLinkTree(siteId, columnId);
			responseParam.put("columnList", columnList);
			log.info("获得树成功");
			
		// 查找同步栏目格式
		} else if (dealMethod.equals("findRefColumn")) {
			String columnId = (String) requestParam.get("columnId");
			//如果不是新增栏目则要查询栏目的同步信息
			String refColumnNameAndId = "";
			if(columnId != null && !columnId.equals("")) {
				refColumnNameAndId = columnService.findRefColumnNamesAndIds(columnId);
			}
			//查找同步网站名称
			String refSiteName = columnService.findRefSiteName(siteId);
			String articleFormatId = (String) requestParam.get("articleFormatId");
			ArticleFormat articleFormat = columnService.findRefColumnFormat(articleFormatId);
			//根据网站id查找格式不相同的栏目
			String sameFormatColumns = columnService.findSameFormatColumns(articleFormatId, siteId);
			//查找所有网站
			List list = new ArrayList();
			if(this.isUpSystemAdmin){
				list = columnService.findRefSiteList();
			}else{
				list = columnService.findRefSiteListByUserId(sessionID);
			}
			responseParam.put("list", list);
			responseParam.put("articleFormat", articleFormat);
			responseParam.put("sameFormatColumns", sameFormatColumns);
			responseParam.put("columnId", columnId);
			responseParam.put("refSiteId", siteId);
			responseParam.put("refSiteName", refSiteName);
			responseParam.put("refColumnNameAndId", refColumnNameAndId);
			
		// 查找同步栏目树
		} else if (dealMethod.equals("findRefColumnTree")) {
			String refSiteId = (String) requestParam.get("refSiteId");
			String columnId = (String) requestParam.get("treeid");
			List columnList = columnService.findColumnTree(refSiteId, columnId, sessionID, isUpSystemAdmin, isUpSiteAdmin);
			responseParam.put("columnList", columnList);
			responseParam.put("refSiteId", refSiteId);
		
		// 查找带有checkbox的树
		} else if(dealMethod.equals("getCheckBoxTree")) {
			log.info("获得树");
			String columnId = (String) requestParam.get("treeid");
			String checkedIds = (String) requestParam.get("checkedIds");
			List columnList = columnService.findColumnCheckBoxTree(siteId, columnId, sessionID, isUpSystemAdmin, isSiteAdmin, checkedIds);
			responseParam.put("columnList", columnList);
			log.info("获得树成功");
			
			// 粘贴栏目	
		} else  if(dealMethod.equals("choosePcolumnPaste")) {
			log.info("粘贴栏目");
			int isCopy = (Integer) requestParam.get("isCopy");
			String ids = (String) requestParam.get("ids");		
			String checkid = (String) requestParam.get("checkid");
			// 获取needcild（是否需要子结点）
			int needchild = (Integer) requestParam.get("needchild");
			//调用粘贴方法 
			String infoMessage = columnService.pasteParentcolumn(checkid, ids, siteId, sessionID, needchild, isCopy, isUpSystemAdmin, isSiteAdmin, columnService);
			responseParam.put("infoMessage", infoMessage);
			log.info(infoMessage);
			
		} else 	if(dealMethod.equals("choosePSiteColumnRefed")) {
			log.info("选择父站栏目引用");
			String ids = (String) requestParam.get("ids");		
			String checkid = (String) requestParam.get("checkid");
			//调用粘贴方法 
			String infoMessage = columnService.refPSiteColumn(checkid, ids, siteId, sessionID, columnService);
			responseParam.put("infoMessage", infoMessage);
			log.info(infoMessage);
			
			// 根据网站id查找同步网站
		} else 	if(dealMethod.equals("findRefSite")) {
			String refSiteId = (String) requestParam.get("refSiteId");
			String articleFormatId = (String) requestParam.get("articleFormatId");
			//查找同步网站名称
			String refSiteName = columnService.findRefSiteName(refSiteId);
			//根据网站id查找格式不相同的栏目
			String sameFormatColumns = columnService.findSameFormatColumns(articleFormatId, siteId);
			responseParam.put("refSiteId", refSiteId);
			responseParam.put("refSiteName", refSiteName);
			responseParam.put("sameFormatColumns", sameFormatColumns);
			
			// 查找select框的网站
		} else 	if(dealMethod.equals("selSite")) {
			String refSiteId = (String) requestParam.get("refSiteId");
			String articleFormatId = (String) requestParam.get("articleFormatId");
			responseParam.put("refSiteId", refSiteId);
			responseParam.put("articleFormatId", articleFormatId);
		}
		responseParam.put("nodeid", nodeid);
		responseParam.put("localNodeName", nodeName);
	}
	
	@Override
	public ResponseEvent validateData(RequestEvent req) throws Exception {
		return null;
	}
	
	/**
	 * @param columnService the columnService to set
	 */
	public void setColumnService(ColumnService columnService) {
		this.columnService = columnService;
	}
}
