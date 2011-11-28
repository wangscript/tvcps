/**
 * 
 */
package com.baize.ccms.biz.usermanager.web.action;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.baize.ccms.biz.documentmanager.service.CategoryService;
import com.baize.ccms.biz.messagemanager.service.MessageTipsService;
import com.baize.ccms.biz.setupmanager.service.SetupBiz;
import com.baize.ccms.biz.usermanager.domain.Menu;
import com.baize.ccms.biz.usermanager.domain.User;
import com.baize.ccms.biz.usermanager.service.LoginService;
import com.baize.ccms.biz.usermanager.service.MenuService;
import com.baize.ccms.biz.usermanager.service.UserService;
import com.baize.ccms.biz.usermanager.web.event.SessionManager;
import com.baize.ccms.biz.usermanager.web.event.UserEntry;
import com.baize.ccms.biz.usermanager.web.event.UserLoginReqEvent;
import com.baize.ccms.biz.usermanager.web.form.LoginForm;
import com.baize.ccms.sys.GlobalConfig;
import com.baize.common.core.domain.RegInfo;
import com.baize.common.core.util.CollectionUtil;
import com.baize.common.core.util.DateUtil;
import com.baize.common.core.util.RSAHelper;
import com.baize.common.core.util.StringUtil;
import com.baize.common.core.web.BizDelegate;
import com.baize.common.core.web.WebSessionManager;
import com.baize.common.core.web.event.ExternalSessionManager;
import com.baize.common.core.web.event.ResponseEvent;


/** 
 * 覆盖超类中的execute方法。这个方法会依次做如下操作：
 * 1.取得参数LoginForm的user和password属性，生成一个LoginReqEvent对象，并设置它的<br>
 * user属性和password属性<br>
 * ⒉生成一个BizDelegate对象（以LoginReqEvetn为参数），并调用BizDelegate的delegate方法；<br>
 * 3.将delegate方法返回的对象强制转换成LoginResEvent对象，判断LoginResEvent的responseCode属性，
 * 如果属性为非“0”，生成一个ActionError对象（以responseCode为参数），生成一个ActionErrors，
 * 将ActionError保存在ActionErrors中，调用saveErrors方法，以request和ActionErrors为参数。
 * 调用参数mapping的findForward方法作为返回值（参数为“loginfailed”）。如果responseCode为“0”， 那么继续下面步骤
 * 4.得到LoginResEvent中的sessionId属性，将它放入request的seesion中；
 * 5.调用参数mapping的findForward方法作为返回值（参数为“success”）。
 * ccms通用信息管理系统--登录控制类 
 * package: com.baize.ccms.biz.login.web.action
 * File: LoginAction.java 创建时间:2009-1-6下午02:59:14
 * Title: 标题（要求能简洁地表达出类的功能和职责）
 * Description: 描述（简要描述类的职责、实现方式、使用注意事项等）
 * Copyright: Copyright (c) 2009 南京百泽网络科技有限公司
 * Company: 南京百泽网络科技有限公司
 * 模块: 用户管理模块
 * @author  娄伟峰
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等） 
 */
public final class LoginAction extends Action {
	
	private Logger log = Logger.getLogger(LoginAction.class);
	
	
	/**
	 * 描述客户端渠道的类型，可能有web、GUI、message等类型。
	 */
	private String channelType = "WEB";

	/**
	 * 登录业务逻辑处理类
	 */
	private LoginService loginService;
	
	/** 菜单服务类 */
	private MenuService menuService;
	
	/** 用户服务类 */
	private UserService userService;
	
	/** 短消息服务类**/
	private MessageTipsService messageTipsService;
	
	/**类别服务类**/
	private CategoryService categoryService;
	
	/** 登陆用户的状态 */
	public static String loginStatus = "";
	
