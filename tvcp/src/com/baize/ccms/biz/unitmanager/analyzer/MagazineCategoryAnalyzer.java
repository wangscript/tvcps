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
import com.j2ee.cms.biz.articlemanager.domain.ArticleAttribute;
import com.j2ee.cms.biz.columnmanager.dao.ColumnDao;
import com.j2ee.cms.biz.columnmanager.domain.Column;
import com.j2ee.cms.biz.sitemanager.dao.SiteDao;
import com.j2ee.cms.biz.sitemanager.domain.Site;
import com.j2ee.cms.biz.templatemanager.dao.TemplateUnitDao;
import com.j2ee.cms.biz.templatemanager.domain.TemplateUnit;
import com.j2ee.cms.biz.unitmanager.label.CommonLabel;
import com.j2ee.cms.biz.unitmanager.label.MagazineCategoryLabel;
import com.j2ee.cms.biz.unitmanager.label.TitleLinkLabel;
import com.j2ee.cms.sys.GlobalConfig;
import com.j2ee.cms.sys.SiteResource;
import com.j2ee.cms.common.core.util.BeanUtil;
import com.j2ee.cms.common.core.util.DateUtil;
import com.j2ee.cms.common.core.util.StringUtil;
import com.j2ee.cms.common.core.util.XmlUtil;

