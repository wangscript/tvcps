/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.biz.templatemanager.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.baize.ccms.biz.articlemanager.dao.ArticleDao;
import com.baize.ccms.biz.articlemanager.dao.ArticleFormatDao;
import com.baize.ccms.biz.articlemanager.domain.Article;
import com.baize.ccms.biz.articlemanager.domain.ArticleFormat;
import com.baize.ccms.biz.columnmanager.dao.ColumnDao;
import com.baize.ccms.biz.columnmanager.domain.Column;
import com.baize.ccms.biz.configmanager.dao.SystemLogDao;
import com.baize.ccms.biz.documentmanager.service.impl.DocumentServiceImpl;
import com.baize.ccms.biz.publishmanager.service.Publisher;
import com.baize.ccms.biz.sitemanager.dao.SiteDao;
import com.baize.ccms.biz.sitemanager.domain.Site;
import com.baize.ccms.biz.templatemanager.dao.TemplateCategoryDao;
import com.baize.ccms.biz.templatemanager.dao.TemplateDao;
import com.baize.ccms.biz.templatemanager.dao.TemplateInstanceDao;
import com.baize.ccms.biz.templatemanager.dao.TemplateUnitCategoryDao;
import com.baize.ccms.biz.templatemanager.dao.TemplateUnitDao;
import com.baize.ccms.biz.templatemanager.domain.Template;
import com.baize.ccms.biz.templatemanager.domain.TemplateCategory;
import com.baize.ccms.biz.templatemanager.domain.TemplateInstance;
import com.baize.ccms.biz.templatemanager.domain.TemplateUnit;
import com.baize.ccms.biz.templatemanager.domain.TemplateUnitCategory;
import com.baize.ccms.biz.templatemanager.service.TemplateUnitService;
import com.baize.ccms.biz.unitmanager.analyzer.ArticleTextAnalyzer;
import com.baize.ccms.biz.unitmanager.analyzer.MagazineCategoryAnalyzer;
import com.baize.ccms.biz.unitmanager.analyzer.OnlineSurverySetAnalyzer;
import com.baize.ccms.biz.unitmanager.analyzer.TemplateUnitAnalyzer;
import com.baize.ccms.biz.unitmanager.analyzer.TitleLinkPageAnalyzer;
import com.baize.ccms.biz.usermanager.dao.RightDao;
import com.baize.ccms.biz.usermanager.domain.Operation;
import com.baize.ccms.biz.usermanager.domain.Resource;
import com.baize.ccms.biz.usermanager.domain.Right;
import com.baize.ccms.sys.GlobalConfig;
import com.baize.ccms.sys.SiteResource;
import com.baize.common.core.util.BeanUtil;
import com.baize.common.core.util.CollectionUtil;
import com.baize.common.core.util.FileUtil;
import com.baize.common.core.util.IDFactory;
import com.baize.common.core.util.SqlUtil;
import com.baize.common.core.util.StringUtil;

/**
 * <p>标题: 模板单元服务实现类</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 模板管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-5-13 上午09:34:07
 * @history（历次修订内容、修订人、修订时间等）
 */
public class TemplateUnitServiceImpl implements TemplateUnitService {
	
	private final Logger log = Logger.getLogger(DocumentServiceImpl.class);
	
	/** 模板转发路径 */
	public String forwardPath;
	
	/** 注入静态模板分析器 */
	private TemplateUnitAnalyzer staticUnitAnalyzer;
	
	/** 注入栏目链接分析器 */
	private TemplateUnitAnalyzer columnLinkAnalyzer;
	
	/** 注入标题链接分析器 */
	private TemplateUnitAnalyzer titleLinkAnalyzer;
	
	/** 注入当前位置分析器 */
	private TemplateUnitAnalyzer currentLocationAnalyzer;
	
	/** 注入图片新闻分析器 */
	private TemplateUnitAnalyzer pictureNewsAnalyzer;
	
	/** 注入最新信息分析器 */
	private TemplateUnitAnalyzer latestInfoAnalyzer;
	
	/** 注入文章正文分析器 */
	private ArticleTextAnalyzer articleTextAnalyzer;
	
	/** 注入期刊（按分类）解析器*/
	private MagazineCategoryAnalyzer magazineCategoryAnalyzer;
	
	/** 注入标题链接分页解析器 */
	private TitleLinkPageAnalyzer titleLinkPageAnalyzer;
	
	/** 注入网上调查解析器 */
	private OnlineSurverySetAnalyzer onlineSurverySetAnalyzer;
	
	/** 注入模板单元Dao */
	private TemplateUnitDao templateUnitDao;
	
	/** 注入模板类别dao **/
	private TemplateCategoryDao templateCategoryDao;
	
	/** 注入模板dao */
	private TemplateDao templateDao;
	
	/** 注入模板实例dao */
	private TemplateInstanceDao templateInstanceDao;
	
	/** 注入栏目dao */
	private ColumnDao columnDao;
	
	/** 注入文章dao */
	private ArticleDao articleDao;
	
	/** 注入网站dao */
	private SiteDao siteDao;
	
	/** 注入单元类别dao */
	private TemplateUnitCategoryDao templateUnitCategoryDao;  
	
	/** 注入静态发布机 */
	private Publisher publisher;
	
	/**注入日志dao*/
	private SystemLogDao systemLogDao;
	
	/** 注入权利dao */
	private RightDao rightDao;
	
	/** 注入格式dao */
	private ArticleFormatDao articleFormatDao;
	
	/**
	 * 查找模板类别
	 * @param siteId        网站id
	 * @return              返回模板类别列表
	 */
	public List<TemplateCategory> findTemplateCategory(String siteId, String sessionID, boolean isUpSystemAdmin, boolean isSiteAdmin) {
		List<TemplateCategory> list = new ArrayList<TemplateCategory>();
		// 系统管理员及以上
		if(isUpSystemAdmin) {
//			list = templateCategoryDao.findAll();
			list = templateCategoryDao.findByNamedQuery("findTemplateCategoryBySiteId", "siteId", siteId);
			
		//	网站管理员
		} else if(isSiteAdmin) {
			list = templateCategoryDao.findByNamedQuery("findTemplateCategoryBySiteId", "siteId", siteId);
			
		//	普通用户
		} else {
			list = templateCategoryDao.findByNamedQuery("findTemplateCategoryBySiteId", "siteId", siteId);
//			String[] params = {"siteId", "creatorId"};
//			Object[] values = {siteId, sessionID};
//			list = templateCategoryDao.findByNamedQuery("findTemplateCategoryBySiteIdAndCreatorId", params, values);
		}
		return list;
	}
	
