  /**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.columnmanager.service.impl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.log4j.Logger;

import com.j2ee.cms.biz.articlemanager.dao.ArticleDao;
import com.j2ee.cms.biz.articlemanager.dao.ArticleFormatDao;
import com.j2ee.cms.biz.articlemanager.domain.ArticleFormat;
import com.j2ee.cms.biz.columnmanager.dao.ColumnDao;
import com.j2ee.cms.biz.columnmanager.domain.Column;
import com.j2ee.cms.biz.columnmanager.service.ColumnService;
import com.j2ee.cms.biz.columnmanager.web.form.ColumnForm;
import com.j2ee.cms.biz.configmanager.dao.SystemLogDao;
import com.j2ee.cms.biz.publishmanager.service.Publisher;
import com.j2ee.cms.biz.sitemanager.dao.SiteDao;
import com.j2ee.cms.biz.sitemanager.domain.Site;
import com.j2ee.cms.biz.templatemanager.dao.TemplateInstanceDao;
import com.j2ee.cms.biz.templatemanager.domain.TemplateInstance;
import com.j2ee.cms.biz.usermanager.dao.AssignmentDao;
import com.j2ee.cms.biz.usermanager.dao.AuthorityDao;
import com.j2ee.cms.biz.usermanager.dao.OperationDao;
import com.j2ee.cms.biz.usermanager.dao.ResourceDao;
import com.j2ee.cms.biz.usermanager.dao.RightDao;
import com.j2ee.cms.biz.usermanager.dao.UserDao;
import com.j2ee.cms.biz.usermanager.domain.Operation;
import com.j2ee.cms.biz.usermanager.domain.Resource;
import com.j2ee.cms.biz.usermanager.domain.Right;
import com.j2ee.cms.biz.usermanager.domain.User;
import com.j2ee.cms.sys.GlobalConfig;
import com.j2ee.cms.sys.SiteResource;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.util.CollectionUtil;
import com.j2ee.cms.common.core.util.FileUtil;
import com.j2ee.cms.common.core.util.SqlUtil;
import com.j2ee.cms.common.core.util.StringUtil;


/**
 * <p>标题: 栏目业务类</p>
 * <p>描述: 负责业务中的一些处理</p>
 * <p>模块: 栏目管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 郑荣华
 * @version 1.0
 * @since 2009-4-2 下午04:07:22
 * @history（历次修订内容、修订人、修订时间等）
 */
public class ColumnServiceImpl implements ColumnService{
	
	private final Logger log = Logger.getLogger(ColumnServiceImpl.class);
	
	/** 注入栏目数据访问层 */
	private ColumnDao columnDao;
	
	/** 注入权限dao层*/
	private AuthorityDao authorityDao;
	
	/** 注入资源dao层*/
	private ResourceDao resourceDao;
	
	/** 注入网站dao层*/
	private SiteDao siteDao;
	
	/** 注入操作dao层*/
	private OperationDao operationDao;
	
	/** 注入权利dao **/
	private RightDao rightDao;

	/** 注入文章格式dao */
	private ArticleFormatDao articleFormatDao;
	
	/**注入用户dao */
	private UserDao userDao;
	
	/**注入分配dao*/
	private AssignmentDao assignmentDao;
	
	/**注入文章dao*/
	private ArticleDao articleDao;
	
	/** 注入发布机 */
	private Publisher publisher;
	
	/** 注入系统数据库日志dao */
	private SystemLogDao systemLogDao;
	
	/** 注入模板实例数据访问对象 */
	private TemplateInstanceDao templateInstanceDao;
	
	/**
	 * 分页展示当前网站的栏目
	 * @param nodeId   
	 * @param pagination     栏目分页对象
	 * @param siteId		 网站id
	 * @param sessionID		 用户id
	 * @param isUpSystemAdmin
	 * @param isUpSiteAdmin
	 * @return 				栏目分页对象
	 */
	public Pagination findColumnPage(String nodeId, Pagination pagination, String siteId, String sessionID, boolean isUpSystemAdmin, boolean isUpSiteAdmin) {
		List list = this.findColumnTree(siteId, nodeId, sessionID, isUpSystemAdmin, isUpSiteAdmin);
		String columnIds = "";
		for(int i = 0; i < list.size(); i++) {
			Object[] obj = new Object[6];
			obj = (Object[])list.get(i);
			columnIds += "," + String.valueOf(obj[0]);
		}
		columnIds = columnIds.replaceFirst(",", "");
		columnIds = SqlUtil.toSqlString(columnIds);
		List list1 = null;
		if(StringUtil.isEmpty(nodeId) || nodeId.equals("0")){
			pagination = columnDao.getPaginationByIds(pagination, "columnIds", columnIds);	
		}else{
			String[] params = {"nodeId", "columnIds"};
			String[] values = {SqlUtil.toSqlString(nodeId), columnIds};
			pagination = columnDao.getPaginationByIds(pagination, params, values);
		}
		return pagination;
	}
	
	/**
	 * 判断栏目是否达到最大
	 * @param siteId
	 * @param newColumnCount
	 * @return
	 */
	private String getMaxColumn(String siteId, int newColumnCount) {
		String infoMessage = "";
		// 判断是否超出最大建立的栏目数
		if(GlobalConfig.maxColumn != null) {
			int maxColumn = StringUtil.parseInt(GlobalConfig.maxColumn);
			List<Column> columnList = columnDao.findByNamedQuery("findColumnBySiteIdAndDeleted", "siteId", siteId);
			if((columnList.size()+newColumnCount) > maxColumn) {
				infoMessage = "您已经超出能建立的最大栏目数,请与管理员联系";
				return infoMessage;
			}
		} else {
			infoMessage = "您已经超出能建立的最大栏目数,请与管理员联系";
			return infoMessage;
		}
		return infoMessage;
	}
	
	/**
	 * 处理添加栏目
	 * @param separator 分隔符
	 * @param column    栏目对象
	 * @param siteId    网站id
	 * @param sessionID 用户id
	 */
	public String addColumn(String separator, Column column, String siteId, String sessionID, String nodeid,  boolean isUpSystemAdmin, boolean isSiteAdmin) {
		//获得栏目名称字符串
		String columnNameStr = column.getName();		
		String infoMessage = "增加栏目成功";
		//定义一个临时变量 用于防止用分隔符批量添加栏目时column的url重复
		String urlIsNull = "no";
		if(column.getUrl() == null || column.getUrl().equals("")) {
			urlIsNull = "yes";
		}
		
		if(!separator.equals("")) {
			String[] nameStr = columnNameStr.split(separator);
			String newinfo = this.getMaxColumn(siteId, nameStr.length);
			// 判断是否超出最大栏目范围
			if(StringUtil.isEmpty(newinfo)) {
				for(int i = 0; i < nameStr.length; i++) {
					if(nameStr[i] != null && !nameStr[i].equals("")) {
						//获得栏目表中orders最大
						int maxOrders = findMaxColumnOrders();
						column.setName(nameStr[i]);
						column.setOrders(maxOrders + 1);
						column.setChildren(null);
						column.setLinkAddress("column.do?dealMethod=");
						column.setLeaf(true);
						User creator = new User();
						creator.setId(sessionID);
						column.setCreator(creator);
						Site site = new Site();
						site.setId(siteId);
						column.setSite(site);	
						column = getNewColumn(column, nodeid, siteId);
						//防止用分隔符批量添加栏目时column的url重复
						if(urlIsNull.equals("yes")) {
							column.setUrl("");
						}
						addColumn(column);
					}
				}
			} else {
				infoMessage = newinfo;
			}
			
		} else {
			// 判断是否超出最大栏目范围
			String newinfo = this.getMaxColumn(siteId, 1);
			if(StringUtil.isEmpty(newinfo)) {
				//获得栏目表中orders最大
				int maxOrders = findMaxColumnOrders();
				column.setOrders(maxOrders + 1);
				column.setChildren(null);
				column.setLinkAddress("column.do?dealMethod=");
				column.setLeaf(true);
				User creator = new User();
				creator.setId(sessionID);
				column.setCreator(creator);
				Site site = new Site();
				site.setId(siteId);
				column.setSite(site);	
				column = getNewColumn(column, nodeid, siteId);
				addColumn(column);
			} else {
				infoMessage = newinfo;
			}
		}
		return infoMessage;
	}
	
	/**
	 * 添加栏目数据并且处理资源 
	 * @param column     栏目对象
	 */
	public void addColumn(Column column) {
		// 1. 保存栏目
		columnDao.save(column);
		// 添加栏目 的initUrl 
		String columnId = column.getId();
		String siteId = column.getSite().getId();
		String initUrl = SiteResource.getBuildStaticDir(siteId, true) + "/col_"+columnId+"/index.html";
		column.setInitUrl(initUrl);
		if(StringUtil.isEmpty(column.getUrl())){
			column.setUrl(initUrl);
		}
		columnDao.updateAndClear(column); 
		Column c = null;
		if(column.getParent() != null) {
			String parentId = column.getParent().getId();
			c = columnDao.getAndClear(parentId);
			c.setLeaf(false);
			// 修改栏目的叶子
			columnDao.update(c);
		}
		String categoryName = "栏目管理->添加";
		String param = column.getName();
		systemLogDao.addLogData(categoryName, siteId, column.getCreator().getId(), param);
	}

	 /**
	 * 获取栏目ids一起删除栏目
	 * @param strid    		栏目ids
	 * @param siteId   		网站id
	 * @param nodeid   		节点id
	 * @param columnService 栏目业务对象
	 */	
	@SuppressWarnings("unchecked")
	public String deleteColumnids(String ids, String siteId, String nodeid, String sessionID, boolean isUpSystemAdmin, boolean isSiteAdmin) {
		String infoMessage = "删除栏目成功";
		//先判断栏目是否为主站栏目，若是，则删除时要考虑该栏目是否被子站引用
		String[] idstr = ids.split(",");
		Column sonColumn = new Column();
		
		String sonId = "";
		String sonName = "";
		//删除父站栏目，子站引用的栏目变为子站自己的，只是名称被修改了
		for(int k = 0; k < idstr.length; k++) {
			List sonColumnList = columnDao.findByNamedQuery("findSonColumnByParentColumnId", "columnId", idstr[k]);
			for(int m = 0; m < sonColumnList.size(); m++) {
				sonId  = (String) sonColumnList.get(m);
				sonColumn = columnDao.getAndClear(sonId);
				sonName = sonColumn.getName() + "_" + sonColumn.getId();
				sonColumn.setName(sonName);
				sonColumn.setParentSiteColumnId("");
				columnDao.update(sonColumn);
			}
		}
		
		// 根据权限来删除
		// 系统管理员及以上  或者网站管理员
		if(isUpSystemAdmin || isSiteAdmin) {
			String deletedIds = SqlUtil.toSqlString(ids);
			// 将本栏目的deleted设为true
			String[] cparams = {"deleted", "ids"};
			String[] cvalues = {"1", deletedIds};
			columnDao.updateByDefine("updateColumnAsDeleted", cparams, cvalues);
			// 将栏目的父节点的leaf设为true
			if(nodeid != null) {
				if(columnDao.getAndClear(nodeid).getChildren().size() == 0) {
					 // 修改要删除的节点的leaf字段
					 String[] params = {"columnLeaf", "parentId"};
					 boolean leaf = true;
					 Object[] values = {leaf, nodeid};
					 columnDao.bulkUpdate("updateParentLeaf", params, values);
				}
			}
		} else {
			String normalUserColumnIds = "";
			Column column = null;
			String[] strid = ids.split(",");
			for(int i = 0; i < strid.length; i++) {
				column = columnDao.getAndClear(strid[i]);
				if(!StringUtil.isEmpty(normalUserColumnIds)) {
					normalUserColumnIds = normalUserColumnIds.toString() + "," + strid[i];
				} else {
					normalUserColumnIds = strid[i];
				}
				normalUserColumnIds = this.findNormalUserChildColumnId(column, normalUserColumnIds, sessionID, siteId);
			}
			String columnIds = "";
			for(int i = 0; i < strid.length; i++) {
				column = columnDao.getAndClear(strid[i]);
				if(!StringUtil.isEmpty(columnIds)) {
					columnIds += "," + this.findChildColumnId(column, strid[i]);
				} else {
					columnIds = this.findChildColumnId(column, strid[i]);
				}
			}
			log.debug("normalUserColumnIds============"+normalUserColumnIds);
			log.debug("columnIds======================"+columnIds);
			
			// 删除的栏目当中没有管理员的栏目
			if(columnIds.equals(normalUserColumnIds)) {
				String deletedIds = SqlUtil.toSqlString(ids);
				// 将本栏目的deleted设为true
				String[] cparams = {"deleted", "ids"};
				String[] cvalues = {"1", deletedIds};
				columnDao.updateByDefine("updateColumnAsDeleted", cparams, cvalues);
				// 将栏目的父节点的leaf设为true
				if(nodeid != null) {
					if(columnDao.getAndClear(nodeid).getChildren().size() == 0) {
						 // 修改要删除的节点的leaf字段
						 String[] params = {"columnLeaf", "parentId"};
						 boolean leaf = true;
						 Object[] values = {leaf, nodeid};
						 columnDao.bulkUpdate("updateParentLeaf", params, values);
					}
				}
			} else {
				infoMessage = "删除失败，删除的栏目的子栏目中含有管理员的栏目";
			}
		}
		
		//   2.撤稿
		
		/////////////////////////////////////////
		
		
		///////////////////////////////////////
		Column deletedColumn = null;
		String[] str = ids.split(",");
		for(int i = 0; i  < str.length; i++) {
			deletedColumn = columnDao.getAndClear(str[i]);
			// 添加数据库操作日志
			String categoryName = "栏目管理->删除";
			String param = deletedColumn.getName();
			systemLogDao.addLogData(categoryName, siteId, sessionID, param);
		}
		return infoMessage;	
	} 
	
