<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<html>
<head>
<title>文档管理中的flash预览</title>
<style type="text/css">
	#show {
	  	margin-left:15px;
	}	
</style>
<script type="text/javascript">

	$(document).ready(function() {
		var u;
		u = '/${appName}' + rightFrame.document.getElementById("url").value;
		document.getElementById("show").innerHTML='<embed src=\''+ u +'\' width=\'100%\' height=\'100%\' quality=\'high\' bgcolor=\'#f5f5f5\' ></embed>'; 
	});
</script>
</head>
<body>
	<div id="show"></div>
</body>
</html>