<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>当前位置</title>
<%@include file="/templates/headers/header.jsp"%>
<script type="text/javascript" src="<c:url value="/script/choosecolor.js"/>"></script>
<style type="text/css" media="all">
	.form_cls label {
		width:60px;
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
		var chooseViewStyle = $("#chooseViewStyle").val();
		//选择内容来源
		var chooseContextFrom = $("#chooseContextFrom").val();
		
		if(chooseViewStyle != null && chooseViewStyle != "" && chooseViewStyle != 0){
			$("#viewStyle").val(chooseViewStyle);
			
			document.getElementById("display").innerHTML = $("#viewImg").val();
		}
		if(chooseContextFrom != null && chooseContextFrom != "" && chooseContextFrom != 0){
			document.all.contextFrom.value =chooseContextFrom;
			if(chooseContextFrom > 1) {
				document.getElementById("havecolumn").style.display = "";
				var fixedColumn = $("#fixedColumnExample").val();
				var columnName =  fixedColumn.split("##");
				$("#fixedColumnExample").val(columnName[1]);
			}
		}
		
	}); 

	  //选择的时候插入数据到文本框
	function insertAtCaret(txtobj, text){
		if (txtobj.createTextRange && txtobj.caretPos) { 
	       var caretPos = txtobj.caretPos;               //获取光标所在的位置
	       //替换光标处位置
	       caretPos.text =caretPos.text.charAt(caretPos.text.length - 1) =='' ?text + '' : text; 
	       } 
	    else 
	       txtobj.value = text;   
	  }
	//选择文本时保存光标位置-单击时同样
	function storePos (txtobj) 
	{ 
		if (txtobj.createTextRange)                      //获取选中的内容
			txtobj.caretPos = document.selection.createRange().duplicate(); 
	} 

	// 改变内容来源
	function changesource(obj)	{
		if (obj.value > 1 ) {
			$("#fixedColumnExample").val("");
			$("#fixedColumn").val("");
			document.all.havecolumn.style.display = "";		
		}else{
			document.all.havecolumn.style.display = "none";		
		}
	}
	//选择图片，把图片的路径赋给这个ID
	function selectFile(id){
		
	}
	//改变显示样式
	/**function changeshow(txtobj,obj){	
		var styleValue = obj.value;			
	    str=styleValue.split("##");       
	    if(str.length >= 2){		    
	    	document.all.viewStyle.value = styleValue;	    	
	    	document.getElementById("display").innerHTML = str[1];	    
	    //	txtobj.faucs();	
	    	if (txtobj.createTextRange && txtobj.caretPos) { 
	 	       var caretPos = txtobj.caretPos;               //获取光标所在的位置
	 	       //替换光标处位置
	 	       caretPos.text =caretPos.text.charAt(caretPos.text.length - 1) =='' ?str[0] + '' : str[0]; 
	 	       } 
	 	    else 
	 	       txtobj.value = str[0];   
	    }
	  
	}**/
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

	//显示样式管理
	function showStyleManager(){
		win = showWindow("showStyleManager","样式管理","<c:url value='/templateUnitStyle.do?dealMethod=list&categoryId="+$("#unit_categoryId").val()+"'/>",0,0,850,590);	 			
	}


	//保存数据
	function btn_save(){		
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
		if(contextFrom > 1) {
			if($("#fixedColumn").val().isEmpty()) {
				alert("请选择指定栏目");
				return false;
			} 
		}
		var titleLimit = $("#titleLimit").val();
		if(titleLimit < 0) {
			alert("有效字数不能小于0");
			return false;
		}
		if($("#htmlContent").val().trim() == null || $("#htmlContent").val().trim() == "") {
			alert("效果源码不能为空");
			return false;
		}
		$("#dealMethod").val("saveConfig");
	 	var options = {	 	
 		    	url: "currentLocation.do",
 		    success: function(msg) { 		    		
 		    		 alert(msg); 		    		
 		    } 
 		  };
		$('#currentLocationForm').ajaxSubmit(options);
		//设置标记为已保存
		document.getElementById("hasSaved").value = "Y";
	}

	//网站内保存数据
	function btn_site_save(){	
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
		if(contextFrom > 1) {
			if($("#fixedColumn").val().isEmpty()) {
				alert("请选择指定栏目");
				return false;
			} 
		}
		var titleLimit = $("#titleLimit").val();
		if(titleLimit < 0) {
			alert("有效字数不能小于0");
			return false;
		}
		if($("#htmlContent").val().trim() == null || $("#htmlContent").val().trim() == "") {
			alert("效果源码不能为空");
			return false;
		}	
		
		$("#dealMethod").val("saveSiteConfig");
	 	var options = {	 	
 		    	url: "currentLocation.do",
 		    success: function(msg) { 		    		
 		    		 alert(msg); 		    		
 		    } 
 		  };

 		$('#currentLocationForm').ajaxSubmit(options);	
 		//设置标记为已保存
		document.getElementById("hasSaved").value = "Y";
	}
	
	function closeNewChild() {
		closeWindow(win);
	}

	// 指定栏目
	function fixedColumn11() {
		win = showWindow("chooseColumn", "指定栏目", "<c:url value='/module/template_set/unit/choose_column.jsp'/>", 170, 20, 300, 300);
	}
	
	//设置CSS
	function managerCss() {
		var css = $("#unit_css").val();
		win = showWindow("css", "管理css", "<c:url value='/module/template_set/manage_css.jsp?css=" + css + "'/>", 0, 0,490,350);
	}
