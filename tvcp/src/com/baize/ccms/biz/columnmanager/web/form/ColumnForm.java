  /**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.biz.columnmanager.web.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.baize.ccms.biz.articlemanager.domain.ArticleFormat;
import com.baize.ccms.biz.columnmanager.domain.Column;
import com.baize.ccms.biz.sitemanager.domain.Site;
import com.baize.common.core.web.GeneralForm;

/**
 * 
 * <p>标题: 栏目表单</p>
 * <p>描述: 栏目的表单数据，以便页面和方法中调用</p>
 * <p>模块: 栏目管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 魏仲芹
 * @version 1.0
 * @since 2009-3-31 下午06:57:26
 * @history（历次修订内容、修订人、修订时间等）
 */
public class ColumnForm extends GeneralForm{

	private static final long serialVersionUID = -2585433256800429859L;
	
	/** 创建一个栏目父对象 **/
	private Column parent=new Column();
	/** 创建一个栏目对象 **/
	private Column column=new Column();
	/** 创建一个网站对象 **/
	private Site site=new Site();
	/** 创建一个列表对象 **/
	private List list = new ArrayList();
	/** 创建一个父栏目id **/
	private String pid;
	/** 创建一个栏目ids **/
	private String ids;
	/** 创建一个栏目id **/
	private String id;
	/** 栏目名字 **/
	private String name;
	/** 用户的id **/
	private String userid;
	/** 网站的id **/
	private String siteid;
	/** 栏目的创建时间 **/
	private String createTime;
	/** 父栏目id **/
	private String parentid;
	/** 修改前的栏目id **/
	private String modifyparentid;	
	/** 存取要复制 的栏目的id **/
	private String pasteIds;
	/** 是否需要子节点 **/
	private int needchild;
	/** 存放排序时的序列 **/
	private String ordersColumn;
	/** 存放某个栏目下的子栏目ids **/
	private String childColumnIds;
	/** 栏目id **/
	private String columnId;
	/** 栏目名字 **/
	private String columnName;
	/** 父栏目的名字 **/
	private String parentName;
	/** 获取要复制到的栏目的id **/
	private String checkid;
	/** 导出栏目的id **/
	private String exportColumnIds;
	/** 所有格式 **/
	private List<ArticleFormat> articleFormats;
	/** 文章格式ID */
	private String articleFormatId;
	/**是否是新增栏目**/
	private int isAddColumn;
	/**栏目更新时间*/
	private String updateTime;
	/** 当前网站是否为根站**/
	private String isRootSite;
	/**检索栏目从栏目的起始位置*/
	private int fromCount;
	/**检索栏目到栏目的截止位置*/
	private int toCount;
	/**是否是复制栏目*/
	private int isCopy;
	/**判断要修改的栏目下面是否有文章*/
	private int hasArticle;
	/**分隔符**/
	private String separator;
	/**替换nodeName**/
	private String localNodeName;
	/** 栏目初始地址 */
	private String initUrl;
	/** 栏目信息积分 */
	private String infoScore;
	/** 栏目图片积分 */
	private String picScore;
	/** 栏目同步->可否修改**/
	private String allowModify;
	/** 栏目同步->同步类型**/
	private String refType;
	/** 栏目格式**/
	private ArticleFormat articleFormat;
	/** 栏目id字符串 **/
	private String columnIds;
	/** 栏目名字字符串 **/
	private String columnNames;
	/** 栏目同步->同步栏目**/
	private String refColumnIds;
	/** 与文章格式一致的栏目ids**/
	private String sameFormatColumns; 
	/** 网站同步类型**/
	private String siteRefType;
	/** 选择同步的网站Id**/
	private String refSiteId;
	/** 被选择的栏目 */
	private String checkedIds;
	
	/** 从父站引用的栏目**/
	private String pSiteColumn;
	
	/** 树的url*/
	private String treeUrl;
	
	/** 同步网站名称**/
	private String refSiteName;
	
	/** 同步栏目名称和id字符串**/
	private String refColumnNameAndId;
	
	/**
	 * @return the isAddColumn
	 */
	public int getIsAddColumn() {
		return isAddColumn;
	}

	/**
	 * @param isAddColumn the isAddColumn to set
	 */
	public void setIsAddColumn(int isAddColumn) {
		this.isAddColumn = isAddColumn;
	}

	/**
	 * @return the columnName
	 */
	public String getColumnName() {
		return columnName;
	}

