<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@taglib uri="/WEB-INF/tld/complat.tld" prefix="complat"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href='images/commentStyle.css' rel='stylesheet' type='text/css' />
		<!-- 
		<link rel="stylesheet" type="text/css" href="<c:url value="/css/style.css"/>" >
		<script type="text/javascript" src="<c:url value='/script/jquery-1.2.6.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/script/global.js'/>" ></script>
		<link rel="stylesheet" type="text/css"	href="<c:url value='/css/ajaxpagination.css'/>">
		<script type="text/javascript"src="<c:url value='/script/jquery.pagination.js'/>"></script>
		 -->
		<link rel="stylesheet" type="text/css" href="css/style.css"/>
		<script type="text/javascript" src="script/jquery-1.2.6.js"></script>
		<script type="text/javascript" src="script/global.js" ></script>
		<link rel="stylesheet" type="text/css"	href="css/ajaxpagination.css"/>
		<script type="text/javascript"src="script/jquery.pagination.js"></script>
	</head> 
<body>
<form action='commitComment.do'	name='articleCommentForm' id='articleCommentForm' method="post">
<input type="hidden" name="dealMethod" value="commitComment">
<input type='hidden' name='infoMessage' id='infoMessageId' value="${articleCommentForm.infoMessage}"/>
	<complat:ajaxpage page='${articleCommentForm.pagination}' pageId='pp'	form='articleCommentForm'	action='commitComment.do?dealMethod=commentList2' />
	${requestScope.content}
<br/>   
<div id="commentDivId">
<table>
	<tr>
		<td>评论人：</td>
		<td><input type='text' name='comment.authorName' id='author' /></td>
	</tr>
	<tr>
		<td>评论内容：</td>
		<td>
			<textarea rows='6' cols='80' name='comment.content'	id='contentID'></textarea>
		</td>
	</tr>
	<tr>
		<td colspan='2' align='center'>
			<input type='button' onclick='checkComment();' value='提交' />
		</td>
	</tr>
</table>
</div>
</form>
</body>
</html>