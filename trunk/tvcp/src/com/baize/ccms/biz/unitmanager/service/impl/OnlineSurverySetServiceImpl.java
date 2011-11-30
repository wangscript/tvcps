/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.unitmanager.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.j2ee.cms.biz.templatemanager.dao.TemplateUnitDao;
import com.j2ee.cms.biz.templatemanager.domain.TemplateUnit;
import com.j2ee.cms.biz.templatemanager.domain.TemplateUnitCategory;
import com.j2ee.cms.biz.unitmanager.service.OnlineSurverySetService;
import com.j2ee.cms.biz.unitmanager.web.form.OnlineSurverySetForm;
import com.j2ee.cms.plugin.onlinesurvey.dao.OnlineSurveyContentDao;
import com.j2ee.cms.plugin.onlinesurvey.dao.OnlineSurveyDao;
import com.j2ee.cms.plugin.onlinesurvey.domain.OnlineSurvey;
import com.j2ee.cms.plugin.onlinesurvey.domain.OnlineSurveyContent;
import com.j2ee.cms.sys.GlobalConfig;
import com.j2ee.cms.common.core.util.CollectionUtil;
import com.j2ee.cms.common.core.util.FileUtil;
import com.j2ee.cms.common.core.util.IDFactory;
import com.j2ee.cms.common.core.util.StringUtil;
import com.j2ee.cms.common.core.util.XmlUtil;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CPS</p>
 * <p>版权: Copyright (c) 2009  
 * @author 郑荣华
 * @version 1.0
 * @since 2009-6-1 下午05:42:58
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class OnlineSurverySetServiceImpl implements OnlineSurverySetService{
	
	private static final Logger log = Logger.getLogger(OnlineSurverySetServiceImpl.class);
	
	/** 注入网上调查类型dao */
	private OnlineSurveyDao onlineSurveyDao;
	/** 注入模版单元dao */
	private TemplateUnitDao templateUnitDao;
	/** 注入网上调查问题dao */
	private OnlineSurveyContentDao onlineSurveyContentDao;
	
	/**
	 * 查找配置文件
	 * @param form  栏目链接表单对象
	 */
	public OnlineSurverySetForm findConfig(OnlineSurverySetForm form, String siteId){
		String unitId = form.getUnit_unitId();
		String categoryId = form.getUnit_categoryId();
		String str1[] = {"unitId", "CategoryId"};
		Object obj1[] = {unitId, categoryId};
		List tempUnitList = templateUnitDao.findByNamedQuery("findTemplateUnitByUnitIdAndUnitCategory", str1, obj1);
		String htmlContent = "";
		if(tempUnitList != null && tempUnitList.size() > 0 ){
			TemplateUnit unit = (TemplateUnit)tempUnitList.get(0);
			htmlContent = unit.getHtml();
			String filePath = unit.getConfigFile();
			if(filePath != null && !filePath.equals("")) {
				form = this.setXmlData(GlobalConfig.appRealPath + filePath, form);
			} else {
				String haveconfigFilePath = GlobalConfig.appRealPath + TemplateUnit.UNIT_CONFIG_DIR + "/online_survery.xml";
				form = this.setXmlData(haveconfigFilePath, form);
			}
			List<OnlineSurveyContent> list = new ArrayList<OnlineSurveyContent>();
			//一般调查主题
			if(form.getCategory().equals("1")){
				list =  onlineSurveyDao.findByNamedQuery("findAllName", "categoryId", "f020001");
			
			//问卷调查指定主题	
			}else if(form.getCategory().equals("3")){
				list =  onlineSurveyDao.findByNamedQuery("findAllName", "categoryId", "f020002");
			}
			form.setScript(unit.getScript());
			form.setThemeList(list);
			
		}else{
			String configFile = "";
			configFile = GlobalConfig.appRealPath + TemplateUnit.UNIT_CONFIG_DIR+"/online_survery.xml";
			form = this.setXmlData(configFile, form);
		}
		
		// 样式
		String styleSetPath = GlobalConfig.appRealPath + "/plugin/onlineSurvey_manager/conf/"+siteId+"_styleSet.xml";
		if(!FileUtil.isExist(styleSetPath)){
			styleSetPath = GlobalConfig.appRealPath + "/plugin/onlineSurvey_manager/conf/styleSet.xml";
		}
		XmlUtil xmlUtil = XmlUtil.getInstance(styleSetPath);
		if(StringUtil.isEmpty(htmlContent)){
			htmlContent = ""; //xmlUtil.getNodeText("/plugin_style/style/stylecontent");
		}
		form.setHtmlContent(htmlContent);
		form.setAnswerRow(xmlUtil.getNodeText("/plugin_style/style/row"));
		form.setHeight(xmlUtil.getNodeText("/plugin_style/style/height"));
		form.setWidth(xmlUtil.getNodeText("/plugin_style/style/width"));
		return form;
	}
	
	/**
	 * 获取xml的值来设置form 	
	 * @param filePath xml文件的路径
	 * @param OnlineSurverySetForm 网上调查form对象
	 */
	private OnlineSurverySetForm setXmlData(String filePath, OnlineSurverySetForm form){
		XmlUtil xmlUtil = XmlUtil.getInstance(filePath);
		// 获取xml文件中的字段信息
		form.setCategory(xmlUtil.getNodeText("/j2ee.cms/online_survery/category"));
		form.setTheme(xmlUtil.getNodeText("/j2ee.cms/online_survery/theme"));
		form.setQuestion(xmlUtil.getNodeText("/j2ee.cms/online_survery/question"));
		form.setAnswerCount(xmlUtil.getNodeText("/j2ee.cms/online_survery/answerCount"));
		form.setRowCount(xmlUtil.getNodeText("/j2ee.cms/online_survery/rowCount"));
		form.setColCount(xmlUtil.getNodeText("/j2ee.cms/online_survery/colCount"));
		form.setDefaultCount(xmlUtil.getNodeText("/j2ee.cms/online_survery/defaultCount"));
		form.setMore(xmlUtil.getNodeText("/j2ee.cms/online_survery/more"));
		return form;
	}
	
	public String findThemeByCategory(String category){
		String themes = "";
		String ids = "";
		String names = "";
		List<OnlineSurvey> list = new ArrayList<OnlineSurvey>();
		// 一般主题列表
		if(category.equals("1")){
			list =  onlineSurveyDao.findByNamedQuery("findAllName", "categoryId", "f020001");
			
		// 问卷调查主题列表	
		}else{
			list =  onlineSurveyDao.findByNamedQuery("findAllName", "categoryId", "f020002");
		}
		if(!CollectionUtil.isEmpty(list)){
			OnlineSurvey onlineSurvey = null;
			for(int i = 0; i < list.size(); i++){
				onlineSurvey = list.get(i);
				if(!StringUtil.isEmpty(ids)){
					ids += ":::"+onlineSurvey.getId();
					names += ":::"+onlineSurvey.getName();
				}else{
					ids = onlineSurvey.getId();
					names = onlineSurvey.getName();
				}
			}
			themes = ids + "###" + names;
		}
		return themes;
	}

	public String saveConfigXml(OnlineSurverySetForm form){
		String unitId = form.getUnit_unitId();
		//新xml文件的保存路径
		String newFilePath = "";
		String filePath = "";
		TemplateUnit templateUnit = templateUnitDao.getAndClear(unitId);
		if(templateUnit != null){
			String configFilePath = templateUnit.getConfigFile();
			String configDir = templateUnit.getConfigDir();
			String haveconfigFilePath = GlobalConfig.appRealPath+TemplateUnit.UNIT_CONFIG_DIR+"/online_survery.xml";
			filePath = haveconfigFilePath;
			if(configFilePath != null && !configFilePath.equals("")){
				newFilePath = GlobalConfig.appRealPath+configFilePath;
			}else{
				newFilePath = GlobalConfig.appRealPath+configDir +"/"+ IDFactory.getId()+".xml";
			}
		}
		this.setXmlData(filePath, newFilePath, form);
		return newFilePath;
	}
	
	private void setXmlData(String filePath, String newFilePath, OnlineSurverySetForm form){
		XmlUtil xmlUtil = XmlUtil.getInstance(filePath);		
		xmlUtil.setNodeCDATAText("j2ee.cms/online_survery/category",form.getCategory());
		xmlUtil.setNodeCDATAText("j2ee.cms/online_survery/theme",form.getTheme());
		xmlUtil.setNodeCDATAText("j2ee.cms/online_survery/question",form.getQuestion());
		xmlUtil.setNodeCDATAText("j2ee.cms/online_survery/answerCount",form.getAnswerCount());
		xmlUtil.setNodeCDATAText("j2ee.cms/online_survery/rowCount",form.getRowCount());
		xmlUtil.setNodeCDATAText("j2ee.cms/online_survery/colCount",form.getColCount());
		xmlUtil.setNodeCDATAText("j2ee.cms/online_survery/defaultCount",form.getDefaultCount());
		xmlUtil.setNodeCDATAText("j2ee.cms/online_survery/more",form.getMore());
		xmlUtil.save(newFilePath);
	}
	
	public void addOnlineSurveryData(OnlineSurverySetForm form, String filePath, String siteId){
		String unitId = form.getUnit_unitId();
		String categoryId = form.getUnit_categoryId();
		TemplateUnit unit = templateUnitDao.getAndClear(unitId);
		if(unit != null){
			TemplateUnitCategory templateUnitCategory = new TemplateUnitCategory();
			templateUnitCategory.setId(categoryId);
			unit.setTemplateUnitCategory(templateUnitCategory);
			unit.setHtml(form.getHtmlContent());
			unit.setConfigFile(filePath);
			unit.setCss(form.getUnit_css());
			unit.setScript(form.getScript());
			templateUnitDao.saveOrUpdate(unit);
		}
	}
	
	
	
	
	
	
    public List<OnlineSurverySetForm> findAllOnlineSurvey(String categoryId){
	     return onlineSurveyDao.findByNamedQuery("findAllName", "categoryId", categoryId);
    }
	        
	public void setStyle(OnlineSurverySetForm form, String siteId){
		String path = GlobalConfig.appRealPath + TemplateUnit.UNIT_CONFIG_DIR + "/online_survery.xml";
		if (!FileUtil.isExist(path)) {
			log.debug("目录文件不存在" + path);
			return;
		}
		SAXReader read = new SAXReader();
		try {
			Document document = read.read(new File(path));
			document.selectSingleNode("//plugin_style//style//category").setText(form.getCategory());
			document.selectSingleNode("//plugin_style//style//theme").setText(form.getTheme());
			document.selectSingleNode("//plugin_style//style//question").setText(form.getQuestion());
			document.selectSingleNode("//plugin_style//style//answerCount").setText(form.getAnswerCount());
			document.selectSingleNode("//plugin_style//style//rowCount").setText(form.getRowCount());
			document.selectSingleNode("//plugin_style//style//colCount").setText(form.getColCount());
			document.selectSingleNode("//plugin_style//style//defaultCount").setText(form.getDefaultCount());
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("utf-8");
			XMLWriter writer = new XMLWriter(new FileOutputStream(path), format);
			writer.write(document);
			writer.close();
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

	@SuppressWarnings("finally")
	public OnlineSurverySetForm getStyle(String siteid, OnlineSurverySetForm form){
		String category = "";
		String theme = "";
		String question = "";
		String answerCount = "";
		String rowCount = "";
		String colCount = "";
		String defaultCount = "";
		String filePath = GlobalConfig.appRealPath + TemplateUnit.UNIT_CONFIG_DIR + "/online_survery.xml";		                                                                                     
		if (!FileUtil.isExist(filePath)) {
			log.debug("目录文件不存在" + filePath);
			return  form;
		}
		SAXReader read = new SAXReader();
		try {
			Document document = read.read(new File(filePath));
			Element e = (Element) document.getRootElement().elementIterator("style").next();
			//调查类别
			category = e.elementText("category");
			//问卷主题
			theme = e.elementText("theme");
			//问卷列数
			answerCount = e.elementText("answerCount");
			//调查问题
			question = e.elementText("question");
			//反馈框行数大小
			rowCount = e.elementText("rowCount");
			//反馈框行数列数
			colCount = e.elementText("colCount");
			//列表默认
			defaultCount = e.elementText("defaultCount");
			form.setCategory(category);
			form.setTheme(theme);
			form.setQuestion(question);
			form.setAnswerCount(answerCount);
			form.setRowCount(rowCount);
			form.setColCount(colCount);
			form.setDefaultCount(defaultCount);
		}catch(DocumentException e){
			e.printStackTrace();
		}finally {
			return form;
		}
	}

	/**
	 * @param onlineSurveyDao the onlineSurveyDao to set
	 */
	public void setOnlineSurveyDao(OnlineSurveyDao onlineSurveyDao) {
		this.onlineSurveyDao = onlineSurveyDao;
	}

	/**
	 * @param templateUnitDao the templateUnitDao to set
	 */
	public void setTemplateUnitDao(TemplateUnitDao templateUnitDao) {
		this.templateUnitDao = templateUnitDao;
	}


	/**
	 * @param onlineSurveyContentDao the onlineSurveyContentDao to set
	 */
	public void setOnlineSurveyContentDao(
			OnlineSurveyContentDao onlineSurveyContentDao) {
		this.onlineSurveyContentDao = onlineSurveyContentDao;
	}
}