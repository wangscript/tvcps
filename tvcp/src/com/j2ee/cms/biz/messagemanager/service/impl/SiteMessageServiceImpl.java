/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.messagemanager.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.j2ee.cms.biz.messagemanager.dao.ContactDao;
import com.j2ee.cms.biz.messagemanager.dao.SiteMessageDao;
import com.j2ee.cms.biz.messagemanager.domain.Contact;
import com.j2ee.cms.biz.messagemanager.domain.SiteMessage;
import com.j2ee.cms.biz.messagemanager.service.SiteMessageService;
import com.j2ee.cms.biz.usermanager.dao.AssignmentDao;
import com.j2ee.cms.biz.usermanager.dao.OrganizationDao;
import com.j2ee.cms.biz.usermanager.dao.UserDao;
import com.j2ee.cms.biz.usermanager.domain.User;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.util.SqlUtil;

/**
 * <p>标题: 消息业务类</p>
 * <p>描述: 负责业务中的一些处理</p>
 * <p>模块: 消息管理</p>
 * <p>版权: Copyright (c) 2009 
 * @author 杨信
 * @version 1.0
 * @since 2009-5-18 上午11:01:35 
 * @history（历次修订内容、修订人、修订时间等） 
*/

public class SiteMessageServiceImpl implements SiteMessageService {

	private final Logger log = Logger.getLogger(SiteMessageServiceImpl.class);
	
	/**注入消息的dao**/
	private SiteMessageDao siteMessageDao;
	
	/**注入联系人的dao**/
	private ContactDao contactDao;
	
	/** 用户dao**/
	private UserDao userDao;
	
	/** 机构dao**/
	private OrganizationDao organizationDao;
	
	/** 注入分配角色DAO*/
	private AssignmentDao assignmentDao;
	
	/**
	 * 查询用户列表，当前用户除外
	 * @param id 当前用户id
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List findContacter(String ids) {
		List list = siteMessageDao.findByNamedQuery("findContacterList", "id", ids);
		return list;
	}
	
	/**
	 * 查找用户姓名，被findContacterNameStr(String ids)调用
	 * @param id 用户id
	 * @return 返回用户对象List
	 */
	@SuppressWarnings("unchecked")
	public List findUserName(String id) {
		return siteMessageDao.findByNamedQuery("findUserName", "id", id);
	}
	
	/**
	 * 删除消息
	 * @param ids 消息id
	 * @return void
	 */
	public void deleteMessages(String ids) {
		ids = SqlUtil.toSqlString(ids);
		siteMessageDao.deleteByIds(ids);
	}
	
	/**
	 * 修改读标志
	 * @param ids 消息id
	 * @return void
	 */
	public void modify(String ids) {
		SiteMessage siteMessage = siteMessageDao.getAndNonClear(ids);
		siteMessage.setReaded(true);
		siteMessageDao.update(siteMessage);
		return;
	}
	
	/**
	 * 查看消息内容
	 * @param ids 消息id
	 * @param nodeId 标记是从发件箱(2)还是收件箱(1)查看消息内容
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List findContent(String ids, String nodeId) {
		List list;
		if(nodeId.equals("1"))
			list = siteMessageDao.findByNamedQuery("findReceiveContentById", "id", ids);
		else
			list = siteMessageDao.findByNamedQuery("findSendContentById", "id", ids);
		return list;
	}
	
	/**
	 * 显示收件人姓名字符串，调用findUserName()方法
	 * @param ids 所有收件人的ids
	 * @return 所有收件人姓名组成的字符串
	 */
	@SuppressWarnings("unchecked")
	public String findContacterNameStr(String ids) {
		String[]  str = ids.split(",");	
		  String id = "";
		  String contacterName = "";
		  User user = new User();
		  String orgId = "";
		  List orgList = new ArrayList();
		  String orgName = "";
		  for(int i = 0 ; i < str.length ; i++){
			  id = str[i];
			  user = userDao.getAndNonClear(id);
			  String userName = user.getName();
			  if(user.getOrganization() == null) {
				  orgName = "";
			  } else {
				  orgId = user.getOrganization().getId();
				  orgList = organizationDao.findByNamedQuery("findOrgNameById", "id", orgId);
				  orgName = (String)orgList.get(0);
			  }	  
	//		  log.debug("------------------------------------------------------" + userName + "(" + orgName + ")");
			  contacterName += userName + "(" + orgName + ")" + ",";
//			  
//			  List list = findUserName(id);
//			  String[] nameStr = new String[list.size()];
//			  for(int j = 0 ; j < list.size() ; j++){
//				  Object[] name = (Object[])list.get(j);
//				  if(name != null){
//					  nameStr[j] = String.valueOf(name[1]);
//				  }	
//				  contacterName += nameStr[j] + ",";
//			  }
		  }
		  //去掉末尾的逗号
		  contacterName = contacterName.substring(0, contacterName.length()-1);
		  return contacterName;
	}
	
