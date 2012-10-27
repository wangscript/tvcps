/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.templatemanager.analyzer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.j2ee.cms.common.core.util.FileUtil;

/**
 * <p>标题: 静态单元分析器</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 模板管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-5-13 上午09:19:05
 * @history（历次修订内容、修订人、修订时间等）
 */
public class StaticUnitAnalyzer {

	public String getHtml() {
		return null;
	}
	
	public static void main(String[] args) {
		String html = FileUtil.read("D:\\桌面\\测试模板\\测试模板.htm");
		String script = FileUtil.read("D:\\桌面\\测试模板\\script.js");
		String hidden = FileUtil.read("D:\\桌面\\测试模板\\hidden.txt");
		
		StringBuilder buf = new StringBuilder();
		String pre = "<!--{%[";
		String middel = "]--><div height style=\"border:1px dashed red;cursor:hand;height:12px;font:12px italic;color:#FF0000\" onclick=\"openSetWin(0);\">";
		String suffix = "</div><!--%}-->";
		StringBuffer temp = new StringBuffer();
		
		String unitRegex = "<!--\\s*\\{\\[%\\s*-->([^<]*)<!--\\s*%\\]\\}\\s*-->";
		String headRegex = "</head[^>]*>";
		String bodyRegex = "<body[^<]*>";
		html = html.replaceFirst(headRegex, 
				"<script type=\"text/javascript\">\n" + script + "\n</script>\n</head>");
		html = html.replaceFirst(bodyRegex, "<body>\n" + hidden);
		
		Pattern unitPatn = Pattern.compile(unitRegex);
		Matcher m = unitPatn.matcher(html);
		while (m.find()) {
			String unitName = m.group(1).trim();
			m.appendReplacement(temp, 
					buf.append(pre)
					.append(12)
					.append(middel)
					.append("{" + unitName + "}->未设")
					.append(suffix)
					.toString());
			buf.delete(0, buf.length());
		}
		m.appendTail(temp);
		FileUtil.write("D:\\桌面\\测试模板\\build.htm", temp.toString());
		System.out.println(temp);
	}

}
