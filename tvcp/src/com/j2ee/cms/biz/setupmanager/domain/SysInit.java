/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.setupmanager.domain;

/**
 * <p>
 * 标题: —— 要求能简洁地表达出类的功能和职责
 * </p>
 * <p>
 * 描述: —— 简要描述类的职责、实现方式、使用注意事项等
 * </p>
 * <p>
 * 模块: CPS
 * </p>
 * <p>
 * 版权: Copyright (c) 2009  
 * </p>
 * <p>
 * 网址：http://www.j2ee.cmsweb.com
 * 
 * @author 曹名科
 * @version 1.0
 * @since 2009-9-8 下午06:55:31
 * @history（历次修订内容、修订人、修订时间等） 
 */
 
public class SysInit extends  GenericReg{
	/** 网站群名称. **/
	private String siteName;
	/** 服务器IP. **/
	private String serverIP;
	/** 数据库连接端口. **/
	private String dataPort;
	/** 数据库名称. **/
	private String dataBaseName;
	/** 数据库用户名. **/
	private String dataUserName;
	/** 数据库密码. **/
	private String dataUserPass;
	/**次数 为0则是没有创建数据库，1表示创建好了数据库.**/
	private String countId;
	public void setCountId(String countId) {
		this.countId = countId;
	}

	public String getCountId() {
		return countId;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getServerIP() {
		return serverIP;
	}

	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	public String getDataPort() {
		return dataPort;
	}

	public void setDataPort(String dataPort) {
		this.dataPort = dataPort;
	}

	public String getDataBaseName() {
		return dataBaseName;
	}

	public void setDataBaseName(String dataBaseName) {
		this.dataBaseName = dataBaseName;
	}

	public String getDataUserName() {
		return dataUserName;
	}

	public void setDataUserName(String dataUserName) {
		this.dataUserName = dataUserName;
	}

	public String getDataUserPass() {
		return dataUserPass;
	}

	public void setDataUserPass(String dataUserPass) {
		this.dataUserPass = dataUserPass;
	}

}
