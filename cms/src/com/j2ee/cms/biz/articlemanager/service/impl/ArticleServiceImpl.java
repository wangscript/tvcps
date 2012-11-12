/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.articlemanager.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.springframework.jdbc.core.JdbcTemplate;

import com.j2ee.cms.biz.articlemanager.dao.ArticleAttachDao;
import com.j2ee.cms.biz.articlemanager.dao.ArticleAttributeDao;
import com.j2ee.cms.biz.articlemanager.dao.ArticleDao;
import com.j2ee.cms.biz.articlemanager.dao.ArticleFormatDao;
import com.j2ee.cms.biz.articlemanager.dao.EnumerationDao;
import com.j2ee.cms.biz.articlemanager.domain.Article;
import com.j2ee.cms.biz.articlemanager.domain.ArticleAttach;
import com.j2ee.cms.biz.articlemanager.domain.ArticleAttribute;
import com.j2ee.cms.biz.articlemanager.domain.ArticleFormat;
import com.j2ee.cms.biz.articlemanager.domain.Enumeration;
import com.j2ee.cms.biz.articlemanager.service.ArticleService;
import com.j2ee.cms.biz.articlemanager.web.form.ArticleForm;
import com.j2ee.cms.biz.columnmanager.dao.ColumnDao;
import com.j2ee.cms.biz.columnmanager.domain.Column;
import com.j2ee.cms.biz.configmanager.dao.GeneralSystemSetDao;
import com.j2ee.cms.biz.configmanager.dao.InformationFilterDao;
import com.j2ee.cms.biz.configmanager.dao.SystemLogDao;
import com.j2ee.cms.biz.configmanager.domain.GeneralSystemSet;
import com.j2ee.cms.biz.configmanager.domain.InformationFilter;
import com.j2ee.cms.biz.publishmanager.dao.ArticleBuildListDao;
import com.j2ee.cms.biz.publishmanager.dao.ArticlePublishListDao;
import com.j2ee.cms.biz.publishmanager.domain.ArticleBuildList;
import com.j2ee.cms.biz.publishmanager.service.Publisher;
import com.j2ee.cms.biz.publishmanager.service.remotepublish.client.FtpSender;
import com.j2ee.cms.biz.sitemanager.dao.SiteDao;
import com.j2ee.cms.biz.sitemanager.domain.Site;
import com.j2ee.cms.biz.templatemanager.dao.TemplateUnitDao;
import com.j2ee.cms.biz.templatemanager.domain.TemplateInstance;
import com.j2ee.cms.biz.templatemanager.domain.TemplateUnit;
import com.j2ee.cms.biz.usermanager.dao.RightDao;
import com.j2ee.cms.biz.usermanager.dao.UserDao;
import com.j2ee.cms.biz.usermanager.domain.Operation;
import com.j2ee.cms.biz.usermanager.domain.Resource;
import com.j2ee.cms.biz.usermanager.domain.Right;
import com.j2ee.cms.biz.usermanager.domain.User;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.util.BeanUtil;
import com.j2ee.cms.common.core.util.CollectionUtil;
import com.j2ee.cms.common.core.util.DateUtil;
import com.j2ee.cms.common.core.util.FileUtil;
import com.j2ee.cms.common.core.util.SqlUtil;
import com.j2ee.cms.common.core.util.StringUtil;
import com.j2ee.cms.sys.GlobalConfig;
import com.j2ee.cms.sys.SiteResource;

/**
 * <p>
 * 标题: 文章服务类.
 * </p>
 * <p>
 * 描述: —— 简要描述类的职责、实现方式、使用注意事项等
 * </p>
 * <p>
 * 模块: 文章管理
 * </p>
 * <p>
 * 版权: Copyright (c) 2009  
 * 
 * @author 杨信
 * @version 1.0
 * @since 2009-3-10 下午05:26:02
 * @history（历次修订内容、修订人、修订时间等） 
 */
 

public class ArticleServiceImpl implements ArticleService {

	private final Logger log = Logger.getLogger(ArticleServiceImpl.class);

	/** 注入文章附件dao */
	private ArticleAttachDao articleAttachDao;
	/** 注入文章dao */
	private ArticleDao articleDao;

	/** 注入栏目da0 */
	private ColumnDao columnDao;

	/** 注入文章格式dao */
	private ArticleFormatDao articleFormatDao;

	/** 注入文章发布列表dao */
	private ArticlePublishListDao articlePublishListDao;

	/** 注入发布机 */
	private Publisher publisher;

	/** 注入文章生成dao */
	private ArticleBuildListDao articleBuildListDao;

	/** 注入系统数据库日志dao */
	private SystemLogDao systemLogDao;

	/** 注入枚举dao **/
	private EnumerationDao enumerationDao;

	/** 注入文章属性dao **/
	private ArticleAttributeDao articleAttributeDao;

	/** 注入权力DAO */
	private RightDao rightDao;

	/** 注入用户DAO */
	private UserDao userDao;

	/** 注入网站dao */
	private SiteDao siteDao;

	/**
	 * 所有的格式字段 key : 文章类型 value : 该类型对应的字段串，中间以","隔开
	 */
	public static Map<String, String> formatFields = new HashMap<String, String>();

	/** 注入系统设置dao **/
	private GeneralSystemSetDao generalSystemSetDao;

	/** 注入系统过滤设置dao **/
	private InformationFilterDao informationFilterDao;
	
	/** 注入前台JdbcTemplate. **/
	private JdbcTemplate jdbcTemplate;
	
	/** 注册模板单元 */
	private TemplateUnitDao templateUnitDao;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void addArticleAtach(Article article, List<String> picAttachList, List<String> mediaAttachList, List<String> attachAttachList){
	    if(article != null && article.getId() != null){
	        ArticleAttach attach = null;
	        if(picAttachList != null && picAttachList.size() > 0){
    	        for(int i = 0; i < picAttachList.size(); i++){
    	            attach = new ArticleAttach();
    	            attach.setArticle(article);
    	            attach.setPath(picAttachList.get(i));
    	            attach.setType("pic");
    	            if(i == 0){
    	                attach.setMajor(1);
    	            }
    	            articleAttachDao.save(attach);
    	        }
	        }
	        if(mediaAttachList != null && mediaAttachList.size() > 0){
    	        for(int i = 0; i < mediaAttachList.size(); i++){
                    attach = new ArticleAttach();
                    attach.setArticle(article);
                    attach.setPath(mediaAttachList.get(i));
                    attach.setType("media");
                    if(i == 0){
                        attach.setMajor(1);
                    }
                    articleAttachDao.save(attach);
                }
	        }
	        if(attachAttachList != null && attachAttachList.size() > 0){
                for(int i = 0; i < attachAttachList.size(); i++){
                    attach = new ArticleAttach();
                    attach.setArticle(article);
                    attach.setPath(attachAttachList.get(i));
                    attach.setType("attach");
                    if(i == 0){
                        attach.setMajor(1);
                    }
                    articleAttachDao.save(attach);
                }
	        }
	    }
	}

	public void modifyArticleAtach(Article article, List<String> picAttachList, List<String> mediaAttachList, List<String> attachAttachList){
        if(article != null && article.getId() != null){
            String[] params = {"articleId", "type"};
            Object[] values1 = {article.getId(), "pic"};
            Object[] values2 = {article.getId(), "media"};
            Object[] values3 = {article.getId(), "attach"};
            List<ArticleAttach> picList = articleAttachDao.findByNamedQuery("findArticleAttachsByArticleIdAndAttachType", params, values1);
            Map<String, ArticleAttach> picMap = new LinkedHashMap<String, ArticleAttach>();
            for(int i = 0; i < picList.size(); i++){
                picMap.put(picList.get(i).getPath(), picList.get(i));
            }
            List<ArticleAttach> mediaList = articleAttachDao.findByNamedQuery("findArticleAttachsByArticleIdAndAttachType", params, values2);
            Map<String, ArticleAttach> mediaMap = new LinkedHashMap<String, ArticleAttach>();
            for(int i = 0; i < mediaList.size(); i++){
                mediaMap.put(mediaList.get(i).getPath(), mediaList.get(i));
            }
            List<ArticleAttach> attachList = articleAttachDao.findByNamedQuery("findArticleAttachsByArticleIdAndAttachType", params, values3);
            Map<String, ArticleAttach> attachMap = new LinkedHashMap<String, ArticleAttach>();
            for(int i = 0; i < attachList.size(); i++){
                attachMap.put(attachList.get(i).getPath(), attachList.get(i));
            }
            ArticleAttach attach = null;
            if(picAttachList.size() > 0 && picMap.get(picAttachList.get(0)) != null){
                attach = picMap.get(picAttachList.get(0));
                if(attach.getMajor() != 1){
                    attach.setMajor(1);
                    articleAttachDao.updateAndClear(attach);
                }
            }
            if(mediaAttachList.size() > 0 && mediaMap.get(mediaAttachList.get(0)) != null){
                attach = mediaMap.get(mediaAttachList.get(0));
                if(attach.getMajor() != 1){
                    attach.setMajor(1);
                    articleAttachDao.updateAndClear(attach);
                }
            }
            if(attachAttachList.size() > 0 && attachMap.get(attachAttachList.get(0)) != null){
                attach = attachMap.get(attachAttachList.get(0));
                if(attach.getMajor() != 1){
                    attach.setMajor(1);
                    articleAttachDao.updateAndClear(attach);
                }
            }
            for(int i = 0; i < picAttachList.size(); i++){
                if(picMap.get(picAttachList.get(i)) == null){
                    attach = new ArticleAttach();
                    attach.setArticle(article);
                    attach.setPath(picAttachList.get(i));
                    attach.setType("pic");
                    if(i == 0){
                        attach.setMajor(1);
                    }
                    articleAttachDao.save(attach);
                }else{
                    picMap.remove(picAttachList.get(i));
                }
            }
            if(!picMap.isEmpty()){
                Iterator itr = picMap.keySet().iterator();
                while(itr.hasNext()) {
                    articleAttachDao.delete(picMap.get(itr.next()));
                }
            }
            for(int i = 0; i < mediaAttachList.size(); i++){
                if(mediaMap.get(mediaAttachList.get(i)) == null){
                    attach = new ArticleAttach();
                    attach.setArticle(article);
                    attach.setPath(mediaAttachList.get(i));
                    attach.setType("media");
                    if(i == 0){
                        attach.setMajor(1);
                    }
                    articleAttachDao.save(attach);
                }else{
                    mediaMap.remove(mediaAttachList.get(i));
                }
            }
            if(!mediaMap.isEmpty()){
                Iterator itr = mediaMap.keySet().iterator();
                while(itr.hasNext()) {
                    articleAttachDao.delete(mediaMap.get(itr.next()));
                }
            }
            for(int i = 0; i < attachAttachList.size(); i++){
                if(attachMap.get(attachAttachList.get(i)) == null){
                    attach = new ArticleAttach();
                    attach.setArticle(article);
                    attach.setPath(attachAttachList.get(i));
                    attach.setType("attach");
                    if(i == 0){
                        attach.setMajor(1);
                    }
                    articleAttachDao.save(attach);
                }else{
                    attachMap.remove(attachAttachList.get(i));
                }
            }
            if(!attachMap.isEmpty()){
                Iterator itr = attachMap.keySet().iterator();
                while(itr.hasNext()) {
                    articleAttachDao.delete(attachMap.get(itr.next()));
                }
            }
        }
    }
	
