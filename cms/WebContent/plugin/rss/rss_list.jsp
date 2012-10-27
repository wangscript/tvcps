<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>RSS 设置</title>
</head>
<body>
<%
	List list=(List)request.getAttribute("list");
	for(int i=0;i<list.size();i++){
	%>
		<%=list.get(i) %>
	<%
}
%>
</body>
</html>