	/**
	 * 保存发件人、收件人、消息内容
	 * @param ids 所有收件人的ids
	 * @param id 发件人的id
	 * @param message 消息内容
	 * @return infoMessage
	 */
	public String addMessage(String ids, String id, SiteMessage message) {
		String[]  str = ids.split(",");
		String receiverId;
		User receiver = new User();
		User sender = new User();
		Date createTime = new Date();
		
		String infoMessage = "";
		try{
			//设置发件人id
	//		sender.setId(id);
			sender = (User) userDao.findByNamedQuery("findUserById","id",id).get(0);
			//获取消息标题和内容
			String title = message.getTitle();
			String content = message.getContent();
			//保存收件人的消息flag="1"
			for(int i = 0 ; i < str.length ; i++){
				SiteMessage siteMessage = new SiteMessage();
				//设置收到消息标志
				String flag = "1";
				siteMessage.setFlag(flag);
				receiverId = str[i];
				receiver = (User) userDao.findByNamedQuery("findUserById","id",receiverId).get(0);
			//	receiver.setId(receiverId);
				siteMessage.setReceiver(receiver);
				siteMessage.setSender(sender);
				siteMessage.setTitle(title);
				siteMessage.setContent(content);
				siteMessage.setCreateTime(createTime);
				siteMessage.setReaded(false);
				siteMessageDao.save(siteMessage);
			}
			//保存发件人的消息flag="2"
			for(int i = 0 ; i < str.length ; i++){
				SiteMessage siteMessage = new SiteMessage();
				//设置发送消息标志
				String flag = "2";
				siteMessage.setFlag(flag);
				//receiver.setId(str[i]);
				receiver = (User) userDao.findByNamedQuery("findUserById","id",str[i]).get(0);
				siteMessage.setReceiver(receiver);
				siteMessage.setSender(sender);
				siteMessage.setTitle(title);
				siteMessage.setContent(content);
				siteMessage.setCreateTime(createTime);
				siteMessage.setReaded(false);
				siteMessageDao.save(siteMessage);
				infoMessage = "发送成功";
			}
		}catch (Exception e) {
			log.debug("encapsulateField error:", e);
			infoMessage = "发送失败";
		}
		return infoMessage;
	}
	
	/**
	 * 创建树的方法
	 * @param treeid          树的节点
	 * @return object         返回一个list的对象
	 */
    public List<Object> getTreeList(String treeid){
    	log.debug("获取树的操作");
    	List<Object> list = new ArrayList<Object>();
    	// 加载第一级
		if (treeid.equals("0")) {
			//添加发送消息树
			Object[] objects =  new Object[4];
			objects[0] = "sendMessage";
			objects[1] = "发送消息";
			objects[2] = "sitemessage.do?dealMethod=";
			objects[3] = true;
			list.add(objects);
			//添加收件箱树
			objects =  new Object[4];
			objects[0] = "receiveMessageBox";
			objects[1] = "收件箱";
			objects[2] = "sitemessage.do?dealMethod=";
			objects[3] = true;
			list.add(objects);
			//添加发件箱树
			objects =  new Object[4];
			objects[0] = "sendMessageBox";
			objects[1] = "发件箱";
			objects[2] = "sitemessage.do?dealMethod=";
			objects[3] = true;
			list.add(objects);
		}
		return list;
    }

    /**
	 * 查看发件箱消息列表
	 * @param pagination 分页对象
	 * @param ids 发件人id
	 * @return Pagination
	 */
	public Pagination findSendBoxMessage(Pagination pagination,String ids) {
		Pagination page = siteMessageDao.getPagination(pagination, "id", ids);
		return  page;
    }
    
