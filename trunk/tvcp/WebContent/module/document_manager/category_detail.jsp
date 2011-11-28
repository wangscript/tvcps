<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>新增文档</title>
<script type="text/javascript">

	//原来类别名称
	var nameStr;
	$(document).ready(function() {
		$("#category").focus();
		nameStr = document.getElementById("category").value;
		if($("#message").val() != null && $("#message").val() != "") {
			if($("#message").val() == "增加成功" || $("#message").val() == "修改成功") {
				top.reloadAccordion("/${appName}/module/document_manager/refresh_Tree.jsp");
				rightFrame.window.location.href = "<c:url value='/category.do?nodeId=${categoryForm.nodeId}&dealMethod='/>";
				closeWindow(rightFrame.getWin());
	    	} else {
				alert($("#message").val());
	    	}
		}
	});

	function addCategory(obj){
		if(document.getElementById("category").value == null || document.getElementById("category").value == "" || document.getElementById("category").value.trim() == "") {
			alert("请输入文档名称");
			return false;
		}
		var name = document.getElementById("category").value.trim();
		var names = $("#categoryName").val();
		var arr = new Array();
		arr = names.split(",");
		for(var i = 0; i < arr.length; i++) {
			if(arr[i] == name) {
				alert("该类别已存在!");
				return false;
			}
		}
        $("#dealMethod").val("add");
		$("#categoryForm").submit(); 
	}

	function modifyCategory(obj) {
		if(document.getElementById("category").value == null || document.getElementById("category").value == "") {
			alert("文档名称不能为空");
			return false;
		}
		var name = document.getElementById("category").value;
		var names = $("#categoryName").val();
		var arr = new Array();
		arr = names.split(",");
		//如果名字没被修改
		if(nameStr == name) {
			$("#dealMethod").val("modify");	
			$("#categoryForm").submit();  
		} else {
			for(var i = 0; i < arr.length; i++) {
				if(arr[i] == name) {
					alert("该类别已存在!");
					return false;
				}
			}
			$("#dealMethod").val("modify");	
			$("#categoryForm").submit();   
		}
	}
	
	function closeChild() {
		closeWindow(win);
	}

	
</script>

<style  type="text/css" media="all">
	 
</style>

</head>
<body>
    <form action="<c:url value="/category.do"/>" method="post" name="categoryForm" id="categoryForm" >
         <input type="hidden" name="message" id="message" value="${categoryForm.infoMessage}"/>
		 <input type="hidden" name="dealMethod" id="dealMethod" />
		 <input type="hidden" name="ids" id="strids" />
         <input type="hidden" name="category.id" id="id" value="${categoryForm.category.id }"/>
         <input type="hidden" name="nodeId" id="nodeId" value="<%=request.getParameter("nodeId")%>">
         <input type="hidden" name="createTime" value="${categoryForm.category.createTime }"/>
         <input type="hidden" name="userid" value="${categoryForm.category.creator.id}"/>
         <input type="hidden" name="siteid" value="${categoryForm.category.site.id }"/>
		 <input type="hidden" name=categoryName id="categoryName" value="${categoryForm.categoryName}"/>
		 <div class="form_div">
		 	<table style="font:12px; width:500px;">
				<tr>
					<td class="td_left" width="90px;"><i>*</i>文档名称：</td>
					<td width="400px;"><input type="text" class="input_text_normal" name="category.name" style="width:400px;" id="category" valid="string" tip="文档名称不能为空" value="${categoryForm.category.name}" onkeydown="if(event.keyCode==13){return false;}"/></td>
				</tr>
				<tr>
					<td class="td_left" width="90px;"><i>&nbsp;</i>描述：</td>
					<td width="400px;"><textarea  rows="7" cols="15" name="category.description" class="input_textarea_normal" id="category.description">${categoryForm.category.description}</textarea></td>
				</tr>
				<tr><td></td></tr>
				<tr>
					<td class="td_left" width="90px;"><i>&nbsp;</i></td>
					<td width="400px;"><center>
						<c:if test="${categoryForm.category.id == null}">
							<input type="button" class="btn_normal" value="添加" onclick="addCategory()" />
						</c:if>
						<c:if test="${categoryForm.category.id != null}">
							<input type="button" class="btn_normal" value="保存" onclick="modifyCategory()" />
						</c:if>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="reset" class="btn_normal" value="重置"/></center>
					</td>
				</tr>
			</table>
	     </div>
   </form>           
</body>
</html>