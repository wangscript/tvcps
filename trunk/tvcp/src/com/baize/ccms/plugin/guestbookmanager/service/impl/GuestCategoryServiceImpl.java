/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.plugin.guestbookmanager.service.impl;

import java.util.Date;
import java.util.List;
import com.j2ee.cms.biz.sitemanager.domain.Site;
import com.j2ee.cms.biz.usermanager.dao.UserDao;
import com.j2ee.cms.plugin.guestbookmanager.dao.GuestBookContentDao;
import com.j2ee.cms.plugin.guestbookmanager.dao.GuestCategoryDao;
import com.j2ee.cms.plugin.guestbookmanager.dao.GuestRevertDao;
import com.j2ee.cms.plugin.guestbookmanager.domain.GuestBookCategory;
import com.j2ee.cms.plugin.guestbookmanager.domain.GuestBookContent;
import com.j2ee.cms.plugin.guestbookmanager.domain.GuestBookRevert;
import com.j2ee.cms.plugin.guestbookmanager.service.GuestCategoryService;
import com.j2ee.cms.plugin.guestbookmanager.web.form.GuestBookForm;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.util.DateUtil;
import com.j2ee.cms.common.core.util.SqlUtil;

/**
 * 
 * <p>
 * 标题: —— 留言板类别管理服务类 该类中返回1代表操作成功
 * </p>
 * <p>
 * 描述: —— 简要描述类的职责、实现方式、使用注意事项等
 * </p>
 * <p>
 * 模块: CPS
 * </p>
 * <p>
 * 版权: Copyright (c) 2009  
 * </p>
 * <p>
 * 网址：http://www.j2ee.cmsweb.com
 * 
 * @author 曹名科
 * @version 1.0
 * @since 2009-11-3 下午02:07:03
 * @history（历次修订内容、修订人、修订时间等）
 */
 

public class GuestCategoryServiceImpl implements GuestCategoryService {
	/** 注入类别管理DAO */
	private GuestCategoryDao guestCategoryDao;
	/** 注入留言DAO */
	private GuestBookContentDao guestContentDao;
	/** 注入回复留言DAO */
	private GuestRevertDao revertDao;
	/** 用户DAO */
	private UserDao userDao;

	/**
	 * @param userDao
	 *            the userDao to set
	 */
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * @param guestCategoryDao
	 *            the guestCategoryDao to set
	 */
	public void setGuestCategoryDao(GuestCategoryDao guestCategoryDao) {
		this.guestCategoryDao = guestCategoryDao;
	}

	/**
	 * @param guestContentDao
	 *            the guestContentDao to set
	 */
	public void setGuestContentDao(GuestBookContentDao guestContentDao) {
		this.guestContentDao = guestContentDao;
	}

	/**
	 * @param revertDao
	 *            the revertDao to set
	 */
	public void setRevertDao(GuestRevertDao revertDao) {
		this.revertDao = revertDao;
	}

	public Pagination getPagination(Pagination p, String siteId,
			boolean isManager, String userId) {
		if (isManager) {
			Pagination pa = guestContentDao.getPagination(p, "siteid", siteId);
			return pa;
		} else {
			String str1[] = { "siteid", "userid" };
			String str2[] = { siteId, userId };
			Pagination pa = guestContentDao.getPagination(p, str1, str2);
			return pa;
		}

	}

	public Pagination getPaginationByAudit(Pagination p, String status,
			String siteId, boolean isManager, String userId) {
		if (isManager) {
			String[] str1 = { "auditStatus", "siteid" };
			String[] str2 = { status, siteId };
			Pagination pa = guestContentDao.getPagination(p, str1, str2);
			return pa;
		} else {
			String[] str1 = { "auditStatus", "userid", "siteid" };
			String[] str2 = { status, userId, siteId };
			Pagination pa = guestContentDao.getPagination(p, str1, str2);
			return pa;
		}
	}

