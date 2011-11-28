<%@page contentType="text/html;charset=utf-8"%>
<%
	out.println("$.ajax({");
	out.println("url: \"/"+request.getParameter("appName")+"/outOnlineSurvery.do?dealMethod=getAnswerDetail&unit_unitId="+request.getParameter("unit_unitId")+"&themeId="+request.getParameter("themeId")+"&display="+request.getParameter("display")+"\",");
	out.println("type: \"post\",");
	out.println("success: function(msg) {"); 
	out.println("var unitId = msg.split(\"###\")[0].trim();");
	out.println("var html = msg.split(\"###\")[1];");
	out.println("document.getElementById(unitId).innerHTML = html;");
	out.println("}");
	out.println("});");
%>