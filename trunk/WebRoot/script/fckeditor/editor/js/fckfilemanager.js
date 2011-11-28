function doOptFileManager(obj,label, queform, url, localhost) {		
	//返回值
	var returnvaluestr = "";
	switch( label ) {
		//确定
		case "sure":		
			returnvaluestr += "";
			//超级链接
			var hyperlink = obj.hyperlink.value;
			if(hyperlink != "" && hyperlink != null) {
				returnvaluestr += "<a href='" + hyperlink + "' ";
				//目标窗口
				var target = obj.target.value;
				if(target != "" && target != null) {
					returnvaluestr += " target='" + target + "' ";
				}
				returnvaluestr += ">";
			}			
			//读取单选按钮 
			//单选按钮的值
			var sel_value ;
			if(obj.binsert[0].checked==true){
				sel_value = 0;
			}else if(obj.binsert[1].checked==true){
				sel_value = 1;
			}
			if(sel_value == 0) {	
				//插入原图
				var picwidth = obj.width.value;  //原图的宽度
				var picheight = obj.height.value;//原图的高度
				returnvaluestr += " <img src='" + localhost + "' width='"+ picwidth+"' height='"+picheight+"'" ;
				//returnvaluestr += " <img  width='" + picwidth + "'  height='" + picheight + "' src=\"http://localhost:8080/ccms1.0/release/site2/upload/picture/200905121745002341.jpg\"" ;
			}
			if(sel_value == 1) {	
				//插入缩略图
				var suoluwidth = obj.suolvwidth.value;  //缩略图的宽度
				var suolvheight = obj.suolvheight.value;//缩略图的高度
				document.delhidden.location = "picture.do?dealMethod=scaleImage&picurl=" + localhost + "&width=" + suoluwidth + "&height=" + suolvheight;
				returnvaluestr += " <img   src='" + localhost + "' width='"+ suoluwidth+"' height='"+suolvheight+"'" ;
			
				//document.delhidden.location = "picture.do?dealMethod=scaleImage&picurl=/release/site2/upload/picture/200905121745002341.jpg&width=" + suoluwidth + "&height=" + suolvheight;
				//returnvaluestr += " <img   width=" + suoluwidth + " height=" + suolvheight + " src=\"http://localhost:8080/ccms1.0/release/site2/upload/picture/200905121745002341.jpg\"";
			}
			//获取对齐方式的值
			var align = obj.align.value;
			if(align != "" && align != null) {
				returnvaluestr += " align='" + align + "' ";
			}
			//水平距离
			var hspace = obj.hspace.value;
			if(hspace != "" && hspace != null) {
				returnvaluestr += " hspace='" + hspace + " '";
			}
			//竖直距离
			var vspace = obj.vspace.value;
			if(vspace != "" && vspace != null) {
				returnvaluestr += " vspace='" + vspace + "' ";
			}
			//边框宽度
			var borderwidth = obj.borderwidth.value;
			if(borderwidth != "" && borderwidth != null) {
				returnvaluestr += " border='" + borderwidth + "'";
			}
			//边框颜色
			var inputcolor = obj.inputcolor.value;
			if(inputcolor != "" && inputcolor != null && inputcolor != "颜色选择") {
				returnvaluestr += " style='border-color:" + inputcolor + "' ";
			}
			//文字提示
			var alt = obj.alt.value;
			if(alt != "" && alt != null) {
				returnvaluestr += " alt='" + alt + "'";
			}
			returnvaluestr += " />";
			if(hyperlink != "" && hyperlink != null) {
				returnvaluestr += "</a>";
			}
			returnvaluestr += "";	
			break;
	}
	return returnvaluestr;
}

// 处理插入附件的操作
function proccessAttachmentValue(obj, label, arrNames, arrUrls) {		
	//返回值
	var returnvaluestr = "";
	switch( label ) {
		//确定	
		case "sure":	 
			for(var i = 0; i < arrNames.length; i++) {
				returnvaluestr += " ";
				//获取对齐方式的值
				var align = obj.align.value;
				if(align != "" && align != null) {
					returnvaluestr += "<span style=\"width:100%;text-align:" + align + ";\">";
				}			
				//获得下载的地址
				var url = arrUrls[i];
				returnvaluestr += "<a href='" + url + "'> ";
				var name = arrNames[i];
				returnvaluestr += name;
				returnvaluestr += "</a>";
				if(align != "" && align != null) {
					returnvaluestr += "</span>";
				}
				returnvaluestr += "";	
			}	 
	}
	return returnvaluestr;
}

//处理Flash设置属性的操作
function processDoFlash(obj, label, location) {
	//返回值
	var returnvaluestr="";
	returnvaluestr += "<embed";
	switch( label ) {
		//确定
		case "sure":	
			
			//获取Flash宽度值
			var width = obj.width.value;
			if(width != "" && width != null) {
				returnvaluestr += " width='" + width + "'";
			}
			//获取Flash高度值
			var height = obj.height.value;
			if(height != "" && height != null) {
				returnvaluestr += " height='" + height + "'";
			}
			returnvaluestr += " src=\""+ location +"\"";
			//获取Flash对齐方式的值
			var align = obj.align.value;
			if(align != "" && align != null) {
				returnvaluestr += " align='" + align + "' ";
			}
			//获取水平距离
			var hspace = obj.hspace.value;
			if(hspace != "" && hspace != null) {
				returnvaluestr += " hspace='" + hspace + " '";
			}
			//获取竖直距离
			var vspace = obj.vspace.value;
			if(vspace != "" && vspace != null) {
				returnvaluestr += " vspace='" + vspace + "' ";
			}
			//是否自动播放
			var type = obj.type.value;
			if(type != "" && type != null) {
				switch( type ) {
				case "true":returnvaluestr += " pluginspage=\"http://www.macromedia.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash\"";break;
				case "false":returnvaluestr += " pluginspage=\"http://www.macromedia.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash\"" + " play=false ";break;
				}
			}
			//是否循环
			var cycle = obj.cycle.value;
			if(cycle != "" && cycle != null) {
				switch( cycle ) {
				case "true":returnvaluestr += "loop=ture";break;
				case "false":returnvaluestr += "loop=false";break;
				}
			}
			//获取画面品质值
			var quality = obj.quality.value;
			if(quality != "" && quality != null) {
				returnvaluestr += " quality='" + quality + "' ";
			}
		
			returnvaluestr += "> </embed>";
	}
	return returnvaluestr;
}

//处理插入js的操作
function proccessScriptValue(obj, label, arrNames, arrUrls) {		
	//返回值
	var returnvaluestr = "";
	switch( label ) {
		//确定	
		case "sure":	 
			for(var i = 0; i < arrNames.length; i++) {
				returnvaluestr += "<script type=\"text/javascript\" src=\"";
				//获得下载的地址
				var url = arrUrls[i];
				returnvaluestr += url ;
				returnvaluestr += "\"></script>";	
			}	 
	}
	return returnvaluestr;
}