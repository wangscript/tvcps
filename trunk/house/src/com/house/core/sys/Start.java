package com.house.core.sys;

import javax.servlet.ServletContextEvent;

/**
 * 
 * <p>标题: 监听启动接口</p>
 * <p>描述: 该接口定义需要在应用启动时做的业务操作.</p>
 * <p>模块: 通用平台</p>
 * <p>版权: Copyright (c) 2011 江苏集群信息产业股份有限公司
 * @author 娄伟峰
 * @version 1.0
 * @since 2011-4-8 下午02:14:27
 * <p>修订历史:（历次修订内容、修订人、修订时间等） </p>
 */
public interface Start {		
	/**
	 * 初始化应用路径.
	 * @param event 上下文事件
	 */
	void initAppRealPath(ServletContextEvent event);
	
	/**
	 * 设置系统环境变量.
	 */
	void setEnv();
}
