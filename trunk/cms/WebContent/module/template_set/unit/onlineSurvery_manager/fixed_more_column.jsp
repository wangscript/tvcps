<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>指定标题链接栏目</title>
	<link rel="stylesheet" type="text/css" href="<c:url value="/script/ext/resources/css/ext-all.css"/>"/>
	<script type="text/javascript" src="<c:url value="/script/ext/adapter/ext/ext-base.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/script/ext/ext-all-debug.js"/>"></script>
<script>

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
		

	function button_sure_onclick(ee) {
		if($("#checkid").val() == 0) {
			alert("请选择指定栏目");
			return false;
		}	
		secondTempWindow.document.getElementById("moreExample").value = $("#checkname").val(); 
		secondTempWindow.document.getElementById("more").value = $("#checkid").val() + "##" + $("#checkname").val();	
		top.closeWindow(secondWin);	
	}
	
	function tree_onclick(node) {	
		$("#checkid").val(node.id);
		$("#checkname").val(node.text);
	}
	     
	function button_quit_onclick(ee) {	
		top.closeWindow(secondWin);	
	}
</script>

</head>
<body>
<form action="<c:url value="/onlineSurveyConcret.do"/>" id="onlineSurverySetForm" name="onlineSurverySetForm" >
	<div id="treeboxbox_tree" style="width:250; height:150;background-color:#f5f5f5;border :1px solid Silver;scroll:auto;">
		<complat:tree  unique="column" classname="Column" treeurl="../../../../column.do?dealMethod=findTemplateSetColumnLinkTree&nodeId=0&nodeName=null"/>
	</div>
	<input type="hidden" name="nodeId" id="nodeId"/>
	<input type="hidden" name="checkid" id="checkid"/>
	<input type="hidden" name="checkname" id="checkname"/>
	<input type="hidden" name="dealMethod" id="dealMethod"/>
 	<input  type="button" name="button_sure" class="btn_normal" value="确定" onClick="javascript:button_sure_onclick(this);" >
	<input  type="button" name="button_quit" class="btn_normal" value="退出" onClick="javascript:button_quit_onclick(this);" >
</form>
</body>
</html>