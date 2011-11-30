/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.templatemanager.service;

import com.j2ee.cms.biz.templatemanager.domain.TemplateUnitStyle;
import com.j2ee.cms.biz.templatemanager.web.form.TemplateUnitStyleForm;
import com.j2ee.cms.common.core.dao.Pagination;

/**
 * 
 * <p>标题: —— 模板单元样式业务逻辑处理类最高接口</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 用户管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-6-6 下午05:46:53
 * @history（历次修订内容、修订人、修订时间等）
 */
public interface TemplateUnitStyleService {
	/**
	 * 查找所有本网站的样式数据
	 * @param pagination 分页对象
	 * @param siteId 网站ID
	 * @param categoryId 模板单元类型ID
	 * @return Pagination 分页对象
	 */
	Pagination findTemplateUnitStylePage(Pagination pagination,String categoryId,String siteId);	
	
	/**
	 * 根据模板单元类别ID查询模板类别名称
	 * @param categoryId 模板单元类别ID
	 * @return String 模板单元类别名称
	 */
	String findTemplateUnitCategoryNameByKey(String categoryId);
	
	/**
	 * 根据样式ID查询样式详细信息
	 * @param styleId 样式ID
	 * @return TemplateUnitStyle 模板单元样式对象
	 */
	TemplateUnitStyle findTemplateUnitStyleByKey(String styleId);
	
	/**
	 * 保存样式数据
	 * @param templateUnitStyle 模板单元样式对象
	 * @param categoryId 模板单元类别ID
	 * @param htmlContent 模板单元HTML代码
	 * @param userId 用户ID
	 * @param siteId 网站ID
	 */
	void addTemplateUnitStyle(TemplateUnitStyle templateUnitStyle,String categoryId,String htmlContent,String userId,String siteId);
	
	
	/**
	 * 修改样式数据
	 * @param templateUnitStyle 模板单元样式对象
	 * @param categoryId 模板单元类别ID
	 * @param htmlContent 模板单元HTML代码
	 * @param userId 用户ID
	 * @param siteId 网站ID
	 * @param styleId 样式ID
	 */
	void modifyTemplateUnitStyle(TemplateUnitStyle templateUnitStyle,String categoryId,String htmlContent,String userId,String siteId,String styleId);
	
	/**
	 * 根据多个主键删除数据
	 * @param ids          主键集合
	 * @param siteId
	 * @param sessionID
	 * @param categoryId
	 */
	void deleteTemplateUnitStyleByIds(String ids, String siteId, String sessionID, String categoryId);
	
	/**
	 * 查找所有样式名称
	 * @return styleNameStr 样式名称
	 */
	public String findStyleNameStr(String categoryId);

	/**
	 * 根据样式管理ID查询样式具体属性,将预览地址+样式内容数据放到form里面
	 * @param templateUnitStyleForm 样式管理form
	 * @param siteId 网站ID
	 *  
	 */
	void findStyleByStyleId(TemplateUnitStyleForm templateUnitStyleForm,String siteId);

}
