  /**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.usermanager.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.j2ee.cms.biz.configmanager.dao.SystemLogDao;
import com.j2ee.cms.biz.usermanager.dao.MenuDao;
import com.j2ee.cms.biz.usermanager.dao.UserDao;
import com.j2ee.cms.biz.usermanager.domain.Menu;
import com.j2ee.cms.biz.usermanager.domain.User;
import com.j2ee.cms.biz.usermanager.service.MenuService;
import com.j2ee.cms.common.core.util.SqlUtil;
import com.j2ee.cms.common.core.util.StringUtil;
import org.apache.log4j.Logger;

/**
 * <p>标题: —— 系统菜单业务逻辑具体实现类</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 用户管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-2-26 下午06:31:47
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class MenuServiceImpl implements MenuService{
	
	private Logger log = Logger.getLogger(MenuServiceImpl.class);
	
	/** 注入菜单dao. */
	private MenuDao menuDao;
	/** 注入用户dao **/
	private UserDao userDao;
	/** 注入日志dao */
	private SystemLogDao systemLogDao;
	
	/**
	 * 增加菜单.
	 * @param menu 菜单对象
	 */
	public  void addMenu(final Menu menu) {
		log.debug("Execute service : addMenu(final Menu menu).");
		// TODO 自动生成方法存根
		
	}

	public void deleteMenu(final String id) {
		log.debug("Execute service : deleteMenu(final int id).");
		// TODO 自动生成方法存根
		
	}

	public List<Menu> findAll() {
		// TODO 自动生成方法存根
		return menuDao.findAll();
	}

	public List findMenu() {
		// TODO 自动生成方法存根
		List allmenulist =  menuDao.findAll();		
		List allmenulistdata = new ArrayList();
		for(int i= 0 ; i < allmenulist.size(); i ++){
			String[] str = new String[2];
			String id = String.valueOf(((Menu)allmenulist.get(i)).getId());
			String name = String.valueOf(((Menu)allmenulist.get(i)).getName());
			str[0] = id;
			str[1] = name;
			allmenulistdata.add(str);
		}
		return allmenulistdata;
	}
	/**
	 * 
	 * @param id 菜单唯一标识
	 * @return  List 
	 */
	public Menu findMenuDataById(final String id) {
		return menuDao.getAndClear(id);
	}

	/**
	 * 
	 * @param identifier 菜单唯一标识
	 * @return  List 
	 */
	public List<Menu> findMenuDataByIdentifier(final String identifier) {
		// TODO 自动生成方法存根
		return menuDao.findByNamedQuery("findMenuDataByIdentifier","identifier",identifier) ;
	
	}

	public void modifyMenu(Menu menu) {
		// TODO 自动生成方法存根
		
	}

	public List<Menu> findMenuByIds(String ids) {
		log.debug("Execute service : List<Menu> findMenuByUserId(String ids).");
		
		return menuDao.findByDefine("findMenuByIds", "ids", ids);
	}
	/**
	 * 根据用户ID查找菜单
	 * @param userId   用户id
	 * @param siteId   网站ID
	 * @return Map     map里面存放已选的和未选的数据
	 */
	public Map findMenuIdsByUserIdAndSiteId(String userId,String siteId) {
		Map map = new HashMap();
		User user = userDao.getAndNonClear(userId);
		String systemFunction = user.getSystemFunction();
		//所有的菜单ID
		String menuFunctionIds = user.getMenuIds();
		//已选择的菜单ID
		String chooseMenuFunctionIds = user.getChooseMenuIds();
		//已选择的菜单
		List<Menu> menulist = new ArrayList<Menu>();
		//没有选择的 菜单
		List<Menu> menulistNoChoose = new ArrayList<Menu>();
		String chooseMenuIds = chooseMenuFunctionIds;
		if(StringUtil.contains(chooseMenuFunctionIds, "#")){
			chooseMenuIds = this.getMenuIds(userId, siteId, chooseMenuFunctionIds);
		}
		String getSiteId = menuFunctionIds.split("#")[0].split(",")[0];
		String allMenuIds = this.getMenuIds(userId, siteId, menuFunctionIds);
		String strChooseMenuIds[] = chooseMenuIds.split(",");
		for(int i = 0 ; i < strChooseMenuIds.length ; i++){
			allMenuIds = allMenuIds.replace(strChooseMenuIds[i], "");
		}
		String strAllMenuIds[] = allMenuIds.split(",");
		String noChooseMenuIds = "";
		for(int i = 0 ; i < strAllMenuIds.length ; i++){
			if(strAllMenuIds[i] != null && strAllMenuIds[i].trim().length()>0 ){
				noChooseMenuIds += strAllMenuIds[i]+",";
			}
		}
		noChooseMenuIds = StringUtil.replaceFirst(noChooseMenuIds, ",");
		noChooseMenuIds = StringUtil.clearRepeat(noChooseMenuIds);
		noChooseMenuIds = StringUtil.replaceFirst(noChooseMenuIds, ",");
		if(StringUtil.contains(noChooseMenuIds, "''")){
			noChooseMenuIds = StringUtil.replaceFirst(noChooseMenuIds, "''");
			noChooseMenuIds = StringUtil.clearRepeat(noChooseMenuIds);
			noChooseMenuIds = StringUtil.replaceFirst(noChooseMenuIds, "''");
		}
		noChooseMenuIds = StringUtil.replaceFirst(noChooseMenuIds, ",");
		if(StringUtil.contains(chooseMenuIds, "''")){
			chooseMenuIds = SqlUtil.toSqlString(chooseMenuIds);
		}
		if(chooseMenuIds != null && !chooseMenuIds.equals("")){
			menulist = menuDao.findByDefine("findMenuByIds", "ids", chooseMenuIds);
		}
		if(noChooseMenuIds != null && !noChooseMenuIds.equals("")){
			menulistNoChoose = menuDao.findByDefine("findMenuByIds", "ids", (SqlUtil.toSqlString(siteId) + noChooseMenuIds));
		}
		List<Menu> menuChoose = new ArrayList<Menu>();
		/**普通用户的显示菜单*/
		for(Object o:menulist){
			Menu menu = (Menu)o;
			if(!"网站管理".equals(menu.getName())){
				menuChoose.add(menu);
			}
		}
		map.put("menulist", menuChoose);
	
		map.put("menulistNoChoose", menulistNoChoose);
		return map;
	}
	
	private String getMenuIds(String userId,String siteId,String chooseMenuIds){
		String strMenuIds = "";
		if(StringUtil.contains(chooseMenuIds,"*")){
			//如果有多个网站的菜单
			String strChooseMenuIds[] = chooseMenuIds.split("[*]");
			for(int i = 0 ; i < strChooseMenuIds.length ; i++){
				//如果包含当前这个网站
				if(StringUtil.contains(strChooseMenuIds[i],siteId)){
					String tempStrChooseMenuIds = strChooseMenuIds[i];
					String str[] = tempStrChooseMenuIds.split(",");
					for(int j = 1 ; j < str.length ; j++){
						String temp[] = str[j].split("#");
						if(temp != null && temp.length > 1){
							strMenuIds = strMenuIds + "," + SqlUtil.toSqlString(temp[1]);
							strMenuIds = StringUtil.replaceFirst(strMenuIds,",");
							strMenuIds = StringUtil.clearRepeat(strMenuIds);
							strMenuIds = StringUtil.replaceFirst(strMenuIds,",");
							return strMenuIds;
						}	
					}
				}
			}
		}else{
			//如果只有一个网站的菜单
			if(StringUtil.contains(chooseMenuIds,siteId)){
				//如果这个网站的ID在里面
				String strChooseMenuIds[] = chooseMenuIds.split(",");
				//从1开始，第一个是网站
				for(int i = 1 ; i < strChooseMenuIds.length ; i++){
					if(strChooseMenuIds[i]!=null && strChooseMenuIds[i].trim().length()>0){
						String temp[] = strChooseMenuIds[i].split("#");
						if(temp != null && temp.length > 1){
							strMenuIds = strMenuIds + "," + SqlUtil.toSqlString(temp[1]);
						}
					}
				}
				strMenuIds = StringUtil.replaceFirst(strMenuIds,",");
				strMenuIds = StringUtil.clearRepeat(strMenuIds);
				strMenuIds = StringUtil.replaceFirst(strMenuIds,",");
				return strMenuIds;
			}else{
				//如果这个网站的ID不在里面
				return strMenuIds;
			}
		}
		return strMenuIds;
	}
	public List findAllMenuIds(){
		List listmenuIds=menuDao.findByNamedQuery("findAllMenus");
		return listmenuIds;
	}
	
	
	/**
	 * 修改菜单顺序 
	 * @param userid       用户di
	 * @param list         获得新的菜单ids
	 * @param siteId
	 */
	public void modifyMenuOrder(String userid, String menuIds, String siteId) {
		User user = userDao.getAndClear(userid); 
		String chooseMenuIds = user.getChooseMenuIds(); 
		log.debug("menuIds======================="+menuIds);
		//从已选择菜单里面获取具体的某一个网站的菜单
		String strMenuIds = "";
		if(StringUtil.contains(chooseMenuIds,"*")){
			//如果有多个网站的菜单
			String strChooseMenuIds[] = chooseMenuIds.split("*");
			for(int i = 0 ; i < strChooseMenuIds.length ; i++){
				//如果包含当前这个网站
				if(StringUtil.contains(strChooseMenuIds[i],siteId)){
					strMenuIds = strChooseMenuIds[i];					 
				}
			}
		}else{
			//如果只有一个网站的菜单
			if(StringUtil.contains(chooseMenuIds,siteId)){
				//如果这个网站的ID在里面
				strMenuIds = chooseMenuIds;
			 
			} 
		}
		//具体某一个网站的菜单集合
		String oneSiteMenuIds[] =  strMenuIds.split(",");
		//页面排序完的菜单
		String orderMenuIds[] = menuIds.split(",");
		//最终排完序的系统功能菜单
		String lastMenuIds = "";
		for(int i = 1 ; i < orderMenuIds.length ; i++){
			String tempMenuId = orderMenuIds[i];			
			for(int j = 1 ; j <oneSiteMenuIds.length ; j++){
				if(StringUtil.contains(oneSiteMenuIds[j],tempMenuId)){
					lastMenuIds = lastMenuIds + "," + oneSiteMenuIds[j];
				}
			}
		}
		lastMenuIds = siteId + lastMenuIds;
		chooseMenuIds = chooseMenuIds.replace(strMenuIds, lastMenuIds);
		user.setChooseMenuIds(chooseMenuIds);
		userDao.update(user);
	 
		// 写入日志文件
		String categoryName = "菜单管理->菜单排序";
		String param = "";
		systemLogDao.addLogData(categoryName, siteId, userid, param);
	}
	
	/**
	 * @param menuDao 要设置的 menuDao
	 */
	public void setMenuDao(MenuDao menuDao) {
		this.menuDao = menuDao;
	}
	
	/**
	 * 
	 * @param userDao 要设置的 userDao
	 */
	public void setUserDao(UserDao userDao){
		this.userDao = userDao;
	}

	/**
	 * @param systemLogDao the systemLogDao to set
	 */
	public void setSystemLogDao(SystemLogDao systemLogDao) {
		this.systemLogDao = systemLogDao;
	}
}
