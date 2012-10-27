 <%--
	功能：用于显示模板实例列表
	作者：郑荣华
 --%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>模板实例添加</title>

<style  type="text/css" media="all">
	
</style>

<script type="text/javascript">

	$(document).ready(function() {
		$("#templateInstanceName").focus();
		var message = $("#message").val();
		if(!message.isEmpty()) {
			if(message != "添加模板实例成功" && message != "修改成功") {
				alert(message);
			}
			var nodeId = $("#nodeId").val();
			var url = "<c:url value='/templateInstance.do?templateId=${templateInstanceForm.templateId}&dealMethod=&nodeId="+ nodeId +"&t="+ new Date()+"'/>";
			top.document.getElementById("rightFrame").src = url;
			closeWindow(rightFrame.getWin());
		} 
	});

	function addTemplateInstance() {
		if($("#templateInstanceName").val().isEmpty()) {
			alert("请输入模板实例名称");
			return false;
		}
		$("#dealMethod").val("add");
		$("#templateInstanceForm").submit();
	}

	function updateName() {
		if($("#templateInstanceName").val().isEmpty()) {
			alert("请输入模板实例名称");
			return false;
		}
		if($("#name").val() == $("#templateInstanceName").val()) {
			closeWindow(rightFrame.getWin());
		} else {
			$("#dealMethod").val("modify");
			$("#templateInstanceForm").submit();
		}	
	}

	function resetName() {
		$("#templateInstanceName").val("");
	}
</script>
</head>
<body>
	<form action="<c:url value="/templateInstance.do"/>" method="post" name="templateInstanceForm" id="templateInstanceForm">
		<input type="hidden" name="dealMethod"          id="dealMethod" />
		<input type="hidden" name="message"             id="message"            value="${templateInstanceForm.infoMessage }"/>
		<input type="hidden" name="templateId"          id="templateId"         value="<%=request.getParameter("templateId")%>" />
		<input type="hidden" name="templateInstance.id" id="templateInstanceId" value="${templateInstanceForm.templateInstance.id }" />
		<input type="hidden" name="nodeId"              id="nodeId"             value="<%=request.getParameter("nodeId") %>" />
		<input type="hidden" name="name"         		id="name"        		value="${templateInstanceForm.templateInstance.name }" />
		<input type="hidden" name="templateSet"         id="templateSet"        value="0" />
		<div class="form_div">
			<table style="font:12px; width:330px;">
				<tr>
					<td class="td_left" width="80px;"><i>*</i>实例名称：</td>
					<td>
						<input type="text" class="input_text_normal" name="templateInstance.name" id="templateInstanceName" style="width:240px;" value="${templateInstanceForm.templateInstance.name }" valid="string" tip="实例名称不能为空"
							   onkeydown="if(event.keyCode==13){return false;}"/>
					</td>
				</tr>
				<tr><td><li>&nbsp;</li></td></tr>
				<tr><td><li>&nbsp;</li></td></tr>
				<tr>
					<td class="td_left" width="80px;"><i>&nbsp;</i></td>
					<td><center>
						<c:if test="${templateInstanceForm.templateInstance.id  == null}">
							<input type="button" class="btn_normal" value="保存" onclick="addTemplateInstance()" align="middle" />
						</c:if>
						<c:if test="${templateInstanceForm.templateInstance.id  != null}">
							<input type="button" class="btn_normal" value="保存" onclick="updateName()" />
						</c:if>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="reset"  class="btn_normal" value="重置" />
						</center>
					</td>
				</tr>
			</table>
	    </div>		
	</form>
</body>
</html>