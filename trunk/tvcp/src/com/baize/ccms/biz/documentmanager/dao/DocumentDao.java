/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.documentmanager.dao;

import com.baize.ccms.biz.documentmanager.domain.Document;
import com.baize.common.core.dao.GenericDao;

/**
 * <p>标题: 文档的dao</p>
 * <p>描述: 文档的dao并继承GenericDao</p>
 * <p>模块: 文档管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 郑荣华
 * @version 1.0
 * @since 2009-3-23 上午09:31:59
 * @history（历次修订内容、修订人、修订时间等） 
 */
public interface DocumentDao extends GenericDao<Document, String> {

}
