/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.plugin.letterbox.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.j2ee.cms.biz.sitemanager.dao.SiteDao;
import com.j2ee.cms.biz.sitemanager.domain.Site;
import com.j2ee.cms.biz.usermanager.dao.OrganizationDao;
import com.j2ee.cms.biz.usermanager.dao.UserDao;
import com.j2ee.cms.biz.usermanager.domain.Organization;
import com.j2ee.cms.biz.usermanager.domain.User;
import com.j2ee.cms.plugin.letterbox.dao.LetterCategoryDao;
import com.j2ee.cms.plugin.letterbox.dao.LetterDao;
import com.j2ee.cms.plugin.letterbox.dao.LetterReplyDao;
import com.j2ee.cms.plugin.letterbox.dao.TransferRecordDao;
import com.j2ee.cms.plugin.letterbox.domain.Letter;
import com.j2ee.cms.plugin.letterbox.domain.LetterCategory;
import com.j2ee.cms.plugin.letterbox.domain.LetterReply;
import com.j2ee.cms.plugin.letterbox.domain.TransferRecord;
import com.j2ee.cms.plugin.letterbox.service.LetterService;
import com.j2ee.cms.sys.GlobalConfig;
import com.j2ee.cms.sys.ReadSystemXml;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.pager.PageQuery;
import com.j2ee.cms.common.core.util.DateUtil;
import com.j2ee.cms.common.core.util.FileUtil;
import com.j2ee.cms.common.core.util.SqlUtil;
import com.j2ee.cms.common.core.util.StringUtil;


/**
 * <p>标题: 信件的业务类</p>
 * <p>描述: 负责信件的审查,回复等业务</p>
 * <p>模块: 信件箱</p>
 * <p>版权: Copyright (c) 2009 
 * @author 杨信
 * @version 1.0
 * @since 2009-6-14 下午02:30:23 
 * @history（历次修订内容、修订人、修订时间等） 
*/

public class LetterServiceImpl implements LetterService {

	private  JdbcTemplate jdbcTemplate;
	
	private final Logger log = Logger.getLogger(LetterCategoryServiceImpl.class);
	/** 注入信件DAO */
	private LetterDao letterDao;
	
	/** 注入回复信件DAO */
	private LetterReplyDao letterReplyDao;
	
	/** 注入信件类别DAO */
	private LetterCategoryDao letterCategoryDao;
	
	/** 注入网站dao */
	private SiteDao siteDao;
	
	/**
	 * @return the letterCategoryDao
	 */
	public LetterCategoryDao getLetterCategoryDao() {
		return letterCategoryDao;
	}

	/**
	 * @param letterCategoryDao the letterCategoryDao to set
	 */
	public void setLetterCategoryDao(LetterCategoryDao letterCategoryDao) {
		this.letterCategoryDao = letterCategoryDao;
	}

	/** 注入部门DAO */
	private OrganizationDao organizationDao;
	
	/** 注入用户DAO */
	private UserDao userDao;
	
	/** 注入转办记录DAO**/
	private TransferRecordDao transferRecordDao;
	
	/**
	 * @return the userDao
	 */
	public UserDao getUserDao() {
		return userDao;
	}

	/**
	 * @param userDao the userDao to set
	 */
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * 创建树的方法
	 * @param treeid          树的节点
	 * @param categoryService 传递一个categoryService对象,以便调用当中的一些方法
	 * @param siteid          网站id
	 * @return object         返回一个list的对象
	 */
    public List<Object> getTreeList(String treeid) {
    	log.debug("获取树的操作");
    	return ReadSystemXml.getTreeList(treeid, GlobalConfig.functionUnitXmlPath);
    }

    /**
	 * 分页显示所有机构
	 * @param pagination 分页对象
	 * @return pagination
	 */
    public Pagination findOrganization(Pagination pagination) {
    	return organizationDao.getPagination(pagination);
    }
    
