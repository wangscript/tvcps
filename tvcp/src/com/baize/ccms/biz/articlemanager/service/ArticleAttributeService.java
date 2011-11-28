/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.biz.articlemanager.service;

import java.util.List;

import com.baize.ccms.biz.articlemanager.domain.ArticleAttribute;
import com.baize.ccms.biz.configmanager.domain.GeneralSystemSet;
import com.baize.common.core.dao.Pagination;

/**
 * <p>标题: 属性服务类</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 文章管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-3-30 下午04:32:21
 * @history（历次修订内容、修订人、修订时间等）
 */
public interface ArticleAttributeService {
	
	/**
	 * 添加属性
	 * @param attribute
	 * @param siteId
	 * @param sessionID
	 * @return
	 */
	String addAttribute(ArticleAttribute attribute, String siteId, String sessionID);
	
	/**
	 * 删除属性
	 * @param attribute
	 * @param siteId
	 * @param sessionID
	 */
	void deleteAttribute(ArticleAttribute attribute, String siteId, String sessionID);
	
	/**
	 * 修改属性
	 * @param attribute
	 * @param siteId
	 * @param sessionID
	 * @return
	 */
	String modifyAttribute(ArticleAttribute attribute, String siteId, String sessionID);
	
	/**
	 * 通过ID查找属性
	 * @param id
	 * @return
	 */
	ArticleAttribute findAttributeById(String id);
	
	/**
	 * 查找文章属性分页
	 * @param pagination
	 * @param formatId 格式ID
	 * @return
	 */
	Pagination findAttributePage(Pagination pagination, String formatId);
	
	/** 查询格式名称
	 * @param formatId 格式Id
	 */
	String findFormatName(String formatId);
	
	/** 查询枚举信息
	 * return enumInfoStr
	 */
	String findEnumInfo();
	
	/**
	 * 查找所有的属性名称
	 * @param formatId 格式id
	 * @return
	 */
	String findAllAttributeNameStr(String formatId);
	
	/**
	 * 根据格式id查找所有的属性信息
	 * @param formatId  格式id
	 * @return
	 */
	String findAttributeInfoStr(String formatId);
	
	/**
	 * 排序
	 * @param attributeIdStr 属性ids
	 * @return
	 */
	String modifyAttributeSort(String attributeIdStr);
	
	/**
	 * 批量删除属性
	 * @param ids 属性id
	 * @return
	 */
	String deleteAttributesByIds(String ids);
	
	/**
	 * 通过属性名称查找属性id 
	 * @param formatId 格式id
	 * @return 
	 */
	String findDefaultAttrIdsByName(String formatId);
}
