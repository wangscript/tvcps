<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>转办记录</title>
</head>
<body>
	<form id="letterForm" name="letterForm" method="post" action="letter.do" >
	<div class="currLocation">转办记录</div>
	<input type="hidden" id="dealMethod" name="dealMethod" value=""/>
	<table border="1" align="center" width=80%>
		<tr>
			<td>发送部门</td>
			<td>转至部门</td>
			<td>备注</td>
		</tr>
		<tr>
		<c:forEach items="${letterForm.list}" var="letter" >
		
			<td>${letter[0]}</td>
			<td>${letter[1]}</td>
			<td>${letter[2]}</td>

		</tr>
　　		</c:forEach>
	</table>
	
	</form>
</body>
</html>