	/***/
	/**
	 * 执行方法
	 */
	public ActionForward execute(ActionMapping actionMapping,
				 ActionForm actionForm,HttpServletRequest request,
				 HttpServletResponse response) {
		// 初始化应用名
		GlobalConfig.appName = request.getContextPath().replaceFirst("/", "");
		
		ActionForward actionForward = null;
		String logintype = null;
		logintype = String.valueOf(request.getParameter("logintype"));
		if(!logintype.equals(null) && !logintype.equals("null")){
			if(logintype.equals("loginout")){
				actionForward = LoginOut(actionMapping, actionForm, request,response);
			}
			if(logintype.equals("changepassword")){
				actionForward = LoginChangePassword(actionMapping, actionForm, request,response);
			}
			if(logintype.equals("checkRand")){
				actionForward = checkRand(actionMapping, actionForm, request,response);
			}
		}else{
			log.debug("login start ======================"+DateUtil.getCurrTime());			
			actionForward = Login(actionMapping, actionForm, request,response);
			log.debug("login end ======================"+DateUtil.getCurrTime());
			//	return new ActionForward("/main/mainframe.jsp",true);
		}
		
		
		return actionForward;
	 }

	/**
	 * 用户登录
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward Login(ActionMapping actionMapping,
				   ActionForm actionForm,
				   HttpServletRequest request,
				   HttpServletResponse response) {
		
		
						/**用户登录首先要判断使用期限是否过期**/
		// 获取路径
		String strDirPath = new File(request.getSession().getServletContext()
				.getRealPath(request.getRequestURI())).getParent();
		String path = strDirPath.substring(0, strDirPath.lastIndexOf(File.separator)); //截取路径
		boolean flag = true;// 过期
		// 获取注册信息
		RegInfo regInfo = RSAHelper.readFile(path);
		// 判断是否获取了注册码
		if (regInfo != null && regInfo.getRegCode() != null && RSAHelper.decodeString(regInfo.getRegCode())) {
			if (RSAHelper.checkDate(regInfo)) {//在有效期内
				flag=false;
			}else{//在有效期内
				flag=true;
			}
		} else {//注册码不正确，程序直接退出
			log.info("注册码为空");
			request.setAttribute("regeInfo", "没有找到注册码，请联系供应商获取");
			flag = false;	
			return actionMapping.findForward("useispass");
		}
		if(flag){//使用期到了
			log.info("使用期到了");
			request.setAttribute("regeInfo", "你的使用期限已经到了，要继续使用请联系供应商");
			return actionMapping.findForward("useispass");
		}
		
		
		
		
		
		String strUser = null;
		//现在所有的都为普通登录
		String loginType = "LOGIN_TYPE_FRONT";
	//  StringUtil.stringTrim(httpServletRequest.getParameter("authType"));
		UserLoginReqEvent req = new UserLoginReqEvent("web", "","","","");
		log.debug("loginType:(" + loginType + ")");
   
	//	req.setRemoteUserid(httpServletRequest.getRemoteUser());
		req.setDealMethod("checklogin");
		
		LoginForm form = (LoginForm) actionForm;
		String name = form.getName();
		String password = form.getPassword();
		
		if (StringUtil.isEmpty(name) || StringUtil.isEmpty(password)) {
			return actionMapping.findForward("timeout");
		}
	
		req.setLoginName(name);
		req.setPassword(password);
		req.setChannelType(channelType);

		//调用数据库操作方法
		BizDelegate delegate = new BizDelegate(req);
		ResponseEvent res =  delegate.delegate(loginService);
	/*	ResponseEvent res = new ResponseEvent(req.getSessionID(),req.getChannelType(),
				req.getSiteid(),req.getRoleName(),req.getLoginName());
		try{
			 loginService.loginSystem(req,res);
		}catch(Exception e){
			
		}*/
		
		ActionErrors errors = new ActionErrors();
		if(res == null){
			request.setAttribute("message","响应参数为空!");
			return actionMapping.findForward("loginfailed");
		}
		log.debug("获取用户SESSION的ID" + res.getSessionID());
		
		if(res.getSessionID() == null ||  res.getSessionID().equals("null") ||  res.getSessionID().equals("")){
			request.setAttribute("message","用户名或密码错误!");
			return actionMapping.findForward("loginfailed");
		}
		
