/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.plugin.onlineBulletin.service.impl;

import java.util.Date;
import java.util.regex.Pattern;

import com.j2ee.cms.biz.sitemanager.dao.SiteDao;
import com.j2ee.cms.biz.sitemanager.domain.Site;
import com.j2ee.cms.plugin.onlineBulletin.dao.OnlineBulletinDao;
import com.j2ee.cms.plugin.onlineBulletin.domain.OnlineBulletin;
import com.j2ee.cms.plugin.onlineBulletin.service.OnlineBulletinService;
import com.j2ee.cms.sys.GlobalConfig;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.util.FileUtil;
import com.j2ee.cms.common.core.util.SqlUtil;
import com.j2ee.cms.common.core.util.StringUtil;

/**
 * <p>
 * 标题: 网上公告业务类
 * </p>
 * <p>
 * 描述: 网上公告的内容，等业务
 * </p>
 * <p>
 * 模块: 网上公告
 * </p>
 * <p>
 * 版权: Copyright (c) 2009 
 * 
 * @author 包坤涛
 * @version 1.0
 * @since 2009-6-14 下午02:30:23
 * @history（历次修订内容、修订人、修订时间等） 
*/
 

public class OnlineBulletinServiceImpl implements OnlineBulletinService {
	/** 注入网上调查问题dao */
	private OnlineBulletinDao onlineBulletinDao;
	
	/** 注入网站dao */
	private SiteDao siteDao;

	// 网上调查问题具体内容表注入

	// 单选框或多选框的值

	// 查找网上公告类别分页
	
	/**
	 * 网上公告类别分页
	 * @param  OnlineBulletin   公告类类型
	 * @param  pagination       分页对象
	 * @return Pagination
	 */
	public Pagination finOnlineBulletinServiceData(Pagination pagination,
			String siteID) {
		if (siteID != null && !siteID.equals("")) {
			return onlineBulletinDao
					.getPagination(pagination, "siteID", siteID);
		} else {
			return new Pagination();
		}
	}

	

	/**
	 *  网上公告类别添加
	 * @param onlineSurvey  网上调查类别对象
	 * @return String  "1"
 	 */
	public String addOnlineBulletinService(OnlineBulletin onlineBulletin) {
		onlineBulletin.setCreateTime(new Date());
		String content = onlineBulletin.getContext();
		if(!StringUtil.isEmpty(content)){
			content = content.replaceAll("/"+GlobalConfig.appName+"/release/site"+onlineBulletin.getSite().getId(), "");
			onlineBulletin.setContext(content);
		}
		onlineBulletinDao.save(onlineBulletin);
		return "1";

	}
   
	/**
	   *  网上公告类删除
	   * @param id  网上调查类别对象id
	   * @return String "1"
	   */
	   
	public String deleteOnlineBulletinService(String id) {
		onlineBulletinDao.deleteByIds(SqlUtil.toSqlString(id));
		return "1";
	}
	
	
	 /**
	   *  网上公告类根据id查找相应的实体类
	   * @param id  网上调查类别对象id
	   * @return OnlineBulletin   网上调查类别对象
	   */
	public OnlineBulletin findOnlineBulletin(String id) {
		return 	onlineBulletinDao.getAndClear(id);
		
	}
   
