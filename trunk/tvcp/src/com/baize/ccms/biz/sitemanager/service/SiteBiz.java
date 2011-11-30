/**
 * project：通用内容管理系统
 * Company:   
 */
package com.j2ee.cms.biz.sitemanager.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.j2ee.cms.biz.sitemanager.domain.Site;
import com.j2ee.cms.biz.sitemanager.web.form.SiteForm;
import com.j2ee.cms.biz.usermanager.domain.User;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.service.BaseService;
import com.j2ee.cms.common.core.util.FileUtil;
import com.j2ee.cms.common.core.util.StringUtil;
import com.j2ee.cms.common.core.web.event.RequestEvent;
import com.j2ee.cms.common.core.web.event.ResponseEvent;

/**
 * <p>标题: 网站管理的biz层</p>
 * <p>描述: 网站管理的biz层，用于处理网站，调用服务接口中的一些方法</p>
 * <p>模块: 网站管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 郑荣华
 * @version 1.0
 * @since 2009-4-3 下午03:06:19
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class SiteBiz extends BaseService {

	/**注入网站业务对象**/
	private SiteService siteService;
	
	@Override
	public void performTask(RequestEvent requestEvent, ResponseEvent responseEvent)
			throws Exception {
		String dealMethod = requestEvent.getDealMethod();
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		// 获取sessionID
		String sessionID = requestEvent.getSessionID();
		// 网站id
		String siteId = requestEvent.getSiteid();
		
		//查询网站并分页显示
		if(dealMethod.equals("")){
			log.info("分页显示网站");
			Pagination pagination = (Pagination)requestParam.get("pagination");
			if(this.isUpSystemAdmin) {
				responseParam.put("pagination", siteService.findSiteData(pagination));
			} else {
				responseParam.put("pagination", pagination); 
			}
			log.info("分页显示网站成功");

		// 添加网站信息
		}else if(dealMethod.equals("add")){
			log.info("添加网站");
			String infoMessage = "";
			if(this.isUpSystemAdmin) {
				Site site = (Site) requestParam.get("site");
				User creator = new User();
				creator.setId(sessionID);
				site.setCreator(creator);
				site.setCreateTime(new Date());
				//获得发布方式
				String publishWay = site.getPublishWay();
				if(publishWay.equals("local")) {
					String path = site.getPublishDir();
					if(!FileUtil.isExist(path)) {
//						infoMessage = "本地发布目录不存在,添加网站失败";
						FileUtil.makeDirs(path);
					}
					//获得父节点
					String pid = siteService.getSiteIdByParentIdIsNull();
					//处理添加网站的操作
					Site parent = new Site();
					parent.setId(pid);
					site.setParent(parent);
					
					Map<String, String> map = siteService.addSite(site, sessionID, siteId, false);
					infoMessage = map.get("infoMessage");
					String newSiteId = map.get("newSiteId");
					if(StringUtil.isEmpty(infoMessage)){
						if(!StringUtil.isEmpty(newSiteId)){
							Site newSite = siteService.getNewAddSite(newSiteId);
							// 初始化新增网站的相关目录
							siteService.initSiteDir(newSite);
							// 是否是第一次创建网站
							siteService.addInitSiteInfo(newSite, sessionID);
							infoMessage = "添加网站成功";
						}
					}
				} else if(publishWay.equals("remote")){
					//获得父节点
					String pid = siteService.getSiteIdByParentIdIsNull();
					//处理添加网站的操作
					Site parent = new Site();
					parent.setId(pid);
					site.setParent(parent);
					
					Map<String, String> map = siteService.addSite(site, sessionID, siteId, false);
					infoMessage = map.get("infoMessage");
					String newSiteId = map.get("newSiteId");
					if(StringUtil.isEmpty(infoMessage)){
						if(!StringUtil.isEmpty(newSiteId)){
							Site newSite = siteService.getNewAddSite(newSiteId);
							// 初始化新增网站的相关目录
							siteService.initSiteDir(newSite);
							// 添加网站的其他初始化信息
							siteService.addInitSiteInfo(newSite, sessionID);
							infoMessage = "添加网站成功";
						}
					}
				}
			 else if(publishWay.equals("ftp")){
				//获得父节点
					String pid = siteService.getSiteIdByParentIdIsNull();
					//处理添加网站的操作
					Site parent = new Site();
					parent.setId(pid);
					site.setParent(parent);
					
					Map<String, String> map = siteService.addSite(site, sessionID, siteId, false);
					infoMessage = map.get("infoMessage");
					String newSiteId = map.get("newSiteId");
					if(StringUtil.isEmpty(infoMessage)){
						if(!StringUtil.isEmpty(newSiteId)){
							Site newSite = siteService.getNewAddSite(newSiteId);
							// 初始化新增网站的相关目录
							siteService.initSiteDir(newSite);
							// 添加网站的其他初始化信息
							siteService.addInitSiteInfo(newSite, sessionID);
							infoMessage = "添加网站成功";
						}
					}
			 }
			}else{
				infoMessage = "不是系统管理员，无权添加网站";
			}
			responseParam.put("infoMessage", infoMessage);
			log.info(infoMessage);

		//删除指定网站    
		}else if(dealMethod.equals("delete")){
			log.info("删除网站");
			String deletedId = (String) requestParam.get("deletedId");
			String infoMessage = "";
			infoMessage = siteService.deleteSite(deletedId, siteId, sessionID);
			responseParam.put("infoMessage", infoMessage);
			log.info("删除网站完成");

		//修改网站信息
		}else if(dealMethod.equals("modify")){
			log.info("修改网站");
			String oldSiteName = (String) requestParam.get("oldSiteName");
			SiteForm siteForm = (SiteForm) requestParam.get("form");
			// 判断网站重名问题
			String infoMessage = siteService.judgeSiteName(siteForm);
			
			// 修改网站发布问题
			if(StringUtil.isEmpty(infoMessage)){
				//修改网站
				Site newSite = siteService.getNewModifiedSite(siteForm);
				siteService.modifySite(newSite, siteId, sessionID);
				
				//修改网站默认管理员登录名
				siteService.modifySiteManagerLoginName(newSite, oldSiteName, siteId, sessionID);
				infoMessage = "修改网站成功";
			}
			responseParam.put("infoMessage", infoMessage);
			log.info("修改网站完成");

		//指定网站的详细信息	
		}else if(dealMethod.equals("detail")){
			log.info("网站细节");
			String siteid = (String) requestParam.get("siteid");
			Site site = siteService.findSiteBySiteId(siteid);
			responseParam.put("site", site);
			log.info("查询网站细节成功");

		//选择网站	
		}else if(dealMethod.equals("changeSite")){
			log.info("获取网站的处理");
			List<Site> siteList = new ArrayList<Site>();
			siteList = siteService.findChangeSites(siteId, sessionID);
			//当前用户拥有的角色所在的网站
			String siteOfRolesIn = siteService.findSiteOfRolesInByUserId(sessionID);
			responseParam.put("siteOfRolesIn", siteOfRolesIn);
			responseParam.put("siteList", siteList);
			responseParam.put("currentWebId", siteId);
			log.info("获取网站成功");

		//加载树的信息	
		}else if(dealMethod.equals("getTree")){
			log.info("加载树");
			String treeid = (String) requestParam.get("treeid");
			List<Object> treeList = new ArrayList<Object>();
			//添加一个静态树
			treeList = siteService.proccessAddTree(treeid);
			responseParam.put("treeList", treeList);
			log.info("加载树成功");
			
		// 用户没有网站时创建网站	
		} else if(dealMethod.equals("addSite")) {
			log.info("用户没有网站时创建网站	");		
			String infoMessage = "";

			Site site = (Site) requestParam.get("site");
			User creator = new User();
			creator.setId(sessionID);
			site.setCreator(creator);
			site.setCreateTime(new Date());
			site.setParent(null);
			
			//获得发布方式
			String publishWay = site.getPublishWay();
			if(publishWay.equals("local")) {
				String path = site.getPublishDir();
				if(!FileUtil.isExist(path)) {
//					infoMessage = "本地发布目录不存在,添加网站失败";
					FileUtil.makeDirs(path);
				} 
				//处理添加网站的操作
				Map<String, String> map = siteService.addSite(site, sessionID, "", true);
				infoMessage = map.get("infoMessage");
				String newSiteId = map.get("newSiteId");
				if(StringUtil.isEmpty(infoMessage)){
					if(!StringUtil.isEmpty(newSiteId)){
						Site newSite = siteService.getNewAddSite(newSiteId);
						// 初始化新增网站的相关目录
						siteService.initSiteDir(newSite);
						// 添加网站初始化信息
						siteService.addInitSiteInfo(newSite, sessionID);
						infoMessage = "添加网站成功";
					}
				}
			} else if(publishWay.equals("remote")){
				Map<String, String> map = siteService.addSite(site, sessionID, "", true);
				infoMessage = map.get("infoMessage");
				String newSiteId = map.get("newSiteId");
				if(StringUtil.isEmpty(infoMessage)){
					if(!StringUtil.isEmpty(newSiteId)){
						Site newSite = siteService.getNewAddSite(newSiteId);
						// 初始化新增网站的相关目录
						siteService.initSiteDir(newSite);
						// 添加网站初始化信息
						siteService.addInitSiteInfo(newSite, sessionID);
						infoMessage = "添加网站成功";
					}
				}
			} else if(publishWay.equals("ftp")){
				Map<String, String> map = siteService.addSite(site, sessionID, "", true);
				infoMessage = map.get("infoMessage");
				String newSiteId = map.get("newSiteId");
				if(StringUtil.isEmpty(infoMessage)){
					if(!StringUtil.isEmpty(newSiteId)){
						Site newSite = siteService.getNewAddSite(newSiteId);
						// 初始化新增网站的相关目录
						siteService.initSiteDir(newSite);
						// 添加网站初始化信息
						siteService.addInitSiteInfo(newSite, sessionID);
						infoMessage = "添加网站成功";
					}
				}
			}
			responseParam.put("infoMessage", infoMessage);
			log.info("用户没有网站时创建网站完成");
			
			
		// 网站切换后的初始化
		} else if(dealMethod.equals("initSite")) {
			String siteid = (String) requestParam.get("siteid");			
			User user = siteService.findUser(sessionID);
			responseParam.put("user", user);
			responseParam.put("siteid", siteid);
			//修改用户网站顺序
			siteService.modifyUserSite(sessionID, siteid);
			
		// 添加网站成功后	
		}else if(dealMethod.equals("addSiteSuccess")){
			User user = siteService.findUser(sessionID);
			responseParam.put("user", user);
		}
	}
	
	@Override
	public ResponseEvent validateData(RequestEvent arg0) throws Exception {
		return null;
	}
	
	/**
	 * @param siteService 设置网站服务
	 */
	public void setSiteService(SiteService siteService){
		this.siteService = siteService;
	}

}
