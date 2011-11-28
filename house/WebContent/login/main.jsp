<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理后台</title>
<style>

</style>
</head>

<frameset rows="85,*" frameborder="no">
	<frame src="<c:url value="/login/top.jsp"/>"/>
	<frameset cols="177,*">
		<frame id="leftFrame" src="<c:url value="/login/left.jsp" />" scrolling="auto"/>
		<frame id="rightFrame" name="rightFrame"  src="<c:url value="/village/queryVillage.shtml"/>" />
	</frameset>
</frameset>
<noframes>
	<body></body>
</noframes>
</html>