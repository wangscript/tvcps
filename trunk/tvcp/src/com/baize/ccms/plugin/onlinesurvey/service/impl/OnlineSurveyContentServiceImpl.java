/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.plugin.onlinesurvey.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.j2ee.cms.biz.sitemanager.dao.SiteDao;
import com.j2ee.cms.biz.sitemanager.domain.Site;
import com.j2ee.cms.plugin.onlinesurvey.dao.OnlineSurveyAnswerContentDao;
import com.j2ee.cms.plugin.onlinesurvey.dao.OnlineSurveyContentDao;
import com.j2ee.cms.plugin.onlinesurvey.domain.OnlineSurveyContent;
import com.j2ee.cms.plugin.onlinesurvey.service.OnlineSurveyContentService;
import com.j2ee.cms.plugin.onlinesurvey.web.form.OnlineSurveyContentForm;
import com.j2ee.cms.sys.GlobalConfig;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.util.FileUtil;
import com.j2ee.cms.common.core.util.SqlUtil;

/**
 * <p>
 * 标题: 网上调查问题具体内容表的业务类
 * </p>
 * <p>
 * 描述: 负责信件的审查,回复等业务
 * </p>
 * <p>
 * 模块: 网上调查
 * </p>
 * <p>
 * 版权: Copyright (c) 2009 
 * 
 * @author 包坤涛
 * @version 1.0
 * @since 2009-6-14 下午02:30:23
 * @history（历次修订内容、修订人、修订时间等） 
*/
 

public class OnlineSurveyContentServiceImpl implements OnlineSurveyContentService {

	private final Logger log = Logger.getLogger(OnlineSurveyContentServiceImpl.class);

	/** 网上调查问题具体内容表dao注入类 */
	private OnlineSurveyContentDao onlineSurveyContentDao;
	/** 单选框或多选框的值dao注入类 */
	private OnlineSurveyAnswerContentDao onlineSurveyAnswerContentDao;
	/** 注入网站dao */
	private SiteDao siteDao;
	
	public Pagination findOnlineQuestionPage(Pagination pagination, String nodeId) {
		return onlineSurveyContentDao.getPagination(pagination, "id", nodeId);
	}
	
	public void addOnlineSurveyContent(OnlineSurveyContent onlineSurveyContent) {
		onlineSurveyContentDao.save(onlineSurveyContent);
	}
	
	public void modifyOnlineSurveyContent(OnlineSurveyContent onlineSurveyContent, String requestionId) {
		OnlineSurveyContent onlineSurveyContentEntitly = onlineSurveyContentDao.getAndClear(requestionId);
		onlineSurveyContentEntitly.setName(onlineSurveyContent.getName());
		onlineSurveyContentEntitly.setViewed(onlineSurveyContent.isViewed());
		onlineSurveyContentEntitly.setFeedback(onlineSurveyContent.isFeedback());
		onlineSurveyContentEntitly.setStyle(onlineSurveyContent.getStyle());
		onlineSurveyContentEntitly.setRequired(onlineSurveyContent.isRequired());
		onlineSurveyContentEntitly.setColCount(onlineSurveyContent.getColCount());
		onlineSurveyContentEntitly.setFeedbackContent(onlineSurveyContent.getFeedbackContent());
		onlineSurveyContentDao.update(onlineSurveyContentEntitly);
	}
	
	public OnlineSurveyContent findOnlineContent(String requestionId) {
		return onlineSurveyContentDao.getAndNonClear(requestionId);
	}
	
	public void deleteOnlineSurveyContentByQuestionId(String requestionId){
		requestionId = SqlUtil.toSqlString(requestionId);
		// 删除问题答案
		onlineSurveyAnswerContentDao.updateByDefine("deleteOnlineSurveyAnswerByQuestionId", "requestionId", requestionId);
		//删除问题
		onlineSurveyContentDao.deleteByIds(requestionId);
	}
	
    public Pagination findQuestionByThemeIdPage(Pagination pagination, String nodeId){
    	return onlineSurveyContentDao.getPagination(pagination, "themeId", nodeId);
    }
	
	
	
	
	
