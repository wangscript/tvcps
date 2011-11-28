/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.biz.templatemanager.domain;

import java.io.File;
import java.io.Serializable;

import com.baize.ccms.biz.sitemanager.domain.Site;

/**
 * <p>标题: 模板单元</p>
 * <p>描述: 是模板实例的基本组成元素，和模板实例是多对一的关系</p>
 * <p>模块: 模板管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-5-12 下午02:24:18
 * @history（历次修订内容、修订人、修订时间等）
 */
public class TemplateUnit implements Serializable {

	private static final long serialVersionUID = 2259167988094065340L;
	
	/** 最初模板单元正则 */
	public static final String REGEX_ORIGINAL = "<!--\\s*\\{\\[%\\s*-->([^!]*)<!--\\s*%\\]\\}\\s*-->";
	
	/** span模板正则 */
	public static final String REGEX_SPAN = "<span\\s*style\\s*=\\s*\"([^>]+)\"\\s*>([^<]+)</span\\s*>";
	
	/** 替换后模板单元正则 */
	public static final String REGEX_REPLACE = "\\{[^{]*\\}->未设";
	
	/** 需要发布的单元正则 */
	public static final String REGEX_PUBLISH = "<!--\\{%\\[([\\d\\w]+)\\]-->(.*)<!--%\\}-->";
	
	/** 配置文件存放路径（相对于应用） */
	public static final String UNIT_CONFIG_DIR = File.separator+"module"+File.separator+"template_set"+File.separator+"config";

	/** 唯一标识符 */
	private String id;
	
	/** 单元名称 */
	private String name;
	
	/** 单元html代码 */
	private String html;
	
	/** 样式 */
	private String css;
	
	/** 脚本 */
	private String script;
	
	/** 配置文件（文件名） */
	private String configFile;
	
	/** 模板实例存放单元配置的目录 */
	private String configDir;
	
	/** 所属单元类别 */
	private TemplateUnitCategory templateUnitCategory;
	
	/** 所属模板实例 */
	private TemplateInstance templateInstance;

	/** 网站 */
	private Site site;
	
	/** 栏目ids的集合（要同步更新的栏目） */
	private String columnIds;
	
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
	 * @return the html
	 */
	public String getHtml() {
		return html;
	}

	/**
	 * @param html the html to set
	 */
	public void setHtml(String html) {
		this.html = html;
	}

	/**
	 * @return the templateInstance
	 */
	public TemplateInstance getTemplateInstance() {
		return templateInstance;
	}

	/**
	 * @param templateInstance the templateInstance to set
	 */
	public void setTemplateInstance(TemplateInstance templateInstance) {
		this.templateInstance = templateInstance;
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
	 * @return the templateUnitCategory
	 */
	public TemplateUnitCategory getTemplateUnitCategory() {
		return templateUnitCategory;
	}

	/**
	 * @param templateUnitCategory the templateUnitCategory to set
	 */
	public void setTemplateUnitCategory(TemplateUnitCategory templateUnitCategory) {
		this.templateUnitCategory = templateUnitCategory;
	}

	/**
	 * @return the configDir
	 */
	public String getConfigDir() {
		return configDir;
	}

	/**
	 * @param configDir the configDir to set
	 */
	public void setConfigDir(String configDir) {
		this.configDir = configDir;
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

	/**
	 * @return the script
	 */
	public String getScript() {
		return script;
	}

	/**
	 * @param script the script to set
	 */
	public void setScript(String script) {
		this.script = script;
	}

	/**
	 * @return the site
	 */
	public Site getSite() {
		return site;
	}

	/**
	 * @param site the site to set
	 */
	public void setSite(Site site) {
		this.site = site;
	}

	/**
	 * @return the columnIds
	 */
	public String getColumnIds() {
		return columnIds;
	}

	/**
	 * @param columnIds the columnIds to set
	 */
	public void setColumnIds(String columnIds) {
		this.columnIds = columnIds;
	}

}
