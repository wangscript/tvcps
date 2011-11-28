<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file='/templates/headers/header.jsp'%>
<html>
	
	<head>
		<title>评论详细页面</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	</head>
<script type="text/javascript">
	function btnAudit(){
		    $("#dealMethod").val("audited");
		    $("#objContentId").val("1");//代表审核
			$("#articleCommentForm").submit();
		}
	function closeWin(){
		closeWindow(rightFrame.getWin());		
		}
</script>
<body>
<form action="<c:url value='/articleComment.do'/>" method="post" name="articleCommentForm" id="articleCommentForm">
<input type="hidden" name="ids" id="strid" value="${articleCommentForm.comment.id}"/>
<input type="hidden" name="dealMethod" id="dealMethod" value="commitComment"/>
<input type="hidden" name="objContent" id="objContentId" />
<input type="hidden" name="delTag" id="delTagId" />
<table align="center">
	<tr>
		<td>发表人：</td>
		<td>
			<input type="text" name="comment.authorName" value="${articleCommentForm.comment.authorName}"/>
		</td>
	</tr>
	<tr>
		<td>发表内容：</td>
		<td> 
			<textarea rows='6' cols='80' name='comment.content'	id='contentID'>${articleCommentForm.comment.content}</textarea>
		</td>
	</tr>
	<tr>
		<td colspan="2" align="center">
			<input type="button" onclick="closeWin();" value="关闭"/>
		</td>
	</tr>
</table>
</form>
</body>
</html>