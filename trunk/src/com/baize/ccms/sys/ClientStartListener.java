/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.sys;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.baize.common.core.util.FileUtil;

/**
 * 
 * <p>标题: —— 启动监听类</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 平台</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-10-31 上午09:03:30
 * @history（历次修订内容、修订人、修订时间等）
 */
 

public class ClientStartListener implements ServletContextListener {

	private Logger log = Logger.getLogger(ClientStartListener.class);

	/** 应用装载器 */
	private Start start = new ApplicationLoader();

	public void contextDestroyed(ServletContextEvent event) {
		// TODO Auto-generated method stub

	}

	public void contextInitialized(ServletContextEvent event) {

		// 初始化数据
		log.info("init all sql");

		// 初始化应用路径
		log.info("init appPath ...");
		start.initAppRealPath(event);

		// 设置系统环境变量
		log.info("set env ...");
		start.setEnv();

		// 初始化日志配置
		log.info("init log config ...");
		start.initLogConfig();

		// 初始化站点信息
		log.info("init site info ...");
				start.initSiteInfo();
 

	/*			// 定义最大连接数
		GlobalConfig.maxConnection = 20;
		log.debug(GlobalConfig.appRealPath);	*/
		
	}

	public void setStart(Start start) {
		this.start = start;
	}

}
