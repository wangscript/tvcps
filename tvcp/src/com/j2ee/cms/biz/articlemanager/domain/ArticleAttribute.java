/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.articlemanager.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>标题: 属性</p>
 * <p>描述: 表示某个格式所需要的属性，一个格式对应多个属性</p>
 * <p>模块: 文章管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 杨信
 * @version 1.0
 * @since 2009-3-10 下午01:44:55
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class ArticleAttribute implements Serializable {
	
	private static final long serialVersionUID = 3381061500138390719L;
	
	/** 整数类型 */
	public static final String ATTR_TYPE_INTEGER = "integer";
	
	/** 布尔类型 */
	public static final String ATTR_TYPE_BOOL = "bool";
	
	/** 小数类型 */
	public static final String ATTR_TYPE_FLOAT = "float";
	
	/** 日期类型 */
	public static final String ATTR_TYPE_DATE = "date";
	
	/** 字符类型 */
	public static final String ATTR_TYPE_TEXT = "text";
	
	/** 文本类型 */
	public static final String ATTR_TYPE_TEXTAREA = "textArea";
	
	/** 图片类型 */
	public static final String ATTR_TYPE_PIC = "pic";
	
	/** 附件类型 */
	public static final String ATTR_TYPE_ATTACH = "attach";
	
	/** 媒体类型 */
	public static final String ATTR_TYPE_MEDIA = "media";
	
	/** 枚举类型 */
	public static final String ATTR_TYPE_ENUMERATION = "enumeration";

	/** 唯一标识符 */
	private String id;

	/** 文章的格式 */
	private ArticleFormat articleFormat;
	
	/** 属性名称 */
	private String attributeName;
	
	/** 字段名称 */
	private String fieldName;
	
	/** 属性类型 */
	private String attributeType;
	
	/** 是否在页面显示 */
	private boolean showed = true;
	
	/** 可否修改 */
	private boolean modified = true;
	
	/** 提示信息 */
	private String tip;
	
	/** 是否可以为空 */
	private boolean empty = true;
	
	/** 有效值 (num,string,num|num ...)*/
	private String validValue;
	
	/** 文章属性创建时间 */
	private Date createTime = new Date();
	
	/** 枚举类别 */
	private Enumeration enumeration;
	
	/** 排序序号**/
	private int orders;
	
	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * @param fieldName the fieldName to set
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * @return the showed
	 */
	public boolean isShowed() {
		return showed;
	}

	/**
	 * @param showed the showed to set
	 */
	public void setShowed(boolean showed) {
		this.showed = showed;
	}

	/**
	 * @return the attributeType
	 */
	public String getAttributeType() {
		return attributeType;
	}

	/**
	 * @param attributeType the attributeType to set
	 */
	public void setAttributeType(String attributeType) {
		this.attributeType = attributeType;
	}

	/**
	 * @return the articleFormat
	 */
	public ArticleFormat getArticleFormat() {
		return articleFormat;
	}

	/**
	 * @param articleFormat the articleFormat to set
	 */
	public void setArticleFormat(ArticleFormat articleFormat) {
		this.articleFormat = articleFormat;
	}

	/**
	 * @return the modified
	 */
	public boolean isModified() {
		return modified;
	}

	/**
	 * @param modified the modified to set
	 */
	public void setModified(boolean modified) {
		this.modified = modified;
	}

	/**
	 * @return the empty
	 */
	public boolean isEmpty() {
		return empty;
	}

	/**
	 * @param empty the empty to set
	 */
	public void setEmpty(boolean empty) {
		this.empty = empty;
	}

	/**
	 * @return the validValue
	 */
	public String getValidValue() {
		return validValue;
	}

	/**
	 * @param validValue the validValue to set
	 */
	public void setValidValue(String validValue) {
		this.validValue = validValue;
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

	/**
	 * @return the tip
	 */
	public String getTip() {
		return tip;
	}

	/**
	 * @param tip the tip to set
	 */
	public void setTip(String tip) {
		this.tip = tip;
	}

	/**
	 * @return the enumeration
	 */
	public Enumeration getEnumeration() {
		return enumeration;
	}

	/**
	 * @param enumeration the enumeration to set
	 */
	public void setEnumeration(Enumeration enumeration) {
		this.enumeration = enumeration;
	}

	public void setOrders(int orders) {
		this.orders = orders;
	}

	public int getOrders() {
		return orders;
	}

}
