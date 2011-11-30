/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.plugin.rss.service;

import java.util.List;
import java.util.Map;

import com.j2ee.cms.plugin.rss.web.form.RssForm;

/**
 * <p>
 * 标题: —— RSS service
 * </p>
 * <p>
 * 描述: —— RSS 业务层
 * </p>
 * <p>
 * 模块: CPS 插件
 * </p>
 * <p>
 * 版权: Copyright (c) 2009  
 * </p>
 * <p>
 * 网址：http://www.j2ee.cmsweb.com
 * 
 * @author 曹名科
 * @version 1.0
 * @since 2009-10-16 下午03:35:47
 * @history（历次修订内容、修订人、修订时间等） 
 */
 
public interface RssService {
	/**
	 * 保存设置 .
	 * @param rf 设置参数
	 * @param path 路径
	 */
	 void saveRssSet(RssForm rf,String siteId);
	 /**
	  * 获取XML配置 .
	  * @param path 路径
	  * @return rss实体
	  */
	 Map<String,String> readRssXml(String siteId);
	 /**
	  * 判断是多栏目还是单栏目，然后根据设置生成RSS.
	  * @param form RSS设置表单
	  * @param siteId 网站ID
	  */
	 void isColumnsOrMoreColumns(String siteId);
	 /**
	  * 获取RSS列表用于在页面显示.
	  * @param ids
	  * @return
	  */
	 List<String> getRssList(String ids, String siteId);
	 
	 void tt(String siteId);
	  
	 /**
	  * 发布Rss目录 
	  * @param siteId
	  */
	 void publishRssDir(String siteId);
	 
	 String getOutRssList(String siteId, String appName);
}
