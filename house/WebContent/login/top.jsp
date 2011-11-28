<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理后台登录</title>
<script>
	function logOut(){
		parent.window.location.href = "<c:url value='/adminuser/logOut.shtml'/>";
	}
</script>
</head>
<body>
家政服务管理后台
<a href="#" onclick="logOut()">注销</a>
</body>
</html>