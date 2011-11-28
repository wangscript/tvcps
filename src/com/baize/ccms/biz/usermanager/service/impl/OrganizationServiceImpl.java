  /**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.biz.usermanager.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.baize.ccms.biz.configmanager.dao.SystemLogDao;
import com.baize.ccms.biz.usermanager.dao.OrganizationDao;
import com.baize.ccms.biz.usermanager.dao.UserDao;
import com.baize.ccms.biz.usermanager.domain.Organization;
import com.baize.ccms.biz.usermanager.service.OrganizationService;
import com.baize.common.core.dao.Pagination;
import com.baize.common.core.util.SqlUtil;

/**
 * <p>标题: —— 机构业务逻辑处理类</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 用户管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-2-16 下午02:59:56
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class OrganizationServiceImpl  implements OrganizationService{
	private static final Logger log = Logger.getLogger(OrganizationServiceImpl.class);
	/** 机构DAO */
	private OrganizationDao organizationDao;
	/** 注入用户数据访问对象 */
	private UserDao userDao;
	/** 注入日志dao */
	private SystemLogDao systemLogDao;
	
	public List<Organization> findAll() {
		// TODO 自动生成方法存根
		return organizationDao.findAll();
	}


	public void deleteObject(String id) {
		// TODO 自动生成方法存根
		organizationDao.deleteByKey(id);
	}


	public Organization findOrganizationByKey(String id) {
		// TODO 自动生成方法存根
		if(id != null) {
			return organizationDao.getAndNonClear(id);
		} else {
			return null;
		}
	}

	public Pagination findOrganizationDataByNodeId(Pagination pagination,String nodeid,boolean isUpSystemAdmin,boolean isSiteAdmin) {
		//如果是超级管理员或者系统管理员
	//	if(isUpSystemAdmin){
			if(nodeid != null) {
				return organizationDao.getPagination(pagination,"nodeid",nodeid);
			}else{
				return organizationDao.getPagination(pagination);
			}			
	/*	}else if(isSiteAdmin){
			if(nodeid != null) {
				return organizationDao.getPagination(pagination,"nodeid",nodeid);
			}else{
				return organizationDao.getPagination(pagination);
			}			
		}else{
			return new Pagination();
		}	*/
	}

	
	public boolean modifyObject(Organization organization,String pid,String oldpid, String siteId, String sessionID) {
		// TODO 自动生成方法存根
	
		if(pid != null) {
			List organizationList = organizationDao.findByNamedQuery("findOrganizationByPid","pid",pid);
			int j = 0;
			for(int i = 0 ; i < organizationList.size() ; i++){
				Organization org = (Organization)organizationList.get(i);
				if(organization.getName().equals(org.getName()) && !org.getId().equals(organization.getId())){
					return true;
				}
			}
		
			Organization neworganization = organizationDao.getAndNonClear(organization.getId());
			neworganization.setName(organization.getName());
			neworganization.setDescription(organization.getDescription());
			Organization parent  = new Organization();
			parent.setId(pid);
			neworganization.setParent(parent);
	/*		Organization suborg = organizationDao.getAndClear(pid);
			if(suborg.isLeaf()){
				suborg.setLeaf(false);
				organizationDao.update(suborg);
			}	*/
		//	organizationDao.update(organization);
			organizationDao.update(neworganization);
			String a = oldpid;
			if(a == null) {
				a = "";
			} 
			if(!a.equals(pid)) {
				//根据原来的父节点ID查询这个父节点有没有其他子节点了，如果没有就修改leaf为true
				List orgList = organizationDao.findByNamedQuery("findOrganizationByPid", "pid", pid);
				List oldorgList = new ArrayList();
				if(oldpid != null) {
					oldorgList = organizationDao.findByNamedQuery("findOrganizationByPid", "pid", oldpid);
				}
				Organization  suborg = organizationDao.getAndNonClear(pid);
			/*	if(suborg.getParent() == null){
					suborg.setParent(new Organization());
				}*/
				Organization oldsuborg = new Organization();
				if(oldpid != null) {
					oldsuborg = organizationDao.getAndNonClear(oldpid);	
					if(oldorgList == null || oldorgList.size() == 0){					
						if(oldsuborg != null){
							oldsuborg.setLeaf(true);
							organizationDao.update(oldsuborg);							
						}							
					}else{					
						if(oldsuborg != null){
							oldsuborg.setLeaf(false);
							organizationDao.update(oldsuborg);	
						}	
					}
				}
				if(orgList == null || orgList.size() == 0){	
					if(suborg != null){
						suborg.setLeaf(true);
						organizationDao.update(suborg);
					}				
				}else{
					if(suborg != null){
						suborg.setLeaf(false);
						organizationDao.update(suborg);
					}
				}
				
				
				// 写入日志文件
				String param = neworganization.getName();
				String categoryName = "机构管理->修改";
				systemLogDao.addLogData(categoryName, siteId, sessionID, param);
			}
		
		
		}else{
			List organizationList = organizationDao.findByNamedQuery("findFirstOrganization");
			
			for(int i = 0 ; i < organizationList.size() ; i++){
				Organization org = (Organization)organizationList.get(i);
				if(organization.getName().equals(org.getName()) && !org.getId().equals(organization.getId())){
					return true;
				}
			}
			Organization neworganization = organizationDao.getAndNonClear(organization.getId());
			neworganization.setName(organization.getName());
			neworganization.setDescription(organization.getDescription());
			// 旧的pid不为空
			
			//	neworganization.setLinkAddress("userManager.do?dealMethod=");
				//设置没有子节点
			//	neworganization.setLeaf(true);
			List oldorgList = null;
			Organization oldsuborg = null;
			if(oldpid != null) {
				 oldorgList = organizationDao.findByNamedQuery("findOrganizationByPid", "pid", oldpid);
				 oldsuborg = organizationDao.getAndClear(oldpid);	
			}
			if(oldorgList == null || (oldorgList.size()-1) == 0){					
				if(oldsuborg != null){
					oldsuborg.setLeaf(true);
					organizationDao.updateAndClear(oldsuborg);	
				}							
			}else{					
				if(oldsuborg != null){
					oldsuborg.setLeaf(false);
					organizationDao.updateAndClear(oldsuborg);	
				}	
			}
			Organization org = new Organization();
			org.setId(neworganization.getId());
			org.setDeleted(neworganization.isDeleted());
			org.setLeaf(neworganization.isLeaf());
			org.setDescription(organization.getDescription());
			org.setName(organization.getName());
		/*	neworganization.setDescription(organization.getDescription());
			neworganization.setName(organization.getName());*/
			organizationDao.clearCache();
			organizationDao.updateAndClear(org);
			
			// 写入日志文件
			String param = org.getName();
			String categoryName = "机构管理->修改";
			systemLogDao.addLogData(categoryName, siteId, sessionID, param);
		}
		return false;
	}

	public boolean addOrganization(Organization organization, String pid, String siteId, String sessionID) {
		String orgName = organization.getName();
		Organization parent = new Organization();
		if(pid != null) {
			List organizationList = organizationDao.findByNamedQuery("findOrganizationByPid","pid",pid);
			for(int i = 0 ; i < organizationList.size() ; i++){
				Organization org = (Organization)organizationList.get(i);
				if(orgName.equals(org.getName())){
					return true;
				}
			}
			parent.setId(pid);
			organization.setParent(parent);
			Organization suborg = organizationDao.getAndClear(pid);
			if(suborg.isLeaf()){
				suborg.setLeaf(false);
				organizationDao.update(suborg);
			}			
		}else{
			List organizationList = organizationDao.findByNamedQuery("findFirstOrganization");
			for(int i = 0 ; i < organizationList.size() ; i++){
				Organization org = (Organization)organizationList.get(i);
				if(orgName.equals(org.getName())){
					return true;
				}
			}
		}	
		organization.setLinkAddress("userManager.do?dealMethod=");
		//设置没有子节点
		organization.setLeaf(true);
		organizationDao.save(organization);
		
		// 写入日志文件
		String categoryName = "机构管理->添加";
		String param = organization.getName();
		systemLogDao.addLogData(categoryName, siteId, sessionID, param);
		
		return false;
	}


	public List findSubOrgTreeByPid(String pid) {
		if (pid == null) {
			return organizationDao.findByNamedQuery("findFirstLevelOrgTreeByPid");
		}else{
			return organizationDao.findByNamedQuery("findSubOrgTreeByPid", "pid", pid);	
		}		
	}
	
	public boolean modifyAllObject(String ids, String siteId, String sessionID) {	
		boolean temp = false;
		ids = SqlUtil.toSqlString(ids);
		List orgList = organizationDao.findByDefine("findOrganizationByIds", "ids",ids);		
		
		for(int i = 0 ; i < orgList.size() ; i++){
			Organization organization = (Organization)orgList.get(i);
			String pid = organization.getId();
			List porgList = organizationDao.findByNamedQuery("findOrganizationByPid", "pid", pid);
			//如果此节点有子节点则为true;
			if(porgList != null && porgList.size() > 0){			
				temp = true;
				return temp;
			}
		}	
		if(temp == false){
			String paramName[] = {"deleted","ids"};
			String value[] = {"1",ids};		
			organizationDao.updateByDefine("updateOrganizationByIds", paramName, value);
	
			
			for(int i = 0 ; i < orgList.size() ; i++){
				Organization organization = (Organization)orgList.get(i);
				Organization parent = organization.getParent();
				if(parent != null){
					Organization porganization = organizationDao.getAndNonClear(parent.getId());
					
					if(porganization != null){
						List newporgList = organizationDao.findByNamedQuery("findOrganizationByPid", "pid", porganization.getId());
						if(newporgList != null && newporgList.size() > 0){
							porganization.setLeaf(false);							
						}else{
							porganization.setLeaf(true);
						}
						organizationDao.update(porganization);
					}					
				}
				
				// 写入日志文件
				String categoryName = "机构管理->删除";
				String param = organization.getName();
				systemLogDao.addLogData(categoryName, siteId, sessionID, param);
			}
		}
		return temp;			
	}
	
	
	public boolean findUserByOrganizationId(String  orgids){		
		String strOrgIds[] = orgids.split(",");
		boolean temp = false;
		for(int i = 0; i < strOrgIds.length ; i++){
			List userList = userDao.findByNamedQuery("findUserByOrganizationId","orgid", strOrgIds[i]);		
			if(userList != null && userList.size() > 0){
				temp = true;
				return temp;
			}
		}	
		return temp;
	}
	/**
	 * 递归获取所有的子栏目名称集合
	 * @param list 所有名称的集合
	 * @param id 机构ID
	 * @return 返回所有的子栏目名称
	 */
	public List findOrgName(List list ,String id){
		Organization org = organizationDao.getAndNonClear(id);
		if(org != null){
			list.add(org.getName());
			if(org.getParent() != null){
				list = findOrgName(list,org.getParent().getId());			
			}
		}
		
		return list;
	}
	
	public String getOrganizationName(String id){
		List list = new ArrayList();
		list = findOrgName(list ,id);
		String strName = "";
		if(list.size() > 0){
			for(int i  = list.size()-1 ; i >=0 ; i--){
				strName = strName + ">>" + list.get(i);
			}
		}
		return strName;
	}
	
	public  void findOrganizationChildById(StringBuffer orgIds,String id){
		if(id != null) {
			List orgList = organizationDao.findByNamedQuery("findOrganizationByPid","pid",id);
			if(orgList != null && orgList.size() > 0){
				for(int i = 0 ; i < orgList.size() ; i++){
					Organization org = (Organization)orgList.get(i);
					String orgid = org.getId();
					orgIds.append("," + orgid);
					findOrganizationChildById(orgIds,orgid);
				}
			}
		}
	}
	
	public String findChildOrganizationNameById(String id){
		StringBuffer sb = new StringBuffer();
		List orgList = organizationDao.findByNamedQuery("findOrganizationByPid","pid",id);
		if(orgList != null && orgList.size() > 0){
			for(int i = 0 ; i < orgList.size() ; i++){
				Organization org = (Organization)orgList.get(i);
				sb.append(","+org.getName());			
			}
		}
		String str = sb.toString();
		if(str.startsWith(",")){
			str = str.replaceFirst(",", "");
		}
		return str;
	}

	/**
	 * @param organizationDao 要设置的 organizationDao
	 */
	public void setOrganizationDao(OrganizationDao organizationDao) {
		this.organizationDao = organizationDao;
	}


	/**
	 * @return the userDao
	 */
	public UserDao getUserDao() {
		return userDao;
	}


	/**
	 * @param userDao the userDao to set
	 */
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}


	/**
	 * @param systemLogDao the systemLogDao to set
	 */
	public void setSystemLogDao(SystemLogDao systemLogDao) {
		this.systemLogDao = systemLogDao;
	}


}
