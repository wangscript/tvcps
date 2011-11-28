<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>栏目链接</title>
<%@include file="/templates/headers/header.jsp"%>
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

<script type="text/javascript">
	$(document).ready(function() {
		document.getElementById("unit_css1").innerHTML = $("#unit_css").val();
		//选择显示样式
		var columnStyle = $("#getColumnStyle").val();
		//选择内容来源
		var columnType = $("#getColumnType").val();
		if(columnStyle != null && columnStyle != "" && columnStyle != 0) {
			$("#columnStyle1").val(columnStyle);
			document.getElementById("display").innerHTML = $("#viewImg").val();
		}
		if(columnType != null && columnType != "" && columnType != 0) {
			$("#columnType1").val(columnType);
			if(columnType > 5) {
				var fixedColumn = $("#fixedColumnExample").val();
				var str1 = fixedColumn.split("##");
				$("#fixedColumnExample").val(str1[1]);
				document.all.havecolumn.style.display = "";	
			}
		}
		if($("#columnPrefixPic").val() == 1) {
			$("#columnPrefixPic").attr("checked", true);
		}
		if($("#columnSuffixPic").val() == 1) {
			$("#columnSuffixPic").attr("checked", true);
		}
	//	if(!$("#textareaContent").val().isEmpty()) {
	//		$("#htmlContent").val($("#textareaContent").val());
	//	}
	});

	//保存数据
	function btn_save() {	
		var columnStyle = $("#columnStyle1 :selected").val();
		if(columnStyle == 0 || columnStyle == "" || columnStyle == null) {	
			alert("你必须先选择样式");
			return false;
		}
		var columnType = $("#columnType1 :selected").val();
		if(columnType == 0) {
			alert("你必须先选择栏目内容来源");
			return false;
		}
		if(columnType > 5) {
			if($("#fixedColumn").val().isEmpty()) {
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
		$("#dealMethod").val("saveConfig");
	 	var options = {	 	
 		    	url: "columnLink.do",
 		    success: function(msg) { 		    		
 		    		 alert(msg); 		    		
 		    } 
 		};
		$('#columnLinkForm').ajaxSubmit(options);	
		//设置标记为已保存
		document.getElementById("hasSaved").value = "Y";
	}

	//网站内保存数据
	function btn_site_save() {	
		var columnStyle = $("#columnStyle1 :selected").val();
		if(columnStyle == 0 || columnStyle == "" || columnStyle == null) {	
			alert("你必须先选择样式");
			return false;
		}
		var columnType = $("#columnType1 :selected").val();
		if(columnType == 0) {
			alert("你必须先选择栏目内容来源");
			return false;
		}
		if(columnType > 5) {
			if($("#fixedColumn").val().isEmpty()) {
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
		$("#dealMethod").val("saveSiteConfig");
	 	var options = {	 	
 		    	url: "columnLink.do",
 		    success: function(msg) { 		    		
 		    		 alert(msg); 		    		
 		    } 
 		};
		$('#columnLinkForm').ajaxSubmit(options);	
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

	// 改变内容来源(在选择指定栏目时出现)
	function changesource(obj) {
		if (obj.value > 5) {
			$("#fixedColumnExample").val("");
			$("#fixedColumn").val("");
			document.all.havecolumn.style.display = "";		
		}else {
			document.all.havecolumn.style.display = "none";		
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
	
	//显示样式管理
	function showStyleManager(){
		win = showWindow("showStyleManager","样式管理","<c:url value='/templateUnitStyle.do?dealMethod=list&categoryId=" + $("#unit_categoryId").val()+"'/>", 0, 0, 850, 590);	 			
	}
	// 指定栏目
	function fixedColumn1() {
		win = showWindow("chooseColumn", "指定栏目", "<c:url value='/module/template_set/unit/choose_column.jsp'/>", 170, 20, 300, 300);
	}	

	// 选择栏目前缀
	function selectColumnPrefix() {
		win = parent.showWindow("chooseColumnPrefix", "选择栏目前缀","<c:url value='/picture.do?dealMethod=uploadPicList&columnLink=1&nodeId=f004&nodeName=图片类别'/>", 0, 0, 940, 555);
	}

	// 选择栏目后缀
	function selectColumnSuffix() {
		win = parent.showWindow("chooseColumnPrefix", "选择栏目后缀","<c:url value='/picture.do?dealMethod=uploadPicList&columnLink=2&nodeId=f004&nodeName=图片类别'/>", 0, 0, 940, 555);
	}


	function managerCss() {
		var css = $("#unit_css").val();
		win = showWindow("css", "管理css", "<c:url value='/module/template_set/manage_css.jsp?css=" + css + "'/>", 0, 0, 490, 350);
	}

	
	
	function closeNewChild() {		
		closeWindow(win);
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
<form id="columnLinkForm" action="<c:url value="/columnLink.do"/>" name="columnLinkForm" method="post">
	<input type="hidden" value="${appName}" id="apprealpath"/>
	<input type="hidden" id="unit_unitId" name="unit_unitId" value="${columnLinkForm.unit_unitId}"/>
	<input type="hidden" id="unit_categoryId" name="unit_categoryId" value="${columnLinkForm.unit_categoryId}"/>
	<input type="hidden" id="unit_columnId" name="unit_columnId" value="${columnLinkForm.unit_columnId }" />
	<input type="hidden" name="dealMethod" id="dealMethod" />
	<input type="hidden" name="configPath" id="configPath" value="${columnLinkForm.configPath }"/>
	<input type="hidden" id="getColumnStyle" value='${columnLinkForm.columnStyle }' />
	<input type="hidden" id="getColumnType"  value='${columnLinkForm.columnType }'/>
	<input type="hidden" name="fixedColumn" id="fixedColumn" value="${columnLinkForm.fixedColumn}" />
	<input type="hidden" name="viewImg" id="viewImg" value='${columnLinkForm.viewImg}'/>
	<input type="hidden" id="hasSaved" name="hasSaved" value="N" />	
	<span style="display:none">
		<div id="unit_css1"></div>		
		<TEXTAREA id="unit_css" name="unit_css">${columnLinkForm.unit_css}</TEXTAREA>  		 
	</span>
	<div id="setParamArea" style="position:absolute; left:3px; top:10px; width:278px; height:400px; z-index:2; ">
			<fieldset style="height:400" id="obj1" style="display:">
				<legend style="color:#0000ff;">参数设置</legend>
				<div id="paramSet" class="form_cls"  style="display: ;width:100%;align:center">
					<li>
						<select   name="columnStyle" id="columnStyle1" style="width:100%"  onchange="changeshow(htmlContent,this)">
							<option value='0' selected>请选择显示样式</option>
							<c:forEach var="templateUnitStyle" items="${columnLinkForm.templateUnitStyleList}" >									
								<option value='${templateUnitStyle.id}'>${templateUnitStyle.name}</option>						
							</c:forEach>
						</select>
					</li>
					<li>
						<select   name="columnType"  id="columnType1" style="width:100%" onchange="changesource(this)">
							<option value='0' SELECTED>请选择内容来源</option>	
							<option value="1">第一级栏目</option>
							<option value="2">当前栏目</option>   
							<option value="3">当前栏目的父栏目</option>   						
							<option value="4">当前栏目的子栏目</option>
							<option value="5">当前栏目的同级栏目</option>
							<option value="6">指定栏目</option>   
							<option value="7">指定栏目的父栏目</option> 
							<option value="8">指定栏目的子栏目</option>
						</select>
					</li>

					<span id="havecolumn" style="display:none;border:0">
						<li>
							<label>指定栏目</label>
							<input   type="text" name="fixedColumnExample" id="fixedColumnExample" value="${columnLinkForm.fixedColumn}" maxlength="255" size="17" readonly >
							<input type="button"  class="btn_small"  title="指定栏目" value="√" onclick="fixedColumn1()">	
						</li>
					</span>	 
	
					<li>
						<label>信息显示</label>				
						<input   type="text" name="columnRow" id="columnRow"  value="${columnLinkForm.columnRow }"  maxlength="3" SIZE="3" onkeyup="value=value.replace(/[^\d]/g,'')" />
						<span>行</span>				
						<input   type="text" name="columnCol" id="columnCol"  value="${columnLinkForm.columnCol }"  maxlength="3" SIZE="3" onkeyup="value=value.replace(/[^\d]/g,'')" />
						<span>列</span>
					</li>
					<li>
						<label>栏目前缀</label>
						<input   type="text" name="columnPrefix" id="columnPrefix"   value="${columnLinkForm.columnPrefix}" title="" maxlength="255" SIZE="17">
						<input  type="button" class="btn_small"  title="选择栏目前缀图"  value="√" onclick="selectColumnPrefix()">
					</li> 
					<li>
						<label>前缀有效期</label>
						<input  type="text" name="columnPrefixDate" id="columnPrefixDate"    empty="true"   value="${columnLinkForm.columnPrefixDate}"  title="-1代表永远有效"   maxlength="4" SIZE="3" onkeydown="myKeyDown('columnPrefixDate')">
						<span>天</span>
						<span class="span">
							<input type="checkbox"  class="input_blank" name="columnPrefixPic" id="columnPrefixPic" value="${columnLinkForm.columnPrefixPic}">
						图形</span>
					</li>
					<li>
						<label>栏目后缀</label>
						<input  type="text" name="columnSuffix"  id="columnSuffix" value="${columnLinkForm.columnSuffix}" title="" maxlength="255" SIZE="17"> 
						<input type="button"   class="btn_small" title="选择栏目后缀图"  value="√" onclick="selectColumnSuffix()">
					</li>
					<li>
						<label>后缀有效期</label>
						<input  type="text" name="columnSuffixDate"  id="columnSuffixDate"    value="${columnLinkForm.columnSuffixDate}" title="-1代表永远有效"  maxlength="4" SIZE="3" onkeydown="myKeyDown('columnPrefixDate')">
						<span>天</span>
						<span class="span"><input type="checkbox"  class="input_blank" name="columnSuffixPic"  id="columnSuffixPic"  value="${columnLinkForm.columnSuffixPic}">
						图形</span>
					</li>
				
				</div>
			</fieldset>
		</div>		   
		<div id="displayArea"  style="position:absolute; left:283px; top:10px; width:320px; height:140px; z-index:2;">
			<fieldset id="displayEffect" style="width:320px;height:140px;"><legend style="color:#0000ff;"><b>显示效果</b>&nbsp;</legend>
				<div id="display" style="width:307px;height:135px;"></div>
			</fieldset>  
		</div>
		<div id="codeArea"  style="position:absolute; left:283px; top:160px; width:320px; height:260px; z-index:2; ">
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
				<c:forEach var="commonMap" items="${columnLinkForm.commonMap}">
					<option value="<c:out value="${commonMap.value}" />">  
						<c:out value="${commonMap.key}" /> 
					</option>	
				</c:forEach>
				<c:forEach var="columnMap" items="${columnLinkForm.map}">									
					<option value="<c:out value="${columnMap.value}" />"> 
						<c:out value="${columnMap.key}" /> 
					</option>						
				</c:forEach>
			</select>
		<!-- 	<select  name="fieldCode" id="fieldCode"  style="width:103px" onChange="insertAtCaret(htmlContent,this.value)" disabled="disabled">
				<option value=''>---字段标签---</option>	
				<c:forEach var="articleAttribute" items="${columnLinkForm.articleAttributeList}" varStatus="s" step="1">									
					<option value="${articleAttribute.fieldName}" >${articleAttribute.attributeName}</option>						
				</c:forEach>
			</select>
			 -->
			<input type="button" value="样式管理" class="btn_small" onClick="showStyleManager()"/>	
			<input type="button" value="css管理" class="btn_small" onClick="managerCss()"/>
			<TEXTAREA id="htmlContent"  name="htmlContent" ROWS="11"  onselect="storePos(this);" onclick="storePos(this);" onkeyup="storePos(this);"  onfocus="storePos(this);" COLS="37" >${columnLinkForm.htmlContent}</TEXTAREA>
			</fieldset> 				
	    </div>
		<div id="saveArea" style="position:absolute;height:15px;z-index:4;left: 250px;top: 410px;">			
			<input type="button" value="保存" class="btn_normal" onclick="btn_save()"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" value="站内保存"  class="btn_normal"  onclick="btn_site_save()"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
		 	
		</div>



</form>
</body>
</html>