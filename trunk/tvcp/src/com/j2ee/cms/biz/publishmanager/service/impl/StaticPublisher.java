/**
 * project：通用内容管理系统
 * Company:   
 */
package com.j2ee.cms.biz.publishmanager.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.jdbc.core.JdbcTemplate;

import com.j2ee.cms.biz.articlemanager.dao.ArticleDao;
import com.j2ee.cms.biz.articlemanager.domain.Article;
import com.j2ee.cms.biz.columnmanager.dao.ColumnDao;
import com.j2ee.cms.biz.columnmanager.domain.Column;
import com.j2ee.cms.biz.publishmanager.dao.ArticleBuildListDao;
import com.j2ee.cms.biz.publishmanager.dao.ArticlePublishListDao;
import com.j2ee.cms.biz.publishmanager.domain.ArticleBuildList;
import com.j2ee.cms.biz.publishmanager.service.Publisher;
import com.j2ee.cms.biz.publishmanager.service.remotepublish.client.FtpSender;
import com.j2ee.cms.biz.publishmanager.service.remotepublish.client.Sender;
import com.j2ee.cms.biz.sitemanager.dao.SiteDao;
import com.j2ee.cms.biz.sitemanager.domain.Site;
import com.j2ee.cms.biz.templatemanager.dao.TemplateCategoryDao;
import com.j2ee.cms.biz.templatemanager.dao.TemplateDao;
import com.j2ee.cms.biz.templatemanager.dao.TemplateInstanceDao;
import com.j2ee.cms.biz.templatemanager.dao.TemplateUnitCategoryDao;
import com.j2ee.cms.biz.templatemanager.dao.TemplateUnitDao;
import com.j2ee.cms.biz.templatemanager.domain.TemplateInstance;
import com.j2ee.cms.biz.templatemanager.domain.TemplateUnit;
import com.j2ee.cms.biz.templatemanager.domain.TemplateUnitCategory;
import com.j2ee.cms.biz.unitmanager.analyzer.ArticleTextAnalyzer;
import com.j2ee.cms.biz.unitmanager.analyzer.MagazineCategoryAnalyzer;
import com.j2ee.cms.biz.unitmanager.analyzer.OnlineSurverySetAnalyzer;
import com.j2ee.cms.biz.unitmanager.analyzer.TemplateUnitAnalyzer;
import com.j2ee.cms.biz.unitmanager.analyzer.TitleLinkPageAnalyzer;
import com.j2ee.cms.plugin.onlineBulletin.dao.OnlineBulletinDao;
import com.j2ee.cms.plugin.onlineBulletin.domain.OnlineBulletin;
import com.j2ee.cms.sys.GlobalConfig;
import com.j2ee.cms.sys.SiteResource;
import com.j2ee.cms.common.core.util.BeanUtil;
import com.j2ee.cms.common.core.util.CollectionUtil;
import com.j2ee.cms.common.core.util.DateUtil;
import com.j2ee.cms.common.core.util.FileUtil;
import com.j2ee.cms.common.core.util.StringUtil;
import org.apache.log4j.Logger;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 发布管理</p>
 * <p>版权: Copyright (c) 2009  </p>
 * @author <a href="mailto:sean_yang@163.com">杨信</a>
 * @version 1.0
 * @since 2009-6-18 上午11:19:42
 * @history（历次修订内容、修订人、修订时间等）
 */
public class StaticPublisher implements Publisher {
	
	private static final Logger log = Logger.getLogger(StaticPublisher.class);
	
	/** 单元类别Map  - key:id  value:name*/
	private static Map<String,String> categoryMap;
	
	/** 注入jdbc模板 */
	private JdbcTemplate jdbcTemplate;
	
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
	
	/** 注入标题链接分页解析器 */
	private TitleLinkPageAnalyzer titleLinkPageAnalyzer;
	
	/** 注入期刊（按分类）解析器 */
	private MagazineCategoryAnalyzer magazineCategoryAnalyzer;
	
	/** 注入网上调查解析器 */
	private OnlineSurverySetAnalyzer onlineSurverySetAnalyzer;
	
	/** 注入模板单元Dao */
	private TemplateUnitDao templateUnitDao;
	
	/** 注入模板类别dao */
	private TemplateCategoryDao templateCategoryDao;
	
	/** 注入模板dao */
	private TemplateDao templateDao;
	
	/** 注入模板实例dao */
	private TemplateInstanceDao templateInstanceDao;
	
	/** 注入文章Dao */
	private ArticleDao articleDao;
	
	/** 注入栏目dao */
	private ColumnDao columnDao;
	
	/** 注入网站dao */
	private SiteDao siteDao;
	
	/** 注入单元类别dao */
	private TemplateUnitCategoryDao templateUnitCategoryDao;
	
	/** 注入文章生成dao */
	private ArticleBuildListDao articleBuildListDao;
	
	/** 发布列表dao */
	private ArticlePublishListDao articlePublishListDao;
	
	/** 注入网上调查问题dao */
	private OnlineBulletinDao onlineBulletinDao;
	
	/*
	 * 定时发布
	 * (non-Javadoc)
	 * @see com.j2ee.cms.biz.publishmanager.service.Publisher#publish()
	 */
	public void publish() {
		
	}
	
	public void publish(String columnIds, String publishType) {
		
	}
	
	/**
	 * 发布网站的首页
	 * @param siteId
	 */
	public void publishHomePage(String siteId) {
		// 初始化单元类别Map
		initCategoryMap();
		Site site = Site.siteMap.get(siteId);
		if (site == null) {
			site = new Site();
			BeanUtil.copyProperties(site, siteDao.getAndNonClear(siteId));
			Site.siteMap.put(siteId, site);
		}
		site = Site.siteMap.get(siteId);
		String homePagePath = GlobalConfig.appRealPath + site.getUrl();
		publishPageAndResource(homePagePath, siteId, true);
	}
	
	/**
	 * 发布一批栏目页
	 * @param columnIds
	 * @param siteId
	 */
	public void publishColumnsByColumnIds(String[] columnIds, String siteId) {
		// 初始化单元类别Map
		initCategoryMap();
		// 将栏目分批
		
		// 首先发布首页
		buildHomePage(siteId);
		publishHomePage(siteId);
		
		// 栏目循环
		for (int i = 0; i < columnIds.length; i++) {
			// 生成栏目页
			buildColumnByColumnId(columnIds[i], siteId);
			// 发布栏目页
			publishColumnByColumnId(columnIds[i], siteId);
		}
		
	}
	
	/**
	 * 发布单个栏目页
	 * @param columnId
	 * @param siteId
	 */
	public void publishColumnByColumnId(String columnId, String siteId) {
		Column column = columnDao.getAndClear(columnId);
		String columnPath = GlobalConfig.appRealPath + column.getUrl();
		publishPageAndResource(columnPath, siteId, true);
	}
	
