/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: —— 设计模块</p>
 * <p>版权: Copyright (c) 2011 ackwin
 * @author 
 * @version 1.0
 * @since 2011-10-14 上午09:55:55
 * <p>修订历史:（历次修订内容、修订人、修订时间等） </p>
 */
package com.house.core.service;

import java.io.Serializable;

import com.house.core.dao.GenericDaoImpl;
import com.house.core.entity.Pagination;

public interface GenericService <T extends Serializable, PK extends Serializable> {
	
	/**
	 * <p>方法说明：通过条件(对象) 查询记录</p> 
	 * <p>创建时间：2011-10-14上午10:03:10</p>
	 * <p>作者: 刘加东</p>
	 * @param pagination
	 * @param t
	 * @return Pagination
	 */
	public Pagination findObjectsByObject(Pagination pagination,T t);
	
	/**
	 * 根据对象条件查询分页数据
	 * @param t
	 * @param pagination
	 * @return
	 * @throws Exception
	 */	
	public Pagination queryObjectsByPaginationAndObject(T t,Pagination pagination) throws Exception;
	/**
	 * 根据ID集合删除数据
	 * @param ids
	 * @return
	 */
	public String deleteObjectByIds(String ids) ;
	/**
	 * 根据主键查找对象
	 * @param id
	 * @return
	 */
	public T findObjectById(String id);
	/**
	 * 更新对象
	 * @param t
	 */
	public String updateObject(T t);
	/**
	 * 保存对象
	 * @param t
	 * @return
	 */
	
	public String saveObject(T t) ;
}
