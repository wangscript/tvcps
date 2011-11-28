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
			var url = "templateCategory.do?dealMethod=&nodeId="+node.id;
			if(node.id != 0) {
				url = "template.do?dealMethod=&nodeId="+node.id;
			}
			parent.changeFrameUrl("rightFrame", url);
		}
	</script>
</head>
<body>
		<complat:tree unique="345"  treeurl="../../templateCategory.do?dealMethod=getTree" />
</body>
</html> 