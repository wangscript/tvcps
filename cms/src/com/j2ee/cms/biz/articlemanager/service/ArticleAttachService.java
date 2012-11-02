/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.articlemanager.service;

import java.util.List;

import com.j2ee.cms.biz.articlemanager.domain.ArticleAttach;

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
public interface ArticleAttachService {
	
	/**  
	 * 添加文章附件
	 * @param articleAttach
	 */
	 public void addArticleAttach(ArticleAttach articleAttach);
	 
	/**
	 * 通过文章附件ID查找文章附件
	 * @param id
	 */
	ArticleAttach findArticleAttachById(String id);
	
	/**
     * 通过文章附件ID查找文章附件
     * @param articleId
     */
	List<ArticleAttach> findArticleAttachByArticleId(String articleId);
    
    /**
     * 通过文章id和附件类型查找文章附件
     * @param articleId
     * @param type
     */
	List findArticleAttachByArticleIdAndType(String articleId, String type);
    
}
