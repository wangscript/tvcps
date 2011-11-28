/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.biz.messagemanager.service;

import java.util.List;

import com.baize.ccms.biz.messagemanager.domain.SiteMessage;
import com.baize.common.core.dao.Pagination;

/**
 * <p>标题: 消息最高层接口</p>
 * <p>描述: 消息表最高层接口</p>
 * <p>模块: 消息管理</p>
 * <p>版权: Copyright (c) 2009南京百泽网络科技有限公司
 * @author 杨信
 * @version 1.0
 * @since 2009-5-18 上午10:57:28 
 * @history（历次修订内容、修订人、修订时间等） 
*/

public interface SiteMessageService {
	
	/**
	 * 查询用户列表，当前用户除外
	 * @param id 当前用户id
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	List findContacter(String ids);

	@SuppressWarnings("unchecked")
	List findUserName(String id);	
	
	/**
	 * 显示收件人姓名字符串
	 * @param ids 所有收件人的ids
	 * @return 所有收件人姓名组成的字符串
	 */
	String findContacterNameStr(String ids);
	
	/**
	 * 保存发件人、收件人、消息内容
	 * @param ids 所有收件人的ids
	 * @param id 发件人的id
	 * @param message 消息内容
	 * @return String
	 */
	String addMessage(String ids, String id, SiteMessage message);
	
	/**
	 * 删除消息
	 * @param ids 消息id
	 * @return void
	 */
	void deleteMessages(String ids);
	
	/**
	 * 显示消息内容
	 * @param ids 消息id
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	List findContent(String ids, String nodeId);
	
	/**
	 * 创建树的方法
	 * @param treeid          树的节点
	 * @return object         返回一个list的对象
	 */
    List<Object> getTreeList(String treeid);
    
    /**
	 * 查看发件箱消息列表
	 * @param pagination 分页对象
	 * @param ids 发件人id
	 * @return Pagination
	 */
	Pagination findSendBoxMessage(Pagination pagination,String ids);
    
	/**
	 * 查看收件箱消息列表
	 * @param pagination 分页对象
	 * @param ids 收件人id
	 * @return Pagination
	 */
	Pagination findReceiveBoxMessage(Pagination pagination,String ids);
	
	/**
	 * 回复消息时查询收件人姓名
	 * @param ids 收件人id
	 * @return List
	 */
	String findReplyName(String ids);
	
	/**
	 * 回复消息时查询收件人id
	 * @param ids 消息的id
	 * @return List
	 */
	String findReplyIds(String ids);
	
	/**
	 * 修改读标志
	 * @param ids 消息id
	 * @return void
	 */
	void modify(String ids);
	
	/**
	 * 查询用户列表，当前用户除外
	 * @param pagination分页对象
	 * @param id 当前用户id
	 * @return Pagination
	 */
	Pagination findUsers(Pagination pagination, String ids);
	
	/**
	 * 查询用户列表，当前用户除外
	 * @param ids 常用联系人id
	 * @param userId 当前用户id
	 * @return void
	 */
	void saveContacter(String ids, String userId);
	
	/**
	 * 查找常用联系人,显示在发件箱
	 * @param id 当前用户id
	 * @return str id和名字组成的字符串
	 */
	String findUsefulContacter(String ids);
	
	/**
	 * 查找所有常用联系人,用于管理
	 * @param pagination 分页对象
	 * @param id 当前用户id
	 * @return Pagination
	 */
	Pagination findAllUsefulContacter(Pagination pagination,String ids);
	
	/**
	 * 删除常用联系人
	 * @param ids 联系人id
	 * @return void
	 */
	void deleteUsefulContacter(String ids, String id);
	
	/**
	 * 查找机构下的用户
	 * @param orgId 机构Id
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	List findUserByOrgId(String orgId);
	
	/**
	 * 查看消息是否已读
	 * @param id 消息Id
	 * @return readed
	 */
	String hasReaded(String id);
	
	/**
	 * 查找系统管理员和网站管理员
	 * @param siteId 网站Id
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	List findSysAdminAndSiteAdmin(String siteId);
}





