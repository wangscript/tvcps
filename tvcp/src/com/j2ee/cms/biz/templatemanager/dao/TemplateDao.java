/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.templatemanager.dao;

import com.j2ee.cms.biz.templatemanager.domain.Template;
import com.j2ee.cms.common.core.dao.GenericDao;

/**
 * <p>标题: 模板dao</p>
 * <p>描述: 主要是继承GenericDao的方法以便提供templateService调用</p>
 * <p>模块: 模板管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-4-9 下午03:28:11
 * @history（历次修订内容、修订人、修订时间等）
 */
public interface TemplateDao extends GenericDao<Template, String> {

}
