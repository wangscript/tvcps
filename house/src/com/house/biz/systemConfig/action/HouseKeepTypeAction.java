package com.house.biz.systemConfig.action;

import com.house.biz.entity.HouseKeepTypeEntity;
import com.house.biz.systemConfig.service.HouseKeepTypeService;
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
public class HouseKeepTypeAction extends GenericAction{	
	
	private HouseKeepTypeService houseKeepTypeService;
	private HouseKeepTypeEntity houseKeepTypeEntity = new HouseKeepTypeEntity();

	/**
	 * 分页查询显示列表。
	 * @return
	 * @throws Exception
	 */
	public String queryHouseKeepType() throws Exception{
		pagination = houseKeepTypeService.queryObjectsByPaginationAndObject(houseKeepTypeEntity, pagination);
		return SUCCESS;
	}
	
	/**
	 * 保存家政服务类型(添加修改)。
	 * @return
	 * @throws Exception
	 */
	public String saveHouseKeepType()throws Exception{
		addActionMessage(houseKeepTypeService.saveHouseKeepType(houseKeepTypeEntity));
		return SUCCESS;
	}
	
	/**
	 * 删除家政服务类型数据。
	 * @return
	 * @throws Exception
	 */
	public String deleteHouseKeepTypeByIds()throws Exception{
		addActionMessage(houseKeepTypeService.deleteObjectByIds(strChecked));
		return SUCCESS;
	}
	
	/**
	 * 根据ID查询出家政服务类型信息。
	 * @return
	 */
	public String findHouseKeepTypeById() throws Exception{		
		houseKeepTypeEntity = houseKeepTypeService.findObjectById(strChecked);
		return SUCCESS;
	}
	



	public HouseKeepTypeEntity getHouseKeepTypeEntity() {
		return houseKeepTypeEntity;
	}

	public void setHouseKeepTypeEntity(HouseKeepTypeEntity houseKeepTypeEntity) {
		this.houseKeepTypeEntity = houseKeepTypeEntity;
	}

	public void setHouseKeepTypeService(HouseKeepTypeService houseKeepTypeService) {
		this.houseKeepTypeService = houseKeepTypeService;
	}


}
