<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>办件类别管理</title>
<style  type="text/css" media="all">
	
</style>
</head>
<body>
	<form id="letterForm" name="letterForm" method="post" action="userLetter.do" >
	<div class="form_div">	
		<table style="font:12px; width:780px;">
			<tr>
				<td class="td_left" width="90"><li>&nbsp;</li>标 　　题：</td>
				<td>${letterForm.title}</td>
			</tr>
			<tr>
				<td class="td_left" width="90"><li>&nbsp;</li>信件内容：</td>
				<td>${letterForm.content}</td>
			</tr>
			<tr>
				<td class="td_left" width="90"><li>&nbsp;</li>回复部门：</td>
				<td>${letterForm.replyOrg}</td>
			</tr>
			<tr>
				<td class="td_left" width="90"><li>&nbsp;</li>回复日期：</td>
				<td>${letterForm.replyDate}</td>
			</tr>
			<tr>
				<td class="td_left" width="90"><li>&nbsp;</li>回复内容：</td>
				<td>${letterForm.replyContent}</td>
			</tr>
		</table>
	</div>
	</form>
</body>
</html>
