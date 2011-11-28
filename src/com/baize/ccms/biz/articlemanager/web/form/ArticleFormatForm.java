/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.biz.articlemanager.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.baize.ccms.biz.articlemanager.domain.ArticleFormat;
import com.baize.common.core.web.GeneralForm;

/**
 * <p>标题: 格式表单</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 文章管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-3-31 上午09:42:50
 * @history（历次修订内容、修订人、修订时间等）
 */
public class ArticleFormatForm extends GeneralForm {

	private static final long serialVersionUID = -6880762919779822762L;
	
	/** 格式 */
	private ArticleFormat format = new ArticleFormat();
	
	/** 格式ID */
	private String id;
	
	/** 导出格式ids**/
	private String exportFormatIds;
	
	/** 导入格式路径**/
	private String path;
	
	@Override
	protected void validateData(ActionMapping mapping,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @return the format
	 */
	public ArticleFormat getFormat() {
		return format;
	}

	/**
	 * @param format the format to set
	 */
	public void setFormat(ArticleFormat format) {
		this.format = format;
	}

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

	public void setExportFormatIds(String exportFormatIds) {
		this.exportFormatIds = exportFormatIds;
	}

	public String getExportFormatIds() {
		return exportFormatIds;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

}
