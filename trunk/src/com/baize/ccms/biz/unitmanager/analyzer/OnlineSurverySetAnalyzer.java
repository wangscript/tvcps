/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.unitmanager.analyzer;

import java.util.ArrayList;
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
import com.baize.ccms.biz.unitmanager.label.ColumnLinkLabel;
import com.baize.ccms.biz.unitmanager.label.CommonLabel;
import com.baize.ccms.biz.unitmanager.label.OnlineSurverySetLabel;
import com.baize.ccms.plugin.onlinesurvey.dao.OnlineSurveyContentDao;
import com.baize.ccms.plugin.onlinesurvey.dao.OnlineSurveyDao;
import com.baize.ccms.plugin.onlinesurvey.domain.OnlineSurvey;
import com.baize.ccms.plugin.onlinesurvey.domain.OnlineSurveyContent;
import com.baize.ccms.sys.GlobalConfig;
import com.baize.ccms.sys.SiteResource;
import com.baize.common.core.util.CollectionUtil;
import com.baize.common.core.util.DateUtil;
import com.baize.common.core.util.IDFactory;
import com.baize.common.core.util.SqlUtil;
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
public class OnlineSurverySetAnalyzer implements TemplateUnitAnalyzer {
	
	private static final Logger log = Logger.getLogger(OnlineSurverySetAnalyzer.class);
	
	/** 注入模板单元dao */
	private TemplateUnitDao templateUnitDao;	
	/** 注入模板实例dao */
	private TemplateInstanceDao templateInstanceDao;
	/** 网站dao */
	private SiteDao siteDao;
	/** 注入网上调查主题dao */
	private OnlineSurveyDao onlineSurveyDao;
	/** 注入网上调查问题dao */
	private OnlineSurveyContentDao onlineSurveyContentDao;
	
	/**
	 * 获取新的html代码
	 * @param unitId
	 * @param columnId
	 * @param commonLabel
	 * @return
	 */
	public String getHtml(String unitId, String articleId, String columnId, String siteId, Map<String,String> commonLabel) {
		TemplateUnit templateUnit = templateUnitDao.getAndClear(unitId);
		StringBuffer sb = new StringBuffer();
		String script = templateUnit.getScript();
		if(!StringUtil.isEmpty(script)){
			sb.append(script);
		}
		sb.append("<div id=\""+unitId+"\"></div>");
		return sb.toString();
		
		/*String configFilePath = templateUnit.getConfigFile();
		String htmlCode = templateUnit.getHtml();
		//获取到当前模板实例的xml配置文件路径
		String filePath = GlobalConfig.appRealPath + configFilePath;
		XmlUtil xmlUtil = XmlUtil.getInstance(filePath);

		StringBuffer sb = new StringBuffer();
		StringBuffer sb1 = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		Pattern forPattern = Pattern.compile(CommonLabel.REGEX_FOR);
		Matcher forMatcher = forPattern.matcher(htmlCode);
		if(forMatcher.find()){
			String str = this.getForData(forMatcher.group(1), xmlUtil);
			forMatcher.appendReplacement(sb2, str);
		}
		forMatcher.appendTail(sb2);*/
	}

	
	
	/**
	 * 获得for标签里面的数据
	 * @param htmlCode
	 * @param unitId
	 * @param columnId
	 * @param siteId
	 * @return
	 */
	private String getForData(String htmlCode, XmlUtil xmlUtil) {
		Pattern pattern = Pattern.compile(CommonLabel.REGEX_LABEL);
		Matcher matcher = pattern.matcher(htmlCode);
		String label = "";
		
		StringBuffer sb = new StringBuffer();
		String category = xmlUtil.getNodeText("/baize/online_survery/category");
		//问卷指定主题问题列表
		List<OnlineSurvey> listThemes = new ArrayList<OnlineSurvey>();
		//一般调查全部问题列表
		List<OnlineSurveyContent> listQuestion = new ArrayList<OnlineSurveyContent>();
		
		//一般调查指定主题
		if(category.equals("1")){
			String theme = xmlUtil.getNodeText("/baize/online_survery/theme");
			listThemes = onlineSurveyDao.findByDefine("findThemesByThemeId", "id", SqlUtil.toSqlString(theme));
			String question = xmlUtil.getNodeText("/baize/online_survery/question");
			question = question.split("###")[0];
			listQuestion = onlineSurveyContentDao.findByDefine("findQuestionByQuestionIds", "ids", SqlUtil.toSqlString(question));
			
		//一般调查全部问题	
		}else if(category.equals("2")){
			listQuestion = onlineSurveyContentDao.findByNamedQuery("findQuestionByCategoryId", "id", "f001");
			
			
			
		//问卷调查指定主题	
		}else if(category.equals("3")){
			String theme = xmlUtil.getNodeText("/baize/online_survery/theme");
			listThemes = onlineSurveyDao.findByDefine("findThemesByThemeId", "id", SqlUtil.toSqlString(theme));
			listQuestion = onlineSurveyContentDao.findByNamedQuery("findQuestionByThemeId", "themeId", theme);
			
			
			
		//问卷调查列表	
		}else if(category.equals("4")){
			listThemes = onlineSurveyDao.findByNamedQuery("findonlineSurveyOfEntitly", "id", "f002");
			if(!CollectionUtil.isEmpty(listThemes)){
				OnlineSurvey onlineSurvey = null;
				for(int i = 0; i < listThemes.size(); i++){
					onlineSurvey = listThemes.get(i);
					while(matcher.find()){
						label = matcher.group();
						//序号
						if(label.equals(OnlineSurverySetLabel.NUMBER)){
							matcher.appendReplacement(sb, (i+1)+"");
							
						//调查主题	
						}else if(label.equals(OnlineSurverySetLabel.THEME)){
							matcher.appendReplacement(sb, onlineSurvey.getName());
							
						}else{
							matcher.appendReplacement(sb, "");
						}
					}
				}
			}
		}
		matcher.appendTail(sb);
		return sb.toString();
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
	 * @param siteDao the siteDao to set
	 */
	public void setSiteDao(SiteDao siteDao) {
		this.siteDao = siteDao;
	}

	/**
	 * @param onlineSurveyDao the onlineSurveyDao to set
	 */
	public void setOnlineSurveyDao(OnlineSurveyDao onlineSurveyDao) {
		this.onlineSurveyDao = onlineSurveyDao;
	}

	/**
	 * @param onlineSurveyContentDao the onlineSurveyContentDao to set
	 */
	public void setOnlineSurveyContentDao(
			OnlineSurveyContentDao onlineSurveyContentDao) {
		this.onlineSurveyContentDao = onlineSurveyContentDao;
	}
}
