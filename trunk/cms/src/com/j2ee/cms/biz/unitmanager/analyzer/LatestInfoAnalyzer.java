/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.unitmanager.analyzer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.j2ee.cms.biz.articlemanager.dao.ArticleAttributeDao;
import com.j2ee.cms.biz.articlemanager.dao.ArticleDao;
import com.j2ee.cms.biz.articlemanager.domain.Article;
import com.j2ee.cms.biz.columnmanager.dao.ColumnDao;
import com.j2ee.cms.biz.columnmanager.domain.Column;
import com.j2ee.cms.biz.templatemanager.dao.TemplateInstanceDao;
import com.j2ee.cms.biz.templatemanager.dao.TemplateUnitDao;
import com.j2ee.cms.biz.templatemanager.domain.TemplateUnit;
import com.j2ee.cms.sys.GlobalConfig;
import com.j2ee.cms.sys.SiteResource;
import com.j2ee.cms.common.core.util.CollectionUtil;
import com.j2ee.cms.common.core.util.DateUtil;
import com.j2ee.cms.common.core.util.FileUtil;
import com.j2ee.cms.common.core.util.IDFactory;
import com.j2ee.cms.common.core.util.SqlUtil;
import com.j2ee.cms.common.core.util.StringUtil;
import com.j2ee.cms.common.core.util.XmlUtil;

/**
 * 
 * <p>标题: —— 最新信息解析类</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 模板单元管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-6-9 上午09:39:41
 * @history（历次修订内容、修订人、修订时间等）
 */
public class LatestInfoAnalyzer implements TemplateUnitAnalyzer {
	/** 日志 */
	private static final Logger log = Logger.getLogger(LatestInfoAnalyzer.class);
	/** 注入模板单元dao */
	private TemplateUnitDao templateUnitDao;	
	/** 注入模板实例dao */
	private TemplateInstanceDao templateInstanceDao;
	/** 注入栏目dao */
	private ColumnDao columnDao;
	/** 注入文章属性dao */
	private ArticleAttributeDao articleAttributeDao;
	/** 文章dao */
	private ArticleDao articleDao;
	/** 是否显示更多 */
	private String displayMore = "0";
	
