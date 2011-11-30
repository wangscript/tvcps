/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.plugin.letterbox.dao;

import com.j2ee.cms.plugin.letterbox.domain.Letter;
import com.j2ee.cms.common.core.dao.GenericDao;
/**
 * <p>标题: 信件的Dao</p>
 * <p>描述: 信件的Dao并继承GenericDao</p>
 * <p>模块: 信件箱</p>
 * <p>版权: Copyright (c) 2009 
 * @author 杨信
 * @version 1.0
 * @since 2009-6-14 下午02:20:37 
 * @history（历次修订内容、修订人、修订时间等） 
*/

public interface LetterDao extends GenericDao<Letter, String> {

}
