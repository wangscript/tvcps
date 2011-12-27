<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<%@ include file="/common/message.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>雇员添加</title>
<script type="text/javascript" language="JavaScript">

$(document).ready(function(){
    $("#employerForm").validate();
  });
</script>
</head>

<body>
<form id="employerForm" action="<c:url value="/employer/addEmployer.shtml"/>" method="post">
	<div class="currentLocationStyle"><img src="<c:url value='/images/common/currentLocation.png'/>"/>当前位置>>
		<a href="<c:url value='/employer/queryEmployer.shtml'/>">雇员管理</a>>>
		<span>增加雇员</span>	
	</div>
	<input type="hidden" name="employer.employerId" value="${employer.employerId}"/>
	<div class="tableDiv" style="text-align: left">
		<table border="0" class="tableDetail">
			<tr>
				<td class="labelTd">用户名：</td>
				<td class="textTd">
					<input type="text" class="required"  name="employer.loginName" value="${employer.loginName}"/>
				</td>
			</tr>
			<tr>
				<td class="labelTd">密码：</td>
				<td class="textTd">
					<input type="password"  class="required" name="employer.passWord" value= "${employer.passWord}" />
				</td>
			</tr>
			<tr>
				<td class="labelTd">联系电话：</td>
				<td class="textTd">
					<input type="text"  class="required" name="employer.tel" value= "${employer.tel}" />
				</td>
			</tr>
			<tr>
				<td class="labelTd">联系人： </td>
				<td class="textTd">
					<input type="text"  class="required" name="employer.linkMan" value= "${employer.linkMan}"/>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					 <input type="submit" name="saveButton" id="saveButton" value="保存"  class="buttonStyle" validatorType="disable"/> 
				     <input type="reset" name="resetButton" id="resetButton" value="重置" class="buttonStyle" />
				</td>
			</tr>
		</table>
	</div>
</form>
</body>
</html>
