/**
 * 
 */
package com.baize.ccms.biz.usermanager.service;

import java.util.List;
import java.util.Map;

import com.baize.ccms.biz.sitemanager.dao.SiteDao;
import com.baize.ccms.biz.sitemanager.domain.Site;
import com.baize.ccms.biz.usermanager.dao.AssignmentDao;
import com.baize.ccms.biz.usermanager.dao.LoginDao;
import com.baize.ccms.biz.usermanager.domain.Assignment;
import com.baize.ccms.biz.usermanager.domain.User;
import com.baize.ccms.biz.usermanager.web.event.SessionManager;
import com.baize.ccms.biz.usermanager.web.event.UserLoginReqEvent;
import com.baize.common.core.service.BaseService;
import com.baize.common.core.util.CollectionUtil;
import com.baize.common.core.util.StringUtil;
import com.baize.common.core.web.event.RequestEvent;
import com.baize.common.core.web.event.ResponseEvent;

/**
 * 登录业务处理类 package: com.baize.ccms.biz.login.service File: LoginServiceImpl.java
 * 创建时间:2009-1-6下午03:01:24 Title: 标题（要求能简洁地表达出类的功能和职责） Description:
 * 描述（简要描述类的职责、实现方式、使用注意事项等） Copyright: Copyright (c) 2009 南京百泽网络科技有限公司
 * Company: 南京百泽网络科技有限公司 模块: 用户管理模块
 * 
 * @author 娄伟峰
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class LoginService extends BaseService {

	private RequestEvent reqEvent;

	/**
	 * 登录模块数据库操作类
	 */
	private LoginDao loginDao;
	
	/** 网站Dao */
	private SiteDao siteDao;

	/** 用户分配DAO */
	private AssignmentDao assignmentDao;

	@Override
	public void performTask(RequestEvent requestEvent, ResponseEvent responseEvent)
	throws Exception {
		String dealMethod = requestEvent.getDealMethod();
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		if ("checklogin".equals(dealMethod)) {
			responseEvent = this.loginSystem(requestEvent, responseEvent);
		}
	}

	/**
	 * 获取登录的用户名跟密码保存到缓存中
	 * 
	 * @param req
	 * @param userList
	 *            用户对象集合
	 * @return UserLoginResEvent
	 * @throws Exception
	 */
	private ResponseEvent loginSystem(RequestEvent req, ResponseEvent resp) throws Exception {

		UserLoginReqEvent loginReqEvent = (UserLoginReqEvent) req; // 对接受的request对象进行造型
		// String remoteUserid = loginReq.getRemoteUserid();
		String loginName = loginReqEvent.getLoginName(); // 取登陆名
		String password = loginReqEvent.getPassword(); // 取密码
		String md5Pwd = "";
		if (password.length() == 32) {
			md5Pwd = password;
		} else {
			md5Pwd = StringUtil.encryptMD5(password);
		}

		// 用户表唯一标识ID
		String id = null;
		
		String str[] = { "loginName", "password" };
		Object obj[] = { loginName, md5Pwd };
		List userList = loginDao.findByNamedQuery("checkUser", str, obj);
		String siteId = "";
		boolean loginOk = false;
		
		
		// 检验该用户是否存在 
		if (userList.size() > 0) {
			// 登陆成功
			loginOk = true;
			User user = (User) userList.get(0);
			id = user.getId();
			List<String> siteIds = user.getSiteIds();
			
			// 获取网站
			if (!CollectionUtil.isEmpty(siteIds)) {
				if(siteIds.get(0).equals("") && siteIds.size() > 1){
					siteId = siteIds.get(1);
				}else{
					siteId = siteIds.get(0);
				}
				
			} else if ("admin".equals(user.getName())) {	// superAdmin
//				List<Site> sites = siteDao.findAll();
				List<Site> sites = siteDao.findByNamedQuery("findSiteByDeleted");
				if (CollectionUtil.isEmpty(sites)) {
					siteId = String.valueOf(sites.get(0).getId());
				}
			}
		}
		
		if (loginOk == false) {
			ResponseEvent res = new ResponseEvent(null, req
					.getChannelType(), siteId, "", "");
			res.setRepCode("00200"); // 用户名，密码不正确，登陆失败！

			return res;
		}
		
		// 获取角色
		String roleName = "";
/*		List<Assignment> assignmentSystemList = assignmentDao.findByNamedQuery("findRoleIdByUserId","id",id);
		if(assignmentSystemList != null && assignmentSystemList.size() > 0){
			
			roleName = ((Assignment)assignmentSystemList.get(0)).getRole().getName();
		}
		if(roleName != null && !roleName.equals("") && !roleName.equals("系统管理员")){
			
		}else{
			String assstr[] = {"id","siteid"};
			Object assobj[] = {id,siteId};
			List<Assignment> assignmentList = assignmentDao.findByNamedQuery("findRoleIdByUserIdAndSiteId", assstr, assobj);
			if (assignmentList.size() > 0) {
				roleName = ((Assignment) assignmentList.get(0)).getRole().getName();
			}
		}*/
		
		String assstr[] = {"id","siteid"};
		Object assobj[] = {id,siteId};
		List<Assignment> assignmentList = assignmentDao.findByNamedQuery("findRoleIdByUserIdAndSiteId", assstr, assobj);
		for(int i = 0 ; i < assignmentList.size(); i++){
			String temproleName = ((Assignment) assignmentList.get(0)).getRole().getName();
			if(temproleName.equals("系统管理员")){
				roleName = "系统管理员";
			}
			if(temproleName.equals("网站管理员") && !roleName.equals("系统管理员")){
				roleName = "网站管理员";
			}
			if(roleName.equals("")){
				roleName = temproleName;
			}
			
		}
		if (assignmentList.size() > 0) {
			roleName = ((Assignment) assignmentList.get(0)).getRole().getName();
		}
		
		
		// 装配入口参数
		String[] entryargs = new String[4];
		entryargs[0] = id;
		entryargs[1] = req.getChannelType();
		entryargs[2] = siteId;
		entryargs[3] = roleName;
		
		// 保存sessionid到缓存中
		String sessionid = SessionManager.singleton().addSessionEntry(
				entryargs, req);
		log.debug("************sessionid = " + sessionid);
		
		// 要加入角色ID
		resp.setSessionID(sessionid);
		resp.setChannelType(req.getChannelType());
		resp.setRoleName(roleName);
		resp.setLoginName(loginName);
		resp.setSiteId(siteId);
		
		return resp;
		
		
		
//		String loginType = loginReqEvent.getLoginType();
//		// if (remoteUserid == null || ("".equals(remoteUserid))) {//
//		// 在普通登陆(非sso登陆时)才进行密码校验
//		if (UserLoginReqEvent.LOGIN_TYPE_FRONT.equals(loginType)) {// 为前台登录
//			/*
//			 * } else if (UserLoginReqEvent.LOGIN_TYPE_BACK.equals(loginType))
//			 * {// 为后台登陆--已在sso // server做了验证,现直接表明通过 verified = true; }
//			 */
//
//			// 如果登陆失败，返回
//			if (loginOk == false) {
//				ResponseEvent res = new ResponseEvent(null, req
//						.getChannelType(), siteId, "", "");
//				res.setRepCode("00200"); // 用户名，密码不正确，登陆失败！
//
//				return res;
//			}
//		} else {
//			// userid = remoteUserid;
//			loginOk = true;
//		}

	}

	@Override
	public ResponseEvent validateData(RequestEvent req) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the reqEvent
	 */
	public RequestEvent getReqEvent() {
		return reqEvent;
	}

	/**
	 * @param reqEvent
	 *            the reqEvent to set
	 */
	public void setReqEvent(RequestEvent reqEvent) {
		this.reqEvent = reqEvent;
	}

	/**
	 * @param loginDao
	 *            要设置的 loginDao
	 */
	public void setLoginDao(LoginDao loginDao) {
		this.loginDao = loginDao;
	}

	/**
	 * @param assignmentDao
	 *            the assignmentDao to set
	 */
	public void setAssignmentDao(AssignmentDao assignmentDao) {
		this.assignmentDao = assignmentDao;
	}

	/**
	 * @param siteDao the siteDao to set
	 */
	public void setSiteDao(SiteDao siteDao) {
		this.siteDao = siteDao;
	}

}