	public String getHtml(String unitId, String articleId, String columnId, String siteId,  Map<String,String> commonLabel) {
		TemplateUnit templateUnit = templateUnitDao.getAndClear(unitId);
		String path = templateUnit.getConfigFile(); 
		path = "/" + GlobalConfig.appName + path;
		StringBuffer sb = new StringBuffer();
		
		TemplateUnit unit = templateUnitDao.getAndNonClear(unitId);
		String configFilePath = unit.getConfigFile();
		//获取到当前模板实例的xml配置文件路径
		String filePath = GlobalConfig.appRealPath + configFilePath;
		XmlUtil xmlUtil = XmlUtil.getInstance(filePath);
		String pageSite = xmlUtil.getNodeText("j2ee.cms/latest-info/pageSite");
		int realPage = this.findLatestInfo(unit, siteId);
		sb.append(SiteResource.getPageJsPath());
		sb.append("<style type=\"text/css\" media=\"all\"> .mj_pagefoot_green{text-align:"+pageSite+";}</style>");
		String id = IDFactory.getId();
		String dir = "/" + GlobalConfig.appName + "/release/site"+siteId+"/build/static/latestInfo"; //templateUnit.getConfigDir();
		String appName = GlobalConfig.appName; 
		sb.append("<input type=\"hidden\" name=\"latestinfo\" value=\""+ unitId +"\" />");
		sb.append("<div id=\""+ id +"_display\"></div>");
		sb.append("<div id=\"pager\"></div>");
		sb.append("<SCRIPT LANGUAGE=\"JavaScript\">");
		sb.append("	window.onload = function() {");
		sb.append("		var a = document.getElementsByName(\"latestinfo\");");
		sb.append("		for(var i = 0; i < a.length; i++) {");
		sb.append("			getPage(a[i].value, \""+ path +"\", \""+ id +"\", \""+ dir +"\", \""+appName+"\", \""+realPage+"\", \""+displayMore+"\", \""+siteId+"\");");
		sb.append("		}");
		sb.append("	}");
		sb.append("</SCRIPT>");
		return sb.toString();
	}
	
	
	private int findLatestInfo(TemplateUnit templateUnit, String siteId) {
		int realPage = 0;
		String columnIds = "";
		String configFile = templateUnit.getConfigFile();
		// 拷贝最新信息xml配置文件
		String destFileDir = SiteResource.getBuildStaticDir(siteId, false)+"/latestInfo";
		if(!FileUtil.isExist(destFileDir)) {
			FileUtil.makeDirs(destFileDir);
		}
		XmlUtil xmlUtil = XmlUtil.getInstance(GlobalConfig.appRealPath + configFile);
		String unitType = xmlUtil.getNodeText("/j2ee.cms/latest-info/unitType");
		String allColumn = xmlUtil.getNodeText("/j2ee.cms/latest-info/allColumn");
		String selectCol = xmlUtil.getNodeText("/j2ee.cms/latest-info/selectCol");
		String chooseColumn = xmlUtil.getNodeText("/j2ee.cms/latest-info/chooseColumn");
		String ispage = xmlUtil.getNodeText("/j2ee.cms/latest-info/page");
		String row = xmlUtil.getNodeText("/j2ee.cms/latest-info/row");
		String col = xmlUtil.getNodeText("/j2ee.cms/latest-info/col");
		String count = xmlUtil.getNodeText("/j2ee.cms/latest-info/count");   // 显示总数
		String pageCount = xmlUtil.getNodeText("/j2ee.cms/latest-info/pageCount");  //  每页显示数目
		// 指定栏目（只有一个）
		if(unitType.equals("1")) {
			if(!StringUtil.isEmpty(chooseColumn)) {
				String[] columnIdAndColumnName = chooseColumn.split("##");
				columnIds = columnIdAndColumnName[0].toString();
			}
			
		// 其他栏目	
		} else {
			// 包含所有
			if(selectCol.equals("0")) {
				List<Column> list = columnDao.findByNamedQuery("findColumnByCurrentSite", "siteId", siteId);
				if(list != null && list.size() > 0) {
					for(int i = 0; i < list.size(); i++) {
						if(!columnIds.equals("")) {
							columnIds += "," + list.get(i).getId();
						} else {
							columnIds = list.get(i).getId();
						}
					}
				}
				
			// 包含所选	
			} else if(selectCol.equals("1")) {
				if(!StringUtil.isEmpty(allColumn)) {
					String[] columnIdAndColumnName = allColumn.split("##");
					columnIds = columnIdAndColumnName[0].toString();
				}
				
			// 不包含所选	
			} else {
				String tempIds = "";
				if(!StringUtil.isEmpty(allColumn)) {
					String[] columnIdAndColumnName = allColumn.split("##");
					tempIds = columnIdAndColumnName[0].toString();
				}
				if(!tempIds.equals("")) {
					tempIds =  SqlUtil.toSqlString(tempIds);
					String[] params1 = {"siteId", "ids"};
					String[] values1 = {SqlUtil.toSqlString(siteId), tempIds};
					List<Column> list = columnDao.findByDefine("findColumnAndColumnIdsNotInFixedIdsAndSiteId", params1, values1);
					if(!CollectionUtil.isEmpty(list)) {
						for(int i = 0; i < list.size(); i++) {
							if(!columnIds.equals("")) {
								columnIds += "," + list.get(i).getId();
							} else {
								columnIds = list.get(i).getId();
							}
						}
					}
				}
			}
		} 
		String tempColumnIds = SqlUtil.toSqlString(columnIds);
		// 查询栏目下面的文章   
		///////////////////////////////////
		//最多要查询前999条
		/////////////////////////////////
		List list = null;
		if(ispage.equals("yes")){
			list = articleDao.findByDefine("findAuditedArticlesByColumnIds", "columnId", tempColumnIds, 0, StringUtil.parseInt(count));	
		}else{
			list = articleDao.findByDefine("findAuditedArticlesByColumnIds", "columnId", tempColumnIds, 0, StringUtil.parseInt(row)*StringUtil.parseInt(col));
		}
		List list1 = articleDao.findByDefine("findAuditedArticlesByColumnIds", "columnId", tempColumnIds, 0, 999);
		if(ispage.equals("yes")){
			displayMore = "0";
		}else{
			if(list.size() < list1.size()){
				displayMore = "1";
			}else{
				displayMore = "0";
			}
		}
//		System.out.println("=article.size====================="+list.size());
		File file = new File(SiteResource.getBuildStaticDir(siteId, false)+"/latestInfo");
		File[] files = file.listFiles();
		if(files.length > 0) {
			for(int i = 0; i < files.length; i++) {
				if(files[i].getName().startsWith(templateUnit.getId())) {
					FileUtil.delete(files[i].toString());
				}
			}
		}
		if(!CollectionUtil.isEmpty(list)) { 
			realPage = this.createXml(list, templateUnit.getId(), siteId, ispage, row, col, count, pageCount);
		}
		return realPage;
	}

