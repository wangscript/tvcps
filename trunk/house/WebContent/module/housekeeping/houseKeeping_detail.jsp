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
    $("#adminUserForm").validate();
  });
</script>
</head>

<body>
<form id="houseKeepingEntityForm" action="<c:url value="/houseKeeping/saveHouseKeeping.shtml"/>" method="post">
	<div class="currentLocationStyle"><img src="<c:url value='/images/common/currentLocation.png'/>"/>当前位置>>
		<a href="<c:url value='/houseKeeping/queryHouseKeeping.shtml'/>">家政公司管理</a>>>
		<span>增加公司</span>	
	</div>
	<input type="hidden" name="houseKeepingEntity.houseKeepingId" value="${houseKeepingEntity.houseKeepingId}"/>
	<div class="tableDiv" style="text-align: left">
		<table border="0" class="tableDetail">
			<tr>
				<td class="labelTd">家政公司名称：</td>
				<td class="textTd">
					<input type="text"  class="required"  name="houseKeepingEntity.companyName" id="companyName" value="${houseKeepingEntity.companyName}"/>
				</td>
			</tr>
			<tr>	
				<td class="labelTd">家政公司电话：</td>
				<td class="textTd">
					<input  type="text"   class="required"  name="houseKeepingEntity.phone" id="phone" value="${houseKeepingEntity.phone}"></input>
				</td>
			</tr>
			<tr>
				<td class="labelTd">推荐价格：</td>
				<td class="textTd">
					<input type="text"  class="required"   name="houseKeepingEntity.price" id="price" value= "${houseKeepingEntity.price}" ></input>
				</td>
			</tr>
			<tr>	
				<td class="labelTd">公司联系人：</td>
				<td class="textTd">
					<input type="text"  name="houseKeepingEntity.connectPerson" id="connectPerson" value ="${houseKeepingEntity.connectPerson}" ></input>
				</td>
			</tr>
			<tr>
			<td class="labelTd">公司网址：</td>
				<td class="textTd">
					<input type="text" name="houseKeepingEntity.siteUrl" id="siteUrl"  value = "${houseKeepingEntity.siteUrl}"></input>
				</td>
			</tr>
			<tr>	
				<td class="labelTd">公司地址：</td>
				<td class="textTd">
					<input type="text" name="houseKeepingEntity.address" id="address" value = "${houseKeepingEntity.address}"></input>
				</td>
			</tr>
			<tr>
				<td class="labelTd">公司邮箱：</td>
				<td class="textTd">
					<input type="text"  name="houseKeepingEntity.mail" id="mail" value = "${houseKeepingEntity.mail}"></input>
				</td>
			</tr>
			<tr>
				<td class="labelTd">信息来源：</td>
				<td class="textTd">
					<input type="text"  name="houseKeepingEntity.source" id="source"  value = "${houseKeepingEntity.source}"></input>
				</td>
			</tr>
			<tr>
				<td class="labelTd">备注：</td>
				<td class="textTd">
					<textarea  rows="6"  name="houseKeepingEntity.text1" id="text1">${houseKeepingEntity.source}</textarea>
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
