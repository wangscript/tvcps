/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.articlemanager.web.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.baize.ccms.biz.articlemanager.domain.Enumeration;
import com.baize.common.core.web.GeneralForm;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CCMS</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司</p>
 * <p>网址：http://www.baizeweb.com
 * @author <a href="mailto:sean_yang@163.com">杨信</a>
 * @version 1.0
 * @since 2009-9-2 上午11:37:48
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class EnumerationForm extends GeneralForm {
	
	private static final long serialVersionUID = -6880762919779862762L;
	
	/** 枚举类型 */
	private Enumeration enumeration;
	
	/**枚举名称**/
	private String enumerationName;
	
	/** 枚举类别id**/
	private String enumerationId;
	
	/** 枚举值List**/
	@SuppressWarnings("unchecked")
	private List list = new ArrayList();
	
	/** 枚举值字符串**/
	private String enumValuesStr;
	
	/**分隔符**/
	private String separator;
	
	/** 枚举值字符串**/
	private String enumValues;
	
	/** 所有枚举名称字符串**/
	private String allEnumNameStr;

	/** 导出枚举型ids**/
	private String exportEnumIds;
	
	/** 导出枚举型路径**/
	private String path;
	
	@Override
	protected void validateData(ActionMapping mapping,
			HttpServletRequest request) {
		// TODO Auto-generated method stub

	}

	public Enumeration getEnumeration() {
		return enumeration;
	}

	public void setEnumeration(Enumeration enumeration) {
		this.enumeration = enumeration;
	}

	public void setEnumerationName(String enumerationName) {
		this.enumerationName = enumerationName;
	}

	public String getEnumerationName() {
		return enumerationName;
	}

	public void setEnumerationId(String enumerationId) {
		this.enumerationId = enumerationId;
	}

	public String getEnumerationId() {
		return enumerationId;
	}

	public void setList(List list) {
		this.list = list;
	}

	public List getList() {
		return list;
	}

	public void setEnumValuesStr(String enumValuesStr) {
		this.enumValuesStr = enumValuesStr;
	}

	public String getEnumValuesStr() {
		return enumValuesStr;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

	public String getSeparator() {
		return separator;
	}

	public void setEnumValues(String enumValues) {
		this.enumValues = enumValues;
	}

	public String getEnumValues() {
		return enumValues;
	}

	public void setAllEnumNameStr(String allEnumNameStr) {
		this.allEnumNameStr = allEnumNameStr;
	}

	public String getAllEnumNameStr() {
		return allEnumNameStr;
	}

	public void setExportEnumIds(String exportEnumIds) {
		this.exportEnumIds = exportEnumIds;
	}

	public String getExportEnumIds() {
		return exportEnumIds;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

}