	/**
	 * 创建xml文件
	 * @param fileName
	 * @param tmp
	 * @param articleId
	 * @param siteId
	 * @param divId
	 * @param localPath
	 * @return
	 */
    private int createXml(List list, String unitId, String siteId, String ispage, String row, String col, String count, String pageCount1) {
   // 	System.out.println("createXml start............................");
    	// 要显示的信息数
    	int infoCount = 0;
    	// 要显示的文章页数
    	int page = 0;
    	// 每页显示的文章数目
    	int pageCount = StringUtil.parseInt(pageCount1);
    	// 分页
    	if(ispage.equals("yes")) {
    		infoCount = StringUtil.parseInt(count);
    		if(infoCount > list.size()) {
        		infoCount = list.size();
        	}
    		// 获取页数
    		page = infoCount / pageCount;
    		if(infoCount % pageCount != 0) {
    			page++;
    		}
    	
    	// 不分页	
    	} else {
    		infoCount = StringUtil.parseInt(row) * StringUtil.parseInt(col);
    		if(infoCount > list.size()) {
        		infoCount = list.size();
        	}
    		pageCount = infoCount;
    		page = 1;
    	}
    	int a = 0;
    	int tmp = pageCount;
    	// 循环将文章的数据写入xml文件中
    	for(int p = 0; p < page; p++) {
	    	Document document = DocumentHelper.createDocument();    
			Element rootElement = document.addElement("j2ee.cms"); 
			
			Element copyRight = rootElement.addElement("copyright");
			copyRight.addCDATA("j2ee.cms Net Work");
			if(infoCount < pageCount) {
				tmp = infoCount;
			}
			Element latestInfo = rootElement.addElement("article-page");
			for(int i = a; i < a+tmp; i++) {
				Article article = (Article) list.get(i);
				if(article != null) {
					Element articles = latestInfo.addElement("article");

					/*Element siteName = articles.addElement("siteName");
					siteName.addCDATA(article.getSite().getName());

					Element siteLink = articles.addElement("siteLink");
					siteLink.addCDATA(article.getSite().getUrl());*/

					Element columnName = articles.addElement("columnName");
					columnName.addCDATA(article.getColumn().getName());

					Element columnLink = articles.addElement("columnLink");
					String columnId = article.getColumn().getId();
					Column column = columnDao.getAndClear(columnId);
					String newUrl = "";
					if(column.getColumnType().equals("single")){
						if(!StringUtil.isEmpty(columnId)){
							List<Article> list1 = articleDao.findByNamedQuery("findAuditedArticlesByColumnId", "columnId", columnId, 0, 1);
							if(!CollectionUtil.isEmpty(list1)){
								newUrl = SiteResource.getUrl(list1.get(0).getUrl(), true);
							}
						}
					}else{
						newUrl =  SiteResource.getUrl(article.getColumn().getUrl(), true);
					}
					columnLink.addCDATA(newUrl);

					Element title = articles.addElement("articletitle");
					title.addCDATA(article.getTitle());

					Element url = articles.addElement("articleurl");
					url.addCDATA(article.getUrl());

					Element subtitle = articles.addElement("articlesubtitle");
					subtitle.addCDATA(article.getSubtitle());

					//Element articlequotetitle = articles.addElement("articlequotetitle");
					//articlequotetitle.addCDATA(article.getLeadingTitle());

					//Element articlebrief = articles.addElement("articlebrief");
					//articlebrief.addCDATA(article.getBrief());
					//文章作者
					Element articleauthor = articles.addElement("articleauthor");
					articleauthor.addCDATA(article.getAuthor());
					//文章点击数
					Element articlehits = articles.addElement("articlehits");
					articlehits.addCDATA(String.valueOf(article.getHits()));
					//创建时间
					Element createTime = articles.addElement("createTime");
					createTime.addCDATA(DateUtil.toString(article.getCreateTime()));
					//显示时间
					Element displayTime = articles.addElement("displayTime");
					displayTime.addCDATA(DateUtil.toString(article.getDisplayTime()));
					/*
					
					//审核时间
					Element auditTime = articles.addElement("auditTime");
					auditTime.addCDATA(DateUtil.toString(article.getAuditTime()));
					//发布时间
					Element publishTime = articles.addElement("publishTime");
					publishTime.addCDATA(DateUtil.toString(article.getPublishTime()));
					//失效时间
					Element invalidTime = articles.addElement("invalidTime");
					invalidTime.addCDATA(DateUtil.toString(article.getInvalidTime()));
					//图片地址
					Element pic1 = articles.addElement("pic");
					pic1.addCDATA(article.getPic());					
					// 信息来源
					Element infoSource = articles.addElement("infoSource");
					infoSource.addCDATA(article.getInfoSource());
					// 关键字
					Element keyword = articles.addElement("keyword");
					keyword.addCDATA(article.getKeyword());
					// 是否删除
					Element deleted = articles.addElement("deleted");
					if(article.isDeleted()) {
						deleted.addCDATA("是");
					} else {
						deleted.addCDATA("否");
					}
					// 是否审核
					Element audited = articles.addElement("audited");
					if(article.isAudited()) {
						audited.addCDATA("是");
					} else {
						audited.addCDATA("否");
					}
					// 发布状态
					Element publishState = articles.addElement("publishState");
					if(article.getPublishState().equals(article.PUBLISH_STATE_UNPUBLISHEED)) {
						publishState.addCDATA("未发布");
					} else if(article.getPublishState().equals(article.PUBLISH_STATE_PUBLISHED)) {
						publishState.addCDATA("已发布");
					} else if(article.getPublishState().equals(article.PUBLISH_STATE_PUBLISHING)) {					
						publishState.addCDATA("发布中");
					}else if(article.getPublishState().equals(article.PUBLISH_STATE_DRAFT)) {					
						publishState.addCDATA("已撤稿");
					}
					// 关键词过滤
					Element keyFilter = articles.addElement("keyFilter");
					if(article.isKeyFilter()) {
						keyFilter.addCDATA("是");
					} else {
						keyFilter.addCDATA("否");
					}*/
					// 置顶
					Element toped = articles.addElement("toped");
					if(article.isToped()) {
						toped.addCDATA("是");						
					} else {
						toped.addCDATA("否");
					}
				
				}
			}
			a += pageCount;
			if(infoCount - a < pageCount) {
				tmp = infoCount - a;
			}
			XMLWriter output = null;
			try {
				String staticDir = SiteResource.getBuildStaticDir(siteId, false);
				String infoDir = staticDir + "/latestInfo";
				if(!FileUtil.isExist(infoDir)) {
					FileUtil.makeDirs(infoDir);
				}
				String latestInfoXml = infoDir + "/" + unitId +"_"+ (p+1) +".xml";
				if(FileUtil.isExist(latestInfoXml)) {
					FileUtil.delete(latestInfoXml);
				}
				OutputFormat of = OutputFormat.createPrettyPrint();
				of.setEncoding("UTF-8");
				output = new XMLWriter(new FileOutputStream(new File(latestInfoXml)));
				output.write(document);
				
			//	System.out.println("写入成功");
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					output.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
    	}
    	return page;
	}

	/**
	 * @param templateUnitDao the templateUnitDao to set
	 */
	public void setTemplateUnitDao(TemplateUnitDao templateUnitDao) {
		this.templateUnitDao = templateUnitDao;
	}

	/**
	 * @param templateInstanceDao the templateInstanceDao to set
	 */
	public void setTemplateInstanceDao(TemplateInstanceDao templateInstanceDao) {
		this.templateInstanceDao = templateInstanceDao;
	}


	/**
	 * @param columnDao the columnDao to set
	 */
	public void setColumnDao(ColumnDao columnDao) {
		this.columnDao = columnDao;
	}


	/**
	 * @param articleAttributeDao the articleAttributeDao to set
	 */
	public void setArticleAttributeDao(ArticleAttributeDao articleAttributeDao) {
		this.articleAttributeDao = articleAttributeDao;
	}


	/**
	 * @param articleDao the articleDao to set
	 */
	public void setArticleDao(ArticleDao articleDao) {
		this.articleDao = articleDao;
	}

}
