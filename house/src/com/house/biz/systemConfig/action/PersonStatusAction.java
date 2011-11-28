package com.house.biz.systemConfig.action;

import com.house.biz.entity.PersonStatusEntity;
import com.house.biz.systemConfig.service.PersonStatusService;
import com.house.core.action.GenericAction;

/**
 * 
 * <p>标题: —— 人员状态管理ACTION</p>
 * <p>描述: —— 对人员状态管理管理相关的操作</p>
 * <p>模块: 人员状态管理</p>
 * <p>版权: Copyright (c) 2011 娄伟峰
 * @author 娄伟峰
 * @version 1.0
 * @since 2011-11-9 下午05:09:43
 * @history（历次修订内容、修订人、修订时间等）。
 */
@SuppressWarnings("serial")
public class PersonStatusAction extends GenericAction{	
	
	private PersonStatusService personStatusService;
	private PersonStatusEntity personStatusEntity = new PersonStatusEntity();

	
	/**
	 * 分页查询显示列表。
	 * @return
	 * @throws Exception
	 */
	public String queryPersonStatus() throws Exception{
		pagination = personStatusService.queryObjectsByPaginationAndObject(personStatusEntity, pagination);
		return SUCCESS;
	}
	
	/**
	 * 保存人员状态信息(添加修改)。
	 * @return
	 * @throws Exception
	 */
	public String savePersonStatus()throws Exception{
		addActionMessage(personStatusService.savePersonStatus(personStatusEntity));
		return SUCCESS;
	}
	
	/**
	 * 删除人员状态信息数据。
	 * @return
	 * @throws Exception
	 */
	public String deletePersonStatusByIds()throws Exception{
		addActionMessage(personStatusService.deleteObjectByIds(strChecked));
		return SUCCESS;
	}
	
	/**
	 * 根据ID查询出人员状态信息。
	 * @return
	 */
	public String findPersonStatusById() throws Exception{		
		personStatusService.findObjectById(strChecked);
		return SUCCESS;
	}
	

	public PersonStatusEntity getPersonStatusEntity() {
		return personStatusEntity;
	}

	public void setPersonStatusEntity(PersonStatusEntity personStatusEntity) {
		this.personStatusEntity = personStatusEntity;
	}

	public void setPersonStatusService(PersonStatusService personStatusService) {
		this.personStatusService = personStatusService;
	}


}
