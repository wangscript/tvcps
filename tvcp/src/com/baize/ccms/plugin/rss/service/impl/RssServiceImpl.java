/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.plugin.rss.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.jdbc.core.JdbcTemplate;

import com.j2ee.cms.biz.articlemanager.dao.ArticleDao;
import com.j2ee.cms.biz.articlemanager.domain.Article;
import com.j2ee.cms.biz.columnmanager.dao.ColumnDao;
import com.j2ee.cms.biz.columnmanager.domain.Column;
import com.j2ee.cms.biz.sitemanager.dao.SiteDao;
import com.j2ee.cms.biz.sitemanager.domain.Site;
import com.j2ee.cms.biz.unitmanager.label.CommonLabel;
import com.j2ee.cms.plugin.rss.dao.RssDao;
import com.j2ee.cms.plugin.rss.domain.Rss;
import com.j2ee.cms.plugin.rss.service.RssService;
import com.j2ee.cms.plugin.rss.web.form.RssForm;
import com.j2ee.cms.sys.GlobalConfig;
import com.j2ee.cms.sys.SiteResource;
import com.j2ee.cms.common.core.util.FileUtil;
import com.j2ee.cms.common.core.util.IDFactory;

/**
 * <p>
 * 标题: —— RSS service
 * </p>
 * <p>
 * 描述: —— RSS 业务层
 * </p>
 * <p>
 * 模块: CPS 插件
 * </p>
 * <p>
 * 版权: Copyright (c) 2009  
 * </p>
 * <p>
 * 网址：http://www.j2ee.cmsweb.com
 * 
 * @author 曹名科
 * @version 1.0
 * @since 2009-10-16 下午03:35:47
 * @history（历次修订内容、修订人、修订时间等） 
 */
 

public class RssServiceImpl implements RssService {
	private final Logger log = Logger.getLogger(RssServiceImpl.class);
	/** 注入后台RSSDao. **/
	private RssDao rssDao;
	/** 注入网站dao **/
	private SiteDao siteDao;
	/** 注入栏目数据访问层 */
	private ColumnDao columnDao;
	/** 注入文章dao */
	private ArticleDao articleDao;
	/** 注入前台JdbcTemplate. **/
	private JdbcTemplate jdbcTemplate;

	private boolean flag;

