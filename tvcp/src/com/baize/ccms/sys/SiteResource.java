/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.sys;

import java.io.File;

import com.j2ee.cms.common.core.util.FileUtil;

import org.apache.log4j.Logger;

/**
 * <p>标题: 网站资源</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 系统配置</p>
 * <p>版权: Copyright (c) 2009  
 * @author <a href="mailto:sean_yang@163.com">杨信</a>
 * @version 1.0
 * @since 2009-4-29 下午02:26:44
 * @history（历次修订内容、修订人、修订时间等）
 */
public final class SiteResource {
	
	private static final Logger log = Logger.getLogger(SiteResource.class);
	
	private static String resoureDir = GlobalConfig.appRealPath + "/release";
	
	private static String resouceRelativeDir = "/release";
	
	/**
	 * 初始化网站相关目录
	 * @param siteId
	 */
	public static void initSiteDir(String siteId) {
		mkDir(getTemplateDir(siteId, false));
		mkDir(getTemplateInstanceDir(siteId, false));
		mkDir(getUnitConfigDir(siteId, false));
//		mkDir(getPublishDynimacDir(siteId, false));
//		mkDir(getPublishStaticDir(siteId, false));
		mkDir(getBuildDynimacDir(siteId, false));
		mkDir(getBuildStaticDir(siteId, false));
		mkDir(getBuildStaticTemplateDir(siteId, false));
		mkDir(getPreviewDir(siteId, false));
		mkDir(getFlashDir(siteId, false));
		mkDir(getPictureDir(siteId, false));
		mkDir(getAttachmentDir(siteId, false));
		mkDir(getJsDir(siteId, false));
		mkDir(getWaterDir(siteId, false));
		mkDir(getRssDir(siteId, false));
		mkDir(getStyleDir(siteId, false));
		mkDir(getGuestBookDir(siteId, false));
		mkDir(getOnlineStyleDir(siteId, false));
		//样式文件创建完毕后 将工程中的文章评论文件和样式拷贝到网站目录中
		String fromFile = GlobalConfig.appRealPath+File.separator+"plugin"+File.separator+"article_comment"+File.separator+"conf";
		String toFile = GlobalConfig.appRealPath+File.separator+"release"+File.separator+"site"+siteId+File.separator+"plugin"+File.separator+"articleComments";
		FileUtil.copy(fromFile+File.separator+"articleAttribute.xml", toFile);
		FileUtil.copy(fromFile+File.separator+"styleSet.xml", toFile);
		//将工程中留言管理的文件拷贝到网站目录中
		String guestBookFromFile = GlobalConfig.appRealPath+File.separator+"plugin"+File.separator+"guestbook_manager"+File.separator+"conf";
		String guestBookToFile = GlobalConfig.appRealPath+File.separator+"release"+File.separator+"site"+siteId+File.separator+"plugin"+File.separator+"guestbook_manager";
		FileUtil.copy(guestBookFromFile+File.separator+"guestBookAttribute.xml", guestBookToFile);
		
		//样式文件创建完毕后 将工程中的网上调查文件xml拷贝到网站目录中
		String fromFile1 = GlobalConfig.appRealPath+File.separator+"plugin"+File.separator+"onlineSurvey_manager"+File.separator+"conf";
	//	System.out.println(fromFile1);
		String toFile1 = GlobalConfig.appRealPath+File.separator+"release"+File.separator+"site"+siteId+File.separator+"plugin"+File.separator+"onlineSurveymanager"+File.separator+"conf";
		
		FileUtil.copy(fromFile1+File.separator+"onlineAttribute.xml", toFile1);
		FileUtil.copy(fromFile1+File.separator+"styleSet.xml", toFile1);
		if (!FileUtil.isExist(getTempDir(false))) {
			mkDir(getTempDir(false));
		}
	}
	
	/**
	 * 销毁网站相关目录
	 * @param siteId
	 */
	public static void destorySiteDir(String siteId) {
		FileUtil.delete(getSiteDir(siteId, false));
	}
	
