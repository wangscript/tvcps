/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.biz.usermanager.service.impl;

import java.util.List;

import com.baize.ccms.biz.usermanager.dao.ResourceDao;
import com.baize.ccms.biz.usermanager.domain.Resource;
import com.baize.ccms.biz.usermanager.service.ResourceService;
import com.baize.common.core.dao.Pagination;

/**
 * <p>标题: —— 资源业务逻辑处理类</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 通用平台</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 杨信
 * @version 1.0
 * @since 2009-3-4 上午10:09:18
 * @history（历次修订内容、修订人、修订时间等） 
 */

public class ResourceServiceImpl implements ResourceService {

	/** 注入资源dao */
	private ResourceDao resourceDao;



	/* (non-Javadoc)
	 * @see com.baize.ccms.biz.usermanager.service.ResourceService#findResourceByKey(int)
	 */
	public Resource findResourceByKey(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.baize.ccms.biz.usermanager.service.ResourceService#findResourceData(com.baize.common.core.dao.Pagination)
	 */
	public Pagination findResourceData(Pagination pagination) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.baize.ccms.biz.usermanager.service.ResourceService#modifyResource(com.baize.ccms.biz.usermanager.domain.Resource)
	 */
	public void modifyResource(Resource resource) {
		// TODO Auto-generated method stub

	}
	
	public List<Resource> findResourceByTypeAndIdentifier(Resource resource) {
		// TODO Auto-generated method stub
		String[] str = {"identifier","siteid","type"};
		Object[] obj = {resource.getIdentifier(),resource.getSite().getId(),resource.getType()};
		return resourceDao.findByNamedQuery("findResourceByTypeAndIdentifier", str, obj);		
	}
	
	public List<Resource> findResourceByTypeAndSiteId(String siteId) {
		// TODO Auto-generated method stub
		String[] str = {"siteid","type"};
		Object[] obj = {siteId,Resource.TYPE_COLUMN};
		return resourceDao.findByNamedQuery("findResourceByTypeAndSiteId", str, obj);		
	}
	
  public void addResource(Resource Resource) {
		// TODO Auto-generated method stub
		resourceDao.save(Resource);
	}

		/* (non-Javadoc)
		 * @see com.baize.ccms.biz.usermanager.service.ResourceService#deleteResource(int)
		 */
	public void deleteResource(String id) {
		// TODO Auto-generated method stub
		resourceDao.deleteByKey(id);
	}

		/* (non-Javadoc)
		 * @see com.baize.ccms.biz.usermanager.service.ResourceService#findAll()
		 */
	public List<Resource> findAll() {
		// TODO Auto-generated method stub
		return resourceDao.findAll();
	}

	
	/**
	 * @param resourceDao the resourceDao to set
	 */
	public void setResourceDao(ResourceDao resourceDao) {
		this.resourceDao = resourceDao;
	}



}
