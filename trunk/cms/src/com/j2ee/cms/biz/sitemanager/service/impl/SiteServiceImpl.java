/**
 * project：通用内容管理系统
 * Company:   
*/
package com.j2ee.cms.biz.sitemanager.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.j2ee.cms.biz.configmanager.dao.SystemLogDao;
import com.j2ee.cms.biz.documentmanager.dao.CategoryDao;
import com.j2ee.cms.biz.documentmanager.domain.AttachmentCategory;
import com.j2ee.cms.biz.documentmanager.domain.FlashCategory;
import com.j2ee.cms.biz.documentmanager.domain.JsCategory;
import com.j2ee.cms.biz.documentmanager.domain.PictureCategory;
import com.j2ee.cms.biz.publishmanager.service.remotepublish.client.FtpSender;
import com.j2ee.cms.biz.publishmanager.service.remotepublish.client.Sender;
import com.j2ee.cms.biz.sitemanager.dao.SiteDao;
import com.j2ee.cms.biz.sitemanager.domain.Site;
import com.j2ee.cms.biz.sitemanager.service.SiteService;
import com.j2ee.cms.biz.sitemanager.web.form.SiteForm;
import com.j2ee.cms.biz.templatemanager.dao.TemplateCategoryDao;
import com.j2ee.cms.biz.templatemanager.dao.TemplateDao;
import com.j2ee.cms.biz.templatemanager.dao.TemplateUnitStyleDao;
import com.j2ee.cms.biz.templatemanager.domain.TemplateCategory;
import com.j2ee.cms.biz.templatemanager.domain.TemplateUnitCategory;
import com.j2ee.cms.biz.templatemanager.domain.TemplateUnitStyle;
import com.j2ee.cms.biz.usermanager.dao.AssignmentDao;
import com.j2ee.cms.biz.usermanager.dao.AuthorityDao;
import com.j2ee.cms.biz.usermanager.dao.MenuDao;
import com.j2ee.cms.biz.usermanager.dao.MenuFunctionDao;
import com.j2ee.cms.biz.usermanager.dao.OperationDao;
import com.j2ee.cms.biz.usermanager.dao.ResourceDao;
import com.j2ee.cms.biz.usermanager.dao.RoleDao;
import com.j2ee.cms.biz.usermanager.dao.UserDao;
import com.j2ee.cms.biz.usermanager.domain.Assignment;
import com.j2ee.cms.biz.usermanager.domain.Menu;
import com.j2ee.cms.biz.usermanager.domain.Role;
import com.j2ee.cms.biz.usermanager.domain.User;
import com.j2ee.cms.sys.GlobalConfig;
import com.j2ee.cms.sys.SiteResource;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.util.CollectionUtil;
import com.j2ee.cms.common.core.util.FileUtil;
import com.j2ee.cms.common.core.util.SqlUtil;
import com.j2ee.cms.common.core.util.StringUtil;