	/**
	 * 获取临时目录
	 * @param relative true:相对路径，false:物理路径
	 * @return
	 */
	public static String getTempDir(boolean relative) {
		if (relative) {
			return File.separator+"temp"; 
		} else {
			return GlobalConfig.appRealPath + File.separator+"temp";
		}
	}
	
	/**
	 * 获取网站目录
	 * @param siteId
	 * @param relative true:相对路径，false:物理路径
	 * @return
	 */
	public static String getSiteDir(String siteId, boolean relative) {
		if (relative) {
			return resouceRelativeDir + "/site" + siteId; 
		} else {
			return resoureDir + "/site" + siteId;
		}
	}
	
	/**
	 * 发布动态页面路径
	 * @param relative true:相对路径，false:物理路径
	 * @param siteId
	 * @return
	 */
	public static String getPublishDynimacDir(String siteId, boolean relative) {
		if (relative) {
			return resouceRelativeDir + "/site" + siteId + "/public"+"/dynimac";
		} else {
			return resoureDir + "/site" + siteId + "/public"+"/dynimac";
		}
	}
	
	/**
	 * 发布静态页面路径
	 * @param siteId
	 * @param relative true:相对路径，false:物理路径
	 * @return
	 */
	public static String getPublishStaticDir(String siteId, boolean relative) {
		if (relative) {
			return resouceRelativeDir + "/site" + siteId +"/public"+"/static";
		} else {
			return resoureDir + "/site" + siteId + "/public"+"/static";
		}
	}
	
	/**
	 * 获取生成静态页面路径
	 * @param siteId
	 * @param relative true:相对路径，false:物理路径
	 * @return
	 */
	public static String getBuildStaticDir(String siteId, boolean relative) {
		if (relative) {
			return resouceRelativeDir +"/site" + siteId + "/build"+"/static";
		} else {
			return resoureDir +"/site" + siteId + "/build"+"/static";
		}
	}
	
	/**
	 * 获取生成动态页面路径
	 * @param siteId
	 * @param relative true:相对路径，false:物理路径
	 * @return
	 */
	public static String getBuildDynimacDir(String siteId, boolean relative) {
		if (relative) {
			return resouceRelativeDir + "/site" + siteId +"/build"+"/dynimac";
		} else {
			return resoureDir +"/site" + siteId + "/build"+"/dynimac";
		}
		
	}
	
	/**
	 * 获取静态模板页面路径
	 * @param siteId
	 * @param relative true:相对路径，false:物理路径
	 * @return
	 */
	public static String getBuildStaticTemplateDir(String siteId, boolean relative) {
		if (relative) {
			return resouceRelativeDir + "/site" + siteId + "/build"+"/static"+"/template_instance";
		} else {
			return resoureDir + "/site" + siteId + "/build"+"/static"+"/template_instance";
		}
		
	}
	
	/**
	 * 获取预览页面路径
	 * @param siteId
	 * @param relative true:相对路径，false:物理路径
	 * @return
	 */
	public static String getPreviewDir(String siteId, boolean relative) {
		if (relative) {
			return resouceRelativeDir +"/site" + siteId + "/preview";
		} else {
			return resoureDir + "/site" + siteId + "/preview";
		}
	}
	
	/**
	 * 模板路径
	 * @param siteId
	 * @param relative true:相对路径，false:物理路径
	 * @return
	 */
	public static String getTemplateDir(String siteId, boolean relative) {
		if (relative) {
			return resouceRelativeDir + "/site" + siteId + "/template";
		} else {
			return resoureDir + File.separator+"site" + siteId + File.separator+"template";
		}
	}
	
	/**
	 * 模板实例路径
	 * @param siteId
	 * @param relative true:相对路径，false:物理路径
	 * @return
	 */
	public static String getTemplateInstanceDir(String siteId, boolean relative) {
		if (relative) {
			return resouceRelativeDir + "/site" + siteId + "/template_instance";
		} else {
			return resoureDir + File.separator+"site" + siteId + File.separator+"template_instance";
		}
		
	}
	
