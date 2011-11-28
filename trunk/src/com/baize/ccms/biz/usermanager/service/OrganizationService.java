  /**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.biz.usermanager.service;

import java.util.List;

import com.baize.ccms.biz.usermanager.domain.Organization;
import com.baize.common.core.dao.Pagination;

/**
 * <p>标题: —— 机构业务逻辑处理</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 用户管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-2-16 下午02:59:56
 * @history（历次修订内容、修订人、修订时间等） 
 */
public interface OrganizationService {
	
	/**
	 * 添加数据
	 * @param  organization 机构对象
	 * @param siteId
	 * @param sessionID
	 */
	boolean addOrganization(Organization organization, String pid, String siteId, String sessionID);
	
	/**
	 * 删除数据
	 * @param id 主键ID
	 * 
	 */
	void deleteObject(String id);
	
	/**
	 * 修改数据
	 * @param organization 机构对象
	 * @param pid 父节点ID
	 * @param siteId
	 * @param sessionID
	 */
	boolean modifyObject(Organization organization,String pid,String oldpid, String siteId, String sessionID);
	
	/**
	 * 查询数据
	 * @param id 主键ID
	 * @return Organization 机构数据
	 */
	Organization findOrganizationByKey(String id);
	
	/**
	 * 查询所有机构数据
	 * @return List 所有机构数据 
	 */
	List<Organization> findAll();
	
	/**
	 * 根据根节点查找机构数据
	 * @param pagination 分页对象
	 * @param nodeid 节点ID
	 * @return Pagination 分页对象
	 */
	Pagination findOrganizationDataByNodeId(Pagination pagination,String nodeid,boolean isUpSystemAdmin,boolean isSiteAdmin);	

	
	/**
	 * 通过父机构id查找其一级子机构树 
	 * @param pid 父id
	 * @return List 所有机构树数据
	 */
	List findSubOrgTreeByPid(String pid);
	
	/**
	 * 根据所有的主键删除数据
	 * @param ids 所有主键的ID集合，中间以,号个尅
	 * @param siteId
	 * @param sessionID	
	 * @return boolean true,false
	 */
	boolean modifyAllObject(String ids, String siteId, String sessionID); 
	/**
	 * 递归获取所有的子栏目名称
	 * @param list 所有名称的集合
	 * @param id 机构ID
	 * @return 返回所有的子栏目名称
	 */
	List findOrgName(List list ,String id);
	/**
	 * 获取页面显示的所有的栏目的名称，以<<隔开
	 * @param id 当前选择节点ID
	 * @return String 栏目的名称，以<<隔开
	 */
	String getOrganizationName(String id);
	/**
	 * 根据机构ID查询用户
	 * @param orgids 机构ID集合
	 * @return boolean 如果有用户返回true否则返回false 
	 */
	boolean findUserByOrganizationId(String orgids);
	
	/**
	 * 根据栏目ID查询它的子节点
	 * @param StringBuffer返回的字符串
	 * @param id 当前栏目的ID
	 * 
	 */
	void findOrganizationChildById(StringBuffer sb,String id);
	
	/**
	 * 根据选择的ID查询机构名称
	 * @param id 选择的ID
	 * @return String 机构名称的集合
	 */
	String findChildOrganizationNameById(String id);
}
