  /**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.usermanager.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.j2ee.cms.biz.usermanager.dao.OperationDao;
import com.j2ee.cms.biz.usermanager.domain.Operation;
import com.j2ee.cms.biz.usermanager.service.OperationService;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.util.StringUtil;

/**
 * <p>标题: —— 操作业务处理类</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 用户管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-2-28 下午03:51:36
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class OperationServiceImpl implements OperationService{
	/** 操作dao */
	private OperationDao operationDao;

	public void addOperation(Operation operation) {
		// TODO 自动生成方法存根
		this.operationDao.save(operation);
	}

	public void deleteOperation(String id) {
		// TODO 自动生成方法存根
		this.operationDao.deleteByKey(id);
	}

	public List<Operation> findAll() {
		// TODO 自动生成方法存根
		return operationDao.findAll();		
	}

	public void modifyOperation(Operation operation) {
		// TODO 自动生成方法存根
		 this.operationDao.update(operation);
	}

	public Operation findOperationById(String id) {
		// TODO Auto-generated method stub
		return this.operationDao.getAndClear(id);
		
	}

	public Pagination findOperationData(Pagination pagination) {
		// TODO Auto-generated method stub
		return operationDao.getPagination(pagination);
	}

	public List findOperation() {
		// TODO Auto-generated method stub
		List alloperationlist =  operationDao.findAll();		
		List alloperationlistdata = new ArrayList();
		for(int i= 0 ; i < alloperationlist.size(); i ++){
			String[] str = new String[2];
			String id = String.valueOf(((Operation)alloperationlist.get(i)).getId());
			String name = String.valueOf(((Operation)alloperationlist.get(i)).getDescription());
			str[0] = id;
			str[1] = name;
			alloperationlistdata.add(str);
		}
		return alloperationlistdata;
	}

	public List<Operation> findOperationDataByName(String name) {
		// TODO Auto-generated method stub
		return operationDao.findByNamedQuery("findOperationDataByName", "name",name);		
	}
	
	public List findOperationByIds(String alloperatorid){
		// TODO Auto-generated method stub
		List alloperationlist = new ArrayList();
		//将字符串转换成LIST
		String stralloperatorid[] = StringUtil.split(alloperatorid, ",");
		for(int i = 0 ; i < stralloperatorid.length; i++){
			Object obj[] = new Object[2];
			obj[0] = stralloperatorid[i];
			Operation operation = operationDao.getAndClear(stralloperatorid[i]);
			obj[1] = StringUtil.convert(operation.getDescription());
			alloperationlist.add(obj);			
		}
		return alloperationlist;
	}
	
	/**
	 * @param operationDao 要设置的 operationDao
	 */
	public void setOperationDao(OperationDao operationDao) {
		this.operationDao = operationDao;
	}

}
