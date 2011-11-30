  /**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.usermanager.service;

import java.util.List;
import java.util.Map;

import com.j2ee.cms.biz.usermanager.domain.Organization;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.service.BaseService;
import com.j2ee.cms.common.core.web.event.RequestEvent;
import com.j2ee.cms.common.core.web.event.ResponseEvent;

/**
 * <p>标题: —— 机构详细业务逻辑处理方法</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 用户管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-2-16 下午03:05:55
 * @history（历次修订内容、修订人、修订时间等） 
 */
public final  class OrganizationBiz extends BaseService{

	private OrganizationService organizationService;
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
		Organization organization = new Organization();		

		if (dealMethod.equals("")|| dealMethod.equals("return")) {
			log.debug("开始查询机构分页数据!");
			Pagination pagination = (Pagination) requestParam.get("pagination");
			String nodeid = (String) requestParam.get("nodeid");
			pagination = organizationService.findOrganizationDataByNodeId(pagination,nodeid,isUpSystemAdmin,isUpSiteAdmin);
			if(pagination == null || pagination.getData() == null || pagination.getData().size() == 0){
				pagination.currPage = pagination.currPage - 1;
				pagination = organizationService.findOrganizationDataByNodeId(pagination,nodeid,isUpSystemAdmin,isUpSiteAdmin);
			}
			responseParam.put("pagination",pagination);
			log.info("查询机构分页成功.");
	//		String strNodeName= organizationService.getOrganizationName(nodeid);
			responseParam.put("nodeid", nodeid);
			
	//		this.userErrorCode = "000002";
	//		responseParam.put("strNodeName", strNodeName);
		}else if (dealMethod.equals("insert")) {
			// 获取参数
			organization = (Organization) requestParam.get("organization");	
			String pid = (String)requestParam.get("pid");	
			// 保存页面数据
			boolean temp = organizationService.addOrganization(organization,pid, siteId, sessionID);
			responseParam.put("temp",temp);
		}else if (dealMethod.equals("delete")) {
			// 获取参数
			String ids = (String) requestParam.get("ids");		
			boolean usertemp = organizationService.findUserByOrganizationId(ids);
			boolean temp  = false;
			if(usertemp == false){
				  temp  = organizationService.modifyAllObject(ids, siteId, sessionID);	
			}			
		
			responseParam.put("temp",temp);	
			responseParam.put("usertemp",usertemp);	
			
		}else if (dealMethod.equals("detail")) {
			String id = (String) requestParam.get("id");
			log.info("获取机构表主键id==========="+id);
			Organization org = organizationService.findOrganizationByKey(id);
			String str = "";
			if(org.getParent() != null ){
				String pid = org.getParent().getId();
				//获取同级机构下面的名称	 		
	 			str = organizationService.findChildOrganizationNameById(pid);
	 			
			}
			responseParam.put("orgNames",str);	
		//	int nodeid = Integer.parseInt(String.valueOf(requestParam.get("nodeid")));
			StringBuffer sb = new StringBuffer();
			organizationService.findOrganizationChildById(sb, id);
			String strIds = sb.toString();
			if(strIds != null && strIds.startsWith(",")){
				strIds.replaceFirst(",", "");
			}
			responseParam.put("strIds",strIds);	
			responseParam.put("organization",org);	
			
		}else if (dealMethod.equals("modify")) {
			log.debug("修改数据!");
			organization = (Organization) requestParam.get("organization");			
			String pid = (String)requestParam.get("pid");	
			String oldpid = (String)requestParam.get("oldpid");
			boolean temp = organizationService.modifyObject(organization,pid,oldpid, siteId, sessionID);	
			responseParam.put("temp",temp);
		}else if (dealMethod.equals("gettree")) {
			String pid = (String) requestParam.get("treeId");
			List list = organizationService.findSubOrgTreeByPid(pid);
			responseParam.put("list", list);
		}else if (dealMethod.equals("showDetail")){
 			//显示新增对话框
			String orgid = (String)requestParam.get("nodeid");
			Organization org = organizationService.findOrganizationByKey(orgid);	
			StringBuffer sb = new StringBuffer();
			organizationService.findOrganizationChildById(sb, orgid);
			String strIds = sb.toString();
			if(strIds != null && strIds.startsWith(",")){
				strIds.replaceFirst(",", "");
			}
			responseParam.put("strIds",strIds);	
			responseParam.put("organization",org);	
 		}else if(dealMethod.equals("getChildName")){
 			//获取同级机构下面的名称
 			String checkId = (String) requestParam.get("checkId");
 			String str = organizationService.findChildOrganizationNameById(checkId);
 			responseParam.put("orgNames",str);	
 		}
		
		
	}

	@Override
	public ResponseEvent validateData(RequestEvent req) throws Exception {
		// TODO 自动生成方法存根
		return null;
	}

	/**
	 * @param organizationService 要设置的 organizationService
	 */
	public void setOrganizationService(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}

}
