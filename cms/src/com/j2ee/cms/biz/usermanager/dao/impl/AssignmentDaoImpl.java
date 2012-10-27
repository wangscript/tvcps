  /**
 * project：通用内容管理系统
 * Company:   
*/
package com.j2ee.cms.biz.usermanager.dao.impl;

import com.j2ee.cms.biz.usermanager.dao.AssignmentDao;
import com.j2ee.cms.biz.usermanager.domain.Assignment;
import com.j2ee.cms.common.core.dao.GenericDaoImpl;

/**
 * <p>标题: —— 角色和用户之间分配权限的中间表数据库处理类</p>
 * <p>描述: —— 将用户ID和角色ID拆分成一对多的关系保存到数据库</p>
 * <p>模块: 用户管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-2-25 上午11:15:40
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class AssignmentDaoImpl extends GenericDaoImpl<Assignment,String>  implements AssignmentDao {



}
