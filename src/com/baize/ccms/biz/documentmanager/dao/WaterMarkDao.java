/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.documentmanager.dao;

import com.baize.ccms.biz.documentmanager.domain.Document;
import com.baize.ccms.biz.documentmanager.domain.TextWatermark;
import com.baize.ccms.biz.documentmanager.domain.Watermark;
import com.baize.common.core.dao.GenericDao;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CCMS</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司</p>
 * <p>网址：http://www.baizeweb.com
 * @author 曹名科
 * @version 1.0
 * @since 2009-9-1 下午03:20:04
 * @history（历次修订内容、修订人、修订时间等） 
 */
public interface WaterMarkDao extends GenericDao<TextWatermark, String> {

}
