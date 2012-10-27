/**
 * project：通用内容管理系统
 * Company:   
 */
package com.j2ee.cms.biz.publishmanager.service.impl;

import java.io.File;
import java.util.Date;

import org.apache.log4j.Logger;

import com.baize.ccms.search.index.IndexCreate;
import com.baize.ccms.search.util.GlobalFunc;
import com.j2ee.cms.biz.articlemanager.dao.ArticleDao;
import com.j2ee.cms.biz.articlemanager.domain.Article;
import com.j2ee.cms.biz.configmanager.dao.SystemLogDao;
import com.j2ee.cms.biz.publishmanager.dao.ArticlePublishListDao;
import com.j2ee.cms.biz.publishmanager.service.PublishService;
import com.j2ee.cms.biz.publishmanager.service.Publisher;
import com.j2ee.cms.biz.sitemanager.dao.SiteDao;
import com.j2ee.cms.biz.sitemanager.domain.Site;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.util.DateUtil;
import com.j2ee.cms.common.core.util.FileUtil;
import com.j2ee.cms.common.core.util.SqlUtil;
import com.j2ee.cms.common.core.util.StringUtil;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 发布管理</p>
 * <p>版权: Copyright (c) 2009 </p>
 * @author <a href="mailto:sean_yang@163.com">杨信</a>
 * @version 1.0
 * @since 2009-6-18 上午10:50:49
 * @history（历次修订内容、修订人、修订时间等）
 */
public class PublishServiceImpl implements PublishService {
	
	private static final Logger log = Logger.getLogger(PublishServiceImpl.class);
	
	/** 注入发布机 */
	private Publisher publisher;
	
	/** 注入文章发布列表dao */
	private ArticlePublishListDao articlePublishListDao;
	
	/** 注入日志dao */
	private SystemLogDao systemLogDao;
	
	/** 注入网站dao */
	private SiteDao siteDao;
	
	/** 注入文章dao */
	private ArticleDao articleDao;
	
	public void publish(String columnIds, String publishType, boolean publishTemplate, String siteId, String sessionID) {
		String[] columnArray = StringUtil.split(columnIds, ",");
		
		// 写入日志文件
		String categoryName = "";
		String param = "";
		if ("column".equals(publishType)) {
			log.info("发布栏目页");
			
			if (publishTemplate) {
				log.info("发布模板");
				publisher.publishTemplatesBySiteId(siteId);
			}
			
			publisher.publishColumnsByColumnIds(columnArray, siteId);
			categoryName = "发布管理->发布栏目页";
			
		} else if ("article".equals(publishType)) {
			log.info("发布文章页");
			publisher.publishArticlesByColumnIds(columnArray, siteId);
			categoryName = "发布管理->发布文章页";
			
		} else if("publishHome".equals(publishType)){
			log.info("发布首页");
			// 首先发布首页
			publisher.buildHomePage(siteId);
			publisher.publishHomePage(siteId);
			
		}else if ("resource".equals(publishType)) {
			log.info("发布资源");
			categoryName = "发布管理->发布资源";
			throw new UnsupportedOperationException();
		}
		
		systemLogDao.addLogData(categoryName, siteId, sessionID, param);
	}
	
	public void publishArticleByArticleId(String articleIds, String siteId){
		String[] paramNames = new String[]{"publishState", "publishTime", "ids"};
		if(articleIds != null){
			String strArticleIds[] = articleIds.split(",");
			if(strArticleIds.length == 1){
				publisher.buildArticleByArticleId(strArticleIds[0],siteId);
				articlePublishListDao.deleteByKey(strArticleIds[0]);
				String[] values = new String[]{SqlUtil.toSqlString(Article.PUBLISH_STATE_PUBLISHED), 
						SqlUtil.toSqlString(DateUtil.toString(new Date())), 
						SqlUtil.toSqlString(strArticleIds[0].replaceFirst(",", ""))};
				articleDao.updateByDefine("updateArticlePublishStateByIds", paramNames, values);
			}else if(strArticleIds.length > 1){
				for(int i = 0 ; i < strArticleIds.length ; i++){
					publisher.publishArticleByArticleId(strArticleIds[i],siteId);
					articlePublishListDao.deleteByKey(strArticleIds[i]);
					String[] values = new String[]{SqlUtil.toSqlString(Article.PUBLISH_STATE_PUBLISHED), 
													SqlUtil.toSqlString(DateUtil.toString(new Date())), 
													SqlUtil.toSqlString(strArticleIds[i].replaceFirst(",", ""))};
					articleDao.updateByDefine("updateArticlePublishStateByIds", paramNames, values);
				}
			}
		}
	}
	
