/**
 * 
 */
package com.house.core.util;

import java.net.URL;


/**
 * 
 * @Title: GlobalUtil.java
 * @Package com.joyque.plugin.house365.util
 * @Description: TODO 提供系统通用变量，通用参数的获取
 * @Company 江苏集群信息产业股份有限公司
 * @author ZHAIANXU
 * @date 2011-1-11 下午03:18:42
 * @version V1.0
 */
public class GlobalUtil{	
	 
	/**
	 * 
	  * @method :getAppPath
	  * @description: 获取系统CLASSPATH物理路径
	  * @return String 返回CLASSPATH物理路径
	  * @createTime:2011-1-11下午03:23:00
	  * @author:ZHAIANXU
	 */
	public static String getAppClassPath(){
		URL url = GlobalUtil.class.getClassLoader().getResource("//");
		return url.getPath();
	}

}
