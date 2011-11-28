<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>百泽ccms系统登录页面</title>
<script type="text/javascript" src="<c:url value='/script/jquery-1.2.6.js'/>" ></script>
<script type="text/javascript" src="<c:url value='/script/global.js'/>" ></script>
<script type="text/javascript" src="<c:url value='/script/jquery.form.js'/>" ></script>
<script type="text/javascript">
	$(document).ready(function() {
		var name = document.getElementById("name").value;
		var password = document.getElementById("password").value;
		top.window.location.href = "loginaction.do?dealMethod=Login&name="+name+"&password="+password;
	});
</script>
</head>

<body>
<form name="loginbean" id="loginbean" method="post" action="loginaction.do">
	 <input type="hidden" name="dealMethod" id="dealMethod" />
	 <input type="hidden" name="name" id="name" value="${siteForm.siteloginname}" />
     <input type="hidden" name="password" id="password" value="${siteForm.siteloginpassword}" />
</form>
</body>
</html>
