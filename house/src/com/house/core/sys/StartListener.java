package com.house.core.sys;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

/**
 * 
 * <p>标题: —— 启动监听器</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 通用平台</p>
 * <p>版权: Copyright (c) 2011 江苏集群信息产业股份有限公司
 * @author 娄伟峰
 * @version 1.0
 * @since 2011-4-8 下午02:12:35
 * <p>修订历史:（历次修订内容、修订人、修订时间等） </p>
 */
public class StartListener implements ServletContextListener{
	/**
	 * 日志
	 */
	private Logger log = Logger.getLogger(StartListener.class);

	/** 应用装载器 */
	private Start start = new ApplicationLoader();

	public void contextDestroyed(ServletContextEvent event) {
		// TODO Auto-generated method stub

	}

	public void contextInitialized(ServletContextEvent event) {  
		// 初始化应用路径
		log.info("init appPath ...");
		start.initAppRealPath(event);

		// 设置系统环境变量
		log.info("set env ...");
		start.setEnv();
 	}

	public void setStart(Start start) {
		this.start = start;
	}
}
