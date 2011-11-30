/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.unitmanager.analyzer;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.j2ee.cms.biz.templatemanager.dao.TemplateUnitDao;
import com.j2ee.cms.biz.templatemanager.domain.TemplateUnit;
import com.j2ee.cms.biz.unitmanager.label.ArticleTextLabel;
import com.j2ee.cms.biz.unitmanager.label.CommonLabel;
import com.j2ee.cms.sys.GlobalConfig;
import com.j2ee.cms.common.core.util.StringUtil;

/**
 * <p>
 * 标题: 静态单元分析器
 * </p>
 * <p>
 * 描述: —— 简要描述类的职责、实现方式、使用注意事项等
 * </p>
 * <p>
 * 模块: 模板管理
 * </p>
 * <p>
 * 版权: Copyright (c) 2009  
 * 
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-5-13 上午09:19:05
 * @history（历次修订内容、修订人、修订时间等）
 */
 
public class StaticUnitAnalyzer implements TemplateUnitAnalyzer {

	private static final Logger log = Logger.getLogger(StaticUnitAnalyzer.class);
	
	/** 注入模板单元dao */
	private TemplateUnitDao templateUnitDao;

	public String getHtml(String unitId, String articleId, String columnId,
			String siteId, Map<String, String> commonLabel) {
		String html = "";
		TemplateUnit templateUnit = templateUnitDao.getAndClear(unitId);
		if (templateUnit != null) {
			html = templateUnit.getHtml();
		}
		html = StringUtil.trimEnter(html);
		/**下面这段代码用于获取静态单元中获取<!--siteId-->*/
		Pattern labelPatttern = Pattern.compile(CommonLabel.REGEX_LABEL);
		Matcher labelMatcher = labelPatttern.matcher(html);
		StringBuffer sb = new StringBuffer();
		String label = "";
		while (labelMatcher.find()) {
			label = labelMatcher.group();
			// 网站ID
			if (label.equals(CommonLabel.SITE_ID)) {
				labelMatcher.appendReplacement(sb, siteId);
				
			// 应用名	
			}else if(label.equals(CommonLabel.APP_NAME)){
				labelMatcher.appendReplacement(sb, GlobalConfig.appName);
			}
		}
		labelMatcher.appendTail(sb);
		log.debug("静态单元==="+sb.toString());
		return sb.toString();
	}

	/**
	 * @param templateUnitDao
	 *            the templateUnitDao to set
	 */
	public void setTemplateUnitDao(TemplateUnitDao templateUnitDao) {
		this.templateUnitDao = templateUnitDao;
	}

}
