
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

    /** 栏目名称 */
    var TITLELINKPAGE_COLUMN_NAME =  "<!--columnName-->";
    /** 栏目链接 */
    var TITLELINKPAGE_COLUMNLINK = "<!--columnLink-->";
    /**  标题 */
    var TITLELINKPAGE_ARTICLETITLE  = "<!--articletitle-->";
    /**  标题链接 */
    var TITLELINKPAGE_ARTICLEURL  = "<!--articleurl-->";
    /** 作者 */
    var TITLELINKPAGE_ARTICLEAUTHOR = "<!--articleauthor-->";
    /** 点击次数 */
    var TITLELINKPAGE_ARTICLEHITS = "<!--articlehits-->";
    /** 附件 */
    var TITLELINKPAGE_ATTACH = "<!--attach-->";
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
    /** 发布时间 */
    var TITLELINKPAGE_PUBLISHTIME = "<!--publishTime-->";
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
        var xmlDoc = importXML(titleLinkPageXml);
        if(ie) {
            var xmlfile = xmlDoc.selectNodes("/j2ee.cms/title-link-page");
            setParamValue(xmlfile,0);
        }else{
            var xmlfile = xmlDoc.childNodes;
            for(var i = 0 ; i < xmlfile.length; i++){
                if(xmlfile[i].nodeName == "title-link-page"){
                    setParamValue(xmlfile,i);
                }
            }
        }
    }
    /**
    *给变量赋值
    */
    function setParamValue(xmlfile,i){
        TunitStyle = xmlfile[i].getElementsByTagName("viewStyle")[0].firstChild.nodeValue;
        Tsource = xmlfile[i].getElementsByTagName("contextFrom")[0].firstChild.nodeValue;
        TfixedColumn = xmlfile[i].getElementsByTagName("fixedColumn")[0].firstChild.nodeValue;
        TpageInfoCount = xmlfile[i].getElementsByTagName("pageInfoCount")[0].firstChild.nodeValue;
        TtitleLimit = xmlfile[i].getElementsByTagName("titleLimit")[0].firstChild.nodeValue;        
        TtitlePrefix = xmlfile[i].getElementsByTagName("titleHead")[0].firstChild.nodeValue;
        TtitlePrefixPic = xmlfile[i].getElementsByTagName("titleHeadPic")[0].firstChild.nodeValue;
        TtitleSuffix = xmlfile[i].getElementsByTagName("titleEnd")[0].firstChild.nodeValue;
        TtitleSuffixPic = xmlfile[i].getElementsByTagName("titleEndPic")[0].firstChild.nodeValue;
        // 替换解析代码中的回撤(即换行)
        ThtmlContent = xmlfile[i].getElementsByTagName("htmlContent")[0].firstChild.nodeValue;
        var reg = new RegExp("\r\n","g"); 
        var reg1 = new RegExp(" ","g"); 
        var code = ThtmlContent.replace(reg,"<br>"); 
        ThtmlContent = code.replace(reg1," ");

        TtitlePrefixValidity = xmlfile[i].getElementsByTagName("titleHeadValidity")[0].firstChild.nodeValue;    
        TtitleSuffixValidity = xmlfile[i].getElementsByTagName("titleEndValidity")[0].firstChild.nodeValue;    
        TprefixDate = xmlfile[i].getElementsByTagName("prefixDate")[0].firstChild.nodeValue;
        TsuffixDate = xmlfile[i].getElementsByTagName("suffixDate")[0].firstChild.nodeValue;
    }

    /************************定义文章对象******************************************************************************/ 

    var TcolumnName;   // 栏目名称
    var TcolumnLink;   // 栏目链接
    var Ttitle;        // 文章标题
    var Turl;          // 文章链接
    var Tauthor;       // 作者
    var Thits;         // 点击次数
    var TdisplayTime;  // 显示时间
    var TpublishTime;  // 发布时间
    var toped;         // 置顶
    var Tattach;       // 附件
    
    function TArticle(columnName, columnLink, title, url, author, hits,displaytime, publishtime,toped,attach) {
        this.TcolumnName = columnName;
        this.TcolumnLink = columnLink;
        this.Ttitle = title;
        this.Turl = url;
        this.Tauthor = author;
        this.Thits = hits;
        this.TdisplayTime = displaytime;
        this.TpublishTime = publishtime;
        this.toped = toped;  // 置顶
        this.Tattach = attach;
        // 日期 date   10
    }

    /************************获取所有文章的信息并放入数组*************************************************************************/

    function TgetArticles(articleUrl, flag) {
        // 导入要显示的文章的配置xml
        var xmlDoc = importXML(articleUrl);
        var arr = new Array();    
        var xmlfile = null;
        /**
        if(flag == "titleLinkPage") {
            xmlfile = xmlDoc.selectSingleNode("/j2ee.cms/article-page");
        } else {
            xmlfile = xmlDoc.selectSingleNode("/j2ee.cms/article-page");
        }*/
        if(ie){
            xmlfile = xmlDoc.selectSingleNode("/j2ee.cms/article-page");
        }else{    
            var tempXmlfile = xmlDoc.childNodes;
            for(var j = 0 ; j < tempXmlfile.length; j++){
                if(tempXmlfile[j].nodeName == "article-page"){
                    xmlfile = tempXmlfile[j];
                }
            }
        }
        var articles = xmlfile.childNodes;
        var articleArray = new Array();
        for (var i = 0; i < articles.length; i++) {        
            var columnName = articles[i].getElementsByTagName("columnName")[0].firstChild.nodeValue;
            var columnLink = articles[i].getElementsByTagName("columnLink")[0].firstChild.nodeValue;
            var articletitle = articles[i].getElementsByTagName("articletitle")[0].firstChild.nodeValue;
            var articleurl = articles[i].getElementsByTagName("articleurl")[0].firstChild.nodeValue;    
            var articleauthor = articles[i].getElementsByTagName("articleauthor")[0].firstChild.nodeValue;
            var articlehits = articles[i].getElementsByTagName("articlehits")[0].firstChild.nodeValue;    
            var displayTime = articles[i].getElementsByTagName("displayTime")[0].firstChild.nodeValue;        
            var publishTime = articles[i].getElementsByTagName("publishTime")[0].firstChild.nodeValue;
            var attach = articles[i].getElementsByTagName("attach")[0].firstChild.nodeValue;
        //    var createTime = articles[i].getElementsByTagName("createTime")[0].firstChild.nodeValue;
            var toped = articles[i].getElementsByTagName("toped")[0].firstChild.nodeValue;    
            var article = new TArticle(columnName,columnLink,articletitle,articleurl,articleauthor,articlehits,displayTime,publishTime,toped,attach)
            articleArray.push(article);
        }   
        return articleArray;
    }

    /*********************************获得if之间的数据（解析）*******************************************************************************/

    // 对if之间的标签进行解析 (str 有可能是if和else之间的数据 也有可能是else和if之间的数据)
    function TsubIf(str, article, appName, flag, displayMore) {
        var columnName = article.TcolumnName;
        var columnLink = article.TcolumnLink;
        var title = article.Ttitle;
        var url = article.Turl;        
        var author = article.Tauthor;
        var hits = article.Thits;
        var displaytime = article.TdisplayTime;
        var publishtime = article.TpublishTime;
        var createtime = article.TcreateTime;
        var toped = article.toped;  // 置顶
        var attach = article.Tattach;
        	
        var arr = "";
        var a = 0;    
        var temp = "";
        var newStr = str;
        while( (arr = TITLELINKPAGE_REGEX_LABEL.exec(newStr)) != null) {
            label = arr[0];
            var left = RegExp.leftContext;
            var right = RegExp.rightContext;
            // 栏目名称
            if(label == TITLELINKPAGE_COLUMN_NAME) {
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
                        newStr = "#";
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
           // 附件
           } else if(label == TITLELINKPAGE_ATTACH) {
                if(a == 0) {
                    temp = attach;
                    newStr = left + right;
                } else {
                    if(temp != "" && temp != null) {
                        if(!attach.startWith("http://")) {
                            newStr = left + "/" + appName + attach + right;
                        } else {
                            newStr = left + attach + right;
                        }
                    } else {
                        newStr = "#";
                        break;
                    }
                }
            }else if(label == TITLELINKPAGE_ARTICLEURL) {
                // 标题链接
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
                        newStr = "#";
                        break;
                    }
                }
            } else if(label == TITLELINKPAGE_ARTICLEAUTHOR) {
                //作者
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
            } else if(label == TITLELINKPAGE_DISPLAYTIME) {
                // 显示时间
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
            } else if(label == TITLEPREFIX) {
                // 标题前缀
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
                //                  第二次: <!--if-->aa<!--else-->
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
        var columnName = article.TcolumnName;
        var columnLink = article.TcolumnLink;
        var title = article.Ttitle;
        var url = article.Turl;
        var author = article.Tauthor;
        var hits = article.Thits;
        var displaytime = article.TdisplayTime;
        var publishtime = article.TpublishTime;
        var toped = article.toped;
        var attach = article.Tattach;
        
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
            if(label == TITLELINKPAGE_COLUMN_NAME) {
                // 栏目名称
                str = left + columnName + right;
            } else if(label == TITLELINKPAGE_COLUMNLINK) {
                // 栏目链接
                if(!columnLink.startWith("http://")) {
                    str = left + "/" + appName + columnLink + right;
                } else {
                    str = left + columnLink + right;
                }
            //附件
            } else if(label == TITLELINKPAGE_ATTACH) {
                if(!attach.startWith("http://")) {
                    str = left + "/" + appName + attach + right;
                } else {
                    str = left + attach + right;
                }
            } else if(label == TITLELINKPAGE_ARTICLETITLE) {
                // 标题
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
            } else if(label == TITLELINKPAGE_ARTICLEURL) {
                // 标题链接
                if(!url.startWith("http://")) {
                    str = left + "/" + appName + url + right;
                } else {
                    str = left + url + right;
                }
            } else if(label == TITLELINKPAGE_ARTICLEAUTHOR) {
                //作者
                 str = left + author + right;
            } else if(label == TITLELINKPAGE_ARTICLEHITS) {
                // 点击次数
                 str = left + hits + right;
            } else if(label == YEAR4) {
                // 四位年
                var time = "";
                if(displaytime != null) {
                    time = displaytime.split("-")[0];
                }
                str = left + time + right;
            } else if(label == YEAR2) {
                // 两位年
                var time = "";
                if(displaytime != null) {
                    time = displaytime.split("-")[0].slice(2, 4);
                }
                str = left + time + right;
            } else if(label == MONTH2) {
                // 两位月
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
            }else if(label == TITLELINKPAGE_DISPLAYTIME) {
                //显示时间
                str = left + displaytime + right;    
            }else if(label == TITLELINKPAGE_PUBLISHTIME) {
                // 发布时间
                str = left + publishtime + right; 
            } else if(label == TITLEPREFIX) {
                // 标题前缀
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
                article = new TArticle("","",articleArray[0].TcolumnName,articleArray[0].TcolumnLink,"","","","","","");
            } else {
                article = new TArticle("","","","","","","","","","");
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
    // page                     页数

    // id 不需要   localPath不需要   appName 
    function getTitleLinkPage(unitId, titlelinkpagexmlpath, id, dir, appName, pageCount, siteId) {
        getTitleLinkPageXmlContent(titlelinkpagexmlpath);
        var html = "";
        // 分页
        var realPage = 1;
        /** 
        for(var i = 1; i <= page; i++) {
            var path = dir + "/"+ unitId + "_"+ i +".xml";
             
            var xmlhttp;
            if(ie){
                xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
                xmlhttp.open("GET", path, false);
                xmlhttp.send();
            }else{
                xmlhttp = new window.XMLHttpRequest();
                xmlhttp.open("GET", path, false);
                xmlhttp.send(null);
                xmlDoc = xmlhttp.responseXML.documentElement;
            }
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
        } */
 
        if(realPage > 0) {
            var count = pageCount;
        /*    html = TgetHtml(dir + "/" + unitId + "_1.xml", appName, "titleLinkPage", "0");
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
     */
        //    document.getElementById(id +"_display").innerHtml = html;
        //    document.getElementById("pager").innerHtml = html;
             
                    //alert("count==="+c); 
                    //alert("pagesize==="+TpageInfoCount);
         var pp = new Pager(count,TpageInfoCount);  // 20 为总记录数（可随意或是指定），5 为页大小               
         pp.init('pager', getTitlePage,realPage,id,unitId,dir, appName,siteId);  
         //pp.init(id +"_display", getTitlePage, realPage, id, unitId, dir, appName, siteId);
            
        
            //$("#"+ id +"_display").html(html);
    
            /**if(realPage > 1) {
                //$("#pager").
                     var temp = {                    
                    pagesize:TpageInfoCount,
                    count:c,
                    css:"mj_pagefoot_green",
                    paging:function(page){
                    //    alert("44");
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
                            document.getElementById(id +"_display").innerHtml = newHtml;
                        //$("#"+ id +"_display").html(newHtml);                 
                    }
                }; */
             //    var newTemp = pagefoot(temp,document.getElementById("pager"));

//                 alert("temp");
            //    document.getElementById("page").innerHtml = page;
        //    }
        }
    }

    function getTitlePage(realPage,id,unitId,dir, appName,siteId){
        if(realPage > 0) {
            var c = realPage * TpageInfoCount;
            html = TgetPageHtml(realPage, unitId, dir, appName, "titleLinkPage", "0");
            // 替换路径
            var  replacePath = "/" + appName + "/release/site"+siteId+"/build/static";
            var temp = html.split(replacePath);
            html = "";
            for(var i = 0; i < temp.length; i++){
                html += temp[i];
            }
            // 替换图片或则附件路径
            replacePath = "/" + appName + "/release/site"+siteId;
            temp = html.split(replacePath);
            html = "";
            for(var k = 0; k < temp.length; k++){
                html += temp[k];
            }
            document.getElementById(id +"_display").innerHTML = html;
        //    return html;
        }
    }

    function TgetPageHtml(pageclickednumber, uid, strdir, appName, flag, displayMore) {
        var html = TgetHtml(strdir +"/"+ uid +"_"+ pageclickednumber +".xml", appName, flag, displayMore);
        return html;
    }