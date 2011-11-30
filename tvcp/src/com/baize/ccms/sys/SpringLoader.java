/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.sys;

import java.util.List;

import org.apache.log4j.Logger;

import com.j2ee.cms.biz.articlemanager.dao.ArticleDao;
import com.j2ee.cms.biz.articlemanager.domain.Article;
import com.j2ee.cms.biz.publishmanager.dao.ArticlePublishListDao;
import com.j2ee.cms.biz.publishmanager.service.Publisher;

/**
 * <p>标题: 用于做一些初始化工作</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CPS</p>
 * <p>版权: Copyright (c) 2009  </p>
 * <p>网址：http://www.j2ee.cmsweb.com
 * @author <a href="mailto:sean_yang@163.com">杨信</a>
 * @version 1.0
 * @since 2009-8-7 下午03:48:13
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class SpringLoader{
	
	private static final Logger log = Logger.getLogger(SpringLoader.class);
	
	ArticleDao articleDao;
	
	ArticlePublishListDao articlePublishListDao;

	Publisher publisher;
	
	public SpringLoader() {
		// need for spring
	}

	public void init() {
		startPublishList();
	}
	
	private void startPublishList() {
		new Thread() {
			public void run() {
//				while(true) {
					log.debug("article publish thread start ...");
					try {
						Thread.sleep(10000 * 1000);
				//		System.out.println("search article.........................");
//						// 定时发布不知道网站id所以只有取出前十条（删除的除外）文章
//						List list = articlePublishListDao.findByNamedQuery("findAllArticle", 0, 10);
//						System.out.println("search success.................."+list.size());
//						if(list != null && list.size() > 0) {
//							Article article = null;
//							for(int i = 0; i < list.size(); i++) { 
//								article = (Article) list.get(i);
//								String siteId = article.getSite().getId();
//								String articleId = article.getId();
//								publisher.publishArticleByArticleId(articleId, siteId);
//							}
//						}
					} catch (Exception e) {
						e.printStackTrace();
					}
//				}
			}
		}.start();
	}
	
	public void setArticleDao(ArticleDao articleDao) {
		this.articleDao = articleDao;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	/**
	 * @param articlePublishListDao the articlePublishListDao to set
	 */
	public void setArticlePublishListDao(ArticlePublishListDao articlePublishListDao) {
		this.articlePublishListDao = articlePublishListDao;
	}

}
