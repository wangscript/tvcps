/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.publishmanager.service.impl;

import com.baize.ccms.biz.configmanager.dao.SystemLogDao;
import com.baize.ccms.biz.publishmanager.dao.ArticleBuildListDao;
import com.baize.ccms.biz.publishmanager.dao.ArticlePublishListDao;
import com.baize.ccms.biz.publishmanager.service.BuildService;
import com.baize.ccms.biz.publishmanager.service.Publisher;
import com.baize.common.core.dao.Pagination;
import com.baize.common.core.util.SqlUtil;
import com.baize.common.core.util.StringUtil;
import org.apache.log4j.Logger;

/**
 * 
 * <p>标题: —— 生成列表业务逻辑处理类</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 用户管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-11-11 下午02:24:44
 * @history（历次修订内容、修订人、修订时间等）
 */
public class BuildServiceImpl implements BuildService {
	
	private static final Logger log = Logger.getLogger(BuildServiceImpl.class); 
	
	/** 注入文章发布列表dao */
	private ArticleBuildListDao articleBuildListDao;
	
	/** 注入日志dao */
	private SystemLogDao systemLogDao;
	 
 
	public Pagination findArticleBuildListPagination(Pagination pagination, String siteId) {
		return articleBuildListDao.getPagination(pagination, "siteId", siteId);
	}
	
	public void deleteArticleBuildListByIds(String ids){
		String strIds[] = ids.split(",");
		String newIds = "";
		//转换成删除语句所需要的ID格式
		for(int i = 0 ; i < strIds.length ; i++){
			newIds = newIds + "," + SqlUtil.toSqlString(strIds[i]);
		}
		newIds = StringUtil.replaceFirst(newIds, ",");
		articleBuildListDao.deleteByIds(newIds);		
	}
	
	
	public void deleteAll(){
		articleBuildListDao.deleteAll(articleBuildListDao.findAll());
	}
 
	/**
	 * @param systemLogDao the systemLogDao to set
	 */
	public void setSystemLogDao(SystemLogDao systemLogDao) {
		this.systemLogDao = systemLogDao;
	}

	/**
	 * @param articleBuildListDao the articleBuildListDao to set
	 */
	public void setArticleBuildListDao(ArticleBuildListDao articleBuildListDao) {
		this.articleBuildListDao = articleBuildListDao;
	}
 
}
