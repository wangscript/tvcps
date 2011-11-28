<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>办件类别管理</title>
<style type="text/css" media="all">

</style>
<script type="text/javascript">
	//原来类别名称
	var nameStr;
	$(document).ready(
					function() {
						$("#name").focus();
						nameStr = document.getElementById("name").value;
						if ($("#flag").val() == 1) {
							top.document.getElementById("rightFrame").src = "<c:url value='/letterCategory.do?dealMethod=find&" + getUrlSuffixRandom() + "'/>";
							closeWindow(rightFrame.getWin());
							//parent.window.location.href = "<c:url value='/letterCategory.do?dealMethod=find'/>";
							//parent.closeChild();
						}
					});

	function add_letterCategory() {

		if (document.getElementById('name').value == null
				|| document.getElementById('name').value == "") {
			alert("请输入信件类别名称");
			return false;
		}

		var name = $("#name").val();
		var names = $("#categoryName").val();
		var arr = new Array();
		arr = names.split(",");
		for ( var i = 0; i < arr.length; i++) {
			if (arr[i] == name) {
				alert("该类别已存在!");
				return false;
			}
		}
		
		$("#dealMethod").val("add");
		$("#letterCategoryForm").submit();
	}

	function modify_letterCategory() {

		if (document.getElementById('name').value == null
				|| document.getElementById('name').value == "") {
			alert("请输入类别名称!");
			return false;
		}

		var name = $("#name").val();
		var names = $("#categoryName").val();
		var arr = new Array();
		arr = names.split(",");
		if (nameStr == name) {
			$("#dealMethod").val("modify");
			$("#letterCategoryForm").submit();
		} else {
			for ( var i = 0; i < arr.length; i++) {
				if (arr[i] == name) {
					alert("该类别已存在!");
					return false;
				}
			}
			$("#dealMethod").val("modify");
			$("#letterCategoryForm").submit();
		}
	}

	function reset_letterCategory() {
		$("#name").val("");
	}
</script>
</head>
<body>
<form id="letterCategoryForm" action="<c:url value="/letterCategory.do"/>" method="post" name="letterCategoryForm">
	<input type="hidden" name="dealMethod" id="dealMethod" />
	<input type="hidden" name="flag" id="flag" value="${letterCategoryForm.flag}" /> 
	<input type="hidden" name="categoryName" id="categoryName" value="${letterCategoryForm.categoryName}" />
	<input type="hidden" name="categoryId" id="categoryId" value="${letterCategoryForm.letterCategory.id}" />
	<div class="form_div">
		<table style="width:330px; font:12px;">
			<tr>
				<td class="td_left" width="100"><i>*</i>信件类别名称：</td>
				<td>
					<input type="text" name="name" style="width:220" id="name" class="input_text_normal" tip="不能为空" valid="string" value="${letterCategoryForm.letterCategory.name}" 
						   onkeydown="if(event.keyCode==13){return false;}"/>
				</td>
			</tr>
			<tr><td><li>&nbsp;</li></td></tr>
			<tr><td><li>&nbsp;</li></td></tr>
			<tr>
				<td colspan="2">　　　　
					<c:if
						test="${letterCategoryForm.letterCategory.id == null}">　　　
						　　　　<input type="button" class="btn_normal" value="添加" onclick="add_letterCategory()" />　　
					</c:if> 
					<c:if test="${letterCategoryForm.letterCategory.id != null}">　　
						<input type="button" class="btn_normal" value="保存" onclick="modify_letterCategory()" />　　
					</c:if> 
						&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="reset" class="btn_normal" value="重置" />
				</td>
			</tr>
		</table>
	</div>
</form>
</body>
</html>
