/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.configmanager.web.action;

import java.util.Map;

import org.apache.struts.action.ActionForm;

import com.baize.ccms.biz.configmanager.domain.GeneralSystemSet;
import com.baize.ccms.biz.configmanager.domain.InformationFilter;
import com.baize.ccms.biz.configmanager.web.form.InformationFilterForm;
import com.baize.common.core.dao.Pagination;
import com.baize.common.core.web.GeneralAction;
import com.baize.common.core.web.event.RequestEvent;
import com.baize.common.core.web.event.ResponseEvent;

/**
 * <p>
 * 标题: 系统安装程序Action
 * </p>
 * <p>
 * 描述: 数据的操作及生成xml文档
 * </p>
 * <p>
 * 模块: 系统安装程序
 * </p>
 * <p>
 * 版权: Copyright (c) 2009南京百泽网络科技有限公司
 * 
 * @author 杨信
 * @version 1.0
 * @since 2009-7-15 下午04:12:33
 * @history（历次修订内容、修订人、修订时间等） 
*/
 

public class InformationFilterAction extends GeneralAction {

	private String dealMethod = "";

	@Override
	protected void doFormFillment(ActionForm actionForm,
			ResponseEvent responseEvent, String userIndr) {
		InformationFilterForm form = (InformationFilterForm) actionForm;
		InformationFilter informationFilter = new InformationFilter();
		// Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		Map<Object, Object> responseParam = responseEvent.getRespMapParam();

		if (dealMethod.equals("")) {
			log.debug("显示作者列表");
			form.setPagination((Pagination) responseParam.get("pagination"));
			form.setCategoryId((String) responseParam.get("categoryId"));
			String categoryId = (String) responseParam.get("categoryId");
			this.setRedirectPage("success", userIndr);
		}else if(dealMethod.equals("detail")) {
			
			form.setCategoryId((String) responseParam.get("categoryId"));
			String categoryId = (String) responseParam.get("categoryId");
			
			this.setRedirectPage("detail", userIndr);
		}else if(dealMethod.equals("add")) {
			log.debug("添加过滤项目");
			String cate = (String) responseParam.get("categoryId");
			form.setCategoryId((String) responseParam.get("categoryId"));
            
			String  message=(String)responseParam.get("message");
			form.setInfoMessage((String)responseParam.get("message"));
			this.setRedirectPage("detail", userIndr);
		}else if(dealMethod.equals("delete")) {
			log.debug("删除过滤项目");
			String categoryId = (String) responseParam.get("categoryId");
			form.setCategoryId(categoryId);
		//	System.out.println(categoryId+"rrrrrrrrrrrrrrrrrrrrrrrr");
			this.setRedirectPage("list", userIndr);
		}else if(dealMethod.equals("update")) {
			String cate = (String) responseParam.get("categoryId");
			form.setCategoryId((String) responseParam.get("categoryId"));
            
			String  message=(String)responseParam.get("message");
			form.setInfoMessage((String)responseParam.get("message"));
			this.setRedirectPage("updateOne", userIndr);
		/*	generalSystemSet = form.getGeneralSystemSet();
			log.debug("---------------" + generalSystemSet.getId());
			// String authorId = form.getAuthorId();
			// requestParam.put("authorId", authorId);
			String categoryId = form.getCategoryId();
			Map<Object, Object> requestParam;
			requestParam.put("categoryId", categoryId);
			requestParam.put("generalSystemSet", generalSystemSet);	*/
		}else if(dealMethod.equals("updateOne")) {
			
			String categoryId = (String) responseParam.get("categoryId");
			form.setCategoryId(categoryId);
			informationFilter = (InformationFilter) responseParam
					.get("informationFilter");
			form.setInformationFilter(informationFilter);
			this.setRedirectPage("updateOne", userIndr);
		}
		
    }


	protected void performTask(ActionForm actionForm,
			RequestEvent requestEvent, String userIndr) throws Exception {
		InformationFilterForm form = (InformationFilterForm) actionForm;
		this.dealMethod = form.getDealMethod();
		Map<Object, Object> requestParam = requestEvent.getReqMapParam();
		InformationFilter informationFilter = new InformationFilter();
		informationFilter = form.getInformationFilter();
		if (dealMethod.equals("")) {
			// 显示信息过滤设置列表
			String categoryId = form.getCategoryId();
			requestParam.put("categoryId", categoryId);
			requestParam.put("informationFilter", informationFilter);
			// 查询信息过滤设置内容
			
			if(this.isUpSystemAdmin){
				form.setQueryKey("findInformationFilterPage");	
			}else{               
				form.setQueryKey("findInformationSessionIdFilterPage");
			}
			
		}else if (dealMethod.equals("detail")) {
				// 添加过滤设置
			   informationFilter = form.getInformationFilter();
				// 传过来的树节点id
				String categoryId = form.getCategoryId();
				requestParam.put("categoryId", categoryId);
				requestParam.put("informationFilter", informationFilter);
				
		}else if(dealMethod.equals("add")){ 
			// 显示作者内容详细信息
			String categoryId = form.getCategoryId();
//			System.out.println("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww"+categoryId);
			String authorId = form.getAuthorId();
			requestParam.put("categoryId", categoryId);
		//	System.out.println("fffffffffffffffffff"+form.getInformationFilter().getField1());
 			informationFilter = form.getInformationFilter();
			requestParam.put("informationFilter", informationFilter);
	    } else if (dealMethod.equals("delete")) {
			String categoryId = form.getCategoryId();
			String authorId = form.getInformationFilter().getId();
			requestParam.put("categoryId", categoryId);
			requestParam.put("authorId", authorId);
	}else if(dealMethod.equals("update")) {
		
	    informationFilter= form.getInformationFilter();
		String categoryId = form.getCategoryId();
		requestParam.put("categoryId", categoryId);
		requestParam.put("informationFilter", informationFilter);	
	}else if(dealMethod.equals("updateOne")){
		String categoryId = form.getCategoryId();
		String authorId = form.getAuthorId();
		requestParam.put("authorId", authorId);
		requestParam.put("categoryId", categoryId);
		requestParam.put("informationFilter", informationFilter);
	}
	}
	@Override
	protected void init(String userIndr) throws Exception {

	}
}
