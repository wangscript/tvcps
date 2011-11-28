/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.unitmanager.web.form;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.baize.ccms.biz.templatemanager.domain.TemplateUnitStyle;
import com.baize.ccms.biz.unitmanager.label.CommonLabel;
import com.baize.ccms.biz.unitmanager.label.LatestInfoLabel;
import com.baize.ccms.biz.unitmanager.label.TitleLinkPageLabel;

/**
 * 
 * <p>标题: —— 最新信息form</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 模板单元管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-6-3 上午10:23:23
 * @history（历次修订内容、修订人、修订时间等）
 */
public class LatestInfoForm extends UnitForm {

	private static final long serialVersionUID = 8650489452547538924L;
	
	/** 单元类型 */
	private String unitType;
	/** 选择目录范围的选择数据 */
	private String allColumn;
	/** 选择目录范围 */
	private String selectCol;
	/** 指定栏目 */
	private String chooseColumn;
	/** 嵌入源码*/
	private String htmlContent;
	/**************************************** 嵌入页样式 *************************************/
	
	/** 标题字数： */
	private String titleWordCount;
	/** 标题前缀： */
	private String titleHead;
	/** 标题前缀是否是图片*/
	private String titleHeadPic;
	/** 标题后缀： */
	private String titleEnd;
	/** 标题后缀是否是图片*/
	private String titleEndPic;

	/*****************************************列表页样式***********************************/
	/** 是否分页 */
	private String page;
	/** 显示记录总数 */
	private String count;
	/** 每页显示记录数： */
	private String pageCount;
	/** 信息显示：行*/
	private String row;
	/** 信息显示：列*/
	private String col;
	

	/** 所有单元样式*/
	private List<TemplateUnitStyle> templateUnitStyleList;
	/** 所有标题单元标签*/
	private Map map = TitleLinkPageLabel.map;
	/** 所有公用*/
	private Map commonMap = CommonLabel.allLabels;
	/*** 最新信息标签 */
	private Map latestMap = LatestInfoLabel.map;
	
	/** 最新信息更多信息链接栏目(id#name) */
	private String moreLinkColumn;
	/** 更多信息链接地址(用于最新信息的分页) */
	private String moreLinkColumnUrl;
	/** 更多 */
	private String moreLink;
	/** 更多是否图片 */
	private String moreLinkPic;
	/** 分页位置 */
	private String pageSite;
	
	
	@Override
	protected void validateData(ActionMapping arg0, HttpServletRequest arg1) {
	}

	/**
	 * @return the templateUnitStyleList
	 */
	public List<TemplateUnitStyle> getTemplateUnitStyleList() {
		return templateUnitStyleList;
	}


	/**
	 * @param templateUnitStyleList the templateUnitStyleList to set
	 */
	public void setTemplateUnitStyleList(
			List<TemplateUnitStyle> templateUnitStyleList) {
		this.templateUnitStyleList = templateUnitStyleList;
	}

 

	/**
	 * @return the commonMap
	 */
	public Map getCommonMap() {
		return commonMap;
	}


	/**
	 * @param commonMap the commonMap to set
	 */
	public void setCommonMap(Map commonMap) {
		this.commonMap = commonMap;
	}


	/**
	 * @return the chooseColumn
	 */
	public String getChooseColumn() {
		return chooseColumn;
	}


	/**
	 * @param chooseColumn the chooseColumn to set
	 */
	public void setChooseColumn(String chooseColumn) {
		this.chooseColumn = chooseColumn;
	}



	/**
	 * @return the row
	 */
	public String getRow() {
		return row;
	}


	/**
	 * @param row the row to set
	 */
	public void setRow(String row) {
		this.row = row;
	}


	/**
	 * @return the col
	 */
	public String getCol() {
		return col;
	}


	/**
	 * @param col the col to set
	 */
	public void setCol(String col) {
		this.col = col;
	}


	/**
	 * @return the titleWordCount
	 */
	public String getTitleWordCount() {
		return titleWordCount;
	}


	/**
	 * @param titleWordCount the titleWordCount to set
	 */
	public void setTitleWordCount(String titleWordCount) {
		this.titleWordCount = titleWordCount;
	}


	/**
	 * @return the titleHead
	 */
	public String getTitleHead() {
		return titleHead;
	}


	/**
	 * @param titleHead the titleHead to set
	 */
	public void setTitleHead(String titleHead) {
		this.titleHead = titleHead;
	}


