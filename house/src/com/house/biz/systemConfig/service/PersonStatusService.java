package com.house.biz.systemConfig.service;

import java.util.List;

import com.house.biz.entity.PersonStatusEntity;
import com.house.biz.entity.VillageEntity;
import com.house.core.entity.Pagination;
import com.house.core.service.GenericService;


/**
 * 
 * <p>标题: —— 小区代码表业务管理层接口</p>
 * <p>描述: —— 小区代码表相关的业务操作</p>
 * <p>模块: 小区代码管理表</p>
 * <p>版权: Copyright (c) 2011 娄伟峰
 * @author 杜小猛
 * @version 1.0
 * @since 2011-11-9 下午 3:13:42
 * @history（历次修订内容、修订人、修订时间等）
 */
public interface PersonStatusService extends GenericService<PersonStatusEntity,String>{
	
	
	/**
	 * 保存人员状态信息实体对象
	 * @param personStatusEntity
	 */
	public String savePersonStatus(PersonStatusEntity personStatusEntity)throws Exception;

	
}
