<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>

<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>模板单元样式列表</title>
<script type="text/javascript">
	//增加样式
	function button_add_onclick(ee){	
		$("#dealMethod").val("detail");
		document.getElementById("styleId").value="0";
		$("#templateUnitStyleForm").submit();
	}
	//模板单元样式详细信息
	function templateStyleDetail(id){
		$("#dealMethod").val("detail");
		document.getElementById("styleId").value=id;
		$("#templateUnitStyleForm").submit();
	}
	//删除数据
	function button_delete_onclick(ee){		
		var ids = document.getElementById("sId").value;
		if(ids == "" || ids == null){
			alert("请至少选择一条记录操作！");
			return false;
		}
		if(confirm("你确定要删除吗？")) {	
			document.getElementById("ids").value = document.getElementById("sId").value;		
			document.all("dealMethod").value="delete";	
			document.forms[0].submit();
		}else { 
 	 		return false;
 	 	}
	}

	function win_close(opsid) {
		var firstWin = top.getWin();		
		var temp = firstWin.split("###");
		var dialogProperty = temp[0];
		var detailFrame = "_DialogFrame_"+dialogProperty;	 		   
		var tempWindow = top.document.getElementById(detailFrame).contentWindow;
		tempWindow.frames["unitEditArea"].document.location.reload();
	}
</script>
</head>
<body>	
	<div class="currLocation">模板单元>>样式管理</div>
	<form id="templateUnitStyleForm" action="<c:url value='/templateUnitStyle.do'/>" method="post" name="templateUnitStyleForm">	
        <input type="hidden" name="dealMethod" id="dealMethod"  value="list"/>
		<input type="hidden" name="ids" id="strid"/>
        <input type="hidden" name="styleId" id="styleId" >
		<input type="hidden" name="categoryId" id="categoryId" value="${templateUnitStyleForm.categoryId}"/>
		<input type="hidden" name="categoryName" id="categoryName" value="${templateUnitStyleForm.categoryName}"/>
		<complat:button name="button" buttonlist="add|0|delete" buttonalign="left"/>
		<complat:grid ids="sId" width="*,*,*,*" head="样式名称,创建者,创建日期,模板单元类型"		  element="[1,link,onclick,templateStyleDetail]" 
		     page="${templateUnitStyleForm.pagination}" form="templateUnitStyleForm" action="<c:url value='templateUnitStyle.do?dealMethod=list&categoryId=${templateUnitStyleForm.categoryId}'/>" />
	</form>
</body> 
</html>