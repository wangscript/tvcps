 <%--
	功能：用于显示模板管理的树
	作者：郑荣华
 --%>
<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>模板管理树</title>
	<%@include file="/templates/headers/header.jsp"%>
	<link rel="stylesheet" type="text/css" href="<c:url value="/script/ext/resources/css/ext-all.css"/>"/>
	<script type="text/javascript" src="<c:url value="/script/ext/adapter/ext/ext-base.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/script/ext/ext-all-debug.js"/>"></script>
	<script type="text/javascript">
		function tree_onclick(node){
			var url = "templateCategory.do?dealMethod=&nodeId="+node.id+"&"+getUrlSuffixRandom();
			if(node.id != 0) {
				url = "template.do?dealMethod=&nodeId="+node.id+"&"+getUrlSuffixRandom();
			}
			parent.changeFrameUrl("rightFrame", url);
		}
	</script>
</head>
<body>
       <complat:tree unique="111"  treeurl="../../templateCategory.do?dealMethod=getTree" pageurl="templateCategory.do?dealMethod=&nodeId=0"/>
</body>
</html>