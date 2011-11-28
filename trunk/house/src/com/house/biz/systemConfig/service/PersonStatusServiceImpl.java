package com.house.biz.systemConfig.service;

import com.house.biz.entity.PersonStatusEntity;
import com.house.biz.entity.VillageEntity;
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
public class PersonStatusServiceImpl extends GenericServiceImpl<PersonStatusEntity,String>implements PersonStatusService{
	
	private PersonStatusDao personStatusDao;
	/**
	 * 设置子类dao
	 */
	public void setDao() {
		this.genericDao = personStatusDao;
	}

	public String savePersonStatus(PersonStatusEntity personStatusEntity)throws Exception{
		if(personStatusEntity.getServiceStatusId()!= null && !personStatusEntity.getServiceStatusId().equals("") ){
			return updateObject(personStatusEntity);
		}else{
			personStatusEntity.setServiceStatusId(IDFactory.getId());   //ID号生成策略（时间与5位随机数）可以加前缀字符以区分
			return saveObject(personStatusEntity);
		}
	}
	
	public void setPersonStatusDao(PersonStatusDao personStatusDao) {
		this.personStatusDao = personStatusDao;
	}
}
