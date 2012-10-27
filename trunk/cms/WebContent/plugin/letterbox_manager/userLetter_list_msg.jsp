<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>
	<%@include file="/templates/headers/header.jsp"%>

</head>
<body>
	<form id="letterForm" action="userLetter.do" name="letterForm">
	<input type="hidden" id="dealMethod" name="dealMethod" value=""/>
	<input type="hidden" id="letterStatus" name="letterStatus" />
	<table width="80%" border="1" align="center" cellpadding="1" cellspacing="1" >
		<tr style="background:#cccccc; height:40px;">
			<td><center>编　号</center></td>
			<td><center>类　别</center></td>
			<td><center>标　题</center></td>
			<td><center>状　态</center></td>
			<td><center>写　信　日　期</center></td>
		</tr>
		<tr>
			<c:forEach items="${letterForm.list}" var="list" >
			<td>${list[1]}</td>
			<td>${list[2]}</td>
			<td onClick="showUserLetterDetail('${list[0]}', ${list[4]})">${list[3]}</td>
			<c:if test="${list[4] == 1}"> 
                <td>未处理</td>           
        	</c:if>
        	<c:if test="${list[4] == 2}"> 
                <td>待处理</td>           
        	</c:if>
			<c:if test="${list[4] == 3}"> 
                <td>已处理</td>           
        	</c:if>  
			<td>${list[5]}</td>
			</th>
		</tr>
		</c:forEach>
	</table>
	</form>
</body>
</html>