</script>
</head>
<body>
	<form id="currentLocationForm" action="<c:url value="/currentLocation.do"/>" name="currentLocationForm" method="post">
	<input type="hidden" value="${appName}" id="apprealpath"/>	
	<input type="hidden" id="unit_unitId" name="unit_unitId" value="${currentLocationForm.unit_unitId}"/>
	<input type="hidden" id="unit_categoryId" name="unit_categoryId" value="${currentLocationForm.unit_categoryId}"/>
	<input type="hidden" name="dealMethod" id="dealMethod" />
	<input type="hidden" name="chooseViewStyle" id="chooseViewStyle" value='${currentLocationForm.viewStyle}' >
	<input type="hidden" name="chooseContextFrom" id="chooseContextFrom" value="${currentLocationForm.contextFrom}" >
	<input type="hidden" id="hasSaved" name="hasSaved" value="N" />	
	<span style="display:none">
		<div id="unit_css1"  ></div>		
		<TEXTAREA id="unit_css" name="unit_css" >${currentLocationForm.unit_css}</TEXTAREA>  		 
	</span>
	<input type="hidden" name="viewImg" id="viewImg" value='${currentLocationForm.viewImg}'/>
		<div id="belowleft" style="position:absolute; left:3px; top:10px; width:278px; height:400px; z-index:2; ">
			<fieldset style="height:400" id="obj1" style="display:">
				<legend style="color:#0000ff;">参数设置	</legend>
				<div id="paramSet1" class="form_cls"  style="display: ;width:100%;align:center">
					<li>
						<select name="viewStyle" id="viewStyle" style="width:100%"  onchange="changeshow(htmlContent,this)">
							<option value='0' selected="selected">请选择显示样式</option>
							<c:forEach var="templateUnitStyle" items="${currentLocationForm.templateUnitStyleList}" varStatus="s" step="1">									
								<option value='${templateUnitStyle.id}'>${templateUnitStyle.name}</option>						
							</c:forEach>
						</select>
					</li>
					<li>
						<select name="contextFrom"  id="contextFrom" style="width:100%" onchange="changesource(this)">
							<option value='0'>请选择内容来源</option>						
							<option value="1" SELECTED>当前栏目</option>   					   						
							<option value="2">指定栏目</option>   
			
						</select>
					</li>
					<span id="havecolumn" style="display:none;border:0">
						<li>
							<label>指定栏目</label>						
							<input type="text"   name="fixedColumnExample" id="fixedColumnExample" value="${currentLocationForm.columnName}" maxlength="255" size="17" readonly />
							<input type="hidden" name="columnName" id="fixedColumn" value="${currentLocationForm.columnName}" />
							<input type="button"  class="btn_small"    title="指定栏目" value="√" onclick="fixedColumn11()">
						</li>
					</span>		
					<li>
						<label>有效字数</label>	
						<input  TYPE="text" NAME="titleLimit" id="titleLimit" value="${currentLocationForm.titleLimit}" maxlength="3" SIZE="3" onkeyup="value=value.replace(/[^\d]/g,'')"/>
					</li>				
				</div>		
			</fieldset>
		</div>		   
			<div id="belowrightTop" style="position:absolute; left:283px; top:10px; width:320px; height:140px; z-index:2;">
			<fieldset id="displayEffect" style="width:320px;height:140px;"><legend style="color:#0000ff;"><b>显示效果</b>&nbsp;</legend>	
				<div id="display"  style="width:307px;height:135px;overflow:auto"></div>
			</fieldset>  
		</div>
		
		<div id="belowleftBelow" style="position:absolute; left:283px; top:160px; width:320px; height:260px; z-index:2; ">
			<fieldset id="code" style="width:320px;height:250px;"><legend style="color:#0000ff;"><b>效果源码</b>&nbsp;</legend>
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
				<c:forEach var="commonMap" items="${currentLocationForm.commonMap}" >
					<c:if test="${commonMap.value != '<!--if--><!--else--><!--/if-->' 
								and commonMap.value != '<!--for--><!--/for-->'
								and commonMap.value != '<!--columnName-->'}">
						<option value="<c:out value="${commonMap.value}" />"><c:out value="${commonMap.key}" /></option>
					</c:if>
				</c:forEach>
				<c:forEach var="titleMap" items="${currentLocationForm.map}" >									
						<option value="<c:out value="${titleMap.value}" />"><c:out value="${titleMap.key}" /></option>						
				</c:forEach>

			</select>
		<!--<select name="fieldCode" id="fieldCode"  style="width:103px" onChange="insertAtCaret(htmlContent,this.value)" disabled="disabled">
				<option value=''>---字段标签---</option>	
			</select>
			 -->
			<input type="button" value="样式管理"  class="btn_small"  onClick="showStyleManager()">	
			<input type="button" value="css管理"  class="btn_small"   onClick="managerCss()">		
				<TEXTAREA id="htmlContent"  name="htmlContent" ROWS="11"  onselect="storePos(this);" onclick="storePos(this);" onkeyup="storePos(this);" COLS="37">${currentLocationForm.htmlContent}</TEXTAREA>
			</fieldset> 				
	    </div>
		<div id="saveArea" style="position:absolute;height:15px;z-index:4;left: 250px;top: 410px;">			
			<input type="button" value="保存"  class="btn_normal"  onclick="btn_save()"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" value="站内保存"  class="btn_normal"  onclick="btn_site_save()"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;				
		</div>
	</form>

</body>
</html>