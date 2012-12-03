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
import org.dom4j.io.XMLWriter;

import com.j2ee.cms.biz.articlemanager.dao.ArticleAttachDao;
import com.j2ee.cms.biz.articlemanager.dao.ArticleAttributeDao;
import com.j2ee.cms.biz.articlemanager.dao.ArticleDao;
import com.j2ee.cms.biz.articlemanager.domain.Article;
import com.j2ee.cms.biz.articlemanager.domain.ArticleAttach;
import com.j2ee.cms.biz.columnmanager.dao.ColumnDao;
import com.j2ee.cms.biz.columnmanager.domain.Column;
import com.j2ee.cms.biz.sitemanager.dao.SiteDao;
import com.j2ee.cms.biz.templatemanager.dao.TemplateInstanceDao;
import com.j2ee.cms.biz.templatemanager.dao.TemplateUnitDao;
import com.j2ee.cms.biz.templatemanager.domain.TemplateUnit;
import com.j2ee.cms.common.core.util.CollectionUtil;
import com.j2ee.cms.common.core.util.DateUtil;
import com.j2ee.cms.common.core.util.FileUtil;
import com.j2ee.cms.common.core.util.IDFactory;
import com.j2ee.cms.common.core.util.StringUtil;
import com.j2ee.cms.common.core.util.XmlUtil;
import com.j2ee.cms.sys.GlobalConfig;
import com.j2ee.cms.sys.SiteResource;

/**
 * 
 * <p>标题: —— 标题链接解析类</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 模板单元管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-6-9 上午09:39:41
 * @history（历次修订内容、修订人、修订时间等）
 */
public class TitleLinkPageAnalyzer implements TemplateUnitAnalyzer {
	/** 日志 */
	private static final Logger log = Logger.getLogger(TitleLinkPageAnalyzer.class);
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
	/** 网站dao */
	private SiteDao siteDao;
	/** 注入文章附件dao */
    private ArticleAttachDao articleAttachDao;
	
	/**
	 * 解析html代码并返回给页面
	 * @param unitId
	 * @param columnId
	 * @param siteId
	 * @param commonLabel
	 * @return
	 */
	public String getHtml(String unitId, String articleId, String columnId, String siteId, Map<String,String> commonLabel) {
		StringBuilder sb = new StringBuilder();
	//	sb.append(SiteResource.getPageJsPath());
		TemplateUnit unit = templateUnitDao.getAndNonClear(unitId);
		String configFilePath = unit.getConfigFile();
		configFilePath = configFilePath.replace("//", "/");
		//获取到当前模板实例的xml配置文件路径
		String filePath = GlobalConfig.appRealPath + configFilePath;
		// 获得新的栏目id
		columnId = this.getColumnId(columnId, siteId, filePath);
		log.debug("titlelink$$$$$$$$##############columnId=========="+columnId);
		int page = 0;
		List<Article> list = null;
		if(!StringUtil.isEmpty(columnId) && !columnId.equals("0")) {
			// 查询前999条
			list = articleDao.findByNamedQuery("findAuditedArticlesByColumnId", "columnId", columnId, 0, 999);
			if(!CollectionUtil.isEmpty(list)) {
				XmlUtil xmlUtil = XmlUtil.getInstance(filePath);
				// 每页信息数
				String pageInfoCount = xmlUtil.getNodeText("j2ee.cms/title-link-page/pageInfoCount");
				//总页数
				page = this.createXml(list, unit.getId(), siteId, pageInfoCount);
				String pageSite = xmlUtil.getNodeText("j2ee.cms/title-link-page/pageSite");
				sb.append("<style type=\"text/css\" media=\"all\"> .mj_pagefoot_green{text-align:"+pageSite+";}</style>");
			}
		}
		// path : /cps1.0/release/site200909202044556621/template_instance/1253531769265/conf/200909221732342181578487400.xml
		String path = "/" + GlobalConfig.appName + configFilePath;
		String id = IDFactory.getId();
		String dir = SiteResource.getLatestInfoPath(GlobalConfig.appName,siteId); 
		
		sb.append("<input type=\"hidden\" name=\"titleLinkPage\" value=\""+ columnId +"\" />");
		sb.append("<div id=\""+ id +"_display\">无数据</div>"); 
		sb.append("<div id=\"pager\"></div>");
		sb.append("<SCRIPT LANGUAGE=\"JavaScript\">");
		sb.append("	window.onload = function() {");
		sb.append("		var a = document.getElementsByName(\"titleLinkPage\");");
		sb.append("		for(var i = 0; i < a.length; i++) {");
		sb.append("			getTitleLinkPage(a[i].value, \""+ path +"\", \""+ id +"\", \""+ dir +"\", \""+ GlobalConfig.appName +"\", "+ list.size() +", \""+siteId+"\");");
		sb.append("		}");
		sb.append("	}");
		sb.append("</SCRIPT>");
		return sb.toString();
	}
	
