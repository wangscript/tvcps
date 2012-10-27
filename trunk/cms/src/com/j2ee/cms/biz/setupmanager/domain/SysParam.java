/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.setupmanager.domain;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CPS</p>
 * <p>版权: Copyright (c) 2009  </p>
 * <p>网址：http://www.j2ee.cmsweb.com
 * @author 曹名科
 * @version 1.0
 * @since 2009-9-8 下午06:03:32
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class SysParam extends GenericReg{
	/** 系统安装路径. **/
	private String loadPath;
	/** 服务器名称. **/
	private String serverName;
	/** 数据库类型. **/
	private String databaseType;
	/** 部署应用名称. **/
	private String appName;
	/** 发布类型. **/
	private String putType;
	/** 登陆验证码. **/
	private String checkCode;
	/**密码.**/
	private String pwd;
	
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPwd() {
		return pwd;
	}

	public String getLoadPath() {
		return loadPath;
	}

	public void setLoadPath(String loadPath) {
		this.loadPath = loadPath;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getDatabaseType() {
		return databaseType;
	}

	public void setDatabaseType(String databaseType) {
		this.databaseType = databaseType;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getPutType() {
		return putType;
	}

	public void setPutType(String putType) {
		this.putType = putType;
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
}
