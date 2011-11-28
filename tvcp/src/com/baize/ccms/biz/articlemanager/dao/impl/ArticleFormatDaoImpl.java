/**
 * project：通用内容管理系统
 * Company: 南京瀚沃信息科技有限责任公司
*/
package com.baize.ccms.biz.articlemanager.dao.impl;

import java.util.List;

import com.baize.ccms.biz.articlemanager.dao.ArticleFormatDao;
import com.baize.ccms.biz.articlemanager.domain.ArticleAttribute;
import com.baize.ccms.biz.articlemanager.domain.ArticleFormat;
import com.baize.common.core.dao.GenericDaoImpl;
import com.baize.common.core.util.BeanUtil;
import com.baize.common.core.util.CollectionUtil;
import com.baize.common.core.util.StringUtil;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 文章管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-3-29 下午04:33:42
 * @history（历次修订内容、修订人、修订时间等）
 */
public class ArticleFormatDaoImpl extends GenericDaoImpl<ArticleFormat, String> implements ArticleFormatDao {

	@SuppressWarnings("unchecked")
	public int findCurrTypeNumByAttribute(ArticleAttribute attribute) {
		int currTypeNum = 0;
		String field = "curr" + StringUtil.firstUpperCase(attribute.getAttributeType());
		String hql = "SELECT DISTINCT attribute.articleFormat." + field +
					  " FROM ArticleAttribute attribute" + 
					 " WHERE attribute.articleFormat.id = '" + attribute.getArticleFormat().getId() + "'";
		List list = (List) getFindByComplat(hql); 
		if (!CollectionUtil.isEmpty(list)) {
			if(list.get(0) != null) {
				currTypeNum = (Integer) list.get(0);
			}
		}
		return currTypeNum;
	}
	
	public int updateFormatFieldsAndCurrNumByAttribute(ArticleAttribute attribute) {
		ArticleFormat format = attribute.getArticleFormat();
		
		// 如：currText
		String field = "curr" + StringUtil.firstUpperCase(attribute.getAttributeType());
		int currNum = (Integer) BeanUtil.getFieldValue(format, "com.baize.ccms.biz.articlemanager.domain.ArticleFormat", field);
		// 字段集合
		String fields = attribute.getArticleFormat().getFields();
		
		// 新字段集合为  "title,auditor" -> "title,auditor,float2"
		String hql = "UPDATE ArticleFormat format" +
					   " SET format.fields = '" + fields + "'," +
					       " format." + field + " = " + currNum +
					 " WHERE format.id = '" + format.getId() + "'";
		return getUpdateByComplat(hql);
		
	}
	

}
