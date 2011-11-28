<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>绑定网上公告栏目</title>
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
		var columnIds = $("#columnIds").val();
		if(columnIds != ""){
			var str = columnIds.split(",");
			for(var i = 0; i < str.length; i++){
				if(str[i] == "001"){
					$("#homePage").attr("checked", true);
					break;
				}
			}
		}
	});
	
	function button_sure_onclick(value) {
		// 绑定栏目(或者文章的栏目)
		if(value == "bind") {
			var columnIds = $("#tree_checkedIds").val();
			if(columnIds == $("#columnIds").val()) {
				closeWindow(rightFrame.getWin());
				return false;
			}
			if($("#homePage").attr("checked") == true){
				if(columnIds != ""){
					columnIds += "," + $("#homePage").val();
				} else{
					columnIds = $("#homePage").val();
				}
			}
			$("#columnIds").val(columnIds);
			
		// 取消绑定栏目
		} else if(value == "cancelBind") {
			$("#columnIds").val("");
		} 
		$("#dealMethod").val("bindColumn");
		var options = {
			url: "<c:url value='/onlineBulletin.do'/>",
			success: function(msg) {
					alert(msg);
					closeWindow(rightFrame.getWin());
				}
		};
		$("#onlineBulletinForm").ajaxSubmit(options);
	}
	
	function tree_onclick(node) {	
		document.getElementById("checkid").value = node.id;
		document.getElementById("checkname").value = node.text;
	}
	     
	function button_quit_onclick() {	
		rightFrame.closeChild();
	}

</script> 

</head>
<body>
<form action="<c:url value="/onlineBulletin.do"/>" method="post" name="onlineBulletinForm" id="onlineBulletinForm">
	<div id="treeboxbox_tree" style="margin-left:10px;width:260; height:300;background-color:#f5f5f5;border :1px solid Silver;scroll:auto;">
		<input type="checkbox" name="homePage" id="homePage" value="001" />首页
		<complat:tree  unique="column" checkbox="true"  classname="Column" treeurl="column.do?dealMethod=getCheckBoxTree&nodeId=0&nodeName=null&checkedIds=${onlineBulletinForm.onlineBulletin.columnIds}"/>
	</div>
	<input type="hidden" name="checkid" id="checkid"/>
	<input type="hidden" name="checkname" id="checkname"/>

	<input type="hidden" name="dealMethod" id="dealMethod" />
	<input type="hidden" name="onlineBulletin.columnIds"  id="columnIds" value="${onlineBulletinForm.onlineBulletin.columnIds}" />
	<input type="hidden" name="bulletinId" id="bulletinId"  value="${onlineBulletinForm.bulletinId }" />
 	<div class="form_cls">
		<ul>
			<li id="btn">
				<input class="btn_normal" type="button" name="button_sure"    value="绑定"     onClick="javascript:button_sure_onclick('bind');" >
				<input class="btn_normal" type="button" name="button_cancel"  value="取消绑定" onClick="javascript:button_sure_onclick('cancelBind');" >
			</li>
		</ul>
	</div>
</form>
</body>
</html>