<%@page contentType="text/html;charset=utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="css/style.css"/>
<title>网上调查结果</title>
</head>
<body>
<form action=""	name="onlineSurveyContentForm" id="onlineSurveyContentForm" method="post">
<c:if test="${onlineSurveyContentForm.listQuestion != null}">
	<c:forEach var="list1" items="${onlineSurveyContentForm.listQuestion}" step="1" varStatus="question">
		${list1[1]}
		<table border="1" width="100%">
		<c:set scope="page" value="0" var="temp"></c:set>
		<c:forEach var="list2" items="${onlineSurveyContentForm.listAnswer}" step="1" varStatus="answer">
			<c:if test="${list1[0] == list2[0] and temp == 0}">
				<tr>
					<td align="center">
						选项
					</td>
					<td align="center">
						票数
					</td>
					<td colspan="2" align="center">
						比例
					</td>
				</tr>
				<c:set var="temp" value="1" scope="page"></c:set>
			</c:if>
		</c:forEach>
		<c:forEach var="list3" items="${onlineSurveyContentForm.listAnswer}" step="1" varStatus="ans">
			<c:if test="${list1[0] == list3[0]}">
				<tr>
					<td align="center">
						${list3[1]}
					</td>
					<td align="center">
						${list3[2]}
					</td>
					<td align="center">
						${list3[3]}
					</td>
					<td align="left">
  						<img src="images/comment.jpg" width="${list3[3]}" height="10">
					</td>
				</tr>
			</c:if>
		</c:forEach>
		</table>
	</c:forEach>
</c:if>
</body>
</html>