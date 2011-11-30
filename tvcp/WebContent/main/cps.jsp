<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>	
	<%@include file="/templates/headers/header.jsp"%>
	<script>
	$(document).ready(
			function(){			
				document.loginbean.submit();				
			}
		);
	</script>
</head>
<body>
<form name="loginbean" id="loginbean" method="post" action="<c:url value='/loginaction.do'/>">
<input type="hidden" name="name" id="name" value="<%=request.getParameter("name") %>"/> 
<input type="hidden" name="password" id="password" value="<%=request.getParameter("password") %>" /> 
</form>

</body>
</html>