    /**
	 * 根据信件id查找部门
	 * @param id 信件id
	 * @return organization
	 */
    public Organization findOrganizationById(String id) {
    	Letter letter = letterDao.getAndNonClear(id);
    	return organizationDao.getAndNonClear(letter.getOrganization().getId());
    }
    
    /**
	 * 外网用户写信
	 * @param letter 信件
	 * @param openStr 是否公开
	 * @return void
	 */
	public String addLetter(Letter letter, String openStr) {
	
		String date = (DateUtil.toString(letter.getSubmitDate())) ;
		//生成回执编号
		int num = (int)(Math.random()*10000);
		String replyCode = date.substring(0, 10) + num;
		String sql = "";
		sql = "INSERT INTO plugin_letterbox_letters " 
			+ "(id, title, content, opened, letterStatus, submitDate, userIP, replyCode, mobileTel, userName, homeTel, residence, email, transfered, letter_category_id, organization_id)"
			+ " VALUES ('" + StringUtil.UUID() + "','" 
			+ letter.getTitle() + "', '" + letter.getContent() + "', " + openStr + ", " + Letter.LETTER_STATUS_UNDEAL + " , '" + date + "', '" + "127.0.0.1', '" + replyCode
			+ "', '" + letter.getMobileTel() + "', '" + letter.getUserName() + "', '" + letter.getHomeTel() + "', '" + letter.getResidence() + "', '" + letter.getEmail() + "'," + "0"  
			+ ", '" + letter.getLetterCategory().getId() + "', '" + letter.getOrganization().getId() + "')";
	
		jdbcTemplate.execute(sql);
		return replyCode;
	}
    
	 /**
	 * 分页显示待受理信件
	 * @param pagination 分页对象
	 * @return pagination
	 */
    public Pagination findWaittingAcceptLetters(Pagination pagination) {
    	return letterDao.getPagination(pagination);
    }
    
    /**
	 * 修改信件状态
	 * @param ids 信件id
	 * @param key 信件状态
	 * @return void
	 */
    public void modifyLetterStatus(String ids, int key) {
    	String[]  str = ids.split(",");	
		for(int i = 0 ; i < str.length ; i++){
			Letter letter = letterDao.getAndNonClear(str[i]);
			letter.setLetterStatus(key);
			letterDao.update(letter);
		}
		return;
    }
    
    /**
	 * 分页显示待处理信件
	 * @param pagination 分页对象
	 * @return pagination
	 */
    public Pagination findWaittingDealwithLetters(Pagination pagination) {
    	return letterDao.getPagination(pagination);
    }
    
    /**
	 * 分页显示已回复信件
	 * @param pagination 分页对象
	 * @return pagination
	 */
    public Pagination findDoneLetters(Pagination pagination) {
    	return letterDao.getPagination(pagination);
    }
    
    /**
	 * 分页显示全部信件
	 * @param pagination 分页对象
	 * @return pagination
	 */
    public Pagination findAllLetters(Pagination pagination) {
    	return letterDao.getPagination(pagination);
    }
    
    /**
	 * 处理转办信件
	 * @param toOrgId 要转办机构的id
	 * @param letterId 要转办的信件
	 * @return void
	 */
    public void modifyTransfered(String toOrgId, String letterId) {
		Letter letter = letterDao.getAndNonClear(letterId);
		Organization organization = new Organization();
		organization.setId(toOrgId);
		letter.setOrganization(organization);
		//设置信件为已转办
		letter.setTransfered(true);
		letterDao.update(letter);
		return;
    }
    
    /**
	 * 在受理列表中修改信件类别
	 * @param id 类别id
	 * @param letterId 要转办的信件
	 * @return void
	 */
    public void modifyLetterCategory(String id, String letterId) {
		Letter letter = letterDao.getAndNonClear(letterId);
		LetterCategory letterCategory = new LetterCategory();
		letterCategory.setId(id);
		letter.setLetterCategory(letterCategory);
		return;
    }
    
