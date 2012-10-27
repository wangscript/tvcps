/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.plugin.onlinesurvey.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.jdbc.core.JdbcTemplate;

import com.j2ee.cms.biz.sitemanager.dao.SiteDao;
import com.j2ee.cms.biz.sitemanager.domain.Site;
import com.j2ee.cms.biz.templatemanager.dao.TemplateUnitDao;
import com.j2ee.cms.biz.templatemanager.domain.TemplateUnit;
import com.j2ee.cms.biz.unitmanager.label.CommonLabel;
import com.j2ee.cms.biz.unitmanager.label.OnlineSurverySetLabel;
import com.j2ee.cms.plugin.onlinesurvey.dao.OnlineSurveyAnswerContentDao;
import com.j2ee.cms.plugin.onlinesurvey.dao.OnlineSurveyContentDao;
import com.j2ee.cms.plugin.onlinesurvey.dao.OnlineSurveyDao;
import com.j2ee.cms.plugin.onlinesurvey.dao.OnlinefeedbackContentDao;
import com.j2ee.cms.plugin.onlinesurvey.domain.OnlineSurvey;
import com.j2ee.cms.plugin.onlinesurvey.domain.OnlineSurveyContent;
import com.j2ee.cms.plugin.onlinesurvey.domain.OnlineSurveyContentAnswer;
import com.j2ee.cms.plugin.onlinesurvey.domain.OnlinefeedbackContent;
import com.j2ee.cms.plugin.onlinesurvey.service.OnlineSurveyService;
import com.j2ee.cms.sys.GlobalConfig;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.pager.PageQuery;
import com.j2ee.cms.common.core.util.CollectionUtil;
import com.j2ee.cms.common.core.util.FileUtil;
import com.j2ee.cms.common.core.util.SqlUtil;
import com.j2ee.cms.common.core.util.StringUtil;

/**
 * <p>标题: 网上调查业务类</p>
 * <p>描述: 网上调查的内容,答案，等业务</p>
 * <p>模块: 网上调查</p>
 * <p>版权: Copyright (c) 2009 
 * @author 包坤涛
 * @version 1.0
 * @since 2009-6-14 下午02:30:23 
 * @history（历次修订内容、修订人、修订时间等） 
 */

public class OnlineSurveyServiceImpl implements OnlineSurveyService {

	private final Logger log = Logger.getLogger(OnlineSurveyServiceImpl.class);
	
	/** 注入前台JdbcTemplate. **/
	private JdbcTemplate jdbcTemplate;
	/** 网上调查问题名称表注入 */
	private OnlineSurveyDao onlineSurveyDao;
	/** 网上调查问题具体内容表注入 */
	private OnlineSurveyContentDao onlineSurveyContentDao;
	/** 单选框或多选框的值*/
	private OnlineSurveyAnswerContentDao onlineSurveyAnswerContentDao;
    /** 注入网站dao */
	private SiteDao siteDao;
	/** 注入模版单元dao */
	private TemplateUnitDao templateUnitDao;
	/** 注入反馈dao */
	private OnlinefeedbackContentDao onlinefeedbackContentDao;
	
	
	public List<OnlineSurvey> findOnlineSurvey(String categoryId){
		return onlineSurveyDao.findByNamedQuery("findonlineSurveyOfEntitly", "categoryId", categoryId);
	}
	
	public Pagination finOnlineSurveyData(Pagination pagination, String categoryId){
		if (!StringUtil.isEmpty(categoryId)) {
			return onlineSurveyDao.getPagination(pagination, "categoryId", categoryId);
		} else {
			return new Pagination();
		}
	}
	
	public void addOnlineSurvey(OnlineSurvey onlineSurvey){
		onlineSurveyDao.save(onlineSurvey);
	}
	
	public void modifyOnlineSurvey(OnlineSurvey onlineSurvey, String nodeId){
		OnlineSurvey onlineSurveyOne = onlineSurveyDao.getAndNonClear(nodeId);
		String name = onlineSurvey.getName();
		onlineSurveyOne.setName(name);
		String description = onlineSurvey.getDescription();
		onlineSurveyOne.setDescription(description);
		onlineSurveyOne.setStopTime(onlineSurvey.getStopTime());
		onlineSurveyDao.update(onlineSurveyOne);
	}
	
	public OnlineSurvey findEntitlyOnline(String nodeId) {
		return onlineSurveyDao.getAndNonClear(nodeId);
	}
	
