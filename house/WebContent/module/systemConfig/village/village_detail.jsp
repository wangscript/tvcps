<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<%@ include file="/common/message.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>小区维护</title>
<script type="text/javascript" language="JavaScript">

$(document).ready(function(){
    $("#villageForm").validate();
  });
</script>
</head>

<body>
<form id="villageForm" action="<c:url value="/village/saveVillage.shtml"/>" method="post">
	<div class="currentLocationStyle"><img src="<c:url value='/images/common/currentLocation.png'/>"/>当前位置>>
		<a href="<c:url value='/village/queryVillage.shtml'/>">小区管理</a>>>
		<span>增加小区</span>	
	</div>
	<input type="hidden" name="villageEntity.villageId" value="${villageEntity.villageId}"/>
	<div class="tableDiv" style="text-align: left">
		<table border="0" class="tableDetail">
			<tr>
				<td class="labelTd">小区名称：</td>
				<td class="textTd">
					<input type="text"  class="required"  name="villageEntity.villageName" id="villageName" value="${villageEntity.villageName }"/>
				</td>
			</tr>
			<tr>
				<td class="labelTd">小区简介：</td>
				<td class="textTd">
					<textarea cols="15" rows="15"  name="villageEntity.description" id="description">${villageEntity.description }</textarea>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					 <input type="submit" name="saveButton" id="saveButton" value="保存" onclick="saveVillage()" class="buttonStyle"  validatorType="disable"/> 
				     <input type="reset" name="resetButton" id="resetButton" value="重置" class="buttonStyle" />
				</td>
			</tr>
		</table>
	</div>
	
	
	
</form>

</body>
</html>
