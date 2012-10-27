/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.templatemanager.service;

import java.util.List;

import com.j2ee.cms.biz.templatemanager.domain.TemplateInstance;
import com.j2ee.cms.common.core.dao.Pagination;

/**
 * <p>标题: 模板实例接口</p>
 * <p>描述: 负责模板实例的调用</p>
 * <p>模块: 模板管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-4-9 下午03:59:28
 * @history（历次修订内容、修订人、修订时间等）
 */
public interface TemplateInstanceService {

	/**
	 * 修改模板实例
	 * @param templateInstance 要修改的模板实例
	 * @param siteId
	 * @param sessionID
	 * @return
	 */
	String modifyTemplateInstance(TemplateInstance templateInstance, String siteId, String sessionID);

	/**
	 * 按照模板id查找模板实例
	 * @param templateid 模板id
	 * @return			 返回模板实例列表
	 */
	List<TemplateInstance> findTemplateInstanceByTemplateId(String templateid, String siteId, String sessionID, boolean isUpSystemAdmin, boolean isSiteAdmin);
	
	/**
	 * 模板实例分页
	 * @param pagination 分页对象
	 * @param templateId 模板id
	 * @return           返回模板实例分页对象
	 */
	Pagination templateInstancePagination(Pagination pagination, String templateId, String siteId, String sessionID, boolean isUpSystemAdmin, boolean isSiteAdmin);
	
	/**
	 * 处理模板实例的添加
	 * @param templateInstance  		模板实例
	 * @param templateId				模板id
	 * @param siteId					网站id
	 * @param url						当前项目的访问地址
	 * @param sessionID					用户的id
	 */
	String addTemplateInstance(TemplateInstance templateInstance, String templateId, String siteId, StringBuffer url, String sessionID);
	
	/**
	 * 处理模板实例的删除
	 * @param ids    要删除的模板实例的ids
	 * @param siteId 网站id
	 * @param sessionID
	 * @return		 返回是否删除成功
	 */
	String deleteTemplateInstance(String ids, String siteId, String sessionID);
	
	/**
	 * 按照模板实例id查找模板实例
	 * @param templateInstanceId 模板实例id
	 * @return                   返回模板实例对象
	 */
	TemplateInstance findTemplateInstanceById(String templateInstanceId);
	
	/***
	 * 查找被绑定的栏目(或者文章的栏目)ids
	 * @param bind
	 * @param templateInstanceId
	 * @param siteId
	 * @return
	 */
	String findBindedColumnIds(String bind, String templateInstanceId, String siteId);
	
	/**
	 * 绑定栏目或者文章
	 * @param bind
	 * @param bindedIds
	 * @param templateInstanceId
	 * @param canceledIds
	 */
	void modifyBindColumnOrArticle(String bind, String bindedIds, String templateInstanceId, String canceledIds);
	
	/**
	 * 取消绑定栏目或者文章
	 * @param bind
	 * @param bindedIds
	 * @param templateInstanceId
	 */
	void cancelBindColumnOrArticle(String bind, String bindedIds, String templateInstanceId);
}
