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
		function tree_onclick(node){
			var url = "<c:url value='column.do?dealMethod=&operationType=column&nodeId='/>"+node.id+"&localNodeName="+encodeURI(node.text)+"&"+getUrlSuffixRandom();			
			parent.changeFrameUrl("rightFrame", url);
		}
	</script> 
</head>
<body>
	<complat:tree unique="123"  treeurl="../../column.do?dealMethod=getTree" pageurl="column.do?dealMethod=&nodeId=0&operationType=column"/> 
</body>
</html>