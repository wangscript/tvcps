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
 * 
 * <p>标题: —— 标题链接标签常量类</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 模板单元管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-6-9 下午02:10:50
 * @history（历次修订内容、修订人、修订时间等）
 */
public final class TitleLinkLabel {
	/** 文章ID*/
	public static final String ARTICLEID ="<!--articleId-->";
	/** 栏目链接 */
	public final static String COLUMN_LINK = "<!--columnLink-->";
	/**  标题 */
	public static final String ARTICLETITLE  = "<!--articletitle-->";
	/**  标题(缩) */
	public static final String ARTICLETITLESHORT  = "<!--articletitleshort-->";
	/**  标题链接 */
	public static final String ARTICLEURL  = "<!--articleurl-->";
	/**  标题前缀 */
	public static final String ARTICLEHEADER  = "<!--titlePrefix-->";
	/**  标题后缀 */
	public static final String ARTICLEENDER  = "<!--titleSuffix-->";
	/**  副标题 */
	public static final String ARTICLESUBTITLE  = "<!--articlesubtitle-->";
	/**  副标题(缩) */
	public static final String ARTICLESUBTITLESHORT  = "<!--articlesubtitleshort-->";
	/**  引题 */
	public static final String ARTICLEQUOTETITLE  = "<!--articlequotetitle-->";
	/**  引题(缩) */
	public static final String ARTICLEQUOTETITLESHORT  = "<!--articlequotetitleshort-->";
	/**  链接标题 */
	public static final String ARTICLELINKTITLE  = "<!--articlelinktitle-->";
	/**  链接标题(缩) */
	public static final String ARTICLELINKTITLESHORT  = "<!--articlelinktitleshort-->";
	/**  更多内容 */
	public static final String MORELINK  = "<!--more-->";
	/**  信息创建时间 */
	public static final String ARTICLECREATEDATE  = "<!--createTime-->";
	/**  信息显示时间 */
	public static final String ARTICLEDEPLOYTIME  = "<!--displayTime-->";
	/**  四位年 */
	public static final String YEAR4  = "<!--year4-->";
	/**  两位年 */
	public static final String YEAR2  = "<!--year2-->";
	/**  两位月 */
	public static final String MONTH2  = "<!--month2-->";
	/**  一位月 */
	public static final String MONTH1  = "<!--month1-->";
	/**  两位日 */
	public static final String DAY2  = "<!--day2-->";
	/**  一位日 */
	public static final String DAY1  = "<!--day1-->";
	/**  时 */
	public static final String HOUR  = "<!--hour-->";
	/**  分 */
	public static final String MINUTE  = "<!--minute-->";
	/**  秒 */
	public static final String SECOND  = "<!--second-->";
	/**  信息摘要 */
	public static final String ARTICLEBRIEF = "<!--articlebrief-->";
	/**  信息关键字 */
	public static final String ARTICLEKEYWORDS  = "<!--articlekeywords-->";
	/**  信息作者 */
	public static final String ARTICLEARTICLEAUTHOR  = "<!--articleauthor-->";
	/**  信息录入人 */
	public static final String ARTICLEEDITOR  = "<!--articleeditor-->";
	/**  信息审核人 */
	public static final String ARTICLEAUDITOR  = "<!--articleauditor-->";
	/**  信息来源 */
	public static final String ARTICLESORCE  = "<!--articlesource-->";
	/**  信息访问次数 */
	public static final String ARTICLEHITS  = "<!--articlehits-->";
	
	/** 所有标题链接的List */
	public final static List titleLinkLabels = new ArrayList();
	

	public final static Map map = new HashMap();
	static {	

		/**  标题 */
		map.put("标题",ARTICLETITLE); 
		/**  标题(缩) */
		map.put("标题(缩)",ARTICLETITLESHORT); 
		/**  标题链接 */
		map.put("标题链接",ARTICLEURL);
		/**  标题前缀 */
		map.put("标题前缀",ARTICLEHEADER); 
		/**  标题后缀 */
		map.put("标题后缀",ARTICLEENDER);
		/**  副标题 */
		map.put("副标题",ARTICLESUBTITLE);
		/**  副标题(缩) */
		map.put("副标题(缩)",ARTICLESUBTITLESHORT);
		/**  引题 */
		map.put("引题",ARTICLEQUOTETITLE);
		/**  引题(缩) */
		map.put("引题(缩)",ARTICLEQUOTETITLESHORT); 
		/**  链接标题 */
		map.put("链接标题",ARTICLELINKTITLE); 
		/**  链接标题(缩) */
		map.put("链接标题(缩)",ARTICLELINKTITLESHORT);
		/**  更多内容 */
		map.put("更多内容",MORELINK);	
		/**  信息创建时间 */
		map.put("信息创建时间",ARTICLECREATEDATE); 
		/**  信息显示时间 */
		map.put("信息显示时间",ARTICLEDEPLOYTIME); 
		/**  四位年 */
		map.put("四位年",YEAR4);
		/**  两位年 */
		map.put("两位年",YEAR2); 
		/**  两位月 */
		map.put("两位月",MONTH2);
		/**  一位月 */
		map.put("一位月",MONTH1); 
		/**  两位日 */
		map.put("两位日",DAY2); 
		/**  一位日 */
		map.put("一位日",DAY1);
		/**  时 */
		map.put("时",HOUR);
		/**  分 */
		map.put("分",MINUTE); 
		/**  秒 */
		map.put("秒",SECOND);
		/**  信息摘要 */
		map.put("信息摘要",ARTICLEBRIEF);
		/**  信息关键字 */
		map.put("信息关键字",ARTICLEKEYWORDS);
		/**  信息作者 */
		map.put("信息作者",ARTICLEARTICLEAUTHOR); 
		/**  信息录入人 */
		map.put("信息录入人",ARTICLEEDITOR); 
		/**  信息审核人 */
		map.put("信息审核人",ARTICLEAUDITOR);
		/**  信息来源 */
		map.put("信息来源",ARTICLESORCE);
		/**  信息访问次数 */
		map.put("信息访问次数",ARTICLEHITS);
		/** 栏目链接 */
		map.put("栏目链接", COLUMN_LINK);
		/**文章ID*/
		map.put("文章ID", ARTICLEID);
		
		titleLinkLabels.add(map);
	}

	private TitleLinkLabel() {
		// null
	}
}
