/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.unitmanager.analyzer;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.baize.ccms.biz.articlemanager.dao.ArticleAttributeDao;
import com.baize.ccms.biz.articlemanager.dao.ArticleDao;
import com.baize.ccms.biz.articlemanager.domain.Article;
import com.baize.ccms.biz.columnmanager.dao.ColumnDao;
import com.baize.ccms.biz.columnmanager.domain.Column;
import com.baize.ccms.biz.sitemanager.dao.SiteDao;
import com.baize.ccms.biz.sitemanager.domain.Site;
import com.baize.ccms.biz.templatemanager.dao.TemplateInstanceDao;
import com.baize.ccms.biz.templatemanager.dao.TemplateUnitDao;
import com.baize.ccms.biz.templatemanager.domain.TemplateUnit;
import com.baize.ccms.biz.unitmanager.label.ArticleTextLabel;
import com.baize.ccms.biz.unitmanager.label.ColumnLinkLabel;
import com.baize.ccms.biz.unitmanager.label.CommonLabel;
import com.baize.ccms.sys.GlobalConfig;
import com.baize.ccms.sys.SiteResource;
import com.baize.common.core.util.CollectionUtil;
import com.baize.common.core.util.DateUtil;
import com.baize.common.core.util.IDFactory;
import com.baize.common.core.util.StringUtil;
import com.baize.common.core.util.XmlUtil;

/**
 * <p>标题: —— 栏目链接解析类</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 模板管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司</p>
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-6-3 下午05:34:42
 * @history（历次修订内容、修订人、修订时间等）
 */
public class ColumnLinkAnalyzer implements TemplateUnitAnalyzer {
	
