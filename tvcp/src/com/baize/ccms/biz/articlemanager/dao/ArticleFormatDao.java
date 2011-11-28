/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.biz.articlemanager.dao;

import com.baize.ccms.biz.articlemanager.domain.ArticleAttribute;
import com.baize.ccms.biz.articlemanager.domain.ArticleFormat;
import com.baize.common.core.dao.GenericDao;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 文章管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 杨信
 * @version 1.0
 * @since 2009-3-12 下午03:30:18
 * @history（历次修订内容、修订人、修订时间等） 
 */
public interface ArticleFormatDao extends GenericDao<ArticleFormat, String> {
	
	/**
	 * 通过属性查找当前类型号
	 * @param attribute 属性 (需要属性类型和格式id)
	 * @return
	 */
	int findCurrTypeNumByAttribute(ArticleAttribute attribute);

	/**
	 * 通过属性更新格式中的字段集合及当前类型号
	 * @param attribute
	 * @return
	 */
	int updateFormatFieldsAndCurrNumByAttribute(ArticleAttribute attribute);
}