	public Map addArticle(Article article, Boolean isUpSystemAdmin) {
		// 定时发布的文章
		String timeSettingArticleIds = "";
		// 定时发布的文章的时间
		String timeSetting = "";
		// 发布方式，定时时间
		Map<String, String> map = new HashMap<String, String>();
		int maxArticle = 0;
		// 设置文章的order
		maxArticle = this.findMaxArticleOrder();
		article.setOrders(maxArticle + 1);
		String columnId = article.getColumn().getId();
		String siteId = article.getSite().getId();
		String Id = "f017";
		List<GeneralSystemSet> list = findGeneralSystemSetLink(Id);
		String articleTextArea = article.getTextArea();
		article.setCreateTime(new Date());
		// 先全部替换需要替换的内容 keyFilter
		// 查找过滤内容
		/** 判断过滤 */
		/** 判断父栏目是否存在，如果不存在就是主栏目 */
		if (article.isKeyFilter()) {
			/** 系统管理员过滤内容 */
			List SiteIdCount = siteDao.findByNamedQuery("findSiteParentId");
			if (isUpSystemAdmin) {
				if (SiteIdCount.size() < 1) {
					articleTextArea = replaceAllTextArearBySystemAuthor(articleTextArea);
				} else {
					articleTextArea = replaceAllTextArear(articleTextArea, siteId);
				}
			} else {
				articleTextArea = replaceAllTextArear(articleTextArea, siteId);
			}
		}
		for (int i = 0; i < list.size(); i++) {
			/** <a href='http://www.qq.com'>中国</a> */
			GeneralSystemSet generalSystemSet = (GeneralSystemSet) list.get(i);
			String setContent = generalSystemSet.getSetContent();
			String url = generalSystemSet.getUrl();
			String replace = "<a href='" + url + "'><font color='blue'>" + setContent + "</font></a>";
			/** 不断的给articleTextAreal替换内容 */
			if(!StringUtil.isEmpty(articleTextArea)){
				articleTextArea = StringUtil.replace(articleTextArea, setContent,	replace);
			}
		}
		article.setTextArea(articleTextArea);
		
		// 根据栏目的发布方式来发布文章
		Column column = columnDao.getAndClear(columnId);
		String publishWay = column.getPublishWay();
		// 自动发布,如果栏目设置为已发布，文章也是已审核状态则将文章添加到发布列表中，状态改为发布中
		if(article.isAudited() && publishWay.equals(Column.PUBLISH_WAY_AUTO) && !article.getPublishState().equals(article.PUBLISH_STATE_DRAFT)){
			article.setPublishState(Article.PUBLISH_STATE_PUBLISHING);
		// 定时发布 、 手动发布	
		}else{
			article.setPublishState(Article.PUBLISH_STATE_UNPUBLISHEED);
		}
		if(article.getDisplayTime() == null){
			article.setDisplayTime(article.getCreateTime());
		}
		articleDao.save(article);
		
		// 建立文章时自动生成一个url（修改的时候不允许修改 ->.html）
		String initUrl = SiteResource.getBuildStaticDir(siteId, true) + "/col_"
				+ columnId + "/" + DateUtil.getCurrDate().replaceAll("-", "/")
				+ "/art_" + article.getId() + ".html";
		article.setInitUrl(initUrl);
		if (article.getUrl() == null || article.getUrl().equals("")) {
			article.setUrl(initUrl);
		}
		articleDao.updateAndClear(article);
		
		// 先保存文章才能复制
		// 自动发布,如果栏目设置为已发布，文章也是已审核状态则将文章添加到发布列表中，状态改为发布中
		if(article.isAudited() && publishWay.equals(Column.PUBLISH_WAY_AUTO) && !article.getPublishState().equals(article.PUBLISH_STATE_DRAFT)){
			// 写入生成列表
			ArticleBuildList articleBuildList = new ArticleBuildList();
			BeanUtil.copyProperties(articleBuildList, article);
		/*	publisher.initCategoryMap();
			//将文章相关的栏目页重新发布
			publisher.buildColumnByColumnId(article.getColumn().getId(), siteId);
			
			//首页重新发布
			publisher.buildArticleByArticleId(article.getId(), siteId);*/
			if (articleBuildListDao.getAndClear(articleBuildList.getId()) == null) {
				articleBuildList.setStatus("true");
				articleBuildListDao.saveAndClear(articleBuildList);
			} else {
				articleBuildList.setStatus("true");
				articleBuildListDao.updateAndClear(articleBuildList);
			}
		
/*			if(!article.isRef()){
				//如果这篇文章不是引用过来的文章
				//再查询此篇文章是否被引用		
				Article refArticle = null;
				List articleList = articleDao.findByNamedQuery("findArticlesByReferedArticleIdAndSiteId","id",article.getId());
				if(articleList != null && articleList.size() > 0 ){
					for(int i = 0  ; i < articleList.size() ; i++){
						refArticle = (Article)articleList.get(i);
						// 写入生成列表
						ArticleBuildList temparticleBuildList = new ArticleBuildList();
						BeanUtil.copyProperties(temparticleBuildList, article);
						if (articleBuildListDao.getAndClear(temparticleBuildList.getId()) == null) {
							articleBuildListDao.saveAndClear(temparticleBuildList);
						} else {
							articleBuildListDao.updateAndClear(temparticleBuildList);
						}
						//将文章相关的栏目页重新发布
						publisher.buildColumnByColumnId(article.getColumn().getId(), siteId);
					}
				}
			}*/
			
		// 定时发布 、 手动发布	
		}else{
			// 定时发布（文章审核通过的情况下）
			if(article.isAudited() && publishWay.equals(Column.PUBLISH_WAY_TIME)&& !article.getPublishState().equals(article.PUBLISH_STATE_DRAFT)){
				timeSettingArticleIds = article.getId();
				timeSetting = String.valueOf(column.getTimeSetting());
			}
		}
		
		// 如果该栏目是引用父站的，则父站栏目自动和当前栏目同步
		String parentSiteColumnId = "";
		if (column.getParentSiteColumnId() != null	&& !column.getParentSiteColumnId().equals("") && !column.getParentSiteColumnId().equals("null")) {
			Article destArticle = new Article();
			parentSiteColumnId = column.getParentSiteColumnId();
			Article orgArticle = articleDao.getAndClear(article.getId());
			BeanUtil.copyProperties(destArticle, orgArticle);
			destArticle.setId(null);
			Column refColumn = new Column();
			refColumn.setId(parentSiteColumnId);
			destArticle.setColumn(refColumn);
			Site site = new Site();
			Column parentSiteColumn = columnDao.getAndNonClear(parentSiteColumnId);
			site.setId(parentSiteColumn.getSite().getId());
			columnDao.clearCache();
			destArticle.setSite(site);
			// 可否修改
			boolean allowModify = column.isAllowModify();
			destArticle.setRef(!allowModify);
			if (allowModify) {
				destArticle.setReferedArticle(null);
			} else {
				Article referedArticle = new Article();
				referedArticle.setId(orgArticle.getId());
				destArticle.setReferedArticle(referedArticle);
			}
			Map<String, String> newMap = this.addPresentArticle(destArticle);
			String newTimeSettingArticleIds = newMap.get("timeSettingArticleIds");
			String newTimeSetting = newMap.get("timeSetting");
			if(!StringUtil.isEmpty(newTimeSettingArticleIds)){
				if(!StringUtil.isEmpty(timeSettingArticleIds)){
					timeSettingArticleIds += "," + newTimeSettingArticleIds;
					timeSetting += "," + newTimeSetting;
				}else{
					timeSettingArticleIds = newTimeSettingArticleIds;
					timeSetting = newTimeSetting;
				}
			}
		}
		// 栏目同步时添加文章
		boolean allowSend = column.isSendMenu();
		// 开启发送
		if (allowSend) {
			// 获得发送的栏目ids
			String refColumnIds = column.getRefColumnIds();
			String cIds[] = refColumnIds.split(",");
			if (refColumnIds != null && !refColumnIds.equals("") && !refColumnIds.equals("0")) {
				for (int i = 0; i < cIds.length; i++) {
					Column activeColumn = columnDao.getAndClear(cIds[i]);
					if (activeColumn != null) {
						// 被发送的栏目开关
						boolean allowReceive = activeColumn.isReceiveMenu();
						// 开
						if (allowReceive) {
							Article destArticle = new Article();
							Article orgArticle = articleDao.getAndClear(article.getId());
							BeanUtil.copyProperties(destArticle, orgArticle);
							destArticle.setId(null);
							Column refColumn = new Column();
							refColumn.setId(cIds[i]);
							destArticle.setColumn(refColumn);
							Column artColumn = columnDao.getAndClear(cIds[i]);
							String artSiteId = artColumn.getSite().getId();
							Site artSite = new Site();
							artSite.setId(artSiteId);
							destArticle.setSite(artSite);
							// 可否修改
							boolean allowModify = column.isAllowModify();
							destArticle.setRef(!allowModify);
							if (allowModify) {
								destArticle.setReferedArticle(null);
							} else {
								Article referedArticle = new Article();
								referedArticle.setId(orgArticle.getId());
								destArticle.setReferedArticle(referedArticle);
							}
							Map<String, String> newMap = this.addPresentArticle(destArticle);
							String newTimeSettingArticleIds = newMap.get("timeSettingArticleIds");
							String newTimeSetting = newMap.get("timeSetting");
							if(!StringUtil.isEmpty(newTimeSettingArticleIds)){
								if(!StringUtil.isEmpty(timeSettingArticleIds)){
									timeSettingArticleIds += "," + newTimeSettingArticleIds;
									timeSetting += "," + newTimeSetting;
								}else{
									timeSettingArticleIds = newTimeSettingArticleIds;
									timeSetting = newTimeSetting;
								}
							}
						}
					}
				}
			}
		}
		map.put("timeSettingArticleIds", timeSettingArticleIds);
		map.put("timeSetting", timeSetting);
		
		// 写入日志
		String categoryName = "文章管理->添加";
		String userId = article.getCreator().getId();
		String param = article.getTitle();
		systemLogDao.addLogData(categoryName, siteId, userId, param);
		return map;
	}
	
	
	/**
	 * 处理定时发布文章
	 * @param pubishTime
	 * @param articleId
	 * @param articleService
	 */
	public void proccessTimeArticle(final String timeSetting, final String timeSettingArticleIds, final ArticleService articleService) {
		 Thread temp = new Thread() {
			public void run() {
				try {
					if(!StringUtil.isEmpty(timeSettingArticleIds)){
						String[] ids = timeSettingArticleIds.split(",");
						String[] times = timeSetting.split(",");
						for(int i = 0; i < ids.length; i++){
							System.out.println("start============"+new Date());
							Long time = StringUtil.parseLong(times[i]);
							Thread.sleep(1000*time);
							articleService.modifyArticlePublishState(ids[i]);
							System.out.println("end============"+new Date());
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		temp.start();
	 }
	
	/**
	  * 定时修改文章发布状态 
	  * @param articleId
	  */
	 public void modifyArticlePublishState(String articleId){
		// 将定时发布的文章写入生成列表
		Article article = articleDao.getAndClear(articleId);
		ArticleBuildList articleBuildList = new ArticleBuildList();
		BeanUtil.copyProperties(articleBuildList, article);
	/*	publisher.initCategoryMap();
	 
		//将文章相关的栏目页重新发布
		publisher.buildColumnByColumnId(article.getColumn().getId(), article.getSite().getId());
		//首页重新发布
		publisher.buildArticleByArticleId(articleId, article.getSite().getId());*/
		if (articleBuildListDao.getAndClear(articleBuildList.getId()) == null) {
			articleBuildList.setStatus("true");
			articleBuildListDao.saveAndClear(articleBuildList);
		} else {
			articleBuildList.setStatus("true");
			articleBuildListDao.updateAndClear(articleBuildList);
		}
		
		String[] paramNames = new String[]{"publishState", "publishTime", "ids"};
		String[] values = new String[]{SqlUtil.toSqlString(Article.PUBLISH_STATE_PUBLISHING), 
									   SqlUtil.toSqlString(DateUtil.toString(new Date())), 
									   SqlUtil.toSqlString(articleId)};
		articleDao.updateByDefine("updateArticlePublishStateByIds", paramNames, values);
	 }

	public Article findArticleById(String id) {
		return articleDao.getAndNonClear(id);
		
	}

	public void modifyArticle(Article article) {
		articleDao.update(article);
	}

	public Pagination findArticlePagination(Pagination pagination,
			String columnId, boolean deleted, String siteId,
			boolean isUpSiteAdmin, String userId) {
		boolean columnDeleted = false;
		if (isUpSiteAdmin) {
			if (columnId == null) { // 查询全部
				String[] params = { "deleted", "siteId", "columnDeleted" };
				Object[] values = { deleted, siteId, columnDeleted };
				pagination = articleDao.getPagination(pagination, params,
						values);
			} else { // 查询某个目录
				String[] params = { "columnId", "deleted", "siteId" };
				Object[] values = { columnId, deleted, siteId };
				pagination = articleDao.getPagination(pagination, params,
						values);
			}
		} else {
			String columnIds = "";
			
			// 查询所有的文章(先查询所有栏目)
			if(columnId == null) {
				columnIds = this.getAllArticleColumnIds(siteId, columnId, userId, isUpSiteAdmin, columnIds);
				
			// 查询单个栏目下面的文章	
			}else{
				columnIds = columnId;
			}
			if(!StringUtil.isEmpty(columnIds)){
				columnIds = StringUtil.replaceFirst(columnIds, ",");
			}
			columnIds = SqlUtil.toSqlString(columnIds);
			String[] params = {"columnIds", "deleted", "siteId"};
			Object[] values = {columnIds, deleted, SqlUtil.toSqlString(siteId)};
			pagination = articleDao.getPaginationByIds(pagination, params, values);
		}
		List list = pagination.getData();
		for (int i = 0; i < list.size(); i++) {
			Object[] object = (Object[]) list.get(i);
			String str = (String) object[2];
			// 处理标题长度
			if (str.length() > 18) {
				String titleStr = str.substring(0, 18) + "...";
				object[2] = "<p title=\" " + str + " \">" + titleStr + "</p>";
				log.debug(object[2]);
			} else {
				object[2] = "<p title=\" " + str + " \">" + str + "</p>";
				log.debug(object[2]);
			}
		}
		return pagination;
	}

	/**
	 * 递归获取文章管理下所有的栏目
	 * @param siteId
	 * @param columnId
	 * @param userId
	 * @param isUpSiteAdmin
	 * @param columnIds
	 * @return
	 */
	private String getAllArticleColumnIds(String siteId, String columnId, String userId, boolean isUpSiteAdmin, String columnIds){
		List list = this.findArticleColumnTree(siteId, columnId, userId, isUpSiteAdmin);
		if(!CollectionUtil.isEmpty(list)){
			for(int i = 0; i < list.size(); i++){
				Object[] obj = new Object[6];
				obj = (Object[]) list.get(i);
				if(!StringUtil.isEmpty(columnIds)){
					columnIds += "," + String.valueOf(obj[0]); 
				}else{
					columnIds = String.valueOf(obj[0]);
				}
				columnIds = this.getAllArticleColumnIds(siteId, String.valueOf(obj[0]), userId, isUpSiteAdmin, columnIds);
			}
		}
		return columnIds;
	}
	
	/**
	 * 按照网站id或者栏目id查找文章管理栏目树(根据不同的角色来判断显示内容)
	 * @param siteId 	    网站id
	 * @param columnId 	    栏目id
	 * @param sessionId     当前登陆用户的id
	 * @return			    返回List
	 */
	@SuppressWarnings("unchecked")
	public List findArticleColumnTree(String siteId, String columnId, String sessionId, boolean isUpSiteAdmin) {
		List columnList = new ArrayList();
		//根据栏目类型，栏目ID，用户ID，查询权力表,显示选择的操作
		String chooseColumn = "";
		String strColumnId = "";
		if(columnId == null || columnId.equals("0")){
			strColumnId = "0";
			columnList = columnDao.findByNamedQuery("findFirstColumnBySystemAdmin","siteId",siteId);
		}else{
			strColumnId = columnId;
			String[] params1 = {"siteId", "columnId"};
			Object[] values1 ={siteId, strColumnId};
			columnList = columnDao.findByNamedQuery("findColumnBySiteIdAndColumnId", params1, values1);
		}
		//查询所有单独设置文章权限的栏目ID
		String rightStr[] = {"siteId","resourceType","operationTypes"};
		Object rightObj[] = {siteId,Resource.TYPE_COLUMN,Operation.TYPE_ARTICLE};
		List rightList = rightDao.findByNamedQuery("findRightBySiteIdAndResourceTypeAndOperationTypes",rightStr,rightObj);
		Right right = null;	
		String setColumnIds = "";
		StringBuffer columnids = new StringBuffer();
		for(int i = 0 ; i < rightList.size() ; i++){
			right = (Right)rightList.get(i);
			this.getParentColumnId(columnids, right.getAuthority().getResource().getIdentifier(), siteId);	 
		}
		setColumnIds = columnids.toString();
		setColumnIds = StringUtil.clearRepeat(setColumnIds);
		setColumnIds = StringUtil.replaceFirst(setColumnIds, ",");
		Column column = null;
		for(int i = 0 ; i < columnList.size() ; i++){
			column = (Column)columnList.get(i);
			if(isUpSiteAdmin){
				chooseColumn = chooseColumn + "," +SqlUtil.toSqlString(column.getId());
			}else{
				List list = this.getTreeCheckBoxChooseObject(column.getId(), sessionId, siteId, Operation.TYPE_ARTICLE);
				if(list != null && list.size() > 0){
					chooseColumn = chooseColumn + "," +SqlUtil.toSqlString(column.getId());
				}else if(StringUtil.contains(setColumnIds, column.getId())){
					if(chooseColumn != null && !chooseColumn.equals("")){
						chooseColumn = chooseColumn + "," +SqlUtil.toSqlString(column.getId());
					}else{
						chooseColumn = SqlUtil.toSqlString(column.getId());
					}
				}
			}
		}
		log.debug("chooseColumn===========111111111111===="+chooseColumn);
		chooseColumn = StringUtil.replaceFirst(chooseColumn, ",");
		//用户自己建的栏目集合
		List selfList = new ArrayList();
		if(!StringUtil.isEmpty(strColumnId) && !strColumnId.equals("0")) {
			String[] params = {"siteId", "creatorId", "columnId"};
			Object[] values = {siteId, sessionId, strColumnId};
			selfList = columnDao.findByNamedQuery("findSelfColumnByNormalUser", params, values);
		} else {
			String[] params = {"siteId", "creatorId"};
			Object[] values = {siteId, sessionId};
			selfList = columnDao.findByNamedQuery("findSelfColumnByNormalUserAndPidIsNull", params, values);
		}
		Object obj[] = null;
		for(int i = 0; i < selfList.size(); i++) {
			obj = (Object[])selfList.get(i);
			if(obj != null){
				chooseColumn = chooseColumn + "," + SqlUtil.toSqlString(String.valueOf(obj[0]));
			}			
		}
		chooseColumn = StringUtil.replaceFirst(chooseColumn, ",");
		chooseColumn =  StringUtil.clearRepeat(chooseColumn); 
		chooseColumn = StringUtil.replaceFirst(chooseColumn, ",");
		//用户拥有权限的栏目集合
		log.debug("chooseColumn==========222222222222====="+chooseColumn);
		List list1 = new ArrayList();
		if(chooseColumn != null && !chooseColumn.equals("")){
			list1 = columnDao.findByDefine("findColumnTreeByColumnIds", "columnIds", chooseColumn);
		}
		return list1; 
	}	
	
	/**
	 * 递归获取这个栏目的父栏目ID
	 * @param columnList 第一级栏目的集合
	 * @param columnids 栏目ID集合
	 * @return 返回所有的子栏目ID集合，以逗号隔开
	 */
	private void getParentColumnId(StringBuffer columnids, String columnId,String siteId){	
		String str[] = {"siteId","columnId"};
		Object obj[] = {siteId,columnId};
		List columnList = columnDao.findByNamedQuery("findColumnBySiteIdAndCurColumnId", str, obj);
		Column column = null;
		if (columnList != null && columnList.size() > 0) {		 
			column = (Column)columnList.get(0);
			if(column.getParent() != null ){
				String columnid = column.getParent().getId();
				columnids.append("," + columnid);
				// 递归
				this.getParentColumnId(columnids, columnid,siteId);							
			}else{
				columnids.append(",0");
			}
		}else{
			columnids.append(",0");
		}	
	}
	
	/**
	 * 获取treecheckbox标签所需要的已选择数组对象
	 * @param columnId 栏目ID
	 * @param userId  用户ID
	 * @param siteId 网站ID
	 * @param operationType 操作类型
	 * @param rootId 一个treecheckbox根节点的ID
	 * @param rootName 一个treecheckbox根节点的名称 
	 * @return List 组装好的LIST数据
	 */
	private List getTreeCheckBoxChooseObject(String columnId, String userId, String siteId,String operationType){
		Map map = new HashMap();
		Right right = null;
		Object tempObj[] = null;
		List tempList = new ArrayList();
		String str[] = {"itemType","itemId","userId","siteId","types"};
		Object obj[] = {Resource.TYPE_COLUMN,columnId,userId,siteId,operationType};
		List dataList = rightDao.findByNamedQuery("findRightByUserIdAndItemIdAndItemTypeAndSiteIdAndOperationType",str,obj);
		Object obj2[] = {Resource.TYPE_COLUMN,columnId,userId,siteId,Operation.TYPE_COLUMN_NONE};
		List nodeDataList = rightDao.findByNamedQuery("findRightByUserIdAndItemIdAndItemTypeAndSiteIdAndOperationType",str,obj2);
		Object allDataObj[] = {Resource.TYPE_COLUMN,columnId,userId,siteId,Operation.TYPE_COLUMN_NONE};
		List allDataList = rightDao.findByNamedQuery("findRightByUserIdAndItemIdAndItemTypeAndSiteIdAndOperationNoneType",str,allDataObj);

		StringBuffer columnids = new StringBuffer();
		//先把自己加进去
		columnids.append(columnId);
		this.getParentColumnId(columnids, columnId, siteId);
		String columnIds = columnids.toString();		
		if(StringUtil.contains(columnIds, ",0")){
			columnIds = columnIds.replace(",0", "");
			columnIds = columnIds + ",0";
		}
		columnIds = StringUtil.replaceFirst(columnIds,",");
		 
		String strColumnIds[] = columnIds.split(",");
		boolean columnExtends = false;
		//如果对此栏目单独设置了权限
		if(dataList != null && dataList.size() > 0){
			for(int i = 0 ; i < dataList.size() ; i++){
				right = (Right)dataList.get(i);			 
				if(!columnExtends){
					columnExtends = right.getAuthority().isColumnExtends();					
				}				
				tempList.add(right);
			}
		}else if(nodeDataList != null && nodeDataList.size() > 0){
			//如果对此栏目设置了无然后操作权限
			tempList = new ArrayList();
		}else if(allDataList == null || allDataList.size() == 0){
			//如果没有对此栏目单独设置了权限，则查询他的上级栏目有没有设置下级权限继承上级权限的
			List newDataList = null;
			
			for(int i = 0 ; ((i < strColumnIds.length) && (newDataList == null)) ; i++){
				String str1[] = {"itemType","itemId","userId","siteId","types"};
				Object obj1[] = {Resource.TYPE_COLUMN,strColumnIds[i],userId,siteId,operationType};
				List rightDataList = rightDao.findByNamedQuery("findRightByUserIdAndItemIdAndItemTypeAndSiteIdAndOperationType",str1,obj1);
				
				Object allDataObj1[] = {Resource.TYPE_COLUMN,strColumnIds[i],userId,siteId,Operation.TYPE_COLUMN_NONE};
				List allDataList1 = rightDao.findByNamedQuery("findRightByUserIdAndItemIdAndItemTypeAndSiteIdAndOperationNoneType",str1,allDataObj1);
 
				if(rightDataList != null && rightDataList.size() > 0 ){
					//如果对这个栏目单独设置了权限
					for(int j = 0 ; ((j < rightDataList.size()) && (newDataList == null)); j++){
						right = (Right)rightDataList.get(j);
						if((right.getAuthority() != null) && (right.getAuthority().getId() != null) && !(right.getAuthority().isColumnExtends())){
							newDataList = new ArrayList();
						}
						boolean setChild = right.getAuthority().isColumnExtends();
						if(setChild){
							newDataList = rightDataList;
						}
					}
				}else if(allDataList1 != null && allDataList1.size() > 0){
				/*	 
					//则没有对这个栏目设置权限，对文章或者模板设置设置了权限
					newDataList = allDataList1;
					
					*/
					
					//如果对这个栏目单独设置了权限
					for(int j = 0 ; ((j < allDataList1.size()) && (newDataList == null)); j++){
						right = (Right)allDataList1.get(j);
						if((right.getAuthority() != null) && (right.getAuthority().getId() != null) && !(right.getAuthority().isColumnExtends())){
							newDataList = new ArrayList();
						}
						boolean setChild = right.getAuthority().isColumnExtends();
						if(setChild){
							//则没有对这个栏目设置权限，对文章或者模板设置设置了权限
							newDataList = allDataList1;
						}
					}
				}
				
				Object obj3[] = {Resource.TYPE_COLUMN,strColumnIds[i],userId,siteId,Operation.TYPE_COLUMN_NONE};
				List nodeRightDataList = rightDao.findByNamedQuery("findRightByUserIdAndItemIdAndItemTypeAndSiteIdAndOperationType",str1,obj3);
				if(newDataList == null && nodeRightDataList != null && nodeRightDataList.size() > 0){
					newDataList = new ArrayList();
				}
			}
			if(newDataList != null){
				for(int i = 0 ; i < newDataList.size() ; i++){
					right = (Right)newDataList.get(i);
				 
					tempList.add(right);
				}
			} 
		}
		return tempList;
	}

	/**
	 * 清除回收站里面的文章
	 * @param ids
	 * @param siteId
	 * @param sessionID
	 * @return
	 */
	public int clearArticleByIds(String ids, String siteId, String sessionID) {
		String refIds = "";
		String[] arr = ids.split(",");
		Article article1 = null;
		String firstDeletedIds = "";
		String lastDeletedIds = "";
		for(int i = 0; i < arr.length; i++ ){
			article1 = articleDao.getAndClear(arr[i]);
			if(article1.isRef()){
				firstDeletedIds += "," + article1.getId(); 
			}else{
				refIds = this.getRefArticleIds(article1.getId());
				lastDeletedIds += "," + article1.getId(); 
				if(!StringUtil.isEmpty(refIds)){
					firstDeletedIds += "," + refIds; 
				}
			}
			
			// 写入日志
			String categoryName = "文章管理（回收站）->删除";
			String param = article1.getTitle();
			systemLogDao.addLogData(categoryName, siteId, sessionID, param);
		}
		if(!StringUtil.isEmpty(firstDeletedIds)){
			firstDeletedIds = StringUtil.replaceFirst(firstDeletedIds, ",");
		}
		if(!StringUtil.isEmpty(lastDeletedIds)){
			lastDeletedIds = StringUtil.replaceFirst(lastDeletedIds, ",");
		}
		//删除文章的附件
		articleDao.updateByDefine("deleteArticleAttachByArticleId", "articleId", SqlUtil.toSqlString(firstDeletedIds));
		articleDao.updateByDefine("deleteArticleAttachByArticleId", "articleId", SqlUtil.toSqlString(lastDeletedIds));
		
		// 1.删除引用 的文章
		articleDao.deleteByIds(SqlUtil.toSqlString(firstDeletedIds));
		// 2.删除被引用的文章
		return articleDao.deleteByIds(SqlUtil.toSqlString(lastDeletedIds));
	}

	/***
	 * 删除文章
	 * 
	 * @param ids
	 * @param siteId
	 * @param sessionID
	 * @return
	 */
	public int deleteArticleByIds(String ids, String siteId, String sessionID) {
		String refIds = "";
		refIds = this.getRefArticleIds(ids);
		if (!StringUtil.isEmpty(refIds)) {
			ids = refIds + "," + ids;
		}
		// 写入日志
		String[] str = ids.split(",");
		Article article = null;
		for (int i = 0; i < str.length; i++) {
			article = articleDao.getAndClear(str[i]);
			String categoryName = "文章管理->删除";
			String param = article.getTitle();
			systemLogDao.addLogData(categoryName, siteId, sessionID, param);
		}
		Site site = siteDao.getAndClear(siteId);
		String sitePublishDir = site.getPublishDir();
		String articleUrl = article.getUrl();
		articleUrl = sitePublishDir + articleUrl.replaceFirst("/release/site"+siteId+"/build/static", "");
		if(site.getPublishWay().equals("local")){
			if(FileUtil.isExist(articleUrl)){
				FileUtil.delete(articleUrl);
			}
		}
		if(site.getPublishWay().equals("ftp")){
			ArrayList fileList = new ArrayList<String>();
			fileList.add(articleUrl);
			new FtpSender(site.getRemoteIP(), site.getRemotePort(),site.getFtpUserName(),site.getFtpPassWord(),site.getFtpFilePath(), fileList, GlobalConfig.appRealPath,"delete").start();
		}
		
		// 删除生成列表数据
		String newIds = SqlUtil.toSqlString(ids);
		String[] params = { "ids", "deleted" };
		String[] values = { newIds, "1" };
		articleBuildListDao.deleteByIds(newIds);
		articlePublishListDao.deleteByIds(newIds);
		return articleDao.updateByDefine("updateArticleDeletedByIds", params,
				values);
	}

	public List<ArticleAttribute> findAttributesByFormatId(String formatId) {
		List list = articleDao.findByNamedQuery("findAttributesByFormatId",
				"formatId", formatId);
		return list;
	}

	/**
	 * 获得引用文章id
	 * 
	 * @param referedIds
	 * @return
	 */
	private String getRefArticleIds(String referedIds) {
		String[] str = referedIds.split(",");
		String ids = "";
		if (!StringUtil.isEmpty(str[0])) {
			for (int i = 0; i < str.length; i++) {
				List list = articleDao.findByNamedQuery(
						"findArticleByReferedArticleId", "refId", str[i]);
				if (list != null && list.size() > 0) {
					for (int j = 0; j < list.size(); j++) {
						Article article = (Article) list.get(j);
						ids += "," + article.getId();
					}
				}
			}
		}
		if(!StringUtil.isEmpty(ids)){
			ids = ids.replaceFirst(",", "");
		}
		return ids;
	}

	/**
	 * 修改文章和引用文章
	 * 
	 * @param article
	 */
	public Map<String, String> modifyArticleAndRefArticle(Article article, String siteId,
			String sessionID) {
		// 定时发布的文章
		String timeSettingArticleIds = "";
		// 定时发布的文章的时间
		String timeSetting = "";
		// 发布方式，定时时间
		Map<String, String> map = new HashMap<String, String>();
		
		
		String articleId = article.getId();
		String Id = "f017";
		List<GeneralSystemSet> list1 = findGeneralSystemSetLink(Id);
		Article newArticle = articleDao.getAndClear(articleId);
		String url = article.getUrl();
		if (url == null || url.equals("")) {
			url = newArticle.getInitUrl();
			article.setUrl(url);
		}
		/** 需要修改的内容 */
		String articleTextAreal = article.getTextArea();
		for (int i = 0; i < list1.size(); i++) {
			/** <a href='http://www.qq.com'>中国</a> */
			GeneralSystemSet generalSystemSet = (GeneralSystemSet) list1.get(i);
			String setContent = generalSystemSet.getSetContent();
			String urls = generalSystemSet.getUrl();
			String replace = "<a href='" + urls + "'>" + setContent + "</a>";
			/** 不断的给articleTextAreal替换内容 */
			articleTextAreal = StringUtil.replace(articleTextAreal, setContent,	replace);
		}
		
		/** 网站 id */
		String siteid = article.getSite().getId();
		/** 判断过滤 */
		/** 判断父栏目是否存在，如果不存在就是主栏目 */
		if (article.isKeyFilter()) {
			/** 系统管理员过滤内容 */
			List SiteIdCount = siteDao.findByNamedQuery("findSiteParentId");
			/** 系统管理员过滤主网站 */
			if (SiteIdCount.size() < 1) {
				articleTextAreal = replaceAllTextArearBySystemAuthor(articleTextAreal);
				article.setTextArea(articleTextAreal);
			} else {
				articleTextAreal = replaceAllTextArear(articleTextAreal, siteId);
				article.setTextArea(articleTextAreal);
			}
		}else{
			/**包坤涛添加*/
			article.setTextArea(articleTextAreal);
		}
		// 如果是审核并保存
		if(article.isAudited() && !article.getPublishState().equals(article.PUBLISH_STATE_DRAFT)){
			// 根据栏目的发布方式来发布文章
			Column column = columnDao.getAndClear(article.getColumn().getId());
			String publishWay = column.getPublishWay();
			// 自动发布,如果栏目设置为已发布，文章也是已审核状态则将文章添加到发布列表中，状态改为发布中
			if(publishWay.equals(Column.PUBLISH_WAY_AUTO)){
				// 写入生成列表
				ArticleBuildList articleBuildList = new ArticleBuildList();
			/*	publisher.initCategoryMap();
				//将文章相关的栏目页重新发布
				publisher.buildColumnByColumnId(article.getColumn().getId(), siteId);
				//首页重新发布
				publisher.buildArticleByArticleId(article.getId(), siteId);*/
				BeanUtil.copyProperties(articleBuildList, article);
				if (articleBuildListDao.getAndClear(articleBuildList.getId()) == null) {
					articleBuildList.setStatus("true");
					articleBuildListDao.saveAndClear(articleBuildList);
				} else {
					articleBuildList.setStatus("true");
					articleBuildListDao.updateAndClear(articleBuildList);
				}
			
				article.setPublishState(Article.PUBLISH_STATE_PUBLISHING);
			
			// 定时发布	
			}else if(publishWay.equals(Column.PUBLISH_WAY_TIME)){
				article.setPublishState(Article.PUBLISH_STATE_UNPUBLISHEED);
				timeSettingArticleIds = article.getId();
				timeSetting = String.valueOf(column.getTimeSetting());
			}else{
				article.setPublishState(Article.PUBLISH_STATE_UNPUBLISHEED);
			}
			
		}else{
			// 修改文章时同时也将文章的审核和发布状态修改 
			article.setAudited(false);
			article.setPublishState(Article.PUBLISH_STATE_UNPUBLISHEED);
		}
		articleDao.updateAndClear(article);
		
		// 修改关联文章
		List list = articleDao.findByNamedQuery("findArticleByReferedArticleId", "refId", articleId);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Article refArticle = (Article) list.get(i);
				String refSiteId = refArticle.getSite().getId();
				String id = refArticle.getId();
				int order = refArticle.getOrders();
				boolean ref = refArticle.isRef();
				Column column = refArticle.getColumn();
				Article article1 = refArticle.getReferedArticle();
				BeanUtil.copyProperties(refArticle, article);
				refArticle.setId(id);
				refArticle.setOrders(order);
				refArticle.setRef(ref);
				refArticle.setReferedArticle(article1);
				refArticle.setColumn(column);
				refArticle.setUrl(url);
				Site refSite = new Site();
				refSite.setId(refSiteId);
				refArticle.setSite(refSite);
				refArticle.setAudited(false);
				refArticle.setPublishState(Article.PUBLISH_STATE_UNPUBLISHEED);
				articleDao.updateAndClear(refArticle);
			}
		}
		map.put("timeSettingArticleIds", timeSettingArticleIds);
		map.put("timeSetting", timeSetting);
		// 写入日志
		String categoryName = "文章管理->修改";
		String param = article.getTitle();
		systemLogDao.addLogData(categoryName, siteId, sessionID, param);
		return map;
	}

	/**
	 * 封装Article对象字段 其中的字段名与字段值要严格对应
	 * 
	 * @param fieldNames
	 *            字段名
	 * @param fieldValues
	 *            字段值
	 * @return Article对象
	 */
	@SuppressWarnings("unused")
	private Article encapsulateField(String[] fieldNames, List<?> fieldValues) {
		Class<Article> cls = Article.class;
		Article article = new Article();
		try {
			for (int i = 0; i < fieldNames.length; i++) {
				String firstLetter = fieldNames[i].substring(0, 1).toUpperCase();
				Class<?> type = cls.getDeclaredField(fieldNames[i]).getType();
				Method setMethod = cls.getDeclaredMethod("set" + firstLetter
						+ fieldNames[i].substring(1), type);
				setMethod.invoke(article, fieldValues.get(i));
			}
		} catch (Exception e) {
			log.debug("encapsulateField error:", e);
		}
		return article;
	}

	public int revertArticleByIds(String ids, String siteId, String sessionID) {
		String newIds = this.getRefArticleIds(ids);
		if (!StringUtil.isEmpty(newIds)) {
			ids += "," + newIds;
		}
		String[] str = ids.split(",");
		// 写入生成列表
		for (int i = 0; i < str.length; i++) {
			Article article = new Article();
			article = articleDao.getAndClear(str[i]);
	/*		
			ArticleBuildList articleBuildList = new ArticleBuildList();
			BeanUtil.copyProperties(articleBuildList, article);
			articleBuildListDao.save(articleBuildList);*/

			// 写入日志
			String categoryName = "文章管理->还原";
			String param = article.getTitle();
			systemLogDao.addLogData(categoryName, siteId, sessionID, param);
			articleDao.clearCache();
		}
		ids = SqlUtil.toSqlString(ids);
		String[] params = { "ids", "deleted" };
		String[] values = { ids, "0" };
		return articleDao.updateByDefine("updateArticleDeletedByIds", params,
				values);
	}

	@SuppressWarnings("unchecked")
	public void publish(String articleIds) {
		List<Article> articles = articleDao.findByDefine("findArticlesByArticleIds", "articleIds", SqlUtil.toSqlString(articleIds));
		ArticleBuildList articleBuildList = null;
		// 写入生成列表
		for (Article article : articles) {
			// 审核之后的文章才可以发布
			if(article.isAudited()&& !article.getPublishState().equals(article.PUBLISH_STATE_DRAFT)){
				articleBuildList = new ArticleBuildList();
			/*	publisher.initCategoryMap();
				//将文章相关的栏目页重新发布
				publisher.buildColumnByColumnId(article.getColumn().getId(), article.getSite().getId());
				//首页重新发布
				publisher.buildArticleByArticleId(article.getId(), article.getSite().getId());
*/
				BeanUtil.copyProperties(articleBuildList, article);
				if (articleBuildListDao.getAndClear(articleBuildList.getId()) == null) {
					articleBuildList.setStatus("true");
					articleBuildListDao.saveAndClear(articleBuildList);
				} else {
					articleBuildList.setStatus("true");
					articleBuildListDao.updateAndClear(articleBuildList);
				}
				article.setPublishState(Article.PUBLISH_STATE_PUBLISHING);
				articleDao.updateAndClear(article);
			}
		}
	}

	public List<ArticleFormat> findAllFormats() {
		return articleFormatDao.findAll();
	}

	/**
	 * 审核文章
	 * 
	 * @param ids
	 * @param siteId
	 * @param auditorId
	 * @return
	 */
	public Map<String, String> auditArticleByIds(String ids, String siteId, String auditorId) {
		// 定时发布的文章
		String timeSettingArticleIds = "";
		// 定时发布的文章的时间
		String timeSetting = "";
		// 发布方式，定时时间
		Map<String, String> map = new HashMap<String, String>();
		
		String[] str = ids.split(",");
		Article article = null;
		String columnId = "";
		for (int i = 0; i < str.length; i++) {
			article = articleDao.getAndNonClear(str[i]);
			// 写入日志
			String categoryName = "文章管理->审核";
			String param = article.getTitle();
			if(columnId == null || columnId.equals("")){
				columnId = article.getColumn().getId();
			}		
			systemLogDao.addLogData(categoryName, siteId, auditorId, param);
		}
		//发布状态
		String publicState = "";
		if(columnId != null && !columnId.equals("")){
			// 根据栏目的发布方式来发布文章
			Column column = columnDao.getAndClear(columnId);
			String publishWay = column.getPublishWay();
			// 自动发布,如果栏目设置为已发布，文章也是已审核状态则将文章添加到发布列表中，状态改为发布中
			if(publishWay.equals(Column.PUBLISH_WAY_AUTO)){
				for (int i = 0; i < str.length; i++) {
					article = articleDao.getAndNonClear(str[i]);
					if(article.isAudited() && !article.getPublishState().equals(article.PUBLISH_STATE_DRAFT) ){
						// 写入生成列表
						ArticleBuildList articleBuildList = new ArticleBuildList();
						BeanUtil.copyProperties(articleBuildList, article);
						/*publisher.initCategoryMap();
						//将文章相关的栏目页重新发布
						publisher.buildColumnByColumnId(article.getColumn().getId(), siteId);
						//首页重新发布
						publisher.buildArticleByArticleId(article.getId(), siteId);
	*/					if (articleBuildListDao.getAndClear(articleBuildList.getId()) == null) {
							articleBuildList.setStatus("true");
							articleBuildListDao.saveAndClear(articleBuildList);
						} else {
							articleBuildList.setStatus("true");
							articleBuildListDao.updateAndClear(articleBuildList);
						}
					}
					
			
				}
				publicState = Article.PUBLISH_STATE_PUBLISHING;
			}else{
				publicState = Article.PUBLISH_STATE_UNPUBLISHEED;
			}
			String[] params = {"ids", "audited", "auditorId", "auditTime", "publishState"};
			String[] values = {SqlUtil.toSqlString(ids), "1", SqlUtil.toSqlString(auditorId), 
					SqlUtil.toSqlString(DateUtil.toString(new Date())), SqlUtil.toSqlString(publicState)};
			articleDao.updateByDefine("updateArticleAuditedByIds", params, values);
			
			// 如果栏目为定时发布
			if(publishWay.equals(Column.PUBLISH_WAY_TIME)){
				timeSettingArticleIds = ids;
				for(int i = 0; i < str.length; i++) {
					if(!StringUtil.isEmpty(timeSetting)){
						timeSetting = String.valueOf(column.getTimeSetting());
					}else{
						timeSetting +=  "," + String.valueOf(column.getTimeSetting());
					}
				}
			}
		}
		map.put("timeSettingArticleIds", timeSettingArticleIds);
		map.put("timeSetting", timeSetting);
		return map;
	}

	/**
	 * 呈送文章
	 * 
	 * @param columnIds
	 * @param presentArticleIds
	 * @param presentMethod
	 * @param articleService
	 * @param siteId
	 * @param sessionID
	 */
	public Map<String, String> presentArticle(String columnIds, String presentArticleIds,
			int presentMethod, ArticleService articleService, String siteId,
			String sessionID) {
		// 定时发布的文章
		String timeSettingArticleIds = "";
		// 定时发布的文章的时间
		String timeSetting = "";
		// 发布方式，定时时间
		Map<String, String> map = new HashMap<String, String>();
		
		
		String[] cIds = columnIds.split(",");
		String[] artIds = presentArticleIds.split(",");
		String infoMessage = "";

		// 呈送实体
		if (presentMethod == 1) {
			for (int i = 0; i < cIds.length; i++) {
				for (int j = 0; j < artIds.length; j++) {
					Article destArticle = new Article();
					Article orgArticle = articleDao.getAndClear(artIds[j]);
					BeanUtil.copyProperties(destArticle, orgArticle);
					destArticle.setId(null);
					Column destColumn = new Column();
					destColumn.setId(cIds[i]);
					destArticle.setColumn(destColumn);
					Map<String, String> newMap = articleService.addPresentArticle(destArticle);
					String newTimeSettingArticleIds = newMap.get("timeSettingArticleIds");
					String newTimeSetting = newMap.get("timeSetting");
					if(!StringUtil.isEmpty(newTimeSettingArticleIds)){
						if(!StringUtil.isEmpty(timeSettingArticleIds)){
							timeSettingArticleIds += "," + newTimeSettingArticleIds;
							timeSetting += "," + newTimeSetting;
						}else{
							timeSettingArticleIds = newTimeSettingArticleIds;
							timeSetting = newTimeSetting;
						}
					}
					// 写入日志
					String categoryName = "文章管理->呈送";
					infoMessage = "呈送实体";
					String param = infoMessage + "[" + orgArticle.getTitle() + "]";
					systemLogDao.addLogData(categoryName, siteId, sessionID, param);
				}
			}

		// 呈送链接
		} else if (presentMethod == 0) {
			for (int i = 0; i < cIds.length; i++) {
				for (int j = 0; j < artIds.length; j++) {
					Article destArticle = new Article();
					Article orgArticle = articleDao.getAndClear(artIds[j]);
					BeanUtil.copyProperties(destArticle, orgArticle);
					destArticle.setId(null);
					Column column = new Column();
					column.setId(cIds[i]);
					destArticle.setColumn(column);
					Article referedArticle = new Article();
					referedArticle.setId(orgArticle.getId());
					destArticle.setReferedArticle(referedArticle);
					destArticle.setRef(true);
					Map<String, String> newMap = articleService.addPresentArticle(destArticle);
					String newTimeSettingArticleIds = newMap.get("timeSettingArticleIds");
					String newTimeSetting = newMap.get("timeSetting");
					if(!StringUtil.isEmpty(newTimeSettingArticleIds)){
						if(!StringUtil.isEmpty(timeSettingArticleIds)){
							timeSettingArticleIds += "," + newTimeSettingArticleIds;
							timeSetting += "," + newTimeSetting;
						}else{
							timeSettingArticleIds = newTimeSettingArticleIds;
							timeSetting = newTimeSetting;
						}
					}
					// 写入日志
					String categoryName = "文章管理->呈送";
					infoMessage = "呈送链接";
					String param = infoMessage + "[" + orgArticle.getTitle() + "]";
					systemLogDao.addLogData(categoryName, siteId, sessionID, param);
				}
			}
		}
		map.put("timeSettingArticleIds", timeSettingArticleIds);
		map.put("timeSetting", timeSetting);
		return map;
	}

	/**
	 * 文章呈送时调用的添加文章方法
	 * 
	 * @param article
	 */
	public Map<String, String> addPresentArticle(Article article) {
		String timeSettingArticleIds = "";
		String timeSetting = "";
		Map<String, String> map = new HashMap<String, String>();
		int maxArticle = 0;
		// 设置文章的order
		maxArticle = this.findMaxArticleOrder();
		article.setOrders(maxArticle + 1);
		String columnId = article.getColumn().getId();
		
		// 根据栏目的发布方式来发布文章
		Column column = columnDao.getAndClear(columnId);
		String publishWay = column.getPublishWay();
		// 先判断栏目是否审核
		if(column.isAudited()){
			// 自动发布,如果栏目设置为已发布，文章也是已审核状态则将文章添加到发布列表中，状态改为发布中
			if(publishWay.equals(Column.PUBLISH_WAY_AUTO)){
				article.setPublishState(Article.PUBLISH_STATE_PUBLISHING);
				article.setAuditTime(new Date());
				article.setAuditor(article.getCreator());
				article.setAudited(true);
			// 定时发布 、 手动发布	
			}else{
				// 定时发布(在文章审核通过的情况下)
				if(publishWay.equals(Column.PUBLISH_WAY_TIME)){
					article.setAudited(true);
					article.setAuditTime(new Date());
					article.setAuditor(article.getCreator());
				}else{
					article.setAudited(false);
				}
				article.setPublishState(Article.PUBLISH_STATE_UNPUBLISHEED);
			}
		}else{
			article.setAudited(false);
			article.setPublishState(Article.PUBLISH_STATE_UNPUBLISHEED);
		}
		if(article.getDisplayTime() == null){
			article.setDisplayTime(article.getCreateTime());
		}
		articleDao.saveAndClear(article);

		// 建立文章时自动生成一个url（修改的时候不允许修改 ->.html）
		String articleId = article.getId();
		String siteId = article.getSite().getId();
		String url = SiteResource.getBuildStaticDir(siteId, true) + "/col_"
				+ columnId + "/" + DateUtil.getCurrDate().replaceAll("-", "/")
				+ "/art_" + articleId + ".html";
		article.setUrl(url);
		articleDao.updateAndClear(article);
		
		
		// 先判断栏目是否审核
		if(column.isAudited()){
			// 自动发布,如果栏目设置为已发布，文章也是已审核状态则将文章添加到发布列表中，状态改为发布中
			if(publishWay.equals(Column.PUBLISH_WAY_AUTO)){
				// 写入生成列表
				ArticleBuildList articleBuildList = new ArticleBuildList();
				BeanUtil.copyProperties(articleBuildList, article);
			/*	publisher.initCategoryMap();
				//将文章相关的栏目页重新发布
				publisher.buildColumnByColumnId(article.getColumn().getId(), siteId);
				//首页重新发布
				publisher.buildArticleByArticleId(articleId, siteId);
*/				if (articleBuildListDao.getAndClear(articleBuildList.getId()) == null) {
					articleBuildList.setStatus("true");
					articleBuildListDao.saveAndClear(articleBuildList);
				} else {
					articleBuildList.setStatus("true");
					articleBuildListDao.updateAndClear(articleBuildList);
				}
				
			// 定时发布 、 手动发布	
			}else{
				// 定时发布(在文章审核通过的情况下)
				if(publishWay.equals(Column.PUBLISH_WAY_TIME)){
					timeSettingArticleIds = article.getId();
					timeSetting = String.valueOf(column.getTimeSetting());
				}
			}
		}
		
		// 写入日志
		String categoryName = "文章管理->添加";
		String userId = article.getCreator().getId();
		String param = article.getTitle();
		systemLogDao.addLogData(categoryName, siteId, userId, param);

		map.put("timeSettingArticleIds", timeSettingArticleIds);
		map.put("timeSetting", timeSetting);
		return map;
	}

	/**
	 * 获取文章中order字段的最大值
	 * 
	 * @return
	 */
	public int findMaxArticleOrder() {
		int maxOrders = 0;
		List list = articleDao.findByNamedQueryAndClear("findMaxArticleOrders");
		if (list != null && list.size() > 0) {
			Article article = (Article) list.get(0);
			maxOrders = article.getOrders();
		}
		return maxOrders;
	}

	/**
	 * 转移文章
	 * 
	 * @param moveArticleIds
	 * @param columnId
	 * @param articleService
	 * @param siteId
	 * @param sessionID
	 */
	public Map<String, String> moveArticle(String moveArticleIds, String columnId,
			ArticleService articleService, String siteId, String sessionID) {
		String timeSettingArticleIds = "";
		String timeSetting = "";
		Map<String, String> map = new HashMap<String, String>();
		
		String[] moveIds = moveArticleIds.split(",");
		Column column = columnDao.getAndClear(columnId);
		for (String moveId : moveIds) {
			Article article = articleDao.getAndClear(moveId);
			article.setColumn(column);
			if(column.isAudited()){
				String publishWay = column.getPublishWay();
				if(publishWay.equals(Column.PUBLISH_WAY_AUTO)){
					// 写入生成列表
					ArticleBuildList articleBuildList = new ArticleBuildList();
					BeanUtil.copyProperties(articleBuildList, article);
			/*		publisher.initCategoryMap();
					//将文章相关的栏目页重新发布
					publisher.buildColumnByColumnId(article.getColumn().getId(), siteId);
					//首页重新发布
					publisher.buildArticleByArticleId(article.getId(), siteId);
*/					if (articleBuildListDao.getAndClear(articleBuildList.getId()) == null) {
						articleBuildList.setStatus("true");
						articleBuildListDao.saveAndClear(articleBuildList);
					} else {
						articleBuildList.setStatus("true");
						articleBuildListDao.updateAndClear(articleBuildList);
					}
					
					article.setAudited(true);
					article.setAuditTime(new Date());
					User auditor = new User();
					auditor.setId(sessionID);
					article.setAuditor(auditor);
					article.setPublishState(Article.PUBLISH_STATE_PUBLISHING);
				}else if(publishWay.equals(Column.PUBLISH_WAY_TIME)){
					article.setAudited(true);
					article.setAuditTime(new Date());
					User auditor = new User();
					auditor.setId(sessionID);
					article.setAuditor(auditor);
					article.setPublishState(Article.PUBLISH_STATE_UNPUBLISHEED);
					if(StringUtil.isEmpty(timeSettingArticleIds)){
						timeSettingArticleIds = article.getId();
						timeSetting = String.valueOf(column.getTimeSetting());
					}else{
						timeSettingArticleIds = "," + article.getId();
						timeSetting = "," + String.valueOf(column.getTimeSetting());
					}
				}else{
					article.setAudited(false);
					article.setPublishState(Article.PUBLISH_STATE_UNPUBLISHEED);
				}
			}
			articleService.modifyArticle(article);
			// 写入日志
			String categoryName = "文章管理->转移";
			String param = article.getTitle();
			systemLogDao.addLogData(categoryName, siteId, sessionID, param);
		}
		
		map.put("timeSettingArticleIds", timeSettingArticleIds);
		map.put("timeSetting", timeSetting);
		return map;
	}

	/**
	 * 查找排序文章
	 * 
	 * @param columnId
	 * @param siteId
	 */
	public List<Article> findSortArticle(ArticleForm form, String columnId,
			String siteId, boolean isUpSiteAdmin, String creatorId) {
		int start = form.getFromCount();
		int end = form.getToCount();
		if (start == 0 && end == 0) {
			start = 0;
			end = 50;
			form.setFromCount(start);
			form.setToCount(end);
		}
		boolean deleted = false;
		List<Article> list = new ArrayList<Article>();
		if (isUpSiteAdmin) {
			String[] param = { "columnId", "siteId", "deleted" };
			Object[] value = { columnId, siteId, deleted };
			list = articleDao.findByNamedQuery("findSortArticle", param, value,
					start, end);
		} else {
			String[] param = { "columnId", "siteId", "deleted", "creatorId" };
			Object[] value = { columnId, siteId, deleted, creatorId };
			list = articleDao.findByNamedQuery("findSortArticleByNormalUser",
					param, value, start, end);
		}
		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

	/**
	 * 排序文章
	 * 
	 * @param sortArticleIds
	 * @param articleService
	 * @param siteId
	 * @param sessionID
	 */
	public void sortArticle(String sortArticleIds,
			ArticleService articleService, String siteId, String sessionID) {
		log.debug("文章排序");
		String[] orders = sortArticleIds.split(",");
		int[] ordersArray = new int[orders.length];
		// 获取文章的顺序
		for (int i = 0; i < orders.length; i++) {
			String articleId = orders[i];
			Article article = articleDao.getAndClear(articleId);
			ordersArray[i] = article.getOrders();
		}
		// 对orders值进行从排序
		for (int i = 0; i < ordersArray.length; i++) {
			for (int j = 0; j < ordersArray.length - i - 1; j++) {
				if (ordersArray[j] < ordersArray[j + 1]) {
					int temp = ordersArray[j];
					ordersArray[j] = ordersArray[j + 1];
					ordersArray[j + 1] = temp;
				}
			}
		}
		Article article = null;
		// 修改排序后的orders值
		for (int i = 0; i < orders.length; i++) {
			String articleId = orders[i];
			article = articleDao.getAndClear(articleId);
			article.setOrders(ordersArray[i]);
			articleService.modifyArticle(article);
		}

		// 写入日志
		String categoryName = "文章管理->排序";
		String param = "";
		systemLogDao.addLogData(categoryName, siteId, sessionID, param);
	}

	/**
	 * 按照格式id查询格式
	 * 
	 * @param fromatId
	 * @return
	 */
	public ArticleFormat findFormatByFormatId(String fromatId) {
		return articleFormatDao.getAndClear(fromatId);
	}

	/**
	 * 预览文章
	 * 
	 * @param articleId
	 * @param columnId
	 * @param siteId
	 * @param sessionID
	 * @return
	 */
	public String getPreviewPage(String articleId, String columnId,
			String siteId, String sessionID) {
		if (StringUtil.isEmpty(columnId)) {
			columnId = articleDao.getAndNonClear(articleId).getId();
		}

		// 写入日志
		Article article = articleDao.getAndClear(articleId);
		String categoryName = "文章管理->预览";
		String param = article.getTitle();
		systemLogDao.addLogData(categoryName, siteId, sessionID, param);

		Column column = columnDao.getAndNonClear(columnId);
		TemplateInstance templateInstance = column.getArticleTemplate();
		String templateInstanceId = null;

		// 栏目指定的文章页模版不存在
		if (templateInstance == null) {
			Site site = siteDao.getAndNonClear(siteId);
			templateInstance = site.getArticleTemplate();

			// 默认模版页不存在
			if (templateInstance == null) {
				String prompt = getErrorInfo("模板未设");
				String previewFile = SiteResource.getPreviewDir(siteId, false)
						+ "/" + "prompt.html";
				FileUtil.write(previewFile, prompt);
				previewFile = previewFile.substring(GlobalConfig.appRealPath
						.length());
				return previewFile;
			} else {
				templateInstanceId = templateInstance.getId();
			}
		} else {
			templateInstanceId = templateInstance.getId();
		}

		return publisher.previewPage(templateInstanceId, articleId, columnId,
				siteId);
	}

	public void setArticleDao(ArticleDao articleDao) {
		this.articleDao = articleDao;
	}

	/**
	 * 获取当前文章所在栏目id
	 * 
	 * @param articleId
	 * @return
	 */
	public String getColumnIdByArticleId(String articleId) {
		String colId = articleDao.getAndClear(articleId).getColumn().getId();
		return colId;
	}

	/**
	 * 获取当前文章所在栏目的格式名字
	 * 
	 * @param columnId
	 * @return
	 */
	public String findpresentFormatNameByColumnId(String columnId) {
		Column column = columnDao.getAndClear(columnId);
		String presentFormatId = column.getArticleFormat().getId();
		String presentFormatName = articleFormatDao
				.getAndClear(presentFormatId).getName();
		return presentFormatName;
	}

	/**
	 * 获取当前文章所在栏目的格式id
	 * 
	 * @param columnId
	 *            栏目id
	 * @return
	 */
	public String findpresentFormatIdByColumnId(String columnId) {
		Column column = columnDao.getAndClear(columnId);
		String presentFormatId = column.getArticleFormat().getId();
		return presentFormatId;
	}

	/**
	 * 文章置顶
	 * 
	 * @param articleId
	 * @param orders
	 * @param isToped
	 * @param siteId
	 * @param userId
	 */
	public void modifyArticleTop(String articleId, int orders, String isToped,
			String siteId, String userId) {
		String categoryName = "";

		Article article = articleDao.getAndClear(articleId);
		if (isToped.equals("0")) {
			article.setToped(false);
			categoryName = "文章管理->取消置顶";
		} else {
			article.setToped(true);
			article.setOrders(orders + 1);
			categoryName = "文章管理->置顶";
		}
		articleDao.update(article);

		// 写入日志
		systemLogDao.addLogData(categoryName, siteId, userId, article
				.getTitle());

	}

	/**
	 * 查找枚举信息字符串,形式为：id1,name1,#value11,value12,value13::id2,name2#value21,
	 * value22...
	 * 
	 * @return
	 */
	public String findEnumInfoInArticleService() {
		String enumInfoStr = "";
		String valueStr = "";
		List list = enumerationDao.findAll();
		for (int i = 0; i < list.size(); i++) {
			valueStr = "";
			Enumeration enumeration = (Enumeration) list.get(i);
			List valuesList = enumeration.getEnumValues();
			if(!CollectionUtil.isEmpty(valuesList)) {
				for (int j = 0; j < valuesList.size(); j++) {
					valueStr += valuesList.get(j) + ",";
				}
				enumInfoStr += enumeration.getId() + "," + valueStr + "::";
			}
		}
		return enumInfoStr;
	}

	/**
	 * 查找格式属性枚举类别Id
	 * 
	 * @param formatId
	 * @return
	 */
	public String findEnumerationIdByFormatId(String formatId) {
		List list = articleAttributeDao.findByNamedQuery(
				"findEnumerationIdByFormatId", "formatId", formatId);
		String enumerationId = "";
		String attributeId = "";
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			for (int j = 0; j < obj.length; j++) {
				if (obj[1].equals("enumeration")) {
					attributeId = (String) obj[0];
				}
			}
			if (attributeId != "" && attributeId != null) {
				ArticleAttribute articleAttribute = articleAttributeDao
						.getAndClear(attributeId);
				if (articleAttribute != null) {
					enumerationId += articleAttribute.getFieldName() + ","
							+ articleAttribute.getEnumeration().getId() + "#";
				}
			}
		}
		return enumerationId;
	}

	/**
	 * 导出文章
	 * 
	 * @param exportArticleIds
	 *            导出的文章ids
	 * @param path
	 *            导出文章的路径
	 * @return message
	 */
	@SuppressWarnings("unchecked")
	public String exportArticles(String exportArticleIds, String path) {
		// 创建根节点
		Element root = new Element("list");
		// 根节点添加到文档中
		Document document = new Document(root);

		exportArticleIds = SqlUtil.toSqlString(exportArticleIds);
		// 根据id查询要导出的文章
		List<Article> list = articleDao.findByDefine("findExportArticlesByIds", "ids", exportArticleIds);
		for (int i = 0; i < list.size(); i++) {
			Article article = list.get(i);
			Element elements = new Element("article");
			// 给article 节点添加id 属性
			elements.setAttribute("id", "" + (i + 1));
			// 给 article 节点添加子节点并赋值；
			elements.addContent(new Element("title").setText(article.getTitle()));
			elements.addContent(new Element("url").setText(article.getUrl()));
			elements.addContent(new Element("initUrl").setText(article.getInitUrl()));
			elements.addContent(new Element("subtitle").setText(StringUtil.convert(article.getSubtitle())));
			elements.addContent(new Element("leadingTitle").setText(StringUtil.convert(article.getLeadingTitle())));
			elements.addContent(new Element("infoSource").setText(StringUtil.convert(article.getInfoSource())));
			elements.addContent(new Element("author").setText(StringUtil.convert(article.getAuthor())));
			elements.addContent(new Element("orders").setText(String.valueOf(article.getOrders())));
			elements.addContent(new Element("hits").setText(String.valueOf(article.getHits())));
			elements.addContent(new Element("brief").setText(StringUtil.convert(article.getBrief())));
			elements.addContent(new Element("keyword").setText(StringUtil.convert(article.getKeyword())));
			elements.addContent(new Element("deleted").setText(String.valueOf(article.isDeleted())));
			elements.addContent(new Element("audited").setText(String.valueOf(article.isAudited())));
			elements.addContent(new Element("publishState").setText(article.getPublishState()));
			elements.addContent(new Element("ref").setText(String.valueOf(article.isRef())));
			elements.addContent(new Element("toped").setText(String.valueOf(article.isToped())));
			elements.addContent(new Element("keyFilter").setText(String.valueOf(article.isKeyFilter())));
			elements.addContent(new Element("createTime").setText(DateUtil.toString(article.getCreateTime())));
			elements.addContent(new Element("displayTime").setText(DateUtil.toString(article.getDisplayTime())));
			elements.addContent(new Element("invalidTime").setText(DateUtil.toString(article.getInvalidTime())));
			elements.addContent(new Element("publishTime").setText(DateUtil.toString(article.getPublishTime())));
			elements.addContent(new Element("auditTime").setText(DateUtil.toString(article.getAuditTime())));

			String refered_article_id = "";
			if (article.getReferedArticle() != null) {
				refered_article_id = article.getReferedArticle().getId();
			}
			elements.addContent(new Element("refered_article_id").setText(refered_article_id));
			elements.addContent(new Element("column_id").setText(article.getColumn().getId()));
			elements.addContent(new Element("site_id").setText(article.getSite().getId()));
			
			elements.addContent(new Element("creator_id").setText(article.getCreator().getId()));

			String auditor_id = "";
			if (article.getAuditor() != null) {
				auditor_id = "" + article.getAuditor().getId();
			}
			elements.addContent(new Element("auditor_id").setText(auditor_id));
			//elements.addContent(new Element("text").setText(StringUtil.convert(article.getText())));
			elements.addContent(new Element("textArea").setText(StringUtil.convert(article.getTextArea())));
			//elements.addContent(new Element("pic").setText(StringUtil.convert(article.getPic())));
			//elements.addContent(new Element("attach").setText(StringUtil.convert(article.getAttach())));
			//elements.addContent(new Element("media").setText(StringUtil.convert(article.getMedia())));
			
/*			elements.addContent(new Element("date1").setText(DateUtil.toString(article.getDate1())));
			elements.addContent(new Element("date2").setText(DateUtil.toString(article.getDate2())));
			elements.addContent(new Element("date3").setText(DateUtil.toString(article.getDate3())));
			elements.addContent(new Element("date4").setText(DateUtil.toString(article.getDate4())));
			elements.addContent(new Element("date5").setText(DateUtil.toString(article.getDate5())));
			elements.addContent(new Element("date6").setText(DateUtil.toString(article.getDate6())));
			elements.addContent(new Element("date7").setText(DateUtil.toString(article.getDate7())));
			elements.addContent(new Element("date8").setText(DateUtil.toString(article.getDate8())));
			elements.addContent(new Element("date9").setText(DateUtil.toString(article.getDate9())));
			elements.addContent(new Element("date10").setText(DateUtil.toString(article.getDate10())));

			elements.addContent(new Element("text1").setText(StringUtil.convert(article.getText1())));
			elements.addContent(new Element("text2").setText(StringUtil.convert(article.getText2())));
			elements.addContent(new Element("text3").setText(StringUtil.convert(article.getText3())));
			elements.addContent(new Element("text4").setText(StringUtil.convert(article.getText4())));
			elements.addContent(new Element("text5").setText(StringUtil.convert(article.getText5())));
			elements.addContent(new Element("text6").setText(StringUtil.convert(article.getText6())));
			elements.addContent(new Element("text7").setText(StringUtil.convert(article.getText7())));
			elements.addContent(new Element("text8").setText(StringUtil.convert(article.getText8())));
			elements.addContent(new Element("text9").setText(StringUtil.convert(article.getText9())));
			elements.addContent(new Element("text10").setText(StringUtil.convert(article.getText10())));
			elements.addContent(new Element("text11").setText(StringUtil.convert(article.getText11())));
			elements.addContent(new Element("text12").setText(StringUtil.convert(article.getText12())));
			elements.addContent(new Element("text13").setText(StringUtil.convert(article.getText13())));
			elements.addContent(new Element("text14").setText(StringUtil.convert(article.getText14())));
			elements.addContent(new Element("text15").setText(StringUtil.convert(article.getText15())));
			elements.addContent(new Element("text16").setText(StringUtil.convert(article.getText16())));
			elements.addContent(new Element("text17").setText(StringUtil.convert(article.getText17())));
			elements.addContent(new Element("text18").setText(StringUtil.convert(article.getText18())));
			elements.addContent(new Element("text19").setText(StringUtil.convert(article.getText19())));
			elements.addContent(new Element("text20").setText(StringUtil.convert(article.getText20())));
			elements.addContent(new Element("text21").setText(StringUtil.convert(article.getText21())));
			elements.addContent(new Element("text22").setText(StringUtil.convert(article.getText22())));
			elements.addContent(new Element("text23").setText(StringUtil.convert(article.getText23())));
			elements.addContent(new Element("text24").setText(StringUtil.convert(article.getText24())));
			elements.addContent(new Element("text25").setText(StringUtil.convert(article.getText25())));

			elements.addContent(new Element("textArea1").setText(StringUtil.convert(article.getTextArea1())));
			elements.addContent(new Element("textArea2").setText(StringUtil.convert(article.getTextArea2())));
			elements.addContent(new Element("textArea3").setText(StringUtil.convert(article.getTextArea3())));
			elements.addContent(new Element("textArea4").setText(StringUtil.convert(article.getTextArea4())));
			elements.addContent(new Element("textArea5").setText(StringUtil.convert(article.getTextArea5())));
			elements.addContent(new Element("textArea6").setText(StringUtil.convert(article.getTextArea6())));
			elements.addContent(new Element("textArea7").setText(StringUtil.convert(article.getTextArea7())));
			elements.addContent(new Element("textArea8").setText(StringUtil.convert(article.getTextArea8())));
			elements.addContent(new Element("textArea9").setText(StringUtil.convert(article.getTextArea9())));
			elements.addContent(new Element("textArea10").setText(StringUtil.convert(article.getTextArea10())));

			elements.addContent(new Element("integer1").setText(String.valueOf(article.getInteger1())));
			elements.addContent(new Element("integer2").setText(String.valueOf(article.getInteger2())));
			elements.addContent(new Element("integer3").setText(String.valueOf(article.getInteger3())));
			elements.addContent(new Element("integer4").setText(String.valueOf(article.getInteger4())));
			elements.addContent(new Element("integer5").setText(String.valueOf(article.getInteger5())));
			elements.addContent(new Element("integer6").setText(String.valueOf(article.getInteger6())));
			elements.addContent(new Element("integer7").setText(String.valueOf(article.getInteger7())));
			elements.addContent(new Element("integer8").setText(String.valueOf(article.getInteger8())));
			elements.addContent(new Element("integer9").setText(String.valueOf(article.getInteger9())));
			elements.addContent(new Element("integer10").setText(String.valueOf(article.getInteger10())));

			elements.addContent(new Element("float1").setText(""+StringUtil.convert(article.getFloat1())));
			elements.addContent(new Element("float2").setText(""+StringUtil.convert(article.getFloat2())));
			elements.addContent(new Element("float3").setText(""+StringUtil.convert(article.getFloat3())));
			elements.addContent(new Element("float04").setText(""+StringUtil.convert(article.getFloat04())));
			elements.addContent(new Element("float5").setText(""+StringUtil.convert(article.getFloat5())));
			elements.addContent(new Element("float6").setText(""+StringUtil.convert(article.getFloat6())));
			elements.addContent(new Element("float7").setText(""+StringUtil.convert(article.getFloat7())));
			elements.addContent(new Element("float08").setText(""+StringUtil.convert(article.getFloat08())));
			elements.addContent(new Element("float9").setText(""+StringUtil.convert(article.getFloat9())));
			elements.addContent(new Element("float10").setText(""+StringUtil.convert(article.getFloat10())));

			elements.addContent(new Element("bool1").setText(String.valueOf(article.isBool1())));
			elements.addContent(new Element("bool2").setText(String.valueOf(article.isBool2())));
			elements.addContent(new Element("bool3").setText(String.valueOf(article.isBool3())));
			elements.addContent(new Element("bool4").setText(String.valueOf(article.isBool4())));
			elements.addContent(new Element("bool5").setText(String.valueOf(article.isBool5())));
			elements.addContent(new Element("bool6").setText(String.valueOf(article.isBool6())));
			elements.addContent(new Element("bool7").setText(String.valueOf(article.isBool7())));
			elements.addContent(new Element("bool8").setText(String.valueOf(article.isBool8())));
			elements.addContent(new Element("bool9").setText(String.valueOf(article.isBool9())));
			elements.addContent(new Element("bool10").setText(String.valueOf(article.isBool10())));

			elements.addContent(new Element("pic1").setText(StringUtil.convert(article.getPic1())));
			elements.addContent(new Element("pic2").setText(StringUtil.convert(article.getPic2())));
			elements.addContent(new Element("pic3").setText(StringUtil.convert(article.getPic3())));
			elements.addContent(new Element("pic4").setText(StringUtil.convert(article.getPic4())));
			elements.addContent(new Element("pic5").setText(StringUtil.convert(article.getPic5())));
			elements.addContent(new Element("pic6").setText(StringUtil.convert(article.getPic6())));
			elements.addContent(new Element("pic7").setText(StringUtil.convert(article.getPic7())));
			elements.addContent(new Element("pic8").setText(StringUtil.convert(article.getPic8())));
			elements.addContent(new Element("pic9").setText(StringUtil.convert(article.getPic9())));
			elements.addContent(new Element("pic10").setText(StringUtil.convert(article.getPic10())));

			elements.addContent(new Element("attach1").setText(StringUtil.convert(article.getAttach1())));
			elements.addContent(new Element("attach2").setText(StringUtil.convert(article.getAttach2())));
			elements.addContent(new Element("attach3").setText(StringUtil.convert(article.getAttach3())));
			elements.addContent(new Element("attach4").setText(StringUtil.convert(article.getAttach4())));
			elements.addContent(new Element("attach5").setText(StringUtil.convert(article.getAttach5())));
			elements.addContent(new Element("attach6").setText(StringUtil.convert(article.getAttach6())));
			elements.addContent(new Element("attach7").setText(StringUtil.convert(article.getAttach7())));
			elements.addContent(new Element("attach8").setText(StringUtil.convert(article.getAttach8())));
			elements.addContent(new Element("attach9").setText(StringUtil.convert(article.getAttach9())));
			elements.addContent(new Element("attach10").setText(StringUtil.convert(article.getAttach10())));

			elements.addContent(new Element("media1").setText(StringUtil.convert(article.getMedia1())));
			elements.addContent(new Element("media2").setText(StringUtil.convert(article.getMedia2())));
			elements.addContent(new Element("media3").setText(StringUtil.convert(article.getMedia3())));
			elements.addContent(new Element("media4").setText(StringUtil.convert(article.getMedia4())));
			elements.addContent(new Element("media5").setText(StringUtil.convert(article.getMedia5())));
			elements.addContent(new Element("media6").setText(StringUtil.convert(article.getMedia6())));
			elements.addContent(new Element("media7").setText(StringUtil.convert(article.getMedia7())));
			elements.addContent(new Element("media8").setText(StringUtil.convert(article.getMedia8())));
			elements.addContent(new Element("media9").setText(StringUtil.convert(article.getMedia9())));
			elements.addContent(new Element("media10").setText(StringUtil.convert(article.getMedia10())));

			elements.addContent(new Element("enumeration1").setText(StringUtil.convert(article.getEnumeration1())));
			elements.addContent(new Element("enumeration2").setText(StringUtil.convert(article.getEnumeration2())));
			elements.addContent(new Element("enumeration3").setText(StringUtil.convert(article.getEnumeration3())));
			elements.addContent(new Element("enumeration4").setText(StringUtil.convert(article.getEnumeration4())));
			elements.addContent(new Element("enumeration5").setText(StringUtil.convert(article.getEnumeration5())));
*/
			// 给父节点list添加user子节点
			root.addContent(elements);
		}

		XMLOutputter XMLOut = new XMLOutputter();
		// 输出 article.xml 文件
		try {
			XMLOut.output(document, new FileOutputStream(path));
		} catch (Exception e) {
			e.printStackTrace();
		}

		String message = "导出成功";
		return message;
	}

	/**
	 * 导入文章
	 * 
	 * @param xmlpath
	 *            文件路径
	 * @param columnId
	 * @param siteId
	 * @param creatorId
	 * @return
	 * @throws JDOMException 
	 * @throws FileNotFoundException 
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> importArticlesXml(String xmlpath, String columnId, String siteId, String creatorId, boolean isUpSystemAdmin){
		String message = "";
		// 定时发布的文章
		String timeSettingArticleIds = "";
		// 定时发布的文章的时间
		String timeSetting = "";
		// 发布方式，定时时间
		Map<String, String> map = new HashMap<String, String>();
		
		String columnFormatId = "";
		// 获得栏目格式
		Column column = columnDao.getAndClear(columnId);
		columnFormatId = column.getArticleFormat().getId();

		// 使用JDOM首先要指定使用什么解析器,可以给参数false、true
		SAXBuilder builder = new SAXBuilder();
		try {
			// 得到Document，我们以后要进行的所有操作都是对这个Document操作的：
			Document doc = builder.build(new FileInputStream(xmlpath));
			// 得到根元素
			Element articles = doc.getRootElement();
			// 得到元素（节点）的集合：
			List list = articles.getChildren();
			// 循环导入所有文章
			for (int i = 0; i < list.size(); i++) {
				// 得到根节点下面的第一篇文章
				Element article = (Element) list.get(i);
				Article newArticle = new Article();
				// 比对格式
				Element article_format_id = article.getChild("article_format_id");
				String formatId = article_format_id.getText();
				if (!formatId.equals(columnFormatId)) {
					message = "文章格式与当前栏目格式不一致,导入失败";
					break;
				}
				// 保存格式
		 

				newArticle.setColumn(column);

				Element title = article.getChild("title");
				String newTitle = "" + title.getText();
				newArticle.setTitle(newTitle);
				
				Element subtitle = article.getChild("subtitle");
				String newSubtitle = "" + subtitle.getText();
				newArticle.setSubtitle(newSubtitle);

				Element leadingTitle = article.getChild("leadingTitle");
				String newLeadingTitle = "" + leadingTitle.getText();
				newArticle.setLeadingTitle(newLeadingTitle);

				Element infoSource = article.getChild("infoSource");
				String newInfoSource = "" + infoSource.getText();
				newArticle.setInfoSource(newInfoSource);

				Element author = article.getChild("author");
				String newAuthor = "" + author.getText();
				newArticle.setAuthor(newAuthor);

				Element hits = article.getChild("hits");
				int newHits = Integer.parseInt(hits.getText());
				newArticle.setHits(newHits);

				Element brief = article.getChild("brief");
				String newBrief = "" + brief.getText();
				newArticle.setBrief(newBrief);

				Element keyword = article.getChild("keyword");
				String newKeyword = "" + keyword.getText();
				newArticle.setKeyword(newKeyword);

				Element deleted = article.getChild("deleted");
				String newDeleted = "" + deleted.getText();
				if (newDeleted.equals("true")) {
					newArticle.setDeleted(true);
				} else {
					newArticle.setDeleted(false);
				}

				// 根据栏目审核情况来确定导入文章的审核情况
				boolean hasAudite = column.isAudited();
				newArticle.setAudited(hasAudite);

				Element ref = article.getChild("ref");
				String newRef = "" + ref.getText();
				if (newRef.equals("true")) {
					newArticle.setRef(true);
				} else {
					newArticle.setRef(false);
				}

				Element toped = article.getChild("toped");
				String newToped = "" + toped.getText();
				if (newToped.equals("true")) {
					newArticle.setToped(true);
				} else {
					newArticle.setToped(false);
				}

				Element keyFilter = article.getChild("keyFilter");
				String newKeyFilter = "" + keyFilter.getText();
				if (newKeyFilter.equals("true")) {
					newArticle.setKeyFilter(true);
				} else {
					newArticle.setKeyFilter(false);
				}

				newArticle.setCreateTime(new Date());
				newArticle.setDisplayTime(new Date());
				newArticle.setInvalidTime(null);
				newArticle.setPublishTime(null);
				newArticle.setAuditTime(null);
				
				Element refered_article_id = article.getChild("refered_article_id");
				String newRefered_article_id = "" + refered_article_id.getText();
				Article refArticle = articleDao.getAndClear(newRefered_article_id);
				newArticle.setReferedArticle(refArticle);

				Site site = new Site();
				site.setId(siteId);
				newArticle.setSite(site);

				User creator = new User();
				creator.setId(creatorId);
				newArticle.setCreator(creator);

				newArticle.setAuditor(null);
				
//				Element text1 = article.getChild("text");
//				String newText1 = "" + text1.getText();
//				newArticle.setText(newText1);

				
				Element textArea1 = article.getChild("textArea");
				String newTextArea1 = "" + textArea1.getText();
				newArticle.setTextArea(newTextArea1);
				
//				Element pic1 = article.getChild("pic");
//				String newPic1 = "" + pic1.getText();
//				newArticle.setPic(newPic1);
/*
				Element date1 = article.getChild("date1");
				String newDate1 = date1.getText();
				if (newDate1 != null && !newDate1.equals("") && !newDate1.equals("null")) {
					Date time1 = DateUtil.toDate(newDate1);
					newArticle.setDate1(time1);
				} else {
					newArticle.setDate1(null);
				}

				Element date2 = article.getChild("date2");
				String newDate2 = date2.getText();
				if (newDate2 != null && !newDate2.equals("") && !newDate2.equals("null")) {
					Date time2 = DateUtil.toDate(newDate2);
					newArticle.setDate2(time2);
				} else {
					newArticle.setDate2(null);
				}

				Element date3 = article.getChild("date3");
				String newDate3 = date3.getText();
				if (newDate3 != null && !newDate3.equals("") && !newDate3.equals("null")) {
					Date time3 = DateUtil.toDate(newDate3);
					newArticle.setDate3(time3);
				} else {
					newArticle.setDate3(null);
				}

				Element date4 = article.getChild("date4");
				String newDate4 = date4.getText();
				if (newDate4 != null && !newDate4.equals("") && !newDate4.equals("null")) {
					Date time4 = DateUtil.toDate(newDate4);
					newArticle.setDate4(time4);
				} else {
					newArticle.setDate4(null);
				}

				Element date5 = article.getChild("date5");
				String newDate5 = date5.getText();
				if (newDate5 != null && !newDate5.equals("") && !newDate5.equals("null")) {
					Date time5 = DateUtil.toDate(newDate5);
					newArticle.setDate5(time5);
				} else {
					newArticle.setDate5(null);
				}

				Element date6 = article.getChild("date6");
				String newDate6 = date6.getText();
				if (newDate6 != null && !newDate6.equals("") && !newDate6.equals("null")) {
					Date time6 = DateUtil.toDate(newDate6);
					newArticle.setDate6(time6);
				} else {
					newArticle.setDate6(null);
				}

				Element date7 = article.getChild("date7");
				String newDate7 = date7.getText();
				if (newDate7 != null && !newDate7.equals("") && !newDate7.equals("null")) {
					Date time7 = DateUtil.toDate(newDate7);
					newArticle.setDate7(time7);
				} else {
					newArticle.setDate7(null);
				}

				Element date8 = article.getChild("date8");
				String newDate8 = date8.getText();
				if (newDate8 != null && !newDate8.equals("") && !newDate8.equals("null")) {
					Date time8 = DateUtil.toDate(newDate8);
					newArticle.setDate8(time8);
				} else {
					newArticle.setDate8(null);
				}

				Element date9 = article.getChild("date9");
				String newDate9 = date9.getText();
				if (newDate9 != null && !newDate9.equals("") && !newDate9.equals("null")) {
					Date time9 = DateUtil.toDate(newDate9);
					newArticle.setDate9(time9);
				} else {
					newArticle.setDate9(null);
				}

				Element date10 = article.getChild("date10");
				String newDate10 = date10.getText();
				if (newDate10 != null && !newDate10.equals("") && !newDate10.equals("null")) {
					Date time10 = DateUtil.toDate(newDate10);
					newArticle.setDate10(time10);
				} else {
					newArticle.setDate10(null);
				}

				Element text1 = article.getChild("text1");
				String newText1 = "" + text1.getText();
				newArticle.setText1(newText1);

				Element text2 = article.getChild("text2");
				String newText2 = "" + text2.getText();
				newArticle.setText2(newText2);

				Element text3 = article.getChild("text3");
				String newText3 = "" + text3.getText();
				newArticle.setText3(newText3);

				Element text4 = article.getChild("text4");
				String newText4 = "" + text4.getText();
				newArticle.setText4(newText4);

				Element text5 = article.getChild("text5");
				String newText5 = "" + text5.getText();
				newArticle.setText5(newText5);

				Element text6 = article.getChild("text6");
				String newText6 = "" + text6.getText();
				newArticle.setText6(newText6);

				Element text7 = article.getChild("text7");
				String newText7 = "" + text7.getText();
				newArticle.setText7(newText7);

				Element text8 = article.getChild("text8");
				String newText8 = "" + text8.getText();
				newArticle.setText8(newText8);

				Element text9 = article.getChild("text9");
				String newText9 = "" + text9.getText();
				newArticle.setText9(newText9);

				Element text10 = article.getChild("text10");
				String newText10 = "" + text10.getText();
				newArticle.setText10(newText10);

				Element text11 = article.getChild("text11");
				String newText11 = "" + text11.getText();
				newArticle.setText11(newText11);

				Element text12 = article.getChild("text12");
				String newText12 = "" + text12.getText();
				newArticle.setText12(newText12);

				Element text13 = article.getChild("text13");
				String newText13 = "" + text13.getText();
				newArticle.setText13(newText13);

				Element text14 = article.getChild("text14");
				String newText14 = "" + text14.getText();
				newArticle.setText14(newText14);

				Element text15 = article.getChild("text15");
				String newText15 = "" + text15.getText();
				newArticle.setText15(newText15);

				Element text16 = article.getChild("text16");
				String newText16 = "" + text16.getText();
				newArticle.setText16(newText16);

				Element text17 = article.getChild("text17");
				String newText17 = "" + text17.getText();
				newArticle.setText17(newText17);

				Element text18 = article.getChild("text18");
				String newText18 = "" + text18.getText();
				newArticle.setText18(newText18);

				Element text19 = article.getChild("text19");
				String newText19 = "" + text19.getText();
				newArticle.setText19(newText19);

				Element text20 = article.getChild("text20");
				String newText20 = "" + text20.getText();
				newArticle.setText20(newText20);

				Element text21 = article.getChild("text21");
				String newText21 = "" + text21.getText();
				newArticle.setText21(newText21);

				Element text22 = article.getChild("text22");
				String newText22 = "" + text22.getText();
				newArticle.setText22(newText22);

				Element text23 = article.getChild("text23");
				String newText23 = "" + text23.getText();
				newArticle.setText23(newText23);

				Element text24 = article.getChild("text24");
				String newText24 = "" + text24.getText();
				newArticle.setText24(newText24);

				Element text25 = article.getChild("text25");
				String newText25 = "" + text25.getText();
				newArticle.setText25(newText25);

				Element textArea1 = article.getChild("textArea1");
				String newTextArea1 = "" + textArea1.getText();
				newArticle.setTextArea1(newTextArea1);

				Element textArea2 = article.getChild("textArea2");
				String newTextArea2 = "" + textArea2.getText();
				newArticle.setTextArea2(newTextArea2);

				Element textArea3 = article.getChild("textArea3");
				String newTextArea3 = "" + textArea3.getText();
				newArticle.setTextArea3(newTextArea3);

				Element textArea4 = article.getChild("textArea4");
				String newTextArea4 = "" + textArea4.getText();
				newArticle.setTextArea4(newTextArea4);

				Element textArea5 = article.getChild("textArea5");
				String newTextArea5 = "" + textArea5.getText();
				newArticle.setTextArea5(newTextArea5);

				Element textArea6 = article.getChild("textArea6");
				String newTextArea6 = "" + textArea6.getText();
				newArticle.setTextArea6(newTextArea6);

				Element textArea7 = article.getChild("textArea7");
				String newTextArea7 = "" + textArea7.getText();
				newArticle.setTextArea7(newTextArea7);

				Element textArea8 = article.getChild("textArea8");
				String newTextArea8 = "" + textArea8.getText();
				newArticle.setTextArea8(newTextArea8);

				Element textArea9 = article.getChild("textArea9");
				String newTextArea9 = "" + textArea9.getText();
				newArticle.setTextArea9(newTextArea9);

				Element textArea10 = article.getChild("textArea10");
				String newTextArea10 = "" + textArea10.getText();
				newArticle.setTextArea10(newTextArea10);

				Element integer1 = article.getChild("integer1");
				int newInteger1 = StringUtil.parseInt(integer1.getText());
				newArticle.setInteger1(newInteger1);

				Element integer2 = article.getChild("integer2");
				int newInteger2 = StringUtil.parseInt(integer2.getText());
				newArticle.setInteger2(newInteger2);

				Element integer3 = article.getChild("integer3");
				int newInteger3 = StringUtil.parseInt(integer3.getText());
				newArticle.setInteger3(newInteger3);

				Element integer4 = article.getChild("integer4");
				int newInteger4 = StringUtil.parseInt(integer4.getText());
				newArticle.setInteger4(newInteger4);

				Element integer5 = article.getChild("integer5");
				int newInteger5 = StringUtil.parseInt(integer5.getText());
				newArticle.setInteger5(newInteger5);

				Element integer6 = article.getChild("integer6");
				int newInteger6 = StringUtil.parseInt(integer6.getText());
				newArticle.setInteger6(newInteger6);

				Element integer7 = article.getChild("integer7");
				int newInteger7 = StringUtil.parseInt(integer7.getText());
				newArticle.setInteger7(newInteger7);

				Element integer8 = article.getChild("integer8");
				int newInteger8 = StringUtil.parseInt(integer8.getText());
				newArticle.setInteger8(newInteger8);

				Element integer9 = article.getChild("integer9");
				int newInteger9 = StringUtil.parseInt(integer9.getText());
				newArticle.setInteger9(newInteger9);

				Element integer10 = article.getChild("integer10");
				int newInteger10 = StringUtil.parseInt(integer10.getText());
				newArticle.setInteger10(newInteger10);

				Element float1 = article.getChild("float1");
				float newFloat1 = StringUtil.parseFloat(float1.getText());
				newArticle.setFloat1(newFloat1);

				Element float2 = article.getChild("float2");
				float newFloat2 = StringUtil.parseFloat(float2.getText());
				newArticle.setFloat2(newFloat2);

				Element float3 = article.getChild("float3");
				float newFloat3 = StringUtil.parseFloat(float3.getText());
				newArticle.setFloat3(newFloat3);

				Element float04 = article.getChild("float04");
				float newFloat04 = StringUtil.parseFloat(float04.getText());
				newArticle.setFloat04(newFloat04);

				Element float5 = article.getChild("float5");
				float newFloat5 = StringUtil.parseFloat(float5.getText());
				newArticle.setFloat5(newFloat5);

				Element float6 = article.getChild("float6");
				float newFloat6 = StringUtil.parseFloat(float6.getText());
				newArticle.setFloat6(newFloat6);

				Element float7 = article.getChild("float7");
				float newFloat7 = StringUtil.parseFloat(float7.getText());
				newArticle.setFloat7(newFloat7);

				Element float08 = article.getChild("float08");
				float newFloat08 = StringUtil.parseFloat(float08.getText());
				newArticle.setFloat08(newFloat08);

				Element float9 = article.getChild("float9");
				float newFloat9 = StringUtil.parseFloat(float9.getText());
				newArticle.setFloat9(newFloat9);

				Element float10 = article.getChild("float10");
				float newFloat10 = StringUtil.parseFloat(float10.getText());
				newArticle.setFloat10(newFloat10);

				Element bool1 = article.getChild("bool1");
				String newBool1 = bool1.getText();
				if (newBool1.equals("true")) {
					newArticle.setBool1(true);
				} else {
					newArticle.setBool1(false);
				}
				Element bool2 = article.getChild("bool2");
				String newBool2 = bool2.getText();
				if (newBool2.equals("true")) {
					newArticle.setBool2(true);
				} else {
					newArticle.setBool2(false);
				}
				Element bool3 = article.getChild("bool3");
				String newBool3 = bool3.getText();
				if (newBool3.equals("true")) {
					newArticle.setBool3(true);
				} else {
					newArticle.setBool3(false);
				}
				Element bool4 = article.getChild("bool4");
				String newBool4 = bool4.getText();
				if (newBool4.equals("true")) {
					newArticle.setBool4(true);
				} else {
					newArticle.setBool4(false);
				}
				Element bool5 = article.getChild("bool5");
				String newBool5 = bool5.getText();
				if (newBool5.equals("true")) {
					newArticle.setBool5(true);
				} else {
					newArticle.setBool5(false);
				}
				Element bool6 = article.getChild("bool6");
				String newBool6 = bool6.getText();
				if (newBool6.equals("true")) {
					newArticle.setBool6(true);
				} else {
					newArticle.setBool6(false);
				}
				Element bool7 = article.getChild("bool7");
				String newBool7 = bool7.getText();
				if (newBool7.equals("true")) {
					newArticle.setBool7(true);
				} else {
					newArticle.setBool7(false);
				}
				Element bool8 = article.getChild("bool8");
				String newBool8 = bool8.getText();
				if (newBool8.equals("true")) {
					newArticle.setBool8(true);
				} else {
					newArticle.setBool8(false);
				}
				Element bool9 = article.getChild("bool9");
				String newBool9 = bool9.getText();
				if (newBool9.equals("true")) {
					newArticle.setBool9(true);
				} else {
					newArticle.setBool9(false);
				}
				Element bool10 = article.getChild("bool10");
				String newBool10 = bool10.getText();
				if (newBool10.equals("true")) {
					newArticle.setBool10(true);
				} else {
					newArticle.setBool10(false);
				}

				Element pic1 = article.getChild("pic1");
				String newPic1 = "" + pic1.getText();
				newArticle.setPic1(newPic1);

				Element pic2 = article.getChild("pic2");
				String newPic2 = "" + pic2.getText();
				newArticle.setPic2(newPic2);

				Element pic3 = article.getChild("pic3");
				String newPic3 = "" + pic3.getText();
				newArticle.setPic3(newPic3);

				Element pic4 = article.getChild("pic4");
				String newPic4 = "" + pic4.getText();
				newArticle.setPic4(newPic4);

				Element pic5 = article.getChild("pic5");
				String newPic5 = "" + pic5.getText();
				newArticle.setPic5(newPic5);

				Element pic6 = article.getChild("pic6");
				String newPic6 = "" + pic6.getText();
				newArticle.setPic6(newPic6);

				Element pic7 = article.getChild("pic7");
				String newPic7 = "" + pic7.getText();
				newArticle.setPic7(newPic7);

				Element pic8 = article.getChild("pic8");
				String newPic8 = "" + pic8.getText();
				newArticle.setPic8(newPic8);

				Element pic9 = article.getChild("pic9");
				String newPic9 = "" + pic9.getText();
				newArticle.setPic9(newPic9);

				Element pic10 = article.getChild("pic10");
				String newPic10 = "" + pic10.getText();
				newArticle.setPic10(newPic10);

				Element attach1 = article.getChild("attach1");
				String newAttach1 = "" + attach1.getText();
				newArticle.setAttach1(newAttach1);

				Element attach2 = article.getChild("attach2");
				String newAttach2 = "" + attach2.getText();
				newArticle.setAttach2(newAttach2);

				Element attach3 = article.getChild("attach3");
				String newAttach3 = "" + attach3.getText();
				newArticle.setAttach3(newAttach3);

				Element attach4 = article.getChild("attach4");
				String newAttach4 = "" + attach4.getText();
				newArticle.setAttach4(newAttach4);

				Element attach5 = article.getChild("attach5");
				String newAttach5 = "" + attach5.getText();
				newArticle.setAttach5(newAttach5);

				Element attach6 = article.getChild("attach6");
				String newAttach6 = "" + attach6.getText();
				newArticle.setAttach6(newAttach6);

				Element attach7 = article.getChild("attach7");
				String newAttach7 = "" + attach7.getText();
				newArticle.setAttach7(newAttach7);

				Element attach8 = article.getChild("attach8");
				String newAttach8 = "" + attach8.getText();
				newArticle.setAttach8(newAttach8);

				Element attach9 = article.getChild("attach9");
				String newAttach9 = "" + attach9.getText();
				newArticle.setAttach9(newAttach9);

				Element attach10 = article.getChild("attach10");
				String newAttach10 = "" + attach10.getText();
				newArticle.setAttach10(newAttach10);

				Element media1 = article.getChild("media1");
				String newMedia1 = "" + media1.getText();
				newArticle.setMedia1(newMedia1);

				Element media2 = article.getChild("media2");
				String newMedia2 = "" + media2.getText();
				newArticle.setMedia2(newMedia2);

				Element media3 = article.getChild("media3");
				String newMedia3 = "" + media3.getText();
				newArticle.setMedia3(newMedia3);

				Element media4 = article.getChild("media4");
				String newMedia4 = "" + media4.getText();
				newArticle.setMedia4(newMedia4);

				Element media5 = article.getChild("media5");
				String newMedia5 = "" + media5.getText();
				newArticle.setMedia5(newMedia5);

				Element media6 = article.getChild("media6");
				String newMedia6 = "" + media6.getText();
				newArticle.setMedia6(newMedia6);

				Element media7 = article.getChild("media7");
				String newMedia7 = "" + media7.getText();
				newArticle.setMedia7(newMedia7);

				Element media8 = article.getChild("media8");
				String newMedia8 = "" + media8.getText();
				newArticle.setMedia8(newMedia8);

				Element media9 = article.getChild("media9");
				String newMedia9 = "" + media9.getText();
				newArticle.setMedia9(newMedia9);

				Element media10 = article.getChild("media10");
				String newMedia10 = "" + media10.getText();
				newArticle.setMedia10(newMedia10);

				Element enumeration1 = article.getChild("enumeration1");
				String newEnumeration1 = "" + enumeration1.getText();
				newArticle.setEnumeration1(newEnumeration1);

				Element enumeration2 = article.getChild("enumeration2");
				String newEnumeration2 = "" + enumeration2.getText();
				newArticle.setEnumeration2(newEnumeration2);

				Element enumeration3 = article.getChild("enumeration3");
				String newEnumeration3 = "" + enumeration3.getText();
				newArticle.setEnumeration3(newEnumeration3);

				Element enumeration4 = article.getChild("enumeration4");
				String newEnumeration4 = "" + enumeration4.getText();
				newArticle.setEnumeration4(newEnumeration4);

				Element enumeration5 = article.getChild("enumeration5");
				String newEnumeration5 = "" + enumeration5.getText();
				newArticle.setEnumeration5(newEnumeration5);*/
				
				int maxArticle = 0;
				// 设置文章的order
				maxArticle = this.findMaxArticleOrder();
				newArticle.setOrders(maxArticle + 1);
				
				String Id = "f017";
				List<GeneralSystemSet> list1 = findGeneralSystemSetLink(Id);
				// 先全部替换需要替换的内容 keyFilter
				// 查找过滤内容
				/** 判断过滤 */
				/** 判断父栏目是否存在，如果不存在就是主栏目 */
				if (newArticle.isKeyFilter()) {
					/** 系统管理员过滤内容 */
					List SiteIdCount = siteDao.findByNamedQuery("findSiteParentId");
					if (isUpSystemAdmin) {
						if (SiteIdCount.size() < 1) {
							newTextArea1 = replaceAllTextArearBySystemAuthor(newTextArea1);
						} else {
							newTextArea1 = replaceAllTextArear(newTextArea1, siteId);
						}
					} else {
						newTextArea1 = replaceAllTextArear(newTextArea1, siteId);
					}
				}
				for (int j = 0; j < list1.size(); j++) {
					/** <a href='http://www.qq.com'>中国</a> */
					GeneralSystemSet generalSystemSet = (GeneralSystemSet) list1.get(j);
					String setContent = generalSystemSet.getSetContent();
					String url = generalSystemSet.getUrl();
					String replace = "<a href='" + url + "'><font color='blue'>" + setContent + "</font></a>";
					/** 不断的给articleTextAreal替换内容 */
					if(!StringUtil.isEmpty(newTextArea1)){
						newTextArea1 = StringUtil.replace(newTextArea1, setContent,	replace);
					}
				}
				newArticle.setTextArea(newTextArea1);
				// 导入到 的栏目为审核状态
				if(column.isAudited()){
					User user = new User();
					user.setId(creatorId);
					newArticle.setAuditor(user);
					newArticle.setAuditTime(new Date());
					if(column.getPublishWay().equals(Column.PUBLISH_WAY_AUTO)){
						newArticle.setPublishState(Article.PUBLISH_STATE_PUBLISHING);
					}else{
						newArticle.setPublishState(Article.PUBLISH_STATE_UNPUBLISHEED);
					}
				}else{
					newArticle.setPublishState(Article.PUBLISH_STATE_UNPUBLISHEED);
				}
				if(newArticle.getDisplayTime() == null){
					newArticle.setDisplayTime(newArticle.getCreateTime());
				}
				articleDao.save(newArticle);
				
				// 建立文章时自动生成一个url（修改的时候不允许修改 ->.html）
				String initUrl = SiteResource.getBuildStaticDir(siteId, true) + "/col_"
						+ columnId + "/" + DateUtil.getCurrDate().replaceAll("-", "/")
						+ "/art_" + newArticle.getId() + ".html";
				newArticle.setInitUrl(initUrl);
				newArticle.setUrl(initUrl);
				articleDao.updateAndClear(newArticle);

				// 导入到 的栏目为审核状态
				if(column.isAudited()){
					if(column.getPublishWay().equals(Column.PUBLISH_WAY_AUTO)){
						// 写入生成列表
						ArticleBuildList articleBuildList = new ArticleBuildList();
						BeanUtil.copyProperties(articleBuildList, newArticle);
				/*		publisher.initCategoryMap();	
						//将文章相关的栏目页重新发布
						publisher.buildColumnByColumnId(newArticle.getColumn().getId(), siteId);
						//首页重新发布
						publisher.buildArticleByArticleId(newArticle.getId(), siteId);
*/
						if (articleBuildListDao.getAndClear(articleBuildList.getId()) == null) {
							articleBuildList.setStatus("true");
							articleBuildListDao.saveAndClear(articleBuildList);
						} else {
							articleBuildList.setStatus("true");
							articleBuildListDao.updateAndClear(articleBuildList);
						}
										}
					if(column.getPublishWay().equals(Column.PUBLISH_WAY_TIME)){
						// 定时发布（文章审核通过的情况下）
						if(StringUtil.isEmpty(timeSettingArticleIds)){
							timeSettingArticleIds = newArticle.getId();
							timeSetting = String.valueOf(column.getTimeSetting());
						}else{
							timeSettingArticleIds += "," + newArticle.getId();
							timeSetting += "," + String.valueOf(column.getTimeSetting());
						}
					}
				}
				
				// 如果该栏目是引用父站的，则父站栏目自动和当前栏目同步
				String parentSiteColumnId = "";
				if (column.getParentSiteColumnId() != null
						&& !column.getParentSiteColumnId().equals("")
						&& !column.getParentSiteColumnId().equals("null")) {
					Article destArticle = new Article();

					parentSiteColumnId = column.getParentSiteColumnId();
					Article orgArticle = articleDao.getAndClear(newArticle.getId());
					BeanUtil.copyProperties(destArticle, orgArticle);
					destArticle.setId(null);
					Column refColumn = new Column();
					refColumn.setId(parentSiteColumnId);
					destArticle.setColumn(refColumn);
					
					Site newsite = new Site();
					Column parentSiteColumn = columnDao.getAndNonClear(parentSiteColumnId);
					site.setId(parentSiteColumn.getSite().getId());
					columnDao.clearCache();
					destArticle.setSite(newsite);
					
					// 可否修改
					boolean allowModify = column.isAllowModify();
					destArticle.setRef(!allowModify);
					if (allowModify) {
						destArticle.setReferedArticle(null);
					} else {
						Article referedArticle = new Article();
						referedArticle.setId(orgArticle.getId());
						destArticle.setReferedArticle(referedArticle);
					}
					Map<String, String> newMap = this.addPresentArticle(destArticle);
					String newTimeSettingArticleIds = newMap.get("timeSettingArticleIds");
					String newTimeSetting = newMap.get("timeSetting");
					if(!StringUtil.isEmpty(newTimeSettingArticleIds)){
						if(!StringUtil.isEmpty(timeSettingArticleIds)){
							timeSettingArticleIds += "," + newTimeSettingArticleIds;
							timeSetting += "," + newTimeSetting;
						}else{
							timeSettingArticleIds = newTimeSettingArticleIds;
							timeSetting = newTimeSetting;
						}
					}
				}

				// 栏目同步时添加文章
				boolean allowSend = column.isSendMenu();
				// 开启发送
				if (allowSend) {
					// 获得发送的栏目ids
					String refColumnIds = column.getRefColumnIds();
					String cIds[] = refColumnIds.split(",");
					if (refColumnIds != null && !refColumnIds.equals("")
							&& !refColumnIds.equals("0")) {
						for (int k = 0; k < cIds.length; k++) {
							Column activeColumn = columnDao.getAndClear(cIds[k]);
							if (activeColumn != null) {
								// 被发送的栏目开关
								boolean allowReceive = activeColumn.isReceiveMenu();
								// 开
								if (allowReceive) {
									Article destArticle = new Article();
									Article orgArticle = articleDao.getAndClear(newArticle.getId());
									BeanUtil.copyProperties(destArticle, orgArticle);
									destArticle.setId(null);
									Column refColumn = new Column();
									refColumn.setId(cIds[i]);
									destArticle.setColumn(refColumn);
									Column artColumn = columnDao.getAndClear(cIds[i]);
									String artSiteId = artColumn.getSite().getId();
									Site artSite = new Site();
									artSite.setId(artSiteId);
									destArticle.setSite(artSite);
									// 可否修改
									boolean allowModify = column.isAllowModify();
									destArticle.setRef(!allowModify);
									if (allowModify) {
										destArticle.setReferedArticle(null);
									} else {
										Article referedArticle = new Article();
										referedArticle.setId(orgArticle.getId());
										destArticle.setReferedArticle(referedArticle);
									}
									Map<String, String> newMap = this.addPresentArticle(destArticle);
									String newTimeSettingArticleIds = newMap.get("timeSettingArticleIds");
									String newTimeSetting = newMap.get("timeSetting");
									if(!StringUtil.isEmpty(newTimeSettingArticleIds)){
										if(!StringUtil.isEmpty(timeSettingArticleIds)){
											timeSettingArticleIds += "," + newTimeSettingArticleIds;
											timeSetting += "," + newTimeSetting;
										}else{
											timeSettingArticleIds = newTimeSettingArticleIds;
											timeSetting = newTimeSetting;
										}
									}
								}
							}
						}
					}
				}
				message = "导入成功";
			}
		} catch (Exception e) {
			message = "导入失败";
		}
		map.put("timeSettingArticleIds", timeSettingArticleIds);
		map.put("timeSetting", timeSetting);
		map.put("infoMessage", message);
		return map;
	}

