
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>用户操作管理</title>
<script>
	var childNewOrgWin = "";
	var childDetailOrgWin = "";
	function button_add_onclick(ee){	
		childNewOrgWin = showWindow("newoperation","新增操作","<c:url value='/module/config_manager/operation_detail.jsp'/>",293, 0,480,400);	 
	}
	
	function button_delete_onclick(ee){	
		var ids = document.getElementById("xx").value;
		if(ids == "" || ids == null){
			alert("请至少选择一条记录操作！");
			return false;
		}
		document.getElementById("strid").value = document.getElementById("xx").value;
		alert(document.getElementById("strid").value);
		document.all("dealMethod").value="delete";	
		document.forms[0].submit(); 
	    
	   // document.execCommand('Refresh');
	    window.location.reload();
   	    parent.tree.clearAll(true);
		tree.loadXML("<c:url value="/resource/configManager/configManager.xml"/>");
	 //   window.location.reload();
	}
	
	function closeNewChild() {
		childNewOrgWin.close();
	}
	function closeDetailChild() {
		childDetailOrgWin.close();
	}
	function operdetail(id){
		childDetailOrgWin = showWindow("operationdetail","操作信息","operation.do?dealMethod=detail&id="+id+"",293, 0,480,400);	 
	}
</script>
</head>
<body>
	<div class="currLocation">用户操作管理</div>
	
	<form id="operationForm" action="operation.do" method="post" name="operationForm">
		<input type="hidden" name="dealMethod" id="dealMethod"/>
		<input type="hidden" name="ids" id="strid"/>
		<complat:button name="button" buttonlist="add|0|delete" buttonalign="left"/>
		<complat:grid ids="xx" width="*" head="操作名称" 
		element="[1,link,onclick,operdetail]" 
		page="${operationForm.pagination}" form="operationForm" action="operation.do"/>
	</form>
</body>
</html>