	public Pagination findArticlePublishListPagination(Pagination pagination, String siteId) {
		return articlePublishListDao.getPagination(pagination, "siteId", siteId);
	}
	
	/**
	 * 整站发布
	 * @param siteId
	 * @param sessionID
	 * @return
	 */
	public String publishAll(String siteId, String sessionID) {
		String columnIds = "";
		columnIds = publisher.findColumnBySiteId(siteId);
		
		// 写入日志文件
		String categoryName = "发布管理->整站发布";
		String param = "";
		systemLogDao.addLogData(categoryName, siteId, sessionID, param);
		
		return columnIds;
	}
	
	
	public void deleteArticlePublishListByIds(String ids){
		String strIds[] = ids.split(",");
		String newIds = "";
		//转换成删除语句所需要的ID格式
		for(int i = 0 ; i < strIds.length ; i++){
			newIds = newIds + "," + SqlUtil.toSqlString(strIds[i]);
		}
		newIds = StringUtil.replaceFirst(newIds, ",");
		articlePublishListDao.deleteByIds(newIds);		
	}
	
	public void deleteAll(){
		articlePublishListDao.deleteAll(articlePublishListDao.findAll());
	}
	
	/**
	 * 按照网站id获取网站信息
	 * @param siteId
	 * @return
	 */
	public Site getSiteInfo(String siteId){
		Site site = siteDao.getAndClear(siteId);
		return site;
	}
	
	/**
	 * 索引整站
	 * @param siteId  网站id
	 */
	public void createAllSiteIndex(String siteId){
		Site site = siteDao.getAndClear(siteId);
		String sitePublishDir = site.getPublishDir();
		
		// 网站发布目录
		String[] dir = sitePublishDir.split("/");
		String path = sitePublishDir.substring(0, sitePublishDir.length()-(dir[dir.length-1].length()));
		path = path + "lucence";
		if(!FileUtil.isExist(path)){
			FileUtil.makeDirs(path);
		}
		log.debug("path========="+path);
		// 重新设置lucence目录
		GlobalFunc.setIndexPath(path);
		
		GlobalFunc.setFilePath(sitePublishDir);
		log.debug("publishDir===="+sitePublishDir);
		IndexCreate indexCreate = new IndexCreate();
		siteId = dir[dir.length-1];
		try {
			indexCreate.addFile(siteId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 索引栏目和文章
	 * @param columnIds    要索引的栏目ids
	 * @param siteId       要索引的网站id
	 */
	public void createColumnAndArticleIndex(String columnIds, String siteId){
		if(!StringUtil.isEmpty(columnIds)){
			Site site = siteDao.getAndClear(siteId);
			String sitePublishDir = site.getPublishDir();
			
			// 网站发布目录
			String[] dir = sitePublishDir.split("/");
			String path = sitePublishDir.substring(0, sitePublishDir.length()-(dir[dir.length-1].length()));
			path = path + "lucence";
			if(!FileUtil.isExist(path)){
				FileUtil.makeDirs(path);
			}   
			log.debug("path========="+path);
			// 重新设置lucence目录
			GlobalFunc.setIndexPath(path);
			String[] ids = columnIds.split(",");
			siteId = dir[dir.length-1];
			// 循环对栏目索引    
			for(int i = 0; i < ids.length; i++){
				String columnId = ids[i];
				String columnPath = sitePublishDir + File.separator+"col_"+columnId;
				log.debug("columnPath========="+columnPath);
				if(FileUtil.isExist(columnPath)){   
					GlobalFunc.setFilePath(columnPath);
					IndexCreate indexCreate = new IndexCreate();
					try {
						indexCreate.addFile(siteId);
					} catch (Exception e) {
						e.printStackTrace(); 
					}
				}
			}
		}
	}
	
	/**
	 * @param publisher the publisher to set
	 */
	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public void setArticlePublishListDao(ArticlePublishListDao articlePublishListDao) {
		this.articlePublishListDao = articlePublishListDao;
	}

	/**
	 * @param systemLogDao the systemLogDao to set
	 */
	public void setSystemLogDao(SystemLogDao systemLogDao) {
		this.systemLogDao = systemLogDao;
	}

	/**
	 * @param siteDao the siteDao to set
	 */
	public void setSiteDao(SiteDao siteDao) {
		this.siteDao = siteDao;
	}

	/**
	 * @param articleDao the articleDao to set
	 */
	public void setArticleDao(ArticleDao articleDao) {
		this.articleDao = articleDao;
	}

}
