/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.documentmanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.baize.ccms.biz.documentmanager.domain.AttachmentCategory;
import com.baize.ccms.biz.documentmanager.domain.DocumentCategory;
import com.baize.ccms.biz.documentmanager.domain.FlashCategory;
import com.baize.ccms.biz.documentmanager.domain.JsCategory;
import com.baize.ccms.biz.documentmanager.domain.PictureCategory;
import com.baize.ccms.biz.sitemanager.domain.Site;
import com.baize.ccms.biz.usermanager.domain.User;
import com.baize.common.core.dao.Pagination;
import com.baize.common.core.service.BaseService;
import com.baize.common.core.web.event.RequestEvent;
import com.baize.common.core.web.event.ResponseEvent;

/**
 * <p>标题: 类别的biz</p>
 * <p>描述: 类别的biz层，用于响应类别的action</p>
 * <p>模块: 文档管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 郑荣华
 * @version 1.0
 * @since 2009-3-26 上午11:16:46
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class CategoryBiz extends BaseService {
   
	/**注入类别业务对象**/
	private CategoryService categoryService;
	
	@Override
	public void performTask(RequestEvent requestEvent, ResponseEvent responseEvent)
			throws Exception {
		String dealMethod = requestEvent.getDealMethod();
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		// 获取sessionID
		String sessionID = requestEvent.getSessionID();
		// 获取频道类型
		String channelType = requestEvent.getChannelType();
		// 网站id
		String siteId = requestEvent.getSiteid();
		
		DocumentCategory category = new DocumentCategory();
		
		String nodeid = (String) requestParam.get("nodeid");
		
		//类别的分页
		if (dealMethod.equals("")) {
			log.info("类别分页");
			Pagination pagination = (Pagination) requestParam.get("pagination");
			pagination = categoryService.findCategoryByCreatorId(pagination, "siteId", siteId);
			responseParam.put("pagination",pagination);
			log.info("类别分页成功");
			
			String categoryName = "";
			if(nodeid.equals("f004")) {
				categoryName = "→ 图片类别";
			} else if(nodeid.equals("f005")) { 
				categoryName = "→ Flash类别";
			} else if(nodeid.equals("f006")) {
				categoryName = "→ 附件类别";
			} else if(nodeid.equals("f007")) {
				categoryName = "→ js脚本类别";
			}
			responseParam.put("categoryName", categoryName);
			
		//添加类别（图片、flash、附件）
		}else   if (dealMethod.equals("add")) {
			//调用类别处理方法
			categoryAdd(nodeid, sessionID, siteId, requestParam);
			
		//删除类别
		}else	if (dealMethod.equals("delete")) {
			log.info("删除类别");
			String ids = (String) requestParam.get("ids");	
			String infoMessage ="";
			String categoryName = "";
			if(nodeid.equals("f004")) {
				categoryName = "文档管理（图片类别）->删除";
			} else if(nodeid.equals("f005")) { 
				categoryName = "文档管理（flash类别）->删除";
			} else if(nodeid.equals("f006")) {
				categoryName = "文档管理（附件类别）->删除";
			} else if(nodeid.equals("f007")) {
				categoryName = "文档管理（js类别）->删除";
			}
			infoMessage = categoryService.deleteCategoryByIds(ids, siteId, sessionID, categoryName);
			responseParam.put("infoMessage", infoMessage);
			log.info("处理删除类别结束");
			
			log.info("类别分页");
			Pagination pagination = (Pagination) requestParam.get("pagination");
			pagination = categoryService.findCategoryByCreatorId(pagination, "siteId", siteId);
			responseParam.put("pagination",pagination);
			log.info("类别分页成功");
			
		//修改类别	
		}else	if (dealMethod.equals("modify")) {
			log.info("修改类别");
			String categoryName = "";
			if(nodeid.equals("f004")) {
				categoryName = "文档管理（图片类别）->修改";
			} else if(nodeid.equals("f005")) { 
				categoryName = "文档管理（flash类别）->修改";
			} else if(nodeid.equals("f006")) {
				categoryName = "文档管理（附件类别）->修改";
			} else if(nodeid.equals("f007")) {
				categoryName = "文档管理（js类别）->修改";
			}
			category = (DocumentCategory) requestParam.get("category");
			categoryService.modifyCategory(category, siteId, sessionID, categoryName);
			log.info("修改类别信息成功");
		
		//类别详细	
		}else   if(dealMethod.equals("detail")) {
			log.info("查询类别详细信息");
		    String id = (String) requestParam.get("id");
		    category = categoryService.findCategoryById(id);
		    responseParam.put("category", category);
		    //查询所有类别名称字符串
		    String categoryName = categoryService.findCategoryName(nodeid, siteId);
			responseParam.put("categoryName", categoryName);
		    log.info("查询类别详细信息成功");

		//加载树    
		}else   if(dealMethod.equals("getTree")) {
		    log.info("加载树的信息");
		    //获得树的treeid
			String treeid = (String) requestParam.get("treeid");
			List<Object> list = new ArrayList<Object>();
			list = categoryService.getTreeList(treeid, categoryService, siteId,sessionID,this.isUpSiteAdmin);
			responseParam.put("treelist", list);
			log.info("加载树的信息成功");
			
		//查询所有类别名称字符串
		}else   if(dealMethod.equals("findCategoryName")) {
			String categoryName = categoryService.findCategoryName(nodeid, siteId);
			responseParam.put("categoryName", categoryName);
		}
		responseParam.put("nodeid", nodeid);
	}
	
	/**
	 * 添加类别
	 * @param nodeid 树的节点
	 * @param sessionID 当前用户的id
	 * @param siteId 网站的id
	 */
	private  void categoryAdd(String nodeid, String sessionID, String siteId, Map<Object,Object> requestParam) {
		//设置文档属于的网站
        Site site = new Site();
		site.setId(siteId);
		//设置文档的创建者
		User creator = new User();
		creator.setId(sessionID);
		
		PictureCategory pictureCategory = new PictureCategory();
		FlashCategory flashCategory = new FlashCategory();
		AttachmentCategory attachmentCategory = new AttachmentCategory();
		JsCategory jsCategory = new JsCategory();
		
		String categoryName = "";
		//如果节点id为-1则添加图片类别
		if(nodeid.equals("f004")) {
        	log.info("添加图片类别");
			pictureCategory = (PictureCategory) requestParam.get("pictureCategory");
        	pictureCategory.setSite(site);
        	pictureCategory.setCreator(creator);
        	categoryName = "文档管理（图片类别）->添加";
        	categoryService.addCategory(pictureCategory, siteId, sessionID, categoryName);
        	log.info("添加图片类别信息成功");
        
        //如果节点id为-2则添加flash类别
		}else if(nodeid.equals("f005")) {
			log.info("添加flash类别");
        	flashCategory = (FlashCategory) requestParam.get("flashCategory");
        	flashCategory.setSite(site);
        	flashCategory.setCreator(creator);
        	categoryName = "文档管理（flash类别）->添加";
        	categoryService.addCategory(flashCategory, siteId, sessionID, categoryName);
        	log.info("添加flash类别信息成功");
        	
        //如果节点id为-3则添加附件类别 	
        }else if(nodeid.equals("f006")) {
        	log.info("添加附件类别");
        	attachmentCategory = (AttachmentCategory) requestParam.get("attachmentCategory");
        	attachmentCategory.setSite(site);
        	attachmentCategory.setCreator(creator);
        	categoryName = "文档管理（附件类别）->添加";
        	categoryService.addCategory(attachmentCategory, siteId, sessionID, categoryName);
        	log.info("添加附件类别信息成功");
        	
        //如果节点id为-4则添加js脚本类别 	
        }else if(nodeid.equals("f007")) {
        	log.info("添加js脚本类别");
        	jsCategory = (JsCategory) requestParam.get("jsCategory");
        	jsCategory.setSite(site);
        	jsCategory.setCreator(creator);
        	categoryName = "文档管理（js类别）->添加";
        	categoryService.addCategory(jsCategory, siteId, sessionID, categoryName);
        	log.info("添加js脚本类别信息成功");
        }
	}
	
	@Override
	public ResponseEvent validateData(RequestEvent requestEvent) throws Exception {
		return null;
	}
	
	/**
	 * @param categoryService 
	 */
	public void setCategoryService(CategoryService categoryService){
		this.categoryService = categoryService;
	}
	
	
       
}