	/**
	 * 获取单元配置路径
	 * @param siteId
	 * @param relative true:相对路径，false:物理路径
	 * @return
	 */
	public static String getUnitConfigDir(String siteId, boolean relative) {
		if (relative) {
			return resouceRelativeDir + "/site" + siteId + "/unit_config";
		} else {
			return resoureDir + File.separator+"site" + siteId + File.separator+"unit_config";
		}
	}
	
	/**
	 * 图片路径
	 * @param siteId
	 * @param relative true:相对路径，false:物理路径
	 * @return
	 */
	public static String getPictureDir(String siteId, boolean relative) {
		if (relative) {
			return resouceRelativeDir + "/site" + siteId + "/upload"+"/picture";
		} else {
			return resoureDir + "/site" + siteId +"/upload"+"/picture";
		}
	}
	
	/**
	 * Flash路径
	 * @param siteId
	 * @param relative true:相对路径，false:物理路径
	 * @return
	 */
	public static String getFlashDir(String siteId, boolean relative) {
		if (relative) {
			return resouceRelativeDir + "/site" + siteId + "/upload"+"/flash";
		} else {
			return resoureDir + File.separator+"site" + siteId + File.separator+"upload"+File.separator+"flash";
		}
	}
	
	/**
	 * 附件路径
	 * @param siteId
	 * @param relative true:相对路径，false:物理路径
	 * @return
	 */
	public static String getAttachmentDir(String siteId, boolean relative) {
		if (relative) {
			return resouceRelativeDir + "/site" + siteId + "/upload"+"/attachment";
		} else {
			return resoureDir + File.separator+"site" + siteId + File.separator+"upload"+File.separator+"attachment";
		}
	}
	
	/**
	 * js脚本文件路径
	 * @param siteId
	 * @param relative true:相对路径，false:物理路径
	 * @return
	 */
	public static String getJsDir(String siteId, boolean relative) {
		if (relative) {
			return resouceRelativeDir + "/site" + siteId + "/upload"+"/js";
		} else {
			return resoureDir + File.separator+"site" + siteId + File.separator+"upload"+File.separator+"js";
		}
	}
	
	/**
	 * 水印图片路径
	 * @param siteId 网站ID
	 * @param relative  true:相对路径，false:物理路径
	 * @return 目录结构
	 */
	public static String getWaterDir(String siteId, boolean relative){
		if (relative) {
			return resouceRelativeDir + File.separator+"site" + siteId + File.separator+"upload"+File.separator+"water";
		} else {
			return resoureDir + File.separator+"site" + siteId + File.separator+"upload"+File.separator+"water";
		}
	}
	/**
	 * RSS订阅生成路径
	 * @param siteId 网站ID
	 * @param relative  true:相对路径，false:物理路径
	 * @return 目录结构
	 */
	public static String getRssDir(String siteId, boolean relative){
		if (relative) {
			return resouceRelativeDir + File.separator+"site" + siteId + File.separator+"plugin"+File.separator+"rss"+File.separator+"conf";
		} else {
			return resoureDir + File.separator+"site" + siteId + File.separator+"plugin"+File.separator+"rss"+File.separator+"conf";
		}
	}
	/**
	 * 文章评论样式配置路径
	 * @param siteId 网站ID
	 * @param relative  true:相对路径，false:物理路径
	 * @return 目录结构
	 */
	public static String getStyleDir(String siteId, boolean relative){
		if (relative) {
			return resouceRelativeDir + File.separator+"site" + siteId + File.separator+"plugin"+File.separator+"articleComments";
		} else {
			return resoureDir + File.separator+"site" + siteId + File.separator+"plugin"+File.separator+"articleComments";
		}
		
	}
	
	
	/**
	 * 留言配置路径
	 * @param siteId 网站ID
	 * @param relative  true:相对路径，false:物理路径
	 * @return 目录结构
	 */
	public static String getGuestBookDir(String siteId, boolean relative){
		if (relative) {
			return resouceRelativeDir + File.separator+"site" + siteId + File.separator+"plugin"+File.separator+"guestbook_manager";
		} else {
			return resoureDir + File.separator+"site" + siteId + File.separator+"plugin"+File.separator+"guestbook_manager";
		}
		
	}
	