	//	 UserLoginResEvent loginResp = (UserLoginResEvent) res;
		 // 将sessionid写入到session当中去。
		WebSessionManager.createSession(res.getSessionID(),res.getSiteId(),res.getRoleName(),name,request);	
	
		//查看当前用户的未读消息数
		int messageNum = messageTipsService.findMessages(res.getSessionID());
		request.setAttribute("messageNum", messageNum);
		log.debug("--------------------------12211name---------------------" + messageNum);
		
		//获取当前用户名
		User person = userService.findUserByKey(res.getSessionID());
		log.debug("--------------------------12211name---------------------" + res.getRoleName());
		String personalName = person.getName();
		request.setAttribute("personalName", personalName);
		//角色名
		request.setAttribute("roleName", res.getRoleName());
		
		log.debug("将登录信息写入环境对象...");
		ExternalSessionManager.getInstance().initUserAttrs(request.getSession());
		
		Map map = res.getRespMapParam();
		List list = (List)map.get("list");	
		Map mapurl = new HashMap();
	
		request.getSession().setAttribute("appName", GlobalConfig.appName);
		System.out.println("appRealPath===" + GlobalConfig.appRealPath);
		request.getSession().setAttribute("appRealPath", GlobalConfig.appRealPath);
		log.debug("LoginAction========appRealPath==="+GlobalConfig.appRealPath);
		
		//菜单集合
		String menuIds = "";
		//如果是系统超级管理员
/*		if((name.equals("admin") && res.getSessionID() != null ) || 
				(!StringUtil.isEmpty(res.getRoleName()) && res.getRoleName().equals("系统管理员")) ||
				(!StringUtil.isEmpty(res.getRoleName()) && res.getRoleName().equals("网站管理员"))){*/
		if((name.equals("admin") && res.getSessionID() != null )){
			//系统超级管理员的网站ID和用户ID都为0
			WebSessionManager.createSession(res.getSessionID(),res.getSiteId(),res.getRoleName(),name,request);	
			List<Menu> menuList = menuService.findAll();
			
			if(!StringUtil.isEmpty(res.getSiteId())) {
				if (!CollectionUtil.isEmpty(menuList)) {
					for (int i = 0 ; i < menuList.size() ; i++) {
						menuIds += "," + ((Menu)menuList.get(i)).getId();
					}
				}
			}
			if(res.getRoleName().equals("网站管理员")){
				this.setMenus(menuIds, request, "siteAdmin");
			}else{
				this.setMenus(menuIds, request, "super");
			}
			log.debug("将默认登录信息写入环境对象...");
			ExternalSessionManager.getInstance().initUserAttrs(request.getSession());
			// 获取ip
			if(GlobalConfig.ip.containsKey(res.getSessionID())) {
				GlobalConfig.ip.remove(res.getSessionID());
			}
			if (request.getHeader("x-forwarded-for") == null) {
				GlobalConfig.ip.put(res.getSessionID(), request.getRemoteAddr());
			} else { 
				GlobalConfig.ip.put(res.getSessionID(), request.getHeader("x-forwarded-for"));
			} 
			
			this.isAlreadyEnter(request.getSession(), res.getSessionID());
			request.setAttribute("menuFunction", "admin");
			return actionMapping.findForward("success");
		} 
		if((!StringUtil.isEmpty(res.getRoleName()) && res.getRoleName().equals("系统管理员")) ||
				(!StringUtil.isEmpty(res.getRoleName()) && res.getRoleName().equals("网站管理员"))){
			request.setAttribute("menuFunction", "admin");
		}
	
