package com.house.biz.systemConfig.action;

import org.apache.catalina.connector.Request;

import com.house.biz.entity.EmployerEntity;
import com.house.biz.entity.VillageEntity;
import com.house.biz.systemConfig.service.VillageService;
import com.house.core.action.GenericAction;

/**
 * 
 * <p>标题: —— 小区代码管理ACTION</p>
 * <p>描述: —— 对小区代码管理相关的操作</p>
 * <p>模块: 小区代码管理</p>
 * <p>版权: Copyright (c) 2011 娄伟峰
 * @author 娄伟峰
 * @version 1.0
 * @since 2011-11-9 下午05:09:43
 * @history（历次修订内容、修订人、修订时间等）
 */
@SuppressWarnings("serial")
public class VillageAction extends GenericAction{	
	
	private VillageService villageService;
	private VillageEntity villageEntity = new VillageEntity();
	/**
	 * 查询显示列表
	 * @return
	 * @throws Exception
	 */
	public String queryVillage() throws Exception{
		pagination = villageService.queryObjectsByPaginationAndObject(villageEntity,pagination);		
		return SUCCESS;
	}
	
	/**
	 * 保存小区信息(添加修改)
	 * @return
	 */
	public String saveVillage() throws Exception{
		addActionMessage(villageService.saveVillage(villageEntity));	
		return SUCCESS;
	}
	
	/**
	 * 根据页面选择的ID删除记录
	 * @return
	 * @throws Exception
	 */
	public String deleteVillageByIds() throws Exception{
		addActionMessage(villageService.deleteObjectByIds(strChecked));
		return SUCCESS;
	}
	
	/**
	 * 根据主键查询
	 * @return
	 */
	public String findVillageById(){
		villageEntity = villageService.findObjectById(strChecked);
		return SUCCESS;
	}
	
	public String chooseVillage() throws Exception {
	    pagination = villageService.queryObjectsByPaginationAndObject(villageEntity, pagination);
        return SUCCESS;
    }
	
	public void setVillageService(VillageService villageService) {
		this.villageService = villageService;
	}
	public VillageEntity getVillageEntity() {
		return villageEntity;
	}	
	public void setVillageEntity(VillageEntity villageEntity) {
		this.villageEntity = villageEntity;
	}

}
