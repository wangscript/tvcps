<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>业主钟点工需求列表</title>
</head>
<body>
    <table width="80%" align="center" style="margin:20px" border="1">
        <tr>
            <td>联系人</td>
            <td>用户名</td>
            <td>联系电话</td>
            <td>频次</td>
            <td>时长(H/次)</td>
            <td>居家面积</td>
            <td>进展情况</td>
            <td>主要需求说明</td>
        </tr>
        <c:forEach var="demand" items="${demandApplyEntityList}">
        <tr>
            <td>${demand.linkman }</td>
            <td>${demand.loginName }</td>
            <td>${demand.tel }</td>
            <td>${demand.rate}</td>
            <td>${demand.hourLength }</td>
            <td>${demand.houseArea }</td>
            <td>${demand.evolveStatus }</td>
            <td>${demand.demandExplain }</td>
        </tr>
        </c:forEach>
    </table>
</body>
</html>