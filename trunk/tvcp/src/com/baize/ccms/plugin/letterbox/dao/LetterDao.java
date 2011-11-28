/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.plugin.letterbox.dao;

import com.baize.ccms.plugin.letterbox.domain.Letter;
import com.baize.common.core.dao.GenericDao;
/**
 * <p>标题: 信件的Dao</p>
 * <p>描述: 信件的Dao并继承GenericDao</p>
 * <p>模块: 信件箱</p>
 * <p>版权: Copyright (c) 2009南京百泽网络科技有限公司
 * @author 杨信
 * @version 1.0
 * @since 2009-6-14 下午02:20:37 
 * @history（历次修订内容、修订人、修订时间等） 
*/

public interface LetterDao extends GenericDao<Letter, String> {

}
