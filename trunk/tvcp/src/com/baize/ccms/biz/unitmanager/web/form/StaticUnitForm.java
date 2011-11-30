/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.unitmanager.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 单元管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-6-1 上午12:18:04
 * @history（历次修订内容、修订人、修订时间等）
 */
public class StaticUnitForm extends UnitForm {

	private static final long serialVersionUID = 3284341151919228829L;
	
	/** 静态内容 */
	private String staticContent;
	
	@Override
	protected void validateData(ActionMapping arg0, HttpServletRequest arg1) {
		
	}

	/**
	 * @return the staticContent
	 */
	public String getStaticContent() {
		return staticContent;
	}

	/**
	 * @param staticContent the staticContent to set
	 */
	public void setStaticContent(String staticContent) {
		this.staticContent = staticContent;
	}

}
