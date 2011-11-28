/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.plugin.guestbookmanager.service.impl;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.baize.ccms.biz.sitemanager.domain.Site;
import com.baize.ccms.biz.unitmanager.label.CommonLabel;
import com.baize.ccms.plugin.guestbookmanager.dao.GuestBookContentDao;
import com.baize.ccms.plugin.guestbookmanager.dao.GuestCategoryDao;
import com.baize.ccms.plugin.guestbookmanager.domain.GuestBookCategory;
import com.baize.ccms.plugin.guestbookmanager.domain.GuestBookContent;
import com.baize.ccms.plugin.guestbookmanager.service.GuestBookContentService;
import com.baize.ccms.plugin.guestbookmanager.web.form.GuestBookForm;
import com.baize.ccms.sys.GlobalConfig;
import com.baize.common.core.dao.Pagination;
import com.baize.common.core.pager.PageQuery;
import com.baize.common.core.util.DateUtil;
import com.baize.common.core.util.FileUtil;
import com.baize.common.core.util.SqlUtil;
import com.baize.common.core.util.StringUtil;

/**
 * <p>
 * 标题: —— 留言具体实现类，主要用于前台操作
 * </p>
 * <p>
 * 描述: —— 简要描述类的职责、实现方式、使用注意事项等
 * </p>
 * <p>
 * 模块: CCMS
 * </p>
 * <p>
 * 版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * </p>
 * <p>
 * 网址：http://www.baizeweb.com
 * 
 * @author 曹名科
 * @version 1.0
 * @since 2009-11-7 下午02:19:16
 * @history（历次修订内容、修订人、修订时间等） 
 */
 

