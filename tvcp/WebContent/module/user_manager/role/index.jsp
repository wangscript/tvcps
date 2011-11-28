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
	/**	function treeonclick(){				
			parent.changeFrameUrl("rightFrame", "role.do?dealMethod=&t="+ new Date());	
		}*/
			function tree_onclick(node){	
				parent.changeFrameUrl("rightFrame", "role.do?dealMethod=&treeId="+node.id+"&t="+ new Date());		
			}
		
	</script>
</head>
<body>
	<!-- <form id="roleForm"  name="roleForm">	
		<a onclick="treeonclick()" style="CURSOR: hand;font-size: 12px;margin-left:15px; " >${roleForm.siteName}</a>
	</form>
 -->
	<complat:tree unique="role"  treeurl="../../../role.do?dealMethod=getSiteTree" siteid="${siteId}"/> 
</body>
</html>