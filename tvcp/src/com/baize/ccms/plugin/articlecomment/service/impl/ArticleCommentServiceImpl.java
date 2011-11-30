/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.plugin.articlecomment.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
import org.springframework.jdbc.core.JdbcTemplate;

import com.j2ee.cms.biz.articlemanager.dao.ArticleDao;
import com.j2ee.cms.biz.articlemanager.domain.Article;
import com.j2ee.cms.biz.configmanager.domain.InformationFilter;
import com.j2ee.cms.biz.sitemanager.dao.SiteDao;
import com.j2ee.cms.biz.sitemanager.domain.Site;
import com.j2ee.cms.biz.usermanager.domain.User;
import com.j2ee.cms.plugin.articlecomment.dao.ArticelCommentReplaceDao;
import com.j2ee.cms.plugin.articlecomment.dao.ArticleCommentDao;
import com.j2ee.cms.plugin.articlecomment.domain.ArticleComment;
import com.j2ee.cms.plugin.articlecomment.domain.ArticleCommentsReplace;
import com.j2ee.cms.plugin.articlecomment.service.ArticleCommentService;
import com.j2ee.cms.plugin.articlecomment.web.form.ArticleCommentForm;
import com.j2ee.cms.sys.GlobalConfig;
import com.j2ee.cms.sys.SiteResource;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.pager.PageQuery;
import com.j2ee.cms.common.core.util.CollectionUtil;
import com.j2ee.cms.common.core.util.DateUtil;
import com.j2ee.cms.common.core.util.FileUtil;
import com.j2ee.cms.common.core.util.SqlUtil;

/**
 * <p>
 * 标题: —— 该类用于操作文章评论和文章评论的设置，文章评论的样式设置,具体设置保存在XML文件中
 * </p>
 * <p>
 * 描述: —— 分前台和后台，前台用于发表评论，后台用于设置评论样式和设置评论的显示过滤hibernater，前台发表评论用JDBC
 * 前台不能直接获取到用户的ID和网站ID要通过手动查找数据库才能找到，修改时请慎重
 * </p>
 * <p>
 * 模块: CPS 文章评论
 * </p>
 * <p>
 * 版权: Copyright (c) 2009  
 * </p>
 * <p>
 * 网址：http://www.j2ee.cmsweb.com
 * 
 * @author 曹名科
 * @version 1.0
 * @since 2009-10-20 下午03:30:06
 * @history（历次修订内容、修订人、修订时间等） 
 */
 

public class ArticleCommentServiceImpl implements ArticleCommentService {
	private final Logger log = Logger.getLogger(ArticleCommentServiceImpl.class);
	/** 注入前台JdbcTemplate. **/
	private JdbcTemplate jdbcTemplate;

	/** 注入文章dao. */
	private ArticleDao articleDao;
	
	/** 注入网站dao */
	private SiteDao siteDao;

	/** 注入文章评论dao. */
	private ArticleCommentDao articleCommentDao;
	/** 注入文章替换设置dao. */
	private ArticelCommentReplaceDao articleCommentReplaceDao;
	/** 显示IP */
	private final static String IP = "<!--ip-->";
	/** 文章评论ID */
	private final static String COMMENTID = "<!--commentId-->";
	/** 发表人 */
	private final static String AUTHOR = "<!--author-->";
	/** 发表内容 */
	private final static String CONTENT = "<!--content-->";
	/** 支持 */
	private final static String BALLOT = "<!--ballot-->";
	/** 发表时间 */
	private final static String DATE = "<!--date-->";
	/** 反对 */
	private final static String UNSUPORT = "<!--unsupport-->";

	public ArticleComment getArticleCommentById(String id) {
		return articleCommentDao.getAndClear(id);
	}

	public Pagination getCommentPagination(Pagination pagination) {
		pagination.setQueryString("select ip,authorName,content,supporter,"
				+ "createTime,ironfoe,id from "
				+ "article_comment where audit=1 and deleted=0 "
				+ " GROUP BY createTime,essence ORDER BY createTime DESC,essence DESC");
		return PageQuery.getPaginationByQueryString(pagination, jdbcTemplate);
	}

	public String getTime(String articleId) {
		String time = (String) jdbcTemplate.queryForObject(
				"select createTime from articles where id=?",
				new Object[] { articleId }, java.lang.String.class);
		return time;
	}

