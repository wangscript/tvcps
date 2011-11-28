<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<%@ include file="/common/message.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>家政服务公司维护</title>
<script type="text/javascript" language="JavaScript">

$(document).ready(function(){
    $("#servicePersonForm").validate();
  });
</script>
</head>

<body>
<form id="servicePersonForm" action="<c:url value="/servicePerson/saveServicePerson.shtml"/>" method="post">
	<div class="currentLocationStyle"><img src="<c:url value='/images/common/currentLocation.png'/>"/>当前位置>>
		<a href="<c:url value='/servicePerson/queryServicePerson.shtml'/>">服务人员管理</a>>>
		<span>增加服务人员</span>	
	</div>
	<input type="hidden" name="servicePersonEntity.servicePersonId" value="${servicePersonEntity.servicePersonId}"/>
	<div class="tableDiv" style="text-align: left">
		<table border="0" class="tableDetail">
			<tr>
				<td class="labelTd">服务人员名称：</td>
				<td class="textTd">
					<input type="text"  class="required"  name="servicePersonEntity.servicePersonName" id="servicePersonName" value="${servicePersonEntity.servicePersonName}"/>
				</td>
			</tr>
			<tr>
				<td class="labelTd">服务人员年龄：</td>
				<td class="textTd">
					<input type="text"  class="required"   name="servicePersonEntity.age" id="age" value= "${servicePersonEntity.age}" ></input>
				</td>
			</tr>
			<tr>	
				<td class="labelTd">联系方式：</td>
				<td class="textTd">
					<input  type="text"   class="required"  name="servicePersonEntity.contactMethod" id="contactMethod" value="${servicePersonEntity.contactMethod}"></input>
				</td>
			</tr>
			<tr>
				<td class="labelTd">籍贯：</td>
				<td class="textTd">
					<input type="text"  class="required"   name="servicePersonEntity.nativePlace" id="nativePlace" value= "${servicePersonEntity.nativePlace}" ></input>
				</td>
			</tr>
			<tr>
				<td class="labelTd">备注：</td>
				<td class="textTd">
					<textarea  rows="6"  name="servicePersonEntity.comment1" id="comment1">${servicePersonEntity.comment1}</textarea>
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
