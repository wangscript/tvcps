package com.house.biz.housekeeping.service;

import com.house.biz.entity.HouseKeepingEntity;
import com.house.biz.housekeeping.dao.HouseKeepingDao;
import com.house.core.service.GenericServiceImpl;
import com.house.core.util.IDFactory;


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
public class HouseKeepingServiceImpl extends GenericServiceImpl<HouseKeepingEntity,String>implements HouseKeepingService{
	
	private HouseKeepingDao houseKeepingDao;
	/**
	 * 设置子类dao
	 */
	public void setDao() {
		this.genericDao = houseKeepingDao;
	}

	public String saveHouseKeeping(HouseKeepingEntity houseKeepingEntity)throws Exception{
		if(houseKeepingEntity.getHouseKeepingId()!= null && !houseKeepingEntity.getHouseKeepingId().equals("") ){
			return updateObject(houseKeepingEntity);
		}else{
			houseKeepingEntity.setHouseKeepingId(IDFactory.getId());   //ID号生成策略（时间与5位随机数）可以加前缀字符以区分
			return saveObject(houseKeepingEntity);
		}
	}

	public void setHouseKeepingDao(HouseKeepingDao houseKeepingDao) {
		this.houseKeepingDao = houseKeepingDao;
	}

	
}