	private static final Logger log = Logger.getLogger(ColumnLinkAnalyzer.class);
	
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
	/**
	 * 获取新的html代码
	 * @param unitId
	 * @param columnId
	 * @param commonLabel
	 * @return
	 */
	public String getHtml(String unitId, String articleId, String columnId, String siteId, Map<String,String> commonLabel) {
		TemplateUnit unit = templateUnitDao.getAndClear(unitId);
		String configFilePath = unit.getConfigFile();
		//获取到当前模板实例的xml配置文件路径
		String filePath = GlobalConfig.appRealPath + configFilePath;
		XmlUtil xmlUtil = XmlUtil.getInstance(filePath);
		//栏目链接列
		int col = StringUtil.parseInt(String.valueOf(xmlUtil.getNodeText("baize/column-link/display-column-col")));
		//栏目链接行
		int row = StringUtil.parseInt(String.valueOf(xmlUtil.getNodeText("baize/column-link/display-column-row")));
		// 指定栏目
		String fixedColumn = xmlUtil.getNodeText("/baize/column-link/fixedColumn");
		// 栏目类型
		String type = xmlUtil.getNodeText("/baize/column-link/column-type");
		// 获得要解析的html代码
		String htmlCode = this.findHtmlCode(unitId);
		htmlCode = StringUtil.trimEnter(htmlCode);
		int columnType = StringUtil.parseInt(type);
		// 网站模板设置，如果是与当前栏目则不返回数据
		if(columnId == null || columnId.equals("") || columnId.equals("0")) {
			Pattern forPattern = Pattern.compile(CommonLabel.REGEX_FOR);
			Matcher forMatcher = forPattern.matcher(htmlCode);
			StringBuffer sb4 = new StringBuffer();
			if(forMatcher.find()) {
				forMatcher.appendReplacement(sb4, "");
			}
			forMatcher.appendTail(sb4);
			if(columnType == 2 || columnType == 3 || columnType == 4 || columnType == 5) {
				String str = this.sub(sb4.toString(), new Column(), xmlUtil, unitId, siteId);
				return str;
			}
		}
		// 如果是第一级栏目或者是指定栏目的子栏目，要是没有for标签则返回空
		if(columnType == 1 || columnType == 8) {
			Pattern forPattern = Pattern.compile(CommonLabel.REGEX_FOR);
			Matcher forMatcher = forPattern.matcher(htmlCode);
			if(!forMatcher.find()) {
				String str = this.sub(htmlCode, new Column(), xmlUtil, unitId, siteId);
				return str;
			}
		}
		//一共要显示多少条记录
		int count = col * row;	
		// 获取要操作的栏目ids
		String columnIds = "";
		Column column = null;
		if(columnId == null || columnId.equals("") || columnId.equals("0")) {
			columnId = "";
			column = new Column();
		} else {
			column = columnDao.getAndNonClear(columnId);
			if(column == null){
				column = new Column();
			}
		}
		columnIds = this.getColumnIds(columnId, siteId, fixedColumn, column, columnIds, type);
		String[] allids = columnIds.split(",");
		
		int displayCount = 0;
		if(allids.length > count) {
			displayCount = count;
		} else {
			displayCount = allids.length;
		}
		// 重新获得ids
		String [] ids = new String[displayCount];
		for(int i = 0; i < ids.length; i++) {
			ids[i] = allids[i];
		}
		
		Pattern forPattern = Pattern.compile(CommonLabel.REGEX_FOR);
		Matcher forMatcher = forPattern.matcher(htmlCode);
		String str = htmlCode;
		StringBuffer sb = new StringBuffer();
		/*sb.append("<script>var curDate = new Date(); var curYear =curDate.getFullYear(); var curMonth = curDate.getMonth()+1;" +
					"var curDay = curDate.getDate(); var date = curYear+\"-\"+curMonth+\"-\"+curDay;</script>");
*/		// 先处理所有的for
		if(forMatcher.find()) {
			forMatcher.appendReplacement(sb, "");
			str = this.getForData(forMatcher.group(), ids, siteId, col, xmlUtil, unitId);
			sb.append(str);
		}
		forMatcher.appendTail(sb);
		log.debug("aftrer for =========="+sb.toString()); 
		StringBuffer sb1 = new StringBuffer();
		String[] tmp = columnIds.split(",");
		if(!StringUtil.isEmpty(columnIds) && tmp.length == 1) {
			Pattern ifPattern = Pattern.compile(CommonLabel.IF);
			Matcher ifMatcher = ifPattern.matcher(sb.toString());
			// 处理完for的情况后处理有if的情况
			if(ifMatcher.find()) {
				str = this.getData(ifMatcher.group(), unitId, columnIds.toString(), siteId);
				ifMatcher.appendReplacement(sb1, str);
			}
			ifMatcher.appendTail(sb1);
			// 处理完for和if后处理符合正则的标签
			Column newColumn = columnDao.getAndClear(columnIds.toString());
			str = this.sub(sb1.toString(), newColumn, xmlUtil, unitId, siteId);
			return str;
		} else {
			log.debug("====="+sb.toString());
			str = this.sub(sb.toString(), new Column(), xmlUtil, unitId, siteId);
			log.debug("str==="+str);
			return str;
		}
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
	 * 获得for标签里面的数据
	 * @param htmlCode
	 * @param unitId
	 * @param columnId
	 * @param siteId
	 * @return
	 */
	private String getForData(String htmlCode, String[] ids, String siteId, int col, XmlUtil xmlUtil, String unitId) {
		//标签
		StringBuffer sb = new StringBuffer();
		Pattern forPattern = Pattern.compile(CommonLabel.REGEX_FOR);
		Matcher forMatcher = forPattern.matcher(htmlCode);	
		int m = 0;
		// 1、匹配for, <dddddd><!--for--><sapn id=""><!--columnName--><!--columnLink--></span><!--/for--><dddddddddd>
		if(forMatcher.find()) {
			// 2、匹配for, <!--for--><sapn id=""><!--columnName--><!--columnLink--></span><!--/for-->
			StringBuffer sb3 = new StringBuffer();
			String str = "";
			if(!StringUtil.isEmpty(ids.toString())) {	
				int z = 1;
				int temp = 0;
				boolean bool = false;
				
				if(ids.length == 1){
					String columnid = ids[0];
					if(!columnid.equals("0") && !columnid.equals("")){
						Column newColumn = columnDao.getAndClear(columnid);
						str = this.sub(forMatcher.group(1), newColumn, xmlUtil, unitId, siteId);
						sb3.append(str);
					}
				}else{
					for(int i = 0 ; i < ids.length; i++) {		
						String columnid = ids[i];
						if(!columnid.equals("0") && !columnid.equals("")){
							Column newColumn = columnDao.getAndClear(columnid);
							StringBuffer sb4 = new StringBuffer();
							if(m == 0) {
//								sb4.append("<table>");
							}
							double xx = (i)%(col);	
							double colorIndex = z % 2;
							double line = 0.0;
							if(xx == 0){
								if(line == 0){
									temp = 1;
									if(colorIndex == 0 ){
//										sb4.append("<tr><td>");		
										sb4.append("<tr>");		
									}else{
//										sb4.append("<tr><td>");	
										sb4.append("<tr>");
									}
								}else{
									temp = 2;
									if(colorIndex == 0 ){
//										sb4.append("<tr><td>");
										sb4.append("<tr>");
									}else{
//										sb4.append("<tr><td>");
										sb4.append("<tr>");
									}
								}
								bool = true;
	
							}else if(i != 0){		
								if(temp == 1){
									if(colorIndex == 0 ){
//										sb4.append("<td>");										
									}else{
//										sb4.append("<td>");										
									}
								}else if(temp == 2){
									if(colorIndex == 0 ){
//										sb4.append("<td>");
									}else{
//										sb4.append("<td>");
									}
								}else{
//									sb4.append("<td>");	
								}
							}	
							//sb4 ======   第一次：<div><ul><li><!--columnName--><!--columnLink-->
							//             第二次: <li><!--columnName--><!--columnLink-->
							sb4.append(forMatcher.group(1));
							str = this.sub(sb4.toString(), newColumn, xmlUtil, unitId, siteId);
							double yy = (i+col+1)%col;
							if(yy == 0){
								temp = 0;
//								str = str +"</td></tr>";
								str = str +"</tr>";
								if(bool == true){
									z++;
									bool = false;
								}
							}else{
//								str = str +"</td>";
							}						
							if(i == (ids.length-1)) {
//								str = str + "</table>";
							}
							m++;
							// sb3 ========== 第一次： <div><ul><li>columnName columnLink</li>
							//                第二次：  <div><ul><li>columnName columnLink</li><li>columnName columnLink</li></ul>
							sb3.append(str);
						}
					}
				}
			}
			sb.append(sb3.toString());
		}
		return sb.toString();
	}

	/**
	 * 截取字符串（截取标签，然后替换）
	 * @param src
	 * @param column
	 * @param xmlUtil
	 * @param unitId
	 * @return
	 */
	public String sub(String src, Column column, XmlUtil xmlUtil, String unitId, String siteId) {	
		String prefix = "";
		String prefixDate = "";
		String prefixPic = "";
		String suffix = "";
		String suffixDate = "";
		String suffixPic = "";
		Site site = siteDao.getAndClear(siteId);
		// 获取xml文件中的字段信息
		prefix = xmlUtil.getNodeText("/baize/column-link/column-prefix");
		prefixDate = xmlUtil.getNodeText("/baize/column-link/column-prefix-date");
		prefixPic = xmlUtil.getNodeText("/baize/column-link/column-prefix-picture");
		suffix = xmlUtil.getNodeText("/baize/column-link/column-suffix");
		suffixDate = xmlUtil.getNodeText("/baize/column-link/column-suffix-date");
		suffixPic = xmlUtil.getNodeText("/baize/column-link/column-suffix-picture");
		StringBuffer sb = new StringBuffer();
		// 处理for里面的if
		Pattern ifPattern = Pattern.compile(CommonLabel.IF);
		Matcher ifMatcher = ifPattern.matcher(src);
		String ifData = "";
		StringBuffer sb1 = new StringBuffer();
		if(ifMatcher.find()) {
			ifData = this.getData(ifMatcher.group(), unitId, column.getId(), siteId);
			ifMatcher.appendReplacement(sb1, ifData);
		}
		ifMatcher.appendTail(sb1);
		// 处理for
		String label = "";	
		Pattern labelPatttern = Pattern.compile(CommonLabel.REGEX_LABEL);	
		Matcher labelMatcher = labelPatttern.matcher(sb1.toString());
		while(labelMatcher.find()) {
			label = labelMatcher.group();
			// 栏目名称	
			if(label.equals(CommonLabel.COLUMN_NAME)) {
				if(!StringUtil.isEmpty(column.getName())) {
					labelMatcher.appendReplacement(sb, column.getName());
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
			
				
			// 栏目前缀	
			} else if(label.equals(ColumnLinkLabel.COLUMN_PREFIX)) {
				if(column.getId() != null) {
					this.proccessSub(prefix, prefixPic, prefixDate, labelMatcher, sb);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
				
			// 栏目后缀	
			} else if(label.equals(ColumnLinkLabel.COLUMN_SUFFIX)) {
				if(column.getId() != null) {
					this.proccessSub(suffix, suffixPic, suffixDate, labelMatcher, sb);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
				
			// 栏目更新日期 	
			} else if(label.equals(ColumnLinkLabel.COLUMN_UPDATE_TIME)) {
				Date updateTime = column.getUpdateTime();
				String date = "";
				if(updateTime != null) {
					date = DateUtil.toString(updateTime);
				}
				labelMatcher.appendReplacement(sb, date);
				
			// 栏目说明
			} else if(label.equals(ColumnLinkLabel.COLUMN_NOTE)) {
				String description = column.getDescription();				
				if(!StringUtil.isEmpty(description)) {					
					labelMatcher.appendReplacement(sb, description);		
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
				
			// 栏目链接
			} else if(label.equals(ColumnLinkLabel.COLUMN_LINK)) {
				String columnType = column.getColumnType();
				String url = "";
				// 如果是单信息栏目                    
				if(columnType.equals("single")){
					String columnId = column.getId();
					if(!StringUtil.isEmpty(columnId)){
						List<Article> list = articleDao.findByNamedQuery("findAuditedArticlesByColumnId", "columnId", columnId, 0, 1);
						if(!CollectionUtil.isEmpty(list)){
							url = list.get(0).getUrl();
						}
					}
				}else{
					url = column.getUrl();
				}
				if(!StringUtil.isEmpty(url)) {
					url = SiteResource.getUrl(url, true);
					labelMatcher.appendReplacement(sb, url);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
				
			// 网站名称	
			} else  if(label.equals(CommonLabel.SITE_NAME)) {
				String siteName = site.getName();
				if(siteName != null ){
					labelMatcher.appendReplacement(sb, siteName);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
				
			// 网站链接
			}else if(label.equals(CommonLabel.SITE_LINK)) {
				String siteUrl = site.getUrl();
				if(siteUrl != null ){
					labelMatcher.appendReplacement(sb, SiteResource.getUrl(siteUrl, true));
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
			}
		}
		labelMatcher.appendTail(sb);
		return sb.toString();
	}
	
	/**
	 * 处理sub中栏目前缀和后缀
	 * @param fix
	 * @param fixPic
	 * @param fixDate
	 * @param labelMatcher
	 * @param sb
	 */
	private void proccessSub(String fix, String fixPic, String fixDate, Matcher labelMatcher, StringBuffer sb) {
		if(!StringUtil.isEmpty(fix)) {
			if(StringUtil.isEmpty(fixPic)) {
				fixPic = "0";
			}
			String newdate = DateUtil.getAddDaysDateFormat(DateUtil.toStringTrim(new Date(), "yyyy-MM-dd"), StringUtil.parseInt(fixDate));
			int isPic = StringUtil.parseInt(fixPic);
			String id = IDFactory.getId();
			// 前缀(或者后缀)是否是图片	
			if(isPic == 1) {
				if(StringUtil.parseInt(fixDate) >= 0) {
					labelMatcher.appendReplacement(sb, "<img id=\""+ id +"\" src=\"" + SiteResource.getUrl(fix, true) +"\" style=\"display:none\"/> <script>if(checkDate('" + newdate + "')){ document.getElementById(\""+ id +"\").style.display=\"\";}</script>");
				} else {
					labelMatcher.appendReplacement(sb, "<img id=\""+ id +"\" src=\""+ SiteResource.getUrl(fix, true)  +"\"/>");
				}
			} else if(isPic == 0) {
				if(StringUtil.parseInt(fixDate) >= 0) {
					labelMatcher.appendReplacement(sb, "<span id=\""+ id +"\" style=\"display:none\">"+ fix +"</span> <script>if(checkDate('" + newdate + "')){ document.getElementById(\""+ id +"\").style.display=\"\";}</script> ");
				} else {
					labelMatcher.appendReplacement(sb, fix);
				}
			} else {
				labelMatcher.appendReplacement(sb, "");
			}
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
	public  String getData(String ifSrc, String unitId, String columnId, String siteId) {
		String str = ifSrc;
		while(true) {
			Pattern ifPattern= Pattern.compile(CommonLabel.IF);
			Matcher ifMatcher = ifPattern.matcher(str);
			//  如果还有与<!--if--><!--else--><!--/if-->匹配的
			if(ifMatcher.find()) {
				str = getIfData(str, unitId, columnId, siteId);	
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
	public  String getIfData(String ifsrc, String unitId, String columnId, String siteId) {
		Pattern ifPattern = Pattern.compile(CommonLabel.IF);
		Matcher ifMatcher = ifPattern.matcher(ifsrc);
		TemplateUnit unit = templateUnitDao.getAndClear(unitId);
		String configFilePath = unit.getConfigFile();
		//"D:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp2/wtpwebapps/ccms1.0/release/site1/template_instance/1244619970656/conf/20090616190926187450861579.xml"; 
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
					str = this.getIfData(ifStartMatcher.group(), unitId, columnId, siteId);	
					sb2.append(str);
					ifStartMatcher.appendReplacement(sb, sb2.toString());
				} 
			}
			if(a == 1) {
				ifStartMatcher.appendTail(sb);
				a = 2;
			}
			if(a==0) {
				String str = getIFAndElseAndIFData(ifsrc, xmlUtil, columnId, siteId, unitId);
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
	private String getIFAndElseAndIFData(String str, XmlUtil xmlUtil, String columnId, String siteId, String unitId) {
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
				changeCode = getIfStartOrIfEnd(label, column, xmlUtil, siteId, unitId);
			}
			sb2.append(changeCode);
			if(StringUtil.isEmpty(changeCode)) {
				Pattern ifEndPattern1 = Pattern.compile(CommonLabel.IF_END);
				Matcher ifEndMatcher1 = ifEndPattern1.matcher(m.group());
				if(ifEndMatcher1.find()) {
					//<!--else--><!--/if-->
					String label = ifEndMatcher1.group(1);
					changeCode = sub(label, column, xmlUtil, unitId, siteId);
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
	private String getIfStartOrIfEnd(String src, Column column, XmlUtil xmlUtil, String siteId, String unitId) {
		String prefix = "";
		String prefixDate = "";
		String prefixPic = "";
		String suffix = "";
		String suffixDate = "";
		String suffixPic = "";
		Site site = siteDao.getAndClear(siteId);
		// 获取xml文件中的字段信息
		prefix = xmlUtil.getNodeText("/baize/column-link/column-prefix");
		prefixDate = xmlUtil.getNodeText("/baize/column-link/column-prefix-date");
		prefixPic = xmlUtil.getNodeText("/baize/column-link/column-prefix-picture");
		suffix = xmlUtil.getNodeText("/baize/column-link/column-suffix");
		suffixDate = xmlUtil.getNodeText("/baize/column-link/column-suffix-date");
		suffixPic = xmlUtil.getNodeText("/baize/column-link/column-suffix-picture");
		
		StringBuffer sb = new StringBuffer();
		String label = "";	
		Pattern labelPatttern = Pattern.compile(CommonLabel.REGEX_LABEL);	
		// src <!--columnLink--><!--columnNote--> 代码在<!--if--><!--else-->之间
		Matcher labelMatcher = labelPatttern.matcher(src);
		String str = "";
		int a = 0;
		while(labelMatcher.find()) {
			label = labelMatcher.group();	
			// 栏目名称	
			if(label.equals(CommonLabel.COLUMN_NAME)) {
				String columnName = column.getName();
				str = this.processLabel(labelMatcher, a, str, columnName, sb);
			
			// 应用名	
			} else if(label.equals(CommonLabel.APP_NAME)){
				String appName = GlobalConfig.appName;
				str = this.processLabel(labelMatcher, a, str, appName, sb);
			
			// 网站id	
			} else if(label.equals(CommonLabel.SITE_ID)){
				str = this.processLabel(labelMatcher, a, str, siteId, sb);
			
				
			// 栏目前缀	
			} else if(label.equals(ColumnLinkLabel.COLUMN_PREFIX)) {
				if(column != null) {
					str = this.proccessIfPrefixOrSuffix(labelMatcher, prefix, prefixPic, sb, a, str, prefixDate);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
				
			// 栏目后缀	
			} else if(label.equals(ColumnLinkLabel.COLUMN_SUFFIX)) {
				if(column != null) {
					str = this.proccessIfPrefixOrSuffix(labelMatcher, suffix, suffixPic, sb, a, str, suffixDate);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
				
			// 栏目更新日期 	
			} else if(label.equals(ColumnLinkLabel.COLUMN_UPDATE_TIME)) {
				Date date = column.getUpdateTime();
				String updateDate = "";
				if(date != null) {
					updateDate = DateUtil.toString(date);
				}
				str = this.processLabel(labelMatcher, a, str, updateDate, sb);
				
			// 栏目说明
			} else if(label.equals(ColumnLinkLabel.COLUMN_NOTE)) {	
				String description = column.getDescription();				
				str = this.processLabel(labelMatcher, a, str, description, sb);
				
			// 栏目链接
			} else if(label.equals(ColumnLinkLabel.COLUMN_LINK)) {
				String columnType = column.getColumnType();
				String url = "";
				// 如果是单信息栏目
				if(columnType.equals("single")){
					String columnId = column.getId();
					if(!StringUtil.isEmpty(columnId)){
						List<Article> list = articleDao.findByNamedQuery("findAuditedArticlesByColumnId", "columnId", columnId, 0, 1);
						if(!CollectionUtil.isEmpty(list)){
							url = list.get(0).getUrl();
						}
					}
				}else{
					url = column.getUrl();
				}
				if(!StringUtil.isEmpty(url)) {
					if(a == 0) {
						str = url;
						labelMatcher.appendReplacement(sb, "");
					}  else {
						if(!StringUtil.isEmpty(str)) {
							labelMatcher.appendReplacement(sb, SiteResource.getUrl(url, true));
						} else {
							labelMatcher.appendReplacement(sb, "");
						}
					}
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
				
			// 网站名称				
			}else  if(label.equals(CommonLabel.SITE_NAME)){
				String siteName = site.getName();
				str = this.processLabel(labelMatcher, a, str, siteName, sb);
			
			// 网站链接	
			}else if(label.equals(CommonLabel.SITE_LINK)) {
				String siteUrl = site.getUrl();
				if(!StringUtil.isEmpty(siteUrl)) {
					str = this.processLabel(labelMatcher, a, str, SiteResource.getUrl(siteUrl, true), sb);
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
	 * @param a
	 * @param str
	 * @param data
	 * @param sb
	 */
	private String processLabel(Matcher labelMatcher, int a, String str, String data, StringBuffer sb) {
		if(!StringUtil.isEmpty(data)) {
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
	 * 处理if中栏目前缀或者后缀
	 * @param labelMatcher
	 * @param fix
	 * @param fixPic
	 * @param sb
	 * @param a
	 * @param str
	 * @param fixDate
	 */
	private String proccessIfPrefixOrSuffix(Matcher labelMatcher, String fix, String fixPic, StringBuffer sb, int a, String str, String fixDate) {
		if(!StringUtil.isEmpty(fix)) {
			if(!StringUtil.isEmpty(fixPic)) {
				String newdate = DateUtil.getAddDaysDateFormat(DateUtil.toStringTrim(new Date(), "yyyy-MM-dd"), StringUtil.parseInt(fixDate));
				int isPic = StringUtil.parseInt(fixPic);
				String id = IDFactory.getId();
				// 前缀(或后缀)是否是图片	
				if(isPic == 1) {
					String img = "";
					if(StringUtil.parseInt(fixDate) >= 0) {
						img = "<img id=\""+ id +"\" src=\"" + SiteResource.getUrl(fix, true)  +"\" style=\"display:none\"/> <script>if(checkDate('" + newdate + "')){ document.getElementById(\""+ id +"\").style.display=\"\";}</script>";
					} else {
						img =  "<img src=\"" + SiteResource.getUrl(fix, true)  + "\"/>";
					}
					str = this.processLabel(labelMatcher, a, str, img, sb);
					
				} else if(isPic == 0) {
					String span = "";
					if(StringUtil.parseInt(fixDate) >= 0) {
						span = "<span id=\""+ id +"\" style=\"display:none\">"+ fix +"</span> <script>if(checkDate('" + newdate + "')){ document.getElementById(\""+ id +"\").style.display=\"\";}</script> ";
					} else {
						span = fix;
					}
					str = this.processLabel(labelMatcher, a, str, span, sb);
				} 
			} else {
				labelMatcher.appendReplacement(sb, "");
			}
		} else {
			labelMatcher.appendReplacement(sb, "");
		}
		return str;
	}
	/**
	 * 获得栏目ids
	 * @param columnId
	 * @param siteId
	 * @param fixedColumn
	 * @param column
	 * @param columnIds
	 * @param type
	 * @return
	 */
	private String getColumnIds(String columnId, String siteId, String fixedColumn, Column column, String columnIds, String type) {
		// 判断栏目的类型
		int columnType = StringUtil.parseInt(type);
		if(columnType > 0) {
			// 第一级栏目
			if(columnType == 1) {
				// 按照网站id查出栏目信息
				List<Column> columnList = columnDao.findByNamedQuery("findFirstColumnBySystemAdmin", "siteId", siteId);
				if(columnList != null && columnList.size() > 0) {
					for(int i = 0; i < columnList.size(); i++) {
						columnIds += "," + columnList.get(i).getId();
					}
				}
				
			// 当前栏目	
			} else if(columnType == 2) {
				columnIds = String.valueOf(columnId);
				
			// 当前栏目的父栏目	
			} else if(columnType == 3) {
				if(column != null && column.getParent() != null) {
					columnIds = String.valueOf(column.getParent().getId());
				}
				
			// 当前栏目的子栏目	
			} else if(columnType == 4) {
				if(column != null && column.getChildren().size() > 0) {
					// 换一种查找方法
					List<Column> list = columnDao.findByNamedQuery("findColumnByParentId", "pid", column.getId());
					if(!CollectionUtil.isEmpty(list)) {
						for(int i = 0; i < list.size(); i++) {
							columnIds += "," + list.get(i).getId();
						}
					}
				}
				
			// 当前栏目的同级栏目	
			} else if(columnType == 5) {
				if(column != null) {
					if(column.getParent() != null) {
						String parentId = column.getParent().getId();
						if(parentId != null) {
							Column parentColumn = columnDao.getAndNonClear(parentId);
							// 换一种查找方法
							List<Column> list = columnDao.findByNamedQuery("findColumnByParentId", "pid", parentColumn.getId());
							if(!CollectionUtil.isEmpty(list)) {
								for(int i = 0; i < list.size(); i++) {
									columnIds += "," + list.get(i).getId();
								}
							}
						}
					// 查找第一级
					} else {
						// 按照网站id查出栏目信息
						List<Column> columnList = columnDao.findByNamedQuery("findFirstColumnBySystemAdmin", "siteId", siteId);
						if(columnList != null && columnList.size() > 0) {
							for(int i = 0; i < columnList.size(); i++) {
								columnIds += "," + columnList.get(i).getId();
							}
						}
					}
				}
				
			// 指定栏目	
			} else if(columnType == 6) {
				String[] str = fixedColumn.split("##");
				columnIds = str[0];
				
			// 指定栏目的父栏目	
			} else if(columnType == 7) {
				String[] str = fixedColumn.split("##");
				String id = str[0];
				if(id != null) {
					Column newcolumn = columnDao.getAndClear(id);
					if(newcolumn.getParent() != null) {
						columnIds = String.valueOf(newcolumn.getParent().getId());
					}
				}
				
			// 指定栏目的子栏目	
			} else if(columnType == 8) {
				String[] str = fixedColumn.split("##");
				String id = str[0];
				if(id != null) {
					Column newColumn = columnDao.getAndNonClear(id);
					if(newColumn != null) {
						// 换一种查找方法
						List<Column> list = columnDao.findByNamedQuery("findColumnByParentId", "pid", newColumn.getId());
						if(!CollectionUtil.isEmpty(list)) {
							for(int i = 0; i < list.size(); i++) {
								columnIds += "," + list.get(i).getId();
							}
						}
					}
				}
			}
		}
		// 如果栏目ids以逗号 开头则删除逗号
		if(columnIds.startsWith(",")) {
			columnIds = columnIds.replaceFirst(",", "");
		}
		return columnIds;
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
}
