<%@ page contentType="text/html;charset=UTF-8"%><%@page import="com.baize.ccms.biz.usermanager.web.action.LoginAction"%><%
	//判断是否被踢
	String kick = (String)session.getAttribute(LoginAction.loginStatus);
	if(kick != null && kick != "" && kick.equals("1")) {
		session.invalidate();
		out.print("KICK"); 
	}  
%>