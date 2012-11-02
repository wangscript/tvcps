/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.articlemanager.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.j2ee.cms.biz.articlemanager.dao.ArticleAttachDao;
import com.j2ee.cms.biz.articlemanager.dao.ArticleDao;
import com.j2ee.cms.biz.articlemanager.domain.ArticleAttach;
import com.j2ee.cms.biz.articlemanager.service.ArticleAttachService;

/**
 * <p>
 * 标题: 文章服务类.
 * </p>
 * <p>
 * 描述: —— 简要描述类的职责、实现方式、使用注意事项等
 * </p>
 * <p>
 * 模块: 文章管理
 * </p>
 * <p>
 * 版权: Copyright (c) 2009  
 * 
 * @author 郑荣华
 * @version 1.0
 * @since 2009-3-10 下午05:26:02
 * @history（历次修订内容、修订人、修订时间等） 
 */
 

public class ArticleAttachServiceImpl implements ArticleAttachService {

	private final Logger log = Logger.getLogger(ArticleAttachServiceImpl.class);

	private ArticleDao articleDao;
    private ArticleAttachDao articleAttachDao;
	
	public void addArticleAttach(ArticleAttach articleAttach) {
	    articleAttachDao.save(articleAttach);
	}

	public List<ArticleAttach> findArticleAttachByArticleId(String articleId){
	    return articleAttachDao.findByNamedQuery("findArticleAttachsByArticleId", "articleId", articleId);
	}

	public ArticleAttach findArticleAttachById(String id){
	    return articleAttachDao.getAndClear(id);
    }
    
    public List<ArticleAttach> findArticleAttachByArticleIdAndType(String articleId, String type){
        String[] params = {"articleId", "type"};
        Object[] values = {articleId, type};
        return articleAttachDao.findByNamedQuery("findArticleAttachsByArticleIdAndAttachType", params, values);
    }

    public ArticleDao getArticleDao() {
        return articleDao;
    }

    public void setArticleDao(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    public ArticleAttachDao getArticleAttachDao() {
        return articleAttachDao;
    }

    public void setArticleAttachDao(ArticleAttachDao articleAttachDao) {
        this.articleAttachDao = articleAttachDao;
    }
}
