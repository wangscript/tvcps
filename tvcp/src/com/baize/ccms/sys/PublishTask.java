/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.sys;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.baize.ccms.biz.articlemanager.dao.ArticleDao;
import com.baize.ccms.biz.articlemanager.domain.Article;
import com.baize.ccms.biz.publishmanager.dao.ArticlePublishListDao;
import com.baize.ccms.biz.publishmanager.domain.ArticlePublishList;
import com.baize.ccms.biz.publishmanager.service.Publisher;
import com.baize.ccms.biz.templatemanager.dao.TemplateUnitDao;
import com.baize.common.core.util.DateUtil;
import com.baize.common.core.util.SqlUtil;
import com.baize.common.core.util.StringUtil;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CCMS</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司</p>
 * <p>网址：http://www.baizeweb.com
 * @author <a href="mailto:sean_yang@163.com">杨信</a>
 * @version 1.0
 * @since 2009-8-31 下午04:10:23
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class PublishTask {
	
	private static final Logger log = Logger.getLogger(PublishTask.class);
	
	/** 注入发布文章列表 */
	private ArticlePublishListDao articlePublishListDao;
	
	/** 注入发布机 */
	private Publisher publisher;
	
	/** 注入文章dao */
	private ArticleDao articleDao;

	/** 注册模板单元 */
	private TemplateUnitDao templateUnitDao;
	/**
	 * 定时发布页面
	 */ 
	public void publish() {
		
		StringBuffer idsBuf = new StringBuffer();
		
		// 定时发布不知道网站id所以只有取出前十条（删除的除外）文章
		List list = articlePublishListDao.findByNamedQuery("findAllArticle", 0, 10);
		log.info("publish aricles begin, size is "+list.size());
		String siteId = "";
		String columnId = "";
		List tempList = new ArrayList();
		if(list != null && list.size() > 0) {
			ArticlePublishList articlePublishList = null;
			for(int i = 0; i < list.size(); i++) { 
				articlePublishList = (ArticlePublishList) list.get(i);
				if(!articlePublishList.getStatus().equals("false")){
					
				siteId = articlePublishList.getSite().getId();
				String articleId = articlePublishList.getId();
				columnId = articlePublishList.getColumn().getId();
				try {
					log.debug("publishArticleByArticleId---articleId================"+articleId);
					publisher.publishArticleByArticleId(articleId, siteId);
					log.debug("articlePublishListDao.deleteByKey============"+articleId);
					articlePublishListDao.deleteByKey(articleId);
					String[] columnArray = {articlePublishList.getColumn().getId()};
					log.debug("publisher.publishColumnsByColumnIds========"+columnArray.length);
					publisher.publishColumnsByColumnIds(columnArray,siteId);
					idsBuf.append(",").append(articleId);
				} catch (Exception e) {
				
					if (StringUtil.contains(e.getMessage(), "publish")) {
						log.error(e.getMessage());
					}
					articlePublishList.setStatus("false");
					tempList.add(articlePublishList);
					e.printStackTrace();
					
				//	e.printStackTrace();
					// 跳过该未生成成功的文章
					//log.info("publish ... skip " + articleId);
				}
				}
			}
			
			for(int i = 0 ; i < tempList.size() ; i++){
				articlePublishList = (ArticlePublishList) tempList.get(i);
				articlePublishListDao.updateAndClear(articlePublishList);
			}
			if (idsBuf.length() > 1) {
				String[] paramNames = new String[]{"publishState", "publishTime", "ids"};
				String[] values = new String[]{SqlUtil.toSqlString(Article.PUBLISH_STATE_PUBLISHED), 
												SqlUtil.toSqlString(DateUtil.toString(new Date())), 
												SqlUtil.toSqlString(idsBuf.toString().replaceFirst(",", ""))};
				articleDao.updateByDefine("updateArticlePublishStateByIds", paramNames, values);
			}
		}
		
	/*	if(siteId != null && !siteId.equals("")){
			templateUnitDao.findByNamedQuery("findTemplateUnitBySiteId","siteId",siteId);
		}*/
		
		log.info("publish articles success!");
	}

	/**
	 * @param articlePublishListDao the articlePublishListDao to set
	 */
	public void setArticlePublishListDao(ArticlePublishListDao articlePublishListDao) {
		this.articlePublishListDao = articlePublishListDao;
	}

	/**
	 * @param publisher the publisher to set
	 */
	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	/**
	 * @param articleDao the articleDao to set
	 */
	public void setArticleDao(ArticleDao articleDao) {
		this.articleDao = articleDao;
	}

	/**
	 * @param templateUnitDao the templateUnitDao to set
	 */
	public void setTemplateUnitDao(TemplateUnitDao templateUnitDao) {
		this.templateUnitDao = templateUnitDao;
	}

}