    /**
	 * 回复信件前的数据处理
	 * @param id 信件id
	 * @return letter
	 */
    public Letter findLetterByKey(String id) {
		return letterDao.getAndNonClear(id);
	}
    
    /**
	 * 保存已回复信件
	 * @param letterReply 信件
	 * @return void
	 */
    public void saveReplyLetter(LetterReply letterReply) {
    	letterReplyDao.saveOrUpdate(letterReply);
    	return;
	}
    
    /**
     * 查询用户信件列表
	 * @param pagination
	 * @return list
	 */
	public Pagination queryList(Pagination pagination) {
		String sql = "SELECT A.id, replyCode, B.name, title, letterStatus, submitDate "
				   + "FROM plugin_letterbox_letters A, plugin_letterbox_categories B "
				   + "WHERE A.letter_category_id = B.id "
				   + "GROUP BY submitDate ORDER BY submitDate DESC";
		
		pagination.setQueryString(sql);
		Pagination page = PageQuery.getPaginationByQueryString(pagination,jdbcTemplate);
	 	return page;
	}
    
	/**
	 * @param pagination
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List findOrgList(Pagination pagination) {
		String sql = "SELECT id, name FROM organizations WHERE deleted = '0'";
		log.debug("----sql==" + sql);
		List list = jdbcTemplate.queryForList(sql);
		List arrList = new ArrayList();
		for(int i = 0 ; i < list.size() ; i++){
			ListOrderedMap map = (ListOrderedMap)list.get(i);
			Object obj[] = new Object[map.size()];
			for(int j = 0 ; j < map.size(); j++){				
				obj[j] = map.getValue(j);				
			}
			arrList.add(obj);
		}
 
		return arrList;
	}
	
	 /**
	 * 查询转办人
	 * @param id 当前用户id
	 * @return User
	 */
    public User findTransferUserByKey(String userId) {
		return userDao.getAndNonClear(userId);
	}
    
    /**
	 * 查询转办后的部门
	 * @param organizationId 部门id
	 * @return organization
	 */
    public Organization findOrganizationByKey(String organizationId) {
    	return organizationDao.getAndNonClear(organizationId);
    }
    
    /**
	 * 查询信件类别
	 * @param letterId 信件id
	 * @return letterCategory
	 */
    public LetterCategory findLetterCategoryByKey(String letterId) {
    	Letter letter = letterDao.getAndNonClear(letterId);
    	String id = letter.getLetterCategory().getId();
    	return letterCategoryDao.getAndNonClear(id);
    }
    
    /**
	 * 保存信件转办记录
	 * @param transferRecord 信件
	 * @return void
	 */
    public void addTransferRecord(TransferRecord transferRecord) {
    	transferRecordDao.saveOrUpdate(transferRecord);
    	return;
	}
    
    /** 获取转办前后的部门名称
	 * @param letterId 信件 id
	 * @return list
	 */
	@SuppressWarnings({ "unchecked" })
	public List findOrgName(String id) {
		Letter letter = letterDao.getAndNonClear(id);
		//信件是转办版过
		boolean boo = letter.isTransfered();
		List list = new ArrayList();
		if(boo) {
			//从转办记录中查
			List list1 = transferRecordDao.findByNamedQuery("findTransferOrgNameById", "id", id);
			for(int i = 0; i < list1.size(); i++) {
				Object[] object = (Object[]) list1.get(i);
				if(i == 0) {
					list.add(String.valueOf(object[0]));
					list.add(String.valueOf(object[1]));
				} 
				if(i == (list1.size() - 1)) {
					list.add(String.valueOf(object[2]));
					list.add(String.valueOf(object[3]));
				}
			}
		} else {
			//从信件中查
			List list2 = letterDao.findByNamedQuery("findOrganizationNameById", "id", id);
			Object[] name = (Object[])list2.get(0);
			if(name != null){
				list.add(String.valueOf(name[0]));
				list.add(String.valueOf(name[1]));
				list.add(String.valueOf(name[0]));
				list.add(String.valueOf(name[1]));
			}	
		}
		return list;
	}
    
