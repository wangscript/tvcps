/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.plugin.guestbookmanager.service.impl;

import java.util.Date;
import java.util.List;

import com.j2ee.cms.biz.sitemanager.dao.SiteDao;
import com.j2ee.cms.biz.sitemanager.domain.Site;
import com.j2ee.cms.plugin.guestbookmanager.dao.GuestBookSensitiveWordDao;
import com.j2ee.cms.plugin.guestbookmanager.domain.GuestBookSensitiveWord;
import com.j2ee.cms.plugin.guestbookmanager.service.GuestBookService;
import com.j2ee.cms.sys.GlobalConfig;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.util.FileUtil;
import com.j2ee.cms.common.core.util.SqlUtil;

/**
 * <p>
 * 标题: —— 留言板业务具体实现类
 * </p>
 * <p>
 * 描述: —— 简要描述类的职责、实现方式、使用注意事项等
 * </p>
 * <p>
 * 模块: 插件管理
 * </p>
 * <p>
 * 版权: Copyright (c) 2009  
 * 
 * @author 曹名科
 * @version 1.0
 * @since 2009-11-01 下午04:25:58
 * @history（历次修订内容、修订人、修订时间等） 
 */
 

public class GuestBookServiceImpl implements GuestBookService {

	/** 注入JDBC模板 */
	// private JdbcTemplate jdbcTemplate;
	/** 注入敏感词DAO */
	private GuestBookSensitiveWordDao sensitiveWordDao;
	
	/** 注入网站dao */
	private SiteDao siteDao;

	public Pagination getWordPagination(Pagination pa, String siteId) {
		Pagination p = sensitiveWordDao.getPagination(pa, "siteid", siteId);
		return p;
	}

	public String getAllSensitiveWordBySiteId(String siteId) {
		List list = sensitiveWordDao.findByNamedQuery(
				"findSensitiveWordBySiteId", "siteid", siteId);
		String SensitiveWord = "";
		for (int i = 0; i < list.size(); i++) {
			SensitiveWord += "," + list.get(i);
		}
		return SensitiveWord;
	}

	public String modifySensitiveWord(String ids, String sensitiveWord,
			String siteId) {
		String mess = "";
		GuestBookSensitiveWord s = sensitiveWordDao.getAndClear(ids);
		s.setSensitiveWord(sensitiveWord);
		sensitiveWordDao.update(s);
		return mess;
	}

	public void deleteSensitiveWord(String ids) {
		sensitiveWordDao.deleteByIds(SqlUtil.toSqlString(ids));
	}

	public String addSensitiveWord(String word, String siteId) {
		String mess = "";
		GuestBookSensitiveWord sensitiveWord = new GuestBookSensitiveWord();
		sensitiveWord.setSensitiveWord(word);
		sensitiveWord.setCreateTime(new Date());
		Site sites = new Site();
		sites.setId(siteId);
		sensitiveWord.setSites(sites);
		sensitiveWordDao.save(sensitiveWord);
		return mess;
	}

	public GuestBookSensitiveWord getSensitiveWordById(String id) {
		return sensitiveWordDao.getAndClear(id);
	}

	private boolean isExistWord(String siteId, String word) {
		String str[] = { "siteid", "sensitiveWord" };
		String str1[] = { siteId, word };
		List s = sensitiveWordDao.findByNamedQuery("findSensitiveWord", str,
				str1);
	//	System.out.println(s.size() + "    " + word);
		if (s != null && s.size() > 0) {
			for (int i = 0; i < s.size(); i++) {
		//		System.out.println(s.get(i));
				if (word.equals(s.get(i))) {// 存在
				//	System.out.println("存在");
					return true;
				}
			}
		}
		return false;
	}

	/**
	  * 发布留言本目录 
	  * @param siteId
	  */
	public void publishGuestBookDir(String siteId){
		Site site = siteDao.getAndClear(siteId);
		String dir = site.getPublishDir().substring(0, site.getPublishDir().length()-site.getPublishDir().split("/")[site.getPublishDir().split("/").length-1].length());
		String publishDir = dir+"/plugin";
		if(!FileUtil.isExist(publishDir)){
			FileUtil.makeDirs(publishDir);
		}
		String guestbook_manager = GlobalConfig.appRealPath + "/plugin/guestbook_manager";
		if(FileUtil.isExist(guestbook_manager)){
			if(!FileUtil.isExist(publishDir+"/guestbook_manager")){
				 FileUtil.copy(guestbook_manager, publishDir, true);
			 }
		}
		
		// 发布要使用的js和css文件     
		
		//images/commentStyle.css
		String destCss = dir + "/images";
		if(!FileUtil.isExist(destCss)){
			FileUtil.makeDirs(destCss);
		}
		if(!FileUtil.isExist(destCss+"/commentStyle.css")){
			FileUtil.copy(GlobalConfig.appRealPath+"/images/commentStyle.css", destCss);
		}
		// css/style.css
		String destCss1 = dir + "/css";
		if(!FileUtil.isExist(destCss1)){
			FileUtil.makeDirs(destCss1);
		}
		if(!FileUtil.isExist(destCss1+"/style.css")){
			FileUtil.copy(GlobalConfig.appRealPath+"/css/style.css", destCss1);
		}
		//css/ajaxpagination.css
		if(!FileUtil.isExist(destCss1+"/ajaxpagination.css")){
			FileUtil.copy(GlobalConfig.appRealPath+"/css/ajaxpagination.css", destCss1);
		}
		
		// script/jquery-1.2.6.js
		String destJs = dir + "/script";
		if(!FileUtil.isExist(destJs)){
			FileUtil.makeDirs(destJs);
		}
		if(!FileUtil.isExist(destJs+"/jquery-1.2.6.js")){
			FileUtil.copy(GlobalConfig.appRealPath+"/script/jquery-1.2.6.js", destJs);
		}
		
		//script/jquery.pagination.js
		if(!FileUtil.isExist(destJs+"/jquery.pagination.js")){
			FileUtil.copy(GlobalConfig.appRealPath+"/script/jquery.pagination.js", destJs);
		}
		
		// script/My97DatePicker/
		if(!FileUtil.isExist(destJs+"/My97DatePicker")){
			FileUtil.copy(GlobalConfig.appRealPath+"/script/My97DatePicker", destJs);
		}
		
		// /public/images.jsp
		String publicJsp = dir + "/public"; 
		if(!FileUtil.isExist(publicJsp+"/image.jsp")){
			FileUtil.makeDirs(publicJsp);
			FileUtil.copy(GlobalConfig.appRealPath+"/public/image.jsp", publicJsp);
		}
	}
	
	/**
	 * @param jdbcTemplate
	 *            the jdbcTemplate to set
	 */
	// public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
	// this.jdbcTemplate = jdbcTemplate;
	// }
	/**
	 * @param sensitiveWordDao
	 *            the sensitiveWordDao to set
	 */
	public void setSensitiveWordDao(GuestBookSensitiveWordDao sensitiveWordDao) {
		this.sensitiveWordDao = sensitiveWordDao;
	}

	/**
	 * @param siteDao the siteDao to set
	 */
	public void setSiteDao(SiteDao siteDao) {
		this.siteDao = siteDao;
	}

}
