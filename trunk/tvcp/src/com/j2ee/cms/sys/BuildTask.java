/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.sys;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.j2ee.cms.biz.articlemanager.dao.ArticleDao;
import com.j2ee.cms.biz.articlemanager.domain.Article;
import com.j2ee.cms.biz.columnmanager.dao.ColumnDao;
import com.j2ee.cms.biz.columnmanager.domain.Column;
import com.j2ee.cms.biz.publishmanager.dao.ArticleBuildListDao;
import com.j2ee.cms.biz.publishmanager.dao.ArticlePublishListDao;
import com.j2ee.cms.biz.publishmanager.domain.ArticleBuildList;
import com.j2ee.cms.biz.publishmanager.domain.ArticlePublishList;
import com.j2ee.cms.biz.publishmanager.service.Publisher;
import com.j2ee.cms.biz.templatemanager.dao.TemplateUnitDao;
import com.j2ee.cms.biz.templatemanager.domain.TemplateUnit;
import com.j2ee.cms.common.core.util.BeanUtil;
import com.j2ee.cms.common.core.util.DateUtil;
import com.j2ee.cms.common.core.util.SqlUtil;
import com.j2ee.cms.common.core.util.StringUtil;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CPS</p>
 * <p>版权: Copyright (c) 2009  </p>
 * <p>网址：http://www.j2ee.cmsweb.com
 * @author <a href="mailto:sean_yang@163.com">杨信</a>
 * @version 1.0
 * @since 2009-8-31 下午04:10:23
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class BuildTask {
	
	private static final Logger log = Logger.getLogger(BuildTask.class);
	
	/** 注入生成文章列表 */
	private ArticleBuildListDao articleBuildListDao;
	
	/** 注入发布文章列表*/
	private ArticlePublishListDao articlePublishListDao;
	
	/** 注入发布机 */
	private Publisher publisher;
	/** 注册模板单元 */
	private TemplateUnitDao templateUnitDao;
	
	/** 栏目dao*/
	private ColumnDao columnDao;
	/** 文章dao */
	private ArticleDao articleDao;
	
	/**
	 * 定时生成文章页面和栏目页面
	 */ 
	public void build() {
		StringBuffer idsBuf = new StringBuffer();
		// 1.定时生成文章页面

		// 定时发布不知道网站id所以只有取出前十条（删除的除外）文章
		List list = articleBuildListDao.findByNamedQuery("findAllBuildArticle", 0, 10);
		log.info("build aricles begin, size is "+list.size());
		String siteId = "";
		String str[] = {"siteId","columnId"};
		//栏目实例或者文章实例
		String strColumnTemplate[] = {"templateInstanceId","siteId"};	 
		TemplateUnit templateUnit = null;
		Column column = null;
		String strInstanceId = "";
		String strColumnIds = "";
		Article article = null;
		String strColumnId = "";
		String columnType = "";
		
		if(list != null && list.size() > 0) {
			ArticleBuildList articleBuildList = null;
			ArticlePublishList articlePublishList = null;
/*			for(int  i = 0 ; i  < list.size() ; i++){
				articlePublishList = new ArticlePublishList(); 
				articleBuildList = (ArticleBuildList) list.get(i);
				BeanUtil.copyProperties(articlePublishList, articleBuildList);
				if (articlePublishListDao.getAndNonClear(articlePublishList.getId()) == null) {
					articlePublishListDao.save(articlePublishList);
					//articlePublishListDao.clearCache();
				} else {
					articlePublishListDao.update(articlePublishList);
			//		articlePublishListDao.clearCache();
				}
				String articleId = articleBuildList.getId();
				articleBuildListDao.deleteByKey(articleId);
			}*/
			for(int i = 0; i < list.size(); i++) { 
				articleBuildList = (ArticleBuildList) list.get(i);
				log.debug("articleBuildList.getStatus()==="+articleBuildList.getStatus() );
				if(articleBuildList.getStatus() == null || !articleBuildList.getStatus().equals("false")){
					try{
						articlePublishList = new ArticlePublishList();
						BeanUtil.copyProperties(articlePublishList, articleBuildList);
					}catch(Exception e){
						e.printStackTrace();
					}
					
					try {
						log.debug("i==========================================="+i);
					
						column = articleBuildList.getColumn();			
						strColumnId = column.getId();
						column = columnDao.getAndClear(strColumnId);
						columnType = column.getColumnType();
						String articleId = articleBuildList.getId();
						siteId = articleBuildList.getSite().getId();
						log.info("siteId============="+siteId);
						publisher.buildArticleByArticleId(articleId, siteId);
						log.info("articleId=========="+articleId);
						
						log.debug("articlePublishList.getId()================"+articlePublishList.getId());
						ArticlePublishList newArticlePublishList = articlePublishListDao.getAndClear(articlePublishList.getId());
						log.debug("newArticlePublishList================"+newArticlePublishList);
						// 生成成功写入发布列表中，同时删除生成列表
					//	BeanUtil.copyProperties(articlePublishList, articleBuildList);
						if ( newArticlePublishList == null ) {
							articlePublishListDao.cleanCache();
							articlePublishList.setStatus("true");
							log.debug("save articlePublishList");
							articlePublishListDao.save(articlePublishList);
							//articlePublishListDao.clearCache();
						} else {
							articlePublishList.setStatus("true");
							articlePublishListDao.update(articlePublishList);
					//		articlePublishListDao.clearCache();
						}
						articleBuildListDao.deleteByKey(articleId);
					
						article = null;
						log.debug("articleBuildList.getColumn().getId()===="+SqlUtil.toSqlString(strColumnId));
						//关联栏目页文章页发布，查询出实例ID，再根据栏目关联的实例查询出具体栏目
						String obj[]  = {SqlUtil.toSqlString(siteId),SqlUtil.toSqlString(strColumnId)};
						
						List relevanceColumnList = templateUnitDao.findByDefine("findTemplateUnitBySiteId",str,obj);
					
						strInstanceId = "";
						strColumnIds = "";
						for(int j = 0 ; j < relevanceColumnList.size() ; j++){
							log.debug(" relevanceColumnList.size()===="+ relevanceColumnList.size());
							templateUnit = (TemplateUnit)relevanceColumnList.get(j);
							String instanceId = templateUnit.getTemplateInstance().getId();
							if(!StringUtil.contains(strInstanceId, instanceId)){
								strInstanceId = strInstanceId + "," + instanceId;	
												
								Object objColumnTemplate[] = {instanceId,siteId};			 
								List columnList = columnDao.findByNamedQuery("findColumnByColumnTemplateInstanceIdAndSiteId",strColumnTemplate,objColumnTemplate);
								if(columnList != null && columnList.size() > 0){
									
								}else{
									Object obj2[] = {siteId,strColumnId};
									columnList = columnDao.findByNamedQuery("findColumnByColumnIdAndSiteId",str,obj2);								
								}
								for(int t = 0 ; t < columnList.size() ; t++){
									//关联栏目页
									column = (Column)columnList.get(t);	
									strColumnId = column.getId();				
									if(!StringUtil.contains(strColumnIds, strColumnId)){
										strColumnIds = strColumnIds + "," + strColumnId;
										publisher.buildColumnByColumnId(column.getId(), siteId);
										publisher.publishColumnByColumnId(column.getId(), siteId);
									}
				 
									
									columnType = column.getColumnType();
														
									if(columnType != null && columnType.equals("single")){
										List tempArticleList = articleDao.findByNamedQuery("findAuditedArticlesByColumnId","columnId",strColumnId);
										if(tempArticleList != null && tempArticleList.size() > 0){
											article = (Article)tempArticleList.get(0);
											publisher.buildArticleByArticleId(article.getId(), siteId);
											publisher.publishArticleByArticleId(article.getId(), siteId);
										}
								
									}
								}
								columnList = columnDao.findByNamedQuery("findColumnByArticleTemplateInstanceIdAndSiteId",strColumnTemplate,objColumnTemplate);
								if(columnList != null && columnList.size() > 0){
									
								}else{
									Object obj2[] = {siteId,strColumnId};
									columnList = columnDao.findByNamedQuery("findColumnByColumnIdAndSiteId",str,obj2);								
								}
								//生成发布关联文章页
								for(int t = 0 ; t < columnList.size() ; t++){
									column = (Column)columnList.get(t);
									String columnId = column.getId();
							
										
									columnType = column.getColumnType();
									strColumnId = column.getId();				
									if(columnType != null && columnType.equals("single")){
										List tempArticleList = articleDao.findByNamedQuery("findAuditedArticlesByColumnId","columnId",strColumnId);
										if(tempArticleList != null && tempArticleList.size() > 0){
											article = (Article)tempArticleList.get(0);
											publisher.buildArticleByArticleId(article.getId(), siteId);
											publisher.publishArticleByArticleId(article.getId(), siteId);
										}
								
									}
									if(!StringUtil.contains(strColumnIds, strColumnId)){
										strColumnIds = strColumnIds + "," + columnId;
										List tempArticleList = articleDao.findByNamedQuery("findArticleByColumnId","columnId",columnId);
										for(int y = 0 ; y < tempArticleList.size() ; y++){
											article = (Article)tempArticleList.get(y);
											if(article.isAudited()&& !article.getPublishState().equals(article.PUBLISH_STATE_DRAFT)){
												//只关联发布已审核的和未撤稿的
												publisher.buildArticleByArticleId(article.getId(), siteId);
												publisher.publishArticleByArticleId(article.getId(), siteId);
												idsBuf.append(",").append(article.getId());
											}								
										}
									}
								}
							}
						}
						
						}catch(Exception e){						 
							
								articleBuildListDao.clearCache();
								articleBuildList = articleBuildListDao.getAndNonClear(articleBuildList.getId());
								articleBuildList.setStatus("false");
								articleBuildListDao.update(articleBuildList);
								articleBuildListDao.cleanCache();
								e.printStackTrace();
								log.error("build task error=="+e.getMessage());
							 
							
						}
				}
			
			
			}
		}
		if (idsBuf.length() > 1) {
			String[] paramNames = new String[]{"publishState", "publishTime", "ids"};
			
			String[] values = new String[]{SqlUtil.toSqlString(Article.PUBLISH_STATE_PUBLISHED), 
											SqlUtil.toSqlString(DateUtil.toString(new Date())), 
											SqlUtil.toSqlString(idsBuf.toString().replaceFirst(",", ""))};
			articleDao.updateByDefine("updateArticlePublishStateByIds", paramNames, values);
		}
	//	articleDao.clearCache();
		log.info("build articles success!");
		
		// ////////////////////////
		
		// 2. 定时生成栏目页面
		
		/////////////////////////////////////

	}

	/**
	 * @param publisher the publisher to set
	 */
	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	/**
	 * @param articlePublishListDao the articlePublishListDao to set
	 */
	public void setArticlePublishListDao(ArticlePublishListDao articlePublishListDao) {
		this.articlePublishListDao = articlePublishListDao;
	}

	/**
	 * @param articleBuildListDao the articleBuildListDao to set
	 */
	public void setArticleBuildListDao(ArticleBuildListDao articleBuildListDao) {
		this.articleBuildListDao = articleBuildListDao;
	}

	/**
	 * @param templateUnitDao the templateUnitDao to set
	 */
	public void setTemplateUnitDao(TemplateUnitDao templateUnitDao) {
		this.templateUnitDao = templateUnitDao;
	}

	/**
	 * @param columnDao the columnDao to set
	 */
	public void setColumnDao(ColumnDao columnDao) {
		this.columnDao = columnDao;
	}

	/**
	 * @param articleDao the articleDao to set
	 */
	public void setArticleDao(ArticleDao articleDao) {
		this.articleDao = articleDao;
	}

}