public class GuestBookContentServiceImpl implements GuestBookContentService {
	/** 显示IP */
	private final static String IP = "<!--ip-->";
	/** 发表人 */
	private final static String AUTHOR = "<!--author-->";
	/** 发表主题 */
	private final static String TITLE = "<!--title-->";
	/** 发表内容 */
	private final static String CONTENT = "<!--content-->";
	/** 发表时间 */
	private final static String DATE = "<!--date-->";
	/** 回复状态 */
	private final static String REPLYSTATE = "<!--replyState-->";
	/** 日志 */
	private final Logger log = Logger
			.getLogger(GuestBookContentServiceImpl.class);
	/** 注入前台JdbcTemplate. **/
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<GuestBookCategory> getGuestCategoryList(String siteId) {
		List<GuestBookCategory> list = new ArrayList<GuestBookCategory>();
		String sql = "select * from guestbook_guest_categories where site_id='"
				+ siteId + "'";
		List li = jdbcTemplate.queryForList(sql);
		Iterator it = li.iterator();
		while (it.hasNext()) {
			GuestBookCategory g = new GuestBookCategory();
			Map map = (Map) it.next();
			g.setId(map.get("id").toString());
			g.setCategoryName(map.get("categoryName").toString());
			Site sites = new Site();
			sites.setId(map.get("site_id").toString());
			g.setSites(sites);
			list.add(g);
		}
		return list;
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
		if (!FileUtil.isExist(path)) {
			log.debug("目录文件不存在" + path);
			return map;
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

	public String getAllCount(String siteId) {
		String sql = "select count(*) from guestbook_contents "
				+ "where guest_category_id "
				+ "in (select guest_category_id from guestbook_guest_categories "
				+ "where site_id='" + siteId + "')";
		int i = jdbcTemplate.queryForInt(sql);
		return i + "";
	}

	/**
	 * 查询敏感词
	 * 
	 * @param siteId
	 * @return
	 */
	private List<String> getAllSensitiveBySiteId(String siteId) {
		List list = jdbcTemplate
				.queryForList("select sensitiveWord from guestbook_sensitive_word where site_id='"
						+ siteId + "'");
		List<String> li = new ArrayList<String>();
		if (null != list && list.size() > 0) {
			Iterator it = list.iterator();
			while (it.hasNext()) {
				Map map = (Map) it.next();
				li.add(map.get("sensitiveWord").toString());
			}
		}
		return li;
	}

	/**
	 * 判断是否有敏感词
	 * 
	 * @param siteId
	 *            网站ID
	 * @param content
	 *            发表内容
	 * @return
	 */
	private boolean isExistSensitive(String siteId, String content, String title) {
		List<String> li = getAllSensitiveBySiteId(siteId);
		if (li.size() > 0) {
			for (int i = 0; i < li.size(); i++) {
				int sa = content.indexOf(li.get(i));// 内容中查找敏感词
				int sa1 = title.indexOf(li.get(i));// 标题中查找敏感词
				if (sa1 != -1) {// 找到了
					return true;
				}
				if (sa != -1) {// 找到了
					return true;
				}
			}
		}
		return false;
	}

	public String addGuestBookContent(GuestBookForm form) {
		/**
		 * 返回的信息mess 1.发表成功，待管理员审核 2.你的留言含有管理员设置的敏感词，请更正后再发表
		 * 
		 */
		String mess = "";
		Map<String, String> map = readAttributeSet(form.getSites());
		final GuestBookContent content = form.getGuestContent();// 获取发表内容对象
		GuestBookCategory gbc = new GuestBookCategory();
		gbc.setId(form.getGuestBookCategory());// 获取留言类别ID
		content.setGuestBookCategory(gbc);// 将ID放入留言
		String isAudit = map.get("isAudit");// 获取审核状态
		if (isAudit.equals("0")) {// 要审核
			content.setAuditStatus("0");// 设为未审核
		} else {
			content.setAuditStatus("3");// 设为不需要审核
		}
		if (isExistSensitive(form.getSites(), content.getBookContent(), content
				.getTitle())) {// 存在敏感词
			String isPublish = map.get("isPublish");
			if (isPublish.equals("0")) {// 管理员设置有过滤词时，禁止发布
				mess = "2";
				log.info(mess);
				return mess;
			} else {// 管理员设置有过滤词时，需要审核
				content.setAuditStatus("0");// 将状态设置为 未审核
			}
		}
		// 下面开始插入数据
		String sql = "insert into guestbook_contents(id,title,bookContent,"
				+ "email,bookName,phone,auditStatus,replyStatus,area,address,"
				+ "createTime,ip,guest_category_id)"
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		int i = jdbcTemplate.update(sql, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) {
				try {
					ps.setString(1, StringUtil.UUID());
					ps.setString(2, content.getTitle());
					ps.setString(3, content.getBookContent());
					ps.setString(4, content.getEmail());
					ps.setString(5, content.getBookName());
					ps.setString(6, content.getPhone());
					ps.setString(7, content.getAuditStatus());
					ps.setString(8, "0");
					ps.setString(9, content.getArea());
					ps.setString(10, content.getAddress());
					ps.setString(11, DateUtil.toString(new Date()));
					ps.setString(12, content.getIp());
					ps.setString(13, content.getGuestBookCategory().getId());
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		if (i > 0) {
			mess = "1";
		}
		log.info(mess);
		return mess;
	}

	/**
	 * 获取样式内容
	 * 
	 * @param siteId
	 * @return
	 */
	private String getStyleContent(String siteId) {
//		String filePath = GlobalConfig.appRealPath + "/release/site" + siteId
//				+ "/plugin/guestbook_manager/guestBookAttribute.xml";
		
		String filePath = GlobalConfig.appRealPath + "/plugin/guestbook_manager/conf/"+siteId+".xml";
		if (!FileUtil.isExist(filePath)) {
			log.debug("目录文件不存在" + filePath);
			return "";
		}
		SAXReader read = new SAXReader();
		try {
			Document document = read.read(new File(filePath));
			Element e = (Element) document.getRootElement().elementIterator("style").next();
			String style = e.elementText("styleContent");
			return style;
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return "";
	}

	public String getContentByCategory(String siteId, Pagination pa,
			String categoryName) {
		List li = findByCategory(pa, siteId, categoryName).getData();// 获取根据类别搜索到的分页数据
		StringBuffer sb = new StringBuffer();
		if (li != null) {// 替换样式内容
			for (int i = 0; i < li.size(); i++) {
				sb.append(replaceContent((Object[]) li.get(i), siteId));
			}
		}
		return sb.toString();
	}

	public String getContentByCondition(String siteId, Pagination pa,
			String categoryName, String keyword, String startTime,
			String endTime) {
		List li = getpaginationContentBySelect(pa, siteId, categoryName,
				keyword, startTime, endTime).getData();// 获取根据条件搜索到的分页数据
		StringBuffer sb = new StringBuffer();
		if (li != null) {// 替换样式内容
			for (int i = 0; i < li.size(); i++) {
				sb.append(replaceContent((Object[]) li.get(i), siteId));
			}
		}
		return sb.toString();
	}

	public String getContent(String siteId, Pagination pa) {
		List li = getpaginationContent(pa, siteId).getData();
		StringBuffer sb = new StringBuffer();
		if (li != null) {// 替换样式内容
			for (int i = 0; i < li.size(); i++) {
				sb.append(replaceContent((Object[]) li.get(i), siteId));
			}
		}
		return sb.toString();
	}

	/**
	 * 替换样式内容
	 * 
	 * @param obj
	 * @param siteId
	 * @return
	 */
	private StringBuffer replaceContent(Object[] obj, String siteId) {
		String input = getStyleContent(siteId);
		StringBuffer sb = new StringBuffer();
		Pattern p = Pattern.compile(CommonLabel.REGEX_LABEL);
		Matcher ma = p.matcher(input);
		String id = obj[0].toString();
		while (ma.find()) {
			String findout = ma.group();
			if (findout.equals(TITLE)) {// 留言标题
				ma.appendReplacement(sb, obj[1].toString());
			}
			if (findout.equals(CONTENT)) {// 留言内容
				ma.appendReplacement(sb, obj[2].toString());
			}
			if (findout.equals(AUTHOR)) {// 留言人
				ma.appendReplacement(sb, obj[3].toString());
			}
			if (findout.equals(DATE)) {// 留言日期
				ma.appendReplacement(sb, obj[4].toString());
			}
			if (findout.equals(IP)) {// 留言IP
				ma.appendReplacement(sb, obj[5].toString());
			}
			if (findout.equals(REPLYSTATE)) {// 留言的恢复状态
				String status = obj[6].toString();
				if (status.equals("0")) {// 未回复
					ma.appendReplacement(sb, "未回复");
				} else {//已回复,显示回复内容
					String content=getReplyContent(id);
					ma.appendReplacement(sb, content);
				}
			}
		}
		ma.appendTail(sb);
		return sb;
	}
	/**
	 * 显示已经回复的内容
	 * @param id
	 * @return
	 */
	private String getReplyContent(String id){
		String sql = "select revertContent from guestbook_revert where guest_content_id='"+id+"'";
		List li = jdbcTemplate.queryForList(sql);
		String content = "";
		if(li!=null&&li.size()>0){
			Map map = (Map)li.iterator().next();
			content=map.get("revertContent").toString();
		}
		return content;
	}
	/**
	 * 获取类别ID(由于分页不能用两个select所以将它先查出来)
	 * 
	 * @param siteId
	 * @return
	 */
	private String getCategoryId(String siteId) {
		String sql = "select id from guestbook_guest_categories where site_id='"+ siteId + "'";
		List li = jdbcTemplate.queryForList(sql);
		List list = new ArrayList();
		Iterator it = li.iterator();
		while (it.hasNext()) {
			Map map = (Map) it.next();
			list.add(map.get("id").toString());
		}
		if (list != null && list.size() > 0) {
			if (list.size() == 1) {// 当查的类别就只有一条记录时,直接返回，无需处理
				String cid = "";// 存储ID
				cid = list.get(0).toString();
				return SqlUtil.toSqlString(cid);
			} else {// 当查询的类别多条记录，需将ID处理然后再返回
				String cid = "";// 存储ID
				for (int i = 0; i < list.size(); i++) {
					cid = SqlUtil.toSqlString(list.get(i).toString()) + ","	+ cid;
				}
				String lastId = cid.substring(0, cid.length() - 1);// 去掉最后一个,号
				return lastId;
			}
		} else {
			return "";
		}

	}

	public Pagination findByCategory(Pagination pa, String siteId, String categoryName) {
		String categoryId = getCategoryId(siteId);// 查询类别ID
		if (null != categoryId && !categoryId.equals("")) {
			String sql = "select id,title,bookContent,bookName,createTime,ip,replyStatus from guestbook_contents where"
					+ " (auditStatus='1' or auditstatus='3') and"
					+ " guest_category_id in (" + categoryId + ")";
			if (null != categoryName && !categoryName.equals("")) {
				sql += "and guest_category_id='" + categoryName + "' ";
			}
			pa.setQueryString(sql);
			Pagination p = PageQuery.getPaginationByQueryString(pa,	jdbcTemplate);
			return p;
		} else {
			String sql = "select id,title,bookContent,bookName,createTime,ip,replyStatus from guestbook_contents where"
				+ " (auditStatus='1' or auditstatus='3') ";
			pa.setQueryString(sql);
			Pagination p = PageQuery.getPaginationByQueryString(pa,	jdbcTemplate);
			return p;
		}
		//return new Pagination();
	}

	public Pagination getpaginationContentBySelect(Pagination pa,
			String siteId, String categoryName, String keyword,
			String startTime, String endTime) {
		String categoryId = getCategoryId(siteId);// 查询类别ID
		if (null != categoryId && !categoryId.equals("")) {
			String sql = "select id,title,bookContent,bookName,createTime,ip,replyStatus from guestbook_contents where "
					+ " (auditStatus='1' or auditstatus='3') and"
					+ " guest_category_id in (" + categoryId + ")";
			if (null != categoryName && !categoryName.equals("")
					&& null != keyword && !keyword.equals("")) {// 根据用户选择的搜索条件查询
				sql = sql + " and " + categoryName + " like '%" + keyword
						+ "%'";
			}
			if (null != startTime && !startTime.equals("")) {// 根据留言时间范围查询
				sql = sql + " and createTime >'" + startTime + "'";
			}
			if (null != endTime && !endTime.equals("")) {// 根据留言时间范围查询
				sql = sql + " and createTime <'" + endTime + "'";
			}
			pa.setQueryString(sql);
			Pagination p = PageQuery.getPaginationByQueryString(pa,
					jdbcTemplate);
			return p;
		}
		return new Pagination();
	}

	public Pagination getpaginationContent(Pagination pa, String siteId) {
		String categoryId = getCategoryId(siteId);// 查询类别ID
		if (null != categoryId && !categoryId.equals("")) {
			String sql = "select id,title,bookContent,bookName,createTime,ip,replyStatus from guestbook_contents where "
					+ " (auditStatus='1' or auditstatus='3') and"
					+ " guest_category_id in (" + categoryId + ")";
			pa.setQueryString(sql);
			Pagination p = PageQuery.getPaginationByQueryString(pa,
					jdbcTemplate);
			return p;
		}
		return new Pagination();
	}

}