		//判断sessionid是否为Null
		if(res.getSessionID() == null || res.getSessionID().equals("")){			
			return actionMapping.findForward("loginfailed");
		}
		log.debug("siteId====================="+res.getSiteId());
		// 没有网站登陆
		if(res.getSiteId() == null || res.getSiteId().equals("")) {
			return actionMapping.findForward("loginSiteFailed");
		}
		User user = userService.findUserByKey(res.getSessionID());
		//现在菜单ID已#号分割，每个网站的菜单单独分开，如网站1的菜单id是2001，菜单的id分别为m1,m2.
		//网站2的菜单id是2002，菜单的id分别为m1,m2,则在数据库中保存的格式为2001,m1,m2#2002,m1,m2
		//判段用户选择的菜单栏是否为空
		String tempListchoseMenuIds=user.getChooseMenuIds();
		String tempList = user.getMenuIds();
	
	//	String userId = requestEvent.getSessionID();
		//最后处理完的菜单IDS
		String lastMenuIds = "";
		/**修改人包坤涛*/
		String   siteIds= res.getSiteId();
		if(tempListchoseMenuIds!=null&& !tempListchoseMenuIds.equals("")&& (tempListchoseMenuIds.indexOf(siteIds)!=-1)  ){
			//String strSiteMenuIds[] = tempListchoseMenuIds.split("#");
			//2009-10-22娄伟峰修改
			menuIds = this.setChooseMenuIds(tempListchoseMenuIds, siteIds, res.getRoleName(), request);
			/**修改人包坤涛*/
		}else if(tempList != null && !tempList.equals("")){
			//2009-10-22娄伟峰修改
			menuIds = this.setChooseMenuIds(tempList, siteIds, res.getRoleName(), request);
		}
		
		//2009-10-22娄伟峰新增
		if(menuIds != null && !menuIds.equals("")){
			String strMenuIds[] = menuIds.split(",");
			for(int i = 0 ; i < strMenuIds.length ; i++){
				String tempMenuIds =  strMenuIds[i];
				if((tempMenuIds.split("#").length > 1) && !StringUtil.contains(lastMenuIds, (tempMenuIds.split("#")[1]))){
					lastMenuIds = lastMenuIds + "," +(tempMenuIds.split("#")[1]);
				}				
			}
		}
	
		lastMenuIds = StringUtil.replaceFirst(lastMenuIds, ",");
		log.debug("menu==========="+lastMenuIds);
		if(StringUtil.isEmpty(lastMenuIds)) {
			return actionMapping.findForward("loginMenuFailed");
		}
	    this.setMenus(lastMenuIds, request, "normal",res.getSessionID(),res.getSiteId());
		//获取用户的登录名
		UserEntry userentity = SessionManager.singleton().getSessionEntry(res.getSessionID());
	//	request.setAttribute("tempurl", GlobalConfig.appRealPath+"/xml/organization/organization.xml");
		//将menu菜单list放到请求对象
		if (res == null || (res.getRepCode() != null && !"0".equals(res.getRepCode().trim()) && !res.getRepCode().equals(""))  || res.getSessionID() == null) {
		    return actionMapping.findForward("loginfailed");
		}
		
