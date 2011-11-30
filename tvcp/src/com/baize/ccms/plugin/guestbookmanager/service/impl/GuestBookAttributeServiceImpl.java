/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.plugin.guestbookmanager.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.j2ee.cms.biz.sitemanager.dao.SiteDao;
import com.j2ee.cms.biz.sitemanager.domain.Site;
import com.j2ee.cms.plugin.guestbookmanager.service.GuestBookAttributeService;
import com.j2ee.cms.plugin.guestbookmanager.web.form.GuestBookForm;
import com.j2ee.cms.sys.GlobalConfig;
import com.j2ee.cms.common.core.util.FileUtil;

/**
 * 
 * <p>
 * 标题: —— 留言板业务具体实现类
 * </p>
 * <p>
 * 描述: —— 简要描述类的职责、实现方式、使用注意事项等
 * </p>
 * <p>
 * 模块: CPS
 * </p>
 * <p>
 * 版权: Copyright (c) 2009  
 * </p>
 * <p>
 * 网址：http://www.j2ee.cmsweb.com
 * 
 * @author 曹名科
 * @version 1.0
 * @since 2009-11-2 下午02:57:55
 * @history（历次修订内容、修订人、修订时间等）
 */
 

public class GuestBookAttributeServiceImpl implements GuestBookAttributeService {
	
	/** 注入网站dao */
	private SiteDao siteDao;
	
	/** 显示IP */
	private final static String IP = "<!--ip-->";
	/** 文章评论ID */
	private final static String COMMENTID = "<!--commentId-->";
	/** 发表人 */
	private final static String AUTHOR = "<!--author-->";
	/** 发表内容 */
	private final static String CONTENT = "<!--content-->";
	/** 发表时间 */
	private final static String DATE = "<!--date-->";
	
	private final Logger log = Logger.getLogger(GuestBookAttributeServiceImpl.class);

