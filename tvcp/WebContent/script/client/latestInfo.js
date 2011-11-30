

	/*******************************导入xml文件***************************************************************************************************/
	
	String.prototype.Trim = function() {
		return this.replace(/(^\s*)|(\s*$)/g, ""); 
	}

	var xmlDoc;
	var moz = (typeof document.implementation != 'undefined') && (typeof document.implementation.createDocument != 'undefined');
	var ie = (typeof window.ActiveXObject != 'undefined');	
	
	function importXML(file) {
		 if (moz) {
			xmlDoc = document.implementation.createDocument("", "doc", null);
			//xmlDoc.onload = getXmlContent;
		 } else if (ie) {
		   xmlDoc = new ActiveXObject("MSXML2.DOMDocument.3.0");
		   xmlDoc.async = false;
		   while(xmlDoc.readyState != 4) {};
		 } 
		 xmlDoc.load(file);
	}	
	
	/** 处理以http开头的字符串截取 */
	String.prototype.startWith=function(str){
		if(str==null||str==""||this.length==0||str.length>this.length)
		    return false;
		if(this.substr(0,str.length)==str)
			return true;
		else
			return false;
	 	return true;
	 }

	/***********************************获得xml里面的内容*****************************************************/	

	// 单元类型
	var unitType;
	// 选择所有的栏目
	var allColumn;
	// 选择目录范围
	var selectCol;
	// 指定栏目
	var chooseColumn;
	// 显示行数
	var row;
	// 显示列数
	var col;
	// 标题字符
	var titleWordCount;
	// 标题前缀
	var titleHead;
	// 标题前缀是否是图片
	var titleHeadPic;
	// 标题后缀
	var titleEnd;
	// 标题后缀是否是图片
	var titleEndPic;
	// 显示记录总数
	var count;
	// 每页显示记录数
	var pageCount;
	// html代码
	var htmlContent;
	// 是否分页
	var ispage;
	// 更多
	var more;
	// 更多图片
	var morePic;
	// 更多最信息链接地址
	var moreLinkUrl;

	// 获得配置的xml文件信息 
	function getLatestInfoXmlContent(latestinfourl) {
		// 导入最新信息xml
		importXML(latestinfourl);
		if(moz) { 
				
		} else {
			var xmlfile = xmlDoc.selectNodes("/j2ee.cms/latest-info");
			unitType = xmlfile[0].getElementsByTagName("unitType")[0].firstChild.nodeValue;
			allColumn = xmlfile[0].getElementsByTagName("allColumn")[0].firstChild.nodeValue;
			selectCol = xmlfile[0].getElementsByTagName("selectCol")[0].firstChild.nodeValue;
			chooseColumn = xmlfile[0].getElementsByTagName("chooseColumn")[0].firstChild.nodeValue;
			row = xmlfile[0].getElementsByTagName("row")[0].firstChild.nodeValue;
			col = xmlfile[0].getElementsByTagName("col")[0].firstChild.nodeValue;
			TtitleLimit = xmlfile[0].getElementsByTagName("titleWordCount")[0].firstChild.nodeValue;
			TtitlePrefix = xmlfile[0].getElementsByTagName("titleHead")[0].firstChild.nodeValue;
			TtitlePrefixPic = xmlfile[0].getElementsByTagName("titleHeadPic")[0].firstChild.nodeValue;
			TtitleSuffix = xmlfile[0].getElementsByTagName("titleEnd")[0].firstChild.nodeValue;
			TtitleSuffixPic = xmlfile[0].getElementsByTagName("titleEndPic")[0].firstChild.nodeValue;
			count = xmlfile[0].getElementsByTagName("count")[0].firstChild.nodeValue;
			pageCount = xmlfile[0].getElementsByTagName("pageCount")[0].firstChild.nodeValue;	
			// 替换html解析代码中的回车
			ThtmlContent = xmlfile[0].getElementsByTagName("htmlContent")[0].firstChild.nodeValue;
			var reg = new RegExp("\r\n","g"); 
			var reg1 = new RegExp(" ","g"); 
			var code = ThtmlContent.replace(reg,"<br>"); 
			ThtmlContent = code.replace(reg1," ");
			
			ispage = xmlfile[0].getElementsByTagName("page")[0].firstChild.nodeValue;
			more = xmlfile[0].getElementsByTagName("moreLink")[0].firstChild.nodeValue;
			morePic = xmlfile[0].getElementsByTagName("moreLinkPic")[0].firstChild.nodeValue;
			moreLinkUrl = xmlfile[0].getElementsByTagName("moreLinkColumnUrl")[0].firstChild.nodeValue;
		}
	}

	/****************获取最新信息信息*********************************************************************************************/
	
	/** 获取最新信息分页信息 */
	function getPageLatestInfo(unitId, randId, dir, appName, pagelen, displayMore, siteId) {
		if(pagelen > 0) {
			var realPage = 0; 
			for(var i = 1; i <= pagelen; i++) {
				var path = dir + "/" + unitId + "_" + i + ".xml";
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
				var html = TgetHtml(dir + "/" + unitId + "_1.xml", appName, "latestInfoPage", displayMore);		
				// 替换路径
				var  replacePath = "/" + appName + "/release/site"+siteId+"/build/static";
				var temp = html.split(replacePath);
				html = "";
				for(var j = 0; j < temp.length; j++){
					html += temp[j];
				}
				// 替换图片或则附件路径
				replacePath = "/" + appName + "/release/site"+siteId
				temp = html.split(replacePath);
				html = "";
				for(var k = 0; k < temp.length; k++){
					html += temp[k];
				}
				
				$("#"+ randId +"_display").html(html);
				if(realPage > 1) {
					var c = realPage * pageCount;
					$("#pager").pagefoot({
						pagesize:pageCount,
						count:c,
						css:"mj_pagefoot_green",
						paging:function(page){
							var newHtml = TgetPageHtml(page, unitId, dir, appName, "latestInfoPage", displayMore);
							// 替换路径
							var  replacePath = "/" + appName + "/release/site"+siteId+"/build/static";
							var temp = newHtml.split(replacePath);
							newHtml = "";
							for(var t = 0; t < temp.length; t++){
								newHtml += temp[t];
							}
							// 替换图片或则附件路径
							replacePath = "/" + appName + "/release/site"+siteId
							temp = newHtml.split(replacePath);
							newHtml = "";
							for(var k = 0; k < temp.length; k++){
								newHtml += temp[k];
							}
							$("#"+ randId +"_display").html(newHtml); 
						}
					});
				}
				/*PageClick = function(pageclickednumber) {
					var newHtml = TgetPageHtml(pageclickednumber, unitId, dir, appName, "latestInfoPage");
					$("#pager").pager({ pagenumber: pageclickednumber, pagecount: realPage, buttonClickCallback: PageClick });
					$("#"+ randId +"_display").html(newHtml); 
				}
				$(document).ready(function() {
					$("#pager").pager({ pagenumber: 1, pagecount: realPage , buttonClickCallback: PageClick });
					$("#"+ randId +"_display").html(html);
				});*/
			}
		}else{
			$("#"+ randId +"_display").html("");
		}
	}

	/** 获取最新信息不分页信息 */
	function getNotPageLatestInfo(unitId, randId, dir, appName, realPage, displayMore, siteId) {
		var html = "";
		var page = 1;
		var realPage = 0;
		var path = dir + "/" + unitId + "_" + page + ".xml";
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
		 // 要分行和列(几行几列)
		 if(realPage != 0) {
			 html = TgetHtml(dir + "/" + unitId + "_1.xml", appName, "latestInfoPage", displayMore);	
		 }
		 // 替换路径
		 var  replacePath = "/" + appName + "/release/site"+siteId+"/build/static";
		 var temp = html.split(replacePath);
		 html = "";
		 for(var j = 0; j < temp.length; j++){
			 html += temp[j];
		 }
		 document.getElementById(randId +"_display").innerHTML = html;
	}


    /**********************************给页面调用的函数************************************************************************/

	// unitId             单元id
	// latestInfoxmlpath  最新信息xml配置路径
	// randId             页面生成的随机数
	// localPath          本地路径
	// dir                文件配置的目录
	// realPgae           显示几页
	// displayMore        是否显示更多   0:不显示 1：显示
	// siteId 

	function getPage(unitId, latestInfoxmlpath, randId, dir, appName, realPage, displayMore, siteId) {
		getLatestInfoXmlContent(latestInfoxmlpath);
		// 分页
		if(ispage == "yes") {
			// 获取分页数目	
			getPageLatestInfo(unitId, randId, dir, appName, realPage, "0", siteId);

		// 不分页
		} else {
			// 根据 row 和  col 来判断显示总数
			getNotPageLatestInfo(unitId, randId, dir, appName, realPage, displayMore, siteId);			
		}
	}