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

					Element siteName = articles.addElement("siteName");
					siteName.addCDATA(article.getSite().getName());

					Element siteLink = articles.addElement("siteLink");
					siteLink.addCDATA(article.getSite().getUrl());

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

					Element articlequotetitle = articles.addElement("articlequotetitle");
					articlequotetitle.addCDATA(article.getLeadingTitle());

					Element articlebrief = articles.addElement("articlebrief");
					articlebrief.addCDATA(article.getBrief());

					Element articleauthor = articles.addElement("articleauthor");
					articleauthor.addCDATA(article.getAuthor());

					Element articlehits = articles.addElement("articlehits");
					articlehits.addCDATA(String.valueOf(article.getHits()));

					Element createTime = articles.addElement("createTime");

					createTime.addCDATA(DateUtil.toString(article.getCreateTime()));

					Element displayTime = articles.addElement("displayTime");
					displayTime.addCDATA(DateUtil.toString(article.getDisplayTime()));

					Element auditTime = articles.addElement("auditTime");
					auditTime.addCDATA(DateUtil.toString(article.getAuditTime()));

					Element publishTime = articles.addElement("publishTime");
					publishTime.addCDATA(DateUtil.toString(article.getPublishTime()));

					Element invalidTime = articles.addElement("invalidTime");
					invalidTime.addCDATA(DateUtil.toString(article.getInvalidTime()));

					Element pic1 = articles.addElement("pic1");
					pic1.addCDATA(article.getPic1());
					
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
					}
					// 置顶
					Element toped = articles.addElement("toped");
					if(article.isToped()) {
						toped.addCDATA("是");						
					} else {
						toped.addCDATA("否");
					}
					// 日期 date   10
				/*	Element date1 = articles.addElement("date1");
					date1.addCDATA(DateUtil.toString(article.getDate1()));
					Element date2 = articles.addElement("date2");
					date2.addCDATA(DateUtil.toString(article.getDate2()));
					Element date3 = articles.addElement("date3");
					date3.addCDATA(DateUtil.toString(article.getDate3()));
					Element date4 = articles.addElement("date4");
					date4.addCDATA(DateUtil.toString(article.getDate4()));
					Element date5 = articles.addElement("date5");
					date5.addCDATA(DateUtil.toString(article.getDate5()));
					Element date6 = articles.addElement("date6");
					date6.addCDATA(DateUtil.toString(article.getDate6()));
					Element date7 = articles.addElement("date7");
					date7.addCDATA(DateUtil.toString(article.getDate7()));
					Element date8 = articles.addElement("date8");
					date8.addCDATA(DateUtil.toString(article.getDate8()));
					Element date9 = articles.addElement("date9");
					date9.addCDATA(DateUtil.toString(article.getDate9()));
					Element date10 = articles.addElement("date10");
					date10.addCDATA(DateUtil.toString(article.getDate10()));
					// 文本类型 text 25
					Element text1 = articles.addElement("text1");
					text1.addCDATA(article.getText1());
					Element text2 = articles.addElement("text2");
					text2.addCDATA(article.getText2());
					Element text3 = articles.addElement("text3");
					text3.addCDATA(article.getText3());
					Element text4 = articles.addElement("text4");
					text4.addCDATA(article.getText4());
					Element text5 = articles.addElement("text5");
					text5.addCDATA(article.getText5());
					Element text6 = articles.addElement("text6");
					text6.addCDATA(article.getText6());
					Element text7 = articles.addElement("text7");
					text7.addCDATA(article.getText7());
					Element text8 = articles.addElement("text8");
					text8.addCDATA(article.getText8());
					Element text9 = articles.addElement("text9");
					text9.addCDATA(article.getText9());
					Element text10 = articles.addElement("text10");
					text10.addCDATA(article.getText10());
					Element text11 = articles.addElement("text11");
					text11.addCDATA(article.getText11());
					Element text12 = articles.addElement("text12");
					text12.addCDATA(article.getText12());
					Element text13 = articles.addElement("text13");
					text13.addCDATA(article.getText13());
					Element text14 = articles.addElement("text14");
					text14.addCDATA(article.getText14());
					Element text15 = articles.addElement("text15");
					text15.addCDATA(article.getText15());
					Element text16 = articles.addElement("text16");
					text16.addCDATA(article.getText16());
					Element text17 = articles.addElement("text17");
					text17.addCDATA(article.getText17());
					Element text18 = articles.addElement("text18");
					text18.addCDATA(article.getText18());
					Element text19 = articles.addElement("text19");
					text19.addCDATA(article.getText19());
					Element text20 = articles.addElement("text20");
					text20.addCDATA(article.getText20());
					Element text21 = articles.addElement("text21");
					text21.addCDATA(article.getText21());
					Element text22 = articles.addElement("text22");
					text22.addCDATA(article.getText22());
					Element text23 = articles.addElement("text23");
					text23.addCDATA(article.getText23());
					Element text24 = articles.addElement("text24");
					text24.addCDATA(article.getText24());
					Element text25 = articles.addElement("text25");
					text25.addCDATA(article.getText25());
					// 文本域类型  textArea 10
					Element textArea1 = articles.addElement("textArea1");
					textArea1.addCDATA(article.getTextArea1());
					Element textArea2 = articles.addElement("textArea2");
					textArea2.addCDATA(article.getTextArea2());
					Element textArea3 = articles.addElement("textArea3");
					textArea3.addCDATA(article.getTextArea3());
					Element textArea4 = articles.addElement("textArea4");
					textArea4.addCDATA(article.getTextArea4());
					Element textArea5 = articles.addElement("textArea5");
					textArea5.addCDATA(article.getTextArea5());
					Element textArea6 = articles.addElement("textArea6");
					textArea6.addCDATA(article.getTextArea6());
					Element textArea7 = articles.addElement("textArea7");
					textArea7.addCDATA(article.getTextArea7());
					Element textArea8 = articles.addElement("textArea8");
					textArea8.addCDATA(article.getTextArea8());
					Element textArea9 = articles.addElement("textArea9");
					textArea9.addCDATA(article.getTextArea9());
					Element textArea10 = articles.addElement("textArea10");
					textArea10.addCDATA(article.getTextArea10());
					// 整型   integer 10  
					Element integer1 = articles.addElement("integer1");
					integer1.addCDATA(String.valueOf(article.getInteger1()));
					Element integer2 = articles.addElement("integer2");
					integer2.addCDATA(String.valueOf(article.getInteger2()));
					Element integer3 = articles.addElement("integer3");
					integer3.addCDATA(String.valueOf(article.getInteger3()));
					Element integer4 = articles.addElement("integer4");
					integer4.addCDATA(String.valueOf(article.getInteger4()));
					Element integer5 = articles.addElement("integer5");
					integer5.addCDATA(String.valueOf(article.getInteger5()));
					Element integer6 = articles.addElement("integer6");
					integer6.addCDATA(String.valueOf(article.getInteger6()));
					Element integer7 = articles.addElement("integer7");
					integer7.addCDATA(String.valueOf(article.getInteger7()));
					Element integer8 = articles.addElement("integer8");
					integer8.addCDATA(String.valueOf(article.getInteger8()));
					Element integer9 = articles.addElement("integer9");
					integer9.addCDATA(String.valueOf(article.getInteger9()));
					Element integer10 = articles.addElement("integer10");
					integer10.addCDATA(String.valueOf(article.getInteger10()));
					// 浮点型  float 10 
					Element float1 = articles.addElement("float1");
					float1.addCDATA(String.valueOf(String.valueOf(article.getFloat1())));
					Element float2 = articles.addElement("float2");
					float2.addCDATA(String.valueOf(String.valueOf(article.getFloat2())));
					Element float3 = articles.addElement("float3");
					float3.addCDATA(String.valueOf(String.valueOf(article.getFloat3())));
					Element float4 = articles.addElement("float04");
					float4.addCDATA(String.valueOf(String.valueOf(article.getFloat04())));
					Element float5 = articles.addElement("float5");
					float5.addCDATA(String.valueOf(String.valueOf(article.getFloat5())));
					Element float6 = articles.addElement("float6");
					float6.addCDATA(String.valueOf(String.valueOf(article.getFloat6())));
					Element float7 = articles.addElement("float7");
					float7.addCDATA(String.valueOf(String.valueOf(article.getFloat7())));
					Element float8 = articles.addElement("float08");
					float8.addCDATA(String.valueOf(String.valueOf(article.getFloat08())));
					Element float9 = articles.addElement("float9");
					float9.addCDATA(String.valueOf(String.valueOf(article.getFloat9())));
					Element float10 = articles.addElement("float10");
					float10.addCDATA(String.valueOf(String.valueOf(article.getFloat10())));*/
					// 布尔型 bool   10
					Element bool1 = articles.addElement("bool1");
					if(article.isBool1()) {
						bool1.addCDATA("是");	
					} else {
						bool1.addCDATA("否");
					}
					Element bool2 = articles.addElement("bool2");
					if(article.isBool2()) {
						bool2.addCDATA("是");	
					} else {
						bool2.addCDATA("否");
					}
				/*	Element bool3 = articles.addElement("bool3");
					if(article.isBool3()) {
						bool3.addCDATA("是");	
					} else {
						bool3.addCDATA("否");
					}
					Element bool4 = articles.addElement("bool4");
					if(article.isBool4()) {
						bool4.addCDATA("是");	
					} else {
						bool4.addCDATA("否");
					}
					Element bool5 = articles.addElement("bool5");
					if(article.isBool5()) {
						bool5.addCDATA("是");	
					} else {
						bool5.addCDATA("否");
					}
					Element bool6 = articles.addElement("bool6");
					if(article.isBool6()) {
						bool6.addCDATA("是");	
					} else {
						bool6.addCDATA("否");
					}
					Element bool7 = articles.addElement("bool7");
					if(article.isBool7()) {
						bool7.addCDATA("是");	
					} else {
						bool7.addCDATA("否");
					}
					Element bool8 = articles.addElement("bool8");
					if(article.isBool8()) {
						bool8.addCDATA("是");	
					} else {
						bool8.addCDATA("否");
					}
					Element bool9 = articles.addElement("bool9");
					if(article.isBool9()) {
						bool9.addCDATA("是");	
					} else {
						bool9.addCDATA("否");
					}
					Element bool10 = articles.addElement("bool10");
					if(article.isBool10()) {
						bool10.addCDATA("是");	
					} else {
						bool10.addCDATA("否");
					}*/
					// 图片 pic      2~10  
				/*	Element pic2 = articles.addElement("pic2");
					pic2.addCDATA(article.getPic2());
					Element pic3 = articles.addElement("pic3");
					pic3.addCDATA(article.getPic3());
					Element pic4 = articles.addElement("pic4");
					pic4.addCDATA(article.getPic4());
					Element pic5 = articles.addElement("pic5");
					pic5.addCDATA(article.getPic5());
					Element pic6 = articles.addElement("pic6");
					pic6.addCDATA(article.getPic6());
					Element pic7 = articles.addElement("pic7");
					pic7.addCDATA(article.getPic7());
					Element pic8 = articles.addElement("pic8");
					pic8.addCDATA(article.getPic8());
					Element pic9 = articles.addElement("pic9");
					pic9.addCDATA(article.getPic9());
					Element pic10 = articles.addElement("pic10");
					pic10.addCDATA(article.getPic10());*/
					// 附件 attach   10
					Element attache1 = articles.addElement("attach1");
					attache1.addCDATA(article.getAttach1());
					Element attache2 = articles.addElement("attach2");
					attache2.addCDATA(article.getAttach2());
				/*	Element attache3 = articles.addElement("attach3");
					attache3.addCDATA(article.getAttach3());
					Element attache4 = articles.addElement("attach4");
					attache4.addCDATA(article.getAttach4());
					Element attache5 = articles.addElement("attach5");
					attache5.addCDATA(article.getAttach5());
					Element attache6 = articles.addElement("attach6");
					attache6.addCDATA(article.getAttach6());
					Element attache7 = articles.addElement("attach7");
					attache7.addCDATA(article.getAttach7());
					Element attache8 = articles.addElement("attach8");
					attache8.addCDATA(article.getAttach8());
					Element attache9 = articles.addElement("attach9");
					attache9.addCDATA(article.getAttach9());
					Element attache10 = articles.addElement("attach10");
					attache10.addCDATA(article.getAttach10());
					// 媒体 media    10
					Element media1 = articles.addElement("media1");
					media1.addCDATA(article.getMedia1());
					Element media2 = articles.addElement("media2");
					media2.addCDATA(article.getMedia2());
					Element media3 = articles.addElement("media3");
					media3.addCDATA(article.getMedia3());
					Element media4 = articles.addElement("media4");
					media4.addCDATA(article.getMedia4());
					Element media5 = articles.addElement("media5");
					media5.addCDATA(article.getMedia5());
					Element media6 = articles.addElement("media6");
					media6.addCDATA(article.getMedia6());
					Element media7 = articles.addElement("media7");
					media7.addCDATA(article.getMedia7());
					Element media8 = articles.addElement("media8");
					media8.addCDATA(article.getMedia8());
					Element media9 = articles.addElement("media9");
					media9.addCDATA(article.getMedia9());
					Element media10 = articles.addElement("media10");
					media10.addCDATA(article.getMedia10());
					// 枚举 enumeration 5
					Element enumeration1 = articles.addElement("enumeration1");
					enumeration1.addCDATA(article.getEnumeration1());
					Element enumeration2 = articles.addElement("enumeration2");
					enumeration2.addCDATA(article.getEnumeration2());
					Element enumeration3 = articles.addElement("enumeration3");
					enumeration3.addCDATA(article.getEnumeration3());
					Element enumeration4 = articles.addElement("enumeration4");
					enumeration4.addCDATA(article.getEnumeration4());
					Element enumeration5 = articles.addElement("enumeration5");
					enumeration5.addCDATA(article.getEnumeration5());*/
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