	@SuppressWarnings("unchecked")
	public String getAnalyStyle(Pagination pagination, ArticleCommentForm form,	String articleId) {
		String siteId = getSiteId(articleId);
		StringBuffer buffer = new StringBuffer();
		List list = getCommentPagination(pagination).getData();

		Map<String, String> map = getCommentSet(siteId);// 丛文件中获取配置信息
		String creamCount = map.get("openType");

		buffer.append("<script>");
		// 判断设置的是开发类型
		if (creamCount == null || creamCount.equals("0")) {// 关闭
			buffer.append("window.onload=function(){document.getElementById(\"commentDivId\").style.display='none';" + "\n");
			buffer.append("var m=document.getElementById(\"infoMessageId\").value;if(m!=\"\"){alert(m);}}" + "\n");
		} else {// 全部开放
			buffer.append("window.onload=function(){document.getElementById(\"commentDivId\").style.display='block';" + "\n");
			buffer.append("var m=document.getElementById(\"infoMessageId\").value;if(m!=\"\"){alert(m);}}" + "\n");
		}
		buffer.append("function checkComment(){");
		buffer.append("var author = $(\"#author\").val();");
		buffer.append("var content = $('#contentID').val();");
		buffer.append("var issub = '0';");

		int count = getCommentCount();// 丛数据库中获取文章的评论数目
		String maxComment = map.get("articleCommentCount");// 文章最大评论数
		String timeOut = map.get("timeOutSet");// 设置过期的天数

		if (timeOut != null && !timeOut.equals("0")) {// 判断是否过期,0无效,其他为设置过期时间
			SimpleDateFormat smdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			String time = getTime(articleId);
			c.setTime(DateUtil.toDate(time));// 设置日期
			c.add(Calendar.DAY_OF_YEAR, Integer.parseInt(timeOut));// 在当前日期上加配置中的时间
			log.info(c.getTime());
			String today = smdf.format(c.getTime());
			if (today.compareTo(smdf.format(new Date())) < 0) {// 如果小于0，过期
				buffer.append("issub = '1';");
			}
		}
		// 判断评论数是否已经满了
		if (maxComment != null && !maxComment.equals("0")) {// 0为无效，1或其他为 文章最大评论数目
			if (count >= Integer.parseInt(maxComment)) {// 文章评论数目已经超过设置的评论数
				buffer.append("issub = '2';");
			}
		}
		buffer.append("if(issub=='1'){alert('这篇文章已经过期，不能发表评论');return false;}");
		buffer.append("else if(issub=='2'){alert('文章的评论数已满，不能发表评论');return false;}");
		buffer.append("else if(author.trim()==''){alert('请输入评论人');return false;}");
		buffer.append("else if(content.trim()==''){alert('请输入评论内容');return false;}");
		buffer.append("else{document.articleCommentForm.submit();return true;}");
		buffer.append("  } ");
		buffer.append(" var s = null;");
		buffer.append("  function checkSubmitTime(id,flag){");
		buffer.append("  if(!s){");
		buffer.append(" s = new Date().getTime();");
		buffer.append(" alert('提交成功');");
		buffer.append("if(flag==\"1\"){");
		buffer.append("onclickSuport(id);}else{onclickIronfoe(id);}");
		buffer.append("return;}");
		buffer.append(" if((new Date().getTime() -s)<10000){");
		buffer.append(" alert('你发送评论的时间太快，请10秒后再发表');");
		buffer.append(" }else{");
		buffer.append(" s=new Date().getTime();");
		buffer.append("if(flag==\"1\"){");
		buffer.append("onclickSuport(id);}else{onclickIronfoe(id);}");
		buffer.append(" alert('提交成功');");
		buffer.append(" }");
		buffer.append(" }");
		buffer.append("function onclickIronfoe(id){");// 投票反对
		buffer.append("var artid=$(\"#articleId\").val();");
		buffer.append("$.ajax({");
		buffer.append(" url:'commitComment.do?dealMethod=commentIronfoe&articleid='+artid+'&commentId='+id,");
		buffer.append(" type:\"post\",");
		buffer.append("success:function(msg) {");
		buffer.append("document.getElementById(id+\"0\").innerHTML=msg;");//刷新页面
		buffer.append("}});");
		buffer.append("}");
		buffer.append("function onclickSuport(id){");// 投票支持
		buffer.append("var artid=$(\"#articleId\").val();");
		buffer.append("$.ajax({");
		buffer.append(" url:'commitComment.do?dealMethod=commentSupport&articleid='+artid+'&commentId='+id,");
		buffer.append(" type:\"post\",");
		buffer.append("success:function(msg) {");
		buffer.append("document.getElementById(id+\"1\").innerHTML=msg;");//刷新页面
		buffer.append("}});");
		buffer.append("}");
		buffer.append("</script>");
		if (list != null && list.size() > 0) {
			buffer.append("<div id='pp' >");
			for (int i = 0; i < list.size(); i++) {
				buffer.append(getStyleContentRegexReplace(((Object[]) list.get(i)), siteId));
			}
			buffer.append("</div>");
		}	
		return buffer.toString();
	}

