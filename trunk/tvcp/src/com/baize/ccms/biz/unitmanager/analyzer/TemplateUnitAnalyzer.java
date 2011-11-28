/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.biz.unitmanager.analyzer;

import java.util.Map;

/**
 * <p>标题: 分析器</p>
 * <p>描述: 用于模板单元的分析</p>
 * <p>模块: 模板管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-5-13 上午09:16:15
 * @history（历次修订内容、修订人、修订时间等）
 */
public interface TemplateUnitAnalyzer {
	
	/**
	 * 获取该单元生成的HTML代码
	 * @param unitId 单元ID
	 * @param articleId 文章id
	 * @param columnId 栏目ID
	 * @param siteId 网站ID
	 * @param commonLabel 公共标签
	 * @return
	 */
	String getHtml(String unitId, String articleId, String columnId, String siteId, Map<String,String> commonLabel);
	
}