	public Pagination getPaginationByReplay(Pagination p, String status,
			String siteId, boolean isManager, String userId) {
		if (isManager) {
			String str1[] = { "replyStatus", "siteid" };
			String str2[] = { status, siteId };
			Pagination pa = guestContentDao.getPagination(p, str1, str2);
			return pa;
		} else {
			String str1[] = { "replyStatus", "userid", "siteid" };
			String str2[] = { status, userId, siteId };
			Pagination pa = guestContentDao.getPagination(p, str1, str2);
			return pa;
		}
	}

	public void deleteGuestContent(String ids) {
		revertDao.updateByDefine("deleteReplyContentById", "contentId", SqlUtil.toSqlString(ids));
		guestContentDao.deleteByIds(SqlUtil.toSqlString(ids));
	}

	public void getGuestBookContentById(GuestBookForm form) {
		GuestBookContent g =new GuestBookContent();
		g=guestContentDao.getAndClear(form.getIds());
		List li = revertDao.findByNamedQuery("findByReplyById","contentId",form.getIds());
		GuestBookRevert reply = new GuestBookRevert();
		if(null!=li&&li.size()>0){
			reply = (GuestBookRevert)li.get(0);
		}
		g.setToParseDate(DateUtil.toString(g.getCreateTime()));//将日期格式化在界面显示
		form.setGuestContent(g);
		reply.setToParseDate(DateUtil.toString(reply.getRevertTime()));
		form.setReply(reply);
		
	}
	public String deleteCategoryById(String ids){
		String mess="";
		String id[]=ids.split(",");
		for (int i = 0; i < id.length; i++) {//findByCategoryId
			List li=guestContentDao.findByNamedQuery("findByCategoryId", "categoryId", id[i]);
			if(li.size()>0){
				mess="选择的类别下含有留言，必须先删除当前类别下的所有留言才能删除该类别";
				return mess;
			}
		}
		deleteCategory(ids);
		return mess;
		
	}
	/**
	 * 删除类别
	 * @param ids
	 */
	private void deleteCategory(String ids) {
		guestCategoryDao.deleteByIds(SqlUtil.toSqlString(ids));
	}

	public GuestBookCategory getCategoryById(String id) {
		return guestCategoryDao.getAndClear(id);
	}

	public String getAllCategoryName(String siteId){
		List li = guestCategoryDao.findByNamedQuery("findCategoryBySiteId","siteId",siteId);
		String category="";
		for (int i = 0; i < li.size(); i++) {
			category+=","+li.get(i);
		}
		return category;
	}
	public String modifyCategory(String ids, GuestBookForm form) {
			GuestBookCategory g = guestCategoryDao.getAndClear(ids);
			g.setCategoryName(form.getGuestCategory().getCategoryName());
			guestCategoryDao.update(g);
			return "";
	}

	public String addCategory(GuestBookForm form, String siteId) {
			GuestBookCategory category = form.getGuestCategory();
			Site site = new Site();
			site.setId(siteId);
			category.setSites(site);
			guestCategoryDao.save(category);
		return "";
	}

	public Pagination getCategoryPagination(Pagination p, String siteId) {
		return guestCategoryDao.getPagination(p, "siteid", siteId);
	}

