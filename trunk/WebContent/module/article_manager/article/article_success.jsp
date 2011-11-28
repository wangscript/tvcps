<%@page language="java" contentType="text/html; charset=utf-8"%>
<html>
<head>
<%@include file="/templates/headers/header.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>转移(呈送)文章成功</title>
<script type="text/javascript">
	$(document).ready(function() { 
		if(!$("#message").val().isEmpty()){
			closeWindow(rightFrame.getWin());
			var columnId = document.getElementById("columnId").value;
			var formatId = document.getElementById("formatId").value;
			var url = "<c:url value='/article.do?dealMethod=&formatId="+formatId+"&columnId="+ columnId +"&operationType=article'/>";
			top.document.getElementById("rightFrame").src = url;
		}
	});
</script>
</head>
<body>
	<input type="hidden" id="columnId" name="columnId" value="${articleForm.columnId}">
	<input type="hidden" name="message" id="message" value="${articleForm.infoMessage}" />
	<input type="hidden" name="formatId" id="formatId" value="${articleForm.formatId}">
</body>
</html>