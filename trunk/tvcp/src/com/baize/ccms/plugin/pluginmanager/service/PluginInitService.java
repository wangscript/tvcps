/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.plugin.pluginmanager.service;

import java.io.InputStream;
import java.util.List;

/**
 * 
 * <p>标题: —— 插件管理业务层接口</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 插件管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-9-10 下午02:54:15
 * @history（历次修订内容、修订人、修订时间等）
 */
public interface PluginInitService {

	/**
	 * 创建树的方法
	 * @param treeid          树的节点
	 * @param userId          用户ID
	 * @param siteId          网站ID
	 * @param isUpSystemAdmin 是否是系统管理员
	 * @return List<Object>   返回一个list的对象
	 */
    List<Object> getTreeList(String treeid,String userId ,String siteId,boolean isUpSiteAdmin);
    
    /**
     * 根据用户ID，网站ID查询出右侧页面
     * @param userId 用户ID
     * @param siteId 网站ID
     * @param isUpSystemAdmin 是否是系统管理员
     * @return String 右侧显示的url
     */
    String findRightFrameUrlByUserId(String userId , String siteId,boolean isUpSiteAdmin);
    
  

}
