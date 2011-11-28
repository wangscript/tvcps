package com.house.biz.systemConfig.service;

import com.house.biz.entity.VillageEntity;
import com.house.biz.systemConfig.dao.VillageDao;
import com.house.biz.systemConfig.dao.VillageDaoImpl;
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
public interface VillageService extends GenericService<VillageEntity,String>{	

	/**
	 * 保存数据
	 * @return
	 */
	public String saveVillage(VillageEntity villageEntity);
	


}
