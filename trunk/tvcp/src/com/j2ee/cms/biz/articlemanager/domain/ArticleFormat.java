/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.articlemanager.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.j2ee.cms.biz.templatemanager.domain.TemplateInstance;
import com.j2ee.cms.biz.usermanager.domain.User;

/**
 * <p>标题: 格式</p>
 * <p>描述: 表示某篇文章的格式，一篇文章只能属于一种格式</p>
 * <p>模块: 文章管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 杨信
 * @version 1.0
 * @since 2009-3-10 下午01:40:37
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class ArticleFormat implements Serializable {

	private static final long serialVersionUID = 4988695017588865306L;
	
	public static List<ArticleAttribute> ATTRIBUTES_DEFAULT = new ArrayList<ArticleAttribute>();
	
	/**
	 * 初始化格式的默认属性
	 */
	static {
		addAttribute("标题", "title", "text", true, true, false, "string", "标题不能为空");
		//addAttribute("副标题", "subtitle", "text", true, true, true, null, null);
		//addAttribute("引题", "leadingTitle", "text", true, true, true, null, null);
		addAttribute("链接地址", "url", "text", true, true, true, null, null);
		//addAttribute("信息来源", "infoSource", "text", true, true, true, null, null);
		//addAttribute("作者", "author", "text", true, true, true, null, null);
		//addAttribute("内容", "textArea1", "textArea", true, true, true, null, null);
		//addAttribute("摘要", "brief", "text", true, true, true, null, null);
		//addAttribute("关键字", "keyword", "text", true, true, true, null, null);
		//addAttribute("图片", "pic1", "text", true, true, true, null, null);
		//addAttribute("媒体", "media1", "text", true, true, true, null, null);
		//addAttribute("附件", "attach1", "text", true, true, true, null, null);
		addAttribute("是否删除", "deleted", "bool", false, false, false, null, null);
		addAttribute("是否审核", "audited", "bool", false, true, false, null, null);
		addAttribute("是否发布", "publishState", "bool", false, true, false, null, null);
		addAttribute("创建时间", "createTime", "date", true, false, false, null, null);
		addAttribute("显示时间", "displayTime", "date", true, true, true, null, null);
		addAttribute("发布时间", "publishTime", "date", true, false, true, null, null);
		addAttribute("审核时间", "auditTime", "date", true, false, true, null, null);
		addAttribute("失效时间", "invalidTime", "date", true, true, true, null, null);
		addAttribute("置顶", "toped", "bool", true, true, false, null, null);
		addAttribute("过滤关键词", "keyFilter", "bool", true, true, false, null, null);
	}
	
	public static String FIELD_NAMES_DEFAULT = "id,title,deleted,audited,published,createTime,displayTime,publishTime,auditTime,invalidTime,column_id,site_id,format_id,creator_id,auditor_id";

	/** 唯一标识符 */
	private String id;
	
	/** 格式名称 */
	private String name;
	
	/** 描述 */
	private String description;
	
	/** 数据库Article表中对应的字段名，中间用","隔开 */
	private String fields = FIELD_NAMES_DEFAULT;
	
	/** 当前日期号 */
	private int currDate;
	
	/** 当前整数号 */
	private int currInteger;
	
	/** 当前字符号 */
	private int currText;
	
	/** 当前文本号 */
	private int currTextArea;
	
	/** 当前小数号 */
	private int currFloat;
	
	/** 当前布尔号 */
	private int currBool;
	
	/** 当前图片号 */
	private int currPic;
	
	/** 当前附件号 */
	private int currAttach;
	
	/** 但前媒体号 */
	private int currMedia;
	
	/** 当前枚举号 */
	private int currEnumeration;
	
	/** 创建时间 */
	private Date createTime = new Date();
	
	/** 对应的模板实例 */
	private TemplateInstance template;
	
	/** 创建者 */
	private User creator;
	
	/** 是否默认 */
	private boolean defaults;
	
	/** 格式缓存 */
	private static List<ArticleFormat> articleFormats;
	/** 缓存更新标志 */
	private static boolean needUpdated = true;
	
	/**
	 * 添加属性
	 * @param attributeName 属性名
	 * @param fieldName 字段名
	 * @param attributeType 属性类型
	 * @param isShowed 是否显示
	 */
	private static void addAttribute(String attributeName, String fieldName, String attributeType, boolean showed, boolean modified, boolean empty, String validValue, String tip) {
		ArticleAttribute attribute = new ArticleAttribute();
		attribute.setAttributeName(attributeName);
		attribute.setFieldName(fieldName);
		attribute.setAttributeType(attributeType);
		attribute.setShowed(showed);
		attribute.setModified(modified);
		attribute.setEmpty(empty);
		attribute.setValidValue(validValue);
		attribute.setTip(tip);
		ATTRIBUTES_DEFAULT.add(attribute);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the fields
	 */
	public String getFields() {
		return fields;
	}

	/**
	 * @param fields the fields to set
	 */
	public void setFields(String fields) {
		this.fields = fields;
	}

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

	/**
	 * @return the creator
	 */
	public User getCreator() {
		return creator;
	}

	/**
	 * @param creator the creator to set
	 */
	public void setCreator(User creator) {
		this.creator = creator;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the currDate
	 */
	public int getCurrDate() {
		return currDate;
	}

	/**
	 * @param currDate the currDate to set
	 */
	public void setCurrDate(int currDate) {
		this.currDate = currDate;
	}

	/**
	 * @return the currInteger
	 */
	public int getCurrInteger() {
		return currInteger;
	}

	/**
	 * @param currInteger the currInteger to set
	 */
	public void setCurrInteger(int currInteger) {
		this.currInteger = currInteger;
	}

	/**
	 * @return the currText
	 */
	public int getCurrText() {
		return currText;
	}

	/**
	 * @param currText the currText to set
	 */
	public void setCurrText(int currText) {
		this.currText = currText;
	}

	/**
	 * @return the currTextArea
	 */
	public int getCurrTextArea() {
		return currTextArea;
	}

	/**
	 * @param currTextArea the currTextArea to set
	 */
	public void setCurrTextArea(int currTextArea) {
		this.currTextArea = currTextArea;
	}

	/**
	 * @return the currFloat
	 */
	public int getCurrFloat() {
		return currFloat;
	}

	/**
	 * @param currFloat the currFloat to set
	 */
	public void setCurrFloat(int currFloat) {
		this.currFloat = currFloat;
	}

	/**
	 * @return the currBool
	 */
	public int getCurrBool() {
		return currBool;
	}

	/**
	 * @param currBool the currBool to set
	 */
	public void setCurrBool(int currBool) {
		this.currBool = currBool;
	}

	/**
	 * @param template the template to set
	 */
	public void setTemplate(TemplateInstance template) {
		this.template = template;
	}

	/**
	 * @return the template
	 */
	public TemplateInstance getTemplate() {
		return template;
	}

	/**
	 * @return the articleFormats
	 */
	public static List<ArticleFormat> getArticleFormats() {
		return articleFormats;
	}

	/**
	 * @param articleFormats the articleFormats to set
	 */
	public static void setArticleFormats(List<ArticleFormat> articleFormats) {
		ArticleFormat.articleFormats = articleFormats;
	}

	/**
	 * @return the needUpdated
	 */
	public static boolean isNeedUpdated() {
		return needUpdated;
	}

	/**
	 * @param needUpdated the needUpdated to set
	 */
	public static void setNeedUpdated(boolean needUpdated) {
		ArticleFormat.needUpdated = needUpdated;
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
	 * @return the currPic
	 */
	public int getCurrPic() {
		return currPic;
	}

	/**
	 * @param currPic the currPic to set
	 */
	public void setCurrPic(int currPic) {
		this.currPic = currPic;
	}

	/**
	 * @return the currAttach
	 */
	public int getCurrAttach() {
		return currAttach;
	}

	/**
	 * @param currAttach the currAttach to set
	 */
	public void setCurrAttach(int currAttach) {
		this.currAttach = currAttach;
	}

	/**
	 * @return the currMedia
	 */
	public int getCurrMedia() {
		return currMedia;
	}

	/**
	 * @param currMedia the currMedia to set
	 */
	public void setCurrMedia(int currMedia) {
		this.currMedia = currMedia;
	}

	/**
	 * @return the defaults
	 */
	public boolean isDefaults() {
		return defaults;
	}

	/**
	 * @param defaults the defaults to set
	 */
	public void setDefaults(boolean defaults) {
		this.defaults = defaults;
	}

	public void setCurrEnumeration(int currEnumeration) {
		this.currEnumeration = currEnumeration;
	}

	public int getCurrEnumeration() {
		return currEnumeration;
	}

}
