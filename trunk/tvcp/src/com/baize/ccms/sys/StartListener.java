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
 * <p>
 * 标题: —— 要求能简洁地表达出类的功能和职责
 * </p>
 * <p>
 * 描述: —— 简要描述类的职责、实现方式、使用注意事项等
 * </p>
 * <p>
 * 模块: 通用平台
 * </p>
 * <p>
 * 版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * 
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since Feb 12, 2009 7:58:28 PM
 * @history（历次修订内容、修订人、修订时间等） 
 */
 

public class StartListener implements ServletContextListener {

	private Logger log = Logger.getLogger(StartListener.class);

	/** 应用装载器 */
	private Start start = new ApplicationLoader();

	public void contextDestroyed(ServletContextEvent event) {
		// TODO Auto-generated method stub

	}

	public void contextInitialized(ServletContextEvent event) {

		// 初始化数据
		log.info("init all sql");
		if (!start.checkDate(event)) {//检查验证码是否有效，無效的話就不執行了
			// 先创建表，
			if (start.createTable(event)) {
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

				// 初始化格式字段
				// log.info("init article fields ...");
				// start.initFormatFields();

				// 定义最大连接数
				GlobalConfig.maxConnection = 20;
				log.debug(GlobalConfig.appRealPath);
			}else{
				System.out.println("数据初始化失败，程序将退出");
				System.exit(0);
			}
		}else{
			System.out.println("注册失败，注册信息不正确，程序将退出");
		//	System.exit(0);
		}
		
	//	FileUtil.zipDirectory(GlobalConfig.appRealPath + "/WEB-INF", SiteResource.getTempDir(false)+"/WEB-INF.zip");
	}

	public void setStart(Start start) {
		this.start = start;
	}

}
