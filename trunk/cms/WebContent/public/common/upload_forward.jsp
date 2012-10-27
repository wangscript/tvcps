<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">	
<title></title>
<title>Insert title here</title>
<script type="text/javascript">
	var action = "<%=request.getParameter("action")%>";
	var dealMethod = "<%=request.getParameter("dealMethod")%>";
	var nodeId = "<%=request.getParameter("nodeId")%>";
	var nodeName = "<%=new String(request.getParameter("nodeName").getBytes("ISO-8859-1"),"utf-8")%>";
	var url = "../../" + action + "?dealMethod=" + dealMethod
					 + "&nodeId=" + nodeId
					 + "&nodeName=" + nodeName;
	window.onload = function() {
		window.location.href = url;
	};	
</script>
</head>
<body>
</body> 
</html>