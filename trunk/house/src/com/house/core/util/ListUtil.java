/**
 * project：电视互联网项目
 */
package com.house.core.util;

import java.util.List;

/**
 * <p>标题: —— List对象处理工具类</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 通用平台</p>
 * <p>版权: Copyright (c) 2011 江苏集群信息产业股份有限公司
 * @author 刘加东
 * @version 1.0
 * @since 2011-5-18 下午03:25:33
 * <p>修订历史:（历次修订内容、修订人、修订时间等） </p>
 */
public class ListUtil {
	
	/**
	 * 从List对象序列中获取单个对象
	 * @param List<Object>
	 * @return object
	 */
	public static Object getListOne(List<Object> list){
		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
}
