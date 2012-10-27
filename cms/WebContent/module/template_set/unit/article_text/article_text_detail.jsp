<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>文章正文</title>
<%@include file="/templates/headers/header.jsp"%>
<style type="text/css" media="all">
	.form_cls label {
		width:50px;
		margin-right:5px;		
	}
	.form_cls {
		margin:10px auto;
	}
	.form_cls li {
		list-style-type:none;
		width:246px;
		margin:5px;	
		float:left; 
	}
	.form_cls .span {
		border:0px;
		width:60px;
		margin-right:5px;
		text-align:left;
	}

	#display img
	{
	   /* max-width:307px;
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
		(this));*/
	}
</style>

<script type="text/javascript">
	$(document).ready(function() {
		//选择显示样式
		var articleTextStyle = $("#getArticleStyle").val();

		var chooseViewStyle = document.getElementById("chooseViewStyle").value;
		if(articleTextStyle != null && articleTextStyle != "" && articleTextStyle != 0) {			
			document.all.articleTextStyle.value = chooseViewStyle;   
			document.getElementById("display").innerHTML = $("#getArticleStyle").val();
		}
		if($("#columnSuffixPic").val() == 1) {
			$("#columnSuffixPic").attr("checked", true);
		}
	});

	//保存数据
	function btn_save() {	
		// css样式
		//var a = $(["#unit_css"]).val();

		var articleTextStyle = $("#articleTextStyle :selected").val();
		if(articleTextStyle == 0) {	
			alert("你必须先选择样式");
			return false;
		}
		if($("#htmlContent").val().trim() == null || $("#htmlContent").val().trim() == "") {
			alert("效果源码不能为空");
			return false;
		}
		// 文章评论
		if($("#columnSuffixPic").attr("checked") == true) {
			$("#columnSuffixPic").val(1);
		} else {
			$("#columnSuffixPic").val(0);
		}
		$("#dealMethod").val("saveConfig");
	 	var options = {	 	
 		    	url: "articleText.do",
 		    success: function(msg) { 		    		
 		    		 alert(msg); 		    		
 		    } 
 		}; 
		$('#articleTextForm').ajaxSubmit(options);	
		//设置标记为已保存
		document.getElementById("hasSaved").value = "Y";
	}

	//网站内保存数据
	function btn_site_save() {	
		var articleTextStyle = $("#articleTextStyle :selected").val();
		if(articleTextStyle == 0) {	
			alert("你必须先选择样式");
			return false;
		}
		if($("#htmlContent").val().trim() == null || $("#htmlContent").val().trim() == "") {
			alert("效果源码不能为空");
			return false;
		}
		// 文章评论
		if($("#columnSuffixPic").attr("checked") == true) {
			$("#columnSuffixPic").val(1);
		} else {
			$("#columnSuffixPic").val(0);
		}
		$("#dealMethod").val("saveSiteConfig");
	 	var options = {	 	
 		    	url: "articleText.do",
 		    success: function(msg) { 		    		
 		    		 alert(msg); 		    		
 		    } 
 		};
		$('#articleTextForm').ajaxSubmit(options);	
		//设置标记为已保存
		document.getElementById("hasSaved").value = "Y";
	}
	
	//选择的时候插入数据到文本框
	function insertAtCaret(txtobj, text) {
		if (txtobj.createTextRange && txtobj.caretPos) { 
	       var caretPos = txtobj.caretPos;               //获取光标所在的位置
	       //替换光标处位置
	       caretPos.text = caretPos.text.charAt(caretPos.text.length - 1) =='' ?text + '' : text; 
	    } else {
	       txtobj.value = text;   
	    }
	}
	  
	//选择文本时保存光标位置-单击时同样   
	function storePos(txtobj) { 
		if (txtobj.createTextRange) {                     //获取选中的内容
			txtobj.caretPos = document.selection.createRange().duplicate(); 
		}
	} 

	//改变显示样式
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
	
	// 显示样式管理
	function showStyleManager(){
		win = showWindow("showStyleManager", "样式管理", "<c:url value='/templateUnitStyle.do?dealMethod=list&categoryId=" + $("#unit_categoryId").val()+"'/>", 0, 0, 850, 590);	 			
	}

	// 评论
	function selectArticleTextComment() {
		win = parent.showWindow("commment", "评论", "<c:url value='/picture.do?dealMethod=uploadPicList&columnLink=2&nodeId=-1&nodeName=图片类别'/>", 0, 0, 913, 580);
	}

	// 管理css
	function managerCss() {
		var css = $("#unit_css").val();
		win = showWindow("css", "管理css", "<c:url value='/module/template_set/manage_css.jsp?css=" + css + "'/>", 0, 0, 490, 350);
	}
	
	function closeNewChild() {		
		closeWindow(win);
	}

	function changeDisplay(param) {
		if(param == "add") {
			 document.getElementById("displayArea").style.height = 140+"px";	
			 document.getElementById("displayEffect").style.height = 140+"px";
			 document.getElementById("codeArea").style.height = 260+"px";
			 document.getElementById("code").style.height = 250+"px";
			 document.getElementById("sub").style.display = "";
			 document.getElementById("add").style.display = "none";
			 document.getElementById("displayCode").style.display = "";
			 document.getElementById("displayCode").style.height = 250+"px";
			 document.getElementById("displayCode").style.top = 20+"px";
			 document.getElementById("codeArea").style.top = 160+"px";
			 document.getElementById("display").style.height = 140+"px";
			 //document.getElementById("display").style.overflow = "hidden";
			 //document.getElementById("codeArea").style.display = "";
		} else {
			document.getElementById("display").style.overflow = "visible";
			 document.getElementById("displayArea").style.height = 400+"px";	
			 document.getElementById("displayEffect").style.height = 400+"px";
			 document.getElementById("codeArea").style.height = 10+"px";
			 document.getElementById("code").style.height = 10+"px";
			 document.getElementById("sub").style.display = "none";
			 document.getElementById("add").style.display = "";
			 document.getElementById("displayCode").style.display = "none";
			 document.getElementById("displayCode").style.height = 0+"px";
			 document.getElementById("codeArea").style.top = 380+"px";
			 document.getElementById("displayCode").style.top = 0+"px";
		}
	}

