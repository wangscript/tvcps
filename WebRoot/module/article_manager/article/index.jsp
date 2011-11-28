<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>
	<%@include file="/templates/headers/header.jsp"%>
	<link rel="stylesheet" type="text/css" href="<c:url value="/script/ext/resources/css/ext-all.css"/>"/>
	<script type="text/javascript" src="<c:url value="/script/ext/adapter/ext/ext-base.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/script/ext/ext-all-debug.js"/>"></script>
<script type="text/javascript">
	function tree_onclick(node) {
		var columnId = node.id;
		var formatId = 0;
		if (node.attributes.formatid) {
			formatId = node.attributes.formatid;
		}
		var url = "<c:url value="/article.do?dealMethod=&columnId="/>" + columnId + "&operationType=article&nodeId="+columnId+"&formatId=" + formatId + "&" + getUrlSuffixRandom();
		parent.changeFrameUrl("rightFrame", url);
	}
</script>
</head>
<body>
	<complat:tree unique="column1"  treeurl="../../../column.do?dealMethod=getArticleColumnTree&nodeId=0&nodeName=null" pageurl="article.do?dealMethod=&operationType=article"/> 
</body>
</html>