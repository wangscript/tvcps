  /**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.biz.columnmanager.web.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.baize.ccms.biz.articlemanager.domain.ArticleFormat;
import com.baize.ccms.biz.columnmanager.domain.Column;
import com.baize.ccms.biz.columnmanager.web.form.ColumnForm;
import com.baize.ccms.sys.GlobalConfig;
import com.baize.common.core.dao.Pagination;
import com.baize.common.core.domain.UploadedFile;
import com.baize.common.core.util.DateUtil;
import com.baize.common.core.util.ParseEncoding;
import com.baize.common.core.web.GeneralAction;
import com.baize.common.core.web.WebClientUtil;
import com.baize.common.core.web.event.RequestEvent;
import com.baize.common.core.web.event.ResponseEvent;
/**
 * <p>标题: 栏目Action</p>
 * <p>描述: 管理栏目的不同操作，封装请求对象</p>
 * <p>模块: 栏目管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 魏仲芹
 * @version 1.0
 * @since 2009-4-2 下午03:24:23
 * @history（历次修订内容、修订人、修订时间等）
 */
public class ColumnAction extends GeneralAction{
	
	private String dealMethod = "";
	
	@SuppressWarnings("unchecked")
	@Override
	protected void doFormFillment(ActionForm actionForm, ResponseEvent responseEvent, String userIndr) {
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		
		ColumnForm form = (ColumnForm) actionForm;
		String nodeid = (String) responseParam.get("nodeid");
		String localNodeName = "";
		localNodeName = (String) responseParam.get("localNodeName");
		String isRootSite = (String) responseParam.get("isRootSite");
		form.setIsRootSite(isRootSite);
		form.setNodeId(nodeid);
		form.setLocalNodeName(localNodeName);
		
		// 查询栏目并分页
		if(dealMethod.equals("")) {
			Pagination pagination = (Pagination) responseParam.get("pagination");
			form.setPagination(pagination);
			this.setRedirectPage("success", userIndr);
		
		// 添加栏目
		} else 	if(dealMethod.equals("add")) {
			String infoMessage = (String) responseParam.get("infoMessage");
			// "增加栏目成功"
			form.setInfoMessage(infoMessage);
			this.setRedirectPage("return", userIndr);
		
		// 修改栏目
		} else 	if(dealMethod.equals("modify")) {
			form.setInfoMessage("修改栏目成功");
			this.setRedirectPage("return", userIndr);
			
		// 删除栏目	
		} else 	if(dealMethod.equals("delete")) {
			String infoMessage = "";
			infoMessage = (String) responseParam.get("infoMessage");
			form.setInfoMessage(infoMessage);
			this.setRedirectPage("return", userIndr);
		
		// 显示栏目详细信息
		} else 	if(dealMethod.equals("detail")) {
			//同步网站类型
			String siteRefType = (String) responseParam.get("siteRefType");
			//同步网站id
			String refSiteId = (String) responseParam.get("refSiteId");
			List list = (List) responseParam.get("list");
			String columnNames = (String) responseParam.get("columnNames");
			String parentName = (String) responseParam.get("parentName");
			Column column = (Column) responseParam.get("column");
			int hasArticle = (Integer) responseParam.get("hasArticle");
			List<ArticleFormat> articleFormats = (List<ArticleFormat>) responseParam.get("articleFormats");
			form.setParentName(parentName);
			form.setArticleFormats(articleFormats);
			if(column.getUpdateTime() != null) {
				Date updateTime = column.getUpdateTime();
				String time = DateUtil.toString(updateTime);
				form.setUpdateTime(time);
			}
			form.setSiteRefType(siteRefType);
			form.setColumnNames(columnNames);
			form.setColumn(column);
			form.setIsAddColumn(0);
			form.setHasArticle(hasArticle);
			form.setRefSiteId(refSiteId);
			form.setList(list);
			
		// 导入excel时先上传到服务器，再将名称保存到请求中
		} else 	if(dealMethod.equals("import")) {
			String infoMessage = "";
			infoMessage = (String) responseParam.get("infoMessage");
			form.setInfoMessage(infoMessage);
			this.setRedirectPage("importSuccess", userIndr);
		
		// 导出excel时获取服务器中的路径
		} else 	if(dealMethod.equals("export")) {
			form.setInfoMessage("栏目导出成功");
			this.setRedirectPage("columnExportSuccess", userIndr);
		
		// 保存已复制的栏目id到请求对象中
		} else 	if(dealMethod.equals("paste")) {
			String infoMessage = (String) responseParam.get("infoMessage");
			form.setInfoMessage(infoMessage);
			this.setRedirectPage("pasteSuccess", userIndr);
		
		// 排序时展示原来的根目录下的节点
		} else 	if(dealMethod.equals("findSortColumn")) {
			form = (ColumnForm) responseParam.get("form");
			form.setIsRootSite(isRootSite);
			form.setNodeId(nodeid);
			form.setLocalNodeName(localNodeName);
			List<Column> childColumnList = (List<Column>) responseParam.get("childColumnList");
			form.setJson_list(childColumnList);	
			this.setRedirectPage("sortColumns", userIndr);
		
		// 获取页面排序后的栏目id保存到请求对象中
		} else 	if(dealMethod.equals("sort")) {
			form.setInfoMessage("栏目排序成功");
			this.setRedirectPage("sortColumns", userIndr);
		
		// 选择网站时，将选中的父网站栏目id保存到请求对象中	
		} else 	if(dealMethod.equals("findParentSiteColumn")) {
			//form.setInfoMessage("栏目选择成功");
			String parentSiteId =  (String) responseParam.get("parentSiteId");
			form.setSiteid(parentSiteId);
			Pagination pagination = (Pagination) responseParam.get("pagination");
			form.setPagination(pagination);
			this.setRedirectPage("findParentSiteColumn", userIndr);
						
		// 获得树	
		} else  if(dealMethod.equals("getTree")) {
			List columnList = (List) responseParam.get("columnList");
			form.setJson_attrnames(new String[]{"id","text","url","leaf","checked","formatid"});
			form.setJson_list(columnList);
			this.setRedirectPage("tree", userIndr);
			
		//	检查要选择的上级栏目是否是自己的子栏目或者自己本身
		} else  if(dealMethod.equals("checkColumn")) {
			String columnIds = (String) responseParam.get("columnIds");
			form.setChildColumnIds(columnIds);
			this.setRedirectPage("chooseColumn", userIndr);
			
		// 加载父网站的树
		} else  if(dealMethod.equals("parentSiteTree")) {
			List columnList = (List) responseParam.get("columnList");
			form.setJson_attrnames(new String[]{"id","text","url","leaf","checked","formatid"});
			form.setJson_list(columnList);
			this.setRedirectPage("tree", userIndr);
		
		// 保存导出栏目	
	    } else  if(dealMethod.equals("savaExportColumn")) {
	    	this.setRedirectPage("webClient", userIndr);
	    	
		// 查询所有文章格式
		} else if(dealMethod.equals("findAllFormats")) {
			//同步网站id
			String refSiteId = (String) responseParam.get("siteId");
			List list = (List) responseParam.get("list");
			List<ArticleFormat> articleFormats = (List<ArticleFormat>) responseParam.get("articleFormats");
			form.setRefSiteId(refSiteId);
			form.setArticleFormats(articleFormats);
			form.setList(list);
			form.setIsAddColumn(1);
			this.setRedirectPage("detailsuccess", userIndr);
			
		// 模板设置中的栏目连接->指定栏目->查找栏目树(查找所有数据)
		} else if(dealMethod.equals("findTemplateSetColumnLinkTree")) {
			List columnList = (List) responseParam.get("columnList");
			form.setJson_attrnames(new String[]{"id","text","url","leaf","checked","formatid"});
			form.setJson_list(columnList);
			this.setRedirectPage("tree", userIndr);
			
		// 查找同步栏目格式
		} else if(dealMethod.equals("findRefColumn")) {
			//同步栏目名称和id字符串
			String refColumnNameAndId = (String) responseParam.get("refColumnNameAndId");
			List list = (List) responseParam.get("list");
			form.setList(list);
			//同步的网站id
			String refSiteId = (String) responseParam.get("refSiteId");
			//同步的网站名称
			String refSiteName = (String) responseParam.get("refSiteName");
			//格式相同的栏目
			String columnId = (String) responseParam.get("columnId");
			String sameFormatColumns = (String) responseParam.get("sameFormatColumns");
			ArticleFormat articleFormat = (ArticleFormat) responseParam.get("articleFormat");
			form.setRefSiteId(refSiteId);
			form.setRefSiteName(refSiteName);
			form.setArticleFormat(articleFormat);
			form.setSameFormatColumns(sameFormatColumns);
			form.setColumnId(columnId);
			form.setRefColumnNameAndId(refColumnNameAndId);
			this.setRedirectPage("findRefColumn", userIndr);
			
		// 查找同步栏目树
		} else if(dealMethod.equals("findRefColumnTree")) {
			String refSiteId = (String) responseParam.get("refSiteId");
			List columnList = (List) responseParam.get("columnList");
			form.setJson_attrnames(new String[]{"id","text","url","leaf","checked","formatid"});
			form.setJson_list(columnList);
			form.setRefSiteId(refSiteId);
			this.setRedirectPage("tree", userIndr);
			
		// 查找带有checkbox的树	
		} else if(dealMethod.equals("getCheckBoxTree")) {
			List columnList = (List) responseParam.get("columnList");
			form.setJson_list(columnList);
			this.setRedirectPage("tree", userIndr);
			
		// 保存已复制的栏目id到请求对象中
		} else 	if(dealMethod.equals("choosePcolumnPaste")) {
			String infoMessage = (String) responseParam.get("infoMessage");
			form.setInfoMessage(infoMessage);
			this.setRedirectPage("pasteSuccess", userIndr);
			
		// 获得文章管理栏目树	
		} else  if(dealMethod.equals("getArticleColumnTree")) {
			log.info("获得文章管理栏目树");
			List columnList = (List) responseParam.get("columnList");
			form.setJson_attrnames(new String[]{"id","text","url","leaf","checked","formatid"});
			form.setJson_list(columnList);
			this.setRedirectPage("tree", userIndr);

		// 获得文章管理栏目树	
		} else  if(dealMethod.equals("getTemplateSetTree")) {
			log.info("获得文章管理栏目树");
			List columnList = (List) responseParam.get("columnList");
			form.setJson_attrnames(new String[]{"id","text","url","leaf","checked","formatid"});
			form.setJson_list(columnList);
			this.setRedirectPage("tree", userIndr);
		
		// 保存已复制的栏目id到请求对象中
		} else 	if(dealMethod.equals("choosePSiteColumnRefed")) {
			String infoMessage = (String) responseParam.get("infoMessage");
			form.setInfoMessage(infoMessage);
			this.setRedirectPage("pasteSuccess", userIndr);
			
			// 根据网站id查找同步网站
		} else 	if(dealMethod.equals("findRefSite")) {
			String refSiteId = (String) responseParam.get("refSiteId");
			form.setRefSiteId(refSiteId);
			String sameFormatColumns = (String) responseParam.get("sameFormatColumns");
			//同步的网站名称
			String refSiteName = (String) responseParam.get("refSiteName");
			form.setRefSiteName(refSiteName);
			form.setSameFormatColumns(sameFormatColumns);
			form.setTreeUrl("column.do?dealMethod=findRefColumnTree&nodeId=0&nodeName=null&refSiteId="+refSiteId);
			this.setRedirectPage("findRefSite", userIndr);
			
			// 查找select框的网站
		} else 	if(dealMethod.equals("selSite")) {
			String refSiteId = (String) responseParam.get("refSiteId");
			String articleFormatId = (String) responseParam.get("articleFormatId");

			form.setRefSiteId(refSiteId);
			form.setArticleFormatId(articleFormatId);
			this.setRedirectPage("selSite", userIndr);
		}
	}

