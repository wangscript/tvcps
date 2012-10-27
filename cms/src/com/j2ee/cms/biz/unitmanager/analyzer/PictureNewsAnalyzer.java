/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.unitmanager.analyzer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.j2ee.cms.biz.articlemanager.dao.ArticleAttributeDao;
import com.j2ee.cms.biz.articlemanager.dao.ArticleDao;
import com.j2ee.cms.biz.articlemanager.domain.Article;
import com.j2ee.cms.biz.columnmanager.dao.ColumnDao;
import com.j2ee.cms.biz.columnmanager.domain.Column;
import com.j2ee.cms.biz.documentmanager.dao.CategoryDao;
import com.j2ee.cms.biz.documentmanager.dao.DocumentDao;
import com.j2ee.cms.biz.sitemanager.dao.SiteDao;
import com.j2ee.cms.biz.sitemanager.domain.Site;
import com.j2ee.cms.biz.templatemanager.dao.TemplateInstanceDao;
import com.j2ee.cms.biz.templatemanager.dao.TemplateUnitDao;
import com.j2ee.cms.biz.templatemanager.domain.TemplateUnit;
import com.j2ee.cms.biz.unitmanager.label.ColumnLinkLabel;
import com.j2ee.cms.biz.unitmanager.label.CommonLabel;
import com.j2ee.cms.biz.unitmanager.label.TitleLinkLabel;
import com.j2ee.cms.biz.usermanager.dao.UserDao;
import com.j2ee.cms.biz.usermanager.domain.User;
import com.j2ee.cms.sys.GlobalConfig;
import com.j2ee.cms.sys.SiteResource;
import com.j2ee.cms.common.core.util.BeanUtil;
import com.j2ee.cms.common.core.util.CollectionUtil;
import com.j2ee.cms.common.core.util.DateUtil;
import com.j2ee.cms.common.core.util.IDFactory;
import com.j2ee.cms.common.core.util.StringUtil;
import com.j2ee.cms.common.core.util.XmlUtil;

/**
 * 
 * <p>标题: —— 图片新闻解析类</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 模板单元管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-6-9 上午09:39:41
 * @history（历次修订内容、修订人、修订时间等）
 */
public class PictureNewsAnalyzer implements TemplateUnitAnalyzer {
	/** 日志 */
	private static final Logger log = Logger.getLogger(PictureNewsAnalyzer.class);
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
	/** 注入文档的dao **/
	private DocumentDao documentDao;
	/** 注入类别dao **/
	private CategoryDao categoryDao;
	/** 网站dao */
	private SiteDao siteDao;
	/** 用户dao*/
	private UserDao userDao;
	/** 全局变量用于控制更多是否显示 */
	private boolean displayMore = false;
	
