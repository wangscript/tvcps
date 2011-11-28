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
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CCMS</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 郑荣华
 * @version 1.0
 * @since 2009-7-23 上午11:07:21
 * @history（历次修订内容、修订人、修订时间等） 
 */
public final class ArticleTextLabel {
	
	/** 上一篇 **/
	public final static String ARTICLE_PREVIOUS = "<!--articlePrevious-->";
	/** 下一篇 **/
	public final static String ARTICLE_NEXT = "<!--articleNext-->";
	/** 信息分页**/
	public final static String ARTICLE_PAGE = "<!--articlePage-->";
	/** 文章评论 */
	public final static String ARTICLE_COMMENT = "<!--articleComment-->";
	/** 所有了文章正文链接的List */
	public final static List articleTextLabels = new ArrayList();
	/**所有标签 */
	public final static Map map = new HashMap();
	
	static {
		map.put("文章评论", ARTICLE_COMMENT);
		map.put("上一篇", ARTICLE_PREVIOUS);
		map.put("下一篇", ARTICLE_NEXT); 
		map.put("信息分页", ARTICLE_PAGE);
		articleTextLabels.add(map);
	}
	
	private ArticleTextLabel() {
		
	}
}
