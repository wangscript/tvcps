/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.biz.unitmanager.label;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>标题: —— 当前位置标签</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 模板单元管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-6-15 上午10:27:47
 * @history（历次修订内容、修订人、修订时间等） 
 */

public class CurrentLocationLabel {
	
	/** 所有当前位置的List */
	@SuppressWarnings("unchecked")
	public final static List currentLocationLabels = new ArrayList();	
	/** 当前位置标签 */
	public final static String CURRENTLOCATION ="<!--currentLocation-->";

	public final static Map map = new HashMap();
	
	static {	
		map.put("当前位置", CURRENTLOCATION);
		currentLocationLabels.add(map);
	}
	
	private CurrentLocationLabel() {
		// null
	}
}
