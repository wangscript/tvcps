/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.unitmanager.service;

import java.util.List;

import com.j2ee.cms.biz.unitmanager.web.form.OnlineSurverySetForm;
import com.j2ee.cms.plugin.onlinesurvey.domain.OnlineSurveyContent;
import com.j2ee.cms.common.core.dao.Pagination;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CPS</p>
 * <p>版权: Copyright (c) 2009  
 * @author 包坤涛
 * @version 1.0
 * @since 2009-6-1 下午05:42:03
 * @history（历次修订内容、修订人、修订时间等） 
 */
public interface OnlineSurverySetService {
	
	/**
	 * 查找配置文件
	 * @param form  栏目链接表单对象
	 * @param siteId  网站id
	 */
	public OnlineSurverySetForm  findConfig(OnlineSurverySetForm form, String siteId);
	
	/**
	 * 保存样式
	 * @param form
	 * @param siteId
	 */
	public void setStyle(OnlineSurverySetForm form, String siteId);
		
	/**  
	 * 查找样式
	 * @param  siteId 网站id
	 * return arrayList
	 **/
	public OnlineSurverySetForm getStyle(String siteid, OnlineSurverySetForm form);
	
	/**
	 * 网上调查类型文件名查找
	 * @param categoryId
	 * @return
	 */
    public List<OnlineSurverySetForm> findAllOnlineSurvey(String categoryId);
    
    /**
     * 查找主题
     * @param category
     * @return
     */
    String findThemeByCategory(String category);
    
    /**
     * 保存网上调查单元的xml配置
     * @param form
     * @return
     */
    String saveConfigXml(OnlineSurverySetForm form);
    
    /**
     * 添加在线调查单元数据
     * @param form
     * @param filePath
     * @param siteId
     */
    void addOnlineSurveryData(OnlineSurverySetForm form, String filePath, String siteId);
}
