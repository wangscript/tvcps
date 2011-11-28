
package com.baize.ccms.biz.usermanager.service;


import java.util.Map;

import com.baize.ccms.biz.usermanager.domain.Operation;
import com.baize.common.core.dao.Pagination;
import com.baize.common.core.service.BaseService;
import com.baize.common.core.web.event.RequestEvent;
import com.baize.common.core.web.event.ResponseEvent;

/**
 * 
 * <p>标题: —— 系统参数操作表</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 系统参数</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 魏仲芹
 * @version 1.0
 * @since 2009-3-9 上午11:48:12
 * @history（历次修订内容、修订人、修订时间等）
 */
public  final class OperationBiz extends BaseService {
	/**   */
	private OperationService operationService;
	@Override
	public void performTask(RequestEvent requestEvent, ResponseEvent responseEvent)
	throws Exception {
		String dealMethod = requestEvent.getDealMethod();
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		
		//显示用户操作列表	 
		if(dealMethod.equals("")){			
			//获取分页对象
			Pagination pagination = (Pagination) requestEvent.getReqMapParam().get("pagination");
			//向map中保存数据
			responseParam.put("pagination",operationService.findOperationData(pagination));
		}
		// 增加一条操作记录		
		if(dealMethod.equals("insert")){
			//获取页面参数
			Operation operation=(Operation) requestEvent.getReqMapParam().get("operation");
			//保存
			operationService.addOperation(operation);
		}		
		//修改某条数据		
		if(dealMethod.equals("modify")){
			Operation operation=(Operation)requestEvent.getReqMapParam().get("operation");
			operationService.modifyOperation(operation);
		}		
		//删除数据		 
		if(dealMethod.equals("delete")){
			String ids=(String) requestEvent.getReqMapParam().get("ids");
			String id[]=ids.split(",");
			for(int i = 0 ; i < id.length; i++){
				//根据主键删除数据
				operationService.deleteOperation(id[i]);	
			}
		}		
		//查看详细信息		 
		if(dealMethod.equals("detail")){
			String id=(String) requestEvent.getReqMapParam().get("id");
			Operation operation=operationService.findOperationById(id);
			responseParam.put("operation", operation);
		}
		
	}

	@Override
	public ResponseEvent validateData(RequestEvent req) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void setOperationService(OperationService operationService) {
		this.operationService = operationService;
	}

}