	/**
	 * 递归查找某个栏目的子栏目(普通用户)
	 * @param column        要递归的栏目的对象
	 * @param ids           结果要返回的ids 集合
	 * @return              返回栏目下的所有子节点
	 */
	private String findNormalUserChildColumnId(Column column, String ids, String sessionID, String siteId) {
		String[] params = {"siteId", "columnId", "creatorId"};
		Object[] values = { siteId, column.getId(), sessionID};
		List list = new ArrayList();
		list = columnDao.findByNamedQuery("findColumnBySiteIdAndColumnIdAndCreatorId", params, values);
		if(list != null && list.size() > 0) {
			Column newColumn = null;
			for(int i = 0; i < list.size(); i++) {
				newColumn = (Column) list.get(i);
				if(StringUtil.isEmpty(ids)) {
					ids = newColumn.getId();
				} else {
					ids = ids + "," + newColumn.getId();
				}
				ids = findNormalUserChildColumnId(newColumn, ids, sessionID, siteId);
			}
		}
		return ids;
	}

	/**
	 * 查找栏目表中顺序最大的
	 * @return      返回顺序最大的栏目顺序
	 */
	private int findMaxColumnOrders() {
		int maxOrders = 0;
		List list = columnDao.findByNamedQueryAndClear("findMaxColumnOrders");
		if(!CollectionUtil.isEmpty(list)) {
			if(list.get(0) != null) {
				maxOrders = (Integer) list.get(0);
			} 
		} 
		return maxOrders;
	}

	/**
	 * 修改数据
	 * @param column    		栏目对象
	 * @param parentId       	修改后的栏目父id
	 * @param modifyParentId 	要修改的栏目以往的父id
	 */
	public void modifyColumn(Column column, String parentId, String modifyParentId, boolean isUpSystemAdmin, boolean isSiteAdmin, String siteId, String sessionID) {
		Column newColumn = new Column();
		newColumn = columnDao.getAndClear(column.getId());
		newColumn.setArticleFormat(column.getArticleFormat());
		Column parent = new Column();
		// 如果选择的父节点是没有的，也就是没有父节点
		if(parentId != null) {
			parent.setId(parentId);
			newColumn.setParent(parent);
		} else {
			newColumn.setParent(null);
		}
		newColumn.setSelfShowPage(column.getSelfShowPage());
		newColumn.setShowInFront(column.isShowInFront());
		newColumn.setAudited(column.isAudited());
		newColumn.setDescription(column.getDescription());
		newColumn.setUpdateTime(column.getUpdateTime());
		//修改链接地址
		newColumn.setUrl(column.getUrl());
		//修改信息及图片积分值
		newColumn.setInfoScore(column.getInfoScore());
		newColumn.setPicScore(column.getPicScore());
		newColumn.setSendMenu(column.isSendMenu());
		newColumn.setAllowModify(column.isAllowModify());
		newColumn.setReceiveMenu(column.isReceiveMenu());
		newColumn.setRefColumnIds(column.getRefColumnIds());
		//修改发布方式
		newColumn.setPublishWay(column.getPublishWay());
		//修改定时发布时间
		newColumn.setTimeSetting(column.getTimeSetting());
		
		// 栏目类型
		newColumn.setColumnType(column.getColumnType());
		if(column.getColumnType().equals("single")){
			if(newColumn.getColumnTemplate() != null){
				// 获得实例id
				String id = newColumn.getColumnTemplate().getId();
				TemplateInstance templateInstance = templateInstanceDao.getAndClear(id);
				templateInstance.setUsedNum(templateInstance.getUsedNum()-1);
				templateInstanceDao.update(templateInstance);
			}
			newColumn.setColumnTemplate(null);
		}
		// 如果重新选择了新的父栏目
		String a = parentId;
		String b = modifyParentId;
		if(parentId == null) {
			a = "";
		} 
		if(modifyParentId == null) {
			b = "";
		}
		if(!a.equals(b)) {
			newColumn.setName(column.getName());
			// 获得新栏目
			newColumn = getNewColumn(newColumn, parentId, siteId);
			// 修改新栏目
			columnDao.update(newColumn);
			if(parentId != null) {
				// 修改被选择的栏目的叶子
				Column column1 = columnDao.getAndClear(parentId);
				column1.setLeaf(false);
				columnDao.update(column1);
			}
			// 原来的栏目不是第一级栏目
			if(modifyParentId != null) {
				//查找原来父栏目的信息
				List<Column> columnList =  columnDao.findByNamedQuery("findColumnByColumnId", "columnId", modifyParentId);
				if(columnList.size() > 0) {
					Column newColumnTwo = (Column) columnList.get(0);
					//如果栏目的孩子为0则修改叶子为true
					if(newColumnTwo.getChildren().size() == 0) {
						Column column2 = columnDao.getAndClear(modifyParentId);
						column2.setLeaf(true);
						columnDao.update(column2);
					}
				}
			}
		} else {
			// 修改时栏目名称有改变
			if(!newColumn.getName().equals(column.getName())) {
				newColumn.setName(column.getName());
				// 获得新栏目
				newColumn = getNewColumn(newColumn, parentId, siteId);
			}
			columnDao.update(newColumn);
		}
		if(newColumn.getColumnTemplate() != null) {
			publisher.buildColumnByColumnId(newColumn.getId(), siteId);
		}
		
		// 添加数据库操作日志
		String categoryName = "栏目管理->修改";
		String param = newColumn.getName();
		systemLogDao.addLogData(categoryName, siteId, sessionID, param);
	}
	
	/**
	 * 获得重新定义的栏目对象
	 * @param column
	 * @return
	 */
	private Column getNewColumn(Column newColumn, String parentId, String siteId) {
		List<Column> list = new ArrayList<Column>();
		String name = newColumn.getName();
		// 如果选择的父节点是没有的，也就是没有父节点
		if(parentId != null) {
			Object[] values = {parentId, name};
			String[] params = {"pid", "name"};
			list =  columnDao.findByNamedQueryAndClear("findColumnByColumnNameAndColumnPid", params, values);
			
		// 父节点为0时
		} else {
			Object[] values = {siteId, name};
			String[] params = {"siteId", "name"};
			list =  columnDao.findByNamedQueryAndClear("findFirstColumnBySiteIdAndColumnName", params, values);
		}
		// 如果名字有重复
		if(!CollectionUtil.isEmpty(list)) {
			newColumn.setName(newColumn.getName() + "_" + System.currentTimeMillis());
		}
		return newColumn;
	}
	
	/**
	 * 修改栏目的字段orders
	 * @param column    即将修改的栏目对象
	 */
	public void modifyColumnOrders(Column column) {
		columnDao.update(column);
	}
	
	/**
	 * 根据栏目id获取整条数据
	 * @param id    栏目id
	 * @return      栏目对象
	 */
	public Column findColumnById(String id) {
		return columnDao.getAndNonClear(id);
	}		
	
	/**
	 * 按照网站id或者栏目id查找栏目树(根据不同的角色来判断显示内容)
	 * @param siteId 	    网站id
	 * @param columnId 	    栏目id
	 * @param sessionId     当前登陆用户的id
	 * @return			    返回List
	 */
	public List findColumnTree(String siteId, String columnId, String userId, boolean isUpSystemAdmin, boolean isUpSiteAdmin) {
		List columnList = new ArrayList();
		
		//根据栏目类型，栏目ID，用户ID，查询权力表,显示选择的操作
		String chooseColumn = "";
 
		String strColumnId = "";
		if(columnId == null || columnId.equals("0")){
			strColumnId = "0";
			columnList = columnDao.findByNamedQuery("findFirstColumnBySystemAdmin","siteId",siteId);
		}else{
			strColumnId = columnId;
			String[] params1 = {"siteId", "columnId"};
			Object[] values1 ={siteId, strColumnId};
			columnList = columnDao.findByNamedQuery("findColumnBySiteIdAndColumnId", params1, values1);
		}
		//查询所有单独设置权限的栏目ID
		String rightStr[] = {"siteId","resourceType","operationTypes"};
		Object rightObj[] = {siteId,Resource.TYPE_COLUMN,Operation.TYPE_COLUMN};
		List rightList = rightDao.findByNamedQuery("findRightBySiteIdAndResourceTypeAndOperationTypes",rightStr,rightObj);
		Right right = null;	
		String setColumnIds = "";
		StringBuffer columnids = new StringBuffer();
		for(int i = 0 ; i < rightList.size() ; i++){
			right = (Right)rightList.get(i);
			this.getChildColumnId(columnids, right.getAuthority().getResource().getIdentifier(), siteId);	 
		}
		setColumnIds = columnids.toString();
		setColumnIds = StringUtil.clearRepeat(setColumnIds);
		setColumnIds = StringUtil.replaceFirst(setColumnIds, ",");
		Column column = null;
		for(int i = 0 ; i < columnList.size() ; i++){
			column = (Column)columnList.get(i);
			if(isUpSiteAdmin){
				chooseColumn = chooseColumn + "," +SqlUtil.toSqlString(column.getId());
			}else{
				List list = this.getTreeCheckBoxChooseObject(column.getId(), userId, siteId, Operation.TYPE_COLUMN);
				
				if(list != null && list.size() > 0){
					chooseColumn = chooseColumn + "," +SqlUtil.toSqlString(column.getId());
				}else if(StringUtil.contains(setColumnIds, column.getId())){
					if(chooseColumn != null && !chooseColumn.equals("")){
						chooseColumn = chooseColumn + "," +SqlUtil.toSqlString(column.getId());
					}else{
						chooseColumn = SqlUtil.toSqlString(column.getId());
					}
					
				}
			}
		
		}
		
		log.debug("chooseColumn===========111111111111===="+chooseColumn);
		chooseColumn = StringUtil.replaceFirst(chooseColumn, ",");
		//用户自己建的栏目集合
		List selfList = new ArrayList();
		if(!StringUtil.isEmpty(strColumnId) && !strColumnId.equals("0")) {
			String[] params = {"siteId", "creatorId", "columnId"};
			Object[] values = {siteId, userId, strColumnId};
			selfList = columnDao.findByNamedQuery("findSelfColumnByNormalUser", params, values);
		} else {
			String[] params = {"siteId", "creatorId"};
			Object[] values = {siteId, userId};
			selfList = columnDao.findByNamedQuery("findSelfColumnByNormalUserAndPidIsNull", params, values);
		}
		String sefId = "";
		Object obj[] = null;
		
		for(int i = 0; i < selfList.size(); i++) {
			obj = (Object[])selfList.get(i);
			if(obj != null){
				chooseColumn = chooseColumn + "," + SqlUtil.toSqlString(String.valueOf(obj[0]));
			}			
		}
		chooseColumn = StringUtil.replaceFirst(chooseColumn, ",");
		chooseColumn =  StringUtil.clearRepeat(chooseColumn); 
		chooseColumn = StringUtil.replaceFirst(chooseColumn, ",");
		//用户拥有权限的栏目集合
	//	List list1 = this.getTreeList2(chooseColumn, columnId, userId, siteId, isUpSiteAdmin);
		log.debug("chooseColumn==========222222222222====="+chooseColumn);
		List list1 = new ArrayList();
		if(chooseColumn != null && !chooseColumn.equals("")){
			list1 = columnDao.findByDefine("findColumnTreeByColumnIds", "columnIds", chooseColumn);
		}
		
		return list1; 
	}	
	
