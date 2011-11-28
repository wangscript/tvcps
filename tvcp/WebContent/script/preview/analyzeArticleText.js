
/*******************************导入xml文件***************************************************************************************************/
	
	String.prototype.Trim = function() {
		return this.replace(/(^\s*)|(\s*$)/g, ""); 
	}

	var xmlDoc;
	var moz = (typeof document.implementation != 'undefined') && (typeof document.implementation.createDocument != 'undefined');
	var ie = (typeof window.ActiveXObject != 'undefined');	
	
	function importXMLTest(file) {
		 if (moz) {
			xmlDoc = document.implementation.createDocument("", "doc", null);
		 } else if (ie) {
		   xmlDoc = new ActiveXObject("MSXML2.DOMDocument.3.0");
		   xmlDoc.async = false;
		   while(xmlDoc.readyState != 4) {};
		 } 
		 xmlDoc.load(file);
	}

	 
	// 获得配置的xml文件信息 
	function getArticleTextXmlContent(xmlUrl, articleId, page) {
		importXMLTest(xmlUrl);
		var article_text = "content-"+articleId;
	    var xmlfile = xmlDoc.selectNodes("/baize/"+article_text+"/pages");
	    var content = xmlfile[0].getElementsByTagName("page"+page)[0].firstChild.nodeValue;
	    return content;
	}
 
    // 替换页面上显示的内容
	function replaceHtmlContentFromXmlFile(path, articleId, page, articleTextDisplay, siteId, appName) {
		var content = getArticleTextXmlContent(path, articleId, page);
		document.getElementById(articleTextDisplay).innerHTML = content;
	}