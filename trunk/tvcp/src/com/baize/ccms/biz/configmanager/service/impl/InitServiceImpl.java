/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.configmanager.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.baize.ccms.biz.configmanager.service.InitService;
import com.baize.ccms.biz.sitemanager.dao.SiteDao;
import com.baize.ccms.biz.usermanager.dao.UserDao;
import com.baize.ccms.biz.usermanager.domain.User;
import com.baize.ccms.sys.GlobalConfig;
import com.baize.ccms.sys.ReadSystemXml;
import com.baize.common.core.util.StringUtil;

/**
 * 
 * <p>标题: —— 系统设置初始化业务层</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 系统设置</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-9-10 下午02:53:20
 * @history（历次修订内容、修订人、修订时间等）
 */
 

public class InitServiceImpl implements InitService {

	private final Logger log = Logger.getLogger(InitServiceImpl.class);
	/** 用户dao */
	private UserDao userDao;
	
	/** 网站DAO */
	private SiteDao siteDao;

	/**
	 * 创建树的方法
	 * @param treeid  树的节点
	 * @return List   返回一个list的对象
	 */
	public List<Object> getTreeList(String treeid,String userId,String siteId, boolean isUpSiteAdmin) {
		String treeIds = this.getTreeIds(userId, siteId);
		log.debug("获取树的操作");
		List<Object> list = new ArrayList<Object>();
		String xmlPath = GlobalConfig.appRealPath+ GlobalConfig.systemSetXmlPath;
		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(new File(xmlPath));
			Element root = document.getRootElement();
			for (Iterator system = root.elementIterator(); system.hasNext();) {
				Element systemElement = (Element) system.next();
				if (systemElement != null) {
					Object[] obj = new Object[4];
					Element nodeId = systemElement.element("nodeId");
					Element nodeName = systemElement.element("nodeName");
					Element nodeUrl = systemElement.element("nodeUrl");
					Element nodeLeaf = systemElement.element("nodeLeaf");
					Element nodePid = systemElement.element("nodePid");
					String strNodeId = nodeId.getText();
					String strNodePid = nodePid.getText();
					if(isUpSiteAdmin){
						//如果是系统管理员以上权限，则查询所有的
						if (nodePid.getText().equals(treeid)) {
							obj[0] = nodeId.getText();
							obj[1] = nodeName.getText();
							obj[2] = nodeUrl.getText();
							if (nodeLeaf.getText().equals("true")) {
								obj[3] = true;
							} else {
								obj[3] = false;
							}
							list.add(obj);
						}
					}else {
						if(treeid != null && treeid.equals("0")){
							if(strNodePid != null && strNodePid.equals("0") && StringUtil.contains(treeIds, strNodeId)){
								obj[0] = nodeId.getText();
								obj[1] = nodeName.getText();
								obj[2] = nodeUrl.getText();
								if (nodeLeaf.getText().equals("true")) {
									obj[3] = true;
								} else {
									obj[3] = false;
								}
								list.add(obj);
							}
 
						}else{
							if(treeid != null && strNodePid.equals(treeid)){
								obj[0] = nodeId.getText();
								obj[1] = nodeName.getText();
								obj[2] = nodeUrl.getText();
								if (nodeLeaf.getText().equals("true")) {
									obj[3] = true;
								} else {
									obj[3] = false;
								}
								list.add(obj);
							}
						}
					}					
				}
			}
		} catch (DocumentException e) {
			log.debug("文件里面没有内容");
		}	
		 
		return list;		
	}
	

	 
	 
 
	   /**
     * 根据用户ID，网站ID查询出右侧页面
     * @param userId 用户ID
     * @param siteId 网站ID
     * @param isUpSystemAdmin 是否是系统管理员
     * @return String 右侧显示的url
     */
	public String findRightFrameUrlByUserId(String userId , String siteId,boolean isUpSiteAdmin){
	    String treeIds = this.getTreeIds(userId, siteId);
		String ss = "";     
 
		List<Object> list = new ArrayList<Object>();
		String xmlPath = GlobalConfig.appRealPath+ GlobalConfig.systemSetXmlPath;
		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(new File(xmlPath));
			Element root = document.getRootElement();
			for (Iterator system = root.elementIterator(); system.hasNext();) {
				Element systemElement = (Element) system.next();
				if (systemElement != null) {
					Object[] obj = new Object[4];
					Element nodeId = systemElement.element("nodeId");
					Element nodeName = systemElement.element("nodeName");
					Element nodeUrl = systemElement.element("nodeUrl");
					Element nodeLeaf = systemElement.element("nodeLeaf");
					Element nodePid = systemElement.element("nodePid");
					String strNodeId = nodeId.getText();
					String strNodePid = nodePid.getText();
					if(isUpSiteAdmin){
						if (strNodePid.equals("0")) {
							obj[0] = nodeId.getText();
							obj[1] = nodeName.getText();
							obj[2] = nodeUrl.getText();
							if (nodeLeaf.getText().equals("true")) {
								obj[3] = true;
							} else {
								obj[3] = false;
							}
							list.add(obj);						 
						}
					}else{
						if (strNodePid.equals("0") && StringUtil.contains(treeIds, strNodeId) ) {
							obj[0] = nodeId.getText();
							obj[1] = nodeName.getText();
							obj[2] = nodeUrl.getText();
							if (nodeLeaf.getText().equals("true")) {
								obj[3] = true;
							} else {
								obj[3] = false;
							}
							list.add(obj);						 
						}
					}
					
				}
			}
		} catch (DocumentException e) {
			log.debug("文件里面没有内容");
		}
		String rightFrameUrl = "";
		if(list != null && list.size() > 0){
			Object obj[] = (Object[])list.get(0);
			rightFrameUrl = (String)obj[2];
		}
		log.debug("rightFrameUrl==============="+rightFrameUrl);
		
		return rightFrameUrl;
	}
	/**
	 * 根据用户ID和网站ID查询出树的IDS
	 * @param userId 用户ID
	 * @param siteId 网站ID
	 * @return String 树的IDS
	 */
	private String getTreeIds(String userId , String siteId){
		String treeIds = "";
		User user = userDao.getAndNonClear(userId);
		String menuIds = user.getMenuIds();
		if(menuIds != null && !menuIds.equals("")){			
			if(StringUtil.contains(menuIds, "*")){
				//如果有多个网站
				String strMenuIds[] = menuIds.split("*");
				for(int i = 0 ; i < strMenuIds.length ; i++){
					String siteIdAndMenuId[] = strMenuIds[i].split(",");
					String setSiteId = siteIdAndMenuId[0];
					if(setSiteId.equals(siteId)){
						for(int j = 1 ; j < siteIdAndMenuId.length ; j++){
							String strSiteIdAndMenuId[] = siteIdAndMenuId[j].split("#");
							if(strSiteIdAndMenuId.length > 1){
								if(strSiteIdAndMenuId[1].equals("m010")){
									treeIds = treeIds + "," + strSiteIdAndMenuId[0];
								}
							}
						}
					}					
				}
			}else{
				//如果只有一个网站
				if(StringUtil.contains(menuIds, siteId)){
					treeIds = StringUtil.replaceFirst(menuIds, siteId+",");
				}
			}
		}	 
		//获取到对这个网站设置的树的权限的ID
		treeIds = StringUtil.replaceFirst(treeIds, ",");
		return treeIds;
	}

	/**
	 * @param userDao the userDao to set
	 */
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * @param siteDao the siteDao to set
	 */
	public void setSiteDao(SiteDao siteDao) {
		this.siteDao = siteDao;
	}

}