	/**
	 * 获取treecheckbox标签所需要的已选择数组对象
	 * @param columnId 栏目ID
	 * @param userId  用户ID
	 * @param siteId 网站ID
	 * @param operationType 操作类型
	 * @param rootId 一个treecheckbox根节点的ID
	 * @param rootName 一个treecheckbox根节点的名称 
	 * @return List 组装好的LIST数据
	 */
	private List getTreeCheckBoxChooseObject(String columnId, String userId, String siteId,String operationType){
		Map map = new HashMap();
		Right right = null;
		Object tempObj[] = null;
		List tempList = new ArrayList();
		String str[] = {"itemType","itemId","userId","siteId","types"};
		Object obj[] = {Resource.TYPE_COLUMN,columnId,userId,siteId,operationType};
		List dataList = rightDao.findByNamedQuery("findRightByUserIdAndItemIdAndItemTypeAndSiteIdAndOperationType",str,obj);
		Object obj2[] = {Resource.TYPE_COLUMN,columnId,userId,siteId,Operation.TYPE_COLUMN_NONE};
		List nodeDataList = rightDao.findByNamedQuery("findRightByUserIdAndItemIdAndItemTypeAndSiteIdAndOperationType",str,obj2);
		Object allDataObj[] = {Resource.TYPE_COLUMN,columnId,userId,siteId,Operation.TYPE_COLUMN_NONE};
		List allDataList = rightDao.findByNamedQuery("findRightByUserIdAndItemIdAndItemTypeAndSiteIdAndOperationNoneType",str,allDataObj);

		StringBuffer columnids = new StringBuffer();
		//先把自己加进去
		columnids.append(columnId);
		this.getChildColumnId(columnids, columnId, siteId);
		String columnIds = columnids.toString();		
		if(StringUtil.contains(columnIds, ",0")){
			columnIds = columnIds.replace(",0", "");
			columnIds = columnIds + ",0";
		}
		
		columnIds = StringUtil.replaceFirst(columnIds,",");
		 
		String strColumnIds[] = columnIds.split(",");
		boolean columnExtends = false;
		//如果对此栏目单独设置了权限
		if(dataList != null && dataList.size() > 0){
			for(int i = 0 ; i < dataList.size() ; i++){
				right = (Right)dataList.get(i);			 
				if(!columnExtends){
					columnExtends = right.getAuthority().isColumnExtends();					
				}				
				tempList.add(right);
			}
		}else if(nodeDataList != null && nodeDataList.size() > 0){
			//如果对此栏目设置了无然后操作权限
			tempList = new ArrayList();
		}else if(allDataList == null || allDataList.size() == 0){
			//如果没有对此栏目单独设置了权限，则查询他的上级栏目有没有设置下级权限继承上级权限的
			List newDataList = null;
			
			for(int i = 0 ; ((i < strColumnIds.length) && (newDataList == null)) ; i++){
				String str1[] = {"itemType","itemId","userId","siteId","types"};
				Object obj1[] = {Resource.TYPE_COLUMN,strColumnIds[i],userId,siteId,operationType};
				List rightDataList = rightDao.findByNamedQuery("findRightByUserIdAndItemIdAndItemTypeAndSiteIdAndOperationType",str1,obj1);
				
				Object allDataObj1[] = {Resource.TYPE_COLUMN,strColumnIds[i],userId,siteId,Operation.TYPE_COLUMN_NONE};
				List allDataList1 = rightDao.findByNamedQuery("findRightByUserIdAndItemIdAndItemTypeAndSiteIdAndOperationNoneType",str1,allDataObj1);
 
				if(rightDataList != null && rightDataList.size() > 0 ){
					//如果对这个栏目单独设置了权限
					for(int j = 0 ; ((j < rightDataList.size()) && (newDataList == null)); j++){
						right = (Right)rightDataList.get(j);
						if((right.getAuthority() != null) && (right.getAuthority().getId() != null) && !(right.getAuthority().isColumnExtends())){
							newDataList = new ArrayList();
						}
						boolean setChild = right.getAuthority().isColumnExtends();
						if(setChild){
							newDataList = rightDataList;
						}
					}
				}else if(allDataList1 != null && allDataList1.size() > 0){
					//如果对这个栏目单独设置了权限
					for(int j = 0 ; ((j < allDataList1.size()) && (newDataList == null)); j++){
						right = (Right)allDataList1.get(j);
						if((right.getAuthority() != null) && (right.getAuthority().getId() != null) && !(right.getAuthority().isColumnExtends())){
							newDataList = new ArrayList();
						}
						boolean setChild = right.getAuthority().isColumnExtends();
						if(setChild){
							//则没有对这个栏目设置权限，对文章或者模板设置设置了权限
							newDataList = allDataList1;
						}
					}
				
				}
				
				Object obj3[] = {Resource.TYPE_COLUMN,strColumnIds[i],userId,siteId,Operation.TYPE_COLUMN_NONE};
				List nodeRightDataList = rightDao.findByNamedQuery("findRightByUserIdAndItemIdAndItemTypeAndSiteIdAndOperationType",str1,obj3);
				if(newDataList == null && nodeRightDataList != null && nodeRightDataList.size() > 0){
					newDataList = new ArrayList();
				}
			}
			if(newDataList != null){
				for(int i = 0 ; i < newDataList.size() ; i++){
					right = (Right)newDataList.get(i);
				 
					tempList.add(right);
				}
			} 
		}
 
 
		return tempList;
	}
	/**
	 * 递归获取这个栏目的父栏目ID
	 * @param columnList 第一级栏目的集合
	 * @param columnids 栏目ID集合
	 * @return 返回所有的子栏目ID集合，以逗号隔开
	 */
	private void getChildColumnId(StringBuffer columnids, String columnId,String siteId){	
		String str[] = {"siteId","columnId"};
		Object obj[] = {siteId,columnId};
		List columnList = columnDao.findByNamedQuery("findColumnBySiteIdAndCurColumnId", str, obj);
		Column column = null;
		if (columnList != null && columnList.size() > 0) {		 
			column = (Column)columnList.get(0);
			if(column.getParent() != null ){
				String columnid = column.getParent().getId();
				columnids.append("," + columnid);
				// 递归
				getChildColumnId(columnids, columnid,siteId);							
			}else{
				columnids.append(",0");
			}
		}else{
			columnids.append(",0");
		}	
	}
	
	/**
	 * 递归获取所有的子栏目ID
	 * @param columnList 第一级栏目的集合
	 * @param columnids 栏目ID集合
	 * @return 返回所有的子栏目ID集合，以逗号隔开
	 */
	private void getColumnId(StringBuffer columnids, String columnId,String siteId){	
		String str[] = {"pid","siteId"};
		Object obj[] = {columnId,siteId};
		List columnList = columnDao.findByNamedQuery("findColumnByParentIdAndSiteId", str, obj);
		Column column = null;
		if (columnList != null && columnList.size() > 0) {
			for (int i = 0 ; i < columnList.size(); i++ ) {
			//	Object[] obj = (Object[])columnList.get(i);
			//	for(int j = 0 ; j < obj.length ; j++){
					column = (Column)columnList.get(i);
					if(column != null && column.getId() != null){
						String colId = column.getId();
						String columnid = column.getId();			
						columnids.append("," + columnid);
						// 递归
						getColumnId(columnids, colId,siteId);							
					}		
			//	}
					
			}
		}
	}
	/**
	 * 按照网站id或者栏目id查找栏目树
	 * @param siteId 	 网站id
	 * @param columnId 	 栏目id
	 * @return			 返回List
	 */
	public List findParentSiteColumnTreeBySiteIdAndColumnId(String siteId, String columnId) {
		List columnList = new ArrayList();
		if(columnId == null) { 
			columnList = columnDao.findByNamedQuery("findColumnTreeBySiteId", "siteId", siteId);
			
		} else {
			columnList = columnDao.findByNamedQuery("findColumnTreeByColumnId", "columnId", columnId);
		}
		return columnList;
	}
	
	/**
	 * 查找父网站栏目并分页显示
	 * @param pagination   网站分页对象
	 * @param siteid  	   网站id
	 * @return		  	   返回父网站下的栏目信息	
	 */
	public Pagination findParentSiteColumnPage(Pagination pagination, String siteId) {
		//调用网站dao查询父网站id
		String parentSiteId = (String) siteDao.findByNamedQuery("findParentSite", "siteId", siteId).get(0);
		log.debug("查找出的父网站id");
		pagination = columnDao.getPagination(pagination, "siteId", parentSiteId);
		return pagination;
	}
	
	/**
	 * 查询父网站id
	 * @param siteId 网站id
	 */
	public String findParentSiteId(String siteId) {
		return (String) siteDao.findByNamedQuery("findParentSite", "siteId", siteId).get(0); 
	}
	
	/**
	 * 栏目排序
	 * @param ordersColumn    要排序的栏目ids
	 * @param columnService   栏目业务对象
	 */
	public void orderColumn(String ordersColumn, ColumnService columnService, String siteId, String sessionID) {
		log.debug("栏目排序");
		if(!StringUtil.isEmpty(ordersColumn)) {
			String[] orders = ordersColumn.split(",");
			List list = new ArrayList();
			int[] ordersArray = new int[orders.length];
			Column column = null;
			// 获取栏目的顺序
			for(int i = 0; i < orders.length; i++) {
				String columnId = orders[i].toString();
				column = columnDao.getAndClear(columnId);
				ordersArray[i] = column.getOrders();
			}
			//对orders值进行从排序
			for(int i = 0; i < ordersArray.length; i++) {
				for(int j = 0; j < ordersArray.length-i-1; j++ ) {
					if(ordersArray[j] > ordersArray[j+1]) {
						int temp = ordersArray[j];
						ordersArray[j] = ordersArray[j+1];
						ordersArray[j+1] = temp;
					}
				}
			}
			//修改排序后的orders值
			for(int i = 0; i < orders.length; i++) {
				String columnId = orders[i].toString();
				column = columnDao.getAndClear(columnId);
				column.setOrders(ordersArray[i]);
				columnService.modifyColumnOrders(column);
			}
		}
		
		// 添加数据库操作日志
		String categoryName = "栏目管理->排序";
		systemLogDao.addLogData(categoryName, siteId, sessionID, "");
	}
	