	/**
	 *  网上公告类根据id查找相应的实体类
	 *  并对查找出来的对象进行修改
	 * @param onlineBulletin  网上调查类别对象
	 * @return String   "1"
	 * 
	 * 
	 * 
	 */
	public String modifyOnlineBulletinService(OnlineBulletin onlineBulletin) {
		//调用OnlineBulletinServiceImpl中的findOnlineBulletin方法
		OnlineBulletin onlineBulletinEntitly=findOnlineBulletin(onlineBulletin.getId());
		//公告标题
		onlineBulletinEntitly.setName(onlineBulletin.getName());
		//公告内容
		String content = onlineBulletin.getContext();
		if(!StringUtil.isEmpty(content)){
			content = content.replaceAll("/"+GlobalConfig.appName+"/release/site"+onlineBulletinEntitly.getSite().getId(), "");
		}
		onlineBulletinEntitly.setContext(content);
		//截止日期
		onlineBulletinEntitly.setEndTime(onlineBulletin.getEndTime());
		//窗口名称
		onlineBulletinEntitly.setWindowName(onlineBulletin.getWindowName());
		//窗口大小(高度)
		onlineBulletinEntitly.setWidowHeight(onlineBulletin.getWidowHeight());
		//窗口大小(宽度)
		onlineBulletinEntitly.setWidowWidth(onlineBulletin.getWidowWidth());
		//窗口上下边界(上)
		onlineBulletinEntitly.setWindowTop(onlineBulletin.getWindowTop());
		//窗口上下边界(左)
		onlineBulletinEntitly.setWindowLeft(onlineBulletin.getWindowLeft());
		//显示工具栏
		onlineBulletinEntitly.setShowTool(onlineBulletin.isShowTool());
		//显示菜单栏
		onlineBulletinEntitly.setShowMenu(onlineBulletin.isShowMenu());
		//显示滚动条
		onlineBulletinEntitly.setShowScroll(onlineBulletin.isShowScroll());
		//可改变窗口大小
		onlineBulletinEntitly.setChangeSize(onlineBulletin.isChangeSize());
		//显示地址栏
		onlineBulletinEntitly.setShowAddress(onlineBulletin.isShowAddress());
		//显示状态信息
	    onlineBulletinEntitly.setShowStatus(onlineBulletin.isShowStatus());
	    
		onlineBulletinDao.update(onlineBulletinEntitly);
		return "1";
	}
	
	/**
	  * 修改绑定栏目
	  * @param columnIds
	  * @param bulletinId
	  */
	public void modifyBindColumn(String columnIds, String bulletinId){
		OnlineBulletin onlineBulletin = onlineBulletinDao.getAndClear(bulletinId);
		onlineBulletin.setColumnIds(columnIds);
		onlineBulletinDao.update(onlineBulletin);
	}
	
	/***
	  * 查找绑定的栏目ids
	  * @param bulletinId
	  * @return
	  */
	 public String findBindColumnIds(String bulletinId){
		 OnlineBulletin onlineBulletin = onlineBulletinDao.getAndClear(bulletinId);
		 if(!StringUtil.isEmpty(onlineBulletin.getColumnIds())){
			 return onlineBulletin.getColumnIds();
		 }else{
			 return "";
		 }
	 }
	 
	/**
	  * 发布网上公告目录
	  * @param siteId
	  */
	public void publishBulletin(String siteId){
		Site site = siteDao.getAndClear(siteId);
		String dir = site.getPublishDir().substring(0, site.getPublishDir().length()-site.getPublishDir().split("/")[site.getPublishDir().split("/").length-1].length());
		String publishDir = dir+"/plugin";
		if(!FileUtil.isExist(publishDir)){
			FileUtil.makeDirs(publishDir);
		}
		String guestBookDir = GlobalConfig.appRealPath + "/plugin/onlineBulletin_manager";
		if(FileUtil.isExist(guestBookDir)){
			if(!FileUtil.isExist(publishDir+"/onlineBulletin_manager")){
				FileUtil.copy(guestBookDir, publishDir, true);
			}else{
				FileUtil.copy(guestBookDir, publishDir+"/onlineBulletin_manager", false);
			}
		}
	}
	
	/**
	 * @return the onlineBulletinDao
	 */
	public OnlineBulletinDao getOnlineBulletinDao() {
		return onlineBulletinDao;
	}

	/**
	 * @param onlineBulletinDao
	 *            the onlineBulletinDao to set
	 */
	public void setOnlineBulletinDao(OnlineBulletinDao onlineBulletinDao) {
		this.onlineBulletinDao = onlineBulletinDao;
	}



	/**
	 * @param siteDao the siteDao to set
	 */
	public void setSiteDao(SiteDao siteDao) {
		this.siteDao = siteDao;
	}
}