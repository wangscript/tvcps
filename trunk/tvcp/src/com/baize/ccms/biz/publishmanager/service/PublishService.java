/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.publishmanager.service;

import com.j2ee.cms.biz.sitemanager.domain.Site;
import com.j2ee.cms.common.core.dao.Pagination;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 发布管理</p>
 * <p>版权: Copyright (c) 2009  </p>
 * @author <a href="mailto:sean_yang@163.com">杨信</a>
 * @version 1.0
 * @since 2009-6-18 上午10:48:08
 * @history（历次修订内容、修订人、修订时间等）
 */
public interface PublishService {
	
	/**
	 * 发布
	 * @param columnIds 栏目ids
	 * @param publishType 发布类型（column:栏目页,article:文章页）
	 * @param publishTemplate 是否发布模板
	 * @param siteId
	 * @param publishAll
	 * @param sessionID
	 */
	void publish(String columnIds, String publishType, boolean publishTemplate, String siteId, String sessionID);
	
	/**
	 * 查找文章发布列表页
	 * @param pagination
	 * @return
	 */
	Pagination findArticlePublishListPagination(Pagination pagination, String siteId);

	/**
	 * 整站发布
	 * @param siteId
	 * @param sessionID
	 * @return
	 */
	String publishAll(String siteId, String sessionID);
	
	/**
	 * 根据多个主键删除发布列表数据
	 * @param ids 主键
	 */
	void deleteArticlePublishListByIds(String ids);
	
	/**
	 * 删除所有发布列表的数据
	 */
	void deleteAll();
	
	/**
	 * 索引整站
	 * @param siteId  网站id
	 */
	void createAllSiteIndex(String siteId);
	
	/**
	 * 索引栏目和文章
	 * @param columnIds    要索引的栏目ids
	 * @param siteId       要索引的网站id
	 */
	void createColumnAndArticleIndex(String columnIds, String siteId);
	
	/**
	 * 发布文章页
	 * @param articleId 文章ID
	 * @param siteId 网站ID
	 */
	void publishArticleByArticleId(String articleId, String siteId);
}
