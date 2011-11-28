<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title></title>
	<%@include file="/templates/headers/header.jsp"%>
	<style type="text/css">
	li {
		cursor:pointer;
	}
	</style>
	<script type="text/javascript">
		//parent.changeFrameUrl("rightFrame","<c:url value="/publish.do?dealMethod=&"/>" + getUrlSuffixRandom());
		function jump(flag) {
			if (flag == 1) {
				parent.changeFrameUrl("rightFrame", "<c:url value="/module/publish_manager/column_tree.jsp?publishType=column&"/>" + getUrlSuffixRandom());
			} else if (flag == 2) {
				parent.changeFrameUrl("rightFrame","<c:url value="/module/publish_manager/column_tree.jsp?publishType=article&"/>" + getUrlSuffixRandom());
			} else if (flag == 3) {
				parent.changeFrameUrl("rightFrame","<c:url value="/build.do?dealMethod=&"/>" + getUrlSuffixRandom());
			} else if(flag == 4){
				parent.changeFrameUrl("rightFrame","<c:url value="/publish.do?dealMethod=&"/>" + getUrlSuffixRandom());
			} else if(flag == 6){
				parent.changeFrameUrl("rightFrame", "<c:url value="/module/publish_manager/column_tree.jsp?publishType=createIndex&"/>" + getUrlSuffixRandom());
			} else {
				parent.changeFrameUrl("rightFrame", "<c:url value="/module/publish_manager/column_tree.jsp?publishType=publishHome&"/>" + getUrlSuffixRandom());
			}
		}
	</script>
</head>
<body>
	<div>
		<ul>
			<li onclick="jump(5);">发布首页</li>
			<li onclick="jump(1);">发布栏目页</li>
			<li onclick="jump(2);">发布文章页</li>			 
			<li onclick="jump(3);">生成列表</li>			 
			<li onclick="jump(4);">发布列表</li>
			<li onclick="jump(6);">建立索引</li>
		</ul>
	</div>
</body>
</html>