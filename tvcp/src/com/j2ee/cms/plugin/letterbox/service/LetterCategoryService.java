/**
 * project：通用内容管理系统
 * Company:   
*/
package com.j2ee.cms.plugin.letterbox.service;

import java.util.List;

import com.j2ee.cms.plugin.letterbox.domain.LetterCategory;
import com.j2ee.cms.common.core.dao.Pagination;

/**
 * <p>标题: 信件类别最高层接口</p>
 * <p>描述: 信件类别表最高层接口</p>
 * <p>模块: 信件箱</p>
 * <p>版权: Copyright (c) 2009  
 * @author 杨信
 * @version 1.0
 * @since 2009-6-13 下午03:49:56 
 * @history（历次修订内容、修订人、修订时间等） 
*/

public interface LetterCategoryService {

	/**
	 * 添加办件类别
	 * @param name 办件类别名称
	 * @return void
	 */
	public void addLetterCategory(String name);
	
	/**
	 * 分页显示办件类别
	 * @param pagination 分页对象
	 * @return Pagination
	 */
	public Pagination findLetterCategory(Pagination pagination);
	
	/**
	 * 删除办件类别
	 * @param ids 办件类别id
	 * @return String
	 */
	public String deleteLetterCategory(String ids);
	
	/**
	 * 修改办件类别
	 * @param id 办件类别id
	 * @return void
	 */
	public void modifyLetterCategory(String id, String name);
	
	/**
	 * 显示所有办件类别
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List findLetterCategoryList();
	
	/**
	 * 显示办件名称
	 * @param id 机构id
	 * @return orgName 机构名称
	 */
	public String findOrgNameById(String id);
	
	/**
	 * 查找信件类别名称
	 * @return categoryName 类别名称
	 */
	public String findCategoryNameStr(); 
	
	/**
	 * 查找信件类别信息
	 * @param id 类别id
	 * @return letterCategory 信件类别
	 */
	public LetterCategory findCategoryDetail(String id);
	
	/**
	 * 修改类别信息
	 * @param id 类别id
	 * @param name 修改后的类别名称
	 * @return 
	 */
	public void modifyCategory(String id, String name);
	
}
