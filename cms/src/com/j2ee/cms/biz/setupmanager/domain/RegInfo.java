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
 * @since 2009-9-7 下午04:14:08
 * @history（历次修订内容、修订人、修订时间等） 
 */
 

public class RegInfo extends GenericReg {
	/** 用户名 . **/
	private String userName;
	/** 密码 . **/
	private String userPassword;

	/** 版本号 . **/
	private String version;
	/** 注册码 . **/
	private String regCode;
	/** 栏目数 . **/
	private String columCount;
	/** 网站数 . **/
	private String siteCount;
	/** 注册日期 . **/
	private String dateMax;
	/**年.**/
	private String year;
	/**月.**/
	private String month;
	/**日.**/
	private String day;

	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getRegCode() {
		return regCode;
	}

	public void setRegCode(String regCode) {
		this.regCode = regCode;
	}

	public String getColumCount() {
		return columCount;
	}

	public void setColumCount(String columCount) {
		this.columCount = columCount;
	}

	public String getSiteCount() {
		return siteCount;
	}

	public void setSiteCount(String siteCount) {
		this.siteCount = siteCount;
	}

	public String getDateMax() {
		return dateMax;
	}

	public void setDateMax(String dateMax) {
		this.dateMax = dateMax;
	}

}
