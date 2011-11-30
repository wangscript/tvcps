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
 * <p>模块: 模板管理</p>
 * <p>版权: Copyright (c) 2009  </p>
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-6-9 上午11:04:54
 * @history（历次修订内容、修订人、修订时间等）
 */
public final class ColumnLinkLabel {

	/** 栏目链接 */
	public final static String COLUMN_LINK = "<!--columnLink-->";
	/**栏目前缀**/
	public final static String COLUMN_PREFIX = "<!--columnPrefix-->";
	/**栏目后缀**/
	public final static String COLUMN_SUFFIX = "<!--columnSuffix-->";
	/**栏目更新时间**/
	public final static String COLUMN_UPDATE_TIME = "<!--columnUpdateTime-->";
	/**栏目说明**/
	public final static String COLUMN_NOTE = "<!--columnNote-->";
	
	/** 所有了栏目链接的List */
	public final static List columnLinkLabels = new ArrayList();
	/**所有标签 */
	public final static Map map = new HashMap();
	
	static {
		map.put("栏目链接", COLUMN_LINK);
		map.put("栏目前缀", COLUMN_PREFIX);
		map.put("栏目后缀", COLUMN_SUFFIX);
		map.put("栏目更新时间", COLUMN_UPDATE_TIME);
		map.put("栏目说明", COLUMN_NOTE);
		columnLinkLabels.add(map);
	}
	
	private ColumnLinkLabel() {
		// null
	}
	
}
