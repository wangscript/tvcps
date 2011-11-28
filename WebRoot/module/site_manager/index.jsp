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

		parent.changeFrameUrl("rightFrame", "site.do?dealMethod=&"+getUrlSuffixRandom());
		
		function treeonclick() {
			parent.changeFrameUrl("rightFrame", "site.do?dealMethod=&"+getUrlSuffixRandom());	
		}
		
	</script>
</head>
<body>
	<!--  
		<complat:tree unique="222" classname="Site" treeurl="../../site.do?dealMethod=getTree" pageurl="site.do?dealMethod="/>
		<complat:tree unique="222"  images="../../images/" pageurl="site.do?dealMethod=" xmlpath="site.xml" />
	 -->

	<a onclick="treeonclick()" style="CURSOR: hand;font-size: 12px;margin-left:15px; ">网站管理</a>
</body>
</html>  