/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.articlemanager.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.j2ee.cms.biz.articlemanager.domain.ArticleAttribute;
import com.j2ee.cms.common.core.web.GeneralForm;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 文章管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-4-7 下午02:07:35
 * @history（历次修订内容、修订人、修订时间等）
 */
public class ArticleAttributeForm extends GeneralForm {

	private static final long serialVersionUID = -6198501492759844919L;
	
	/** 属性对象 */
	private ArticleAttribute attribute = new ArticleAttribute();
	
	/** 格式ID */
	private String formatId;
	
	/** 格式拥有的字段 */
	private String formatFields;
	
	/**格式名称**/
	private String formatName;
	
	/** 属性ID */
	private String attributeId;
	
	/** ajax提交返回消息 */
	private String ajaxMsg;
	
	/** 可否显示**/
	private boolean showed;
	
	/** 可否为空**/
	private boolean empty;
	
	/** 可否修改**/
	private boolean modified;

	/** 枚举信息字符串**/
	private String enumInfoStr;
	
	/** 枚举类别名**/
	private String enumerationName;
	
	/** 枚举类别Id**/
	private String enumerationId;
	
	/** 属性名称字符串**/
	private String attributeNameStr;
	
	/** 属性信息字符串**/
	private String attributeInfoStr;
	
	/** 属性idStr**/
	private String attributeIdStr;
	
	/** 默认属性ids**/
	private String defaultAttrIds;
	
	/** 是否是默认格式的属性**/
	private String fromDefault;
	
	@Override
	protected void validateData(ActionMapping mapping,
			HttpServletRequest request) {
		
	}

	/**
	 * @return the attribute
	 */
	public ArticleAttribute getAttribute() {
		return attribute;
	}

	/**
	 * @param attribute the attribute to set
	 */
	public void setAttribute(ArticleAttribute attribute) {
		this.attribute = attribute;
	}

	/**
	 * @return the formatFields
	 */
	public String getFormatFields() {
		return formatFields;
	}

	/**
	 * @param formatFields the formatFields to set
	 */
	public void setFormatFields(String formatFields) {
		this.formatFields = formatFields;
	}

	/**
	 * @return the formatId
	 */
	public String getFormatId() {
		return formatId;
	}

	/**
	 * @param formatId the formatId to set
	 */
	public void setFormatId(String formatId) {
		this.formatId = formatId;
	}

	/**
	 * @return the attributeId
	 */
	public String getAttributeId() {
		return attributeId;
	}

	/**
	 * @param attributeId the attributeId to set
	 */
	public void setAttributeId(String attributeId) {
		this.attributeId = attributeId;
	}

	/**
	 * @return the ajaxMsg
	 */
	public String getAjaxMsg() {
		return ajaxMsg;
	}

	/**
	 * @param ajaxMsg the ajaxMsg to set
	 */
	public void setAjaxMsg(String ajaxMsg) {
		this.ajaxMsg = ajaxMsg;
	}

	/**
	 * @return the formatName
	 */
	public String getFormatName() {
		return formatName;
	}

	/**
	 * @param formatName the formatName to set
	 */
	public void setFormatName(String formatName) {
		this.formatName = formatName;
	}

	public void setEnumInfoStr(String enumInfoStr) {
		this.enumInfoStr = enumInfoStr;
	}

	public String getEnumInfoStr() {
		return enumInfoStr;
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

	public void setAttributeNameStr(String attributeNameStr) {
		this.attributeNameStr = attributeNameStr;
	}

	public String getAttributeNameStr() {
		return attributeNameStr;
	}

	public void setAttributeInfoStr(String attributeInfoStr) {
		this.attributeInfoStr = attributeInfoStr;
	}

	public String getAttributeInfoStr() {
		return attributeInfoStr;
	}

	public void setAttributeIdStr(String attributeIdStr) {
		this.attributeIdStr = attributeIdStr;
	}

	public String getAttributeIdStr() {
		return attributeIdStr;
	}

	public void setDefaultAttrIds(String defaultAttrIds) {
		this.defaultAttrIds = defaultAttrIds;
	}

	public String getDefaultAttrIds() {
		return defaultAttrIds;
	}

	public void setFromDefault(String fromDefault) {
		this.fromDefault = fromDefault;
	}

	public String getFromDefault() {
		return fromDefault;
	}

}