	public String getHtml(String unitId, String articleId, String columnId, String siteId,  Map<String,String> commonLabel) {
		String htmlCode = this.findHtmlPath(unitId);
		try{
			htmlCode = StringUtil.trimEnter(htmlCode);
			boolean flag = true;
			if(StringUtil.contains(htmlCode, ".swf")){
				flag = false;
			}
			StringBuffer sb = new StringBuffer();
			TemplateUnit unit = templateUnitDao.getAndClear(unitId);
			String configFilePath = unit.getConfigFile();
			//获取到当前模板实例的xml配置文件路径
			String filePath = GlobalConfig.appRealPath + configFilePath;
			String newColumnId = this.getColumnId(columnId, siteId, filePath);
			String reg = "/release/pictureNews.swf";
			Pattern p = Pattern.compile(reg);
			Matcher m = p.matcher(htmlCode);
			StringBuffer sb5 = new StringBuffer();
			if(m.find()){
				m.appendReplacement(sb5, "/"+GlobalConfig.appName+reg);
			}
			m.appendTail(sb5);
			htmlCode = sb5.toString();
			if(!StringUtil.isEmpty(newColumnId) && !newColumnId.equals("0")) {
				XmlUtil xmlUtil = XmlUtil.getInstance(filePath);
				//信息起始
				int start = StringUtil.parseInt(String.valueOf(xmlUtil.getNodeText("j2ee.cms/picture-news/start")));
				//列
				int col = StringUtil.parseInt(String.valueOf(xmlUtil.getNodeText("j2ee.cms/picture-news/col")));
				//行
				int row = StringUtil.parseInt(String.valueOf(xmlUtil.getNodeText("j2ee.cms/picture-news/row")));
				//一共多少条记录
				int count = col * row;
				//根据栏目ID查询文章
				List articleList1 = new ArrayList();
				articleList1 = articleDao.findByNamedQuery("findAuditedArticlesByColumnId", "columnId", newColumnId, start, count+start);
				int size1 = articleList1.size();
				articleList1 = articleDao.findByNamedQuery("findAuditedArticlesByColumnId", "columnId", newColumnId);
				int size2 = articleList1.size();
				if(size1 != size2) {
					displayMore = true;
				} else {
					displayMore = false;
				}
			//	articleDao.clearCache();
			}
			
			if(!StringUtil.isEmpty(newColumnId) && !newColumnId.equals("0")) {
				/*sb.append("<script>var curDate = new Date(); var curYear =curDate.getFullYear(); var curMonth = curDate.getMonth()+1;" +
				"var curDay = curDate.getDate(); var date = curYear+\"-\"+curMonth+\"-\"+curDay;</script>");
	*/
				Pattern forPattern = Pattern.compile(CommonLabel.REGEX_FOR);
				Matcher forMatcher = forPattern.matcher(htmlCode);
				String str = htmlCode;
				// 先处理所有的for
				if(forMatcher.find()) {
					str = this.getForData(forMatcher.group(), unitId, newColumnId, siteId, flag);
					forMatcher.appendReplacement(sb, str);
				}
				forMatcher.appendTail(sb);
				log.debug("aftrer for =========="+sb.toString());
				Pattern ifPattern = Pattern.compile(CommonLabel.IF);
				Matcher ifMatcher = ifPattern.matcher(sb.toString());
				StringBuffer sb1 = new StringBuffer();
				
				//根据栏目ID查询文章
				List<Article> articleList = new ArrayList<Article>();
				articleList = articleDao.findByNamedQuery("findAuditedArticlesByColumnId", "columnId", newColumnId);
				Article article = new Article();
				if(articleList != null && articleList.size() > 0) {
					article = articleList.get(0);
				}
				// 处理完for的情况后处理有if的情况 
				if(ifMatcher.find()) {
					str = this.getData(ifMatcher.group(), unitId, newColumnId, article, siteId);
					ifMatcher.appendReplacement(sb1, str);
				}
				ifMatcher.appendTail(sb1);
				log.debug("after if============="+sb1.toString());
				
				Pattern labelPattern = Pattern.compile(CommonLabel.REGEX_LABEL);
				Matcher labelMatcher = labelPattern.matcher(sb1.toString());
				// 处理完for和if后处理符合正则的标签
			 	if(labelMatcher.find()) {
					str = this.setData(sb1.toString(), unitId, newColumnId, article, siteId);
					log.debug("result=============="+str);
					return str;
				} else {
					log.debug("result1=============="+sb1.toString());
					return sb1.toString();
				}
			 
			} else {
				String str = htmlCode;
				Pattern forPattern = Pattern.compile(CommonLabel.REGEX_FOR);
				Matcher matcher = forPattern.matcher(str);
				// 去掉for
				StringBuffer sb2 = new StringBuffer();
				if(matcher.find()) {
					matcher.appendReplacement(sb2, "");
				}
				matcher.appendTail(sb2);
				
				Pattern ifPattern = Pattern.compile(CommonLabel.IF);
				Matcher ifMatcher = ifPattern.matcher(sb2);
				StringBuffer sb1 = new StringBuffer();
				Article article = new Article();
				article.setCreateTime(null);
				article.setDisplayTime(null);
				// 处理完for的情况后处理有if的情况
				if(ifMatcher.find()) {
					str = this.getData(ifMatcher.group(), unitId, newColumnId, article, siteId);
					ifMatcher.appendReplacement(sb1, str);
				}
				ifMatcher.appendTail(sb1);
				log.debug("after if============="+sb1.toString());
				
				Pattern labelPattern = Pattern.compile(CommonLabel.REGEX_LABEL);
				Matcher labelMatcher = labelPattern.matcher(sb1.toString());
				// 处理完for和if后处理符合正则的标签
				 if(labelMatcher.find()) {
					str = this.setData(sb1.toString(), unitId, newColumnId, article, siteId);
					log.debug("result=============="+str);
					return str;
				} else {
					log.debug("result1=============="+sb1.toString());
					return sb1.toString();
				}
				
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error(e);
			return "图片新闻设置错误";
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
	private String getForData(String src, String unitId, String columnId, String siteId, boolean flag) {
		// 判断是否是图片新闻flash滚动
		
		TemplateUnit unit = templateUnitDao.getAndClear(unitId);
		String configFilePath = unit.getConfigFile();
		//获取到当前模板实例的xml配置文件路径
		String filePath = GlobalConfig.appRealPath + configFilePath;
		XmlUtil xmlUtil = XmlUtil.getInstance(filePath);
		
		//信息起始
		int start = StringUtil.parseInt(String.valueOf(xmlUtil.getNodeText("j2ee.cms/picture-news/start")));
		//列
		int col = StringUtil.parseInt(String.valueOf(xmlUtil.getNodeText("j2ee.cms/picture-news/col")));
		//行
		int row = StringUtil.parseInt(String.valueOf(xmlUtil.getNodeText("j2ee.cms/picture-news/row")));
		//标题后缀
		String titleEnd = String.valueOf(xmlUtil.getNodeText("j2ee.cms/picture-news/titleEnd"));
		//标题前缀
		String titleHead = String.valueOf(xmlUtil.getNodeText("j2ee.cms/picture-news/titleHead"));
		//标题字数
		String titleLimit = String.valueOf(xmlUtil.getNodeText("j2ee.cms/picture-news/titleLimit"));
		//更多
		String moreLink = String.valueOf(xmlUtil.getNodeText("j2ee.cms/picture-news/moreLink"));
		//一共多少条记录
		int count = col * row;	
		//根据栏目ID查询文章
		List articleList = new ArrayList();
		articleList = articleDao.findByNamedQuery("findAuditedArticlesByColumnId", "columnId", columnId, start, count+start);	
		StringBuffer sb = new StringBuffer();
		StringBuffer sb3 = new StringBuffer();
		Pattern forPattern = Pattern.compile(CommonLabel.REGEX_FOR);
		Matcher forMatcher = forPattern.matcher(src);	
		int m = 0;
		
		//给奇行偶行使用
		int z = 1;
		// 1、匹配for, <dddd><!--for--><sapn id=""><!--articletitle--><!--articleurl--></sapn><!--/for--><2222>
		while (forMatcher.find()) {
			Matcher m1 = forPattern.matcher(forMatcher.group());
			if(m1.find()) {
				// 2、匹配for, <!--for--><sapn id=""><!--articletitle--><!--articleurl--></sapn><!--/for-->
				String str = "";
				if(articleList != null && articleList.size() > 0){					
					for(int i = 0 ; i < articleList.size() ; i++){		
						StringBuffer sb4 = new StringBuffer();
						Article article = (Article) articleList.get(i);				
						if(m == 0) {
//							sb4.append("<table>");
						}
						double xx = (i)%(col);				
						if(xx == 0){
							if(flag){
								sb4.append("<tr><td>");
							}
							z++;
						}else if(i != 0){						
							if(flag){
								sb4.append("<td>");
							}
						}
						//sb4 ======   第一次：<div><ul><li><!--articletitle--><!--articleurl-->
						//             第二次: <li><!--articletitle--><!--articleurl-->
						sb4.append(forMatcher.group(1));
						str = this.sub(sb4.toString(), unitId, columnId, article, siteId);
						double yy = (i+col+1)%col;
						if(yy == 0){
							if(flag){
								str = str +"</td></tr>";
							}
						}else{
							if(flag){
								str = str +"</tr>";
							}
						}						
						if(i == (articleList.size()-1)){
//							str = str + "</table>";
						}
						m++;
						// sb3 ========== 第一次： <div><ul><li>articletitle articleurl</li>
						//                第二次：  <div><ul><li>articletitle articleurl</li><li>columnName columnLink</li></ul>
						sb3.append(str);
					}
				}
				sb.append(sb3.toString());
			}
		}
		return sb.toString();
	}
	
	/**
	 * 解析标签（替换标签里面的内容）
	 * @param src
	 * @param unitId
	 * @param columnId
	 * @param article
	 * @return
	 */
	public String sub(String src, String unitId, String columnId, Article article, String siteId) {	
		StringBuffer sb = new StringBuffer();
		TemplateUnit unit = templateUnitDao.getAndClear(unitId);
		Site site = siteDao.getAndClear(siteId);
		Column column = new Column();
		if(columnId != null) {
			 column = columnDao.getAndClear(columnId);
		} 
		String configFilePath = unit.getConfigFile();
		//获取到当前模板实例的xml配置文件路径
		String filePath = GlobalConfig.appRealPath+ configFilePath;
		XmlUtil xmlUtil = XmlUtil.getInstance(filePath);
		//标题后缀
		String titleEnd = String.valueOf(xmlUtil.getNodeText("j2ee.cms/picture-news/titleEnd"));
		//标题前缀
		String titleHead = String.valueOf(xmlUtil.getNodeText("j2ee.cms/picture-news/titleHead"));
		//标题字数
		String titleLimit = String.valueOf(xmlUtil.getNodeText("j2ee.cms/picture-news/titleLimit"));
		//列
		int col = StringUtil.parseInt(String.valueOf(xmlUtil.getNodeText("j2ee.cms/picture-news/col")));
		//行
		int row = StringUtil.parseInt(String.valueOf(xmlUtil.getNodeText("j2ee.cms/picture-news/row")));
		//前缀是否图片
		String headPic = String.valueOf(xmlUtil.getNodeText("j2ee.cms/picture-news/titleHeadPic"));
		//后缀是否图片
		String endPic = String.valueOf(xmlUtil.getNodeText("j2ee.cms/picture-news/titleEndPic"));

		// 处理for里面的if
		Pattern ifPattern = Pattern.compile(CommonLabel.IF);
		Matcher ifMatcher = ifPattern.matcher(src);
		String ifData = "";
		StringBuffer sb1 = new StringBuffer();
		if(ifMatcher.find()) {
			ifData = this.getData(ifMatcher.group(), unitId, columnId, article, siteId);
			ifMatcher.appendReplacement(sb1, ifData);
		}
		ifMatcher.appendTail(sb1);
		// 处理for
		String label = "";	
		Pattern labelPatttern = Pattern.compile(CommonLabel.REGEX_LABEL);	
		Matcher labelMatcher = labelPatttern.matcher(sb1.toString());
		
		// 处理for里面的标签
		while (labelMatcher.find() ) {
			label = labelMatcher.group();									
			if(label.equals(TitleLinkLabel.ARTICLEARTICLEAUTHOR)){	
				//信息作者 
				String author = article.getAuthor();				
				this.proccessSubLabel(labelMatcher, sb, author);
				
				// 应用名	
			} else if(label.equals(CommonLabel.APP_NAME)){
				String appName = GlobalConfig.appName;
				labelMatcher.appendReplacement(sb, appName);
			
				// 网站id	
			} else if(label.equals(CommonLabel.SITE_ID)){
				labelMatcher.appendReplacement(sb, siteId);
				
			}else if(label.equals(TitleLinkLabel.ARTICLEAUDITOR)){
				// 文章审核人					
				User auditor = article.getAuditor();
				if(auditor != null) {
					String auditorId = auditor.getId();
					auditor = userDao.getAndClear(auditorId);
					String strauditor = auditor.getName();
					this.proccessSubLabel(labelMatcher, sb, strauditor);
				}else {
					labelMatcher.appendReplacement(sb, "");
				}
				
			} else if(label.equals(TitleLinkLabel.ARTICLEEDITOR)) {
				// 信息录入人
				User user = article.getCreator();
				if(user != null) {
					String userid = user.getId();
					user = userDao.getAndClear(userid);
					String creatorName = user.getName();
					this.proccessSubLabel(labelMatcher, sb, creatorName);
				}else {
					labelMatcher.appendReplacement(sb, "");
				}	
				
			}else if(label.equals(TitleLinkLabel.ARTICLEBRIEF)){
				//信息摘要
				String brief = article.getBrief();
				this.proccessSubLabel(labelMatcher, sb, brief);
				
			}else if(label.equals(TitleLinkLabel.ARTICLECREATEDATE)){
				//信息创建时间
				Date createTime = article.getCreateTime();
				String time = "";
				if(createTime != null) {
					time = DateUtil.toString(createTime, "yyyy-MM-dd HH:mm:ss");
				}
				this.proccessSubLabel(labelMatcher, sb, time);
							
			}else if(label.equals(TitleLinkLabel.ARTICLEDEPLOYTIME)){
				//信息显示时间
				Date displayTime = article.getDisplayTime();
				String time = "";
				if(displayTime != null) {
					time = DateUtil.toString(displayTime, "yyyy-MM-dd HH:mm:ss");
				}
				this.proccessSubLabel(labelMatcher, sb, time);
				
			}else if(label.equals(TitleLinkLabel.ARTICLEEDITOR)){
				//根据栏目ID查询文章		
				User user = article.getCreator();
				if(user != null) {
					String creatorName = user.getName();
					this.proccessSubLabel(labelMatcher, sb, creatorName);
				}else {
					labelMatcher.appendReplacement(sb, "");
				}
				
			}else if(label.equals(TitleLinkLabel.ARTICLEENDER)){
				if(article.getId() != null) {
					//标题后缀						
					String titleEndValidity = String.valueOf(xmlUtil.getNodeText("j2ee.cms/picture-news/titleEndValidity"));
					if(!StringUtil.isEmpty(titleEnd)) {
						String newdate = DateUtil.getAddDaysDateFormat(DateUtil.toStringTrim(new Date(), "yyyy-MM-dd"), StringUtil.parseInt(titleEndValidity));
						int isSuffixPic = StringUtil.parseInt(endPic);
						String id = IDFactory.getId();
						// 后缀是否是图片	
						if(isSuffixPic == 1) {
							if(StringUtil.parseInt(titleEndValidity) >= 0) {
								labelMatcher.appendReplacement(sb, "<img id=\""+ id +"\" src=\""+ SiteResource.getUrl(titleEnd, true) +"\" style=\"display:none\"/> <script>if(checkDate('" + newdate + "')){ document.getElementById(\""+ id +"\").style.display=\"\";}</script>");
							} else {
								labelMatcher.appendReplacement(sb, "<img id=\""+ id +"\" src=\""+ SiteResource.getUrl(titleEnd, true) +"\"/>");
							}
						} else if(isSuffixPic == 0) {
							if(StringUtil.parseInt(titleEndValidity) >= 0) {
								labelMatcher.appendReplacement(sb, "<span id=\""+ id +"\" style=\"display:none\">"+ titleEnd +"</span> <script>if(checkDate('" + newdate + "')){ document.getElementById(\""+ id +"\").style.display=\"\";}</script> ");
							} else {
								labelMatcher.appendReplacement(sb, titleEnd);
							}
						} else {
							labelMatcher.appendReplacement(sb, "");
						}
					} else {
						labelMatcher.appendReplacement(sb, "");
					}
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
				
			}else if(label.equals(TitleLinkLabel.ARTICLEHEADER)){
				if(article.getId() != null) {
					// 标题前缀
					String titleHeadValidity = String.valueOf(xmlUtil.getNodeText("j2ee.cms/picture-news/titleHeadValidity"));
					if(!StringUtil.isEmpty(titleHead)) {
						String newdate = DateUtil.getAddDaysDateFormat(DateUtil.toStringTrim(new Date(), "yyyy-MM-dd"), StringUtil.parseInt(titleHeadValidity));
						int isprefixPic = StringUtil.parseInt(headPic);
						String id = IDFactory.getId();
						// 前缀是否是图片	
						if(isprefixPic == 1) {
							if(StringUtil.parseInt(titleHeadValidity) >= 0) {
								labelMatcher.appendReplacement(sb, "<img id=\""+ id +"\" src=\""+ SiteResource.getUrl(titleHead, true) +"\" style=\"display:none\"/> <script>if(checkDate('" + newdate + "')){ document.getElementById(\""+ id +"\").style.display=\"\";}</script>");
							} else {
								labelMatcher.appendReplacement(sb, "<img id=\""+ id +"\" src=\""+ SiteResource.getUrl(titleHead, true) +"\"/>");
							}
						} else if(isprefixPic == 0) {
							if(StringUtil.parseInt(titleHeadValidity) >= 0) {
								labelMatcher.appendReplacement(sb, "<span id=\""+ id +"\" style=\"display:none\">"+ titleHead +"</span> <script>if(checkDate('" + newdate + "')){ document.getElementById(\""+ id +"\").style.display=\"\";}</script> ");
							} else {
								labelMatcher.appendReplacement(sb, titleHead);
							}
						} else {
							labelMatcher.appendReplacement(sb, "");
						}
					} else {
						labelMatcher.appendReplacement(sb, "");
					}
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
				
			}else if(label.equals(TitleLinkLabel.ARTICLEHITS)){
				//信息访问次数 
				if(article.getId() != null) {
					int hits = article.getHits();
					String hit = String.valueOf(hits);
					this.proccessSubLabel(labelMatcher, sb, hit);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
				
			}else if(label.equals(TitleLinkLabel.ARTICLEKEYWORDS)){
				//信息关键字 
				String keyword = article.getKeyword();
				this.proccessSubLabel(labelMatcher, sb, keyword);
				
			}else if(label.equals(TitleLinkLabel.ARTICLELINKTITLE)){
				//链接标题
		/*		String title = article.getTitle();
				
				if(title != null) {
					labelMatcher.appendReplacement(sb, title);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}*/
				String url = article.getUrl();
				if(url != null) {
					url = SiteResource.getUrl(url, true);
					labelMatcher.appendReplacement(sb, url);
				} else {					
					labelMatcher.appendReplacement(sb, "");
				}	
			}else if(label.equals(TitleLinkLabel.ARTICLELINKTITLESHORT)){
				//链接标题(缩) 
			/*	String title = article.getTitle();
				if(title != null){
					if(StringUtil.parseInt(titleLimit) > 0) { 
						if(title.length() >= StringUtil.parseInt(titleLimit)){
							title = title.substring(0, StringUtil.parseInt(titleLimit)) + "...";
						}	
					}
					labelMatcher.appendReplacement(sb, title);
				}else{
					labelMatcher.appendReplacement(sb,"");
				}	*/
				
				String url = article.getUrl();
				if(url != null) {
					url = SiteResource.getUrl(url, true);
					labelMatcher.appendReplacement(sb, url);
				} else {					
					labelMatcher.appendReplacement(sb, "");
				}
				
			}else if(label.equals(TitleLinkLabel.ARTICLEQUOTETITLE)){
				//引题
				String leadingTitle = article.getLeadingTitle();
				this.proccessSubLabel(labelMatcher, sb, leadingTitle);
				
			}else if(label.equals(TitleLinkLabel.ARTICLEQUOTETITLESHORT)){
				//引题(缩) 
				String leadingTitle = article.getLeadingTitle();
				if(leadingTitle != null){
					if(StringUtil.parseInt(titleLimit) > 0) { 
						if(leadingTitle.length() >= StringUtil.parseInt(titleLimit)){
							leadingTitle = leadingTitle.substring(0, StringUtil.parseInt(titleLimit)) + "...";
						} 	
					}
					labelMatcher.appendReplacement(sb, leadingTitle);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
				
			}else if(label.equals(TitleLinkLabel.ARTICLESORCE)){
				//信息来源 
				String infoSource = article.getInfoSource();
				this.proccessSubLabel(labelMatcher, sb, infoSource);
				
			}else if(label.equals(TitleLinkLabel.ARTICLESUBTITLE)){
				//副标题 
				String subtitle = article.getSubtitle();
				this.proccessSubLabel(labelMatcher, sb, subtitle);
				
			}else if(label.equals(TitleLinkLabel.ARTICLESUBTITLESHORT)){
				//副标题(缩) 
				String subtitle = article.getSubtitle();
				if(subtitle != null){
					if(StringUtil.parseInt(titleLimit) > 0) { 
						if(subtitle.length() >= StringUtil.parseInt(titleLimit)){
							subtitle = subtitle.substring(0, StringUtil.parseInt(titleLimit)) + "...";
						}	
					}
					labelMatcher.appendReplacement(sb, subtitle);
				}else {
					labelMatcher.appendReplacement(sb, "");
				}
				
			}else if(label.equals(TitleLinkLabel.ARTICLETITLE)){				
				//标题				
				String title = article.getTitle();
				this.proccessSubLabel(labelMatcher, sb, title);
				
			}else if(label.equals(TitleLinkLabel.ARTICLETITLESHORT)){
				//标题(缩) 
				String title = article.getTitle();
				if(title != null){
					if(StringUtil.parseInt(titleLimit) > 0) { 
						if(title.length() >= StringUtil.parseInt(titleLimit)){
							title = title.substring(0, StringUtil.parseInt(titleLimit)) + "...";
						}	
					}
					labelMatcher.appendReplacement(sb, title);
				}else{
					labelMatcher.appendReplacement(sb, "");
				}
				
			}else if(label.equals(TitleLinkLabel.ARTICLEURL)){
				//标题链接
				String url = article.getUrl();
				if(url != null) {
					labelMatcher.appendReplacement(sb, SiteResource.getUrl(url, true));
				}else{
					labelMatcher.appendReplacement(sb, "");
				}
				
			}else if(label.equals(TitleLinkLabel.DAY1)){
				//一位日 
				Date date = article.getDisplayTime();
				if(date != null){
					String strdate = DateUtil.toString(date,DateUtil.FORMAT_DATE_1);
					String str[] = strdate.split("-");							
					labelMatcher.appendReplacement(sb, String.valueOf(StringUtil.parseInt(str[2])));
				}else{
					labelMatcher.appendReplacement(sb,"");
				}
				
			}else if(label.equals(TitleLinkLabel.DAY2)){
				//两位日 
				Date date = article.getDisplayTime();
				if(date != null){
					String strdate = DateUtil.toString(date,DateUtil.FORMAT_DATE_1);
					String str[] = strdate.split("-");	
					labelMatcher.appendReplacement(sb, str[2]);
				}else{
					labelMatcher.appendReplacement(sb,"");
				}
				
			}else if(label.equals(TitleLinkLabel.HOUR)){
				//时
				Date date = article.getDisplayTime();
				if(date != null){
					String strdate = DateUtil.toString(date,"HH");			
					labelMatcher.appendReplacement(sb, strdate);	
				}else{
					labelMatcher.appendReplacement(sb,"");
				}
				
			}else if(label.equals(TitleLinkLabel.MINUTE)){
				//分
				Date date = article.getDisplayTime();
				if(date != null) {
					String strdate = DateUtil.toString(date,"mm");			
					labelMatcher.appendReplacement(sb, strdate);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
				
			}else if(label.equals(TitleLinkLabel.SECOND)){
				//秒
				Date date = article.getDisplayTime();
				if(date != null){
					String strdate = DateUtil.toString(date,"ss");		
					labelMatcher.appendReplacement(sb, strdate);
				}else{
					labelMatcher.appendReplacement(sb, "");
				}
					
			}else if(label.equals(TitleLinkLabel.MONTH1)){
				//一位月
				Date date = article.getDisplayTime();
				if(date != null){
					String strdate = DateUtil.toString(date,"yyyy-MM-dd");							
					String str[] = strdate.split("-");							
					labelMatcher.appendReplacement(sb, String.valueOf(StringUtil.parseInt(str[1])));
				}else{
					labelMatcher.appendReplacement(sb, "");
				}
				
			}else if(label.equals(TitleLinkLabel.MONTH2)){
				//两位月 
				Date date = article.getDisplayTime();
				if(date != null){
					String strdate = DateUtil.toString(date,"yyyy-MM-dd");
					String str[] = strdate.split("-");
					labelMatcher.appendReplacement(sb, str[1]);
				}else{
					labelMatcher.appendReplacement(sb, "");
				}
				
			}else if(label.equals(TitleLinkLabel.MORELINK)){
				if(displayMore) {
					//更多内容 
					String moreLink = String.valueOf(xmlUtil.getNodeText("j2ee.cms/picture-news/moreLink"));
					//是否是图片
					String moreLinkPic = String.valueOf(xmlUtil.getNodeText("j2ee.cms/picture-news/moreLinkPic"));
					String url = column.getUrl();
					if(moreLinkPic != null && !moreLinkPic.equals("") && !moreLinkPic.equals("0")) {
						String newUrl = "";
						if(!StringUtil.isEmpty(url)) {
							newUrl = "<a href=\"" + SiteResource.getUrl(url, true) + "\"><img src=\"" + SiteResource.getUrl(moreLink, true) + "\"/></a>";
						} else {
							newUrl = "<img src=\"" + SiteResource.getUrl(moreLink, true) + "\"/>";
						}
						labelMatcher.appendReplacement(sb, newUrl);
					}else{
						if(!StringUtil.isEmpty(url)) {
							labelMatcher.appendReplacement(sb, "<a href=\""+ SiteResource.getUrl(url, true) + "\">" + moreLink + "</a>");
						} else {
							labelMatcher.appendReplacement(sb, moreLink);
						}
					}
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
				
			}else if(label.equals(TitleLinkLabel.YEAR2)){
				//两位年 
				Date date = article.getDisplayTime();
				if(date != null){
					String strdate = DateUtil.toString(date,"yy");
					labelMatcher.appendReplacement(sb, strdate);
				}else {
					labelMatcher.appendReplacement(sb, "");
				}
				
			}else if(label.equals(TitleLinkLabel.YEAR4)){
				//四位年 
				Date date = article.getDisplayTime();
				if(date != null){
					String strdate = DateUtil.toString(date,"yyyy");
					labelMatcher.appendReplacement(sb, strdate);
				}else{
					labelMatcher.appendReplacement(sb, "");
				}
				
			// 网站名称	
			}else  if(label.equals(CommonLabel.SITE_NAME)){
				// 网站名称	
				String siteName = site.getName();
				if(siteName != null ){
					labelMatcher.appendReplacement(sb, siteName);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
			}else if(label.equals(CommonLabel.SITE_LINK)) {
				// 网站链接
				String siteUrl = site.getUrl();
				if(siteUrl != null ){
					labelMatcher.appendReplacement(sb, SiteResource.getUrl(siteUrl, true));
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
			}else if(label.equals(CommonLabel.COLUMN_NAME)){
				//栏目名称
				String columnName = column.getName();
				if(columnName != null){
					labelMatcher.appendReplacement(sb,columnName );
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
			
			// 栏目链接	
			}else if(label.equals(ColumnLinkLabel.COLUMN_LINK)){
				String columnType = column.getColumnType();
				String url = "";
				// 如果是单信息栏目
				if(columnType.equals("single")){
					String columnid = column.getId();
					if(!StringUtil.isEmpty(columnid)){
						List<Article> list = articleDao.findByNamedQuery("findAuditedArticlesByColumnId", "columnId", columnid, 0, 1);
						if(!CollectionUtil.isEmpty(list)){
							url = list.get(0).getUrl();
						}
					}
				}else{
					url = column.getUrl();
				}
				if(!StringUtil.isEmpty(url)) {
					labelMatcher.appendReplacement(sb, SiteResource.getUrl(url, true));
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
				
			}else {
				String other = "<!--(.*)-->";
				Pattern p = Pattern.compile(other);
				Matcher m = p.matcher(label);
				String getOtherValue = "";
				if(article.getId() != null) {
					if(m.find()) {
						getOtherValue = m.group(1);
						Object obj = BeanUtil.getFieldValue(article, "com.j2ee.cms.biz.articlemanager.domain.Article", getOtherValue);
						if (obj instanceof Date) {
							obj = DateUtil.toString((Date)obj, "yyyy-MM-dd HH:mm:ss");
							String str = String.valueOf(obj);
							this.proccessSubLabel(labelMatcher, sb, str);
						} else {
							if(getOtherValue.startsWith("pic") || getOtherValue.startsWith("attach") || getOtherValue.startsWith("media")) {
								String str = String.valueOf(obj);
								if(!StringUtil.isEmpty(str) && !str.equals("null")) {
									str = SiteResource.getUrl(str, true);
								}
								this.proccessSubLabel(labelMatcher, sb, str);
							}  else {
								String str = String.valueOf(obj);
								if(str.equals(false) || str.equals("false")) {
									str = "否";
								} else if(str.equals(true) || str.equals("true")) {
									str = "是";
								}
								this.proccessSubLabel(labelMatcher, sb, str);
							}
						}
					}
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
			}
		}
		labelMatcher.appendTail(sb);
		return sb.toString();
	}
	
	
	private String setData(String src, String unitId, String columnId, Article article, String siteId) {	
		StringBuffer sb = new StringBuffer();
		TemplateUnit unit = templateUnitDao.getAndClear(unitId);
		Site site = siteDao.getAndClear(siteId);
		Column column = null;
		if(columnId != null && !columnId.equals("") && !columnId.equals("null") && !columnId.equals("0")) {
			 column = columnDao.getAndClear(columnId);
		} 
		String configFilePath = unit.getConfigFile();
		//获取到当前模板实例的xml配置文件路径
		String filePath = GlobalConfig.appRealPath+ configFilePath;
		XmlUtil xmlUtil = XmlUtil.getInstance(filePath);
 
		String label = "";	
		Pattern labelPatttern = Pattern.compile(CommonLabel.REGEX_LABEL);	
		log.debug("set data src ================="+src);
		Matcher labelMatcher = labelPatttern.matcher(src);
		while (labelMatcher.find() ) {
			label = labelMatcher.group();	
			//更多内容 
			if(label.equals(TitleLinkLabel.MORELINK)) {
				if(displayMore) {
					String moreLink = String.valueOf(xmlUtil.getNodeText("j2ee.cms/picture-news/moreLink"));
					//是否是图片
					String moreLinkPic = String.valueOf(xmlUtil.getNodeText("j2ee.cms/picture-news/moreLinkPic"));
					if(column != null){
						String url = column.getUrl();
						if(moreLinkPic != null && !moreLinkPic.equals("") && !moreLinkPic.equals("0")) {
							labelMatcher.appendReplacement(sb, "<a href=\""+ SiteResource.getUrl(url, true) +"\"><img src=\""+ SiteResource.getUrl(moreLink, true) +"\"/></a>");
						} else {
							labelMatcher.appendReplacement(sb, "<a href=\""+ SiteResource.getUrl(url, true) +"\">"+ moreLink +"</a>");
						}
					}
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
			
			// 应用名	
			} else if(label.equals(CommonLabel.APP_NAME)){
				String appName = GlobalConfig.appName;
				labelMatcher.appendReplacement(sb, appName);
			
			// 网站id	
			} else if(label.equals(CommonLabel.SITE_ID)){
				labelMatcher.appendReplacement(sb, siteId);
				
			// 网站名称	
			}else if(label.equals(CommonLabel.SITE_NAME)){
				// 网站名称	
				String siteName = site.getName();
				if(siteName != null ){
					labelMatcher.appendReplacement(sb, siteName);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
				
			}else if(label.equals(CommonLabel.SITE_LINK)) {
				// 网站链接
				String siteUrl = site.getUrl();
				if(siteUrl != null ){
					labelMatcher.appendReplacement(sb, SiteResource.getUrl(siteUrl, true));
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
				
			}else if(label.equals(CommonLabel.COLUMN_NAME)){
				//栏目名称
				if(column != null){
					String columnName = column.getName();
					if(columnName != null){
						labelMatcher.appendReplacement(sb,columnName);
					} else {
						labelMatcher.appendReplacement(sb, "");
					}
				}else{
					labelMatcher.appendReplacement(sb, "");
				}
				
			// 栏目链接	
			}else if(label.equals(ColumnLinkLabel.COLUMN_LINK)){
				if(column != null){
					String columnType = column.getColumnType();
					String url = "";
					// 如果是单信息栏目
					if(columnType.equals("single")){
						String columnid = column.getId();
						if(!StringUtil.isEmpty(columnid)){
							List<Article> list = articleDao.findByNamedQuery("findAuditedArticlesByColumnId", "columnId", columnid, 0, 1);
							if(!CollectionUtil.isEmpty(list)){
								url = SiteResource.getUrl(list.get(0).getUrl(), true);
							}
						}
					}else{
						url = SiteResource.getUrl(column.getUrl(), true);
					}
					labelMatcher.appendReplacement(sb, url);
				}else{
					labelMatcher.appendReplacement(sb, "");
				}
			
			} else {
				labelMatcher.appendReplacement(sb, "");
			}
		}
	    labelMatcher.appendTail(sb);
		return sb.toString();
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
	public  String getData(String ifSrc, String unitId, String columnId, Article article, String siteId) {
		String str = ifSrc;
		while(true) {
			Pattern ifPattern= Pattern.compile(CommonLabel.IF);
			Matcher ifMatcher = ifPattern.matcher(str);
			//  如果还有与<!--if--><!--else--><!--/if-->匹配的
			if(ifMatcher.find()) {
				str = getIfData(str, unitId, columnId, article,siteId);	
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
	public  String getIfData(String ifsrc, String unitId, String columnId, Article article, String siteId) {
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
					str = this.getIfData(ifStartMatcher.group(), unitId, columnId, article,siteId);	
					sb2.append(str);
					ifStartMatcher.appendReplacement(sb, sb2.toString());
				} 
			}
			if(a == 1) {
				ifStartMatcher.appendTail(sb);
				a = 2;
			}
			if(a==0) {
				String str = getIFAndElseAndIFData(ifsrc, xmlUtil, columnId, article, unitId,siteId);
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
	private String getIFAndElseAndIFData(String str, XmlUtil xmlUtil, String columnId, Article article, String unitId,String siteId) {
		Pattern p = Pattern.compile(CommonLabel.IF);
		Matcher m = p.matcher(str);
		StringBuffer sb = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		Column column = new Column();
		if(columnId != null) {
			column = columnDao.getAndClear(columnId);
		}
		if(m.find()) {
			Pattern ifStartPattern1 = Pattern.compile(CommonLabel.IF_START);
			//ifStartMatcher.group() (<!--if--><!--else--><!--/if-->)
			Matcher ifStartMatcher1 = ifStartPattern1.matcher(m.group());
			// ifStartMatcher1.group()  (<!--if--><!--else-->)
			String changeCode = "";
			if(ifStartMatcher1.find()) {
				String label = ifStartMatcher1.group(1);
				//  原来： <!--columnName--><!--columnLink-->  在 <!--if--><!--else-->之间
				//  后来: columnName columLink
				changeCode = getIfStartOrIfEnd(label, unitId, column, xmlUtil, article,siteId);
			}
			sb2.append(changeCode);
			Pattern ifEndPattern1 = Pattern.compile(CommonLabel.IF_END);
			Matcher ifEndMatcher1 = ifEndPattern1.matcher(m.group());
			if(StringUtil.isEmpty(changeCode)) {
				if(ifEndMatcher1.find()) {
					//<!--else--><!--/if-->
					String label = ifEndMatcher1.group(1);
					changeCode = sub(label, unitId, columnId, article, siteId);
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
	private String getIfStartOrIfEnd(String src, String unitId, Column column, XmlUtil xmlUtil, Article article,String siteId) {
		//标题后缀
		String titleEnd = String.valueOf(xmlUtil.getNodeText("j2ee.cms/picture-news/titleEnd"));
		//标题前缀
		String titleHead = String.valueOf(xmlUtil.getNodeText("j2ee.cms/picture-news/titleHead"));
		//标题字数
		String titleLimit = String.valueOf(xmlUtil.getNodeText("j2ee.cms/picture-news/titleLimit"));
		//前缀是否图片
		String headPic = String.valueOf(xmlUtil.getNodeText("j2ee.cms/picture-news/titleHeadPic"));
		//后缀是否图片
		String endPic = String.valueOf(xmlUtil.getNodeText("j2ee.cms/picture-news/titleEndPic"));
		Site site = siteDao.getAndClear(siteId);

		String label = "";	
		StringBuffer sb = new StringBuffer();
		Pattern labelPatttern = Pattern.compile(CommonLabel.REGEX_LABEL);	
		Matcher labelMatcher = labelPatttern.matcher(src);	
		int a = 0;
		String str = "";
		while (labelMatcher.find() ) {
			label = labelMatcher.group();	
			// 文章作者
			if(label.equals(TitleLinkLabel.ARTICLEARTICLEAUTHOR)) {	
				String author = article.getAuthor();				
				str = this.proccessLabel(labelMatcher, str, a, sb, author);
				
			// 应用名	
			} else if(label.equals(CommonLabel.APP_NAME)){
				String appName = GlobalConfig.appName;
				str = this.proccessLabel(labelMatcher, str, a, sb, appName);
			
			// 网站id	
			} else if(label.equals(CommonLabel.SITE_ID)){
				str = this.proccessLabel(labelMatcher, str, a, sb, siteId);
				
			//文章审核人
			}else if(label.equals(TitleLinkLabel.ARTICLEAUDITOR)){
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
			}else if(label.equals(TitleLinkLabel.ARTICLEEDITOR)) {
				User user = article.getCreator();
				if(user != null) {
					String id = user.getId();
					user = userDao.getAndClear(id);
					String creatorName = user.getName();
					str = this.proccessLabel(labelMatcher, str, a, sb, creatorName);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}	
				
			//信息摘要
			}else if(label.equals(TitleLinkLabel.ARTICLEBRIEF)) {
				String brief = article.getBrief();
				str = this.proccessLabel(labelMatcher, str, a, sb, brief);

			//信息创建时间
			}else if(label.equals(TitleLinkLabel.ARTICLECREATEDATE)){
				Date createTime = article.getCreateTime();
				if(createTime != null) {
					String date = DateUtil.toString(createTime);
					str = this.proccessLabel(labelMatcher, str, a, sb, date);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
				
			//信息显示时间	
			}else if(label.equals(TitleLinkLabel.ARTICLEDEPLOYTIME)){
				Date displayTime = article.getDisplayTime();
				if(displayTime != null) {	
					String date = DateUtil.toString(displayTime);
					str = this.proccessLabel(labelMatcher, str, a, sb, date);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
				
			//标题后缀
			}else if(label.equals(TitleLinkLabel.ARTICLEENDER)) {
				if(article.getId() != null) {
					String titleEndValidity = String.valueOf(xmlUtil.getNodeText("j2ee.cms/picture-news/titleEndValidity"));
					str = this.proccessTitlePrefixOrSuffix(labelMatcher, str, a, titleEndValidity, endPic, titleEnd, sb);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
				
			//标题前缀
			}else if(label.equals(TitleLinkLabel.ARTICLEHEADER)) {
				if(article.getId() != null) {
					String titleHeadValidity = String.valueOf(xmlUtil.getNodeText("j2ee.cms/picture-news/titleHeadValidity"));
					str = this.proccessTitlePrefixOrSuffix(labelMatcher, str, a, titleHeadValidity, headPic, titleHead, sb);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
				
			//更多内容
			}else if(label.equals(TitleLinkLabel.MORELINK)) {
				if(displayMore) {
					String moreLink = String.valueOf(xmlUtil.getNodeText("j2ee.cms/picture-news/moreLink"));
					//是否是图片
					String moreLinkPic = String.valueOf(xmlUtil.getNodeText("j2ee.cms/picture-news/moreLinkPic"));
					String url = column.getUrl();
					String str1 = "<a href=\""+ SiteResource.getUrl(url, true) + "\"><img src=\""+ SiteResource.getUrl(moreLink, true) +"\"/></a>";
					String str2 = "<a href=\""+ SiteResource.getUrl(url, true) + "\">"+ moreLink +"</a>";
					if(moreLinkPic != null && !moreLinkPic.equals("") && !moreLinkPic.equals("0")) {
						str = this.proccessLabel(labelMatcher, str, a, sb, str1);
					} else {
						str = this.proccessLabel(labelMatcher, str, a, sb, str2);
					}
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
				
			//信息访问次数 	
			}else if(label.equals(TitleLinkLabel.ARTICLEHITS)) {
				if(article.getId() != null) {
					int hits = article.getHits();
					String hit = String.valueOf(hits);
					str = this.proccessLabel(labelMatcher, str, a, sb, hit);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
				
			//信息关键字 	
			}else if(label.equals(TitleLinkLabel.ARTICLEKEYWORDS)) {
				String keyword = article.getKeyword();
				str = this.proccessLabel(labelMatcher, str, a, sb, keyword);

			//信息来源
			}else if(label.equals(TitleLinkLabel.ARTICLESORCE)) {
				String infoSource = article.getInfoSource();
				str = this.proccessLabel(labelMatcher, str, a, sb, infoSource);
				
			//链接标题
			}else if(label.equals(TitleLinkLabel.ARTICLELINKTITLE)) {
				String url = article.getUrl();
				if(url != null) {
					url = SiteResource.getUrl(url, true);
					str = this.proccessLabel(labelMatcher, str, a, sb, url);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}	
				
			//链接标题(缩)
			}else if(label.equals(TitleLinkLabel.ARTICLELINKTITLESHORT)) {
				String title = article.getTitle();
				String url = article.getUrl();
				if(title != null){
					if(StringUtil.parseInt(titleLimit) > 0) { 
						if(title.length() >= StringUtil.parseInt(titleLimit)) {
							title = title.substring(0, StringUtil.parseInt(titleLimit)) + "...";
						}
					}
					String value = "";
					if(!StringUtil.isEmpty(url)) {
						value = "<a href=\""+ SiteResource.getUrl(url, true) + "\">"+ title+"</a>";
					} else {
						value = title;
					}
					str = this.proccessLabel(labelMatcher, str, a, sb, value);
				}else{
					labelMatcher.appendReplacement(sb,"");
				}	
			
			//标题链接
			}else if(label.equals(TitleLinkLabel.ARTICLEURL)) {
				String url = article.getUrl();
				String link = "";
				if(!StringUtil.isEmpty(url)) {
					link = SiteResource.getUrl(url, true);
				}
				if(!link.equals("")) {
					str = this.proccessLabel(labelMatcher, str, a, sb, link);
				}else{
					labelMatcher.appendReplacement(sb, "");
				}
				
			//引题
			}else if(label.equals(TitleLinkLabel.ARTICLEQUOTETITLE)) {
				String leadingTitle = article.getLeadingTitle();
				str = this.proccessLabel(labelMatcher, str, a, sb, leadingTitle);
				
			//引题(缩) 
			}else if(label.equals(TitleLinkLabel.ARTICLEQUOTETITLESHORT)) {
				String leadingTitle = article.getLeadingTitle();
				str = this.proccessTitle(labelMatcher, str, a, sb, leadingTitle, titleLimit);
				
			//副标题
			}else if(label.equals(TitleLinkLabel.ARTICLESUBTITLE)) {
				String subtitle = article.getSubtitle();
				str = this.proccessLabel(labelMatcher, str, a, sb, subtitle);
				
			//副标题(缩)
			}else if(label.equals(TitleLinkLabel.ARTICLESUBTITLESHORT)) {
				String subtitle = article.getSubtitle();
				str = this.proccessTitle(labelMatcher, str, a, sb, subtitle, titleLimit);
				
			// 标题
			}else if(label.equals(TitleLinkLabel.ARTICLETITLE)) {				
				String title = article.getTitle();
				str = this.proccessLabel(labelMatcher, str, a, sb, title);
				
			// 标题(缩)
			}else if(label.equals(TitleLinkLabel.ARTICLETITLESHORT)) {
				String title = article.getTitle();
				str = this.proccessTitle(labelMatcher, str, a, sb, title, titleLimit);
			
			//一位日
			}else if(label.equals(TitleLinkLabel.DAY1)) {
				Date date = article.getDisplayTime();
				if(date != null) {
					String strdate = DateUtil.toString(date,DateUtil.FORMAT_DATE_1);
					String str1[] = strdate.split("-");	
					String day = String.valueOf(StringUtil.parseInt(str1[2]));
					str = this.proccessLabel(labelMatcher, str, a, sb, day);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
				
			//两位日
			}else if(label.equals(TitleLinkLabel.DAY2)) {
				Date date = article.getDisplayTime();
				if(date != null) {
					String strdate = DateUtil.toString(date,DateUtil.FORMAT_DATE_1);
					String str1[] = strdate.split("-");
					String day = str1[2];
					str = this.proccessLabel(labelMatcher, str, a, sb, day);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
				
			//时	
			}else if(label.equals(TitleLinkLabel.HOUR)) {
				Date date = article.getDisplayTime();
				if(date != null) {
					String strdate = DateUtil.toString(date, "HH");	
					str = this.proccessLabel(labelMatcher, str, a, sb, strdate);
				}else{
					labelMatcher.appendReplacement(sb, "");
				}
				
			//分
			}else if(label.equals(TitleLinkLabel.MINUTE)) {
				Date date = article.getDisplayTime();
				if(date != null) {
					String strdate = DateUtil.toString(date, "mm");	
					str = this.proccessLabel(labelMatcher, str, a, sb, strdate);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
				
			//一位月
			}else if(label.equals(TitleLinkLabel.MONTH1)) {
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
				
			//两位月
			}else if(label.equals(TitleLinkLabel.MONTH2)) {
				Date date = article.getDisplayTime();
				if(date != null) {
					String strdate = DateUtil.toString(date, "yyyy-MM-dd");	
					String[] str1 = strdate.split("-");
					String month = "";
					month = str1[1];
					str = this.proccessLabel(labelMatcher, str, a, sb, month);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
				
			//秒
			}else if(label.equals(TitleLinkLabel.SECOND)) {
				Date date = article.getDisplayTime();
				if(date != null) {
					String strdate = DateUtil.toString(date, "ss");	
					str = this.proccessLabel(labelMatcher, str, a, sb, strdate);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
				
			//两位年
			}else if(label.equals(TitleLinkLabel.YEAR2)) {
				Date date = article.getDisplayTime();
				if(date != null) {
					String strdate = DateUtil.toString(date, "yy");
					str = this.proccessLabel(labelMatcher, str, a, sb, strdate);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
				
			//四位年					
			}else if(label.equals(TitleLinkLabel.YEAR4)) {
				Date date = article.getDisplayTime();
				if(date != null){
					String strdate = DateUtil.toString(date, "yyyy");
					str = this.proccessLabel(labelMatcher, str, a, sb, strdate);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}

			// 网站名称				
			}else  if(label.equals(CommonLabel.SITE_NAME)){
				String siteName = site.getName();
				str = this.proccessLabel(labelMatcher, str, a, sb, siteName);

			// 网站链接
			}else if(label.equals(CommonLabel.SITE_LINK)) {
				String siteUrl = site.getUrl();
				if(siteUrl != null ){
					siteUrl = SiteResource.getUrl(siteUrl, true);
					str = this.proccessLabel(labelMatcher, str, a, sb, siteUrl);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
			
			// 栏目名称	
			}else if(label.equals(CommonLabel.COLUMN_NAME)) {
				String columnName = column.getName();
				str = this.proccessLabel(labelMatcher, str, a, sb, columnName);
				
			// 栏目链接	
			}else if(label.equals(ColumnLinkLabel.COLUMN_LINK)){
				String columnType = column.getColumnType();
				String url = "";
				// 如果是单信息栏目
				if(columnType.equals("single")){
					String columnid = column.getId();
					if(!StringUtil.isEmpty(columnid)){
						List<Article> list = articleDao.findByNamedQuery("findAuditedArticlesByColumnId", "columnId", columnid, 0, 1);
						if(!CollectionUtil.isEmpty(list)){
							url = SiteResource.getUrl(list.get(0).getUrl(), true);
						}
					}
				}else{
					url = SiteResource.getUrl(column.getUrl(), true);
				}
				str = this.proccessLabel(labelMatcher, str, a, sb, url);
				
			}else {
				//字段标签
				String other = "<!--(.*)-->";
				Pattern p = Pattern.compile(other);
				Matcher m = p.matcher(label);
				String getOtherValue = "";
				if(article.getId() != null) {
					if(m.find()) {
						getOtherValue = m.group(1);
						Object obj = BeanUtil.getFieldValue(article, "com.j2ee.cms.biz.articlemanager.domain.Article", getOtherValue);
						if(obj instanceof Date) {
							obj = DateUtil.toString((Date)obj, "yyyy-MM-dd HH:mm:ss");
							String tmp = String.valueOf(obj);
							str = this.proccessLabel(labelMatcher, str, a, sb, tmp);
						} else {
							if(getOtherValue.startsWith("pic") || getOtherValue.startsWith("attach") || getOtherValue.startsWith("media")) {
								String tmp = String.valueOf(obj);
								if(!StringUtil.isEmpty(tmp) && !tmp.equals("null")) {
									tmp = SiteResource.getUrl(tmp, true);
								} 
								str = this.proccessLabel(labelMatcher, str, a, sb, tmp);
							} else {
								String tmp = String.valueOf(obj);
								if(tmp.equals(false) || tmp.equals("false")){
									if(a == 0) {
										tmp = "";
									} else {
										tmp = "否";
									}
								}else if(tmp.equals(true) || tmp.equals("true")){
									tmp = "是";
								}
								str = this.proccessLabel(labelMatcher, str, a, sb, tmp);
							}
						}
					} else {
						labelMatcher.appendReplacement(sb, "");
					}
				}else {
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
	 * 处理标题前缀或者后缀
	 * @param labelMatcher
	 * @param str
	 * @param a
	 * @param titleValidity
	 * @param fixPic
	 * @param fix
	 * @param sb
	 */
	private String proccessTitlePrefixOrSuffix(Matcher labelMatcher, String str, int a, String titleValidity, String fixPic, String fix, StringBuffer sb) {
		if(!StringUtil.isEmpty(fix)) {
			String newdate = DateUtil.getAddDaysDateFormat(DateUtil.toStringTrim(new Date(), "yyyy-MM-dd"), StringUtil.parseInt(titleValidity));
			int isSuffixPic = StringUtil.parseInt(fixPic);
			String id = IDFactory.getId();
			// 后缀(前缀)是否是图片	
			if(isSuffixPic == 1) {
				String img = "<img id=\""+ id +"\" src=\""+ SiteResource.getUrl(fix, true) +"\" style=\"display:none\"/> <script>if(checkDate('" + newdate + "')){ document.getElementById(\""+ id +"\").style.display=\"\";}</script>";
				str = this.proccessLabel(labelMatcher, str, a, sb, img);
			} else if(isSuffixPic == 0) {
				String span = "<span id=\""+ id +"\" style=\"display:none\">"+ fix +"</span> <script>if(checkDate('" + newdate + "')){ document.getElementById(\""+ id +"\").style.display=\"\";}</script> ";
				str = this.proccessLabel(labelMatcher, str, a, sb, span);
			} 
		} else {
			labelMatcher.appendReplacement(sb, "");
		}
		return str;
	}
	
	/**
	 * 处理引题(缩) 和副标题（缩）
	 * @param labelMatcher
	 * @param str
	 * @param a
	 * @param sb
	 * @param leadingTitle
	 * @param titleLimit
	 */
	private String proccessTitle(Matcher labelMatcher, String str, int a, StringBuffer sb, String leadingTitle, String titleLimit) {
		if(leadingTitle != null) {
			if(StringUtil.parseInt(titleLimit) > 0) { 
				if(leadingTitle.length() >= StringUtil.parseInt(titleLimit)){
					leadingTitle = leadingTitle.substring(0, StringUtil.parseInt(titleLimit)) + "...";
				}	
			}
			str = this.proccessLabel(labelMatcher, str, a, sb, leadingTitle);
		} else {
			labelMatcher.appendReplacement(sb, "");
		}
		return str;
	}
	
	/**
	 * 获取页面所选择的栏目
	 * @param columnId
	 * @param siteId
	 * @param filePath
	 * @return
	 */
	private String getColumnId(String columnId, String siteId,String filePath){
		XmlUtil xmlUtil = XmlUtil.getInstance(filePath);
		//内容来源
		String contextFrom = xmlUtil.getNodeText("j2ee.cms/picture-news/contextFrom");
		//栏目名称
		String columnName = xmlUtil.getNodeText("j2ee.cms/picture-news/columnName");
		String fixedColumnId = null;
		String strColumn[] = columnName.split("##");
		Column column = null;
		if(strColumn != null && strColumn.length == 2){
			fixedColumnId = strColumn[0];
		}
		if(StringUtil.isEmpty(columnId) || columnId.equals("0")) {
			columnId = null;
		} else {
			column = columnDao.getAndNonClear(columnId);
		}
		String newColumnId = null;
		if(contextFrom.equals("1")){
			//当前栏目
			newColumnId = columnId;
		}else if(contextFrom.equals("2")){
			//当前栏目的父栏目
			if(column != null && column.getParent() != null) {
				newColumnId = column.getParent().getId();
			}
		}else if(contextFrom.equals("3")){
			//指定栏目
			newColumnId = fixedColumnId;
		}else if(contextFrom.equals("4")){
			//指定栏目的父栏目
			Column newcolumn = columnDao.getAndClear(fixedColumnId);
			if(newcolumn.getParent() != null) {
				newColumnId = newcolumn.getParent().getId();
			}
		}
		return newColumnId;
	}
	
	/**
	 * 获得html代码
	 * @param unitId
	 * @return
	 */
	private String findHtmlPath(String unitId){
		TemplateUnit templateUnit = templateUnitDao.getAndNonClear(unitId);
		String html = templateUnit.getHtml();
		return html;
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
	 * @param documentDao the documentDao to set
	 */
	public void setDocumentDao(DocumentDao documentDao) {
		this.documentDao = documentDao;
	}

	/**
	 * @param categoryDao the categoryDao to set
	 */
	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	/**
	 * @param siteDao the siteDao to set
	 */
	public void setSiteDao(SiteDao siteDao) {
		this.siteDao = siteDao;
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
