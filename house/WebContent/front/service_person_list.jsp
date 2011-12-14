<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>家政服务人员列表</title>
</head>
<body>
    <table width="80%" align="center" style="margin:20px" border="1">
        <tr>
            <td>钟点工名字</td>
            <td>服务人员联系方式</td>
            <td>服务人员年龄</td>
            <td>籍贯地址</td>
            <td>备注</td>
        </tr>
        <c:forEach var="person" items="${servicePersonList}">
        <tr>
            <td>${person.servicePersonName }</td>
            <td>${person.contactMethod }</td>
            <td>${person.age }</td>
            <td>${person.nativePlace }</td>
            <td>${person.comment1}</td>
        </tr>
        </c:forEach>
    </table>
</body>
</html>