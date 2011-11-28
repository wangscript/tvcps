/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.documentmanager.web.action;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.baize.ccms.biz.documentmanager.domain.AttachmentCategory;
import com.baize.ccms.biz.documentmanager.domain.DocumentCategory;
import com.baize.ccms.biz.documentmanager.domain.FlashCategory;
import com.baize.ccms.biz.documentmanager.domain.JsCategory;
import com.baize.ccms.biz.documentmanager.domain.PictureCategory;
import com.baize.ccms.biz.documentmanager.web.form.CategoryForm;
import com.baize.ccms.biz.sitemanager.domain.Site;
import com.baize.ccms.biz.usermanager.domain.User;
import com.baize.common.core.dao.Pagination;
import com.baize.common.core.util.DateUtil;
import com.baize.common.core.web.GeneralAction;
import com.baize.common.core.web.event.RequestEvent;
import com.baize.common.core.web.event.ResponseEvent;

/**
 * <p>标题: 类别的action</p>
 * <p>描述: 类别的action处理，用于平台</p>
 * <p>模块: 文档管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 郑荣华
 * @version 1.0
 * @since 2009-3-26 上午11:16:18
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class CategoryAction extends GeneralAction {
	
	
	private String dealMethod = "";

	@Override
	protected void doFormFillment(ActionForm actionForm, ResponseEvent responseEvent, String userIndr) {
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		CategoryForm form = (CategoryForm) actionForm;
		DocumentCategory category = form.getCategory();
		
		//获得节点
		String nodeid = (String) responseParam.get("nodeid");
		form.setNodeId(nodeid);
		
		//显示类别列表
		if (dealMethod.equals("")) {
			form.setPagination((Pagination) responseParam.get("pagination"));
			String categoryName = (String)responseParam.get("categoryName");
			form.setCategoryName(categoryName);
			this.setRedirectPage("categorySuccess", userIndr);
		
			// 添加类别
		} else 	if (dealMethod.equals("add")) {			
			form.setInfoMessage("增加成功");
			this.setRedirectPage("categoryDetail", userIndr);
		
		// 删除类别	
		} else	if (dealMethod.equals("delete")) {
			form.setInfoMessage((String) responseParam.get("infoMessage"));
			form.setPagination((Pagination) responseParam.get("pagination"));
			this.setRedirectPage("deleteCategorySuccess", userIndr);
		
		//修改类别	
		} else	if (dealMethod.equals("modify")) {					
			form.setInfoMessage("修改成功");
			this.setRedirectPage("categoryDetail", userIndr);
			
		//类别详细信息	
		} else	if (dealMethod.equals("detail")) {					
			category = (DocumentCategory)responseParam.get("category");
			form.setCategory(category);
			String categoryName = (String) responseParam.get("categoryName");
			form.setCategoryName(categoryName);
			
		// 获得树的信息	
		}else if (dealMethod.equals("getTree")){
			form.setJson_list((List)responseParam.get("treelist"));
			this.setRedirectPage("tree", userIndr);
			
		//查询所有类别名称字符串
		}else   if(dealMethod.equals("findCategoryName")) {
			String categoryName = (String) responseParam.get("categoryName");
			form.setCategoryName(categoryName);
			this.setRedirectPage("findCategoryNameSuccess", userIndr);
		}
	}

	@Override
	protected void performTask(ActionForm actionForm, RequestEvent requestEvent, String userIndr) throws Exception {
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		HttpServletRequest request = (HttpServletRequest) requestParam.get("HttpServletRequest");
		CategoryForm form = (CategoryForm)actionForm;
		this.dealMethod = form.getDealMethod();
        DocumentCategory category = form.getCategory(); 
		String nodeid = form.getNodeId();
		log.debug("---------nodeid-----------" + nodeid);
		if(nodeid == null || nodeid.equals("0") || nodeid.equals("") || nodeid.equals("null")) {
			nodeid = null;
		}
		// 显示分类列表
		if(dealMethod.equals("")) {
			proccessPagination(nodeid, form, requestParam);
		
		// 添加列表
		}else	if(dealMethod.equals("add")) {
			categoryInfoAdd(nodeid,category, requestParam);
			this.setRedirectPage("categoryDetail", userIndr);
			
		//修改类别	
		}else	if(dealMethod.equals("modify")) {
			categoryUpdate(form, requestParam);
			
		//删除类别	
		}else	if(dealMethod.equals("delete")) {
			String ids = form.getIds();
			requestParam.put("ids", ids);
			
			proccessPagination(nodeid, form, requestParam);
		
		//类别的详细信息
		}else   if(dealMethod.equals("detail")) {
			requestParam.put("id", form.getIds());
			
		//获得树	
		}else   if(dealMethod.equals("getTree")) {
			String treeid1 = form.getTreeId();
			requestParam.put("treeid", treeid1);
			
		//查询所有类别名称字符串
		}else   if(dealMethod.equals("findCategoryName")) {
	//		System.out.println("d");
	    }
		requestParam.put("nodeid", nodeid);
	}
	
    /**
     * 
     * @param nodeid 
     * @param form
     */
	public void proccessPagination(String nodeid, CategoryForm form, Map<Object,Object> requestParam) {
		// 获取图片类别
    	if(nodeid.equals("f004")){	
			form.setQueryKey("findCategoryPictureBySiteIdPage");
			
		// 获取flash类别
		}else  if(nodeid.equals("f005")){
			form.setQueryKey("findCategoryFlashBySiteIdPage");
		
		// 获取附件类别
		} else if(nodeid.equals("f006")){
			form.setQueryKey("findCategoryAttachmentBySiteIdPage");
			
		// 获取js类别
		} else if(nodeid.equals("f007")){
			form.setQueryKey("findCategoryJsBySiteIdPage");
		}
    	requestParam.put("pagination", form.getPagination());
    }
	
    /**
     * 添加类别信息
     * @param nodeid
     * @param category
     * @throws ParseException
     */
	public void categoryInfoAdd(String nodeid, DocumentCategory category, Map<Object,Object> requestParam) throws ParseException {
		Date currentTime = new Date();
		//添加图片类别
		if(nodeid.equals("f004")) {
			PictureCategory pictureCategory = new PictureCategory();
			pictureCategory.setId(category.getId());
			pictureCategory.setName(category.getName());
			pictureCategory.setDescription(category.getDescription());
			pictureCategory.setCreateTime(currentTime);
			requestParam.put("pictureCategory", pictureCategory);
			
		// 添加flash类别
		} else if(nodeid.equals("f005")) {
			FlashCategory flashCategory = new FlashCategory();
			flashCategory.setId(category.getId());
			flashCategory.setName(category.getName());
			flashCategory.setDescription(category.getDescription());
			flashCategory.setCreateTime(currentTime);
			requestParam.put("flashCategory", flashCategory);
		
		// 添加附件类别 
		} else if(nodeid.equals("f006")) {
			AttachmentCategory attachmentCategory = new AttachmentCategory();
			attachmentCategory.setId(category.getId());
			attachmentCategory.setName(category.getName());
			attachmentCategory.setDescription(category.getDescription());
			attachmentCategory.setCreateTime(currentTime);
			requestParam.put("attachmentCategory", attachmentCategory);
			
		// 添加js脚本类别 
		} else if(nodeid.equals("f007")) {
			JsCategory jsCategory = new JsCategory();
			jsCategory.setId(category.getId());
			jsCategory.setName(category.getName());
			jsCategory.setDescription(category.getDescription());
			jsCategory.setCreateTime(currentTime);
			requestParam.put("jsCategory", jsCategory);
		}
	}
	
	/**
	 * 修改类别
	 * @param form 类别表单
	 */
	private void categoryUpdate(CategoryForm form, Map<Object,Object> requestParam) {
		DocumentCategory category = form.getCategory();
		//添加网站的信息
		String siteid = form.getSiteid();
		Site site = new Site();
		site.setId(siteid);
		category.setSite(site);
		//添加创建者的信息
		String userid = form.getUserid();
		User creator = new User();
		creator.setId(userid);
		category.setCreator(creator);
		//获得创建的原来时间
		category.setCreateTime(DateUtil.toDate(form.getCreateTime()));
		requestParam.put("category", category);
	}
	
	@Override
	protected void init(String userIndr) throws Exception {
		this.setRedirectPage("categoryDetail", userIndr);
	}


}
