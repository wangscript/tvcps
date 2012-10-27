/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.plugin.onlineBulletin.service;

import java.util.List;

import com.j2ee.cms.plugin.onlineBulletin.domain.OnlineBulletin;
import com.j2ee.cms.plugin.onlinesurvey.domain.OnlineSurvey;
import com.j2ee.cms.common.core.dao.Pagination;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类网上调查包含的内容</p>
 * <p>模块: CPS</p>
 * <p>版权: Copyright (c) 2009  </p>
 * <p>网址：http://www.j2ee.cmsweb.com
 * @author 包坤涛
 * @version 1.0
 * @since 2009-10-19 下午02:46:26
 * @history（历次修订内容、修订人、修订时间等） 
 */
public interface OnlineBulletinService {
	
	/**
	 * 网上公告类别分页
	 * @param  OnlineBulletin   公告类类型
	 * @param  pagination       分页对象
	 * @return Pagination
	 */
	 Pagination finOnlineBulletinServiceData(Pagination pagination,String category);
	
	/**
	 *  网上公告类别添加
	 * @param onlineSurvey  网上调查类别对象
	 * @return String  "1"
 	 */
	 String   addOnlineBulletinService(OnlineBulletin   onlineBulletin); 
	
	 /**
	   *  网上公告类删除
	   * @param id  网上调查类别对象id
	   * @return String "1"
	   */
	   
	 String     deleteOnlineBulletinService(String id);
	 
	 
	 /**
	   *  网上公告类根据id查找相应的实体类
	   * @param id  网上调查类别对象id
	   * @return OnlineBulletin   网上调查类别对象
	   */
	 OnlineBulletin     findOnlineBulletin(String id);
	 
	 /**
	   *  网上公告类根据id查找相应的实体类
	   * @param OnlineBulletin  网上调查类别对象
	   * @return  String   "1" 网上调查类别对象
	   */
	 
	 String modifyOnlineBulletinService(OnlineBulletin onlineBulletin);
	 
	 /**
	  * 发布网上公告目录
	  * @param siteId
	  */
	 void publishBulletin(String siteId);
	 
	 /**
	  * 修改绑定栏目
	  * @param columnIds
	  * @param bulletinId
	  */
	 void modifyBindColumn(String columnIds, String bulletinId);
	  
	 /***
	  * 查找绑定的栏目ids
	  * @param bulletinId
	  * @return
	  */
	 String findBindColumnIds(String bulletinId);
}
