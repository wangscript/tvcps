/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.unitmanager.label;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.j2ee.cms.common.core.util.StringUtil;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 模板管理</p>
 * <p>版权: Copyright (c) 2009  </p>
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-6-9 上午10:44:56
 * @history（历次修订内容、修订人、修订时间等）
 */
public final class CommonLabel {
	
	/** for正则 */
	public final static String REGEX_FOR = "<!--for-->(.*)<!--/for-->";
	/**  条件判断 */
	public static final String IF  = "<!--if-->(.*)<!--else-->(.*)<!--/if-->";
	
	/**  条件判断开始 */
	public static final String IF_START  = "<!--if-->(.*)<!--else-->";
	
	/**  条件判断结束 */
	public static final String IF_END  = "<!--else-->(.*)<!--/if-->";

	/** 标签正则 */
	public final static String REGEX_LABEL = "<!--[\\w\\d]+-->";

	/** 网站名称 */
	public final static String SITE_NAME = "<!--siteName-->";
	
	/** 网站链接 */
	public final static String SITE_LINK = "<!--siteLink-->";
	
	/** 网站ID */
	public final static String 	SITE_ID = "<!--siteId-->";
	
	/** 应用名 */
	public final static String APP_NAME = "<!--appName-->";
	
	/** 栏目名称 */
	public final static String COLUMN_NAME = "<!--columnName-->";
	
	/** if标签 */
	public final static String IF_LABEL = "<!--if--><!--else--><!--/if-->";
	/** for标签 */
	public final static String FOR_LABEL = "<!--for--><!--/for-->";
	
	/**所有标签 */
	public final static Map allLabels = new HashMap();
	
	static {
		allLabels.put("网站名称", SITE_NAME);
		allLabels.put("网站链接", SITE_LINK);
		allLabels.put("栏目名称", COLUMN_NAME);
		allLabels.put("条件判断", IF_LABEL);
		allLabels.put("循环判断", FOR_LABEL); 
		allLabels.put("网站ID", SITE_ID);
		allLabels.put("应用名", APP_NAME);
	}
	
	private CommonLabel() {
		// null
	}
	
	public static String sub(String src, int row, int col) {
		Map<String,String> data = new HashMap<String,String>();
		data.put("<!--siteId-->", "123");
		data.put("<!--siteName-->", "工商局");
		String forRegex = "<for>(.*)</for>";
		String labelRegex = "<!--[\\w\\d]+-->";
		Pattern forPattern = Pattern.compile(forRegex);
		Pattern labelPatttern = Pattern.compile(labelRegex);
		Matcher forMatcher = forPattern.matcher(src);
		StringBuffer sb = new StringBuffer();
		
		// 1、匹配for, "<table><for><!--siteId--><for><!--siteId--></for><table><!--siteName--></for></table>"
		while (forMatcher.find()) {
			
			StringBuffer sb1 = new StringBuffer();
			Matcher m1 = forPattern.matcher(forMatcher.group());
			Matcher labelMatcher = null;
			
			// 2、匹配for, "<for><!--siteId--><for><!--siteId--></for><table><!--siteName--></for>"
			if (m1.find()) {
				StringBuffer sb2 = new StringBuffer();
				labelMatcher = labelPatttern.matcher(m1.group(1).replaceAll(forRegex, ""));
				
				// 3、匹配for, "<!--siteId--><for><!--siteId--></for><table><!--siteName-->"
				// 从 "<!--siteId--><for><!--siteId--></for><table><!--siteName-->"
				// 到 "<!--siteId--><table><!--siteName-->"
				while (labelMatcher.find()) {
					String replace = "";
					// 网站ID
					if ("<!--siteId-->".equals(labelMatcher.group())) {
						//replace = "" + i;
					} else if ("<!--siteName-->".equals(labelMatcher.group())) {
						//replace = "" + i + data.get("<!--siteName-->");
					}
					
					// 做替换
					labelMatcher.appendReplacement(sb2, replace);
				}
				labelMatcher.appendTail(sb2);
				
				
				
				//sb.append(sb2);
			} else { // 做替换
				labelMatcher = labelPatttern.matcher(m1.group());
				while (labelMatcher.find()) {
					// 做替换
					labelMatcher.appendReplacement(sb1, StringUtil.convert(data.get(labelMatcher.group())));
				}
				labelMatcher.appendTail(sb1);
			}
			//sub(forMatcher.group(), row, col);
		}
		forMatcher.appendTail(sb);
		return sb.toString();
	}
	
	public static void main(String[] args) {
//		String s = " <for><table><tr><td><!--siteId--></td></tr></table></for><!--siteName--><for><table><tr><td></td><!--siteId--></tr></table></for></div><!--%}--></td>";
//		System.out.println(sub(s, 1,2));
		 Pattern p = Pattern.compile("cat");
		 Matcher m = p.matcher("one cat two cats in the yard");
		 StringBuffer sb = new StringBuffer();
		 while (m.find()) {
		     m.appendReplacement(sb, "dog");
		     m.appendReplacement(sb, "444");
		 }
		 m.appendTail(sb);
		 System.out.println(sb.toString());
	}

}
