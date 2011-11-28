/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.sys;

import javax.servlet.ServletContextEvent;

/**
 * <p>标题: 监听启动接口</p>
 * <p>描述: 该接口定义需要在应用启动时做的业务操作.</p>
 * <p>模块: 通用平台</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since Feb 12, 2009 7:59:54 PM
 * @history（历次修订内容、修订人、修订时间等）
 */
public interface Start {

	/**
	 * 创建表.
	 * 
	 * @param path
	 *            路径
	 * @return 是否创建成功
	 */
	boolean createTable(final ServletContextEvent event);
	
	/**
	 * 检查是否正确，验证码，日期是否过期
	 * @param event
	 */
	 boolean checkDate(final ServletContextEvent event); 
	
	/**
	 * 初始化应用路径.
	 * @param event 上下文事件
	 */
void initAppRealPath(ServletContextEvent event);

/**
 * 初始化网站信息
 */
void initSiteInfo();

/**
 * 设置系统环境变量.
 */
void setEnv();

/**
 * 初始化日志环境.
 */
void initLogConfig();

/**
 * 初始化格式对应的字段
 * @return
 */
void initFormatFields();

}