</script>
</head>
<body>
<form id="articleTextForm" action="<c:url value="/articleText.do"/>" name="articleTextForm" method="post">
	<input type="hidden" value="${appName}" id="apprealpath"/>
	<input type="hidden" id="unit_unitId" name="unit_unitId" value="${articleTextForm.unit_unitId}"/>
	<input type="hidden" id="unit_categoryId" name="unit_categoryId" value="${articleTextForm.unit_categoryId}"/>
	<input type="hidden" id="unit_columnId" name="unit_columnId" value="${articleTextForm.unit_columnId }" />
	<input type="hidden" name="dealMethod" id="dealMethod" />
	<input type="hidden" name="configPath" id="configPath" value="${articleTextForm.configPath }"/>
	<input type="hidden" id="getArticleStyle" value='${articleTextForm.articleTextStyle }' />
	<input type="hidden" name="unit_css" id="unit_css" value="${articleTextForm.unit_css }" />
	<input type="hidden"  id="chooseViewStyle" value='${articleTextForm.styleId}' >
	<input type="hidden" id="hasSaved" name="hasSaved" value="N" />	

	<div id="setParamArea" style="position:absolute; left:3px; top:10px; width:278px; height:400px; z-index:2; ">
			<fieldset style="height:400" id="obj1" style="display:">
				<legend style="color:#0000ff;">参数设置</legend>
				<div id="paramSet" class="form_cls"  style="display: ;width:100%;align:center">
					<li>
						<select name="articleTextStyle" id="articleTextStyle" style="width:100%;"  onchange="changeshow(htmlContent,this)">
							<option value='0' selected>请选择显示样式</option>
							<c:forEach var="templateUnitStyle" items="${articleTextForm.templateUnitStyleList}" >									
								<option value='${templateUnitStyle.id}' title="${templateUnitStyle.name}">${templateUnitStyle.name}</option>						
							</c:forEach>
						</select>
					</li>
					<li>
						<label>评&nbsp;&nbsp;&nbsp;&nbsp;论</label>
						<input  type="text" name="articleTextComment" id="columnSuffix" value="${articleTextForm.articleTextComment}" maxlength="255" size="8"/>
						<input  type="button" class="btn_small"  title="选择评论图"  value="√" onclick="selectArticleTextComment()"/>	
						<span class="span">
							<input type="checkbox"  class="input_blank" name="articleTextCommentPic" id="columnSuffixPic" value="${articleTextForm.articleTextCommentPic}"/>
						          图形
						</span> 
					</li>
				</div> 
			</fieldset>  
		</div>		   
		<div id="displayArea"  style="position:absolute; left:283px; top:10px; width:320px; height:400px; z-index:2;overflow:hidden">
			<fieldset id="displayEffect" style="position:absolute; width:320px;height:400px;"><legend style="color:#0000ff;"><b>显示效果</b>&nbsp;</legend>
				<div id="display" style="position:absolute; left:0px; top:20px; width:320px; height:400px;overflow:hidden"></div>
			</fieldset>  
		</div>
		<div id="codeArea"  style="position:absolute; left:283px; top:380px; width:320px; height:10px; z-index:2; ">
			<fieldset id="code" style="width:320px;height:10px;">
			<legend style="color:#0000ff;"><b>效果源码</b>&nbsp;
				<span id="sub" style="cursor:hand;color:red;display:none" onclick="changeDisplay('sub')">&nbsp;－&nbsp;</span>
				<span id="add" style="cursor:hand;color:red;" onclick="changeDisplay('add')">&nbsp;＋&nbsp;</span>
			</legend>
			<div id="displayCode" style="position:absolute; top:0px; width:320px; height:0px; display:none;overflow:hidden;"> 
			 	<SELECT id="htmlCode" name="htmlCode" style="width:103px" onChange="insertAtCaret(htmlContent,this.value)">
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
				<select name="unitCode" id="unitCode" style="width:103px" onchange="insertAtCaret(htmlContent,this.value)">
					<option value=''>---单元标签---</option>
					<c:forEach var="commonMap" items="${articleTextForm.commonMap}">
						<c:if test="${commonMap.value != '<!--columnName-->'}">
							<option value="<c:out value="${commonMap.value}" />">  
								<c:out value="${commonMap.key}" /> 
							</option>	
						</c:if>
					</c:forEach>
					<c:forEach var="titleMap" items="${articleTextForm.titleMap}">
						<c:if test="${titleMap.value != '<!--articleurl-->'
								and titleMap.value != '<!--articlelinktitleshort-->'
								and titleMap.value != '<!--articlesubtitleshort-->'
								and titleMap.value != '<!--articlequotetitleshort-->'
								and titleMap.value != '<!--articletitleshort-->'
								and titleMap.value != '<!--articlePrefix-->' 
								and titleMap.value != '<!--articleSuffix-->'
								and titleMap.value != '<!--columnLink-->'
								and titleMap.value != '<!--more-->'}">
							<option value="<c:out value="${titleMap.value}" />">  
								<c:out value="${titleMap.key}" /> 
							</option>	
						</c:if>
					</c:forEach>
					<c:forEach var="columnMap" items="${articleTextForm.map}">	
						<c:if test="${columnMap.value != '<!--articlePrevious-->' 
							and columnMap.value != '<!--articleNext-->' 
							and columnMap.value != '<!--articlePage-->'}"> 
							<option value="<c:out value="${columnMap.value}"/>">
								<c:out value="${columnMap.key}"/> 
					    	</option>  
						</c:if>			   					
					</c:forEach>
				</select> 
				<select name="fieldCode" id="fieldCode"  style="width:103px;" onChange="insertAtCaret(htmlContent,this.value)" >
					<option value=''>---字段标签---</option>	
						<c:forEach var="articleAttribute" items="${articleTextForm.articleAttributeList}" varStatus="s" step="1">									
							<option value="&lt;!--${articleAttribute.fieldName}--&gt;" >${articleAttribute.attributeName}</option>						
						</c:forEach>
				</select>
				<input type="button" value="样式管理" class="btn_small" onClick="showStyleManager()"/>	
				<input type="button" value="css管理" class="btn_small" onClick="managerCss()"/>
				<TEXTAREA id="htmlContent"  name="htmlContent" ROWS="11"  onselect="storePos(this);" onclick="storePos(this);" onkeyup="storePos(this);" COLS="37">${articleTextForm.htmlContent}</TEXTAREA>
			</div>
			</fieldset> 				
	    </div>
		<div id="saveArea" style="position:absolute;height:15px;z-index:4;left: 250px;top: 410px;">			
			<input type="button" value="保存" class="btn_normal" onclick="btn_save()"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" value="站内保存"  class="btn_normal"  onclick="btn_site_save()"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
		 	
		</div>
</form>
</body>
</html>