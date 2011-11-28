
package com.house.core.sys;

import javax.servlet.ServletContextEvent;

import org.apache.log4j.Logger;

 
/**
 * 
 * 标题: 应用装载器 </p>
 * <p>
 * 描述: 放置一些应用启动期间需要执行的一些任务
 * <p>模块: 通用平台</p>
 * <p>版权: Copyright (c) 2011 江苏集群信息产业股份有限公司
 * @author 娄伟峰
 * @version 1.0
 * @since 2011-4-8 下午02:13:45
 * <p>修订历史:（历次修订内容、修订人、修订时间等） </p>
 */

public class ApplicationLoader implements Start {
	/**
	 * 日志
	 */
	private final Logger log = Logger.getLogger(ApplicationLoader.class);

	/**
	 * @param event   SererletContextEvent事件
	 */
	public void initAppRealPath(final ServletContextEvent event) {
		// 初始化全局配置
		GlobalConfig.setAppRealPath(event.getServletContext().getRealPath("")
				.replaceAll("\\\\", "/"));
		log.info("appRealPath : "+GlobalConfig.appRealPath);
		
		String str[] = GlobalConfig.appRealPath.split("/");
		GlobalConfig.setAppName(str[str != null ? str.length-1 : 0]);
		log.info("appName : "+GlobalConfig.appName);
	}
	 
	/**
	 * 设置环境变量
	 */
	public void setEnv() {
		System.setProperty("mail.webapp.root", GlobalConfig.appRealPath);
	}
	 
}