	/**		
	 * 根据栏目的节点查找栏目的子节点
	 * @param parentid 	    栏目的父节点
	 * @param siteId      	网站id
	 * @param sessionID     当前登陆用户的id
	 * @param columnService 栏目业务对象
	 * @return 			    返回栏目列表
	 */
	public List<Column> findChildColumn(ColumnForm form, String parentid, String siteId, String sessionID, boolean isUpSystemAdmin, boolean isSiteAdmin, ColumnService columnService){
		log.debug("查找子栏目");
		int start = form.getFromCount();
		int end = form.getToCount();
		if(start == 0 && end == 0) {
			start = 0;
			end = 50;
			form.setFromCount(start);
			form.setToCount(end);
		}
		List<Column> list = new ArrayList<Column>();
		if(parentid == null) {
			// 如果是超级管理员，查询所有数据
			if(isUpSystemAdmin) {
				log.debug("超级管理员");
				list = columnDao.findByNamedQuery("findFirstColumnBySystemAdmin", "siteId", siteId, start, end);
			
			// 如果是系统管理员，需要根据网站ID查询
			} else  if(isSiteAdmin) {
				log.debug("系统管理员");
				list = columnDao.findByNamedQuery("findFirstColumnBySystemAdmin", "siteId", siteId, start, end);
			
			// 如果是普通用户，需要根据用户ID和网站ID查询
			} else {
				log.debug("普通用户");
				String[] params = {"siteId", "creatorId"};
				Object[] values = {siteId, sessionID};
				list = columnDao.findByNamedQuery("findFirstColumnByNormalUser", params, values, start, end);
			}
		} else {
			// 如果是系统管理员，按照栏目id查询所有数据
			if(isUpSystemAdmin) {
				String[] params = {"siteId", "columnId"};
				Object[] values ={siteId, parentid};
				list = columnDao.findByNamedQuery("findColumnOrdersHasParentBySiteIdAndColumnId", params, values, start, end);
			
			// 如果是网站管理员，需要根据网站ID和栏目id查询
			} else  if(isSiteAdmin) {
				String[] params = {"siteId", "columnId"};
				Object[] values ={siteId, parentid};
				list = columnDao.findByNamedQuery("findColumnOrdersHasParentBySiteIdAndColumnId", params, values, start, end);
			
			// 如果是普通用户，需要根据栏目id和用户ID以及网站ID查询
			} else {
				String[] params = {"siteId", "columnId", "creatorId"};
				Object[] values ={siteId, parentid, sessionID};
				list = columnDao.findByNamedQuery("findColumnOrdersHasParentBySiteIdAndColumnIdAndCreatorId", params, values, start, end);
			}
		}
		return list;
	}

	/**
	 * 处理栏目的粘贴
	 * @param nodeid		  要复制到的位置id
	 * @param pasteIds 	      要复制的栏目id
	 * @param siteId          网站id
	 * @param creatorid       创建者id
	 * @param needchild       是否需要子节点
	 * @param columnService   栏目业务对象
	 */
	public String pasteColumn(String nodeid, String pasteIds, String siteId, String creatorid, int needchild, int isCopy, boolean isUpSystemAdmin, boolean isSiteAdmin, ColumnService columnService) {
		log.debug("处理粘贴栏目");	
		String pasteId[] = pasteIds.split(",");
		
		String infoMessage = "粘贴成功";
		String newIds = "";
		// 判断是否大于最大栏目数量
		Column col = null;
		int length = 0; 
		if(needchild == 1) {
			for(String strid : pasteId) {
				col = columnDao.getAndNonClear(strid);
				if(newIds.equals("")) {
					newIds = strid;
				} else {
					newIds += "," + strid;
				}
				newIds = this.findChildColumnId(col, newIds);
				columnDao.clearCache();
			}
			length = newIds.split(",").length;
		
		} else {
			length = pasteId.length;
		}
		String newInfo = this.getMaxColumn(siteId, length);
		if(!StringUtil.isEmpty(newInfo)) {
			return newInfo;
		}
		
		// 粘贴栏目
		Column column  = null;
		Column newColumn = null;
		for(String strid : pasteId) {
			String pasteid = strid;
			column = columnDao.getAndClear(pasteid);
			column = this.getNewColumn(column, nodeid, siteId);
			newColumn = this.copyToColumns(column, nodeid, siteId, creatorid, columnService);
			
			String parentId = newColumn.getId();
			if(needchild == 1) {
				this.pasteChildColumn(column, parentId, siteId, creatorid, columnService, isCopy, isUpSystemAdmin, isSiteAdmin);
			}
		}
		return infoMessage;
	}

	/**
	 * 复制时递归调用子结点
	 * @param col 			  栏目对象
	 * @param newId           栏目id
	 * @param siteid		  网站id
	 * @param creatorid       创建者id
	 * @param columnService   栏目业务对象
	 */
	private void pasteChildColumn(Column col, String parentId, String siteid, String creatorid, ColumnService columnService, int isCopy, boolean isUpSystemAdmin, boolean isSiteAdmin) {
		// 网站管理员及以上
		if(isUpSystemAdmin || isSiteAdmin || isCopy != 1) {
			// 复制时递归调用子结点
			if(col.getChildren().size() != 0) {
				Column  column = null;
				Column newColumn = null;
			    Iterator<Column>  itr = col.getChildren().iterator();
			    while(itr.hasNext()) {
					column = itr.next();
					column = this.getNewColumn(column, parentId, siteid);
					newColumn = this.copyToColumns(column, parentId, siteid, creatorid, columnService);
					String id = newColumn.getId();
					this.pasteChildColumn(column, id, siteid, creatorid, columnService, isCopy, isUpSystemAdmin, isSiteAdmin);
				}
			}	
			
		// 普通用户	
		} else {
			// 是本网站拷贝，不是选择网站栏目复制（不允许复制非本用户的栏目）
			List<Column> list = new ArrayList<Column>();
			String[] params = {"siteId", "creatorId", "pid"};
			Object[] values = {siteid, creatorid, col.getId()};
			list = columnDao.findByNamedQuery("findColumnBySiteIdAndCreatorIdAndPid", params, values);
			if(list != null && list.size() > 0) {
				Column column = null;
				Column newColumn = null;
				for(int i = 0; i < list.size(); i++) {
					column = list.get(i);
					column = this.getNewColumn(column, parentId, siteid);
					newColumn = this.copyToColumns(column, parentId, siteid, creatorid, columnService);
					String id = newColumn.getId();
					this.pasteChildColumn(column, id, siteid, creatorid, columnService, isCopy, isUpSystemAdmin, isSiteAdmin);
				}
			}
		}
	}
	
	/**
	 * 粘贴时向栏目表中插入内容
	 * @param column 		   栏目对象
	 * @param parentid 		   父栏目id
	 * @param siteid		   网站id
	 * @param creatorId		   创建者id
	 * @param columnservice    栏目业务对象
	 */
	private Column copyToColumns(Column column, String parentid, String siteid, String creatorId, ColumnService columnService){
		log.debug("粘贴时向栏目表中插入内容");
		Column parent = new Column();
		Column newColumn = new Column();
		if(parentid != null) {
			parent.setId(parentid);
			newColumn.setParent(parent);
		}
		newColumn.setName(column.getName());
		newColumn.setDescription(column.getDescription());
		newColumn.setLinkAddress(column.getLinkAddress());
		newColumn.setSelfShowPage(column.getSelfShowPage());
		newColumn.setAudited(column.isAudited());
		newColumn.setDeleted(column.isDeleted());
		newColumn.setUrl(column.getUrl());
		newColumn.setCreateTime(new Date());
		newColumn.setLeaf(true);
		newColumn.setShowInFront(column.isShowInFront());
		newColumn.setChecked(column.isChecked());
		int order = this.findMaxColumnOrders();
		newColumn.setOrders(order + 1);
		
		User creator = new User();
		String creatorid = creatorId; 
		creator.setId(creatorid);
		newColumn.setCreator(creator);
		
		Site site = new Site();
		site.setId(siteid);
		newColumn.setSite(site);
		newColumn.setArticleFormat(column.getArticleFormat());
		
		newColumn.setPublishWay(column.getPublishWay());
		newColumn.setPicScore(column.getPicScore());
		newColumn.setInfoScore(column.getInfoScore());
		newColumn.setTimeSetting(column.getTimeSetting());
		newColumn.setColumnType(column.getColumnType());
		
		columnService.addColumn(newColumn);
		
		// 添加数据库操作日志
		String categoryName = "栏目管理->复制";
		String param = newColumn.getName();
		systemLogDao.addLogData(categoryName, siteid, creatorid, param);
		return newColumn;
	}
	
	/**
	 *  获得刚刚存取栏目表中的最后一行数据
	 */
	private Column findLastRowDate() {
		return (Column) columnDao.findByNamedQuery("findLastRowDate").get(0);
	}
		
	/**
	 * 导入excel
	 * @param file            文件名称
	 * @param siteId          网站id
	 * @param sessionID       用户sessionId
	 * @param nodeid          节点的id
	 * @param columnService   栏目的业务对象
	 * @throws BiffException  解析xsl文件时的异常
	 * @throws IOException    输入输出异常
	 */
	public String importExcel(String file, String siteId, String sessionID, String nodeid, boolean isUpSystemAdmin, boolean isSiteAdmin, ColumnService columnService) throws BiffException, IOException{
		if(file.equals("0")){
			return "文件内容不能为空";
		}
		//创建输入
		InputStream is = new FileInputStream(file);
		jxl.Workbook rwb = Workbook.getWorkbook(is);
		Sheet sheet = rwb.getSheet(0); 
		//获得excel行
		int rowNum = sheet.getRows();
		String infoMessage = "栏目导入成功";
		
		//获得excel列
		int colNum = sheet.getColumns();		
		
		//节点的号码
		int nodeNum = 1;
		
		
		// 获得实际的行数
		while(rowNum > 0){
			Cell[] cell = sheet.getRow(rowNum-1);
			int a = 0;
			for(int j = 0; j < cell.length; j++){
				if(!StringUtil.isEmpty(cell[j].getContents())){
					a = 1;
				}
			}
			if(a == 1) {
				break;
			}
			rowNum--;
		}
		
		// 获得实际的列数
		while(colNum > 0){
			Cell[] cell = sheet.getColumn(colNum-1);
			int a = 0;
			for(int j = 0; j < cell.length; j++){
				if(!StringUtil.isEmpty(cell[j].getContents())){
					a = 1;
				}
			}
			if(a == 1) {
				break;
			}
			colNum--;
		}
		
		// 判断是否超出栏目最大数量
		String newInfo = this.getMaxColumn(siteId, rowNum);
		if(!StringUtil.isEmpty(newInfo)) {
			return newInfo;
		}
		//定义一个总行数
		int totalRowNum = rowNum;
		
		// 判断导入xls文件的格式是否正确
		for(int i = 1; i < rowNum; i++) {
			int tmp = 0;
			Cell[] cell = sheet.getRow(i);
			int j = 0;
			for(j = 0; j < cell.length; j++) {
				String content = cell[j].getContents();
				if(!StringUtil.isEmpty(content)) {
					tmp++;
				}
			}
			if(tmp == 0) {
				infoMessage = "导入失败，xls文件存在空行";
				break;
			}
			if(tmp >= 2) {
				infoMessage = "Excel文件一行只允许有一条数据,详细请看导入说明！导出失败!";
				break;
			}
		}
		if(!infoMessage.equals("栏目导入成功")) {
			return infoMessage;
		}
		if(totalRowNum != 0) {
			//定义一个数组用于存取获得 的栏目的id
			String[] nodeArray = new String[totalRowNum+1];
			nodeArray[0] = nodeid;
			String[] nameArray;
			List<Column> columnList = new ArrayList<Column>();
			// 判断到导入的栏目名字与此节点下的栏目名字是否相同
			if(nodeid == null) {
				// 如果是超级管理员，查询所有数据
				if(isUpSystemAdmin) {
					log.debug("系统管理员");
					columnList = columnDao.findByNamedQuery("findFirstColumnBySystemAdmin", "siteId", siteId);
				// 如果是系统管理员，需要根据网站ID查询
				} else  if(isSiteAdmin) {
					log.debug("网站管理员");
					columnList = columnDao.findByNamedQuery("findFirstColumnBySystemAdmin", "siteId", siteId);
				// 如果是普通用户，需要根据用户ID和网站ID查询
				} else {
					log.debug("普通用户");
					String[] params = {"siteId", "creatorId"};
					Object[] values = {siteId, sessionID};
					columnList = columnDao.findByNamedQuery("findFirstColumnByNormalUser", params, values);
				}
			} else {
				List<Column> list = columnDao.findByNamedQuery("findColumnByColumnId", "columnId", nodeid);
				if(list.size() > 0) {
					Column c = list.get(0);
					// 获得子节点信息
					if(c.getChildren().size() > 0) {
						Set<Column> columnset = c.getChildren();
						Iterator<Column> itr = columnset.iterator();
						while(itr.hasNext()) {
							Column col = (Column)itr.next();
							columnList.add(col);
						}
					}
				}
			}
			nameArray = new String[columnList.size()];
			for(int i = 0; i < columnList.size(); i++) {
				nameArray[i] = columnList.get(i).getName(); 
			}
			
			//其他用户导入栏目时遇到跟admin栏目重名的情况
			List<Column> adminColumnList = new ArrayList<Column>();
			if(nodeid == null) {
				adminColumnList = columnDao.findByNamedQuery("findFirstColumnBySystemAdmin", "siteId", siteId);
			} else {
				adminColumnList = columnDao.findByNamedQuery("findColumnByColumnId", "columnId", siteId);
			}
			String[] adminNameArray = new String[adminColumnList.size()];
			for(int i = 0; i < adminColumnList.size(); i++) {
				adminNameArray[i] = adminColumnList.get(i).getName(); 
			}
			
			//定义一个变量，用于控制流程
			int param = 0;
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("key1", rowNum);
			map.put("key2", nodeNum);
			//如果获得 的行数不为0
			while(StringUtil.parseInt((String)map.get("key1").toString()) > 0) {
				int row = StringUtil.parseInt((map.get("key1").toString()));
				int node = StringUtil.parseInt((String)map.get("key2").toString());
				String contents = sheet.getCell(param, totalRowNum-row).getContents();
				String contents2 = "";
				if( param+1 < colNum) {
					contents2 = sheet.getCell(param+1, totalRowNum-row).getContents();
				} 
				Column column = this.getColumn(sessionID, siteId);
				//判断单元格中的内容是否为空,如果不为空执行下面if中语句
				if(!StringUtil.isEmpty(contents) || !StringUtil.isEmpty(contents2)) {
					if(param == 0) {
						String name = sheet.getCell(0, totalRowNum-row).getContents();
						if(!StringUtil.isEmpty(name)) {
					        Column parent = new Column();
			           		if(nodeid != null) {
			           			parent.setId(nodeid);
			           			column.setParent(parent);
			           		}
			           		for(int i = 0; i < nameArray.length; i++) {
			           			if(nameArray[i].equals(name)) {
			           				name = name + "_" + System.currentTimeMillis();
			           				break;
			           			}
			           		}
			           		
			           		//判断导入栏目是否和admin的第一级栏目同名
			           		for(int i = 0; i < adminNameArray.length; i++) {
			           			if(adminNameArray[i].equals(name)) {
			           				name = name + "_" + System.currentTimeMillis();
			           				break;
			           			}
			           		}
			           		column.setName(name);
			           		columnService.addColumn(column);
			           		
			           		// 添加数据库操作日志
			        		String categoryName = "栏目管理->导入";
			        		String param1 = column.getName();
			        		systemLogDao.addLogData(categoryName, siteId, sessionID, param1);
			        		
			           	    nodeArray[StringUtil.parseInt((String)map.get("key2").toString())] = column.getId();
			           	    row--;
			           	    node++;
			           	    map.put("key1", row);
			           	    map.put("key2", node);
			           	    if(row > 0) {
			           	    	String content = sheet.getCell(0, totalRowNum-row).getContents();
			           	    	if(!StringUtil.isEmpty(content)) {
			           	    		param = 0;
			           	    	} else {
			           	    		param = 1;
			           	    	}
			           	    } else {
			           	    	param = 1;
			           	    }
						}
					} else {
						//获得行数
						int rowIndex = StringUtil.parseInt((String)map.get("key1").toString());
	           	    	map = this.importColumn(map, totalRowNum-rowIndex, param, nodeArray, sheet, columnService, sessionID, siteId, colNum);
	           	    	param = 0;
	           	    }
		        } else {
		        	row--;
		        	map.put("key1", row);
		        }
			}
		}
		if(FileUtil.isExist(file)){
			FileUtil.delete(file);
		}
		return infoMessage;
	}
	
