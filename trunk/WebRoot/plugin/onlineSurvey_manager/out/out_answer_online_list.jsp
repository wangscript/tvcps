<%@page contentType="text/html;charset=utf-8"%>
<%
	out.println("$.ajax({");
	out.println("url: \"/"+request.getParameter("appName")+"/outOnlineSurvery.do?dealMethod=getAnswerlList&appName="+request.getParameter("appName")+"&unit_unitId="+request.getParameter("unit_unitId")+"\",");
	out.println("type: \"post\",");
	out.println("success: function(msg) {"); 
	out.println("var unitId = msg.split(\"###\")[0].trim();");
	out.println("var html = msg.split(\"###\")[1];");
	out.println("document.getElementById(unitId).innerHTML = html;");
	out.println("}");
	out.println("});");

	out.println("function getJsp(content){");
	out.println("document.getElementById(\""+request.getParameter("unit_unitId")+"\").innerHTML = \"<object type='text/x-scriptlet'  data='\"+content+\"' width='100%' height='600px;'></object>\";"); 
	out.println("}");
%>