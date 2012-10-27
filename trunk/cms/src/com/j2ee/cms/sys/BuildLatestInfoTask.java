/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.sys;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.j2ee.cms.biz.articlemanager.dao.ArticleDao;
import com.j2ee.cms.biz.articlemanager.domain.Article;
import com.j2ee.cms.biz.columnmanager.dao.ColumnDao;
import com.j2ee.cms.biz.columnmanager.domain.Column;
import com.j2ee.cms.biz.sitemanager.dao.SiteDao;
import com.j2ee.cms.biz.sitemanager.domain.Site;
import com.j2ee.cms.biz.templatemanager.dao.TemplateUnitDao;
import com.j2ee.cms.biz.templatemanager.domain.TemplateUnit;
import com.j2ee.cms.common.core.util.CollectionUtil;
import com.j2ee.cms.common.core.util.DateUtil;
import com.j2ee.cms.common.core.util.FileUtil;
import com.j2ee.cms.common.core.util.SqlUtil;
import com.j2ee.cms.common.core.util.StringUtil;
import com.j2ee.cms.common.core.util.XmlUtil;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CPS</p>
 * <p>版权: Copyright (c) 2009  </p>
 * <p>网址：http://www.j2ee.cmsweb.com
 * @author <a href="mailto:sean_yang@163.com">杨信</a>
 * @version 1.0
 * @since 2009-8-31 下午06:31:32
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class BuildLatestInfoTask {

	/** 注入网站dao */
	private SiteDao siteDao;
	
	/** 注入单元dao */
	private TemplateUnitDao templateUnitDao;
	
	/** 注入栏目dao */
	private ColumnDao columnDao;
	
	/** 注入文章dao */
	private ArticleDao articleDao;
	
	// 定时建立xml文件
	public void build() {

		// 1. 查询网站
		List<Site> siteList = siteDao.findByNamedQuery("findSiteByDeleted");
		
		for(Site site : siteList) {
			String siteId = site.getId();
			String[] params = {"siteId", "categoryId"};
			Object[] values = {siteId, "t6"};
			String columnIds = "";
			// 2. 按照网站查找最新信息单元
			List<TemplateUnit> templateUnitList = templateUnitDao.findByNamedQuery("findTemplateUnitBySiteIdAndUnitCategory", params, values);
			for(TemplateUnit templateUnit : templateUnitList) {
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
							String[] values1 = {siteId, tempIds};
							List<Column> list = columnDao.findByDefine("findColumnAndColumnIdsNotInFixedIdsAndSiteId", params1, values1);
							if(list != null && list.size() > 0) {
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
				//要查询前999条
				/////////////////////////////////
//				List list = articleDao.findByDefine("findArticlesByColumnIds", "columnIds", tempColumnIds, 0, 999);
				List list = null;
				if(ispage.equals("yes")){
					list = articleDao.findByDefine("findArticlesByColumnIds", "columnIds", tempColumnIds, 0, StringUtil.parseInt(count));	
				}else{
					list = articleDao.findByDefine("findArticlesByColumnIds", "columnIds", tempColumnIds, 0, StringUtil.parseInt(row)*StringUtil.parseInt(col));
				}
//				System.out.println("=article.size====================="+list.size());
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
					this.createXml(list, templateUnit.getId(), site.getId(), ispage, row, col, count, pageCount);
				}
			}
		}
		siteDao.clearCache();
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
    private void createXml(List list, String unitId, String siteId, String ispage, String row, String col, String count, String pageCount1) {
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


					Element columnName = articles.addElement("columnName");
					columnName.addCDATA(article.getColumn().getName());

					Element columnLink = articles.addElement("columnLink");
					columnLink.addCDATA(article.getColumn().getUrl());

					Element title = articles.addElement("articletitle");
					title.addCDATA(article.getTitle());

					Element url = articles.addElement("articleurl");
					url.addCDATA(article.getUrl());


					Element articleauthor = articles.addElement("articleauthor");
					articleauthor.addCDATA(article.getAuthor());

					Element articlehits = articles.addElement("articlehits");
					articlehits.addCDATA(String.valueOf(article.getHits()));

					Element displayTime = articles.addElement("displayTime");
					displayTime.addCDATA(DateUtil.toString(article.getDisplayTime()));

					Element publishTime = articles.addElement("publishTime");
					publishTime.addCDATA(DateUtil.toString(article.getPublishTime()));

					
				
				
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
				XMLWriter output = new XMLWriter(new FileOutputStream(new File(latestInfoXml)));
				output.write(document);
				output.close();
		//		System.out.println("写入成功");
			} catch (Exception e) {
				e.printStackTrace();
			} 
    	}
	}
	
	/**
	 * @param siteDao the siteDao to set
	 */
	public void setSiteDao(SiteDao siteDao) {
		this.siteDao = siteDao;
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
	 * @param articleDao the articleDao to set
	 */
	public void setArticleDao(ArticleDao articleDao) {
		this.articleDao = articleDao;
	}
}
