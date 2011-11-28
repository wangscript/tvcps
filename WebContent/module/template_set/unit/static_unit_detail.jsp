<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>静态单元</title>
<%@include file="/templates/headers/header.jsp"%>
<script type="text/javascript" src="<c:url value="/script/fckeditor/fckeditor.js"/>"></script>
<script type="text/javascript">

	$(document).ready(function() {
		var message = $("#message").val(); 
		if(message != null && message != "" && message != "null") {
			alert(message);
		}
	});

	function btn_save() {	
		var fck = FCKeditorAPI.GetInstance("staticContent"); 
		if ($("#unitId").val() > 0) {
			$("#unit_unitId").val($("#unitId").val());
			$("#unit_categoryId").val($("#categoryId").val());
		}
		if(fck.GetHTML().trim() == null || fck.GetHTML().trim() == "") {
			alert("编辑区不能为空");
			return false;
		}
		$("#dealMethod").val("saveConfig");
		$("#staticUnitForm").submit();	
		//设置标记为已保存
		document.getElementById("hasSaved").value = "Y";
	}
	
	function fck_insertHtml(value){	 
		var fck = FCKeditorAPI.GetInstance("staticContent");
		fck.InsertHtml(value);	
	}   
	
	function saveSite() {
		var fck = FCKeditorAPI.GetInstance("staticContent"); 
		if ($("#unitId").val() > 0) {
			$("#unit_unitId").val($("#unitId").val());
			$("#unit_categoryId").val($("#categoryId").val());
		}
		if(fck.GetHTML().trim() == null || fck.GetHTML().trim() == "") {
			alert("编辑区不能为空");
			return false;
		}
		$("#dealMethod").val("saveSiteConfig");
		$("#staticUnitForm").submit();	
		//设置标记为已保存
		document.getElementById("hasSaved").value = "Y";
	}
</script>
</head>
<body>
<form id="staticUnitForm" action="<c:url value="/staticUnit.do"/>" name="staticUnitForm"  method="post">
	<input type="hidden" id="unit_unitId" name="unit_unitId" value="${staticUnitForm.unit_unitId}"/>
	<input type="hidden" id="message" name="message" value="${staticUnitForm.infoMessage}"/>
	<input type="hidden" id="unitId" name="unitId" value="<%=request.getParameter("unit_unitId")%>"/>
	<input type="hidden" id="categoryId" name="categoryId" value="<%=request.getParameter("unit_categoryId")%>"/>
	<input type="hidden" id="unit_categoryId" name="unit_categoryId" value="${staticUnitForm.unit_categoryId}"/>
	<input type="hidden" name="dealMethod" id="dealMethod" value="findConfig"/>
	<input type="hidden" id="hasSaved" name="hasSaved" value="N" />
	<FCK:editor basePath="/script/fckeditor" instanceName="staticContent" value="${staticUnitForm.staticContent}" toolbarSet="Ccms_openbasic" height="400"></FCK:editor>
	<center>
	<input type="button" value="保存"   class="btn_normal"  onclick="btn_save()"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="button" value="站内保存"   class="btn_normal" onclick="saveSite()" />
	</center>
</form>
</body>
</html>