		// 菜单不为空时记录登陆者信息
		if(!StringUtil.isEmpty(menuIds)) {
			// 获取ip
			if(GlobalConfig.ip.containsKey(res.getSessionID())) {
				GlobalConfig.ip.remove(res.getSessionID());
			}
			if (request.getHeader("x-forwarded-for") == null) {
				GlobalConfig.ip.put(res.getSessionID(), request.getRemoteAddr());
			} else { 
				GlobalConfig.ip.put(res.getSessionID(), request.getHeader("x-forwarded-for"));
			}
			this.isAlreadyEnter(request.getSession(), res.getSessionID());
		}
		if(request.getAttribute("firstId") == null || request.getAttribute("firstId").equals("")){
			request.setAttribute("firstId", "1");
		}
		return actionMapping.findForward("success");
		//	return new ActionForward("/main/mainframe.jsp",true);
	}

	/**
	 * 判断有没有选择具体的菜单，然后处理相应的设置
	 * @param menuList
	 * @param siteId
	 * @param roleName
	 * @param request
	 * @return
	 */
	private String setChooseMenuIds(String menuList,String siteId,String roleName,HttpServletRequest request){
		String menuIds = "";
		if(StringUtil.contains(menuList, "*")){
			String strSiteMenuIds[] = menuList.split("[*]");
			for(int i = 0 ; i < strSiteMenuIds.length ; i++){
				String strMenuIds[] = strSiteMenuIds[i].split(",");
				if(strMenuIds != null && (strMenuIds.length > 0) && strMenuIds[0].equals(siteId)){
					menuIds = strSiteMenuIds[i].replaceFirst(strMenuIds[0]+",", "");
					//判断是否显示格式菜单
					if(roleName != null && roleName.equals("网站管理员")){
						request.setAttribute("menuFunction", "admin");
					}else{							
						if(StringUtil.contains(menuIds,"f002") && StringUtil.contains(menuIds,"f003")){
							//当有文章和格式时
							request.setAttribute("menuFunction", "admin");
						}else if(StringUtil.contains(menuIds,"f002") && !StringUtil.contains(menuIds,"f003")){
							//如果只有文章无格式时
							request.setAttribute("menuFunction", "1");
						}else if(!StringUtil.contains(menuIds,"f002") && StringUtil.contains(menuIds,"f003")){
							//如果无文章有格式时
							request.setAttribute("menuFunction", "2");
						}
						
					}
				}
			}
		}else{
			if(StringUtil.contains(menuList, ",")){
				String strMenuIds[] = menuList.split(",");
				if(strMenuIds != null && (strMenuIds.length > 0) && strMenuIds[0].equals(siteId)){
					menuIds = menuList.replaceFirst(strMenuIds[0]+",", "");
					//判断是否显示格式菜单
					if(roleName != null && roleName.equals("网站管理员")){
						request.setAttribute("menuFunction", "admin");
					}else{							
						if(StringUtil.contains(menuIds,"f002") && StringUtil.contains(menuIds,"f003")){
							//当有文章和格式时
							request.setAttribute("menuFunction", "admin");
						}else if(StringUtil.contains(menuIds,"f002") && !StringUtil.contains(menuIds,"f003")){
							//如果只有文章无格式时
							request.setAttribute("menuFunction", "1");
						}else if(!StringUtil.contains(menuIds,"f002") && StringUtil.contains(menuIds,"f003")){
							//如果无文章有格式时
							request.setAttribute("menuFunction", "2");
						}
						
					}
				}
			}			
		}	
		return menuIds;
	}
	/**
	 * 设置登录初始化时页面需要显示的菜单
	 * @param menuIds 菜单集合
	 * @param request 请求对象
	 * @param params  用来辨别是否是管理员，如果是管理员就可以使用（网站管理）菜单，其他用户都不可以使用
	 */
	private void setMenus(String menuIds, HttpServletRequest request, String params){
		menuIds = StringUtil.replaceFirst(menuIds,",");
		List<Menu> menuList = new ArrayList<Menu>();
		if (!StringUtil.isEmpty(menuIds)) {
		//	menuList = menuService.findMenuByIds(menuIds);
			String[] strmenuid = menuIds.split(","); 
			// 寻找第一个菜单
			int a = 0;
			int b = 0;
			for(int i = 0 ; i < strmenuid.length ; i++){
				// 非管理员不可以使用网站管理菜单
				if(params.equals("normal")) {
					if(!strmenuid[i].equals("m005")) {
						Menu menu = menuService.findMenuDataById(strmenuid[i]);
						if(a != 1 && menu != null && !menu.getId().equals("m010")){	 				
							request.setAttribute("firstId", menu.getId());
							request.setAttribute("firstName", menu.getName());
							request.setAttribute("firstIndex", menu.getIndexPage());
							request.setAttribute("firstPage", menu.getContentPage());
							a = 1;
							menuList.add(b, menu);
							b++;
						}else if(menu != null && !menu.getId().equals("m005"))	{
							menuList.add(b, menu);
							b++;
						}
					}
				// 管理员可以使用网站管理	
				} else {
					Menu menu = menuService.findMenuDataById(strmenuid[i]);
					// 网站管理员
					if(params.equals("siteAdmin")){
						if(a != 1 && menu != null && !menu.getId().equals("m010")){	 				
							request.setAttribute("firstId", menu.getId());
							request.setAttribute("firstName", menu.getName());
							request.setAttribute("firstIndex", menu.getIndexPage());
							request.setAttribute("firstPage", menu.getContentPage());
							a = 1;
							menuList.add(b, menu);
							b++;
						}else if(menu != null && !menu.getId().equals("m005"))	{
							menuList.add(b, menu);
							b++;
						}
						
					}else{
						if(a != 1 && menu != null && !menu.getId().equals("m010")){	 				
							request.setAttribute("firstId", menu.getId());
							request.setAttribute("firstName", menu.getName());
							request.setAttribute("firstIndex", menu.getIndexPage());
							request.setAttribute("firstPage", menu.getContentPage());
							a = 1;
						}	
						menuList.add(b, menu);
						b++;
					}
				}
			}
			request.setAttribute("menuList", menuList);
		}
	}
	
	//10月5号张家勇新加一重载方法 ---解决文档管理下设置了没有图片权限功能不显示图片类别管理的情况
	private void setMenus(String menuIds, HttpServletRequest request, String params,String userId,String siteId){
		menuIds = StringUtil.replaceFirst(menuIds,",");
		List<Menu> menuList = new ArrayList<Menu>();
		if (!StringUtil.isEmpty(menuIds)) {
		//	menuList = menuService.findMenuByIds(menuIds);
			String[] strmenuid = menuIds.split(","); 
			// 寻找第一个菜单
			int a = 0;
			int b = 0;
			for(int i = 0 ; i < strmenuid.length ; i++){
				// 非管理员不可以使用网站管理菜单
				if(params.equals("normal")) {
					if(!strmenuid[i].equals("m005")) {
						Menu menu = menuService.findMenuDataById(strmenuid[i]);
						if(a != 1 && menu != null && !menu.getId().equals("m010")){	 				
							request.setAttribute("firstId", menu.getId());
							request.setAttribute("firstName", menu.getName());
							request.setAttribute("firstIndex", menu.getIndexPage());
							if(menu.getId().equals("m003")){
								List list = categoryService.getTreeList("0", categoryService, siteId, userId, false);
								if(list!=null && list.size() > 0){
									
									Object[] objects = (Object[])list.get(0);
									Object nodeId = objects[0];
									menu.setContentPage("category.do?dealMethod=&nodeId="+nodeId);
								}else{
									menu.setContentPage("category.do?dealMethod=&nodeId=f004");
								}
							}
							request.setAttribute("firstPage", menu.getContentPage());
							a = 1;
							menuList.add(b, menu);
							b++;
						}else if(menu != null && !menu.getId().equals("m005"))	{
							menuList.add(b, menu);
							b++;
						}
					}
				// 管理员可以使用网站管理	
				} else {
					Menu menu = menuService.findMenuDataById(strmenuid[i]);
					// 网站管理员
					if(params.equals("siteAdmin")){
						if(a != 1 && menu != null && !menu.getId().equals("m010")){	 				
							request.setAttribute("firstId", menu.getId());
							request.setAttribute("firstName", menu.getName());
							request.setAttribute("firstIndex", menu.getIndexPage());
							request.setAttribute("firstPage", menu.getContentPage());
							a = 1;
							menuList.add(b, menu);
							b++;
						}else if(menu != null && !menu.getId().equals("m005"))	{
							menuList.add(b, menu);
							b++;
						}
						
					}else{
						if(a != 1 && menu != null && !menu.getId().equals("m010")){	 				
							request.setAttribute("firstId", menu.getId());
							request.setAttribute("firstName", menu.getName());
							request.setAttribute("firstIndex", menu.getIndexPage());
							request.setAttribute("firstPage", menu.getContentPage());
							a = 1;
						}	
						menuList.add(b, menu);
						b++;
					}
				}
			}
			request.setAttribute("menuList", menuList);
		}
	}
	/**
	 * 用户登出
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	private ActionForward LoginOut(ActionMapping actionMapping,
			   ActionForm actionForm,
			   HttpServletRequest request,
			   HttpServletResponse response) {
		HttpSession session = request.getSession();
		
		// 撤销session
		Map userMap = GlobalConfig.userSession;
	    Map logMap = GlobalConfig.loginSession;
	    Iterator itr = userMap.keySet().iterator();
	    while(itr.hasNext()) {
	    	String key = itr.next().toString();
	    	String value = userMap.get(key).toString();
	    	if(value.equals(session.getId())) {
	    	//	System.out.println("=====退出=");
	    		// 移除用户session
	       	 	GlobalConfig.userSession.remove(key);
	       	 	GlobalConfig.ip.remove(key);
	       	 	break;
	    	}
	    }
	    // 移除登陆的session
	    GlobalConfig.loginSession.remove(session.getId());
	    
		session.setAttribute("SESSION_ID", null);
		session.removeAttribute("SESSION_ID");
		session.invalidate();
		session = null;
		return actionMapping.findForward("loginout");
	}
	
	
	private ActionForward checkRand(ActionMapping actionMapping,
			   ActionForm actionForm,
			   HttpServletRequest request,
			   HttpServletResponse response) {
		HttpSession session = request.getSession();
		String sessionrand = String.valueOf(session.getAttribute("rand"));
		LoginForm form = (LoginForm) actionForm;
		String rand = String.valueOf(form.getRand());
	
		if(sessionrand.equals(rand)){
			form.setMessage("0");
		}else{
			form.setMessage("1");
		}
		return actionMapping.findForward("login_rand_msg");
	}
	
	/**
	 * 用户修改密码
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return ActionForward
	 */	
	private ActionForward LoginChangePassword(ActionMapping actionMapping,
			   ActionForm actionForm,
			   HttpServletRequest request,
			   HttpServletResponse response) {
		return actionMapping.findForward("changepassword");
	}

	/**
	 * 放入用户的sessionID和请求的session
	 * @param session
	 * @param sessionID
	 * @return
	 */
	private  void isAlreadyEnter(HttpSession session, String sessionID){   
		log.debug("session-----------"+session.getId());
		if(GlobalConfig.userSession.containsKey(sessionID)){ //如果该用户已经登录过，则使上次登录的用户掉线(依据使用户名是否在hUserName中)
        	//遍历原来的hUserName，删除原用户名对应的sessionID(即删除原来的sessionID和username)   
            Map userMap = GlobalConfig.userSession;
            Map logMap = GlobalConfig.loginSession;
           // userMap.clear();
            //logMap.clear();
            Object newSession = userMap.get(sessionID);
            // 取出session
            HttpSession logSession = (HttpSession) logMap.get(newSession);
            log.debug("logsessionId======"+logSession.getId());
            if(!logSession.getId().equals(session.getId())) {
	            // 移除用户session
	            GlobalConfig.userSession.remove(newSession);
	            //log.debug("========="+logSession.getAttribute(loginStatus));
	            try {
	            	if(logSession.getAttribute(loginStatus) != null) {
	            		logSession.setAttribute(loginStatus, "1");
	            	}
	            } catch(IllegalStateException ise) {
	            	log.debug("上一个用户session已经失效");
	            }
	            GlobalConfig.loginSession.remove(newSession);
	            //添加现在的sessionID和username   
	            session.setAttribute(loginStatus, "");
	            GlobalConfig.userSession.put(sessionID, session.getId()); 
	            GlobalConfig.loginSession.put(session.getId(), session);
            }
            
        } else{   //如果该用户没登录过，直接添加现在的sessionID和username
        	session.setAttribute(loginStatus, "");
        	GlobalConfig.userSession.put(sessionID, session.getId()); 
            GlobalConfig.loginSession.put(session.getId(), session);
        }   
	}
	
	/**
	 * @param menuService the menuService to set
	 */
	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	/**
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * @param loginService the loginService to set
	 */
	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}
	
	public void setMessageTipsService(MessageTipsService messageTipsService) {
		this.messageTipsService = messageTipsService;
	}
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

 }
