/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.usermanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.baize.ccms.biz.usermanager.domain.Menu;
import com.baize.ccms.sys.GlobalConfig;
import com.baize.ccms.sys.ReadSystemXml;
import com.baize.common.core.service.BaseService;
import com.baize.common.core.util.StringUtil;
import com.baize.common.core.web.event.RequestEvent;
import com.baize.common.core.web.event.ResponseEvent;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CCMS</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 郑荣华
 * @version 1.0
 * @since 2009-5-18 下午03:37:36
 * @history（历次修订内容、修订人、修订时间等） 
 */
public final  class MenuBiz extends BaseService {

	/** 注入菜单业务对象 **/
	private MenuService menuService;
	
	@Override
	public void performTask(RequestEvent requestEvent, ResponseEvent responseEvent)
	throws Exception {
		String dealMethod = requestEvent.getDealMethod();
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		
		String userId = requestEvent.getSessionID();
		String siteId = requestEvent.getSiteid();
		// 排序 菜单
		if(dealMethod.equals("order")) {
			log.info("排序菜单");
			//获取页面传过来的排序完的菜单
			String menuIds = (String) requestParam.get("menuIds");			
			// 如果不是网站管理员以上
		//	if(!this.isUpSystemAdmin) {
				String newMenuIds = siteId + "," + menuIds;
				if(menuIds == null || "".equals(menuIds)){
					responseParam.put("infoMessage","用户必须要有权限菜单，请将已选菜单选项框中至少保留一个选项！");
				}else{
					menuService.modifyMenuOrder(userId, newMenuIds, siteId);
				}
			/*}else{
				responseParam.put("infoMessage","管理员有操作所有管理的权限，不需要设置");
			} */
			log.info("排序菜单完成");
			
		// 查找菜单	
		} else if(dealMethod.equals("findMenu")) {
			log.info("查找用户的菜单");
			List<Menu> menulist = new ArrayList<Menu>();
			List<Menu> menulistNoChoose = new ArrayList<Menu>();	
			String menus="";
			// 如果是系统管理员以上
			if(this.isUpSystemAdmin) {
				menulist = menuService.findAll();
				responseParam.put("menulist", menulist);
				responseParam.put("menulistNoChoose", menulistNoChoose);
			} else {				
				Map map = menuService.findMenuIdsByUserIdAndSiteId(userId, siteId);
				responseParam.put("menulist", map.get("menulist"));
				responseParam.put("menulistNoChoose", map.get("menulistNoChoose"));
			}
		
			log.info("查找用户菜单完成");
			
		} else if(dealMethod.equals("getTree")) {
		    	//获取树
				log.info("加载树的信息");
				// 获得树的treeid
				String treeid = (String) requestParam.get("treeid");
				if(StringUtil.isEmpty(treeid) || treeid.equals("null")) {
					treeid = "0";
				}
				List<Object> list = new ArrayList<Object>();
				String xmlPath = GlobalConfig.personSetXmlPath;
				list = ReadSystemXml.getTreeList(treeid, xmlPath);
				responseParam.put("treelist", list);
				log.info("加载树的信息成功");
		}
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}
	
	@Override
	public ResponseEvent validateData(RequestEvent requestEvent)
			throws Exception {
		return null;
	}

}
