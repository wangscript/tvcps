<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>
	<%@include file="/templates/headers/header.jsp"%>
<style type="text/css">
li {
	list-style-type:disc;
}
</style>
<script type="text/javascript">
	function showFormat() {
		parent.changeFrameUrl("rightFrame","<c:url value="/articleFormat.do?dealMethod=&"/>"+ getUrlSuffixRandom()); 
	}

	function showEnumeration() {
		parent.changeFrameUrl("rightFrame","<c:url value="/enumeration.do?dealMethod=&"/>"+ getUrlSuffixRandom());
	}
</script>
</head>
<body>
	<ul style="margin:10px;cursor:pointer;">
		<li onclick="showFormat()">格式管理</li>
		<li onclick="showEnumeration()">枚举类型</li>
	</ul>
</body>
</html>