	/**
	 * 递归处理导入栏目
	 * @param map   		 定义一个map用于存取行数和存取数组的节点的变量
	 * @param row   		 存取行
	 * @param col   		 存取列
	 * @param array 		 定义一个数组，用于存取id
	 * @param sheet  		 定义一个excel表的对象
	 * @param columnService  创建一个栏目业务对象
	 * @param sessionID      用户的id
	 * @param siteId         网站的id
	 * @param colNum         用于存取总的列数
	 * @return               返回存取行数和存取节点的一个map
	 */
    private Map<String, Integer> importColumn(Map<String, Integer> map, int row, int col, String[] array, Sheet sheet, ColumnService columnService, String sessionID, String siteId, int colNum) {
    	String name = sheet.getCell(col, row).getContents();
	   //定义一个变量，用于获得行数
	   int r = row;
	   //如果获得那一行内容部为空
	   if(!StringUtil.isEmpty(name)) {
		   while(r > 0) {
			   //如果找到一行，它的内容部为空，那么查找上一行节点
			   if(!StringUtil.isEmpty(sheet.getCell(col-1, r).getContents())) {
				   Column column = this.getColumn(sessionID, siteId);
				   Column parent = new Column();
				   parent.setId(array[r]);
				   column.setParent(parent);
				   column.setName(name);
				   columnService.addColumn(column);
				   
				   // 添加数据库操作日志
	        		String categoryName = "栏目管理->导入";
	        		String param1 = column.getName();
	        		systemLogDao.addLogData(categoryName, siteId, sessionID, param1);
				   
				   //Column newColumn = (Column) findLastRowDate();
				   array[StringUtil.parseInt((String)map.get("key2").toString())] = column.getId();
				   int num = StringUtil.parseInt((String)map.get("key2").toString())+1;
				   map.put("key2", num);
				   int rowNum = StringUtil.parseInt((String)map.get("key1").toString())-1;
				   map.put("key1", rowNum);
				   //如果处理 的行数小于文件中的所有行数
				   if(StringUtil.parseInt((String)map.get("key1").toString()) > 0) {
					   //如果当前列的下一行不为空
					   if(!StringUtil.isEmpty(sheet.getCell(col, row+1).getContents())) {
						   map = this.importColumn(map, row+1, col, array, sheet, columnService, sessionID, siteId, colNum);
					   } else if(col+1 < colNum){
						   if(!StringUtil.isEmpty(sheet.getCell(col+1, row+1).getContents())) {
							   map = this.importColumn(map, row+1, col+1, array, sheet, columnService, sessionID, siteId, colNum);
						   } else {
							   for(int j = 1; j < colNum; j++) {
								   if(!StringUtil.isEmpty(sheet.getCell(j, row+1).getContents())) {
									   map = this.importColumn(map, row+1, j, array, sheet, columnService, sessionID, siteId, colNum);
								   }
							   	} 
						   }
					   } else {
						   for(int j = 1; j < colNum; j++) {
							   if(!StringUtil.isEmpty(sheet.getCell(j, row+1).getContents())) {
								   map = this.importColumn(map, row+1, j, array, sheet, columnService, sessionID, siteId, colNum);
							   }
						   	} 
					   	}
				   	}
				   	break;
			   	} else {
			   		r--;   
			   	}
		   	}
	   	}
	   	return map;
   }
   
   /**
    * 用于定义栏目的初始化对象
    * @param sessionID    用户的id
    * @param siteId       网站的id
    * @return             返回一个栏目对象
    */
   private Column getColumn(String sessionID, String siteId) {
	    //导入栏目前先向栏目中添加一些默认值
		Column column = new Column();
		column.setDescription("");
		column.setDeleted(false);
		column.setSelfShowPage("");
		column.setShowInFront(false);
		int maxOrder = findMaxColumnOrders();
		column.setOrders(maxOrder + 1);
		column.setLinkAddress("column.do?dealMethod=");
		column.setLeaf(true);
		column.setCreateTime(new Date());
		column.setAudited(false);
		User creator = new User();
		creator.setId(sessionID);
		column.setCreator(creator);
		Site site =  new Site();
		site.setId(siteId);
		column.setSite(site);
		ArticleFormat af = new ArticleFormat();
   		af.setId("f1");
   		column.setArticleFormat(af);
   	    //给栏目积分赋值
       	column.setInfoScore("1");
       	column.setPicScore("0");
   		column.setPublishWay("0");
   		column.setColumnType("multi");
		return column;
   }
   
	/**
	 * 根据栏目id查找导出栏目
	 * @param nodeid 	  栏目id
	 * @return 			  返回栏目列表
	 */
	public List<Column> findExportColumnByColumnId(String nodeid) {
		return columnDao.findByNamedQuery("findExportColumnByColumnId", "columnId", nodeid);
	}
	/**
	 * 按照网站id查找导出栏目
	 * @param siteId 	      网站id
	 * @param sessionID       创建者id
	 * @return		 	      返回栏目列表
	 */
	private List<Column> findExortColumnBySiteId(String siteId, String sessionID, boolean isUpSystemAdmin, boolean isSiteAdmin) {
		List<Column> list = new ArrayList<Column>();
		// 如果是超级管理员，查询所有数据
		if(isUpSystemAdmin) {
			log.debug("系统管理员");
			list = columnDao.findByNamedQuery("findFirstColumnBySystemAdmin", "siteId", siteId);
		
		// 如果是系统管理员，需要根据网站ID查询
		} else  if(isSiteAdmin) {
			log.debug("网站管理员");
			list = columnDao.findByNamedQuery("findFirstColumnBySystemAdmin", "siteId", siteId);
		
		// 如果是普通用户，需要根据用户ID和网站ID查询
		} else {
			log.debug("普通用户");
			String[] params = {"siteId", "creatorId"};
			Object[] values = {siteId, sessionID};
			list = columnDao.findByNamedQuery("findFirstColumnByNormalUser", params, values);
		}
		return list;
	}
	
	/**
	 * 导出数据到excel
	 * @param fileName 		  文件名
	 * @param siteId 	      网站id
	 * @param nodeid          节点id
	 * @param sessionID       创建者id
	 * @param columnService   栏目业务对象
	 * @throws Exception      抛出异常
	 */
	public void exportColumnsToExcel(String fileName, String siteId, String nodeid, String exportColumnIds, String sessionID, boolean isUpSystemAdmin, boolean isSiteAdmin, ColumnService columnService) throws Exception {
		log.debug("导出数据到excel"); 
		FileOutputStream fos = new FileOutputStream(fileName);
		WritableWorkbook wwb = Workbook.createWorkbook(fos);
	    // 创建一个工作表
	    WritableSheet ws = wwb.createSheet("栏目列表", 10);        
	    // 设置单元格的文字格式
	    WritableFont wf = new WritableFont(WritableFont.ARIAL, 12, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLUE);
	    WritableCellFormat wcf = new WritableCellFormat(wf);
	    wcf.setVerticalAlignment(VerticalAlignment.CENTRE); 
	    wcf.setAlignment(Alignment.CENTRE); 
	    ws.setRowView(1, 500);
	    
	    // 定义一个表中显示的行数
	    int count = 1;
	    int i = 0;
	    
		//  默认导出方式（将当前栏目下全部导出）
	    if(exportColumnIds == null) {
			List<Column> list = new ArrayList<Column>();
			//节点id为0时，导出全部数据
			if(nodeid == null) {
				list = findExortColumnBySiteId(siteId, sessionID, isUpSystemAdmin, isSiteAdmin);
			} else {
				list = findExportColumnByColumnId(nodeid);
			}
		    // 获取数据的内容
		    Column[] columnArray = new Column[list.size()];
	        while(i < columnArray.length) {
	        	columnArray[i] = (Column) list.get(i);
	        	
	        	// 添加数据库操作日志
				String categoryName = "栏目管理->导出";
				String param = columnArray[i].getName();
				systemLogDao.addLogData(categoryName, siteId, sessionID, param);
				
	        	columnArray[i].setId("0");
	        	count = proccessExportChild(columnArray[i], count, ws, wcf, siteId, sessionID);
	        	i++;
	        	count++;
	        }
	        
	    // 选择导出方式（选择一些导出）
	    } else {
	    	String[] str = exportColumnIds.split(",");
	    	// 获取数据的内容
		    Column[] columnArray = new Column[str.length];
		    while(i < columnArray.length) {
		    	Column column = columnDao.getAndNonClear(str[i]);
		    	columnArray[i] = column;
		    	
				// 添加数据库操作日志
				String categoryName = "栏目管理->导出";
				String param = column.getName();
				systemLogDao.addLogData(categoryName, siteId, sessionID, param);
				
		    	columnArray[i].setId("0");
	        	count = proccessExportChild(columnArray[i], count, ws, wcf, siteId, sessionID);
	        	i++;
	        	count++;
		    }
	    }
	    wwb.write();
	    wwb.close();
	    fos.close();
	}
	
