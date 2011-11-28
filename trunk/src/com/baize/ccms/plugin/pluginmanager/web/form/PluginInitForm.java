/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.plugin.pluginmanager.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.baize.common.core.web.GeneralForm;

/**
 * <p>标题: 插件管理初始化程序表单</p>
 * <p>描述: 插件管理程序表单数据</p>
 * <p>模块: 插件管理</p>
 * <p>版权: Copyright (c) 2009南京百泽网络科技有限公司
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-7-15 下午04:13:06 
 * @history（历次修订内容、修订人、修订时间等） 
*/

public class PluginInitForm extends GeneralForm{

	private static final long serialVersionUID = -5662807453631912349L;
	
	/** 点击插件管理时打开的右侧首页 */	
	private String rightFrameUrl;

	

	/* (non-Javadoc)
	 * @see com.baize.common.core.web.GeneralForm#validateData(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected void validateData(ActionMapping arg0, HttpServletRequest arg1) {
		// TODO Auto-generated method stub
		
	}



	/**
	 * @return the rightFrameUrl
	 */
	public String getRightFrameUrl() {
		return rightFrameUrl;
	}



	/**
	 * @param rightFrameUrl the rightFrameUrl to set
	 */
	public void setRightFrameUrl(String rightFrameUrl) {
		this.rightFrameUrl = rightFrameUrl;
	}
	
}