    /**
	 * 查看收件箱消息列表
	 * @param pagination 分页对象
	 * @param ids 收件人id
	 * @return Pagination
	 */
	@SuppressWarnings("unchecked")
	public Pagination findReceiveBoxMessage(Pagination pagination,String ids) {
		Pagination paginations = siteMessageDao.getPagination(pagination, "id", ids);
		List list = paginations.getData();
        for(int i=0 ; i<list.size(); i++){
        	Object[] object = (Object[]) list.get(i);
//        	Date createTime = (Date) object[3];
//        	String dateTime = (DateUtil.toString(createTime)); 
//        	object[3] = dateTime;
        	String str = (String) object[4];
        	if(str.equals("true")){
        		object[4] = "已读";
        	} else
        		object[4] = "<font color='red'>未读</font>";	 
        }
		return  pagination;
	}
	
	/**
	 * 回复消息时查询收件人姓名
	 * @param ids 消息的id
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public String findReplyName(String ids) {
		List list = siteMessageDao.findByNamedQuery("findReplyNameById", "id", ids);
		String contacterName = (String) list.get(0);
		return contacterName;
	}
	
	/**
	 * 回复消息时查询收件人id
	 * @param ids 消息的id
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public String findReplyIds(String ids) {
		List list = siteMessageDao.findByNamedQuery("findReplyIdsById", "id", ids);
		String idStr = String.valueOf(list.get(0));
		return idStr;
	}
	
	/**
	 * 查询未读信息条数
	 * @return 信息条数
	 */
	@SuppressWarnings("unchecked")
	public int findMessages(String ids) {
		int messageNum = 0;
		List list = siteMessageDao.findByNamedQuery("findMessages", "id", ids);
		messageNum = list.size();
		return messageNum;
	}
    
	/**
	 * 查询用户列表，当前用户除外
	 * @param pagination分页对象
	 * @param id 当前用户id
	 * @return Pagination
	 */
	public Pagination findUsers(Pagination pagination, String ids) {
		return siteMessageDao.getPagination(pagination, "id", ids);
	}
	
	/**
	 * 保存常用联系人
	 * @param ids 常用联系人id
	 * @param userId 当前用户id
	 * @return void
	 */
	@SuppressWarnings("unchecked")
	public void saveContacter(String ids, String userId) {
		String receiverId;
		String senderId = userId;
		User receiver = new User();
		User sender = new User();
		
		//查询已存在的常用联系人
		List list = siteMessageDao.findByNamedQuery("findUsefulContacterById", "id", userId);
		//定义已存在的常用联系人的id字符串
		String[] strOfId1 = new String[list.size()];
		for(int j = 0 ; j < list.size() ; j++){
			Object[] name = (Object[])list.get(j);
			if(name != null){
				strOfId1[j] = String.valueOf(name[0]);
			}	
		}
		
		//定义要添加的常用联系人的id字符串
		String[] strOfId2 = ids.split(",");
		//标记是否常用联系人已经存在
		int flags = 0;
		//筛选掉已有的常用联系人
		for(int i = 0; i < strOfId2.length; i++) {
			flags = 0;
			for(int j = 0; j < strOfId1.length; j++) {
				if(strOfId1[j].equalsIgnoreCase(strOfId2[i])){
					flags = 1;	
				} 
			}
			if(flags == 0) {
				Contact contacter = new Contact();
				receiverId = strOfId2[i];
				receiver.setId(receiverId);
				contacter.setReceiver(receiver);
				sender.setId(senderId);  
				contacter.setSender(sender);
				contactDao.save(contacter);
			}
		}
	}
	
	/**
	 * 查找常用联系人,显示在发件箱
	 * @param id 当前用户id
	 * @return userfulContacterStr id和名字组成的字符串
	 */
	@SuppressWarnings("unchecked")
	public String findUsefulContacter(String id) {
		List list = siteMessageDao.findByNamedQuery("findUsefulContacterById", "id", id);
		List orgList = new ArrayList();
		String[] idStr = new String[list.size()];
		String[] nameStr = new String[list.size()];
		String userfulContacterStr = "";
		String strOfId = "";
		String strOfName = "";
		String orgName = "";
		User user = null;
		String orgId = "";
		for(int j = 0 ; j < list.size() ; j++){
			Object[] name = (Object[])list.get(j);
			if(name != null){
				nameStr[j] = String.valueOf(name[1]);
				idStr[j] = String.valueOf(name[0]);
				strOfId += idStr[j] + "^";
				user = userDao.getAndNonClear(idStr[j]);
				if(user.getOrganization() == null) {
					orgName = "";
				} else {
					orgId = user.getOrganization().getId();
					orgList = organizationDao.findByNamedQuery("findOrgNameById", "id", orgId);
					orgName = (String)orgList.get(0);
				}
				strOfName += nameStr[j] + "(" + orgName + ")" + "^";
			}	
		}
		userfulContacterStr = strOfId + "," + strOfName;
		return userfulContacterStr;
	}
	
