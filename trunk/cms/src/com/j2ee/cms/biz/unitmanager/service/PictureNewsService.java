/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.unitmanager.service;

import java.util.List;

import com.j2ee.cms.biz.templatemanager.domain.TemplateUnitStyle;
import com.j2ee.cms.biz.unitmanager.web.form.PictureNewsForm;

/**
 * <p>标题: —— 图片新闻业务逻辑处理接口</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 模板单元管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-6-3 上午10:36:58
 * @history（历次修订内容、修订人、修订时间等） 
 */

public interface PictureNewsService {

	/**
	 * 保存生成的配置内容到数据库
	 * @param pictureNewsForm 图片新闻form对象
	 * @param filePath 配置文件路径
	 * @param siteId
	 * @param sessionID
	 */
	void saveConfigContent( PictureNewsForm pictureNewsForm, String filePath, String siteId, String sessionID);
	
	/**
	 * 保存生成的配置内容到数据库
	 * @param pictureNewsForm 图片新闻form对象
	 * @return newfilepath
	 */
	String saveConfigXml( PictureNewsForm pictureNewsForm);
	
	
	/**
	 * 查找配置
	 * @param pictureNewsForm 图片新闻form对象
	 * @param siteId 网站ID
	 */
	void findConfig(PictureNewsForm pictureNewsForm,String siteId);
	
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
	
	/**
	 * 查询文章属性
	 * @param pictureNewsForm
	 * @return
	 */
	void findArticleAttributeByColumnId(PictureNewsForm pictureNewsForm);
}
