
package com.house.core.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.house.core.entity.Pagination;
/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: —— 设计模块</p>
 * <p>版权: Copyright (c) 2011 house
 * @author 
 * @version 1.0
 * @since 2011-10-13 上午10:14:51
 * <p>修订历史:（历次修订内容、修订人、修订时间等） </p>
 */
public interface GenericDao<T extends Serializable, PK extends Serializable> {

	/**
	 * <p>方法说明：通过条件(对象) 查询 分页 对象</p> 
	 * <p>创建时间：2011-10-14上午10:03:10</p>
	 * <p>作者: 刘加东</p>
	 * @param pagination
	 * @param t
	 * @return Pagination
	 * @throws IllegalAccessException 
	 * @throws NoSuchFieldException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 */
	public Pagination queryObjectsByPaginationAndObject(Pagination pagination,T t) throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException;
	
	/**
	 * <p>方法说明：通过条件(对象) 查询 分页 记录</p> 
	 * <p>创建时间：2011-10-14上午11:47:03</p>
	 * <p>作者: 刘加东</p>
	 * @param t
	 * @return List<T>
	 */
	public List<T> selectByObjectForPagination(T t);
	
	/**
	 * <p>方法说明：通过主键获取对象</p> 
	 * <p>创建时间：2011-10-13上午11:29:31</p>
	 * <p>作者: 刘加东</p>
	 * @param id
	 * @return List<T>
	 */
	public T queryObject(PK id);
	
	/**
	 * <p>方法说明：通过主键Id序列获取一条记录</p> 
	 * <p>创建时间：2011-10-13上午11:32:36</p>
	 * <p>作者: 刘加东</p>
	 * @param ids
	 * @return List<T>
	 */
	public List<T> queryObjects(List<PK> ids);
	
	/**
	 * <p>方法说明：通过条件(对象) 查询记录</p> 
	 * <p>创建时间：2011-10-13上午11:34:35</p>
	 * <p>作者: 刘加东</p>
	 * @param t
	 * @return List<T>
	 */
	public List<T> queryObjectsByObject(T t);
	
	/**
	 * <p>方法说明：通过条件(属性-值) 查询记录</p> 
	 * <p>创建时间：2011-10-13上午11:34:35</p>
	 * <p>作者: 刘加东</p>
	 * @param map
	 * @return List<T>
	 */
	public List<T> queryObjectsByMap(Map map);
	
	/**
	 * <p>方法说明：通过条件(对象) 查询主键id列表</p> 
	 * <p>创建时间：2011-10-13上午11:35:39</p>
	 * <p>作者: 刘加东</p>
	 * @param t
	 * @return List<PK>
	 */
	public List<PK> queryPKsByObject(T t);
	
	/**
	 * <p>方法说明：通过条件(属性-值) 查询主键id列表</p> 
	 * <p>创建时间：2011-10-13上午11:35:39</p>
	 * <p>作者: 刘加东</p>
	 * @param map
	 * @return List<PK>
	 */
	public List<PK> queryPKsByMap(Map map);
	
	/**
	 * <p>方法说明：通过条件(属性-值) 统计数量</p> 
	 * <p>创建时间：2011-10-13上午11:36:44</p>
	 * <p>作者: 刘加东</p>
	 * @return Long
	 */
	public Long countObjecs();
	
	/**
	 * <p>方法说明：通过条件(属性-值) 统计数量</p> 
	 * <p>创建时间：2011-10-13上午11:36:44</p>
	 * <p>作者: 刘加东</p>
	 * @param t
	 * @return Long
	 */
	public Long countObjecsByObject(T t);
	
	/**
	 * <p>方法说明：通过条件(属性-值) 统计数量</p> 
	 * <p>创建时间：2011-10-13上午11:36:44</p>
	 * <p>作者: 刘加东</p>
	 * @param map
	 * @return Long
	 */
	public Long countObjecsByMap(Map map);
	
	/**
	 * <p>方法说明：插入一条记录</p> 
	 * <p>创建时间：2011-10-13上午11:39:44</p>
	 * <p>作者: 刘加东</p>
	 * @param t 
	 * @return Object 
	 */
	public Object insertObject(T t);
	
	/**
	 * <p>方法说明：通过主键删除一条记录</p> 
	 * <p>创建时间：2011-10-13上午11:44:03</p>
	 * <p>作者: 刘加东</p>
	 * @param id 
	 * @return int 操作的记录数
	 */
	public int deleteById(PK id);
	
	/**
	 * <p>方法说明：通过主键列表删除数个记录</p> 
	 * <p>创建时间：2011-10-13上午11:47:22</p>
	 * <p>作者: 刘加东</p>
	 * @param id 
	 * @return int 操作的记录数
	 */
	public int deleteByIds(List<PK> id);
	
	/**
	 * <p>方法说明：通过条件(对象) 删除一条记录 </p> 
	 * <p>创建时间：2011-10-13下午01:26:56</p>
	 * <p>作者: 刘加东</p>
	 * @param map
	 * @return int 操作的记录数
	 */
	public int deleteByObject(T t);
	
	/**
	 * <p>方法说明：通过对象更新记录</p> 
	 * <p>创建时间：2011-10-13下午01:27:33</p>
	 * <p>作者: 刘加东</p>
	 * @param t 
	 * @return int 操作的记录数
	 */
	public int updateByObject(T t);
	
	/**
	 * <p>方法说明：通过条件(属性-值) 更新记录</p> 
	 * <p>创建时间：2011-10-13下午01:28:18</p>
	 * <p>作者: 刘加东</p>
	 * @param map 
	 * @return int 操作的记录数
	 */
	public int updateByMap(Map map);

}