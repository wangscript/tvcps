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
 * 
 * <p>标题: —— 图片新闻标签常量类</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 模板单元管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-6-9 下午02:10:50
 * @history（历次修订内容、修订人、修订时间等）
 */
public final class PictureNewsLabel {

	/** 所有标题链接的List */
	public final static List pictureNewsLabels = new ArrayList();

	public final static Map map = new HashMap();
	static {	

		
		pictureNewsLabels.add(map);
	}

	private PictureNewsLabel() {
		// null
	}
}