	 /** 获取转办记录
	 * @param id 信件 id
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List findTransferRecord(String id) {
		Letter letter = letterDao.getAndNonClear(id);
		//信件存在转办记录
		boolean boo = letter.isTransfered();
		//从转办记录中查
		List list = transferRecordDao.findByNamedQuery("findTransferRecord", "id", id);
		return list;
	}
	
	/** 从待受理列表删除信件
	 * @param ids 信件 id
	 * @return void
	 */
	public void deleteAcceptList(String ids) {
		ids = SqlUtil.toSqlString(ids);
		letterDao.deleteByIds(ids);
	}
	
	/** 从待处理列表删除信件
	 * @param ids 信件 id
	 * @return void
	 */
	public void deleteDealwithList(String ids) {
		
		String[]  str = ids.split(",");	
    	String id = "";
    	//删除转办记录，如果存在的话
		for(int i = 0 ; i < str.length ; i++){
			Letter letter = letterDao.getAndNonClear(str[i]);
			id = str[i];
			boolean boo = letter.isTransfered();
			if(boo) {
				List list = transferRecordDao.findByNamedQuery("findTransferById", "id", id);
				for(int j = 0; j < list.size(); j++) {
					transferRecordDao.deleteByKey((String) list.get(j));
				}
			}
		}
		//删除信件
		ids = SqlUtil.toSqlString(ids);
		letterDao.deleteByIds(ids);
	}
	
	/** 从已处理列表删除信件
	 * @param ids 信件 id
	 * @return void
	 */
	@SuppressWarnings("unchecked")
	public void deleteDoneList(String ids) {
		String[]  str = ids.split(",");	
    	String id = "";
		for(int i = 0 ; i < str.length ; i++){
			//删除转办记录，如果存在的话
			Letter letter = letterDao.getAndNonClear(str[i]);
			id = str[i];
			boolean boo = letter.isTransfered();
			if(boo) {
				List list = transferRecordDao.findByNamedQuery("findTransferById", "id", id);
				for(int j = 0; j < list.size(); j++) {
					//转办记录id
					transferRecordDao.deleteByKey((String) list.get(j));
				}
			}
			//删除回复部分
			List replyList = letterReplyDao.findByNamedQuery("findReplyById", "id", id);
			//回复id
			for(int j = 0; j < replyList.size(); j++) {
				letterReplyDao.deleteByKey((String) replyList.get(j));
			}
		}
		//删除信件
		ids = SqlUtil.toSqlString(ids);
		letterDao.deleteByIds(ids);
	}
	
	/** 从全部信件列表删除信件
	 * @param ids 信件 id
	 * @return void
	 */
	@SuppressWarnings("unchecked")
	public void deleteAllList(String ids) {
		
		String[]  str = ids.split(",");	
    	String id = "";
		for(int i = 0 ; i < str.length ; i++){
			//删除转办记录，如果存在的话
			Letter letter = letterDao.getAndNonClear(str[i]);
			id = str[i];
			boolean boo = letter.isTransfered();
			if(boo) {
				List list = transferRecordDao.findByNamedQuery("findTransferById", "id", id);
				for(int j = 0; j < list.size(); j++) {
					//转办记录id
					transferRecordDao.deleteByKey((String) list.get(j));
				}
			}
			//删除回复部分,如果已回复的话
			if(letter.getLetterStatus() == Letter.LETTER_STATUS_DEALED) {
				List replyList = letterReplyDao.findByNamedQuery("findReplyById", "id", id);
				//回复id
				for(int j = 0; j < replyList.size(); j++) {
					letterReplyDao.deleteByKey((String) replyList.get(j));
				}
			}
		}
		//删除信件
		ids = SqlUtil.toSqlString(ids);
		letterDao.deleteByIds(ids);
	}
	
