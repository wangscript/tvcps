/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.plugin.rss.web.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.baize.common.core.web.GeneralForm;

/**
 * <p>
 * 标题: —— 要求能简洁地表达出类的功能和职责
 * </p>
 * <p>
 * 描述: —— 简要描述类的职责、实现方式、使用注意事项等
 * </p>
 * <p>
 * 模块: CCMS
 * </p>
 * <p>
 * 版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * </p>
 * <p>
 * 网址：http://www.baizeweb.com
 * 
 * @author 曹名科
 * @version 1.0
 * @since 2009-10-16 下午03:52:01
 * @history（历次修订内容、修订人、修订时间等） 
 */
 
public class RssForm extends GeneralForm {
	private static final long serialVersionUID = 1177843005554046494L;
	/** 间隔时间 */
	private String spacingTime;
	/** 单栏目OR多栏目 */
	private String isColumnOrMoreColumn;
	/** 显示的栏目集合 */
	private List list;
	/** rss内容 */
	private String rssContent;

	/**
	 * @return the spacingTime
	 */
	public String getSpacingTime() {
		return spacingTime;
	}

	/**
	 * @param spacingTime
	 *            the spacingTime to set
	 */
	public void setSpacingTime(String spacingTime) {
		this.spacingTime = spacingTime;
	}

	/**
	 * @return the isColumnOrMoreColumn
	 */
	public String getIsColumnOrMoreColumn() {
		return isColumnOrMoreColumn;
	}

	/**
	 * @param isColumnOrMoreColumn
	 *            the isColumnOrMoreColumn to set
	 */
	public void setIsColumnOrMoreColumn(String isColumnOrMoreColumn) {
		this.isColumnOrMoreColumn = isColumnOrMoreColumn;
	}

	@Override
	protected void validateData(ActionMapping arg0, HttpServletRequest arg1) {
		// TODO Auto-generated method stub

	}

	/**
	 * @return the list
	 */
	public List getList() {
		return list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(List list) {
		this.list = list;
	}

	/**
	 * @return the rssContent
	 */
	public String getRssContent() {
		return rssContent;
	}

	/**
	 * @param rssContent the rssContent to set
	 */
	public void setRssContent(String rssContent) {
		this.rssContent = rssContent;
	}

}
