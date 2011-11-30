/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.articlemanager.tld;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.j2ee.cms.biz.articlemanager.domain.Article;
import com.j2ee.cms.biz.articlemanager.domain.ArticleAttribute;
import com.j2ee.cms.biz.articlemanager.domain.ArticleFormat;
import com.j2ee.cms.biz.articlemanager.service.impl.ArticleAttributeServiceImpl;
import com.j2ee.cms.biz.configmanager.domain.GeneralSystemSet;
import com.j2ee.cms.common.core.util.BeanUtil;
import com.j2ee.cms.common.core.util.DateUtil;
import com.j2ee.cms.common.core.util.StringUtil;
import org.apache.log4j.Logger;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 文章管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-4-7 下午05:28:03
 * @history（历次修订内容、修订人、修订时间等）
 */
public class ArticleForm extends BodyTagSupport {

	private static final long serialVersionUID = -6866522407916559536L;

	private final Logger log = Logger.getLogger(ArticleForm.class);
	
	/** 属性 */
	private List<ArticleAttribute> attributes = new ArrayList<ArticleAttribute>();
	
	/** 文章 */
	private Article article;
	
	/** 文章格式 */
	private ArticleFormat format;
	/** 系统设置对象*/
	private GeneralSystemSet generalSystemSet =new GeneralSystemSet();
	
	private String selectNameStr = "";