	public ArrayList<String> getStyle(String siteId){
		String str = "";
		String row = "";
		String width = "";
		String height = "";
		ArrayList<String> arraylist = new ArrayList<String>();
		String path = GlobalConfig.appRealPath + "/plugin/onlineSurvey_manager/conf/styleSet.xml";
		String filePath = GlobalConfig.appRealPath + "/plugin/onlineSurvey_manager/conf/"+siteId+"_styleSet.xml";
		if(!FileUtil.isExist(filePath)){
			String path1 = GlobalConfig.appRealPath+"/release/site"+siteId+"/plugin/onlineSurveymanager/conf/"+siteId+"_styleSet.xml";	
			if(!FileUtil.isExist(path1)){
				FileUtil.copy(path, GlobalConfig.appRealPath+"/release/site"+siteId+"/plugin/onlineSurveymanager/conf");
				File file1 = new File(GlobalConfig.appRealPath+"/release/site"+siteId+"/plugin/onlineSurveymanager/conf/styleSet.xml");
				File file2 = new File(path1);
				file1.renameTo(file2);
			}
			FileUtil.copy(path1, GlobalConfig.appRealPath+"/plugin/onlineSurvey_manager/conf");
		}
		if (!FileUtil.isExist(filePath)) {
			log.debug("目录文件不存在" + filePath);
			ArrayList<String> arraylist1 = new ArrayList<String>();
			arraylist1.add(str);
			return arraylist1;
		}
		SAXReader read = new SAXReader();
		try {
			Document document = read.read(new File(filePath));
			Element e = (Element) document.getRootElement().elementIterator("style").next();
			str = e.elementText("stylecontent");
			String listStyle = e.elementText("listStyle");
			row = e.elementText("row");
			width = e.elementText("width");
			height = e.elementText("height");
			
			arraylist.add(str);
			arraylist.add(listStyle);
			arraylist.add(row);
			arraylist.add(width);
			arraylist.add(height);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return arraylist;
	}
	
	public void setStyle(OnlineSurveyContentForm form, String siteId) {
		String filePath = GlobalConfig.appRealPath + "/release/site"+ siteId +"/plugin/onlineSurveymanager/"+siteId+"_styleSet.xml";
		File file = new File(GlobalConfig.appRealPath + "/release/site" + siteId+ "/plugin/onlineSurveymanager/styleSet.xml");
		File file1 = new File(filePath);
		if (!FileUtil.isExist(filePath)){
			FileUtil.copy(GlobalConfig.appRealPath+"/plugin/onlineSurvey_manager/conf/styleSet.xml", 
					GlobalConfig.appRealPath + "/release/site" + siteId	+ "/plugin/onlineSurveymanager");
			file.renameTo(file1);
		}
		SAXReader read = new SAXReader();
		try {
			Document document = read.read(new File(filePath));
			document.selectSingleNode("//plugin_style//style//stylecontent").setText(form.getStyleContent());
			document.selectSingleNode("//plugin_style//style//row").setText(form.getColCount());
			document.selectSingleNode("//plugin_style//style//listStyle").setText(form.getListStyle());
			document.selectSingleNode("//plugin_style//style//width").setText(form.getTextwidth());
			document.selectSingleNode("//plugin_style//style//height").setText(form.getTexthight());
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("utf-8");
			XMLWriter writer = new XMLWriter(new FileOutputStream(filePath), format);
			writer.write(document);
			
			// 拷贝到/plugin/onlineSurvey_manager/conf/目录
			if(FileUtil.isExist(GlobalConfig.appRealPath+"/plugin/onlineSurvey_manager/conf/"+siteId+"_styleSet.xml")){
				FileUtil.delete(GlobalConfig.appRealPath+"/plugin/onlineSurvey_manager/conf/"+siteId+"_styleSet.xml");
			}
			FileUtil.copy(GlobalConfig.appRealPath +"/release/site"+ siteId +"/plugin/onlineSurveymanager/"+siteId+"_styleSet.xml", GlobalConfig.appRealPath+"/plugin/onlineSurvey_manager/conf");
			
			
			// 拷贝文件到发布目录
			Site site = siteDao.getAndClear(siteId);
			String dir = site.getPublishDir().substring(0, site.getPublishDir().length()-site.getPublishDir().split("/")[site.getPublishDir().split("/").length-1].length());
			String publishDir = dir+"/plugin/onlineSurvey_manager/conf";
			if(!FileUtil.isExist(publishDir)){
				FileUtil.makeDirs(publishDir);
			}
			if(FileUtil.isExist(publishDir+"/"+siteId+"_styleSet.xml")){
				FileUtil.delete(publishDir+"/"+siteId+"_styleSet.xml");
			}
			FileUtil.copy(GlobalConfig.appRealPath +"/release/site"+ siteId +"/plugin/onlineSurveymanager/"+siteId+"_styleSet.xml", publishDir);
			
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	public Pagination finOnlineAnswerQuestions(Pagination pagination) {
		return onlineSurveyContentDao.getPagination(pagination);
	}
     
	public Pagination finOnlineAnswerQuestions(Pagination pagination, String id) {
		if (id != null) {
			Pagination paginati = onlineSurveyContentDao.getPagination(
					pagination, "id", id);
			return onlineSurveyContentDao.getPagination(pagination, "id", id);
		} else {
			return new Pagination();
		}
	}
	
	public Pagination finSurveyServiceData(Pagination pagination, String category) {
		return null;
	}
	
	public OnlineSurveyAnswerContentDao getOnlineSurveyAnswerContentDao() {
		return onlineSurveyAnswerContentDao;
	}
	public void setOnlineSurveyAnswerContentDao(OnlineSurveyAnswerContentDao onlineSurveyAnswerContentDao) {
		this.onlineSurveyAnswerContentDao = onlineSurveyAnswerContentDao;
	}
	public OnlineSurveyContentDao getOnlineSurveyContentDao() {
		return onlineSurveyContentDao;
	}
	public void setOnlineSurveyContentDao(OnlineSurveyContentDao onlineSurveyContentDao) {
		this.onlineSurveyContentDao = onlineSurveyContentDao;
	}

	/**
	 * @param siteDao the siteDao to set
	 */
	public void setSiteDao(SiteDao siteDao) {
		this.siteDao = siteDao;
	}
}