	/**
	 * 查找格式相同的栏目
	 * @param columnId   呈送文章所在栏目id
	 * @param siteId     当前网站id
	 * @return sameFormatColumns 格式相同的栏目ids
	 */
	@SuppressWarnings("unchecked")
	public String findSameFormatColumns(String columnId, String siteId) {
		String sameFormatColumns = "";
		// 获得栏目格式
		Column column = columnDao.getAndClear(columnId);
		String articleFormatId = column.getArticleFormat().getId();
		String[] params = { "siteId", "articleFormatId" };
		Object[] values = { siteId, articleFormatId };

		List list = columnDao.findByNamedQuery(
				"findSameFormatColumnsByFormatId", params, values);
		for (int i = 0; i < list.size(); i++) {
			sameFormatColumns += list.get(i) + ",";
		}
		return sameFormatColumns;
	}

	/**
	 * 确认栏目是否已审核
	 * @param columnId
	 * @return
	 */
	public boolean sureColumnHasAudited(String columnId) {
		Column column = columnDao.getAndClear(columnId);
		boolean audited = column.isAudited();
		return audited;
	}

	/**
	 * 确认用户是否拥有文章审核权限
	 * @param userId
	 * @param siteId
	 * @param columnId
	 * @return articleAuditedRights
	 */
	public String findUserAuditedRights(String userId, String siteId,
			String columnId) {
		String articleAuditedRights = "";
		User user = userDao.getAndClear(userId);
		String userName = user.getName();
		if (userName.equals("超级管理员") || userName.equals("系统管理员")
				|| userName.equals("网站管理员")) {
			articleAuditedRights = "RIGHT";
		} else {
			String[] params = { "userId", "siteId", "resourceType",
					"operationTypes", "columnId" };
			Object[] values = { userId, siteId, Resource.TYPE_COLUMN,
					Operation.TYPE_ARTICLE, columnId };
			List list = rightDao
					.findByNamedQuery(
							"findRightByUserIdAndSiteIdAndResourceTypeAndOperationIdsAndColumnId",
							params, values);
			if (list != null && list.size() > 0) {
				articleAuditedRights = "RIGHT";
			} else {
				articleAuditedRights = "NORIGHT";
			}
		}
		return articleAuditedRights;
	}

