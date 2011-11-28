/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.biz.templatemanager.service;

import java.util.List;

import com.baize.ccms.biz.columnmanager.domain.Column;
import com.baize.ccms.biz.sitemanager.domain.Site;
import com.baize.ccms.biz.templatemanager.domain.TemplateCategory;
import com.baize.ccms.biz.templatemanager.domain.TemplateUnit;
import com.baize.ccms.biz.templatemanager.domain.TemplateUnitCategory;

/**
 * <p>标题: 模板单元服务类</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 模板管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-5-13 上午09:13:16
 * @history（历次修订内容、修订人、修订时间等）
 */
public interface TemplateUnitService {
	
	/**
	 * 查找模板类别 
	 * @param siteId           网站id
	 * @param sessionID        用户id
	 * @param isUpSystemAdmin
	 * @param isSiteAdmin 
	 * @return                 返回模板类别列表
	 */
     List<TemplateCategory> findTemplateCategory(String siteId, String sessionID, boolean isUpSystemAdmin, boolean isSiteAdmin);
     
     /**
      * 查找栏目模板
      * @param columnId 栏目id
      * @return         返回栏目对象
      */
     Column findColumnTemplate(String columnId);
     
     /**
      * 处理模板选择
      * @param templateCategoryList 模板类别列表
      * @param siteId               网站id
      * @param sessionID            用户id
      * @param isUpSystemAdmin      
      * @param isSiteAdmin
      * @return                     返回模板实例的字符串
      */
     String chooseTemplate(List<TemplateCategory> templateCategoryList, String siteId, String sessionID, boolean isUpSystemAdmin, boolean isSiteAdmin);
     
     /**
      * 按照网站id查找网站
      * @param siteId  网站id
      * @return        返回网站对象
      */
     Site findSiteBySiteId(String siteId);
     
     /**
      * 选择模板
      * @param type               类别用于判断是网站的还是栏目的
      * @param nodeId			  节点id（有可能是栏目id）
      * @param siteId             网站id
      * @param templateInstanceId 模板实例id
      */
     String modifyTemplateChoose(String type, String nodeId, String siteId, String templateInstanceId, String sesionID);
     
     /**
      * 撤销选择的模板
      * @param siteId  				网站id
      * @param type    				类别用于判断是网站的还是栏目的
      * @param nodeId  				节点id（有可能是栏目id）
      * @param templateInstanceId	要取消的模板实例id
      */
     void modifyCancelTemplate(String siteId, String type, String nodeId, String templateInstanceId, String sessionID);
     
     /**
      * 添加模板单元
      * @param templateUnit 单元实例
      */
     void addTempateUnit(TemplateUnit templateUnit);
     
     /**
      * 获得替换后的模板
      * @param templateType 模版类型
      * @param tempateInstanceId 模板实例ID
      * @param columnId 栏目ID
      * @param siteId 网站ID
      * @return
      */
     String getReplacementTemplate(String templateType, String tempateInstanceId, String columnId, String siteId);
     
     /**
      * 获得替换后的页面
      * @param templateInstanceId 模版实例ID
      * @param articleId 文章ID
      * @param columnId 栏目ID
      * @param siteId 网站ID
      * @return
      */
     String getPreviewPage(String templateInstanceId, String articleId, String columnId, String siteId);
     
     /**
      * 通过模板实例ID查找模板单元
      * @param instanceId 模板实例ID
      * @return
      */
     List<TemplateUnit> findTemplateUnitsByInstaceId(String instanceId);
     
     /**
      * 查询所有单元类别
      * @return
      */
     List<TemplateUnitCategory> findAllUnitCategories();
     
     /**
      * 通过单元ID查找模板单元
      * @param unitId
      * @return
      */
     TemplateUnit findTemplateUnitByUnitId(String unitId);
     
     /**
      * 撤销单元设置
      * @param unitId
      */
     void cancelUnitSet(String unitId);
     
     /**
      * 获取转发路径
      * @return
      */
     String getForwardPath();
     
     /**
      * 拷贝单元设置
      * @param fromUnitId
      * @param toUnitId
      */
     void copyUnitSet(String fromUnitId, String toUnitId); 
     
     /**
 	 * 按照网站id查询第一级栏目
 	 * @param siteId
 	 * @return
 	 */
 	List findColumnTempalte(String columnId, String siteId, String creatorId, boolean isUpSiteAdmin);
 	
 	/**
 	 * 按照网站id和栏目id查找栏目模板
 	 * @param siteId
 	 * @param columnId
 	 * @return
 	 */
 	List findColumnTempateByColumnId(String columnId, String sessionID, String siteId, boolean isUpSiteAdmin);
 	
 	/**
 	 * 查找模版类别和模版
 	 * @param templateCategoryList
 	 * @param siteId
 	 * @param sessionID
 	 * @param isUpSystemAdmin
 	 * @param isSiteAdmin
 	 * @return
 	 */
 	String findTemplateCategoryAndTemplate(List<TemplateCategory> templateCategoryList, String siteId, String sessionID, boolean isUpSystemAdmin, boolean isSiteAdmin);
 	
 	/**
 	 * 查询所有不属于这个ID的类别
 	 * @param categoryId 类别ID
 	 * @return  List 类别集合
 	 */
 	List findTemplateUnitCategoryNotArticle(String categoryId);
 	
 	/**
 	 * 查找模版设置是否有权限
 	 * @param nodeId
 	 * @param siteId
 	 * @param sessionID
 	 * @return
 	 */
 	String findColumnTemplateSet(String nodeId, String siteId, String sessionID);
}
