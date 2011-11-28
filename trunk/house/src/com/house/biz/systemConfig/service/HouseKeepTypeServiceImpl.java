package com.house.biz.systemConfig.service;

import com.house.biz.entity.HouseKeepTypeEntity;
import com.house.biz.entity.PersonStatusEntity;
import com.house.biz.entity.VillageEntity;
import com.house.biz.systemConfig.dao.HouseKeepTypeDao;
import com.house.biz.systemConfig.dao.PersonStatusDao;
import com.house.core.entity.Pagination;
import com.house.core.service.GenericServiceImpl;
import com.house.core.util.IDFactory;
import com.house.core.util.StringUtil;


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
public class HouseKeepTypeServiceImpl extends GenericServiceImpl<HouseKeepTypeEntity,String>implements HouseKeepTypeService{
	
	private HouseKeepTypeDao houseKeepTypeDao;
	/**
	 * 设置子类dao
	 */
	public void setDao() {
		this.genericDao = houseKeepTypeDao;
	}

	public String saveHouseKeepType(HouseKeepTypeEntity houseKeepTypeEntity)throws Exception{
		if(houseKeepTypeEntity.getHouseKeepTypeId()!= null && !houseKeepTypeEntity.getHouseKeepTypeId().equals("") ){
			return updateObject(houseKeepTypeEntity);
		}else{
			houseKeepTypeEntity.setHouseKeepTypeId(IDFactory.getId());   //ID号生成策略（时间与5位随机数）可以加前缀字符以区分
			return saveObject(houseKeepTypeEntity);
		}
	}

	public void setHouseKeepTypeDao(HouseKeepTypeDao houseKeepTypeDao) {
		this.houseKeepTypeDao = houseKeepTypeDao;
	}
	
}
