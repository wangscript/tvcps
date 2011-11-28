<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>模板设置</title>
	<%@include file="/templates/headers/header.jsp"%>
	<link rel="stylesheet" type="text/css" href="<c:url value="/script/ext/resources/css/ext-all.css"/>"/>
	<script type="text/javascript" src="<c:url value="/script/ext/adapter/ext/ext-base.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/script/ext/ext-all-debug.js"/>"></script>
	<script type="text/javascript">
		function tree_onclick(node){
			var url = "templateUnit.do?dealMethod=&nodeId=" + node.id+"&"+getUrlSuffixRandom();
			parent.changeFrameUrl("rightFrame", url);
		}
	</script>
</head> 
<body>
	<complat:tree unique="templateset"  classname="Column" treeurl="../../column.do?dealMethod=getTemplateSetTree&nodeId=0&nodeName=null" pageurl="templateUnit.do?dealMethod=&nodeId=0"/> 
</body>
</html>