	/**
	 * 查找所有常用联系人,用于管理
	 * @param pagination 分页对象
	 * @param id 当前用户id
	 * @return Pagination
	 */
	public Pagination findAllUsefulContacter(Pagination pagination,String id) {
		pagination = siteMessageDao.getPagination(pagination, "id", id);
		return pagination;
	}
	
	/**
	 * 删除常用联系人
	 * @param ids 联系人id
	 * @return void
	 */
	public void deleteUsefulContacter(String ids, String id) {
		String[] params = new String[]{"ids", "id"};
		ids = SqlUtil.toSqlString(ids);
		id = SqlUtil.toSqlString(id);
		String[] values = new String[]{ids, id};
		contactDao.updateByDefine("deleteUsefulContacterById", params, values);
	}
	
	/**
	 * 查找机构下的用户
	 * @param orgId 机构Id
	 * @return list
	 */
	
	public List findUserByOrgId(String orgId) {
	   String[]array=orgId.split("s");
		   List arrayList=new ArrayList();
		   List  arrayLists=new ArrayList();		   
		   for(int i=0;i<array.length;i++){
			 if(null!=array[i]){
			 String orgIds=array[i];
			 arrayList =userDao.findByNamedQuery("findUserListByOrgId", "orgId", orgIds);
		     for(int j=0;j<arrayList.size();j++){
		    	 arrayLists.add(arrayList.get(j)) ;  	 
		     }
	    //	List list =userDao.findByNamedQuery("findUserListByOrgId", "orgId", orgId);
			  }
		  }
		return  arrayLists;
	}
	
	/**
	 * 查看消息是否已读
	 * @param id 消息Id
	 * @return readed
	 */
	public String hasReaded(String id) {
		SiteMessage message = siteMessageDao.getAndClear(id);
		String readed = "";
		if(message.isReaded()) {
			readed = "Y";
		} else {
			readed = "N";
		}
		return readed;
	}
	
	/**
	 * 查找系统管理员和网站管理员
	 * @param siteId 网站Id
	 * @return list
	 */
	
	
	@SuppressWarnings("unchecked")
	public List findSysAdminAndSiteAdmin(String siteId) {
		String[] str = {"userName", "siteId"};
		Object[] obj = {"网站管理员", siteId};
		List list1 = userDao.findByNamedQuery("findSiteAdmin", str, obj);
		
		//查询用户id        
		List list = assignmentDao.findByNamedQuery("findUserIdByRoleId", "roleId", "r1");
		if(list == null||list.size()==0){
			return null;
		}
		
		String id = (String)list.get(0); 
		//查询系统管理员
		List list2 = userDao.findByNamedQuery("findSysAdmin", "id", id);
		for(int i = 0; i < list2.size(); i++) {
			Object[] object = (Object[]) list2.get(i);
			object[1] = object[1] + "【系统管理员】";
		}
		list2.add(list1.get(0));
		return list2;
	}
	
    /**
	 * @param SiteMessageDao the siteMessageDao to set
	 */
	public void setSiteMessageDao(SiteMessageDao siteMessageDao) {
		this.siteMessageDao = siteMessageDao;
	}

	/**
	 * @param ContactDao the contactDao to set
	 */
	public void setContactDao(ContactDao contactDao) {
		this.contactDao = contactDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setOrganizationDao(OrganizationDao organizationDao) {
		this.organizationDao = organizationDao;
	}

	public void setAssignmentDao(AssignmentDao assignmentDao) {
		this.assignmentDao = assignmentDao;
	}

	public AssignmentDao getAssignmentDao() {
		return assignmentDao;
	}

}





