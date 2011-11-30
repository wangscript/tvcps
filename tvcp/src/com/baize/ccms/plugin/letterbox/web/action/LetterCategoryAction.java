/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.plugin.letterbox.web.action;

import java.util.List;
import java.util.Map;

import org.apache.struts.action.ActionForm;

import com.j2ee.cms.plugin.letterbox.domain.LetterCategory;
import com.j2ee.cms.plugin.letterbox.web.form.LetterCategoryForm;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.web.GeneralAction;
import com.j2ee.cms.common.core.web.event.RequestEvent;
import com.j2ee.cms.common.core.web.event.ResponseEvent;

/**
 * <p>标题: 信件类别Action</p>
 * <p>描述: 管理信件类别的不同操作，封装请求对象</p>
 * <p>模块: 信件箱</p>
 * <p>版权: Copyright (c) 2009 
 * @author 杨信
 * @version 1.0
 * @since 2009-6-13 下午03:58:41 
 * @history（历次修订内容、修订人、修订时间等） 
*/

public class LetterCategoryAction extends GeneralAction {
	
	private String dealMethod = "";
	@SuppressWarnings("unchecked")
	@Override
	protected void doFormFillment(ActionForm actionForm, ResponseEvent responseEvent, String userIndr) {
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		LetterCategoryForm form = (LetterCategoryForm) actionForm;
		
		 //分页显示所有信件类别
		if(dealMethod.equals("find")) {
			log.info("分页显示所有信件类别");
			Pagination pagination  = (Pagination)responseParam.get("pagination");
			form.setPagination(pagination);
			this.setRedirectPage("success", userIndr);
			
		 //添加信件类别
		} else  if(dealMethod.equals("add")) {
			log.info("添加信件类别");
			int flag = 1;
			form.setFlag(flag);
			this.setRedirectPage("addSuccess", userIndr);
		
		 //删除信件类别
		} else  if(dealMethod.equals("delete")) {
			log.info("删除信件类别");
			String message = (String) responseParam.get("message");
			form.setMessage(message);
			Pagination pagination  = (Pagination)responseParam.get("pagination");
			form.setPagination(pagination);
			
			this.setRedirectPage("deleteSuccess", userIndr);
			log.info("删除成功!");
			
		 //在信件表单中显示类别
		} else  if(dealMethod.equals("showCatrgory")) {
			log.info("在信件表单中显示类别");
			List<LetterCategory> list = (List<LetterCategory>) responseParam.get("list");
			//orgId是要写信的机构的id
			String orgId = (String) responseParam.get("orgId");
			String orgName = (String) responseParam.get("orgName");
			
			form.setOrgId(orgId);
			form.setOrgName(orgName);
			form.setList(list);
			this.setRedirectPage("showCatrgorySuccess", userIndr);
			
		 //查找信件所有类别名称
		} else  if(dealMethod.equals("findCatrgoryName")) {
			log.info("查找信件所有类别名称");
			String categoryName = (String) responseParam.get("categoryName");
			form.setCategoryName(categoryName);
			this.setRedirectPage("findCatrgoryNameSuccess", userIndr);
			
		 //信件类别信息
		} else  if(dealMethod.equals("categoryDetail")) {
			log.info("信件类别信息");
			LetterCategory letterCategory = (LetterCategory) responseParam.get("letterCategory");
			String categoryName = (String) responseParam.get("categoryName");
			//所有信件类别名称字符串
			form.setCategoryName(categoryName);
			form.setLetterCategory(letterCategory);
			this.setRedirectPage("findCatrgoryDetailSuccess", userIndr);
			
		 //修改类别信息
		} else  if(dealMethod.equals("modify")) {
			log.info("修改类别信息");
			form.setFlag(1);
			this.setRedirectPage("modifySuccess", userIndr);
		}
	}

	@Override
	protected void performTask(ActionForm actionForm, RequestEvent requestEvent, String userIndr) throws Exception {		
		LetterCategoryForm form = (LetterCategoryForm) actionForm;
		this.dealMethod = form.getDealMethod();
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		 //分页显示所有信件类别
		if(dealMethod.equals("find")) {
			log.info("分页显示所有信件类别");
			form.setQueryKey("findLetterCategoryByPage");
			
		 //添加信件类别
		} else  if(dealMethod.equals("add")) {
			log.info("添加信件类别");
			String name = form.getName();
			requestParam.put("name", name);
			
		 //删除信件类别
		} else  if(dealMethod.equals("delete")) {
			log.info("删除信件类别");
			String ids=form.getIds();
			requestParam.put("ids", ids);
			form.setQueryKey("findLetterCategoryByPage");
			
		 //在信件表单中显示类别
		} else  if(dealMethod.equals("showCatrgory")) {
			log.info("在信件表单中显示类别");
			//orgId是要写信的机构的id
			String orgId=form.getIds();
			requestParam.put("orgId", orgId);
			
		 //查找信件所有类别名称
		} else  if(dealMethod.equals("findCatrgoryName")) {
			log.info("查找信件所有类别名称");
			
		 //信件类别信息
		} else  if(dealMethod.equals("categoryDetail")) {
			log.info("信件类别信息");
			String categoryId = form.getIds();
			requestParam.put("categoryId", categoryId);
			
		 //修改类别信息
		} else  if(dealMethod.equals("modify")) {
			log.info("修改类别信息");
			String name = form.getName();
			String categoryId = form.getCategoryId();
			requestParam.put("name", name);
			requestParam.put("categoryId", categoryId);
		}
		
	}

	
	@Override
	protected void init(String arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}


