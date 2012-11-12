/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.articlemanager.web.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.j2ee.cms.biz.articlemanager.domain.Article;
import com.j2ee.cms.biz.articlemanager.domain.ArticleAttach;
import com.j2ee.cms.biz.articlemanager.domain.ArticleAttribute;
import com.j2ee.cms.biz.articlemanager.domain.ArticleFormat;
import com.j2ee.cms.biz.configmanager.domain.GeneralSystemSet;
import com.j2ee.cms.common.core.web.GeneralForm;

/**
 * <p>标题: 文章表单</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 文章管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-3-19 上午10:16:09
 * @history（历次修订内容、修订人、修订时间等）
 */
public class ArticleForm extends GeneralForm {

	private static final long serialVersionUID = 4888416335005966455L;
	
	/** 文章 */
	private Article article = new Article();
	
	/** 文章属性 */
	private List<ArticleAttribute> attributeList = new ArrayList<ArticleAttribute>();
	
	/** 所有格式 */
	private List<ArticleFormat> formats = new ArrayList<ArticleFormat>();
	
	private Integer maxPicCount;
	private Integer maxMediaCount;
	private Integer maxAttachCount;
	private List<ArticleAttach> picList = new ArrayList<ArticleAttach>();
	private List<ArticleAttach> mediaList = new ArrayList<ArticleAttach>();
	private List<ArticleAttach> attachList = new ArrayList<ArticleAttach>();
	private String majorPicPath;
	private String majorAttachPath;
	private String majorMediaPath;
	
	/** 文章ID */
	private String articleId;
	
 
	
	/** 栏目ID */
	private String columnId;
	
	/** 网站ID */
	private String siteId;
	
	/** 创建者ID */
	private String creatorId;
	
	/** 审核人ID */
	private String auditorId;
	
	/** 呈送文章id*/
	private String presentArticleIds; 
	
	/** 转移文章id */
	private String moveArticleIds;
	
	/** 排序文章id */
	private String sortArticleIds;
	
	/** 文章显示顺序 */
	private int orders;
	
	/** 呈送方式*/
	private int presentMethod;
	
	/** 引用id */
	private String refId;
	
	/** 文章是否是引用*/
	private int isref;
	
	/**检索文章从文章的起始位置*/
	private int fromCount;
	
	/**检索文章到文章的截止位置*/
	private int toCount;
	
	/**是否从article_all_list.jsp调过来的**/
	private String isfromAll;
	
	/**当前文章所在栏目的格式名称**/
	private String presentFormatName;
	
	/**当前文章所在栏目的格式id**/
	private String presentFormatId;
	
	/**是否要将文章置顶**/
	private String isToped;
	
	/** 枚举信息字符串**/
	private String enumInfoStr;
	
	/** 枚举类别Id**/
	private String enumerationId;
	
	/** 枚举值**/
	private String enumerationValue;
	
	/** 导出文章ids**/
	private String exportArticleIds;
	
	/** 导入文章路径**/
	private String path;
	
	/** 初始化地址**/
	private String initUrl;
	
	/** 文章Id,用于查找initUrl**/
	private String articleDetailId;
	
	/****/
	@SuppressWarnings("unchecked")
	private List<String[]> list = new ArrayList();
	


	/** 作者集合**/
	private String generalSystemSetList ;
	
	/**来源设置**/
	private String generalSystemSetOrgin;
	

	

	/** 与文章格式一致的栏目ids**/
	private String sameFormatColumns; 
	

	/** 用户是否具有文章审核权限**/
	private String articleAuditedRights;
	
	/** 判断栏目是否已经审核，栏目审核后，文章则自动审核 */
	private String columnAudited;
	
	/**
	 * 判断是否为默认格式
	 */
	private String isDefault;
	
	private String formatId;
	
	private String formatName;
	
	/**
	 * @return the isref
	 */
	public int getIsref() {
		return isref;
	}

	/**
	 * @param isref the isref to set
	 */
	public void setIsref(int isref) {
		this.isref = isref;
	}

	/**
	 * @return the orders
	 */
	public int getOrders() {
		return orders;
	}

	/**
	 * @param orders the orders to set
	 */
	public void setOrders(int orders) {
		this.orders = orders;
	}