	/**
	 * 按照网站id查询第一级栏目
	 * @param siteId
	 * @return
	 */
	public List findColumnTempalte(String columnId, String siteId, String creatorId, boolean isUpSiteAdmin) {
		List<Column> list = new ArrayList<Column>();
		if(isUpSiteAdmin){
			List<Column> list1 = new ArrayList<Column>();
			if(StringUtil.isEmpty(columnId) || columnId.equals("0")){
				list1 = columnDao.findByNamedQuery("findFirstColumnBySystemAdmin", "siteId", siteId);
			}else{
				String[] params1 = {"siteId", "columnId"};
				Object[] values1 ={siteId, columnId};
				list1 = columnDao.findByNamedQuery("findColumnBySiteIdAndColumnId", params1, values1);
			}
			Column column = null;
			for(int i = 0; i < list1.size(); i++) {
				column = list1.get(i);
				column.setDescription("yes");
				list.add(column);
			}
		}else{
			String isColumnTemplateSet = "no";
			// 查询下级有哪些栏目
			List list1 = this.findTemplateSetColumnTree(siteId, columnId, creatorId, isUpSiteAdmin);
			// 查看哪些栏目可以进行模版设置
			if(!CollectionUtil.isEmpty(list1)){
				Column column = null;
				for(int i = 0; i < list1.size(); i++) {
					Object[] obj = new Object[6];
					obj = (Object[]) list1.get(i);
					String columnid = String.valueOf(obj[0]);
					column = columnDao.getAndNonClear(columnid);
					if(column.getCreator().getId().equals(creatorId) && !isUpSiteAdmin){
						isColumnTemplateSet = "yes";
					}else{
						log.debug("name==="+obj[1]);
						String str1[] = {"itemType", "itemId", "userId", "siteId", "types"};
						Object obj1[] = {Resource.TYPE_COLUMN, columnid, creatorId, siteId, Operation.TYPE_TEMPLATESET};
						List list2 = rightDao.findByNamedQuery("findRightByUserIdAndItemIdAndItemTypeAndSiteIdAndOperationType", str1, obj1);
						// 判断此栏目是否有模版设置权限
						if(!CollectionUtil.isEmpty(list2)){
							isColumnTemplateSet = "yes";
						} else{
							isColumnTemplateSet = this.findParentIsColumnTempateSet(columnid, siteId, creatorId);
						}
					}
					column.setDescription(isColumnTemplateSet);
					if(isColumnTemplateSet.equals("yes")){
						if(column.getArticleTemplate() != null) {
							TemplateInstance articleTemplate = column.getArticleTemplate();
							String instanceId = articleTemplate.getId();
							articleTemplate = templateInstanceDao.getAndClear(instanceId);
							column.setArticleTemplate(articleTemplate);
						}
						if(column.getColumnTemplate() != null) {
							TemplateInstance columnTemplate = column.getColumnTemplate();
							String instanceId = columnTemplate.getId();
							columnTemplate = templateInstanceDao.getAndClear(instanceId);
							column.setColumnTemplate(columnTemplate);
						}
					}
					list.add(column);
				}
			}
		}
		return list;
	}
	
	
	
	
	/**
	 * 按照网站id或者栏目id查找模版设置栏目树(根据不同的角色来判断显示内容)
	 * @param siteId 	    网站id
	 * @param columnId 	    栏目id
	 * @param sessionId     当前登陆用户的id
	 * @return			    返回List
	 */
	@SuppressWarnings("unchecked")
	private List findTemplateSetColumnTree(String siteId, String columnId, String sessionId, boolean isUpSiteAdmin) {
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
		StringBuilder columnids = new StringBuilder();
		for(int i = 0 ; i < rightList.size() ; i++){
			right = (Right)rightList.get(i);
			this.getParentColumnId(columnids, right.getAuthority().getResource().getIdentifier(), siteId);	 
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
	 * 递归获取这个栏目的父栏目ID
	 * @param columnList 第一级栏目的集合
	 * @param columnids 栏目ID集合
	 * @return 返回所有的子栏目ID集合，以逗号隔开
	 */
	private void getParentColumnId(StringBuilder columnids, String columnId,String siteId){	
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
				getParentColumnId(columnids, columnid,siteId);							
			}else{
				columnids.append(",0");
			}
		}else{
			columnids.append(",0");
		}	
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

		StringBuilder columnids = new StringBuilder();
		//先把自己加进去
		columnids.append(columnId);
		this.getParentColumnId(columnids, columnId, siteId);
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
					/*//则没有对这个栏目设置权限，对文章或者模板设置设置了权限
					newDataList = allDataList1;*/
					
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
 	 * 按照网站id和栏目id查找栏目模板
 	 * @param siteId
 	 * @param columnId
 	 * @return
 	 */
 	public List findColumnTempateByColumnId(String columnId, String creatorId, String siteId, boolean isUpSiteAdmin) {
 		Column column = columnDao.getAndNonClear(columnId);
 		List list = new ArrayList();
 		if(column != null) {
 			if(isUpSiteAdmin) {
				list = columnDao.findByNamedQuery("findColumnByParentId", "pid", column.getId());
 			} else {
 				String[] params = {"pid" ,"siteId", "creatorId"};
 				Object[] values = {columnId, siteId, creatorId};
 				list = columnDao.findByNamedQuery("findColumnBySiteIdAndCreatorIdAndPid", params, values);
 			}
 		}
 		return list;
 	}
 	
	/**
     * 查找栏目模板
     * @param columnId 栏目id
     * @return         返回栏目对象
     */
	public Column findColumnTemplate(String columnId) {
		List<Object> list = (List) columnDao.findByNamedQuery("findTemplateByColumnId", "columnId", columnId);
		Column column = new Column();
		Object[] object = new Object[4];
		object = (Object[])list.get(0);
		TemplateInstance articleTemplate = new TemplateInstance();
		TemplateInstance columnTemplate = new TemplateInstance();
		column.setArticleTemplate(null);
		column.setColumnTemplate(null);
		// 文章模板不为空
		if(object[0] != null) {
			articleTemplate.setId(String.valueOf(object[0]));
		    articleTemplate.setName(String.valueOf(object[1]));
		    column.setArticleTemplate(articleTemplate);
		}
		// 栏目模板不为空
		if(object[2] != null) {
			columnTemplate.setId(String.valueOf(object[2]));
			columnTemplate.setName(String.valueOf(object[3]));
			column.setColumnTemplate(columnTemplate);
		}
		Column newColumn = columnDao.getAndClear(columnId);
		column.setId(newColumn.getId());
		column.setColumnType(newColumn.getColumnType());
		
		return column;
	}
	
	/**
     * 选择模板
     * @param type               类别用于判断是网站的还是栏目的
     * @param nodeId			  节点id（有可能是栏目id）
     * @param siteId             网站id
     * @param templateInstanceId 模板实例id
     */
	public String modifyTemplateChoose(String type, String nodeId, String siteid, String templateInstanceId, String sessionID) {
		TemplateInstance templateInstance = new TemplateInstance();
		templateInstance.setId(templateInstanceId);
		
		String categoryName = "";
		String newInstanceId = "";
		// 网站下的首页模板
		if(type.equals("1")) {
			Site site = siteDao.getAndNonClear(siteid);
			if(site.getHomeTemplate() != null) {
				newInstanceId = site.getHomeTemplate().getId();
			}
			site.setHomeTemplate(templateInstance);
			siteDao.clearCache();
			siteDao.update(site);
			categoryName = "模板设置->选择网站首页";
			
		// 网站下的文章模板	
		} else if(type.equals("2")) {
			Site site = siteDao.getAndNonClear(siteid);
			if(site.getArticleTemplate() != null) {
				newInstanceId = site.getArticleTemplate().getId();
			}
			site.setArticleTemplate(templateInstance);
			siteDao.clearCache();
			siteDao.update(site);
			categoryName = "模板设置->选择网站文章页";
			
		// 网站下的栏目模板	
		} else if(type.equals("3")) {
			Site site = siteDao.getAndNonClear(siteid);
			if(site.getColumnTemplate() != null) {
				newInstanceId = site.getColumnTemplate().getId();
			}
			site.setColumnTemplate(templateInstance);
			siteDao.clearCache();
			siteDao.update(site);
			categoryName = "模板设置->选择网站栏目页";
			
		// 栏目下的文章模板	
		} else if(type.equals("4")) {
			Column column = columnDao.getAndNonClear(nodeId);
			if(column.getArticleTemplate() != null) {
				newInstanceId = column.getArticleTemplate().getId();
			}
			column.setArticleTemplate(templateInstance);
			columnDao.clearCache();
			columnDao.update(column);
			categoryName = "模板设置（栏目）->选择文章页";
			
		// 栏目下的栏目模板		
		} else if(type.equals("5")) {
			Column column = columnDao.getAndNonClear(nodeId);
			if(column.getColumnTemplate() != null) {
				newInstanceId = column.getColumnTemplate().getId();
			}
			column.setColumnTemplate(templateInstance);
			columnDao.clearCache();
			columnDao.update(column);
			categoryName = "模板设置（栏目）->选择栏目页";
		}
		if(!StringUtil.isEmpty(newInstanceId)) {
			TemplateInstance newTemplateInstance = templateInstanceDao.getAndClear(newInstanceId);
			newTemplateInstance.setUsedNum(newTemplateInstance.getUsedNum() - 1);
			templateInstanceDao.update(newTemplateInstance);
		}
		
		// 选择后模板实例被使用的次数加 1
		templateInstance = templateInstanceDao.getAndClear(templateInstanceId);
		templateInstance.setUsedNum(templateInstance.getUsedNum() + 1);
		templateInstanceDao.update(templateInstance);
		
		// 写入日志文件
		String param = templateInstance.getName();
		systemLogDao.addLogData(categoryName, siteid, sessionID, param);
		
		return templateInstance.getName();
	}
	
	/**
     * 按照网站id查找网站
     * @param siteId  网站id
     * @return        返回网站对象
     */
	public Site findSiteBySiteId(String siteId) {
		List<Object> list = (List)siteDao.findByNamedQuery("findSiteBySiteId", "siteId", siteId);
		Site site = new Site();
		Object[] object = new Object[6];
		object = (Object[])list.get(0);
		TemplateInstance homeTemplate = new TemplateInstance();
		TemplateInstance articleTemplate = new TemplateInstance();
		TemplateInstance columnTemplate = new TemplateInstance();
		site.setHomeTemplate(null);
		site.setArticleTemplate(null);
		site.setHomeTemplate(null);
		// 主页模板不为空
		if(object[0] != null) {
			homeTemplate.setId(String.valueOf(object[0]));
			homeTemplate.setName(String.valueOf(object[1]));
			site.setHomeTemplate(homeTemplate);
		} 
		// 文章模板不为空
		if(object[2] != null) {
			articleTemplate.setId(String.valueOf(object[2]));
		    articleTemplate.setName(String.valueOf(object[3]));
			site.setArticleTemplate(articleTemplate);
		}
		// 栏目模板不为空
		if(object[4] != null) {
			columnTemplate.setId(String.valueOf(object[4]));
			columnTemplate.setName(String.valueOf(object[5]));
			site.setColumnTemplate(columnTemplate);
		}
		return site;
	}
	
	/**
 	 * 查找模版设置是否有权限
 	 * @param nodeId
 	 * @param siteId
 	 * @param sessionID
 	 * @return
 	 */
 	public String findColumnTemplateSet(String nodeId, String siteId, String sessionID) {
 		String isColumnTemplateSet = "no";
 		if(StringUtil.isEmpty(nodeId)) {
 			nodeId = "0";
 		}
		List list = null;
		// 网站是否有模版设置权限
		if(nodeId.equals("0")) {
			String str[] = {"itemType", "itemId", "userId", "siteId", "types"};
			Object obj[] = {Resource.TYPE_COLUMN, nodeId, sessionID, siteId, Operation.TYPE_TEMPLATESET};
			list = rightDao.findByNamedQuery("findRightByUserIdAndItemIdAndItemTypeAndSiteIdAndOperationType", str, obj);
			if(!CollectionUtil.isEmpty(list)) {
				isColumnTemplateSet = "yes";
			}else{
				isColumnTemplateSet = "no";
			}
			
		// 栏目是否有设置权限 	
		}else{
			String[] params = {"siteId", "columnId"};
			Object[] values = {siteId, nodeId};
			List list1 = columnDao.findByNamedQuery("findColumnByColumnIdAndSiteId", params, values);
			if(!CollectionUtil.isEmpty(list1)){
				Column column = (Column) list1.get(0);
				if(column != null) {
					// 查看栏目是否是用户自己添加的栏目
					if(column.getCreator().getId().equals(sessionID)){
						isColumnTemplateSet = "yes";
						
					// 查看用户是否有本栏目权限
					}else{
						String str[] = {"itemType", "itemId", "userId", "siteId", "types"};
						Object obj[] = {Resource.TYPE_COLUMN, nodeId, sessionID, siteId, Operation.TYPE_TEMPLATESET};
						list = rightDao.findByNamedQuery("findRightByUserIdAndItemIdAndItemTypeAndSiteIdAndOperationType", str, obj);
						if(!CollectionUtil.isEmpty(list)){
							isColumnTemplateSet = "yes";
						}else{
							isColumnTemplateSet = this.findParentIsColumnTempateSet(nodeId, siteId, sessionID);
						}
					}
				}
			}else{
				isColumnTemplateSet = this.findParentIsColumnTempateSet(nodeId, siteId, sessionID);
			}
		}
		return isColumnTemplateSet;
 	}

 	/**
	 * 递归获取这个栏目的父栏目ID
	 * @param columnList 第一级栏目的集合
	 * @param columnids 栏目ID集合
	 * @return 返回所有的子栏目ID集合，以逗号隔开
	 */
	private String findParentIsColumnTempateSet(String columnId, String siteId, String sessionID){	
		String isColumnTemplateSet = "no";
		if(StringUtil.isEmpty(columnId) || columnId.equals("0")){
			columnId = "0";
			String str[] = {"itemType", "itemId", "userId", "siteId", "types"};
			Object obj[] = {Resource.TYPE_COLUMN, columnId, sessionID, siteId, Operation.TYPE_TEMPLATESET};
			List list = rightDao.findByNamedQuery("findRightByUserIdAndItemIdAndItemTypeAndSiteIdAndOperationType", str, obj);
			if(!CollectionUtil.isEmpty(list)){
				Right right = (Right) list.get(0);
				log.debug("is==="+right.getAuthority().isColumnExtends());
				if(right.getAuthority().isColumnExtends()){
					isColumnTemplateSet = "yes";
				}
			}
		}else{
			String str[] = {"siteId","columnId"};
			Object obj[] = {siteId,columnId};
			List columnList = columnDao.findByNamedQuery("findColumnBySiteIdAndCurColumnId", str, obj);
			Column column = null;
			if (!CollectionUtil.isEmpty(columnList)) {		 
				column = (Column) columnList.get(0);
				if(column != null) {
					if(column.getParent() != null) {
						columnId = column.getParent().getId();
						String str1[] = {"itemType", "itemId", "userId", "siteId", "types"};
						Object obj1[] = {Resource.TYPE_COLUMN, columnId, sessionID, siteId, Operation.TYPE_TEMPLATESET};
						List list = rightDao.findByNamedQuery("findRightByUserIdAndItemIdAndItemTypeAndSiteIdAndOperationType", str1, obj1);
						if(!CollectionUtil.isEmpty(list)){
							Right right = (Right) list.get(0);
							if(right.getAuthority().isColumnExtends()){
								isColumnTemplateSet = "yes";
							}else{
								isColumnTemplateSet = this.findParentIsColumnTempateSet(columnId, siteId, sessionID);
							}
						}else{
							isColumnTemplateSet = this.findParentIsColumnTempateSet(columnId, siteId, sessionID);
						}
					}else{
						isColumnTemplateSet = this.findParentIsColumnTempateSet("0", siteId, sessionID);
					}
				}
			}	
		}
		return isColumnTemplateSet;
	}
	
	 /**
     * 处理模板选择
     * @param templateCategoryList 模板类别列表
     * @param templateUnitService  模板单元业务对象
     * @return						返回模板实例的字符串
     */
	public String chooseTemplate(List<TemplateCategory> templateCategoryList, String siteId, String sessionID, boolean isUpSystemAdmin, boolean isSiteAdmin) {
		String templateInstanceStr = "";
		List<Template> templateList = new ArrayList<Template>();
		List<TemplateInstance> templateInstanceList = new ArrayList<TemplateInstance>();
		// 将模板类别id和模板实例id，name，url拼成字符串，形如：templateCateogryId####templateInstanceId,,templateInstanceName,,templateInstacneUrl::::
		if(templateCategoryList.size() > 0) {
			for(int j = 0; j < templateCategoryList.size(); j++) {
				String templateCategoryId = templateCategoryList.get(j).getId();
				if(isUpSystemAdmin) {
					//templateList = templateDao.findByNamedQuery("findTemplateByTemplateCategoryId", "templateCategoryId", templateCategoryId);
					String[] params = {"templateCategoryId", "siteId"};
					Object[] values = {templateCategoryId, siteId};
					templateList = templateDao.findByNamedQuery("findTemplateByTemplateCategoryIdAndSiteId", params, values);
				} else if(isSiteAdmin) {
					String[] params = {"templateCategoryId", "siteId"};
					Object[] values = {templateCategoryId, siteId};
					templateList = templateDao.findByNamedQuery("findTemplateByTemplateCategoryIdAndSiteId", params, values);
				} else{
					String[] params = {"templateCategoryId", "siteId"};
					Object[] values = {templateCategoryId, siteId};
					templateList = templateDao.findByNamedQuery("findTemplateByTemplateCategoryIdAndSiteId", params, values);
					/*String[] params = {"templateCategoryId", "siteId", "creatorId"};
					Object[] values = {templateCategoryId, siteId, sessionID};
					templateList = templateDao.findByNamedQuery("findTemplateByTemplateCategoryIdAndSiteIdAndCreatorId", params, values);*/
				}
				if(templateList.size() > 0) {
					Template template = null;
					for(int t = 0; t < templateList.size(); t++) {
						template = templateList.get(t);
						String templateId = template.getId();
						templateInstanceList = templateInstanceDao.findByNamedQuery("findTemplateInstanceByTemplateId", "templateId", templateId);
						if(templateInstanceList.size() > 0) {
							for(int m = 0; m < templateInstanceList.size(); m++) {
								String templateInstanceId = templateInstanceList.get(m).getId();
								String templateInstanceName = templateInstanceList.get(m).getName();
								String name = template.getName() + "&nbsp;【&nbsp;" + templateInstanceName + "&nbsp;】";
								String templateInstanceUrl = templateInstanceList.get(m).getUrl();
								if(templateInstanceStr == "" || templateInstanceStr == null) {
									templateInstanceStr += templateCategoryId + "####" + templateInstanceId + ",," + name + ",," + templateInstanceUrl;
								} else {
									templateInstanceStr += "::::" + templateCategoryId + "####" + templateInstanceId + ",," + name + ",," + templateInstanceUrl;
								}
							}
						} 
					}
				} 
			}
		}
		return templateInstanceStr;
	}
	
	/**
 	 * 查找模版类别和模版
 	 * @param templateCategoryList
 	 * @param siteId
 	 * @param sessionID
 	 * @param isUpSystemAdmin
 	 * @param isSiteAdmin
 	 * @return
 	 */
 	public String findTemplateCategoryAndTemplate(List<TemplateCategory> templateCategoryList, String siteId, String sessionID, boolean isUpSystemAdmin, boolean isSiteAdmin) {
		List<Template> templateList = new ArrayList<Template>();
		String templateStr = "";
		List<TemplateInstance> templateInstanceList = new ArrayList<TemplateInstance>();
		// 将模板类别id和模板实例id，name，url拼成字符串，形如：templateCateogryId####templateInstanceId,,templateInstanceName,,templateInstacneUrl::::
		if(templateCategoryList != null && templateCategoryList.size() > 0) {
			for(int j = 0; j < templateCategoryList.size(); j++) {
				String templateCategoryId = templateCategoryList.get(j).getId();
				if(isUpSystemAdmin) {
					String[] params = {"templateCategoryId", "siteId"};
					Object[] values = {templateCategoryId, siteId};
					templateList = templateDao.findByNamedQuery("findTemplateByTemplateCategoryIdAndSiteId", params, values);
				} else if(isSiteAdmin) {
					String[] params = {"templateCategoryId", "siteId"};
					Object[] values = {templateCategoryId, siteId};
					templateList = templateDao.findByNamedQuery("findTemplateByTemplateCategoryIdAndSiteId", params, values);
				} else{
					String[] params = {"templateCategoryId", "siteId"};
					Object[] values = {templateCategoryId, siteId};
					templateList = templateDao.findByNamedQuery("findTemplateByTemplateCategoryIdAndSiteId", params, values);
					
					/*String[] params = {"templateCategoryId", "siteId", "creatorId"};
					Object[] values = {templateCategoryId, siteId, sessionID};
					templateList = templateDao.findByNamedQuery("findTemplateByTemplateCategoryIdAndSiteIdAndCreatorId", params, values);*/
				}
				if(templateList.size() > 0) {
					Template template = null;
					for(int t = 0; t < templateList.size(); t++) {
						template = templateList.get(t);
						String templateId = template.getId();
						String templateName = template.getName();
						String templateUrl = template.getUrl();
						if(templateStr == "" || templateStr == null) {
							templateStr += templateCategoryId + "####" + templateId + ",," + templateName + ",," + templateUrl;
						} else {
							templateStr += "::::" + templateCategoryId + "####" + templateId + ",," + templateName + ",," + templateUrl; 
						}
					}
				} 
			}
		}
		return templateStr;
 	}
	
	 /**
     * 撤销选择的模板
     * @param siteId  				网站id
     * @param type   				类别用于判断是网站的还是栏目的
     * @param nodeId  				节点id（有可能是栏目id）
     * @param templateInstanceId	要取消的模板实例id
     */
	public void modifyCancelTemplate(String siteid, String type, String nodeId, String templateInstanceId, String sessionID) {
		Site site = siteDao.getAndClear(siteid);
		
		String categoryName = "";
		
		// 网站下的首页模板
		if(type.equals("1")) {
			site.setHomeTemplate(null);
			siteDao.update(site);
			categoryName = "模板设置->撤销网站首页";
			
		// 网站下的文章模板	
		} else if(type.equals("2")) {
			site.setArticleTemplate(null);
			siteDao.update(site);
			categoryName = "模板设置->撤销网站文章页";
			
		// 网站下的栏目模板	
		} else if(type.equals("3")) {
			site.setColumnTemplate(null);
			siteDao.update(site);
			categoryName = "模板设置->撤销网站栏目页";
			
		// 栏目下的文章模板	
		} else if(type.equals("4")) {
			Column column = columnDao.getAndClear(nodeId);
			column.setArticleTemplate(null);
			columnDao.update(column);
			categoryName = "模板设置（栏目）->撤销文章页";
			
		// 栏目下的栏目模板		
		} else if(type.equals("5")) {
			Column column = columnDao.getAndClear(nodeId);
			column.setColumnTemplate(null);
			columnDao.update(column);
			categoryName = "模板设置（栏目）->撤销栏目页";
		}
		// 取消模板时将模板实例被使用的次数减 1
		TemplateInstance templateInstance = templateInstanceDao.getAndClear(templateInstanceId);
		templateInstance.setUsedNum(templateInstance.getUsedNum() - 1);
		templateInstanceDao.update(templateInstance);
		
		// 写入日志文件
		String param = templateInstance.getName();
		systemLogDao.addLogData(categoryName, siteid, sessionID, param);
	}
	
	public void addTempateUnit(TemplateUnit templateUnit) {
		templateUnitDao.save(templateUnit);
	}
	
	/**
	 * 根据模板实例获取替换后的模板
	 * @param tempateInstanceId 模板实例ID
	 */
	public String getReplacementTemplate(String templateType, String tempateInstanceId,  String columnId, String siteId) {
		String rs = "";
		TemplateInstance template = templateInstanceDao.getAndClear(tempateInstanceId);
		String articleId = null;
		
		List articleList = new ArrayList();
	/*	// 确定栏目ID和文章ID
		if (StringUtil.isEmpty(columnId) || columnId.equals("0")) { 
			// 找默认格式栏目
			String[] params = {"siteId", "formatId"};
			Object[] values = {siteId, "f1"};
			List<Column> list = columnDao.findByNamedQuery("findColumnByDefaultFormatIdAndSiteId", params, values, 0, 1);
			if(!CollectionUtil.isEmpty(list)) {
				columnId = list.get(0).getId();
				// 根据栏目ID查找该栏目下一篇被审核文章
				articleList = articleDao.findByNamedQuery("findAuditedArticlesByColumnId", "columnId", columnId);
			} 
			
		} else {
			// 根据栏目ID查找该栏目下一篇被审核的文章
			articleList = articleDao.findByNamedQuery("findAuditedArticlesByColumnId", "columnId", columnId);
		}*/
		
		if (StringUtil.isEmpty(columnId) || columnId.equals("0")) { 
			boolean defaultFormat = true; 
			ArticleFormat articleFormat = new ArticleFormat(); 
			List list1 = articleFormatDao.findByNamedQuery("findArticleFormatByDefaultFormat", "defaultFormat", defaultFormat);
			if(list1 != null && list1.size() > 0) {
				articleFormat = (ArticleFormat) list1.get(0);
				String articleFormatId = articleFormat.getId();
				boolean deleted = false;
				String[] params1 = {"articleFormatId", "deleted"};
				Object[] values1 = {articleFormatId, deleted};
				articleList = articleDao.findByNamedQuery("findAuditArticleByArticleFormatId", params1, values1);
				if(!CollectionUtil.isEmpty(articleList)) { 
					Object[] objects =  new Object[2];
					objects = (Object[]) articleList.get(0);
					Article article = (Article) objects[0];
					articleId = article.getId();
					columnId = article.getColumn().getId();
				}
				if(StringUtil.isEmpty(columnId) || columnId.equals("0")){
					// 找默认格式栏目
					String[] params = {"siteId", "formatId"};
					Object[] values = {siteId, "f1"};
					List<Column> list = columnDao.findByNamedQuery("findColumnByDefaultFormatIdAndSiteId", params, values, 0, 1);
					if(!CollectionUtil.isEmpty(list)) {
						columnId = list.get(0).getId();
					} 
				}
			}
		}else{
			// 根据栏目ID查找该栏目下一篇被审核的文章
			articleList = articleDao.findByNamedQuery("findAuditedArticlesByColumnId", "columnId", columnId);
			if(!CollectionUtil.isEmpty(articleList)) {
				articleId = ((Article)articleList.get(0)).getId();
			}
		}
		
		
		/*if(!CollectionUtil.isEmpty(articleList)) {
			articleId = articleList.get(0).getId();
		}*/
		
		// 1.组织替换内容
		Map<String,String> replaceMap = new HashMap<String,String>();
		Map<String,String> namesMap = new HashMap<String,String>();
		List<TemplateUnit> units = templateUnitDao.findByNamedQuery("findTemplateUnitByInstanceId", "id", tempateInstanceId);
		List<TemplateUnitCategory> categories = templateUnitCategoryDao.findAll();
		Map<String,String> categoryMap = new HashMap<String,String>();
		for (TemplateUnitCategory category : categories) {
			String categoryId = String.valueOf(category.getId());
			categoryMap.put(categoryId, category.getName());
		}
		
		for (TemplateUnit unit : units) {
			String unitId = String.valueOf(unit.getId());
			String categoryId = "";
			if (unit.getTemplateUnitCategory() != null) {
				categoryId = unit.getTemplateUnitCategory().getId();
				namesMap.put(unitId, categoryMap.get(categoryId+""));
			}
			replaceMap.put(unitId, unit.getHtml());
		}
		
		// 公共标签
		Map<String,String> commonLabel = new HashMap<String,String>();

		// 2.读取模板文件内容
		String templateStr = FileUtil.read(GlobalConfig.appRealPath + template.getLocalPath());
		//应用名
		String appName = "var app = '"+GlobalConfig.appName+"';";
		//String script = FileUtil.read(GlobalConfig.appRealPath + "/public/config/script.js");
		
		StringBuilder enjoinRightJs = new StringBuilder();
		enjoinRightJs.append("if (window.Event)");
		enjoinRightJs.append("	document.captureEvents(Event.MOUSEUP);");
		enjoinRightJs.append("function nocontextmenu() {");
		enjoinRightJs.append("	event.cancelBubble = true");
		enjoinRightJs.append("	event.returnValue = false;");
		enjoinRightJs.append("	return false;");
		enjoinRightJs.append("}");
		enjoinRightJs.append("function norightclick(e) {");
		enjoinRightJs.append("	if (window.Event) {");
		enjoinRightJs.append("		if (e.which == 2 || e.which == 3)");
		enjoinRightJs.append("			return false;");
		enjoinRightJs.append("	} else if (event.button == 2 || event.button == 3) {");
		enjoinRightJs.append("		event.cancelBubble = true");
		enjoinRightJs.append("		event.returnValue = false;");
		enjoinRightJs.append("		return false;");
		enjoinRightJs.append("	}");
		enjoinRightJs.append("}");
		enjoinRightJs.append("document.oncontextmenu = nocontextmenu; // for IE5+\n");
		enjoinRightJs.append("document.onmousedown = norightclick; // for all others\n"); 
		
		StringBuilder rightMenuJs = new StringBuilder();
		String templateUrl = "/"+GlobalConfig.appName+"/templateUnit.do?dealMethod=";
		rightMenuJs.append("\\$(document).ready(function() {");
		rightMenuJs.append("      \\$('div._unitcls').contextMenu('myMenu1', {");
		rightMenuJs.append("        bindings: {");
		rightMenuJs.append("          'copy': function(t) {");
		rightMenuJs.append("            var unitId = \\$(t).attr(\"uid\");");
/*		rightMenuJs.append("			\\$.ajax({");
		rightMenuJs.append("				url:'").append(templateUrl+"copy&unitId='+unitId").append(";");
		rightMenuJs.append("				success:function(msg){");
		rightMenuJs.append("					if (msg != 'copySuccess') {alert('复制失败！');}else{alert('复制成功！');}");
		rightMenuJs.append("				}");
		rightMenuJs.append("			});");*/
		rightMenuJs.append("			window.location.href = '").append(templateUrl+"copy&unitId='+unitId").append(";");
		rightMenuJs.append("          },");
		rightMenuJs.append("          'paste': function(t) {");
		rightMenuJs.append("            var unitId = \\$(t).attr(\"uid\");");
		rightMenuJs.append("			window.location.href = '").append(templateUrl+"paste&unitId='+unitId").append(";");
		rightMenuJs.append("          },");
		rightMenuJs.append("          'delete': function(t) {");
		rightMenuJs.append("            var unitId = \\$(t).attr(\"uid\");");
		rightMenuJs.append("			window.location.href = '").append(templateUrl+"cancel&unitId='+unitId;");
		rightMenuJs.append("          },");
		rightMenuJs.append("          'refresh': function(t) {");
		rightMenuJs.append("			window.location.href = \"").append(templateUrl+"templateSet").append("\";");
		rightMenuJs.append("          },");
		rightMenuJs.append("          'preview': function(t) {");
		rightMenuJs.append("            var columnId = \\$(\"#columnId\").val();");
		rightMenuJs.append("            var instanceId = \\$(\"#instanceId\").val();");
		rightMenuJs.append("            var templateType = \\$(\"#templateType\").val();");
		rightMenuJs.append("            var articleId = \\$(\"#articleId\").val();");
		rightMenuJs.append("		    openWindow('").append(templateUrl+"preview&columnId='+columnId+'&instanceId='+instanceId+'&templateType='+templateType+'&articleId='+articleId").append(",\"预览\",8000,5000,0,0);");
		rightMenuJs.append("          }");
		rightMenuJs.append("        }");
		rightMenuJs.append("      });");
		rightMenuJs.append("    });\n");

		String script = rightMenuJs +
				 "function openSetWin(unitId,colId) {" + 
		 			 "var instanceId = document.getElementById(\"instanceId\").value;" +
		 			 "var articleId = document.getElementById(\"articleId\").value;" +
		 			 "var url = \"/"+GlobalConfig.appName+"/templateUnit.do?dealMethod=getUnitForm&templateType="+templateType+"&unitId=\" + unitId + \"&instanceId=\" + instanceId + \"&columnId=\" + colId+\"&articleId=\"+articleId;" +
					 "win = showWindow(\"unitsWin\",\"单元设置\",url,230, 20,850,600);" +
				 "}";
		String /*hidden = FileUtil.read(GlobalConfig.appRealPath + "/public/config/hidden.txt");*/
		hidden = "<input type=\"hidden\" id=\"siteId\" name=\"siteId\" value=\""+ siteId +"\">\n" +
				 "<input type=\"hidden\" id=\"instanceId\" name=\"instanceId\" value=\""+tempateInstanceId+"\">\n" +
				 "<input type=\"hidden\" id=\"articleId\" name=\"articleId\" value=\""+articleId+"\">\n" +
				 "<input type=\"hidden\" id=\"columnId\" name=\"columnId\" value=\""+columnId+"\">\n" +
				 "<input type=\"hidden\" id=\"templateType\" name=\"templateType\" value=\""+templateType+"\">\n";
		
		StringBuilder menu = new StringBuilder();
		menu.append("<div class=\"contextMenu\" id=\"myMenu1\">");
		menu.append("    <ul>");
		menu.append("      <li id=\"copy\"><img src=\"/"+GlobalConfig.appName+"/script/menu/copy.png\" />复制</li>");
		menu.append("      <li id=\"paste\"><img src=\"/"+GlobalConfig.appName+"/script/menu/paste.png\" />粘贴</li>");
		menu.append("      <li id=\"delete\"><img src=\"/"+GlobalConfig.appName+"/script/menu/delete.png\" />删除</li>");
		menu.append("      <li id=\"preview\"><img src=\"/"+GlobalConfig.appName+"/script/menu/preview.png\" />预览</li>");
		menu.append("      <li id=\"refresh\"><img src=\"/"+GlobalConfig.appName+"/script/menu/refresh.png\" />刷新</li>");
		menu.append("    </ul>");
		menu.append("</div>");
		
		String css = /*"<link rel=\"stylesheet\" type=\"text/css\" href=\"/"+ GlobalConfig.appName +"/css/dhtmlx.css\"/>\n" +
					 "<link rel=\"stylesheet\" type=\"text/css\" href=\"/"+ GlobalConfig.appName +"/images/window/skins/dhtmlxwindows_dhx_blue.css\"/>\n" +
					 "<link rel=\"stylesheet\" type=\"text/css\" href=\"/"+ GlobalConfig.appName +"/images/grid/skins/dhtmlxgrid_dhx_blue.css\"/>\n" + 
					 "<link rel=\"stylesheet\" type=\"text/css\" href=\"/"+ GlobalConfig.appName +"/images/layout/skins/dhtmlxlayout_dhx_blue.css\"/>\n" + 
		 			*/
					"<link rel=\"stylesheet\" type=\"text/css\" href=\"/"+ GlobalConfig.appName +"/css/pagination.css\"/>\n" + 
					 "<link rel=\"stylesheet\" type=\"text/css\" href=\"/"+ GlobalConfig.appName +"/css/ajaxpagination.css\"/>\n" +
					 "<link rel=\"stylesheet\" type=\"text/css\" href=\"/"+ GlobalConfig.appName +"/css/style.css\"/>\n" ;
//					 "<link rel=\"stylesheet\" type=\"text/css\" href=\"/"+ GlobalConfig.appName +"/script/jquery-tooltip/css/tooltip.css\"/>\n" + 
//					 "<link rel=\"stylesheet\" type=\"text/css\" href=\"/"+ GlobalConfig.appName +"/script/ext/resources/css/ext-all.css\"/>\n";
					 
		String js = //"<script type=\"text/javascript\" src=\"/"+ GlobalConfig.appName +"/script/dhtmlx.js\"></script>\n" +
 					SiteResource.getPreviewFrontScript(true)+
					"<script type=\"text/javascript\" src=\"/"+ GlobalConfig.appName +"/script/jquery.form.js\"></script>\n" +
					"<script type=\"text/javascript\" src=\"/"+ GlobalConfig.appName +"/script/util.js\"></script>\n" +
//					"<script type=\"text/javascript\" src=\"/"+ GlobalConfig.appName +"/script/ext/adapter/ext/ext-base.js\"></script>\n" +
//					"<script type=\"text/javascript\" src=\"/"+ GlobalConfig.appName +"/script/ext/ext-all-debug.js\"></script>\n" +
					"<script type=\"text/javascript\" src=\"/"+ GlobalConfig.appName +"/script/fckeditor/fckeditor.js\"></script>\n" +
					"<script type=\"text/javascript\" src=\"/"+ GlobalConfig.appName +"/script/My97DatePicker/WdatePicker.js\"></script>\n" +
					"<script type=\"text/javascript\" src=\"/"+ GlobalConfig.appName +"/script/jquery.pagination.js\"></script>\n" +
					"<script type=\"text/javascript\" src=\"/"+ GlobalConfig.appName +"/script/jquery.blockUI.js\"></script>\n" +
//					"<script type=\"text/javascript\" src=\"/"+ GlobalConfig.appName +"/script/jquery-tooltip/js/tooltip.js\"></script>\n" +
					"<script type=\"text/javascript\" src=\"/"+ GlobalConfig.appName +"/script/jquery.funkyUI.js\"></script>\n" +
					"<script type=\"text/javascript\" src=\"/"+ GlobalConfig.appName +"/script/jquery.contextmenu.r2.js\"></script>\n" +
					"<script type=\"text/javascript\">\n "+ appName +" \n</script>\n" +
					"<script type=\"text/javascript\">\n" + script + "\n</script>" ;
					
		
		String headRegex = "</head[^>]*>";
		templateStr = templateStr.replaceFirst(headRegex, 
				css + js + "</head>");
		String bodyRegex = "<body[^<]*>";
		Pattern bodyPatn = Pattern.compile(bodyRegex);
		Matcher bodyMatcher = bodyPatn.matcher(templateStr);
		StringBuffer sb = new StringBuffer();
		while (bodyMatcher.find()) {
			String bodyLabel = bodyMatcher.group();
			bodyMatcher.appendReplacement(sb, bodyLabel+"\n"+hidden+menu);
		}
		bodyMatcher.appendTail(sb);
		templateStr = sb.toString();
		//tempateStr = tempateStr.replaceFirst(bodyRegex, "<body>\n" + hidden);
		
		
		// 设置转发路径
		this.forwardPath = FileUtil.getFileDir(template.getLocalPath()) + "/template_set.jsp";
		
		// 3.替换模板内容
		String page = analyzerTemplate(articleId, columnId, siteId, replaceMap,
				namesMap, commonLabel, templateStr);
		
		// 替换栏目ID
		String colOld = "<input type=\"hidden\" id=\"columnId\" name=\"columnId\" value=\"\">";
		String colNew = "<input type=\"hidden\" id=\"columnId\" name=\"columnId\" value=\""+columnId+"\">";
		rs = page.replaceFirst(colOld, colNew);
		
		return rs;
	}

	/**
	 * 分析模板
	 * @param articleId
	 * @param columnId
	 * @param siteId
	 * @param replaceMap  key : unit.id,  value: unit.html
	 * @param namesMap    key : unit.id,  value: unitCategory.name
	 * @param commonLabel 公共标签
	 * @param tempateStr 模板内容（字符串）     
	 * @return
	 */
	private String analyzerTemplate(String articleId, String columnId, String siteId,
			Map<String, String> replaceMap, Map<String, String> namesMap,
			Map<String, String> commonLabel, String tempateStr) {
		StringBuffer temp = new StringBuffer();
		String unitRegex = TemplateUnit.REGEX_PUBLISH;
		Pattern unitPatn = Pattern.compile(unitRegex);
		Matcher m = unitPatn.matcher(tempateStr);
		String unitId = null;
		String unitName = null;
		String replace = null;
		while (m.find()) {
			unitId = m.group(1);
			replace = StringUtil.convert(replaceMap.get(unitId));
			String unitStr = m.group();
			// 替换栏目ID
			if (!StringUtil.isEmpty(columnId)) {
				unitStr = unitStr.replaceFirst("'0'\\);", "'"+columnId+"'\\);");
			}
			if (!StringUtil.isEmpty(replace)) {	// 如果设置了则用内容替代
				// 单元名 ，如：静态单元
				unitName = namesMap.get(unitId);
				// 分析内容
				replace = analyzeContent(unitName, unitId, articleId, columnId, siteId, commonLabel);
				unitStr = unitStr.replaceAll("color:[^;]+;", "");
				unitStr = unitStr.replaceAll(TemplateUnit.REGEX_REPLACE, replace);
			} 
			m.appendReplacement(temp, unitStr);
		}
		m.appendTail(temp);
		return temp.toString();
	}
	
	public String getForwardPath() {
		if (forwardPath == null) {
			throw new RuntimeException("must first get replacement template.");
		}
		return forwardPath;
	}
	
	public String getPreviewPage(String templateInstanceId, String articleId, String columnId, String siteId) {
		return publisher.previewPage(templateInstanceId, articleId, columnId, siteId);
	}
	
	public List<TemplateUnit> findTemplateUnitsByInstaceId(String instanceId) {
		return templateUnitDao.findByNamedQuery("findTemplateUnitByInstanceId", "id", instanceId);
	}
	
	public List<TemplateUnitCategory> findAllUnitCategories() {
		return templateUnitCategoryDao.findAll();
	}
	
	public List findTemplateUnitCategoryNotArticle(String categoryId){
		return templateUnitCategoryDao.findByNamedQuery("findTemplateUnitCategoryNotArticle","categoryId",categoryId);
	}
	
	public TemplateUnit findTemplateUnitByUnitId(String unitId) {
		return templateUnitDao.getAndNonClear(unitId);
	}
	
	public void cancelUnitSet(String unitId) {
		TemplateUnit unit = templateUnitDao.getAndClear(unitId);
		String file = unit.getConfigFile();
		if (!StringUtil.isEmpty(file)) {
			FileUtil.delete(GlobalConfig.appRealPath+file);
		}
		unit.setCss(null);
		unit.setHtml(null);
		unit.setConfigFile(null);
		unit.setScript(null);
		unit.setTemplateUnitCategory(null);
		
		templateUnitDao.update(unit);
		int i=1;
		//this.forwardPath = FileUtil.getFileDir(template.getLocalPath()) + "/template_set.jsp";
	}
	
	public void copyUnitSet(String fromUnitId, String toUnitId) {
		TemplateUnit fromUnit = templateUnitDao.getAndNonClear(fromUnitId);
		TemplateUnit newUnit = templateUnitDao.getAndNonClear(toUnitId);
		
		TemplateUnit toUnit = new TemplateUnit();
		BeanUtil.copyProperties(toUnit, fromUnit);
		toUnit.setId(newUnit.getId());
		toUnit.setName(newUnit.getName());
		toUnit.setConfigDir(newUnit.getConfigDir());
		TemplateInstance templateInstance = new TemplateInstance();
		templateInstance.setId(newUnit.getTemplateInstance().getId());
		toUnit.setTemplateInstance(templateInstance);
		
		String file = fromUnit.getConfigFile();
		String configDir = fromUnit.getConfigDir();
		if (!StringUtil.isEmpty(file)) {
			String configContent = FileUtil.read(GlobalConfig.appRealPath + "/" + file);
			String newConfigXml = configDir + "/" + IDFactory.getId() + ".xml";
			FileUtil.write(GlobalConfig.appRealPath + newConfigXml, configContent);
			toUnit.setConfigFile(newConfigXml);
		}
		templateUnitDao.clearCache();
		templateUnitDao.update(toUnit);
	}
	
	/**
	 * 分析内容
	 * @param unitName	单元名称（除静态单元外）
	 * @param unitId
	 * @param articleId
	 * @param columnId
	 * @param siteId
	 * @return
	 */
	private String analyzeContent(String unitName, String unitId, String articleId, String columnId, String siteId, Map<String,String> commonLabel) {
		String rs = "";
		if ("栏目链接".equals(unitName)) {
			rs = columnLinkAnalyzer.getHtml(unitId, articleId, columnId, siteId, commonLabel);
			
		} else if ("标题链接".equals(unitName)) {
			rs = titleLinkAnalyzer.getHtml(unitId, articleId, columnId, siteId, commonLabel);
			
		} else if ("当前位置".equals(unitName)) {
			rs = currentLocationAnalyzer.getHtml(unitId, articleId, columnId, siteId, commonLabel);
			
		} else if ("图片新闻".equals(unitName)) {
			rs = pictureNewsAnalyzer.getHtml(unitId, articleId, columnId, siteId, commonLabel);
			
		} else if ("最新信息".equals(unitName)) {
			rs = latestInfoAnalyzer.getHtml(unitId, articleId, columnId, siteId, commonLabel);
			
		} else if ("静态单元".equals(unitName)) {
			rs = staticUnitAnalyzer.getHtml(unitId, articleId, columnId, siteId, commonLabel);
			
		} else if("文章正文".equals(unitName)) {
			rs = articleTextAnalyzer.getHtml(unitId, articleId, columnId, siteId, commonLabel);
			
		} else if("期刊（按分类）".equals(unitName)) {
			rs = magazineCategoryAnalyzer.getHtml(unitId, articleId, columnId, siteId, commonLabel);

		} else if("标题链接（分页）".equals(unitName)) {
			rs = titleLinkPageAnalyzer.getHtml(unitId, articleId, columnId, siteId, commonLabel);
			
		} else if("网上调查".equals(unitName)){
			rs = onlineSurverySetAnalyzer.getHtml(unitId, articleId, columnId, siteId, commonLabel);
		}
		return rs;
	}
	
	/**
	 * @param templateInstanceDao the templateInstanceDao to set
	 */
	public void setTemplateInstanceDao(TemplateInstanceDao templateInstanceDao) {
		this.templateInstanceDao = templateInstanceDao;
	}
	
	/**
	 * @param templateCategoryDao the templateCategoryDao to set
	 */
	public void setTemplateCategoryDao(TemplateCategoryDao templateCategoryDao) {
		this.templateCategoryDao = templateCategoryDao;
	}
	
	/**
	 * @param templateDao the templateDao to set
	 */
	public void setTemplateDao(TemplateDao templateDao) {
		this.templateDao = templateDao;
	}

	/**
	 * @param templateUnitDao the templateUnitDao to set
	 */
	public void setTemplateUnitDao(TemplateUnitDao templateUnitDao) {
		this.templateUnitDao = templateUnitDao;
	}
	
	/**
	 * @param columnDao the columnDao to set
	 */
	public void setColumnDao(ColumnDao columnDao) {
		this.columnDao = columnDao;
	}

	/**
	 * @param siteDao the siteDao to set
	 */
	public void setSiteDao(SiteDao siteDao) {
		this.siteDao = siteDao;
	}

	/**
	 * @return the templateUnitCategoryDao
	 */
	public TemplateUnitCategoryDao getTemplateUnitCategoryDao() {
		return templateUnitCategoryDao;
	}

	/**
	 * @param templateUnitCategoryDao the templateUnitCategoryDao to set
	 */
	public void setTemplateUnitCategoryDao(
			TemplateUnitCategoryDao templateUnitCategoryDao) {
		this.templateUnitCategoryDao = templateUnitCategoryDao;
	}

	/**
	 * @param staticUnitAnalyzer the staticUnitAnalyzer to set
	 */
	public void setStaticUnitAnalyzer(TemplateUnitAnalyzer staticUnitAnalyzer) {
		this.staticUnitAnalyzer = staticUnitAnalyzer;
	}

	/**
	 * @param titleLinkAnalyzer the titleLinkAnalyzer to set
	 */
	public void setTitleLinkAnalyzer(TemplateUnitAnalyzer titleLinkAnalyzer) {
		this.titleLinkAnalyzer = titleLinkAnalyzer;
	}

	/**
	 * @param columnLinkAnalyzer the columnLinkAnalyzer to set
	 */
	public void setColumnLinkAnalyzer(TemplateUnitAnalyzer columnLinkAnalyzer) {
		this.columnLinkAnalyzer = columnLinkAnalyzer;
	}

	/**
	 * @param currentLocationAnalyzer the currentLocationAnalyzer to set
	 */
	public void setCurrentLocationAnalyzer(
			TemplateUnitAnalyzer currentLocationAnalyzer) {
		this.currentLocationAnalyzer = currentLocationAnalyzer;
	}

	/**
	 * @param latestInfoAnalyzer the latestInfoAnalyzer to set
	 */
	public void setLatestInfoAnalyzer(TemplateUnitAnalyzer latestInfoAnalyzer) {
		this.latestInfoAnalyzer = latestInfoAnalyzer;
	}

	/**
	 * @param pictureNewsAnalyzer the pictureNewsAnalyzer to set
	 */
	public void setPictureNewsAnalyzer(TemplateUnitAnalyzer pictureNewsAnalyzer) {
		this.pictureNewsAnalyzer = pictureNewsAnalyzer;
	}
	
	/**
	 * @param articleTextAnalyzer this articleTextAnalyzer to set
	 */
	public void setArticleTextAnalyzer(ArticleTextAnalyzer articleTextAnalyzer) {
		this.articleTextAnalyzer = articleTextAnalyzer;
	}

	/**
	 * @param publisher the publisher to set
	 */
	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public void deleteUnit() {
		// TODO Auto-generated method stub
		
	}

	public void setArticleDao(ArticleDao articleDao) {
		this.articleDao = articleDao;
	}

	/**
	 * @param systemLogDao the systemLogDao to set
	 */
	public void setSystemLogDao(SystemLogDao systemLogDao) {
		this.systemLogDao = systemLogDao;
	}

	/**
	 * @param magazineCategoryAnalyzer the magazineCategoryAnalyzer to set
	 */
	public void setMagazineCategoryAnalyzer(
			MagazineCategoryAnalyzer magazineCategoryAnalyzer) {
		this.magazineCategoryAnalyzer = magazineCategoryAnalyzer;
	}

	/**
	 * @param titleLinkPageAnalyzer the titleLinkPageAnalyzer to set
	 */
	public void setTitleLinkPageAnalyzer(TitleLinkPageAnalyzer titleLinkPageAnalyzer) {
		this.titleLinkPageAnalyzer = titleLinkPageAnalyzer;
	}

	/**
	 * @param rightDao the rightDao to set
	 */
	public void setRightDao(RightDao rightDao) {
		this.rightDao = rightDao;
	}

	/**
	 * @param articleFormatDao the articleFormatDao to set
	 */
	public void setArticleFormatDao(ArticleFormatDao articleFormatDao) {
		this.articleFormatDao = articleFormatDao;
	}

	/**
	 * @param onlineSurverySetAnalyzer the onlineSurverySetAnalyzer to set
	 */
	public void setOnlineSurverySetAnalyzer(
			OnlineSurverySetAnalyzer onlineSurverySetAnalyzer) {
		this.onlineSurverySetAnalyzer = onlineSurverySetAnalyzer;
	}
}
