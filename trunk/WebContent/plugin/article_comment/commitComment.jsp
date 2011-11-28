<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>替换词设置</title>
</head>
<script type="text/javascript">
	
</script>
<body>
<form action="<c:url value='/commitComment.do'/>" method="post" name="articleCommentForm" id="articleCommentForm">
<input type="hidden" name="ids" id="strid" value="<%=request.getParameter("cid") %>"/>
<input type="hidden" name="dealMethod" id="dealMethod" value="commitComment"/>
<table align="center">
	<tr>
		<td>发表人：</td>
		<td>
			<input type="text" name="comment.authorName"/>
		</td>
	</tr>
	<tr>
		<td>发表内容：</td>
		<td> 
			<textarea rows="5" cols="15" name="comment.content"></textarea>
		</td>
	</tr>
	<tr>
		<td colspan="2" align="center">
			<input type="submit" value="提交"/>
		</td>
	</tr>
</table>
</form>
</body>
</html>