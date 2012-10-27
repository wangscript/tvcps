  /**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.columnmanager.service;

import java.io.IOException;
import java.util.List;

import jxl.read.biff.BiffException;

import com.j2ee.cms.biz.articlemanager.domain.ArticleFormat;
import com.j2ee.cms.biz.columnmanager.domain.Column;
import com.j2ee.cms.biz.columnmanager.web.form.ColumnForm;
import com.j2ee.cms.common.core.dao.Pagination;

/**
 * <p>标题: 栏目最高层接口</p>
 * <p>描述: 栏目表最高层接口</p>
 * <p>模块: 栏目管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 魏仲芹
 * @version 1.0
 * @since 2009-4-2 下午04:03:29
 * @history（历次修订内容、修订人、修订时间等）
 */
public interface ColumnService {
	
	/**
	 *添加数据 
	 *@param column 栏目对象
	 *@param type 操作类型
	 */
	
	void addColumn(Column column) ;
	
	/**
	 * 处理添加栏目
	 * @param separator 分隔符
	 * @param column    栏目对象
	 * @param siteId    网站id
	 * @param sessionID 用户id
	 */
	public String addColumn(String separator, Column column, String siteId, String sessionID, String nodeid,  boolean isUpSystemAdmin, boolean isSiteAdmin);
	
	/**
	 * 修改数据
	 * @param column 			栏目对象
	 * @param parentId 			要修改的栏目父id
	 * @param modifyparentid 	要修改的栏目id
	 */
	 void modifyColumn(Column column, String parentId, String modifyparentid, boolean isUpSystemAdmin, boolean isSiteAdmin, String siteId, String sessinID);
	
	 /**
	  * 修改栏目
	  * @param column    栏目对象
	  */
	 void modifyColumnOrders(Column column);
	 	
	/**
	 * 根据栏目id获取整条数据
	 * @param id    栏目id
	 * @return      栏目对象
	 */
	 Column findColumnById(String id);
	 
	/**
	 * 根据栏目的id分页展示栏目
	 * @param pagination     栏目分页对象
	 * @param columnid       栏目id
	 * @param siteId         网站id
	 * @param sessionID      用户id
	 * @param isUpSystemAdmin
	 * @param isSiteAdmin
	 * @return
	 */
	 Pagination findColumnByColumnIdPage(Pagination pagination, String columnid, String siteId, String sessionID, boolean isUpSystemAdmin, boolean isSiteAdmin);
	
	/**
	 * 分页展示当前网站中的第一级栏目
	 * @param pagination      栏目分页对象
	 * @param siteId          网站id
	 * @param sessionID       用户id
	 * @param isUpSystemAdmin
	 * @param isSiteAdmin
	 * @return                栏目分页对象
	 */
	 Pagination findColumnPage(String nodeId, Pagination pagination, String siteId, String sessionID, boolean isUpSystemAdmin, boolean isSiteAdmin); 
	
	/**
	 * 获取栏目ids，删除栏目
	 * @param ids             栏目ids
	 * @param siteId          网站id
	 * @param columnService   拉姆业务对象
	 */
	String deleteColumnids(String ids, String siteId, String nodeid, String sessionID, boolean isUpSystemAdmin, boolean isSiteAdmin);
	
	/**
	 * 栏目排序
	 * @param strid         栏目ids数组
	 * @param columnService 栏目业务对象
	 */
	void orderColumn(String ordersColumn, ColumnService columnService, String siteId, String sessionId);
	
	/**
	 * 导出数据到excel
	 * @param fileName 		  文件名
	 * @param siteId 	      网站id
	 * @param nodeid          节点id
	 * @param sessionID       创建者id
	 * @param columnService   栏目业务对象
	 * @throws Exception      抛出异常
	 */
	void exportColumnsToExcel(String fileName, String siteId, String nodeid,  String exportColumnIds, String sessionID, boolean isUpSystemAdmin, boolean isSiteAdmin, ColumnService columnService) throws Exception;
	