	/**
	 * @return the titleHeadPic
	 */
	public String getTitleHeadPic() {
		return titleHeadPic;
	}


	/**
	 * @param titleHeadPic the titleHeadPic to set
	 */
	public void setTitleHeadPic(String titleHeadPic) {
		this.titleHeadPic = titleHeadPic;
	}


	/**
	 * @return the titleEnd
	 */
	public String getTitleEnd() {
		return titleEnd;
	}


	/**
	 * @param titleEnd the titleEnd to set
	 */
	public void setTitleEnd(String titleEnd) {
		this.titleEnd = titleEnd;
	}


	/**
	 * @return the titleEndPic
	 */
	public String getTitleEndPic() {
		return titleEndPic;
	}


	/**
	 * @param titleEndPic the titleEndPic to set
	 */
	public void setTitleEndPic(String titleEndPic) {
		this.titleEndPic = titleEndPic;
	}



	/**
	 * @return the count
	 */
	public String getCount() {
		return count;
	}


	/**
	 * @param count the count to set
	 */
	public void setCount(String count) {
		this.count = count;
	}


	/**
	 * @return the pageCount
	 */
	public String getPageCount() {
		return pageCount;
	}


	/**
	 * @param pageCount the pageCount to set
	 */
	public void setPageCount(String pageCount) {
		this.pageCount = pageCount;
	}

	/**
	 * @return the unitType
	 */
	public String getUnitType() {
		return unitType;
	}


	/**
	 * @param unitType the unitType to set
	 */
	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}


	/**
	 * @return the selectCol
	 */
	public String getSelectCol() {
		return selectCol;
	}


	/**
	 * @param selectCol the selectCol to set
	 */
	public void setSelectCol(String selectCol) {
		this.selectCol = selectCol;
	}

	/**
	 * @return the map
	 */
	public Map getMap() {
		return map;
	}

	/**
	 * @param map the map to set
	 */
	public void setMap(Map map) {
		this.map = map;
	}

	/**
	 * @return the allColumn
	 */
	public String getAllColumn() {
		return allColumn;
	}

	/**
	 * @param allColumn the allColumn to set
	 */
	public void setAllColumn(String allColumn) {
		this.allColumn = allColumn;
	}


	/**
	 * @return the htmlContent
	 */
	public String getHtmlContent() {
		return htmlContent;
	}


	/**
	 * @param htmlContent the htmlContent to set
	 */
	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}

	/**
	 * @return the page
	 */
	public String getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(String page) {
		this.page = page;
	}

	/**
	 * @return the moreLinkColumn
	 */
	public String getMoreLinkColumn() {
		return moreLinkColumn;
	}

	/**
	 * @param moreLinkColumn the moreLinkColumn to set
	 */
	public void setMoreLinkColumn(String moreLinkColumn) {
		this.moreLinkColumn = moreLinkColumn;
	}

	/**
	 * @return the moreLinkColumnUrl
	 */
	public String getMoreLinkColumnUrl() {
		return moreLinkColumnUrl;
	}

	/**
	 * @param moreLinkColumnUrl the moreLinkColumnUrl to set
	 */
	public void setMoreLinkColumnUrl(String moreLinkColumnUrl) {
		this.moreLinkColumnUrl = moreLinkColumnUrl;
	}

	/**
	 * @return the moreLink
	 */
	public String getMoreLink() {
		return moreLink;
	}

	/**
	 * @param moreLink the moreLink to set
	 */
	public void setMoreLink(String moreLink) {
		this.moreLink = moreLink;
	}

	/**
	 * @return the moreLinkPic
	 */
	public String getMoreLinkPic() {
		return moreLinkPic;
	}

	/**
	 * @param moreLinkPic the moreLinkPic to set
	 */
	public void setMoreLinkPic(String moreLinkPic) {
		this.moreLinkPic = moreLinkPic;
	}

	/**
	 * @return the latestMap
	 */
	public Map getLatestMap() {
		return latestMap;
	}

	/**
	 * @param latestMap the latestMap to set
	 */
	public void setLatestMap(Map latestMap) {
		this.latestMap = latestMap;
	}

	/**
	 * @return the pageSite
	 */
	public String getPageSite() {
		return pageSite;
	}

	/**
	 * @param pageSite the pageSite to set
	 */
	public void setPageSite(String pageSite) {
		this.pageSite = pageSite;
	}



}