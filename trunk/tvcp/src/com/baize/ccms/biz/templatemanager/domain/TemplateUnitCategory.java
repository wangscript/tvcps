/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.templatemanager.domain;

import java.io.Serializable;

/**
 * <p>模板: 单元类别</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 模板管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-5-26 上午10:22:23
 * @history（历次修订内容、修订人、修订时间等）
 */
public class TemplateUnitCategory implements Serializable {

	private static final long serialVersionUID = -7478354338526538485L;
	
	/** 唯一标识符 */
	private String id;
	
	/** 类别名称 */
	private String name;
	
	/** 类别对应的配置URL */
	private String configUrl;
	
	/** 类别对应的配置文件（相对于应用的路径） */
	private String configFile;
	
	/** 对应样式 */
	private String css;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the configFile
	 */
	public String getConfigFile() {
		return configFile;
	}

	/**
	 * @param configFile the configFile to set
	 */
	public void setConfigFile(String configFile) {
		this.configFile = configFile;
	}

	/**
	 * @return the configUrl
	 */
	public String getConfigUrl() {
		return configUrl;
	}

	/**
	 * @param configUrl the configUrl to set
	 */
	public void setConfigUrl(String configUrl) {
		this.configUrl = configUrl;
	}

	/**
	 * @return the css
	 */
	public String getCss() {
		return css;
	}

	/**
	 * @param css the css to set
	 */
	public void setCss(String css) {
		this.css = css;
	}

}
