<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<%@ include file="/common/message.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>操作员用户维护</title>
<script type="text/javascript" language="JavaScript">

$(document).ready(function(){
    $("#adminUserForm").validate();
  });
</script>
</head>

<body>
<form id="adminUserForm" action="<c:url value="/adminuser/saveUser.shtml"/>" method="post">
	<div class="currentLocationStyle"><img src="<c:url value='/images/common/currentLocation.png'/>"/>当前位置>>
		<a href="<c:url value='/adminuser/queryAdminUser.shtml'/>">操作员用户管理</a>>>
		<span>增加操作员</span>	
	</div>
	<input type="hidden" name="adminUserEntity.adminuser_id" value="${villageEntity.adminuser_id}"/>
	<div class="tableDiv" style="text-align: left">
		<table border="0" class="tableDetail">
			<tr>
				<td class="labelTd">操作员登录名：</td>
				<td class="textTd">
					<input type="text"  class="required"  name="adminUserEntity.loginName" id="loginName" value="${adminUserEntity.loginName}"/>
				</td>
			</tr>
			<tr>	
				<td class="labelTd">密码：</td>
				<td class="textTd">
					<input  type="text"   class="required"  name="adminUserEntity.password" id="password" value="${adminUserEntity.password}"></input>
				</td>
			</tr>
			<tr>
				<td class="labelTd">操作员名称：</td>
				<td class="textTd">
					<input type="text"  class="required"   name="adminUserEntity.user_name" id="user_name" value= "${adminUserEntity.user_name}" ></input>
				</td>
			</tr>
			<tr>	
				<td class="labelTd">邮箱：</td>
				<td class="textTd">
					<input type="text"  name="adminUserEntity.email" id="email" value ="${adminUserEntity.email}" ></input>
				</td>
			</tr>
			<tr>
			<td class="labelTd">电话：</td>
				<td class="textTd">
					<input type="text" name="adminUserEntity.phone" id="phone"  value = "${adminUserEntity.phone}"></input>
				</td>
			</tr>
			<tr>	
				<td class="labelTd">移动电话：</td>
				<td class="textTd">
					<input type="text" name="adminUserEntity.mobile_phone" id="mobile_phone" value = "${adminUserEntity.mobile_phone}"></input>
				</td>
			</tr>
			<tr>
				<td class="labelTd">QQ：</td>
				<td class="textTd">
					<input type="text"  name="adminUserEntity.qq" id="qq" value = "${adminUserEntity.qq}"></input>
				</td>
			</tr>
			<tr>
				<td class="labelTd">MSN：</td>
				<td class="textTd">
					<input type="text"  name="adminUserEntity.msn" id="msn"  value = "${adminUserEntity.msn}"></input>
				</td>
			</tr>
			<tr>
				<td class="labelTd">地址：</td>
				<td class="textTd">
					<textarea  name="adminUserEntity.address" id="address">${adminUserEntity.address}</textarea>
				</td>
			</tr>
			<tr>
				<td class="labelTd">描述：</td>
				<td class="textTd">
					<textarea  rows="6"  name="adminUserEntity.desription" id="desription">${adminUserEntity.desription}</textarea>
				</td>
			</tr>
			
			<tr>
				<td colspan="2">
					 <input type="submit" name="saveButton" id="saveButton" value="保存"  class="buttonStyle"  validatorType="disable"/> 
				     <input type="reset" name="resetButton" id="resetButton" value="重置" class="buttonStyle" />
				</td>
			</tr>
		</table>
	</div>
	
	
	
</form>

</body>
</html>
