<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择机构</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/script/ext/resources/css/ext-all.css"/>"/>
<script type="text/javascript" src="<c:url value="/script/ext/adapter/ext/ext-base.js"/>"></script>
<script type="text/javascript" src="<c:url value="/script/ext/ext-all-debug.js"/>"></script>
<script>
	function button_sure_onclick(ee){		
		var checkid = document.getElementById("checkid").value;
		if(checkid == null || checkid == ""){
			alert("请选择一个机构！");
			return false;
		}
		if(checkid == 0){
			alert("不能选择网站作为机构！");
			return false;
		}
		rightFrame.document.getElementById("pid").value = document.getElementById("checkid").value;	
		rightFrame.document.getElementById("pname").value = document.getElementById("checkname").value;	
		rightFrame.document.getElementById("pname").focus();
	
		rightFrame.closeChild();
	}
	
	function tree_onclick(node){	
		document.getElementById("checkid").value = node.id;
		document.getElementById("checkname").value = node.text;
	}
	     
	function button_quit_onclick(ee){	
		rightFrame.closeChild();
	}
</script>

</head>
<body>

<div id="treeboxbox_tree" style="width:250; height:150;background-color:#f5f5f5;border :1px solid Silver;scroll:auto;">
	<complat:tree  unique="organization" treeurl="../../../organization.do?dealMethod=gettree"  />
</div>

<input type="hidden" name="checkid" id="checkid"/>
<input type="hidden" name="checkname" id="checkname"/>

<input  type="button" class="btn_normal"  name="button_sure" value="确定" onClick="javascript:button_sure_onclick(this);" >
<input  type="button" class="btn_normal"  name="button_quit" value="退出" onClick="javascript:button_quit_onclick(this);" >

</body>
</html>