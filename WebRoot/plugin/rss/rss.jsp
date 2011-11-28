<%@page contentType="text/html;charset=utf-8"%>
<%
	//request.setAttribute("newSiteId", request.getParameter("siteId"));
	out.println("$.ajax({");
	out.println("url: \"/"+request.getParameter("appName")+"/rssOuter.do?dealMethod=getRssList&siteId="+request.getParameter("siteId")+"&appName="+request.getParameter("appName")+"\",");
	out.println("type: \"post\","); 
	out.println("success: function(msg) {");   
	out.println("document.getElementById(\"rss\").innerHTML = msg;");
	out.println("}");
	out.println("});");
%>	