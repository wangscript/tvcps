  /**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.usermanager.service;

import java.util.List;

import com.j2ee.cms.biz.usermanager.domain.Operation;
import com.j2ee.cms.common.core.dao.Pagination;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 用户管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-2-28 下午03:48:50
 * @history（历次修订内容、修订人、修订时间等） 
 */
public interface OperationService {
	/**
	 *添加数据 
	 */
	public void addOperation(Operation operation);
	
	/**
	 * 删除数据
	 */
	public void deleteOperation(String id);
	
	/**
	 * 修改数据
	 */
	public void modifyOperation(Operation operation);
	
	/**
	 * 查询数据
	 */
	public List findOperation();
	
	/**
	 * 查询所有数据
	 * @return
	 */
	public List<Operation> findAll();
	
	/**
	 * 查找数据
	 * @return
	 */
	public List<Operation> findOperationDataByName(String name);
	/*
	 * 分页显示所有记录
	 * */
	public Pagination findOperationData(Pagination pagination);
	
	/*
	 * 根据id显示该所有信息
	 * */
	public Operation findOperationById(String id);
	
	/**
	 * 根据多个操作ID查询操作名
	 * @param alloperatorid 所有的操作ID，中间以逗号隔开
	 * @return List list里面存放多个OBJECT数组，数组大小为2，1为ID，2为name 
	 */
	public List findOperationByIds(String alloperatorid);
}
