  /**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.biz.usermanager.service;

import java.util.List;
import java.util.Map;

import com.baize.ccms.biz.usermanager.domain.Menu;

/**
 * <p>标题: —— 系统菜单业务逻辑处理.</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 用户管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-2-26 下午06:19:04
 * @history（历次修订内容、修订人、修订时间等） 
 */
public interface MenuService {
/**
 *添加数据.
 *@param menu 菜单对象
 */
void addMenu(Menu menu);

/**
 * 删除数据.
 * @param id 根据主键删除数据
 */
void deleteMenu(String id);

/**
 * 修改数据.
 * @param menu 菜单对象
 */
void modifyMenu(Menu menu);

/**
 * 查询数据.
 * @return List menu对象集合
 */
List findMenu();

/**
 * 查询所有数据.
 * @return List menu对象集合
 */
List<Menu> findAll();

/**
 * 查找数据.
 * @param identifier 唯一标识
 * @return  List menu对象集合
 */
List<Menu> findMenuDataByIdentifier(String identifier);

/**
 * 通过菜单ID集合查找其对应的菜单列表.
 * @param ids 菜单ID集合, 如："1,2,3"
 * @return 菜单列表
 */
List<Menu> findMenuByIds(String ids);

/**
 * 
 * @param id 菜单唯一标识
 * @return  List 
 */
Menu findMenuDataById(String id);

/**
 * 根据用户ID和网站ID获得用户的菜单
 * @param userid  用户的id
 * @param siteId  网站的ID
 * @return
 */
Map findMenuIdsByUserIdAndSiteId(String userid,String siteId);


/**
 * 获得用户的所有菜单
 * @param userid  用户的id
 * @return
 */

public List findAllMenuIds();


/**
 * 修改菜单顺序 
 * @param userid       用户di
 * @param list         获得新的菜单ids
 * @param siteId
 */
void modifyMenuOrder(String userid, String menuIds, String siteId);

}
