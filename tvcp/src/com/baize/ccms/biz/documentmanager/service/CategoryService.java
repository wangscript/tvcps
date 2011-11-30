/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.documentmanager.service;

import java.util.List;

import com.j2ee.cms.biz.documentmanager.domain.DocumentCategory;
import com.j2ee.cms.common.core.dao.Pagination;

/**
 * <p>标题: 类别service</p>
 * <p>描述: 类别的接口，用于存放方法</p>
 * <p>模块: 文档管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 郑荣华
 * @version 1.0
 * @since 2009-3-26 上午10:54:19
 * @history（历次修订内容、修订人、修订时间等） 
 */
public interface CategoryService {
	
	/**
	 * 添加数据
	 * @param category
	 * @param siteId
	 * @param sessionID
	 * @param categoryName
	 */
	 void addCategory(DocumentCategory category, String siteId, String sessionID, String categoryName);
	
	/**
	 * 删除数据
	 * id    要删除的类别的id
	 */
	void deleteCategory(String id);
	
	/**
	 * 修改数据
	 * category  要修改的类别对象
	 */
	void modifyCategory(DocumentCategory category, String siteId, String sessionID, String categoryName );
	
	/**
	 * 查找图片类别数据并返回Category
	 * @param  siteid   网站id
	 * @return list     返回一个图片类别对象
	 */
	List<DocumentCategory> findCategoryPictureData(String siteid);
	
	/**
	 * 查找flash类别数据并返回Category
	 * @param  siteid  网站id
	 * @return list    返回一个flash类别对象
	 */
	List<DocumentCategory> findCategoryFlashData(String siteid);
	
	/**
	 * 查找附件类别数据并返回Category
	 * @param  siteid  网站id
	 * @return list    返回一个附件类别对象
	 */
	List<DocumentCategory> findCategoryAttachmentData(String siteid);
	
	/**
	 * 分页显示类别数据
	 * @param  pagination   分页对象
	 * @return pagination   返回一个分页对象
	 */
	Pagination findCategoryData(Pagination pagination);
	
	/**
	 * 按照用户id查找类别信息
	 * @param  pagination   分页对象
	 * @param  id           要查询的属性
	 * @param  value        要查询的属性的值
	 * @return pagination   返回一个分页对象
	 */
	Pagination findCategoryByCreatorId(Pagination pagination, String id, Object value);
	
	/**
	 * 根据栏目id获取类别数据
	 * @param  id        要查询的文档的id
	 * @return category  返回一个类别对象
	 */
	DocumentCategory findCategoryById(String id);
	
	/**
	 * 创建树的方法
	 * @param  treeid          树的节点
	 * @param  categoryService 传递一个categoryService对象,以便调用当中的一些方法
	 * @param  siteId          网站ID
	 * @param  userId          获得当前登录用户的id
	 * @param  isUpSiteAdmin   是否是网站管理员以上权限
	 * @return object          返回一个list的对象
	 */
	List<Object> getTreeList(String treeid, CategoryService categoryService, String siteId,String userId,boolean isUpSiteAdmin);
	
	/**
	 * 处理删除
	 * @param ids  要删除的类别的ids
	 * @return     返回是否删除成功	
	 * */
	String deleteCategoryByIds(String ids, String siteId, String sessionID, String categoryName); 
	
	/**
	 * 查找所有类别名称
	 * @param nodeid  类别标记
	 * @param siteId 网站id
	 * @return  category 名称字符串
	 */
    public String findCategoryName(String nodeid, String siteId);
    
    /**
	 * 查找js脚本类别数据并返回Category
	 * @param  siteid  网站id
	 * @return list    返回一个js脚本类别对象
	 */
	List<DocumentCategory> findCategoryJsData(String siteid);
	
}
