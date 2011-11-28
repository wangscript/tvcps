/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.documentmanager.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.baize.ccms.biz.documentmanager.domain.AttachmentCategory;
import com.baize.ccms.biz.documentmanager.domain.DocumentCategory;
import com.baize.ccms.biz.documentmanager.domain.FlashCategory;
import com.baize.ccms.biz.documentmanager.domain.JsCategory;
import com.baize.ccms.biz.documentmanager.domain.PictureCategory;
import com.baize.ccms.biz.sitemanager.domain.Site;
import com.baize.ccms.biz.usermanager.domain.User;
import com.baize.common.core.web.GeneralForm;

/**
 * <p>标题: 类别的form</p>
 * <p>描述: 类别的表单数据，以便页面和方法中调用</p>
 * <p>模块: 文档管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 郑荣华
 * @version 1.0
 * @since 2009-3-26 上午11:10:06
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class CategoryForm extends GeneralForm {

	private static final long serialVersionUID = -5662807453631912239L;
	
	/** 类别的id **/
	private String id;
	/** 类别的名字 **/
	private String name;
	/** 类别的描述 **/
	private String description;
	/** 类别的创建时间 **/
	private String createTime;
	/** 创建一个网站对象 **/
	private Site site = new Site();
	/** 创建一个用户对象 **/
	private User user = new User();
	/** 创建一个类别对象 **/
	private DocumentCategory category = new DocumentCategory();
	/** 用户的id **/
	private String userid;
	/** 网站的id **/
	private String siteid;
	/** 创建一个图片类别的对象 **/
	private PictureCategory pictureCategory = new PictureCategory();
	/** 创建一个flash类别的对象 **/
	private FlashCategory flashCategory = new FlashCategory();
	/** 创建一个附件类别的对象 **/
	private AttachmentCategory attachmentCategory = new AttachmentCategory();
	/** 创建一个js脚本类别的对象 **/
	private JsCategory jsCategory = new JsCategory();
	/** 节点 **/
	private String nodeid;
	
	/** 文件类别名称字符串**/
	private String categoryName;
	
	
	/**
	 * @return the pictureCategory
	 */
	public PictureCategory getPictureCategory() {
		return pictureCategory;
	}

	/**
	 * @param pictureCategory the pictureCategory to set
	 */
	public void setPictureCategory(PictureCategory pictureCategory) {
		this.pictureCategory = pictureCategory;
	}

	/**
	 * @return the flashCategory
	 */
	public FlashCategory getFlashCategory() {
		return flashCategory;
	}

	/**
	 * @param flashCategory the flashCategory to set
	 */
	public void setFlashCategory(FlashCategory flashCategory) {
		this.flashCategory = flashCategory;
	}

	/**
	 * @return the attachmentCategory
	 */
	public AttachmentCategory getAttachmentCategory() {
		return attachmentCategory;
	}

	/**
	 * @param attachmentCategory the attachmentCategory to set
	 */
	public void setAttachmentCategory(AttachmentCategory attachmentCategory) {
		this.attachmentCategory = attachmentCategory;
	}

	/**
	 * @return the category
	 */
	public DocumentCategory getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(DocumentCategory category) {
		this.category = category;
	}

	/**
	 * @return the site
	 */
	public Site getSite() {
		return site;
	}

	/**
	 * @param site the site to set
	 */
	public void setSite(Site site) {
		this.site = site;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the createTime
	 */
	public String getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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

	@Override
	protected void validateData(ActionMapping arg0, HttpServletRequest arg1) {

	}

	/**
	 * @return the userid
	 */
	public String getUserid() {
		return userid;
	}

	/**
	 * @param userid the userid to set
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}

	/**
	 * @return the siteid
	 */
	public String getSiteid() {
		return siteid;
	}

	/**
	 * @param siteid the siteid to set
	 */
	public void setSiteid(String siteid) {
		this.siteid = siteid;
	}

	/**
	 * @return the nodeid
	 */
	public String getNodeid() {
		return nodeid;
	}

	/**
	 * @param nodeid the nodeid to set
	 */
	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}

	/**
	 * @param categoryName the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}

	public void setJsCategory(JsCategory jsCategory) {
		this.jsCategory = jsCategory;
	}

	public JsCategory getJsCategory() {
		return jsCategory;
	}
	
}
