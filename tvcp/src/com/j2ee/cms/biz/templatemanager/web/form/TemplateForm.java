/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.templatemanager.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.j2ee.cms.biz.templatemanager.domain.Template;
import com.j2ee.cms.common.core.web.GeneralForm;

/**
 * <p>标题: 模板表单</p>
 * <p>描述: 模板的表单数据，以便页面和方法中调用</p>
 * <p>模块: 模板管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-4-9 下午03:53:49
 * @history（历次修订内容、修订人、修订时间等）
 */
public class TemplateForm extends GeneralForm {

	private static final long serialVersionUID = -7954837061038542767L;
	
	/** 模板 */
	private Template template = new Template();
	/** 网站id **/
	private String siteId;
	/**	用户id **/
	private String creatorId;
	/** 粘贴的ids **/
	private String pasteIds;
	/** 要打开的链接的url **/
	private String url;
	/** 创建日期 **/
	private String createDate;
	/**模板路径.*/
	private String tempPath;
	/**模板内容**/
	private String tempContent;

	/**
	 * @return the createDate
	 */
	public String getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the pasteIds
	 */
	public String getPasteIds() {
		return pasteIds;
	}

	/**
	 * @param pasteIds the pasteIds to set
	 */
	public void setPasteIds(String pasteIds) {
		this.pasteIds = pasteIds;
	}

	/**
	 * @return the siteId
	 */
	public String getSiteId() {
		return siteId;
	}

	/**
	 * @param siteId the siteId to set
	 */
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	/**
	 * @return the creatorId
	 */
	public String getCreatorId() {
		return creatorId;
	}

	/**
	 * @param creatorId the creatorId to set
	 */
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	/**
	 * @return the template
	 */
	public Template getTemplate() {
		return template;
	}

	/**
	 * @param template the template to set
	 */
	public void setTemplate(Template template) {
		this.template = template;
	}
	
	@Override
	protected void validateData(ActionMapping mapping,
			HttpServletRequest request) {
	}

	/**
	 * @return the tempPath
	 */
	public String getTempPath() {
		return tempPath;
	}

	/**
	 * @param tempPath the tempPath to set
	 */
	public void setTempPath(String tempPath) {
		this.tempPath = tempPath;
	}

	/**
	 * @return the tempContent
	 */
	public String getTempContent() {
		return tempContent;
	}

	/**
	 * @param tempContent the tempContent to set
	 */
	public void setTempContent(String tempContent) {
		this.tempContent = tempContent;
	}
}
