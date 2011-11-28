  /**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.biz.configmanager.service;

import java.util.List;

import com.baize.ccms.biz.configmanager.domain.GeneralSystemSet;
import com.baize.common.core.dao.Pagination;

/**
 * <p>标题: —— 机构业务逻辑处理</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 用户管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-2-16 下午02:59:56
 * @history（历次修订内容、修订人、修订时间等） 
 */
public interface GeneralSystemSetService {
	
	/**
	 * 根据根节点查找作者数据
	 * @param pagination 分页对象
	 * @param nodeid 节点ID
	 * @return Pagination 分页对象
	 */
	Pagination findGeneralSystemSetData(Pagination pagination,String category);
	
	
	/**
	 * 根据根节点添加系统数据
	 * @param GeneralSystemSet 系统设置对象
	 * @param separator 分隔符
	 *  @param categoryId 树节点ID
	 * @return  消息对象 分页对象
	 */
	
	
	public String addgeneralSystemSetService(GeneralSystemSet generalSystemSet,
			String separator, String categoryId, String overDefault,String sessionID,
			String   siteId) ;
		//String addgeneralSystemSetService(GeneralSystemSet  generalSystemSet,String separator,String categoryId,String overDefault);
	
	/**
	 *  根据根节点查找系统数据
	 *  @param   id  系统数据唯一编号  
	 *  @return      系统数据对象
	 */
	GeneralSystemSet findGeneralSystemSetByKey(String id);
	
	/**
	 *  根据根系统数据对象修改属性
	 *  @param   generalSystemSet  系统数据对象
	 */
	
	String modifyGenralSystemSetServic(GeneralSystemSet generalSystemSet,String categoryId);
	
	/**
	 *  根据根节点删除系统数据对象
	 *  @param id  系统数据唯一编号  
	 *  @return     
	 */
	
    void deleteGenralSystemSetServic(String id);
	
    /**
	 *  根据根节添加系统数据对象
	 *  @param categoryId   树节点数据唯一编号  
	 *  @param generalSystemSet   系统数据对象
	 *  @return     
	 */
    
      Boolean addPectureGeneralSystemSetService(  GeneralSystemSet generalSystemSet,String categoryId);
      
      /**
  	 *  根据根节添加图片数据对象
  	 *  @param categoryId   树节点数据唯一编号  
  	 *  @param generalSystemSet   系统数据对象
  	 *  @return     
  	 */
    
  	public String addLinkgeneralSystemSetService(
			               GeneralSystemSet generalSystemSet, String categoryId,String sessionID,
			               String   siteId ) ;
     /**
   	 *  根据根节修改系统设置对象
   	 *  @param generalSystemSet   需要修改的数据对象
   	 *  @param categoryId   节点id         
   	 *  @param  overDefault  默认状态
   	 *  @return     message  消息信息
   	 */
       
 	public String modifyAuthorSystem(GeneralSystemSet generalSystemSet, String  categoryId ,String overDefault) ;
}

	 
