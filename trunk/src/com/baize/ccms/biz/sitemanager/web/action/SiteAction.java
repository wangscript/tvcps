/**
 * project：通用内容管理系统
 * Company: 南京瀚沃信息科技有限责任公司
*/
package com.baize.ccms.biz.sitemanager.web.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;

import com.baize.ccms.biz.sitemanager.domain.Site;
import com.baize.ccms.biz.sitemanager.web.form.SiteForm;
import com.baize.ccms.biz.usermanager.domain.User;
import com.baize.common.core.dao.Pagination;
import com.baize.common.core.web.GeneralAction;
import com.baize.common.core.web.event.RequestEvent;
import com.baize.common.core.web.event.ResponseEvent;

/**
 * <p>标题: 网站管理的action</p>
 * <p>描述: 网站管理的action，用于处理网站，接受表单等页面中传过来的值，然后再传回表单</p>
 * <p>模块: 网站管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author <a href="mailto:xinyang921@gmail.com">郑荣华</a>
 * @version 1.0
 * @since 2009-3-13 下午04:10:27
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class SiteAction extends GeneralAction {
	
	private String dealMethod = "";
	 
	@Override
	protected void doFormFillment(ActionForm actionForm, ResponseEvent responseEvent, String userIndr) {
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
        SiteForm form = (SiteForm)actionForm;
        HttpServletRequest request = (HttpServletRequest) responseParam.get("HttpServletRequest");
        
        //分页显示网站信息
        if(dealMethod.equals("")){
        	Pagination pagination = (Pagination)responseParam.get("pagination");
        	form.setPagination(pagination);
        	this.setRedirectPage("siteSuccess", userIndr);

        // 添加网站信息		
        }else if(dealMethod.equals("add")){
        	String infoMessage = "";
        	infoMessage = (String) responseParam.get("infoMessage");
        	form.setInfoMessage(infoMessage);
        	this.setRedirectPage("add", userIndr);

        //删除指定网站     
        }else if(dealMethod.equals("delete")){
        	String infoMessage = "";
        	infoMessage = (String) responseParam.get("infoMessage");
        	form.setInfoMessage(infoMessage);
        	this.setRedirectPage("deleteSiteSuccess", userIndr);

        //修改网站信息		
        }else if(dealMethod.equals("modify")){
        	String infoMessage = (String) responseParam.get("infoMessage");
        	form.setInfoMessage(infoMessage);
        	this.setRedirectPage("add", userIndr);

        //指定网站的详细信息		
        }else if(dealMethod.equals("detail")){
        	Site site = (Site)responseParam.get("site");
        	form.setSite(site);

        //选择网站    
        }else if(dealMethod.equals("changeSite")){
        	String siteOfRolesIn = (String) responseParam.get("siteOfRolesIn");
        	form.setSiteOfRolesIn(siteOfRolesIn);
        	List<Site> siteList = (List)responseParam.get("siteList");
        	//处理切换网站
        	changeSite(form, siteList, (String)responseParam.get("currentWebId"));
        	this.setRedirectPage("chooseSite", userIndr);

        //加载树的信息    
        }else if(dealMethod.equals("getTree")){
        	form.setJson_list((List)responseParam.get("treeList"));
        	this.setRedirectPage("tree", userIndr);
        
        // 用户没有网站时创建网站
		} else if(dealMethod.equals("addSite")) {
			String infoMessage = (String)responseParam.get("infoMessage");
			form.setInfoMessage(infoMessage+"#");
        	this.setRedirectPage("add", userIndr);
			
		// 网站切换后的初始化
		} else if(dealMethod.equals("initSite")) {
			HttpSession session = request.getSession();
			String siteId = (String) responseParam.get("siteid");
			User user = (User) responseParam.get("user");
			form.setSiteloginname(user.getLoginName());
			form.setSiteloginpassword(user.getPassword());
	        session.getAttribute("siteId");
	        session.setAttribute("siteId", siteId);
			this.setRedirectPage("initSiteSuccess", userIndr);
			
		// 添加网站成功后	
		}else if(dealMethod.equals("addSiteSuccess")){
			User user = (User) responseParam.get("user");
			form.setSiteloginname(user.getLoginName());
			form.setSiteloginpassword(user.getPassword());
			form.setLoginMessage("添加网站成功");
			this.setRedirectPage("addSiteSuccess", userIndr);
		}
	}

	@Override
	protected void performTask(ActionForm actionForm, RequestEvent requestEvent, String userIndr) throws Exception {
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		HttpServletRequest request = (HttpServletRequest) requestParam.get("HttpServletRequest");
		SiteForm form = (SiteForm)actionForm;
		this.dealMethod = form.getDealMethod();
		Site site = form.getSite();
	
		//分页显示网站信息
		if(dealMethod.equals("")){
			if(this.isUpSystemAdmin) {
				form.setQueryKey("findSitePage");
			}
			requestParam.put("pagination", form.getPagination());

		// 添加网站信息	
		}else if(dealMethod.equals("add")){
			requestParam.put("site", site);

		//删除指定网站    		
		}else if(dealMethod.equals("delete")){
			String deletedId = form.getSiteid();
			requestParam.put("deletedId", deletedId);

		//修改网站信息	
		}else if(dealMethod.equals("modify")){
			String oldSiteName  = form.getOldSiteName();
			requestParam.put("oldSiteName", oldSiteName);
			requestParam.put("form", form);

		//指定网站的详细信息		
		}else if(dealMethod.equals("detail")){
			// 要查看网站id
			String siteid = form.getSiteid();
			requestParam.put("siteid", siteid);

		//切换网站	
		}else if(dealMethod.equals("changeSite")){
			
		//加载树的信息	
		}else  if(dealMethod.equals("getTree")){
			String treeid = form.getTreeId();
			requestParam.put("treeid", treeid);
			
		// 用户没有网站时创建网站
		} else if(dealMethod.equals("addSite")) {
			requestParam.put("request", request);
			requestParam.put("site", site);
			
		// 网站切换后的初始化
		} else if(dealMethod.equals("initSite")) {
			String siteid = form.getSiteid();
			requestParam.put("siteid", siteid);
		
		// 添加网站成功后	
		}else if(dealMethod.equals("addSiteSuccess")){
			
		}
	}
	
	/**
	 * 处理网站的切换
	 * @param form 网站表单对象
	 * @param siteList 查处的网站列表
	 */
	private void changeSite(SiteForm form, List<Site> siteList, String currentwebid){
		//设置网站列表以便传入表单中
		form.setJson_list(siteList);
    	String webName = "";
    	String webid = "";
    	//将查出的网站名字和id放入一个字符串中，传入页面
    	for(int i=0 ; i < siteList.size(); i++ ){
    		if(i == siteList.size()-1)
    		{
    			webid += siteList.get(i).getId();
     		    webName += siteList.get(i).getName();
    		}else{
    			webid += siteList.get(i).getId()+":::";
     		    webName += siteList.get(i).getName()+":::";
    		}
    	}
    	form.setWebname(webName);
    	form.setWebid(webid);
    	form.setCurrentwebid(currentwebid);
	}
	
	@Override
	protected void init(String userIndr) throws Exception {
		this.setRedirectPage("siteDetail", userIndr);
	}
}
