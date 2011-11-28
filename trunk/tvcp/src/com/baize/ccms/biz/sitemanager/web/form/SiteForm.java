/**
 * project：通用内容管理系统
 * Company: 南京瀚沃信息科技有限责任公司
 */
package com.baize.ccms.biz.sitemanager.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.baize.ccms.biz.sitemanager.domain.Site;
import com.baize.ccms.biz.usermanager.domain.User;
import com.baize.common.core.web.GeneralForm;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CCMS</p>
 * <p>版权: Copyright (c) 2009 南京瀚沃信息科技有限责任公司
 * @author 郑荣华
 * @version 1.0
 * @since 2009-4-3 下午03:26:52
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class SiteForm extends GeneralForm {

	 private static final long serialVersionUID = 2364035854475876301L;
    
	 /**创建一个用户对象**/
	 private User creator = new User();
	 /**创建一个网站父对象**/
	 private Site parent = new Site();
	 /**创建网站的日期**/
	 private String createTime;
	 /**创建一个网站对象**/
	 private Site site = new Site();
	 /**用户的id**/
	 private String userid;
	 /**网站的id**/
	 private String siteid;
	 /**网站的父id**/
	 private String parentid;
	 /**切换网站中网站的名字**/
	 private String webname;
	 /**切换网站中网站的id**/
	 private String webid;
	 /**切换网站中网站的当前id**/
	 private String currentwebid;
	 /**添加完网站后显示信息**/
	 private String loginMessage;
	 /**用于登陆密码**/
	 private String siteloginpassword;
	 /**用户登陆名**/
	 private String siteloginname;
	 /** 修改前的网站名称**/
	 private String oldSiteName;
	 
	 /** 当前用户拥有的角色所在的网站**/
	 private String siteOfRolesIn;
	 
	/**
	 * @return the loginMessage
	 */
	public String getLoginMessage() {
		return loginMessage;
	}

	/**
	 * @param loginMessage the loginMessage to set
	 */
	public void setLoginMessage(String loginMessage) {
		this.loginMessage = loginMessage;
	}

	/**
	 * @return the siteloginpassword
	 */
	public String getSiteloginpassword() {
		return siteloginpassword;
	}

	/**
	 * @param siteloginpassword the siteloginpassword to set
	 */
	public void setSiteloginpassword(String siteloginpassword) {
		this.siteloginpassword = siteloginpassword;
	}

	/**
	 * @return the siteloginname
	 */
	public String getSiteloginname() {
		return siteloginname;
	}

	/**
	 * @param siteloginname the siteloginname to set
	 */
	public void setSiteloginname(String siteloginname) {
		this.siteloginname = siteloginname;
	}

	
	/**
	 * @return the site
	 */
	public Site getSite() {
		return site;
	}

	/**
	 * @param site the site to set 要设置的网站对象
	 */
	public void setSite(Site site) {
		this.site = site;
	}

	/**
	 * @return the createTime
	 */
	public String getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set 要设置的网站的创建时间
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the creator
	 */
	public User getCreator() {
		return creator;
	}

	/**
	 * @param creator the creator to set 要设置的网站的创建者对象
	 */
	public void setCreator(User creator) {
		this.creator = creator;
	}

	/**
	 * @return the parent
	 */
	public Site getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set 要设置的网站的父对象
	 */
	public void setParent(Site parent) {
		this.parent = parent;
	}

	/**
	 * @return the webname
	 */
	public String getWebname() {
		return webname;
	}

	/**
	 * @param webname the webname to set 切换网站中要设置的网站的名字
	 */
	public void setWebname(String webname) {
		this.webname = webname;
	}

	/**
	 * @return the webid
	 */
	public String getWebid() {
		return webid;
	}

	/**
	 * @param webid the webid to set 切换网站中要设置的网站中的id
	 */
	public void setWebid(String webid) {
		this.webid = webid;
	}

	/**
	 * @return the currentwebid
	 */
	public String getCurrentwebid() {
		return currentwebid;
	}

	/**
	 * @param currentwebid the currentwebid to set 切换网站中要设置的网站的当前网站id
	 */
	public void setCurrentwebid(String currentwebid) {
		this.currentwebid = currentwebid;
	}
	
	@Override
	protected void validateData(ActionMapping arg0, HttpServletRequest arg1) {
	}

	/**
	 * @return the userid
	 */
	public String getUserid() {
		return userid;
	}

	/**
	 * @param userid the userid to set
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}

	/**
	 * @return the siteid
	 */
	public String getSiteid() {
		return siteid;
	}

	/**
	 * @param siteid the siteid to set
	 */
	public void setSiteid(String siteid) {
		this.siteid = siteid;
	}

	/**
	 * @return the parentid
	 */
	public String getParentid() {
		return parentid;
	}

	/**
	 * @param parentid the parentid to set
	 */
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public void setOldSiteName(String oldSiteName) {
		this.oldSiteName = oldSiteName;
	}

	public String getOldSiteName() {
		return oldSiteName;
	}

	public void setSiteOfRolesIn(String siteOfRolesIn) {
		this.siteOfRolesIn = siteOfRolesIn;
	}

	public String getSiteOfRolesIn() {
		return siteOfRolesIn;
	}

}
