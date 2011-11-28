
package com.house.core.sys;

import com.house.core.util.PropertiesUtil;

/**
 * 
 * <p>标题: 全局配置</p>
 * <p>描述: 存放一些系统的全局配置</p>
 * <p>模块: 通用平台</p>
 * <p>版权: Copyright (c) 2011 江苏集群信息产业股份有限公司
 * @author 娄伟峰
 * @version 1.0
 * @since 2011-4-8 下午02:10:54
 * <p>修订历史:（历次修订内容、修订人、修订时间等） </p>
 */
public final class GlobalConfig {
	
	/** 应用的实际路径() */
	public static String appRealPath;
	
	/** 应用名 */
	public static String appName;
	
	private static PropertiesUtil propertiesUtil = null;
	static {
		String fileName[] = {"globalMessages.properties"};
		propertiesUtil = new PropertiesUtil(fileName);
		
	}
	/**
	 * 
	  * <p>方法说明：获取config.properties文件里面属性值</p> 
	  * <p>创建时间:2011-5-18上午09:38:35</p>
	  * <p>作者: 娄伟峰</p>
	  * @param property
	  * @return String
	 */
	public static String getConfProperty(String property) {
		return propertiesUtil.getProperty(property);
	}

	public static void setAppRealPath(String appRealPath) {
		GlobalConfig.appRealPath = appRealPath;
	}

	public static void setAppName(String appName) {
		GlobalConfig.appName = appName;
	}

}
