<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
	<%@include file="/templates/headers/header.jsp"%>
	<link rel="stylesheet" type="text/css" href="<c:url value="/script/ext/resources/css/ext-all.css"/>"/>
	<script type="text/javascript" src="<c:url value="/script/ext/adapter/ext/ext-base.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/script/ext/ext-all-debug.js"/>"></script>
	<link rel="stylesheet" type="text/css" href="<c:url value="/script/dhtmlx.css"/>"/>
	<script type="text/javascript" src="<c:url value="/script/dhtmlx.js"/>"></script>	
	<script>
	parent.changeFrameUrl("rightFrame", "<c:url value="/sitemessage.do?dealMethod=receiveMessageBox"/>");
	</script>
	<script type="text/javascript">
		function tree_onclick(node){
			parent.changeFrameUrl("rightFrame","sitemessage.do?dealMethod="+node.id);		
		}
	</script>
</head>
<body>
 <complat:tree unique="message"  treeurl="../../sitemessage.do?dealMethod=getTree" />
</body>
</html>