	ArticleAttributeServiceImpl  articles=new ArticleAttributeServiceImpl();
	@Override
	public int doEndTag() throws JspException {
		final String appName = (String) pageContext.getSession().getAttribute("appName");
		JspWriter writter = pageContext.getOut();
		StringBuffer sb = new StringBuffer();
 
		try {
			log.debug("attributes's size is " + attributes.size());
			// 默认格式
			if(format.isDefaults()) {
				buildDefaultArticleHtmlTag(sb, appName);
			} else {
				sb.append("<div class=\"form_div\">");
				sb.append("<table width=\"900\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
				
				
				sb.append("<script type=\"text/javascript\">$(\"#articleTitle\").focus();</script>");
				log.debug("=="+sb.toString());
				// 构造格式属性
				for (ArticleAttribute attribute : attributes) {
					sb.append(this.getDefaultFrontAttribute(attribute, appName));
				}
				log.debug("sb=="+sb.toString());
	//			sb.append(this.getDefaultBackAttribute());
				
				sb.append("</table>");
				sb.append("</div>");
			}
			// 添加按钮
			sb.append("<li id=\"btn\">");
			sb.append("	<input type=\"hidden\" id=\"selectNameStr\" name=\"selectNameStr\" value=\"" + selectNameStr + "\" />");
			sb.append("<table width=\"500px;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >");
			sb.append(" <tr>");
			sb.append(" <td class=\"td_right\" width=\"60px;\" >");
			sb.append("	<input type=\"button\" style=\"text-align:center;\" class=\"btn_normal\" value=\"确定\" onclick=\"btn_confirm();\"/>");
			sb.append(" </td>");
			sb.append(" <td  width=\"60px;\" >");
			sb.append("	<input type=\"reset\"  class=\"btn_normal\" value=\"重置\" />");
			sb.append(" </td>");
			sb.append(" <td  id=\"articleAuditRight\" width=\"100px;\" style=\"display:none\">");
			sb.append("	<input type=\"button\" class=\"btn_normal\" value=\"审核并保存\" onclick=\"btn_audit()\"/>");
			sb.append(" </td>");
			sb.append(" <td  width=\"120px;\" >");
			sb.append("	<input type=\"button\" class=\"btn_normal\" value=\"保存并继续添加\" onclick=\"btn_saveAndAdd()\"/>");
			sb.append(" </td>");
			sb.append(" <td  width=\"60px;\" >");
			sb.append(" <input type=\"button\" class=\"btn_normal\" value=\"返回\" onclick=\"returnArticleList()\"/>");
			sb.append(" </td>");
			sb.append("  </tr>");
			sb.append("</table>");
			sb.append("</li>");
		//	log.debug("==="+sb.toString());
	//		System.out.println("articleform tld===="+sb.toString());
			writter.print(sb.toString());
		} catch (IOException e) {
			log.error("articleForm: build error.");
		}
		return EVAL_PAGE;
	}
	
	/**
	 * 根据默认文章属性构造html标签
	 * @param sb
	 * @param appName
	 */
	public void buildDefaultArticleHtmlTag(StringBuffer sb, String appName) {
		String title = "";
		String subtitle = "";
		String leadingTitle = "";
		String url = "";
		String infoSource = "";
		String author = "";
		String keyword = "";
		String brief = "";
		
		String pic = "";
		String attach = "";
		String media = "";
		String textArea = "";
		
		String createTime = "";
		String displayTime = "";
		String publishTime = "";
		String auditTime = "";
		String invalidTime = "";

		
		String isAudited = "";
		String isDeleted = "";
		String publishState = "";
		String toped = "";
		String keyFilter = "";
		if(article != null) {
			if (article != null && article.getId() != null ) {
				title = StringUtil.convert((String) getArticleFieldValue("title"));
				subtitle = StringUtil.convert((String) getArticleFieldValue("subtitle"));
				leadingTitle = StringUtil.convert((String) getArticleFieldValue("leadingTitle"));
				url = StringUtil.convert((String) getArticleFieldValue("url"));
				infoSource = StringUtil.convert((String) getArticleFieldValue("infoSource"));
				author = StringUtil.convert((String) getArticleFieldValue("author"));
				textArea = StringUtil.convert((String) getArticleFieldValue("textArea1"));
				keyword = StringUtil.convert((String) getArticleFieldValue("keyword"));
				brief = StringUtil.convert((String) getArticleFieldValue("brief"));
				isAudited = StringUtil.convert(getArticleFieldValue("audited").toString());
				isDeleted = StringUtil.convert(getArticleFieldValue("deleted").toString());
				publishState = StringUtil.convert(getArticleFieldValue("publishState").toString());
				pic = StringUtil.convert((String) getArticleFieldValue("pic1"));
				attach = StringUtil.convert((String) getArticleFieldValue("attach1"));
				media = StringUtil.convert((String) getArticleFieldValue("media1"));
				displayTime = StringUtil.convert(DateUtil.toString((Date) getArticleFieldValue("displayTime")));
				auditTime = StringUtil.convert(DateUtil.toString((Date) getArticleFieldValue("auditTime")));
				invalidTime = StringUtil.convert(DateUtil.toString((Date) getArticleFieldValue("invalidTime")));
				publishTime = StringUtil.convert(DateUtil.toString((Date) getArticleFieldValue("publishTime")));
				
				createTime = StringUtil.convert(DateUtil.toString((Date) getArticleFieldValue("createTime")));
				toped = StringUtil.convert(getArticleFieldValue("toped").toString());
				keyFilter = StringUtil.convert(getArticleFieldValue("keyFilter").toString());
			}
		}
		sb.append("<input type=\"hidden\" name=\"article.deleted\" id=\"article.deleted\" value=\""+ isDeleted +"\"/>");
		sb.append("<input type=\"hidden\" name=\"article.audited\" id=\"articleaudited\" value=\""+ isAudited +"\"/>");
		sb.append("<input type=\"hidden\" name=\"article.publishState\" id=\"articlepublished\" value=\""+ publishState +"\"/>");
		sb.append("<script type=\"text/javascript\">$(\"#articleTitle\").focus();</script>");
		sb.append("<div class=\"form_div\">");
		sb.append("<table width=\"900\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
		sb.append("  <tr>");
		sb.append("    <td class=\"td_left\" width=\"90px;\"><i>*</i>标题：</td>");
		sb.append("    <td valign=\"middle\">");
		sb.append("		<input name=\"article.title\" type=\"text\" class=\"input_text_normal\" tip=\"标题不能为空\" valid=\"string\" id=\"articleTitle\" style=\"width:747px\" empty=\"false\" value=\""+ title +"\"/>");
		sb.append("	</td></tr>");
		sb.append("  <tr>");
		sb.append("    <td class=\"td_left\">副标题：</td>");
		sb.append("    <td valign=\"middle\">");
		sb.append("		<input name=\"article.subtitle\" type=\"text\"  class=\"input_text_normal\" id=\"subtitle\" style=\"width:747px\" empty=\"true\" valid=\"string\" value=\""+ subtitle +"\"/>");
		sb.append("	</td></tr>");
		sb.append("  <tr>");
		sb.append("    <td class=\"td_left\">引题：</td>");
		sb.append("    <td valign=\"middle\">");
		sb.append("		<input name=\"article.leadingTitle\" type=\"text\"   class=\"input_text_normal\"  id=\"leadingTitle\" style=\"width:747px\" empty=\"true\" valid=\"string\" value=\""+ leadingTitle +"\"/>");
		sb.append("	</td></tr>");
		sb.append("  <tr>");
		sb.append("    <td class=\"td_left\">链接地址：</td>");
		sb.append("    <td valign=\"middle\">");
		sb.append("		<input name=\"article.url\" type=\"text\"  class=\"input_text_normal\"  id=\"url\" style=\"width:600px\" empty=\"true\" valid=\"string\" value=\""+ url +"\"/>");
		sb.append("		&nbsp;&nbsp;&nbsp;<input type=\"button\" value=\"恢复默认\" class=\"btn_small\" onClick=\"setDefault()\"/>");
		sb.append("	</td></tr>");
		sb.append("  <tr>");
		sb.append("    <td class=\"td_left\">信息来源：</td>");
		sb.append("    <td valign=\"middle\">");
		sb.append("		<input name=\"article.infoSource\" type=\"text\"  id=\"infoSource\"  class=\"input_text_normal\"  style=\"width:250px\" empty=\"true\" valid=\"string\" value=\""+ infoSource +"\"/>");
		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		sb.append("<select name=\"generalSystemSetOrgin\" id=\"yy\" style=\"width:155px;\" class=\"input_select_normal\" onchange=\"changeValueOrgin(this)\"></select>");           
		sb.append("	</td></tr>");
		sb.append("  <tr>");
		sb.append("    <td class=\"td_left\">作者：</td>");
		sb.append("<td valign=\"middle\" width=\"600px\">");
		sb.append("<input name=\"article.author\" type=\"text\"id=\"author\"   class=\"input_text_normal\"  style=\"width:250px;\" empty=\"true\" valid=\"string\" value=\""+ author+"\"/>");
		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
	    sb.append("<select name=\"generalSystemSetHtml\" id=\"generalSystemSetHtml\" style=\"width:155px;\" class=\"input_select_normal\" onchange=\"changeValueSelect(this)\"></select>");	 
		sb.append("</td>");
		sb.append("  </tr>");                      
		sb.append("  <tr>");                   
		sb.append("    <td class=\"td_left\">内容：</td>");
		sb.append("    <td>");
		sb.append("      <textarea name=\"article.textArea1\" id=\"textArea1\"  class=\"input_textarea_normal\"  cols=\"98\" rows=\"10\">"+ textArea +"</textarea>");
		sb.append("   </td>");
		sb.append("  </tr>");
		sb.append("  <tr>");
		sb.append("    <td class=\"td_left\">关键词：</td>");
		sb.append("    <td valign=\"middle\">");
		sb.append("      <input name=\"article.keyword\" type=\"text\"   class=\"input_text_normal\"  id=\"keyword\" style=\"width:400px\" empty=\"true\" valid=\"string\" onblur=\"checklenth('keyword', '关键词')\" value=\""+ keyword +"\"/>");
		sb.append("&nbsp;&nbsp;&nbsp;<input type=\"button\" name=\"keywordbutton\" id=\"keywordbutton\" value=\"自动提取\"  class=\"btn_small\"  onclick=\"generate_KeyWords()\" /> ");
		sb.append("&nbsp;&nbsp;&nbsp;<input type=\"button\" name=\"keywordbuttonInfo\" id=\"keywordbuttonInfo\" value=\"详细信息\"  class=\"btn_small\"  onclick=\"showKeyWords()()\" /> ");
		sb.append("   </td>");
		sb.append("  </tr>");
		sb.append("  <tr>");
		sb.append("    <td class=\"td_left\">摘要：</td>");
		sb.append("    <td>");
		//	sb.append("      <textarea name=\"article.brief\" id=\"brief\"  class=\"input_textarea_normal\"  cols=\"50\" rows=\"7\">"+ brief +"</textarea>");
		sb.append("      <input type=\"text\" name=\"article.brief\" cols=\"45\"  class=\"input_text_normal\"  rows=\"5\" id=\"brief\" style=\"width:400px\" empty=\"true\" valid=\"string\" onblur=\"checklenth('brief', '摘要')\" value=\""+ brief +"\"/>");
		sb.append("&nbsp;&nbsp;&nbsp;<input type=\"button\" name=\"briefbutton\" id=\"briefbutton\" value=\"自动提取\"  class=\"btn_small\"   onclick=\"generate_Brief()\" /> ");
		sb.append("	</td>");
		sb.append("  </tr>");
		sb.append("  <tr>");
		sb.append("    <td class=\"td_left\">图片：</td>");
		sb.append("    <td valign=\"middle\">");
		sb.append("	    <input name=\"article.pic1\" type=\"text\"   class=\"input_text_normal\"  id=\"article.pic1\" style=\"width:470px\" readonly value=\""+ pic +"\"/>");
		sb.append("     &nbsp;&nbsp;<input name=\"button\" type=\"button\" value=\"上传\" class=\"btn_samll\" onclick=\"uploadPicture('article.pic1')\"/>");
		sb.append("	    &nbsp;&nbsp;<input name=\"button2\" type=\"button\" value=\"清空\" class=\"btn_samll\"  onclick=\"clearPic('article.pic1')\"/>");
		sb.append("    </td>");
		sb.append("  </tr>");
		sb.append("  <tr>");
		sb.append("    <td class=\"td_left\">附件：</td>");
		sb.append("    <td valign=\"middle\">");
		sb.append("        <input name=\"article.attach1\" type=\"text\"   class=\"input_text_normal\"  id=\"article.attach1\" readonly style=\"width:470px\" value=\""+ attach +"\"/>");
		sb.append("		&nbsp;&nbsp;<input name=\"button\" type=\"button\"  value=\"上传\" class=\"btn_samll\"  onclick=\"uploadAtta('article.attach1')\"/>");
		sb.append("		&nbsp;&nbsp;<input name=\"button2\" type=\"button\"  class=\"btn_samll\"  onclick=\"clearAtta('article.attach1')\" value=\"清空\" />");
		sb.append("    </td>");
		sb.append("  </tr>");
		sb.append("  <tr>");
		sb.append("    <td class=\"td_left\">媒体：</td>");
		sb.append("    <td valign=\"middle\">");
		sb.append("      <input name=\"article.media1\" type=\"text\"  class=\"input_text_normal\"   id=\"article.media1\" readonly style=\"width:470px\" value=\""+ media +"\"/>");
		sb.append("		&nbsp;&nbsp;<input name=\"button\" type=\"button\"  value=\"上传\" class=\"btn_samll\"  onclick=\"uploadFlash('article.media1')\"/>");
		sb.append("		&nbsp;&nbsp;<input name=\"button2\" type=\"button\" onclick=\"clearFlash('article.media1')\" class=\"btn_samll\"  value=\"清空\" />");
		sb.append("    </td>");
		sb.append("  </tr>");
		sb.append("  <tr>");
		sb.append("    <td class=\"td_left\">创建时间：</td>");
		sb.append("    <td valign=\"middle\">");
		sb.append("	     <input type=\"text\" readonly=\"readonly\" empty=\"false\" name=\"article.createTime\" id=\"article.createTime\"  class=\"Wdate\" style=\"width:140px\" value=\""+ createTime +"\"/> ");
		sb.append("      显示时间：");
		sb.append("       <input type=\"text\"  empty=\"true\" name=\"article.displayTime\" id=\"article.displayTime\"  class=\"Wdate\"  style=\"width:140px\" onfocus=\"WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})\" value=\""+ displayTime +"\"/>");
		sb.append("      发布时间：");
		sb.append("      <input type=\"text\" readonly=\"readonly\" empty=\"true\" name=\"article.publishTime\" id=\"article.publishTime\"  class=\"Wdate\"  style=\"width:140px\"  value=\""+ publishTime +"\"/> ");
		sb.append("   </td>");
		sb.append("  </tr>");
		sb.append("  <tr>");
		sb.append("	  <td  class=\"td_left\">审核时间：</td>");
		sb.append("	  <td valign=\"middle\">");
		sb.append("	    <input type=\"text\" readonly=\"readonly\" empty=\"true\" name=\"article.auditTime\" id=\"article.auditTime\" class=\"Wdate\"  style=\"width:140px\"  value=\""+ auditTime +"\"/>");
		sb.append("	           失效时间：");
		sb.append("     <input type=\"text\"  empty=\"true\" name=\"article.invalidTime\" id=\"article.invalidTime\" class=\"Wdate\"  style=\"width:140px\" onfocus=\"WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})\" value=\""+ invalidTime +"\"/>");
		sb.append("   </td>");
		sb.append("  </tr>");
		sb.append("  <tr>");
		sb.append("  <td class=\"td_left\">置顶：</td>");
		sb.append("  <td>");
		String yes = "";
		String no = "";
		if(toped.toString().equals("true")) {
			yes = " checked=\"checked\"";
		} else {
			no = " checked=\"checked\"";
		}
		sb.append("	<input type=\"radio\"  name=\"article.toped\" value=\"true\"");
		sb.append(yes);
		sb.append("/>是");
		sb.append("	<input type=\"radio\" name=\"article.toped\" value=\"false\"");
		sb.append(no);
		sb.append("/>否");
		sb.append("  </td>");
		sb.append("  </tr>");
		sb.append("  <tr>");
		sb.append("  <td class=\"td_left\">过滤关键词：</td>");
		sb.append("  <td>");
		String openKeyFilter = "";
		String closeKeyFilter = "";
		if(keyFilter.toString().equals("true")) {
			openKeyFilter = " checked=\"checked\"";
		} else {
			closeKeyFilter = " checked=\"checked\"";
		}
		sb.append("	<input type=\"radio\"  name=\"article.keyFilter\" value=\"true\"");
		sb.append(openKeyFilter);
		sb.append("/>是");
		sb.append("	<input type=\"radio\" name=\"article.keyFilter\" value=\"false\"");
		sb.append(closeKeyFilter);
		sb.append("/>否");
		sb.append("  </td>");
		sb.append("  </tr>");
		sb.append("</table>");
		sb.append("</div>");
	}
	
	
	/**
	 * 获取文章字段属性对应的值
	 * @param fieldName
	 * @return
	 */
	private Object getArticleFieldValue(String fieldName) {
		return BeanUtil.getFieldValue(article, "com.j2ee.cms.biz.articlemanager.domain.Article", fieldName);
	}
	
	
	private String getDefaultFrontAttribute(ArticleAttribute attribute, String appName) {
		String title = "";
		String subtitle = "";
		String leadingTitle = "";
		String url = "";
		String infoSource = "";
		String author = "";
		String textArea1 = "";
		String keyword = "";
		String brief = "";
		String pic1 = "";
		String media1 = "";
		String attach1 = "";
		
		StringBuffer sb = new StringBuffer();
		String fieldName = "";
		Object fieldValue = null;
		
		// 属性名
		String attributeName = attribute.getAttributeName();
		// 属性类型
		String attributeType = attribute.getAttributeType();
		
			fieldName = attribute.getFieldName();
			if(article != null) {
				fieldValue = getArticleFieldValue(fieldName);
				fieldValue = StringUtil.convert(fieldValue);
				fieldValue = StringUtil.convert(fieldValue);
			}
			// 是否在页面上显示
			boolean isShowed = attribute.isShowed();
			// 是否可以被修改
			boolean isModified = attribute.isModified();
			String readOnly = "";
			if (!isModified && article != null && (article.getId() != null && article.getId() != "")) {
				readOnly = "readonly=\"readonly\"";
			}
			// 是否可以为空
			boolean isEmpty = attribute.isEmpty();		
			// 有效值
			String validValue = attribute.getValidValue();
			String empty = "";
			if(isEmpty == true) {
				empty = " empty=\"true\"";
			}
			String validData = "";
			if (!StringUtil.isEmpty(validValue)) {
				validData = " valid=\""+ validValue +"\"";
			}
			String tip = "";
			if (!StringUtil.isEmpty(attribute.getTip())) {
				tip = " tip=\"" + attribute.getTip() + "\"";
			}
			StringBuffer validateStr = new StringBuffer();
			validateStr.append(empty).append(validData).append(tip).append(readOnly);
			
			if(fieldName.equals("title")) {
				if(isShowed) {
					title = "<tr><td class=\"td_left\" width=\"90px;\"><i>*</i>标题：</td>" +
					"<td valign=\"middle\">	" +
					"<input name=\"article.title\" type=\"text\" class=\"input_text_normal\"  valid=\"string\" id=\"articleTitle\" style=\"width:747px\"  "+empty+"  " + readOnly + "  value=\""+ fieldValue +"\""+validateStr+" />"+
					"</td></tr>";
				} else {
					title = "<input name=\"article.title\" type=\"hidden\" id=\"articleTitle\" style=\"width:747px\"   value=\""+ fieldValue +"\"/>";
				}
				sb.append(title);
				
			} else if(fieldName.equals("subtitle")) {
				if(isShowed) {
					subtitle = "<tr><td class=\"td_left\">副标题：</td>" +
					"<td valign=\"middle\">	" +
					"<input name=\"article.subtitle\" type=\"text\" id=\"subtitle\"  class=\"input_text_normal\" " + readOnly + "  valid=\"string\"  style=\"width:747px\" " + empty + " value=\""+ fieldValue +"\"" + validateStr +"/>"+
					"</td></tr>";
				} else {
					subtitle = "<input name=\"article.subtitle\" type=\"hidden\" id=\"subtitle\"  class=\"input_text_normal\" style=\"width:747px\"   value=\""+ fieldValue +"\""  +"/>";
				}
				sb.append(subtitle);
				
			} else if(fieldName.equals("leadingTitle")) {
				if(isShowed) {
					leadingTitle = "<tr><td class=\"td_left\">引题：</td>" +
					"<td valign=\"middle\">	" +
					"<input name=\"article.leadingTitle\" type=\"text\" id=\"leadingTitle\"  class=\"input_text_normal\"  " + readOnly + "  valid=\"string\"  style=\"width:747px\"  " + empty + "  value=\""+ fieldValue +"\""  + validateStr +"/>"+
					"</td></tr>";
				} else {
					leadingTitle = "<input name=\"article.leadingTitle\" type=\"hidden\" id=\"leadingTitle\"  class=\"input_text_normal\" style=\"width:747px\"  value=\""+ fieldValue +"\"/>";
				}
				sb.append(leadingTitle);
				
			} else if(fieldName.equals("url")) {
				if(isShowed) {
					url = "<tr><td class=\"td_left\">链接地址：</td>" +
					"<td valign=\"middle\">	" +
					"<input name=\"article.url\" type=\"text\" id=\"url\"  class=\"input_text_normal\"  " + readOnly + " valid=\"string\"  style=\"width:600px\"  " + empty + "   value=\""+ fieldValue +"\""  + validateStr +"/>"+
					"&nbsp;&nbsp;&nbsp;<input type=\"button\" value=\"恢复默认\" class=\"btn_small\" onClick=\"setDefault()\"/>" +
					"</td></tr>";
				} else {
					url = "<input name=\"article.url\" type=\"hidden\" id=\"url\" style=\"width:350px\" value=\""+ fieldValue +"\"/>";
				}
				sb.append(url);
				
			} else if(fieldName.equals("infoSource")) {
				if(isShowed) {
					infoSource = "<tr><td class=\"td_left\">信息来源：</td>" +
					"<td valign=\"middle\">	" +
					"<input name=\"article.infoSource\" type=\"text\" id=\"infoSource\"   class=\"input_text_normal\" " + readOnly + "  valid=\"string\"  style=\"width:400px\"  " + empty + "   value=\""+ fieldValue +"\""  + validateStr +"/>"+
					"</td></tr>";
				} else {
					infoSource = "<input name=\"article.infoSource\" type=\"hidden\" id=\"infoSource\" style=\"width:747px\" value=\""+ fieldValue +"\"/>";
				}
				sb.append(infoSource);
				
			} else if(fieldName.equals("author")) {
				if(isShowed) {
					author = "<tr><td class=\"td_left\">作者：</td>" +
					"<td valign=\"middle\">	" +
					"<input name=\"article.author\" type=\"text\"  class=\"input_text_normal\" id=\"author\"  " + readOnly + "  valid=\"string\"  style=\"width:747px\"  " + empty + "  value=\""+ fieldValue +"\""  + validateStr +"/>"+
					"</td></tr>";
				} else {
					author = "<input name=\"article.author\" type=\"hidden\"  id=\"author\" style=\"width:747px\" value=\""+ fieldValue +"\"/>";
				}
				sb.append(author);
				
			} else if(fieldName.equals("textArea1")) {
				if(isShowed) {
					textArea1 = "<tr><td class=\"td_left\">内容：</td>" +
					"<td valign=\"middle\">	" +
					"<textarea name=\"article.textArea1\" id=\"textArea1\" class=\"input_textarea_normal\" cols=\"98\" rows=\"10\">"+ fieldValue +"</textarea>"+
					"</td></tr>";
				} else {
					textArea1 = "<input name=\"article.textArea1\" type=\"hidden\" id=\"textArea1\" style=\"width:747px\" value=\""+ fieldValue +"\"/>";
				}	
				sb.append(textArea1);
				
			} else if(fieldName.equals("keyword")) {
				if(isShowed) {
					keyword = "<tr><td class=\"td_left\">关键词：</td>" +
					"<td valign=\"middle\">	" +
					"<input name=\"article.keyword\" type=\"text\"  class=\"input_text_normal\" id=\"keyword\"  " + readOnly + "  valid=\"string\"  style=\"width:747px\"  " + empty + "  value=\""+ fieldValue +"\""  + validateStr +"/>"+
					"&nbsp;&nbsp;&nbsp;<input type=\"button\" name=\"keywordbutton\" id=\"keywordbutton\" value=\"自动提取\"  class=\"btn_small\" /> "+
					"&nbsp;&nbsp;&nbsp;<input type=\"button\" name=\"keywordbuttonInfo\" id=\"keywordbuttonInfo\" value=\"详细信息\"  class=\"btn_small\"  onclick=\"showKeyWords()()\" /> "+
					"</td></tr>";
				} else {
					keyword = "<input name=\"article.keyword\" type=\"hidden\" id=\"keyword\" style=\"width:747px\" value=\""+ fieldValue +"\"/><input type=\"button\" name=\"briefbutton\" id=\"briefbutton\" value=\"自动提取\" onclick=\"generate_KeyWords()\"  class=\"btn_small\"  />";
				}
				sb.append(keyword);
				
			} else if(fieldName.equals("brief")) {
				if(true) {
					brief = "<tr><td class=\"td_left\">摘要：</td>" +
					"<td valign=\"middle\">	" +
					//		"      <textarea name=\"article.brief\" id=\"brief\"  class=\"input_textarea_normal\"  cols=\"50\" rows=\"7\">"+ brief +"</textarea>"+
					"<input name=\"article.brief\" type=\"text\" id=\"brief\"  class=\"input_text_normal\"  " + readOnly + "  valid=\"string\"  style=\"width:747px\"  " + empty + "  value=\""+ fieldValue +"\""  + validateStr +"/>"+
					"&nbsp;&nbsp;&nbsp;<input type=\"button\" name=\"briefbutton\" id=\"briefbutton\" value=\"自动提取\"  class=\"btn_small\" /> "+
					"</td></tr>";
				} else {
					brief = "<input name=\"article.brief\" type=\"hidden\" id=\"brief\" style=\"width:747px\" value=\""+ fieldValue +"\"/><input type=\"button\" name=\"briefbutton\" id=\"briefbutton\" value=\"自动提取\" onclick=\"generate_Brief()\"  class=\"btn_small\" />";
					 
				}
				sb.append(brief);
				
			} else if(fieldName.equals("pic1")) {
				if(isShowed) {
					pic1 = "<tr><td class=\"td_left\">图片：</td>" +
					"<td valign=\"middle\">	" +
					"<input name=\"article.pic1\" type=\"text\" id=\"pic1\" class=\"input_text_normal\" style=\"width:470px\"  readOnly  value=\""+ fieldValue +"\"/>"+
					"&nbsp;&nbsp;<input name=\"button\" type=\"button\" value=\"上传\" class=\"btn_small\" onclick=\"uploadPicture('article.pic1')\"/>"+
					"&nbsp;&nbsp;<input name=\"button2\" type=\"button\" value=\"清空\" class=\"btn_small\"  onclick=\"clearPic('article.pic1')\"/>"+
					"</td></tr>";
				} else {
					pic1 = "<input name=\"article.pic1\" type=\"hidden\" id=\"article.pic1\" style=\"width:747px\" value=\""+ fieldValue +"\"/>";
				}
				sb.append(pic1);
				
			} else if(fieldName.equals("media1")) {
				if(isShowed) {
					media1 = "<tr><td class=\"td_left\">媒体：</td>" +
					"<td valign=\"middle\">	" +
					"<input name=\"article.media1\" type=\"text\" class=\"input_text_normal\" id=\"media1\" style=\"width:470px\" readOnly value=\""+ fieldValue +"\"/>"+
					"&nbsp;&nbsp;<input name=\"button\" type=\"button\" value=\"上传\" class=\"btn_small\"  onclick=\"uploadFlash('article.media1')\"/>"+
					"&nbsp;&nbsp;<input name=\"button2\" type=\"button\" value=\"清空\" class=\"btn_small\" onclick=\"clearFlash('article.media1')\"/>"+
					"</td></tr>";
				} else {
					media1 = "<input name=\"article.media1\" type=\"hidden\" id=\"article.media1\" style=\"width:747px\" value=\""+ fieldValue +"\"/>";
				}
				sb.append(media1);
				
			} else if(fieldName.equals("attach1")) {
				if(isShowed) {
					attach1 = "<tr><td class=\"td_left\">附件：</td>" +
					"<td valign=\"middle\">	" +
					"<input name=\"article.attach1\" type=\"text\"  class=\"input_text_normal\" id=\"attach1\" style=\"width:470px\" readOnly value=\""+ fieldValue +"\"/>"+
					"&nbsp;&nbsp;<input name=\"button\" type=\"button\" value=\"上传\" class=\"btn_small\"  onclick=\"uploadAtta('article.attach1')\"/>"+
					"&nbsp;&nbsp;<input name=\"button2\" type=\"button\" value=\"清空\" class=\"btn_small\"  onclick=\"clearAtta('article.attach1')\"/>"+
					"</td></tr>";
				} else {
					attach1 = "<input name=\"article.attach1\" type=\"hidden\" id=\"attach1\" style=\"width:747px\" value=\""+ fieldValue +"\"/>";
				}
				sb.append(attach1);
			}

			if(!attribute.getFieldName().equals("title") 
					&& !attribute.getFieldName().equals("subtitle")
					&& !attribute.getFieldName().equals("leadingTitle")
					&& !attribute.getFieldName().equals("url")
					&& !attribute.getFieldName().equals("infoSource")
					&& !attribute.getFieldName().equals("author")
					&& !attribute.getFieldName().equals("textArea1")
					&& !attribute.getFieldName().equals("keyword")
					&& !attribute.getFieldName().equals("brief")
					&& !attribute.getFieldName().equals("pic1")
					&& !attribute.getFieldName().equals("media1")
					&& !attribute.getFieldName().equals("attach1")
					&& !attribute.getFieldName().equals("createTime")
					&& !attribute.getFieldName().equals("displayTime")
					&& !attribute.getFieldName().equals("publishTime")
					&& !attribute.getFieldName().equals("auditTime")
					&& !attribute.getFieldName().equals("invalidTime")
					&& !attribute.getFieldName().equals("toped") 
					&& !attribute.getFieldName().equals("keyFilter")){
				
				if (article != null && (article.getId() != null && article.getId() != "")) {
					fieldValue = BeanUtil.getFieldValue(article, "com.j2ee.cms.biz.articlemanager.domain.Article", fieldName);
				}
				
				fieldName = "article." + fieldName;
				String readyOnly = "";
				if (!isModified && (article.getId() != null && article.getId() != "")) {
					readyOnly = "readonly=\"readonly\"";
				} else {
					readyOnly = " ";
				}
				if (fieldValue instanceof Date) {
					fieldValue = DateUtil.toString((Date)fieldValue, "yyyy-MM-dd HH:mm:ss");
				}
				
				fieldValue = StringUtil.convert(fieldValue);
				
				
				String tagType = ""; 
				// 字符
				if (ArticleAttribute.ATTR_TYPE_TEXT.equals(attributeType)) {
					String stringStr = "";
					if (isShowed) {
						stringStr = "<tr>" + 
									"<td class=\"td_left\">" + attributeName + "：" + "</td>" +
									"<td valign=\"middle\"><input type=\"text\"  class=\"input_text_normal\" " + " style=\"width:747px\"" + readyOnly + " name=\"" + fieldName + "\" id=\"" + fieldName + "\" value=\"" + fieldValue + "\"" + validateStr + "/>" + 
									"</td></tr>";
					} else {
						stringStr = "<input type=\"hidden\" name=\" " + fieldName + "\" id=\"" + fieldName + "\" value=\"" + fieldValue + "\"/>";
					}
					sb.append(stringStr);
				// 文本
				} else if (ArticleAttribute.ATTR_TYPE_TEXTAREA.equals(attributeType)) {
					String textStr = "";
					if (isShowed) {
						textStr = "<tr>" +
								  "	<td class=\"td_left\">" + attributeName + "：" + "</td>" + 
								  " <td valign=\"middle\"><textarea rows=\"4\"   class=\"input_textarea_normal\"  cols=\"60\" " + readyOnly + "  " + empty + "  " + validData + "  name=\"" + fieldName + "\" style=\"display:none\">" + fieldValue + "</textarea>" +      
								  "</td></tr>";
					} else {
						textStr = "	<input type=\"hidden\" name=\"" + fieldName + "\" id=\"" + fieldName + "\" value=\"" + fieldValue + "\"/>";
					}
					sb.append(textStr);
				// 日期
				} else if (ArticleAttribute.ATTR_TYPE_DATE.equals(attributeType)) {
					String dateStr = "";
					// 日历控件
					String calendarControler = "onfocus=\"WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})\"";
					
					
					if (isShowed) {
						dateStr = "<tr>" + 
								  "	<td class=\"td_left\">" + attributeName + "：" + "</td>" + 
								  "	<td valign=\"middle\"><input type=\"text\"  style=\"width:140px;\" " + readyOnly + "  " + empty + "  " + validData + " name=\"" + fieldName + "\" id=\"" + fieldName + "\"  class=\"Wdate\"  value=\""+ fieldValue +"\" "  + calendarControler +"/>"+
								  "</td></tr>";
					} else {
						dateStr = "	<input type=\"hidden\" name=\"" + fieldName + "\" id=\"" + fieldName + "\" value=\"" + fieldValue + "\"/>";
					}
					sb.append(dateStr);
				// 整数
				} else if (ArticleAttribute.ATTR_TYPE_INTEGER.equals(attributeType)) {
					String intStr = "";
					if (isShowed) {
						intStr = "<tr>" + 
								 "	<td class=\"td_left\">" + attributeName + "：" + "</td>" + 
								 "	<td valign=\"middle\"><input type=\"text\"  class=\"input_text_normal\"  style=\"width:140px;\"  " + readyOnly + "  name=\"" + fieldName + "\" id=\"" + fieldName + "\" value=\"" + fieldValue + "\" " + validateStr + " />" + 
								 "</td></tr>";
					} else {
						intStr = "	<input type=\"hidden\" name=\"" + fieldName + "\" id=\"" + fieldName + "\" value=\"" + fieldValue + "\"/>";
					}
					sb.append(intStr);
				// 小数
				} else if (ArticleAttribute.ATTR_TYPE_FLOAT.equals(attributeType)) {
					String floatStr = "";
					if (isShowed) {
						floatStr = "<tr>" + 
								   "	<td class=\"td_left\">" + attributeName + "：</td>" + 
								   "	<td valign=\"middle\"><input type=\"text\"  class=\"input_text_normal\"  style=\"width:140px;\"  " + readyOnly + " name=\"" + fieldName + "\" id=\"" + fieldName + "\" value=\"" + fieldValue + "\"" + validateStr + "/>" +
								   "</td></tr>";
					} else {
						floatStr = "	<input type=\"hidden\" name=\"" + fieldName + "\" id=\"" + fieldName + "\"  value=\"" + fieldValue + "\"/>";
					}
					sb.append(floatStr);
				// 布尔
				} else if (ArticleAttribute.ATTR_TYPE_BOOL.equals(attributeType)) {
					String booStr = "";
					if (isShowed) {
						String yes = "";
						String no = "";
						if(fieldValue.toString().equals("true")) {
							yes = " checked=\"checked\"";
						} else {
							no = " checked=\"checked\"";
						}
						booStr = "<tr>" + 
								 "	<td class=\"td_left\">" + attributeName + "：</td>" + 
								 "	<td valign=\"middle\"><input type=\"radio\" " + readyOnly + " name=\"" + fieldName + "\" id=\"" + fieldName+"1" + "\" value=\"true\" " + yes + "/>是" + 
								 "<input type=\"radio\" " + readyOnly + " name=\"" + fieldName + "\" id=\"" + fieldName+"2" + "\" value=\"false\" " + no + "/>否" + 
								 "</td></tr>";
					} else {
						booStr = "	<input type=\"hidden\" name=\"" + fieldName + "\" id=\"" + fieldName + "\" value=\"" + fieldValue + "\"/>";
					}
					sb.append(booStr);
				// 图片
				} else if (ArticleAttribute.ATTR_TYPE_PIC.equals(attributeType)) {
					String picStr = "";
					if (isShowed) {
						picStr = "<tr>" + 
						         "	<td class=\"td_left\">" + attributeName + "：" + "</td>" + 
						         "	<td valign=\"middle\"><input type=\"text\"  class=\"input_text_normal\"  style=\"width:470px\"  readonly=\"readonly\" " + empty + "  " + validData + " name=\"" + fieldName + "\" id=\"" + fieldName + "\" value=\"" + fieldValue + "\"/>" + 
						         " &nbsp;&nbsp;<input type=\"button\" class=\"btn_small\"  value=\"上传\" onclick=\"uploadPicture('"+ fieldName +"')\">" + 
						         " &nbsp;&nbsp;<input type=\"button\" class=\"btn_small\"  value=\"清空\" onclick=\"clearPic('"+ fieldName +"')\"/>" + 
						         "</td></tr>";
					} else {
						picStr = "	<input type=\"hidden\" name=\"" + fieldName + "\" id=\"" + fieldName + "\" value=\"" + fieldValue + "\"/>";
					}
					sb.append(picStr);
				// 附件
				} else if (ArticleAttribute.ATTR_TYPE_ATTACH.equals(attributeType)) {
					String attStr = "";
					if (isShowed) {
						attStr = "<tr>" + 
								 "	<td class=\"td_left\">" + attributeName + "：</td>" + 
								 "	<td valign=\"middle\"><input type=\"text\" class=\"input_text_normal\"  style=\"width:470px\"  readonly=\"readonly\" " + empty + "  " + validData + " name=\"" + fieldName + "\" id=\"" + fieldName + "\" value=\"" + fieldValue + "\"/>" + 
								 " &nbsp;&nbsp;<input type=\"button\" value=\"上传\" class=\"btn_small\"   onclick=\"uploadAtta('"+ fieldName +"')\"/>" + 
								 " &nbsp;&nbsp;<input type=\"button\" value=\"清空\" class=\"btn_small\"   onclick=\"clearAtta('"+ fieldName +"')\"/>" + 
								 "</td></tr>";
					} else {
						attStr = "	<input type=\"hidden\" name=\"" + fieldName + "\" id=\"" + fieldName + "\" value=\"" + fieldValue + "\"/>";
					}
					sb.append(attStr);
				// 媒体
				} else if (ArticleAttribute.ATTR_TYPE_MEDIA.equals(attributeType)) {
					String midStr = "";
					if (isShowed) {
						midStr = "<tr>" + 
								 "	<td class=\"td_left\">" + attributeName + "：</td>" + 
								 "	<td valign=\"middle\"><input type=\"text\"  class=\"input_text_normal\"  style=\"width:470px\" readonly=\"readonly\" " + empty + "  " + validData + " name=\"" + fieldName + "\" id=\"" + fieldName + "\" value=\"" + fieldValue + "\"/>" +     
								 " &nbsp;&nbsp;<input type=\"button\" value=\"上传\" class=\"btn_small\"   onclick=\"uploadFlash('"+ fieldName +"')\"/>" + 
								 " &nbsp;&nbsp;<input type=\"button\" value=\"清空\" class=\"btn_small\"   onclick=\"clearFlash('"+ fieldName +"')\"/>" +
							 	 "</td></tr>";
					} else {
						midStr = "	<input type=\"hidden\" name=\"" + fieldName + "\" id=\"" + fieldName + "\" value=\"" + fieldValue + "\"/>";
					}
					sb.append(midStr);
					// 枚举
				} else if (ArticleAttribute.ATTR_TYPE_ENUMERATION.equals(attributeType)) {
					String enumStr = "";
					String arr = fieldName.substring(8);
					selectNameStr += arr + ","; 
					
					if (isShowed) {
						enumStr = "<tr>" + 
								  "	<td class=\"td_left\">" + attributeName + "：</td>" + 
								  "	<td valign=\"middle\"><select name=\""+arr+"\" id=\""+arr+"\" style=\"width:155px;\" class=\"input_select_normal\" onchange=\"changeValue('"+arr+"')\"></select>" + 
								  "	<input type=\"hidden\" name=\"" + fieldName + "\" id=\""+arr+"0\" value=\"" + fieldValue + "\" />" +
								  "</td></tr>";
					} else {
						enumStr = "	<select name=\""+arr+"\" id=\""+arr+"\" style=\"width:155px;\" class=\"input_select_normal\" onchange=\"changeValue('"+arr+"')\"></select>" + 
								  "	<input type=\"hidden\" name=\"" + fieldName + "\" id=\""+arr+"0\" value=\"" + fieldValue + "\" />";
					}
					sb.append(enumStr);
				} 
			}
			
			String createTime = "";
			String displayTime = "";
			String publishTime = "";
			String auditTime = "";
			String invalidTime = "";
			
			String toped = "";
			String keyFilter = "";
			
			fieldName = attribute.getFieldName();
			if(article != null) {
				fieldValue = getArticleFieldValue(fieldName);
				if (fieldValue instanceof Date) {
					fieldValue = DateUtil.toString((Date)fieldValue, "yyyy-MM-dd HH:mm:ss");
				}
				fieldValue = StringUtil.convert(fieldValue);
			}

			// 日历控件
			String calendarControler = "onfocus=\"WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})\"";
			if (!StringUtil.isEmpty(readOnly)) {
				calendarControler = "";
			}
			if(fieldName.equals("createTime")) {
				if(isShowed) {
					createTime = "<tr><td class=\"td_left\">创建时间：</td>" +
					"<td valign=\"middle\">	" +
					"<input name=\"article.createTime\"  id=\"article.createTime\" type=\"text\"  class=\"Wdate\" readonly=\"readonly\" style=\"width:140px\"  value=\""+ fieldValue +"\" />"+
					"</td></tr>";
				} else {
					createTime = "<input name=\"article.createTime\" id=\"article.createTime\" type=\"hidden\"  class=\"Wdate\"  style=\"width:140px\"  value=\""+ fieldValue +"\" />";
				}
				sb.append(createTime);
				
			} else if(fieldName.equals("displayTime")) {
				if(isShowed) {
					displayTime = "<tr><td class=\"td_left\">显示时间：</td>" +
					"<td valign=\"middle\">	" +
					"<input name=\"article.displayTime\" id=\"article.displayTime\" type=\"text\"  class=\"Wdate\" style=\"width:140px\"  value=\""+ fieldValue +"\""  + calendarControler +"/>"+
					"</td></tr>";
				} else {
					displayTime = "<input name=\"article.displayTime\" id=\"article.displayTime\" type=\"hidden\"  class=\"Wdate\" style=\"width:140px\"  value=\""+ fieldValue +"\""  + calendarControler +"/>";
				}
				sb.append(displayTime);
				
			} else if(fieldName.equals("publishTime")) {
				if(isShowed) {
					publishTime = "<tr><td class=\"td_left\">发布时间：</td>" +
					"<td valign=\"middle\">	" +
					"<input name=\"article.publishTime\" id=\"article.publishTime\" type=\"text\" readonly=\"readonly\"  class=\"Wdate\" style=\"width:140px\"  value=\" " + fieldValue + "\" />"+
					"</td></tr>";
				} else {
					publishTime = "<input name=\"article.publishTime\" id=\"article.publishTime\" type=\"hidden\"  class=\"Wdate\" style=\"width:140px\"  value=\""+ fieldValue +"\" />";
				}
				sb.append(publishTime);
				
			} else if(fieldName.equals("auditTime")) {
				if(isShowed) {
					auditTime = "<tr><td class=\"td_left\">审核时间：</td>" +
					"<td valign=\"middle\">	" +
					"<input name=\"article.auditTime\" readonly=\"readonly\" id=\"article.auditTime\" type=\"text\"  class=\"Wdate\" style=\"width:140px\"  value=\""+ fieldValue +"\" />"+
					"</td></tr>";
				} else {
					auditTime = "<input name=\"article.auditTime\" id=\"article.auditTime\" type=\"hidden\"  class=\"Wdate\" style=\"width:140px\"  value=\""+ fieldValue +"\" />";
				}
				sb.append(auditTime);
				
			} else if(fieldName.equals("invalidTime")) {
				if(isShowed) {
					invalidTime = "<tr><td class=\"td_left\">失效时间：</td>" +
					"<td valign=\"middle\">	" +
					"<input name=\"article.invalidTime\" id=\"article.invalidTime\" type=\"text\"  class=\"Wdate\" style=\"width:140px\"  value=\""+ fieldValue +"\""  + calendarControler +"/>"+
					"</td></tr>";
				} else {
					invalidTime = "<input name=\"article.invalidTime\" id=\"article.invalidTime\" type=\"hidden\"  class=\"Wdate\" style=\"width:140px\"  value=\""+ fieldValue +"\""  + calendarControler +"/>";
				}
				sb.append(invalidTime);
				
			} else if(fieldName.equals("toped")) {
				String yes = "";
				String no = "";
				if(fieldValue.toString().equals("true")) {
					yes = " checked=\"checked\"";
				} else {
					no = " checked=\"checked\"";
				}
				if(isShowed) {
					toped = "<tr><td class=\"td_left\">置顶：</td>" +
					"<td valign=\"middle\">	" +
					"<input name=\"article.toped\" id=\"article.toped1\" type=\"radio\"  value=\"true\" "+ yes +"/>是"+
					"<input name=\"article.toped\" id=\"article.toped2\" type=\"radio\"  value=\"false\""+ no +"/>否"+
					"</td></tr>";
				} else {
					toped = "<input name=\"article.toped\" id=\"article.toped2\" type=\"hidden\"  value=\""+ fieldValue + "\"/>";
				}
				sb.append(toped);
				
			} else if(fieldName.equals("keyFilter")) {
				String yes = "";
				String no = "";
				if(fieldValue.toString().equals("true")) {
					yes = " checked=\"checked\"";
				} else {
					no = " checked=\"checked\"";
				}
				if(isShowed) {
					keyFilter = "<tr><td class=\"td_left\">过滤关键词：</td>" +
					"<td valign=\"middle\">	" +
					"<input name=\"article.keyFilter\" id=\"article.keyFilter1\" type=\"radio\"  value=\"true\" "+ yes +"/>是"+
					"<input name=\"article.keyFilter\" id=\"article.keyFilter2\" type=\"radio\"  value=\"false\""+ no +"/>否"+
					"</td></tr>";
				} else {
					keyFilter = "<input name=\"article.keyFilter\" id=\"article.keyFilter2\" type=\"hidden\"  value=\""+ fieldValue + "\"/>";
				}
				sb.append(keyFilter);
			} 
			
			
		return sb.toString();
	}
		
	
	/**
	 * @return the attributes
	 */
	public List getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes the attributes to set
	 */
	public void setAttributes(List attributes) {
		this.attributes = attributes;
	}

	/**
	 * @return the article
	 */
	public Article getArticle() {
		return article;
	}

	/**
	 * @param article the article to set
	 */
	public void setArticle(Article article) {
		this.article = article;
	}

	/**
	 * @return the format
	 */
	public ArticleFormat getFormat() {
		return format;
	}

	/**
	 * @param format the format to set
	 */
	public void setFormat(ArticleFormat format) {
		this.format = format;
	}
	
	public void setGeneralSystemSet(GeneralSystemSet generalSystemSet) {
		this.generalSystemSet = generalSystemSet;
	}
	

    
	public GeneralSystemSet getGeneralSystemSet() {
		return generalSystemSet;
	}
	
}
