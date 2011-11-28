/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.configmanager.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.baize.ccms.biz.configmanager.dao.FilterCategoryDao;
import com.baize.ccms.biz.configmanager.dao.InformationFilterDao;
import com.baize.ccms.biz.configmanager.domain.FilterCategory;
import com.baize.ccms.biz.configmanager.domain.InformationFilter;
import com.baize.ccms.biz.configmanager.service.InformationFilterService;
import com.baize.ccms.biz.sitemanager.domain.Site;
import com.baize.ccms.biz.usermanager.domain.User;
import com.baize.common.core.dao.Pagination;
import com.baize.common.core.util.SqlUtil;
import com.baize.common.core.util.StringUtil;

/**
 * <p>
 * 标题: —— 要求能简洁地表达出类的功能和职责
 * </p>
 * <p>
 * 描述: —— 简要描述类的职责、实现方式、使用注意事项等
 * </p>
 * <p>
 * 模块: CCMS
 * </p>
 * <p>
 * 版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * </p>
 * <p>
 * 网址：http://www.baizeweb.com
 * 
 * @author 包坤涛
 * @version 1.0
 * @since 2009-9-5 下午02:22:02
 * @history（历次修订内容、修订人、修订时间等） 
 */
 

public class InformationFilterServiceImpl implements InformationFilterService {
	private static final Logger log = Logger
			.getLogger(InformationFilterServiceImpl.class);
	/** 系统设置DAO */
	private InformationFilterDao informationFilterDao;

	/** 系统设置类别dao */
	@SuppressWarnings("unused")
	private FilterCategoryDao filterCategoryDao;

	public InformationFilterDao getInformationFilterDao() {
		return informationFilterDao;
	}

	public FilterCategoryDao getFilterCategoryDao() {
		return filterCategoryDao;
	}

	/**
	 * 根据根节点查找作者数据
	 * 
	 * @param pagination
	 *            分页对象
	 * @param nodeid
	 *            节点ID
	 * @return Pagination 分页对象
	 */
	public Pagination findInformationFilterData(Pagination pagination) {			
		return informationFilterDao.getPagination(pagination);
	}

	/**
	 *  根据根节点查找作者数据
	 * @param pagination      分页对象
	 * @param sessionID       用户类型
	 * @param siteId          栏目类型 
	 * @return Pagination     分页对象
	 */
	public Pagination findInformationSessionIdFilterData(Pagination pagination, String sessionID,String siteId) {
		String[]KeyNumbers={"sessionID","siteId"};
		String[] keyvalues = { sessionID, siteId};
		return  informationFilterDao.getPagination(pagination,KeyNumbers,keyvalues);	
	}
	/**添加过滤条件*/   
	
	public String addinformationFilterService(InformationFilter informationFilter ,String siteId,String sessionID){
		String message = "0";
		/**sessionID用户*/
		/**siteId 栏目*/
		String field1=informationFilter.getField1();
		String[] keyNumbers = {"sessionID" ,"siteId","field1"};
		/**用户, 栏目,值*/  
		String[] keyvalues = { sessionID, siteId,field1};
		List list=informationFilterDao.findByNamedQuery("findInformationFilterCount",keyNumbers, keyvalues);
		
		String replaceField1 = informationFilter.getReplaceField1();
		String[] keyNumbers1 = {"sessionID","siteId","replaceField1"};
		String[] keyvalues1 = {sessionID,siteId,replaceField1};
		List list1 = informationFilterDao.findByNamedQuery("findInformationFilterCount1",keyNumbers1, keyvalues1);
		
		String field2 = informationFilter.getField2();
		String[] keyNumbers2 = {"sessionID","siteId","field1","field2"};
		String[] keyvalues2 = {sessionID,siteId,field1,field2};
		List list2 = informationFilterDao.findByNamedQuery("findInformationFilterCount2", keyNumbers2, keyvalues2);
		
		if(list.size()>0 && list1.size()>0){  
		  return message; 
		}
		if(list2.size()>0){
			message = "2";
			return message;
		}
   		/** 过滤类型*/
		FilterCategory filterCategory = new FilterCategory();
		filterCategory.setId(informationFilter.getFilterCategory().getId());
		informationFilter.setFilterCategory(filterCategory);
	
		if(informationFilter.getFilterCategory().getId().equals("5")){
			informationFilter.setField1("编辑");
		}
		
		//用户类型
		User  user=new User();
		user.setId(sessionID);
		informationFilter.setUser(user);
		//栏目类型
		Site site=new Site();
		site.setId(siteId);
		informationFilter.setSite(site);
		informationFilter.setCreateTime(new Date());
		message = "1";
		informationFilterDao.save(informationFilter);
		return message;
	}
	
