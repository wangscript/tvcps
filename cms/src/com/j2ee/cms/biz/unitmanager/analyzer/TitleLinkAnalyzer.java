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
import com.j2ee.cms.biz.sitemanager.dao.SiteDao;
import com.j2ee.cms.biz.sitemanager.domain.Site;
import com.j2ee.cms.biz.templatemanager.dao.TemplateInstanceDao;
import com.j2ee.cms.biz.templatemanager.dao.TemplateUnitDao;
import com.j2ee.cms.biz.templatemanager.domain.TemplateUnit;
import com.j2ee.cms.biz.unitmanager.label.CommonLabel;
import com.j2ee.cms.biz.unitmanager.label.TitleLinkLabel;
import com.j2ee.cms.biz.usermanager.domain.User;
import com.j2ee.cms.sys.GlobalConfig;
import com.j2ee.cms.sys.SiteResource;
import com.j2ee.cms.common.core.util.BeanUtil;
import com.j2ee.cms.common.core.util.CollectionUtil;
import com.j2ee.cms.common.core.util.DateUtil;
import com.j2ee.cms.common.core.util.IDFactory;
import com.j2ee.cms.common.core.util.SqlUtil;
import com.j2ee.cms.common.core.util.StringUtil;
import com.j2ee.cms.common.core.util.XmlUtil;

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
public class TitleLinkAnalyzer implements TemplateUnitAnalyzer {
	/** 日志 */
	private static final Logger log = Logger.getLogger(TitleLinkAnalyzer.class);
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
	/** 全局变量用于控制更多是否显示 */
	private boolean displayMore = false;
	
	/**
	 * 解析html代码并返回给页面
	 * @param unitId
	 * @param columnId
	 * @param siteId
	 * @param commonLabel
	 * @return
	 */
	public String getHtml(String unitId, String articleId, String columnId, String siteId, Map<String,String> commonLabel) {
		String htmlCode = this.findHtmlPath(unitId);
		
		try{
			htmlCode = StringUtil.trimEnter(htmlCode);
			String str = htmlCode;
			TemplateUnit unit = templateUnitDao.getAndNonClear(unitId);
			String configFilePath = unit.getConfigFile();
			//获取到当前模板实例的xml配置文件路径
			String filePath = GlobalConfig.appRealPath + configFilePath;
			XmlUtil xmlUtil = XmlUtil.getInstance(filePath);
			//内容来源
			String contextFrom = xmlUtil.getNodeText("j2ee.cms/title-link/contextFrom");
			// 获得新的栏目id
			String newColumnId = "";		
			newColumnId = this.getColumnId(columnId, siteId, filePath);
			
			if(!StringUtil.isEmpty(newColumnId) && !newColumnId.equals("0")) {
				//信息起始
				int start = StringUtil.parseInt(String.valueOf(xmlUtil.getNodeText("j2ee.cms/title-link/start")));
				//列
				int col = StringUtil.parseInt(String.valueOf(xmlUtil.getNodeText("j2ee.cms/title-link/col")));
				//行
				int row = StringUtil.parseInt(String.valueOf(xmlUtil.getNodeText("j2ee.cms/title-link/row")));
				//一共多少条记录
				int count = col * row;
				//根据栏目ID查询文章
				List articleList1 = new ArrayList();
				int size1 = 0;
				if(!StringUtil.isEmpty(contextFrom) && contextFrom.equals("3")) {
					// 多信息标题链接不需要更多链接
					String morecolumnName = xmlUtil.getNodeText("j2ee.cms/title-link/moreColumnName");
					String morestrColumn[] = morecolumnName.split("##");
					if(morestrColumn[0].split(",").length > 1) {
						displayMore = false; 
					} else {
						articleList1 = articleDao.findByNamedQuery("findAuditedArticlesByColumnId", "columnId",  morestrColumn[0]);
						size1 = articleList1.size();
						articleDao.clearCache();
						articleList1 = articleDao.findByNamedQuery("findAuditedArticlesByColumnId", "columnId",  morestrColumn[0], start, start+count);
						int size2 = articleList1.size();
						articleDao.clearCache();
						if(size1 != size2) {
							displayMore = true;
						} else {
							displayMore = false;
						}
					//	articleDao.clearCache();
					}
				} else {
					articleList1 = articleDao.findByNamedQuery("findAuditedArticlesByColumnId", "columnId", newColumnId, start, count+start);
					size1 = articleList1.size();
					articleDao.clearCache();
					articleList1 = articleDao.findByNamedQuery("findAuditedArticlesByColumnId", "columnId", newColumnId);
					int size2 = articleList1.size();
					articleDao.clearCache();
					if(size1 != size2) {
						displayMore = true;
					} else {
						displayMore = false;
					}
				}
			 //	articleDao.clearCache();
			}
			
			
			StringBuffer sb = new StringBuffer();
			/*sb.append("<script>var curDate = new Date(); var curYear =curDate.getFullYear(); var curMonth = curDate.getMonth()+1;" +
						"var curDay = curDate.getDate(); var date = curYear+\"-\"+curMonth+\"-\"+curDay;</script>");*/
			String tt = "";
			if(contextFrom != null &&contextFrom.equals("3")){
				if(newColumnId != null){
				//	String strColumnIds[] = newColumnId.split(",");
				//	for(int i = 0 ; i < strColumnIds.length ; i++){
						tt = tt + this.getMoreColumnHtmlByColumnIds(newColumnId, htmlCode, unitId, siteId, str, sb);
				//	}
				}
			}else{
				tt =   this.getHtmlByColumnIds(newColumnId, htmlCode, unitId, siteId, str, sb);
			}
			return tt;
		}catch(Exception e){
			e.printStackTrace();
			log.error(e);
			return "标题链接设置错误";
		}
		
	}
	
