/**
 * project：通用内容管理系统
 * Company: 南京瀚沃信息科技有限责任公司
*/
package com.baize.ccms.plugin.letterbox.service;

import java.util.List;
import java.util.Map;

import com.baize.ccms.plugin.letterbox.domain.LetterCategory;
import com.baize.common.core.dao.Pagination;
import com.baize.common.core.service.BaseService;
import com.baize.common.core.web.event.RequestEvent;
import com.baize.common.core.web.event.ResponseEvent;

/**
 * <p>标题: 信件类别业务处理类</p>
 * <p>描述: 信件类别的添加、删除、修改等功能<</p>
 * <p>模块: 信件箱</p>
 * <p>版权: Copyright (c) 2009南京瀚沃信息科技有限责任公司
 * @author 杨信
 * @version 1.0
 * @since 2009-6-13 下午03:40:57 
 * @history（历次修订内容、修订人、修订时间等） 
*/

public class LetterCategoryBiz extends BaseService {
	
	/** 注入信件类别业务对象 */
	private LetterCategoryService letterCategoryService;
	
	/**
	 * 业务处理
	 */
	@SuppressWarnings("unchecked")
	public void performTask(RequestEvent requestEvent, ResponseEvent responseEvent)
			throws Exception {
		String dealMethod = requestEvent.getDealMethod();
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		
		 //分页显示所有信件类别
		if(dealMethod.equals("find")) {
			log.info("分页显示所有信件类别");
			Pagination pagination = (Pagination) requestParam.get("pagination");
			responseParam.put("pagination",letterCategoryService.findLetterCategory(pagination));
		
		
		 //添加信件类别
		} else  if(dealMethod.equals("add")) {
			log.info("添加信件类别");
			String name = (String) requestParam.get("name");
			letterCategoryService.addLetterCategory(name);
			
		 //删除信件类别
		} else  if(dealMethod.equals("delete")) {
			log.info("删除信件类别");
			String ids = (String) requestParam.get("ids");
			String message = letterCategoryService.deleteLetterCategory(ids);
			
			responseParam.put("message", message);
			
			Pagination pagination = (Pagination) requestParam.get("pagination");
			responseParam.put("pagination",letterCategoryService.findLetterCategory(pagination));
			
		 //在信件表单中显示类别
		} else  if(dealMethod.equals("showCatrgory")) {
			log.info("在信件表单中显示类别");
			//orgId是要写信的机构的id
			String orgId = (String) requestParam.get("orgId");
			//查询所有信件类别
			List list = letterCategoryService.findLetterCategoryList();
			//查找机构名称
			String orgName = letterCategoryService.findOrgNameById(orgId);
			
			responseParam.put("orgId", orgId);
			responseParam.put("orgName", orgName);
			responseParam.put("list", list);
			
		 //查找信件所有类别名称
		} else  if(dealMethod.equals("findCatrgoryName")) {
			log.info("查找信件所有类别名称");
			String categoryName = letterCategoryService.findCategoryNameStr();
			responseParam.put("categoryName", categoryName);
			
		 //信件类别信息
		} else  if(dealMethod.equals("categoryDetail")) {
			log.info("信件类别信息");
			String categoryId = (String) requestParam.get("categoryId");
			LetterCategory letterCategory = new LetterCategory();
			letterCategory = letterCategoryService.findCategoryDetail(categoryId);
			//查找所有信件类别名称
			String categoryName = letterCategoryService.findCategoryNameStr();
			responseParam.put("categoryName", categoryName);
			responseParam.put("letterCategory", letterCategory);
			
		 //修改类别信息
		} else  if(dealMethod.equals("modify")) {
			log.info("修改类别信息");	
			String categoryId = (String) requestParam.get("categoryId");
			String name = (String) requestParam.get("name");
			letterCategoryService.modifyCategory(categoryId, name);
		}
		
	}

	/**
	 * @param letterCategoryService the letterCategoryService to set
	 */
	public void setLetterCategoryService(LetterCategoryService letterCategoryService) {
		this.letterCategoryService = letterCategoryService;
	}

	
	@Override
	public ResponseEvent validateData(RequestEvent requestEvent)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