	/**
	 * 生成单个栏目页
	 * @param columnId
	 * @param siteId
	 */
	public void buildColumnByColumnId(String columnId, String siteId) {
		StringBuffer scriptBuf = new StringBuffer();
		StringBuffer cssBuf = new StringBuffer();
		// 1、查询模板实例
		Column column = columnDao.getAndNonClear(columnId);
		TemplateInstance templateInstance = column.getColumnTemplate();
		Site site = siteDao.getAndNonClear(siteId);
	 
		if (templateInstance ==  null) {
			// 如果栏目的模版为空则选择网站默认的模版
//			Site site = siteDao.getAndNonClear(siteId);
			templateInstance = site.getColumnTemplate();
			if(templateInstance == null) {
				return;
			}
		}
		String instanceId = templateInstance.getId();
	 
		List<TemplateUnit> templateUnits = templateUnitDao.findByNamedQuery("findTemplateUnitByInstanceId", "id", instanceId);
		// 发布的内容map 
		Map<String,String> publishMap = new HashMap<String,String>();
		Map<String,String> namesMap = new HashMap<String,String>();
		for (TemplateUnit templateUnit : templateUnits) {
			String categoryId = null;
			String unitId = String.valueOf(templateUnit.getId());
			if (templateUnit != null) {
				scriptBuf.append(StringUtil.convert(templateUnit.getScript()));
				cssBuf.append(StringUtil.convert(templateUnit.getCss()));
				if (templateUnit.getTemplateUnitCategory() != null) {
					categoryId = templateUnit.getTemplateUnitCategory().getId();				 
					namesMap.put(unitId, categoryMap.get(String.valueOf(categoryId)));
				}
				publishMap.put(unitId, templateUnit.getHtml());
			}
		}
		// 2.组织替换内容
		
		// 公共标签
		Map<String,String> commonLabel = new HashMap<String,String>();

		// 3.读取模板文件内容
		String buildTemplateDir = SiteResource.getBuildStaticTemplateDir(siteId, false) + File.separator + templateInstance.getId();
		String fileName = FileUtil.getFileName(templateInstance.getLocalPath());
		
		String buildTemplateFile = buildTemplateDir + File.separator + fileName;
		if (new File(buildTemplateDir).exists()) {
			
			// 拷贝模板资源
			FileUtil.makeDirs(buildTemplateDir);
		}
			String templateFile = GlobalConfig.appRealPath + templateInstance.getLocalPath();
			String content = FileUtil.read(templateFile);
			String templateDir = FileUtil.getFileDir(templateFile);
			FileUtil.copy(templateDir, buildTemplateDir, false);
			
	/*		//替换模板文件中的资源路径
			String rsRegx = "<[^<]+=[\"']([^'\"]+[.][^'\"/-_]+)[\"'][>]";
			Pattern p = Pattern.compile(rsRegx);
			Matcher m = p.matcher(content);
			StringBuffer sb = new StringBuffer();
			while (m.find()) {
				String s = "/template_instance/"+ templateInstance.getId() + "/" + m.group(1);
				m.appendReplacement(sb, m.group().replaceAll("[^'\"]+[.][^'\"/-_]+", s));
			}
			m.appendTail(sb);*/
			
			FileUtil.write(buildTemplateFile,content.replace("images",  "/template_instance/"+ templateInstance.getId() + "/images" ));
			
			String latestInfConf = SiteResource.getBuildStaticDir(siteId, false) + File.separator +"latestInfo";
			if(!FileUtil.isExist(latestInfConf)) {
				FileUtil.makeDirs(latestInfConf);
			}
			//拷贝XML配置文件
			FileUtil.copy(buildTemplateDir + File.separator + "conf",  latestInfConf);
			// 删除无用的资源
			FileUtil.delete(buildTemplateDir + File.separator + "conf");
			FileUtil.delete(buildTemplateDir + File.separator + "template_set.jsp");
//		}
		
		//读出替换后的模板
		String templateStr = FileUtil.read(buildTemplateFile);
		
		
		// 4.替换单元模板内容
		String page = analyzerTemplate(null, columnId, siteId, publishMap,
				namesMap, commonLabel, templateStr, true);
		
		// 栏目页路径
		String columnFile = GlobalConfig.appRealPath  + column.getUrl();
		String columnDir = FileUtil.getFileDir(columnFile);
		if(!FileUtil.isExist(columnDir)) {
			FileUtil.makeDirs(columnDir);
		}
		
		// 5.建立css目录
		String cssDir = columnDir + File.separator + "css";
		String cssFile = columnDir + File.separator + "css"+File.separator + "index.css";
		if(!FileUtil.isExist(cssDir)) {
			FileUtil.makeDirs(cssDir);
		}
		FileUtil.write(cssFile, cssBuf.toString());
		
/*		// 新建自己的资源文件
		String conf = columnDir + "/conf";
		String xmlConfPath = SiteResource.getPublishStaticDir(siteId, false) + "/latestInfo";
		if(!FileUtil.isExist(xmlConfPath)) {
			FileUtil.makeDirs(xmlConfPath);
		}
		FileUtil.copy(conf, xmlConfPath);
		*/
		
		log.debug("page=============="+page);
		// 替换资源路径
		page = page.replaceAll("/template_instance/"+"\\d+"+"/conf", "/latestInfo/conf");
		page = path2domain(siteId, page, "");
/*		String js = "<script type=\"text/javascript\" src=\"/script/client/jquery-1.2.6.js\"></script>"+
					"<script type=\"text/javascript\" src=\"/script/client/jquery.pager.js\"></script>"+
					"<script type=\"text/javascript\" src=\"/script/client/global.js\"></script>"+
					"<script type=\"text/javascript\" src=\"/script/client/analyzeArticleText.js\"></script>"+
					"<script type=\"text/javascript\" src=\"/script/client/titleLinkPage.js\"></script>"+
					"<script type=\"text/javascript\" src=\"/script/client/latestInfo.js\"></script>"+
					"<link rel=\"stylesheet\" type=\"text/css\" href=\"/script/client/Pager.css\"/>";
		String css = "<link rel=\"stylesheet\" type=\"text/css\" href=\"/css/index.css\"/>";*/
		String js = SiteResource.getPublisherJsPath();
		String headRegex = "</head[^>]*>";
		page = page.replaceFirst(headRegex, js +  "</head>");
		
		
		String titleRegex = "<title>(.*)</title[^>]*>";
		String title = column.getName();
		String keywords = "<meta name=\"keywords\" content=\""+title+"政府门户、内容管理、CMS、CPS、内容管理、门户建设方案、门户建设、教育门户、政府门户建设、信息公开、政务公开、信息采集、信息雷达、信息采编、企业门户CMS、集团门户CMS、站群建设、系统集成、网络、门户管理专家、校园门户、OA、视频系统\" /><meta name=\"Generator\" content=\"CPS内容管理\"><meta name=\"Author\" content=\"网络\">";
		String newApp = "";
		if(site.getPublishWay().equals("local")){
			newApp = site.getPublishDir().split("/")[site.getPublishDir().split("/").length-2];
		}else{
			newApp = site.getPublishDir().substring(0, site.getPublishDir().length()-site.getPublishDir().split("/")[site.getPublishDir().split("/").length-1].length());
		}
	//	String newApp = site.getPublishDir().split("/")[site.getPublishDir().split("/").length-2];
//		String newApp = site.getPublishDir().substring(0, site.getPublishDir().length()-site.getPublishDir().split("/")[site.getPublishDir().split("/").length-1].length());
		// 判断是否有网上公告
		StringBuffer sb = new StringBuffer();
		List<OnlineBulletin> list = new ArrayList<OnlineBulletin>();
		String[] params = {"columnId"};
		String[] values = {"'%"+columnId+"%'"};
		list = onlineBulletinDao.findByDefine("findOnlineBulletinByColumnId", params, values);
		if(!CollectionUtil.isEmpty(list)){
			sb.append("<script type=\"text/javascript\">");
			sb.append("window.onload=function(){");
			OnlineBulletin onlineBulletin = null;
			for(int i = 0; i < list.size(); i++){
				onlineBulletin = list.get(i);
				String height = onlineBulletin.getWidowHeight();
				String width = onlineBulletin.getWidowWidth();
				String top = onlineBulletin.getWindowTop();
				String left = onlineBulletin.getWindowLeft();
				
				String showTool = "";
				if(onlineBulletin.isShowTool()){
					showTool = "yes";
				}else{
					showTool = "no";
				}
				String showMenu = "";
				if(onlineBulletin.isShowMenu()){
					showMenu = "yes";
				}else{
					showMenu = "no";
				}
				String showScroll = "";
				if(onlineBulletin.isShowScroll()){
					showScroll = "yes";
				}else{
					showScroll = "no";
				}
				String changeSize = "";
				if(onlineBulletin.isChangeSize()){
					changeSize = "yes";
				}else{
					changeSize = "no";
				}
				String showAddress = "";
				if(onlineBulletin.isShowAddress()){
					showAddress = "yes";
				}else{
					showAddress = "no";
				}
				String showStatus = "";
				if(onlineBulletin.isShowStatus()){
					showStatus = "yes";
				}else{
					showStatus = "no";
				}
				sb.append("window.open(");
				sb.append("\"/"+newApp+"/onlineBulletinOut.do?dealMethod=findOnlineBulletin&bulletinId="+onlineBulletin.getId()+"\"");
				sb.append(",");
				sb.append("\""+column.getName()+"\"");
				sb.append(",\"");
				sb.append("height="+height+"px");
				sb.append(",width="+width+"px");
				sb.append(",top="+top+"px");
				sb.append(",left="+left+"px");
				sb.append(",toolbar="+showTool+"");
				sb.append(",menubar="+showMenu+"");
				sb.append(",scrollbars="+showScroll+"");
				sb.append(",resizable="+changeSize+"");
				sb.append(",location="+showAddress+"");
				sb.append(",status="+showStatus+"");
				sb.append("\");");
			}
			sb.append("}");
			sb.append("</script>");
		}
		page = page.replaceFirst(titleRegex, keywords+"<title>"+title+"</title>"+sb.toString());
		String app3 = "/" + GlobalConfig.appName + "/plugin/onlineSurvey_manager";
		page = page.replaceAll(app3, "/"+newApp+"/plugin/onlineSurvey_manager");
	
		String app = GlobalConfig.appName+"/plugin";
		page = page.replaceAll(app, newApp+"/plugin");
		
		String app1 = "&appName="+GlobalConfig.appName;
		page = page.replaceAll(app1, "&appName="+newApp);
		
		String app2 = GlobalConfig.appName+"/guestOuter";
		page = page.replaceAll(app2, newApp+"/guestOuter");
		
		String app4 = GlobalConfig.appName+"/outOnlineSurvery.do";
		page = page.replaceAll(app4, newApp+"/outOnlineSurvery.do");
		
		// 写入文件
		FileUtil.write(columnFile, page);
		
		this.filterPublishUploadFile(page, siteId, columnDir);
	}
	
	

	/**
	 * 选择多个栏目发布一批文章页
	 * @param columnIds
	 * @param siteId
	 */
	public void publishArticlesByColumnIds(String[] columnIds, String siteId) {
		// 初始化单元类别Map
		//initCategoryMap();
		// 将栏目分批
		
		// 首先发布首页
		//publishHomePage(siteId);
		
		// 按照栏目循环发布
		for (int i = 0; i < columnIds.length; i++) {
			publishArticlesByColumnId(columnIds[i], siteId);
		}
	}
	
	/**
	 * 对单个栏目下发布一批文章页
	 * @param columnId
	 * @param siteId
	 */
	public void publishArticlesByColumnId(String columnId, String siteId) {
		// 查询该栏目下所有审核的文章
		List<Article> articles = articleDao.findByNamedQuery("findAuditedArticlesByColumnId", "columnId", columnId);
		if(articles != null && articles.size() > 0) {
			for (Article article : articles) {
				if(article.isAudited()&& !article.getPublishState().equals(article.PUBLISH_STATE_DRAFT)){
					//将已审核的文章信息加入生成列表					
					ArticleBuildList articleBuildList = new ArticleBuildList();
					BeanUtil.copyProperties(articleBuildList, article);
					
					if (articleBuildListDao.getAndClear(articleBuildList.getId()) == null) {
						articleBuildListDao.saveAndClear(articleBuildList);
					} else {
						articleBuildList.setStatus("true");
						articleBuildListDao.updateAndClear(articleBuildList);
					}				
					// 将文章发布状态改为发布中
					article.setPublishState(Article.PUBLISH_STATE_PUBLISHING);
					articleDao.updateAndClear(article);
				}
			
			}
		}
	}

