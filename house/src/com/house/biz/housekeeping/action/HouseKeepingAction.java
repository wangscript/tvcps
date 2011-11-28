package com.house.biz.housekeeping.action;

import com.house.biz.entity.HouseKeepingEntity;
import com.house.biz.housekeeping.service.HouseKeepingService;
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
public class HouseKeepingAction extends GenericAction{	
	
	private HouseKeepingService houseKeepingService;
	private HouseKeepingEntity houseKeepingEntity = new HouseKeepingEntity();

	/**
	 * 分页查询显示列表。
	 * @return
	 * @throws Exception
	 */
	public String queryHouseKeeping() throws Exception{
		pagination = houseKeepingService.queryObjectsByPaginationAndObject(houseKeepingEntity, pagination);
		return SUCCESS;
	}
	
	/**
	 * 保存家政服务类型(添加修改)。
	 * @return
	 * @throws Exception
	 */
	public String saveHouseKeeping()throws Exception{
		addActionMessage(houseKeepingService.saveHouseKeeping(houseKeepingEntity));
		return SUCCESS;
	}
	
	/**
	 * 删除家政服务类型数据。
	 * @return
	 * @throws Exception
	 */
	public String deleteHouseKeepingByIds()throws Exception{
		addActionMessage(houseKeepingService.deleteObjectByIds(strChecked));
		return SUCCESS;
	}
	
	/**
	 * 根据ID查询出家政服务类型信息。
	 * @return
	 */
	public String findHouseKeepingById() throws Exception{	
		houseKeepingEntity = houseKeepingService.findObjectById(strChecked);
		return SUCCESS;
	}
	




	public void setHouseKeepingService(HouseKeepingService houseKeepingService) {
		this.houseKeepingService = houseKeepingService;
	}

	public HouseKeepingEntity getHouseKeepingEntity() {
		return houseKeepingEntity;
	}

	public void setHouseKeepingEntity(HouseKeepingEntity houseKeepingEntity) {
		this.houseKeepingEntity = houseKeepingEntity;
	}



}
