/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.sys;

import java.util.HashMap;
import java.util.Map;


/**
 * <p>标题: 全局配置</p>
 * <p>描述: 存放一些系统的全局配置</p>
 * <p>模块: 通用平台</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since Feb 13, 2009 11:06:12 AM
 * @history（历次修订内容、修订人、修订时间等） 
 */
public final class GlobalConfig {
	
	/** 应用的实际路径() */
	public static String appRealPath;
	
	/** 应用名 */
	public static String appName;
	
	/** 主网站id */
	public static String mainSiteId;
	
	/** MAC地址 */
	public static String macAddress;
	
	/** 所有的MAC地址 */
	public static String allMacAddress;
	
	/** 最大连接数 */
	public static int maxConnection;
	
	/** 最大网站数 */
	public static String maxSite = "5";
	
	/** 最大栏目数 */
	public static String maxColumn = "100";
	
	/** 有效日期 */
	public static String effectiveDate;

	/** 实际应用ip */
	public static Map<String, String> ip = new HashMap<String, String>();
	
	/** 存放登陆session */
	public static Map loginSession = new HashMap();
	
	/** 存放用户session */
	public static Map userSession = new HashMap();
	
	/** 功能单元左侧树配置xml文件路径 */
	public static String functionUnitXmlPath = "/plugin/plugin_menu.xml";
	
	/** 系统设置左侧树配置xml文件路径 */
	public static String systemSetXmlPath = "/module/config_manager/systemSetting.xml";
	
	/** 系统个人设置xml配置文件路径 */
	public static String personSetXmlPath = "/module/user_manager/menu/person_set.xml"; 
}
