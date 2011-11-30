/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.plugin.letterbox.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.j2ee.cms.biz.usermanager.dao.OrganizationDao;
import com.j2ee.cms.plugin.letterbox.dao.LetterCategoryDao;
import com.j2ee.cms.plugin.letterbox.dao.LetterDao;
import com.j2ee.cms.plugin.letterbox.domain.LetterCategory;
import com.j2ee.cms.plugin.letterbox.service.LetterCategoryService;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.util.SqlUtil;

/**
 * <p>标题: 信件类别业务类</p>
 * <p>描述: 负责业务中的一些处理</p>
 * <p>模块: 信件箱</p>
 * <p>版权: Copyright (c) 2009 
 * @author 杨信
 * @version 1.0
 * @since 2009-6-13 下午03:52:44 
 * @history（历次修订内容、修订人、修订时间等） 
*/

public class LetterCategoryServiceImpl implements LetterCategoryService {

	private final Logger log = Logger.getLogger(LetterCategoryServiceImpl.class);
	/** 注入注入办件类别数据访问对象 **/
	private LetterCategoryDao letterCategoryDao;
	/** 注入机构DAO **/
	private OrganizationDao organizationDao;
	/** 注入信件DAO **/
	private LetterDao letterDao;

	/**
	 * @param letterCategoryDao the letterCategoryDao to set
	 */
	public void setLetterCategoryDao(LetterCategoryDao letterCategoryDao) {
		this.letterCategoryDao = letterCategoryDao;
	}

	/**
	 * 添加办件类别
	 * @param name 办件类别名称
	 * @return void
	 */
	public void addLetterCategory(String name) {
		LetterCategory letterCategory = new LetterCategory();
		letterCategory.setName(name);
		letterCategoryDao.save(letterCategory);
		return;
	}
	
	/**
	 * 分页显示办件类别
	 * @param pagination 分页对象
	 * @return Pagination
	 */
	public Pagination findLetterCategory(Pagination pagination) {
		return letterCategoryDao.getPagination(pagination);
	}
	
	/**
	 * 删除办件类别
	 * @param ids 办件类别id
	 * @return String
	 */
	public String deleteLetterCategory(String ids) {
		String[]  str = ids.split(",");	
		String categoryId;
		String message;
		for(int i = 0 ; i < str.length ; i++){
			categoryId = str[i];
			List list = letterDao.findByNamedQuery("findLetterByCategoryId", "id", categoryId);
			if(list.size() > 0) {
				message = "该类别正在使用,不能删除";
				return message;
			}
		}
		ids = SqlUtil.toSqlString(ids);
		letterCategoryDao.deleteByIds(ids);
		message = "删除类别成功";
		return message;
	}
	
	/**
	 * 修改办件类别
	 * @param id 办件类别id
	 * @return void
	 */
	public void modifyLetterCategory(String id, String name) {
		LetterCategory letterCategory = new LetterCategory();
		letterCategory.setId(id);
		letterCategory.setName(name);
		letterCategoryDao.update(letterCategory);
	}
	
	/**
	 * 显示所有办件类别
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List findLetterCategoryList() {
		List list = letterCategoryDao.findAll();
		return list;
	}
	
	/**
	 * 查找部门名称
	 * @param id 机构id
	 * @return orgName 机构名称
	 */
	@SuppressWarnings("unchecked")
	public String findOrgNameById(String id) {
		List list = organizationDao.findByNamedQuery("findOrgNameById", "id", id);
		String orgName = "";
		orgName = (String)list.get(0);
		return orgName;
	}
	
	/**
	 * 查找信件类别名称
	 * @return categoryName 类别名称
	 */
	@SuppressWarnings("unchecked")
	public String findCategoryNameStr() {
		List list = organizationDao.findByNamedQuery("findLetterCategoryName");
		String categoryName = "";
		for(int i = 0; i < list.size(); i++) {
			categoryName += "," + String.valueOf(list.get(i)); 
		}
		return categoryName;
	}

	/**
	 * 查找信件类别信息
	 * @param id 类别id
	 * @return letterCategory 信件类别
	 */
	public LetterCategory findCategoryDetail(String id) {
		LetterCategory letterCategory = letterCategoryDao.getAndNonClear(id);
		return letterCategory;
	}
	
	/**
	 * 修改类别信息
	 * @param id 类别id
	 * @param name 修改后的类别名称
	 * @return 
	 */
	public void modifyCategory(String id, String name) {
		LetterCategory letterCategory = new LetterCategory();
		letterCategory = letterCategoryDao.getAndNonClear(id);
		letterCategory.setName(name);
		letterCategoryDao.update(letterCategory);
	}
	
	/**
	 * @param organizationDao the organizationDao to set
	 */
	public void setOrganizationDao(OrganizationDao organizationDao) {
		this.organizationDao = organizationDao;
	}

	/**
	 * @return the organizationDao
	 */
	public OrganizationDao getOrganizationDao() {
		return organizationDao;
	}

	/**
	 * @param letterDao the letterDao to set
	 */
	public void setLetterDao(LetterDao letterDao) {
		this.letterDao = letterDao;
	}

	/**
	 * @return the letterDao
	 */
	public LetterDao getLetterDao() {
		return letterDao;
	}
}


