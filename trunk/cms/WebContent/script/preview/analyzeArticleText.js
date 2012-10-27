
/*******************************导入xml文件***************************************************************************************************/
	
	String.prototype.Trim = function() {
		return this.replace(/(^\s*)|(\s*$)/g, ""); 
	}

	var xmlDoc;
	var moz = (typeof document.implementation != 'undefined') && (typeof document.implementation.createDocument != 'undefined');
	var ie = (typeof window.ActiveXObject != 'undefined');	
	
	function importXMLTest(file) {
	var xmlDoc = null;
		/* if (moz) {
			 
			xmlDoc = document.implementation.createDocument("", "doc", null);
			//xmlDoc.onload = getXmlContent;
		 } else */
		 if (ie){
		   xmlDoc = new ActiveXObject("MSXML2.DOMDocument.3.0");
		   xmlDoc.async = false;
		   while(xmlDoc.readyState != 4) {}; 
		   xmlDoc.load(file);//如果用的是xml文件。		 
		 } else if (document.implementation && document.implementation.createDocument){	
			try{
			var xmlhttp = new window.XMLHttpRequest();
			xmlhttp.open("GET", file, false);
			xmlhttp.send(null);
			xmlDoc = xmlhttp.responseXML.documentElement;

		 }catch(e)   
		  {   
		   error=e.message;   
		  }  
		 }
		 return xmlDoc;
	}

	 
	// 获得配置的xml文件信息 
	function getArticleTextXmlContent(xmlUrl, articleId, page) {
		importXMLTest(xmlUrl);
		var article_text = "content-"+articleId;
	    var xmlfile = xmlDoc.selectNodes("/j2ee.cms/"+article_text+"/pages");

		if(ie){
			xmlfile = xmlDoc.selectNodes("/j2ee.cms/"+article_text+"/pages");
		}else{	
			var tempXmlfile = xmlDoc.childNodes;
			for(var j = 0 ; j < tempXmlfile.length; j++){
				if(tempXmlfile[j].nodeName == article_text+"/pages"){
					xmlfile = tempXmlfile[j];
				}	
			}
		}
	    var content = xmlfile[0].getElementsByTagName("page"+page)[0].firstChild.nodeValue;
	    return content;
	}
 
    // 替换页面上显示的内容
	function replaceHtmlContentFromXmlFile(path, articleId, page, articleTextDisplay, siteId, appName) {
		var content = getArticleTextXmlContent(path, articleId, page);
		var replacePath = "/" + appName + "/release/site"+siteId+"/build/static";
		// 替换路径
		var temp = content.split(replacePath);
		content = "";
		for(var i = 0; i < temp.length; i++){
			content += temp[i];
		}
		// 替换图片或则附件路径
		replacePath = "/" + appName + "/release/site"+siteId
		temp = content.split(replacePath);
		content = "";
		for(var j = 0; j < temp.length; j++){
			content += temp[j];
		}
		document.getElementById(articleTextDisplay).innerHTML = content;
	}