	@Override
	protected void performTask(ActionForm actionForm, RequestEvent requestEvent, String userIndr) throws Exception {
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		HttpServletRequest request = (HttpServletRequest) requestParam.get("HttpServletRequest");
		
		ColumnForm form = (ColumnForm) actionForm;
		this.dealMethod = form.getDealMethod();
		Column column = new Column();
		String nodeid = form.getNodeId();
		if(nodeid == null || nodeid.equals("null") || nodeid.equals("0") || nodeid.equals("")) {
			nodeid = null;
		}
		String localNodeName = "";
		localNodeName = form.getLocalNodeName();
		if(localNodeName != null && !localNodeName.equalsIgnoreCase("")) {
			localNodeName = new String(localNodeName.getBytes("ISO-8859-1"), "UTF-8");
			ParseEncoding parseEncoding = new ParseEncoding();
			parseEncoding.getEncoding(localNodeName);
		}
		// 查询栏目并分页
		if(dealMethod.equals("")) {
			//节点为0时，加载第一级栏目
			if(nodeid == null) {
				form.setQueryKey("findFirstColumnByColumnIdsPage");
			} else {
				form.setQueryKey("findColumnByColumnIdsPage");
			}
			requestParam.put("pagination", form.getPagination());
		
		// 添加栏目	
		} else 	if(dealMethod.equals("add")) {
			String separator = form.getSeparator();
			column = form.getColumn();
			column.setCreateTime(new Date());
			String updateTime = form.getUpdateTime();
			if(!updateTime.equals("") && updateTime != null && !updateTime.equals("null")) {
				Date time = DateUtil.toDate(updateTime);
				column.setUpdateTime(time);	
			}
			Column parent = form.getParent();
			if(nodeid != null) {
				parent.setId(nodeid);
				column.setParent(parent);
			}
			if(form.getArticleFormatId() != null && !form.getArticleFormatId().equals("0") && !form.getArticleFormatId().equals("")) {
				// 设置文章的格式
				String articleFormatId = form.getArticleFormatId();
				ArticleFormat af = new ArticleFormat();
				af.setId(articleFormatId);
				column.setArticleFormat(af);
			}
			requestParam.put("separator", separator);
			requestParam.put("column", column);
			
		// 删除栏目	
		} else 	if(dealMethod.equals("delete")) {
			String ids = form.getIds();
			requestParam.put("ids", ids);
			
		// 修改栏目
		} else 	if(dealMethod.equals("modify")) {
			// 获得栏目修改前的父栏目id
			String modifyParentId = form.getModifyparentid();
			if(modifyParentId == null || modifyParentId.equals("0") || modifyParentId.equals("")) {
				modifyParentId = null;
			}
			String parentId = form.getParentid();
			if(parentId == null || parentId.equals("0") || parentId.equals("")) {
				parentId = null;
			}
			String articleFormatId = form.getArticleFormatId();
			ArticleFormat articleFormat = new ArticleFormat();
			column = form.getColumn();
			
			if (articleFormatId != null) {
				articleFormat.setId(articleFormatId);
				column.setArticleFormat(articleFormat);
			} else {
				column.setArticleFormat(null);
			}
			String updateTime = form.getUpdateTime();
			if(!updateTime.equals("") && updateTime != null && !updateTime.equals("null")) {
				Date time = DateUtil.toDate(updateTime); 
				column.setUpdateTime(time);	
			}
			requestParam.put("modifyParentId", modifyParentId);
			requestParam.put("parentId", parentId);
			requestParam.put("column", column);
				
		// 显示栏目详细信息	
		} else	if(dealMethod.equals("detail")) {
			requestParam.put("columnId", form.getIds());
		
		// 导入excel时先上传到服务器，再将名称保存到请求中
		} else 	if(dealMethod.equals("import")) {
			String filePath = GlobalConfig.appRealPath;
			List<UploadedFile> list = WebClientUtil.uploadFile(form, filePath, 1024*1024);
			UploadedFile uploadedFile = list.get(0);
			String files = "";
			if(uploadedFile.getFileSize() == 0) {
				files = "0";
			}else{
				files = filePath + "/" + uploadedFile.getChangefileName();
			}
			requestParam.put("file", files);
			
		// 导出excel的路径
		} else 	if(dealMethod.equals("export")) {
			// 获取导出栏目的id
			String exportColumnIds = form.getExportColumnIds();
			if(exportColumnIds == null || exportColumnIds.equals("") || exportColumnIds.equals("null")) {
				exportColumnIds = null;
			}
			requestParam.put("nodeid", nodeid);
			requestParam.put("exportColumnIds", exportColumnIds);
			
		// 保存已复制的栏目id到请求对象中
		} else 	if(dealMethod.equals("paste")) {
			// 是否是复制（非选择父网站栏目的复制）
			int isCopy = form.getIsCopy();
			//是否需要粘贴子节点
			int needchild = form.getNeedchild();
			//获得要粘贴的节点
			String ids = form.getIds();
			//获取要复制到的栏目的位置
			String checkid = form.getCheckid();
			if(checkid == null || checkid.equals("0") || checkid.equals("")) {
				checkid = null;
			}
			requestParam.put("checkid", checkid);
			requestParam.put("ids", ids);
			requestParam.put("needchild", needchild);
			requestParam.put("isCopy", isCopy);
			
		// 获取页面排序后的栏目id保存到请求对象中	
		} else 	if(dealMethod.equals("sort")){
			String ordersColumn = form.getOrdersColumn();
			requestParam.put("ordersColumn", ordersColumn);
		
		// 查找父网站栏目信息	
		} else  if(dealMethod.equals("findParentSiteColumn")) {
			form.setQueryKey("findColumnBySiteId");
			
		// 获得树	
		} else  if(dealMethod.equals("getTree")) {
			String treeid = form.getTreeId();
			if(treeid == null || treeid.equals("0") || treeid.equals("")) {
				treeid = null;
			}
			requestParam.put("treeid", treeid);
		
		// 获得文章管理栏目树	
		} else  if(dealMethod.equals("getArticleColumnTree")) {
			String treeid = form.getTreeId();
			if(treeid == null || treeid.equals("0") || treeid.equals("")) {
				treeid = null;
			}
			requestParam.put("treeid", treeid);
		
		// 获取模版设置栏目树
		}else if(dealMethod.equals("getTemplateSetTree")){
			String treeid = form.getTreeId();
			if(treeid == null || treeid.equals("0") || treeid.equals("")) {
				treeid = null;
			}
			requestParam.put("treeid", treeid);
			
		// 检查要选择的上级栏目是否是自己的子栏目或者自己本身	
		} else  if(dealMethod.equals("checkColumn")) {
			String columnId = form.getColumnId();
			if(columnId == null || columnId.equals("0") || columnId.equals("")) {
				columnId = null;
			}
			requestParam.put("columnId", columnId);
		
		// 加载父网站的树
		} else if(dealMethod.equals("parentSiteTree")) {
			String siteid = form.getSiteid();
			String treeid = form.getTreeId();
			if(treeid == null || treeid.equals("0") || treeid.equals("")) {
				treeid = null;
			}
			requestParam.put("treeid", treeid);
			requestParam.put("siteid", siteid);
			
		// 保存导出栏目		
		} else  if(dealMethod.equals("savaExportColumn")) {
			requestParam.put("request", request);
			
		// 查询所有文章格式
		} else if (dealMethod.equals("findAllFormats")) {
			
		// 查找排序栏目	
		} else 	if(dealMethod.equals("findSortColumn")) {
			requestParam.put("form", form);
			
		// 模板设置中的栏目连接->指定栏目->查找栏目树(查找所有数据)
		} else if (dealMethod.equals("findTemplateSetColumnLinkTree")) {
			String treeid = form.getTreeId();
			if(treeid == null || treeid.equals("0") || treeid.equals("")) {
				treeid = null;
			}
			requestParam.put("treeid", treeid);
			
		// 查找同步栏目格式
		} else if (dealMethod.equals("findRefColumn")) {
			String columnId = form.getColumnId();
			String articleFormatId = form.getArticleFormatId();
			requestParam.put("articleFormatId", articleFormatId);
			requestParam.put("columnId", columnId);
			
		// 查找同步栏目树
		} else if (dealMethod.equals("findRefColumnTree")) {
			String refSiteId = form.getRefSiteId();
			String treeid = form.getTreeId();
			if(treeid == null || treeid.equals("0") || treeid.equals("")) {
				treeid = null;
			}
			requestParam.put("treeid", treeid);
			requestParam.put("refSiteId", refSiteId);
			
		// 查找带有checkbox的树	
		} else if(dealMethod.equals("getCheckBoxTree")) {
			String checkedIds = form.getCheckedIds();
			String treeid = form.getTreeId();
			if(treeid == null || treeid.equals("0") || treeid.equals("")) {
				treeid = null;
			}
			requestParam.put("treeid", treeid);
			requestParam.put("checkedIds", checkedIds);
			
			// 保存已复制的栏目id到请求对象中
		} else 	if(dealMethod.equals("choosePcolumnPaste")) {
			// 是否是复制（非选择父网站栏目的复制）
			int isCopy = form.getIsCopy();
			//是否需要粘贴子节点
			int needchild = form.getNeedchild();
			//获得要粘贴的节点
			String ids = form.getIds();
			//获取要复制到的栏目的位置
			String checkid = form.getCheckid();
			if(checkid == null || checkid.equals("0") || checkid.equals("")) {
				checkid = null;
			}
			requestParam.put("checkid", checkid);
			requestParam.put("ids", ids);
			requestParam.put("needchild", needchild);
			requestParam.put("isCopy", isCopy);
		
		// 保存已复制的栏目id到请求对象中
		} else 	if(dealMethod.equals("choosePSiteColumnRefed")) {
			// 是否是复制（非选择父网站栏目的复制）
			int isCopy = form.getIsCopy();
			//是否需要粘贴子节点
			int needchild = form.getNeedchild();
			//获得要粘贴的节点
			String ids = form.getIds();
			//获取要复制到的栏目的位置
			String checkid = form.getCheckid();
			if(checkid == null || checkid.equals("0") || checkid.equals("")) {
				checkid = null;
			}
			requestParam.put("checkid", checkid);
			requestParam.put("ids", ids);
			requestParam.put("needchild", needchild);
			requestParam.put("isCopy", isCopy);
			
		// 根据网站id查找同步网站
		} else 	if(dealMethod.equals("findRefSite")) {
			String refSiteId = form.getRefSiteId();
			String articleFormatId = form.getArticleFormatId();
			requestParam.put("refSiteId", refSiteId);
			requestParam.put("articleFormatId", articleFormatId);
			
		// 查找select框的网站
		} else 	if(dealMethod.equals("selSite")) {
			String refSiteId = form.getRefSiteId();
			String articleFormatId = form.getArticleFormatId();
			requestParam.put("refSiteId", refSiteId);
			requestParam.put("articleFormatId", articleFormatId);
			
		} 
		requestParam.put("nodeid", nodeid);
		requestParam.put("localNodeName", localNodeName);
	}

	@Override
	protected void init(String userIndr) throws Exception {
		this.setRedirectPage("detailsuccess", userIndr);
	}
	
	/**
	 * @param userIndr
	 * @throws Exception
	 */
	protected void beforeRedirect(String userIndr) throws Exception {
	}
}
