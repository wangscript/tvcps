/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.documentmanager.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.j2ee.cms.biz.configmanager.dao.SystemLogDao;
import com.j2ee.cms.biz.documentmanager.dao.CategoryDao;
import com.j2ee.cms.biz.documentmanager.dao.DocumentDao;
import com.j2ee.cms.biz.documentmanager.domain.DocumentCategory;
import com.j2ee.cms.biz.documentmanager.service.CategoryService;
import com.j2ee.cms.biz.usermanager.dao.UserDao;
import com.j2ee.cms.biz.usermanager.domain.User;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.util.SqlUtil;
import com.j2ee.cms.common.core.util.StringUtil;


/**
 * <p>标题: 类别的service实现</p>
 * <p>描述: 类别的一些实现，通过类别dao的方法</p>
 * <p>模块: 文档管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 郑荣华
 * @version 1.0
 * @since 2009-3-26 上午10:57:20
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class CategoryServiceImpl implements CategoryService {

	private final Logger log = Logger.getLogger(CategoryServiceImpl.class);
	
	/**注入类别的dao**/
	private CategoryDao categoryDao;
	/**注入文档的dao**/
	private DocumentDao documentDao;
	/**注入日志dao*/
	private SystemLogDao systemLogDao;
	/** 用户dao */
	private UserDao userDao;
	
	/**
	 * 添加数据
	 * @param category
	 * @param siteId
	 * @param sessionID
	 * @param categoryName
	 */
	public void addCategory(DocumentCategory category, String siteId, String sessionID, String categoryName) {
		categoryDao.save(category);
		
		// 写入日志
		String param = category.getName();
		systemLogDao.addLogData(categoryName, siteId, sessionID, param);
	}

	/**
	 * 删除数据
	 * id 要删除的类别的id
	 */
	public void deleteCategory(String id) {
		categoryDao.deleteByKey(id);
	}
	
	/**
	 * 修改数据
	 * category 要修改的类别对象
	 */
	public void modifyCategory(DocumentCategory category, String siteId, String sessionID, String categoryName) {
        categoryDao.update(category);
       
        // 写入日志
		String param = category.getName();
		systemLogDao.addLogData(categoryName, siteId, sessionID, param);
	}
	
	/**
	 * 查找图片类别数据并返回Category
	 * @param siteid   网站id
	 * @return list    返回一个图片类别对象
	 */
	public List<DocumentCategory> findCategoryPictureData(String siteid) {
		return categoryDao.findByNamedQuery("findPictureCategoryBySiteId", "siteId", siteid);
	}

	/**
	 * 查找flash类别数据并返回Category
	 * @param siteid   网站id
	 * @return list    返回一个flash类别对象
	 */
	public List<DocumentCategory> findCategoryFlashData(String siteid){
		return categoryDao.findByNamedQuery("findFlashCategoryBySiteId", "siteId", siteid);
	}
	
	/**
	 * 查找附件类别数据并返回Category
	 * @param siteid   网站id
	 * @return list    返回一个附件类别对象
	 */
	public List<DocumentCategory> findCategoryAttachmentData(String siteid){
		return categoryDao.findByNamedQuery("findAttachmentCategoryBySiteId", "siteId", siteid);
	}
	
	/**
	 * 查找js脚本类别数据并返回Category
	 * @param siteid   网站id
	 * @return list    返回一个js脚本类别对象
	 */
	public List<DocumentCategory> findCategoryJsData(String siteid){
		return categoryDao.findByNamedQuery("findJsCategoryBySiteId", "siteId", siteid);
	}
	
	
	/**
	 * 分页显示类别数据
	 * @param pagination 分页对象
	 * @return pagination 返回一个分页对象
	 */
	public DocumentCategory findCategoryById(String id) {
		return categoryDao.getAndClear(id);
	}
	
	/**
	 * 根据栏目id获取类别数据
	 * @param id         要查询的文档的id
	 * @return category  返回一个类别对象
	 */
	public Pagination findCategoryData(Pagination pagination){
		return categoryDao.getPagination(pagination);
	}
	
	/**
	 *   查询指定用户的类别信息并分页显示
	 * @param creatorId 要查询的用户的id
	 * @param value     设置用户的id值
	 * @return          返回一个分页对象
	 */
	public Pagination findCategoryByCreatorId(Pagination pagination, String id, Object value){
		return categoryDao.getPagination(pagination, id, value);
	}
	
	/**
	 * 创建树的方法
	 * @param treeid          树的节点
	 * @param categoryService 传递一个categoryService对象,以便调用当中的一些方法
	 * @param siteid          网站id
	 * @param userId          用户ID
	 * @return object         返回一个list的对象
	 */
    public List<Object> getTreeList(String treeid, CategoryService categoryService, String siteid,String userId,boolean isUpSiteAdmin){
    	//获取当前登录用户的对象
    	User user = userDao.getAndNonClear(userId);
    	String menuIds = user.getMenuIds();
    	String menuFunctionIds = "";
    	if(isUpSiteAdmin){
    		menuFunctionIds = "f004,f005,f006,f007";
    	}else{
    		if(menuIds != null && StringUtil.contains(menuIds, "*")){
    			//如果有多个网站的设置
    			String strMoreMenuIds[] = menuIds.split("[*]");
    			for(int i = 0 ; i < strMoreMenuIds.length ; i++){
    				if(StringUtil.contains(strMoreMenuIds[i],siteid)){   	      
    	        		menuFunctionIds = strMoreMenuIds[i];    	        			 
    	        	}
    			}
        	}else if(menuIds != null){
        		//如果只有一个网站的设置
        		if(StringUtil.contains(menuIds,siteid)){
    				menuFunctionIds = menuIds;
    			}
        	}
    	}
    	
    	log.debug("获取树的操作");
    	List<Object> list = new ArrayList<Object>();
    	// 加载第一级
		if (StringUtil.isEmpty(treeid) || treeid.equals("0")) { 
			//添加图片类别的树
			Object[] objects =  new Object[4];
			objects[0] = "f004";
			objects[1] = "图片类别";
			objects[2] = "category.do?dealMethod=";
			objects[3] = false;
			if(StringUtil.contains(menuFunctionIds, "f004")){
				list.add(objects);
			}
			
			//添加flash类别的树
			objects =  new Object[4];
			objects[0] = "f005";
			objects[1] = "Flash类别";
			objects[2] = "category.do?dealMethod=";
			objects[3] = false;
			if(StringUtil.contains(menuFunctionIds, "f005")){
				list.add(objects);
			}
		 
			//添加附件类别的树
			objects =  new Object[4];
			objects[0] = "f006";
			objects[1] = "附件类别";
			objects[2] = "category.do?dealMethod=";
			objects[3] = false;
			if(StringUtil.contains(menuFunctionIds, "f006")){
				list.add(objects);
			}
			
			//添加js脚本类别的树
			objects =  new Object[4];
			objects[0] = "f007";
			objects[1] = "js脚本类别";
			objects[2] = "category.do?dealMethod=";
			objects[3] = false;
			if(StringUtil.contains(menuFunctionIds, "f007")){
				list.add(objects);
			}

		}
		// 加载第二级（图片类别）
		if (!StringUtil.isEmpty(treeid) && treeid.equals("f004")) {
			List<DocumentCategory> pictureList = new ArrayList<DocumentCategory>();
			//查找数据库获得所有的图片信息
			pictureList = categoryService.findCategoryPictureData(siteid);
            //将查找到的所有图片信息添加到树中
			for(int i = 0; i < pictureList.size(); i++){
            	DocumentCategory categorypicture = pictureList.get(i);
            	Object[] picObj = new Object[4];
            	picObj[0] = categorypicture.getId();
            	picObj[1] = categorypicture.getName();
            	picObj[2] = "picture.do?dealMethod=";
            	picObj[3] = true;
            	list.add(picObj);
            }
		}
		// 加载第二级（flash类别）
		if (!StringUtil.isEmpty(treeid) && treeid.equals("f005")) {
			List<DocumentCategory> flashList = new ArrayList<DocumentCategory>();
			//查找数据库获得所有的flash信息
			flashList = categoryService.findCategoryFlashData(siteid);
			 //将查找到的所有flash信息添加到树中
            for(int i = 0; i < flashList.size(); i++){
            	DocumentCategory categoryflash = flashList.get(i);
            	Object[] flashObj = new Object[4];
            	flashObj[0] = categoryflash.getId();
            	flashObj[1] = categoryflash.getName();
            	flashObj[2] = "flash.do?dealMethod=";
            	flashObj[3] = true;
            	list.add(flashObj);
            }
		}
		// 加载第二级（附件类别）
		if (!StringUtil.isEmpty(treeid) && treeid.equals("f006")) {
			List<DocumentCategory> attachmentList = new ArrayList<DocumentCategory>();
			//查找数据库获得所有的附件信息
			attachmentList = categoryService.findCategoryAttachmentData(siteid);
			 //将查找到的所有附件信息添加到树中
            for(int i = 0; i < attachmentList.size(); i++){
            	DocumentCategory categoryattachment = attachmentList.get(i);
            	Object[] attachmentObje = new Object[4];
            	attachmentObje[0] = categoryattachment.getId();
            	attachmentObje[1] = categoryattachment.getName();
            	attachmentObje[2] = "attachment.do?dealMethod=";
            	attachmentObje[3] = true;
            	list.add(attachmentObje);
            }
		}

		//加载第二级（js脚本类别）
		if (!StringUtil.isEmpty(treeid) && treeid.equals("f007")) {
			List<DocumentCategory> jsList = new ArrayList<DocumentCategory>();
			//查找数据库获得所有的附件信息
			jsList =  categoryService.findCategoryJsData(siteid);
			 //将查找到的所有附件信息添加到树中
		    for(int i = 0; i < jsList.size(); i++){
		    	DocumentCategory categoryattachment = jsList.get(i);
		    	Object[] jsObje = new Object[4];
		    	jsObje[0] = categoryattachment.getId();
		    	jsObje[1] = categoryattachment.getName();
		    	jsObje[2] = "js.do?dealMethod=";
		    	jsObje[3] = true;
		    	list.add(jsObje);
		    }
		}
		return list;
    }

    /**
	 * 处理删除
	 * @param ids  要删除的类别的ids
	 * @return     返回是否删除成功
	 */
    public String deleteCategoryByIds(String ids, String siteId, String sessionID, String categoryName) {
		log.debug("处理类别的删除操作");
		String infoMessage = "";
		// 判断要删除的图片类别下是否有图片       
		String strid[] = ids.split(",");
		for(int i = 0 ; i < strid.length; i++) {
			String id = strid[i];
			List list = documentDao.findByNamedQuery("dcoumentCount", "categoryid" , id);    
			long count = list.size();
			count = (Long) list.get(0);
			int c = (int) count;
			if(c > 0) {
				infoMessage = "要删除类别前必须先删除类别下的内容";
				return infoMessage;
			}
		}
		
		// 写入日志
		String[] str = ids.split(",");
		DocumentCategory category = null;
		for(int i = 0; i < str.length; i++) {
			category = categoryDao.getAndClear(str[i]);
			String param = category.getName();
			systemLogDao.addLogData(categoryName, siteId, sessionID, param);
		}
		
		ids = SqlUtil.toSqlString(ids);
		categoryDao.deleteByIds(ids);
		infoMessage = "删除成功";
		return infoMessage;
	}

    /**
	 * 查找所有类别名称
	 * @param nodeid  类别标记
	 * @return  category 名称字符串
	 */
    @SuppressWarnings("unchecked")
	public String findCategoryName(String nodeid, String siteId) {
    	List list = new ArrayList();
    	// 获取图片类别
    	if(nodeid.equals("f004")){	
    		list = categoryDao.findByNamedQuery("findPictureCategoryNameBySiteId", "siteId", siteId); 
			
		// 获取flash类别
		}else  if(nodeid.equals("f005")){
			list = categoryDao.findByNamedQuery("findFlashCategoryNameBySiteId", "siteId", siteId); 
		
		// 获取附件类别
		} else if(nodeid.equals("f006")){
			list = categoryDao.findByNamedQuery("findAttachmentCategoryNameBySiteId", "siteId", siteId); 
			
		// 获取js脚本类别
		} else if(nodeid.equals("f007")){
			list = categoryDao.findByNamedQuery("findJsCategoryNameBySiteId", "siteId", siteId); 
			
		}
    	String categoryName = "";
    	for(int i = 0; i < list.size(); i++) {
    		categoryName += "," + String.valueOf(list.get(i));
    	}
    	return categoryName;
    }
    
    
    /**
     * @param documentDao 设置文档的dao
     */
	public void setDocumentDao(DocumentDao documentDao) {
		this.documentDao = documentDao;
	}
	
	/**
	 * @param categoryDao  设置类别的dao
	 */
	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	/**
	 * @param systemLogDao the systemLogDao to set
	 */
	public void setSystemLogDao(SystemLogDao systemLogDao) {
		this.systemLogDao = systemLogDao;
	}

	/**
	 * @param userDao the userDao to set
	 */
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}