	/**
	 * 获取样式内容，并且用正则替换
	 * @return 替换后的内容
	 */
	private StringBuffer getStyleContentRegexReplace(Object[] obj, String siteId) {
		String input = getStyle(siteId);
		Pattern pa = Pattern.compile("<!--[\\w\\d]+-->");// 标签正则 任意字符或数字
		Matcher matcher = pa.matcher(input);
		StringBuffer sb = new StringBuffer();
		String regexText = "";

		Map<String, String> map = getCommentSet(siteId);// 丛文件中获取配置信息
		String ipstyle = map.get("ipStyle");
		while (matcher.find()) {// 开始查找
			regexText = matcher.group();
			if (regexText.equals(IP)) {// 如果找到<!--ip-->,则将它替换为IP地址，以下几个同理
				if (ipstyle.equals("0")) {// 0为不显示
					matcher.appendReplacement(sb, "");
				} else if (ipstyle.equals("1")) {// 1为全部显示
					matcher.appendReplacement(sb, String.valueOf(obj[0]));
				} else {// 隐藏末尾
					String str = String.valueOf(obj[0]);
					String subIp = str.substring(0, str.lastIndexOf(".") + 1)
							+ "*";
					matcher.appendReplacement(sb, subIp);
				}
			}
			if (regexText.equals(AUTHOR)) {
				matcher.appendReplacement(sb, String.valueOf(obj[1]));
			}
			if (regexText.equals(CONTENT)) {
				matcher.appendReplacement(sb, String.valueOf(obj[2]));
			}
			if (regexText.equals(BALLOT)) {
				matcher.appendReplacement(sb, String.valueOf(obj[3]));
			}
			if (regexText.equals(DATE)) {
				matcher.appendReplacement(sb, String.valueOf(obj[4]));
			}
			if (regexText.equals(UNSUPORT)) {
				matcher.appendReplacement(sb, String.valueOf(obj[5]));
			}
			if (regexText.equals(COMMENTID)) {
				matcher.appendReplacement(sb, String.valueOf(obj[6]));
			}

		}
		matcher.appendTail(sb);
		return sb;
	}

	public Pagination getCommentOnauditededPagination(Pagination pagination,
			String isAudite) {
		if (isAudite.equals("1")) {// 审核通过的
			return articleCommentDao.getPagination(pagination, "auditid", true);
		} else {// 未通过的
			return articleCommentDao
					.getPagination(pagination, "auditid", false);
		}
	}

	public Pagination getCommentOndeletePagination(Pagination pagination) {
		return articleCommentDao.getPagination(pagination, "deletedid", true);
	}

	public void modifyCommentById(String ids, String deleteWhere) {
		String str[] = { "deleted", "ids" };
		String strWhere[] = { deleteWhere, ids };
		articleCommentDao.updateByDefine("updateArticleCommentDeletedByIds",
				str, strWhere);
	}

	public void deleteCommentById(String ids) {
		articleCommentDao.deleteByIds(SqlUtil.toSqlString(ids));
	}

	/**
	 * 获取文章中order字段的最大值.
	 * 
	 * @return 最大的order
	 */
	@SuppressWarnings("unchecked")
	private int findMaxArticleOrder() {
		int maxOrders = 0;
		List list = articleDao
				.findByNamedQueryAndClear("findMaxArticleCommentOrders");
		if (list != null && list.size() > 0) {
			ArticleComment article = (ArticleComment) list.get(0);
			maxOrders = article.getOrders();
		}
		return maxOrders;
	}

	public void modifyArticleCommentDeleteByAuditid(String ids, String flag,
			String siteId, String userId) {
		String id[] = ids.split(",");
		for (int j = 0; j < id.length; j++) {
			ArticleComment article = articleCommentDao.getAndClear(id[j]);
			if (flag.equals("1")) {// 审核通过
				article.setAudited(true);
			} else {// 审核未通过
				article.setAudited(false);
			}
			articleCommentDao.update(article);
		}
	}

	/**
	 *根据文章ID获取栏目ID.
	 * 
	 * @param articleId
	 * @return 栏目ID
	 */
	@SuppressWarnings("unchecked")
	private String getColumnId(String articleId) {
		String sql = "select column_id from articles where id=?";
		List<String> list = jdbcTemplate.queryForList(sql, new Object[]{articleId}, java.lang.String.class);
		String columnId = "";
		if (!CollectionUtil.isEmpty(list)) {
			columnId = list.get(0);
		}
		return columnId;
	}

	/**
	 * 根据栏目ID获取网站ID
	 * 
	 * @param articleId
	 *            文章ID
	 * @return 栏目ID
	 */
	private String getSiteId(String articleId) {
		String columnId = getColumnId(articleId);
		String sql = "select site_id from columns where id=?";
		List<String> list = jdbcTemplate.queryForList(sql, new Object[] { columnId }, java.lang.String.class);
		String siteId = "";
		if (list != null && list.size() > 0) {
			siteId = list.get(0);
		}
		return siteId;
	}

