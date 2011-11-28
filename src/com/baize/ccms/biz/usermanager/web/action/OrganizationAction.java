  /**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.biz.usermanager.web.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.baize.ccms.biz.usermanager.domain.Organization;
import com.baize.ccms.biz.usermanager.web.form.OrganizationForm;
import com.baize.ccms.sys.GlobalConfig;
import com.baize.common.core.dao.Pagination;
import com.baize.common.core.util.StringUtil;
import com.baize.common.core.web.GeneralAction;
import com.baize.common.core.web.event.RequestEvent;
import com.baize.common.core.web.event.ResponseEvent;

/**
 * <p>标题: —— 机构ACTION</p>
 * <p>描述: —— 响应页面的请求</p>
 * <p>模块: 用户管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-2-16 下午03:53:13
 * @history（历次修订内容、修订人、修订时间等） 
 */
public final  class OrganizationAction  extends GeneralAction{
	private String dealMethod = "";
	@Override
	protected void init(String userIndr) throws Exception {
		// TODO 自动生成方法存根
		
	}

	@Override
	protected void performTask(ActionForm actionForm, RequestEvent requestEvent, String userIndr) throws Exception {
		OrganizationForm form = (OrganizationForm) actionForm;
		dealMethod = form.getDealMethod();
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		
		
		Organization organization = new Organization();
		organization = form.getOrganization();
		String ids = form.getIds();
		
		//机构ID
		String nodeid = form.getNodeId();
		// 如果nodeid为空或者0
		if(nodeid == null || nodeid.equals("0") || nodeid.equals("")) {
			nodeid = null;
		}
		
		if (dealMethod.equals("") || dealMethod.equals("return")) {	
			//显示机构列表
			form.setInfoShowType(0);		
		/*	
			List list = null;
			list.get(1);*/
			
			String nodename = form.getNodeName();
		//	if(isUpSystemAdmin){
				if(nodeid != null) {
					form.setQueryKey("findOrgPageByNodeid");
				}else{
					form.setQueryKey("findOrgPage");
				}
		/*	}else if(isSiteAdmin){
				if(nodeid != null) {
					form.setQueryKey("findOrgPageByNodeidAndSiteId");
				}else{
					form.setQueryKey("findOrgPageBySiteId");
				}
			}
				*/
						
			requestParam.put("nodeid", nodeid);
			requestParam.put("nodename", nodename);	
			
		}else if (dealMethod.equals("insert")) {
			//保存机构信息		
			String pid = form.getPid();
			if(pid == null || pid.equals("0") || pid.equals("")) {
				pid = null;
			}
			requestParam.put("organization", organization);	
			requestParam.put("pid", pid);	
			
		}else if (dealMethod.equals("delete")) {
			//删除机构信息
			requestParam.put("ids", ids);	
			
		}else if (dealMethod.equals("detail")) {
			//显示机构详细信息
			String id = form.getId();		
		//	requestParam.put("nodeid", nodeid);
			requestParam.put("id", id);
			
		}else if (dealMethod.equals("modify")) {	
			//修改机构信息
			String id = form.getId();			
			String pid = form.getPid();
			String oldpid = form.getParent().getId();
			organization.setId(id);
			if(pid == null || pid.equals("0") || pid.equals("")) {
				pid = null;
			}
			if(oldpid == null || oldpid.equals("0") || oldpid.equals("")) {
				oldpid = null;
			}
			requestParam.put("organization", organization);	
			requestParam.put("pid", pid);
			requestParam.put("oldpid",oldpid);
			
		}else if (dealMethod.equals("gettree")) {
			log.debug("获取机构栏目树");
			String treeId = form.getTreeId();
			if(treeId == null || treeId.equals("0") || treeId.equals("")) {
				treeId = null;
			}

			requestParam.put("treeId", treeId);
	    }else if (dealMethod.equals("showDetail")){
 			//显示新增对话框
 			requestParam.put("nodeid", nodeid);
 		}else if (dealMethod.equals("getChildName")){
 			//获取这个机构下同级机构的名称
 			String checkId = form.getCheckid();
 			requestParam.put("checkId", checkId);
 		}
		
		
	}
	
