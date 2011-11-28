<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>标题链接</title>
<%@include file="/templates/headers/header.jsp"%>

<style type="text/css" media="all">	
	.form_cls label {
		width:70px;
		margin-right:5px;		
	}
	.form_cls {
		margin:10px auto;
	}

	.form_cls .span {
		border:0px;
		width:60px;
		margin-right:5px;
		text-align:left;
	}
	
	#display img
	{
	    max-width:307px;
	    max-height:135px;
		zoom:expression( function(elm) {
		          if(elm.width<elm.height) {
		             if (elm.height>135) {
		         		var oldVW = elm.height; elm.height=135;             
		                elm.width = elm.width*(135/oldVW );        
		             }
		          }
		           if(elm.width>elm.height) {
			     	if (elm.width>307) {
		         		var oldVW = elm.width; elm.width=307;             
		                elm.height = 110;        
		             }
		          }
		              elm.style.zoom = '1';     
		      }
		(this));
	}
	
</style>
<script>

	$(document).ready(function() {
		document.getElementById("unit_css1").innerHTML = $("#unit_css").val();
		//选择显示样式
		var chooseViewStyle = document.getElementById("chooseViewStyle").value;
		//选择内容来源
		var chooseContextFrom = document.getElementById("chooseContextFrom").value;
		//选择底线样式
		var chooseLineStyle = document.getElementById("chooseLineStyle").value;
		var moreLinkPic = "${titleLinkForm.moreLinkPic}";
		var titleHeadPic = "${titleLinkForm.titleHeadPic}";
		var titleEndPic = "${titleLinkForm.titleEndPic}";
		
		if(chooseViewStyle != null && chooseViewStyle != "" && chooseViewStyle != 0){
			document.all.viewStyle.value = chooseViewStyle;
			document.getElementById("display").innerHTML = $("#viewImg").val();
		}
		if(chooseContextFrom != null && chooseContextFrom != "" && chooseContextFrom != 0){
			document.all.contextFrom.value =chooseContextFrom;
			if(chooseContextFrom == 4) {
				document.getElementById("morehavecolumn").style.display = "none";
				document.getElementById("havecolumn").style.display = "";
				var fixedColumn = $("#fixedColumnExample").val();
				var columnName =  fixedColumn.split("##");
				$("#fixedColumnExample").val(columnName[1]);
			}
			if(chooseContextFrom == 3) {
				document.getElementById("morehavecolumn").style.display = "";
				document.getElementById("havecolumn").style.display = "none";
				var fixedColumn = $("#morefixedColumnExample").val();
				var columnName =  fixedColumn.split("##");
				$("#morefixedColumnExample").val(columnName[1]);
			}
		}
		if(chooseLineStyle != null && chooseLineStyle != "" && chooseLineStyle != 0){
			document.all.lineStyle.value =chooseLineStyle;
		}
		if(moreLinkPic == 1){
			$("#moreLinkPic").attr("checked", true);
		}
		if(titleHeadPic == 1){
			$("#columnPrefixPic").attr("checked", true);
		}
		if(titleEndPic == 1){
			$("#columnSuffixPic").attr("checked", true);
		}
	
	}); 

	  //选择的时候插入数据到文本框
	function insertAtCaret(txtobj, text){
		if (txtobj.createTextRange && txtobj.caretPos) { 
	       var caretPos = txtobj.caretPos;               //获取光标所在的位置
	       //替换光标处位置
	       caretPos.text =caretPos.text.charAt(caretPos.text.length - 1) =='' ?text + '' : text; 
	    } else { 
	       txtobj.value = text;   
	    }
	}

	//选择文本时保存光标位置-单击时同样
	function storePos (txtobj) { 
		if (txtobj.createTextRange)                      //获取选中的内容
			txtobj.caretPos = document.selection.createRange().duplicate(); 
	} 

	//参数设置与扩展参数切换
	function changeSet(paramSet){
		if(paramSet=="paramSet1"){
			document.getElementById('paramSet1').style.display="block";
			document.getElementById('paramSet2').style.display="none";
		}else{
			document.getElementById('paramSet1').style.display="none";
			document.getElementById('paramSet2').style.display="block";
		}
	}

	// 改变内容来源
	function changesource(obj) {
		var columnId = $("#unit_columnId").val();	
		// 指定栏目或者指定栏目的父栏目
		if(obj.value == "3"){	
			$("#morefixedColumnExample").val("");
			$("#morefixedColumn").val("");
			document.all.morehavecolumn.style.display = "block";	
			document.all.havecolumn.style.display = "none";	
			 
		}else if(obj.value == "4"){
			$("#fixedColumnExample").val("");
			$("#fixedColumn").val("");
			document.all.morehavecolumn.style.display = "none";	
			document.all.havecolumn.style.display = "block";		
		}else {		
			document.all.morehavecolumn.style.display = "none";
			document.all.havecolumn.style.display = "none";			
		}
		// 网站下面的单元
		if(columnId == 0) {
			$("#fieldCode").attr("disabled", "disabled");
		} else {
			// 非栏目 、 指定栏目 、指定栏目的父栏目
			if(obj.value == 0 || obj.value == "" || obj.value == 3 || obj.value == 4) {
				 
				$("#fieldCode").attr("disabled", "disabled");
			// 当前栏目、  当前栏目的父栏目
			} else if(obj.value == 1 || obj.value == 2) {
				$.ajax({
					url : '<c:url value="/titleLink.do?dealMethod=findFieldCode&nodeId="/>'+ columnId + "&contextFrom="+ obj.value,
					type: "post",
				success : function(msg) {
						var str = msg.split("###");
						var selobj = document.getElementById("fieldCode");
						selobj.disabled = false;  
						if(str[0] != "") {
							var attributeName = str[0];
							var attributeFieldName = str[1];
							var name = attributeName.split(":::");			 // 标题
							var fieldName = attributeFieldName.split(":::"); // title
							selobj.length = name.length+1;
							selobj.options[0].value = "";
							selobj.options[0].text = "---字段标签---";					
							for(var i = 0; i < name.length; i++) {
								selobj.options[i+1].value = "<!--"+fieldName[i]+"-->";
								
								selobj.options[i+1].text = name[i];
							}
						} else {   
							selobj.length = 1;
							selobj.options[0].value = "";
							selobj.options[0].text = "---字段标签---";
						}
					}
				});
			}
		}
		
	}

	//选择图片，把图片的路径赋给这个ID
	function chooseMore() {
		win = parent.showWindow("chooseMore", "选择更多","<c:url value='/picture.do?dealMethod=uploadPicList&columnLink=3&nodeId=f004&nodeName=图片类别'/>", 0, 0, 940, 555);
	}
	
	function changeshow(txtobj,obj) {	 
		$.ajax({
			url: "templateUnitStyle.do?dealMethod=findStyleById&styleId="+obj.value+"&categoryId="+$("#unit_categoryId").val(),
		   type: "post", 
	    success: function(msg) {			 
			    str = msg.split("##");  
			    if(str.length == 2) {
				    document.getElementById("display").innerHTML = str[0];
					document.getElementById("htmlContent").value = str[1];
			   } else {
					document.getElementById("display").innerHTML = "";
					document.getElementById("htmlContent").value = "";
			   }			 
     		}
		}); 
	

	}


	//显示样式管理
	function showStyleManager(){
		win = showWindow("showStyleManager","样式管理","<c:url value='/templateUnitStyle.do?dealMethod=list&categoryId="+$("#unit_categoryId").val()+"'/>",0,0,850,590);	 			
	}

	//保存数据
	function btn_save() {
		var viewStyle = $("#viewStyle :selected").val();
		if(viewStyle == 0 || viewStyle == "" || viewStyle == null) {	
			alert("你必须先选择样式");
			return false;
		}
		var contextFrom = $("#contextFrom :selected").val();
		if(contextFrom == 0) {
			alert("你必须先选择栏目内容来源");
			return false;
		}
		if(contextFrom == 4) {
			if($("#fixedColumn").val().isEmpty()) {
				alert("请选择指定栏目");
				return false;
			} 
		}
		if(contextFrom == 3) {
			if($("#morefixedColumn").val().isEmpty()) {
				alert("请选择指定栏目");
				return false;
			} 
		}
		if($("#htmlContent").val().trim() == null || $("#htmlContent").val().trim() == "") {
			alert("效果源码不能为空");
			return false;
		}	
		if($("#columnPrefixPic").attr("checked") == true) {
			$("#columnPrefixPic").val(1);
		} else {
			$("#columnPrefixPic").val(0);
		}
		if($("#columnSuffixPic").attr("checked") == true) {
			$("#columnSuffixPic").val(1);
		} else {
			$("#columnSuffixPic").val(0);
		}
		if($("#moreLinkPic").attr("checked") == true) {
			$("#moreLinkPic").val(1);
		} else {
			$("#moreLinkPic").val(0);
		}		
		$("#dealMethod").val("saveConfig");
	 	var options = {	 	
 		    	url: "titleLink.do",
 		    success: function(msg) { 		    		
 		    		 alert(msg); 		    		
 		    } 
 		  };
		$('#titleLinkForm').ajaxSubmit(options);	
		//设置标记为已保存
		document.getElementById("hasSaved").value = "Y";
	}

	//网站内保存数据
	function btn_site_save() {	
		var viewStyle = $("#viewStyle :selected").val();
		if(viewStyle == 0 || viewStyle == "" || viewStyle == null) {	
			alert("你必须先选择样式");
			return false;
		}
		var contextFrom = $("#contextFrom :selected").val();
		if(contextFrom == 0) {
			alert("你必须先选择栏目内容来源");
			return false;
		}
		if(contextFrom == 4) {
			if($("#fixedColumn").val().isEmpty()) {
				alert("请选择指定栏目");
				return false;
			} 
		}
		if(contextFrom == 3) {
			if($("#morefixedColumn").val().isEmpty()) {
				alert("请选择指定栏目");
				return false;
			} 
		}
		if($("#htmlContent").val().trim() == null || $("#htmlContent").val().trim() == "") {
			alert("效果源码不能为空");
			return false;
		}	
		if($("#columnPrefixPic").attr("checked") == true) {
			$("#columnPrefixPic").val(1);
		} else {
			$("#columnPrefixPic").val(0);
		}
		if($("#columnSuffixPic").attr("checked") == true) {
			$("#columnSuffixPic").val(1);
		} else {
			$("#columnSuffixPic").val(0);
		}
		if($("#moreLinkPic").attr("checked") == true) {
			$("#moreLinkPic").val(1);
		} else {
			$("#moreLinkPic").val(0);
		}	
		$("#dealMethod").val("saveSiteConfig");
	 	var options = {	 	
 		    	url: "titleLink.do",
 		    success: function(msg) { 		    		
 		    		 alert(msg); 		    		
 		    } 
 		};
 		$('#titleLinkForm').ajaxSubmit(options);	
 		//设置标记为已保存
		document.getElementById("hasSaved").value = "Y";
	}
	
	function closeNewChild() {
		closeWindow(win);
	}
	
	//设置CSS
	function managerCss() {
		var css = $("#unit_css").val();
		win = showWindow("css", "管理css", "<c:url value='/module/template_set/manage_css.jsp?css=" + css + "'/>", 0, 0,490,350);
	}

	// 指定栏目
	function fixedColumn11() {
		// win = showWindow("chooseColumn", "指定栏目", "<c:url value='/module/template_set/unit/choose_column.jsp'/>", 170, 20, 300, 300);
		var type = $("#contextFrom :selected").val();
		win = showWindow("chooseColumn", "指定栏目", "<c:url value='/module/template_set/unit/title_link/fixed_titleLink_column.jsp?type="+ type +"'/>", 170, 20, 300, 300);
	}
	
	function morefixedColumn1(){
		var type = $("#contextFrom :selected").val();
		win = showWindow("morechooseColumn", "指定栏目", "<c:url value='/module/template_set/unit/title_link/morefixed_titleLink_column.jsp?type="+ type +"'/>", 170, 20, 300, 300);
	}
	// 选择标题前缀
	function chooseTitlePrefix() {
		win = parent.showWindow("chooseTitlePrefix", "选择标题前缀","<c:url value='/picture.do?dealMethod=uploadPicList&columnLink=1&nodeId=f004&nodeName=图片类别'/>", 0, 0, 940, 555);
	}

	// 选择标题后缀
	function chooseTitleSuffix() {
		win = parent.showWindow("chooseTitleSuffix", "选择标题后缀","<c:url value='/picture.do?dealMethod=uploadPicList&columnLink=2&nodeId=f004&nodeName=图片类别'/>", 0, 0, 940, 555);
	}

	// 用于键盘事件（只允许输入整数：包括负数）
	function myKeyDown(id) {
		var k=window.event.keyCode;   
		if(document.getElementById(id).value.length >=1 ){
			if ((k==46)||(k==8)||(k==189)||(k==190)||(k==110)|| (k>=48 && k<=57)||(k>=96 && k<=105)||(k>=37 && k<=40)){
				
			}else if(k==13){
				window.event.keyCode = 9;
			}else{
				window.event.returnValue = false;
			}
		}else{
			if ((k==46)||(k==8)||(k==189)||(k==109)||(k==190)||(k==110)|| (k>=48 && k<=57)||(k>=96 && k<=105)||(k>=37 && k<=40)){
				
			}else if(k==13){
				window.event.keyCode = 9;
			}else{
				window.event.returnValue = false;
			}
		}
	}
	