	/**
	 * 导入excel
	 * @param file            文件名称
	 * @param siteId          网站id
	 * @param sessionID       用户sessionId
	 * @param nodeid          节点的id
	 * @param columnService   栏目的业务对象
	 * @throws BiffException  解析xsl文件时的异常
	 * @throws IOException    输入输出异常
	 */
	String importExcel(String file, String siteId, String sessionID, String nodeid, boolean isUpSystemAdmin, boolean isSiteAdmin, ColumnService columnService) throws BiffException, IOException;
	
	/**
	 * 处理栏目的粘贴
	 * @param nodeid		  要复制到的位置id
	 * @param pasteIds 	      要复制的栏目id
	 * @param siteId          网站id
	 * @param creatorid       创建者id
	 * @param needchild       是否需要子节点
	 * @param columnService   栏目业务对象
	 */
	String pasteColumn(String nodeid, String pasteIds, String siteId, String creatorId, int needchild, int isCopy, boolean isUpSystemAdmin, boolean isSiteAdmin, ColumnService columnService);
	
	/**		
	 * 根据栏目的节点查找栏目的子节点
	 * @param parentid 	    栏目的父节点
	 * @param siteId      	网站id
	 * @param sessionID     当前登陆用户的id
	 * @param columnService 栏目业务对象
	 * @return 			    返回栏目列表
	 */
	List<Column> findChildColumn(ColumnForm form, String parentid, String siteId, String sessionId, boolean isUpSystemAdmin, boolean isSiteAdmin, ColumnService columnService);
	
	/**
	 * 查找父网站栏目并分页显示
	 * @param pagination  网站分页对象
	 * @param siteid  	  网站id
	 * @return		  	  返回父网站下的栏目信息	
	 */
	Pagination findParentSiteColumnPage(Pagination pagination, String siteid);

	/**
	 * 递归查找某个栏目的子栏目
	 * @param columnList  要递归的栏目的对象
	 * @param ids         结果要返回的ids 集合
	 * @return            返回栏目下的所有子节点
	 */
	String findChildColumnId(Column column, String ids);

	/**
	 * 按照网站id或者栏目id查找栏目树(根据不同的角色来判断显示内容)
	 * @param siteId 	    网站id
	 * @param columnId 	    栏目id
	 * @param sessionId     当前登陆用户的id
	 * @return			    返回List
	 */
	List findColumnTree(String siteId, String columnId, String sessionID, boolean isUpSystemAdmin, boolean isUpSiteAdmin);
	
	/**
	 * 查找父网站
	 * @param siteId 要查找的网站id
	 * @return       返回父网站的id
	 */
	String findParentSiteId(String siteId);
	
	/**
	 * 按照网站id或者栏目id查找栏目树
	 * @param siteId 	 网站id
	 * @param columnId 	 栏目id
	 * @return			 返回List
	 */
	List findParentSiteColumnTreeBySiteIdAndColumnId(String siteid, String columnid);
	
	/**
	 * 查询所有文章格式
	 * @return
	 */
	List<ArticleFormat> findAllArticleFormats();
	
	/**
	 * 查找父节点的名字
	 * @param nodeid  父节点id
	 * @return nodeName       返回栏目下的所有子节点
	 */
	String findNodeName(String nodeid);
	
	/** 查找当前是否为根站
	 * @param siteId 网站Id
	 * @return String
	 */
	String findSiteById(String siteId);
	
	/**
	 * 按照栏目id查找文章
	 * @param columnId
	 * @return             返回栏目下面是否有文章(1代表有，0代表没有)
	 */
	int findArticleByColumnId(String columnId);
	
	/**
	 * 模板设置中的栏目连接->指定栏目->查找栏目树(查找所有数据)
	 * @param siteId 	    网站id
	 * @param columnId 	    栏目id
	 * @return			    返回List
	 */
	public List findTemplateSetColumnLinkTree(String siteId, String columnId);
	