	/**
	 * 递归处理导出
	 * @param column 	 	栏目对象
	 * @param count 		导出时在表中的行数
	 * @param ws 			工作表对象
	 * @param wcf 		 	表的格式对象
	 * @return 			 	返回导出时在表中的行数
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	private int proccessExportChild(Column column, int count, WritableSheet ws, WritableCellFormat wcf, String siteId, String sessionID) throws RowsExceededException, WriteException{
		ws.addCell(new Label(StringUtil.parseInt(column.getId()), count, column.getName(), wcf));
		if(column.getChildren().size() != 0) {
			Set<Column> columnList = column.getChildren();
			Iterator<Column> ite = columnList.iterator();
			while(ite.hasNext()) {
				Column column1 = ite.next();		
				count++;
				//设置在表中显示的列数
				column1.setId((StringUtil.parseInt(column.getId())+1)+"");
			    count = proccessExportChild(column1, count, ws, wcf, siteId, sessionID);
			}
		}
		return count;
	}	

	/**
	 * 根据栏目的id分页展示栏目
	 * @param pagination     栏目分页对象
	 * @param columnid       栏目id
	 * @param siteId         网站id
	 * @param sessionID      用户id
	 * @param isUpSystemAdmin
	 * @param isSiteAdmin
	 * @return
	 */
	public Pagination findColumnByColumnIdPage(Pagination pagination, String columnId, String siteId, String sessionID, boolean isUpSystemAdmin, boolean isSiteAdmin) {
		log.debug("根据栏目id查找栏目");
		if(isUpSystemAdmin) {
			String[] params = {"columnId", "siteId"};
			Object[] values = {columnId, siteId};
			return columnDao.getPagination(pagination, params, values);
		} else if(isSiteAdmin){
			String[] params = {"columnId", "siteId"};
			Object[] values = {columnId, siteId};
			return columnDao.getPagination(pagination, params, values);
		} else {
			String[] params = {"columnId", "siteId", "creatorId"};
			Object[] values = {columnId, siteId, sessionID};
			return columnDao.getPagination(pagination, params, values);
		}
	}
	
	/**
	 * 根据id查找要删除的栏目
	 * @param id            栏目的id
	 * @return              返回栏目对象
	 */
	private Column findDeletedColumn(String id) {
		return (Column) columnDao.findByNamedQuery("findColumnByColumnId", "columnId", id).get(0);
	}
	
	/**
	 * 递归查找某个栏目的子栏目
	 * @param column        要递归的栏目的对象
	 * @param ids           结果要返回的ids 集合
	 * @return              返回栏目下的所有子节点
	 */
	public String findChildColumnId(Column column, String ids) {
		// 子节点不为空
		if(column.getChildren() != null && column.getChildren().size() > 0) {
			Set<Column> childColumn = column.getChildren();
			Iterator<Column> itr = childColumn.iterator();
			while(itr.hasNext()) {
				Column newColumn = itr.next();
				if(StringUtil.isEmpty(ids)) {
					ids = newColumn.getId();
				} else {
					ids += "," + newColumn.getId();
				}
				ids = findChildColumnId(newColumn, ids);
			}
		}
		return ids;
	}
	
	public List<ArticleFormat> findAllArticleFormats() {
		 List<ArticleFormat> list = articleFormatDao.findAll();
		 return list;
	}
	
	/**
	 * 查找父节点的名字
	 * @param nodeid  父节点id
	 * @return nodeName       返回栏目下的所有子节点
	 */
	public String findNodeName(String nodeid) {
		List list = columnDao.findByNamedQuery("findColumnNameByColumnId", "columnId", nodeid);
		String nodeName = "";
		for(int i = 0; i < list.size(); i++) {
			nodeName = String.valueOf(list.get(i));
		}
		return nodeName;
	}
	
	/** 查找当前是否为根站
	 * @param siteId 网站Id
	 * @return String
	 */
	public String findSiteById(String siteId) {
		Site site = siteDao.getAndNonClear(siteId);
		String isRootSite = "";
		if(site.getParent() == null) {
			isRootSite = "yes";
		} else {
			isRootSite = "no";
		}
		return isRootSite;
	}
	
	/**
	 * 按照栏目id查找文章
	 * @param columnId
	 * @return             返回栏目下面是否有文章(1代表有，0代表没有)
	 */
	public int findArticleByColumnId(String columnId) {
		List list = articleDao.findByNamedQuery("findAllArticleByColumnId", "columnId", columnId);
		if(list != null && list.size() > 0) {
			return 1;
		} else {
			return 0;
		}
	}
	
	/**
	 * 模板设置中的栏目连接->指定栏目->查找栏目树(查找所有数据)
	 * @param siteId 	    网站id
	 * @param columnId 	    栏目id
	 * @return			    返回List
	 */
	public List findTemplateSetColumnLinkTree(String siteId, String columnId) {
		List columnList = new ArrayList();
		if(columnId == null) {
			columnList = columnDao.findByNamedQuery("findColumnBySiteAdmin", "siteId", siteId);
		} else {
			String[] params = {"siteId", "columnId"};
			Object[] values ={siteId, columnId};
			columnList = columnDao.findByNamedQuery("findColumnTreeBySiteIdAndColumnId", params, values);
		}
		return columnList;
	}	
	
	/**
	 * 查找同步栏目格式
	 * @param articleFormatId  格式id
	 * @return	 返回格式对象
	 */
	public ArticleFormat findRefColumnFormat(String articleFormatId) {
		ArticleFormat articleFormat = articleFormatDao.getAndClear(articleFormatId);
		return articleFormat;
		
	}
	
	/**
	 * 查找同步栏目名称
	 * @param refColumnIds  栏目ids
	 * @return	栏目名称
	 */
	public String findRefColumnNames(String columnId) {
		Column newColumn = columnDao.getAndClear(columnId);
		String refColumnIds = newColumn.getRefColumnIds();
		if(refColumnIds == null || refColumnIds.trim().length() == 0) {
			refColumnIds = null;
		}
		String columnNames = "";
		if(refColumnIds != null ) {
			String[] str = refColumnIds.split(",");
			Column column = new Column();
			Site site = new Site();
			String columnName = "";
			for(int i = 0; i < str.length; i++) {
				column = columnDao.getAndClear(str[i]);
				site = siteDao.getAndClear(column.getSite().getId());
				columnName = column.getName() + "【" + site.getName() + "】";
				columnNames = columnNames + columnName + ",";
			}
			columnNames = columnNames.substring(0, columnNames.length()-1);
		}
		
		return columnNames;
	}
	
	/**
	 * 查找栏目树中所有与当前格式格式不相同的栏目
	 * @param articleFormatId
	 * @param siteId 网站 id
	 * @return sameFormatColumns 格式不相同的栏目ids
	 */
	@SuppressWarnings("unchecked")
	public String findSameFormatColumns(String articleFormatId, String siteId) {
		String sameFormatColumns = "";
		String[] params = {"siteId", "articleFormatId"};
		Object[] values ={siteId, articleFormatId};
		List list = columnDao.findByNamedQuery("findSameFormatColumnsByFormatId", params, values);
		for(int i = 0; i < list.size(); i++) {
			sameFormatColumns += list.get(i) + ",";
		}
		return sameFormatColumns;
	}
	