	public String writerAttribute(String siteId, GuestBookForm form, String sessionId) {
		String mess = "";
		String filePath = GlobalConfig.appRealPath +"/plugin/guestbook_manager/conf/"+siteId+".xml";
		if (!FileUtil.isExist(filePath)) {
			log.debug("目录文件不存在" + filePath);
			mess = "目录文件不存在";
			return mess;
		}
		SAXReader read = new SAXReader();
		XMLWriter writer = null;
		try {
			Document document = read.read(new File(filePath));
			document.selectSingleNode("//plugin//guestbookplugin//openType").setText(form.getOpenType());

			/** 开放类型 0关闭 1全天开放 2定时开放 3达到限定留言时关闭 */
			if (form.getOpenType().equals("2")) {
				document
						.selectSingleNode("//plugin//guestbookplugin//openHour")
						.setText(form.getOpenHour());
				document.selectSingleNode(
						"//plugin//guestbookplugin//openMinute").setText(
						form.getOpenMinute());
				document.selectSingleNode(
						"//plugin//guestbookplugin//openHour1").setText(
						form.getOpenHour1());
				document.selectSingleNode(
						"//plugin//guestbookplugin//openMinute1").setText(
						form.getOpenMinute1());
			}
			if (form.getOpenType().equals("3")) {
				document.selectSingleNode(
						"//plugin//guestbookplugin//leaveCount").setText(
						form.getLeaveCount());
				document
						.selectSingleNode("//plugin//guestbookplugin//leaveMsg")
						.setText(form.getLeaveMsg());
			}

			document.selectSingleNode("//plugin//guestbookplugin//isAudit")
					.setText(form.getIsAudit());
			document.selectSingleNode("//plugin//guestbookplugin//isPublish")
					.setText(form.getIsPublish());
			document.selectSingleNode("//plugin//guestbookplugin//sessionId")
					.setText(sessionId);
			OutputFormat format = new OutputFormat();
			format.setEncoding("utf-8");
			writer = new XMLWriter(new FileOutputStream(filePath), format);
			writer.write(document);
			mess = "保存成功";
			
			// 拷贝文件到发布目录
			Site site = siteDao.getAndClear(siteId);
			String dir = site.getPublishDir().substring(0, site.getPublishDir().length()-site.getPublishDir().split("/")[site.getPublishDir().split("/").length-1].length());
			String publishDir = dir+"/plugin/guestbook_manager/conf";
			if(!FileUtil.isExist(publishDir)){
				FileUtil.makeDirs(publishDir);
			}
			if(FileUtil.isExist(publishDir+"/"+siteId+".xml")){
				FileUtil.delete(publishDir+"/"+siteId+".xml");
			}
			FileUtil.copy(GlobalConfig.appRealPath +"/plugin/guestbook_manager/conf/"+siteId+".xml", publishDir);
			
			
		} catch (DocumentException e) {
			e.printStackTrace();
			mess = "保存失败";
		} catch (IOException e) {
			e.printStackTrace();
			mess = "保存失败";
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		log.info(mess);
		return mess;
	}

	public void getAttributeSetForm(GuestBookForm form, String siteId) {
		Map<String, String> map = readAttributeSet(siteId);
		form.setOpenType(map.get("openType"));
		form.setIsAudit(map.get("isAudit"));
		form.setOpenHour(map.get("openHour"));
		form.setOpenMinute(map.get("openMinute"));
		form.setOpenHour1(map.get("openHour1"));
		form.setOpenMinute1(map.get("openMinute1"));
		form.setIsPublish(map.get("isPublish"));
		form.setLeaveCount(map.get("leaveCount"));
		form.setLeaveMsg(map.get("leaveMsg"));
	}

	/**
	 * 获取属性设置
	 * 
	 * @param siteId
	 * @return
	 */
	private Map<String, String> readAttributeSet(String siteId) {
		Map<String, String> map = new HashMap<String, String>();
		String path = GlobalConfig.appRealPath + "/plugin/guestbook_manager/conf/"+siteId+".xml";
		String realPath = GlobalConfig.appRealPath + "/plugin/guestbook_manager/conf/guestBookAttribute.xml";
		String filePath = GlobalConfig.appRealPath + "/release/site" + siteId+ "/plugin/guestbook_manager/"+siteId+".xml";
		String destDir= GlobalConfig.appRealPath + "/release/site" + siteId+ "/plugin/guestbook_manager";
		String tempPath = GlobalConfig.appRealPath + "/release/site" + siteId+ "/plugin/guestbook_manager/guestBookAttribute.xml";
		if(!FileUtil.isExist(path)){
			FileUtil.copy(realPath, destDir);
			File file = new File(tempPath);
			File newFile = new File(filePath);
			file.renameTo(newFile);
			FileUtil.copy(filePath, GlobalConfig.appRealPath+"/plugin/guestbook_manager/conf");
		}
		
		SAXReader read = new SAXReader();
		try {
			Document document = read.read(new File(path));
			Element e = (Element) document.getRootElement().elementIterator("guestbookplugin").next();
			/** 开放类型 0关闭 1全天开放 2定时开放 3达到限定留言时关闭 */
			map.put("openType", e.elementText("openType"));
			map.put("openHour", e.elementText("openHour"));// 开放的时间
			map.put("openMinute", e.elementText("openMinute"));
			map.put("openHour1", e.elementText("openHour1"));// 结束开放的时间
			map.put("openMinute1", e.elementText("openMinute1"));
			map.put("leaveCount", e.elementText("leaveCount"));// 留言的数量
			map.put("leaveMsg", e.elementText("leaveMsg"));// 当达到数量时给的提示
			map.put("isAudit", e.elementText("isAudit"));// 是否审核 0否 1是
			map.put("isPublish", e.elementText("isPublish"));// 有敏感词时是否发布
			// 0为不发布1为发布
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return map;
	}

	public String getGuestBookStyle(String siteId) {
		String content = "";
		String path = GlobalConfig.appRealPath + "/plugin/guestbook_manager/conf/"+siteId+".xml";
		String realPath = GlobalConfig.appRealPath + "/plugin/guestbook_manager/conf/guestBookAttribute.xml";
		String filePath = GlobalConfig.appRealPath + "/release/site" + siteId+ "/plugin/guestbook_manager/"+siteId+".xml";
		String destDir= GlobalConfig.appRealPath + "/release/site" + siteId+ "/plugin/guestbook_manager";
		String tempPath = GlobalConfig.appRealPath + "/release/site" + siteId+ "/plugin/guestbook_manager/guestBookAttribute.xml";
		if(!FileUtil.isExist(path)){
			FileUtil.copy(realPath, destDir);
			File file = new File(tempPath);
			File newFile = new File(filePath);
			file.renameTo(newFile);
		}
		SAXReader read = new SAXReader();
		try {
			Document document = read.read(new File(path));
			Element e = (Element) document.getRootElement().elementIterator("style").next();
			content = e.elementText("styleContent");
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return content;
	}

	public String setGuestBookStyle(String styleContent, String siteId) {
		String content = "";
		String filePath = GlobalConfig.appRealPath +"/plugin/guestbook_manager/conf/"+siteId+".xml";
		if (!FileUtil.isExist(filePath)) {
			log.info("目录不存在");
			return content;
		}
		SAXReader read = new SAXReader();
		XMLWriter writer = null;
		try {
			Document document = read.read(new File(filePath));
			document.selectSingleNode("//plugin//style//styleContent").setText(
					styleContent);
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("utf-8");
			writer = new XMLWriter(new FileOutputStream(filePath), format);
			writer.write(document);
			content = "保存成功";
		} catch (DocumentException e) {
			content = "保存失败";
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			content = "保存失败";
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			content = "保存失败";
			e.printStackTrace();
		} catch (IOException e) {
			content = "保存失败";
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return content;
	}

	public StringBuffer getReplaceTag(String siteId) {
		String input = getGuestBookStyle(siteId);
		String regex = "<!--\\w\\d+-->";
		Pattern pa =Pattern.compile(regex);
		Matcher ma = pa.matcher(input);
		StringBuffer sb = new StringBuffer();
		String findout="";
		while(ma.find()){
			findout=ma.group();
			if(findout.equals(IP)){
				ma.appendReplacement(sb, "显示IP");
			}
			if(findout.equals(COMMENTID)){
				ma.appendReplacement(sb, "ID");
			}
			if(findout.equals(AUTHOR)){
				ma.appendReplacement(sb, "留言人");
			}
			if(findout.equals(CONTENT)){
				ma.appendReplacement(sb, "留言内容");
			}
			if(findout.equals(DATE)){
				ma.appendReplacement(sb, "留言时间");
			}
			
		}
		ma.appendTail(sb);
		return sb;
	}

	/**
	 * @param siteDao the siteDao to set
	 */
	public void setSiteDao(SiteDao siteDao) {
		this.siteDao = siteDao;
	}
}
