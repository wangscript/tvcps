<%@ page language="java" contentType="text/html; charset=utf-8"%>
<html>
<head>
<%@include file="/templates/headers/header.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>文章转移</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/script/ext/resources/css/ext-all.css"/>"/>
<script type="text/javascript" src="<c:url value="/script/ext/adapter/ext/ext-base.js"/>"></script>
<script type="text/javascript" src="<c:url value="/script/ext/ext-all-debug.js"/>"></script>
<style type="text/css" media="all">
	
	#contacter {
	 text-align:right;
	 float:right;
	 margin-right:10px;
	 margin-top:10px;
	}
	#treeboxbox_tree {
	 float:left;
	 margin-left:5px;
	 margin-top:10px;
	}
</style>

<script>

	var flag = "false";
	// 选择要转移到的栏目
	function button_sure_onclick(ee) {
		var columnId = $("#checkid").val();
		if(columnId == null || columnId == "" || columnId == 0) {
			alert("请选择转移到的栏目");
			return false;
		}
		if(flag == "true") {
			alert("栏目格式与文章格式不一致");
			return false;
		}
		$("#nodeId").val(columnId);
		$("#dealMethod").val("move");
		$("#articleForm").submit();
	}

	function tree_onclick(node) {
		var formatid = node.attributes.formatid;
		var presentFormatId = document.getElementById("presentFormatId").value;
		if(presentFormatId != formatid) {
			alert("栏目格式与文章格式不一致");
			flag = "true";
			return;
		} else {
			flag = "false";
		}
		document.getElementById("checkid").value = node.id;
		document.getElementById("checkname").value = node.text;	
	}
	    
	function button_quit_onclick(ee) {	
		rightFrame.closeDetailWin();
	}
</script>
</head>
<body>
	<form name="articleForm" method="post" id="articleForm" action="<c:url value="/article.do"/>">
	<input type="hidden" name="checkid" id="checkid"/>
	<input type="hidden" name="checkname" id="checkname"/>
	<input type="hidden" name="dealMethod" id="dealMethod" > 
	<input type="hidden" name="columnId" id="columnId" value="${articleForm.columnId}"/>
	<input type="hidden" name="formatId" id="formatId" value="${articleForm.formatId}">
	<input type="hidden" name="ids" id="strid" />
	<input type="hidden" name="nodeId" id="nodeId" />
	<input type="hidden" name="moveArticleIds" id="moveArticleIds" value="${articleForm.moveArticleIds}"/>
	<input type="hidden" name="presentFormatId" id="presentFormatId" value="${articleForm.presentFormatId}"/>
	<table style="font:12px;" >
		<tr>
			<td class="td_left" ><i>&nbsp;</i>文章所在栏目格式：${articleForm.presentFormatName}</td>
		</tr>
	</table>

	<div id="treeboxbox_tree" style="width:360px; height:370px;background-color:#f5f5f5;border :1px solid Silver;scroll:auto;">
		<complat:tree unique="column" classname="Column" treeurl="column.do?dealMethod=getTree&nodeId=0&nodeName=null"/>
	</div> 
	
	<table width="360px;">
		<tr>
			<td>
		<center>
			<input class="btn_normal" type="button" name="button_sure"  value="确定" onClick="button_sure_onclick(this);" >
			&nbsp;&nbsp;&nbsp;&nbsp;	
			<input class="btn_normal" type="button" name="button_quit"  value="退出" onClick="button_quit_onclick(this);" >
		</center></td>
		</tr>
	</table>
	</form> 
</body>
</html>