	/**
	 * 查找同步网站
	 * @param siteId 当前网站
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List findRefSiteList(String siteId) {
		List list = siteDao.findByNamedQuery("findRefSiteList", "siteId", siteId);
		return list;
	}
	
	/**
	 * 查找同步网站
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List findRefSiteList() {
		List list = siteDao.findByNamedQuery("findAllRefSiteList");
		return list;
	}
	
	/**
	 * 查找带有checkbox的树
	 * @param siteId
	 * @param columnId
	 * @param sessionID
	 * @param isUpSystemAdmin
	 * @param isSiteAdmin
	 * @param checkedIds
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List findColumnCheckBoxTree(String siteId, String columnId, String sessionID, boolean isUpSystemAdmin, boolean isSiteAdmin, String checkedIds) {
		List<Column> columnList = new ArrayList<Column>();
		if(columnId == null) {
			// 如果是系统管理员，查询所有数据
			if(isUpSystemAdmin) {
				columnList = columnDao.findByNamedQuery("findFirstColumnBySystemAdmin", "siteId", siteId);
			
			// 如果是网站管理员，需要根据网站ID查询
			} else  if(isSiteAdmin) {
				columnList = columnDao.findByNamedQuery("findFirstColumnBySystemAdmin", "siteId", siteId);
			
			// 如果是普通用户，需要根据用户ID和网站ID查询
			} else {
				String[] params = {"siteId", "creatorId"};
				Object[] values = {siteId, sessionID};
				columnList = columnDao.findByNamedQuery("findFirstColumnByNormalUser", params, values);
			}
		} else {
			// 如果是系统管理员，按照栏目id查询所有数据
			if(isUpSystemAdmin) {
				String[] params = {"siteId", "columnId"};
				Object[] values = {siteId, columnId};
				columnList = columnDao.findByNamedQuery("findColumnBySiteIdAndColumnId", params, values);
			
			// 如果是网站管理员，需要根据网站ID和栏目id查询
			} else  if(isSiteAdmin) {
				String[] params = {"siteId", "columnId"};
				Object[] values = {siteId, columnId};
				columnList = columnDao.findByNamedQuery("findColumnBySiteIdAndColumnId", params, values);
			
			// 如果是普通用户，需要根据栏目id和用户ID以及网站ID查询
			} else {
				String[] params = {"siteId", "columnId", "creatorId"};
				Object[] values = {siteId, columnId, sessionID};
				columnList = columnDao.findByNamedQuery("findColumnBySiteIdAndColumnIdAndCreatorId", params, values);
			}
		}
		List list = new ArrayList();
		if(!CollectionUtil.isEmpty(columnList)) {
			Column column = null;
			for(int i = 0; i < columnList.size(); i++) {
				column = columnList.get(i);
				Object[] obj = new Object[5];
				obj[0] = column.getId();
				obj[1] = column.getName();
				obj[2] = column.getLinkAddress();
				if(column.isLeaf()) {
					obj[3] = true;	
				} else {
					obj[3] = false;
				}
				if(!StringUtil.isEmpty(checkedIds) && !checkedIds.equals("null")) {
					String[] str = checkedIds.split(",");
					for(int j = 0; j < str.length; j++) {
						if(str[j].equals(column.getId())) {
							obj[4] = true;
							break;
						} 
						obj[4] = false;
					}
				} else {
					obj[4] = false;
				}
				list.add(obj);
			}
		}
		return list;
	}
	
	
	/**
	 * 按照网站id或者栏目id查找文章管理栏目树(根据不同的角色来判断显示内容)
	 * @param siteId 	    网站id
	 * @param columnId 	    栏目id
	 * @param sessionId     当前登陆用户的id
	 * @return			    返回List
	 */
	@SuppressWarnings("unchecked")
	public List findArticleColumnTree(String siteId, String columnId, String sessionId, boolean isUpSystemAdmin, boolean isUpSiteAdmin) {
		List columnList = new ArrayList();
		//根据栏目类型，栏目ID，用户ID，查询权力表,显示选择的操作
		String chooseColumn = "";
		String strColumnId = "";
		if(columnId == null || columnId.equals("0")){
			strColumnId = "0";
			columnList = columnDao.findByNamedQuery("findFirstColumnBySystemAdmin","siteId",siteId);
		}else{
			strColumnId = columnId;
			String[] params1 = {"siteId", "columnId"};
			Object[] values1 ={siteId, strColumnId};
			columnList = columnDao.findByNamedQuery("findColumnBySiteIdAndColumnId", params1, values1);
		}
		//查询所有单独设置文章权限的栏目ID
		String rightStr[] = {"siteId","resourceType","operationTypes"};
		Object rightObj[] = {siteId,Resource.TYPE_COLUMN,Operation.TYPE_ARTICLE};
		 List rightList = rightDao.findByNamedQuery("findRightBySiteIdAndResourceTypeAndOperationTypes",rightStr,rightObj);
		Right right = null;	
		String setColumnIds = "";
		StringBuffer columnids = new StringBuffer();
		for(int i = 0 ; i < rightList.size() ; i++){
			right = (Right)rightList.get(i);
			this.getChildColumnId(columnids, right.getAuthority().getResource().getIdentifier(), siteId);	 
		}
		setColumnIds = columnids.toString();
		setColumnIds = StringUtil.clearRepeat(setColumnIds);
		setColumnIds = StringUtil.replaceFirst(setColumnIds, ",");
		Column column = null;
		for(int i = 0 ; i < columnList.size() ; i++){
			column = (Column)columnList.get(i);
			if(isUpSiteAdmin){
				chooseColumn = chooseColumn + "," +SqlUtil.toSqlString(column.getId());
			}else{
				List list = this.getTreeCheckBoxChooseObject(column.getId(), sessionId, siteId, Operation.TYPE_ARTICLE);
				if(list != null && list.size() > 0){
					chooseColumn = chooseColumn + "," +SqlUtil.toSqlString(column.getId());
				}else if(StringUtil.contains(setColumnIds, column.getId())){
					if(chooseColumn != null && !chooseColumn.equals("")){
						chooseColumn = chooseColumn + "," +SqlUtil.toSqlString(column.getId());
					}else{
						chooseColumn = SqlUtil.toSqlString(column.getId());
					}
				}
			}
		}
		log.debug("chooseColumn===========111111111111===="+chooseColumn);
		chooseColumn = StringUtil.replaceFirst(chooseColumn, ",");
		//用户自己建的栏目集合
		List selfList = new ArrayList();
		if(!StringUtil.isEmpty(strColumnId) && !strColumnId.equals("0")) {
			String[] params = {"siteId", "creatorId", "columnId"};
			Object[] values = {siteId, sessionId, strColumnId};
			selfList = columnDao.findByNamedQuery("findSelfColumnByNormalUser", params, values);
		} else {
			String[] params = {"siteId", "creatorId"};
			Object[] values = {siteId, sessionId};
			selfList = columnDao.findByNamedQuery("findSelfColumnByNormalUserAndPidIsNull", params, values);
		}
		Object obj[] = null;
		for(int i = 0; i < selfList.size(); i++) {
			obj = (Object[])selfList.get(i);
			if(obj != null){
				chooseColumn = chooseColumn + "," + SqlUtil.toSqlString(String.valueOf(obj[0]));
			}			
		}
		chooseColumn = StringUtil.replaceFirst(chooseColumn, ",");
		chooseColumn =  StringUtil.clearRepeat(chooseColumn); 
		chooseColumn = StringUtil.replaceFirst(chooseColumn, ",");
		//用户拥有权限的栏目集合
		log.debug("chooseColumn==========222222222222====="+chooseColumn);
		List list1 = new ArrayList();
		if(chooseColumn != null && !chooseColumn.equals("")){
			list1 = columnDao.findByDefine("findColumnTreeByColumnIds", "columnIds", chooseColumn);
		}
		return list1; 
		
		/*
		//根据栏目类型，栏目ID，用户ID，查询权力表,显示选择的操作
		List rightList = this.findRightByUserIdAndItemIdAndItemTypeAndSiteId(Resource.TYPE_COLUMN, sessionId, siteId,Operation.TYPE_ARTICLE);
		String chooseColumn = "";
		Right right = null;
		//查询这个用户的这个栏目的权限--已有的操作			
		for(int i = 0 ; i < rightList.size(); i++ ){
			right = (Right) rightList.get(i);
			String hasColumnId = right.getAuthority().getResource().getIdentifier();
			//如果拥有所有栏目
			chooseColumn = chooseColumn + "," +hasColumnId;
		}
		String strColumnId = "";
		if(columnId == null || columnId.equals("0")){
			strColumnId = "";
		}else{
			strColumnId = columnId;
		}
		//用户自己建的栏目集合
		String[] params = {"siteId", "creatorId","columnId"};
		Object[] values ={siteId, sessionId,strColumnId};
		List selfList = columnDao.findByNamedQuery("findSelfColumnByNormalUser", params, values);
		Object obj[] = null;
		
		for(int i = 0; i < selfList.size(); i++) {
			obj = (Object[])selfList.get(i);
			if(obj != null){
				chooseColumn = chooseColumn + "," + obj[0];
			}
		}
		chooseColumn = StringUtil.replaceFirst(chooseColumn, ",");
		chooseColumn =  StringUtil.clearRepeat(chooseColumn);
		//用户拥有权限的栏目集合
		List list1 = this.getTreeList2(chooseColumn, columnId, sessionId, siteId, isUpSiteAdmin);
		return list1;*/
	}	
	
	/**
	 * 按照网站id或者栏目id查找模版设置栏目树(根据不同的角色来判断显示内容)
	 * @param siteId 	    网站id
	 * @param columnId 	    栏目id
	 * @param sessionId     当前登陆用户的id
	 * @return			    返回List
	 */
	@SuppressWarnings("unchecked")
	public List findTemplateSetColumnTree(String siteId, String columnId, String sessionId, boolean isUpSystemAdmin, boolean isUpSiteAdmin) {
		List columnList = new ArrayList();
		//根据栏目类型，栏目ID，用户ID，查询权力表,显示选择的操作
		String chooseColumn = "";
		String strColumnId = "";
		if(columnId == null || columnId.equals("0")){
			strColumnId = "0";
			columnList = columnDao.findByNamedQuery("findFirstColumnBySystemAdmin","siteId",siteId);
		}else{
			strColumnId = columnId;
			String[] params1 = {"siteId", "columnId"};
			Object[] values1 ={siteId, strColumnId};
			columnList = columnDao.findByNamedQuery("findColumnBySiteIdAndColumnId", params1, values1);
		}
		//查询所有单独设置模版权限的栏目ID
		String rightStr[] = {"siteId", "resourceType", "operationTypes"};
		Object rightObj[] = {siteId, Resource.TYPE_COLUMN, Operation.TYPE_TEMPLATESET};
		List rightList = rightDao.findByNamedQuery("findRightBySiteIdAndResourceTypeAndOperationTypes",rightStr,rightObj);
		Right right = null;	
		String setColumnIds = "";
		StringBuffer columnids = new StringBuffer();
		for(int i = 0 ; i < rightList.size() ; i++){
			right = (Right)rightList.get(i);
			this.getChildColumnId(columnids, right.getAuthority().getResource().getIdentifier(), siteId);	 
		}
		setColumnIds = columnids.toString();
		setColumnIds = StringUtil.clearRepeat(setColumnIds);
		setColumnIds = StringUtil.replaceFirst(setColumnIds, ",");
		Column column = null;
		for(int i = 0 ; i < columnList.size() ; i++){
			column = (Column)columnList.get(i);
			if(isUpSiteAdmin){
				chooseColumn = chooseColumn + "," +SqlUtil.toSqlString(column.getId());
			}else{
				List list = this.getTreeCheckBoxChooseObject(column.getId(), sessionId, siteId, Operation.TYPE_TEMPLATESET);
				if(list != null && list.size() > 0){
					chooseColumn = chooseColumn + "," +SqlUtil.toSqlString(column.getId());
				}else if(StringUtil.contains(setColumnIds, column.getId())){
					if(chooseColumn != null && !chooseColumn.equals("")){
						chooseColumn = chooseColumn + "," +SqlUtil.toSqlString(column.getId());
					}else{
						chooseColumn = SqlUtil.toSqlString(column.getId());
					}
				}
			}
		}
		log.debug("chooseColumn===========111111111111===="+chooseColumn);
		chooseColumn = StringUtil.replaceFirst(chooseColumn, ",");
		//用户自己建的栏目集合
		List selfList = new ArrayList();
		if(!StringUtil.isEmpty(strColumnId) && !strColumnId.equals("0")) {
			String[] params = {"siteId", "creatorId", "columnId"};
			Object[] values = {siteId, sessionId, strColumnId};
			selfList = columnDao.findByNamedQuery("findSelfColumnByNormalUser", params, values);
		} else {
			String[] params = {"siteId", "creatorId"};
			Object[] values = {siteId, sessionId};
			selfList = columnDao.findByNamedQuery("findSelfColumnByNormalUserAndPidIsNull", params, values);
		}
		Object obj[] = null;
		for(int i = 0; i < selfList.size(); i++) {
			obj = (Object[])selfList.get(i);
			if(obj != null){
				chooseColumn = chooseColumn + "," + SqlUtil.toSqlString(String.valueOf(obj[0]));
			}			
		}
		chooseColumn = StringUtil.replaceFirst(chooseColumn, ",");
		chooseColumn =  StringUtil.clearRepeat(chooseColumn); 
		chooseColumn = StringUtil.replaceFirst(chooseColumn, ",");
		//用户拥有权限的栏目集合
		log.debug("chooseColumn==========222222222222====="+chooseColumn);
		List list1 = new ArrayList();
		if(chooseColumn != null && !chooseColumn.equals("")){
			list1 = columnDao.findByDefine("findColumnTreeByColumnIds", "columnIds", chooseColumn);
		}
		return list1; 
	}	
	
	
	
	/**
	 * 根据栏目类型，栏目ID，用户ID，查询权力表 
	 * @param itemType 栏目类型
	 * @param userId 用户ID
	 * @param siteId 网站ID
	 * @return List 
	 */
	@SuppressWarnings("unchecked")
	private List<Right> findRightByUserIdAndItemIdAndItemTypeAndSiteId(String itemType,String userId,String siteId,String operTypes){
		String[] str = {"userId", "siteId", "resourceType", "operationTypes"};
		Object[] obj = {userId, siteId, itemType, operTypes};
		List list = rightDao.findByNamedQuery("findRightByUserIdAndSiteIdAndResourceTypeAndOperationIds", str, obj);
		return list;		
	}
	
 

	/**
	 * 根据栏目本身的集合和权限集合拼装新的集合
	 * @param columnList 栏目集合
	 * @return List 新的栏目树集合
	 */
	private List getNewListByColumnList(String ss,List columnList){
		List newList = new ArrayList();
		for(int i = 0 ; i < columnList.size() ; i++){
			Object[] tempobj = (Object[])columnList.get(i);				 
			String nodeid = (String)tempobj[0];					 
		 	if(StringUtil.contains(ss, nodeid) ){					 
		 		ss = ss.replace(nodeid, "");				 
				newList.add(tempobj);	
			}					 
		}
		return newList;
	}
	
