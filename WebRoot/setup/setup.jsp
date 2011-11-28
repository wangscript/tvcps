<%@ page language="java" contentType="text/html; charset=utf-8"  errorPage="error.jsp"%>
<%@include file="/templates/headers/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0"> 
<title>CCMS后台管理</title>
<link href="images/setupimages/setup.css" type="text/css"
	rel="stylesheet" />
</head>
<c:if test="${sessionScope.uname eq null}">
	<%
		response
				.sendRedirect(request.getContextPath() + "/setup/error.jsp");
	%>
</c:if>

<frameset rows="64,*" cols="*" frameborder="no" border="0"
	framespacing="0">

	<frame src="<c:url value='/setup/top.jsp'/>" name="topFrame" scrolling="No"
		noresize="noresize" id="topFrame" title="topFrame" />

	<frameset rows="*" cols="202,*" framespacing="0" frameborder="no"
		border="0">
		<frame src="<c:url value='/setup/left.jsp'/>" name="leftFrame" scrolling="No"
			noresize="noresize" id="leftFrame" title="leftFrame" />
		<frame src="<c:url value='/setup/upload_lic.jsp'/>" name="mainFrame" id="mainFrame"
			title="mainF" />
	</frameset>

</frameset>


<noframes>
<body>
</body>
</noframes>
</html>
