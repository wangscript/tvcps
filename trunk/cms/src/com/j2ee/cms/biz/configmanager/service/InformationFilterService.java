  /**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.configmanager.service;

import com.j2ee.cms.biz.configmanager.domain.InformationFilter;
import com.j2ee.cms.common.core.dao.Pagination;

/**
 * <p>标题: —— 机构业务逻辑处理</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 用户管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 包坤涛
 * @version 1.0
 * @since 2009-2-16 下午02:59:56
 * @history（历次修订内容、修订人、修订时间等） 
 */
public interface InformationFilterService {
	
 
	
	/**
	 * 根据根节点查找作者数据
	 * @param pagination 分页对象
	 * @return Pagination 分页对象
	 */
	Pagination findInformationFilterData(Pagination pagination);
   
	
	
	/**
	 * 根据根节点查找作者数据
	 * @param pagination 分页对象
	 * @param sessionID  用户类型
	 * @param siteId  栏目类型 
	 * @return Pagination 分页对象
	 */
	 Pagination findInformationSessionIdFilterData(Pagination pagination, String sessionID,String siteId) ;
	
	
	
	/**
	 * 根据更具过滤要求填写过滤内容
	 * @param informationFilter 过滤对象
	 * @param categoryId 节点ID
	 * @return Message  消息对象
	 */
	 String  addinformationFilterService(InformationFilter informationFilter ,String siteId,String sessionID);
	
	 
	
    
    void  deleteInformationFilterService(String  authorId );
    	
    
    void  modifyInformationFilterServic(InformationFilter  informationFilter);
      InformationFilter  findInformationFilterByKey(String  authorId);
    
}

	 