	/**
	 * 根据权限栏目ids，页面传过来的treeid（节点ID），用户ID，网站ID,是否拥有网站管理员以上权限，拼装栏目树
	 * @param treeIds 权限栏目ids
	 * @param treeid 页面传过来的treeid（节点ID）
	 * @param userId 用户ID
	 * @param siteId 网站ID
	 * @param isUpSiteAdmin 是否拥有网站管理员以上权限
	 * @return  List 拼装完的树
	 */
	private List getTreeList2(String treeIds,String treeid,String userId,String siteId,boolean isUpSiteAdmin){
		    List<Object> list = new ArrayList<Object>(); 
			String ss = "";
			ss = this.getTreeList3(treeIds, "0", ss, siteId, isUpSiteAdmin);		
			ss = StringUtil.clearRepeat(ss);	
			List newList = new ArrayList();
			if(treeid == null || treeid.equals("0")){
				//查询根节点是0的
				if(isUpSiteAdmin){
					newList = columnDao.findByNamedQuery("findColumnBySiteAdmin", "siteId", siteId);
				}else{
				
					List columnList = columnDao.findByNamedQuery("findColumnBySiteAdmin", "siteId", siteId);				
					newList = this.getNewListByColumnList(ss, columnList);
				}		
			}else{
				if(isUpSiteAdmin){
					String[] params = {"siteId", "columnId"};
					Object[] values ={siteId, treeid};
					newList = columnDao.findByNamedQuery("findColumnTreeBySiteIdAndColumnId", params, values);
				}else{
					//查询父亲节点（treeid）不是0的栏目
					String str[] = {"columnId","siteId"};
					Object obj[] = {treeid,siteId};
				//	ss = this.getTreeList3(treeIds, treeid, ss, siteId, isUpSiteAdmin);		
					ss = StringUtil.clearRepeat(ss);	
					List columnList = columnDao.findByNamedQuery("findColumnTreeByColumnIdAndSiteId", str, obj);				
					newList = this.getNewListByColumnList(ss, columnList);
				}
			}
		
			return newList;			
						
	}
	/**
	 * 递归获取所有级联的ID
	 * @param treeIds
	 * @param treeid
	 * @param str
	 * @return
	 */
	private String getTreeList3(String treeIds,String treeid,String str,String siteId,boolean isUpSiteAdmin){	
		List columnList = columnDao.findByNamedQuery("findColumnAndPidBySiteAdmin", "siteId", siteId);	 
		if(!isUpSiteAdmin){
			for(int i = 0 ; i < columnList.size() ; i++){
				Object obj[] = (Object[])columnList.get(i);
				String strNodePid = String.valueOf(obj[6]);
				String strNodeId = String.valueOf(obj[0]);
				if(treeid == null || treeid.equals("") || treeid.equals("0") || treeid.equals("null")){
					if(StringUtil.contains(treeIds, strNodeId) ){	
						str = str + "," + strNodeId;
						if(strNodePid != null && !strNodePid.equals("0") &&  !strNodePid.equals("") && !strNodePid.equals("null") ){
							str = str + "," + strNodePid;
							getTreeList3(treeIds,strNodePid,str,siteId,isUpSiteAdmin);
						}
					}
				}else{
					if(treeid.equals(strNodeId)){
						if(strNodePid != null && !strNodePid.equals("0") &&  !strNodePid.equals("") && !strNodePid.equals("null") ){
							str = str + "," + strNodePid;
							getTreeList3(treeIds,strNodePid,str,siteId,isUpSiteAdmin);
						}
					}
				}
			}
		}
		log.debug("str===================="+str);
		return str;
}

	/**
	 * 根据用户ID和网站ID查询出树的IDS
	 * @param userId 用户ID
	 * @param siteId 网站ID
	 * @return String 树的IDS
	 */
	private String getTreeIds(String userId , String siteId){
		User user = userDao.getAndNonClear(userId);
		String treeIds = "";
		if(user != null){
			String systemFunction = user.getSystemFunction();		
			if(systemFunction != null){
				//将对多个网站设置的权限分割开来，获取某一个网站的具体权限
				String strsystemFunction[] = systemFunction.split("#");
				String siteSet = "";
				for(int i = 0 ; i < strsystemFunction.length ; i++){
					siteSet = strsystemFunction[i];
					log.debug("siteSet=================="+siteSet);
					if(StringUtil.contains(siteSet, siteId+",m8")){
						treeIds = StringUtil.replaceFirst(siteSet,siteId+",m8");
					}
				}
			}
		}
	
		//获取到对这个网站设置的树的权限的ID
		treeIds = StringUtil.replaceFirst(treeIds, ",");
		return treeIds;
	}
	
	
	 /**
	 * 处理父网站栏目的粘贴
	 * @param nodeid		  要复制到的位置id
	 * @param pasteIds 	      要复制的栏目ids
	 * @param siteId          网站id
	 * @param creatorid       创建者id
	 * @param needchild       是否需要子节点
	 * @param columnService   栏目业务对象
	 */
	public String pasteParentcolumn(String nodeid, String pasteIds, String siteId, String creatorid, int needchild, int isCopy, boolean isUpSystemAdmin, boolean isSiteAdmin, ColumnService columnService) {
		 String[] str = pasteIds.split(",");
		 for(int i =0; i < str.length; i++) {
		 	this.pasteColumn(nodeid, str[i], siteId, creatorid, needchild, isCopy, isUpSystemAdmin, isSiteAdmin, columnService);
		 }
		 return "粘贴成功";
	 }
	
	/**
	 * 处理引用父站的栏目
	 * @param nodeid		  要复制到的位置id
	 * @param pasteIds 	      要复制的栏目id
	 * @param siteId          网站id
	 * @param creatorid       创建者id
	 * @param needchild       是否需要子节点
	 * @param columnService   栏目业务对象
	 */
	public String refPSiteColumn(String nodeid, String pasteIds, String siteId, String creatorid, ColumnService columnService) {
		log.debug("处理粘贴栏目");	
		String pasteId[] = pasteIds.split(",");
		
		String infoMessage = "粘贴成功";
	
		int length = 0; 
		length = pasteId.length;
		String newInfo = this.getMaxColumn(siteId, length);
		if(!StringUtil.isEmpty(newInfo)) {
			return newInfo;
		}
		
		// 粘贴栏目
		Column column  = null;
		for(String strid : pasteId) {
			String pasteid = strid;
			column = columnDao.getAndClear(pasteid);
			column = this.getNewColumn(column, nodeid, siteId);
			this.copyRefColumnToColumns(column, nodeid, siteId, creatorid, columnService);
		}
		return infoMessage;
	}
	
	/**
	 * 粘贴时向栏目表中插入内容
	 * @param column 		   栏目对象
	 * @param parentid 		   父栏目id
	 * @param siteid		   网站id
	 * @param creatorId		   创建者id
	 * @param columnservice    栏目业务对象
	 */
	private Column copyRefColumnToColumns(Column column, String parentid, String siteid, String creatorId, ColumnService columnService){
		log.debug("粘贴时向栏目表中插入内容");
		Column parent = new Column();
		Column newColumn = new Column();
		if(parentid != null) {
			parent.setId(parentid);
			newColumn.setParent(parent);
		}
		newColumn.setName(column.getName());
		newColumn.setDescription(column.getDescription());
		newColumn.setLinkAddress(column.getLinkAddress());
		newColumn.setSelfShowPage(column.getSelfShowPage());
		newColumn.setAudited(column.isAudited());
		newColumn.setDeleted(column.isDeleted());
		newColumn.setUrl(column.getUrl());
		newColumn.setCreateTime(new Date());
		newColumn.setLeaf(true);
		newColumn.setShowInFront(column.isShowInFront());
		newColumn.setChecked(column.isChecked());
		int order = findMaxColumnOrders();
		newColumn.setOrders(order + 1);
		
		//设置引用栏目为父站栏目
		newColumn.setParentSiteColumnId(column.getId());
		
		User creator = new User();
		String creatorid = creatorId; 
		creator.setId(creatorid);
		newColumn.setCreator(creator);
		
		Site site = new Site();
		site.setId(siteid);
		newColumn.setSite(site);
		newColumn.setArticleFormat(column.getArticleFormat());
		
		newColumn.setInfoScore(column.getInfoScore());
		newColumn.setPicScore(column.getPicScore());
		newColumn.setTimeSetting(column.getTimeSetting());
		newColumn.setPublishWay(column.getPublishWay());
		
		newColumn.setColumnType(column.getColumnType());
		columnService.addColumn(newColumn);
		
		// 添加数据库操作日志
		String categoryName = "栏目管理->复制";
		String param = newColumn.getName();
		systemLogDao.addLogData(categoryName, siteid, creatorid, param);
		return newColumn;
	}
	
	/**
	 * 根据栏目id查找同步网站id
	 * @param columnId
	 * @return
	 */
	public String findRefSiteIdByColumnId(String columnId) {
		Column column = columnDao.getAndClear(columnId);
		String refIds = column.getRefColumnIds();
		String refSiteId = "";
		if(refIds != null && !refIds.equals("") && !refIds.equals("0")) {
			String refIdStr[] = refIds.split(",");
			Column refColumn = columnDao.getAndClear(refIdStr[0]);
			refSiteId = refColumn.getSite().getId();
		} else {
			refSiteId = "";
		}
		return refSiteId;
		
	}
	
	/**
	 * 查找同步网站名称
	 * @param siteId
	 * @return refSiteName
	 */
	public String findRefSiteName(String siteId) {
		String refSiteName = "";
		refSiteName = siteDao.getAndClear(siteId).getName();
		return refSiteName;
	}
	
	/**
	 * 根据栏目id查找同步栏目名称和id字符串
	 * @param columnId
	 * @return refColumnNameAndId
	 */
	public String findRefColumnNamesAndIds(String columnId) {
		String refColumnNameAndId = "";
		Column newColumn = columnDao.getAndClear(columnId);
		String refColumnIds = newColumn.getRefColumnIds();
		if(refColumnIds == null || refColumnIds.trim().length() == 0) {
			refColumnIds = null;
		}
		if(refColumnIds != null ) {
			String[] str = refColumnIds.split(",");
			Column column = new Column();
			Site site = new Site();
			String columnName = "";
			for(int i = 0; i < str.length; i++) {
				column = columnDao.getAndClear(str[i]);
				site = siteDao.getAndClear(column.getSite().getId());
				columnName = column.getName() + "【" + site.getName() + "】";
				refColumnNameAndId = refColumnNameAndId + columnName + "," + column.getId() + "::";
			}
			refColumnNameAndId = refColumnNameAndId.substring(0, refColumnNameAndId.length()-2);
		}
		return refColumnNameAndId;
	}
	
	/**
	 * 按照用户id查找同步网站
	 * @param sessionID
	 * @return
	 */
	public List findRefSiteListByUserId(String sessionID){
		User user = userDao.getAndClear(sessionID);
		List list = new ArrayList();
		List<String> siteIds = user.getSiteIds();
		if(!CollectionUtil.isEmpty(siteIds)){
			for(int i = 0; i < siteIds.size(); i++){
				Object[] obj = new Object[2];
				Site site1 = new Site();
				log.debug("siteIds=="+siteIds.get(i));
				site1 = siteDao.getAndClear(siteIds.get(i));
				obj[0] = siteIds.get(i);
				obj[1] = site1.getName();
				list.add(obj);
			}
		}
		return list;
	}
	
	/**
	 * @param columnDao     要设置的 columnDao
	 */
	public void setColumnDao(ColumnDao columnDao) {
		this.columnDao = columnDao;
	}

	/**
	 * @param resourceDao   要设置的 resourceDao
	 */
	public void setResourceDao(ResourceDao resourceDao) {
		this.resourceDao = resourceDao;
	}
	
	/**
	 * @param authorityDao  要设置的 authorityDao
	 */
	public void setAuthorityDao(AuthorityDao authorityDao) {
		this.authorityDao = authorityDao;
	}
	
	/**
	 * @param operationDao 要设置的 operationDao
	 */
	public void setOperationDao(OperationDao operationDao) {
		this.operationDao = operationDao;
	}

	/**
	 * @param siteDao      要设置的 siteDao
	 */
	public void setSiteDao(SiteDao siteDao) {
		this.siteDao = siteDao;
	}

	/**
	 * @param rightDao the rightDao to set
	 */
	public void setRightDao(RightDao rightDao) {
		this.rightDao = rightDao;
	}

	/**
	 * @return the articleFormatDao
	 */
	public ArticleFormatDao getArticleFormatDao() {
		return articleFormatDao;
	}

	/**
	 * @param articleFormatDao the articleFormatDao to set
	 */
	public void setArticleFormatDao(ArticleFormatDao articleFormatDao) {
		this.articleFormatDao = articleFormatDao;
	}

	/**
	 * @param userDao the userDao to set
	 */
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * @param assignmentDao the assignmentDao to set
	 */
	public void setAssignmentDao(AssignmentDao assignmentDao) {
		this.assignmentDao = assignmentDao;
	}

	/**
	 * @param articleDao the articleDao to set
	 */
	public void setArticleDao(ArticleDao articleDao) {
		this.articleDao = articleDao;
	}

	/**
	 * @param publisher the publisher to set
	 */
	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	/**
	 * @param systemLogDao the systemLogDao to set
	 */
	public void setSystemLogDao(SystemLogDao systemLogDao) {
		this.systemLogDao = systemLogDao;
	}

	/**
	 * @param templateInstanceDao the templateInstanceDao to set
	 */
	public void setTemplateInstanceDao(TemplateInstanceDao templateInstanceDao) {
		this.templateInstanceDao = templateInstanceDao;
	}
}
