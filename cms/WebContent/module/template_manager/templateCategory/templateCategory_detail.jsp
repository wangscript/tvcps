 <%--
	功能：用于模板类别的添加、修改
	作者：郑荣华
 --%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增或者修改模板类别</title>
<%@include file="/templates/headers/header.jsp"%>
<style type="text/css" media="all">

</style>

<script type="text/javascript">
	
	function add_templateCategory() {
		if($('#templateCategoryName').val().trim().isEmpty()){
			alert("请输入模板类别名称");
		}
		$("#dealMethod").val("add");		
		$("#templateCategoryForm").submit();
    }

	function modify_templateCategory() {
		var templateCategoryName = $("#templateCategoryName").val();
		var name = $("#name").val();
		if(templateCategoryName == name) {
			closeWindow(rightFrame.getWin());
		} else {
			$("#dealMethod").val("modify");	
			$("#templateCategoryForm").submit();
		}
	}

	$(document).ready(function() {	
		$("#templateCategoryName").focus();
		if(!$("#message").val().isEmpty()) {			
			var message = $("#message").val();
			if(message != "添加模板类别成功" && message != "修改模板类别成功") {
				alert(message);
			}  	
			var url = "<c:url value='/templateCategory.do?nodeId=${templateCategoryForm.nodeId}&dealMethod='/>";
			top.reloadAccordion("/${appName}/module/template_manager/refresh_Tree.jsp"); 	
			//top.document.getElementById("rightFrame").src = url;    //+"&t="+ new Date();

			rightFrame.window.location.href = url;
			closeWindow(rightFrame.getWin());
		}
	});

</script>
</head>
<body>
	<form action="<c:url value="/templateCategory.do"/>" method="post" name="templateCategoryForm" id="templateCategoryForm">
		<input type="hidden"  name="dealMethod"          id="dealMethod" />
		<input type="hidden"  name="ids"                 id="strids" />
		<input type="hidden"  name="message"             id="message"            value="${templateCategoryForm.infoMessage}" />
        <input type="hidden"  name="templateCategory.id" id="templateCategoryid" value="${templateCategoryForm.templateCategory.id }" />
        <input type="hidden"  name="nodeId"              id="nodeId"             value="<%=request.getParameter("nodeId")%>" />
        <input type="hidden"  name="creatorid"           id="creatorid"          value="${templateCategoryForm.templateCategory.creator.id}" />
        <input type="hidden"  name="siteid"              id="siteid"             value="${templateCategoryForm.templateCategory.site.id }" />
		<input type="hidden"  name="categoryName"        id="name"               value="${templateCategoryForm.templateCategory.name}"/>
		<div class="form_div">
			<table style="font:12px; width:330px;">
				<tr>
					<td class="td_left" width="100px;"><i>*</i>模板类别名称：</td>
					<td>
						<input type="text" class="input_text_normal" name="templateCategory.name" id="templateCategoryName" style="width:230px;" tip="模板类别不能为空" valid="string"  value="${templateCategoryForm.templateCategory.name}"
								onkeydown="if(event.keyCode==13){return false;}"/>
						</td>
				</tr>
				<tr><td><li>&nbsp;</li></td></tr>
				<tr><td><li>&nbsp;</li></td></tr>
				<tr>
					<td class="td_left" width="100px;"><i>&nbsp;</i></td>
					<td><center>
						<c:if test="${templateCategoryForm.templateCategory.id == null}">
							<input class="btn_normal" type="button" value="保存" onclick="add_templateCategory()" />
						</c:if>
						<c:if test="${templateCategoryForm.templateCategory.id != null}">
							<input class="btn_normal" type="button" value="保存" onclick="modify_templateCategory()" />
						</c:if>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input class="btn_normal" type=reset value="重置" /></center> 
					</td>
				</tr>
			</table>
	    </div>
	</form>     
</body>
</html>