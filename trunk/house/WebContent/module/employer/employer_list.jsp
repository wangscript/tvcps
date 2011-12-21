<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<%@ include file="/common/message.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>雇主管理</title>
<script type="text/javascript" language="JavaScript">
    function queryEmployer(){
        var employerForm = document.getElementById("employerForm");
        employerForm.submit(); 
    }

    function queryEmployerDemands(linkMan){
        var employerForm = document.getElementById("employerForm");
        $("#linkMan").val(linkMan);
        employerForm.action = "<c:url value='/employerDemands/queryEmployerDemands.shtml'/>";
        employerForm.submit(); 
    }
</script>
</head>
<body>
<form id="employerForm" action="<c:url value="/employer/queryEmployer.shtml"/>" method="post">
	<div class="currentLocationStyle"><img src="<c:url value='/images/common/currentLocation.png'/>"/>当前位置>>
		<span>雇主管理</span>
	</div>
	<div class="findText">
		<li><span>联系人：</span><input type="text" id="linkMan" name="employer.linkMan" value="${employer.linkMan}"/></li>
	</div>	
	<div class="buttonGeneralStyle">
		<span><input type="button"  class="buttonStyle" value="查询" onclick="queryEmployer()" /></span>
	</div>
	
	<table class="tableStyle">
	  <tr  class="firstTr">
	  	<th class="firstTd"><input type="checkbox" name="allChecked" id="checkbox" onclick="checkedAll(this.checked,'employerId')"/></th>
	    <th>用户名</th>
	    <th>密码</th>
	    <th>联系电话</th>
	    <th>联系人</th>
	  </tr>
	  <c:forEach items="${pagination.data}" var="employerEntity" >
	  <tr>
	    <td class="firstTd"><input type="checkbox" name="employerEntity.employerId" id="employerId" value="${employerEntity.employerId}" /></td>
	    <td>${employerEntity.loginName}</td>
	    <td>${employerEntity.passWord}</td>
	    <td>${employerEntity.tel}</td>
	    <td>${employerEntity.linkMan}</td>
	    <td><input type="button" onclick="queryEmployerDemands('${employerEntity.linkMan}')" value="需求信息"/></td>
	  </tr>
	  </c:forEach>
	</table>
	<house:page  pagination="${pagination}" formId="employerForm" pageAction="/employer/queryEmployer.shtml"/>
</form>
</body>
</html>