	private String getHtmlByColumnIds(String newColumnId,String htmlCode,String unitId,String siteId,String str,StringBuffer sb) throws Exception{
		Pattern forPattern = Pattern.compile(CommonLabel.REGEX_FOR);
		Matcher forMatcher = forPattern.matcher(htmlCode);
		// 先处理所有的for
		if(forMatcher.find()) {
			str = this.getForData(forMatcher.group(), unitId, newColumnId, siteId);
			forMatcher.appendReplacement(sb, str);
		}
		forMatcher.appendTail(sb);
		log.debug("aftrer for =========="+sb.toString());
		Pattern ifPattern = Pattern.compile(CommonLabel.IF);
		Matcher ifMatcher = ifPattern.matcher(sb.toString());
		//根据栏目ID查询文章
		String str2 = "";
		String rr = "";
		StringBuffer sb1 = new StringBuffer();
		
//		List<Article> articleList = new ArrayList<Article>();
//		if(newColumnId != null && !newColumnId.equals("") && !newColumnId.equals("0")) {
//			articleList = articleDao.findByNamedQuery("findAuditedArticlesByColumnId", "columnId", newColumnId);
//		} 
		
		//处理完for的情况后处理有if的情况
		Article article = null;
		if(ifMatcher.find()) {
			str = this.getData(ifMatcher.group(), unitId, newColumnId, article, siteId);
			ifMatcher.appendReplacement(sb1, str);
		}
		ifMatcher.appendTail(sb1);
		Pattern labelPattern = Pattern.compile(CommonLabel.REGEX_LABEL);
		Matcher labelMatcher = labelPattern.matcher(sb1.toString());
		// 处理完for和if后处理符合正则的标签
		if(labelMatcher.find()) {
			str2 = str2 + this.sub(sb1.toString(), unitId, newColumnId, siteId, article);
			log.debug("str2=============="+str2);
		} else {
			log.debug("result=============="+sb1.toString());				
		}	
		rr = rr + sb1;
		if(str2 != null && !str2.equals("")){
			return str2;
		}else{
			return rr;
		}	 
	//	return sb.toString();
	}
	
	
	private String getMoreColumnHtmlByColumnIds(String newColumnId,String htmlCode,String unitId,String siteId,String str,StringBuffer sb) throws Exception{
		Pattern forPattern = Pattern.compile(CommonLabel.REGEX_FOR);
		Matcher forMatcher = forPattern.matcher(htmlCode);
		// 先处理所有的for
		if(forMatcher.find()) {
			str = this.getMoreColumnForData(forMatcher.group(), unitId, newColumnId, siteId);
			forMatcher.appendReplacement(sb, str);
		}
		forMatcher.appendTail(sb);
		log.debug("aftrer for =========="+sb.toString());
		Pattern ifPattern = Pattern.compile(CommonLabel.IF);
		Matcher ifMatcher = ifPattern.matcher(sb.toString());
		//根据栏目ID查询文章
		String str2 = "";
		String rr = "";
		StringBuffer sb1 = new StringBuffer();
//		List<Article> articleList = new ArrayList<Article>();
//		if(newColumnId != null && !newColumnId.equals("") && !newColumnId.equals("0")) {
//			articleList = articleDao.findByNamedQuery("findAuditedArticlesByColumnId", "columnId", newColumnId);
//		} 
		String strColumnIds[] = newColumnId.split(",");
	 //	for(int i = 0 ; i < strColumnIds.length ; i++){
		if(strColumnIds.length < 2){
			//处理完for的情况后处理有if的情况
			Article article = null;
			if(ifMatcher.find()) {
				str = this.getData(ifMatcher.group(), unitId,newColumnId, article, siteId);
				ifMatcher.appendReplacement(sb1, str);
			}
			ifMatcher.appendTail(sb1);
			Pattern labelPattern = Pattern.compile(CommonLabel.REGEX_LABEL);
			Matcher labelMatcher = labelPattern.matcher(sb1.toString());
			// 处理完for和if后处理符合正则的标签
			if(labelMatcher.find()) {
				str2 = str2 + this.sub(sb1.toString(), unitId, newColumnId, siteId, article);
				log.debug("str2=============="+str2);
			} else {
				log.debug("result=============="+sb1.toString());				
			}	
			rr = rr + sb1;
			if(str2 != null && !str2.equals("")){
				return str2;
			}else{
				return rr;
			}	
		}else{
			return sb.toString();
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
	private String getMoreColumnForData(String src, String unitId, String columnId, String siteId) throws Exception {
		StringBuffer sb = new StringBuffer();
		if(columnId != null && !columnId.equals("") && !columnId.equals("0")) {
			TemplateUnit unit = templateUnitDao.getAndNonClear(unitId);
			String configFilePath = unit.getConfigFile();
			//获取到当前模板实例的xml配置文件路径
			String filePath = GlobalConfig.appRealPath + configFilePath;
			XmlUtil xmlUtil = XmlUtil.getInstance(filePath);
			
			//信息起始
			int start = StringUtil.parseInt(String.valueOf(xmlUtil.getNodeText("j2ee.cms/title-link/start")));
			//列
			int col = StringUtil.parseInt(String.valueOf(xmlUtil.getNodeText("j2ee.cms/title-link/col")));
			//行
			int row = StringUtil.parseInt(String.valueOf(xmlUtil.getNodeText("j2ee.cms/title-link/row")));
			//标题后缀
			String titleEnd = String.valueOf(xmlUtil.getNodeText("j2ee.cms/title-link/titleEnd"));
			//标题前缀
			String titleHead = String.valueOf(xmlUtil.getNodeText("j2ee.cms/title-link/titleHead"));
			//标题字数
			String titleLimit = String.valueOf(xmlUtil.getNodeText("j2ee.cms/title-link/titleLimit"));
			//更多
			String moreLink = String.valueOf(xmlUtil.getNodeText("j2ee.cms/title-link/moreLink"));
			//底线样式
			String lineStyle = String.valueOf(xmlUtil.getNodeText("j2ee.cms/title-link/lineStyle"));
			//底线隔行
			String lineGroup = String.valueOf(xmlUtil.getNodeText("j2ee.cms/title-link/lineGroup"));
			//奇行背景色
			String oddColor = xmlUtil.getNodeText("j2ee.cms/title-link/oddColor");
			//偶行背景色
			String evenColor = xmlUtil.getNodeText("j2ee.cms/title-link/evenColor");
			//指定行加粗
			String strongTitle = xmlUtil.getNodeText("j2ee.cms/title-link/strongTitle");
			
			//一共多少条记录
			int count = col * row;	
			//根据栏目ID查询文章
			List articleList = new ArrayList();
			String strColumnId[] = columnId.split(",");
			String tempColumn = "";
			for(int i = 0 ; i< strColumnId.length ; i++){
				tempColumn = tempColumn +"," + SqlUtil.toSqlString(strColumnId[i]);
			}
			tempColumn = StringUtil.replaceFirst(tempColumn, ",");
			articleList = articleDao.findByDefine("findAuditedArticlesByColumnIds", "columnId", tempColumn, start, start+count);
			StringBuffer sb3 = new StringBuffer();
			Pattern forPattern = Pattern.compile(CommonLabel.REGEX_FOR);
			Matcher forMatcher = forPattern.matcher(src);	
			int m = 0;
			//给奇行偶行使用
			int z = 1;
			// 1、匹配for, <dddd><!--for--><sapn id=""><!--articletitle--><!--articleurl--></sapn><!--/for--><2222>
			if(forMatcher.find()) {
				Matcher m1 = forPattern.matcher(forMatcher.group());
				if(m1.find()) {
					// 2、匹配for, <!--for--><sapn id=""><!--articletitle--><!--articleurl--></sapn><!--/for-->
					String str = "";
					int temp = 0;
					boolean bool = false;
					if(articleList != null && articleList.size() > 0){					
						for(int i = 0 ; i < articleList.size() ; i++){		
							StringBuffer sb4 = new StringBuffer();
							Article article = (Article) articleList.get(i);		
							if(m == 0) { 
								sb4.append("<table>");
							}
							double xx = (i)%(col);		
							double colorIndex = z % 2;
							String blod = "";
							if(!StringUtil.isEmpty(strongTitle)){
								String[] str1 = strongTitle.split(",");
								for(int j = 0; j < str1.length; j++){
									if(str1[j].equals(String.valueOf(z))){
										//指定行加粗
										blod = "font-weight:bold;";
									}
								}
							}
							/*if(strongTitle.equals(String.valueOf(z))){
								//指定行加粗
								blod = "font-weight:bold;";
							}	*/
							double line = 0.0;
							//设置底线样式
							if(lineGroup != null && !lineGroup.equals("") && !lineGroup.equals("0")){
								line = z % StringUtil.parseInt(String.valueOf(lineGroup)); 
							}
						
							if(xx == 0){
							
								//指定行加粗
								double strongTitleBlod = 0.0;
								if(strongTitle != null && !strongTitle.equals("") && !strongTitle.equals("0")){
									strongTitleBlod = (double)z % StringUtil.parseInt(strongTitle);
								}
								
							//	if(strongTitleBlod == z){
															
								if(line == 0){
									temp = 1;
									//设置底线样式									
									if(colorIndex == 0 ){
										//偶行背景色									
										sb4.append("<tr><td style='border-bottom:"+lineStyle+";"+blod+"background-color:"+evenColor+";'>");										
									}else{
										//奇行背景色										
										sb4.append("<tr><td style='border-bottom:"+lineStyle+";"+blod+"background-color:"+oddColor+";'>");										
									}
								}else{
									temp = 2;
									if(colorIndex == 0 ){
										//偶行背景色										
										sb4.append("<tr><td style='background-color:"+evenColor+";"+blod+"'>");
									}else{
										//奇行背景色										
										sb4.append("<tr><td style='background-color:"+oddColor+";"+blod+"'>");
									}
								}
								bool = true;
							

							}else if(i != 0){		
							
								if(temp == 1){
									if(colorIndex == 0 ){
										//偶行背景色									
										sb4.append("<td style='border-bottom:"+lineStyle+";"+blod+"background-color:"+evenColor+";'>");										
									}else{
										//奇行背景色										
										sb4.append("<td style='border-bottom:"+lineStyle+";"+blod+"background-color:"+oddColor+";'>");										
									}
								}else if(temp == 2){
									if(colorIndex == 0 ){
										//偶行背景色										
										sb4.append("<td style='background-color:"+evenColor+";"+blod+"'>");
									}else{
										//奇行背景色										
										sb4.append("<td style='background-color:"+oddColor+";"+blod+"'>");
									}
								}else{
									sb4.append("<td>");	
								}
							}
						
							//sb4 ======   第一次：<div><ul><li><!--articletitle--><!--articleurl-->
							//             第二次: <li><!--articletitle--><!--articleurl-->
							sb4.append(forMatcher.group(1));
							/*if(columnId != null){
								String strColumnIds[] = columnId.split(",");*/
							//	for(int y = 0 ; y < strColumnIds.length ; y++){
							String tempcolumnId = article.getColumn().getId();
							str = this.sub(sb4.toString(), unitId, tempcolumnId, siteId, article);
							double yy = (i+col+1)%col;
							if(yy == 0){
								temp = 0;
								str = str +"</td></tr>";
								if(bool == true){
									z++;
									bool = false;
								}
							}else{
								//temp = 0;
								str = str +"</td>";
							}						
							if(i == (articleList.size()-1)) {
								str = str + "</table>";
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
		}
		return sb.toString();
	}
	
	/**
	 * 获得循环标签里面的数据
	 * @param src
	 * @param unitId
	 * @param columnId
	 * @param siteId
	 * @return
	 */
	private String getForData(String src, String unitId, String columnId, String siteId) throws Exception{
		StringBuffer sb = new StringBuffer();
		if(columnId != null && !columnId.equals("") && !columnId.equals("0")) {
			TemplateUnit unit = templateUnitDao.getAndNonClear(unitId);
			String configFilePath = unit.getConfigFile();
			//获取到当前模板实例的xml配置文件路径
			String filePath = GlobalConfig.appRealPath + configFilePath;
			XmlUtil xmlUtil = XmlUtil.getInstance(filePath);
			
			//信息起始
			int start = StringUtil.parseInt(String.valueOf(xmlUtil.getNodeText("j2ee.cms/title-link/start")));
			//列
			int col = StringUtil.parseInt(String.valueOf(xmlUtil.getNodeText("j2ee.cms/title-link/col")));
			//行
			int row = StringUtil.parseInt(String.valueOf(xmlUtil.getNodeText("j2ee.cms/title-link/row")));
			//标题后缀
			String titleEnd = String.valueOf(xmlUtil.getNodeText("j2ee.cms/title-link/titleEnd"));
			//标题前缀
			String titleHead = String.valueOf(xmlUtil.getNodeText("j2ee.cms/title-link/titleHead"));
			//标题字数
			String titleLimit = String.valueOf(xmlUtil.getNodeText("j2ee.cms/title-link/titleLimit"));
			//更多
			String moreLink = String.valueOf(xmlUtil.getNodeText("j2ee.cms/title-link/moreLink"));
			//底线样式
			String lineStyle = String.valueOf(xmlUtil.getNodeText("j2ee.cms/title-link/lineStyle"));
			//底线隔行
			String lineGroup = String.valueOf(xmlUtil.getNodeText("j2ee.cms/title-link/lineGroup"));
			//奇行背景色
			String oddColor = xmlUtil.getNodeText("j2ee.cms/title-link/oddColor");
			//偶行背景色
			String evenColor = xmlUtil.getNodeText("j2ee.cms/title-link/evenColor");
			//指定行加粗
			String strongTitle = xmlUtil.getNodeText("j2ee.cms/title-link/strongTitle");
			//一共多少条记录
			int count = col * row;	
			//根据栏目ID查询文章
			List articleList = new ArrayList();
			articleList = articleDao.findByNamedQuery("findAuditedArticlesByColumnId", "columnId", columnId, start, start+count);
			StringBuffer sb3 = new StringBuffer();
			Pattern forPattern = Pattern.compile(CommonLabel.REGEX_FOR);
			Matcher forMatcher = forPattern.matcher(src);	
			int m = 0;
			//给奇行偶行使用
			int z = 1;
			// 1、匹配for, <dddd><!--for--><sapn id=""><!--articletitle--><!--articleurl--></sapn><!--/for--><2222>
			if(forMatcher.find()) {
				Matcher m1 = forPattern.matcher(forMatcher.group());
				if(m1.find()) {
					// 2、匹配for, <!--for--><sapn id=""><!--articletitle--><!--articleurl--></sapn><!--/for-->
					String str = "";
					int temp = 0;
					boolean bool = false;
					if(articleList != null && articleList.size() > 0){					
						for(int i = 0 ; i < articleList.size() ; i++){		
							StringBuffer sb4 = new StringBuffer();
							Article article = (Article) articleList.get(i);		
							if(m == 0) {
								sb4.append("<table>");
							}
							double xx = (i)%(col);		
							double colorIndex = z % 2;
							String blod = "";
							
							if(!StringUtil.isEmpty(strongTitle)){
								String[] str1 = strongTitle.split(",");
								for(int j = 0; j < str1.length; j++){
									if(str1[j].equals(String.valueOf(z))){
										//指定行加粗
										blod = "font-weight:bold;";
									}
								}
							}
							
							/*if(strongTitle.equals(String.valueOf(z))){
								//指定行加粗
								blod = "font-weight:bold;";
							}*/	
							double line = 0.0;
							//设置底线样式
							if(lineGroup != null && !lineGroup.equals("") && !lineGroup.equals("0")){
								line = z % StringUtil.parseInt(String.valueOf(lineGroup)); 
							}
						
							if(xx == 0){
							
								//指定行加粗
								double strongTitleBlod = 0.0;
								if(strongTitle != null && !strongTitle.equals("") && !strongTitle.equals("0")){
									strongTitleBlod = (double)z % StringUtil.parseInt(strongTitle);
								}
								
							//	if(strongTitleBlod == z){
															
								if(line == 0){
									temp = 1;
									//设置底线样式									
									if(colorIndex == 0 ){
										//偶行背景色									
										sb4.append("<tr><td style='border-bottom:"+lineStyle+";"+blod+"background-color:"+evenColor+";'>");										
									}else{
										//奇行背景色										
										sb4.append("<tr><td style='border-bottom:"+lineStyle+";"+blod+"background-color:"+oddColor+";'>");										
									}
								}else{
									temp = 2;
									if(colorIndex == 0 ){
										//偶行背景色										
										sb4.append("<tr><td style='background-color:"+evenColor+";"+blod+"'>");
									}else{
										//奇行背景色										
										sb4.append("<tr><td style='background-color:"+oddColor+";"+blod+"'>");
									}
								}
								bool = true;

							}else if(i != 0){		
							
								if(temp == 1){
									if(colorIndex == 0 ){
										//偶行背景色									
										sb4.append("<td style='border-bottom:"+lineStyle+";"+blod+"background-color:"+evenColor+";'>");										
									}else{
										//奇行背景色										
										sb4.append("<td style='border-bottom:"+lineStyle+";"+blod+"background-color:"+oddColor+";'>");										
									}
								}else if(temp == 2){
									if(colorIndex == 0 ){
										//偶行背景色										
										sb4.append("<td style='background-color:"+evenColor+";"+blod+"'>");
									}else{
										//奇行背景色										
										sb4.append("<td style='background-color:"+oddColor+";"+blod+"'>");
									}
								}else{
									sb4.append("<td>");	
								}
							}						
							//sb4 ======   第一次：<div><ul><li><!--articletitle--><!--articleurl-->
							//             第二次: <li><!--articletitle--><!--articleurl-->
							sb4.append(forMatcher.group(1));
							str = this.sub(sb4.toString(), unitId, columnId, siteId, article);
							double yy = (i+col+1)%col;
							if(yy == 0){
								temp = 0;
								str = str +"</td></tr>";
								if(bool == true){
									z++;
									bool = false;
								}
							}else{
								str = str +"</td>";
							}						
							if(i == (articleList.size()-1)) {
								str = str + "</table>";
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
	public String sub(String src, String unitId, String columnId, String siteId, Article article) throws Exception{	
		Site site = siteDao.getAndNonClear(siteId);
		TemplateUnit unit = templateUnitDao.getAndNonClear(unitId);
		String configFilePath = unit.getConfigFile();
		//获取到当前模板实例的xml配置文件路径
		String filePath = GlobalConfig.appRealPath+ configFilePath;
		XmlUtil xmlUtil = XmlUtil.getInstance(filePath);
		Column column = null;
		if(!columnId.equals("")) {
			 column = columnDao.getAndNonClear(columnId);
		}
		// 处理for里面的if
		Pattern ifPattern = Pattern.compile(CommonLabel.IF); 
		Matcher ifMatcher = ifPattern.matcher(src);
		
		StringBuffer sb1 = new StringBuffer();
		if(ifMatcher.find()) {
			String ifData = "";
			ifData = this.getData(ifMatcher.group(), unitId, columnId, article, siteId);
			ifMatcher.appendReplacement(sb1, ifData);
		}
		ifMatcher.appendTail(sb1);
		// 处理for
		String label = "";	
		Pattern labelPatttern = Pattern.compile(CommonLabel.REGEX_LABEL);	
		Matcher labelMatcher = labelPatttern.matcher(sb1.toString());
		StringBuffer sb = new StringBuffer();
		// 处理for里面的标签
		while(labelMatcher.find()) {
			label = labelMatcher.group();	
			//更多内容 
			if(label.equals(TitleLinkLabel.MORELINK)) {
				if(displayMore) {
					String moreLink = String.valueOf(xmlUtil.getNodeText("j2ee.cms/title-link/moreLink"));
					//是否是图片
					String moreLinkPic = String.valueOf(xmlUtil.getNodeText("j2ee.cms/title-link/moreLinkPic"));
					if(column != null){
						String url = column.getUrl();
						if(moreLinkPic != null && !moreLinkPic.equals("") && !moreLinkPic.equals("0")) {
							labelMatcher.appendReplacement(sb, "<a href=\""+ SiteResource.getUrl(url, true) +"\"><img src=\""+ SiteResource.getUrl(moreLink, true)+"\"/></a>");
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
			} else  if(label.equals(CommonLabel.SITE_NAME)) {
				String siteName = StringUtil.toString(site.getName());
				if(!StringUtil.isEmpty(siteName)) {
					labelMatcher.appendReplacement(sb, siteName);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
				
			// 网站链接	
			} else if(label.equals(CommonLabel.SITE_LINK)) {
				String siteUrl = StringUtil.toString(site.getUrl());
				if(!StringUtil.isEmpty(siteUrl)) {
					labelMatcher.appendReplacement(sb, SiteResource.getUrl(siteUrl, true));
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
			

			// 栏目名称
			} else if(label.equals(CommonLabel.COLUMN_NAME)) {
				if(column != null) {
					String columnName = column.getName();
					labelMatcher.appendReplacement(sb, columnName);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
					 	
			// 其他标签	
			} else {
				if(article != null && article.getId() != null) {
					String tmp = "";
					tmp = this.setData(article, label, xmlUtil);
					log.debug("tmp======================="+tmp);
					labelMatcher.appendReplacement(sb, tmp);
					log.debug("sb====================="+sb);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}
			}
		}
		labelMatcher.appendTail(sb);
		return sb.toString();
	}
	
	public String setData(Article article, String label, XmlUtil xmlUtil) throws Exception{
		if(article == null || article.getId() == null) {
			return "";
		}
		String titleEnd = String.valueOf(xmlUtil.getNodeText("j2ee.cms/title-link/titleEnd"));
		//标题前缀
		String titleHead = String.valueOf(xmlUtil.getNodeText("j2ee.cms/title-link/titleHead"));
		//标题字数
		String titleLimit = String.valueOf(xmlUtil.getNodeText("j2ee.cms/title-link/titleLimit"));
		//前缀是否图片
		String headPic = String.valueOf(xmlUtil.getNodeText("j2ee.cms/title-link/titleHeadPic"));
		//后缀是否图片
		String endPic = String.valueOf(xmlUtil.getNodeText("j2ee.cms/title-link/titleEndPic"));
		String str = "";
		if(label.equals(TitleLinkLabel.ARTICLEARTICLEAUTHOR)){	
			//信息作者 
			String author = StringUtil.toString(article.getAuthor());	
			str = this.proccessSubLabel(author);
		
		// 应用名	
		} else if(label.equals(CommonLabel.APP_NAME)){
			String appName = GlobalConfig.appName;
			str = this.proccessSubLabel(appName);
		
		}else if(label.equals(TitleLinkLabel.ARTICLEAUDITOR)){
			//根据栏目ID查询文章					
			User auditor = article.getAuditor();
			if(auditor != null) {
				String strauditor = StringUtil.toString(auditor.getName());
				str = this.proccessSubLabel(strauditor);
			} 
		
		// 栏目名称
		} else if(label.equals(CommonLabel.COLUMN_NAME)) {
			if(article.getColumn() != null) {
				String columnName = article.getColumn().getName();
				str = this.proccessSubLabel(columnName);
			}
		
		} else if(label.equals(TitleLinkLabel.COLUMN_LINK)) {
			// 栏目链接
			if(article.getColumn() != null) {
				String columnType = article.getColumn().getColumnType();
				String url = "";
				// 如果是单信息栏目
				if(columnType.equals("single")){
					String columnid = article.getColumn().getId();
					if(!StringUtil.isEmpty(columnid)){
						List<Article> list = articleDao.findByNamedQuery("findAuditedArticlesByColumnId", "columnId", columnid, 0, 1);
						if(!CollectionUtil.isEmpty(list)){
							url = SiteResource.getUrl(list.get(0).getUrl(), true);
						}
					}
				}else{
					url = SiteResource.getUrl(article.getColumn().getUrl(), true);
				}
				str = this.proccessSubLabel(url);
			}
		
		}else if(label.equals(TitleLinkLabel.ARTICLEBRIEF)){
			//信息摘要
			String brief = StringUtil.toString(article.getBrief());
			str = this.proccessSubLabel(brief);
			
		}else if(label.equals(TitleLinkLabel.ARTICLECREATEDATE)){
			//信息创建时间
			Date createTime = article.getCreateTime();
			String time = DateUtil.toString(createTime,"yyyy-MM-dd HH:mm:ss");	
			str = this.proccessSubLabel(time);
						
		}else if(label.equals(TitleLinkLabel.ARTICLEDEPLOYTIME)){
			//信息显示时间
			if(article.getDisplayTime() != null ){
				Date displayTime = article.getDisplayTime();
				String time = DateUtil.toString(displayTime,"yyyy-MM-dd HH:mm:ss");			
				str = this.proccessSubLabel(time);
			}
			
		}else if(label.equals(TitleLinkLabel.ARTICLEEDITOR)){
			//信息录入人 		
			User user = article.getCreator();
			if(user != null) {
				String creatorName = user.getName();
				str = this.proccessSubLabel(creatorName);
			}
			
		}else if(label.equals(TitleLinkLabel.ARTICLEENDER)){
			//标题后缀						
			String titleEndValidity = String.valueOf(xmlUtil.getNodeText("j2ee.cms/title-link/titleEndValidity"));
			if(!StringUtil.isEmpty(titleEnd)) {
				String newdate = DateUtil.getAddDaysDateFormat(DateUtil.toStringTrim(new Date(), "yyyy-MM-dd"), StringUtil.parseInt(titleEndValidity));
				int isSuffixPic = StringUtil.parseInt(endPic);
				String id = IDFactory.getId();
				// 后缀是否是图片	
				if(isSuffixPic == 1) {
					if(StringUtil.parseInt(titleEndValidity) >= 0) {
						str = "<img id=\""+ id +"\" src=\"" + SiteResource.getUrl(titleEnd, true) +"\" style=\"display:none\"/> <script>if(checkDate('" + newdate + "')){ document.getElementById(\""+ id +"\").style.display=\"\";}</script>";	
					} else {
						str = "<img src=\"" + SiteResource.getUrl(titleEnd, true) +"\"/>";
					}
				} else if(isSuffixPic == 0) {
					if(StringUtil.parseInt(titleEndValidity) >= 0) {
						str = "<span id=\""+ id +"\" style=\"display:none\">"+ titleEnd +"</span> <script>if(checkDate('" + newdate + "')){ document.getElementById(\""+ id +"\").style.display=\"\";}</script> ";
					} else {
						str = titleEnd;
					}
				} 
			}
			
		}else if(label.equals(TitleLinkLabel.ARTICLEHEADER)){
			// 标题前缀
			String titleHeadValidity = String.valueOf(xmlUtil.getNodeText("j2ee.cms/title-link/titleHeadValidity"));
			if(!StringUtil.isEmpty(titleHead)) {
				String newdate = DateUtil.getAddDaysDateFormat(DateUtil.toStringTrim(new Date(), "yyyy-MM-dd"), StringUtil.parseInt(titleHeadValidity));
				int isprefixPic = StringUtil.parseInt(headPic);
				String id = IDFactory.getId();
				// 前缀是否是图片	
				if(isprefixPic == 1) {
					if(StringUtil.parseInt(titleHeadValidity) >= 0) {
						str = "<img id=\""+ id +"\" src=\"" + SiteResource.getUrl(titleHead, true) +"\" style=\"display:none\"/> <script>if(checkDate('" + newdate + "')){ document.getElementById(\""+ id +"\").style.display=\"\";}</script>";	
					} else {
						str = "<img src=\"" + SiteResource.getUrl(titleHead, true) +"\"/>";
					}
				} else if(isprefixPic == 0) {
					if(StringUtil.parseInt(titleHeadValidity) >= 0) {
						str = "<span id=\""+ id +"\" style=\"display:none\">"+ titleHead +"</span> <script>if(checkDate('" + newdate + "')){ document.getElementById(\""+ id +"\").style.display=\"\";}</script> ";
					} else {
						str = titleHead;
					}
				}
			}
			
		}else if(label.equals(TitleLinkLabel.ARTICLEHITS)){
			//信息访问次数 
			int hits = article.getHits();
			String hit = StringUtil.toString(String.valueOf(hits));
			str = this.proccessSubLabel(hit);
			
		}else if(label.equals(TitleLinkLabel.ARTICLEKEYWORDS)){
			//信息关键字 
			String keyword = StringUtil.toString(article.getKeyword());
			str = this.proccessSubLabel(keyword);
			
		}else if(label.equals(TitleLinkLabel.ARTICLELINKTITLE)){
			// 链接标题
			String url = StringUtil.toString(article.getUrl());
			if(!StringUtil.isEmpty(url)) {
				url = SiteResource.getUrl(url, true);
				str = url;
			} 
			
		}else if(label.equals(TitleLinkLabel.ARTICLELINKTITLESHORT)){
			//链接标题(缩) 
			String url = article.getUrl();
			if(!StringUtil.isEmpty(url)) {
				url = SiteResource.getUrl(url, true);
				str = url;
			} 
			
		}else if(label.equals(TitleLinkLabel.ARTICLEQUOTETITLE)){
			//引题
			String leadingTitle = StringUtil.toString(article.getLeadingTitle());
			str = this.proccessSubLabel(leadingTitle);
			
		}else if(label.equals(TitleLinkLabel.ARTICLEQUOTETITLESHORT)){
			//引题(缩) 
			String leadingTitle = article.getLeadingTitle();
			if(leadingTitle != null && titleLimit != null && !titleLimit.equals("") && !titleLimit.equals("0")){
				if(StringUtil.parseInt(titleLimit) > 0) {
					if(leadingTitle.length() >= StringUtil.parseInt(titleLimit)){
						leadingTitle = leadingTitle.substring(0, StringUtil.parseInt(titleLimit))+"...";
					} 	
				}
				str = leadingTitle;
			}
			
		}else if(label.equals(TitleLinkLabel.ARTICLESORCE)){
			//信息来源 
			String infoSource = article.getInfoSource();
			str = this.proccessSubLabel(infoSource);
			
		}else if(label.equals(TitleLinkLabel.ARTICLESUBTITLE)){
			//副标题 
			String subtitle = StringUtil.toString(article.getSubtitle());
			str = this.proccessSubLabel(subtitle);
			
		}else if(label.equals(TitleLinkLabel.ARTICLESUBTITLESHORT)){
			//副标题(缩) 
			String subtitle = article.getSubtitle();
			if(subtitle != null && titleLimit != null && !titleLimit.equals("") && !titleLimit.equals("0")){
				if(StringUtil.parseInt(titleLimit) > 0) {
					if(subtitle.length() >= StringUtil.parseInt(titleLimit)){
						subtitle = subtitle.substring(0, StringUtil.parseInt(titleLimit))+"...";
					}	
				}
				str = subtitle;
			}
			
		}else if(label.equals(TitleLinkLabel.ARTICLETITLE)){				
			//标题				
			String title = StringUtil.toString(article.getTitle());
			str = this.proccessSubLabel(title);
			
		}else if(label.equals(TitleLinkLabel.ARTICLETITLESHORT)){
			//标题(缩) 
			String title = article.getTitle();
			if(title != null && titleLimit != null && !titleLimit.equals("") && !titleLimit.equals("0")){
				if(StringUtil.parseInt(titleLimit) > 0) {
					if(title.length() >= StringUtil.parseInt(titleLimit)){
						title = title.substring(0, StringUtil.parseInt(titleLimit))+"...";
					}	
				}
				str = title;
			}
			
		}else if(label.equals(TitleLinkLabel.ARTICLEURL)){
			//标题链接
			String url = StringUtil.toString(article.getUrl());
			if(!StringUtil.isEmpty(url)){
				str = SiteResource.getUrl(url, true);		
			}
		
		}else if(label.equals(TitleLinkLabel.HOUR)){
			//时
			Date date = article.getDisplayTime();
			if(date != null){
				String strdate = DateUtil.toString(date,"HH");			
				str = strdate;	
			}
			
		}else if(label.equals(TitleLinkLabel.MINUTE)){
			//分
			Date date = article.getDisplayTime();
			if(date != null) {
				String strdate = DateUtil.toString(date,"mm");			
				str = strdate;
			} 
			
		}else if(label.equals(TitleLinkLabel.SECOND)){
			//秒
			Date date = article.getDisplayTime();
			if(date != null){
				String strdate = DateUtil.toString(date,"ss");		
				str = strdate;
			}
			
		}else if(label.equals(TitleLinkLabel.YEAR2)){
			//两位年 
			Date date = article.getDisplayTime();
			if(date != null){
				String strdate = DateUtil.toString(date,"yy");
				str = strdate;
			}
			
		}else if(label.equals(TitleLinkLabel.YEAR4)){
			//四位年 
			Date date = article.getDisplayTime();
			if(date != null){
				String strdate = DateUtil.toString(date,"yyyy");
				str = strdate;
			}
			
		}else if(label.equals(TitleLinkLabel.MONTH1)){
			//一位月
			Date date = article.getDisplayTime();
			if(date != null){
				String strdate = DateUtil.toString(date,"yyyy-MM-dd");							
				String str1[] = strdate.split("-");		
				if(str1 != null && !str1.equals("") && str1.length >= 2){
					str = String.valueOf(StringUtil.parseInt(str1[1]));
				}
			}
			
		}else if(label.equals(TitleLinkLabel.MONTH2)){
			//两位月 
			Date date = article.getDisplayTime();
			if(date != null){
				String strdate = DateUtil.toString(date,"yyyy-MM-dd");	
				String str1[] = strdate.split("-");			
				if(str1 != null && !str1.equals("") && str1.length >= 2){
					str = String.valueOf(str1[1]);
				}		
			}
			
		}else if(label.equals(TitleLinkLabel.DAY1)){
			//一位日 
			Date date = article.getDisplayTime();
			if(date != null){
				String strdate = DateUtil.toString(date,DateUtil.FORMAT_DATE_1);
				String str1[] = strdate.split("-");	
				if(str1 != null && !str1.equals("") && str1.length >= 3){
					str = String.valueOf(StringUtil.parseInt(str1[2]));
				}
			}
			
		}else if(label.equals(TitleLinkLabel.DAY2)){
			//两位日 
			Date date = article.getDisplayTime();
			if(date != null){
				String strdate = DateUtil.toString(date,DateUtil.FORMAT_DATE_1);		
				String str1[] = strdate.split("-");	
				if(str1 != null && !str1.equals("") && str1.length >= 3){
					str = String.valueOf(str1[2]);
				}
			}
			
		}else {
			// 字段标签	
			String other = "<!--(.*)-->";
			Pattern p = Pattern.compile(other);
			Matcher m = p.matcher(label);
			String getOtherValue = "";
			if(m.find()) {
				getOtherValue = m.group(1);
				if(!getOtherValue.equals("if") && !getOtherValue.equals("for")){
					Object obj = BeanUtil.getFieldValue(article, "com.j2ee.cms.biz.articlemanager.domain.Article", getOtherValue);
					log.debug("obj========"+obj);
					// 是日期
					if (obj instanceof Date) {
						obj = DateUtil.toString((Date)obj, "yyyy-MM-dd HH:mm:ss");
					}
					String str1 = String.valueOf(obj);
					if(str1.equals(false) || str1.equals("false")){
						str1 = "否";
					}else if(str1.equals(true) || str1.equals("true")){
						str1 = "是";
					}
					// 是图片、附件、媒体 
					if(getOtherValue.startsWith("pic")
							|| getOtherValue.startsWith("attach")
							|| getOtherValue.startsWith("media")) {
						if(!StringUtil.isEmpty(str1) && !str1.equals("null")) {
							str1 = SiteResource.getUrl(str1, true);
						}
					}
					str = this.proccessSubLabel(str1);
				}		
			}
		}
		return str;
	}
	
	/**
	 * 处理sub截取数据
	 * @param labelMatcher
	 * @param sb
	 * @param data
	 */
	private String proccessSubLabel(String data) {
		if(!StringUtil.isEmpty(data) && !data.equals("null")) {					
			return data;
		} else {
			return "";
		}
	}
	
	/**
	 * 控制if语句，直到没有在出现if语句的情况
	 * @param ifSrc
	 * @param unitId
	 * @param columnId
	 * @return
	 */
	public  String getData(String ifSrc, String unitId, String columnId, Article article, String siteId) throws Exception{
		String str = ifSrc;
		while(true) {
			Pattern ifPattern= Pattern.compile(CommonLabel.IF);
			Matcher ifMatcher = ifPattern.matcher(str);
			//  如果还有与<!--if--><!--else--><!--/if-->匹配的
			if(ifMatcher.find()) {
				str = getIfData(str, unitId, columnId, article, siteId);	
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
	public  String getIfData(String ifsrc, String unitId, String columnId, Article article, String siteId) throws Exception{
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
					str = this.getIfData(ifStartMatcher.group(), unitId, columnId, article, siteId);	
					sb2.append(str);
					ifStartMatcher.appendReplacement(sb, sb2.toString());
				} 
			}
			if(a == 1) {
				ifStartMatcher.appendTail(sb);
				a = 2;
			}
			if(a==0) {
				String str = getIFAndElseAndIFData(ifsrc, xmlUtil, columnId, article, unitId, siteId);
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
	private String getIFAndElseAndIFData(String str, XmlUtil xmlUtil, String columnId, Article article, String unitId, String siteId) throws Exception{
		Pattern p = Pattern.compile(CommonLabel.IF);
		Matcher m = p.matcher(str);
		StringBuffer sb = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		Column column = new Column();
		if(columnId != null && !columnId.equals("") && !columnId.equals("0")) {
			column = columnDao.getAndClear(columnId);
		}
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
				changeCode = getIfStartOrIfEnd(label, unitId, column, xmlUtil, article, siteId);
				sb2.append(changeCode);
			}
			Pattern ifEndPattern1 = Pattern.compile(CommonLabel.IF_END);
			Matcher ifEndMatcher1 = ifEndPattern1.matcher(m.group());
			if(StringUtil.isEmpty(changeCode)) {
				if(ifEndMatcher1.find()) {
					// <!--else--><!--/if-->
					String label = ifEndMatcher1.group(1);
					changeCode = this.sub(label, unitId, columnId, siteId, article);
					sb2.append(changeCode);
				}
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
	private String getIfStartOrIfEnd(String src, String unitId, Column column, XmlUtil xmlUtil, Article article, String siteId) throws Exception{
		Site site = siteDao.getAndClear(siteId);
		StringBuffer sb = new StringBuffer();
		Pattern labelPatttern = Pattern.compile(CommonLabel.REGEX_LABEL);	
		Matcher labelMatcher = labelPatttern.matcher(src);	
		int a = 0;
		String str = "";
		while (labelMatcher.find() ) {
			String label = "";	
			label = labelMatcher.group();	
			// 更多链接
			if(label.equals(TitleLinkLabel.MORELINK)) {
				if(displayMore) {
					String moreLink = String.valueOf(xmlUtil.getNodeText("j2ee.cms/title-link/moreLink"));
					//是否是图片
					String moreLinkPic = String.valueOf(xmlUtil.getNodeText("j2ee.cms/title-link/moreLinkPic"));
					String url = "";
					String str1 = "";
					String str2 = "";
					if(column != null) {
						url = column.getUrl();
						str1 = "<a href=\""+ SiteResource.getUrl(url, true) +"\"><img src=\""+ SiteResource.getUrl(moreLink, true) +"\"/></a>";
						str2 = "<a href=\""+ SiteResource.getUrl(url, true) +"\">"+ moreLink +"</a>";
					} else {
						str1 = "<a href=\"#\"><img src=\""+ SiteResource.getUrl(moreLink, true) +"\"/></a>";
						str2 = "<a href=\"#\">"+ moreLink +"</a>";
					}
					if(moreLinkPic != null && !moreLinkPic.equals("") && !moreLinkPic.equals("0")) {
						str = this.proccessLabel(labelMatcher, str, a, sb, str1);
					} else {
						str = this.proccessLabel(labelMatcher, str, a, sb, str2);
					}
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
				
			// 网站名称	
			}else  if(label.equals(CommonLabel.SITE_NAME)) {
				String siteName = site.getName();
				str = this.proccessLabel(labelMatcher, str, a, sb, siteName);
			
			// 网站链接	
			}else if(label.equals(CommonLabel.SITE_LINK)) {
				String siteUrl = site.getUrl();
				if(!StringUtil.isEmpty(siteUrl)) {
					str = this.proccessLabel(labelMatcher, str, a, sb, SiteResource.getUrl(siteUrl, true));
				} else{
					labelMatcher.appendReplacement(sb, "");
				}

			// 栏目名称
			}else if(label.equals(CommonLabel.COLUMN_NAME)) {
				if(column != null) {
					String columnName = column.getName();
					str = this.proccessLabel(labelMatcher, str, a, sb, columnName);
				} else {
					labelMatcher.appendReplacement(sb, "");
				}

			// 其他标签	
			}else{
				String data = ""; 
				data += this.setData(article, label, xmlUtil);
				str = this.proccessLabel(labelMatcher, str, a, sb, data);
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
	 * 获取页面所选择的栏目
	 * @param columnId
	 * @param siteId
	 * @param filePath
	 * @return
	 */
	private String getColumnId(String columnId, String siteId,String filePath){
		XmlUtil xmlUtil = XmlUtil.getInstance(filePath);
		//内容来源
		String contextFrom = xmlUtil.getNodeText("j2ee.cms/title-link/contextFrom");
		//栏目名称
		String columnName = xmlUtil.getNodeText("j2ee.cms/title-link/columnName");
		String fixedColumnId = "";
		String strColumn[] = columnName.split("##");
		if(strColumn != null && strColumn.length == 2){
			fixedColumnId = strColumn[0];
		}
		String morecolumnName = xmlUtil.getNodeText("j2ee.cms/title-link/moreColumnName");
		String morefixedColumnId = "";
		String morestrColumn[] = morecolumnName.split("##");
		if(morestrColumn != null && morestrColumn.length == 2){
			morefixedColumnId = morestrColumn[0];
		}
		String newColumnId = "";
		Column column = new Column();
		if(columnId != null && !columnId.equals("0") && !columnId.equals("")) {
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
			newColumnId = morefixedColumnId;
			
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
