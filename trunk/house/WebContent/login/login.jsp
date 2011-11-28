<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<%@ include file="/common/message.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理后台登录</title>
<style>

</style>
<script type="text/javascript">
$(document).ready(function(){
    $("#loginForm").validate();
  });
</script>
</head>
<body>
	<form action="<c:url value='/adminuser/login.shtml'/>" method="post" id="loginForm">	
		<div class="tableDiv">
			<table  class="tableDetail">
				<tr>
					<td class="labelTd">账号：</td>
					<td class="textTd"><input type="text" id="loginName" name="loginName" class="required" value="${loginName}" ></td>
				</tr>
				<tr>
					<td class="labelTd">密码：</td>
					<td class="textTd"><input type="password" id="passWord" name="passWord" class="required" ></td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="submit" value="登录" class="buttonStyle" />			
						<input type="reset" value="重置" class="buttonStyle" />
					</td>
				</tr>
			</table>
		</div>
				
	</form>

</body>
</html>