/**
 * <p>标题: 网站服务接口实现</p>
 * <p>描述: 网站服务接口实现，列出网站处理中的一些方法</p>
 * <p>模块: 网站管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author <a href="mailto:xinyang921@gmail.com">郑荣华</a>
 * @version 1.0
 * @since 2009-3-13 下午03:30:41
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class SiteServiceImpl implements SiteService {

	private final Logger log = Logger.getLogger(SiteServiceImpl.class);
	
	/**注入网站dao**/
	private SiteDao siteDao;
	/** 注入菜单dao **/
	private MenuDao menuDao;
	/** 注入用户dao **/
	private UserDao userDao;
	/**注入角色dao**/
	private RoleDao roleDao;
	/** 注入资源dao*/
	private ResourceDao resourceDao;
	/** 注入操作dao**/
	private OperationDao operationDao;
	/**注入权限dao**/
	private AuthorityDao authorityDao;
	/**注入授权dao*/
	private AssignmentDao assignmentDao;
	/**注入模版类别dao**/
	private TemplateCategoryDao templateCategoryDao;
	/**注入模版dao*/
	private TemplateDao templateDao;
	/** 注入系统数据库日志dao */
	private SystemLogDao systemLogDao;
	/**注入类别的dao**/
	private CategoryDao categoryDao;
	/**注入模版样式dao*/
	private TemplateUnitStyleDao templateUnitStyleDao;
	/** 注入菜单功能权限dao */
	private MenuFunctionDao menuFunctionDao;
	
	public User findUserById(String userId){
		return userDao.getAndNonClear(userId);
	}

	/**
	 * 删除网站
	 * @param id      删除的网站id
	 * @param siteId  当前网站id
	 * @return        返回是否删除成功
	 */
	public String deleteSite(String id, String siteId, String userId) {
		String infoMessage = "";
		// 判断是否是主站, 主站不允许删除
		List<Site> list = siteDao.findByNamedQueryAndClear("findFirstLevelSiteTreeByPid");
		if(!CollectionUtil.isEmpty(list)) {
			String mainSiteId = list.get(0).getId();
			log.debug("mainSiteId============="+mainSiteId);
			if(mainSiteId.equals(id)) {
				infoMessage = "删除失败，主站不能删除！";
			}else if(id.equals(siteId)) {
				infoMessage = "不能删除当前正在使用的网站！";
			} else {
				Site site = siteDao.getAndClear(id);
				site.setDeleted(true);
				siteDao.updateAndClear(site);
				infoMessage = "删除网站成功";
				// 删除网站目录
				SiteResource.destorySiteDir(id);
				
				// 删除以网站名称命名的用户
				userDao.updateByDefine("updateSiteUser", "siteName", "'"+site.getName()+"'");
				
				// 写入删除网站日志
				String categoryName = "网站管理->删除";
				systemLogDao.addLogData(categoryName, siteId, userId, site.getName());
			}
		}
		return infoMessage;
		/*
		String infoMessage = "";
		Site site = siteDao.getAndNonClear(id);
		// 网站有子网站
		if(site.getChildren().size() > 0) {
			infoMessage = "此网站下有子网站，删除失败！";
		
		// 网站正在使用
		} else if(siteId.equals(id)) {
			infoMessage = "不能删除当前正在使用的网站！";
		
		// 正常删除	
		}else {
			String[] param = {"siteid", "type", "identifier"};
			Object[] value = {id, Resource.TYPE_COLUMN, "0"};
			List resourceList = resourceDao.findByNamedQuery("findResourceByIdentifierAndTypeAndSiteId", param, value);
			String deletedResouceIds = "";
			if(resourceList != null && resourceList.size() > 0) {
				deletedResouceIds = ((Resource)resourceList.get(0)).getId(); 
			}
			
			// 1.删除权限
			if(!StringUtil.isEmpty(deletedResouceIds)) {
				String deletedAuthorityIds = "";
				List authorityList = authorityDao.findByNamedQuery("findAuthorityByResourceid", "resourceId", deletedResouceIds);
				if(authorityList != null && authorityList.size() > 0) {
					for(int i = 0; i < authorityList.size(); i++) {
						Authority authority = (Authority) authorityList.get(i);
						deletedAuthorityIds += "," + authority.getId();
					}
				}
				deletedAuthorityIds = deletedAuthorityIds.replaceFirst(",", "");
				deletedAuthorityIds = SqlUtil.toSqlString(deletedAuthorityIds);
				authorityDao.deleteByIds(deletedAuthorityIds);
			}
			
			// 2.删除资源
			if(!StringUtil.isEmpty(deletedResouceIds)) {
				deletedResouceIds = SqlUtil.toSqlString(deletedResouceIds);
				resourceDao.deleteByIds(deletedResouceIds);
			}
			
			// 3. 查询角色
			List<Role> roleList = new ArrayList<Role>();
			String[] roleParams = {"roleName", "siteId"};
			Object[] roleValuse = {"网站管理员", id};
			roleList = roleDao.findByNamedQuery("findSiteAdminRoleByNameAndSiteId", roleParams, roleValuse);
			Role role = (Role) roleList.get(0);
			log.debug("======"+role.getId());
			
			// 4.查询网站管理员这个用户
			List<User> userList = new ArrayList<User>();
			String[] userParams = {"loginName"};
			Object[] userValues = {site.getName()};
			userList = userDao.findByNamedQuery("findUserByLoginName", userParams, userValues);
			User user = new User();
			user = userList.get(0);
			
			
			// 5.删除分配
			List<Assignment> assignmentList = new ArrayList<Assignment>();
			String[] assParams = {"userid", "roleid"};
			Object[] assValues = {user.getId(), role.getId()};
			assignmentList = assignmentDao.findByNamedQuery("findAssignmentByUseridAndRoleid", assParams, assValues);
			Assignment assignment = new Assignment();
			assignment = assignmentList.get(0);
			assignmentDao.deleteByKey(assignment.getId());
			
			// 写入用户日志
			String usercategoryName = "用户管理->删除";
			systemLogDao.addLogData(usercategoryName, siteId, userId, user.getName());
			
			
			// 6. 删除网站管理员用户
			userDao.deleteByKey(user.getId());
			
			// 写入角色日志
			String rolecategoryName = "角色管理->删除";
			systemLogDao.addLogData(rolecategoryName, siteId, userId, role.getName());
			
			
			// 7. 删除网站管理员这个角色
			roleDao.deleteByKey(role.getId());
			
			
			// 写入网站日志
			String categoryName = "网站管理->删除";
			systemLogDao.addLogData(categoryName, siteId, userId, site.getName());
			
			// 8.删除网站
			siteDao.deleteByKey(id);
			
			// 9.删除网站目录
			SiteResource.destorySiteDir(id);
			
			infoMessage = "删除网站成功！";
			
		}
		*/
//		return infoMessage;
	}
	
	public void modifyUserSite(String userId, String siteId) {
		User user = userDao.getAndNonClear(userId);
		List siteIds = user.getSiteIds();
		for (int i = 0; i < siteIds.size(); i++) {
			if (siteId.equals((String)siteIds.get(i))) {
				siteIds.remove(i);
			}
		}
		siteIds.add(0, siteId);
		user.setSiteIds(siteIds);
		userDao.update(user);
	}
	
	
	/**
	 * 判断网站名称是否重名
	 * @param form
	 * @return
	 */
	public String judgeSiteName(SiteForm form){
		String infoMessage = "";
		Site site = form.getSite();
		Site newSite = siteDao.getAndClear(site.getId());
		if(!newSite.getName().equals(site.getName())) {
			List list = siteDao.findByNamedQuery("findSiteByName", "siteName" ,site.getName());
			if(list != null && list.size() > 0) {
				infoMessage = "该网站名称已经存在";
				return infoMessage;
			}
		}
		return infoMessage;
	}

	
	/**
	 * 修改网站的发布
	 * @param form
	 * @return
	 */
	public Site getNewModifiedSite(SiteForm form){
		Site site = form.getSite();
		Site newSite = siteDao.getAndClear(site.getId());
		newSite.setName(site.getName());
		newSite.setDescription(site.getDescription());
		newSite.setHomePageTitle(site.getHomePageTitle());
		newSite.setPageEncoding(site.getPageEncoding());
		newSite.setDomainName(site.getDomainName());
		newSite.setUrlSuffix(site.getUrlSuffix());
		newSite.setPublishWay(site.getPublishWay());
		newSite.setFtpFilePath(site.getFtpFilePath());
		newSite.setFtpPassWord(site.getFtpPassWord());
		newSite.setFtpUserName(site.getFtpUserName());
		newSite.setPublishDir(site.getPublishDir());
		if (!FileUtil.isExist(site.getPublishDir())) {
			FileUtil.makeDirs(site.getPublishDir());
		}
		
		String frontJsDir = SiteResource.getFrontScriptDir(false);
		
		String siteDirWebinf =  SiteResource.getBuildStaticDir(site.getId(), false) + "/WEB-INF";
		if (Site.PUBLISH_LOCAL.equals(site.getPublishWay())) {
			//本地发布
			newSite.setPublishDir(site.getPublishDir());
			
			// 修改发布路径
			String publishDir = site.getPublishDir() + SiteResource.getFrontScriptDir(true);
			FileUtil.makeDirs(publishDir);
			
			FileUtil.copy(frontJsDir, publishDir, false);
			String path = site.getPublishDir();
			path = path.replaceAll("\\\\", "/");
			String temp[] = path.split("/");
			String newPath = "";
			if(temp.length > 0){
				newPath = path.replace("/"+temp[temp.length-1], "");
			}else{
				newPath = path;
			}
			if (!FileUtil.isExist(newPath+File.separator + "WEB-INF")) {
				FileUtil.copy(siteDirWebinf, newPath);
			}
			
		} else if (Site.PUBLISH_REMOTE.equals(site.getPublishWay())) {
			//socket发布
			newSite.setRemoteIP(site.getRemoteIP());
			newSite.setRemotePort(site.getRemotePort());
			
			try {
				List<String> fileList = new ArrayList<String>();
				fileList.addAll(FileUtil.getFiles(frontJsDir, false));
				new Sender(site.getRemoteIP(), site.getRemotePort(), fileList, GlobalConfig.appRealPath).start();
				
				if (!FileUtil.isExist(SiteResource.getTempDir(false)+File.separator+"WEB-INF.zip")) {
					FileUtil.zipDirectory(siteDirWebinf, SiteResource.getTempDir(false)+File.separator+"WEB-INF.zip");
				}
				fileList = new ArrayList<String>();
				fileList.add(SiteResource.getTempDir(false)+File.separator+"WEB-INF.zip");
				new Sender(site.getRemoteIP(), site.getRemotePort(), fileList, SiteResource.getTempDir(false)).start();
			} catch (Exception e) {
				if (StringUtil.contains(e.getMessage(), "publish")) {
					log.error(e.getMessage());
				}
			}
		}else if (Site.PUBLISH_FTP.equals(site.getPublishWay())){
			//ftp发布
			newSite.setRemoteIP(site.getRemoteIP());
			newSite.setRemotePort(site.getRemotePort());
			
			try {
				List<String> fileList = new ArrayList<String>();
				fileList.addAll(FileUtil.getFiles(frontJsDir, false));
				new FtpSender(site.getRemoteIP(), site.getRemotePort(),site.getFtpUserName(),site.getFtpPassWord(),site.getFtpFilePath(), fileList, GlobalConfig.appRealPath,"send").start();
			//	new Sender(site.getRemoteIP(), site.getRemotePort(), fileList, GlobalConfig.appRealPath).start();
				
				if (!FileUtil.isExist(SiteResource.getTempDir(false)+File.separator+"WEB-INF.zip")) {
					FileUtil.zipDirectory(siteDirWebinf, SiteResource.getTempDir(false)+File.separator+"WEB-INF.zip");
				}
				fileList = new ArrayList<String>();
				fileList.add(SiteResource.getTempDir(false)+File.separator+"WEB-INF.zip");
				String temp[] = site.getFtpFilePath().split(File.separator);
				String newPath = "";
				if(temp.length > 0){
					newPath = site.getFtpFilePath().replace(File.separator+temp[temp.length-1], "");
				}else{
					newPath = site.getFtpFilePath();
				}
				new FtpSender(site.getRemoteIP(), site.getRemotePort(),site.getFtpUserName(),site.getFtpPassWord(),newPath, fileList, SiteResource.getTempDir(false),"send").start();
 			//	new Sender(site.getRemoteIP(), site.getRemotePort(), fileList, SiteResource.getTempDir(false)).start();
			} catch (Exception e) {
				if (StringUtil.contains(e.getMessage(), "publish")) {
					log.error(e.getMessage());
				}
			}
			
		}
		Site.siteMap.put(newSite.getId(), newSite);
		return newSite;
	}
	
	/**
	 * 修改网站
	 * @param form    网站表单对象
	 * @param siteId  当前网站id
	 * @param sessionID当前用户id
	 */
	public void modifySite(Site site, String siteId, String sessionID){
		siteDao.update(site);
		
		String categoryName = "网站管理->修改";
		systemLogDao.addLogData(categoryName, siteId, sessionID, site.getName());
	}

	/**
	 * 分页显示网站
	 * @param pagination  传递一个分页对象
	 * @return            返回一个分页对象
	 */
	public Pagination findSiteData(Pagination pagination) {
		return siteDao.getPagination(pagination);
	}
	
	/**
	 * 获得父节点为空的网站信息
	 * @return   返回为空的网站的id
	 */
	public String getSiteIdByParentIdIsNull(){
		return String.valueOf(siteDao.findByNamedQuery("findParentSiteIsNull").get(0));
	}
	
	/**
	 * 查询要切换的网站
	 * @param siteId  当前网站id
	 * @param userId  当前用户id
	 * @return  返回所有网站信息
	 */
	public List<Site> findChangeSites(String siteId, String userId) {
		User user = userDao.getAndNonClear(userId);
		List siteIds = user.getSiteIds();
		//用户可以选择切换的网站
		List<Site> list = new ArrayList();
		for (int i = 0; i < siteIds.size(); i++) {
			//派出当前网站
			if (!siteId.equals((String)siteIds.get(i))) {
				Site site = siteDao.getAndClear((String)siteIds.get(i));
				if(!site.isDeleted()) {
					list.add(site);
				}
			}
		}
		return list;
	}
    
	/**
	 * 查询网站
	 * @param id    要查询的网站的id
	 * @return Site 返回一个网站的对象
	 */
	public Site findSiteBySiteId(String id){
		return (Site) siteDao.findByNamedQuery("findSiteById", "siteid", id).get(0);
	}

	/**
	 * 添加一个静态树
	 * @param treeid 获取树的id 
	 * @return       返回树的列表
	 */
	public List<Object> proccessAddTree(String treeid) {
		log.debug("添加一个静态树");
		List<Object> treeList = new ArrayList<Object>();
		if(treeid != null){
			Object[] object = new Object[4];
			object[0] = "1";
			object[1] = "网站管理";
			object[2] = "site.do?dealMethod=";
			object[3] = true;
			treeList.add(object);
		}
		return treeList;
	}

	/**
	 * 判断是否符合添加网站的条件，如果符合：添加，不符合：不添加
	 * @param site		  要添加的网站id
	 * @param sessionID   当前用户id
	 * @param siteId      当前网站的id
	 * @param flag        是否是第一次添加网站
	 * @return
	 */
	public Map<String, String> addSite(Site site, String sessionID, String siteId, boolean flag){
		log.debug("处理网站的添加操作");
		log.debug("maxSize==="+GlobalConfig.maxSite);
		
		Map<String, String> map = new HashMap<String, String>();
		String infoMessage = "";
		String addsiteId = "";
		
		// 1.首先判断是否超出最大网站数量
		if(!StringUtil.isEmpty(GlobalConfig.maxSite)) {
			int maxSite = StringUtil.parseInt(GlobalConfig.maxSite);
			List<Site> siteList = siteDao.findByNamedQuery("findSiteByDeleted");
			if(!CollectionUtil.isEmpty(siteList) && siteList.size() >= maxSite) {
				infoMessage = "您已经超出能建立的最大网站数,请与产品供应商联系";
			}
		} else {
			infoMessage = "您已经超出能建立的最大网站数,请与产品供应商联系";
		}
		
		// 2.判断网站名称是否存在，要保证不能重名
		if(StringUtil.isEmpty(infoMessage)){
			String name = site.getName();
			List sitelist = siteDao.findByNamedQuery("findSiteByName", "siteName", name);
			if(!CollectionUtil.isEmpty(sitelist)) {
				infoMessage = "添加失败，该网站名已经存在";
			} 
		}

		// 3.添加网站
		if(StringUtil.isEmpty(infoMessage)){
			siteDao.save(site);
			site.setUrl(SiteResource.getBuildStaticDir(site.getId(), true)+"/index.html");
			siteDao.updateAndClear(site);
			Site.siteMap.put(site.getId(), site);
			addsiteId = site.getId();
		}
		
		// 5.写入网站日志
		String categoryName = "网站管理->添加";
    	if(flag){
    		systemLogDao.addLogData(categoryName, addsiteId, sessionID, site.getName());
    	}else{
    		systemLogDao.addLogData(categoryName, siteId, sessionID, site.getName());
    	}
		
		// 6.返回网站添加信息
		map.put("infoMessage", infoMessage);
		map.put("newSiteId", addsiteId);
		
		return map;
	}
	
	/**
	 * 获得新增网站的信息
	 * @param siteId   新增网站的id
	 * @return
	 */
	public Site getNewAddSite(String siteId){
		return siteDao.getAndClear(siteId);
	}
	
	/**
	 * 初始化新增的网站目录信息
	 * @param site
	 */
	public void initSiteDir(Site site){
		// 初始化网站相关目录
		SiteResource.initSiteDir(site.getId());
		// 拷贝要使用的几个js文件到static目录下面                                                          analyzeArticleText.js
		String frontJsDir = SiteResource.getFrontScriptDir(false);
		
		//建立生成路径
		String buildStaticPath = SiteResource.getBuildStaticDir(site.getId(), false)+ "/script/client";
		FileUtil.makeDirs(buildStaticPath);
		// 拷贝js文件到preview目录下面
		String prewArticlePath = SiteResource.getPreviewDir(site.getId(), false);
		if (!FileUtil.isExist(prewArticlePath+"/script")) {
			FileUtil.makeDirs(prewArticlePath+"/script");
		}
		 
		FileUtil.copy(frontJsDir, prewArticlePath+"/script", true);

		//复制web-info内容到网站目录下
		String siteDirWebinf =  SiteResource.getBuildStaticDir(site.getId(), false) + "/WEB-INF";
		FileUtil.makeDirs(siteDirWebinf);
		String siteDirWebinflog =  SiteResource.getBuildStaticDir(site.getId(), false) + "/WEB-INF/log";
		FileUtil.makeDirs(siteDirWebinflog);
		//单独拷贝下面的目录到release/site网站ID此目录下
		FileUtil.copy(GlobalConfig.appRealPath + "/WEB-INF/classes",siteDirWebinf);
		FileUtil.copy(GlobalConfig.appRealPath + "/WEB-INF/conf",siteDirWebinf);
		FileUtil.copy(GlobalConfig.appRealPath + "/WEB-INF/lib",siteDirWebinf);
		FileUtil.copy(GlobalConfig.appRealPath + "/WEB-INF/tld",siteDirWebinf);
		FileUtil.copy(GlobalConfig.appRealPath + "/WEB-INF/struts-config.xml",siteDirWebinf);
		FileUtil.copy(GlobalConfig.appRealPath + "/WEB-INF/conf/client/applicationContext-common.xml",siteDirWebinf);		
		FileUtil.copy(GlobalConfig.appRealPath + "/WEB-INF/conf/client/web.xml",siteDirWebinf);
		if(FileUtil.isExist(siteDirWebinf+"/classes/applicationContext-common.xml")) {
			FileUtil.delete(siteDirWebinf+"/classes/applicationContext-common.xml");
			FileUtil.copy(GlobalConfig.appRealPath + "/WEB-INF/conf/client/applicationContext-common.xml",siteDirWebinf+"/classes");
		}
		
		if (!FileUtil.isExist(site.getPublishDir())) {
			FileUtil.makeDirs(site.getPublishDir());
		}

		//建立发布路径
		if (Site.PUBLISH_LOCAL.equals(site.getPublishWay())) {
			String publishDir = site.getPublishDir() + SiteResource.getFrontScriptDir(true);
			//创建发布目录的JS目录
			FileUtil.makeDirs(publishDir); 
//			String webinfDir = site.getPublishDir() + SiteResource.getClientWebInfDir(true);
			String webinfDir = site.getPublishDir().substring(0, site.getPublishDir().length()-site.getPublishDir().split("/")[site.getPublishDir().split("/").length-1].length()) + SiteResource.getClientWebInfDir(true);
			if(!FileUtil.isExist(webinfDir)){
				//创建发布目录的web-inf目录
				FileUtil.makeDirs(webinfDir);
				//拷贝web-inf下面的内容到发布目录
				FileUtil.copy(GlobalConfig.appRealPath + "/WEB-INF/classes",webinfDir);
				FileUtil.copy(GlobalConfig.appRealPath + "/WEB-INF/conf",webinfDir);
				FileUtil.copy(GlobalConfig.appRealPath + "/WEB-INF/lib",webinfDir);
				FileUtil.copy(GlobalConfig.appRealPath + "/WEB-INF/tld",webinfDir);
				FileUtil.copy(GlobalConfig.appRealPath + "/WEB-INF/struts-config.xml",webinfDir);
			//	FileUtil.copy(GlobalConfig.appRealPath + "/WEB-INF/conf/client",webinfDir);  
				FileUtil.copy(GlobalConfig.appRealPath + "/WEB-INF/conf/client/web.xml",webinfDir);  
			}
			if(FileUtil.isExist(webinfDir+"/classes/applicationContext-common.xml")) {
				FileUtil.delete(webinfDir+"/classes/applicationContext-common.xml");
				FileUtil.copy(GlobalConfig.appRealPath + "/WEB-INF/conf/client/applicationContext-common.xml",webinfDir+"/classes");
			}
			//网站发布的根目录
			String rootDir = site.getPublishDir().substring(0, site.getPublishDir().length()-site.getPublishDir().split("/")[site.getPublishDir().split("/").length-1].length());
			if(!FileUtil.isExist(rootDir+"/log")){
				FileUtil.makeDirs(rootDir+"/log");
			}
			FileUtil.copy(frontJsDir, rootDir, true);
			String publishDirWebinflog = site.getPublishDir().substring(0, site.getPublishDir().length()-site.getPublishDir().split("/")[site.getPublishDir().split("/").length-1].length()) + SiteResource.getClientWebInfDir(true) + "/log";
			if(!FileUtil.isExist(publishDirWebinflog)){
				FileUtil.makeDirs(publishDirWebinflog);
				//拷贝JS文件到发布目录
				FileUtil.copy(frontJsDir, publishDir, false);
			}
			
		} else if (Site.PUBLISH_REMOTE.equals(site.getPublishWay())) {	
			//socket发布
			try {
				List<String> fileList = new ArrayList<String>();
				fileList.addAll(FileUtil.getFiles(frontJsDir, false));
				//先发布js相关目录及文件到远程服务器
				new Sender(site.getRemoteIP(), site.getRemotePort(), fileList, GlobalConfig.appRealPath).start();
				//先判断是否存在压缩文件，如果不存在，就需要压缩文件
				if (!FileUtil.isExist(SiteResource.getTempDir(false)+"/WEB-INF.zip")) {
					FileUtil.zipDirectory(siteDirWebinf + "/WEB-INF", SiteResource.getTempDir(false)+"/WEB-INF.zip");
				}
				fileList = new ArrayList<String>();
				fileList.add(SiteResource.getTempDir(false)+"/WEB-INF.zip");
				new Sender(site.getRemoteIP(), site.getRemotePort(), fileList, SiteResource.getTempDir(false)).start();
			} catch (Exception e) {
				if (StringUtil.contains(e.getMessage(), "publish")) {
					log.error(e.getMessage());
				}
			}
		}else if (Site.PUBLISH_FTP.equals(site.getPublishWay())) {
			//ftp发布
			 
			try {
				List<String> fileList = new ArrayList<String>();
				fileList.addAll(FileUtil.getFiles(frontJsDir, false));
				//先发布js相关目录及文件到远程服务器
			//	new Sender(site.getRemoteIP(), site.getRemotePort(), fileList, GlobalConfig.appRealPath).start();
				new FtpSender(site.getRemoteIP(), site.getRemotePort(),site.getFtpUserName(),site.getFtpPassWord(),site.getFtpFilePath(), fileList, GlobalConfig.appRealPath,"send").start();
				//先判断是否存在压缩文件，如果不存在，就需要压缩文件
				if (!FileUtil.isExist(SiteResource.getTempDir(false)+"/WEB-INF.zip")) {
					FileUtil.zipDirectory(siteDirWebinf, SiteResource.getTempDir(false)+"/WEB-INF.zip");
				}
				fileList = new ArrayList<String>();
				fileList.add(SiteResource.getTempDir(false)+"/WEB-INF.zip");
				String temp[] = site.getFtpFilePath().split("/");
				String newPath = "";
				if(temp.length > 0){
					newPath = site.getFtpFilePath().replace("/"+temp[temp.length-1], "");
				}else{
					newPath = site.getFtpFilePath();
				}
				
			//	new Sender(site.getRemoteIP(), site.getRemotePort(), fileList, SiteResource.getTempDir(false)).start();
				new FtpSender(site.getRemoteIP(), site.getRemotePort(),site.getFtpUserName(),site.getFtpPassWord(),newPath, fileList, SiteResource.getTempDir(false),"send").start();
			} catch (Exception e) {
				if (StringUtil.contains(e.getMessage(), "publish")) {
					log.error(e.getMessage());
				}
			}
		}
	}
	
	
	/**
	 * 处理网站的添加操作
	 * @param site		 获得一个网站对象
	 * @param sessionID  用户id 
	 * @param siteId     当前网站id
	 * @param flag		 标示是否是第一次创建网站
	 * @return
	 */
	public void addInitSiteInfo(Site site, String sessionID) {
		
		// 1.修改管理员拥有的网站id
		User user = userDao.getAndClear("u1");
		List siteList = user.getSiteIds();
		if(siteList == null) {
			siteList = new ArrayList();
		}
		siteList.add(site.getId());
		user.setSiteIds(siteList);
		userDao.update(user);
			
		// 2. 添加一个角色（网站管理员）
		Role role = new Role();
		role.setName("网站管理员");
		role.setDescription("拥有当前网站的所有权限");
		role.setSite(site); 
		role.setCreateTime(new Date());
		roleDao.save(role);
		User operator = userDao.getAndClear(sessionID);
		
		//3.添加三个模板类别
		TemplateCategory templateCategory1 = new TemplateCategory();
		templateCategory1.setName("首页模板");
		User templateCategorCreator = new User();
		templateCategorCreator.setId(sessionID);
		Site templateCategorySite = new Site();
		templateCategorySite.setId(site.getId());
    	templateCategory1.setCreator(templateCategorCreator);
    	templateCategory1.setSite(templateCategorySite);
    	templateCategoryDao.save(templateCategory1);
    	
    	TemplateCategory templateCategory2 = new TemplateCategory();
		templateCategory2.setName("栏目页模板");
    	templateCategory2.setCreator(templateCategorCreator);
    	templateCategory2.setSite(templateCategorySite);
    	templateCategoryDao.save(templateCategory2);
    	
    	TemplateCategory templateCategory3 = new TemplateCategory();
		templateCategory3.setName("文章页模板");
    	templateCategory3.setCreator(templateCategorCreator);
    	templateCategory3.setSite(templateCategorySite);
    	templateCategoryDao.save(templateCategory3);
    	
    	// 4.添加文档默认类别
    	PictureCategory pictureCategory = new PictureCategory();
    	PictureCategory pictureCategory1 = new PictureCategory();
		FlashCategory flashCategory = new FlashCategory();
		FlashCategory flashCategory1 = new FlashCategory();
		AttachmentCategory attachmentCategory = new AttachmentCategory();
		AttachmentCategory attachmentCategory1 = new AttachmentCategory();
		JsCategory jsCategory = new JsCategory();
		JsCategory jsCategory1 = new JsCategory();
		Date date = new Date();
		
    	
    	// 4-1. 添加图片默认类别（个人类别、抓取类别）
		pictureCategory.setName("个人类别");
		pictureCategory.setCreator(operator);
		pictureCategory.setSite(site);
		pictureCategory.setCreateTime(date);
		categoryDao.saveAndClear(pictureCategory);
		pictureCategory1.setName("抓取类别");
		pictureCategory1.setCreator(operator);
		pictureCategory1.setSite(site);
		pictureCategory1.setCreateTime(date);
		categoryDao.save(pictureCategory1);
		
    	// 4-2. 添加flash默认类别（个人类别、抓取类别）
		flashCategory.setName("个人类别");
		flashCategory.setCreator(operator);
		flashCategory.setSite(site);
		flashCategory.setCreateTime(date);
		categoryDao.saveAndClear(flashCategory);
		flashCategory1.setName("抓取类别");
		flashCategory1.setCreator(operator);
		flashCategory1.setSite(site);
		flashCategory1.setCreateTime(date);
		categoryDao.save(flashCategory1);
		
    	// 4-3. 添加附件默认类别（个人类别、抓取类别）
		attachmentCategory.setName("个人类别");
		attachmentCategory.setCreator(operator);
		attachmentCategory.setSite(site);
		attachmentCategory.setCreateTime(date);
		categoryDao.saveAndClear(attachmentCategory);
		attachmentCategory1.setName("抓取类别");
		attachmentCategory1.setCreator(operator);
		attachmentCategory1.setSite(site);
		attachmentCategory1.setCreateTime(date);
		categoryDao.save(attachmentCategory1);
		
		// 4-4. 添加js默认类别（个人类别、抓取类别）
		jsCategory.setName("个人类别");
		jsCategory.setCreator(operator);
		jsCategory.setSite(site);
		jsCategory.setCreateTime(date);
		categoryDao.saveAndClear(jsCategory);
		jsCategory1.setName("抓取类别");
		jsCategory1.setCreator(operator);
		jsCategory1.setSite(site);
		jsCategory1.setCreateTime(date);
		categoryDao.save(jsCategory1);
		
		// 5.添加模版设置的样式
		TemplateUnitCategory templateUnitCategory = new TemplateUnitCategory();
		TemplateUnitStyle templateUnitStyle = new TemplateUnitStyle();
		templateUnitStyle.setCreateTime(date);
		templateUnitStyle.setCreator(operator);
		templateUnitStyle.setSite(site);
		
		// 5-1.栏目链接
		templateUnitCategory.setId("t2");
		templateUnitStyle.setTemplateUnitCategory(templateUnitCategory);
		String columnLinkCategoryName = "模板设置(栏目链接)(样式管理)->添加";
		// 1.)
		templateUnitStyle.setId(null);
		templateUnitStyle.setName("当前栏目标题（不带链接）");
		templateUnitStyle.setContent("<table><!--for--><p><!--columnName--></p><!--/for--></table>");
		templateUnitStyle.setDisplayEffect("<img src=\"/" + GlobalConfig.appName + "/images/templateStyle/columnLinkStyle_1.jpg\"/>");
		templateUnitStyleDao.saveAndClear(templateUnitStyle);
		systemLogDao.addLogData(columnLinkCategoryName, site.getId(), sessionID, templateUnitStyle.getName());
		// 2.)
		templateUnitStyle.setId(null);
		templateUnitStyle.setName("当前栏目标题(带链接)");
		templateUnitStyle.setContent("<table><!--for--><a href=\"<!--columnLink-->\"><!--columnName--></a><!--/for--></table>");
		templateUnitStyle.setDisplayEffect("<img src=\"/" + GlobalConfig.appName + "/images/templateStyle/columnLinkStyle_2.jpg\"/>");
		templateUnitStyleDao.saveAndClear(templateUnitStyle);
		systemLogDao.addLogData(columnLinkCategoryName, site.getId(), sessionID, templateUnitStyle.getName());
		// 3.)
		templateUnitStyle.setId(null);
		templateUnitStyle.setName("带前缀多列显示");
		templateUnitStyle.setContent("<table border=\"0\" width=\"100%\"><!--for--><td align=\"center\"><!--columnPrefix--><a href=\"<!--columnLink-->\"><!--columnName--></a></td><!--/for--></table>");
		templateUnitStyle.setDisplayEffect("<img src=\"/" + GlobalConfig.appName + "/images/templateStyle/columnLinkStyle_3.jpg\"/>");
		templateUnitStyleDao.saveAndClear(templateUnitStyle);
		systemLogDao.addLogData(columnLinkCategoryName, site.getId(), sessionID, templateUnitStyle.getName());
		// 4.)
		templateUnitStyle.setId(null);
		templateUnitStyle.setName("带前缀多行一列居中");// 通过
		templateUnitStyle.setContent("<table border=\"0\" width=\"100%\"><!--for--><td align=\"center\" width=\"20\"><!--columnPrefix--></td><td align=\"left\"><a href=\"<!--columnLink-->\"><!--columnName--></a></td><!--/for--></table>");
		templateUnitStyle.setDisplayEffect("<img src=\"/" + GlobalConfig.appName + "/images/templateStyle/columnLinkStyle_4.jpg\"/>");
		templateUnitStyleDao.saveAndClear(templateUnitStyle);
		systemLogDao.addLogData(columnLinkCategoryName, site.getId(), sessionID, templateUnitStyle.getName());
		
		// 5-2.标题链接
		templateUnitCategory.setId("t3");
		templateUnitStyle.setTemplateUnitCategory(templateUnitCategory);
		String titleLinkCategoryName = "模板设置(标题链接)(样式管理)->添加";
		// 1.)
		templateUnitStyle.setId(null);
		templateUnitStyle.setName("标题控制字数并含\"更多\"");
		templateUnitStyle.setContent("<!--for--><!--titlePrefix--><a href=\"<!--articleurl-->\" title=\"<!--articletitle-->\" target=\"_blank\"><!--articletitleshort--></a><!--/for--><span style=\"float:right;clear:right;\"><!--more--></span>");
		templateUnitStyle.setDisplayEffect("<img src=\"/" + GlobalConfig.appName + "/images/templateStyle/titleLinkStyle_1.jpg\"/>");
		templateUnitStyleDao.saveAndClear(templateUnitStyle);
		systemLogDao.addLogData(titleLinkCategoryName, site.getId(), sessionID, templateUnitStyle.getName());
		// 2.)
		templateUnitStyle.setId(null);
		templateUnitStyle.setName("标题控制字数不含\"更多\"");
		templateUnitStyle.setContent("<!--for--><!--titlePrefix--><a href=\"<!--articleurl-->\" title=\"<!--articletitle-->\" target=\"_blank\"><!--articletitleshort--></a><!--/for-->");
		templateUnitStyle.setDisplayEffect("<img src=\"/" + GlobalConfig.appName + "/images/templateStyle/titleLinkStyle_2.jpg\"/>");
		templateUnitStyleDao.saveAndClear(templateUnitStyle);
		systemLogDao.addLogData(titleLinkCategoryName, site.getId(), sessionID, templateUnitStyle.getName());
		// 3.)
		templateUnitStyle.setId(null);
		templateUnitStyle.setName("标题控制字数带\"右对齐日期\"不含\"更多\"");
		templateUnitStyle.setContent("<!--for--><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td><!--titlePrefix--><a href=\"<!--articleurl-->\" title=\"<!--articletitle-->\" target=\"_blank\"><!--articletitleshort--></a></td><td width=\"80\" align=\"right\" style=\"color:#999999\"><!--year4-->-<!--month2-->-<!--day2--></td></tr></table><!--/for-->");
		templateUnitStyle.setDisplayEffect("<img src=\"/" + GlobalConfig.appName + "/images/templateStyle/titleLinkStyle_3.jpg\"/>");
		templateUnitStyleDao.saveAndClear(templateUnitStyle);
		systemLogDao.addLogData(titleLinkCategoryName, site.getId(), sessionID, templateUnitStyle.getName());
		// 4.)
		templateUnitStyle.setId(null);
		templateUnitStyle.setName("标题控制字数带\"标题日期\"含\"更多\"");
		templateUnitStyle.setContent("<!--for--><table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" ><tr><td style=\"width:12px;height:24px;line-height:24px;\"><!--titlePrefix--></td><td><a href=\"<!--articleurl-->\" title=\"<!--articletitle-->\" target=\"_blank\"><!--articletitleshort--></a><!--if--><!--createTime--><span>(<!--year4-->-<!--month2-->-<!--day2-->)</span><!--else--><!--/if--></td></tr></table><!--/for--><span style=\"float:right;clear:right;\"><!--more--></span>");
		templateUnitStyle.setDisplayEffect("<img src=\"/" + GlobalConfig.appName + "/images/templateStyle/titleLinkStyle_4.jpg\"/>");
		templateUnitStyleDao.saveAndClear(templateUnitStyle);
		systemLogDao.addLogData(titleLinkCategoryName, site.getId(), sessionID, templateUnitStyle.getName());
		// 5.)
		templateUnitStyle.setId(null);
		templateUnitStyle.setName("标题不控制字数并含\"更多\"");
		templateUnitStyle.setContent("<!--for--><!--titlePrefix--><a href=\"<!--articleurl-->\" title=\"<!--articletitle-->\" target=\"_blank\"><!--articletitle--></a><!--/for--><span style=\"float:right;clear:right;\"><!--more--></span>");
		templateUnitStyle.setDisplayEffect("<img src=\"/" + GlobalConfig.appName + "/images/templateStyle/titleLinkStyle_5.jpg\"/>");
		templateUnitStyleDao.saveAndClear(templateUnitStyle);
		systemLogDao.addLogData(titleLinkCategoryName, site.getId(), sessionID, templateUnitStyle.getName());
		
		// 5-3.当前位置
		templateUnitCategory.setId("t4");
		templateUnitStyle.setTemplateUnitCategory(templateUnitCategory);
		String currentLocationCategoryName = "模板设置(当前位置)(样式管理)->添加";
		// 1.)
		templateUnitStyle.setId(null);
		templateUnitStyle.setName("单箭头");
		templateUnitStyle.setContent("<a href=\"<!--siteLink-->\">首页</a><!--currentLocation--> >");
		templateUnitStyle.setDisplayEffect("<img src=\"/" + GlobalConfig.appName + "/images/templateStyle/currentLocationStyle_1.jpg\"/>");
		templateUnitStyleDao.saveAndClear(templateUnitStyle);
		systemLogDao.addLogData(currentLocationCategoryName, site.getId(), sessionID, templateUnitStyle.getName());
		// 2.)
		templateUnitStyle.setId(null);
		templateUnitStyle.setName("双箭头");
		templateUnitStyle.setContent("<a href=\"<!--siteLink-->\">首页</a><!--currentLocation--> >>");
		templateUnitStyle.setDisplayEffect("<img src=\"/" + GlobalConfig.appName + "/images/templateStyle/currentLocationStyle_2.jpg\"/>");
		templateUnitStyleDao.saveAndClear(templateUnitStyle);
		systemLogDao.addLogData(currentLocationCategoryName, site.getId(), sessionID, templateUnitStyle.getName());
		
		// 5-4.图片新闻
		templateUnitCategory.setId("t5");
		templateUnitStyle.setTemplateUnitCategory(templateUnitCategory);
		String pictureNewsCategoryName = "模板设置(图片新闻)(样式管理)->添加";
		// 1.)
		templateUnitStyle.setId(null);
		templateUnitStyle.setName("图片新闻FLASH分页滚动显示");
		templateUnitStyle.setContent("<script type=\"text/javascript\">var _width = 259;var _height = 220;var text_height = 22;var flash_height = _height + text_height;var pic_arr = new Array();var link_arr = new Array();var text_arr = new Array();var i = 0;<!--for-->pic_arr[i] = \"<!--pic1-->\";link_arr[i] = \"<!--articleurl-->\";text_arr[i] = \"<!--articletitleshort-->\";i++;<!--/for-->var p = pic_arr.join(\"|\");var l = link_arr.join(\"|\");var t = text_arr.join(\"|\");var f = \"/release/pictureNews.swf\";document.write('<object ID=\"focus_flash\" classid=\"clsid:d27cdb6e-ae6d-11cf-96b8-444553540000\" codebase=\"http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0\" width=\"'+ _width +'\" height=\"'+ flash_height +'\">');document.write('<param name=\"allowScriptAccess\" value=\"sameDomain\"><param name=\"movie\" value=\"'+f+'\"><param name=\"quality\" value=\"high\"><param name=\"bgcolor\">');document.write('<param name=\"menu\" value=\"false\"><param name=wmode value=\"transparent\">');document.write('<param name=\"FlashVars\" value=\"pics='+p+'&links='+l+'&texts='+t+'&borderwidth='+_width+'&borderheight='+_height+'&textheight='+text_height+'\">');document.write('<embed ID=\"focus_flash\" src=\"'+f+'\" wmode=\"opaque\" FlashVars=\"pics='+p+'&links='+l+'&texts='+t+'&borderwidth='+_width+'&borderheight='+_height+'&textheight='+text_height+'\" menu=\"false\" quality=\"high\" width=\"'+ _width +'\" height=\"'+ flash_height +'\" allowScriptAccess=\"sameDomain\" type=\"application/x-shockwave-flash\" pluginspage=\"http://www.macromedia.com/go/getflashplayer\" />');document.write('</object>');</script>");
		templateUnitStyle.setDisplayEffect("<img src=\"/" + GlobalConfig.appName + "/images/templateStyle/pictureNewsStyle_5.jpg\"/>");
		templateUnitStyleDao.saveAndClear(templateUnitStyle);
		// 2.)
		templateUnitStyle.setId(null);
		templateUnitStyle.setName("单条图片新闻标题控制字数");
		templateUnitStyle.setContent("<table><!--for--><table cellpadding=\"0\" cellspacing=\"1\" border=\"0\" bgcolor=\"#000000\"><tr><td bgcolor=\"#FF0000\"><!--if--><!--pic1--><a href=\"<!--articleurl-->\" target=\"_blank\"><img  src=\"<!--pic1-->\" border=\"0\" width=\"180\" height=\"160\"></a><!--else--><!--/if--></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td><a href=\"<!--articleurl-->\" target=\"_blank\"><!--articletitleshort--></a></td></tr></table><!--/for--></table>");
		templateUnitStyle.setDisplayEffect("<img src=\"/" + GlobalConfig.appName + "/images/templateStyle/pictureNewsStyle_1.jpg\"/>");
		templateUnitStyleDao.saveAndClear(templateUnitStyle);
		systemLogDao.addLogData(pictureNewsCategoryName, site.getId(), sessionID, templateUnitStyle.getName());
		// 3.)
		templateUnitStyle.setId(null);
		templateUnitStyle.setName("单条图片新闻右侧显示文章摘要");
		templateUnitStyle.setContent("<table><!--for--><table width=\"100%\"  border=\"0\" cellpadding=\"3\" cellspacing=\"3\"><tr><td width=\"170\" bgcolor=\"#FFFFFF\"><!--if--><!--pic1--><a href=\"<!--articleurl-->\" target=\"_blank\"><IMG SRC=\"<!--pic1-->\" border=\"0\"  width=\"180\" height=\"120\"></a><!--else--><!--/if--></td><td rowspan=\"2\" valign=\"top\"><p  class=\"bt_link\"><!--articlebrief--></p></td></tr><tr><td align=\"center\"><a href=\"<!--articleurl-->\" target=\"_blank\"><!--articletitleshort--></a></td></tr></table><!--/for--></table>");
		templateUnitStyle.setDisplayEffect("<img src=\"/" + GlobalConfig.appName + "/images/templateStyle/pictureNewsStyle_2.jpg\"/>");
		templateUnitStyleDao.saveAndClear(templateUnitStyle);
		systemLogDao.addLogData(pictureNewsCategoryName, site.getId(), sessionID, templateUnitStyle.getName());
		// 4.)
		templateUnitStyle.setId(null);
		templateUnitStyle.setName("图片新闻含标题作者时间摘要");     
		templateUnitStyle.setContent("<table><!--for--><TABLE width=\"99%\" cellspacing=\"3\" cellpadding=\"3\" border=\"0\"><TR><TD width=\"10%\"><!--if--><!--pic1--><a href=\"<!--articleurl-->\"><IMG SRC=\"<!--pic1-->\" width=\"120\" height=\"130\" BORDER=\"0\"></a><!--else--><!--/if--></TD><TD width=\"90%\">[标题]<a href=\"<!--articleurl-->\"><!--articletitleshort--></a><BR><!--if--><!--articleauthor-->[作者] <!--articleauthor--><!--else--><!--/if--><BR><!--if--><!--createTime-->[时间]<span><!--year4-->-<!--month2-->-<!--day2--></span><!--else--><!--/if--><BR><!--if--><!--articlebrief-->[摘要]<!--articlebrief--><!--else--><!--/if--></TD></TR></TABLE><!--/for--></table>");
		templateUnitStyle.setDisplayEffect("<img src=\"/" + GlobalConfig.appName + "/images/templateStyle/pictureNewsStyle_3.jpg\"/>");
		templateUnitStyleDao.saveAndClear(templateUnitStyle);
		systemLogDao.addLogData(pictureNewsCategoryName, site.getId(), sessionID, templateUnitStyle.getName());
		// 5.)
		templateUnitStyle.setId(null);
		templateUnitStyle.setName("图片新闻不间断滚动");
		templateUnitStyle.setContent("<div id=\"demo\" style=\"OVERFLOW: hidden; WIDTH: 650px;\" align=\"center\"><table cellspacing=\"0\" cellpadding=\"0\" align=\"center\" border=\"0\"><tbody><tr><td id=\"marquePic1\" valign=\"top\"><table><!--for--><TABLE><TR><TD><!--if--><!--pic1--><a href=\"<!--articleurl-->\"><IMG SRC=\"<!--pic1-->\" BORDER=\"0\" width=\"120\" height=\"70\"></a><!--else--><!--/if--></TD></TR><tr><td align=\"center\"><a href=\"<!--articleurl-->\"><!--articletitleshort--></a></td></tr></TABLE><!--/for--></table></td><td id=\"marquePic2\" valign=\"top\"></td></tr></tbody></table></div><script language=\"javascript\" type=\"text/javascript\">var speed=\"30\";marquePic2.innerHTML=marquePic1.innerHTML; function Marquee(){ if(demo.scrollLeft>=marquePic1.scrollWidth){ demo.scrollLeft=\"0\"; }else{ demo.scrollLeft++; }}; var MyMar=setInterval(Marquee,speed); demo.onmouseover=function() {clearInterval(MyMar)}; demo.onmouseout=function() {MyMar=setInterval(Marquee,speed)};</script>");
		templateUnitStyle.setDisplayEffect("<img src=\"/" + GlobalConfig.appName + "/images/templateStyle/pictureNewsStyle_4.jpg\"/>");
		templateUnitStyleDao.saveAndClear(templateUnitStyle);
		systemLogDao.addLogData(pictureNewsCategoryName, site.getId(), sessionID, templateUnitStyle.getName());
		
		// 5-5.文章正文
		templateUnitCategory.setId("t7");
		templateUnitStyle.setTemplateUnitCategory(templateUnitCategory);
		String articleTextCategoryName = "模板设置(文章正文)(样式管理)->添加";
		// 1.)
		templateUnitStyle.setId(null);
		templateUnitStyle.setName("正文含日期、信息来源、字号、打印本页、关闭窗口、文章评论");
		templateUnitStyle.setContent("<script language=\"javascript\">function doZoom(size){document.getElementById(\"zoom\").style.fontSize=size+\"px\";}</script><!--for--><table width=\"90%\" align=\"center\"><tr><td align=\"center\"><!--articletitle--><br><hr size=\"1\" noshade color=\"#dddddd\"></td></tr><tr><td><table border=\"0\" align=\"center\" width=\"90%\"><tr><td width=\"25%\">发布日期：<!--year4-->-<!--month2-->-<!--day2--></td><td><!--if--><!--articlesource-->信息来源：<!--articlesource--><!--else--><!--/if--></td><td width=\"25%\">字号：[ <a href=\"javascript:doZoom(16)\">大</a> <a href=\"javascript:doZoom(14)\">中</a> <a href=\"javascript:doZoom(12)\">小</a>]</td></tr></table><BR><BR></td></tr><tr><td ><div id=\"zoom\"><!--if--><!--pic--><table align=\"center\"><tr><td><img src=\"<!--pic-->\" border=\"0\"></td></tr></table><!--else--><!--/if--><!--textArea--></div></td></tr><tr><td align=\"left\" height=\"10\" style=\"padding-left:60px;\"><br><!--if--><!--attach--><a href=\"<!--attach-->\" style=\"font-size:10.5pt;\"><strong>[附件下载]</strong></a><!--else--><!--/if--><br></td></tr></table><BR><BR><table width=\"90%\" align=\"center\"><tr><td align=\"right\" style=\"font-size:10.5pt\"><!--articleComment--><a href=\"javascript:window.print()\" style=\"font-size:10.5pt\">打印本页</a> <a href=\"javascript:window.close()\" style=\"font-size:10.5pt\">关闭窗口</a></td></tr></table><!--/for--><a href=\"/tvcp/commitComment.do?dealMethod=commentList&articleId=<!--articleId-->\"><!--articleComment--></a>");
		templateUnitStyle.setDisplayEffect("<img src=\"/" + GlobalConfig.appName + "/images/templateStyle/articleTextStyle_1.jpg\"/>");
		templateUnitStyleDao.saveAndClear(templateUnitStyle);
		systemLogDao.addLogData(articleTextCategoryName, site.getId(), sessionID, templateUnitStyle.getName());
		// 2.)
		templateUnitStyle.setId(null);
		templateUnitStyle.setName("正文含日期、字号、打印本页、关闭窗口");
		templateUnitStyle.setContent("<script language=\"javascript\">function doZoom(size){document.getElementById(\"zoom\").style.fontSize=size+\"px\";}</script><!--for--><table width=\"100%\"  id=\"article\" cellspacing=\"0\" cellpadding=\"0\"><tr><td align=\"center\" height=\"50\" style=\"padding:15px\"><!--articletitle--></td></tr><tr><td><table width=\"90%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#F3F3F3\"><tr height=\"26\"><td width=\"30%\" align=\"right\" nowrap><span style=\"padding-left:25px;\">发布日期：<!--year4-->-<!--month2-->-<!--day2--></span></td><td width=\"11%\" align=\"left\" nowrap style=\"padding-left:25px;\">字号：[ <a href=\"javascript:doZoom(16)\">大</a> <a href=\"javascript:doZoom(14)\">中</a> <a href=\"javascript:doZoom(12)\">小</a> ]</td><td width=\"14%\" align=\"left\"></td></tr><tr><td colspan=\"4\" height=\"1\" bgcolor=\"#E3E3E3\"></td></tr></table></td></tr><tr><td style=\"padding-left:50px;padding-right:50px;padding-top:20px;font-size:10.5pt; line-height:26px;\" class=\"bt_content\" height=\"280\" valign=\"top\"><div id=\"zoom\"><!--if--><!--pic1--><table align=\"center\"><tr><td><img src=\"<!--pic1-->\" border=\"0\"></td></tr></table><!--else--><!--/if--><!--textArea--></div></td></tr><tr><td align=\"left\" height=\"10\" style=\"padding-left:60px;\"><br><!--if--><!--attach--><a href=\"<!--attach-->\" style=\"font-size:10.5pt;\"><strong>[附件下载]</strong></a><!--else--><!--/if--><br><br></td></tr><tr><td height=\"26\" align=\"right\" valign=\"top\" style=\"padding-right:40px\"><a href=\"javascript:window.print()\" style=\"font-size:10.5pt\">打印本页</a> <a href=\"javascript:window.close()\" style=\"font-size:10.5pt\">关闭窗口</a></td></tr><tr><td height=\"26\" align=\"right\" valign=\"top\" style=\"padding-right:40px\"></td></tr></table><!--/for-->");
		templateUnitStyle.setDisplayEffect("<img src=\"/" + GlobalConfig.appName + "/images/templateStyle/articleTextStyle_2.jpg\"/>");
		templateUnitStyleDao.saveAndClear(templateUnitStyle);
		systemLogDao.addLogData(articleTextCategoryName, site.getId(), sessionID, templateUnitStyle.getName());
		
		// 5-6.期刊（按分类）
		templateUnitCategory.setId("t8");
		templateUnitStyle.setTemplateUnitCategory(templateUnitCategory);
		String magazineCategoryName = "模板设置(期刊分类)(样式管理)->添加";
		// 1.)
		templateUnitStyle.setId(null);
		templateUnitStyle.setName("期刊(按分类)样式");
		templateUnitStyle.setContent("<b><!--categoryName--></b><br><!--for-->→<a href=\"<!--url-->\" title=\"<!--title-->\"><!--title--></a><br><!--/for-->");
		templateUnitStyle.setDisplayEffect("<B>分类1</B> <BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;→ <U style=\"FONT-WEIGHT: normal; FONT-SIZE: 12px; LINE-HEIGHT: normal; FONT-STYLE: normal; FONT-VARIANT: normal\">文章标题1...&nbsp;</U><BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;→ <U style=\"FONT-WEIGHT: normal; FONT-SIZE: 12px; LINE-HEIGHT: normal; FONT-STYLE: normal; FONT-VARIANT: normal\">文章标题2...</U><BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<BR><B>分类2</B> <BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;→ <U style=\"FONT-WEIGHT: normal; FONT-SIZE: 12px; LINE-HEIGHT: normal; FONT-STYLE: normal; FONT-VARIANT: normal\">文章标题1...</U><BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;→ <U style=\"FONT-WEIGHT: normal; FONT-SIZE: 12px; LINE-HEIGHT: normal; FONT-STYLE: normal; FONT-VARIANT: normal\">文章标题2...</U> ");
		templateUnitStyleDao.saveAndClear(templateUnitStyle);
		systemLogDao.addLogData(magazineCategoryName, site.getId(), sessionID, templateUnitStyle.getName());
		
		//标题链接分页
		templateUnitCategory.setId("t9");
		templateUnitStyle.setTemplateUnitCategory(templateUnitCategory);
		String titleLinkPageCategoryName = "模板设置(标题链接分页)(样式管理)->添加";
		// 1.)
		templateUnitStyle.setId(null);
		templateUnitStyle.setName("标题链接分页样式");
		templateUnitStyle.setContent("<!--for--><!--titlePrefix--><a href=\"<!--articleurl-->\" title=\"<!--articletitle-->\" ><!--articletitleshort--></a> <span><!--year4-->-<!--month2-->-<!--day2--></span><!--/for-->");
		templateUnitStyle.setDisplayEffect("<img src=\"/" + GlobalConfig.appName + "/images/templateStyle/titleLinkStyle_3.jpg\"/>");
		templateUnitStyleDao.saveAndClear(templateUnitStyle);
		systemLogDao.addLogData(titleLinkPageCategoryName, site.getId(), sessionID, templateUnitStyle.getName());
		
		
		// 6.写入日志
    	// 6-1.写入文档日志
    	String pictureCategoryName = "文档管理（图片类别）->添加";
    	String flashCategoryName = "文档管理（flash类别）->添加";
    	String attachCategoryName = "文档管理（附件类别）->添加";
    	String jsCategoryName = "文档管理（js类别）->添加";
    	systemLogDao.addLogData(pictureCategoryName, site.getId(), sessionID, "个人类别");
    	systemLogDao.addLogData(pictureCategoryName, site.getId(), sessionID, "抓取类别");
    	systemLogDao.addLogData(flashCategoryName, site.getId(), sessionID, "个人类别");
    	systemLogDao.addLogData(flashCategoryName, site.getId(), sessionID, "抓取类别");
    	systemLogDao.addLogData(attachCategoryName, site.getId(), sessionID, "个人类别");
    	systemLogDao.addLogData(attachCategoryName, site.getId(), sessionID, "抓取类别");
    	systemLogDao.addLogData(jsCategoryName, site.getId(), sessionID, "个人类别");
    	systemLogDao.addLogData(jsCategoryName, site.getId(), sessionID, "抓取类别");
    	
    	// 6-2.写入模版类别日志
    	String templateCategoryName = "模板管理（类别管理）->添加";
    	systemLogDao.addLogData(templateCategoryName, site.getId(), sessionID, templateCategory1.getName());
    	systemLogDao.addLogData(templateCategoryName, site.getId(), sessionID, templateCategory2.getName());
    	systemLogDao.addLogData(templateCategoryName, site.getId(), sessionID, templateCategory3.getName());
    	// 6-3.写入角色日志
		String roleCategoryName = "角色管理->添加";
		String roleParam = role.getName();
		systemLogDao.addLogData(roleCategoryName, site.getId(), sessionID, roleParam);
		
	}
	
	/**
	 * 根据用户id返回用户信息
	 * @param sessionID 用户id
	 * @return          返回用户对象
	 */
	public User findUser(String sessionID) {
		return userDao.getAndClear(sessionID);
	}
	
	/**
	 * 修改网站默认管理员登录名00
	 * @param site 
	 * @param oldSiteName
	 * @param siteId
	 * @param sessionID
	 * @return          返回用户对象
	 */
	public void modifySiteManagerLoginName(Site site, String oldSiteName, String siteId, String sessionID) {
		String siteName = site.getName();
		List<User> list = userDao.findByNamedQuery("findUserByLoginName", "loginName", oldSiteName);
		if(!CollectionUtil.isEmpty(list)){
			User user = list.get(0);
			user.setLoginName(siteName);
			userDao.update(user);
			
			// 写入用户日志
			String categoryName = "用户管理->修改";
			String param = user.getName();
			systemLogDao.addLogData(categoryName, siteId, sessionID, param);
		}
	}
	
	public List<Menu> getAllMenus() {
		return menuDao.findAll();
	}
	
	//删除初始化使得模板类别
	public String deleteTemplateCategory(String siteId, String userId, String deletedSiteId) { 
		List<TemplateCategory> templateCategoryList = templateCategoryDao.findByNamedQuery("findTemplateCategoryBySiteId", "siteId", deletedSiteId);
		TemplateCategory templateCategory ;
		String templateCategoryName = "";
		String templateCategoryId = "";
		String infoMessage = "";
		for(int i = 0; i < templateCategoryList.size(); i++) {
			templateCategory = templateCategoryList.get(i);
			templateCategoryName = templateCategory.getName();
			templateCategoryId = templateCategory.getId();
			if(templateCategoryName.equals("首页模板") || templateCategoryName.equals("栏目页模板") || templateCategoryName.equals("文章页模板")) {
				List list = templateDao.findByNamedQuery("findTemplateByTemplateCategoryId", "templateCategoryId", templateCategoryId);
				if(list != null && list.size() > 0) {
					infoMessage = "网站下的模板类别被引用，删除失败";
					return infoMessage;
				} else {
					// 删除模版日志
					String categoryName = "模板管理（类别管理）->删除";
					String param = templateCategoryName;
					systemLogDao.addLogData(categoryName, siteId, userId, param);
					
					templateCategoryDao.deleteByIds(SqlUtil.toSqlString(templateCategoryId));
				}
			}
		}
		return infoMessage;
	}
	
	//查询当前用户拥有的角色所在的网站
	@SuppressWarnings("unchecked")
	public String findSiteOfRolesInByUserId(String userId) {
		String siteOfRolesIn = "";
		User user = userDao.getAndClear(userId);
		//如果是超级管理员可以切换所有网站
		if(user.getName().equals("超级管理员")) {
			siteOfRolesIn = "超级管理员";
			return siteOfRolesIn;
		}
		//如果是网站管理员不允许切换网站
		if(user.getName().equals("网站管理员")) {
			siteOfRolesIn = "网站管理员";
			return siteOfRolesIn;
		}
		//其他用户情况
		List<Assignment> list = assignmentDao.findByNamedQuery("findRoleIdByUserId", "id", userId);
		for(int i = 0; i< list.size(); i++) {
			Assignment obj = (Assignment) list.get(i);
			Role role = roleDao.getAndClear(obj.getRole().getId());
			log.debug("roleName ===="+ role.getName());
			if(!role.getName().equals("系统管理员")) {
				siteOfRolesIn += role.getSite().getId() + ",";
			}else{
				siteOfRolesIn = "系统管理员";
				break;
			}
		}
		return siteOfRolesIn;
	}
	
	/**
	 * @param userDao the userDao to set
	 */
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	/**
	 * @param menuDao the menuDao to set
	 */
	public void setMenuDao(MenuDao menuDao) {
		this.menuDao = menuDao;
	}
	
	/**
	 * @param siteDao  设置网站dao对象
	 */
	public void setSiteDao(SiteDao siteDao) {
		this.siteDao = siteDao;
	}
	
	/**
	 * @param roleDao the roleDao to set
	 */
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	/**
	 * @param resourceDao the resourceDao to set
	 */
	public void setResourceDao(ResourceDao resourceDao) {
		this.resourceDao = resourceDao;
	}

	/**
	 * @param operationDao the operationDao to set
	 */
	public void setOperationDao(OperationDao operationDao) {
		this.operationDao = operationDao;
	}

	/**
	 * @param authorityDao the authorityDao to set
	 */
	public void setAuthorityDao(AuthorityDao authorityDao) {
		this.authorityDao = authorityDao;
	}

	/**
	 * @param assignmentDao the assignmentDao to set
	 */
	public void setAssignmentDao(AssignmentDao assignmentDao) {
		this.assignmentDao = assignmentDao;
	}

	/**
	 * @return  templateCategoryDao the templateCategoryDao to set
	 */
	public void setTemplateCategoryDao(TemplateCategoryDao templateCategoryDao) {
		this.templateCategoryDao = templateCategoryDao;
	}

	/**
	 * @return  templateDao the templateDao to set
	 */
	public void setTemplateDao(TemplateDao templateDao) {
		this.templateDao = templateDao;
	}

	/**
	 * @param systemLogDao the systemLogDao to set
	 */
	public void setSystemLogDao(SystemLogDao systemLogDao) {
		this.systemLogDao = systemLogDao;
	}

	/**
	 * @param categoryDao the categoryDao to set
	 */
	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	/**
	 * @param templateUnitStyleDao the templateUnitStyleDao to set
	 */
	public void setTemplateUnitStyleDao(TemplateUnitStyleDao templateUnitStyleDao) {
		this.templateUnitStyleDao = templateUnitStyleDao;
	}

	/**
	 * @param menuFunctionDao the menuFunctionDao to set
	 */
	public void setMenuFunctionDao(MenuFunctionDao menuFunctionDao) {
		this.menuFunctionDao = menuFunctionDao;
	}
	
}