	/**
	 * 查询系统表中作者设置内容
	 * @return list作者对象
	 */
	public String findGeneralSystemSetList(String categoryId) {
         /**查找所有作者设置*/
		List list = generalSystemSetDao.findByNamedQuery("findSystemSetByCategoryId", "categoryId", categoryId);
		String setContentStart1 = "";
		String one = "";	
		if(!CollectionUtil.isEmpty(list)) {
			for (int i = 0; i < list.size(); i++) {
				GeneralSystemSet generalSystemSet = (GeneralSystemSet) list.get(i);
				// 是作者来源			 
				if(!StringUtil.isEmpty(setContentStart1)) {
					setContentStart1 += "*" + generalSystemSet.getSetContent();
				}else{
					setContentStart1 = generalSystemSet.getSetContent();
				}
				if(generalSystemSet.getDefaultSet()){
					one = generalSystemSet.getSetContent();
				}
			}
			setContentStart1 += "*" + one;
		}
		return setContentStart1;
	}

 

	/**
	 * 查询系统表中链接对象设置内容
	 * @return list作者对象
	 */
	@SuppressWarnings("unchecked")
	public List<GeneralSystemSet> findGeneralSystemSetLink(String Id) {
		List list = generalSystemSetDao.findAll();
		ArrayList<GeneralSystemSet> arrayList = new ArrayList<GeneralSystemSet>();
		for (int i = 0; i < list.size(); i++) {
			GeneralSystemSet generalSystemSet = (GeneralSystemSet) list.get(i);
			if (generalSystemSet.getReneralSystemSetCategory().getId().equals(Id)) {
				// String SetContent =generalSystemSet.getSetContent();
				arrayList.add(generalSystemSet);
			}
		}
		return arrayList;
	}

