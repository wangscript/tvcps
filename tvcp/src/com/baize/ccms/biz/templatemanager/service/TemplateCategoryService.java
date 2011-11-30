/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.templatemanager.service;

import java.util.List;

import com.j2ee.cms.biz.templatemanager.domain.TemplateCategory;
import com.j2ee.cms.common.core.dao.Pagination;

/**
 * <p>标题: 模板类别的服务类</p>
 * <p>描述: 主要是模块中的模板类别的一些功能的实现的调用，这里是接口</p>
 * <p>模块: 模板管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 郑荣华
 * @version 1.0
 * @since 2009-4-27 下午06:50:33
 * @history（历次修订内容、修订人、修订时间等） 
 */
public interface TemplateCategoryService {
	
	/**	
	 * 删除模板类别
	 * @param ids
	 * @param siteId
	 * @param sessionID
	 * @return
	 */
	String deleteTemplateCategory(String ids, String siteId, String sessionID);
	
	/**
	 * 修改模板类别
	 * @param templateCategory
	 * @param siteId
	 * @param sessionID
	 * @return
	 */
	String modifyTemplateCategory(TemplateCategory templateCategory, String siteId, String sessionID);
	
	/**
	 * 按照类别id查找模板类别
	 * @param id   类别id
	 * @return	   返回类别对象
	 */
	TemplateCategory findTemplateCategoryByTemplateCategoryId(String id);
	
	/**
	 * 加载模板类别树
	 * @param treeid					树的id
	 * @param siteid					网站id
	 * @param creatorid					创建者id
	 * @param isUpSystemAdmin			
	 * @param isSiteAdmin				
	 * @return							返回模板类别列表
	 */
	List<Object> getTreeList(String treeid, String siteid, String creatorid, boolean isUpSystemAdmin, boolean isSiteAdmin);
	
	/**
	 * 模板类别分页
	 * @param pagination             模板类别分页对象
	 * @param siteId				 网站id
	 * @param sessionID				 用户id
	 * @return						 返回模板类别分页对象
	 */
	Pagination templateCategoryPage(Pagination pagination, String siteId, String sessionID, boolean isUpSystemAdmin, boolean isSiteAdmin);
	
	/**
	 * 添加模板类别
	 * @param siteId		     网站id
	 * @param sessionID		     用户id
	 * @param templateCategory	 模板类别对象
	 */
	String addTemplateCategory(String siteId, String sessionID, TemplateCategory templateCategory);

}
