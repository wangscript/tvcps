/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.biz.unitmanager.web.form;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.baize.ccms.biz.templatemanager.domain.TemplateUnitStyle;
import com.baize.ccms.biz.unitmanager.label.CommonLabel;
import com.baize.ccms.biz.unitmanager.label.CurrentLocationLabel;

/**
 * <p>标题: —— 当前位置</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 模板单元管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-6-15 上午10:19:05
 * @history（历次修订内容、修订人、修订时间等） 
 */

public class CurrentLocationForm  extends UnitForm {
	
	private static final long serialVersionUID = -4632925426741826119L;
	
	/** 显示样式
	 * */
	private String viewStyle;
	/** 内容来源
	 * 1-当前栏目
	 * 2-指定栏目
	 * */
	private String contextFrom;
	/** 指定栏目   */
	private String columnName;
	/** 有效字数 */
	private String titleLimit;
	/** html内容*/
	private String htmlContent;
	/** 显示图片 */
	private String viewImg;
	/** 所有标题单元标签*/
	private Map map = CurrentLocationLabel.map;
	
	/** 所有公用*/
	private Map commonMap = CommonLabel.allLabels;
	/** 所有单元样式*/
	private List<TemplateUnitStyle> templateUnitStyleList;

	
	/**
	 * @return the viewStyle
	 */
	public String getViewStyle() {
		return viewStyle;
	}

	/**
	 * @param viewStyle the viewStyle to set
	 */
	public void setViewStyle(String viewStyle) {
		this.viewStyle = viewStyle;
	}

	/**
	 * @return the contextFrom
	 */
	public String getContextFrom() {
		return contextFrom;
	}

	/**
	 * @param contextFrom the contextFrom to set
	 */
	public void setContextFrom(String contextFrom) {
		this.contextFrom = contextFrom;
	}

	/**
	 * @return the columnName
	 */
	public String getColumnName() {
		return columnName;
	}

	/**
	 * @param columnName the columnName to set
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	/**
	 * @return the htmlContent
	 */
	public String getHtmlContent() {
		return htmlContent;
	}

	/**
	 * @param htmlContent the htmlContent to set
	 */
	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}

	/**
	 * @return the map
	 */
	public Map getMap() {
		return map;
	}

	/**
	 * @param map the map to set
	 */
	public void setMap(Map map) {
		this.map = map;
	}

	/**
	 * @return the commonMap
	 */
	public Map getCommonMap() {
		return commonMap;
	}

	/**
	 * @param commonMap the commonMap to set
	 */
	public void setCommonMap(Map commonMap) {
		this.commonMap = commonMap;
	}

	/**
	 * @return the templateUnitStyleList
	 */
	public List<TemplateUnitStyle> getTemplateUnitStyleList() {
		return templateUnitStyleList;
	}

	/**
	 * @param templateUnitStyleList the templateUnitStyleList to set
	 */
	public void setTemplateUnitStyleList(
			List<TemplateUnitStyle> templateUnitStyleList) {
		this.templateUnitStyleList = templateUnitStyleList;
	}

	@Override
	protected void validateData(ActionMapping mapping,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @return the titleLimit
	 */
	public String getTitleLimit() {
		return titleLimit;
	}

	/**
	 * @param titleLimit the titleLimit to set
	 */
	public void setTitleLimit(String titleLimit) {
		this.titleLimit = titleLimit;
	}

	/**
	 * @return the viewImg
	 */
	public String getViewImg() {
		return viewImg;
	}

	/**
	 * @param viewImg the viewImg to set
	 */
	public void setViewImg(String viewImg) {
		this.viewImg = viewImg;
	}

}
