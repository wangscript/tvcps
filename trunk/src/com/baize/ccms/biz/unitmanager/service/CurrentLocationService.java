/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.biz.unitmanager.service;

import java.util.List;

import com.baize.ccms.biz.templatemanager.domain.TemplateUnitStyle;
import com.baize.ccms.biz.unitmanager.web.form.CurrentLocationForm;

/**
 * <p>标题: —— 当前位置业务逻辑处理类最高接口</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 用户管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-6-15 上午10:12:22
 * @history（历次修订内容、修订人、修订时间等） 
 */

public interface CurrentLocationService {
	/**
	 * 保存生成的配置内容到数据库
	 * @param currentLocationForm 当前位置form对象
	 * @param filePath 配置文件路径
	 * @param siteId
	 * @param sessionID
	 */
	void saveConfigContent(CurrentLocationForm currentLocationForm, String filePath, String siteId, String sessionID);
	
	/**
	 * 保存生成的配置内容到数据库
	 * @param currentLocationForm 标题链接form对象
	 * @return newfilepath
	 */
	String saveConfigXml(CurrentLocationForm currentLocationForm);
	
	
	/**
	 * 查找配置
	 * @param currentLocationForm 标题链接form对象
	 * @param siteId 网站ID
	 */
	void findConfig(CurrentLocationForm currentLocationForm,String siteId);
	
	/**
	 * 查找模板单元样式
	 * @param unit_categoryId 模板单元类别ID
	 * @param siteId 网站ID
	 * @return List TemplateUnitStyle 模板单元样式对象
	 */
	List<TemplateUnitStyle> findTemplateUnitStyleByCategoryIdAndSiteId(String unit_categoryId,String siteId);


	/**
	 * 根据名称查询数据
	 * @param id 
	 * @return List 模板单元对象list
	 */
	List findTemplateUnitByUnitId(String id);
}