	/**
	 * 发布单个文章页
	 * @param articleId
	 * @param siteId
	 */
	public void publishArticleByArticleId(String articleId, String siteId) {
		Article article = articleDao.getAndNonClear(articleId);
		String articlePath = GlobalConfig.appRealPath + article.getUrl();
		publishPageAndResource(articlePath, siteId, true);
	}

	/**
	 * 按照选择的文章发布一批文章页
	 * @param articleIds
	 * @param siteId
	 */
	public void publishArticlesByArticleIds(String[] articleIds, String siteId) {
		// 初始化单元类别Map
		initCategoryMap();
		// 将文章分批
		
		// 首先发布首页
		publishHomePage(siteId);
		 
		if(!StringUtil.isEmpty(articleIds.toString())) {
			for(int i = 0; i < articleIds.length; i++) {
				String articleId = articleIds[i];
				publishArticleByArticleId(articleId, siteId);
			}
		}
	}
	
	/**
	 * 重新生成文章的xml
	 * @param siteId
	 * @param xmlPath
	 * @param articleId
	 * @param articleDir
	 * @throws DocumentException
	 */
	private void modifyXml(String siteId, String xmlPath, String articleId, String articleDir) throws DocumentException {
		List list = new ArrayList();
		 SAXReader reader = new SAXReader();
		 File file = new File(xmlPath);
		 if(file.exists()) {
			 Document document = reader.read(file);
			 Element rootElement = document.getRootElement();
			 Iterator itr  = rootElement.elementIterator();
			 while(itr.hasNext()) {
				 Element content = (Element) itr.next();
				 Iterator itr1  = content.elementIterator();
				 while(itr1.hasNext()) {
					 Element pages = (Element) itr1.next();
					 Iterator itr2  = pages.elementIterator();
					 while(itr2.hasNext()) {
						 Element page = (Element) itr2.next();
						 Iterator itr3  = page.elementIterator();
						 while(itr3.hasNext()) {
							 Element pageq = (Element) itr3.next();
							 list.add(pageq.getData());
						 }
					 }
				 }
			 }
			 // 建立一个新的xml文件
			 createXml(list, articleId, articleDir, siteId);
		 }
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
    private String createXml(List list, String articleId, String articleDir, String siteId) {
    	int pageCount = list.size();
    	Document document = DocumentHelper.createDocument();    
		Element rootElement = document.addElement("j2ee.cms"); 
		Element content = rootElement.addElement("content-"+articleId);
		Element pages = content.addElement("pages");
		Element page = pages.addElement("page");
		StringBuffer sb = new StringBuffer();
		Site site = siteDao.getAndClear(siteId);
		String domianName = site.getDomainName();
    	for(int i = 0; i < pageCount; i++) {
    		String name = "page" + (i+1);
    		Element cpage = page.addElement(name);
        	String pageContent = list.get(i).toString();
        	
        	pageContent = path2domain(siteId, pageContent, domianName);
        	cpage.addCDATA(pageContent);
        }
		try {
			OutputFormat of = OutputFormat.createPrettyPrint();
			of.setEncoding("UTF-8");
			String localPath = articleDir + File.separator+"art_"+articleId + ".xml";
			XMLWriter output = new XMLWriter(new FileOutputStream(new File(localPath)));
			output.write(document);
			output.close();
			log.debug("写入成功");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return sb.toString();
		}
	}
	
	/**
	 * 初始化单元类别Map 
	 * key:   id
	 * value: name
	 */
	public void initCategoryMap() {
		// 初始化单元类别Map
		if (CollectionUtil.isEmpty(categoryMap)) {
			categoryMap = new HashMap<String,String>();
			List<TemplateUnitCategory> categories = templateUnitCategoryDao.findAll();
			for (TemplateUnitCategory category : categories) {
				String categoryId = String.valueOf(category.getId());
				categoryMap.put(categoryId, category.getName());
			}
		}
	}
	
	/**
	 * 获取错误提示信息
	 * @param prompt
	 * @return
	 */
	private String getErrorInfo(String prompt) {
		return "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/><span style='color:red;'>" 
				+ prompt + "</span>";
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
	 * @param needReplaceDomain 是否需要替换域名
	 * @return
	 */
	private String analyzerTemplate(String articleId, String columnId, String siteId,
			Map<String, String> publishMap, Map<String, String> namesMap,
			Map<String, String> commonLabel, String tempateStr, boolean needReplaceDomain) {
		StringBuffer temp = new StringBuffer();
		String unitRegex = TemplateUnit.REGEX_PUBLISH;
		Pattern unitPatn = Pattern.compile(unitRegex);
		Matcher m = unitPatn.matcher(tempateStr);
		String unitId = null;
		String unitName = null;
		String replace = null;
		while (m.find()) {
			unitId = m.group(1);
			replace = StringUtil.convert(publishMap.get(unitId));
			if (!StringUtil.isEmpty(replace)) {	// 如果设置了则用内容替代
				// 替换单元内容
				unitName = namesMap.get(unitId);
				replace = analyzeContent(unitName, articleId, unitId, columnId, siteId, commonLabel, needReplaceDomain);
				m.appendReplacement(temp, replace);
			} else {
				m.appendReplacement(temp, "");
			}
		}
		m.appendTail(temp);
		return temp.toString();
	}
	
	/**
	 * 分析内容
	 * @param unitName	单元名称（除静态单元外）
	 * @param unitId
	 * @param columnId
	 * @param siteId
	 * @param needRelaceDomain
	 * @return
	 */
	private String analyzeContent(String unitName, String articleId, String unitId, String columnId, String siteId, Map<String,String> commonLabel, boolean needRelaceDomain) {
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
			
		} else if ("文章正文".equals(unitName)) {
			rs = articleTextAnalyzer.getHtml(unitId, articleId, columnId, siteId, commonLabel);
			
		} else if("标题链接（分页）".equals(unitName)) {
			rs = titleLinkPageAnalyzer.getHtml(unitId, articleId, columnId, siteId, commonLabel);
			
		} else if("期刊（按分类）".equals(unitName)) {
			rs = magazineCategoryAnalyzer.getHtml(unitId, articleId, columnId, siteId, commonLabel);
		
		}else if("网上调查".equals(unitName)){
			rs = onlineSurverySetAnalyzer.getHtml(unitId, articleId, columnId, siteId, commonLabel);
		}
		
		// 替换域名
/*		if (needRelaceDomain) {
			String reg = File.separator + GlobalConfig.appName + File.separator+ com.j2ee.cms.biz.documentmanager.domain.Document.REGX_UPLOAD_PATH;
		//	Pattern pattern = Pattern.compile(reg);
//			Matcher matcher = pattern.matcher(content);
//			String label = "";
//			while(matcher.find()) {
//				label = matcher.group();
//				label = SiteResource.getSiteDir(siteId, false) + "/" + domain2path(siteId, label, site.getDomainName());
//				fileList.add(label);
//			}
		}*/
		
		return rs;
	}
	
	/**
	 * 预览页面
	 * @param templateInstanceId 模版实例ID
	 * @param articleId 文章ID
	 * @param columnId 栏目ID
	 * @param siteId 网站ID
	 * @return 预览页面的链接地址
	 */
	public String previewPage(String templateInstanceId, String articleId, String columnId, String siteId) {
		// 初始化单元类别Map 
		initCategoryMap();
		
		StringBuffer scriptBuf = new StringBuffer();
		StringBuffer cssBuf = new StringBuffer();
		Site site = null;
		TemplateInstance templateInstance = templateInstanceDao.getAndClear(templateInstanceId);
		
		if (templateInstance ==  null) {
			String prompt = getErrorInfo("模版未设");
			String previewFile = SiteResource.getPreviewDir(siteId, false) + File.separator + "prompt.html";
			FileUtil.write(previewFile, prompt);
			previewFile = previewFile.substring(GlobalConfig.appRealPath.length());
			return previewFile;
		}
		
		String instanceId = templateInstance.getId();
		List<TemplateUnit> templateUnits = templateUnitDao.findByNamedQuery("findTemplateUnitByInstanceId", "id", instanceId);
		// 发布的内容map 
		Map<String,String> publishMap = new HashMap<String,String>();
		Map<String,String> namesMap = new HashMap<String,String>();
		for (TemplateUnit templateUnit : templateUnits) {
			String categoryId = null;
			if (templateUnit != null) {
				String unitId = String.valueOf(templateUnit.getId());
				scriptBuf.append(StringUtil.convert(templateUnit.getScript()));
				cssBuf.append(StringUtil.convert(templateUnit.getCss()));
				if (templateUnit.getTemplateUnitCategory() != null) {
					categoryId = templateUnit.getTemplateUnitCategory().getId();
					namesMap.put(unitId, categoryMap.get(String.valueOf(categoryId)));
				}
				publishMap.put(unitId, templateUnit.getHtml());
			}
		}
		
		// 2.组织替换内容
		
		// 公共标签
		Map<String,String> commonLabel = new HashMap<String,String>();

		// 3.读取模板文件内容
		String tempateStr = FileUtil.read(GlobalConfig.appRealPath + templateInstance.getLocalPath());
		
		// 4.替换单元模板内容
		String page = analyzerTemplate(articleId, columnId, siteId, publishMap,
				namesMap, commonLabel, tempateStr, false);
		
		// 预览页路径
		String previewDir = SiteResource.getPreviewDir(siteId, false);
		long systemTime = System.currentTimeMillis();
		FileUtil.makeDirs(previewDir+File.separator+systemTime);
		String previewFile = previewDir + File.separator + systemTime + File.separator +"preview.html";
		// 拷贝模板资源
		String templateDir = FileUtil.getFileDir(GlobalConfig.appRealPath + templateInstance.getLocalPath());
		FileUtil.copy(templateDir, previewDir+File.separator+systemTime, false);
		// 删除无用的资源
		FileUtil.delete(previewDir + File.separator+ systemTime +File.separator+"conf");
		FileUtil.delete(previewDir + File.separator+ systemTime +File.separator+"template_set.jsp");
		FileUtil.delete(previewDir + File.separator+ systemTime + File.separator + FileUtil.getFileName(templateInstance.getLocalPath()));
		
		// 5.建立css目录
		String cssDir = previewDir + File.separator+ systemTime + File.separator+"css";
		String cssFile = previewDir + File.separator+ systemTime + File.separator+"css"+File.separator + "prew.css";
		if(!FileUtil.isExist(cssDir)) {
			FileUtil.makeDirs(cssDir);
		}
		FileUtil.write(cssFile, cssBuf.toString());
		
		// 替换资源路径
		String js = SiteResource.getPreviewFrontScript(true);
					/*"<script type=\"text/javascript\" src=\"../script/latestInfo/jquery.js\"></script>\n"+
					"<script type=\"text/javascript\" src=\"../script/latestInfo/jquery.pager.js\"></script>\n" +
					"<script type=\"text/javascript\" src=\"../script/analyzeArticleText.js\"></script>\n"+
					"<script type=\"text/javascript\" src=\"../script/latestInfo/latestInfo.js\"></script>\n" +
					"<script type=\"text/javascript\" src=\"../script/latestInfo/titleLinkPage.js\"></script>\n"+
					"<script type=\"text/javascript\" src=\"../script/global.js\"></script>\n";*/
					
		String css = "<link rel=\"stylesheet\" type=\"text/css\" href=\"css/prew.css\"/>\n";
		String headRegex = "</head[^>]*>";
		page = page.replaceFirst(headRegex, css + js + "</head>");
		String titleRegex = "<title>(.*)</title[^>]*>";
		Article article = articleDao.getAndClear(articleId); 
		if(article != null){
			String title = article.getTitle();
			String description = "<meta name=\"description\" content=\""+article.getBrief()+"\"/>";
			String keywords = "<meta name=\"keywords\" content="+article.getKeyword()+"\"政府门户、内容管理、CPS、CMS、CPS、内容管理、门户建设方案、门户建设、教育门户、政府门户建设、信息公开、政务公开、信息采集、信息雷达、信息采编、企业门户CMS、集团门户CMS、站群建设、系统集成、网络、门户管理专家、校园门户、OA、视频系统\" /><meta name=\"Generator\" content=\"CPS内容管理\"><meta name=\"Author\" content=\"网络\">";
			page = page.replaceFirst(titleRegex, description+keywords+"<title>"+title+"</title>");
		}
				
		FileUtil.write(previewFile, page);
		previewFile = previewFile.substring(GlobalConfig.appRealPath.length());
		// 拷贝图片和falseh、附件等文件
		String uploadPath = SiteResource.getSiteDir(siteId, false) + File.separator+"upload";
		if(FileUtil.isExist(uploadPath)) {
			FileUtil.copy(uploadPath, SiteResource.getPreviewDir(siteId, false) + File.separator+ systemTime);
		}
		
		return previewFile;
	}
	
	/**
	 * 按照网站id查询栏目
	 * @param siteId
	 * @return
	 */
	public String findColumnBySiteId(String siteId) {
		List list = columnDao.findByNamedQuery("findColumnByCurrentSite", "siteId", siteId);
		String columnIds = "";
		if(list != null && list.size() > 0) {
			Column column = null;
			for(int i = 0; i < list.size(); i++) {
				column = (Column) list.get(i);
				String columnId = column.getId();
				columnIds += "," + columnId;
			}
			columnIds = columnIds.replaceFirst(",", "");
		}
		return columnIds;
	}
	
	/***
	 * 过滤生成文章和栏目时的图片、附件等文件
	 * @param page
	 * @param siteId
	 * @param dir
	 */
	private void filterPublishUploadFile(String page, String siteId, String dir) {
		Pattern pattern = Pattern.compile(com.j2ee.cms.biz.documentmanager.domain.Document.REGX_UPLOAD_PATH);
		Matcher matcher = pattern.matcher(page);
		List list = new ArrayList();
		String label = "";
		while(matcher.find()) {
			label = matcher.group();
			list.add(label);
		}
		String newFilePath = SiteResource.getBuildStaticDir(siteId, false) + File.separator+"upload";
		if(!FileUtil.isExist(newFilePath)) {
			FileUtil.makeDirs(newFilePath);
		}
		for(int i = 0; i < list.size(); i++) {
			String file = SiteResource.getSiteDir(siteId, false) + File.separator + list.get(i);
			String[] str = list.get(i).toString().split("/");
			String documentPath = newFilePath + File.separator + str[1];
			if(!FileUtil.isExist(documentPath)) {
				FileUtil.makeDirs(documentPath);
			}
			String newPath = SiteResource.getPublishStaticDir(siteId, false) + File.separator + list.get(i);
			if(!FileUtil.isExist(newPath)) {
				if(FileUtil.isExist(file)) {
					FileUtil.copy(file, documentPath);
				} else {
					log.debug(str[2]+"路径不存在");
				}
			}
		}
	//	System.out.println("uploadFile Succeess............................");
	}
	
	/***
	 * 过滤生成文章和栏目时的图片、附件等文件
	 * @param page
	 * @param siteId
	 * @param dir
	 */
	private void filterBuildUploadFile(String page, String siteId, String dir) {
		Pattern pattern = Pattern.compile(com.j2ee.cms.biz.documentmanager.domain.Document.REGX_UPLOAD_PATH);
		Matcher matcher = pattern.matcher(page);
		List list = new ArrayList();
		String label = "";
		while(matcher.find()) {
			label = matcher.group();
			list.add(label);
		}
		String newFilePath = SiteResource.getBuildStaticDir(siteId, false) + File.separator+"upload";
		if(!FileUtil.isExist(newFilePath)) {
			FileUtil.makeDirs(newFilePath);
		}
		for(int i = 0; i < list.size(); i++) {
			String file = SiteResource.getSiteDir(siteId, false) + File.separator + list.get(i);
			String[] str = list.get(i).toString().split("/");
			String documentPath = newFilePath + File.separator + str[1];
			if(!FileUtil.isExist(documentPath)) {
				FileUtil.makeDirs(documentPath);
			}
			String newPath = SiteResource.getBuildStaticDir(siteId, false) + File.separator + list.get(i);
			if(!FileUtil.isExist(newPath)) {
				if(FileUtil.isExist(file)) {
					FileUtil.copy(file, documentPath);
				} else {
					log.debug(str[2]+"路径不存在");
				}
			}
		}
	//	System.out.println("uploadFile Succeess............................");
	}
	
	/**
	 * 发布页面
	 * @param columnIds 栏目ids
	 * @param publishType 发布类型 (column:栏目页,article:文章页)
	 * @param siteId
	 */
	public void publish(String[] columnIds, String publishType, String siteId) {

	}
	
	public void buildHomePage(String siteId) {
		this.initCategoryMap();
		
		StringBuffer scriptBuf = new StringBuffer();
		StringBuffer cssBuf = new StringBuffer();
		// 1、查询模板实例
		Site site = siteDao.getAndNonClear(siteId);
		TemplateInstance templateInstance = site.getHomeTemplate();
		if (templateInstance ==  null) {
			return;
		}
		
		String instanceId = templateInstance.getId();
		List<TemplateUnit> templateUnits = templateUnitDao.findByNamedQuery("findTemplateUnitByInstanceId", "id", instanceId);
		// 发布的内容map 
		Map<String,String> publishMap = new HashMap<String,String>();
		Map<String,String> namesMap = new HashMap<String,String>();
		for (TemplateUnit templateUnit : templateUnits) {
			String categoryId = null;
			String unitId = String.valueOf(templateUnit.getId());
			if (templateUnit != null) {
				scriptBuf.append(StringUtil.convert(templateUnit.getScript()));
				cssBuf.append(StringUtil.convert(templateUnit.getCss()));
				if (templateUnit.getTemplateUnitCategory() != null) {
					categoryId = templateUnit.getTemplateUnitCategory().getId();
					namesMap.put(unitId, categoryMap.get(String.valueOf(categoryId)));
				}
				publishMap.put(unitId, templateUnit.getHtml());
			}
		}
		// 2.组织替换内容
		
		// 公共标签
		Map<String,String> commonLabel = new HashMap<String,String>();

		// 3.读取模板文件内容
		String buildTemplateDir = SiteResource.getBuildStaticTemplateDir(siteId, false) + File.separator + templateInstance.getId();
		String fileName = FileUtil.getFileName(templateInstance.getLocalPath());
		
		String buildTemplateFile = buildTemplateDir + File.separator + fileName;
//		if (new File(buildTemplateDir).exists()) {
		if (!new File(buildTemplateDir).exists()) {	
			// 拷贝模板资源
			FileUtil.makeDirs(buildTemplateDir);
		}
			String templateFile = GlobalConfig.appRealPath + templateInstance.getLocalPath();
			String content = FileUtil.read(templateFile);
			String templateDir = FileUtil.getFileDir(templateFile);
			//将模版实例目录拷贝到build/static目录下
			FileUtil.copy(templateDir, buildTemplateDir, false);
			
			//替换模板文件中的资源路径
	/*		String rsRegx = "<[^<]+=[\"']([^'\"]+[.][^'\"/-_]+)[\"'][>]";
			Pattern p = Pattern.compile(rsRegx);
			Matcher m = p.matcher(content);
			StringBuffer sb = new StringBuffer();
			while (m.find()) {
				String s = "/template_instance/"+ templateInstance.getId() + "/" + m.group(1);
				m.appendReplacement(sb, m.group().replaceAll("[^'\"]+[.][^'\"/-_]+", s));
			}
			m.appendTail(sb);
			FileUtil.write(buildTemplateFile, sb.toString());
			*/
			FileUtil.write(buildTemplateFile,content.replace("images",  "/template_instance/"+ templateInstance.getId() + "/images" ));
			

			String latestInfConf = SiteResource.getBuildStaticDir(siteId, false) + File.separator+"latestInfo";
			if(!FileUtil.isExist(latestInfConf)) {
				FileUtil.makeDirs(latestInfConf);
			}
			//首页相关图片，css
			String homeImagesDir = SiteResource.getBuildStaticDir(siteId, false) + File.separator+"images";
			if(!FileUtil.isExist(homeImagesDir)) {
				FileUtil.makeDirs(homeImagesDir);
			}
			//拷贝XML配置文件
			FileUtil.copy(buildTemplateDir + File.separator+"conf",  latestInfConf);
			//拷贝首页的images到网站根目录下
			FileUtil.copy(buildTemplateDir + File.separator+"images",  SiteResource.getBuildStaticDir(siteId, false) );
			// 删除无用的资源
			FileUtil.delete(buildTemplateDir + File.separator+"conf");
			FileUtil.delete(buildTemplateDir + File.separator+"template_set.jsp");
//		}
		
		//读出替换后的模板
		String templateStr = FileUtil.read(buildTemplateFile);
		
		
		// 4.替换单元模板内容
		String page = analyzerTemplate(null, null, siteId, publishMap,
				namesMap, commonLabel, templateStr, true);
		
		// 建网站首页目录
		String siteDir = SiteResource.getBuildStaticDir(siteId, false);
		if(!FileUtil.isExist(siteDir)) {
			FileUtil.makeDirs(siteDir);
		}
		// 5.建立css目录
		String cssDir = siteDir + File.separator+"css";
		String cssFile = siteDir + File.separator+"css"+File.separator + "index.css";
		if(!FileUtil.isExist(cssDir)) {
			FileUtil.makeDirs(cssDir);
		}
		FileUtil.write(cssFile, cssBuf.toString());
		
		// 首页路径
		String siteFile = GlobalConfig.appRealPath + site.getUrl();
		// 拷贝模板资源
//		String templateDir = FileUtil.getFileDir(GlobalConfig.appRealPath + templateInstance.getLocalPath());
//		FileUtil.copy(templateDir, siteDir, false);
/*		String latestInfo = siteDir + "/latestInfo";
		//如果网站目录下不存在latestInfo目录，则创建一个
		if(!FileUtil.isExist(latestInfo)) {
			FileUtil.makeDirs(latestInfo);
		}
		//XML信息内容路径
		String xmlConfPath = SiteResource.getPublishStaticDir(siteId, false) + "/latestInfo";
		if(!FileUtil.isExist(xmlConfPath)) {
			FileUtil.makeDirs(xmlConfPath);
		}
		//将生成的文章信息的XML拷贝到
		FileUtil.copy(latestInfo, xmlConfPath);*/
		// 替换资源路径
	//	page = page.replaceAll("/"+GlobalConfig.appName +"/release/site"+siteId+"/template_instance"+"/\\d+"+"/conf", "/latestInfo"+"/conf");
		page = page.replaceAll("/template_instance/"+"\\d+"+"/conf", "/latestInfo/conf");
		page = path2domain(siteId, page, "");
/*		String js = "<script type=\"text/javascript\" src=\"/script/client/jquery-1.2.6.js\"></script>"+
					"<script type=\"text/javascript\" src=\"/script/client/jquery.pager.js\"></script>"+
					"<script type=\"text/javascript\" src=\"/script/client/global.js\"></script>"+
					"<script type=\"text/javascript\" src=\"/script/client/analyzeArticleText.js\"></script>"+
					"<script type=\"text/javascript\" src=\"/script/client/titleLinkPage.js\"></script>"+
					"<script type=\"text/javascript\" src=\"/script/client/latestInfo.js\"></script>"+
					"<link rel=\"stylesheet\" type=\"text/css\" href=\"/script/client/Pager.css\"/>";
		String css = "<link rel=\"stylesheet\" type=\"text/css\" href=\"/css/index.css\"/>";*/
		String js = SiteResource.getPublisherJsPath();
		String headRegex = "</head[^>]*>";

		page = page.replaceFirst(headRegex, js +  "</head>");
		String titleRegex = "<title>(.*)</title[^>]*>";
		String title = siteDao.getAndClear(siteId).getHomePageTitle();
		String keywords = "<meta name=\"keywords\" content=\""+site.getHomePageTitle()+",政府门户、内容管理、CPS、CMS、CPS、内容管理、门户建设方案、门户建设、教育门户、政府门户建设、信息公开、政务公开、信息采集、信息雷达、信息采编、企业门户CMS、集团门户CMS、站群建设、系统集成、网络、门户管理专家、校园门户、OA、视频系统\" /><meta name=\"Generator\" content=\"CPS内容管理\"><meta name=\"Author\" content=\"网络\">";
		String description = "<META NAME=\"Description\" CONTENT=\""+site.getDescription()+"\">";
		//网站发布的根目录
		String newApp = "";
		if(site.getPublishWay().equals("local")){
			newApp = site.getPublishDir().split("/")[site.getPublishDir().split("/").length-2];
		}else{
			newApp = site.getPublishDir().substring(0, site.getPublishDir().length()-site.getPublishDir().split("/")[site.getPublishDir().split("/").length-1].length());
		}
		//	String newApp = site.getPublishDir().split("/")[site.getPublishDir().split("/").length-2];
		// 判断是否有网上公告
		StringBuffer sb = new StringBuffer();
		List<OnlineBulletin> list = new ArrayList<OnlineBulletin>();
		String[] params = {"columnId"};
		String[] values = {"'%001%'"};
		list = onlineBulletinDao.findByDefine("findOnlineBulletinByColumnId", params, values);
		if(!CollectionUtil.isEmpty(list)){
			sb.append("<script type=\"text/javascript\">");
			sb.append("window.onload=function(){");
			OnlineBulletin onlineBulletin = null;
			for(int i = 0; i < list.size(); i++){
				onlineBulletin = list.get(i);
				String height = onlineBulletin.getWidowHeight();
				String width = onlineBulletin.getWidowWidth();
				String top = onlineBulletin.getWindowTop();
				String left = onlineBulletin.getWindowLeft();
				
				String showTool = "";
				if(onlineBulletin.isShowTool()){
					showTool = "yes";
				}else{
					showTool = "no";
				}
				String showMenu = "";
				if(onlineBulletin.isShowMenu()){
					showMenu = "yes";
				}else{
					showMenu = "no";
				}
				String showScroll = "";
				if(onlineBulletin.isShowScroll()){
					showScroll = "yes";
				}else{
					showScroll = "no";
				}
				String changeSize = "";
				if(onlineBulletin.isChangeSize()){
					changeSize = "yes";
				}else{
					changeSize = "no";
				}
				String showAddress = "";
				if(onlineBulletin.isShowAddress()){
					showAddress = "yes";
				}else{
					showAddress = "no";
				}
				String showStatus = "";
				if(onlineBulletin.isShowStatus()){
					showStatus = "yes";
				}else{
					showStatus = "no";
				}
				sb.append("window.open(");
				sb.append("\"/"+newApp+"/onlineBulletinOut.do?dealMethod=findOnlineBulletin&bulletinId="+onlineBulletin.getId()+"\"");
				sb.append(",");
				sb.append("\""+site.getName()+"\"");
				sb.append(",\"");
				sb.append("height="+height+"px");
				sb.append(",width="+width+"px");
				sb.append(",top="+top+"px");
				sb.append(",left="+left+"px");
				sb.append(",toolbar="+showTool+"");
				sb.append(",menubar="+showMenu+"");
				sb.append(",scrollbars="+showScroll+"");
				sb.append(",resizable="+changeSize+"");
				sb.append(",location="+showAddress+"");
				sb.append(",status="+showStatus+"");
				sb.append("\");");
			}
			sb.append("}");
			sb.append("</script>");
		}
		page = page.replaceFirst(titleRegex, keywords+description+"<title>"+title+"</title>"+sb.toString());
		String app3 = "/" + GlobalConfig.appName + "/plugin/onlineSurvey_manager";
		page = page.replaceAll(app3, "/"+newApp+"/plugin/onlineSurvey_manager");
		String app2 = GlobalConfig.appName+"/guestOuter";
		page = page.replaceAll(app2, newApp+"/guestOuter");	
		String app1 = "&appName="+GlobalConfig.appName;
		page = page.replaceAll(app1, "&appName="+newApp);
		
		String app4 = GlobalConfig.appName + "/plugin";
		page = page.replaceAll(app4, newApp+"/plugin");
		
		String app5 = GlobalConfig.appName+"/outOnlineSurvery.do";
		page = page.replaceAll(app5, newApp+"/outOnlineSurvery.do");
		// 写入文件
		FileUtil.write(siteFile, page);
		
//		FileUtil.delete(siteDir + "/conf");
//		FileUtil.delete(siteDir + "/template_set.jsp");
		this.filterBuildUploadFile(page, siteId, siteDir);
	}
	
	public void buildArticleByArticleId(String articleId, String siteId) {
		
		this.initCategoryMap();
		
		StringBuffer scriptBuf = new StringBuffer();
		StringBuffer cssBuf = new StringBuffer();
		Article article = articleDao.getAndNonClear(articleId);
		String columnId = article.getColumn().getId();
		// 1、查询模板实例
		Column column = columnDao.getAndNonClear(columnId);
		Site site = siteDao.getAndNonClear(siteId);
		TemplateInstance templateInstance = column.getArticleTemplate();
		if (templateInstance ==  null) {
			// 如果文章的模版为空，则调用网站默认的模版
			templateInstance = site.getArticleTemplate();
			if(templateInstance == null) {
				throw new RuntimeException("build article error: article has no template.");
			}
		}
		String instanceId = templateInstance.getId();
		List<TemplateUnit> templateUnits = templateUnitDao.findByNamedQuery("findTemplateUnitByInstanceId", "id", instanceId);
		// 发布的内容map 
		Map<String,String> publishMap = new HashMap<String,String>();
		Map<String,String> namesMap = new HashMap<String,String>();
		for (TemplateUnit templateUnit : templateUnits) {
			String categoryId = null;
			String unitId = String.valueOf(templateUnit.getId());
			if (templateUnit != null) {
				scriptBuf.append(StringUtil.convert(templateUnit.getScript()));
				cssBuf.append(StringUtil.convert(templateUnit.getCss()));
				if (templateUnit.getTemplateUnitCategory() != null) {
					categoryId = templateUnit.getTemplateUnitCategory().getId();
					namesMap.put(unitId, categoryMap.get(String.valueOf(categoryId)));
				}
				publishMap.put(unitId, templateUnit.getHtml());
			}
		}
		
		// 2.组织替换内容
		
		// 公共标签
		Map<String,String> commonLabel = new HashMap<String,String>();

		// 3.读取模板文件内容
		//String tempateStr = FileUtil.read(GlobalConfig.appRealPath + templateInstance.getLocalPath());
		String buildTemplateDir = SiteResource.getBuildStaticTemplateDir(siteId, false) + File.separator+ templateInstance.getId();
		String fileName = FileUtil.getFileName(templateInstance.getLocalPath());
		
		String buildTemplateFile = buildTemplateDir + File.separator + fileName;
		if (new File(buildTemplateDir).exists()) {
			
			// 拷贝模板资源
			FileUtil.makeDirs(buildTemplateDir);
		}
			String templateFile = GlobalConfig.appRealPath + templateInstance.getLocalPath();
			String content = FileUtil.read(templateFile);
			String templateDir = FileUtil.getFileDir(templateFile);
			FileUtil.copy(templateDir, buildTemplateDir, false);
			
			//替换模板文件中的资源路径
			/*String rsRegx = "<[^<]+=[\"']([^'\"]+[.][^'\"/-_]+)[\"'][>]";
			Pattern p = Pattern.compile(rsRegx);
			Matcher m = p.matcher(content);
			StringBuffer sb = new StringBuffer();
			while (m.find()) {
				String s = "/template_instance/"+ templateInstance.getId() + "/" + m.group(1);
				m.appendReplacement(sb, m.group().replaceAll("[^'\"]+[.][^'\"/-_]+", s));
			}
			m.appendTail(sb);
			
			FileUtil.write(buildTemplateFile, sb.toString());*/
			FileUtil.write(buildTemplateFile,content.replace("images",  "/template_instance/"+templateInstance.getId() + "/images" ));
			String latestInfConf = SiteResource.getBuildStaticDir(siteId, false) + File.separator+"latestInfo";
			if(!FileUtil.isExist(latestInfConf)) {
				FileUtil.makeDirs(latestInfConf);
			}
			//拷贝XML配置文件
			FileUtil.copy(buildTemplateDir + File.separator+"conf",  latestInfConf);
			// 删除无用的资源
			FileUtil.delete(buildTemplateDir + File.separator+"conf");
			FileUtil.delete(buildTemplateDir + File.separator+"template_set.jsp");
			//FileUtil.delete(buildTemplateDir + "/" + fileName);
//		}
		
		//读出替换后的模板
		String templateStr = FileUtil.read(buildTemplateFile);
		
		// 4.替换单元模板内容
		String page = analyzerTemplate(articleId, columnId, siteId, publishMap,
				namesMap, commonLabel, templateStr, true);
		// 建栏目目录
		String columnDir = SiteResource.getBuildStaticDir(siteId, false) + File.separator + "col_" + columnId;
	//	String articleDir = columnDir + "/" + DateUtil.getCurrDate().replaceAll("-", "/");
		String articleDir = FileUtil.getFileDir(GlobalConfig.appRealPath + article.getUrl());
		 
		if(!FileUtil.isExist(articleDir)) {
			FileUtil.makeDirs(articleDir);
		}
		
		// 5.建立css目录
		String cssDir = articleDir + File.separator+"css";
		String cssFile = articleDir + File.separator+"css"+File.separator + "index.css";
		if(!FileUtil.isExist(cssDir)) {
			FileUtil.makeDirs(cssDir);
		}
		FileUtil.write(cssFile, cssBuf.toString());
		
		// 替换资源路径
		page = path2domain(siteId, page, "");
/*		String js = "<script type=\"text/javascript\" src=\"/script/client/jquery-1.2.6.js\"></script>"+
			"<script type=\"text/javascript\" src=\"/script/client/jquery.pager.js\"></script>"+
			"<script type=\"text/javascript\" src=\"/script/client/global.js\"></script>"+
			"<script type=\"text/javascript\" src=\"/script/client/analyzeArticleText.js\"></script>"+
			"<script type=\"text/javascript\" src=\"/script/client/titleLinkPage.js\"></script>"+
			"<script type=\"text/javascript\" src=\"/script/client/latestInfo.js\"></script>"+
			"<link rel=\"stylesheet\" type=\"text/css\" href=\"/script/client/Pager.css\"/>";
		String css = "<link rel=\"stylesheet\" type=\"text/css\" href=\"/css/index.css\"/>";*/
		String js = SiteResource.getPublisherJsPath();
		String headRegex = "</head[^>]*>";
		page = page.replaceFirst(headRegex, js  + "</head>");
		String titleRegex = "<title>(.*)</title[^>]*>";
		String title = article.getTitle();
	
		String keywords = "<meta name=\"keywords\" content=\""+article.getKeyword()+"政府门户、内容管理、CMS、CPS、内容管理、门户建设方案、门户建设、教育门户、政府门户建设、信息公开、政务公开、信息采集、信息雷达、信息采编、企业门户CMS、集团门户CMS、站群建设、系统集成、网络、门户管理专家、校园门户、OA、视频系统\" /><meta name=\"Generator\" content=\"CPS内容管理\"><meta name=\"Author\" content=\"网络\">";
		String description = "<meta name=\"description\" content=\""+article.getBrief()+"\"/>";
		page = page.replaceFirst(titleRegex, description+keywords+"<title>"+title+"</title>");
		String newApp = "";
		if(site.getPublishWay().equals("local")){
			newApp = site.getPublishDir().split("/")[site.getPublishDir().split("/").length-2];
		}else{
			newApp = site.getPublishDir().substring(0, site.getPublishDir().length()-site.getPublishDir().split("/")[site.getPublishDir().split("/").length-1].length());
		}
//		String newApp = site.getPublishDir().substring(0, site.getPublishDir().length()-site.getPublishDir().split("/")[site.getPublishDir().split("/").length-1].length());
	//	String newApp = site.getPublishDir().split("/")[site.getPublishDir().split("/").length-2];
		
		String app = "/"+GlobalConfig.appName+"/commitComment";
		page = page.replaceAll(app, "/"+newApp+"/commitComment");
		
		String app2 = GlobalConfig.appName+"/guestOuter";
		page = page.replaceAll(app2, newApp+"/guestOuter");
		
		String app3 = "/" + GlobalConfig.appName + "/plugin/onlineSurvey_manager";
		page = page.replaceAll(app3, "/"+newApp+"/plugin/onlineSurvey_manager");
		
		String app1 = "&appName="+GlobalConfig.appName;
		page = page.replaceAll(app1, "&appName="+newApp);
		
		String app4 = GlobalConfig.appName + "/plugin";
		page = page.replaceAll(app4, newApp+"/plugin");
		
		String app5 = GlobalConfig.appName+"/outOnlineSurvery.do";
		page = page.replaceAll(app5, newApp+"/outOnlineSurvery.do");
		// 文章页页路径
		String articleFile = GlobalConfig.appRealPath + article.getUrl();
		// 拷贝文章页的xml文件
		String xmlPath = SiteResource.getSiteDir(siteId, false) +File.separator+"art_"+ articleId +".xml";
		if(FileUtil.isExist(xmlPath)) {
			// 重新拷贝xml文件
			try {
				modifyXml(siteId, xmlPath, articleId, articleDir);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
		// 替换栏目ID
		FileUtil.write(articleFile, page);
		this.filterBuildUploadFile(page, siteId, articleDir);
	}
	
	/**
	 * 发布页面及页面中的资源（资源指文档管理中的资源）
	 * @param url 页面url（物理路径）
	 * @param siteId 网站ID
	 * @param isHomePage 是否首页
	 */
	private void publishPageAndResource(String url, String siteId, boolean isHomePage) {
		Site site = Site.siteMap.get(siteId);
		if (site == null) {
			site = new Site();
			BeanUtil.copyProperties(site, siteDao.getAndClear(siteId));
			Site.siteMap.put(siteId, site);
		}
		site = Site.siteMap.get(siteId);
		String publishWay = site.getPublishWay();
		// 文件列表（存储所有需要发布的文件）
		List<String> fileList = new ArrayList<String>();
		fileList.add(url);
		String content = FileUtil.read(url);
		
		// 匹配页面中的所有文档
		String reg = com.j2ee.cms.biz.documentmanager.domain.Document.REGX_UPLOAD_PATH;
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(content);
		String label = "";
		while(matcher.find()) {
			label = matcher.group();
			label = SiteResource.getSiteDir(siteId, false) + File.separator + label;
			fileList.add(label);
		}
		
		/*******/
		
		/*****/
		// 循环拷贝文件
		if (Site.PUBLISH_LOCAL.equals(publishWay)) {
			//本地发布
			if (isHomePage) {
				//网站资源
		
				String latestInfoPath = GlobalConfig.appRealPath + File.separator+"script"+File.separator+"client";
				String articleCommitstylePath= SiteResource.getStyleDir(siteId, false);
				String guestBookPath = SiteResource.getGuestBookDir(siteId, false);
				String latestInfo = SiteResource.getBuildStaticDir(siteId, false) + File.separator+"latestInfo";
				String latestInfoConf = SiteResource.getBuildStaticDir(siteId, false) + File.separator+"latestInfo"+File.separator+"conf";
				String cssDir = SiteResource.getBuildStaticDir(siteId, false) + File.separator+"css";
				String homeImagesDir = SiteResource.getBuildStaticDir(siteId, false) + File.separator+"images";
			
				if (!new File(site.getPublishDir()+File.separator+"script").exists()) {
					new File(site.getPublishDir()+File.separator+"script").mkdirs();
				}
				if (!new File(site.getPublishDir()+File.separator+"script"+File.separator+"client").exists()) {
					new File(site.getPublishDir()+File.separator+"script"+File.separator+"client").mkdirs();
				}
				if(!new File(site.getPublishDir()+File.separator+"articleComments").exists()){
					new File(site.getPublishDir()+File.separator+"articleComments").mkdirs();
				}
				if(!new File(site.getPublishDir()+File.separator+"guestbook_manager").exists()){
					new File(site.getPublishDir()+File.separator+"guestbook_manager").mkdirs();
				}
				if(!new File(site.getPublishDir()+File.separator+"latestInfo").exists()){
					new File(site.getPublishDir()+File.separator+"latestInfo").mkdirs();
				}
				if(!new File(site.getPublishDir()+File.separator+"latestInfo"+File.separator+"conf").exists()){
					new File(site.getPublishDir()+File.separator+"latestInfo"+File.separator+"conf").mkdirs();
				}
				if(!new File(site.getPublishDir()+File.separator+"css").exists()){
					new File(site.getPublishDir()+File.separator+"css").mkdirs();
				}
				if(!new File(site.getPublishDir()+File.separator+"images").exists()){
					new File(site.getPublishDir()+File.separator+"images").mkdirs();
				}
				// 发布脚本
			
				FileUtil.copy(latestInfoPath, site.getPublishDir()+File.separator+"script"+File.separator+"client", false);
				//发布文章评论样式
				FileUtil.copy(articleCommitstylePath, site.getPublishDir()+File.separator+"articleComments",false);
				//发布留言本样式
				FileUtil.copy(guestBookPath, site.getPublishDir()+File.separator+"guestbook_manager",false);
				//发布文章内容XML及配置文件
				FileUtil.copy(latestInfo, site.getPublishDir()+File.separator+"latestInfo",false);
				//发布文章内容（分页及最新信息，文章正文）配置文件
				FileUtil.copy(latestInfoConf, site.getPublishDir()+File.separator+"latestInfo"+File.separator+"conf",false);
				//拷贝生成的首页css
				FileUtil.copy(cssDir, site.getPublishDir()+File.separator+"css",false);
				//拷贝首页相关的图片及CSS（模版实例里的 ）
				FileUtil.copy(homeImagesDir, site.getPublishDir()+File.separator+"images",false);
			}
			
			for (String file : fileList) {
				log.debug("file======================="+file);
				// 生成的文件路径
				String buildFile = file;
				// 发布的文件路径
				String publishFile = null;
				String staticDir = SiteResource.getBuildStaticDir(siteId, false);
				String publishDir = site.getPublishDir().replaceAll("\\\\", "/");
				String siteDir = SiteResource.getSiteDir(siteId, false);
				log.debug("staticDir==================================="+staticDir);
				log.debug("publishDir==================================="+publishDir);
				log.debug("siteDir==================================="+siteDir);
				if (file.equals(url)) {
					//log.debug("11111111111111111111111111111111111");
					publishFile = file.replaceAll(staticDir, publishDir);
				} else {
					//log.debug("2222222222222222222222222222222222222");
					publishFile = file.replaceAll(siteDir, publishDir);
				}
				
				
				String fileDir = publishFile.replaceAll("[/\\\\][^/\\\\]+$", "");
				String[] strFileDir = fileDir.split("/");
				String newStrFileDir = "";
				for(int j = 0 ;j< strFileDir.length;j++){
					newStrFileDir= newStrFileDir + strFileDir[j]+File.separator;
				}
				log.debug("buildFile============================"+buildFile);
				log.debug("newStrFileDir========================="+newStrFileDir);
				if (!new File(newStrFileDir).exists()) {
					log.debug("mak dir");
					new File(newStrFileDir).mkdirs();
				}

		
				FileUtil.copy(buildFile, newStrFileDir, false);
			}
		} else if (Site.PUBLISH_REMOTE.equals(publishWay)) {
			//socket远程发布
			// 发布资源
			fileList.remove(url);
			new Sender(site.getRemoteIP(), site.getRemotePort(), fileList, SiteResource.getSiteDir(siteId, false)).start();
		
			// 发布文章页
			fileList = new ArrayList<String>();
			fileList.add(url);
			new Sender(site.getRemoteIP(), site.getRemotePort(), fileList, SiteResource.getBuildStaticDir(siteId, false)).start();
			//远程发布所有插件下的文件
			String path= GlobalConfig.appRealPath+File.separator+"release"+File.separator+"site"+siteId+File.separator+"plugin";
			List<String> l = FileUtil.getFiles(path, false);
			List<String> kl = new ArrayList<String>();
			//替换路径中的斜杠
			for (String s : l) {
				s = s.replaceAll("\\\\", File.separator);
				kl.add(s);
			}
			new Sender(site.getRemoteIP(), site.getRemotePort(), kl, path).start();
			
			
			// 发布脚本
			if (isHomePage) {
				String articleTextPath = GlobalConfig.appRealPath + File.separator+"script"+File.separator+"client"+File.separator+"analyzeArticleText.js";
				String latestInfoPath = GlobalConfig.appRealPath + File.separator+"script"+File.separator+"client";
				fileList = new ArrayList<String>();
				fileList.add(articleTextPath);
				fileList.addAll(FileUtil.getFiles(latestInfoPath, false));
				new Sender(site.getRemoteIP(), site.getRemotePort(), fileList, GlobalConfig.appRealPath).start();
			} 
		}else if (Site.PUBLISH_FTP.equals(publishWay)) {
			//ftp上传
		 
			//socket远程发布
			// 发布资源
			fileList.remove(url);
			new FtpSender(site.getRemoteIP(), site.getRemotePort(),site.getFtpUserName(),site.getFtpPassWord(),site.getFtpFilePath(), fileList, SiteResource.getSiteDir(siteId, false),"send").start();
		
			// 发布文章页
			fileList = new ArrayList<String>();
			fileList.add(url);
			new FtpSender(site.getRemoteIP(), site.getRemotePort(),site.getFtpUserName(),site.getFtpPassWord(),site.getFtpFilePath(), fileList, SiteResource.getBuildStaticDir(siteId, false),"send").start();
			//远程发布所有插件下的文件
			String path= GlobalConfig.appRealPath+File.separator+"release"+File.separator+"site"+siteId+File.separator+"plugin";
			List<String> l = FileUtil.getFiles(path, false);
			List<String> kl = new ArrayList<String>();
			//替换路径中的斜杠
			for (String s : l) {
				s = s.replaceAll("\\\\", File.separator);
				kl.add(s);
			}
		//	new Sender(site.getRemoteIP(), site.getRemotePort(), kl, path).start();
			new FtpSender(site.getRemoteIP(), site.getRemotePort(),site.getFtpUserName(),site.getFtpPassWord(),site.getFtpFilePath(), kl,path,"send").start();
			
			// 发布脚本
			if (isHomePage) {
				String articleTextPath = GlobalConfig.appRealPath + File.separator+"script"+File.separator+"client"+File.separator+"analyzeArticleText.js";
				String latestInfoPath = GlobalConfig.appRealPath + File.separator+"script"+File.separator+"client";
				fileList = new ArrayList<String>();
				fileList.add(articleTextPath);
				fileList.addAll(FileUtil.getFiles(latestInfoPath, false));
				new FtpSender(site.getRemoteIP(), site.getRemotePort(),site.getFtpUserName(),site.getFtpPassWord(),site.getFtpFilePath(), fileList, GlobalConfig.appRealPath,"send").start();
			//	new Sender(site.getRemoteIP(), site.getRemotePort(), fileList, GlobalConfig.appRealPath).start();
			}
		}
	}
	
	public void publishTemplatesBySiteId(String siteId) {
		List<TemplateInstance> templates = templateInstanceDao.findByNamedQuery("findTemplateInstanceBySiteId", "siteId", siteId);
		for (TemplateInstance templateInstance : templates) {
			String buildTemplateDir = SiteResource.getBuildStaticTemplateDir(siteId, false) + File.separator + templateInstance.getId();
			String fileName = FileUtil.getFileName(templateInstance.getLocalPath());
			Site site = siteDao.getAndClear(siteId);
			
			String buildTemplateFile = buildTemplateDir + File.separator + fileName;
				
			// 拷贝模板资源
			FileUtil.makeDirs(buildTemplateDir);
			String templateFile = GlobalConfig.appRealPath + templateInstance.getLocalPath();
			String content = FileUtil.read(templateFile);
			String templateDir = FileUtil.getFileDir(templateFile);
			FileUtil.copy(templateDir, buildTemplateDir, false);
			
			//替换模板文件中的资源路径
	/*		String rsRegx = "<[^<]+=[\"']([^'\"]+[.][^'\"/-_]+)[\"']";
			Pattern p = Pattern.compile(rsRegx);
			Matcher m = p.matcher(content);
			StringBuffer sb = new StringBuffer();
			while (m.find()) {
				String s = "/template_instance/"+ templateInstance.getId() + "/" + m.group(1);
				m.appendReplacement(sb, m.group().replaceAll("[^'\"]+[.][^'\"/-_]+", s));
			}
			m.appendTail(sb);
			
			FileUtil.write(buildTemplateFile, sb.toString());*/
			
			FileUtil.write(buildTemplateFile,content.replace("images", File.separator+"template_instance"+File.separator+ templateInstance.getId() + File.separator+"images" ));
			
			// 删除无用的资源
			FileUtil.delete(buildTemplateDir + File.separator+"template_set.jsp");
			
			if (Site.PUBLISH_LOCAL.equals(site.getPublishWay())) {
				if (!FileUtil.isExist(site.getPublishDir()+File.separator+"template_instance")) {
					FileUtil.makeDirs(site.getPublishDir()+File.separator+"template_instance");
				}
				FileUtil.copy(buildTemplateDir, site.getPublishDir()+File.separator+"template_instance", true);
			} else if (Site.PUBLISH_REMOTE.equals(site.getPublishWay())) {
				//socket远程发布
				try {
					List<String> fileList = FileUtil.getFiles(buildTemplateDir, false);
					new Sender(site.getRemoteIP(), site.getRemotePort(), fileList, FileUtil.getFileDir(SiteResource.getBuildStaticTemplateDir(siteId, false))).start();
				} catch (Exception e) {
					if (StringUtil.contains(e.getMessage(), "publish")) {
						log.error(e.getMessage());
					}
				}
			}else if (Site.PUBLISH_FTP.equals(site.getPublishWay())) {
				//ftp发布
			
				try {
					List<String> fileList = FileUtil.getFiles(buildTemplateDir, false);
					new FtpSender(site.getRemoteIP(), site.getRemotePort(),site.getFtpUserName(),site.getFtpPassWord(),site.getFtpFilePath(), fileList, FileUtil.getFileDir(SiteResource.getBuildStaticTemplateDir(siteId, false)),"send").start();
				//	new Sender(site.getRemoteIP(), site.getRemotePort(), fileList, FileUtil.getFileDir(SiteResource.getBuildStaticTemplateDir(siteId, false))).start();
				} catch (Exception e) {
					if (StringUtil.contains(e.getMessage(), "publish")) {
						log.error(e.getMessage());
					}
				}
			}
		}
	}

	/**
	 * 将字符串中的绝对路径转换为域名路径
	 * 先         /cps1.0/release/site20090112321/build/static   -> (http://www.g.cn)|""
	 * 然后    /cps1.0/release/site20090112321   -> (http://www.g.cn)|""
	 * @param siteId
	 * @param str 需要被替换的字符串
	 * @param domainName 域名
	 * @return
	 */
	private String path2domain(String siteId, String str, String domainName) {
		str = str.replace("/"+GlobalConfig.appName+"/release/site"+siteId+"/build"+"/static", domainName);
		return str.replace("/"+GlobalConfig.appName+"/release/site"+siteId, domainName);
	}
	
	/**
	 * 将域名路径转换为符串中的绝对路径
	 * 如： http://www.g.cn    ->   /cps1.0/release/site20090112321/build/static
	 * @param siteId
	 * @param str 需要被替换的字符串
	 * @param domainName 域名
	 * @return
	 */
	private String domain2path(String siteId, String str, String domainName) {
		return str.replace(domainName.replaceAll("[.]", "[.]"), "/"+GlobalConfig.appName+"/release"+"/site"+siteId+"/build"+"/static");
	}
	
	/*
	public void publishArticlesByArticleId(String articleId, String siteId) {
		// TODO Auto-generated method stub
		
	}*/

	/**
	 * @param jdbcTemplate the jdbcTemplate to set
	 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * @param staticUnitAnalyzer the staticUnitAnalyzer to set
	 */
	public void setStaticUnitAnalyzer(TemplateUnitAnalyzer staticUnitAnalyzer) {
		this.staticUnitAnalyzer = staticUnitAnalyzer;
	}

	/**
	 * @param columnLinkAnalyzer the columnLinkAnalyzer to set
	 */
	public void setColumnLinkAnalyzer(TemplateUnitAnalyzer columnLinkAnalyzer) {
		this.columnLinkAnalyzer = columnLinkAnalyzer;
	}

	/**
	 * @param titleLinkAnalyzer the titleLinkAnalyzer to set
	 */
	public void setTitleLinkAnalyzer(TemplateUnitAnalyzer titleLinkAnalyzer) {
		this.titleLinkAnalyzer = titleLinkAnalyzer;
	}

	/**
	 * @param currentLocationAnalyzer the currentLocationAnalyzer to set
	 */
	public void setCurrentLocationAnalyzer(
			TemplateUnitAnalyzer currentLocationAnalyzer) {
		this.currentLocationAnalyzer = currentLocationAnalyzer;
	}

	/**
	 * @param pictureNewsAnalyzer the pictureNewsAnalyzer to set
	 */
	public void setPictureNewsAnalyzer(TemplateUnitAnalyzer pictureNewsAnalyzer) {
		this.pictureNewsAnalyzer = pictureNewsAnalyzer;
	}

	/**
	 * @param latestInfoAnalyzer the latestInfoAnalyzer to set
	 */
	public void setLatestInfoAnalyzer(TemplateUnitAnalyzer latestInfoAnalyzer) {
		this.latestInfoAnalyzer = latestInfoAnalyzer;
	}

	/**
	 * @param templateUnitDao the templateUnitDao to set
	 */
	public void setTemplateUnitDao(TemplateUnitDao templateUnitDao) {
		this.templateUnitDao = templateUnitDao;
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
	 * @param templateInstanceDao the templateInstanceDao to set
	 */
	public void setTemplateInstanceDao(TemplateInstanceDao templateInstanceDao) {
		this.templateInstanceDao = templateInstanceDao;
	}

	/**
	 * @param articleDao the articleDao to set
	 */
	public void setArticleDao(ArticleDao articleDao) {
		this.articleDao = articleDao;
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
	 * @param templateUnitCategoryDao the templateUnitCategoryDao to set
	 */
	public void setTemplateUnitCategoryDao(
			TemplateUnitCategoryDao templateUnitCategoryDao) {
		this.templateUnitCategoryDao = templateUnitCategoryDao;
	}

	public void setArticleTextAnalyzer(ArticleTextAnalyzer articleTextAnalyzer) {
		this.articleTextAnalyzer = articleTextAnalyzer;
	}
	
	public static void main(String[] args) {
//		GlobalConfig.appName = "cps1.0";
//		String s = "/cps1.0/release/site1324123/upload/sdfsdf.jspg";
//		String s1 = "http://www.g.cn/upload/sdfsdf.jspg";
//		System.out.println(path2domain("1324123", s, "http://www.g.cn"));
//		System.out.println(domain2path("1324123", s1, "http://www.g.cn"));
	}

	public ArticleBuildListDao getArticleBuildListDao() {
		return articleBuildListDao;
	}

	public void setArticleBuildListDao(ArticleBuildListDao articleBuildListDao) {
		this.articleBuildListDao = articleBuildListDao;
	}

	/**
	 * @param titleLinkPageAnalyzer the titleLinkPageAnalyzer to set
	 */
	public void setTitleLinkPageAnalyzer(TitleLinkPageAnalyzer titleLinkPageAnalyzer) {
		this.titleLinkPageAnalyzer = titleLinkPageAnalyzer;
	}

	/**
	 * @param magazineCategoryAnalyzer the magazineCategoryAnalyzer to set
	 */
	public void setMagazineCategoryAnalyzer(
			MagazineCategoryAnalyzer magazineCategoryAnalyzer) {
		this.magazineCategoryAnalyzer = magazineCategoryAnalyzer;
	}

	/**
	 * @param articlePublishListDao the articlePublishListDao to set
	 */
	public void setArticlePublishListDao(ArticlePublishListDao articlePublishListDao) {
		this.articlePublishListDao = articlePublishListDao;
	}

	/**
	 * @param onlineBulletinDao the onlineBulletinDao to set
	 */
	public void setOnlineBulletinDao(OnlineBulletinDao onlineBulletinDao) {
		this.onlineBulletinDao = onlineBulletinDao;
	}

	/**
	 * @param onlineSurverySetAnalyzer the onlineSurverySetAnalyzer to set
	 */
	public void setOnlineSurverySetAnalyzer(
			OnlineSurverySetAnalyzer onlineSurverySetAnalyzer) {
		this.onlineSurverySetAnalyzer = onlineSurverySetAnalyzer;
	}

}
