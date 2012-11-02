/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.articlemanager.service;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import org.jdom.JDOMException;

import com.j2ee.cms.biz.articlemanager.domain.Article;
import com.j2ee.cms.biz.articlemanager.domain.ArticleAttribute;
import com.j2ee.cms.biz.articlemanager.domain.ArticleFormat;
import com.j2ee.cms.biz.articlemanager.web.form.ArticleForm;
import com.j2ee.cms.biz.configmanager.domain.GeneralSystemSet;
import com.j2ee.cms.common.core.dao.Pagination;

/**
 * <p>标题: 文章服务</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 文章管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 杨信
 * @version 1.0
 * @since 2009-3-10 下午05:25:42
 * @history（历次修订内容、修订人、修订时间等） 
 */
public interface ArticleService {
	
	/**  
	 * 添加文章
	 * @param article
	 */
	 public Map addArticle(Article article, Boolean isUpSystemAdmin);
	
	 /**
	  * 定时修改文章发布状态 
	  * @param articleId
	  */
	 void modifyArticlePublishState(String articleId);
	 
	 /**
	  * 处理定时发布文章
	  * @param pubishTime
	  */
	 void proccessTimeArticle(String timeSetting, String timeSettingArticleIds, ArticleService articleService);
	 
	/**
	 * 修改文章
	 * @param article
	 */
	void modifyArticle(Article article);
	
	/**
	 * 修改文章和引用文章
	 * @param article
	 * @param siteId
	 * @param sessionID
	 */
	Map<String, String> modifyArticleAndRefArticle(Article article, String siteId, String sessionID);
	
	/**
	 * 通过文章ID查找文章
	 * @param id
	 */
	Article findArticleById(String id);
	
	/**
	 * 查询文章页
	 * @param pagination
	 * @param columnId 栏目ID
	 * @param deleted 是否已删除的
	 * @param siteId 当前网站ID
	 * @return
	 */
	Pagination findArticlePagination(Pagination pagination, String columnId, boolean deleted, String siteId, boolean isUpSiteAdmin, String userId);
	
	/**
	 * 通过ids删除多篇文章
	 * @param ids
	 * @param siteId
	 * @param sessionID
	 * @return 影响的记录数
	 */
	int deleteArticleByIds(String ids, String siteId, String sessionID);
	
	
	/**
	 * 清除多篇文章
	 * @param ids
	 * @param siteId
	 * @param sessionID
	 * @return 影响的记录数
	 */
	int clearArticleByIds(String ids, String siteId, String sessionID);
	
	
	/**
	 * 查询所有格式
	 * @return
	 */
	List<ArticleFormat> findAllFormats();
	
	/**
	 * 通过格式ID查找其所有属性
	 * @param formatId 格式ID
	 * @return
	 */
	List<ArticleAttribute> findAttributesByFormatId(String formatId);
	
	/**
	 * 通过ids还原一批文章
	 * @param ids
	 * @param siteId
	 * @param sessionID
	 * @return 影响的文章数目
	 */
	int revertArticleByIds(String ids, String siteId, String sessionID);
	
	/**
	 * 通过ids审核一批文章
	 * @param ids
	 * @param siteId
	 * @param auditorId 审核人ID
	 * @return
	 */
	Map<String, String> auditArticleByIds(String ids, String siteId, String auditorId);
	
	/**
	 *  呈送文章
	 * @param columnIds
	 * @param presentArticleIds
	 * @param presentMethod
	 * @param articleService
	 * @param siteId
	 * @param sessionID
	 */
	Map<String, String> presentArticle(String columnIds, String presentArticleIds, int presentMethod, ArticleService articleService, String siteId, String sessionID);
	
	/**
	 * 转移文章
	 * @param moveArticleIds
	 * @param columnId
	 * @param articleService
	 * @param siteId
	 * @param sessionID
	 */
	Map<String, String> moveArticle(String moveArticleIds, String columnId, ArticleService articleService, String siteId, String sessionID);
	
	/**
	 * 查找排序文章
	 * @param columnId
	 * @param siteId
	 */
	List findSortArticle(ArticleForm form, String columnId, String siteId, boolean isUpSiteAdmin, String creatorId);
	
	/**
	 * 排序文章
	 * @param sortArticleIds
	 * @param articleService
	 * @param siteId
	 * @param sessionID
	 */
	void sortArticle(String sortArticleIds, ArticleService articleService, String siteId, String sessionID);
	
	/**
	 * 按照格式id查询格式
	 * @param fromatId
	 * @return
	 */
	ArticleFormat findFormatByFormatId(String fromatId);
	