</script>
</head>
<body>
	<script type="text/javascript" src="<c:url value="/script/officecolor.js"/>"></script>
	<form id="titleLinkForm" action="<c:url value="/titleLink.do"/>" name="titleLinkForm" method="post">
	<input type="hidden" value="${appName}" id="apprealpath"/>	
	<input type="hidden" id="unit_unitId" name="unit_unitId" value="${titleLinkForm.unit_unitId}"/>
	<input type="hidden" id="unit_categoryId" name="unit_categoryId" value="${titleLinkForm.unit_categoryId}"/>
	<input type="hidden" name="dealMethod" id="dealMethod" />
	<input type="hidden" name="chooseViewStyle" id="chooseViewStyle" value='${titleLinkForm.viewStyle}' >
	<input type="hidden" name="chooseContextFrom" id="chooseContextFrom" value="${titleLinkForm.contextFrom}" > 
 	<input type="hidden" name="chooseLineStyle" id="chooseLineStyle" value="${titleLinkForm.lineStyle}" >
	<input type="hidden" id="unit_columnId" name="unit_columnId" value="${titleLinkForm.unit_columnId}"/>
	<input type="hidden" id="hasSaved" name="hasSaved" value="N" />	

	<span style="display:none">
		<div id="unit_css1"  ></div>		
		<TEXTAREA id="unit_css" name="unit_css" >${titleLinkForm.unit_css}</TEXTAREA>  		 
	</span>
	<input type="hidden" name="viewImg" id="viewImg" value='${titleLinkForm.viewImg}'/>
		<div id="belowleft" style="position:absolute; left:3px; top:10px; width:278px; height:400px; z-index:2; ">
			<fieldset style="height:400" id="obj1" style="display:">
				<legend style="color:#0000ff;">
					<a href="javascript:;" onclick="changeSet('paramSet1');"><font style="color:#0000ff;">参数设置</font></a>&nbsp;||&nbsp;
					<a href="javascript:;" onclick="changeSet('paramSet2');"><font style="color:#0000ff;">扩展参数</font></a>
				</legend>
				<div id="paramSet1"  class="form_cls"  style="display: ;width:100%;align:center">
					<li>
						<select name="viewStyle" id="viewStyle"   style="width:240px"  onchange="changeshow(htmlContent,this)">
							<option value='0' >请选择显示样式</option>
							<c:forEach var="templateUnitStyle" items="${titleLinkForm.templateUnitStyleList}" varStatus="s" step="1">									
								<option value='${templateUnitStyle.id}'>${templateUnitStyle.name}</option>						
							</c:forEach>
						</select>
					</li>
					<li>
						<select name="contextFrom"  id="contextFrom"   style="width:240px" onchange="changesource(this)">
							<option value='0'>请选择内容来源</option>						
							<option value="1" >当前栏目</option>   
							<option value="2">当前栏目的父栏目</option>   						
							<option value="3">指定栏目</option>   
							<option value="4">指定栏目的父栏目</option> 
						</select>
					</li>
					<span id="havecolumn" style="display:none;border:0">
						<li>
							<label>指定栏目</label>						
							<input type="text"    name="fixedColumnExample" id="fixedColumnExample" value="${titleLinkForm.columnName}" maxlength="255" size="13" readonly />
							<input type="hidden" name="columnName" id="fixedColumn" value="${titleLinkForm.columnName}" />
							<input type="button"  class="btn_small"   title="指定栏目" value="√" onclick="fixedColumn11()">
						</li>
					</span>	
					<span id="morehavecolumn" style="display:none;border:0">
						<li>
							<label>指定栏目</label>		
							<textarea name="morefixedColumnExample" id="morefixedColumnExample"  rows="3" cols="12" readonly>${titleLinkForm.moreColumnName}</textarea>						
							<input type="hidden" name="moreColumnName" id="morefixedColumn" value="${titleLinkForm.moreColumnName}" />
							<input type="button"  class="btn_small"   title="指定栏目" value="√" onclick="morefixedColumn1()">
						</li>
					</span>			
					<li>
						<label>信息起始</label>	
						<input     type="text" name="start"  id="start"  value="${titleLinkForm.start}"  maxlength="4" SIZE="3" onkeyup="value=value.replace(/[^\d]/g,'')"/>
					</li> 
					<li>
						<label>信息显示</label>				
						<input type="text" name="row" id="row" value="${titleLinkForm.row}"   maxlength="3" SIZE="3" onkeyup="value=value.replace(/[^\d]/g,'')"/>
						<span>行</span>				
						<input type="text" name="col" id="col" value="${titleLinkForm.col}"  maxlength="3" SIZE="3" onkeyup="value=value.replace(/[^\d]/g,'')"/>
						<span>列</span>
					</li>				
					<li>
						<label>标题字数</label>
						<input type="text" name="titleLimit"   value="${titleLinkForm.titleLimit}" id="titleLimit"   maxlength="3" SIZE="3" onkeyup="value=value.replace(/[^\d]/g,'')"/>
					</li>				
					<li>
						<label>更多</label>
						<input   type="text" name="moreLink"  id="moreLink"  value="${titleLinkForm.moreLink}" title="更多..." maxlength="255" SIZE="5">
						<input type="button" title="选择“更多”标志图" class="btn_small"  value="√" onclick="chooseMore()">
						<span class="span">
							<input type="checkbox" class="input_blank"   name="moreLinkPic" id="moreLinkPic"  value="${titleLinkForm.moreLinkPic }">
						图形</span>
					</li>

				</div>
				<div id="paramSet2" class="form_cls"   style="display:none;width:100%;align:center">
					<li>
						<label>标题前缀</label>
						<input   type="text" name="titleHead" id="columnPrefix" value="${titleLinkForm.titleHead}" title="" maxlength="255" SIZE="10">
						<input type="button" class="btn_small" title="选择标题前缀图"  value="√" onclick="chooseTitlePrefix()"/>
					</li>
					<li>
						<label>前缀有效期</label>
						<input   type="text" name="titleHeadValidity" id="titleHeadValidity"   value="${titleLinkForm.titleHeadValidity}"  title="-1代表永远有效"   maxlength="4" SIZE="3"  onkeydown="myKeyDown('titleHeadValidity')"/>
						<span>天</span>
						<input type="checkbox" class="input_blank"  name="titleHeadPic" id="columnPrefixPic" value="${titleLinkForm.titleHeadPic }"/>
						<span>图形</span>
					</li>
					<li>
						<label>标题后缀</label>
						<input   type="text" name="titleEnd"  id="columnSuffix" value="${titleLinkForm.titleEnd}" title="" maxlength="255" SIZE="10"> 
						<input type="button"  class="btn_small" title="选择标题后缀图"  value="√" onclick="chooseTitleSuffix()"/>
					</li>
					<li>
						<label>后缀有效期</label>
						<input   type="text" name="titleEndValidity"   id="titleEndValidity"   value="${titleLinkForm.titleEndValidity}" title="-1代表永远有效"  maxlength="4" SIZE="3" onkeydown="myKeyDown('titleEndValidity')"/>
						<span>天</span>						
						<input type="checkbox" class="input_blank"  name="titleEndPic" id="columnSuffixPic"  value="${titleLinkForm.titleEndPic }">
						<span>图形</span>
					</li>
					<li>
						<label>底线样式</label>
						<select name="lineStyle" id="lineStyle"   style="font-family:宋体;font-size:12px;" >
							<option value="">无底线</option>
							<option value="dotted">…………………</option>
							<option value="dashed">--------------</option>
							<option value="solid">───────</option>
						</select>
					</li>
					<li>
						<label>底线隔行</label>
						<input   name="lineGroup" id="lineGroup" type="text"    value="${titleLinkForm.lineGroup}"   maxlength="3" size="3" onkeyup="value=value.replace(/[^\d]/g,'')" /> <span>行</span>		
					</li>
					<li>
						<label>奇行背景色</label>
						<input  type="text" id="oddColor" name="oddColor"  value="${titleLinkForm.oddColor}" size="10"   onclick="colordialog()" />
					</li>
					<li>
						<label>偶行背景色</label>
						<input  type="text" id="evenColor" name="evenColor"  value="${titleLinkForm.evenColor}" size="10"  onclick="colordialog()" />
					</li>
					<li>
						<label>指定行加粗</label>
						<input  type="text" name="strongTitle" id="strongTitle"  value="${titleLinkForm.strongTitle}" maxlength="10" size="10"/>
						<span>如:1,3,5</span>
					</li>
					<li>
				</div>
			</fieldset>
		</div>		   
		<div id="belowrightTop" style="position:absolute; left:283px; top:10px; width:320px; height:140px; z-index:2;">
			<fieldset id="displayEffect" style="width:320px;height:140px;"><legend style="color:#0000ff;"><b>显示效果</b>&nbsp;</legend>	
				<div id="display"  style="width:307px;height:135px;"></div>
			</fieldset>  
		</div>
		
		<div id="belowleftBelow" style="position:absolute; left:283px; top:160px; width:320px; height:260px; z-index:2; ">
			<fieldset id="code" style="width:320px;height:250px;"><legend style="color:#0000ff;"><b>效果源码</b>&nbsp;</legend>
			   <SELECT   id="htmlCode" name="htmlCode" style="width:103px" onChange="insertAtCaret(htmlContent,this.value)">
				<OPTION VALUE=''>---HTML标签---</OPTION> 
				<option value="<table><tr><td></td></tr></table>">一行一列表格</option>
				<option value="<tr><td></td></tr>">表格插入一行</option>
				<option value="<td></td>">表格插入一列</option>
				<option value='<img src="" border="0"/>'/>图片</option>
				<option value="<br/>">回车</option>
				<option value="<pre></pre>">预格式化</option>
				<option value='<a href="" target="_self"></a>'>链接</option>
				<option value="<b></b>">粗体</option>
				<option value="<i></i>">斜体</option>
				<option value="<u></u>">下划线</option>
				<option value='<font size="" color=""></font>'>字体</option>
				<option value='<form method="post" name="" action="" target=""></form>'>表单</option>
				<option value='<input type="text" name="">'>输入框</option>
				<option value='<select name=""><option></option></select>'>列表框</option>
				<option value='<input type="radio" name="">'>单选按钮</option>
				<option value='<input type="checkbox" name="">'>复选框</option>
				<option value='<textarea name="" rows="" cols=""></textarea>'>文本框</option>
				<option value='<input type="reset" value="重置">'>重置按钮</option>
				<option value='<input type="submit" value="提交">'>提交按钮</option>
				<option value='<input type="password" name="">'>密码输入框</option>
				<option value='<input type="hidden" name="">'>隐含字段</option>
				<option value='<input type="image" src="">'>图片按钮</option>
				</SELECT>
			<select  name="unitCode" id="unitCode" style="width:103px" onchange="insertAtCaret(htmlContent,this.value)">
				<option value=''>---单元标签---</option>
				<c:forEach var="commonMap" items="${titleLinkForm.commonMap}" >
					<option value="<c:out value="${commonMap.value}" />"><c:out value="${commonMap.key}" /></option>	
				</c:forEach>
				<c:forEach var="titleMap" items="${titleLinkForm.map}" >									
						<option value="<c:out value="${titleMap.value}" />"><c:out value="${titleMap.key}" /></option>						
				</c:forEach>

			</select>
			<SELECT  name="fieldCode" id="fieldCode"  style="width:103px" onChange="insertAtCaret(htmlContent,this.value)">
				<option value=''>---字段标签---</option>	
				<c:forEach var="articleAttribute" items="${titleLinkForm.articleAttributeList}" varStatus="s" step="1">									
					<option value="<!--${articleAttribute.fieldName}-->" >${articleAttribute.attributeName}</option>						
				</c:forEach>
			</SELECT>
	
			<input type="button" value="样式管理"  class="btn_small"  onClick="showStyleManager()">	
			<input type="button" value="css管理"  class="btn_small"  onClick="managerCss()">		
				<TEXTAREA id="htmlContent"  name="htmlContent" ROWS="11"  onselect="storePos(this);" onclick="storePos(this);" onkeyup="storePos(this);" COLS="37">${titleLinkForm.htmlContent}</TEXTAREA>
			</fieldset> 				
	    </div>
		<div id="saveArea" style="position:absolute;height:15px;z-index:4;left: 250px;top: 410px;">			
			<input type="button" value="保存"  class="btn_normal"  onclick="btn_save()"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" value="站内保存"  class="btn_normal"  onclick="btn_site_save()"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	

		</div>
	</form>

</body>
</html>