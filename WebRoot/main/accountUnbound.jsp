<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.baize.ccms.biz.usermanager.web.action.LoginAction"%>
<%@page import="com.baize.ccms.sys.GlobalConfig"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%
	Map userMap = GlobalConfig.userSession;
    Map logMap = GlobalConfig.loginSession;
    Iterator itr = userMap.keySet().iterator();
    while(itr.hasNext()) {
    	String key = itr.next().toString();
    	String value = userMap.get(key).toString();
    	System.out.println("key==="+key);
    	if(value.equals(session.getId())) {
    		System.out.println("==session失效====");
    		// 移除用户session
       	 	GlobalConfig.userSession.remove(key);
    		GlobalConfig.ip.remove(key);
    		break;
    	}
    }
    // 移除登陆的session
    GlobalConfig.loginSession.remove(session.getId());
    session.invalidate();
%>
