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
public final class TitleLinkPageLabel {	

	/** 栏目链接 */
	private final static String COLUMNLINK = "<!--columnLink-->";
	/** 标题 */
	private final static String ARTICLETITLE = "<!--articletitle-->";
	/** 副标题 */
	private final static String ARTICLESUBTITLE = "<!--articlesubtitle-->";
	/** 引题 */
	private final static String ARTICLEQUOTETITLE = "<!--articlequotetitle-->";
	/** 标题链接 */
	private final static String ARTICLEURL = "<!--articleurl-->";
	/** 摘要 */
	private final static String ARTICLEBRIEF = "<!--articlebrief-->";
	/** 作者 */
	private final static String ARTICLEAUTHOR = "<!--articleauthor-->";
	/** 点击次数 */
	private final static String ARTICLEHITS = "<!--articlehits-->";
	/** 四位年 */
	private final static String YEAR4 = "<!--year4-->";
	/** 两位年 */
	private final static String YEAR2 = "<!--year2-->";
	/** 两位月 */
	private final static String MONTH2 = "<!--month2-->";
	/** 一位月 */
	private final static String MONTH1 = "<!--month1-->";
	/** 两位日 */
	private final static String DAY2 = "<!--day2-->";
	/** 一位日 */
	private final static String DAY1 = "<!--day1-->";
	/** 时 */
	private final static String HOUR = "<!--hour-->";
	/** 分 */
	private final static String MINUTE = "<!--minute-->";
	/** 秒 */
	private final static String SECOND = "<!--second-->";
	/** 创建时间 */
	private final static String CREATETIME = "<!--createTime-->";
	/** 显示时间 */
	private final static String DISPLAYTIME = "<!--displayTime-->";
	/** 审核时间 */
	private final static String AUDITTIME = "<!--auditTime-->";
	/** 发布时间 */
	private final static String PUBLISHTIME = "<!--publishTime-->";
	/** 失效时间 */
	private final static String INVALIDTIME = "<!--invalidTime-->";
	/** 图片 */
	private final static String PIC = "<!--pic-->";
	/** 附件 */
    private final static String ATTACH = "<!--attach-->";
	/** 标题前缀 */
	private final static String TITLEPREFIX = "<!--titlePrefix-->";
	/** 标题后缀 */
	private final static String TITLESUFFIX = "<!--titleSuffix-->";
	
	/** 所有标题链接分页的List */
	public final static List titleLinkPageLabels = new ArrayList();
	
	public final static Map map = new HashMap();
	
	static {	
		map.put("栏目链接", COLUMNLINK);
		map.put("标题", ARTICLETITLE);
		map.put("副标题", ARTICLESUBTITLE);
		map.put("引题", ARTICLEQUOTETITLE);
		map.put("标题链接", ARTICLEURL);
		map.put("摘要", ARTICLEBRIEF);
		map.put("作者", ARTICLEAUTHOR);
		map.put("点击次数", ARTICLEHITS);
		map.put("四位年", YEAR4);
		map.put("两位年", YEAR2);
		map.put("两位月", MONTH2);
		map.put("一位月", MONTH1);
		map.put("两位日", DAY2);
		map.put("一位日", DAY1);
		map.put("时", HOUR);
		map.put("分", MINUTE);
		map.put("秒", SECOND);
		map.put("创建时间", CREATETIME); 
		map.put("显示时间", DISPLAYTIME);
		map.put("审核时间", AUDITTIME);
		map.put("发布时间", PUBLISHTIME);
		map.put("失效时间", INVALIDTIME);
		//map.put("图片", PIC);
		map.put("附件", ATTACH);
		map.put("标题前缀", TITLEPREFIX);
		map.put("标题后缀", TITLESUFFIX);
		titleLinkPageLabels.add(map);
	}

	private TitleLinkPageLabel() {
		
	}
}
