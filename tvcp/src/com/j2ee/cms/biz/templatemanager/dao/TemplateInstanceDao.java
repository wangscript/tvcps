/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.templatemanager.dao;

import com.j2ee.cms.biz.templatemanager.domain.TemplateInstance;
import com.j2ee.cms.common.core.dao.GenericDao;

/**
 * <p>标题: 模板实例的dao</p>
 * <p>描述: 继承GenericDao便于模板实例的调用</p>
 * <p>模块: 模板管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-4-9 下午03:55:50
 * @history（历次修订内容、修订人、修订时间等）
 */
public interface TemplateInstanceDao extends GenericDao<TemplateInstance, String> {

}