	@Override
	protected void doFormFillment(ActionForm actionForm, ResponseEvent responseEvent, String userIndr) {
		// TODO 自动生成方法存根
		OrganizationForm form = (OrganizationForm) actionForm;	
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
	//	Organization organization = new Organization();
//		form.setOrganization(organization);		
		// 根据dealMethod判断调用哪个方法
		if (dealMethod.equals("")|| dealMethod.equals("return")) {
			log.debug("显示机构列表");			
			form.setPagination((Pagination) responseParam.get("pagination"));
			List list = (List)responseParam.get("list");
			form.setNodeId((String) responseParam.get("nodeid"));
	//		form.setOrgNameList(String.valueOf(responseParam.get("strNodeName")));
			if(dealMethod.equals("return")){
				form.setTemp("delete");
			}
			this.setRedirectPage("success", userIndr);
			
		} else if (dealMethod.equals("insert")) {			
			log.debug("增加机构成功!");
			boolean temp = (Boolean.parseBoolean(String.valueOf(responseParam.get("temp"))));
		//	form.setInfoMessage("增加机构成功!");
			if(temp == true) {
				form.setInfoMessage("0");
			}else{
				form.setInfoMessage("1");
			}
			//设置处理成功页面转向
			//this.setRedirectPage("addsuccess", userIndr);
			this.setRedirectPage("saveorgmsg", userIndr);
			
		}else if (dealMethod.equals("delete")) {
			log.debug("删除机构成功!");		
			boolean temp = Boolean.parseBoolean(String.valueOf(responseParam.get("temp")));
			boolean usertemp = Boolean.parseBoolean(String.valueOf(responseParam.get("usertemp")));
			
			if(usertemp == true){
				form.setInfoMessage("删除的机构下有用户存在!请检查并先删除用户!删除机构失败!");			
			}else{
				if(temp == true){
					form.setInfoMessage("删除的机构下有子机构存在,请检查并先删除子机构!删除机构失败!");					
				}
			}
			form.setDealMethod("");
			dealMethod = "";
			this.setRedirectPage("return", userIndr);
			
		}else if (dealMethod.equals("detail")) {		
			Organization org = (Organization)responseParam.get("organization");
			form.setOrganization(org);
			if(org.getParent() != null ){
				form.setPid(org.getParent().getId());
				String str = org.getParent().getName();
				log.debug("pname==================="+str);
				form.setPname(String.valueOf(str));
			}
			String strIds = String.valueOf(responseParam.get("strIds"));
			String orgNames = String.valueOf(responseParam.get("orgNames"));
			form.setOrgIds(strIds);
			form.setDealMethod("detail");
			form.setOrgNames(orgNames);
			this.setRedirectPage("addsuccess", userIndr);
			log.debug("显示机构详细信息");
		}else if (dealMethod.equals("modify")) {					
			log.debug("修改机构详细信息");
			form.setInfoMessage("修改机构成功!");
			form.setDealMethod(dealMethod);
			boolean temp = (Boolean.parseBoolean(String.valueOf(responseParam.get("temp"))));
			if(temp == true) {
				form.setInfoMessage("0");
			}else{
				form.setInfoMessage("1");
			}
			this.setRedirectPage("saveorgmsg", userIndr);
		}else if (dealMethod.equals("gettree")) {
			List list = (List) responseParam.get("list");
			form.setJson_list(list);
			form.setJson_attrnames(new String[]{"id","text","url","leaf","checked"});
			this.setRedirectPage("tree", userIndr);
		}else if (dealMethod.equals("showDetail")){
			//显示新增对话框
			Organization org = (Organization)responseParam.get("organization");
			Organization organization = new Organization();
			if(org != null ){
				organization.setId(org.getId());
				organization.setName(org.getName());
			}
			String strIds = String.valueOf(responseParam.get("strIds"));
			form.setOrgIds(strIds);
			form.setOrganization(organization);
			form.setDealMethod("showDetail");
			this.setRedirectPage("showdetail", userIndr);
		} else if (dealMethod.equals("getChildName")) {
			String orgNames = String.valueOf(responseParam.get("orgNames"));
			//为了省略一个页面，将此orgNames放到inforMessages里面
			form.setInfoMessage(orgNames);
			this.setRedirectPage("saveorgmsg", userIndr);
		}
	}

}
