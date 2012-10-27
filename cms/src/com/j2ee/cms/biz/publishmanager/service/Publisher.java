/**
 * project：通用内容管理系统
 * Company:   
 */
package com.j2ee.cms.biz.publishmanager.service;

import java.util.List;

/**
 * <p>标题: 发布机</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 发布管理</p>
 * <p>版权: Copyright (c) 2009  </p>
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-6-17 上午09:46:38
 * @history（历次修订内容、修订人、修订时间等）
 */
public interface Publisher {

	/**
	 * 发布单个栏目页
	 * @param columnId
	 * @param siteId
	 */
	void publishColumnByColumnId(String columnId, String siteId);
	
	/**
	 * 发布单个文章页
	 * @param articleId
	 * @param siteId
	 */
	void publishArticleByArticleId(String articleId, String siteId);
	
	/**
	 * 发布一批栏目页
	 * @param columnIds
	 * @param siteId
	 */
	void publishColumnsByColumnIds(String[] columnIds, String siteId);
	
	/**
	 * 发布一批文章页
	 * @param columnId
	 * @param siteId
	 */
	void publishArticlesByColumnId(String columnId, String siteId);
	
	/**
	 * 发布一批文章页
	 * @param articleIds
	 * @param siteId
	 */
	void publishArticlesByArticleIds(String[] articleIds, String siteId);
	
	/**
	 * 发布一批文章页
	 * @param columnIds
	 * @param siteId
	 */
	void publishArticlesByColumnIds(String[] columnIds, String siteId);
	
	/**
	 * 发布页面
	 * @param columnIds 栏目ids
	 * @param publishType 发布类型 (column:栏目页,article:文章页)
	 * @param siteId
	 */
	void publish(String[] columnIds, String publishType, String siteId);
	
	/**
	 * 发布所有文章
	 */
	void publish();
	
	/**
	 * 预览页面
	 * @param templateInstaceId 模版实例ID
	 * @param articleId 文章ID
	 * @param columnId 栏目ID
	 * @param siteId 网站ID
	 * @return 预览页面的链接地址
	 */
	String previewPage(String templateInstanceId, String articleId, String columnId, String siteId);
	
	/**
	 * 按照网站id查询栏目
	 * @param siteId
	 * @return
	 */
	String findColumnBySiteId(String siteId);
	
	/**
	 * 生成文章
	 * @param articleId
	 * @param siteId
	 */
	public void buildArticleByArticleId(String articleId, String siteId);
	
	/**
	 * 生成单个栏目页
	 * @param columnId
	 * @param siteId
	 */
	public void buildColumnByColumnId(String columnId, String siteId);
	
	/**
	 * 发布某网站下所有模板
	 * @param siteId
	 */
	public void publishTemplatesBySiteId(String siteId);
	
	/**
	 * 初始化单元类别Map 
	 * key:   id
	 * value: name
	 */
	public void initCategoryMap();
	
	/**
	 * 生成首页
	 * @param siteId
	 */
	void buildHomePage(String siteId);
	
	/**
	 * 发布首页
	 * @param siteId
	 */
	void publishHomePage(String siteId);
}