	/**
	 * 添加留言回复
	 * 
	 * @param revert
	 */
	private String addReplay(String revertContent, String createTime,
			String contentId) {
		try {
			GuestBookRevert revert = new GuestBookRevert();
			GuestBookContent g = new GuestBookContent();
			g.setId(contentId);
			revert.setGuestBookContent(g);
			revert.setRevertContent(revertContent);
			if (createTime != null && !createTime.equals("")) {//处理时间string和date
				revert.setRevertTime(DateUtil.toDate(createTime));
			} else {
				revert.setRevertTime(new Date());
			}
			revertDao.save(revert);
			return "1";
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 修改状态(审核状态，回复状态,回复人)
	 * 
	 * @param id
	 */
	private void modifyGuestBoodContentState(String id, String userId) {
		String str1[] = { "userId", "contentId" };
		String str2[] = { SqlUtil.toSqlString(userId), SqlUtil.toSqlString(id) };
		guestContentDao.updateByDefine("updateStatus", str1, str2);
	}
	
	/**
	 * 修改回复内容
	 * @return
	 */
	private void modifyReplayContent(GuestBookRevert r,GuestBookRevert r1,String contentId){
		GuestBookRevert revert = new GuestBookRevert();
		revert = r1;
		revert.setRevertContent(r.getRevertContent());
	//	System.out.println("修改后的恢复日期："+r.getToParseDate());
		if(null!=r.getToParseDate()&&!r.getToParseDate().equals("")){
			
			revert.setRevertTime(DateUtil.toDate(r.getToParseDate()));
		}else{
			revert.setRevertTime(new Date());
		}
		revertDao.update(revert);
	}
	public String modifyauditAndReplayGuestBookContent(GuestBookForm form,
			String userId) {
		String mess="";
		modifyGuestBoodContentState(form.getIds(), userId);//修改留言回复状态和审核状态,回复人
		List li = revertDao.findByNamedQuery("findByReplyById","contentId",form.getIds());
		GuestBookRevert r = form.getReply();
		if(null!=li&&li.size()>0){//如果当前留言已经回复，则将当前留言的内容更新
			GuestBookRevert r1 =(GuestBookRevert)li.get(0);
			modifyReplayContent(r,r1, form.getIds());
			return "1";
		}else{//如果当前留言未回复，则添加留言回复
		 mess = addReplay(r.getRevertContent(),r.getToParseDate(), form.getIds());//添加留言
			return mess;
		}
	}

	public String modifyauditGuestBookContent(String id) {
		String str1[] = { "auditStatus", "contentId" };
		String str2[] = { "1", SqlUtil.toSqlString(id) };
		int i = guestContentDao.updateByDefine("updateAuditStatus", str1, str2);
		if (i > 0)
			return "1";
		return "";
	}

	public String modifybackAuditGuestBookContent(String id) {
		String str1[] = { "auditStatus", "contentId" };
		String str2[] = { "0", SqlUtil.toSqlString(id) };
		guestContentDao.updateByDefine("updateAuditStatus", str1, str2);
		return "1";
	}

	public String modifynoHandleGuestBookContent(String id) {
		String str1[] = { "auditStatus", "contentId" };
		String str2[] = { "2", SqlUtil.toSqlString(id) };
		int i = guestContentDao.updateByDefine("updateAuditStatus", str1, str2);
		if (i > 0)
			return "1";
		return "";
	}
	private void modifyReplyState(String id,String userId){
		String str1[] = { "userId", "contentId" };
		String str2[] = { SqlUtil.toSqlString(userId), SqlUtil.toSqlString(id) };
		guestContentDao.updateByDefine("updateReplyStatus", str1, str2);
	}
	public String saveGuestBookContent(GuestBookForm form,String userId) {
		String mess="";
		modifyReplyState(form.getIds(),userId);//修改状态
		List li = revertDao.findByNamedQuery("findByReplyById","contentId",form.getIds());
		GuestBookRevert r = form.getReply();
		if(null!=li&&li.size()>0){//如果当前留言已经回复，则将当前留言的内容更新
			GuestBookRevert r1 =(GuestBookRevert)li.get(0);
			modifyReplayContent(r,r1, form.getIds());
			return "1";//回复成功
		}else{//如果当前留言未回复，则添加留言回复
			mess = addReplay(r.getRevertContent(),r.getToParseDate(), form.getIds());//添加留言
			return mess;//回复成功
		}
	}

	public Pagination getAllUser(Pagination pa1) {
		Pagination pa = userDao.getPagination(pa1);
		return pa;
	}

	public String modifyContent(String contentId, String userId) {
		String str1[] = { "userId", "contentId" };
		String str2[] = { SqlUtil.toSqlString(userId),
				SqlUtil.toSqlString(contentId) };
		int i = guestContentDao.updateByDefine("updateReplyUser", str1, str2);
		if (i > 0) {
			return "1";
		}
		return "";
	}
}