	/**
	 * 创建xml文件
	 * @param fileName
	 * @param tmp
	 * @param articleId
	 * @param siteId
	 * @param pageInfoCount   每页信息数
	 * @return
	 */
    private int createXml(List list, String unitId, String siteId, String pageInfoCount) {
    	log.debug("createXml titleLinkPage start............................");
    	// 获得xml数目
    	int len = list.size();
    	int page = 0;
    	int count = StringUtil.parseInt(pageInfoCount); 
    	if(count != 0) {
    		page = len / count;
    		int mod = len % count;
        	if(mod > 0) {
        		page++;
        	}
    	}
    	int a = 0;
    	int tmp = count;
    	if(list.size() < count) {
			tmp = list.size();
		}
    	log.debug("size========"+list.size());
    	String columnId = "";
    	// 循环将文章的数据写入xml文件中
    	for(int p = 0; p < page; p++) {
	    	Document document = DocumentHelper.createDocument();
			Element rootElement = document.addElement("j2ee.cms");
			Element copyRight = rootElement.addElement("copyright");
			copyRight.addCDATA("j2ee.cms Net Work");
			Element latestInfo = rootElement.addElement("article-page");
			for(int i = a; i < (a+tmp); i++) {
				log.debug("after ==========="+ (i));
				Article article = (Article) list.get(i);
				if(article != null) {
					Element articles = latestInfo.addElement("article");
					// 栏目名称
					Element columnName = articles.addElement("columnName");
					columnName.addCDATA(article.getColumn().getName());
					// 栏目链接
					Element columnLink = articles.addElement("columnLink");
					columnId = article.getColumn().getId();
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
					// 标题
					Element title = articles.addElement("articletitle");
					title.addCDATA(article.getTitle());
					// 链接
					Element url = articles.addElement("articleurl");
					url.addCDATA(article.getUrl());
					// 作者
					Element articleauthor = articles.addElement("articleauthor");
					articleauthor.addCDATA(article.getAuthor());
					// 点击次数
					Element articlehits = articles.addElement("articlehits");
					articlehits.addCDATA(String.valueOf(article.getHits()));
					// 显示时间
					Element displayTime = articles.addElement("displayTime");
					displayTime.addCDATA(DateUtil.toString(article.getDisplayTime()));
					// 发布时间
					Element publishTime = articles.addElement("publishTime");
					publishTime.addCDATA(DateUtil.toString(article.getPublishTime()));
					// 失效时间
					Element invalidTime = articles.addElement("invalidTime");
					invalidTime.addCDATA(DateUtil.toString(article.getInvalidTime()));
					// 附件
					String[] params = {"articleId", "type"};
	                Object[] values = {article.getId(), "attach"};
	                List<ArticleAttach> attachList = articleAttachDao.findByNamedQuery("findArticleAttachsByArticleIdAndAttachType", params, values);
	                String attachPath = "";
	                if(attachList != null && attachList.size() > 0){
	                    ArticleAttach attach = attachList.get(0);
	                    if(attach.getMajor() != null && attach.getMajor() == 1){
	                        attachPath = attach.getPath();
	                    }
	                }
                    Element attach = articles.addElement("attach");
                    attach.addCDATA(attachPath);
                    // 摘要
                    Element brief = articles.addElement("brief");
                    brief.addCDATA(article.getBrief());
                    // 置顶
					Element toped = articles.addElement("toped");
					if(article.isToped()) {
					    toped.addCDATA("是");
					} else {
					    toped.addCDATA("否");
					}
				}
			}
			a += count;
			if(list.size() - a < count) {
				tmp = list.size() - a;
			} 
			XMLWriter output = null;
			try {
				String staticDir = SiteResource.getBuildStaticDir(siteId, false);
				String infoDir = staticDir + File.separator + "latestInfo";
				if(!FileUtil.isExist(infoDir)) {
					FileUtil.makeDirs(infoDir);
				}
				String titleLinkPageXml = infoDir + File.separator + columnId +"_"+ (p+1) +".xml";
				if(FileUtil.isExist(titleLinkPageXml)) {
					FileUtil.delete(titleLinkPageXml);
				}
//				OutputFormat of = OutputFormat.createPrettyPrint();
//				of.setEncoding("UTF-8");
				output = new XMLWriter(new FileOutputStream(new File(titleLinkPageXml)));
				output.write(document);
				output.close();
			//	System.out.println("写入标题链接分页文章成功");
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
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
	 * 获取页面所选择的栏目
	 * @param columnId
	 * @param siteId
	 * @param filePath
	 * @return
	 */
	private String getColumnId(String columnId, String siteId, String filePath){
		XmlUtil xmlUtil = XmlUtil.getInstance(filePath);
		//内容来源
		String contextFrom = xmlUtil.getNodeText("j2ee.cms/title-link-page/contextFrom");
		//栏目名称
		String fixedColumn = xmlUtil.getNodeText("j2ee.cms/title-link-page/fixedColumn");
		String fixedColumnId = "";
		String strColumn[] = fixedColumn.split("##");
		if(strColumn != null && strColumn.length == 2){
			fixedColumnId = strColumn[0];
		}
		String newColumnId = "";
		Column column = new Column();
		if(!StringUtil.isEmpty(columnId) && !columnId.equals("0")) {
			 column = columnDao.getAndNonClear(columnId);
		} else {
			columnId = "";
		}
		// 当前栏目
		if(contextFrom.equals("1")) {
			newColumnId = columnId;
			
		// 当前栏目的父栏目	
		}else if(contextFrom.equals("2")) {
			if(column != null && column.getParent() != null) {
				newColumnId = column.getParent().getId();
			}
			
		// 指定栏目	
		}else if(contextFrom.equals("3")) {
			newColumnId = fixedColumnId;
			
		// 指定栏目的父栏目	
		}else if(contextFrom.equals("4")) {
			Column newcolumn = columnDao.getAndClear(fixedColumnId);
			if(newcolumn.getParent() != null) {
				newColumnId = newcolumn.getParent().getId();
			}
		}
		return newColumnId;
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

	/**
	 * @param siteDao the siteDao to set
	 */
	public void setSiteDao(SiteDao siteDao) {
		this.siteDao = siteDao;
	}

    public void setArticleAttachDao(ArticleAttachDao articleAttachDao) {
        this.articleAttachDao = articleAttachDao;
    }
}