	 /**
     * 获得替换后的页面
     * @param articleId 文章ID
     * @param columnId 栏目ID
     * @param siteId 网站ID
     * @param sessionID
     * @return
     */
    String getPreviewPage(String articleId, String columnId, String siteId, String sessionID);
    
    /**
     * 按照文章id获取栏目id
     * @param articleId
     * @return
     */
    String getColumnIdByArticleId(String articleId);

   /**
    *  获取当前文章所在栏目的格式名字
    * @param columnId
    * @return
    */
	String findpresentFormatNameByColumnId(String columnId);
	
	/**
	 * 获取当前文章所在栏目的格式id
	 * @param columnId
	 * @return
	 */
	String findpresentFormatIdByColumnId(String columnId);
	
	/**
	 * 获取文章中order字段的最大值
	 * @return
	 */
	int findMaxArticleOrder();
	
	/**
	 * 将文章置顶
	 * @param articleId
	 * @param orders
	 * @param isToped
	 * @param sitId
	 * @param userId
	 */
	void modifyArticleTop(String articleId, int orders, String isToped, String sitId, String userId);
	
	/**
	 * 发布文章页
	 * 说明：只是将文章放入生成列表
	 * @param articleIds
	 */
	void publish(String articleIds);
	
	/**
	 * 文章呈送时调用的添加文章方法
	 * @param article
	 */
	Map<String, String> addPresentArticle(Article article);
	
	/**
	 * 查找枚举信息字符串,形式为：id1,name1,#value11,value12,value13::id2,name2#value21,value22...
	 * @return
	 */
	String findEnumInfoInArticleService();
	
	/**
	 * 查找格式属性枚举类别Id
	 * @param formatId
	 * @return
	 */
	String findEnumerationIdByFormatId(String formatId);
	
	/**
	 * 导出文章
	 * @param exportArticleIds  导出的文章ids
	 * @param path  导出文章的路径
	 * @return message
	 */
	String exportArticles(String exportArticleIds, String path);
	
	/**
	 * 添加文章审核日志
	 * @param siteId
	 * @param userId
	 * @param categoryName
	 * @param title
	 */
	void addArticleLogs(String siteId, String userId, String categoryName, String title);
	

	

	/**
	 * 查询系统表中作者设置内容
	 * @return list作者对象
	 */
	
	 String findGeneralSystemSetList(String categoryId);
 
	 
	 /**
	   * 查询系统表中连接设置内容
	   * @return list链接替换对象
	   */
	  public List<GeneralSystemSet> findGeneralSystemSetLink(String Id);
      //public List findGeneralSystemSetLink(String Id);
	  
	  

	  
	  /**
	   * 过滤完全替换
	   * @param  articleTextAreal需要过滤的对象
	   * @param  siteId    网站id
	   * @return string过滤对象
	   */
	  String  replaceAllTextArear( String articleTextAreal, String siteId);	
	  
	/**
	 * 导入文章
	 * @param xmlpath 文件路径
	 * @param columnId
	 * @param siteId
	 * @param creatorId
	 * @param isUpSystemAdmin
	 * @return
	 */
	Map<String, String> importArticlesXml(String xmlpath, String columnId, String siteId, String creatorId, boolean isUpSystemAdmin)throws FileNotFoundException, JDOMException ;
	
	/**
	 * 查找格式相同的栏目
	 * @param columnId 呈送文章所在栏目id
	 * @param siteId 当前网站id
	 * @return sameFormatColumns 格式相同的栏目ids
	 */
	String findSameFormatColumns(String columnId, String siteId);
	
	/**
	 * 确认栏目是否已审核
	 * @param columnId
	 * @return
	 */
	boolean sureColumnHasAudited(String columnId);

	
	/**
	 * 确认用户是否拥有文章审核权限
	 * @param userId
	 * @param siteId
	 * @param columnId
	 * @return articleAuditedRights
	 */
	String findUserAuditedRights(String userId, String siteId, String columnId);

	/**
	 * 查找文章初始地址
	 * @param articleId
	 * @return
	 */
	String findArticleInitUrl(String articleId);
	
	/**
	 * 文章撤稿
	 * @param ids
	 * @param articleService
	 */
	void draftArticleByIds(String ids, ArticleService articleService);
	
	/**
	 * 根据主键查询文章格式名
	 * @param id
	 * @return
	 */
	String findFormatNameById(String id);
	
	void addArticleAtach(Article article, List<String> picAttachList, List<String> mediaAttachList, List<String> attachAttachList);
	void modifyArticleAtach(Article article, List<String> picAttachList, List<String> mediaAttachList, List<String> attachAttachList);
}
