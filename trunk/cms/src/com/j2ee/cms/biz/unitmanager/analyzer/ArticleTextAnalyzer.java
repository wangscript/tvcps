/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.unitmanager.analyzer;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.j2ee.cms.biz.articlemanager.dao.ArticleAttributeDao;
import com.j2ee.cms.biz.articlemanager.dao.ArticleDao;
import com.j2ee.cms.biz.articlemanager.dao.ArticleFormatDao;
import com.j2ee.cms.biz.articlemanager.domain.Article;
import com.j2ee.cms.biz.articlemanager.domain.ArticleFormat;
import com.j2ee.cms.biz.columnmanager.dao.ColumnDao;
import com.j2ee.cms.biz.sitemanager.dao.SiteDao;
import com.j2ee.cms.biz.sitemanager.domain.Site;
import com.j2ee.cms.biz.templatemanager.dao.TemplateInstanceDao;
import com.j2ee.cms.biz.templatemanager.dao.TemplateUnitDao;
import com.j2ee.cms.biz.templatemanager.domain.TemplateUnit;
import com.j2ee.cms.biz.unitmanager.label.ArticleTextLabel;
import com.j2ee.cms.biz.unitmanager.label.CommonLabel;
import com.j2ee.cms.biz.unitmanager.label.TitleLinkLabel;
import com.j2ee.cms.biz.usermanager.dao.UserDao;
import com.j2ee.cms.biz.usermanager.domain.User;
import com.j2ee.cms.common.core.util.BeanUtil;
import com.j2ee.cms.common.core.util.CollectionUtil;
import com.j2ee.cms.common.core.util.DateUtil;
import com.j2ee.cms.common.core.util.FileUtil;
import com.j2ee.cms.common.core.util.IDFactory;
import com.j2ee.cms.common.core.util.StringUtil;
import com.j2ee.cms.common.core.util.XmlUtil;
import com.j2ee.cms.sys.GlobalConfig;
import com.j2ee.cms.sys.SiteResource;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CPS</p>
 * <p>版权: Copyright (c) 2009  
 * @author 郑荣华
 * @version 1.0
 * @since 2009-7-23 上午11:08:25
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class ArticleTextAnalyzer implements TemplateUnitAnalyzer {

	private static final Logger log = Logger.getLogger(ArticleTextAnalyzer.class);
	
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
	/** 文章格式dao */
	private ArticleFormatDao articleFormatDao;
	/** 注入用户dao*/
	private UserDao userDao;
	/**
	 * 解析html代码并返回给页面
	 * @param unitId
	 * @param columnId
	 * @param siteId
	 * @param commonLabel
	 * @return
	 */
	public String getHtml(String unitId, String articleId, String columnId, String siteId, Map<String,String> commonLabel) {
		StringBuffer sb = new StringBuffer();
		sb.append(SiteResource.getPageJsPath());
		// 默认查询格式为默认格式的文章articleId == null || articleId.equals("") || articleId.equals("0")
		if(StringUtil.isEmpty(articleId) || articleId.equals("0") || articleId.equals("null")) { 
			boolean defaultFormat = true; 
			ArticleFormat articleFormat = new ArticleFormat(); 
			List list = articleFormatDao.findByNamedQuery("findArticleFormatByDefaultFormat", "defaultFormat", defaultFormat);
			if(list != null && list.size() > 0) {
				articleFormat = (ArticleFormat) list.get(0);
				String articleFormatId = articleFormat.getId();
				boolean deleted = false;
				String[] params = {"articleFormatId"};
				Object[] values = {articleFormatId};
				List list1 = articleDao.findByNamedQuery("findAuditArticleByColumnFormatId", params, values);
				if(!CollectionUtil.isEmpty(list1)) { 
					Object[] objects =  new Object[2];
					objects = (Object[]) list1.get(0);
					Article article = (Article) objects[0];
					if(article != null && article.getId() != null) {
						String str = analyzerArticleText(unitId, article, siteId, commonLabel);
						sb.append(str);
					}
				}
			}
 		} else {
 			Article article = articleDao.getAndNonClear(articleId);
 			if(article != null){
 				String str = analyzerArticleText(unitId, article, siteId, commonLabel);
 				sb.append(str);
 			}
 		}
		return sb.toString();
	}
	
	/**
	 * 解析文章正文
	 * @param unitId
	 * @param article
	 * @param siteId
	 * @param commonLabel
	 * @return
	 */
	private String analyzerArticleText(String unitId, Article article, String siteId, Map<String,String> commonLabel) {
		String htmlCode = this.findHtmlPath(unitId);
		htmlCode = StringUtil.trimEnter(htmlCode);
		Pattern forPattern = Pattern.compile(CommonLabel.REGEX_FOR);
		Matcher forMatcher = forPattern.matcher(htmlCode);
		String str = htmlCode;
		StringBuffer sb = new StringBuffer();
		// 先处理所有的for
		if(forMatcher.find()) {
			forMatcher.appendReplacement(sb, "");
			str = this.getForData(forMatcher.group(1), unitId, article, siteId);
			sb.append(str);
		}
		
		forMatcher.appendTail(sb);
		Pattern ifPattern = Pattern.compile(CommonLabel.IF);
		Matcher ifMatcher = ifPattern.matcher(sb.toString());
		StringBuffer sb1 = new StringBuffer();
		
		// 处理完for的情况后处理有if的情况
		if(ifMatcher.find()) {
			str = this.getData(ifMatcher.group(), unitId, article, siteId);
			ifMatcher.appendReplacement(sb1, str);
		}
		ifMatcher.appendTail(sb1);
		
		Pattern labelPattern = Pattern.compile(CommonLabel.REGEX_LABEL);
		Matcher labelMatcher = labelPattern.matcher(sb1.toString());
		// 处理完for和if后处理符合正则的标签
		if(labelMatcher.find()) {
			str = this.sub(sb1.toString(), unitId, article, siteId);
			return str;
		} else {
			return sb1.toString();
		}
	}
	
	/**
	 * 获得循环标签里面的数据
	 * @param src
	 * @param unitId
	 * @param columnId
	 * @param siteId
	 * @return
	 */
	private String getForData(String src, String unitId, Article article, String siteId) {
		//根据栏目ID查询出栏目对象
		StringBuffer sb = new StringBuffer();
		String str = "";
		str = this.sub(src, unitId, article, siteId);
		sb.append(str);
		return sb.toString();
	}

	/**
	 * 解析标签（替换标签里面的内容）
	 * @param src 形式：aa<!--articleName-->bb 并不出现for标签这里只是for标签里面的内容
	 * @param unitId
	 * @param columnId
	 * @param article
	 * @return
	 */
	public String sub(String src, String unitId, Article article, String siteId) {	
		StringBuffer sb = new StringBuffer();
		Site site = siteDao.getAndClear(siteId);
		TemplateUnit unit = templateUnitDao.getAndClear(unitId);
		String configFilePath = unit.getConfigFile();
		//获取到当前模板实例的xml配置文件路径
		String filePath = GlobalConfig.appRealPath+ configFilePath;
		XmlUtil xmlUtil = XmlUtil.getInstance(filePath);
		// 文章正文样式
		String articleStyle = String.valueOf(xmlUtil.getNodeText("j2ee.cms/article-text/article-text-style"));
		// 文章评论
		String articleComment = String.valueOf(xmlUtil.getNodeText("j2ee.cms/article-text/article-text-comment"));
		// 文章评论是否图片
		String articleCommentPic = String.valueOf(xmlUtil.getNodeText("j2ee.cms/article-text/article-text-comment-pic"));
		// 文章正文每页显示字数
		String pageSize = String.valueOf(xmlUtil.getNodeText("j2ee.cms/article-text/article-text-pageSize"));
		int size = StringUtil.parseInt(pageSize);
		if(size < 0) {
			size = 0;
		}
		// 处理for里面的if
		Pattern ifPattern = Pattern.compile(CommonLabel.IF);
		Matcher ifMatcher = ifPattern.matcher(src);
		String ifData = "";
		StringBuffer sb1 = new StringBuffer();
		if(article != null && article.getId() == null) {
			article.setCreateTime(null);
			article.setDisplayTime(null);
		}
		if(ifMatcher.find()) {
			ifMatcher.appendReplacement(sb1, "");
			// ifMatcher.group()  形式：<!--if-->aaa<!--else-->cc<!--/if-->
			ifData = this.getData(ifMatcher.group(), unitId, article, siteId);
			sb1.append(ifData);
		}
		ifMatcher.appendTail(sb1);
		
		Pattern labelPatttern = Pattern.compile(CommonLabel.REGEX_LABEL);	
		Matcher labelMatcher = labelPatttern.matcher(sb1.toString());
		// 处理for 里面的标签
		String label = "";	
		
		// 处理for里面的标签
		while(labelMatcher.find()) {
			label = labelMatcher.group();
			// 网站名称	
			if(label.equals(CommonLabel.SITE_NAME)) {
				String siteName = site.getName();
				if(siteName != null ){
					labelMatcher.appendReplacement(sb, siteName);
				}
				
			// 应用名	
			} else if(label.equals(CommonLabel.APP_NAME)){
				String appName = GlobalConfig.appName;
				labelMatcher.appendReplacement(sb, appName);
			
			// 网站id	
			} else if(label.equals(CommonLabel.SITE_ID)){
				labelMatcher.appendReplacement(sb, siteId);
			
			// 网站链接	
			} else if(label.equals(CommonLabel.SITE_LINK)) {
				String siteUrl = site.getUrl();
				if(siteUrl != null ) {
					labelMatcher.appendReplacement(sb, SiteResource.getUrl(siteUrl, true));
				}
				
			// 信息作者 
			} else if(label.equals(TitleLinkLabel.ARTICLEARTICLEAUTHOR)) {	
				String author = article.getAuthor();				
				this.proccessSubLabel(labelMatcher, sb, author);
			
			// 文章审核人	
			} else if(label.equals(TitleLinkLabel.ARTICLEAUDITOR)) {
				User auditor = article.getAuditor();
				if(auditor != null) {
					String auditorId = auditor.getId();
					auditor = userDao.getAndClear(auditorId);
					String strauditor = auditor.getName();
					this.proccessSubLabel(labelMatcher, sb, strauditor);
				}else {
					labelMatcher.appendReplacement(sb, "");
				}
				
			// 信息录入人
			} else if(label.equals(TitleLinkLabel.ARTICLEEDITOR)) {
				User user = article.getCreator();
				if(user != null) {
					String userid = user.getId();
					user = userDao.getAndClear(userid);
					String creatorName = user.getName();
					this.proccessSubLabel(labelMatcher, sb, creatorName);
				}else {
					labelMatcher.appendReplacement(sb, "");
				}
			//文章ID
			} else if(label.equals(TitleLinkLabel.ARTICLEID)) {
				String articleId = article.getId();
				labelMatcher.appendReplacement(sb, articleId);
			// 文章标题	
			} else if(label.equals(TitleLinkLabel.ARTICLETITLE)) {				
				String title = article.getTitle();
				this.proccessSubLabel(labelMatcher, sb, title);
			
			// 副标题 	
			} else if(label.equals(TitleLinkLabel.ARTICLESUBTITLE)) {
				String subtitle = article.getSubtitle();
				this.proccessSubLabel(labelMatcher, sb, subtitle);
			
			// 引题
			} else if(label.equals(TitleLinkLabel.ARTICLEQUOTETITLE)) {
				String leadingTitle = article.getLeadingTitle();
				this.proccessSubLabel(labelMatcher, sb, leadingTitle);
			
			// 链接标题	
			} else if(label.equals(TitleLinkLabel.ARTICLELINKTITLE)) {
				String url = article.getUrl();
				if(!StringUtil.isEmpty(url)) {
					labelMatcher.appendReplacement(sb, SiteResource.getUrl(url, true));
				} else {
					labelMatcher.appendReplacement(sb, "");
				}	
				
			// 信息摘要	
			} else if(label.equals(TitleLinkLabel.ARTICLEBRIEF)) {
				String brief = article.getBrief();
				this.proccessSubLabel(labelMatcher, sb, brief);
			
			// 信息关键字	
			} else if(label.equals(TitleLinkLabel.ARTICLEKEYWORDS)) {
				String keyword = article.getKeyword(); 
				this.proccessSubLabel(labelMatcher, sb, keyword);
				
			// 信息访问次数 	
			} else if(label.equals(TitleLinkLabel.ARTICLEHITS)) {
				if(article.getId() != null) {
					int hits = article.getHits();
					String hit = String.valueOf(hits);
					this.proccessSubLabel(labelMatcher, sb, hit);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
			
			// 信息来源		
			} else if(label.equals(TitleLinkLabel.ARTICLESORCE)) {
				String infoSource = article.getInfoSource();
				this.proccessSubLabel(labelMatcher, sb, infoSource);
			
			// 文章评论	
			} else if(label.equals(ArticleTextLabel.ARTICLE_COMMENT)) {
				if(!StringUtil.isEmpty(articleComment)) {
					int isSuffixPic = StringUtil.parseInt(articleCommentPic);
					// 评论是否是图片	
					if(isSuffixPic == 1) {
						labelMatcher.appendReplacement(sb, "<img  src=\"" + SiteResource.getUrl(articleComment, true) +"\"/>");	
					} else if(isSuffixPic == 0) {
						labelMatcher.appendReplacement(sb, articleComment);
					} else {
						labelMatcher.appendReplacement(sb, "");
					}
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
			
			// 信息创建时间	
			} else if(label.equals(TitleLinkLabel.ARTICLECREATEDATE)) {
				Date createTime = article.getCreateTime();
				if(createTime != null) {
					String time = DateUtil.toString(createTime, "yyyy-MM-dd HH:mm:ss");
					this.proccessSubLabel(labelMatcher, sb, time);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
				
			// 信息显示时间	
			} else if(label.equals(TitleLinkLabel.ARTICLEDEPLOYTIME)) {
				Date displayTime = article.getDisplayTime();
				if(displayTime != null) {
					String time = DateUtil.toString(displayTime, "yyyy-MM-dd HH:mm:ss");
					this.proccessSubLabel(labelMatcher, sb, time);	
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
			
			// 四位年 
			} else if(label.equals(TitleLinkLabel.YEAR4)) {
				Date date = article.getDisplayTime();
				if(date != null) {
					String strdate = DateUtil.toString(date, "yyyy");
					labelMatcher.appendReplacement(sb, strdate);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
				
			// 两位年 
			} else if(label.equals(TitleLinkLabel.YEAR2)) {
				Date date = article.getDisplayTime();
				if(date != null) {
					String strdate = DateUtil.toString(date, "yy");
					labelMatcher.appendReplacement(sb, strdate);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
				
			// 两位月 	
			} else if(label.equals(TitleLinkLabel.MONTH2)) {
				Date date = article.getDisplayTime();
				if(date != null){
					String strdate = DateUtil.toString(date,"yyyy-MM-dd");
					String str[] = strdate.split("-");
					if(str != null && !str.equals("") && str.length >= 2) {
						labelMatcher.appendReplacement(sb, str[1]);
					} else {
						labelMatcher.appendReplacement(sb, "");
					}
				}else{
					labelMatcher.appendReplacement(sb, "");
				}
				
			// 一位月	
			} else if(label.equals(TitleLinkLabel.MONTH1)) {
				Date date = article.getDisplayTime();
				if(date != null) {
					String strdate = DateUtil.toString(date,"yyyy-MM-dd");							
					String str[] = strdate.split("-");	
					if(str != null && !str.equals("") && str.length >= 2) {
						labelMatcher.appendReplacement(sb, String.valueOf(StringUtil.parseInt(str[1])));
					} else {
						labelMatcher.appendReplacement(sb, "");
					}
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
			
			// 两位日 
			} else if(label.equals(TitleLinkLabel.DAY2)) {
				Date date = article.getDisplayTime();
				if(date != null){
					String strdate = DateUtil.toString(date,DateUtil.FORMAT_DATE_1);
					String str[] = strdate.split("-");	
					if(str != null && !str.equals("") && str.length >= 3) {
						labelMatcher.appendReplacement(sb, str[2]);
					} else {
						labelMatcher.appendReplacement(sb, "");
					}
				} else {
					labelMatcher.appendReplacement(sb,"");
				}
				
			// 一位日 	
			} else if(label.equals(TitleLinkLabel.DAY1)) {
				Date date = article.getDisplayTime();
				if(date != null) {
					String strdate = DateUtil.toString(date,DateUtil.FORMAT_DATE_1);
					String str[] = strdate.split("-");	
					if(str != null && !str.equals("") && str.length >= 3) {
						labelMatcher.appendReplacement(sb, String.valueOf(StringUtil.parseInt(str[2])));
					} else {
						labelMatcher.appendReplacement(sb, "");
					}
				} else {
					labelMatcher.appendReplacement(sb,"");
				}
			
			// 时	
			} else if(label.equals(TitleLinkLabel.HOUR)) {
				Date date = article.getDisplayTime();
				if(date != null) {
					String strdate = DateUtil.toString(date, "HH");			
					labelMatcher.appendReplacement(sb, strdate);	
				} else {
					labelMatcher.appendReplacement(sb,"");
				}
				
			// 分		
			} else if(label.equals(TitleLinkLabel.MINUTE)) {
				Date date = article.getDisplayTime();
				if(date != null) {
					String strdate = DateUtil.toString(date, "mm");			
					labelMatcher.appendReplacement(sb, strdate);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
			
			// 秒	
			} else if(label.equals(TitleLinkLabel.SECOND)) {
				Date date = article.getDisplayTime();
				if(date != null) {
					String strdate = DateUtil.toString(date, "ss");		
					labelMatcher.appendReplacement(sb, strdate);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
				
			// 字段标签	
			} else {
				String other = "<!--(.*)-->";
				Pattern p = Pattern.compile(other);
				Matcher m = p.matcher(label);
				String getOtherValue = "";
				if(m.find()) {
					getOtherValue = m.group(1);
					String articleId = article.getId();
					article = articleDao.getAndNonClear(articleId);
					Object obj = BeanUtil.getFieldValue(article, "com.j2ee.cms.biz.articlemanager.domain.Article", getOtherValue);
					if(obj != null){
						// 是日期
						if (obj instanceof Date) {
							obj = DateUtil.toString((Date)obj, "yyyy-MM-dd HH:mm:ss");
							String str = String.valueOf(obj);
							this.proccessSubLabel(labelMatcher, sb, str);
						} else {
							String str = String.valueOf(obj);
							// 是图片、附件、媒体 
							if(getOtherValue.startsWith("pic")
									|| getOtherValue.startsWith("attach")
									|| getOtherValue.startsWith("media")) {
								if(!StringUtil.isEmpty(str) && !str.equals("null")) {
									str = SiteResource.getUrl(str, true);
								}
							
							// 要分页的情况
							} else if(getOtherValue.startsWith("textArea")) {
								if(!str.equals("null")) {
									str = this.getArticlePage(str, siteId, articleId);
								} 
							} else {
								if(str.equals("false") || str.equals(false)) {
									str = "否";
								} else if(str.equals("true") || str.equals(true)) {
									str = "是";
								} 
							}
							this.proccessSubLabel(labelMatcher, sb, str);
						}
					}
				
				}
			}
		}
		labelMatcher.appendTail(sb);
		return sb.toString();
	}
	
	/**
	 * 文章分页
	 * @param page
	 * @param size
	 * @param str
	 * @return
	 */
	private String getArticlePage(String str, String siteId, String articleId) {
		String pageFlag = "<div style=\"page-break-after: always\"><span style=\"display: none\">&nbsp;</span></div>";
		//  替换<!--(.*)-->
		String other = "<!--(.*)-->";
		StringBuffer sb = new StringBuffer();
		Pattern p = Pattern.compile(other);
		Matcher m = p.matcher(str);
		while(m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		str = sb.toString();
		String[] tmp = str.split(pageFlag);
		if(tmp.length == 1) {
			return str;
		}
		String articleXml = "article_"+ articleId +".xml";
		String dir = GlobalConfig.appRealPath + SiteResource.getBuildStaticDir(siteId, true)+"/latestInfo/";
		if(!FileUtil.isExist(dir)){
			FileUtil.makeDirs(dir);
		}
		String path =  "/"+GlobalConfig.appName+SiteResource.getBuildStaticDir(siteId, true)+"/latestInfo/" + articleXml;
		String localPath = GlobalConfig.appRealPath + SiteResource.getBuildStaticDir(siteId, true)+"/latestInfo/" + articleXml;
		String divId = IDFactory.getId();
		String content = "<div id=\""+ divId +"\">";
		content += this.createXml(path, tmp, articleId, siteId, divId, localPath);
		content += "</div>";
		return content;
	}

	/**
	 * 处理sub截取数据
	 * @param labelMatcher
	 * @param sb
	 * @param data
	 */
	private void proccessSubLabel(Matcher labelMatcher, StringBuffer sb, String data) {
		if(data != null && !data.equals("null")) {					
			labelMatcher.appendReplacement(sb, data);		
		} else {
			labelMatcher.appendReplacement(sb, "");
		}
	}
	
	/**
	 * 控制if语句，直到没有在出现if语句的情况
	 * @param ifSrc
	 * @param unitId
	 * @param columnId
	 * @return
	 */
	public  String getData(String ifSrc, String unitId, Article article, String siteId) {
		String str = ifSrc;
		while(true) {
			Pattern ifPattern= Pattern.compile(CommonLabel.IF);
			Matcher ifMatcher = ifPattern.matcher(str);
			//  如果还有与<!--if--><!--else--><!--/if-->匹配的
			if(ifMatcher.find()) {
				str = getIfData(str, unitId, article, siteId);	
			} else {
				return str;
			}
		}
	}
	
	/**
	 * 获得if之间的数据
	 * @param ifsrc
	 * @param unitId
	 * @param columnId
	 * @return
	 */
	public  String getIfData(String ifsrc, String unitId, Article article, String siteId) {
		Pattern ifPattern = Pattern.compile(CommonLabel.IF);
		Matcher ifMatcher = ifPattern.matcher(ifsrc);
		TemplateUnit unit = templateUnitDao.getAndClear(unitId);
		String configFilePath = unit.getConfigFile();
		//"D:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp2/wtpwebapps/cps1.0/release/site1/template_instance/1244619970656/conf/20090616190926187450861579.xml"; 
		//获取到当前模板实例的xml配置文件路径
		String filePath = GlobalConfig.appRealPath + configFilePath;
		XmlUtil xmlUtil = XmlUtil.getInstance(filePath);
		StringBuffer sb = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		int a = 0;
		if(ifMatcher.find()) {
			Pattern ifStartPattern = Pattern.compile(CommonLabel.IF_START);
			Matcher ifStartMatcher = ifStartPattern.matcher(ifMatcher.group());
			if(ifStartMatcher.find()) {
				Pattern ifPattern2 = Pattern.compile(CommonLabel.IF);
				Matcher ifMatcher2 = ifPattern2.matcher(ifStartMatcher.group());
				// 如果在<!--if--><!--else--> 之间还有<!--if-->(.*)<!--else-->(.*)<!--/if-->这样的代码
				if(ifMatcher2.find()) {
					a = 1;
					String str = "";
					str = this.getIfData(ifStartMatcher.group(), unitId, article, siteId);	
					sb2.append(str);
					ifStartMatcher.appendReplacement(sb, sb2.toString());
				} 
			}
			if(a == 1) {
				ifStartMatcher.appendTail(sb);
				a = 2;
			}
			if(a==0) {
				String str = getIFAndElseAndIFData(ifsrc, xmlUtil, article, unitId, siteId);
				sb2.append(str);
				return sb2.toString();
			}
		}
		StringBuffer sb4 = new StringBuffer();
		if(a==2) {
			ifMatcher.appendReplacement(sb4, sb.toString());
			ifMatcher.appendTail(sb4);
			return sb4.toString();
		}
		return sb.toString();
	}
	
	/**
	 * 获得if和and之间的数据（只有一层）
	 * @param str
	 * @param xmlUtil
	 * @return
	 */
	private String getIFAndElseAndIFData(String str, XmlUtil xmlUtil, Article article, String unitId, String siteId) {
		Pattern p = Pattern.compile(CommonLabel.IF);
		Matcher m = p.matcher(str);
		StringBuffer sb = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		
		if(m.find()) {
			String changeCode = "";
			Pattern ifStartPattern1 = Pattern.compile(CommonLabel.IF_START);
			//ifStartMatcher.group() (<!--if--><!--else--><!--/if-->)
			Matcher ifStartMatcher1 = ifStartPattern1.matcher(m.group());
			// ifStartMatcher1.group()  (<!--if--><!--else-->)
			if(ifStartMatcher1.find()) {
				String label = ifStartMatcher1.group(1);
				//  原来： <!--columnName--><!--columnLink-->  在 <!--if--><!--else-->之间
				//  后来: columnName columLink
				changeCode = getIfStartOrIfEnd(label, unitId, xmlUtil, article, siteId);
			}
			sb2.append(changeCode);
			Pattern ifEndPattern1 = Pattern.compile(CommonLabel.IF_END);
			Matcher ifEndMatcher1 = ifEndPattern1.matcher(m.group());
			if(StringUtil.isEmpty(changeCode)) {
				if(ifEndMatcher1.find()) {
					//<!--else--><!--/if-->
					String label = ifEndMatcher1.group(1);
					changeCode = sub(label, unitId, article, siteId);
				}
				sb2.append(changeCode);
			}
			m.appendReplacement(sb, sb2.toString());
		}
		m.appendTail(sb);
		return sb.toString();
	}
	
	/**
	 * 获得 <!--if-->(.*)<!--else--> 或者 <!--else-->(.*)<!--/if--> 里面的内容
	 * @param src
	 * @param column
	 * @param xmlUtil
	 * @return
	 */
	private String getIfStartOrIfEnd(String src, String unitId, XmlUtil xmlUtil, Article article, String siteId) {
		// 文章正文样式
		String articleStyle = String.valueOf(xmlUtil.getNodeText("j2ee.cms/article-text/article-text-style"));
		// 文章评论
		String articleComment = String.valueOf(xmlUtil.getNodeText("j2ee.cms/article-text/article-text-comment"));
		// 文章评论是否图片
		String articleCommentPic = String.valueOf(xmlUtil.getNodeText("j2ee.cms/article-text/article-text-comment-pic"));
		// 文章正文每页显示字数
		String pageSize = String.valueOf(xmlUtil.getNodeText("j2ee.cms/article-text/article-text-pageSize"));
		int size = StringUtil.parseInt(pageSize);
		if(size < 0) {
			size = 0;
		}
		Site site = siteDao.getAndClear(siteId);
		String label = "";	
		StringBuffer sb = new StringBuffer();
		Pattern labelPatttern = Pattern.compile(CommonLabel.REGEX_LABEL);	
		Matcher labelMatcher = labelPatttern.matcher(src);	
		int a = 0;
		String str = "";
		
		while (labelMatcher.find() ) {
			label = labelMatcher.group();
			
			// 网站名称	
			if(label.equals(CommonLabel.SITE_NAME)) {
				String siteName = site.getName();
				str = this.proccessLabel(labelMatcher, str, a, sb, siteName);
			
			// 网站链接	
			} else if(label.equals(CommonLabel.SITE_LINK)) {
				String siteUrl = site.getUrl();
				if(siteUrl != null ) {
					str = this.proccessLabel(labelMatcher, str, a, sb, SiteResource.getUrl(siteUrl, true));
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
			
			// 应用名	
			} else if(label.equals(CommonLabel.APP_NAME)){
				String appName = GlobalConfig.appName;
				str = this.proccessLabel(labelMatcher, str, a, sb, appName);
			
			// 网站id	
			} else if(label.equals(CommonLabel.SITE_ID)){
				str = this.proccessLabel(labelMatcher, str, a, sb, siteId);
				
			//文章ID
			} else if(label.equals(TitleLinkLabel.ARTICLEID)) {
				String articleId = article.getId();
				str = this.proccessLabel(labelMatcher, str, a, sb, articleId);
				
			// 文章作者
			} else if(label.equals(TitleLinkLabel.ARTICLEARTICLEAUTHOR)) {	
				String author = article.getAuthor();				
				str = this.proccessLabel(labelMatcher, str, a, sb, author);
				
			//文章审核人
			} else if(label.equals(TitleLinkLabel.ARTICLEAUDITOR)){
				User auditor = article.getAuditor();
				if(auditor != null) {
					String id = auditor.getId();
					auditor = userDao.getAndClear(id);
					String strauditor = auditor.getName();
					str = this.proccessLabel(labelMatcher, str, a, sb, strauditor);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
				
			// 信息录入人
			} else if(label.equals(TitleLinkLabel.ARTICLEEDITOR)) {
				User user = article.getCreator();
				if(user != null) {
					String id = user.getId();
					user = userDao.getAndClear(id);
					String creatorName = user.getName();
					str = this.proccessLabel(labelMatcher, str, a, sb, creatorName);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}	
				
			//引题
			} else if(label.equals(TitleLinkLabel.ARTICLEQUOTETITLE)) {
				String leadingTitle = article.getLeadingTitle();
				str = this.proccessLabel(labelMatcher, str, a, sb, leadingTitle);
				
			//副标题
			} else if(label.equals(TitleLinkLabel.ARTICLESUBTITLE)) {
				String subtitle = article.getSubtitle();
				str = this.proccessLabel(labelMatcher, str, a, sb, subtitle);
				
			// 标题
			} else if(label.equals(TitleLinkLabel.ARTICLETITLE)) {				
				String title = article.getTitle();
				str = this.proccessLabel(labelMatcher, str, a, sb, title);

			// 链接标题
			} else if(label.equals(TitleLinkLabel.ARTICLELINKTITLE)) {
				String url = article.getUrl();
				if(url != null) {
					url = SiteResource.getUrl(url, true);
					str = this.proccessLabel(labelMatcher, str, a, sb, url);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}	
				
			// 信息摘要
			} else if(label.equals(TitleLinkLabel.ARTICLEBRIEF)) {
				String brief = article.getBrief();
				str = this.proccessLabel(labelMatcher, str, a, sb, brief);

			// 信息关键字 	
			} else if(label.equals(TitleLinkLabel.ARTICLEKEYWORDS)) {
				String keyword = article.getKeyword();
				str = this.proccessLabel(labelMatcher, str, a, sb, keyword);
				
			// 信息访问次数 	
			} else if(label.equals(TitleLinkLabel.ARTICLEHITS)) {
				if(article.getId() != null) {
					int hits = article.getHits();
					String hit = String.valueOf(hits);
					str = this.proccessLabel(labelMatcher, str, a, sb, hit);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
				
			// 信息来源
			} else if(label.equals(TitleLinkLabel.ARTICLESORCE)) {
				String infoSource = article.getInfoSource();
				str = this.proccessLabel(labelMatcher, str, a, sb, infoSource);
				
			// 文章评论
			} else if(label.equals(ArticleTextLabel.ARTICLE_COMMENT)) {
				str = this.proccessArticleComment(labelMatcher, str, a, sb, articleComment, articleCommentPic);

			// 信息创建时间
			} else if(label.equals(TitleLinkLabel.ARTICLECREATEDATE)) {
				Date createTime = article.getCreateTime();
				if(createTime != null) {
					String date = DateUtil.toString(createTime);
					str = this.proccessLabel(labelMatcher, str, a, sb, date);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
				
			// 信息显示时间	
			} else if(label.equals(TitleLinkLabel.ARTICLEDEPLOYTIME)) {
				Date displayTime = article.getDisplayTime();
				if(displayTime != null) {	
					String date = DateUtil.toString(displayTime);
					str = this.proccessLabel(labelMatcher, str, a, sb, date);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
			
			// 两位年
			} else if(label.equals(TitleLinkLabel.YEAR2)) {
				Date date = article.getDisplayTime();
				if(date != null) {
					String strdate = DateUtil.toString(date, "yy");
					str = this.proccessLabel(labelMatcher, str, a, sb, strdate);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
				
			// 四位年					
			} else if(label.equals(TitleLinkLabel.YEAR4)) {
				Date date = article.getDisplayTime();
				if(date != null){
					String strdate = DateUtil.toString(date, "yyyy");
					str = this.proccessLabel(labelMatcher, str, a, sb, strdate);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
				
			// 一位月
			} else if(label.equals(TitleLinkLabel.MONTH1)) {
				Date date = article.getDisplayTime();
				if(date != null) {
					String strdate = DateUtil.toString(date,"yyyy-MM-dd");							
					String str1[] = strdate.split("-");	
					String month = "";
					month = String.valueOf(StringUtil.parseInt(str1[1]));
					str = this.proccessLabel(labelMatcher, str, a, sb, month);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
				
			// 两位月
			} else if(label.equals(TitleLinkLabel.MONTH2)) {
				Date date = article.getDisplayTime();
				if(date != null) {
					String strdate = DateUtil.toString(date, "yyyy-MM-dd");	
					String str1[] = strdate.split("-");	
					String month = "";
					month = str1[1];
					str = this.proccessLabel(labelMatcher, str, a, sb, month);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}	
			
			// 一位日
			} else if(label.equals(TitleLinkLabel.DAY1)) {
				Date date = article.getDisplayTime();
				if(date != null) {
					String strdate = DateUtil.toString(date,DateUtil.FORMAT_DATE_1);
					String str1[] = strdate.split("-");	
					String value = String.valueOf(StringUtil.parseInt(str1[2]));
					str = this.proccessLabel(labelMatcher, str, a, sb, value);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
				
			// 两位日
			}else if(label.equals(TitleLinkLabel.DAY2)) {
				Date date = article.getDisplayTime();
				if(date != null) {
					String strdate = DateUtil.toString(date,DateUtil.FORMAT_DATE_1);
					String str1[] = strdate.split("-");	
					String day = "";
					day = str1[2];
					str = this.proccessLabel(labelMatcher, str, a, sb, day);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
				
			// 时	
			}else if(label.equals(TitleLinkLabel.HOUR)) {
				Date date = article.getDisplayTime();
				if(date != null) {
					String strdate = DateUtil.toString(date, "HH");	
					str = this.proccessLabel(labelMatcher, str, a, sb, strdate);
				}else{
					labelMatcher.appendReplacement(sb, "");
				}
				
			// 分
			}else if(label.equals(TitleLinkLabel.MINUTE)) {
				Date date = article.getDisplayTime();
				if(date != null) {
					String strdate = DateUtil.toString(date, "mm");	
					str = this.proccessLabel(labelMatcher, str, a, sb, strdate);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
				
			// 秒
			}else if(label.equals(TitleLinkLabel.SECOND)) {
				Date date = article.getDisplayTime();
				if(date != null) {
					String strdate = DateUtil.toString(date, "ss");	
					str = this.proccessLabel(labelMatcher, str, a, sb, strdate);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
				
			// 单元标签	
			} else {
				String other = "<!--(.*)-->";
				Pattern p = Pattern.compile(other);
				Matcher m = p.matcher(label);
				String getOtherValue = "";
				Object obj = null;
				if(m.find()) {				 	
					getOtherValue = m.group(1);
					obj = BeanUtil.getFieldValue(article, "com.j2ee.cms.biz.articlemanager.domain.Article", getOtherValue);
					if(obj != null){
						// 是日期
						if (obj instanceof Date) {
							obj = DateUtil.toString((Date)obj, "yyyy-MM-dd HH:mm:ss");
							String data = String.valueOf(obj);
							str = this.proccessLabel(labelMatcher, str, a, sb, data);
						} else {
							String data = String.valueOf(obj);
							// 是图片、附件、媒体 
							if(getOtherValue.startsWith("pic")
									|| getOtherValue.startsWith("attach")
									|| getOtherValue.startsWith("media")) {
								if(!StringUtil.isEmpty(data) && !data.equals("null")) {
									data = SiteResource.getUrl(data, true);
								}
							
							// 要分页的情况
							} else if(getOtherValue.startsWith("textArea")) {
								String articleId = article.getId();
								if(!str.equals("null")) {
									data = this.getArticlePage(str, siteId, articleId);
								} 
							} else {
								if(data.equals("false") || data.equals(false)) {
									if(a == 0) {
										data = "";
									} else {
										data = "否";
									}
								} else if(data.equals("true") || data.equals(true)) {
									data = "是";
								} 
							}
							str = this.proccessLabel(labelMatcher, str, a, sb, data);
						}
					}
				 
					
					
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
			}
			a++;
		}
		if(!StringUtil.isEmpty(str)) {
			labelMatcher.appendTail(sb);
			return sb.toString();
		} else {
			return "";
		}
	}
	
	/**
	 * 处理labelMatcher为空和不为空的情况
	 * @param labelMatcher
	 * @param str
	 * @param a
	 * @param sb
	 * @param data
	 */
	private String proccessLabel(Matcher labelMatcher, String str, int a, StringBuffer sb, String data) {
		if(!StringUtil.isEmpty(data) && !data.equals("null")) {	 
			if(a == 0) {
				str = data;
				labelMatcher.appendReplacement(sb, "");
			} else {
				if(!StringUtil.isEmpty(str)) {
					labelMatcher.appendReplacement(sb, data);		
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
			}
		} else {
			labelMatcher.appendReplacement(sb, "");
		}
		return str;
	}
	
	/**
	 * 处理文章评论是否是图片
	 * @param labelMatcher
	 * @param str
	 * @param a
	 * @param sb
	 * @param articleComment
	 * @param articleCommentPic
	 * @return
	 */
	private String proccessArticleComment(Matcher labelMatcher, String str, int a, StringBuffer sb, String articleComment, String articleCommentPic) {
		if(!StringUtil.isEmpty(articleComment)) {
			int isPic = StringUtil.parseInt(articleCommentPic);
			// 文章评论是否是图片	
			if(isPic == 1) {
				String img = "<img  src=\""+ SiteResource.getUrl(articleComment, true)  +"\"/>";
				str = this.proccessLabel(labelMatcher, str, a, sb, img);
			} else if(isPic == 0) {
				String span = articleComment;
				str = this.proccessLabel(labelMatcher, str, a, sb, span);
			} 
		} else {
			labelMatcher.appendReplacement(sb, "");
		}
		return str;
	}
	
	/**
	 * 查找标题连接html代码
	 * @param unitId
	 * @return
	 */
	private String findHtmlPath(String unitId){
		TemplateUnit templateUnit = templateUnitDao.getAndNonClear(unitId);
		String html = templateUnit.getHtml();
		return html;
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
    private String createXml(String fileName, String[] tmp, String articleId, String siteId, String divId, String localPath) {
    	int pageCount = tmp.length;
    	Document document = DocumentHelper.createDocument();    
		Element rootElement = document.addElement("j2ee.cms"); 
		Element content = rootElement.addElement("content-"+articleId);
		Element pages = content.addElement("pages");
		Element page = pages.addElement("page");
		StringBuffer sb = new StringBuffer();
    	for(int i = 0; i < pageCount; i++) {
        	String name = "page" + (i+1);
        	Element cpage = page.addElement(name);
        	// 每页显示的号码(上一页 1 2 3 下一页)
        	String pageFlag = "";
        	// 第一页
        	if(i == 0 && pageCount > 1) {
        		pageFlag = "&nbsp;&nbsp;1";
        		int Tpage = 0;
        		for(int j = 1; j < pageCount; j++) {
        			Tpage = j + 1;
        			pageFlag += "&nbsp;&nbsp;<a href=\"#\" style=\"text-decoration:underline\" onclick='replaceHtmlContentFromXmlFile(\""+ fileName +"\", \""+ articleId +"\", \""+Tpage+"\", \""+divId+"\", \""+siteId+"\", \""+GlobalConfig.appName+"\")'>" + Tpage + "</a> "; 
        		}
        		pageFlag += "&nbsp;&nbsp;<a href=\"#\" onclick='replaceHtmlContentFromXmlFile(\""+ fileName +"\", \""+ articleId +"\", \""+2+"\", \""+divId+"\", \""+siteId+"\", \""+GlobalConfig.appName+"\")'>下一页</a> ";
        		sb.append(tmp[i]+"<p align=\"center\">"+pageFlag+"</p>");
        	
        	// 中间页	
        	} else if(i > 0 && i < pageCount - 1) {
        		pageFlag = "<p align=\"center\">&nbsp;&nbsp;<a href=\"#\" onclick='replaceHtmlContentFromXmlFile(\""+ fileName +"\", \""+ articleId +"\", \""+ i +"\", \""+divId+"\", \""+siteId+"\", \""+GlobalConfig.appName+"\")'>上一页</a> ";
        		int Tpage = 0;
        		for(int j = 0; j < pageCount; j++) {
        			Tpage = j + 1;
        			// 如果是当前页
        			if(Tpage != i+1) {
        				pageFlag += "&nbsp;&nbsp;<a href=\"#\" style=\"text-decoration:underline\" onclick='replaceHtmlContentFromXmlFile(\""+ fileName +"\", \""+ articleId +"\", \""+Tpage+"\", \""+divId+"\", \""+siteId+"\", \""+GlobalConfig.appName+"\")'>" + Tpage + "</a> "; 
        			} else {
        				pageFlag += "&nbsp;&nbsp;" + Tpage + "&nbsp;&nbsp;";
        			}
        		}
        		pageFlag += "&nbsp;&nbsp;<a href=\"#\" onclick='replaceHtmlContentFromXmlFile(\""+ fileName +"\", \""+ articleId +"\", \""+ (i+2) +"\", \""+divId+"\", \""+siteId+"\", \""+GlobalConfig.appName+"\")'>下一页</a></p> ";
        		
        	// 最后一页	
        	} else {
        		pageFlag = "<p align=\"center\">&nbsp;&nbsp;<a href=\"#\" onclick='replaceHtmlContentFromXmlFile(\""+ fileName +"\", \""+ articleId +"\", \""+ (pageCount-1) +"\", \""+divId+"\", \""+siteId+"\", \""+GlobalConfig.appName+"\")'>上一页</a> ";
        		for(int j = 0; j < pageCount-1; j++) {
        			int Tpage = j + 1;
        			pageFlag += "&nbsp;&nbsp;<a href=\"#\" style=\"text-decoration:underline\" onclick='replaceHtmlContentFromXmlFile(\""+ fileName +"\", \""+ articleId+ "\", \""+Tpage+"\", \""+divId+"\", \""+siteId+"\", \""+GlobalConfig.appName+"\")'>" + Tpage + "</a> "; 
        		}
        		pageFlag += "&nbsp;&nbsp;" + pageCount+"</p>";
        	}
        	String pageContent = tmp[i] + pageFlag; 
        	cpage.addCDATA(pageContent);
        }
		try {
			OutputFormat of = OutputFormat.createPrettyPrint();
			of.setEncoding("UTF-8");
			if(FileUtil.isExist(localPath)) {
				FileUtil.delete(localPath);
			}
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
	 * @return the userDao
	 */
	public UserDao getUserDao() {
		return userDao;
	}

	/**
	 * @param userDao the userDao to set
	 */
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}
