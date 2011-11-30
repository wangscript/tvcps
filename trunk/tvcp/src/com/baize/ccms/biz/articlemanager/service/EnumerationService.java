/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.articlemanager.service;

import com.j2ee.cms.biz.articlemanager.domain.Enumeration;
import com.j2ee.cms.common.core.dao.Pagination;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CPS</p>
 * <p>版权: Copyright (c) 2009  </p>
 * <p>网址：http://www.j2ee.cmsweb.com
 * @author <a href="mailto:sean_yang@163.com">杨信</a>
 * @version 1.0
 * @since 2009-9-2 上午11:28:58
 * @history（历次修订内容、修订人、修订时间等） 
 */
public interface EnumerationService {

	
	/**
	 * 分页显示枚举类别
	 * @return pagination 分页对象
	 */
	Pagination findEnumerationPagination(Pagination pagination);
	
	/**
	 * 保存枚举名称
	 * @param enumerationName 枚举名称
	 * @param creatorId
	 * @param siteId
	 * @return message
	 */
	String saveEnumerationName(String enumerationName, String creatorId, String siteId);
	
	/**
	 * 删除枚举类别
	 * @param ids
	 * @param siteId
	 * @param userId
	 * @return
	 */
	String deleteEnumerationByIds(String ids, String siteId, String userId);
	
	/**
	 * 查找枚举名称
	 * @param enumerationId 枚举类别 id
	 * @return enumerationName
	 */
	String findEnumerationNameById(String enumerationId);
	
	/**
	 * 修改枚举类别
	 * @param enumerationId
	 * @param enumerationName
	 * @param siteId
	 * @param userId
	 * @return
	 */
	String modifyEnumerationName(String enumerationId, String enumerationName, String siteId, String userId);
	
	/**
	 * 添加枚举值
	 * @param enumerationId 枚举id
	 * @param enumValuesStr 枚举值字符串
	 * @param  separator 字符串分隔符
	 * @return message
	 */
	String modifyEnumValues(String enumerationId, String enumValuesStr, String separator);
	
	/**
	 * 添加枚举信息
	 * @param enumerationName 枚举名
	 * @param enumValuesStr 枚举值字符串
	 * @param creatorId 创建者id
	 * @param siteId
	 * @return message
	 */
	String addEnum(String enumerationName, String enumValuesStr, String creatorId, String siteId);
	
	/**
	 * 查找枚举值
	 * @param enumerationId 枚举类别 id
	 * @return enumValuesStr
	 */
	String findEnumValuesStrById(String enumerationId);
	
	/**
	 * 修改枚举信息
	 * @param enumerationName 枚举名
	 * @param enumValuesStr 枚举值字符串
	 * @param enumerationId 枚举id
	 * @param siteId
	 * @param userId
	 * @return message
	 */
	String modifyEnum(String enumerationName, String enumValuesStr, String enumerationId, String siteId, String userId);
	
	/**
	 * 查找所有枚举名称字符串
	 * @return allEnumNameStr
	 */
	String findAllEnumNameStr();
	
	/**
	 * 查找除当前之外所有枚举名称字符串
	 * @param enumerationId 枚举id
	 * @return allEnumNameStr
	 */
	String findAllEnumNameStr(String enumerationId);
	
	/**
	 * 导出枚举
	 * 
	 * @param exportFormatIds
	 *            导出的枚举ids
	 * @param path
	 *            导出枚举的路径
	 * @param siteId
	 * @param userId           
	 * @return message
	 */
	String exportEnumerations(String exportEnumIds, String path, String siteId, String userId);
	
	/**
	 * 导入枚举
	 * 
	 * @param xmlpath
	 *            文件路径
	 * @param siteId
	 * @param creatorId
	 * @return
	 */
	String importEnumerationsXml(String xmlpath, String siteId, String creatorId);
}
