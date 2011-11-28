/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.templatemanager.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.baize.ccms.biz.configmanager.dao.SystemLogDao;
import com.baize.ccms.biz.sitemanager.domain.Site;
import com.baize.ccms.biz.templatemanager.dao.TemplateCategoryDao;
import com.baize.ccms.biz.templatemanager.dao.TemplateDao;
import com.baize.ccms.biz.templatemanager.domain.TemplateCategory;
import com.baize.ccms.biz.templatemanager.service.TemplateCategoryService;
import com.baize.ccms.biz.usermanager.domain.User;
import com.baize.common.core.dao.Pagination;
import com.baize.common.core.util.SqlUtil;

/**
 * <p>标题: 模板类别服务类</p>
 * <p>描述: 主要是对模板类别服务的一些功能的具体实现</p>
 * <p>模块: 模板管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 郑荣华
 * @version 1.0
 * @since 2009-4-27 下午06:51:28
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class TemplateCategoryServiceImpl implements TemplateCategoryService {

	private final Logger log = Logger.getLogger(TemplateCategoryServiceImpl.class);
	
	/** 注入模板类别dao **/
	private TemplateCategoryDao templateCategoryDao;
	/** 注入模板dao **/
	private TemplateDao templateDao;
	/**注入日志dao*/
	private SystemLogDao systemLogDao;

	/**	
	 * 删除模板类别
	 * @param ids
	 * @param siteId
	 * @param sessionID
	 * @return
	 */
	public String deleteTemplateCategory(String ids, String siteId, String sessionID) {
		String[] strid = ids.split(",");
		String infoMessage = "";
		for(String templateCategoryId : strid) {
			List list = templateDao.findByNamedQuery("findTemplateByTemplateCategoryId", "templateCategoryId", templateCategoryId);
			if(list != null && list.size() > 0) {
				infoMessage = "删除的模板类别下有模板存在";
				break;
			}
		}
		
		// 写入日志文件
		TemplateCategory templateCategory = null;
		String categoryName = "模板管理（类别管理）->删除";
		if(infoMessage.equals("")) {
			for(int i = 0; i < strid.length; i++) {
				templateCategory = templateCategoryDao.getAndClear(strid[i]);
				String param = templateCategory.getName();
				systemLogDao.addLogData(categoryName, siteId, sessionID, param);
			}
		} else {
			String param = infoMessage;
			systemLogDao.addLogData(categoryName, siteId, sessionID, param);
		}
		
		if(infoMessage.equals("")) {
			ids = SqlUtil.toSqlString(ids);
			templateCategoryDao.deleteByIds(ids);
			infoMessage = "删除成功";
		}
		return infoMessage;
	}

	/**
	 * 修改模板类别
	 * @param templateCategory 模板类别对象
	 */
	public String modifyTemplateCategory(TemplateCategory templateCategory, String siteId, String sessionID) {
		String infoMessage = "";
		String name = templateCategory.getName();
		// 对模板类别的唯一性进行控制
		boolean flag = true;
		String[] params = new String[]{"templateCategoryName", "siteId"};
		Object[] values = new Object[]{name, siteId};
		List list = templateCategoryDao.findByNamedQuery("findTemplateCategoryName", params, values);
		if(list != null && list.size() > 0) {
			flag = false;
		}
		// 存在此模板类别名称
		if(flag) {
			TemplateCategory newTemplateCategory = templateCategoryDao.getAndClear(templateCategory.getId());
			newTemplateCategory.setName(templateCategory.getName());
			templateCategoryDao.update(newTemplateCategory);
	    	infoMessage = "修改模板类别成功";
		} else {
			infoMessage = "修改失败,此模板类别已经存在";
		}
		// 写入日志文件
		String categoryName = "模板管理（类别管理）->修改";
		String param = "[" + templateCategory.getName() + "]" + infoMessage;
		systemLogDao.addLogData(categoryName, siteId, sessionID, param);
		
		return infoMessage;		
	}
	
	/**
	 * 按照模板类别id查找模板类别
	 * @param id  模板类别id
 	 */
	public TemplateCategory findTemplateCategoryByTemplateCategoryId(String id) {
		return templateCategoryDao.getAndClear(id);
	}

	/**
	 * 加载模板类别树
	 * @param treeid					树的id
	 * @param siteid					网站id
	 * @param creatorid					创建者id
	 * @param isUpSystemAdmin
	 * @param isSiteAdmin
	 * @return							返回模板类别列表
	 */
	public List<Object> getTreeList(String treeid, String siteid, String creatorid,
			boolean isUpSystemAdmin, boolean isSiteAdmin) {
		log.debug("获取树的操作");
    	List<Object> list = new ArrayList<Object>();
			
		List<TemplateCategory> templateCategoryList = new ArrayList<TemplateCategory>();
		// 如果是系统管理员以上
		if(isUpSystemAdmin == true) {
//			templateCategoryList = templateCategoryDao.findByNamedQuery("findAllTemplateCategory");
			templateCategoryList = templateCategoryDao.findByNamedQuery("findTemplateCategoryBySiteId", "siteId", siteid);
			
		// 如果是网站管理员	
		} else if(isSiteAdmin == true) {
			templateCategoryList = templateCategoryDao.findByNamedQuery("findTemplateCategoryBySiteId", "siteId", siteid);
			
		// 如果是普通用户	
		} else {
			String[] params = {"siteId", "creatorId"};
	    	Object[] values = {siteid, creatorid};
			templateCategoryList = templateCategoryDao.findByNamedQuery("findTemplateCategoryBySiteIdAndCreatorId", params, values);
		}
		
		// 加载模板类别树
		for(int i = 0; i < templateCategoryList.size(); i++) {
			TemplateCategory tempalteCategory = templateCategoryList.get(i);
			Object[] templateCategoryObject = new Object[4];
			templateCategoryObject[0] = tempalteCategory.getId();
			templateCategoryObject[1] = tempalteCategory.getName();
			templateCategoryObject[2] = "template.do?dealMethod=";
			templateCategoryObject[3] = true;
           	list.add(templateCategoryObject);
		}
		return list;
	}

	/**
	 * 添加模板类别
	 * @param siteId		     网站id
	 * @param creatorid		     用户id
	 * @param templateCategory	 模板类别对象
	 */
	public String addTemplateCategory(String siteid, String creatorid, TemplateCategory templateCategory) {
		String infoMessage = "";
		String templateCategoryName = templateCategory.getName();
		// 对模板类别的唯一性进行控制
		boolean flag = true;
		String[] params = new String[]{"templateCategoryName", "siteId"};
		Object[] values = new Object[]{templateCategoryName, siteid};
		List list = templateCategoryDao.findByNamedQuery("findTemplateCategoryName", params, values);
		if(list != null && list.size() > 0) {
			flag = false;
		}
		// 存在此模板类别名称
		if(flag) {
	    	User creator = new User();
	    	creator.setId(creatorid);
	    	templateCategory.setCreator(creator);
	    	Site site = new Site();
	    	site.setId(siteid);
	    	templateCategory.setSite(site);
	    	templateCategoryDao.save(templateCategory);	
	    	infoMessage = "添加模板类别成功";
		} else {
			infoMessage = "此模板类别已经存在";
		}
		
		// 写入日志文件
		String categoryName = "模板管理（类别管理）->添加";
		String param = "["+ templateCategoryName +"]" + infoMessage;
		systemLogDao.addLogData(categoryName, siteid, creatorid, param);
		
		return infoMessage;
	}

	/**
	 * 模板类别分页
	 * @param pagination             模板类别分页对象
	 * @param siteid				 网站id
	 * @param creatorid				 用户id
	 * @return						 返回模板类别分页对象
	 */
	public Pagination templateCategoryPage(Pagination pagination, String siteid, String creatorid, boolean isUpSystemAdmin, boolean isSiteAdmin) {
		if(isUpSystemAdmin == true) {
			log.debug("系统管理员");
//			return templateCategoryDao.getPagination(pagination);
			return templateCategoryDao.getPagination(pagination, "siteId", siteid);
		} else if(isSiteAdmin == true) {
			log.debug("网站管理员");
			return templateCategoryDao.getPagination(pagination, "siteId", siteid);
		} else {
			log.debug("普通用户");
			String[] params = {"siteId", "creatorId"};
			Object[] values = {siteid, creatorid};
			return templateCategoryDao.getPagination(pagination, params, values);
		}
	}

	/**
	 * @param templateCategoryDao the templateCategoryDao to set
	 */
	public void setTemplateCategoryDao(TemplateCategoryDao templateCategoryDao) {
		this.templateCategoryDao = templateCategoryDao;
	}

	/**
	 * @param templateDao the templateDao to set
	 */
	public void setTemplateDao(TemplateDao templateDao) {
		this.templateDao = templateDao;
	}

	/**
	 * @param systemLogDao the systemLogDao to set
	 */
	public void setSystemLogDao(SystemLogDao systemLogDao) {
		this.systemLogDao = systemLogDao;
	}
	
}
