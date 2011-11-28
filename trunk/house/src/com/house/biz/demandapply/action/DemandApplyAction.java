package com.house.biz.demandapply.action;

import com.house.biz.demandapply.service.DemandApplyService;
import com.house.biz.entity.DemandApplyEntity;
import com.house.biz.entity.ServicePersonEntity;
import com.house.biz.serviceperson.service.ServicePersonService;
import com.house.core.action.GenericAction;

/**
 * 
 * <p>标题: —— 家政服务类型管理ACTION</p>
 * <p>描述: —— 家政服务类型相关的操作</p>
 * <p>模块: 家政服务类型管理</p>
 * <p>版权: Copyright (c) 2011 娄伟峰
 * @author 娄伟峰
 * @version 1.0
 * @since 2011-11-9 下午05:09:43
 * @history（历次修订内容、修订人、修订时间等）。
 */
@SuppressWarnings("serial")
public class DemandApplyAction extends GenericAction{	
	
	private DemandApplyService demandApplyService;
	private DemandApplyEntity demandApplyEntity = new DemandApplyEntity();

	/**
	 * 分页查询显示列表。
	 * @return
	 * @throws Exception
	 */
	public String queryDemandApply() throws Exception{
		pagination = demandApplyService.queryObjectsByPaginationAndObject(demandApplyEntity, pagination);
		return SUCCESS;
	}
	
	/**
	 * 保存家政服务类型(添加修改)。
	 * @return
	 * @throws Exception
	 */
	public String saveDemandApply()throws Exception{
		addActionMessage(demandApplyService.saveDemandApply(demandApplyEntity));
		return SUCCESS;
	}
	
	/**
	 * 删除家政服务类型数据。
	 * @return
	 * @throws Exception
	 */
	public String deleteDemandApplyByIds()throws Exception{
		addActionMessage(demandApplyService.deleteObjectByIds(strChecked));
		return SUCCESS;
	}
	
	/**
	 * 根据ID查询出家政服务类型信息。
	 * @return
	 */
	public String findDemandApplyById() throws Exception{	
		demandApplyEntity = demandApplyService.findObjectById(strChecked);
		return SUCCESS;
	}
	







	public void setDemandApplyService(DemandApplyService demandApplyService) {
		this.demandApplyService = demandApplyService;
	}

	public DemandApplyEntity getDemandApplyEntity() {
		return demandApplyEntity;
	}

	public void setDemandApplyEntity(DemandApplyEntity demandApplyEntity) {
		this.demandApplyEntity = demandApplyEntity;
	}



}
