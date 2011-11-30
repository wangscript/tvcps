
	/************************************定义标签****************************************************/
	
	/** for正则 */
	var TITLELINKPAGE_REGEX_FOR = new RegExp("<!--for-->(.*)<!--/for-->");
	/**  条件判断 */
	var TITLELINKPAGE_IF  = new RegExp("<!--if-->(.*)<!--else-->(.*)<!--/if-->");
	/**  条件判断开始 */
	var TITLELINKPAGE_IF_START  =  new RegExp("<!--if-->(.*)<!--else-->");
	/**  条件判断结束 */
	var TITLELINKPAGE_IF_END  = new RegExp("<!--else-->(.*)<!--/if-->");
	/** 标签正则 */
	var TITLELINKPAGE_REGEX_LABEL =  /<!--([\w]+)-->/;

	/** 网站名称 */
	var TITLELINKPAGE_SITE_NAME = "<!--siteName-->";
	/** 网站链接 */
	var TITLELINKPAGE_SITE_LINK = "<!--siteLink-->";
	/** 栏目名称 */
	var TITLELINKPAGE_COLUMN_NAME =  "<!--columnName-->";
	/** 栏目链接 */
	var TITLELINKPAGE_COLUMNLINK = "<!--columnLink-->";
	/**  标题 */
	var TITLELINKPAGE_ARTICLETITLE  = "<!--articletitle-->";
	/** 副标题 */
	var TITLELINKPAGE_ARTICLESUBTITLE = "<!--articlesubtitle-->";
	/**  标题链接 */
	var TITLELINKPAGE_ARTICLEURL  = "<!--articleurl-->";
	/** 引题 */
	var TITLELINKPAGE_ARTICLEQUOTETITLE = "<--articlequotetitle-->";
	/** 摘要 */
	var TITLELINKPAGE_ARTICLEBRIEF = "<!--articlebrief-->";
	/** 作者 */
	var TITLELINKPAGE_ARTICLEAUTHOR = "<!--articleauthor-->";
	/** 点击次数 */
	var TITLELINKPAGE_ARTICLEHITS = "<!--articlehits-->";
	/** 四位年 */
	var YEAR4 = "<!--year4-->";
	/** 两位年 */
	var YEAR2 = "<!--year2-->";
	/** 两位月 */
	var MONTH2 = "<!--month2-->";
	/** 一位月 */
	var MONTH1 = "<!--month1-->";
	/** 两位日 */
	var DAY2 = "<!--day2-->";
	/** 一位日 */
	var DAY1 = "<!--day1-->";
	/** 时 */
	var HOUR = "<!--hour-->";
	/** 分 */
	var MINUTE = "<!--minute-->";
	/** 秒 */
	var SECOND = "<!--second-->";
	/** 创建时间 */
	var TITLELINKPAGE_ARTICLECREATEDATE  = "<!--createTime-->";	
	/** 显示时间 */
	var TITLELINKPAGE_DISPLAYTIME = "<!--displayTime-->";
	/** 审核时间 */
	var TITLELINKPAGE_AUDITTIME = "<!--auditTime-->";
	/** 发布时间 */
	var TITLELINKPAGE_PUBLISHTIME = "<!--publishTime-->";
	/** 失效时间 */
	var TITLELINKPAGE_INVALIDTIME = "<!--invalidTime-->";
	/** 图片 */
	var PIC1 = "<!--pic1-->";
	/** 标题前缀 */
	var TITLEPREFIX  = "<!--titlePrefix-->";
	/** 标题后缀 */
	var TITLESUFFIX  = "<!--titleSuffix-->";
	/** 更多 */
	var MORE = "<!--more-->";
	/** 更多链接地址 */
	var MORELINK = "<!--moreLink-->";
	
	/***********************************获得xml里面的内容*****************************************************/

	// 单元类型
	var TunitStyle;
	// 内容来源
	var Tsource;
	// 指定栏目
	var TfixedColumn;
	// 每页信息数
	var TpageInfoCount;
	// 标题字数
	var TtitleLimit;
	// 概要字数
	var TbriefLimit;
	// 标题前缀
	var TtitlePrefix;
	// 标题前缀图片
	var TtitlePrefixPic;
	// 标题前缀有效期
	var TtitlePrefixValidity;
	// 标题后缀
	var TtitleSuffix;
	// 标题后缀图片
	var TtitleSuffixPic;
	// 标题后缀有效期
	var TtitleSuffixValidity;
	// 效果源码（即将解析的代码）
	var ThtmlContent; 
	// 前缀到期时间
	var TprefixDate;
	// 后缀到期时间
	var TsuffixDate;

	// 获得配置的xml文件信息 
	function getTitleLinkPageXmlContent(titleLinkPageXml) {
		// 导入最新信息xml
		importXML(titleLinkPageXml);		
		if(moz) { 
			
		} else {
			var xmlfile = xmlDoc.selectNodes("/j2ee.cms/title-link-page");
			TunitStyle = xmlfile[0].getElementsByTagName("viewStyle")[0].firstChild.nodeValue;
			Tsource = xmlfile[0].getElementsByTagName("contextFrom")[0].firstChild.nodeValue;
			TfixedColumn = xmlfile[0].getElementsByTagName("fixedColumn")[0].firstChild.nodeValue;
			TpageInfoCount = xmlfile[0].getElementsByTagName("pageInfoCount")[0].firstChild.nodeValue;
			TtitleLimit = xmlfile[0].getElementsByTagName("titleLimit")[0].firstChild.nodeValue;
			TbriefLimit = xmlfile[0].getElementsByTagName("briefLimit")[0].firstChild.nodeValue;
			TtitlePrefix = xmlfile[0].getElementsByTagName("titleHead")[0].firstChild.nodeValue;
			TtitlePrefixPic = xmlfile[0].getElementsByTagName("titleHeadPic")[0].firstChild.nodeValue;
			TtitleSuffix = xmlfile[0].getElementsByTagName("titleEnd")[0].firstChild.nodeValue;
			TtitleSuffixPic = xmlfile[0].getElementsByTagName("titleEndPic")[0].firstChild.nodeValue;
			// 替换解析代码中的回撤(即换行)
			ThtmlContent = xmlfile[0].getElementsByTagName("htmlContent")[0].firstChild.nodeValue;
			var reg = new RegExp("\r\n","g"); 
			var reg1 = new RegExp(" ","g"); 
			var code = ThtmlContent.replace(reg,"<br>"); 
			ThtmlContent = code.replace(reg1," ");

			TtitlePrefixValidity = xmlfile[0].getElementsByTagName("titleHeadValidity")[0].firstChild.nodeValue;	
			TtitleSuffixValidity = xmlfile[0].getElementsByTagName("titleEndValidity")[0].firstChild.nodeValue;	
			TprefixDate = xmlfile[0].getElementsByTagName("prefixDate")[0].firstChild.nodeValue;
			TsuffixDate = xmlfile[0].getElementsByTagName("suffixDate")[0].firstChild.nodeValue;
		}
	}


	/************************定义文章对象******************************************************************************/ 
	var TsiteName      // 网站名称
	var TsiteLink      // 网站链接
	var TcolumnName;   // 栏目名称
	var TcolumnLink;   // 栏目链接
	var Ttitle;        // 文章标题
	var Turl;          // 文章链接
	var TsubTitle;     // 文章副标题
	var TquoteTitle;   // 引题
	var Tbrief;        // 摘要
	var Tauthor;       // 作者
	var Thits;         // 点击次数
	var TcreateTime;   // 创建时间
	var TdisplayTime;  // 显示时间
	var TpublishTime;  // 发布时间
	var TinvalidTime;  // 失效时间
	var TauditTime;    // 审核时间
	var Tpic1;         // 图片

	var infoSource;    // 信息来源
	var keyword;       // 关键字
	var deleted;       // 是否删除
	var audited;       // 是否审核
	var publishState;  // 发布状态
	var keyFilter;     // 关键词过滤
	var toped;         // 置顶
	// 日期 date   10
	/**var date1;
	var date2;
	var date3;
	var date4;
	var date5;
	var date6;
	var date7;
	var date8;
	var date9;
	var date10;
	// 文本类型 text 25
	var text1;
	var text2;
	var text3
	var text4;
	var text5;
	var text6;
	var text7;
	var text8;
	var text9;
	var text10;
	var text11;
	var text12;
	var text13;
	var text14;
	var text15;
	var text16;
	var text17;
	var text18;
	var text19;
	var text20;
	var text21;
	var text22;
	var text23;
	var text24;
	var text25;
	// 文本域类型  textArea 10
 	var textArea1;
	var textArea2;
	var textArea3;
	var textArea4;
	var textArea5;
	var textArea6;
	var textArea7;
	var textArea8;
	var textArea9;
	var textArea10; 
	// 整型   integer 10  
	var integer1;
	var integer2;
	var integer3;
	var integer4;
	var integer5;
	var integer6;
	var integer7;
	var integer8;
	var integer9;
	var integer10;
	// 浮点型  float 10 
	var float1;
	var float2;
	var float3;
	var float04;
	var float5;
	var float6;
	var float7;
	var float08;
	var float9;
	var float10;
	// 布尔型 bool   10
	var bool1;
	var bool2;
	var bool3;
	var bool4;
	var bool5;
	var bool6;
	var bool7;
	var bool8;
	var bool9;
	var bool10;
	// 图片 pic      2~10  
	var pic2;
	var pic3;
	var pic4;
	var pic5;
	var pic6;
	var pic7;
	var pic8;
	var pic9;
	var pic10;
	// 附件 attach   10
	var attach1;
	var attach2;
	var attach3;
	var attach4;
	var attach5;
	var attach6;
	var attach7;
	var attach8;
	var attach9;
	var attach10;
	// 媒体 media    10
	var media1;
	var media2;
	var media3;
	var media4;
	var media5;
	var media6;
	var media7;
	var media8;
	var media9;
	var media10;
	// 枚举 enumeration 5
	var enumeration1;
	var enumeration2;
	var enumeration3;
	var enumeration4;
	var enumeration5;*/
		
	function TArticle(siteName, siteLink, columnName, columnLink, title, url, subtitle, quotetitle, brief, author, hits, createtime, displaytime, publishtime, invalidtime, audittime, pic1, infoSource, keyword, deleted, audited, publishState, keyFilter, toped) {
		this.TsiteName = siteName;
		this.TsiteLink = siteLink;
		this.TcolumnName = columnName;
		this.TcolumnLink = columnLink;
		this.Ttitle = title;
		this.Turl = url;
		this.TsubTitle = subtitle;
		this.TquoteTitle = quotetitle;
		this.Tbrief = brief;
		this.Tauthor = author;
		this.Thits = hits;
		this.TcreateTime = createtime;
		this.TdisplayTime = displaytime;
		this.TpublishTime = publishtime;
		this.TinvalidTime = invalidtime;
		this.TauditTime = audittime;
		this.Tpic1 = pic1;

		this.infoSource = infoSource;   
		this.keyword = keyword;       
		this.deleted = deleted;       
		this.audited = audited;      
		this.publishState = publishState;  
		this.keyFilter = keyFilter;     // 关键词过滤
		this.toped = toped;         // 置顶
		// 日期 date   10
	/**	this.date1 = date1;
		this.date2 = date2;
		this.date3 = date3;
		this.date4 = date4;
		this.date5 = date5;
		this.date6 = date6;
		this.date7 = date7;
		this.date8 = date8;
		this.date9 = date9;
		this.date10 = date10;
		// 文本类型 text 25
		this.text1 = text1;
		this.text2 = text2;
		this.text3 = text3;
		this.text4 = text4;
		this.text5 = text5;
		this.text6 = text6;
		this.text7 = text7;
		this.text8 = text8;
		this.text9 = text9;
		this.text10 = text10;
		this.text11 = text11;
		this.text12 = text12;
		this.text13 = text13;
		this.text14 = text14;
		this.text15 = text15;
		this.text16 = text16;
		this.text17 = text17;
		this.text18 = text18;
		this.text19 = text19;
		this.text20 = text20;
		this.text21 = text21;
		this.text22 = text22;
		this.text23 = text23;
		this.text24 = text24;
		this.text25 = text25;
		// 文本域类型  textArea 10
	 	this.textArea1 = textArea1;
		this.textArea2 = textArea2;
		this.textArea3 = textArea3;
		this.textArea4 = textArea4;
		this.textArea5 = textArea5;
		this.textArea6 = textArea6;
		this.textArea7 = textArea7;
		this.textArea8 = textArea8;
		this.textArea9 = textArea9;
		this.textArea10 = textArea10; 
		// 整型   integer 10
		this.integer1 = integer1;
		this.integer2 = integer2;
		this.integer3 = integer3;
		this.integer4 = integer4;
		this.integer5 = integer5;
		this.integer6 = integer6;
		this.integer7 = integer7;
		this.integer8 = integer8;
		this.integer9 = integer9;
		this.integer10 = integer10;
		// 浮点型  float 10
		this.float1 = float1;
		this.float2 = float2;
		this.float3 = float3;
		this.float04 = float04;
		this.float5 = float5;
		this.float6 = float6;
		this.float7 = float7;
		this.float08 = float08;
		this.float9 = float9;
		this.float10 = float10;
		// 布尔型 bool   10
		this.bool1 = bool1;
		this.bool2 = bool2;
		this.bool3 = bool3;
		this.bool4 = bool4;
		this.bool5 = bool5;
		this.bool6 = bool6;
		this.bool7 = bool7;
		this.bool8 = bool8;
		this.bool9 = bool9;
		this.bool10 = bool10;
		// 图片 pic      2~10
		this.pic2 = pic2;
		this.pic3 = pic3;
		this.pic4 = pic4;
		this.pic5 = pic5;
		this.pic6 = pic6;
		this.pic7 = pic7;
		this.pic8 = pic8;
		this.pic9 = pic9
		this.pic10 = pic10;
		// 附件 attach   10
		this.attach1 = attach1;
		this.attach2 = attach2;
		this.attach3 = attach3;
		this.attach4 = attach4;
		this.attach5 = attach5;
		this.attach6 = attach6;
		this.attach7 = attach7;
		this.attach8 = attach8;
		this.attach9 = attach9;
		this.attach10 = attach10;
		// 媒体 media    10
		this.media1 = media1;
		this.media2 = media2;
		this.media3 = media3;
		this.media4 = media4;
		this.media5 = media5;
		this.media6 = media6;
		this.media7 = media7;
		this.media8 = media8;
		this.media9 = media9;
		this.media10 = media10;
		// 枚举 enumeration 5
		this.enumeration1 = enumeration1;
		this.enumeration2 = enumeration2;
		this.enumeration3 = enumeration3;
		this.enumeration4 = enumeration4;
		this.enumeration5 = enumeration5;*/
	}


	/************************获取所有文章的信息并放入数组*************************************************************************/

	function TgetArticles(articleUrl, flag) {
		// 导入要显示的文章的配置xml
		importXML(articleUrl);
		var arr = new Array();	
		var xmlfile = null;
		if(flag == "titleLinkPage") {
			xmlfile = xmlDoc.selectSingleNode("/j2ee.cms/article-page");
		} else {
			xmlfile = xmlDoc.selectSingleNode("/j2ee.cms/article-page");
		}
		var articles = xmlfile.childNodes;
		var articleArray = new Array();
		for (var i = 0; i < articles.length; i++) {
			var siteName = articles[i].getElementsByTagName("siteName")[0].firstChild.nodeValue;
			var siteLink = articles[i].getElementsByTagName("siteLink")[0].firstChild.nodeValue;
			var columnName = articles[i].getElementsByTagName("columnName")[0].firstChild.nodeValue;
			var columnLink = articles[i].getElementsByTagName("columnLink")[0].firstChild.nodeValue;
			var articletitle = articles[i].getElementsByTagName("articletitle")[0].firstChild.nodeValue;
			var articleurl = articles[i].getElementsByTagName("articleurl")[0].firstChild.nodeValue;
			var articlesubtitle = articles[i].getElementsByTagName("articlesubtitle")[0].firstChild.nodeValue;
			var articlequotetitle = articles[i].getElementsByTagName("articlequotetitle")[0].firstChild.nodeValue;
			var articlebrief = articles[i].getElementsByTagName("articlebrief")[0].firstChild.nodeValue;
			var articleauthor = articles[i].getElementsByTagName("articleauthor")[0].firstChild.nodeValue;
			var articlehits = articles[i].getElementsByTagName("articlehits")[0].firstChild.nodeValue;
			var createTime = articles[i].getElementsByTagName("createTime")[0].firstChild.nodeValue;
			var displayTime = articles[i].getElementsByTagName("displayTime")[0].firstChild.nodeValue;
			var auditTime = articles[i].getElementsByTagName("auditTime")[0].firstChild.nodeValue;
			var publishTime = articles[i].getElementsByTagName("publishTime")[0].firstChild.nodeValue;
			var invalidTime = articles[i].getElementsByTagName("invalidTime")[0].firstChild.nodeValue;
			var pic1 = articles[i].getElementsByTagName("pic1")[0].firstChild.nodeValue;
			
			var infoSource = articles[i].getElementsByTagName("infoSource")[0].firstChild.nodeValue;
			var keyword = articles[i].getElementsByTagName("keyword")[0].firstChild.nodeValue;
			var deleted = articles[i].getElementsByTagName("deleted")[0].firstChild.nodeValue;
			var audited = articles[i].getElementsByTagName("audited")[0].firstChild.nodeValue;
			var publishState = articles[i].getElementsByTagName("publishState")[0].firstChild.nodeValue;
			var keyFilter = articles[i].getElementsByTagName("keyFilter")[0].firstChild.nodeValue;
			var toped = articles[i].getElementsByTagName("toped")[0].firstChild.nodeValue;
	/**		var date1 = articles[i].getElementsByTagName("date1")[0].firstChild.nodeValue;
			var date2 = articles[i].getElementsByTagName("date2")[0].firstChild.nodeValue;
			var date3 = articles[i].getElementsByTagName("date3")[0].firstChild.nodeValue;
			var date4 = articles[i].getElementsByTagName("date4")[0].firstChild.nodeValue;
			var date5 = articles[i].getElementsByTagName("date5")[0].firstChild.nodeValue;
			var date6 = articles[i].getElementsByTagName("date6")[0].firstChild.nodeValue;
			var date7 = articles[i].getElementsByTagName("date7")[0].firstChild.nodeValue;
			var date8 = articles[i].getElementsByTagName("date8")[0].firstChild.nodeValue;
			var date9 = articles[i].getElementsByTagName("date9")[0].firstChild.nodeValue;
			var date10 = articles[i].getElementsByTagName("date10")[0].firstChild.nodeValue;
			var text1 = articles[i].getElementsByTagName("text1")[0].firstChild.nodeValue;
			var text2 = articles[i].getElementsByTagName("text2")[0].firstChild.nodeValue;
			var text3 = articles[i].getElementsByTagName("text3")[0].firstChild.nodeValue;
			var text4 = articles[i].getElementsByTagName("text4")[0].firstChild.nodeValue;
			var text5 = articles[i].getElementsByTagName("text5")[0].firstChild.nodeValue;
			var text6 = articles[i].getElementsByTagName("text6")[0].firstChild.nodeValue;
			var text7 = articles[i].getElementsByTagName("text7")[0].firstChild.nodeValue;
			var text8 = articles[i].getElementsByTagName("text8")[0].firstChild.nodeValue;
			var text9 = articles[i].getElementsByTagName("text9")[0].firstChild.nodeValue;
			var text10 = articles[i].getElementsByTagName("text10")[0].firstChild.nodeValue;
			var text11 = articles[i].getElementsByTagName("text11")[0].firstChild.nodeValue;
			var text12 = articles[i].getElementsByTagName("text12")[0].firstChild.nodeValue;
			var text13 = articles[i].getElementsByTagName("text13")[0].firstChild.nodeValue;
			var text14 = articles[i].getElementsByTagName("text14")[0].firstChild.nodeValue;
			var text15 = articles[i].getElementsByTagName("text15")[0].firstChild.nodeValue;
			var text16 = articles[i].getElementsByTagName("text16")[0].firstChild.nodeValue;
			var text17 = articles[i].getElementsByTagName("text17")[0].firstChild.nodeValue;
			var text18 = articles[i].getElementsByTagName("text18")[0].firstChild.nodeValue;
			var text19 = articles[i].getElementsByTagName("text19")[0].firstChild.nodeValue;
			var text20 = articles[i].getElementsByTagName("text20")[0].firstChild.nodeValue;
			var text21 = articles[i].getElementsByTagName("text21")[0].firstChild.nodeValue;
			var text22 = articles[i].getElementsByTagName("text22")[0].firstChild.nodeValue;
			var text23 = articles[i].getElementsByTagName("text23")[0].firstChild.nodeValue;
			var text24 = articles[i].getElementsByTagName("text24")[0].firstChild.nodeValue;
			var text25 = articles[i].getElementsByTagName("text25")[0].firstChild.nodeValue;
		 	var textArea1 = articles[i].getElementsByTagName("textArea1")[0].firstChild.nodeValue;
			var textArea2 = articles[i].getElementsByTagName("textArea2")[0].firstChild.nodeValue;
			var textArea3 = articles[i].getElementsByTagName("textArea3")[0].firstChild.nodeValue;
			var textArea4 = articles[i].getElementsByTagName("textArea4")[0].firstChild.nodeValue;
			var textArea5 = articles[i].getElementsByTagName("textArea5")[0].firstChild.nodeValue;
			var textArea6 = articles[i].getElementsByTagName("textArea6")[0].firstChild.nodeValue;
			var textArea7 = articles[i].getElementsByTagName("textArea7")[0].firstChild.nodeValue;
			var textArea8 = articles[i].getElementsByTagName("textArea8")[0].firstChild.nodeValue;
			var textArea9 = articles[i].getElementsByTagName("textArea9")[0].firstChild.nodeValue;
			var textArea10 = articles[i].getElementsByTagName("textArea10")[0].firstChild.nodeValue; 
			var integer1 = articles[i].getElementsByTagName("integer1")[0].firstChild.nodeValue;
			var integer2 = articles[i].getElementsByTagName("integer2")[0].firstChild.nodeValue;
			var integer3 = articles[i].getElementsByTagName("integer3")[0].firstChild.nodeValue;
			var integer4 = articles[i].getElementsByTagName("integer4")[0].firstChild.nodeValue;
			var integer5 = articles[i].getElementsByTagName("integer5")[0].firstChild.nodeValue;
			var integer6 = articles[i].getElementsByTagName("integer6")[0].firstChild.nodeValue;
			var integer7 = articles[i].getElementsByTagName("integer7")[0].firstChild.nodeValue;
			var integer8 = articles[i].getElementsByTagName("integer8")[0].firstChild.nodeValue;
			var integer9 = articles[i].getElementsByTagName("integer9")[0].firstChild.nodeValue;
			var integer10 = articles[i].getElementsByTagName("integer10")[0].firstChild.nodeValue;
			var float1 = articles[i].getElementsByTagName("float1")[0].firstChild.nodeValue;
			var float2 = articles[i].getElementsByTagName("float2")[0].firstChild.nodeValue;
			var float3 = articles[i].getElementsByTagName("float3")[0].firstChild.nodeValue;
			var float04 = articles[i].getElementsByTagName("float04")[0].firstChild.nodeValue;
			var float5 = articles[i].getElementsByTagName("float5")[0].firstChild.nodeValue;
			var float6 = articles[i].getElementsByTagName("float6")[0].firstChild.nodeValue;
			var float7 = articles[i].getElementsByTagName("float7")[0].firstChild.nodeValue;
			var float08 = articles[i].getElementsByTagName("float08")[0].firstChild.nodeValue;
			var float9 = articles[i].getElementsByTagName("float9")[0].firstChild.nodeValue;
			var float10 = articles[i].getElementsByTagName("float10")[0].firstChild.nodeValue;
			var bool1 = articles[i].getElementsByTagName("bool1")[0].firstChild.nodeValue;
			var bool2 = articles[i].getElementsByTagName("bool2")[0].firstChild.nodeValue;
			var bool3 = articles[i].getElementsByTagName("bool3")[0].firstChild.nodeValue;
			var bool4 = articles[i].getElementsByTagName("bool4")[0].firstChild.nodeValue;
			var bool5 = articles[i].getElementsByTagName("bool5")[0].firstChild.nodeValue;
			var bool6 = articles[i].getElementsByTagName("bool6")[0].firstChild.nodeValue;
			var bool7 = articles[i].getElementsByTagName("bool7")[0].firstChild.nodeValue;
			var bool8 = articles[i].getElementsByTagName("bool8")[0].firstChild.nodeValue;
			var bool9 = articles[i].getElementsByTagName("bool9")[0].firstChild.nodeValue;
			var bool10 = articles[i].getElementsByTagName("bool10")[0].firstChild.nodeValue;
			var pic2 = articles[i].getElementsByTagName("pic2")[0].firstChild.nodeValue;
			var pic3 = articles[i].getElementsByTagName("pic3")[0].firstChild.nodeValue;
			var pic4 = articles[i].getElementsByTagName("pic4")[0].firstChild.nodeValue;
			var pic5 = articles[i].getElementsByTagName("pic5")[0].firstChild.nodeValue;
			var pic6 = articles[i].getElementsByTagName("pic6")[0].firstChild.nodeValue;
			var pic7 = articles[i].getElementsByTagName("pic7")[0].firstChild.nodeValue;
			var pic8 = articles[i].getElementsByTagName("pic8")[0].firstChild.nodeValue;
			var pic9 = articles[i].getElementsByTagName("pic9")[0].firstChild.nodeValue;
			var pic10 = articles[i].getElementsByTagName("pic10")[0].firstChild.nodeValue;
			var attach1 = articles[i].getElementsByTagName("attach1")[0].firstChild.nodeValue;
			var attach2 = articles[i].getElementsByTagName("attach2")[0].firstChild.nodeValue;
			var attach3 = articles[i].getElementsByTagName("attach3")[0].firstChild.nodeValue;
			var attach4 = articles[i].getElementsByTagName("attach4")[0].firstChild.nodeValue;
			var attach5 = articles[i].getElementsByTagName("attach5")[0].firstChild.nodeValue;
			var attach6 = articles[i].getElementsByTagName("attach6")[0].firstChild.nodeValue;
			var attach7 = articles[i].getElementsByTagName("attach7")[0].firstChild.nodeValue;
			var attach8 = articles[i].getElementsByTagName("attach8")[0].firstChild.nodeValue;
			var attach9 = articles[i].getElementsByTagName("attach9")[0].firstChild.nodeValue;
			var attach10 = articles[i].getElementsByTagName("attach10")[0].firstChild.nodeValue;
			var media1 = articles[i].getElementsByTagName("media1")[0].firstChild.nodeValue;
			var media2 = articles[i].getElementsByTagName("media2")[0].firstChild.nodeValue;
			var media3 = articles[i].getElementsByTagName("media3")[0].firstChild.nodeValue;
			var media4 = articles[i].getElementsByTagName("media4")[0].firstChild.nodeValue;
			var media5 = articles[i].getElementsByTagName("media5")[0].firstChild.nodeValue;
			var media6 = articles[i].getElementsByTagName("media6")[0].firstChild.nodeValue;
			var media7 = articles[i].getElementsByTagName("media7")[0].firstChild.nodeValue;
			var media8 = articles[i].getElementsByTagName("media8")[0].firstChild.nodeValue;
			var media9 = articles[i].getElementsByTagName("media9")[0].firstChild.nodeValue;
			var media10 = articles[i].getElementsByTagName("media10")[0].firstChild.nodeValue;
			var enumeration1 = articles[i].getElementsByTagName("enumeration1")[0].firstChild.nodeValue;
			var enumeration2 = articles[i].getElementsByTagName("enumeration2")[0].firstChild.nodeValue;
			var enumeration3 = articles[i].getElementsByTagName("enumeration3")[0].firstChild.nodeValue;
			var enumeration4 = articles[i].getElementsByTagName("enumeration4")[0].firstChild.nodeValue;
			var enumeration5 = articles[i].getElementsByTagName("enumeration5")[0].firstChild.nodeValue;*/

			// 17
			var article = new TArticle(siteName,siteLink,columnName,columnLink,articletitle,articleurl,articlesubtitle,articlequotetitle,articlebrief,articleauthor,articlehits,createTime,displayTime,auditTime,publishTime,invalidTime,pic1,infoSource, keyword, deleted, audited, publishState, keyFilter, toped);
			articleArray.push(article);
		}   
		return articleArray;
	}


	/*********************************获得if之间的数据（解析）*******************************************************************************/

	// 对if之间的标签进行解析 (str 有可能是if和else之间的数据 也有可能是else和if之间的数据)
	function TsubIf(str, article, appName, flag, displayMore) {
		var siteName = article.TsiteName;
		var siteLink = article.TsiteLink;
		var columnName = article.TcolumnName;
		var columnLink = article.TcolumnLink;
		var title = article.Ttitle;
		var url = article.Turl;
		var subtitle = article.TsubTitle;
		var quotetitle = article.TquoteTitle;
		var brief = article.Tbrief;
		var author = article.Tauthor;
		var hits = article.Thits;
		var createtime = article.TcreateTime;
		var displaytime = article.TdisplayTime;
		var publishtime = article.TpublishTime;
		var invalidTime = article.TinvalidTime;
		var auditTime = article.TauditTime;
		var pic1 = article.Tpic1;
		
		// 新增
		var  infoSource = article.infoSource;   
		var  keyword = article.keyword;       
		var  deleted = article.deleted;       
		var  audited = article.audited;      
		var  publishState = article.publishState;  
		var  keyFilter = article.keyFilter;     // 关键词过滤
		var  toped = article.toped;         // 置顶
		// 日期 date   10
	/**	var  date1 = article.date1;
		var  date2 = article.date2;
		var  date3 = article.date3;
		var  date4 = article.date4;
		var  date5 = article.date5;
		var  date6 = article.date6;
		var  date7 = article.date7;
		var  date8 = article.date8;
		var  date9 = article.date9;
		var  date10 = article.date10;
		// 文本类型 text 25
		var  text1 = article.text1;
		var  text2 = article.text2;
		var  text3 = article.text3;
		var  text4 = article.text4;
		var  text5 = article.text5;
		var  text6 = article.text6;
		var  text7 = article.text7;
		var  text8 = article.text8;
		var  text9 = article.text9;
		var  text10 = article.text10;
		var  text11 = article.text11;
		var  text12 = article.text12;
		var  text13 = article.text13;
		var  text14 = article.text14;
		var  text15 = article.text15;
		var  text16 = article.text16;
		var  text17 = article.text17;
		var  text18 = article.text18;
		var  text19 = article.text19;
		var  text20 = article.text20;
		var  text21 = article.text21;
		var  text22 = article.text22;
		var  text23 = article.text23;
		var  text24 = article.text24;
		var  text25 = article.text25;
		// 文本域类型  textArea 10
  		var  textArea1 = article.textArea1;
		var  textArea2 = article.textArea2;
		var  textArea3 = article.textArea3;
		var  textArea4 = article.textArea4;
		var  textArea5 = article.textArea5;
		var  textArea6 = article.textArea6;
		var  textArea7 = article.textArea7;
		var  textArea8 = article.textArea8;
		var  textArea9 = article.textArea9;
		var  textArea10 = article.textArea10; 
		// 整型   integer 10
		var  integer1 = article.integer1;
		var  integer2 = article.integer2;
		var  integer3 = article.integer3;
		var  integer4 = article.integer4;
		var  integer5 = article.integer5;
		var  integer6 = article.integer6;
		var  integer7 = article.integer7;
		var  integer8 = article.integer8;
		var  integer9 = article.integer9;
		var  integer10 = article.integer10;
		// 浮点型  float 10
		var  float1 = article.float1;
		var  float2 = article.float2;
		var  float3 = article.float3;
		var  float04 = article.float04;
		var  float5 = article.float5;
		var  float6 = article.float6;
		var  float7 = article.float7;
		var  float08 = article.float08;
		var  float9 = article.float9;
		var  float10 = article.float10;
		// 布尔型 bool   10
		var  bool1 = article.bool1;
		var  bool2 = article.bool2;
		var  bool3 = article.bool3;
		var  bool4 = article.bool4;
		var  bool5 = article.bool5;
		var  bool6 = article.bool6;
		var  bool7 = article.bool7;
		var  bool8 = article.bool8;
		var  bool9 = article.bool9;
		var  bool10 = article.bool10;
		// 图片 pic      2~10
		var  pic2 = article.pic2;
		var  pic3 = article.pic3;
		var  pic4 = article.pic4;
		var  pic5 = article.pic5;
		var  pic6 = article.pic6;
		var  pic7 = article.pic7;
		var  pic8 = article.pic8;
		var  pic9 = article.pic9
		var  pic10 = article.pic10;
		// 附件 attach   10
		var  attach1 = article.attach1;
		var  attach2 = article.attach2;
		var  attach3 = article.attach3;
		var  attach4 = article.attach4;
		var  attach5 = article.attach5;
		var  attach6 = article.attach6;
		var  attach7 = article.attach7;
		var  attach8 = article.attach8;
		var  attach9 = article.attach9;
		var  attach10 = article.attach10;
		// 媒体 media    10
		var  media1 = article.media1;
		var  media2 = article.media2;
		var  media3 = article.media3;
		var  media4 = article.media4;
		var  media5 = article.media5;
		var  media6 = article.media6;
		var  media7 = article.media7;
		var  media8 = article.media8;
		var  media9 = article.media9;
		var  media10 = article.media10;
		// 枚举 enumeration 5
		var  enumeration1 = article.enumeration1;
		var  enumeration2 = article.enumeration2;
		var  enumeration3 = article.enumeration3;
		var  enumeration4 = article.enumeration4;
		var  enumeration5 = article.enumeration5;
*/


		var arr = "";
		var a = 0;	
		var temp = "";
		var newStr = str;
		while( (arr = TITLELINKPAGE_REGEX_LABEL.exec(newStr)) != null) {
			label = arr[0];
			var left = RegExp.leftContext;
			var right = RegExp.rightContext;
			// 网站名称
			if(label == TITLELINKPAGE_SITE_NAME) {	
				if(a == 0) {
					temp = siteName;
					newStr = left + right;
				} else {
					if(temp != "" && temp != null) {
						newStr = left + siteName + right;
					} else {
						newStr = "";
						break;
					}
				}

			// 网站链接
			} else if(label == TITLELINKPAGE_SITE_LINK) {
				if(a == 0) {
					temp = siteLink;
					newStr = left + right;
				} else {
					if(temp != "" && temp != null) {
						if(!siteLink.startWith("http://")) {
							newStr = left + "/" + appName + siteLink + right;
						} else {
							newStr = left + siteLink + right;
						}
					} else {
						newStr = "";
						break;
					}
				}

			// 栏目名称
			} else if(label == TITLELINKPAGE_COLUMN_NAME) {
				if(a == 0) {
					temp = columnName;
					newStr = left + right;
				} else {
					if(temp != "" && temp != null) {
						newStr = left + columnName + right;
					} else {
						newStr = "";
						break;
					}
				}
	
			// 栏目链接
			} else if(label == TITLELINKPAGE_COLUMNLINK) {
				if(a == 0) {
					temp = columnLink;
					newStr = left + right;
				} else {
					if(temp != "" && temp != null) {
						if(!columnLink.startWith("http://")) {
							newStr = left + "/" + appName + columnLink + right;
						} else {
							newStr = left + columnLink + right;
						}
					} else {
						newStr = "";
						break;
					}
				}
			
			// 标题
			} else if(label == TITLELINKPAGE_ARTICLETITLE) {
				if(a == 0) {
					temp = title;
					newStr = left + right;
				} else {
					if(temp != "" && temp != null) {
						if(title != null) {
							if(title.length < TtitleLimit) {
								newStr = left + title + right;
							} else {
								var b = title.substring(0, TtitleLimit) + "...";
								newStr = left + b + right;
							}	
						} else {
							newStr = left + title + right;
						}
					} else { 
						newStr = "";
						break;
					}
				}

			// 副标题
			} else if(label == TITLELINKPAGE_ARTICLESUBTITLE) {
				if(a == 0) {
					temp = subtitle;
					newStr = left + right;
				} else {
					if(temp != "" && temp != null) {
						newStr = left + subtitle + right;
					} else {
						newStr = "";
						break;
					}
				}
			
			// 标题链接
			} else if(label == TITLELINKPAGE_ARTICLEURL) {
				if(a == 0) {
					temp = url;
					newStr = left + right;
				} else {
					if(temp != "" && temp != null) {
						if(!url.startWith("http://")) {
							newStr = left + "/" + appName + url + right;
						} else {
							newStr = left + url + right;
						}
					} else {
						newStr = "";
						break;
					}
				}

			// 引题
			} else if(label == TITLELINKPAGE_ARTICLEQUOTETITLE) {
				if(a == 0) {
					temp = quotetitle;
					newStr = left + right;
				} else {
					if(temp != "" && temp != null) {
						newStr = left + quotetitle + right;
					} else {
						newStr = "";
						break;
					}
				}

			// 摘要
			} else if(label == TITLELINKPAGE_ARTICLEBRIEF)	{
				if(a == 0) {
					temp = brief;
					newStr = left + right;
				} else {
					if(temp != "" && temp != null) {
						if(brief != null) {
							if(brief.length < TbriefLimit) {
								newStr = left + brief + right;
							} else {
								var c = brief.substring(0, TbriefLimit) + "...";
								newStr = left + c + right;
							}	
						} else {
							newStr = left + brief + right;
						}
					} else { 
						newStr = "";
						break;
					}
				}

			// 作者
			} else if(label == TITLELINKPAGE_ARTICLEAUTHOR) {
				if(a == 0) {
					temp = author;
					newStr = left + right;
				} else {
					if(temp != "" && temp != null) {
						newStr = left + author + right;
					} else {
						newStr = "";
						break;
					}
				}

			// 点击次数
			} else if(label == TITLELINKPAGE_ARTICLEHITS) {
				if(a == 0) {
					temp = hits;
					newStr = left + right;
				} else {
					if(temp != "" && temp != null) {
						newStr = left + hits + right;
					} else {
						newStr = "";
						break;
					}
				}
			
			// 四位年
			} else if(label == YEAR4) {
				if(a == 0) {
					temp = displaytime;
					newStr = left + right;
				} else {
					if(temp != "" && temp != null) {
						var time = "";
						if(displaytime != null) {
							time = displaytime.split("-")[0];
						}
						newStr = left + time + right;
					} else {
						newStr = "";
						break;
					}
				}

			// 两位年
			} else if(label == YEAR2) {
				if(a == 0) {
					temp = displaytime;
					newStr = left + right;
				} else {
					if(temp != "" && temp != null) {
						var time = "";
						if(displaytime != null) {
							time = displaytime.split("-")[0].slice(2, 4);
						}
						newStr = left + time + right;
					} else {
						newStr = "";
						break;
					}
				}

			// 两位月
			} else if(label == MONTH2) {
				if(a == 0) {
					temp = displaytime;
					newStr = left + right;
				} else {
					if(temp != "" && temp != null) {
						var time = "";
						if(displaytime != null) {
							time = displaytime.split("-")[1];
						}
						newStr = left + time + right;
					} else {
						newStr = "";
						break;
					}
				}

			// 一位月
			} else if(label == MONTH1) {
				if(a == 0) {
					temp = displaytime;
					newStr = left + right;
				} else {
					if(temp != "" && temp != null) {
						var time = "";
						if(displaytime != null) {
							time = displaytime.split("-")[1];
							if(time < 10) {
								time = time.slice(1,2);
							}
						}
						newStr = left + time + right;
					} else {
						newStr = "";
						break;
					}
				}

			// 两位日
			} else if(label == DAY2) {
				if(a == 0) {
					temp = displaytime;
					newStr = left + right;
				} else {
					if(temp != "" && temp != null) {
						var time = "";
						if(displaytime != null) {
							var temptime = displaytime.replace(/(\d{4})-(\d{1,2})-(\d{1,2})\s{1,100}(\d{1,2}):(\d{1,2}):(\d{1,2})/, "$1-$2-$3-$4-$5-$6");
							time = temptime.split("-")[2];
						}
						newStr = left + time + right;
					} else {
						newStr = "";
						break;
					}
				} 

			// 一位日
			} else if(label == DAY1) {
				if(a == 0) {
					temp = displaytime;
					newStr = left + right;
				} else {
					if(temp != "" && temp != null) {
						var time = "";
						if(displaytime != null) {
							var temptime = displaytime.replace(/(\d{4})-(\d{1,2})-(\d{1,2})\s{1,100}(\d{1,2}):(\d{1,2}):(\d{1,2})/, "$1-$2-$3-$4-$5-$6");
							time = temptime.split("-")[2];
							if(time < 10) {
								time = time.slice(1,2);
							}
						}
						newStr = left + time + right;
					} else {
						newStr = "";
						break;
					}
				}

			// 时
			} else if(label == HOUR) {
				if(a == 0) {
					temp = displaytime;
					newStr = left + right;
				} else {
					if(temp != "" && temp != null) {
						var time = "";
						if(displaytime != null) {
							var temptime = displaytime.replace(/(\d{4})-(\d{1,2})-(\d{1,2})\s{1,100}(\d{1,2}):(\d{1,2}):(\d{1,2})/, "$1-$2-$3-$4-$5-$6");
							time = temptime.split("-")[3];
						}
						newStr = left + time + right;
					} else {
						newStr = "";
						break;
					}
				}

			// 分
			} else if(label == MINUTE) {
				if(a == 0) {
					temp = displaytime;
					newStr = left + right;
				} else {
					if(temp != "" && temp != null) {
						var time = "";
						if(displaytime != null) {
							var temptime = displaytime.replace(/(\d{4})-(\d{1,2})-(\d{1,2})\s{1,100}(\d{1,2}):(\d{1,2}):(\d{1,2})/, "$1-$2-$3-$4-$5-$6");
							time = temptime.split("-")[4];
						}
						newStr = left + time + right;
					} else {
						newStr = "";
						break;
					}
				}

			// 秒
			} else if(label == SECOND) {
				if(a == 0) {
					temp = displaytime;
					newStr = left + right;
				} else {
					if(temp != "" && temp != null) {
						var time = "";
						if(displaytime != null) {
							var temptime = displaytime.replace(/(\d{4})-(\d{1,2})-(\d{1,2})\s{1,100}(\d{1,2}):(\d{1,2}):(\d{1,2})/, "$1-$2-$3-$4-$5-$6");
							time = temptime.split("-")[5];
						}
						newStr = left + time + right;
					} else {
						newStr = "";
						break;
					}
				}

			// 创建时间
			} else if(label == TITLELINKPAGE_ARTICLECREATEDATE) {
				if(a == 0) {
					temp = createtime;
					newStr = left + right;
				} else {
					if(temp != "" && temp != null) {
						newStr = left + createtime + right;
					} else {
						newStr = "";
						break;
					}
				}

			// 显示时间
			} else if(label == TITLELINKPAGE_DISPLAYTIME) {
				if(a == 0) {
					temp = displaytime;
					newStr = left + right;
				} else {
					if(temp != "" && temp != null) {
						newStr = left + displaytime + right;
					} else {
						newStr = "";
						break;
					}
				}

			// 审核时间
			} else if(label == TITLELINKPAGE_AUDITTIME) {
				if(a == 0) {
					temp = auditTime;
					newStr = left + right;
				} else {
					if(temp != "" && temp != null) {
						newStr = left + auditTime + right;
					} else {
						newStr = "";
						break;
					}
				}

			// 发布时间
			} else if(label == TITLELINKPAGE_PUBLISHTIME) {
				if(a == 0) {
					temp = publishtime;
					newStr = left + right;
				} else {
					if(temp != "" && temp != null) {
						newStr = left + publishtime + right;
					} else {
						newStr = "";
						break;
					}
				}

			// 失效时间
			} else if(label == TITLELINKPAGE_INVALIDTIME) {
				if(a == 0) {
					temp = invalidTime;
					newStr = left + right;
				} else {
					if(temp != "" && temp != null) {
						newStr = left + invalidTime + right;
					} else {
						newStr = "";
						break;
					}
				}
		
			// 图片
			} else if(label == PIC1) {
				if(a == 0) {
					temp = pic1;
					newStr = left + right;
				} else {
					if(temp != "" && temp != null) {
						newStr = left + "/" + appName + pic1 + right;
					} else {
						newStr = "";
						break;
					}
				}
		
			// 标题前缀	
			} else if(label == TITLEPREFIX) {
				if(a == 0) {
					temp = TtitlePrefix;
					newStr = left + right;
				} else {
					if(temp != "" && temp != null) {
						if(flag == "titleLinkPage") {
							if(TtitlePrefixValidity < 0) {
								if(TtitlePrefixPic == 1) {
									if(!TtitlePrefix.startWith("http://")) {
										newStr = left + "<img src='/"+ appName + TtitlePrefix +"' border=\"0\"/>" + right;
									} else {
										newStr = left + "<img src='"+TtitlePrefix +"' border=\"0\"/>" + right;
									}
								} else {
									newStr = left + TtitlePrefix + right;
								}
							} else {
								if(checkDate(TprefixDate)) {
									if(TtitlePrefixPic == 1) {
										if(!TtitlePrefix.startWith("http://")) {
											newStr = left + "<img src='/"+ appName + TtitlePrefix +"' border=\"0\"/>" + right;
										} else {
											newStr = left + "<img src='"+TtitlePrefix +"' border=\"0\"/>" + right;
										}
									} else {
										newStr = left + TtitlePrefix + right;
									}
								} else {
									newStr = left + right;	
								}
							}
						} else {
							if(TtitlePrefixPic == 1) {
								if(!TtitlePrefix.startWith("http://")) {
									newStr = left + "<img src='/"+ appName + TtitlePrefix +"' border=\"0\"/>" + right;
								} else {
									newStr = left + "<img src='"+TtitlePrefix +"' border=\"0\"/>" + right;
								}
							} else {
								newStr = left + TtitlePrefix + right;
							}
						}			
					} else {
						newStr = "";
						break;
					}
				}

			// 标题后缀
			} else if(label == TITLESUFFIX) {
				if(a == 0) {
					temp = TtitleSuffix;
					newStr = left + right;
				} else {
					if(temp != "" && temp != null) {
						if(flag == "titleLinkPage") {
							if(TtitleSuffixValidity < 0) {
								if(TtitleSuffixPic == 1) {
									if(!TtitleSuffix.startWith("http://")) {
										newStr = left + "<img src='/"+ appName + TtitleSuffix +"' border=\"0\"/>" + right;
									} else {
										newStr = left + "<img src='"+ TtitleSuffix +"' border=\"0\"/>" + right;
									}
								} else {
									newStr = left + TtitleSuffix + right;
								}
							} else {
								if(checkDate(TsuffixDate)) {
									if(TtitleSuffixPic == 1) {
										if(!TtitleSuffix.startWith("http://")) {
											newStr = left + "<img src='/"+ appName + TtitleSuffix +"' border=\"0\"/>" + right;
										} else {
											newStr = left + "<img src='"+ TtitleSuffix +"' border=\"0\"/>" + right;
										}
									} else {
										newStr = left + TtitleSuffix + right;
									}
								} else {
									newStr = left + right;	
								}
							}
						} else {
							if(TtitleSuffixPic == 1) {
								if(!TtitleSuffix.startWith("http://")) {
									newStr = left + "<img src='/"+ appName + TtitleSuffix +"' border=\"0\"/>" + right;
								} else {
									newStr = left + "<img src='"+ TtitleSuffix +"' border=\"0\"/>" + right;
								}
							} else {
								newStr = left + TtitleSuffix + right;
							}
						}		
					} else {
						newStr = "";
						break;
					}
				}
			
			// 更多	
			} else if(label == MORE) {
				if(a == 0) {
					temp = more;
					newStr = left + right;
				} else {
					if(temp != "" && temp != null) {
						if(flag != "titleLinkPage") {
							if(ispage != "yes") {
								// 是否显示更多
								if(displayMore == "1") {
									if(morePic == 1) {
										if(!more.startWith("http://")) {
											newStr = left + "<img src='/"+ appName + more +"' border=\"0\"/>" + right;
										} else {
											newStr = left + "<img src='"+more +"' border=\"0\"/>" + right;
										}
									} else {
										newStr = left + more + right;
									}
								}else {
									newStr = left + right;
								}
							} else{
								newStr = left + right;
							}
						} else {
							if(morePic == 1) {
								if(!more.startWith("http://")) {
									newStr = left + "<img src='/"+ appName + more +"' border=\"0\"/>" + right;
								} else {
									newStr = left + "<img src='"+more +"' border=\"0\"/>" + right;
								}
							} else {
								newStr = left + more + right;
							}
						}
					} else {
						newStr = "";
						break;
					}
				}
			
			// 更多链接地址	
			} else if(label == MORELINK) {
				if(a == 0) {
					if(ispage != "yes") {
						if(displayMore == "1"){
							temp = moreLinkUrl;
						}
					}
					newStr = left + right;
				} else {
					if(temp != "" && temp != null) {
						if(ispage != "yes") {
							if(displayMore == "1"){
								if(!moreLinkUrl.startWith("http://")) {
									newStr = left + "/" + appName + moreLinkUrl + right;
								} else {
									newStr = left + moreLinkUrl + right;
								}
							}else{
								newStr = left + right;
							}
						}else{
							newStr = left + right;
						}
					} else {
						newStr = "";
						break;
					}
				}
			
			// 其他标签不解析
			} else {
				var newreg = new RegExp("<!--(.*)-->");
				var t = "";
				if( (t = newreg.exec(label)) != null) {
					label = t[1];
				}
				if(!contains(label,"textarea",false)){
					if(a == 0) {
						temp = eval(label);
						newStr = left + right;
					} else {
						if(temp != "" && temp != null) {
							var b = eval(label);
							if(b.startWith("http://")) {
								newStr = left + b + right;						
							} else if(b.startWith("/")) {
								newStr = left + "/" + appName + b + right;	
							} else {
								newStr = left + b + right;						
							}
						} else {
							newStr = "";
							break;
						}
					}
				}
			}
			a++;
		}
		if(a == 0) {
			return "";
		} 
		return newStr;
	}

	// 获得if之间的数据
	function TgetIfAndElseAndIfData(str, article, appName, flag, displayMore) {
		// str====<!--if--><!--articletitle-->aa<!--else--><!--articleurl-->bb<!--/if-->
		var arr = "";
		var ifStart = "";
		var ifEnd = "";
		var returnIfData = "";
		if( (arr = TITLELINKPAGE_IF.exec(str)) != null) {
			if( (ifStart = TITLELINKPAGE_IF_START.exec(str)) != null) {
				// ifStart[1] === if 和 else 之间的数据
				returnIfData += TsubIf(ifStart[1], article, appName, flag, displayMore);
			} 
			// 如果if与else之间没有数据则执行else与if之间的数据
			if(returnIfData == null && returnIfData == "") {
				if( (ifEnd = TITLELINKPAGE_IF_END.exec(str)) != null) {
					/// 不要在去区分for直接解析标签就可以了	          
					returnIfData += TsubFor(article, ifEnd[1], appName, flag, displayMore)
				}
			} 
			return returnIfData;
		} else {
			return "";
		}
	}
	
	// str ===== dd<!--if-->aa<!--else-->bb<!--/if-->cc<!--if-->dd<!--else-->ee<!--/if-->dd 
	//     或者 :<!--if-->aa<!--else-->bb<!--/if--> 
	function TgetIfData(article, str, appName, flag, displayMore) {
		var arr = "";
		var ifStart = "";
		var returnResult = "";
		var a = 0;
		var temp = "";
		if( (temp = TITLELINKPAGE_IF.exec(str)) != null ) {
			var left = RegExp.leftContext;
			var right = RegExp.rightContext;
			if( (arr = TITLELINKPAGE_IF_START.exec(str)) != null) {
				// ifStart ====== 第一次: <!--if-->aa<!--else-->bb<!--/if-->ff<!--if-->dd<!--else-->
				//				  第二次: <!--if-->aa<!--else-->
				ifStart = arr[0];
				var newArr = "";
				var startRight = RegExp.rightContext;
				if( (newArr = TITLELINKPAGE_IF.exec(ifStart)) != null ) {
					a = 1;
					var rig = RegExp.rightContext;
					// newArr[0] ==== <!--if-->aa<!--else-->bb<!--/if-->
					returnResult = TgetIfData(article, newArr[0], appName, flag, displayMore) + rig;
				}
				returnResult = returnResult + startRight;
			}
			if(a == 0) {
			    // 获得if之间的数据 
				returnResult = left + TgetIfAndElseAndIfData(str, article, appName, flag, displayMore) + right;
				return returnResult;	
			}
			if(a == 1) {
				returnResult = left + returnResult;
				return returnResult;
			}
			return returnResult;
		} else {
			return str;
		}
	}	
	
	// 获取if数据以保证不在出现if标签
	function TgetData(article, label, appName, flag, displayMore) {
		var arr = "";
		//到不在出现if标签为止
		while( (arr = TITLELINKPAGE_IF.exec(label)) != null) {
			label = TgetIfData(article, label, appName, flag, displayMore);
		}
		return label;
	}
	
	/*********************************************获得for的信息******************************************************************************************/
	
	function TsubFor(article, forData, appName, flag, displayMore) {
		var siteName = article.TsiteName;
		var siteLink = article.TsiteLink;
		var columnName = article.TcolumnName;
		var columnLink = article.TcolumnLink;
		var title = article.Ttitle;
		var url = article.Turl;
		var subtitle = article.TsubTitle;
		var quotetitle = article.TquoteTitle;
		var brief = article.Tbrief;
		var author = article.Tauthor;
		var hits = article.Thits;
		var createtime = article.TcreateTime;
		var displaytime = article.TdisplayTime;
		var publishtime = article.TpublishTime;
		var invalidTime = article.TinvalidTime;
		var auditTime = article.TauditTime;
		var pic1 = article.Tpic1;
		
		var  infoSource = article.infoSource;   
		var  keyword = article.keyword;       
		var  deleted = article.deleted;       
		var  audited = article.audited;      
		var  publishState = article.publishState;  
		var  keyFilter = article.keyFilter;     // 关键词过滤
		var  toped = article.toped;         // 置顶
		// 日期 date   10
	/**	var  date1 = article.date1;
		var  date2 = article.date2;
		var  date3 = article.date3;
		var  date4 = article.date4;
		var  date5 = article.date5;
		var  date6 = article.date6;
		var  date7 = article.date7;
		var  date8 = article.date8;
		var  date9 = article.date9;
		var  date10 = article.date10;
		// 文本类型 text 25
		var  text1 = article.text1;
		var  text2 = article.text2;
		var  text3 = article.text3;
		var  text4 = article.text4;
		var  text5 = article.text5;
		var  text6 = article.text6;
		var  text7 = article.text7;
		var  text8 = article.text8;
		var  text9 = article.text9;
		var  text10 = article.text10;
		var  text11 = article.text11;
		var  text12 = article.text12;
		var  text13 = article.text13;
		var  text14 = article.text14;
		var  text15 = article.text15;
		var  text16 = article.text16;
		var  text17 = article.text17;
		var  text18 = article.text18;
		var  text19 = article.text19;
		var  text20 = article.text20;
		var  text21 = article.text21;
		var  text22 = article.text22;
		var  text23 = article.text23;
		var  text24 = article.text24;
		var  text25 = article.text25;
		// 文本域类型  textArea 10
	 	var  textArea1 = article.textArea1;
		var  textArea2 = article.textArea2;
		var  textArea3 = article.textArea3;
		var  textArea4 = article.textArea4;
		var  textArea5 = article.textArea5;
		var  textArea6 = article.textArea6;
		var  textArea7 = article.textArea7;
		var  textArea8 = article.textArea8;
		var  textArea9 = article.textArea9;
		var  textArea10 = article.textArea10;
		// 整型   integer 10
		var  integer1 = article.integer1;
		var  integer2 = article.integer2;
		var  integer3 = article.integer3;
		var  integer4 = article.integer4;
		var  integer5 = article.integer5;
		var  integer6 = article.integer6;
		var  integer7 = article.integer7;
		var  integer8 = article.integer8;
		var  integer9 = article.integer9;
		var  integer10 = article.integer10;
		// 浮点型  float 10
		var  float1 = article.float1;
		var  float2 = article.float2;
		var  float3 = article.float3;
		var  float04 = article.float04;
		var  float5 = article.float5;
		var  float6 = article.float6;
		var  float7 = article.float7;
		var  float08 = article.float08;
		var  float9 = article.float9;
		var  float10 = article.float10;
		// 布尔型 bool   10
		var  bool1 = article.bool1;
		var  bool2 = article.bool2;
		var  bool3 = article.bool3;
		var  bool4 = article.bool4;
		var  bool5 = article.bool5;
		var  bool6 = article.bool6;
		var  bool7 = article.bool7;
		var  bool8 = article.bool8;
		var  bool9 = article.bool9;
		var  bool10 = article.bool10;
		// 图片 pic      2~10
		var  pic2 = article.pic2;
		var  pic3 = article.pic3;
		var  pic4 = article.pic4;
		var  pic5 = article.pic5;
		var  pic6 = article.pic6;
		var  pic7 = article.pic7;
		var  pic8 = article.pic8;
		var  pic9 = article.pic9
		var  pic10 = article.pic10;
		// 附件 attach   10
		var  attach1 = article.attach1;
		var  attach2 = article.attach2;
		var  attach3 = article.attach3;
		var  attach4 = article.attach4;
		var  attach5 = article.attach5;
		var  attach6 = article.attach6;
		var  attach7 = article.attach7;
		var  attach8 = article.attach8;
		var  attach9 = article.attach9;
		var  attach10 = article.attach10;
		// 媒体 media    10
		var  media1 = article.media1;
		var  media2 = article.media2;
		var  media3 = article.media3;
		var  media4 = article.media4;
		var  media5 = article.media5;
		var  media6 = article.media6;
		var  media7 = article.media7;
		var  media8 = article.media8;
		var  media9 = article.media9;
		var  media10 = article.media10;
		// 枚举 enumeration 5
		var  enumeration1 = article.enumeration1;
		var  enumeration2 = article.enumeration2;
		var  enumeration3 = article.enumeration3;
		var  enumeration4 = article.enumeration4;
		var  enumeration5 = article.enumeration5;*/
		

		var str = forData;
		var arr = "";
		// 先处理if
		if( (arr = TITLELINKPAGE_IF.exec(str)) != null) {
			var left = RegExp.leftContext;
			var right = RegExp.rightContext;
			str = TgetData(article, arr[0], appName, flag, displayMore);
			str = left + str + right;
		}
		// 处理标签
		while( (arr = TITLELINKPAGE_REGEX_LABEL.exec(str)) != null) {
			var label = arr[0];
			var left = RegExp.leftContext;
			var right = RegExp.rightContext;
			// 网站名称
			if(label == TITLELINKPAGE_SITE_NAME) {	
				str = left + siteName + right;					

			// 网站链接
			} else if(label == TITLELINKPAGE_SITE_LINK) {
				if(!siteLink.startWith("http://")) {
					str = left + "/" + appName + siteLink + right;
				} else {
					str = left + siteLink + right;
				}

			// 栏目名称
			} else if(label == TITLELINKPAGE_COLUMN_NAME) {
				str = left + columnName + right;
	
			// 栏目链接
			} else if(label == TITLELINKPAGE_COLUMNLINK) {
				if(!columnLink.startWith("http://")) {
					str = left + "/" + appName + columnLink + right;
				} else {
					str = left + columnLink + right;
				}

			// 标题
			} else if(label == TITLELINKPAGE_ARTICLETITLE) {
				if(title != null) {
					if(title.length < TtitleLimit) {
						str = left + title + right;
					} else {
						var b = title.substring(0, TtitleLimit) + "...";
						str = left + b + right;
					}	
				} else {
					str = left + right;
				}


			// 副标题
			} else if(label == TITLELINKPAGE_ARTICLESUBTITLE) {				
				 str = left + subtitle + right;
			
			// 标题链接
			} else if(label == TITLELINKPAGE_ARTICLEURL) {	
				if(!url.startWith("http://")) {
					str = left + "/" + appName + url + right;
				} else {
					str = left + url + right;
				}

			// 引题
			} else if(label == TITLELINKPAGE_ARTICLEQUOTETITLE) {
				str = left + quotetitle + right;

			// 摘要
			} else if(label == TITLELINKPAGE_ARTICLEBRIEF)	{	
				if(brief != null) {
					if(brief.length < TbriefLimit) {
						str = left + brief + right;
					} else {
						var d = brief.substring(0, TbriefLimit) + "...";
						str = left + d + right;
					}	
				} else {
					str = left + right;
				}

			// 作者
			} else if(label == TITLELINKPAGE_ARTICLEAUTHOR) {				
				 str = left + author + right;

			// 点击次数
			} else if(label == TITLELINKPAGE_ARTICLEHITS) {				
				 str = left + hits + right;
					
			
			// 四位年
			} else if(label == YEAR4) {				
				var time = "";
				if(displaytime != null) {
					time = displaytime.split("-")[0];
				}
				str = left + time + right;

			// 两位年
			} else if(label == YEAR2) {
				var time = "";
				if(displaytime != null) {
					time = displaytime.split("-")[0].slice(2, 4);
				}
				str = left + time + right;
					

			// 两位月
			} else if(label == MONTH2) {		
				var time = "";
				if(displaytime != null) {
					time = displaytime.split("-")[1];
				}
				str = left + time + right;
					
			// 一位月
			} else if(label == MONTH1) {			
				var time = "";
				if(displaytime != null) {
					time = displaytime.split("-")[1];
					if(time < 10) {
						time = time.slice(1,2);
					}
				}
				str = left + time + right;
					

			// 两位日
			} else if(label == DAY2) {				
				var time = "";
				if(displaytime != null) {
					var temptime = displaytime.replace(/(\d{4})-(\d{1,2})-(\d{1,2})\s{1,100}(\d{1,2}):(\d{1,2}):(\d{1,2})/, "$1-$2-$3-$4-$5-$6");
					time = temptime.split("-")[2];
				}
				str = left + time + right;					
				
			// 一位日
			} else if(label == DAY1) {				
				var time = "";
				if(displaytime != null) {
					var temptime = displaytime.replace(/(\d{4})-(\d{1,2})-(\d{1,2})\s{1,100}(\d{1,2}):(\d{1,2}):(\d{1,2})/, "$1-$2-$3-$4-$5-$6");
					time = temptime.split("-")[2];
					if(time < 10) {
						time = time.slice(1,2);
					}
				}
				str = left + time + right;					

			// 时
			} else if(label == HOUR) {
				var time = "";
				if(displaytime != null) {
					var temptime = displaytime.replace(/(\d{4})-(\d{1,2})-(\d{1,2})\s{1,100}(\d{1,2}):(\d{1,2}):(\d{1,2})/, "$1-$2-$3-$4-$5-$6");
					time = temptime.split("-")[3];
				}
				str = left + time + right;
					

			// 分
			} else if(label == MINUTE) {				
				var time = "";
				if(displaytime != null) {
					var temptime = displaytime.replace(/(\d{4})-(\d{1,2})-(\d{1,2})\s{1,100}(\d{1,2}):(\d{1,2}):(\d{1,2})/, "$1-$2-$3-$4-$5-$6");
					time = temptime.split("-")[4];
				}
				str = left + time + right;
					
			// 秒
			} else if(label == SECOND) {
				var time = "";
				if(displaytime != null) {
					var temptime = displaytime.replace(/(\d{4})-(\d{1,2})-(\d{1,2})\s{1,100}(\d{1,2}):(\d{1,2}):(\d{1,2})/, "$1-$2-$3-$4-$5-$6");
					time = temptime.split("-")[5];
				}
				str = left + time + right;					

			// 创建时间
			} else if(label == TITLELINKPAGE_ARTICLECREATEDATE) {				
				str = left + createtime + right;					

			// 显示时间
			} else if(label == TITLELINKPAGE_DISPLAYTIME) {				
				str = left + displaytime + right;	

			// 审核时间
			} else if(label == TITLELINKPAGE_AUDITTIME) {				
				str = left + auditTime + right;

			// 发布时间
			} else if(label == TITLELINKPAGE_PUBLISHTIME) {				
				str = left + publishtime + right;

			// 失效时间
			} else if(label == TITLELINKPAGE_INVALIDTIME) {
				str = left + invalidTime + right;
					
			// 图片
			} else if(label == PIC1) {
				str = left + "/" + appName + pic1 + right;					
		
			// 标题前缀	
			} else if(label == TITLEPREFIX) {			
				if(flag == "titleLinkPage") {
					if(TtitlePrefixValidity < 0) {
						if(TtitlePrefixPic == 1) {
							if(!TtitlePrefix.startWith("http://")) {
								str = left + "<img src='/"+ appName + TtitlePrefix +"' border=\"0\"/>" + right;
							} else {
								str = left + "<img src='"+ TtitlePrefix +"' border=\"0\"/>" + right;
							}
						} else {
							str = left + TtitlePrefix + right;
						}
					} else {
						if(checkDate(TprefixDate)) {
							if(TtitlePrefixPic == 1) {
								if(!TtitlePrefix.startWith("http://")) {
									str = left + "<img src='/"+ appName + TtitlePrefix +"' border=\"0\"/>" + right;
								} else {
									str = left + "<img src='"+ TtitlePrefix +"' border=\"0\"/>" + right;
								}
							} else {
								str = left + TtitlePrefix + right;
							}
						} else {
							str = left + right;
						}
					}

				} else {
					if(TtitlePrefixPic == 1) {
						if(!TtitlePrefix.startWith("http://")) {
							str = left + "<img src='/"+ appName + TtitlePrefix +"' border=\"0\"/>" + right;
						} else {
							str = left + "<img src='"+ TtitlePrefix +"' border=\"0\"/>" + right;
						}
					} else {
						str = left + TtitlePrefix + right;
					}
				}
			
			// 标题后缀
			} else if(label == TITLESUFFIX) {
				if(flag == "titleLinkPage") {
					if(TtitleSuffixValidity < 0) {
						if(TtitleSuffixPic == 1) {
							if(!TtitleSuffix.startWith("http://")) {
								str = left + "<img src='/"+ appName + TtitleSuffix +"' border=\"0\"/>" + right;
							} else {
								str = left + "<img src='"+ TtitleSuffix +"' border=\"0\"/>" + right;
							}
						} else {
							str = left + TtitleSuffix + right;
						}
					}else{
						if(checkDate(TsuffixDate)) {
							if(TtitleSuffixPic == 1) {
								if(!TtitleSuffix.startWith("http://")) {
									str = left + "<img src='/"+ appName + TtitleSuffix +"' border=\"0\"/>" + right;
								} else {
									str = left + "<img src='"+ TtitleSuffix +"' border=\"0\"/>" + right;
								}
							} else {
								str = left + TtitleSuffix + right;
							}
						} else {
							str = left + right;
						}
					}

				} else {
					if(TtitleSuffixPic == 1) {
						if(!TtitleSuffix.startWith("http://")) {
							str = left + "<img src='/"+ appName + TtitleSuffix +"' border=\"0\"/>" + right;
						} else {
							str = left + "<img src='"+ TtitleSuffix +"' border=\"0\"/>" + right;
						}
					} else {
						str = left + TtitleSuffix + right;
					}
				}
			
			// 更多	
			} else if(label == MORE) {
				if(flag != "titleLinkPage") {
					if(ispage != "yes") {
						if(displayMore == "1"){
							if(morePic == 1) {
								if(!more.startWith("http://")) {
									str = left + "<img src='/"+ appName + more +"' border=\"0\"/>" + right;
								} else {
									str = left + "<img src='"+ more +"' border=\"0\"/>" + right;
								}
							} else {
								str = left + more + right;
							}
						}else{
							str = left + right;	
						}
					}else{
						str = left + right;
					}
				}else{
					if(morePic == 1) {
						if(!more.startWith("http://")) {
							str = left + "<img src='/"+ appName + more +"' border=\"0\"/>" + right;
						} else {
							str = left + "<img src='"+ more +"' border=\"0\"/>" + right;
						}
					} else {
						str = left + more + right;
					}
				}	
			
			// 更多链接地址	
			} else if(label == MORELINK) {
				if(ispage != "yes") {
					if(displayMore == "1") {
						if(!moreLinkUrl.startWith("http://")) {
							str = left + "/" + appName + moreLinkUrl + right;
						} else {
							str = left + moreLinkUrl + right;
						}
					}else{
						str = left + right;
					}
				}else{
					str = left + right;
				}
				
			// 其他标签不解析
			} else {
				var newreg = new RegExp("<!--(.*)-->");
				var t = "";
				if( (t = newreg.exec(label)) != null) {
					label = t[1];
				}
				if(!contains(label,"textarea",false)){
					var b = eval(label);
					if(b.startWith("http://")) {
						str = left + b + right;						
					} else if(b.startWith("/")) {
						str = left + "/" + appName + b + right;	
					} else {
						str = left + b + right;						
					}
				}
			}						
		}
		return str;
	}


	/***** 获得for  (forData:  是for之间的数据)*******/
	function TgetForCode(forData, articleArray, appName, flag, displayMore) {
		var result = "";
		if(articleArray.length > 0) {
			if(flag == "titleLinkPage") {
				for(var i = 0; i < articleArray.length; i++) {
					var article = articleArray[i];
					result += TsubFor(articleArray[i], forData, appName, flag, displayMore);
				}

			} else {
				// 要分页
				if(ispage == "yes") {
					for(var i = 0; i < articleArray.length; i++) {
						var article = articleArray[i];
						result += TsubFor(articleArray[i], forData, appName, flag, displayMore);
					}

				// 不要分页
				} else {
					var len = articleArray.length;
					var temp = 0;
					var temp2 = 0;
					var a = 0;
					result += "<table>";
					for(var i = 0; i < row; i++) {
						result += "<tr>";
						for(var j = 0; j < col; j++) {
							if(a == len) {
								temp2 = 1;
								break;
							}
							result += "<td>" + TsubFor(articleArray[a], forData, appName, flag, displayMore) + "</td>";
							a++;
						}
						result += "</tr>";
						if(temp2 == 1) {
							break;
						}
					}
					result += "</table>";
				}	
			}
		}
		return result;
	}

	/***** 获取html代码*******/

	// flag :用来标记是最新信息来调用的还是标题链接分页调用
	function TgetHtml(articleUrl, appName, flag, displayMore) {
		// 加载xml中相关信息
		var arr = "";
		var data = "";
		// 获得文章
		var articleArray = TgetArticles(articleUrl, flag);
		if(articleArray.length != 0) {
			// 加载for
			if((arr = TITLELINKPAGE_REGEX_FOR.exec(ThtmlContent)) != null) {
				var left = RegExp.leftContext;
				var right = RegExp.rightContext;
				data = TgetForCode(arr[1], articleArray, appName, flag, displayMore);  
				data = left + data + right;
			} else {
				data = ThtmlContent;
			}
			var article = null;
			if(flag == "titleLinkPage") {
				article = new TArticle(articleArray[0].TsiteName,articleArray[0].TsiteLink,articleArray[0].TcolumnName,articleArray[0].TcolumnLink,"","","","","","","","","","","","","",
											"", "", "", "", "", "", "");
										/**	"", "", "", "", "", "", "", "", "", "",
											"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
									//		"", "", "", "", "", "", "", "", "", "",
											"", "", "", "", "", "", "", "", "", "",
											"", "", "", "", "", "", "", "", "", "",
											"", "", "", "", "", "", "", "", "", "",
											"", "", "", "", "", "", "", "", "",
											"", "", "", "", "", "", "", "", "", "",
											"", "", "", "", "", "", "", "", "", "",
											"", "", "", "", "");*/
			} else {
				article = new TArticle(articleArray[0].TsiteName,articleArray[0].TsiteLink,"","","","","","","","","","","","","","","",
											"", "", "", "", "", "", "");
									/**		"", "", "", "", "", "", "", "", "", "",
											"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
									//		"", "", "", "", "", "", "", "", "", "",
											"", "", "", "", "", "", "", "", "", "",
											"", "", "", "", "", "", "", "", "", "",
											"", "", "", "", "", "", "", "", "", "",
											"", "", "", "", "", "", "", "", "",
											"", "", "", "", "", "", "", "", "", "",
											"", "", "", "", "", "", "", "", "", "",
											"", "", "", "", "");*/
			}
			// 处理if
			if( (arr = TITLELINKPAGE_IF.exec(data)) != null) {
				var left = RegExp.leftContext;
				var right = RegExp.rightContext;
				data = TgetData(article, arr[0], appName, flag, displayMore);
				data = left + data + right;
			}
			// 处理完if后还要解析for和if以外的东西(以外的只有 siteName, siteLink, columnName, columnLink)
			data = TsubFor(article, data, appName, flag, displayMore);
		} 
		return data;		
	}

    /**********************************给页面调用的函数************************************************************************/

	// unitId                单元id
	// titlelinkpagexmlpath  最新信息xml配置路径
	// id                    页面生成的随机数
	// dir                   文件配置的目录    (dir:  )
	// page					 页数

	// id 不需要   localPath不需要   appName 
	function getTitleLinkPage(unitId, titlelinkpagexmlpath, id, dir, appName, page, siteId) {
		getTitleLinkPageXmlContent(titlelinkpagexmlpath);
		var html = "";
		// 分页		
		var realPage = 0;
		for(var i = 1; i <= page; i++) {
			var path = dir + "/"+ unitId + "_"+ i +".xml";
			var xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
			xmlhttp.open("GET", path, false);
			xmlhttp.send();
			if(xmlhttp.readyState==4) {   
				 // exist
				  if(xmlhttp.status==200) {
					  realPage++;

				  // Doesn't exist
				  } else if(xmlhttp.status==404) {
					 
				  // Don't know		
				  } else {
					
				  }
			 } 
		}
		if(realPage > 0) {
			var c = realPage * TpageInfoCount;
			html = TgetHtml(dir + "/" + unitId + "_1.xml", appName, "titleLinkPage", "0");
			// 替换路径
			var  replacePath = "/" + appName + "/release/site"+siteId+"/build/static";
			var temp = html.split(replacePath);
			html = "";
			for(var i = 0; i < temp.length; i++){
				html += temp[i];
			}
			// 替换图片或则附件路径
			replacePath = "/" + appName + "/release/site"+siteId
			temp = html.split(replacePath);
			html = "";
			for(var k = 0; k < temp.length; k++){
				html += temp[k];
			}
			
			$("#"+ id +"_display").html(html);
			if(realPage > 1) {
				$("#pager").pagefoot({
					pagesize:TpageInfoCount,
					count:c,
					css:"mj_pagefoot_green",
					paging:function(page){
						var newHtml = TgetPageHtml(page, unitId, dir, appName, "titleLinkPage", "0");
						// 替换路径
						var  replacePath = "/" + appName + "/release/site"+siteId+"/build/static";
						var temp = newHtml.split(replacePath);
						newHtml = "";
						for(var i = 0; i < temp.length; i++){
							newHtml += temp[i];
						}
						// 替换图片或则附件路径
						replacePath = "/" + appName + "/release/site"+siteId
						temp = newHtml.split(replacePath);
						newHtml = "";
						for(var k = 0; k < temp.length; k++){
							newHtml += temp[k];
						}
						$("#"+ id +"_display").html(newHtml); 				
					}
				});
			}
		}
	}

	function TgetPageHtml(pageclickednumber, uid, strdir, appName, flag, displayMore) {
		var html = TgetHtml(strdir +"/"+ uid +"_"+ pageclickednumber +".xml", appName, flag, displayMore);
		return html;
	}