	/** 查找部门名字放到要写信件中
	 * @param id 部门 id
	 * @param pagination 分页对象
	 * @return name 部门名字
	 */
	@SuppressWarnings("unchecked")
	public String findOrgNameById(Pagination pagination, String id) {
		String name = "";
		String sql = "SELECT name FROM organizations WHERE id = '" + id + "'";
		
		pagination.setQueryString(sql);
		Pagination page = PageQuery.getPaginationByQueryString(pagination,jdbcTemplate);
		List list = page.getData();
		for(int i = 0; i < list.size(); i++) {
			Object[] object = (Object[]) list.get(i);
			name = String.valueOf(object[0]);
		}
		return name;
	}
	
	/** 查找信件类别
	 * @param pagination 分页对象
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List findAllCategory(Pagination pagination) {
		String sql = "SELECT id, name FROM plugin_letterbox_categories";
		pagination.setQueryString(sql);
		Pagination page = PageQuery.getPaginationByQueryString(pagination,jdbcTemplate);
		List list = page.getData();
		return list;
	}
	
//	/** 查找信件类别
//	 * @param pagination 分页对象
//	 * @return list
//	 */
//	@SuppressWarnings("unchecked")
//	public List findLetterCategory(Pagination pagination) {
//		String sql = "SELECT id, name FROM plugin_letterbox_categories";
//		pagination.setQueryString(sql);
//		Pagination page = PageQuery.getPaginationByQueryString(pagination,jdbcTemplate);
//		List list = page.getData();
//		List categoryList;
//		for(int i = 0; i < list.size(); i++) {
//			Object[] obj = (Object[]) list.get(i);
//			for(int j = 0; j < obj.length; j++) {
//				System.out.println("---------------------" + String.valueOf(obj[j]));
//			}
//		}
//		return list;
//	}
//	
	/**
	 * 显示信件类别列表
	 * @param pagination 分页对象
	 * @return pagination
	 */
	public Pagination findCategoryList(Pagination pagination) {
		return letterCategoryDao.getPagination(pagination);
	}
	
	/**
	 * 根据回执编号查询信件列表
	 * @param pagination 分页对象
	 * @param replyCode 回执编号
	 * @return pagination
	 */
	public Pagination findLetterByReplyCode(Pagination pagination, String replyCode) {
		String sql = "SELECT A.id, replyCode, B.name, title, letterStatus, submitDate "
				   + "FROM plugin_letterbox_letters A, plugin_letterbox_categories B "
				   + "WHERE A.letter_category_id = B.id " 
				   + "AND replyCode LIKE '" + replyCode + "' ";
			   
		
		pagination.setQueryString(sql);
		Pagination page = PageQuery.getPaginationByQueryString(pagination,jdbcTemplate);
		return page;
	}
	
	/**
	 * 根据信件类别查询信件列表
	 * @param pagination 分页对象
	 * @param categoryName 回执编号
	 * @return pagination
	 */
	public Pagination findLetterByCategory(Pagination pagination, String categoryName) {
		String sql="";
		if(categoryName.equals("0")) {
			//所有类别
			sql = "SELECT A.id, replyCode, B.name, title, letterStatus, submitDate "
				+ "FROM plugin_letterbox_letters A, plugin_letterbox_categories B "
				+ "WHERE A.letter_category_id = B.id "
				+ "ORDER BY submitDate DESC";
		} else{
			sql = "SELECT A.id, replyCode, B.name, title, letterStatus, submitDate "
			    + "FROM plugin_letterbox_letters A, plugin_letterbox_categories B "
			    + "WHERE A.letter_category_id = B.id " 
			    + "AND B.id = '" + categoryName + "' ";
			    
		}
		
		pagination.setQueryString(sql);
		Pagination page = PageQuery.getPaginationByQueryString(pagination,jdbcTemplate);
		return page;
	}
	