	public String getOutRssList(String siteId, String appName){
		
		InputStream input;
		Site site = siteDao.getAndClear(siteId);
		String sitedir = site.getPublishDir().split("/")[site.getPublishDir().split("/").length-1];
		//dir = dir+"/plugin/rss/conf/"+siteId+".xml";
		String dir = GlobalConfig.appRealPath + "/plugin/rss/conf/"+siteId+".xml";
		StringBuffer sb = new StringBuffer();
		List columnNameList = new ArrayList();
		List RssPath = new ArrayList();
		if(FileUtil.isExist(dir)){
			try {
				String rssColumn = "";
				String columnId = "";
				String rssContent = "";
				
				input = new FileInputStream(dir);
				SAXReader read = new SAXReader();
				Document document = read.read(input);
				Element e = (Element) document.getRootElement().elementIterator("rssSet").next();
				rssColumn = e.elementText("rssColumn");
				columnId = e.elementText("columnId");
				rssContent = e.elementText("rssContent");
				String[] id = columnId.split(",");
				 
				if (rssColumn.equals("1")) {// 多栏目
					for (int i = 0; i < id.length; i++) {
						columnNameList = jdbcTemplate.queryForList(
								         "SELECT NAME FROM COLUMNS WHERE ID=?",
								         new Object[] { id[i] }, java.lang.String.class);
						RssPath = jdbcTemplate.queryForList(
								         "SELECT XMLSAVEPATH FROM RSS WHERE columnIdentifier=?",
								         new Object[] { id[i] },	java.lang.String.class);
						if (RssPath.size() > 0 && columnNameList.size() > 0) {						
							String temp = "/"+sitedir+String.valueOf(RssPath.get(0)).replaceFirst("/release/site"+siteId, "");
							Pattern pattern = Pattern.compile(CommonLabel.REGEX_LABEL);
							Matcher matcher = pattern.matcher(rssContent);
							while(matcher.find()){
								String label = matcher.group();
								if(label.equals("<!--columnName-->")){
									matcher.appendReplacement(sb, columnNameList.get(0).toString());
								} else if(label.equals("<!--rssUrl-->")){
									matcher.appendReplacement(sb, "/"+appName+temp);
								} else{
									matcher.appendReplacement(sb, "");
								}
							}
							matcher.appendTail(sb);
						}
					}
					
				} else {// 单栏目
					RssPath = jdbcTemplate.queryForList("SELECT XMLSAVEPATH FROM RSS WHERE columnIdentifier=?",
							new Object[] { "0" }, java.lang.String.class);
					if (RssPath.size() > 0) {
//						String temp = String.valueOf(RssPath.get(0));
						String temp = "/"+sitedir+String.valueOf(RssPath.get(0)).replaceFirst("/release/site"+siteId, "");
						Pattern pattern = Pattern.compile(CommonLabel.REGEX_LABEL);
						Matcher matcher = pattern.matcher(rssContent);
						while(matcher.find()){
							String label = matcher.group();
							if(label.equals("<!--columnName-->")){
								matcher.appendReplacement(sb, "所有栏目");
							} else if(label.equals("<!--rssUrl-->")){
								matcher.appendReplacement(sb, "/"+appName+temp);
							} else{
								matcher.appendReplacement(sb, "");
							}
						}
						matcher.appendTail(sb);
					}
				}
				try {
					input.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
		log.debug("rss====sb.toStirng==="+sb.toString());
		return sb.toString();
	}
	
	public List<String> getRssList(String ids, String siteId) {
		Map<String, String> m = readRssXml(siteId);
		String isColumn = m.get("rssColumn");
		String id[] = ids.split(",");
		List RssPath = new ArrayList();
		List columnNameList = new ArrayList();
		List<String> list = new ArrayList<String>();

		try {
			if (isColumn.equals("1")) {// 多栏目
				for (int i = 0; i < id.length; i++) {
					columnNameList = jdbcTemplate.queryForList(
							"SELECT NAME FROM COLUMNS WHERE ID=?",
							new Object[] { id[i] }, java.lang.String.class);
					RssPath = jdbcTemplate
							.queryForList(
									"SELECT XMLSAVEPATH FROM RSS WHERE columnIdentifier=?",
									new Object[] { id[i] },
									java.lang.String.class);
					if (RssPath.size() > 0 && columnNameList.size() > 0) {						
						String temp = String.valueOf(RssPath.get(0));
						list.add("<a href=" + temp + " >" + columnNameList.get(0) + "</a>");
					}
				}
			} else {// 单栏目
				RssPath = jdbcTemplate.queryForList(
						"SELECT XMLSAVEPATH FROM RSS WHERE columnIdentifier=?",
						new Object[] { "0" }, java.lang.String.class);
				if (RssPath.size() > 0) {
					String temp = String.valueOf(RssPath.get(0));
					list.add("<a href=" + temp + " >所有栏目</a>");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public void tt(final String siteId) {
		Thread temp = new Thread() {
			public void run() {
				Map<String, String> map = readRssXml(siteId);
				 String timeOut = map.get("rssTimeOut");
				String siteid = map.get("siteId");
				while (true) {
					try {
						Thread.sleep(60*1000*Integer.parseInt(timeOut));
						isColumnsOrMoreColumns(siteid);
						log.info("生成...");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}

		};
		temp.start();
	}

	/***
	 * 根据栏目ID查询文章集合
	 * 
	 * @param Id
	 *            栏目ID
	 * @return 文章集合
	 */
	@SuppressWarnings("unchecked")
	private List<Article> getArticleByColumnId(String Id) {
		String s1[] = { "columnId", "isState" };
		String s2[] = { Id, Article.PUBLISH_STATE_PUBLISHED };
		List<Article> list = articleDao.findByNamedQuery("findArticleByColumnIds1", s1, s2);
		if (list != null && list.size() > 0) {
			return list;
		}
		return new ArrayList<Article>();
	}

	/**
	 * 根据栏目ID，网站ID，获取当前栏目信息
	 * 
	 * @param columnId
	 * @param siteId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Column getColumnById(String columnId, String siteId) {
		Column c = new Column();
		String s1[] = { "columnId", "siteId" };
		String s2[] = { columnId, siteId };
		List<Column> li = columnDao.findByNamedQuery("findColumnByColumnIdAndSiteId", s1, s2);
		if (li != null && li.size() > 0) {
			c = (Column)li.get(0);
		}
		return c;
	}

	/**
	 * 根据网站ID查询网站信息
	 * 
	 * @param siteId网站ID
	 * @return 网站信息
	 */
	@SuppressWarnings("unchecked")
	private Site getSiteById(String siteId) {
		Site s = new Site();
		List<Site> list = siteDao.findByNamedQuery("findSiteById", "siteid", siteId);
		if (list != null && list.size() > 0) {
			s = list.get(0);
		}
		return s;
	}

	/**
	 * 将生产的XML文件路径信息添加到数据库
	 * 
	 * @param rss
	 *            RSS实体
	 * @param siteId
	 *            网站ID
	 * @param columnId
	 *            栏目ID
	 */
	private void addRss(Rss rss, String siteId, String columnId) {
		Site site = new Site();
		site.setId(siteId);
		rss.setSites(site);
		rss.setColumnIdentifier(columnId);
		rssDao.save(rss);
	}

	public void isColumnsOrMoreColumns(String siteId) {
		Map<String, String> map = readRssXml(siteId);

		String isColumns = map.get("rssColumn");
		String id = map.get("columnId");
		String ids[] = id.split(",");
		String path = "";
		String path1 = "";

		StringBuffer putRss = new StringBuffer();
		if (isColumns.equals("1")) {// 多栏目栏目脚本
			for (int j = 0; j < ids.length; j++) {
				Rss r = getRssPath(ids[j].trim());
				path = SiteResource.getRssDir(siteId, true) + "/column"	+ ids[j];// RSS栏目
				path1 = path + "/" + ids[j] + ".xml";// RSS具体文件
				String phiyPath = GlobalConfig.appRealPath + path;// 文件物理路径
				FileUtil.makeDirs(phiyPath);// 创建存放RSS栏目文件夹
				putRss = createRss(ids[j], siteId);// 获取RSS内容
				boolean flag = outRssStream(putRss.toString(), phiyPath + "/"+ ids[j] + ".xml");// 将内容写入文件,创建RSS具体文件
				log.info("读取数据");
				if (r == null && flag) {// 如果创建成功
					Rss rss = new Rss();
					rss.setCreateTime(new Date());
					rss.setXmlSavePath(path1);
					this.addRss(rss, siteId, ids[j]);// 将信息添加到数据库中
					log.info("成功保存文件并添加到数据库");
				}
			}
		} else {// 单栏目脚本
			boolean flag = false;
			Rss r = getRssPath("0");
			if (r == null) {
				String randomFile = IDFactory.getId();
				path = SiteResource.getRssDir(siteId, true) + "/column"
						+ randomFile;// RSS栏目
				path1 = path + "/" + randomFile + ".xml";// RSS具体文件
				String phiyPath = GlobalConfig.appRealPath + path;// 文件物理路径
				FileUtil.makeDirs(phiyPath);// 创建存放RSS栏目文件夹
				flag = createRss(id, siteId, phiyPath + "/" + randomFile
						+ ".xml");// 将内容写入文件,创建RSS具体文件
				if (flag) {// 如果创建成功
					Rss rss = new Rss();
					rss.setCreateTime(new Date());
					rss.setXmlSavePath(path1);
					this.addRss(rss, siteId, "0");// 将信息添加到数据库中
					log.info("成功保存文件并添加到数据库");
				}
			} else {
				createRss(id, siteId, GlobalConfig.appRealPath
						+ r.getXmlSavePath());// 将内容写入文件,创建RSS具体文件
				log.info("将内容写入文件");
			}
		}
		
		// 拷贝生成的rssxml文件
		String rssDir = SiteResource.getSiteDir(siteId, false)+"/plugin/rss";
		Site site = siteDao.getAndClear(siteId);
		String publishRssDir = site.getPublishDir()+"/plugin";
		if(!FileUtil.isExist(publishRssDir)){
			FileUtil.makeDirs(publishRssDir);
		}else{
			FileUtil.delete(publishRssDir+"/rss");
		}
		FileUtil.copy(rssDir, publishRssDir, true);
	}

	/**
	 * 根据自定义的栏目ID 查询RSS信息
	 * 
	 * @param columnId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Rss getRssPath(String columnId) {
		Rss r = new Rss();
		List<Rss> list = rssDao.findByNamedQuery("findByColumnId", "columnId",
				columnId);
		if (list != null && list.size() > 0) {
			r = list.get(0);
			return r;
		}
		return null;
	}

	/**
	 * 生成单栏目
	 * 
	 * @param id
	 * @param siteId
	 * @return
	 */
	private boolean createRss(String id, String siteId, String path) {
		String ids[] = id.split(",");
		StringBuffer putRss = new StringBuffer();
		Site s = getSiteById(siteId);
		putRss.append("<?xml version='1.0' encoding='utf-8'?>");
		putRss.append("\n");
		putRss.append("<rss version='2.0'>" + "\n");
		putRss.append("<channel>" + "\n");
		putRss.append("<title>" + s.getName() + "</title>" + "\n");
		putRss.append("<link>" + "http://"+s.getDomainName() + "</link>" + "\n");
		putRss.append("<description>" + s.getDescription() + "</description>"
				+ "\n");
		putRss.append("<language>zh-cn</language>" + "\n");
		putRss
				.append("<generator>" + s.getDomainName() + "</generator>"
						+ "\n");
		for (int i = 0; i < ids.length; i++) {
			List<Article> list = getArticleByColumnId(ids[i]);// 根据栏目ID查询文章，
			// 将文章内容放入XML
			if (list != null && list.size() > 0) {
				for (int j = 0; j < list.size(); j++) {
					Article a = list.get(j);
					putRss.append("<item>" + "\n");
					putRss.append("<title><![CDATA[" + a.getTitle()
							+ "]]></title>" + "\n");
					if(a.getUrl().startsWith("http://")){
						putRss.append("<link>" + a.getUrl() + "</link>" + "\n");
					}else{
						putRss.append("<link>" + "http://"+s.getDomainName()+a.getUrl().replaceFirst("/release/site"+siteId+"/build/static", "") + "</link>" + "\n");
					}
					putRss.append("<author>" + a.getAuthor() + "</author>"
							+ "\n");
					putRss.append("<pubDate>" + a.getCreateTime()
							+ "</pubDate>" + "\n");
					putRss.append("<description><![CDATA[" + a.getSubtitle()
							+ "]]></description>" + "\n");
					putRss.append("</item>" + "\n");
				}
			}
		}
		putRss.append("</channel>" + "\n");
		putRss.append("</rss>" + "\n");
		if (outRssStream(putRss.toString(), path)) {
			return true;
		}
		return false;
	}

	/**
	 * 生成RSS XML文件 生成多栏目
	 * 
	 * @param form
	 *            表单
	 * @param siteId
	 *            网站ID
	 * @return
	 */
	private StringBuffer createRss(String id, String siteId) {
		StringBuffer putRss = new StringBuffer();
		Site s = getSiteById(siteId);
		Column c=getColumnById(id, siteId);
		putRss.append("<?xml version='1.0' encoding='utf-8'?>");
		putRss.append("\n");
		putRss.append("<rss version='2.0'>" + "\n");
		putRss.append("<channel>" + "\n");
		putRss.append("<title>" + s.getName() + "</title>" + "\n");
		if(c.getInitUrl().startsWith("http://")){
			putRss.append("<link>" + c.getInitUrl() + "</link>" + "\n");
		}else{
			putRss.append("<link>" + "http://"+s.getDomainName()+c.getInitUrl().replaceFirst("/release/site"+siteId+"/build/static", "") + "</link>" + "\n");
		}
		//putRss.append("<link>" + c.getInitUrl().replaceFirst("/release/site"+siteId+"/build/static", "") + "</link>" + "\n");
		putRss.append("<description>" + c.getDescription() + "</description>" + "\n");
		putRss.append("<language>zh-cn</language>" + "\n");
		putRss.append("<generator>" + s.getDomainName() + "</generator>" + "\n");
		System.out.println(id + ">>>>");
		List<Article> list = getArticleByColumnId(id);// 根据栏目ID查询文章，将文章放入XML
		if (list != null && list.size() > 0) {
			for (int j = 0; j < list.size(); j++) {
				Article a = list.get(j);
				putRss.append("<item>" + "\n");
				putRss.append("<title><![CDATA[" + a.getTitle() + "]]></title>"	+ "\n");
				//putRss.append("<link>" + a.getUrl().replaceFirst("/release/site"+siteId+"/build/static", "") + "</link>" + "\n");
				if(a.getUrl().startsWith("http://")){
					putRss.append("<link>" + a.getUrl() + "</link>" + "\n");
				}else{
					putRss.append("<link>" + "http://"+s.getDomainName()+a.getUrl().replaceFirst("/release/site"+siteId+"/build/static", "") + "</link>" + "\n");
				}
				
				putRss.append("<author>" + a.getAuthor() + "</author>" + "\n");
				putRss.append("<pubDate>" + a.getCreateTime() + "</pubDate>"
						+ "\n");
				putRss.append("<description><![CDATA[" + a.getSubtitle()
						+ "]]></description>" + "\n");
				putRss.append("</item>" + "\n");
			}
		}
		putRss.append("</channel>" + "\n");
		putRss.append("</rss>" + "\n");
		return putRss;
	}

	/**
	 * 写XML文件
	 * 
	 * @param content      内容
	 * @param path         保存路径
	 * @return             是否成功
	 */
	private boolean outRssStream(String content, String path) {
		boolean flag = false;
		OutputStream output = null;
		try {
			byte[] b = content.toString().getBytes("utf-8");
			output = new FileOutputStream(new File(path));
			for (int i = 0; i < b.length; i++) {
				output.write(b[i]);
			}
			flag = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}

	public void saveRssSet(RssForm form, String siteId) {
		SAXReader read = new SAXReader();
		XMLWriter writer = null;
		try {
			// 删除原来有的配置文件
			File file = new File(SiteResource.getRssDir(siteId, false)+"/saveRss.xml");
			if(file.exists()){
				file.delete();
			}
			File file1 = new File(SiteResource.getRssDir(siteId, false)+"/"+siteId+".xml");
			if(file1.exists()){
				file1.delete();
			}
			// 拷贝新的配置文件
			FileUtil.copy(GlobalConfig.appRealPath + "/plugin/rss/conf/saveRss.xml", SiteResource.getRssDir(siteId, false), false);
			// 重新命名rssxml配置文件
			file.renameTo(file1);
			
			InputStream input = new FileInputStream(file1);
			Document document = read.read(input);
			document.selectSingleNode("//rss//rssSet//rssTimeOut").setText(form.getSpacingTime());
			document.selectSingleNode("//rss//rssSet//rssColumn").setText(form.getIsColumnOrMoreColumn());
			document.selectSingleNode("//rss//rssSet//columnId").setText(form.getIds());
			document.selectSingleNode("//rss//rssSet//rssContent").setText(form.getRssContent());
			document.selectSingleNode("//rss//rssSet//siteId").setText(siteId);
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("utf-8");
			writer = new XMLWriter(new FileOutputStream(file1), format);
			writer.write(document);
			writer.close();
			input.close();
			
			// 拷贝文件到/cps/plugin/rss/conf/saveRss.xml
			String conf = GlobalConfig.appRealPath + "/plugin/rss/conf/"+siteId+".xml";
			if(FileUtil.isExist(conf)) {
				FileUtil.delete(conf);
			}
			FileUtil.copy(SiteResource.getRssDir(siteId, false)+"/"+siteId+".xml", GlobalConfig.appRealPath + "/plugin/rss/conf/");
			
			// 拷贝文件到发布目录
			Site site = siteDao.getAndClear(siteId);
			String dir = site.getPublishDir().substring(0, site.getPublishDir().length()-site.getPublishDir().split("/")[site.getPublishDir().split("/").length-1].length());
			String publishDir = dir+"/plugin/rss/conf";
			if(!FileUtil.isExist(publishDir)){
				FileUtil.makeDirs(publishDir);
			}
			if(FileUtil.isExist(publishDir+"/"+siteId+".xml")){
				FileUtil.delete(publishDir+"/"+siteId+".xml");
			}
			FileUtil.copy(SiteResource.getRssDir(siteId, false)+"/"+siteId+".xml", publishDir);
			
			
			log.info("RSS配置成功");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Map<String, String> readRssXml(String siteId) {
		Map<String, String> map = new Hashtable<String, String>();
		InputStream input;
		try {
			if(FileUtil.isExist(SiteResource.getRssDir(siteId, false)+"/"+siteId+".xml")){
				input = new FileInputStream(SiteResource.getRssDir(siteId, false)+"/"+siteId+".xml");
			} else {
				input = new FileInputStream(GlobalConfig.appRealPath + "/plugin/rss/conf/saveRss.xml");
			}
			SAXReader read = new SAXReader();
			Document document = read.read(input);
			Element e = (Element) document.getRootElement().elementIterator("rssSet").next();
			map.put("rssTimeOut", e.elementText("rssTimeOut"));
			map.put("rssColumn", e.elementText("rssColumn"));
			map.put("columnId", e.elementText("columnId"));
			map.put("siteId", e.elementText("siteId"));
			return map;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 发布Rss目录 
	 * @param siteId
	 */
	public void publishRssDir(String siteId){
		 Site site = siteDao.getAndClear(siteId);
		 String dir = site.getPublishDir().substring(0, site.getPublishDir().length()-site.getPublishDir().split("/")[site.getPublishDir().split("/").length-1].length());
		 String publishDir = dir+"/plugin";
		 if(!FileUtil.isExist(publishDir)){
			 FileUtil.makeDirs(publishDir);
		 }
		 String rssDir = GlobalConfig.appRealPath + "/plugin/rss";
		 if(FileUtil.isExist(rssDir)){
			 if(!FileUtil.isExist(publishDir+"/rss")){
				 FileUtil.copy(rssDir, publishDir, true);
			 }else{
				 FileUtil.copy(rssDir+"/rss_list.jsp", publishDir+"/rss");
				 FileUtil.copy(rssDir+"/rss_set.jsp", publishDir+"/rss");
				 FileUtil.copy(rssDir+"/rss.jsp", publishDir+"/rss");
				 FileUtil.copy(rssDir+"/out_rss_list_success.jsp", publishDir+"/rss");
			 }
		 }
	}
	
	public void setRssDao(RssDao rssDao) {
		this.rssDao = rssDao;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * @param articleDao
	 *            the articleDao to set
	 */
	public void setArticleDao(ArticleDao articleDao) {
		this.articleDao = articleDao;
	}

	/**
	 * @param siteDao
	 *            the siteDao to set
	 */
	public void setSiteDao(SiteDao siteDao) {
		this.siteDao = siteDao;
	}

	/**
	 * @param columnDao
	 *            the columnDao to set
	 */
	public void setColumnDao(ColumnDao columnDao) {
		this.columnDao = columnDao;
	}

	/**
	 * @param flag
	 *            the flag to set
	 */
	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	/**
	 * @return the flag
	 */
	public boolean isFlag() {
		return flag;
	}
}