	/**
	 * 评论内取词
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<ArticleCommentsReplace> getReplaceWord() {
		List list1 = jdbcTemplate
				.queryForList("select * from article_comments_replaceword");
		List<ArticleCommentsReplace> list = new ArrayList<ArticleCommentsReplace>();
		Iterator it = list1.iterator();
		while (it.hasNext()) {
			ArticleCommentsReplace articleComment = new ArticleCommentsReplace();
			Map map = (Map) it.next();
			articleComment.setId(map.get("id").toString());
			articleComment.setFilterWord(map.get("filterWord").toString());
			articleComment.setReplaceWord(map.get("replaceWord").toString());
			list.add(articleComment);
		}
		return list;
	}

	/**
	 * 系统内取词
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<InformationFilter> getSystemReplaceWord() {
		List list1 = jdbcTemplate
				.queryForList("select * from information_filter where status=1");
		List<InformationFilter> list = new ArrayList<InformationFilter>();
		Iterator it = list1.iterator();
		while (it.hasNext()) {
			InformationFilter sysWord = new InformationFilter();
			Map map = (Map) it.next();
			sysWord.setId(map.get("id").toString());
			sysWord.setField1(map.get("field1").toString());
			sysWord.setReplaceField1(map.get("replaceField1").toString());
			list.add(sysWord);
		}
		return list;
	}

	/**
	 * 是否有过滤词
	 * 
	 * @param content
	 *            过滤内容
	 * @param isSystem
	 *            取词范围 ,true为评论内,false为系统内
	 * @return
	 */
	public boolean isFilter(String content, boolean isSystem) {
		boolean flag = false;
		if (isSystem) {
			List<ArticleCommentsReplace> list = getReplaceWord();
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					ArticleCommentsReplace replace = list.get(i);
					String filterWord = replace.getFilterWord();// 过滤词
					int sa = content.indexOf(filterWord);// 查找过滤词
					if (sa != -1) {// 找到了
						flag = true;
						break;
					}
				}
			}
		} else {
			List<InformationFilter> sysWordList = getSystemReplaceWord();
			if (sysWordList != null && sysWordList.size() > 0) {
				for (int i = 0; i < sysWordList.size(); i++) {
					InformationFilter sysInfo = sysWordList.get(i);
					String filterWord = sysInfo.getField1();// 过滤词
					int sa = content.indexOf(filterWord);// 查找过滤词
					if (sa != -1) {// 找到了
						flag = true;
						break;
					}
				}
			}
		}
		return flag;
	}

	/**
	 * 判断是否有过滤词.
	 * 
	 * @param content
	 *            发表内容
	 * @param isReplace
	 *            是否替换
	 * @param getReplace
	 *            取词范围 ,true为评论内,false为系统内
	 * @return 替换后的内容
	 */
	private String isHaveReplace(String content, boolean isReplace,
			boolean getReplace) {
		String c = "";
		if (getReplace) {// 评论内取词
			List<ArticleCommentsReplace> list = getReplaceWord();
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					ArticleCommentsReplace replace = list.get(i);
					String filterWord = replace.getFilterWord();// 过滤词
					String replaceWord = replace.getReplaceWord();// 替换词
					int sa = content.indexOf(filterWord);// 查找过滤词
					if (sa != -1 && isReplace) {// 找到了，并且要替换
						c = content.replaceAll(filterWord, replaceWord);
						log.info("评论内取词,发表的内容有过滤词，替换后是:" + c);
						break;
					} else {
						c = content;
					}
				}
			}
		} else {// 系统内取词
			List<InformationFilter> sysWordList = getSystemReplaceWord();
			if (sysWordList != null && sysWordList.size() > 0) {
				for (int i = 0; i < sysWordList.size(); i++) {
					InformationFilter sysInfo = sysWordList.get(i);
					String filterWord = sysInfo.getField1();// 过滤词
					String replaceWord = sysInfo.getReplaceField1();// 替换词
					int sa = content.indexOf(filterWord);// 查找过滤词
					if (sa != -1 && isReplace) {// 找到了，并且要替换
						c = content.replaceAll(filterWord, replaceWord);
						log.info("系统内取词,发表的内容有过滤词，替换后是:" + c);
						break;
					} else {
						c = content;
					}
				}
			}
		}
		return c;
	}

	public String addSuport(String id, String articleId) {
		jdbcTemplate
				.update("update article_comment set supporter=supporter+1 where id='"
						+ id + "'");
		String siteId = getSiteId(articleId);
		Map<String, String> map = getCommentSet(siteId);// 丛文件中获取配置信息
		String creamCount = map.get("creamCount");
		int maxSouport = getMaxSuport(id);
		if (null != creamCount && !creamCount.equals("0")) {
			if (maxSouport >= Integer.parseInt(creamCount)) {// 自动设置为精华
				jdbcTemplate
						.update("update article_comment set essence=1 where id='"
								+ id + "'");
			}
		}
		int i=jdbcTemplate.queryForInt("select supporter from article_comment where id='"
						+ id + "'");//获取支持的数目
		return i+"";
	}

	public String addIronfoe(String id) {
		jdbcTemplate
				.update("update article_comment set ironfoe=ironfoe+1 where id='"
						+ id + "'");
		int i=jdbcTemplate.queryForInt("select ironfoe from article_comment where id='"
				+ id + "'");//获取反对的数目
		return i+"";
	}

	/***
	 * 获取文章评论的最大支持数
	 * 
	 * @return
	 */
	private int getMaxSuport(String id) {
		int count = jdbcTemplate
				.queryForInt("select max(supporter) from article_comment where id='"
						+ id + "'");
		return count;
	}

	/**
	 * 获取文章评论数,
	 * 
	 * @return
	 */
	private int getCommentCount() {
		int count = jdbcTemplate
				.queryForInt("select count(*) from article_comment ");
		return count;
	}

	public String commitComment(ArticleCommentForm form, String articleId) {
		String mess = "";
		ArticleComment articleComment = form.getComment();
		String siteId = getSiteId(articleId);
		Article article = new Article();
		article.setId(articleId);
		articleComment.setArticle(article);
		int count = getCommentCount();// 丛数据库中获取文章的评论数目
		Map<String, String> map = getCommentSet(siteId);// 丛文件中获取配置信息
		String maxComment = map.get("articleCommentCount");// 文章最大评论数
		if (!maxComment.equals("0")) {// 0为无效，1或其他为设置的 文章最大评论数目
			if (count >= Integer.parseInt(maxComment)) {// 文章评论数目已经超过设置的评论数
				mess = "文章的评论数已满";
			} else {
				mess = addArticleComment(articleComment, articleId);
			}
		} else {
			mess = addArticleComment(articleComment, articleId);
		}
		return mess;

	}

	/**
	 * 添加文章评论
	 * 
	 * @param articleComment
	 * @param siteId
	 * @return
	 */
	private String addArticleComment(ArticleComment articleComment,
			String articleId) {
		String mess = "";
		String siteId = getSiteId(articleId);
		Map<String, String> map = getCommentSet(siteId);
		String userId = map.get("sessionId");
		String isLook = map.get("isLook");// 是否审核 0不审核，1审核
		articleComment.setAudited(isLook.equals("0"));// 如果=0就是向数据库插入直接审核通过
		String haveReplace = map.get("haveReplace");// 获取过滤词设置
		String replaceArea = map.get("replaceArea");// 取词范围,1为评论内,0为系统内

		if (isFilter(articleComment.getContent(), replaceArea.equals("1"))) {// 有过滤词
			if (haveReplace.equals("0")) {// 回收站
				articleComment.setDeleted(true);// 1代表 放入回收站
				articleComment.setAudited(false);// 设置为未审核
				mess = "发表的评论中含有非法词语,已经将评论放入回收站";
			} else if (haveReplace.equals("1")) {// 替换发布
				String c = isHaveReplace(articleComment.getContent(), true,
						replaceArea.equals("1"));
				articleComment.setDeleted(false);// 0代表发布出去
				articleComment.setContent(c);// 内容
				mess = "你的评论中含有非法词语，系统已经替换";
				log.info(articleComment.getAuthorName() + mess);
			} else {
				mess = "你的评论中含有非法词语，请更正后发表";
				return mess;
			}
		} else {// 没有过滤词，可以发表
			articleComment.setDeleted(false);// 0代表发布出去
			mess = "发表成功";
		}
		articleComment.setSupporter(0);// 支持票数
		articleComment.setIronfoe(0);// 反对票数
		articleComment.setCreateTime(new Date());

		articleComment.setEssence(false);// 精华贴
		articleComment.setOrders(1);
		articleComment.setToped(false);
		User user = new User();
		if (!userId.equals("")) {
			user.setId(userId);
			articleComment.setUser(user);
		}
		articleCommentDao.save(articleComment);
		return mess;
	}

	public void modifyArticleCommentDeleteByEssence(String ids,
			String isEssence, String siteId, String userId) {
		String id[] = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			ArticleComment article = articleCommentDao.getAndClear(id[i]);
			if (isEssence.equals("1")) {// 设置精华
				article.setEssence(true);
			} else {// 取消精华
				article.setEssence(false);
			}
			articleCommentDao.update(article);
		}
	}

	public void modifyArticleCommentDelete(String ids, String isToped,
			String siteId, String userId) {
		String Id[] = ids.split(",");
		for (int i = 0; i < Id.length; i++) {
			ArticleComment article = articleCommentDao.getAndClear(Id[i]);
			if (isToped.equals("0")) {// 还原
				article.setDeleted(false);
			} else {// 放入回收站
				article.setDeleted(true);
			}
			articleCommentDao.update(article);
		}

	}

	public void modifyArticleCommentTop(String Ids, String isToped,
			String siteId, String userId) {
		int orders = findMaxArticleOrder();
		String id[] = Ids.split(",");
		String categoryName = "";
		for (int i = 0; i < id.length; i++) {
			ArticleComment article = articleCommentDao.getAndClear(id[i]);
			if (isToped.equals("0")) {// 取消置顶
				article.setToped(false);
				categoryName = "文章评论管理->取消置顶";
			} else {// 置顶
				article.setToped(true);
				article.setOrders(orders + 1);
				categoryName = "文章评论管理->置顶";
			}
			articleCommentDao.update(article);

		}

	}

	/**
	 * 判断过滤词是否存在
	 * 
	 * @param word
	 * @return
	 */
	private boolean isExistReplaceWord(String word) {
		List list = articleCommentReplaceDao.findByNamedQuery(
				"findCommentByReplaceWord", "word", word);
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	public String addReplaceWord(ArticleCommentForm form, String siteId,
			String userId) {
		String mess = "";
		if (!isExistReplaceWord(form.getReplace().getFilterWord())) {
		ArticleCommentsReplace r = form.getReplace();
		Site site = new Site();
		site.setId(siteId);
		r.setSite(site);
		User user = new User();
		user.setId(userId);
		r.setUser(user);
		r.setCreateTime(new Date());
		articleCommentReplaceDao.save(r);
		} else {
			mess = "不能重复添加相同过滤词";
		}
		return mess;
	}

	public ArticleCommentsReplace findByReplaceId(String id) {
		return articleCommentReplaceDao.getAndClear(id);
	}

	public void deleteReplace(String ids) {
		String checkid[] = ids.split(",");
		for (String id : checkid) {
			articleCommentReplaceDao.deleteByKey(id);
		}
	}

	public String modifyReplace(ArticleCommentForm form) {
		String mess = "";
			ArticleCommentsReplace r = form.getReplace();
			ArticleCommentsReplace newR = findByReplaceId(form.getIds());
			newR.setReplaceWord(r.getReplaceWord());
			articleCommentReplaceDao.update(newR);
		return mess;
	}

	public Pagination getPagination(Pagination pagination, String siteId) {
		return articleCommentReplaceDao.getPagination(pagination, "siteId",
				siteId);
	}

	public void getCommentForForm(ArticleCommentForm form, String siteId) {
		Map<String, String> map = getCommentSet(siteId);
		form.setOpenType(map.get("openType"));
		form.setIsLook(map.get("isLook"));
		form.setIpStyle(map.get("ipStyle"));
		form.setHaveReplace(map.get("haveReplace"));
		form.setRowCommentCount(map.get("rowCommentCount"));
		form.setReplaceArea(map.get("replaceArea"));
		form.setArticleCommentCount(map.get("articleCommentCount"));
		form.setCreamCount(map.get("creamCount"));
		form.setTimeOutSet(map.get("timeOutSet"));
	}

	public void setStyle(String content, String siteId) {
		String path = GlobalConfig.appRealPath + "/release/site"+ siteId +"/plugin/articleComments/"+siteId+"_styleSet.xml";
		if (!FileUtil.isExist(path)) {
			FileUtil.copy(GlobalConfig.appRealPath+"/plugin/article_comment/conf/styleSet.xml", GlobalConfig.appRealPath +"/release/site"+ siteId +"/plugin/articleComments");
		}
		SAXReader read = new SAXReader();
		XMLWriter writer = null;
		try {
			Document document = read.read(new File(path));
			document.selectSingleNode("//plugin_style//style//stylecontent").setText(content);
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("utf-8");
			writer = new XMLWriter(new FileOutputStream(path), format);
			writer.write(document);
			
			// 拷贝到/plugin/article_comments/conf/目录
			if(FileUtil.isExist(GlobalConfig.appRealPath+"/plugin/article_comment/conf/"+siteId+"_styleSet.xml")){
				FileUtil.delete(GlobalConfig.appRealPath+"/plugin/article_comment/conf/"+siteId+"_styleSet.xml");
			}
			FileUtil.copy(GlobalConfig.appRealPath +"/release/site"+ siteId +"/plugin/articleComments/"+siteId+"_styleSet.xml", GlobalConfig.appRealPath+"/plugin/article_comment/conf");
			
			// 拷贝文件到发布目录
			Site site = siteDao.getAndClear(siteId);
			String dir = site.getPublishDir().substring(0, site.getPublishDir().length()-site.getPublishDir().split("/")[site.getPublishDir().split("/").length-1].length());
			String publishDir = dir+"/plugin/article_comment/conf";
			if(!FileUtil.isExist(publishDir)){
				FileUtil.makeDirs(publishDir);
			}
			if(FileUtil.isExist(publishDir+"/"+siteId+"_styleSet.xml")){
				FileUtil.delete(publishDir+"/"+siteId+"_styleSet.xml");
			}
			FileUtil.copy(GlobalConfig.appRealPath +"/release/site"+ siteId +"/plugin/articleComments/"+siteId+"_styleSet.xml", publishDir);
			
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String getStyle(String siteId) {
		String str = "";
		String filePath = GlobalConfig.appRealPath +"/plugin/article_comment/conf/"+ siteId +"_styleSet.xml";
		if (!FileUtil.isExist(filePath)) {
			FileUtil.copy(GlobalConfig.appRealPath+"/plugin/article_comment/conf/styleSet.xml", 
					GlobalConfig.appRealPath + "/release/site" + siteId	+ "/plugin/articleComments");
			File file = new File(GlobalConfig.appRealPath + "/release/site" +siteId+ "/plugin/articleComments/styleSet.xml");
			File newFile = new File(GlobalConfig.appRealPath + "/release/site" +siteId+ "/plugin/articleComments/"+siteId+"_styleSet.xml");
			file.renameTo(newFile);
			FileUtil.copy(GlobalConfig.appRealPath +"/release/site" +siteId+ "/plugin/articleComments/"+ siteId +"_styleSet.xml", GlobalConfig.appRealPath +"/plugin/article_comment/conf");
		}
		if(!FileUtil.isExist(filePath)){
			log.debug("文件不存在");
			return "";
		}
		SAXReader read = new SAXReader();
		try {
			Document document = read.read(new File(filePath));
			Element e = (Element) document.getRootElement().elementIterator("style").next();
			str = e.elementText("stylecontent");
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return str;
	}

	public void setCommentForForm(ArticleCommentForm form, String sessionId, String siteId) {
		String filePath = GlobalConfig.appRealPath + "/release/site"+ siteId +"/plugin/articleComments/"+siteId+"_articleAttribute.xml";
		File file = new File(GlobalConfig.appRealPath + "/release/site" + siteId+ "/plugin/articleComments/articleAttribute.xml");
		File file1 = new File(filePath);
		if (!FileUtil.isExist(filePath)) {
			FileUtil.copy(GlobalConfig.appRealPath+"/plugin/article_comment/conf/articleAttribute.xml", 
					GlobalConfig.appRealPath + "/release/site" + siteId	+ "/plugin/articleComments");
			file.renameTo(file1);
		}
		SAXReader read = new SAXReader();
		XMLWriter writer = null;
		try {
			InputStream input = new FileInputStream(file1);
			Document document = read.read(input);
			document.selectSingleNode("//plugin//articleplugin//openType").setText(form.getOpenType());
			document.selectSingleNode("//plugin//articleplugin//isLook").setText(form.getIsLook());
			document.selectSingleNode("//plugin//articleplugin//ipStyle").setText(form.getIpStyle());
			document.selectSingleNode("//plugin//articleplugin//haveReplace").setText(form.getHaveReplace());
			document.selectSingleNode("//plugin//articleplugin//replaceArea").setText(form.getReplaceArea());
			document.selectSingleNode("//plugin//articleplugin//articleCommentCount").setText(form.getArticleCommentCount());
			document.selectSingleNode("//plugin//articleplugin//creamCount").setText(form.getCreamCount());
			document.selectSingleNode("//plugin//articleplugin//timeOutSet").setText(form.getTimeOutSet());
			document.selectSingleNode("//plugin//articleplugin//sessionId").setText(sessionId);
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("utf-8");
			writer = new XMLWriter(new FileOutputStream(file1), format);
			writer.write(document);
			
			// 拷贝到/plugin/article_comments/conf/目录
			if(FileUtil.isExist(GlobalConfig.appRealPath+"/plugin/article_comment/conf/"+siteId+"_articleAttribute.xml")){
				FileUtil.delete(GlobalConfig.appRealPath+"/plugin/article_comment/conf/"+siteId+"_articleAttribute.xml");
			}
			FileUtil.copy(GlobalConfig.appRealPath +"/release/site"+ siteId +"/plugin/articleComments/"+siteId+"_articleAttribute.xml", GlobalConfig.appRealPath+"/plugin/article_comment/conf");
			
			// 拷贝文件到发布目录
			Site site = siteDao.getAndClear(siteId);
			String dir = site.getPublishDir().substring(0, site.getPublishDir().length()-site.getPublishDir().split("/")[site.getPublishDir().split("/").length-1].length());
			String publishDir = dir+"/plugin/article_comment/conf";
			if(!FileUtil.isExist(publishDir)){
				FileUtil.makeDirs(publishDir);
			}
			if(FileUtil.isExist(publishDir+"/"+siteId+"_articleAttribute.xml")){
				FileUtil.delete(publishDir+"/"+siteId+"_articleAttribute.xml");
			}
			FileUtil.copy(GlobalConfig.appRealPath +"/release/site"+ siteId +"/plugin/articleComments/"+siteId+"_articleAttribute.xml", publishDir);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取评论配置信息
	 * @param articleId   文章ID
	 * @return
	 */
	private Map<String, String> getCommentSet(String siteId) {
		Map<String, String> map = new HashMap<String, String>();
		String filePath = GlobalConfig.appRealPath +"/plugin/article_comment/conf/"+ siteId +"_articleAttribute.xml";
		if (!FileUtil.isExist(filePath)) {
			FileUtil.copy(GlobalConfig.appRealPath+"/plugin/article_comment/conf/articleAttribute.xml", 
					GlobalConfig.appRealPath + "/release/site" + siteId	+ "/plugin/articleComments");
			File file = new File(GlobalConfig.appRealPath + "/release/site" +siteId+ "/plugin/articleComments/articleAttribute.xml");
			File newFile = new File(GlobalConfig.appRealPath +"/release/site" +siteId+ "/plugin/articleComments/"+ siteId +"_articleAttribute.xml");
			file.renameTo(newFile);
			FileUtil.copy(GlobalConfig.appRealPath +"/release/site" +siteId+ "/plugin/articleComments/"+ siteId +"_articleAttribute.xml", GlobalConfig.appRealPath +"/plugin/article_comment/conf");
		}
		SAXReader read = new SAXReader();
		try {
			InputStream input = new FileInputStream(filePath);
			Document document = read.read(input);
			Element e = (Element) document.getRootElement().elementIterator("articleplugin").next();
			map.put("openType", e.elementText("openType"));// 开放类型
			map.put("isLook", e.elementText("isLook")); // 是否审核
			map.put("ipStyle", e.elementText("ipStyle")); // IP显示样式
			map.put("haveReplace", e.elementText("haveReplace"));// 当有过滤词时
			map.put("replaceArea", e.elementText("replaceArea"));// 替换范围
			map.put("articleCommentCount", e.elementText("articleCommentCount"));// 文章最大评论数
			map.put("creamCount", e.elementText("creamCount"));// 精华贴设置
			map.put("timeOutSet", e.elementText("timeOutSet"));// 评论过期设置
			map.put("sessionId", e.elementText("sessionId"));// 用户ID
		} catch (FileNotFoundException e) {
			log.debug("没有找到文件" + e);
		} catch (DocumentException e) {
			log.debug("XML文件操作失败" + e);
		}
		return map;
	}
	
	/**
     * 发布文章评论目录
     * @param siteId
     */
	public void publishArticleCommentDir(String siteId){
		Site site = siteDao.getAndClear(siteId);
		String dir = site.getPublishDir().substring(0, site.getPublishDir().length()-site.getPublishDir().split("/")[site.getPublishDir().split("/").length-1].length());
		String publishDir = dir+"/plugin";
		if(!FileUtil.isExist(publishDir)){
			FileUtil.makeDirs(publishDir);
		}
		String article_commentDir = GlobalConfig.appRealPath + "/plugin/article_comment";
		if(FileUtil.isExist(article_commentDir)){
			if(!FileUtil.isExist(publishDir+"/article_comment")){
				 FileUtil.copy(article_commentDir, publishDir, true);
			 }
		}
		// 发布要使用的js和css文件     
		
		//images/commentStyle.css
		String destCss = dir + "/images";
		if(!FileUtil.isExist(destCss)){
			FileUtil.makeDirs(destCss);
		}
		if(!FileUtil.isExist(destCss+"/commentStyle.css")){
			FileUtil.copy(GlobalConfig.appRealPath+"/images/commentStyle.css", destCss);
		}
		// css/style.css
		String destCss1 = dir + "/css";
		if(!FileUtil.isExist(destCss1)){
			FileUtil.makeDirs(destCss1);
		}
		if(!FileUtil.isExist(destCss1+"/style.css")){
			FileUtil.copy(GlobalConfig.appRealPath+"/css/style.css", destCss1);
		}
		//css/ajaxpagination.css
		if(!FileUtil.isExist(destCss1+"/ajaxpagination.css")){
			FileUtil.copy(GlobalConfig.appRealPath+"/css/ajaxpagination.css", destCss1);
		}
		
		// script/jquery-1.2.6.js
		String destJs = dir + "/script";
		if(!FileUtil.isExist(destJs)){
			FileUtil.makeDirs(destJs);
		}
		if(!FileUtil.isExist(destJs+"/jquery-1.2.6.js")){
			FileUtil.copy(GlobalConfig.appRealPath+"/script/jquery-1.2.6.js", destJs);
		}
		// script/global.js
		if(!FileUtil.isExist(destJs+"/global.js")){
			FileUtil.copy(GlobalConfig.appRealPath+"/script/global.js", destJs);
		}
		//script/jquery.pagination.js
		if(!FileUtil.isExist(destJs+"/jquery.pagination.js")){
			FileUtil.copy(GlobalConfig.appRealPath+"/script/jquery.pagination.js", destJs);
		}
	}

	/**
	 * @param articleDao
	 *            the articleDao to set
	 */
	public void setArticleDao(ArticleDao articleDao) {
		this.articleDao = articleDao;
	}

	/**
	 * @param articleCommentDao
	 *            the articleCommentDao to set
	 */
	public void setArticleCommentDao(ArticleCommentDao articleCommentDao) {
		this.articleCommentDao = articleCommentDao;
	}

	/**
	 * @param articleCommentReplaceDao
	 *            the articleCommentReplaceDao to set
	 */
	public void setArticleCommentReplaceDao(
			ArticelCommentReplaceDao articleCommentReplaceDao) {
		this.articleCommentReplaceDao = articleCommentReplaceDao;
	}

	/**
	 * @param jdbcTemplate
	 *            the jdbcTemplate to set
	 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * @param siteDao the siteDao to set
	 */
	public void setSiteDao(SiteDao siteDao) {
		this.siteDao = siteDao;
	}

	// public void setCommentForForm(ArticleCommentForm form, String sessionId)
	// {
	// // TODO Auto-generated method stub
	//		
	// }
	//
	// public void setStyle(String content) {
	// // TODO Auto-generated method stub
	//		
	// }

}
