<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<%@ include file="/common/message.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>服务人员状态维护</title>
<script type="text/javascript" language="JavaScript">

$(document).ready(function(){
    $("#personStatusForm").validate();
  });
</script>
</head>

<body>
<form id="personStatusForm" action="<c:url value="/personStatus/savePersonStatus.shtml"/>" method="post">
	<div class="currentLocationStyle"><img src="<c:url value='/images/common/currentLocation.png'/>"/>当前位置>>
		<a href="<c:url value='/personStatus/queryPersonStatus.shtml'/>">人员状态管理</a>>>
		<span>增加人员状态</span>	
	</div>
	<input type="hidden" name="personStatusEntity.serviceStatusId" value="${personStatusEntity.serviceStatusId}"/>
	<div class="tableDiv" style="text-align: left">
		<table border="0" class="tableDetail">
			<tr>
				<td class="labelTd">状态名称：</td>
				<td class="textTd">
					<input type="text"  class="required"  name="personStatusEntity.statusName" id="statusName" value="${personStatusEntity.statusName}"/>
				</td>
			</tr>
			<tr>
				<td class="labelTd">小区简介：</td>
				<td class="textTd">
					<textarea cols="15" rows="15"  name="personStatusEntity.description" id="description">${personStatusEntity.description }</textarea>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					 <input type="submit" value="保存" class="buttonStyle"  validatorType="disable"/> 
				     <input type="reset" name="resetButton" id="resetButton" value="重置" class="buttonStyle" />
				</td>
			</tr>
		</table>
	</div>
	
	
	
</form>

</body>
</html>
