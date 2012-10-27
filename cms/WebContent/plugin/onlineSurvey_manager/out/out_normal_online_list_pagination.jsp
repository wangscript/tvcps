<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@taglib uri="/WEB-INF/tld/complat.tld" prefix="complat"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		
		<!-- 
		<link href='images/commentStyle.css' rel='stylesheet' type='text/css' />
		<link rel="stylesheet" type="text/css" href="<c:url value="/css/style.css"/>" >
		<script type="text/javascript" src="<c:url value='/script/jquery-1.2.6.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/script/global.js'/>" ></script>
		<link rel="stylesheet" type="text/css"	href="<c:url value='/css/ajaxpagination.css'/>">
		<script type="text/javascript"src="<c:url value='/script/jquery.pagination.js'/>"></script>
		-->
		<link rel="stylesheet" type="text/css" href="css/style.css"/>
		<script type="text/javascript" src="script/jquery-1.2.6.js"></script>
		<script type="text/javascript" src="script/global.js" ></script>
		<link rel="stylesheet" type="text/css"	href="css/ajaxpagination.css"/>
		<script type="text/javascript"src="script/jquery.pagination.js"></script>
		<script type="text/javascript">
			function getJsp(content){
				document.getElementById("jsp").innerHTML = "<object type='text/x-scriptlet'  data='"+content+"' width='100%' height='100%'></object>"; 
			}
		</script>
	</head> 
<body>
<form action="outOnlineSurvery.do"	name="onlineSurveyContentForm" id="onlineSurveyContentForm" method="post">
	<input type="hidden" name="dealMethod" value="getNormalPagination"/>
	<div id="jsp">
		<div id="qq" >
			${requestScope.content}
		</div>
		<complat:ajaxpage page="${onlineSurveyContentForm.pagination}" pageId="qq"	form="onlineSurveyContentForm"	action="outOnlineSurvery.do?dealMethod=getNormalPagination" />
	</div>
	<!-- 
	<IE:Download ID="oDownload" STYLE="behavior:url(#default#download)" />
	 -->
</form>
</body>
</html>