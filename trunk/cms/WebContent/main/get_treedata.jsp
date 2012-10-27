<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@include file="/templates/headers/taglibs.jsp"%>
<%
	request.setAttribute("classname", request.getParameter("classname"));
	request.setAttribute("checkbox", request.getParameter("checkbox"));
	request.setAttribute("treeId", request.getParameter("treeId"));
%>
<complat:json 
       classname="${classname}" 
       checkbox="${checkbox}"
       attrnames="${json_attrnames}" 
       list="${json_list}" 
       treeid="${treeId}"/>