	public void deleteOnlineSurvey(String nodeId){
		nodeId = SqlUtil.toSqlString(nodeId);
		
		//删除问题答案
		List<OnlineSurveyContentAnswer> list =  new ArrayList<OnlineSurveyContentAnswer>();
		list = onlineSurveyAnswerContentDao.findByDefine("findAnswerByOnlineSurveryId", "nodeId", nodeId);
		if(!CollectionUtil.isEmpty(list)){
			for(int i = 0; i < list.size(); i++){
				OnlineSurveyContentAnswer onlineSurveyContentAnswer = list.get(i);
				String id = onlineSurveyContentAnswer.getId();
				onlineSurveyAnswerContentDao.deleteByKey(id);
			}
		}
		
		//删除问题
		onlineSurveyContentDao.updateByDefine("deleteQuestionByOnlineSurveryId", "nodeId", nodeId);
		
		//删除主题
		onlineSurveyDao.deleteByIds(nodeId);
	}
	
	
	public String getNormalDetailHtml(String unitId, String themeId, String questionId, String display, String appName){
		if(StringUtil.isEmpty(display)){
			display = "";
		}
		StringBuffer sb = new StringBuffer();
		StringBuffer sb1 = new StringBuffer();
		StringBuffer sb3 = new StringBuffer();
		TemplateUnit templateUnit = templateUnitDao.getAndNonClear(unitId);
		//读取配置文件
		String conf = GlobalConfig.appRealPath +"/"+ templateUnit.getConfigFile();
		Site site = new Site();
		site = siteDao.getAndClear(templateUnit.getSite().getId());
		//是发布之后页面显示
		if(!FileUtil.isExist(conf)){
			String siteName = site.getPublishDir();
			siteName = siteName.split("/")[siteName.split("/").length-1];
			String instanceId = templateUnit.getTemplateInstance().getId();
			String co = templateUnit.getConfigFile();
			String xml = co.split("/")[co.split("/").length-1];
			conf = GlobalConfig.appRealPath + "/" + siteName + "/template_instance/" + instanceId+"/conf/"+xml;
		}
		SAXReader sr = new SAXReader();
		Document document;
		String row = "";
		String col = "";
		String listCount = "";
		String more = "";
		try {
			document = sr.read(new File(conf));
			Element e = (Element) document.getRootElement().elementIterator("online_survery").next();
			row = e.elementText("rowCount");
			col = e.elementText("colCount");
			listCount = e.elementText("defaultCount");
			more = e.elementText("more");
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		
		if(templateUnit != null){
			String html = templateUnit.getHtml();
			Pattern pattern = Pattern.compile(CommonLabel.REGEX_FOR);
			Matcher matcher = pattern.matcher(html);
			List<OnlineSurvey> listThemes = onlineSurveyDao.findByDefine("findThemesByThemeId", "id", SqlUtil.toSqlString(themeId));
			List<OnlineSurveyContent> listQuestion = onlineSurveyContentDao.findByDefine("findQuestionByQuestionIds", "ids", SqlUtil.toSqlString(questionId)); 
			String label = "";
			boolean flag = true;
			OnlineSurveyContent onlineSurveyContent = null;
			int count = Integer.parseInt(listCount);
			if(count > listQuestion.size()){
				count = listQuestion.size();
				flag = false;
			}
			if(matcher.find()){
				for(int i = 0; i < count; i++){
					Pattern p = Pattern.compile(CommonLabel.REGEX_LABEL);
					Matcher m = p.matcher(matcher.group(1));
					onlineSurveyContent = listQuestion.get(i);
					StringBuffer sb2 = new StringBuffer();
					while(m.find()){
						label = m.group();
						//序号
						if(label.equals(OnlineSurverySetLabel.NUMBER)){
							int a = i+1;
							if(count > 1){
								m.appendReplacement(sb2, a+"");
							}else{
								m.appendReplacement(sb2, "");
							}
							
						//问题	
						}else if(label.equals(OnlineSurverySetLabel.QUESTION)){
							m.appendReplacement(sb2, onlineSurveyContent.getName());
							
						//问题链接地址	
						}else if(label.equals(OnlineSurverySetLabel.URL)){
							String url = "/"+appName+"/outOnlineSurvery.do?dealMethod=getNormalJsp&appName="+appName+"&questionId="+onlineSurveyContent.getId()+"&siteId="+templateUnit.getSite().getId();
							System.out.println(url);
							m.appendReplacement(sb2, url);
							
						//答案	
						}else if(label.equals(OnlineSurverySetLabel.ANSWER)){
							String style = "";
							String question = onlineSurveyContent.getId();
							//获取问题的类型(单选、多选、文本)
							String type = onlineSurveyContent.getStyle();
							//单选
							if(type.equals("0")){
								List<OnlineSurveyContentAnswer> list = onlineSurveyAnswerContentDao.findByNamedQuery("findChart", "questionId", question);
								if(!CollectionUtil.isEmpty(list)){
									OnlineSurveyContentAnswer onlineSurveyContentAnswer = null;
									for(int j = 0; j < list.size(); j++){
										onlineSurveyContentAnswer = list.get(j);
										style += "<input type=\"radio\" name=\"question_"+question+"\" value=\""+onlineSurveyContentAnswer.getId()+"\">"+onlineSurveyContentAnswer.getAnswer();
									}
								}
								
							//多选	
							}else if(type.equals("1")){
								List<OnlineSurveyContentAnswer> list = onlineSurveyAnswerContentDao.findByNamedQuery("findChart", "questionId", question);
								if(!CollectionUtil.isEmpty(list)){
									OnlineSurveyContentAnswer onlineSurveyContentAnswer = null;
									for(int j = 0; j < list.size(); j++){
										onlineSurveyContentAnswer = list.get(j);
										style += "<input type=\"checkbox\" name=\"question_"+question+"\" value=\""+onlineSurveyContentAnswer.getId()+"\">"+onlineSurveyContentAnswer.getAnswer();
									}
								}
								
							//文本							
							}else{
								style += "<textarea name=\"question_"+question+"\" value=\""+question+"\" col=\""+col+"\" row=\""+row+"\"></textarea>";
							}
							m.appendReplacement(sb2, style);
						
						
						}else if(label.equals(OnlineSurverySetLabel.DISPLAYSTYLE)){
							//是否显示样式	
							m.appendReplacement(sb2, display);							
						}else if(label.equals(OnlineSurverySetLabel.URL)){
							String url = "";
							//如果是一般调查问题列表				 
							url = "/"+appName+"/outOnlineSurvery.do?dealMethod=getNormalJsp&appName="+appName+"&questionId="+((OnlineSurveyContent)listQuestion.get(i)).getId()+"&siteId="+templateUnit.getSite().getId();			 
							m.appendReplacement(sb2, url);

						
						}else {
							m.appendReplacement(sb2, "");
						}
					}
					m.appendTail(sb2);
					sb1.append(sb2.toString());
				}
				matcher.appendReplacement(sb, sb1.toString());
			}
			matcher.appendTail(sb);
			Pattern p = Pattern.compile(CommonLabel.REGEX_LABEL);
			Matcher m = p.matcher(sb.toString());
			String lab = "";
			while(m.find()){
				lab = m.group();
				//主题
				if(lab.equals(OnlineSurverySetLabel.THEME)){
					String name = "";
					if(listThemes != null){
						name = listThemes.get(0).getName();
					}
					m.appendReplacement(sb3, name);
				
				//是否显示样式	
				}else if(lab.equals(OnlineSurverySetLabel.DISPLAYSTYLE)){
					m.appendReplacement(sb3, display);
				}else if(lab.equals(OnlineSurverySetLabel.MORE)){
					//更多
					String str = "";
					if(flag){
						if(!StringUtil.isEmpty(more)){
							String[] st = more.split("##");
							String columnId = st[0];
							String siteName = "";
							if(site.getPublishWay().equals("local")){
								siteName = site.getPublishDir().split("/")[site.getPublishDir().split("/").length-1];
							}else{
								siteName = site.getPublishDir().substring(0, site.getPublishDir().length()-site.getPublishDir().split("/")[site.getPublishDir().split("/").length-1].length());
							}
							String url = "/" + appName + "/" + siteName + "/col_" + columnId+"/index.html"; 
							str = url;
							/*str = "<table width=\"100%\">" +
									"<tr><td align=\"right\">" +
									"<a href=\"/"+appName+"/outOnlineSurvery.do?dealMethod=getNormalPagination&appName="+appName+"&unit_unitId="+unitId+"&themeId="+themeId+"\">更多>></a>" +
									"</td></tr></table>";*/
						}
					}
					m.appendReplacement(sb3, str);
				
				//更多显示	
				}else if(lab.equals(OnlineSurverySetLabel.DISPLAYMORE)){
					if(flag){
						m.appendReplacement(sb3, "");
					}else{
						m.appendReplacement(sb3, "none");
					}
					
				}else {
					m.appendReplacement(sb3, "");
				}
			}
			m.appendTail(sb3);
		}
		return sb3.toString();
	}
	
	public String getAnswerDetailHtml(String unitId, String themeId, String appName){
		StringBuffer sb = new StringBuffer();
		StringBuffer sb1 = new StringBuffer();
		StringBuffer sb3 = new StringBuffer();
		TemplateUnit templateUnit = templateUnitDao.getAndClear(unitId);
		String conf = GlobalConfig.appRealPath + "/" + templateUnit.getConfigFile();
		//是发布之后页面显示
		if(!FileUtil.isExist(conf)){
			Site site = siteDao.getAndClear(templateUnit.getSite().getId());
			String siteName = site.getPublishDir();
			siteName = siteName.split("/")[siteName.split("/").length-1];
			String instanceId = templateUnit.getTemplateInstance().getId();
			String co = templateUnit.getConfigFile();
			String xml = co.split("/")[co.split("/").length-1];
			conf = GlobalConfig.appRealPath + "/" + siteName + "/template_instance/" + instanceId+"/conf/"+xml;
		}
		SAXReader sr = new SAXReader();
		Document document;
		String row = "";
		String col = "";
		String listCount = "";
		try {
			document = sr.read(new File(conf));
			Element e = (Element) document.getRootElement().elementIterator("online_survery").next();
			row = e.elementText("rowCount");
			col = e.elementText("colCount");
			listCount = e.elementText("defaultCount");
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		if(templateUnit != null){
			String html = templateUnit.getHtml();
			Pattern pattern = Pattern.compile(CommonLabel.REGEX_FOR);
			Matcher matcher = pattern.matcher(html);
			List<OnlineSurveyContent> listQuestion = onlineSurveyAnswerContentDao.findByNamedQuery("findQuestionByThemeId", "themeId", themeId); 
			int count = 0;
			if(!StringUtil.isEmpty(listCount)){
				count = Integer.parseInt(listCount);
			}
			if(count > listQuestion.size()){
				count = listQuestion.size();
			}
			String label = "";
			OnlineSurveyContent onlineSurveyContent = null;
			if(matcher.find()){
				for(int i = 0; i < count; i++){
					Pattern p = Pattern.compile(CommonLabel.REGEX_LABEL);
					Matcher m = p.matcher(matcher.group(1));
					onlineSurveyContent = listQuestion.get(0);
					StringBuffer sb2 = new StringBuffer();
					while(m.find()){
						label = m.group();
						if(label.equals(OnlineSurverySetLabel.NUMBER)){
							int a = i+1;
							if(count > 1){
								m.appendReplacement(sb2, a+"");
							}else{
								m.appendReplacement(sb2, "");
							}
							
						}else if(label.equals(OnlineSurverySetLabel.QUESTION)){
							m.appendReplacement(sb2, onlineSurveyContent.getName());
							
						}else if(label.equals(OnlineSurverySetLabel.ANSWER)){
							String style = "";
							//问题数目只有一个
							if(count <= 1){
								String question = onlineSurveyContent.getId();
								//获取问题的类型(单选、多选、文本)
								String type = onlineSurveyContent.getStyle();
								//单选
								if(type.equals("0")){
									List<OnlineSurveyContentAnswer> list = onlineSurveyAnswerContentDao.findByNamedQuery("findChart", "questionId", question);
									if(!CollectionUtil.isEmpty(list)){
										OnlineSurveyContentAnswer onlineSurveyContentAnswer = null;
										for(int j = 0; j < list.size(); j++){
											onlineSurveyContentAnswer = list.get(j);
											style += "<input type=\"radio\" name=\""+onlineSurveyContent.getName()+"\">"+onlineSurveyContentAnswer.getAnswer();
										}
									}
									
								//多选	
								}else if(type.equals("1")){
									List<OnlineSurveyContentAnswer> list = onlineSurveyAnswerContentDao.findByNamedQuery("findChart", "questionId", question);
									if(!CollectionUtil.isEmpty(list)){
										OnlineSurveyContentAnswer onlineSurveyContentAnswer = null;
										for(int j = 0; j < list.size(); j++){
											onlineSurveyContentAnswer = list.get(j);
											style += "<input type=\"checkbox\" name=\""+onlineSurveyContent.getName()+"\">"+onlineSurveyContentAnswer.getAnswer();
										}
									}
									
								//文本							
								}else{
									style += "<textarea name=\""+question+"\" col=\""+col+"\" row=\""+row+"\"></textarea>";
								}
							}
							m.appendReplacement(sb2, style);
							
						}else if(label.equals(OnlineSurverySetLabel.DISPLAYSTYLE)){
							m.appendReplacement(sb2, "none");
							
						}else if(label.equals(OnlineSurverySetLabel.URL)){
							String url = "/"+appName+"/outOnlineSurvery.do?dealMethod=getAnswerJsp&appName="+appName+"&themeId="+themeId+"&unitId="+unitId;
							m.appendReplacement(sb2, url);
							
						}else {
							m.appendReplacement(sb2, "");
						}
					}
					m.appendTail(sb2);
					sb1.append(sb2.toString());
				}
				matcher.appendReplacement(sb, sb1.toString());
			}
			matcher.appendTail(sb);
			Pattern p = Pattern.compile(CommonLabel.REGEX_LABEL);
			Matcher m = p.matcher(sb.toString());
			String lab = "";
			OnlineSurvey onlineSurvey = onlineSurveyDao.getAndClear(themeId);
			while(m.find()){
				lab = m.group();
				if(lab.equals(OnlineSurverySetLabel.THEME)){
					String name = onlineSurvey.getName();
					m.appendReplacement(sb3, name);
				
				}else if(lab.equals(OnlineSurverySetLabel.MORE)){
					String str = "";
					/*
					str = "<table width=\"100%\">" +
							"<tr><td align=\"right\">" +
							"<a href=\"/"+GlobalConfig.appName+"/outOnlineSurvery.do?dealMethod=getAnswerPagination&unit_unitId="+unitId+"\">更多>></a>" +
							"</td></tr></table>";
					*/
					m.appendReplacement(sb3, str);
				
				}else if(lab.equals(OnlineSurverySetLabel.DISPLAYSTYLE)){
					m.appendReplacement(sb3, "none");
					
				}else if(lab.equals(OnlineSurverySetLabel.URL)){
					String url = "/"+appName+"/outOnlineSurvery.do?dealMethod=getAnswerJsp&appName="+appName+"&themeId="+themeId+"&unitId="+unitId;
					m.appendReplacement(sb3, url);
						
				}else{
					m.appendReplacement(sb3, "");
				}
			}
			m.appendTail(sb3);
		}
		return sb3.toString();
	}
	
	public Pagination getNormalPagination(Pagination pagination, String themeId, String unitId){
//		TemplateUnit templateUnit = templateUnitDao.getAndClear(unitId);
		if(!StringUtil.isEmpty(themeId)){
			pagination.setQueryString("select id, name from onlinesurveycontent where onlineSurvery_id='"+themeId+"'");
		}else{
			//一般调查全部问题
		/*	String conf = GlobalConfig.appRealPath + "/" + templateUnit.getConfigFile();
			SAXReader sx = new SAXReader();
			String count = "";
			try {
				Document documnet = sx.read(new File(conf));
				Element e = (Element) documnet.getRootElement().elementIterator("online_survery").next();
				count = e.elementText("defaultCount");
			} catch (DocumentException e) {
				e.printStackTrace();
			}
			int max = 0;
			if(!StringUtil.isEmpty(count)){
				max = Integer.parseInt(count);
			}*/
			pagination.setQueryString("select a.id, a.name from onlinesurveycontent a, plugin_onlinesurvey b where a.onlineSurvery_id=b.id and b.category='f020001'");			
		}
		pagination =  PageQuery.getPaginationByQueryString(pagination, jdbcTemplate);
		return pagination;
	}
	
	public String getContent(Pagination pagination, String unitId, boolean flag, String appName){
		TemplateUnit templateUnit = templateUnitDao.getAndClear(unitId);
		String siteId = templateUnit.getSite().getId();
		StringBuffer sb = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		StringBuffer sb3 = new StringBuffer();
		String filePath = GlobalConfig.appRealPath+"/plugin/onlineSurvey_manager/conf/"+siteId+"_styleSet.xml";
		SAXReader read = new SAXReader();
		String html = "";
		Document document;
		try {
			document = read.read(new File(filePath));
			Element e = (Element) document.getRootElement().elementIterator("style").next();
			//列表样式
			html = e.elementText("listStyle");
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}
		Pattern p = Pattern.compile(CommonLabel.REGEX_FOR);
		Matcher m = p.matcher(html);
		
		if(m.find()){
			sb2.append(m.group(1));
			List list = pagination.getData();
			if(!CollectionUtil.isEmpty(list)){
				for(int i = 0; i < list.size(); i++){
					StringBuffer sb1 = new StringBuffer();
					Object[] obj = (Object[])list.get(i);
					String id = String.valueOf(obj[0]);
					String name = String.valueOf(obj[1]);
					Pattern pattern = Pattern.compile(CommonLabel.REGEX_LABEL);
					Matcher matcher = pattern.matcher(sb2.toString());
					String label = "";
					while(matcher.find()){
						label = matcher.group();
						if(label.equals(OnlineSurverySetLabel.NUMBER)){
							int a = i+1;
							matcher.appendReplacement(sb1, a+"");
							
						}else if(label.equals(OnlineSurverySetLabel.KINDNAME)){
							matcher.appendReplacement(sb1, name);
							
						}else if(label.equals(OnlineSurverySetLabel.URL)){
							String url = "";
							//如果是一般调查问题列表
							if(flag){
								url = "/"+appName+"/outOnlineSurvery.do?dealMethod=getNormalJsp&appName="+appName+"&questionId="+id+"&siteId="+siteId;	
						
							//问卷调查主题列表
							}else{
								url = "/"+appName+"/outOnlineSurvery.do?dealMethod=getAnswerJsp&appName="+appName+"&themeId="+id+"&unitId="+unitId;
								//System.out.println(url);
							}
							matcher.appendReplacement(sb1, url);
							
						}else {
							matcher.appendReplacement(sb1, "");
						}
					}
					matcher.appendTail(sb1);
					sb3.append(sb1.toString());
				}
			}
			m.appendReplacement(sb, sb3.toString());
		}
		m.appendTail(sb);
		return sb.toString();
	}
	
	public String getDisplayStyle(String questionId, String siteId){
		String filePath = GlobalConfig.appRealPath+"/plugin/onlineSurvey_manager/conf/"+siteId+"_styleSet.xml";
		SAXReader read = new SAXReader();
		String html = "";
		String width = "";
		String height = "";
		Document document;
		try {
			document = read.read(new File(filePath));
			Element e = (Element) document.getRootElement().elementIterator("style").next();
			//投票样式
			html = e.elementText("stylecontent");
			width = e.elementText("width");
			height = e.elementText("height");
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}
		OnlineSurveyContent onlineSurveyContent = onlineSurveyContentDao.getAndNonClear(questionId);
		String questionName = onlineSurveyContent.getName();
		StringBuffer sb = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		Pattern p = Pattern.compile(CommonLabel.REGEX_FOR);
		Matcher m = p.matcher(html);
		if(m.find()){
			sb2.append(m.group(1));
			StringBuffer sb1 = new StringBuffer();
			Pattern pattern = Pattern.compile(CommonLabel.REGEX_LABEL);
			Matcher matcher = pattern.matcher(sb2.toString());
			String label = "";
			while(matcher.find()){
				label = matcher.group();
				//序号
				if(label.equals(OnlineSurverySetLabel.NUMBER)){
					matcher.appendReplacement(sb1, "");
				//问题
				}else if(label.equals(OnlineSurverySetLabel.QUESTION)){
					matcher.appendReplacement(sb1, questionName);
				//答案
				}else if(label.equals(OnlineSurverySetLabel.ANSWER)){
					String style = "";
					//获取问题的类型(单选、多选、文本)
					String type = onlineSurveyContent.getStyle();
					//单选
					if(type.equals("0")){
						List<OnlineSurveyContentAnswer> list = onlineSurveyAnswerContentDao.findByNamedQuery("findChart", "questionId", questionId);
						if(!CollectionUtil.isEmpty(list)){
							OnlineSurveyContentAnswer onlineSurveyContentAnswer = null;
							for(int j = 0; j < list.size(); j++){
								onlineSurveyContentAnswer = list.get(j);
								style += "<input type=\"radio\" name=\"question_"+questionId+"\" value=\""+onlineSurveyContentAnswer.getId()+"\">"+onlineSurveyContentAnswer.getAnswer();
							}
						}
						
					//多选	
					}else if(type.equals("1")){
						List<OnlineSurveyContentAnswer> list = onlineSurveyAnswerContentDao.findByNamedQuery("findChart", "questionId", questionId);
						if(!CollectionUtil.isEmpty(list)){
							OnlineSurveyContentAnswer onlineSurveyContentAnswer = null;
							for(int j = 0; j < list.size(); j++){
								onlineSurveyContentAnswer = list.get(j);
								style += "<input type=\"checkbox\" name=\"question_"+questionId+"\" value=\""+onlineSurveyContentAnswer.getId()+"\">"+onlineSurveyContentAnswer.getAnswer();
							}
						}
						
					//文本							
					}else{
						style += "<textarea name=\"question_"+questionId+"\" style=\"width:"+width+"px;height:"+height+"px;\"></textarea>";
					}
					matcher.appendReplacement(sb1, style);
					
				}else {
					matcher.appendReplacement(sb1, "");
				}
			}
			matcher.appendTail(sb1);
			m.appendReplacement(sb, sb1.toString());
		}
		m.appendTail(sb);
		Pattern pa = Pattern.compile(CommonLabel.REGEX_LABEL);
		Matcher ma = pa.matcher(sb);
		StringBuffer sb3 = new StringBuffer();
		String label = "";
		while(ma.find()){
			label = ma.group();
			if(label.equals(OnlineSurverySetLabel.KINDNAME)){
				String theme = onlineSurveyContent.getOnlineSurvey().getName();
				ma.appendReplacement(sb3, theme);
			}else{
				ma.appendReplacement(sb3, "");
			}
		}
		ma.appendTail(sb3);
		return sb3.toString();
	}
	
	public void addCommit(String questionId, String answerIds, String feedbackContent){
		//是文本类型(添加到反馈表中)
		if(StringUtil.isEmpty(answerIds)){
			OnlineSurveyContent onlineSurveyContent = new OnlineSurveyContent();
			onlineSurveyContent.setId(questionId);
			OnlinefeedbackContent onlinefeedbackContent = new OnlinefeedbackContent();
			onlinefeedbackContent.setOnlineSurveyContent(onlineSurveyContent);
			onlinefeedbackContent.setFeedbackContent(feedbackContent);
			onlinefeedbackContentDao.save(onlinefeedbackContent);
		}else{
			onlineSurveyAnswerContentDao.updateByDefine("addCommit", "answerIds", SqlUtil.toSqlString(answerIds));
		}
	}
	
	public String getAnswerDisplayStyle(String themeId, String unitId){
		TemplateUnit templateUnit = templateUnitDao.getAndClear(unitId);
		String siteId = templateUnit.getSite().getId();
		String filePath = GlobalConfig.appRealPath+"/plugin/onlineSurvey_manager/conf/"+siteId+"_styleSet.xml";
		SAXReader read = new SAXReader();
		String html = "";
		String width = "";
		String height = "";
		Document document;
		try {
			document = read.read(new File(filePath));
			Element e = (Element) document.getRootElement().elementIterator("style").next();
			//投票样式
			html = e.elementText("stylecontent");
			width = e.elementText("width");
			height = e.elementText("height");
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}
		
		StringBuffer sb = new StringBuffer();
		StringBuffer sb6 = new StringBuffer();
		
		//问题
		List<OnlineSurveyContent> listQuestion = onlineSurveyContentDao.findByNamedQuery("findQuestionByThemeId", "themeId", themeId);
		if(!CollectionUtil.isEmpty(listQuestion)){
			Pattern p = Pattern.compile(CommonLabel.REGEX_FOR);
			Matcher m = p.matcher(html);
			if(m.find()){
				StringBuffer sb2 = new StringBuffer();
				StringBuffer sb4 = new StringBuffer();
				sb2.append(m.group(1));
				OnlineSurveyContent question = null;
				for(int i = 0; i < listQuestion.size(); i++){
					question = listQuestion.get(i);
					if(i == listQuestion.size()-1){
						sb6.append(question.getId());
					}else{
						sb6.append(question.getId()+",");
					}
					StringBuffer sb1 = new StringBuffer();
					Pattern pattern = Pattern.compile(CommonLabel.REGEX_LABEL);
					Matcher matcher = pattern.matcher(sb2.toString());
					String label = "";
					while(matcher.find()){
						label = matcher.group();
						//序号
						if(label.equals(OnlineSurverySetLabel.NUMBER)){
							matcher.appendReplacement(sb1, (i+1)+"");
						//问题
						}else if(label.equals(OnlineSurverySetLabel.QUESTION)){
							matcher.appendReplacement(sb1, question.getName());
						//答案
						}else if(label.equals(OnlineSurverySetLabel.ANSWER)){
							String answer = "";
							//获取问题的类型(单选、多选、文本)
							String type = question.getStyle();
							//单选
							if(type.equals("0")){
								List<OnlineSurveyContentAnswer> list = onlineSurveyAnswerContentDao.findByNamedQuery("findChart", "questionId", question.getId());
								if(!CollectionUtil.isEmpty(list)){
									OnlineSurveyContentAnswer onlineSurveyContentAnswer = null;
									for(int j = 0; j < list.size(); j++){
										onlineSurveyContentAnswer = list.get(j);
										answer += "<input type=\"radio\" name=\"question_"+question.getId()+"\" value=\""+onlineSurveyContentAnswer.getId()+"\">"+onlineSurveyContentAnswer.getAnswer();
									}
								}
							//多选	
							}else if(type.equals("1")){
								List<OnlineSurveyContentAnswer> list = onlineSurveyAnswerContentDao.findByNamedQuery("findChart", "questionId", question.getId());
								if(!CollectionUtil.isEmpty(list)){
									OnlineSurveyContentAnswer onlineSurveyContentAnswer = null;
									for(int j = 0; j < list.size(); j++){
										onlineSurveyContentAnswer = list.get(j);
										answer += "<input type=\"checkbox\" name=\"question_"+question.getId()+"\" value=\""+onlineSurveyContentAnswer.getId()+"\">"+onlineSurveyContentAnswer.getAnswer();
									}
								}
							//文本							
							}else{
								answer += "<textarea name=\"question_"+question.getId()+"\" value=\""+question.getId()+"\" style=\"width:"+width+"px;height:"+height+"px;\"></textarea>";
							}
							matcher.appendReplacement(sb1, answer);
						}else {
							matcher.appendReplacement(sb1, "");
						}
					}
					matcher.appendTail(sb1);
					sb4.append(sb1.toString());
				}
				m.appendReplacement(sb, sb4.toString());
			}
			m.appendTail(sb);
		}
		StringBuffer sb5 = new StringBuffer();
		Pattern pattern = Pattern.compile(CommonLabel.REGEX_LABEL);
		Matcher matcher = pattern.matcher(sb.toString());
		String label = "";
		while(matcher.find()){
			label = matcher.group();
			//主题
			if(label.equals(OnlineSurverySetLabel.KINDNAME)){
				OnlineSurvey onlineSurvey = onlineSurveyDao.getAndClear(themeId);
				matcher.appendReplacement(sb5, onlineSurvey.getName());
				
			}else{
				matcher.appendReplacement(sb5, "");
			}
		}
		matcher.appendTail(sb5);
		return sb6.toString()+"###"+sb5.toString();
	}
	
	public void addAnswerCommit(String questionIds, String answerIds, String feedbackContent){
		//是文本类型(添加到反馈表中)
		if(!StringUtil.isEmpty(questionIds)){
			String[] str = questionIds.split(",");
			String[] str1 = feedbackContent.split("###");
			for(int i = 0; i < str.length; i++){
				OnlineSurveyContent onlineSurveyContent = new OnlineSurveyContent();
				onlineSurveyContent.setId(str[i]);
				OnlinefeedbackContent onlinefeedbackContent = new OnlinefeedbackContent();
				onlinefeedbackContent.setOnlineSurveyContent(onlineSurveyContent);
				onlinefeedbackContent.setFeedbackContent(str1[i]);
				onlinefeedbackContentDao.save(onlinefeedbackContent);
			}
		}
		if(!StringUtil.isEmpty(answerIds)){
			onlineSurveyAnswerContentDao.updateByDefine("addCommit", "answerIds", SqlUtil.toSqlString(answerIds));
		}
	}
	
	public String getNormalContent(Pagination pagination, String unitId, String appName){
		TemplateUnit templateUnit = templateUnitDao.getAndNonClear(unitId);
		Site site = siteDao.getAndClear(templateUnit.getSite().getId());
		StringBuffer sb = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		StringBuffer sb3 = new StringBuffer();
		
		String html = templateUnit.getHtml();
		Pattern p = Pattern.compile(CommonLabel.REGEX_FOR);
		Matcher m = p.matcher(html);
		
		
		//读取配置文件
		String conf = GlobalConfig.appRealPath +"/"+ templateUnit.getConfigFile();
		//是发布之后页面显示
		if(!FileUtil.isExist(conf)){
			String siteName = site.getPublishDir();
			siteName = siteName.split("/")[siteName.split("/").length-1];
			String instanceId = templateUnit.getTemplateInstance().getId();
			String co = templateUnit.getConfigFile();
			String xml = co.split("/")[co.split("/").length-1];
			conf = GlobalConfig.appRealPath + "/" + siteName + "/template_instance/" + instanceId+"/conf/"+xml;
		}
		SAXReader sr = new SAXReader();
		Document document;
		String more = "";
		String defaultCount = "";
		try {
			document = sr.read(new File(conf));
			Element e = (Element) document.getRootElement().elementIterator("online_survery").next();
			more = e.elementText("more");
			defaultCount = e.elementText("defaultCount");
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		int count = Integer.parseInt(defaultCount);
		//标记是否要显示更多
		boolean flag = true;
		List list = pagination.getData();
		if(count > list.size()){
			count = list.size();
			flag = false;
		}
		
		if(m.find()){
			sb2.append(m.group(1));
			if(!CollectionUtil.isEmpty(list)){
				for(int i = 0; i < count; i++){
					StringBuffer sb1 = new StringBuffer();
					Object[] obj = (Object[])list.get(i);
					String id = String.valueOf(obj[0]);
					String name = String.valueOf(obj[1]);
					Pattern pattern = Pattern.compile(CommonLabel.REGEX_LABEL);
					Matcher matcher = pattern.matcher(sb2.toString());
					String label = "";
					while(matcher.find()){
						label = matcher.group();
						if(label.equals(OnlineSurverySetLabel.NUMBER)){
							int a = i+1;
							if(list.size() == 1){
								matcher.appendReplacement(sb1, "");
							}else{
								matcher.appendReplacement(sb1, a+"");
							}
							
						}else if(label.equals(OnlineSurverySetLabel.KINDNAME)){
							matcher.appendReplacement(sb1, name);
							
						}else if(label.equals(OnlineSurverySetLabel.URL)){
							String url = "";
							url = "/"+appName+"/outOnlineSurvery.do?dealMethod=getNormalJsp&appName="+appName+"&questionId="+id+"&siteId="+site.getId();
							matcher.appendReplacement(sb1, url);
						
						}else {
							matcher.appendReplacement(sb1, "");
						}
					}
					matcher.appendTail(sb1);
					sb3.append(sb1.toString());
				}
			}
			m.appendReplacement(sb, sb3.toString());
		}
		m.appendTail(sb);
		StringBuffer sb4 = new StringBuffer();
		Pattern pattern = Pattern.compile(CommonLabel.REGEX_LABEL);
		Matcher matcher = pattern.matcher(sb.toString());
		String label = "";
		
		while(matcher.find()){
			label = matcher.group();
			// 更多
			if(label.equals(OnlineSurverySetLabel.MORE)){
				String str = "";
				String[] st = more.split("##");
				String columnId = st[0];
				String siteName = "";
				if(site.getPublishWay().equals("local")){
					siteName = site.getPublishDir().split("/")[site.getPublishDir().split("/").length-1];
				}else{
					siteName = site.getPublishDir().substring(0, site.getPublishDir().length()-site.getPublishDir().split("/")[site.getPublishDir().split("/").length-1].length());
				}
				String url = "/" + appName + "/" + siteName + "/col_" + columnId+"/index.html"; 
				str = url;
				matcher.appendReplacement(sb4, str);
				
			}else if(label.equals(OnlineSurverySetLabel.DISPLAYSTYLE)){
				if(flag){
					matcher.appendReplacement(sb4, "");
				}else{
					matcher.appendReplacement(sb4, "none");
				}
				
			}else{
				matcher.appendReplacement(sb4, "");
			}
		}
		matcher.appendTail(sb4);
		return sb4.toString();
	}
	
	public String getAnswerList(Pagination pagination, String unitId, String appName){
		TemplateUnit templateUnit = templateUnitDao.getAndNonClear(unitId);
		Site site = siteDao.getAndClear(templateUnit.getSite().getId());
		StringBuffer sb = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		StringBuffer sb3 = new StringBuffer();
		
		//读取配置文件
		String conf = GlobalConfig.appRealPath +"/"+ templateUnit.getConfigFile();
		//是发布之后页面显示
		if(!FileUtil.isExist(conf)){
			String siteName = site.getPublishDir();
			siteName = siteName.split("/")[siteName.split("/").length-1];
			String instanceId = templateUnit.getTemplateInstance().getId();
			String co = templateUnit.getConfigFile();
			String xml = co.split("/")[co.split("/").length-1];
			conf = GlobalConfig.appRealPath + "/" + siteName + "/template_instance/" + instanceId+"/conf/"+xml;
		}
		SAXReader sr = new SAXReader();
		Document document;
		String more = "";
		String defaultCount = "";
		try {
			document = sr.read(new File(conf));
			Element e = (Element) document.getRootElement().elementIterator("online_survery").next();
			more = e.elementText("more");
			defaultCount = e.elementText("defaultCount");
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		List list = pagination.getData();
		int count = Integer.parseInt(defaultCount);
		boolean displayMore = true;
		if(count > list.size()){
			count = list.size();
			displayMore = false;
		}
		
		String html = templateUnit.getHtml();
		Pattern p = Pattern.compile(CommonLabel.REGEX_FOR);
		Matcher m = p.matcher(html);
		if(m.find()){
			sb2.append(m.group(1));
			
			if(!CollectionUtil.isEmpty(list)){
				for(int i = 0; i < count; i++){
					StringBuffer sb1 = new StringBuffer();
					Object[] obj = (Object[])list.get(i);
					String id = String.valueOf(obj[0]);
					String name = String.valueOf(obj[1]);
					Pattern pattern = Pattern.compile(CommonLabel.REGEX_LABEL);
					Matcher matcher = pattern.matcher(sb2.toString());
					String label = "";
					while(matcher.find()){
						label = matcher.group();
						if(label.equals(OnlineSurverySetLabel.NUMBER)){
							int a = i+1;
							if(list.size() == 1){
								matcher.appendReplacement(sb1, "");
							}else{
								matcher.appendReplacement(sb1, a+"");
							}
							
						}else if(label.equals(OnlineSurverySetLabel.KINDNAME)){
							matcher.appendReplacement(sb1, name);
							
						}else if(label.equals(OnlineSurverySetLabel.URL)){
							String url = "";
							url = "/"+appName+"/outOnlineSurvery.do?dealMethod=getAnswerJsp&appName="+appName+"&themeId="+id+"&unitId="+unitId;
							matcher.appendReplacement(sb1, url);
						
						}else {
							matcher.appendReplacement(sb1, "");
						}
					}
					matcher.appendTail(sb1);
					sb3.append(sb1.toString());
				}
			}
			m.appendReplacement(sb, sb3.toString());
		}
		m.appendTail(sb);
		StringBuffer sb4 = new StringBuffer();
		Pattern pattern = Pattern.compile(CommonLabel.REGEX_LABEL);
		Matcher matcher = pattern.matcher(sb.toString());
		String label = "";
		
		while(matcher.find()){
			label = matcher.group();
			// 更多
			if(label.equals(OnlineSurverySetLabel.MORE)){
				String str = "";
				String[] st = more.split("##");
				String columnId = st[0];
				String siteName = "";
				if(site.getPublishWay().equals("local")){
					siteName = site.getPublishDir().split("/")[site.getPublishDir().split("/").length-1];
				}else{
					siteName = site.getPublishDir().substring(0, site.getPublishDir().length()-site.getPublishDir().split("/")[site.getPublishDir().split("/").length-1].length());
				}
				String url = "/" + appName + "/" + siteName + "/col_" + columnId+"/index.html"; 
				str = url;
				matcher.appendReplacement(sb4, str);
				
			}else if(label.equals(OnlineSurverySetLabel.DISPLAYSTYLE)){
				if(displayMore){
					matcher.appendReplacement(sb4, "");	
				}else{
					matcher.appendReplacement(sb4, "none");
				}
				
			}else{
				matcher.appendReplacement(sb4, "");
			}
		}
		matcher.appendTail(sb4);
		return sb4.toString();
	}
	
	public Pagination getAnswerPagination(Pagination pagination, String unitId, int flag){
		// 问卷调查主题列表
		if(flag == 1){
			/*TemplateUnit templateUnit = templateUnitDao.getAndClear(unitId);
			String filePath = GlobalConfig.appRealPath + "/" + templateUnit.getConfigFile();
			SAXReader read = new SAXReader();
			String max = "";
			Document document;
			try {
				document = read.read(new File(filePath));
				Element e = (Element) document.getRootElement().elementIterator("online_survery").next();
				max = e.elementText("defaultCount");
			} catch (DocumentException e1) {
				e1.printStackTrace();
			}
			int count = 0;
			if(!StringUtil.isEmpty(max)){
				count = Integer.parseInt(max);
			}*/
			//pagination.setQueryString("select id, name from plugin_onlinesurvey where category='f020002' limit "+count);
			pagination.setQueryString("select id, name from plugin_onlinesurvey where category='f020002'");
		//分页
		}else{
			pagination.setQueryString("select id, name from plugin_onlinesurvey where category='f020002'");
		}
		pagination =  PageQuery.getPaginationByQueryString(pagination, jdbcTemplate);
		return pagination;
	}
	
	public Map getResult(String questionIds, String themeId){
		Map map = new HashMap();
		List listQuestions = new ArrayList();
		List listAnswers = new ArrayList();
		if(!StringUtil.isEmpty(questionIds)){
			String[] str = questionIds.split(",");
			List<OnlineSurveyContent> listQuestion = new ArrayList<OnlineSurveyContent>();
			//是问卷调查
			if(StringUtil.isEmpty(questionIds)){
				listQuestion = onlineSurveyAnswerContentDao.findByNamedQuery("findQuestionByThemeId", "themeId", themeId);
			}else{
				//查找问题
				listQuestion = onlineSurveyContentDao.findByDefine("findQuestionByQuestionIds", "ids", SqlUtil.toSqlString(questionIds));
			}
			if(!CollectionUtil.isEmpty(listQuestion)){
				OnlineSurveyContent onlineSurveyContent = null;
				for(int i = 0; i < listQuestion.size(); i++){
					onlineSurveyContent = listQuestion.get(i);
					String questionId = onlineSurveyContent.getId();
					String style = onlineSurveyContent.getStyle();
					//不是文本类型(文本类型不显示调查结果)
					if(!style.equals("2")){
						//查找答案
						List<OnlineSurveyContentAnswer> listAnswer = onlineSurveyAnswerContentDao.findByNamedQuery("findChart", "questionId", questionId);
						if(!CollectionUtil.isEmpty(listAnswer)){
							Object obj = onlineSurveyAnswerContentDao.findByDefine("findCommentCountByQuestionId", "questionId", SqlUtil.toSqlString(questionId)).get(0);
							long count = StringUtil.parseLong(obj.toString());
							//问题
							Object[] obj1 = new Object[2];
							obj1[0] = questionId;
							obj1[1] = onlineSurveyContent.getName();
							listQuestions.add(obj1);
							OnlineSurveyContentAnswer answer = null;
							for(int j = 0; j < listAnswer.size(); j++){
								answer = listAnswer.get(j);
								//答案(问题id、答案名称、投票数、显示比例)
								Object[] obj2 = new Object[4];
								obj2[0] = questionId;
								obj2[1] = answer.getAnswer();
								//没有投票(所有的显示为空)
								if(count == 0){
									obj2[2] = 0;
									obj2[3] = "0%";
								}else{
									int vote = StringUtil.parseInt(answer.getVoteCount());
									obj2[2] = vote;
									obj2[3] = String.valueOf(((float)vote/count)*100).substring(0,2)+"%";
								}
								listAnswers.add(obj2);
							}
						}
					}
				}
			}
		}
		map.put("question", listQuestions);
		map.put("answer", listAnswers);
		return map;
	}
	
	
    public void publishGuestBookDir(String siteId){
		Site site = siteDao.getAndClear(siteId);
		String dir = site.getPublishDir().substring(0, site.getPublishDir().length()-site.getPublishDir().split("/")[site.getPublishDir().split("/").length-1].length());
		String publishDir = dir+"/plugin";
		if(!FileUtil.isExist(publishDir)){
			FileUtil.makeDirs(publishDir);
		}
		String article_commentDir = GlobalConfig.appRealPath + "/plugin/onlineSurvey_manager";
		if(FileUtil.isExist(article_commentDir)){
			if(!FileUtil.isExist(publishDir+"/onlineSurvey_manager")){
				 FileUtil.copy(article_commentDir, publishDir, true);
			 }
		}
		
		//发布js文件
		// script/jquery-1.2.6.js
		String destJs = dir + "/script";
		if(!FileUtil.isExist(destJs)){
			FileUtil.makeDirs(destJs);
		}
		if(!FileUtil.isExist(destJs+"/jquery-1.2.6.js")){
			FileUtil.copy(GlobalConfig.appRealPath+"/script/jquery-1.2.6.js", destJs);
		}
		// script/jquery.form.js
		if(!FileUtil.isExist(destJs+"/jquery.form.js")){
			FileUtil.copy(GlobalConfig.appRealPath+"/script/jquery.form.js", destJs);
		}
		//script/jquery.blockUI.js
		if(!FileUtil.isExist(destJs+"/jquery.blockUI.js")){
			FileUtil.copy(GlobalConfig.appRealPath+"/script/jquery.blockUI.js", destJs);
		}
		//拷贝图片(查看调查结果使用)
		String destImages = dir + "/images";
		if(!FileUtil.isExist(destImages)){
			FileUtil.makeDirs(destImages);
			if(!FileUtil.isExist(destImages+"/result.jpg")){
				FileUtil.copy(GlobalConfig.appRealPath+"/images/result.jpg", destImages);
			}
		}
    }
	
	/**
	 * 查找网上调查问题名称表
	 * @param pagination 分页对象
	 * */
	public Pagination finOnlineAnswerQuestions(Pagination pagination) {
		return onlineSurveyDao.getPagination(pagination);
	}
	/**
	 *  查询子菜单的内容
	 * @param pagination 分页对象
	 * @param id  类型id
	 * return Pagination 在线问题答案分页
	 * */
	public Pagination finOnlineAnswerQuestions(Pagination pagination, String id) {
		if (id != null && !id.equals("")) {
			pagination = onlineSurveyDao.getPagination(pagination,"id", id);
		   return onlineSurveyDao.getPagination(pagination, "id", id);
		} else {
			return new Pagination();
		}
	}
	
	public OnlineSurveyAnswerContentDao getOnlineSurveyAnswerContentDao() {
		return onlineSurveyAnswerContentDao;
	}

	public void setOnlineSurveyAnswerContentDao(
			OnlineSurveyAnswerContentDao onlineSurveyAnswerContentDao) {
		this.onlineSurveyAnswerContentDao = onlineSurveyAnswerContentDao;
	}

	public OnlineSurveyContentDao getOnlineSurveyContentDao() {
		return onlineSurveyContentDao;
	}

	public void setOnlineSurveyContentDao(
			OnlineSurveyContentDao onlineSurveyContentDao) {
		this.onlineSurveyContentDao = onlineSurveyContentDao;
	}

	public OnlineSurveyDao getOnlineSurveyDao() {
		return onlineSurveyDao;
	}

	public void setOnlineSurveyDao(OnlineSurveyDao onlineSurveyDao) {
		this.onlineSurveyDao = onlineSurveyDao;
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
	 * @param jdbcTemplate the jdbcTemplate to set
	 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * @param onlinefeedbackContentDao the onlinefeedbackContentDao to set
	 */
	public void setOnlinefeedbackContentDao(
			OnlinefeedbackContentDao onlinefeedbackContentDao) {
		this.onlinefeedbackContentDao = onlinefeedbackContentDao;
	}


}