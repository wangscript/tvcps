/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.unitmanager.label;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CPS</p>
 * <p>版权: Copyright (c) 2009  
 * @author 郑荣华
 * @version 1.0
 * @since 2009-9-8 上午10:35:48
 * @history（历次修订内容、修订人、修订时间等） 
 */
public final class MagazineCategoryLabel {
	
	/** 分类名称 */
	public final static String CATEGORYNAME = "<!--categoryName-->";
	
	/** 期刊分类标签 */
	public final static List magazineCategoryLabels = new ArrayList();
	
	/**所有标签 */
	public final static Map map = new HashMap();
	
	static {
		map.put("分类名称", CATEGORYNAME);
		magazineCategoryLabels.add(map);
	}
	
	private MagazineCategoryLabel() {
	
	}
}