	/**
	 *在线调查样式配置路径
	 * @param siteId 网站ID
	 * @param relative  true:相对路径，false:物理路径
	 * @return 目录结构
	 */
	public static String getOnlineStyleDir(String siteId, boolean relative){
		if (relative) {
			return resouceRelativeDir + File.separator+"site" + siteId + File.separator+"plugin"+File.separator+"onlineSurveymanager"+File.separator+"conf";
		} else {
			return resoureDir + File.separator+"site" + siteId + File.separator+"plugin"+File.separator+"onlineSurveymanager"+File.separator+"conf";
		}
	}
	
	/**
	 * 创建目录
	 * @param Dir
	 */
	private static void mkDir(String dir) {
		File file = new File(dir);
		if (!file.mkdirs()) {
			log.error("创建文件失败:" + dir);
		}
	}
	
	/**
	 * 获取网站前台脚本文件存储路径
	 * @param relative true:相对路径，false:物理路径
	 * @return
	 */
	public static String getFrontScriptDir(boolean relative) {
		if (relative) {
			return File.separator+"script"+File.separator+"client";
		} else {
			return GlobalConfig.appRealPath + File.separator+"script"+File.separator+"client";
			
		}
	}
	
	/**
	 * 获取网站前台WEB-INF文件存储路径
	 * @param relative true:相对路径，false:物理路径
	 * @return
	 */
	public static String getClientWebInfDir(boolean relative) {
		if (relative) {
			return File.separator+"WEB-INF";
		} else {
			return GlobalConfig.appRealPath + File.separator+"WEB-INF";
			
		}
	}
	
	
	/**
	 * 获取网站前台需要的浏览器资源脚本(这里是为了给发布后的js使用)
	 * 如："<script type="text/javascript" src="/script/client/latestInfo/latestInfo.js"></script>
	 * @param withAppName 资源路径是否带应用名   
	 * 		  true: src="/cps1.0/script/client/..."
	 * 		  false: src="/script/client/..."
	 * @return
	 */
	public static String getFrontScript(boolean withAppName) {
		// 应用名  如：/cps1.0
		String appName = withAppName ? File.separator+""+GlobalConfig.appName : "";
		return new StringBuffer()
    	    .append("<script type=\"text/javascript\" src=\"").append(appName).append("/script/client/jquery-1.2.6.js\"></script>")
		    .append("<script type=\"text/javascript\" src=\"").append(appName).append("/script/client/jquery.pager.js\"></script>")
    		.append("<script type=\"text/javascript\" src=\"").append(appName).append("/script/client/latestInfo.js\"></script>")
			.append("<script type=\"text/javascript\" src=\"").append(appName).append("/script/client/analyzeArticleText.js\"></script>")
			.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"").append(appName).append("/script/client/Pager.css\"/>")
			.append("<script type=\"text/javascript\" src=\"").append(appName).append("/script/client/titleLinkPage.js\"></script>")
			.append("<script type=\"text/javascript\" src=\"").append(appName).append("/script/client/global.js\"></script>")
			.toString();
	}
	
	/**
	 * 获取网站前台需要的浏览器资源脚本(这里是为了在站内预览等地方使用的js)
	 * 如："<script type="text/javascript" src="/script/preview/latestInfo/latestInfo.js"></script>
	 * @param withAppName 资源路径是否带应用名   
	 * 		  true: src="/cps1.0/script/client/..."
	 * 		  false: src="/script/client/..."
	 * @return
	 */
	public static String getPreviewFrontScript(boolean withAppName){
		// 应用名  如：/cps1.0
		String appName = withAppName ? File.separator+GlobalConfig.appName : "";
		return new StringBuffer()
    	    .append("<script type=\"text/javascript\" src=\"/").append(appName).append("/script/client/jquery-1.2.6.js\"></script>")
		    .append("<script type=\"text/javascript\" src=\"/").append(appName).append("/script/client/jquery.pager.js\"></script>")
    		.append("<script type=\"text/javascript\" src=\"/").append(appName).append("/script/preview/latestInfo.js\"></script>")
			.append("<script type=\"text/javascript\" src=\"/").append(appName).append("/script/preview/analyzeArticleText.js\"></script>")
			.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"/").append(appName).append("/script/client/Pager.css\"/>")
			.append("<script type=\"text/javascript\" src=\"/").append(appName).append("/script/preview/titleLinkPage.js\"></script>")
			.append("<script type=\"text/javascript\" src=\"/").append(appName).append("/script/client/global.js\"></script>")
			.toString();
	}
	
	/**
	 * 获取发布时需要是jS路径
	 * @return
	 */
	public static String getPublisherJsPath(){
		return new StringBuffer()
			.append("<script type=\"text/javascript\" src=\"/script/client/global.js\"></script>")
			.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"/css/index.css\"/>")
			.append("<script type=\"text/javascript\" src=\"/script/client/jquery-1.2.6.js\"></script>")
			.toString();
	}
	/**
	 * 前台分页的JS
	 * @return
	 */
	public static String getPageJsPath(){
		return new StringBuffer()
		.append("<script type=\"text/javascript\" src=\"/script/client/jquery-1.2.6.js\"></script>")
		.append("<script type=\"text/javascript\" src=\"/script/client/jquery.pager.js\"></script>")		
		.append("<script type=\"text/javascript\" src=\"/script/client/analyzeArticleText.js\"></script>")
		.append("<script type=\"text/javascript\" src=\"/script/client/titleLinkPage.js\"></script>")
		.append("<script type=\"text/javascript\" src=\"/script/client/latestInfo.js\"></script>")
		.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"/script/client/Pager.css\"/>")		
		.toString();
	}
	
	/**
	 * 获取标题链接分页的js路径
	 * @param appName
	 * @return
	 */
	public static String getLatestInfoPath(String appName,String siteId){
		String dir = "/"+ appName + "/release"+"/site"+siteId+"/build"+"/static"+"/latestInfo";
		return dir;
	}
	
	/**
	 * 获取模板实例路径	
	 * @return
	 */
	public static String getTemplate_instancePath(String siteId,long currentDate){
		return "/release"+"/site" + siteId + "/template_instance/"+currentDate+"/"; 
	}
	/**
	 * 获取模板实例配置文件路径	
	 * @return
	 */
	public static String getTemplate_instanceConfPath(String siteId,long currentDate){
		return "/release"+"/site" + siteId + "/template_instance/"+currentDate+"/conf/"; 
	}
	
	/**
	 * 获取模板设置页面路径
	 * @return
	 */
	public static String getTemplate_setPath(){
		return GlobalConfig.appRealPath + "/module"+"/template_set"+"/template_set.jsp";
	}
	
	
	
	/**
	 * 获取资源的url
	 * 如：/cps1.0/release/build/static/link.js
	 *     /release/build/static/link.js
	 *     http://www.sina.com 
	 * @param url         
	 * @param withAppName
	 * @return
	 */
	public static String getUrl(String url, boolean withAppName) {
		log.debug("url==========================="+url);
		String appName = withAppName ? "/"+GlobalConfig.appName : "";
		if(url.startsWith("http://")) {
		//	return url;
		} else {
			url = new StringBuffer().append(appName).append(url).toString();
		}
		log.debug("new url====================="+url);
		return url;
	}
	
	public static void main(String[] args) {
//		initSiteDir(1);
//		initSiteDir(2);
//		initSiteDir(3);
//		initSiteDir(4);
		destorySiteDir("1");
		destorySiteDir("8");
	}
	
}
