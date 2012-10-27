/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.unitmanager.web.form;

import com.j2ee.cms.common.core.web.GeneralForm;

/**
 * <p>标题: 单元表单抽象类</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 单元管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-6-1 上午09:48:50
 * @history（历次修订内容、修订人、修订时间等）
 */
public abstract class UnitForm extends GeneralForm {

	private static final long serialVersionUID = 4997628941323581831L;
	
	/** 类别ID */
	private String unit_categoryId;
	
	/** 单元ID */
	private String unit_unitId;
	
	/** 栏目ID */
	private String unit_columnId;

	/** 前端显示样式管理 **/
	private String unit_css;
	
	/** 样式管理的id */
	private String styleId;
	
	/** 样式预览地址 */
	private String stylePreview;
	
	/** 样式具体内容 */
	private String styleContent;

	/**
	 * @return the unit_categoryId
	 */
	public String getUnit_categoryId() {
		return unit_categoryId;
	}

	/**
	 * @param unitCategoryId the unit_categoryId to set
	 */
	public void setUnit_categoryId(String unitCategoryId) {
		unit_categoryId = unitCategoryId;
	}

	/**
	 * @return the unit_unitId
	 */
	public String getUnit_unitId() {
		return unit_unitId;
	}

	/**
	 * @param unitUnitId the unit_unitId to set
	 */
	public void setUnit_unitId(String unitUnitId) {
		unit_unitId = unitUnitId;
	}

	/**
	 * @return the unit_columnId
	 */
	public String getUnit_columnId() {
		return unit_columnId;
	}

	/**
	 * @param unitColumnId the unit_columnId to set
	 */
	public void setUnit_columnId(String unitColumnId) {
		unit_columnId = unitColumnId;
	}

	/**
	 * @return the unit_css
	 */
	public String getUnit_css() {
		return unit_css;
	}

	/**
	 * @param unitCss the unit_css to set
	 */
	public void setUnit_css(String unitCss) {
		unit_css = unitCss;
	}

	/**
	 * @return the styleId
	 */
	public String getStyleId() {
		return styleId;
	}

	/**
	 * @param styleId the styleId to set
	 */
	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}

	/**
	 * @return the stylePreview
	 */
	public String getStylePreview() {
		return stylePreview;
	}

	/**
	 * @param stylePreview the stylePreview to set
	 */
	public void setStylePreview(String stylePreview) {
		this.stylePreview = stylePreview;
	}

	/**
	 * @return the styleContent
	 */
	public String getStyleContent() {
		return styleContent;
	}

	/**
	 * @param styleContent the styleContent to set
	 */
	public void setStyleContent(String styleContent) {
		this.styleContent = styleContent;
	}
	

}
