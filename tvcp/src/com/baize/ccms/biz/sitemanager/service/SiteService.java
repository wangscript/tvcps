/**
 * project：通用内容管理系统
 * Company: 南京瀚沃信息科技有限责任公司
*/
package com.baize.ccms.biz.sitemanager.service;

import java.util.List;
import java.util.Map;

import com.baize.ccms.biz.sitemanager.domain.Site;
import com.baize.ccms.biz.sitemanager.web.form.SiteForm;
import com.baize.ccms.biz.usermanager.domain.Menu;
import com.baize.ccms.biz.usermanager.domain.User;
import com.baize.common.core.dao.Pagination;

/**
 * <p>标题: 网站服务接口</p>
 * <p>描述: 网站服务接口，列出网站处理中的一些方法</p>
 * <p>模块: 网站管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author <a href="mailto:xinyang921@gmail.com">郑荣华</a>
 * @version 1.0
 * @since 2009-3-13 下午03:30:12
 * @history（历次修订内容、修订人、修订时间等） 
 */
public interface SiteService {

	/**
	 * 根据用户ID查询用户对象
	 * @param userId
	 * @return
	 */
	User findUserById(String userId);
	
	/**
	 * 删除网站
	 * @param id       删除的网站id
	 * @param siteId   当前网站id
	 * @param sessioID 用户id
	 * @return         返回是否删除成功
	 */
	String deleteSite(String id, String siteId, String sessionID);
	
	/**
	 * 分页显示网站
	 * @param pagination 传递一个分页对象
	 * @return           返回一个分页对象
	 */
	Pagination findSiteData(Pagination pagination);
	
	/**
	 * 获得父节点为空的网站信息
	 * @return  返回为空的网站的id
	 */
	String getSiteIdByParentIdIsNull();
	
	/**
	 * 查询要切换的网站
	 * @param siteId  当前网站id
	 * @param userId  当前用户id
	 * @return  返回所有网站信息
	 */
	List<Site> findChangeSites(String siteId, String userId);
	
	/**
	 * 查询网站
	 * @param id    要查询的网站的id
	 * @return Site 返回一个网站的对象
	 */
	Site findSiteBySiteId(String id);
	
	/**
	 * 添加一个静态树
	 * @param treeid 获取树的id 
	 * @return       返回树的列表
	 */
	List<Object> proccessAddTree(String treeid);

	/**
	 * 获取所有菜单
	 * @return
	 */
	List<Menu> getAllMenus();
	
	/**
	 * 根据用户id返回用户信息
	 * @param sessionID 用户id
	 * @return          返回用户对象
	 */
	public User findUser(String sessionID);
	
	/**
	 * 修改网站默认管理员登录名00
	 * @param site 
	 * @param oldSiteName
	 * @param siteId
	 * @param sessionID
	 * @return          返回用户对象
	 */
	public void modifySiteManagerLoginName(Site site, String oldSiteName, String siteId, String sessionID);
	
	/**
	 *  删除初始化使得模板类别
	 * @param siteId
	 * @param userId
	 * @param deletedSiteId
	 * @return
	 */
	
	public String deleteTemplateCategory(String siteId, String userId, String deletedSiteId);
	
	/**
	 * 修改用户拥有的站点
	 * @param userId
	 * @param siteId
	 */
	public void modifyUserSite(String userId, String siteId);
	
	/**
	 * 查询当前用户拥有的角色所在的网站
	 * @param userId
	 * @return
	 */
	String findSiteOfRolesInByUserId(String userId);
	
	/**
	 * 判断是否符合添加网站的条件，如果符合：添加，不符合：不添加
	 * @param site		  要添加的网站id
	 * @param sessionID   当前用户id
	 * @param siteId      当前网站的id
	 * @param flag        是否是第一次添加网站
	 * @return
	 */
	Map<String, String> addSite(Site site, String sessionID, String siteId, boolean flag);
	
	/**
	 * 初始化新增的网站目录信息
	 * @param site
	 */
	void initSiteDir(Site site);
	
	/**
	 * 获得新增网站的信息
	 * @param siteId  新增网站的id
	 * @return
	 */
	Site getNewAddSite(String siteId);
	
	/**
	 * 处理网站的其它初始化操作
	 * @param site		 获得一个网站对象
	 * @param sessionID  用户id 
	 * @return
	 */
	void addInitSiteInfo(Site site, String sessionID);
	
	/**
	 * 判断网站名称是否重名
	 * @param form
	 * @return
	 */
	String judgeSiteName(SiteForm form);
	
	/**
	 * 修改网站的发布
	 * @param form
	 * @return
	 */
	Site getNewModifiedSite(SiteForm form);
	
	/**
	 * 修改网站
	 * @param form    网站表单对象
	 * @param siteId  当前网站id
	 * @param sessionID当前用户id
	 */
	void modifySite(Site site, String siteId, String sessionID);
}