	/**
	 * @return the refId
	 */
	public String getRefId() {
		return refId;
	}

	/**
	 * @param refId the refId to set
	 */
	public void setRefId(String refId) {
		this.refId = refId;
	}

	/**
	 * @return the sortArticleIds
	 */
	public String getSortArticleIds() {
		return sortArticleIds;
	}

	/**
	 * @param sortArticleIds the sortArticleIds to set
	 */
	public void setSortArticleIds(String sortArticleIds) {
		this.sortArticleIds = sortArticleIds;
	}

	/**
	 * @return the presentMethod
	 */
	public int getPresentMethod() {
		return presentMethod;
	}

	/**
	 * @param presentMethod the presentMethod to set
	 */
	public void setPresentMethod(int presentMethod) {
		this.presentMethod = presentMethod;
	}

	/**
	 * @return the moveArticleIds
	 */
	public String getMoveArticleIds() {
		return moveArticleIds;
	}

	/**
	 * @param moveArticleIds the moveArticleIds to set
	 */
	public void setMoveArticleIds(String moveArticleIds) {
		this.moveArticleIds = moveArticleIds;
	}

	/**
	 * @return the presentArticleIds
	 */
	public String getPresentArticleIds() {
		return presentArticleIds;
	}

	/**
	 * @param presentArticleIds the presentArticleIds to set
	 */
	public void setPresentArticleIds(String presentArticleIds) {
		this.presentArticleIds = presentArticleIds;
	}

	/**
	 * @return the article
	 */
	public Article getArticle() {
		return article;
	}

	/**
	 * @param article the article to set
	 */
	public void setArticle(Article article) {
		this.article = article;
	}

	@Override
	protected void validateData(ActionMapping mapping,
		HttpServletRequest request) {
	}

	/**
	 * @return the attributeList
	 */
	public List<ArticleAttribute> getAttributeList() {
		return attributeList;
	}

	/**
	 * @param attributeList the attributeList to set
	 */
	public void setAttributeList(List<ArticleAttribute> attributeList) {
		this.attributeList = attributeList;
	}

	/**
	 * @return the formats
	 */
	public List<ArticleFormat> getFormats() {
		return formats;
	}