	/**
	 * 查找同步栏目格式
	 * @param columnId 	    栏目id
	 * @return			    返回格式对象
	 */
	ArticleFormat findRefColumnFormat(String columnId);
	
	/**
	 * 查找同步栏目名称
	 * @param refColumnIds  栏目ids
	 * @return	栏目名称
	 */
	String findRefColumnNames(String refColumnIds);
	
	/**
	 * 查找栏目树中所有与当前格式格式相同的栏目
	 * @param articleFormatId
	 * @param siteId 网站 id
	 * @return sameFormatColumns 格式相同的栏目ids
	 */
	String findSameFormatColumns(String articleFormatId, String siteId);
	
	/**
	 * 查找同步网站
	 * @param siteId 当前网站
	 * @returnv list
	 */
	List findRefSiteList(String siteId);
	
	/**
	 * 查找带有checkbox的树
	 * @param siteId
	 * @param columnId
	 * @param sessionID
	 * @param isUpSystemAdmin
	 * @param isSiteAdmin
	 * @param checkedIds
	 * @return
	 */
	List findColumnCheckBoxTree(String siteId, String columnId, String sessionID, boolean isUpSystemAdmin, boolean isSiteAdmin, String checkedIds);
	
	/**
	 * 处理父网站栏目的粘贴
	 * @param nodeid		  要复制到的位置id
	 * @param pasteIds 	      要复制的栏目ids
	 * @param siteId          网站id
	 * @param creatorid       创建者id
	 * @param needchild       是否需要子节点
	 * @param columnService   栏目业务对象
	 */
	String pasteParentcolumn(String nodeid, String pasteIds, String siteId, String creatorid, int needchild, int isCopy, boolean isUpSystemAdmin, boolean isSiteAdmin, ColumnService columnService);
	
	/**
	 * 根据栏目id查找同步网站id
	 * @param columnId
	 * @return
	 */
	String findRefSiteIdByColumnId(String columnId);
	
	/**
	 * 按照网站id或者栏目id查找文章管理栏目树(根据不同的角色来判断显示内容)
	 * @param siteId 	    网站id
	 * @param columnId 	    栏目id
	 * @param sessionId     当前登陆用户的id
	 * @return			    返回List
	 */
	@SuppressWarnings("unchecked")
	List findArticleColumnTree(String siteId, String columnId, String sessionId, boolean isUpSystemAdmin, boolean isUpSiteAdmin);
	
	/**
	 * 按照网站id或者栏目id查找模版设置栏目树(根据不同的角色来判断显示内容)
	 * @param siteId 	    网站id
	 * @param columnId 	    栏目id
	 * @param sessionId     当前登陆用户的id
	 * @return			    返回List
	 */
	@SuppressWarnings("unchecked")
	List findTemplateSetColumnTree(String siteId, String columnId, String sessionId, boolean isUpSystemAdmin, boolean isUpSiteAdmin);
	
	/**
	 * 处理引用父站的栏目
	 * @param nodeid		  要复制到的位置id
	 * @param pasteIds 	      要复制的栏目id
	 * @param siteId          网站id
	 * @param creatorid       创建者id
	 * @param needchild       是否需要子节点
	 * @param columnService   栏目业务对象
	 */
	String refPSiteColumn(String nodeid, String pasteIds, String siteId, String creatorid, ColumnService columnService);
	
	/**
	 * 查找同步网站
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	List findRefSiteList();
	
	/**
	 * 查找同步网站名称
	 * @param siteId
	 * @return refSiteName
	 */
	String findRefSiteName(String siteId);
	
	/**
	 * 根据栏目id查找同步栏目名称和id字符串
	 * @param columnId
	 * @return refColumnNameAndId
	 */
	String findRefColumnNamesAndIds(String columnId);
	
	/**
	 * 按照用户id查找同步网站
	 * @param sessionID
	 * @return
	 */
	List findRefSiteListByUserId(String sessionID);
}
