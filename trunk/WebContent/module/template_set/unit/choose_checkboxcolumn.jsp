<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择栏目</title>
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

	function button_quit_onclick(ee) {	
		top.closeWindow(secondWin);
	}

	function button_sure_onclick(ee) {
		if($("#tree_checkedIds").val() == "") {
			alert("请选择栏目");
			return false;
		}		
		secondTempWindow.document.getElementById("colDiv").innerText = $("#tree_checkedTexts").val(); 
		secondTempWindow.document.getElementById("allColumn").value = $("#tree_checkedIds").val() + "##" + $("#tree_checkedTexts").val();	
		top.closeWindow(secondWin);
	}

/*
	function button_sure_onclick(ee) {		
	//	alert("ids====="+ $("#tree_checkedIds").val());
	//	alert("texts===="+ $("#tree_checkedTexts").val());
		parent.document.getElementById("colDiv").innerText = $("#tree_checkedTexts").val(); 
		parent.document.getElementById("allColumn").value = $("#tree_checkedIds").val() + "##" + $("#tree_checkedTexts").val();	
	//	alert(parent.document.getElementById("allColumn").value);
		top.closeNewChild();
	}
	
	function tree_oncheck(node) {	
//		$("#checkid").val(node.id);
//		$("#checkname").val(node.text);
	}
	     
	function button_quit_onclick(ee) {	
		top.closeNewChild();
	}*/
</script>

</head>
<body>
	<div id="treeboxbox_tree" style="width:250; height:150;background-color:#f5f5f5;border :1px solid Silver;scroll:auto;">
		<complat:tree  unique="column" checkbox="true" classname="Column" treeurl="../../../column.do?dealMethod=getTree&nodeId=0&nodeName=null"/>
	</div>

	<input type="hidden" name="checkid" id="checkid"/>
	<input type="hidden" name="checkname" id="checkname"/>

 	<input  type="button" name="button_sure"  class="btn_normal"  value="确定" onClick="javascript:button_sure_onclick(this);" >
	<input  type="button" name="button_quit"  class="btn_normal"  value="退出" onClick="javascript:button_quit_onclick(this);" >
</form>
</body>
</html>