	/**
	 * @param columnName the columnName to set
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	/**
	 * @return the childColumnIds
	 */
	public String getChildColumnIds() {
		return childColumnIds;
	}

	/**
	 * @param childColumnIds the childColumnIds to set
	 */
	public void setChildColumnIds(String childColumnIds) {
		this.childColumnIds = childColumnIds;
	}

	/**
	 * @return the ordersColumn
	 */
	public String getOrdersColumn() {
		return ordersColumn;
	}

	/**
	 * @param ordersColumn the ordersColumn to set
	 */
	public void setOrdersColumn(String ordersColumn) {
		this.ordersColumn = ordersColumn;
	}

	/**
	 * @return the createTime
	 */
	public String getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	/**
	 * @return the parent
	 */
	public Column getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(Column parent) {
		this.parent = parent;
	}

	/**
	 * @return the column
	 */
	public Column getColumn() {
		return column;
	}

	/**
	 * @param column the column to set
	 */
	public void setColumn(Column column) {
		this.column = column;
	}

	/**
	 * @return the site
	 */
	public Site getSite() {
		return site;
	}

	/**
	 * @param site the site to set
	 */
	public void setSite(Site site) {
		this.site = site;
	}

	/**
	 * @return the list
	 */
	public List getList() {
		return list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(List list) {
		this.list = list;
	}

	/**
	 * @return the pid
	 */
	public String getPid() {
		return pid;
	}

	/**
	 * @param pid the pid to set
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}

	/**
	 * @return the ids
	 */
	public String getIds() {
		return ids;
	}

	/**
	 * @param ids the ids to set
	 */
	public void setIds(String ids) {
		this.ids = ids;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the pasteIds
	 */
	public String getPasteIds() {
		return pasteIds;
	}

	/**
	 * @param pasteIds the pasteIds to set
	 */
	public void setPasteIds(String pasteIds) {
		this.pasteIds = pasteIds;
	}

	@Override
	protected void validateData(ActionMapping mapping, HttpServletRequest request) {
	}

	/**
	 * @return the needchild
	 */
	public int getNeedchild() {
		return needchild;
	}

	/**
	 * @param needchild the needchild to set
	 */
	public void setNeedchild(int needchild) {
		this.needchild = needchild;
	}

	/**
	 * @return the articleFormats
	 */
	public List<ArticleFormat> getArticleFormats() {
		return articleFormats;
	}

	/**
	 * @param articleFormats the articleFormats to set
	 */
	public void setArticleFormats(List<ArticleFormat> articleFormats) {
		this.articleFormats = articleFormats;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the userid
	 */
	public String getUserid() {
		return userid;
	}

	/**
	 * @param userid the userid to set
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}

	/**
	 * @return the siteid
	 */
	public String getSiteid() {
		return siteid;
	}

	/**
	 * @param siteid the siteid to set
	 */
	public void setSiteid(String siteid) {
		this.siteid = siteid;
	}

	/**
	 * @return the parentid
	 */
	public String getParentid() {
		return parentid;
	}

	/**
	 * @param parentid the parentid to set
	 */
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	/**
	 * @return the modifyparentid
	 */
	public String getModifyparentid() {
		return modifyparentid;
	}

	/**
	 * @param modifyparentid the modifyparentid to set
	 */
	public void setModifyparentid(String modifyparentid) {
		this.modifyparentid = modifyparentid;
	}

	/**
	 * @return the columnId
	 */
	public String getColumnId() {
		return columnId;
	}

	/**
	 * @param columnId the columnId to set
	 */
	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	/**
	 * @return the articleFormatId
	 */
	public String getArticleFormatId() {
		return articleFormatId;
	}

	/**
	 * @param articleFormatId the articleFormatId to set
	 */
	public void setArticleFormatId(String articleFormatId) {
		this.articleFormatId = articleFormatId;
	}

	/**
	 * @param checkid the checkid to set
	 */
	public void setCheckid(String checkid) {
		this.checkid = checkid;
	}

	/**
	 * @return the checkid
	 */
	public String getCheckid() {
		return checkid;
	}

	/**
	 * @param parentName the parentName to set
	 */
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	/**
	 * @return the parentName
	 */
	public String getParentName() {
		return parentName;
	}

	/**
	 * @return the exportColumnIds
	 */
	public String getExportColumnIds() {
		return exportColumnIds;
	}

	/**
	 * @param exportColumnIds the exportColumnIds to set
	 */
	public void setExportColumnIds(String exportColumnIds) {
		this.exportColumnIds = exportColumnIds;
	}

	/**
	 * @return the updateTime
	 */
	public String getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public void setIsRootSite(String isRootSite) {
		this.isRootSite = isRootSite;
	}

	public String getIsRootSite() {
		return isRootSite;
	}

	/**
	 * @return the fromCount
	 */
	public int getFromCount() {
		return fromCount;
	}

	/**
	 * @param fromCount the fromCount to set
	 */
	public void setFromCount(int fromCount) {
		this.fromCount = fromCount;
	}

	/**
	 * @return the toCount
	 */
	public int getToCount() {
		return toCount;
	}

	/**
	 * @param toCount the toCount to set
	 */
	public void setToCount(int toCount) {
		this.toCount = toCount;
	}

	/**
	 * @return the isCopy
	 */
	public int getIsCopy() {
		return isCopy;
	}

	/**
	 * @param isCopy the isCopy to set
	 */
	public void setIsCopy(int isCopy) {
		this.isCopy = isCopy;
	}

	/**
	 * @return the hasArticle
	 */
	public int getHasArticle() {
		return hasArticle;
	}

	/**
	 * @param hasArticle the hasArticle to set
	 */
	public void setHasArticle(int hasArticle) {
		this.hasArticle = hasArticle;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

	public String getSeparator() {
		return separator;
	}

	public void setLocalNodeName(String localNodeName) {
		this.localNodeName = localNodeName;
	}

	public String getLocalNodeName() {
		return localNodeName;
	}

	public void setInitUrl(String initUrl) {
		this.initUrl = initUrl;
	}

	public String getInitUrl() {
		return initUrl;
	}

	public void setInfoScore(String infoScore) {
		this.infoScore = infoScore;
	}

	public String getInfoScore() {
		return infoScore;
	}

	public void setPicScore(String picScore) {
		this.picScore = picScore;
	}

	public String getPicScore() {
		return picScore;
	}

	public void setAllowModify(String allowModify) {
		this.allowModify = allowModify;
	}

	public String getAllowModify() {
		return allowModify;
	}

	public void setRefType(String refType) {
		this.refType = refType;
	}

	public String getRefType() {
		return refType;
	}

	public void setArticleFormat(ArticleFormat articleFormat) {
		this.articleFormat = articleFormat;
	}

	public ArticleFormat getArticleFormat() {
		return articleFormat;
	}

	public void setColumnIds(String columnIds) {
		this.columnIds = columnIds;
	}

	public String getColumnIds() {
		return columnIds;
	}

	public void setColumnNames(String columnNames) {
		this.columnNames = columnNames;
	}

	public String getColumnNames() {
		return columnNames;
	}

	public void setRefColumnIds(String refColumnIds) {
		this.refColumnIds = refColumnIds;
	}

	public String getRefColumnIds() {
		return refColumnIds;
	}

	public void setSameFormatColumns(String sameFormatColumns) {
		this.sameFormatColumns = sameFormatColumns;
	}

	public String getSameFormatColumns() {
		return sameFormatColumns;
	}

	public void setSiteRefType(String siteRefType) {
		this.siteRefType = siteRefType;
	}

	public String getSiteRefType() {
		return siteRefType;
	}

	public void setRefSiteId(String refSiteId) {
		this.refSiteId = refSiteId;
	}

	public String getRefSiteId() {
		return refSiteId;
	}

	/**
	 * @return the checkedIds
	 */
	public String getCheckedIds() {
		return checkedIds;
	}

	/**
	 * @param checkedIds the checkedIds to set
	 */
	public void setCheckedIds(String checkedIds) {
		this.checkedIds = checkedIds;
	}

	public void setPSiteColumn(String pSiteColumn) {
		this.pSiteColumn = pSiteColumn;
	}

	public String getPSiteColumn() {
		return pSiteColumn;
	}

	public String getTreeUrl() {
		return treeUrl;
	}

	public void setTreeUrl(String treeUrl) {
		this.treeUrl = treeUrl;
	}

	public void setRefSiteName(String refSiteName) {
		this.refSiteName = refSiteName;
	}

	public String getRefSiteName() {
		return refSiteName;
	}

	public void setRefColumnNameAndId(String refColumnNameAndId) {
		this.refColumnNameAndId = refColumnNameAndId;
	}

	public String getRefColumnNameAndId() {
		return refColumnNameAndId;
	}


}
