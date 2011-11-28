<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择栏目</title>
	<link rel="stylesheet" type="text/css" href="<c:url value="/script/ext/resources/css/ext-all.css"/>"/>
	<script type="text/javascript" src="<c:url value="/script/ext/adapter/ext/ext-base.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/script/ext/ext-all-debug.js"/>"></script>
<style type="text/css" media="all">
	#btn {
		margin: 20px 0 0 120px;
	}
</style>
<script>
	function button_sure_onclick(ee) {	
		var checkid = $("#checkid").val();	
		// 重新选择父栏目
		if($("#needchild").val() == "null" || $("#needchild").val() == null) {
			var childColumnIds = $("#childColumnIds").val();
			var ids = childColumnIds.split(",");	
			for(var i = 0; i < ids.length; i++) {
				if(ids[i] == checkid) {
					alert("你不能选择该栏目下的子栏目或者栏目本身");
					return false;
				} 
			}
			rightFrame.document.getElementById("parentid").value = document.getElementById("checkid").value;	
			rightFrame.document.getElementById("pname").value = document.getElementById("checkname").value;	
			closeWindow(rightFrame.getWin());

		// 粘贴栏目	
		} else {
			var nodeId = $("#nodeId").val();
			if(nodeId == null || nodeId == "") {
				nodeId = 0;
			}
			if(checkid == nodeId) {
				alert("不能粘贴到原来栏目的同级");
				return false;
			}
			$("#nodeId").val(document.getElementById("checkid").value);
			$("#dealMethod").val("paste");
			document.getElementById("columnForm").submit();
		}
	}
	
	function tree_onclick(node) {	
		document.getElementById("checkid").value = node.id;
		document.getElementById("checkname").value = node.text;
	}
	     
	function button_quit_onclick(ee) {	
		rightFrame.closeChild();
	}
</script> 

</head>
<body>
<form action="<c:url value="/column.do"/>" method="post" name="columnForm" id="columnForm">
	<c:if test="${columnForm.childColumnIds != null}">        
		<div id="treeboxbox_tree" style="width:340; height:400;background-color:#f5f5f5;border :1px solid Silver;scroll:auto;">
			<complat:tree  unique="column" classname="Column" treeurl="column.do?dealMethod=getTree&nodeId=0&nodeName=null"/>
		</div>
	</c:if> 
	<c:if test="${columnForm.childColumnIds == null}"> 
		<div id="treeboxbox_tree" style="width:340; height:400;background-color:#f5f5f5;border :1px solid Silver;scroll:auto;">
			<complat:tree  unique="column" classname="Column" treeurl="../../column.do?dealMethod=getTree&nodeId=0&nodeName=null"/>
		</div>
	</c:if>
	<input type="hidden" name="checkid" id="checkid"/>
	<input type="hidden" name="checkname" id="checkname"/>

	<input type="hidden"  name="dealMethod" 	id="dealMethod" />
	<input type="hidden"  name="childColumnIds" id="childColumnIds" value="${columnForm.childColumnIds }" />
	<input type="hidden"  name="columnId"       id="columnId"       value="<%=request.getParameter("columnId") %>" />
	<input type="hidden"  name="nodeId"         id="nodeId"         value="<%=request.getParameter("nodeId") %>" />
	<input type="hidden"  name="localNodeName" 	id="localNodeName"  value="<%=request.getParameter("localNodeName")%>" />
	<input type="hidden"  name="ids"			id="strid"			value="<%=request.getParameter("ids")%>"/>
	<input type="hidden"  name="needchild"		id="needchild"      value="<%=request.getParameter("needchild")%>"/>
	<input type="hidden"  name="isCopy"			id="isCopy"			value="<%=request.getParameter("isCopy")%>"/>
 	<div class="form_cls">
		<ul>
			<li id="btn">
				<input class="btn_normal" type="button" name="button_sure"    value="确定" onClick="javascript:button_sure_onclick(this);" >
				<input class="btn_normal" type="button" name="button_quit"    value="退出" onClick="javascript:button_quit_onclick(this);" >
			</li>
		</ul>
	</div>
</form>
</body>
</html>