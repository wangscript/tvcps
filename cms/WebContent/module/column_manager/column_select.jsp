<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择栏目</title>
	<link rel="stylesheet" type="text/css" href="<c:url value="/script/ext/resources/css/ext-all.css"/>"/>
	<script type="text/javascript" src="<c:url value="/script/ext/adapter/ext/ext-base.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/script/ext/ext-all-debug.js"/>"></script>
<style  type="text/css" media="all">
	#btn {
		margin: 30px 50px 0 120px;
	}
</style>

<script>

	$(document).ready(function(){
		var message = $("#message").val();
		if(!message.isEmpty()){
			if(message == "粘贴成功") {
				top.reloadAccordion("/${appName}/module/column_manager/refresh_Tree.jsp?"+getUrlSuffixRandom());
				top.document.getElementById("rightFrame").src = "<c:url value='/column.do?dealMethod=&nodeId=${columnForm.nodeId}&localNodeName=${columnForm.localNodeName}&operationType=column&'/>"+getUrlSuffixRandom();
				closeWindow(rightFrame.getWin());
			} else {
				alert(message);
				closeWindow(rightFrame.getWin());
			}
	
		} else { 
			if($("#siteid").val() == 0) {
				alert("当前网站为根网站,不存在父网站");
				rightFrame.closeChild();
			}
		}
	});

	function button_sure_onclick(ee) {
		if($("#checkid").val().isEmpty() || $("#checkid").val() == 0) {
			alert("请选择栏目");
			return false;
		} 
		confirmQuestion("是否要粘贴栏目下的子栏目？");	
	}

	function showResult(btn) {
		if(btn == "yes") {
			document.getElementById("needchild").value = 1;
		} else if(btn == "no") {
			document.getElementById("needchild").value = 0;
		} else {
			return false;
		}
		$("#dealMethod").val("paste");
		$("#strid").val($("#checkid").val());
		$("#checkid").val($("#nodeId").val());
		document.getElementById("columnForm").submit();
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
	<form id="columnForm" action="column.do" method="post" name="columnForm">
		<div id="treeboxbox_tree" style="width:340; height:400;background-color:#f5f5f5;border :1px solid Silver;scroll:auto;">
			<complat:tree  unique="column" classname="Column" siteid="${columnForm.siteid}" treeurl="column.do?dealMethod=parentSiteTree&nodeId=0&nodeName=null&siteid=${columnForm.siteid}"/>    
		</div>
	
		<input type="hidden" name="checkid" id="checkid"/>
		<input type="hidden" name="checkname" id="checkname"/>

		<input type="hidden" name="dealMethod" id="dealMethod" />
		<input type="hidden" name="ids" id="strid" />
		<input type="hidden" name="needchild" id="needchild" value="${columnForm.needchild }"/>
		<input type="hidden" name="siteid" id="siteid" value="${columnForm.siteid }" />
		<input type="hidden" name="nodeId" id="nodeId" value="${columnForm.nodeId }" />
		<input type="hidden" name="localNodeName" id="localNodeName" value="${columnForm.localNodeName }" />
		<input type="hidden" name="message" id="message" value="${columnForm.infoMessage}" />
		<div class="form_cls">
			<ul>
 				<li id="btn">
					<input  type="button"  name="button_sure" value="确定" class="btn_normal" onClick="javascript:button_sure_onclick(this);" >
					<input  type="button"  name="button_quit" value="退出" class="btn_normal" onClick="javascript:button_quit_onclick(this);" >
				</li>
			</ul>
		</div>
	</form>
</body>
</html>