	/**
	 * @param formats the formats to set
	 */
	public void setFormats(List<ArticleFormat> formats) {
		this.formats = formats;
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
	 * @return the siteId
	 */
	public String getSiteId() {
		return siteId;
	}

	/**
	 * @param siteId the siteId to set
	 */
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	/**
	 * @return the creatorId
	 */
	public String getCreatorId() {
		return creatorId;
	}

	/**
	 * @param creatorId the creatorId to set
	 */
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	/**
	 * @return the auditorId
	 */
	public String getAuditorId() {
		return auditorId;
	}

	/**
	 * @param auditorId the auditorId to set
	 */
	public void setAuditorId(String auditorId) {
		this.auditorId = auditorId;
	}

 

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
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

	public void setIsfromAll(String isfromAll) {
		this.isfromAll = isfromAll;
	}

	public String getIsfromAll() {
		return isfromAll;
	}

	public void setPresentFormatName(String presentFormatName) {
		this.presentFormatName = presentFormatName;
	}

	public String getPresentFormatName() {
		return presentFormatName;
	}

	public void setList(List<String[]> list) {
		this.list = list;
	}

	public List<String[]> getList() {
		return list;
	}

	public void setPresentFormatId(String presentFormatId) {
		this.presentFormatId = presentFormatId;
	}

	public String getPresentFormatId() {
		return presentFormatId;
	}

	public void setIsToped(String isToped) {
		this.isToped = isToped;
	}

	public String getIsToped() {
		return isToped;
	}

	public void setEnumInfoStr(String enumInfoStr) {
		this.enumInfoStr = enumInfoStr;
	}

	public String getEnumInfoStr() {
		return enumInfoStr;
	}

	public void setEnumerationId(String enumerationId) {
		this.enumerationId = enumerationId;
	}

	public String getEnumerationId() {
		return enumerationId;
	}

	public void setEnumerationValue(String enumerationValue) {
		this.enumerationValue = enumerationValue;
	}

	public String getEnumerationValue() {
		return enumerationValue;
	}

	public void setExportArticleIds(String exportArticleIds) {
		this.exportArticleIds = exportArticleIds;
	}

	public String getExportArticleIds() {
		return exportArticleIds;
	}
 


	public String getGeneralSystemSetList() {
		return generalSystemSetList;
	}

	public void setPath(String path) {
		this.path = path;
	}



	public void setGeneralSystemSetList(String generalSystemSetList) {
		this.generalSystemSetList = generalSystemSetList;
	}

	public String getPath() {
		return path;
	}



	public String getGeneralSystemSetOrgin() {
		return generalSystemSetOrgin;
	}

	public void setGeneralSystemSetOrgin(String generalSystemSetOrgin) {
		this.generalSystemSetOrgin = generalSystemSetOrgin;
	}



	public void setSameFormatColumns(String sameFormatColumns) {
		this.sameFormatColumns = sameFormatColumns;
	}

	public String getSameFormatColumns() {
		return sameFormatColumns;
	}

	public void setArticleAuditedRights(String articleAuditedRights) {
		this.articleAuditedRights = articleAuditedRights;
	}

	public String getArticleAuditedRights() {
		return articleAuditedRights;
	}

	public void setInitUrl(String initUrl) {
		this.initUrl = initUrl;
	}

	public String getInitUrl() {
		return initUrl;
	}

	public void setArticleDetailId(String articleDetailId) {
		this.articleDetailId = articleDetailId;
	}

	public String getArticleDetailId() {
		return articleDetailId;
	}

	/**
	 * @return the auditAndSave
	 */
	public String getColumnAudited() {
		return columnAudited;
	}

	/**
	 * @param auditAndSave the auditAndSave to set
	 */
	public void setColumnAudited(String columnAudited) {
		this.columnAudited = columnAudited;
	}

	/**
	 * @return the isDefault
	 */
	public String getIsDefault() {
		return isDefault;
	}

	/**
	 * @param isDefault the isDefault to set
	 */
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	/**
	 * @return the formatId
	 */
	public String getFormatId() {
		return formatId;
	}

	/**
	 * @param formatId the formatId to set
	 */
	public void setFormatId(String formatId) {
		this.formatId = formatId;
	}

	/**
	 * @return the formatName
	 */
	public String getFormatName() {
		return formatName;
	}

	/**
	 * @param formatName the formatName to set
	 */
	public void setFormatName(String formatName) {
		this.formatName = formatName;
	}

    public Integer getMaxPicCount() {
        return maxPicCount;
    }

    public void setMaxPicCount(Integer maxPicCount) {
        this.maxPicCount = maxPicCount;
    }

    public Integer getMaxMediaCount() {
        return maxMediaCount;
    }

    public void setMaxMediaCount(Integer maxMediaCount) {
        this.maxMediaCount = maxMediaCount;
    }

    public Integer getMaxAttachCount() {
        return maxAttachCount;
    }

    public void setMaxAttachCount(Integer maxAttachCount) {
        this.maxAttachCount = maxAttachCount;
    }

    public List<ArticleAttach> getPicList() {
        return picList;
    }

    public void setPicList(List<ArticleAttach> picList) {
        this.picList = picList;
    }

    public List<ArticleAttach> getMediaList() {
        return mediaList;
    }

    public void setMediaList(List<ArticleAttach> mediaList) {
        this.mediaList = mediaList;
    }

    public List<ArticleAttach> getAttachList() {
        return attachList;
    }

    public void setAttachList(List<ArticleAttach> attachList) {
        this.attachList = attachList;
    }

    public String getMajorPicPath() {
        return majorPicPath;
    }

    public void setMajorPicPath(String majorPicPath) {
        this.majorPicPath = majorPicPath;
    }

    public String getMajorAttachPath() {
        return majorAttachPath;
    }

    public void setMajorAttachPath(String majorAttachPath) {
        this.majorAttachPath = majorAttachPath;
    }

    public String getMajorMediaPath() {
        return majorMediaPath;
    }

    public void setMajorMediaPath(String majorMediaPath) {
        this.majorMediaPath = majorMediaPath;
    }

}