	public void replaceInformationFilter(String contex) {
		List list = informationFilterDao.findAll();
		ArrayList arrayList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			InformationFilter informationFilter = (InformationFilter) list.get(i);
			// 不替换内容
			if (informationFilter.getStatus().equals("1")) {
				// 我修改的
				// String filterCategoryId =
				// informationFilter.getFilterCategoryId();
				String filterCategoryId = informationFilter.getFilterCategory()
						.getId();
				if (filterCategoryId.equals("1")) {
					arrayList.add(informationFilter.getField1());
					for (int j = 0; j < arrayList.size(); j++) {
						// 如果内容栏目里存在需要不修改的关键字
						if (contex.indexOf(arrayList.get(j).toString()) > 1) {
						}
					}
				}
			}
		}
	}

	// 管理员过滤方法
	public String replaceAllTextArearBySystemAuthor(String textArea) {
		String context = textArea;
		List listUse = informationFilterDao.findByNamedQuery("findUser");
		for (int i = 0; i < listUse.size(); i++) {
			InformationFilter informationFilter = (InformationFilter) listUse
					.get(i);
			// 需要替换的这段
			String fielde1 = informationFilter.getField1();
			// 替换为什么字段
			String replaceField1 = informationFilter.getReplaceField1();
			if(informationFilter.getStatus()){
				if ((null != context) && (!context.equals(""))) {
					if ((null != fielde1) && (!fielde1.equals(""))) {
						context = StringUtil.replace(context, fielde1,
								replaceField1);
					}
				}
			}
		}

		return context;
	}

	// 完全替换
	public String replaceAllTextArear(String textArea, String siteId) {
		String context = textArea;
		String[] KeyNumbers = { "siteId" };
		String[] keyvalues = { siteId };
		List list = informationFilterDao.findByNamedQuery(
				"findFilterReplaceField1", KeyNumbers, keyvalues);
		for (int i = 0; i < list.size(); i++) {
			InformationFilter informationFilter = (InformationFilter) list
					.get(i);
			/** 需要替换的这段 */
			String fielde1 = informationFilter.getField1();
			/** 替换为什么字段 **/
			String replaceField1 = informationFilter.getReplaceField1();
			if(informationFilter.getStatus()){
				if ((null != context) && (!context.equals(""))) {
					if ((null != fielde1) && (!fielde1.equals(""))) {
						context = StringUtil.replace(context, fielde1,
								replaceField1);
					}
				}
			}
		}
		return context;
	}

	/*** 前向对替换1 */
	/**
	 * public String replaceBeforeAll(String textArea){ }
	 */

	// 前向对替换1
	private String replaceBeforeAllHave(String reg, String str) {
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			String label = "本报" + matcher.group(1) + "报道";
			// System.out.println(matcher.group(1));
			matcher.appendReplacement(sb, label);
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	private static String sub(String reg, String str) {
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			String label = "本报" + matcher.group(1) + "报道";
			// System.out.println(matcher.group(1));
			matcher.appendReplacement(sb, label);
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	/**
	 * 获取错误提示信息
	 * @param prompt
	 * @return
	 */
	private String getErrorInfo(String prompt) {
		return "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/><span style='color:red;'>"
				+ prompt + "</span>";
	}

	/**
	 * 查找文章初始地址
	 * @param articleId
	 * @return
	 */
	public String findArticleInitUrl(String articleId) {
		String initUrl = "";
		if (!articleId.equals("") && articleId != null) {
			Article article = articleDao.getAndClear(articleId);
			initUrl = article.getInitUrl();
		}
		return initUrl;
	}

	/**
	 * 添加文章审核日志
	 * @param siteId
	 * @param userId
	 * @param categoryName
	 * @param title
	 */
	public void addArticleLogs(String siteId, String userId, String categoryName, String title) {
		systemLogDao.addLogData(categoryName, siteId, userId, title);
	}

	/**
	 * 文章撤稿
	 * @param ids
	 */
	public void draftArticleByIds(String ids, ArticleService articleService) {
		String siteId = "";
		if(!StringUtil.isEmpty(ids)){
			String[] str = ids.split(",");
			String strColumnTemplate[] = {"templateInstanceId","siteId"};	
			publisher.initCategoryMap();
			String strColumnIds = "";
			// 将文章的状态修改
			for(int i = 0; i < str.length; i++){
				Article article = articleDao.getAndClear(str[i]);
				if(article != null) {
					article.setAudited(true);
					article.setPublishState(Article.PUBLISH_STATE_DRAFT);
					article.setAuditor(null);
					article.setAuditTime(null);
					articleService.modifyArticle(article);
					
					// 撤稿(将文章发布的页面删除掉)
					siteId = article.getSite().getId();
					Site site = siteDao.getAndNonClear(siteId);
					String sitePublishDir = site.getPublishDir();
					String articleUrl = article.getUrl();
					articleUrl = sitePublishDir + articleUrl.replaceFirst("/release/site"+siteId+"/build/static", "");
					if(site.getPublishWay().equals("local")){
						if(FileUtil.isExist(articleUrl)){
							FileUtil.delete(articleUrl);
						}
						//关联栏目页文章页发布，查询出实例ID，再根据栏目关联的实例查询出具体栏目
						String obj[]  = {SqlUtil.toSqlString(siteId),SqlUtil.toSqlString(article.getColumn().getId())};
						String str2[] = {"siteId","columnId"};
						String columnId  = article.getColumn().getId();
						publisher.buildColumnByColumnId(columnId, siteId);
						publisher.publishColumnByColumnId(columnId, siteId);
						List relevanceColumnList = templateUnitDao.findByDefine("findTemplateUnitBySiteId",str2,obj);
						Column column = null;
						article = null;
						TemplateUnit templateUnit = null; 
						String strInstanceId = "";
						ArticleBuildList articleBuildList = null;
						for(int j = 0 ; j < relevanceColumnList.size() ; j++){
							templateUnit = (TemplateUnit)relevanceColumnList.get(j);
							String instanceId = templateUnit.getTemplateInstance().getId();
						 
							if(!StringUtil.contains(strInstanceId, instanceId)){
								strInstanceId = strInstanceId + "," + instanceId;								
								Object objColumnTemplate[] = {instanceId,siteId};			 
								List columnList = columnDao.findByNamedQuery("findColumnByColumnTemplateInstanceIdAndSiteId",strColumnTemplate,objColumnTemplate);
							
								if(columnList != null && columnList.size() > 0){								
								}else{
									Object obj2[] = {siteId,columnId};
									columnList = columnDao.findByNamedQuery("findColumnByColumnIdAndSiteId",str2,obj2);								
								}
								strColumnIds = "";
								for(int t = 0 ; t < columnList.size() ; t++){
									//关联栏目页
									column = (Column)columnList.get(t);										
									if(column != null){		
										if(!StringUtil.contains(strColumnIds,column.getId())){
											strColumnIds = strColumnIds + "," + column.getId();
											publisher.buildColumnByColumnId(column.getId(), siteId);
											publisher.publishColumnByColumnId(column.getId(), siteId);
											
										}										
									}						
								}
								
								columnList = columnDao.findByNamedQuery("findColumnByArticleTemplateInstanceIdAndSiteId",strColumnTemplate,objColumnTemplate);
								if(columnList != null && columnList.size() > 0){
									
								}else{
									Object obj2[] = {siteId,columnId};
									columnList = columnDao.findByNamedQuery("findColumnByColumnIdAndSiteId",str2,obj2);		
								}
								List tempList = new ArrayList();
								//生成发布关联文章页
								for(int t = 0 ; t < columnList.size() ; t++){
									column = (Column)columnList.get(t);
									if(column != null){
										columnId = column.getId();
										if(!StringUtil.contains(strColumnIds,column.getId()) && column.getColumnType().equals("single")){
											strColumnIds = strColumnIds + "," + columnId;
											List tempArticleList = articleDao.findByNamedQuery("findArticleByColumnId","columnId",columnId);
											if(tempArticleList != null && tempArticleList.size() > 0){
												article = (Article)tempArticleList.get(0);												
												if(article.isAudited() && !article.getPublishState().equals(article.PUBLISH_STATE_DRAFT)){
													//只关联发布已审核的
													tempList.add(article);																						
												}								
											}											
										}
									}
								}
								
								for(int t = 0 ; t < tempList.size() ; t++){
									article = (Article)tempList.get(t);
									//只关联发布已审核的
									articleBuildList = new ArticleBuildList();
									BeanUtil.copyProperties(articleBuildList, article);					
									if (articleBuildListDao.getAndNonClear(articleBuildList.getId()) == null) {
										articleBuildList.setStatus("true");
										articleBuildListDao.save(articleBuildList);							 
									} else {
										articleBuildList = articleBuildListDao.getAndNonClear(articleBuildList.getId());
										articleBuildList.setStatus("true");
										articleBuildListDao.update(articleBuildList);									 
									}
								}
								
							}
							
						}
					}
					if(site.getPublishWay().equals("ftp")){
						ArrayList fileList = new ArrayList<String>();
						fileList.add(articleUrl);
						new FtpSender(site.getRemoteIP(), site.getRemotePort(),site.getFtpUserName(),site.getFtpPassWord(),site.getFtpFilePath(), fileList, GlobalConfig.appRealPath,"delete").start();
					}
					
				}
				articleBuildListDao.clearCache();
			}
			publisher.buildHomePage(siteId);
			publisher.publishHomePage(siteId);
		}
	}
	
	public String findFormatNameById(String id) {
		// TODO Auto-generated method stub
		ArticleFormat articleFormat = articleFormatDao.getAndClear(id);
		if(articleFormat != null){
			return articleFormat.getName();
		}else{
			return null;
		}
		
		
	}
	
	public void setEnumerationDao(EnumerationDao enumerationDao) {
		this.enumerationDao = enumerationDao;
	}

	public void setArticleFormatDao(ArticleFormatDao articleFormatDao) {
		this.articleFormatDao = articleFormatDao;
	}

	public void setArticlePublishListDao(
			ArticlePublishListDao articlePublishListDao) {
		this.articlePublishListDao = articlePublishListDao;
	}

	public void setColumnDao(ColumnDao columnDao) {
		this.columnDao = columnDao;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public void setArticleBuildListDao(ArticleBuildListDao articleBuildListDao) {
		this.articleBuildListDao = articleBuildListDao;
	}

	public void setSystemLogDao(SystemLogDao systemLogDao) {
		this.systemLogDao = systemLogDao;
	}

	public void setArticleAttributeDao(ArticleAttributeDao articleAttributeDao) {
		this.articleAttributeDao = articleAttributeDao;
	}

	public void setRightDao(RightDao rightDao) {
		this.rightDao = rightDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setGeneralSystemSetDao(GeneralSystemSetDao generalSystemSetDao) {
		this.generalSystemSetDao = generalSystemSetDao;
	}

	public InformationFilterDao getInformationFilterDao() {
		return informationFilterDao;
	}

	public void setInformationFilterDao(
			InformationFilterDao informationFilterDao) {
		this.informationFilterDao = informationFilterDao;
	}

	public void setSiteDao(SiteDao siteDao) {
		this.siteDao = siteDao;
	}

	/**
	 * @param templateUnitDao the templateUnitDao to set
	 */
	public void setTemplateUnitDao(TemplateUnitDao templateUnitDao) {
		this.templateUnitDao = templateUnitDao;
	}

    public void setArticleAttachDao(ArticleAttachDao articleAttachDao) {
        this.articleAttachDao = articleAttachDao;
    }


}