	public String addinformationFilterService(
			InformationFilter informationFilter) {
	    	String message = "0";
	//	if (categoryId != null && !categoryId.equals("")) {
			FilterCategory filterCategory = new FilterCategory();
    		//	informationFilter.filterCategoryId
			//filterCategory.setId(informationFilter.getFilterCategoryId());
			filterCategory.setId(informationFilter.getFilterCategory().getId());
			informationFilter.setCreateTime(new Date());
			informationFilter.setFilterCategory(filterCategory);
			message = "1";
			informationFilterDao.save(informationFilter);
			return message;
	}

	/**
	 * 根据根过滤对象修改属性
	 * 
	 * @param informationFilter
	 *            过滤对象
	 */

	public void modifyInformationFilterServic(
			InformationFilter informationFilter) {

	//	System.out.println(informationFilter.getId());
		InformationFilter infoFilt = informationFilterDao
				.getAndClear(informationFilter.getId());
		if (informationFilter.getField1() != null) {
			if (!informationFilter.getField1().equals("")) {
				infoFilt.setField1(informationFilter.getField1());
			}else{
				infoFilt.setField1("");
			}

		}else{
			infoFilt.setField1("编辑");
		}
		if (informationFilter.getField2() != null) {
			if (!informationFilter.getField2().equals("")) {
				infoFilt.setField2(informationFilter.getField2());
			}else{
				infoFilt.setField2("");
			}
		}else{
			infoFilt.setField2("");
		}
		if (informationFilter.getReplaceField1() != null) {
			if (!informationFilter.getReplaceField1().equals("")) {
				infoFilt.setReplaceField1(informationFilter.getReplaceField1());
			}else{
				infoFilt.setReplaceField1("");
			}
		}else{
			infoFilt.setReplaceField1("");
		}

		if (informationFilter.getReplaceField2() != null) {
			if (!informationFilter.getReplaceField2().equals("")) {
				infoFilt.setReplaceField2(informationFilter.getReplaceField2());
			}else{
				infoFilt.setReplaceField2("");
			}

		}else{
			infoFilt.setReplaceField2("");
		}
		if (informationFilter.getStatus() != null) {
			if (!informationFilter.getStatus().equals("")) {
				infoFilt.setStatus((Boolean) informationFilter.getStatus());
			}
		}
		if (null != informationFilter.getFilterCategory().getId()
				|| (!informationFilter.getFilterCategory().getId().equals(""))) {
         
			FilterCategory  filterCategory=new FilterCategory();
			filterCategory.setId(informationFilter.getFilterCategory().getId());
			infoFilt.setFilterCategory(filterCategory);
		}
		
		
		if(informationFilter.getFilterCategory().getId().equals("5")){
			infoFilt.setField1("编辑");
		}
		informationFilterDao.update(infoFilt);
	}

	// 跟据id获取需要修改的对象
	public InformationFilter findInformationFilterByKey(String Id) {

		return informationFilterDao.getAndNonClear(Id);
	}

	/**
	 * 根据根节点删除系统数据对象
	 * 
	 * @param id
	 *            系统数据唯一编号
	 * @return
	 */
	public void deleteInformationFilterService(String authorId) {
		informationFilterDao.deleteByIds(SqlUtil.toSqlString(authorId));
	}

	public void setInformationFilterDao(
			InformationFilterDao informationFilterDao) {
		this.informationFilterDao = informationFilterDao;
	}

	public void setFilterCategoryDao(FilterCategoryDao filterCategoryDao) {
		this.filterCategoryDao = filterCategoryDao;
	}

	
}