/**
 * <p>标题: —— 期刊</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CPS</p>
 * <p>版权: Copyright (c) 2009  
 * @author 郑荣华
 * @version 1.0
 * @since 2009-9-8 上午10:44:59
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class MagazineCategoryAnalyzer implements TemplateUnitAnalyzer {
	
	private static final Logger log = Logger.getLogger(MagazineCategoryAnalyzer.class);

	/** 注入模板单元dao */
	private TemplateUnitDao templateUnitDao;
	
	/** 注入栏目dao */
	private ColumnDao columnDao;
	
	/** 网站dao */
	private SiteDao siteDao;
	
	/** 文章dao */
	private ArticleDao articleDao; 
	
	/** 注入文章属性dao */
	private ArticleAttributeDao articleAttributeDao;
	
	/**
	 * 解析期刊分类
	 * @param unitId
	 * @param articleId
	 * @param columnId
	 * @param siteId
	 * @param commonLabel
	 * @return
	 */
	public String getHtml(String unitId, String articleId, String columnId,
			String siteId, Map<String, String> commonLabel) {
		
		StringBuffer sb = new StringBuffer();
		TemplateUnit unit = templateUnitDao.getAndClear(unitId);
		// 获得要解析的html代码
		String htmlCode = this.findHtmlCode(unitId);		
		htmlCode = StringUtil.trimEnter(htmlCode);
		String configFilePath = unit.getConfigFile();
		//获取到当前模板实例的xml配置文件路径
		String filePath = GlobalConfig.appRealPath + configFilePath;
		XmlUtil xmlUtil = XmlUtil.getInstance(filePath);
		// 期刊来源
		String source = xmlUtil.getNodeText("/j2ee.cms/magazine-category/magazineCategory-source");
		// 指定栏目
		String fixedColumn = xmlUtil.getNodeText("/j2ee.cms/magazine-category/fixedColumn");
		if(source.equals("2")) {
			String[] str = fixedColumn.split("##");
			columnId = str[0];
		}
		// 栏目存在
		if(!StringUtil.isEmpty(columnId) && !columnId.equals("0") && !columnId.equals("null")) {
			Pattern forPattern = Pattern.compile(CommonLabel.REGEX_FOR);
			Matcher forMatcher = forPattern.matcher(htmlCode);
			StringBuffer sb2 = new StringBuffer();
			// 找到for
			if(forMatcher.find()) {
				// 信息分类
				String infoCategory = xmlUtil.getNodeText("/j2ee.cms/magazine-category/infoCategory");
				// 分类不为空
				if(!StringUtil.isEmpty(infoCategory)) {
					sb.append(this.subInfoCategory(columnId, infoCategory, htmlCode, siteId, xmlUtil));
					
				// 分类为空	
				} else {
					StringBuffer sb3 = new StringBuffer();
					Pattern forPattern1 = Pattern.compile(CommonLabel.REGEX_FOR);
					Matcher forMatcher1 = forPattern1.matcher(htmlCode);
					if(forMatcher1.find()) {
						forMatcher1.appendReplacement(sb2, "");
					}
					forMatcher1.appendTail(sb3);
					sb.append(this.sub(sb3.toString(), columnId, siteId, xmlUtil, new Article(), ""));
				}
				
			// 没有找到for直接去解析
			} else {
				sb.append(this.sub(htmlCode, columnId, siteId, xmlUtil, new Article(), ""));
			}
			
		// 栏目不存在	
		} else {
			sb.append(this.sub(htmlCode, columnId, siteId, xmlUtil, new Article(), ""));
		}
		log.debug("期刊分类result===================="+sb.toString());
		return sb.toString();
	}
	
	/**
	 * 解析for之间或者for以外的
	 * @param htmlCode
	 * @param columnId
	 * @param siteId
	 * @param xmlUtil
	 * @param article
	 * @param enuValue
	 * @return
	 */
	private String sub(String htmlCode, String columnId, String siteId, XmlUtil xmlUtil, Article article, String enuValue) {
		StringBuffer sb = new StringBuffer();
		
		StringBuffer sb1 = new StringBuffer();
		Pattern forpattern = Pattern.compile(CommonLabel.REGEX_FOR);
		sb1.append(htmlCode);
		Matcher formatcher = forpattern.matcher(htmlCode);
		if(formatcher.find()) {
			formatcher.appendReplacement(sb1, "");
		}
		formatcher.appendTail(sb1);
		
		Pattern pattern = Pattern.compile(CommonLabel.REGEX_LABEL);
		Matcher matcher = pattern.matcher(sb1);
		String label = "";
		while(matcher.find()) {
			label = matcher.group();
			// 栏目名称
			if(label.equals(CommonLabel.COLUMN_NAME)) {
				if(!StringUtil.isEmpty(columnId) && !columnId.equals("0") && !columnId.equals("null")) {
					Column column = columnDao.getAndClear(columnId);
					matcher.appendReplacement(sb, column.getName());
				} else {
					matcher.appendReplacement(sb, "");
				}
				
			// 网站名称	
			} else if(label.equals(CommonLabel.SITE_NAME)) {
				Site site = siteDao.getAndClear(siteId);
				matcher.appendReplacement(sb, site.getName());
				
			// 网站链接	
			} else if(label.equals(CommonLabel.SITE_LINK)) {
				Site site = siteDao.getAndClear(siteId);
				String url = site.getUrl();
				if(!StringUtil.isEmpty(url)) {
					matcher.appendReplacement(sb, SiteResource.getUrl(url, true));
				} else {
					matcher.appendReplacement(sb, "");
				}
			
			// 标题前缀	
			} else if(label.equals(TitleLinkLabel.ARTICLEHEADER)) {
				String titlePrefix = xmlUtil.getNodeText("/j2ee.cms/magazine-category/title-prefix");
				String titlePrefixPic = xmlUtil.getNodeText("/j2ee.cms/magazine-category/title-prefix-picture");
				String prefix = "";
				if(article != null && article.getId() != null) {
					// 前缀不是图片
					if(StringUtil.parseInt(titlePrefixPic) == 0) {
						prefix = titlePrefix;
					// 前缀是图片	
					} else {
						prefix = "<img src=\""+ SiteResource.getUrl(titlePrefix, true) +"\"/>";
					}
				} 
				matcher.appendReplacement(sb, prefix);
				
			// 标题后缀	
			} else if(label.equals(TitleLinkLabel.ARTICLEENDER)) {
				String titleSuffix = xmlUtil.getNodeText("/j2ee.cms/magazine-category/title-suffix");
				String titleSuffixPic = xmlUtil.getNodeText("/j2ee.cms/magazine-category/title-suffix-picture");
				String suffix = "";
				if(article != null && article.getId() != null) {
					// 前缀不是图片
					if(StringUtil.parseInt(titleSuffixPic) == 0) {
						suffix = titleSuffix;
					// 前缀是图片	
					} else {
						suffix = "<img src=\""+ SiteResource.getUrl(titleSuffix, true) +"\"/>";
					}
				}
				matcher.appendReplacement(sb, suffix);

			// 分类	
			} else if(label.equals(MagazineCategoryLabel.CATEGORYNAME)) {
				if(!StringUtil.isEmpty(enuValue)) {
					matcher.appendReplacement(sb, enuValue);
				} else {
					matcher.appendReplacement(sb, "");
				}
				
			} else {
				matcher.appendReplacement(sb, "");
			}
 		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	/***
	 * 截取分类
	 * @param columnId
	 * @param infoCategory
	 * @param htmlCode
	 * @param siteId
	 * @param xmlUtil
	 * @return
	 */
	public String subInfoCategory(String columnId, String infoCategory, String htmlCode, String siteId, XmlUtil xmlUtil) {
		StringBuffer sb = new StringBuffer();
		Column column = columnDao.getAndClear(columnId);
		String formatId = column.getArticleFormat().getId();
		String[] idAndValue = infoCategory.split(":::");
		for(int i = 0; i < idAndValue.length; i++) {
			String categoryId = idAndValue[i].split("##")[0];
			String enuValue = idAndValue[i].split("##")[1];
			String[] params = {"enumId", "formatId"};
			Object[] values = {categoryId, formatId};
			List<ArticleAttribute> list = articleAttributeDao.findByNamedQuery("findArticleAttributeByEnumIdAndFormatId", params, values);
			List<Article> articleList = new ArrayList<Article>();
			
			if(list != null && list.size() > 0) {
				ArticleAttribute articleAttribute = null;
				List enulist = new ArrayList();
				// 文章属性列表
				for(int j = 0; j < list.size(); j++) {
					articleAttribute = list.get(j); 
					String fieldName = articleAttribute.getFieldName();
					enulist.add(fieldName);
				}
				List<Article> articlelist = articleDao.findByNamedQuery("findAuditedArticlesByColumnId", "columnId", columnId);
				// enum1 enum2 
				for(int j = 0; j < enulist.size(); j++) {
					Article article = null;
					String fieldName = enulist.get(j).toString();
					// 文章列表
					for(int k = 0; k < articlelist.size(); k++) {
						article = articlelist.get(k);
						Object obj = BeanUtil.getFieldValue(article, "com.j2ee.cms.biz.articlemanager.domain.Article", fieldName);
						if(obj != null) {
							if(obj.toString().equals(enuValue)) {
								articleList.add(article);
							}
						}
					}
				}
				if(articleList != null && articleList.size() > 0) {
					sb.append(this.subFor(articleList, enuValue, htmlCode, siteId, columnId, xmlUtil));
				}
			}
		}
		if(StringUtil.isEmpty(sb.toString())) {
			sb.append(this.sub(htmlCode, columnId, siteId, xmlUtil, new Article(), ""));
		}
		return sb.toString();
	}
	
	/**
	 * 截取for内容
	 * @param list
	 * @param enuValue
	 * @param htmlCode
	 * @param siteId
	 * @param columnId
	 * @param xmlUtil
	 * @return
	 */
	private String subFor(List<Article> list, String enuValue, String htmlCode, String siteId, String columnId, XmlUtil xmlUtil) {
		StringBuffer sb = new StringBuffer();
		Pattern forPattern = Pattern.compile(CommonLabel.REGEX_FOR);
		Matcher forMatcher = forPattern.matcher(htmlCode);
		StringBuffer sb1 = new StringBuffer();
		// 提取for之间的内容，单独解析，最后再将for外面的内容组装进去
		if(forMatcher.find()) {
			sb1.append(forMatcher.group(1));
			forMatcher.appendReplacement(sb, "");
		}
		if(!StringUtil.isEmpty(sb1.toString())) {
			Article article = null;
			for(int i = 0; i < list.size(); i++) {
				article = list.get(i);
				Pattern pattern = Pattern.compile(CommonLabel.REGEX_LABEL);
				Matcher matcher = pattern.matcher(sb1);
				String label = "";
				while(matcher.find()) {
					label = matcher.group();
					// 分类名称
					if(label.equals(MagazineCategoryLabel.CATEGORYNAME)) {
						matcher.appendReplacement(sb, enuValue);
					
					// 栏目名称、网站名称、网站链接
					} else if(label.equals(CommonLabel.COLUMN_NAME) 
							|| label.equals(CommonLabel.SITE_NAME) 
							|| label.equals(CommonLabel.SITE_LINK)
							|| label.equals(TitleLinkLabel.ARTICLEHEADER) 
							|| label.equals(TitleLinkLabel.ARTICLEENDER)) {
						String value = this.sub(label, columnId, siteId, xmlUtil, article, "");
						matcher.appendReplacement(sb, value);
					
					// 文章属性	
					} else {
						String reg = "<!--(.*)-->";
						Pattern p = Pattern.compile(reg);
						Matcher m = p.matcher(label);
						if(m.find()) {
							String fieldName = m.group(1);
							String data = "";
							Object obj = BeanUtil.getFieldValue(article, "com.j2ee.cms.biz.articlemanager.domain.Article", fieldName);
							if(obj != null) {
								// 是日期
								if (obj instanceof Date) {
									obj = DateUtil.toString((Date)obj, "yyyy-MM-dd HH:mm:ss");
									data = String.valueOf(obj);
									
								} else {
									data = String.valueOf(obj);
									// 是图片、附件、媒体 
									if(fieldName.startsWith("pic")
											|| fieldName.startsWith("attach")
											|| fieldName.startsWith("media")) {
										if(!StringUtil.isEmpty(data) && !data.equals("null")) {
											data = SiteResource.getUrl(data, true);
										}
									
									} else {
										// 是否关系
										if(data.equals("false") || data.equals(false)) {
											data = "否";
										} else if(data.equals("true") || data.equals(true)) {
											data = "是";
										} 
									}
									// 标题控制字数
									if(fieldName.equals("title")){
										String titleSize = xmlUtil.getNodeText("/j2ee.cms/magazine-category/titleSize");
										if(!StringUtil.isEmpty(titleSize)){
											int size = StringUtil.parseInt(titleSize);
											if(data.length() > size) {
												data = data.substring(0, size) + "...";
											}
										}
									}
									if(fieldName.equals("url")){
										String url = StringUtil.toString(article.getUrl());
										if(!StringUtil.isEmpty(url)) {
											url = SiteResource.getUrl(url, true);
											data = url;
										} 
									}
								}
							} else {
								data = "";
							}
							matcher.appendReplacement(sb, data);
						}
					}
				}
				matcher.appendTail(sb);
			}
		}
		forMatcher.appendTail(sb); 
		return this.sub(sb.toString(), columnId, siteId, xmlUtil, new Article(), enuValue);
	}
	
	/**
	 * 按照单元id查找html代码
	 * @param unitId    单元id
	 * @return
	 */
	private String findHtmlCode(String unitId){
		TemplateUnit templateUnit = templateUnitDao.getAndNonClear(unitId);
		String htmlCode = templateUnit.getHtml();
		return htmlCode;
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
	 * @param siteDao the siteDao to set
	 */
	public void setSiteDao(SiteDao siteDao) {
		this.siteDao = siteDao;
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
