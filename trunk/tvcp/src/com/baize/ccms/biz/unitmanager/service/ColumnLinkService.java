/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.unitmanager.service;

import java.util.List;

import com.baize.ccms.biz.templatemanager.domain.TemplateUnit;
import com.baize.ccms.biz.templatemanager.domain.TemplateUnitStyle;
import com.baize.ccms.biz.unitmanager.web.form.ColumnLinkForm;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CCMS</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 郑荣华
 * @version 1.0
 * @since 2009-6-1 下午05:42:03
 * @history（历次修订内容、修订人、修订时间等） 
 */
public interface ColumnLinkService {
	
	/**
	 * 查找配置文件
	 * @param form  栏目链接表单对象
	 */
	void findConfig(ColumnLinkForm form);
	
	/**
	 * 按照类别id和网站id查找模板单元样式
	 * @param unit_categoryId  单元类别id
	 * @param siteId           网站id
	 * @return                 返回模板单元样式列表
	 */
	List<TemplateUnitStyle> findTemplateUnitStyleByCategoryIdAndSiteId(String unit_categoryId, String siteId);
	
	/**
	 * 保存配置文件信息
	 * @param form     栏目链接表单对象
	 * @return         返回配置文件路径
	 */
	String saveConfigXml(ColumnLinkForm form);
	
	/**
	 * 保存配置的内容
	 * @param form        栏目链接表单对象
	 * @param filePath    配置文件路径
	 * @param siteId
	 * @param sessionID
	 */
	void saveConfigContent(ColumnLinkForm form, String filePath, String siteId, String sessionID);
	
	/**
	 * 站内保存
	 * @param form    栏目链接表单对象
	 * @param unitId  单元id
	 * @param siteId
	 * @param sessionID
	 */
	void saveSiteConfig(ColumnLinkForm form, String unitId, String siteId, String sessionID);
}
