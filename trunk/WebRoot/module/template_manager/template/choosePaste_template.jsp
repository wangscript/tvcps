 <%--
	功能：用于模板的复制粘贴界面的显示
	作者：郑荣华
 --%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择粘贴的模板位置</title>
<%@include file="/templates/headers/header.jsp"%>
	<link rel="stylesheet" type="text/css" href="<c:url value="/script/ext/resources/css/ext-all.css"/>"/>
	<script type="text/javascript" src="<c:url value="/script/ext/adapter/ext/ext-base.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/script/ext/ext-all-debug.js"/>"></script>
<style type="text/css" media="all">
	#btn {
		margin: 20px 0 0 80px;
	}
</style>
<script>
	$(document).ready(function(){
		var message = $("#message").val();
		if(!message.isEmpty() && message != "null") {
			if($("#message").val() != "粘贴成功") {
				alert($("#message").val());
			}
			top.document.getElementById("rightFrame").src = "<c:url value='/template.do?nodeId=${templateForm.nodeId}&dealMethod='/>";
			closeWindow(rightFrame.getWin());
		}
	});
	function button_sure_onclick(ee) {			
		if($("#checkid").val().isEmpty() || $("#checkid").val() == 0) {
			alert("请选择要粘贴的位置");
			return false;
		}
		var nodeId = $("#nodeId").val();
		var checkid = $("#checkid").val();
		if(nodeId == checkid) {
			alert("不能粘贴到同一个模板类别下面");
			return false;
		}
		$("#dealMethod").val("paste");
		$("#strid").val($("#checkid").val());
		document.getElementById("templateForm").submit();
	}
	
	function tree_onclick(node) {	
		document.getElementById("checkid").value = node.id;
		document.getElementById("checkname").value = node.text;
	}
	     
	function button_quit_onclick(ee) {	
		rightFrame.closeDetailChild();
	}
	
</script>

</head>
<body>

<form action="<c:url value="/template.do"/>" method="post" name="templateForm" id="templateForm">
	<div id="treeboxbox_tree" style="width:240; height:200;background-color:#f5f5f5;border :1px solid Silver;scroll:auto;">
		<complat:tree unique="123546"  treeurl="../../../templateCategory.do?dealMethod=getTree&treeId=0" />
	</div>
	<input type="hidden"  name="checkid"     id="checkid"/>
	<input type="hidden"  name="checkname"   id="checkname"/>
	<input type="hidden"  name="message"     id="message"  value="${templateForm.infoMessage}" />
	<input type="hidden"  name="dealMethod"  id="dealMethod" />
	<input type="hidden"  name="nodeId"      id="nodeId"   value="<%=request.getParameter("nodeId")%>" />
	<input type="hidden"  name="ids"         id="strid" />
	<input type="hidden"  name="pasteIds"    id="pasteIds" value="<%=request.getParameter("pasteIds") %>" />
	<div class="form_div">
		<ul>
			<li id="btn">
				<input class="btn_normal" type="button" name="button_sure"  value="确定" onClick="javascript:button_sure_onclick(this);" >
				&nbsp;&nbsp;&nbsp;&nbsp;
				<input class="btn_normal" type="button" name="button_quit"  value="退出" onClick="javascript:button_quit_onclick(this);" >
			</li>
		</ul>
	</div>

</form>
</body>
</html>