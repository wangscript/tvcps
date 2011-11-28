<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<html>
<head>
<%@include file="/templates/headers/header.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>管理前端css</title>
<style type="text/css" media="all">
	label {
		width:60px;
		margin-right:5px;
		text-align:left;
	}
	.set {
		margin:10px auto;
	}
	.set li {
		list-style-type:none;
		width:450px;
		margin:5px;
	}
</style>

<script type="text/javascript">
	var firstWin = top.getWin();		
	var temp = firstWin.split("###");
	var dialogProperty = temp[0];
	var detailFrame = "_DialogFrame_"+dialogProperty;	 		   
	var tempWindow = top.document.getElementById(detailFrame).contentWindow;
	var secondTempWindow = tempWindow.document.getElementById("unitEditArea").contentWindow;		
	var secondWin = secondTempWindow.getWin();		
	var secondtemp = secondWin.split("###");
	var seconddialogProperty = secondtemp[0];
	var seconddetailFrame = "_DialogFrame_"+seconddialogProperty;	
	var secondWindow = top.document.getElementById(seconddetailFrame).contentWindow;
	function saveCss() {
		var css = document.getElementById("css").value;
		if(css == null || css == "") {
			alert("样式不能为空");
			return false;
		}
		secondTempWindow.document.getElementById("unit_css1").innerHTML = $("#css").val();
		secondTempWindow.document.getElementById("unit_css").value =  $("#css").val();
		alert("保存成功(提示：保存后不要再点击样式管理，否则将丢失css样式)");
		quitCss();
	}

	function quitCss() {				
		top.closeWindow(secondWin);	
		secondTempWindow.document.getElementById("htmlContent").focus();
	
	}
	$(document).ready(function() {
		$("#css").val(secondTempWindow.document.getElementById("unit_css").value);
	});
</script>

</head>
<body>
	<div id="top" class="set" style="position:absolute; left:3px; top:10px; width:460px; height:260px;">
		<li>
			<label>css样式</label>
			<TEXTAREA id="css"  name="css" ROWS="11"  COLS="37"></TEXTAREA>
		</li>
		<li>
	</div>
	<div id="bottom2"  style="position:absolute;text-align: center;top:240px; width:460px; height:30px;">
	 	<input type="button" name="saveButton" id="saveButton" class="btn_normal"  value="保存" onclick="saveCss()"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" name="backButton" id="backButton" class="btn_normal"  value="退出" onclick="quitCss()" />  
	</div>
</body>
</html>