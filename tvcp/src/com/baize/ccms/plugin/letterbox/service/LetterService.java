/**
 * project：通用内容管理系统
 * Company:   
*/
package com.j2ee.cms.plugin.letterbox.service;

import java.util.Iterator;
import java.util.List;

import com.j2ee.cms.biz.usermanager.domain.Organization;
import com.j2ee.cms.biz.usermanager.domain.User;
import com.j2ee.cms.plugin.letterbox.domain.Letter;
import com.j2ee.cms.plugin.letterbox.domain.LetterCategory;
import com.j2ee.cms.plugin.letterbox.domain.LetterReply;
import com.j2ee.cms.plugin.letterbox.domain.TransferRecord;
import com.j2ee.cms.common.core.dao.Pagination;

/**
 * <p>标题: 信件最高层接口</p>
 * <p>描述: 信件表的最高层接口</p>
 * <p>模块: 信件箱</p>
 * <p>版权: Copyright (c) 2009  
 * @author 杨信
 * @version 1.0
 * @since 2009-6-14 下午02:28:33 
 * @history（历次修订内容、修订人、修订时间等） 
*/

public interface LetterService {
	
	/**
	 * 创建树的方法
	 * @param treeid          树的节点
	 * @param categoryService 传递一个categoryService对象,以便调用当中的一些方法
	 * @param siteid          网站id
	 * @return object         返回一个list的对象
	 */
    public List<Object> getTreeList(String treeid); 
    
    /**
	 * 分页显示所有机构
	 * @param pagination 分页对象
	 * @return pagination
	 */
    public Pagination findOrganization(Pagination pagination);

	/**
	 * 外网用户写信
	 * @param letter 信件
	 * @param openStr 是否公开
	 * @return void
	 */
	public String addLetter(Letter letter, String openStr); 
	
	/**
	 * @param pagination
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List findOrgList(Pagination pagination);
	
	 /**
	 * 分页显示待处理信件
	 * @param pagination 分页对象
	 * @return pagination
	 */
    public Pagination findWaittingAcceptLetters(Pagination pagination);
    
    /**
	 * 修改信件状态
	 * @param id 信件id
	 * @param key 信件状态
	 * @return void
	 */
    public void modifyLetterStatus(String id, int key);
    
    /**
	 * 分页显示待处理信件
	 * @param pagination 分页对象
	 * @return pagination
	 */
    public Pagination findWaittingDealwithLetters(Pagination pagination);
    
    /**
	 * 分页显示已回复信件
	 * @param pagination 分页对象
	 * @return pagination
	 */
    public Pagination findDoneLetters(Pagination pagination);
    
    /**
	 * 分页显示全部信件
	 * @param pagination 分页对象
	 * @return pagination
	 */
    public Pagination findAllLetters(Pagination pagination);
    
    /**
	 * 处理转办信件
	 * @param toOrgId 要转办机构的id
	 * @param letterId 要转办的信件
	 * @return void
	 */
    public void modifyTransfered(String toOrgId, String letterId);
    
    /**
	 * 在受理列表中修改信件类别
	 * @param ids 类别id
	 * @param letterId 要转办的信件
	 * @return void
	 */
    public void modifyLetterCategory(String ids, String letterId);
    
    /**
	 * 回复信件前的数据处理
	 * @param id 信件id
	 * @return letter
	 */
    public Letter findLetterByKey(String letterId);
    
    /**
	 * 保存已回复信件
	 * @param letterReply 信件
	 * @return void
	 */
    public void saveReplyLetter(LetterReply letterReply);

	/**
	 * @param pagination
	 * @return list
	 */
	public Pagination queryList(Pagination pagination);
	
	/**
	 * 查询转办人
	 * @param id 当前用户id
	 * @return User
	 */
    public User findTransferUserByKey(String userId);
    
    /**
	 * 查询转办后的部门
	 * @param organizationId 部门id
	 * @return organization
	 */
    public Organization findOrganizationByKey(String organizationId);
    
    /**
	 * 保存信件转办记录
	 * @param transferRecord 信件
	 * @return void
	 */
    public void addTransferRecord(TransferRecord transferRecord);

	/** 获取转办前后的部门名称
	 * @param letterId 信件 id
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List findOrgName(String letterId);
	
	/**
	 * 查询信件类别
	 * @param letterId 信件id
	 * @return letterCategory
	 */
    public LetterCategory findLetterCategoryByKey(String letterId);
    
    /** 获取转办记录
	 * @param id 信件 id
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List findTransferRecord(String id);
	
	/** 从待受理列表删除信件
	 * @param ids 信件 id
	 * @return void
	 */
	public void deleteAcceptList(String ids);
	
	/** 从待处理列表删除信件
	 * @param ids 信件 id
	 * @return void
	 */
	public void deleteDealwithList(String ids);
	
	/** 从已处理列表删除信件
	 * @param ids 信件 id
	 * @return void
	 */
	public void deleteDoneList(String ids);
	
	/** 从全部信件列表删除信件
	 * @param ids 信件 id
	 * @return void
	 */
	public void deleteAllList(String ids);
	
	/** 查找部门名字放到要写信件中
	 * @param id 部门 id
	 * @return name 部门名字
	 */
	public String findOrgNameById(Pagination pagination, String id); 
	
	/** 查找信件类别
	 * @param pagination 分页对象
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List findAllCategory(Pagination pagination);
	
	 /**
	 * 根据信件id查找部门
	 * @param letterId 信件id
	 * @return organization
	 */
    public Organization findOrganizationById(String id);
    
    /**
	 * 显示信件类别列表
	 * @param pagination 分页对象
	 * @return pagination
	 */
	public Pagination findCategoryList(Pagination pagination);
	
	/**
	 * 根据信件类别查询信件列表
	 * @param pagination 分页对象
	 * @param categoryName 回执编号
	 * @return pagination
	 */
	public Pagination findLetterByCategory(Pagination pagination, String categoryName);
	
	/**
	 * 根据回执编号查询信件列表
	 * @param pagination 分页对象
	 * @param replyCode 回执编号
	 * @return pagination
	 */
	public Pagination findLetterByReplyCode(Pagination pagination, String replyCode);
	
	/**
	 * 根据信件类别查询信件列表
	 * @param pagination 分页对象
	 * @param ids 信件编号
	 * @param letterStatus 信件状态
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List findUserLetterDetail(Pagination pagination, String ids, int letterStatus);
	
	/**
	 * 查询已回复信件内容
	 * @param id 信件id
	 * @return replyContent
	 */
	public String findReplyContent(String id);
	
	/**
	 * 发布互动信箱目录
	 * @param siteId
	 */
	void publishLetterDir(String siteId);
}
