/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.articlemanager.domain;

import java.io.Serializable;
import java.util.Date;

import com.baize.ccms.biz.columnmanager.domain.Column;
import com.baize.ccms.biz.sitemanager.domain.Site;
import com.baize.ccms.biz.usermanager.domain.User;

/**        
 * <p>标题: 文章</p>
 * <p>描述: 用于网站显示的基本元素</p>
 * <p>模块: 文章管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 杨信
 * @version 1.0
 * @since 2009-3-10 下午01:44:55
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class Article implements Serializable {

	private static final long serialVersionUID = -6841515710963882374L;
	
	/** 未发布 */
	public static final String PUBLISH_STATE_UNPUBLISHEED = "0";
	
	/** 发布中  */
	public static final String PUBLISH_STATE_PUBLISHING = "2";
	
	/** 已发布 */
	public static final String PUBLISH_STATE_PUBLISHED = "1";
	
	/** 已撤稿 */
	public static final String PUBLISH_STATE_DRAFT = "3";
	
	/** 唯一标识符 */
	private String id;
	
	/** 所属栏目 */
	private Column column;
	
	/** 当前网站 */
	private Site site;
	
	/** 文章格式 */
	private ArticleFormat articleFormat;
	
	/** 是否被删除（是否进入回收站） */
	private boolean deleted;
	
	/** 是否审核 */
	private boolean audited;
	
	/** 发布状态(详细查看该类中：PUBLISH_STATE) */
	private String publishState = PUBLISH_STATE_UNPUBLISHEED;
	
	/** 文章标题 */
	private String title;
	
	/** 副标题 */
	private String subtitle;
	
	/** 引题 */
	private String leadingTitle;
	
	/** 信息来源 */
	private String infoSource;
	
	/** 文章页访问地址 */
	private String url;
	
	/** 点击数 */
	private int hits;
	
	/** 作者 */
	private String author;
	
	/** 摘要 */
	private String brief;
	
	/** 关键字 */
	private String keyword;
	
	/** 文章创建者（录入人） */
	private User creator;
	
	/** 审核人 */
	private User auditor;
	
	/** 显示顺序 */
	private int orders;
	
	/** 创建时间 */
	private Date createTime = new Date();
	
	/** 显示时间 */
	private Date displayTime = new Date();
	
	/** 发布时间 */
	private Date publishTime;
	
	/** 失效时间 */
	private Date invalidTime;
	
	/** 审核时间 */
	private Date auditTime;
	
	/** 引用的文章 */
	private Article referedArticle;
	
	/** 是否是引用文章 */
	private boolean ref;
	
	/** 是否置顶 */
	private boolean toped;
	
	/** 初始化地址**/
	private String initUrl;
	
	/** 关键词过滤开关**/
	private boolean keyFilter;
	
	private Date date1;
	private Date date2;
	private Date date3;
	private Date date4;
	private Date date5;
	private Date date6;
	private Date date7;
	private Date date8;
	private Date date9;
	private Date date10;
	private String text1;
	private String text2;
	private String text3;
	private String text4;
	private String text5;
	private String text6;
	private String text7;
	private String text8;
	private String text9;
	private String text10;
	private String text11;
	private String text12;
	private String text13;
	private String text14;
	private String text15;
	private String text16;
	private String text17;
	private String text18;
	private String text19;
	private String text20;
	private String text21;
	private String text22;
	private String text23;
	private String text24;
	private String text25;
	private String textArea1;
	private String textArea2;
	private String textArea3;
	private String textArea4;
	private String textArea5;
	private String textArea6;
	private String textArea7;
	private String textArea8;
	private String textArea9;
	private String textArea10;
	private String pic1;
	private String pic2;
	private String pic3;
	private String pic4;
	private String pic5;
	private String pic6;
	private String pic7;
	private String pic8;
	private String pic9;
	private String pic10;
	private String attach1;
	private String attach2;
	private String attach3;
	private String attach4;
	private String attach5;
	private String attach6;
	private String attach7;
	private String attach8;
	private String attach9;
	private String attach10;
	private String media1;
	private String media2;
	private String media3;
	private String media4;
	private String media5;
	private String media6;
	private String media7;
	private String media8;
	private String media9;
	private String media10;
	private long integer1;
	private long integer2;
	private long integer3;
	private long integer4;
	private long integer5;
	private long integer6;
	private long integer7;
	private long integer8;
	private long integer9;
	private long integer10;
	private Float float1;
	private Float float2;
	private Float float3;
	private Float float04;
	private Float float5;
	private Float float6;
	private Float float7;
	private Float float08;
	private Float float9;
	private Float float10;
	private boolean bool1;
	private boolean bool2;
	private boolean bool3;
	private boolean bool4;
	private boolean bool5;
	private boolean bool6;
	private boolean bool7;
	private boolean bool8;
	private boolean bool9;
	private boolean bool10;
	private String enumeration1;
	private String enumeration2;
	private String enumeration3;
	private String enumeration4;
	private String enumeration5;

	public Article() {
		// null
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Column getColumn() {
		return column;
	}

	public void setColumn(Column column) {
		this.column = column;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public ArticleFormat getArticleFormat() {
		return articleFormat;
	}

	public void setArticleFormat(ArticleFormat articleFormat) {
		this.articleFormat = articleFormat;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public boolean isAudited() {
		return audited;
	}

	public void setAudited(boolean audited) {
		this.audited = audited;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getLeadingTitle() {
		return leadingTitle;
	}

	public void setLeadingTitle(String leadingTitle) {
		this.leadingTitle = leadingTitle;
	}

	public String getInfoSource() {
		return infoSource;
	}

	public void setInfoSource(String infoSource) {
		this.infoSource = infoSource;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public User getAuditor() {
		return auditor;
	}

	public void setAuditor(User auditor) {
		this.auditor = auditor;
	}

	public int getOrders() {
		return orders;
	}

	public void setOrders(int orders) {
		this.orders = orders;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getDisplayTime() {
		return displayTime;
	}

	public void setDisplayTime(Date displayTime) {
		this.displayTime = displayTime;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public Date getInvalidTime() {
		return invalidTime;
	}

	public void setInvalidTime(Date invalidTime) {
		this.invalidTime = invalidTime;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public Article getReferedArticle() {
		return referedArticle;
	}

	public void setReferedArticle(Article referedArticle) {
		this.referedArticle = referedArticle;
	}

	public boolean isRef() {
		return ref;
	}

	public void setRef(boolean ref) {
		this.ref = ref;
	}
	
	public Date getDate1() {
		return date1;
	}

	public void setDate1(Date date1) {
		this.date1 = date1;
	}

	public Date getDate2() {
		return date2;
	}

	public void setDate2(Date date2) {
		this.date2 = date2;
	}

	public Date getDate3() {
		return date3;
	}

	public void setDate3(Date date3) {
		this.date3 = date3;
	}

	public Date getDate4() {
		return date4;
	}

	public void setDate4(Date date4) {
		this.date4 = date4;
	}

	public Date getDate5() {
		return date5;
	}

	public void setDate5(Date date5) {
		this.date5 = date5;
	}

	public Date getDate6() {
		return date6;
	}

	public void setDate6(Date date6) {
		this.date6 = date6;
	}

	public Date getDate7() {
		return date7;
	}

	public void setDate7(Date date7) {
		this.date7 = date7;
	}

	public Date getDate8() {
		return date8;
	}

	public void setDate8(Date date8) {
		this.date8 = date8;
	}

	public Date getDate9() {
		return date9;
	}

	public void setDate9(Date date9) {
		this.date9 = date9;
	}

	public Date getDate10() {
		return date10;
	}

	public void setDate10(Date date10) {
		this.date10 = date10;
	}

	public String getText1() {
		return text1;
	}

	public void setText1(String text1) {
		this.text1 = text1;
	}

	public String getText2() {
		return text2;
	}

	public void setText2(String text2) {
		this.text2 = text2;
	}

	public String getText3() {
		return text3;
	}

	public void setText3(String text3) {
		this.text3 = text3;
	}

	public String getText4() {
		return text4;
	}

	public void setText4(String text4) {
		this.text4 = text4;
	}

	public String getText5() {
		return text5;
	}

	public void setText5(String text5) {
		this.text5 = text5;
	}

	public String getText6() {
		return text6;
	}

	public void setText6(String text6) {
		this.text6 = text6;
	}

	public String getText7() {
		return text7;
	}

	public void setText7(String text7) {
		this.text7 = text7;
	}

	public String getText8() {
		return text8;
	}

	public void setText8(String text8) {
		this.text8 = text8;
	}

	public String getText9() {
		return text9;
	}

	public void setText9(String text9) {
		this.text9 = text9;
	}

	public String getText10() {
		return text10;
	}

	public void setText10(String text10) {
		this.text10 = text10;
	}

	public String getText11() {
		return text11;
	}

	public void setText11(String text11) {
		this.text11 = text11;
	}

	public String getText12() {
		return text12;
	}

	public void setText12(String text12) {
		this.text12 = text12;
	}

	public String getText13() {
		return text13;
	}

	public void setText13(String text13) {
		this.text13 = text13;
	}

	public String getText14() {
		return text14;
	}

	public void setText14(String text14) {
		this.text14 = text14;
	}

	public String getText15() {
		return text15;
	}

	public void setText15(String text15) {
		this.text15 = text15;
	}

	public String getText16() {
		return text16;
	}

	public void setText16(String text16) {
		this.text16 = text16;
	}

	public String getText17() {
		return text17;
	}

	public void setText17(String text17) {
		this.text17 = text17;
	}

	public String getText18() {
		return text18;
	}

	public void setText18(String text18) {
		this.text18 = text18;
	}

	public String getText19() {
		return text19;
	}

	public void setText19(String text19) {
		this.text19 = text19;
	}

	public String getText20() {
		return text20;
	}

	public void setText20(String text20) {
		this.text20 = text20;
	}

	public String getText21() {
		return text21;
	}

	public void setText21(String text21) {
		this.text21 = text21;
	}

	public String getText22() {
		return text22;
	}

	public void setText22(String text22) {
		this.text22 = text22;
	}

	public String getText23() {
		return text23;
	}

	public void setText23(String text23) {
		this.text23 = text23;
	}

	public String getText24() {
		return text24;
	}

	public void setText24(String text24) {
		this.text24 = text24;
	}

	public String getText25() {
		return text25;
	}

	public void setText25(String text25) {
		this.text25 = text25;
	}

	public String getTextArea1() {
		return textArea1;
	}

	public void setTextArea1(String textArea1) {
		this.textArea1 = textArea1;
	}

	public String getTextArea2() {
		return textArea2;
	}

	public void setTextArea2(String textArea2) {
		this.textArea2 = textArea2;
	}

	public String getTextArea3() {
		return textArea3;
	}

	public void setTextArea3(String textArea3) {
		this.textArea3 = textArea3;
	}

	public String getTextArea4() {
		return textArea4;
	}

	public void setTextArea4(String textArea4) {
		this.textArea4 = textArea4;
	}

	public String getTextArea5() {
		return textArea5;
	}

	public void setTextArea5(String textArea5) {
		this.textArea5 = textArea5;
	}

	public String getTextArea6() {
		return textArea6;
	}

	public void setTextArea6(String textArea6) {
		this.textArea6 = textArea6;
	}

	public String getTextArea7() {
		return textArea7;
	}

	public void setTextArea7(String textArea7) {
		this.textArea7 = textArea7;
	}

	public String getTextArea8() {
		return textArea8;
	}

	public void setTextArea8(String textArea8) {
		this.textArea8 = textArea8;
	}

	public String getTextArea9() {
		return textArea9;
	}

	public void setTextArea9(String textArea9) {
		this.textArea9 = textArea9;
	}

	public String getTextArea10() {
		return textArea10;
	}

	public void setTextArea10(String textArea10) {
		this.textArea10 = textArea10;
	}

	public String getPic1() {
		return pic1;
	}

	public void setPic1(String pic1) {
		this.pic1 = pic1;
	}

	public String getPic2() {
		return pic2;
	}

	public void setPic2(String pic2) {
		this.pic2 = pic2;
	}

	public String getPic3() {
		return pic3;
	}

	public void setPic3(String pic3) {
		this.pic3 = pic3;
	}

	public String getPic4() {
		return pic4;
	}

	public void setPic4(String pic4) {
		this.pic4 = pic4;
	}

	public String getPic5() {
		return pic5;
	}

	public void setPic5(String pic5) {
		this.pic5 = pic5;
	}

	public String getPic6() {
		return pic6;
	}

	public void setPic6(String pic6) {
		this.pic6 = pic6;
	}

	public String getPic7() {
		return pic7;
	}

	public void setPic7(String pic7) {
		this.pic7 = pic7;
	}

	public String getPic8() {
		return pic8;
	}

	public void setPic8(String pic8) {
		this.pic8 = pic8;
	}

	public String getPic9() {
		return pic9;
	}

	public void setPic9(String pic9) {
		this.pic9 = pic9;
	}

	public String getPic10() {
		return pic10;
	}

	public void setPic10(String pic10) {
		this.pic10 = pic10;
	}

	public String getAttach1() {
		return attach1;
	}

	public void setAttach1(String attach1) {
		this.attach1 = attach1;
	}

	public String getAttach2() {
		return attach2;
	}

	public void setAttach2(String attach2) {
		this.attach2 = attach2;
	}

	public String getAttach3() {
		return attach3;
	}

	public void setAttach3(String attach3) {
		this.attach3 = attach3;
	}

	public String getAttach4() {
		return attach4;
	}

	public void setAttach4(String attach4) {
		this.attach4 = attach4;
	}

	public String getAttach5() {
		return attach5;
	}

	public void setAttach5(String attach5) {
		this.attach5 = attach5;
	}

	public String getAttach6() {
		return attach6;
	}

	public void setAttach6(String attach6) {
		this.attach6 = attach6;
	}

	public String getAttach7() {
		return attach7;
	}

	public void setAttach7(String attach7) {
		this.attach7 = attach7;
	}

	public String getAttach8() {
		return attach8;
	}

	public void setAttach8(String attach8) {
		this.attach8 = attach8;
	}

	public String getAttach9() {
		return attach9;
	}

	public void setAttach9(String attach9) {
		this.attach9 = attach9;
	}

	public String getAttach10() {
		return attach10;
	}

	public void setAttach10(String attach10) {
		this.attach10 = attach10;
	}

	public String getMedia1() {
		return media1;
	}

	public void setMedia1(String media1) {
		this.media1 = media1;
	}

	public String getMedia2() {
		return media2;
	}

	public void setMedia2(String media2) {
		this.media2 = media2;
	}

	public String getMedia3() {
		return media3;
	}

	public void setMedia3(String media3) {
		this.media3 = media3;
	}

	public String getMedia4() {
		return media4;
	}

	public void setMedia4(String media4) {
		this.media4 = media4;
	}

	public String getMedia5() {
		return media5;
	}

	public void setMedia5(String media5) {
		this.media5 = media5;
	}

	public String getMedia6() {
		return media6;
	}

	public void setMedia6(String media6) {
		this.media6 = media6;
	}

	public String getMedia7() {
		return media7;
	}

	public void setMedia7(String media7) {
		this.media7 = media7;
	}

	public String getMedia8() {
		return media8;
	}

	public void setMedia8(String media8) {
		this.media8 = media8;
	}

	public String getMedia9() {
		return media9;
	}

	public void setMedia9(String media9) {
		this.media9 = media9;
	}

	public String getMedia10() {
		return media10;
	}

	public void setMedia10(String media10) {
		this.media10 = media10;
	}

	public long getInteger1() {
		return integer1;
	}

	public void setInteger1(long integer1) {
		this.integer1 = integer1;
	}

	public long getInteger2() {
		return integer2;
	}

	public void setInteger2(long integer2) {
		this.integer2 = integer2;
	}

	public long getInteger3() {
		return integer3;
	}

	public void setInteger3(long integer3) {
		this.integer3 = integer3;
	}

	public long getInteger4() {
		return integer4;
	}

	public void setInteger4(long integer4) {
		this.integer4 = integer4;
	}

	public long getInteger5() {
		return integer5;
	}

	public void setInteger5(long integer5) {
		this.integer5 = integer5;
	}

	public long getInteger6() {
		return integer6;
	}

	public void setInteger6(long integer6) {
		this.integer6 = integer6;
	}

	public long getInteger7() {
		return integer7;
	}

	public void setInteger7(long integer7) {
		this.integer7 = integer7;
	}

	public long getInteger8() {
		return integer8;
	}

	public void setInteger8(long integer8) {
		this.integer8 = integer8;
	}

	public long getInteger9() {
		return integer9;
	}

	public void setInteger9(long integer9) {
		this.integer9 = integer9;
	}

	public long getInteger10() {
		return integer10;
	}

	public void setInteger10(long integer10) {
		this.integer10 = integer10;
	}

	public Float getFloat1() {
		return float1;
	}

	public void setFloat1(Float float1) {
		this.float1 = float1;
	}

	public Float getFloat2() {
		return float2;
	}

	public void setFloat2(Float float2) {
		this.float2 = float2;
	}

	public Float getFloat3() {
		return float3;
	}

	public void setFloat3(Float float3) {
		this.float3 = float3;
	}

	public Float getFloat04() {
		return float04;
	}

	public void setFloat04(Float float04) {
		this.float04 = float04;
	}

	public Float getFloat5() {
		return float5;
	}

	public void setFloat5(Float float5) {
		this.float5 = float5;
	}

	public Float getFloat6() {
		return float6;
	}

	public void setFloat6(Float float6) {
		this.float6 = float6;
	}

	public Float getFloat7() {
		return float7;
	}

	public void setFloat7(Float float7) {
		this.float7 = float7;
	}

	public Float getFloat08() {
		return float08;
	}

	public void setFloat08(Float float08) {
		this.float08 = float08;
	}

	public Float getFloat9() {
		return float9;
	}

	public void setFloat9(Float float9) {
		this.float9 = float9;
	}

	public Float getFloat10() {
		return float10;
	}

	public void setFloat10(Float float10) {
		this.float10 = float10;
	}

	public boolean isBool1() {
		return bool1;
	}

	public void setBool1(boolean bool1) {
		this.bool1 = bool1;
	}

	public boolean isBool2() {
		return bool2;
	}

	public void setBool2(boolean bool2) {
		this.bool2 = bool2;
	}

	public boolean isBool3() {
		return bool3;
	}

	public void setBool3(boolean bool3) {
		this.bool3 = bool3;
	}

	public boolean isBool4() {
		return bool4;
	}

	public void setBool4(boolean bool4) {
		this.bool4 = bool4;
	}

	public boolean isBool5() {
		return bool5;
	}

	public void setBool5(boolean bool5) {
		this.bool5 = bool5;
	}

	public boolean isBool6() {
		return bool6;
	}

	public void setBool6(boolean bool6) {
		this.bool6 = bool6;
	}

	public boolean isBool7() {
		return bool7;
	}

	public void setBool7(boolean bool7) {
		this.bool7 = bool7;
	}

	public boolean isBool8() {
		return bool8;
	}

	public void setBool8(boolean bool8) {
		this.bool8 = bool8;
	}

	public boolean isBool9() {
		return bool9;
	}

	public void setBool9(boolean bool9) {
		this.bool9 = bool9;
	}

	public boolean isBool10() {
		return bool10;
	}

	public void setBool10(boolean bool10) {
		this.bool10 = bool10;
	}


	public String getPublishState() {
		return publishState;
	}

	public void setPublishState(String publishState) {
		this.publishState = publishState;
	}

	public void setEnumeration1(String enumeration1) {
		this.enumeration1 = enumeration1;
	}

	public String getEnumeration1() {
		return enumeration1;
	}

	public void setEnumeration2(String enumeration2) {
		this.enumeration2 = enumeration2;
	}

	public String getEnumeration2() {
		return enumeration2;
	}

	public void setEnumeration3(String enumeration3) {
		this.enumeration3 = enumeration3;
	}

	public String getEnumeration3() {
		return enumeration3;
	}

	public void setEnumeration4(String enumeration4) {
		this.enumeration4 = enumeration4;
	}

	public String getEnumeration4() {
		return enumeration4;
	}

	public void setEnumeration5(String enumeration5) {
		this.enumeration5 = enumeration5;
	}

	public String getEnumeration5() {
		return enumeration5;
	}

	public void setToped(boolean toped) {
		this.toped = toped;
	}

	public boolean isToped() {
		return toped;
	}

	public void setInitUrl(String initUrl) {
		this.initUrl = initUrl;
	}

	public String getInitUrl() {
		return initUrl;
	}

	public void setKeyFilter(boolean keyFilter) {
		this.keyFilter = keyFilter;
	}

	public boolean isKeyFilter() {
		return keyFilter;
	}

}
