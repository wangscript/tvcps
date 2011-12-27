/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: —— 设计模块</p>
 * <p>版权: Copyright (c) 2011 ackwin
 * @author 
 * @version 1.0
 * @since 2011-10-14 上午10:04:43
 * <p>修订历史:（历次修订内容、修订人、修订时间等） </p>
 */
package com.house.core.service;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.house.core.dao.GenericDao;
import com.house.core.dao.GenericDaoImpl;
import com.house.core.entity.Pagination;
import com.house.core.sys.GlobalConfig;

public class GenericServiceImpl<T extends Serializable, PK extends Serializable>  implements GenericService<T, PK> {

	protected final Logger log = Logger.getLogger(getClass());
	
	/**
	 * 通用数据库操作接口
	 */
	protected GenericDao<T, PK> genericDao;

 
	
	
	

	/* (non-Javadoc)
	 * @see com.ackwin.common.service.GenericService#findObjects(com.ackwin.common.dao.Pagination, java.io.Serializable)
	 */
	@Override
	public Pagination findObjectsByObject(Pagination pagination, T t) {
		// TODO Auto-generated method stub
		if (pagination == null) {
			pagination = new Pagination();
		}
		//获取数据记录集
		List<T> list = genericDao.queryObjectsByObject(t);
		//获取总记录集数量
		long maxRowCount = genericDao.countObjecsByObject(t);
		//--封装分页 - 开始
		pagination.setData(list);
		pagination.setMaxRowCount(maxRowCount);
		return pagination;
	}
	
	
	public Pagination queryObjectsByPaginationAndObject(T t,Pagination pagination) throws Exception {
		return genericDao.queryObjectsByPaginationAndObject(pagination, t);
	}
	


	@Override
	public String deleteObjectByIds(String ids) {
	    try{
    		// TODO Auto-generated method stub		
    		if (null != ids && !"".equals(ids.trim())) {
    			int count = genericDao.deleteByIds(this.stringToArray(ids));
    			if(count > 0){
    				log.info("删除"+ids+"id集合成功，删除总记录数为"+count+"条!");
    				return GlobalConfig.getConfProperty("100002");
    			}			
    		}
    		log.info("删除失败!");
    		return GlobalConfig.getConfProperty("900002");
	    }catch(Exception e){
	        log.info("删除失败!该数据下还包含子数据！");
	        return GlobalConfig.getConfProperty("900002");
	        
	    }
	}
	
	 /**
	  * 把以逗号隔开的ID字符串转换为list
	  * @param ids
	  * @return
	  */
	 public List<PK> stringToArray(String ids){
			List<PK> list = new ArrayList<PK>();
			if(ids != null){
				PK strId[] = (PK[])ids.split(",");
				for(int i = 0 ; i < strId.length ; i++){
					if(!strId[i].equals("")){
						list.add(strId[i]);
					}
				}
			}
			return list;
	 }
	 



	@Override
	public T findObjectById(String id) {
		// TODO Auto-generated method stub
		return genericDao.queryObject((PK)id);		
	}

	@Override
	public String updateObject(T t) {
		// TODO Auto-generated method stub
		if(t != null){
			Object obj = genericDao.updateByObject(t);
			if(obj != null){
				log.info("修改成功");
				return GlobalConfig.getConfProperty("100003");
			}
		}
		log.info("修改失败"+t);
		return GlobalConfig.getConfProperty("900003");
	}
	
	@Override
	public String saveObject(T t) {
		// TODO Auto-generated method stub
		if(t != null){
			Object obj = genericDao.insertObject(t);
			if(obj == null){
				log.info("保存成功");
				return GlobalConfig.getConfProperty("100004");
			}
		}
		log.info("保存失败"+t);
		return GlobalConfig.getConfProperty("900004");
	}
	
	/**
	 * @param genericDao the genericDao to set
	 */
	public void setGenericDao(GenericDao<T, PK> genericDao) {
		this.genericDao = genericDao;
	}


}