	/**
	 * 查询用户信件内容
	 * @param pagination 分页对象
	 * @param ids 信件编号
	 * @param letterStatus 信件状态
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List findUserLetterDetail(Pagination pagination, String ids, int letterStatus) {
		String sql;
		List list = new ArrayList();
		sql = "SELECT A.opened, A.title, A.content "
			   + "FROM plugin_letterbox_letters A "
			   + "WHERE A.id = '" + ids + "'";
		
		pagination.setQueryString(sql);
		Pagination page1 = PageQuery.getPaginationByQueryString(pagination,jdbcTemplate);
		List list1 = page1.getData();
		for(int i = 0; i < list1.size(); i++) {
			Object[] object = (Object[])list1.get(i);
			for(int j = 0; j< object.length; j++) {
				list.add(object[j]);
			}
		}
		if(letterStatus == Letter.LETTER_STATUS_DEALED) {
			//信件已回复
			sql = "SELECT B.content, B.replyDate, C.name "
				   + "FROM  plugin_letterbox_replies B, organizations C "
				   + "WHERE B.letter_id = '" + ids + "'" 
				   + " AND B.organization_id = C.id ";
				  
			
			pagination.setQueryString(sql);
			Pagination page2 = PageQuery.getPaginationByQueryString(pagination,jdbcTemplate);
			List list2 = page2.getData();
			
			for(int i = 0; i < list2.size(); i++) {
				Object[] object = (Object[])list2.get(i);
				for(int j = 0; j< object.length; j++) {
					list.add(object[j]);
				}
			}   	 
		} 
		return list;
	}
	
	/**
	 * 查询已回复信件内容
	 * @param id 信件id
	 * @return replyContent
	 */
	public String findReplyContent(String id) {
		List list = letterReplyDao.findByNamedQuery("findReplyContentById", "id", id);
		String replyContent = "";
		for(int i = 0; i < list.size(); i++) {
			replyContent = (String) list.get(0);
		}
		return replyContent;
	}
	
	/**
	 * 发布互动信箱目录
	 * @param siteId
	 */
	public void publishLetterDir(String siteId){
		Site site = siteDao.getAndClear(siteId);
		String publishDir = site.getPublishDir()+"/plugin";
		if(!FileUtil.isExist(publishDir)){
			FileUtil.makeDirs(publishDir);
		}
		String guestBookDir = GlobalConfig.appRealPath + "/plugin/letterbox_manager";
		if(FileUtil.isExist(guestBookDir)){
			if(FileUtil.isExist(publishDir+"/letterbox_manager")){
				FileUtil.delete(publishDir+"/letterbox_manager");
			}
			FileUtil.copy(guestBookDir, publishDir, true);
		}
	}
	
	public void setLetterDao(LetterDao letterDao) {
		this.letterDao = letterDao;
	}

	public LetterDao getLetterDao() {
		return letterDao;
	}
	
	public void setLetterReplyDao(LetterReplyDao letterReplyDao) {
		this.letterReplyDao = letterReplyDao;
	}

	public LetterReplyDao getLetterReplyDao() {
		return letterReplyDao;
	}

	public void setOrganizationDao(OrganizationDao organizationDao) {
		this.organizationDao = organizationDao;
	}

	public OrganizationDao getOrganizationDao() {
		return organizationDao;
	}

	/**
	 * @param jdbcTemplate the jdbcTemplate to set
	 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * @return the jdbcTemplate
	 */
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	/**
	 * @param transferRecordDao the transferRecordDao to set
	 */
	public void setTransferRecordDao(TransferRecordDao transferRecordDao) {
		this.transferRecordDao = transferRecordDao;
	}

	/**
	 * @return the transferRecordDao
	 */
	public TransferRecordDao getTransferRecordDao() {
		return transferRecordDao;
	}

	/**
	 * @param siteDao the siteDao to set
	 */
	public void setSiteDao(SiteDao siteDao) {
		this.siteDao = siteDao;
	}
}