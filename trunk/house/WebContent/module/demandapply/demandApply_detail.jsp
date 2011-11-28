<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<%@ include file="/common/message.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>业务终点工需求申请</title>
<script type="text/javascript" language="JavaScript">

$(document).ready(function(){
    $("#demandApplyForm").validate();
  });
</script>
</head>

<body>
<form id="demandApplyForm" action="<c:url value="/demandApply/saveDemandApply.shtml"/>" method="post">
	<div class="currentLocationStyle"><img src="<c:url value='/images/common/currentLocation.png'/>"/>当前位置>>
		<a href="<c:url value='/demandApply/queryDemandApply.shtml'/>">业务钟点工需求申请</a>>>
		<span>增加钟点工需求</span>	
	</div>
	<input type="hidden" name="demandApplyEntity.demandApplyId" value="${demandApplyEntity.demandApplyId}"/>
	<div class="tableDiv" style="text-align: left">
		<table border="0" class="tableDetail">
			<tr>
				<td class="labelTd">联系人：</td>
				<td class="textTd">
					<input type="text"  class="required"  name="demandApplyEntity.linkman" id="linkman" value="${demandApplyEntity.linkman}"/>
				</td>
			</tr>
			<tr>
				<td class="labelTd">用户名：</td>
				<td class="textTd">
					<input type="text"  class="required"   name="demandApplyEntity.loginName" id="loginName" value= "${demandApplyEntity.loginName}" ></input>
				</td>
			</tr>
			<tr>	
				<td class="labelTd">密码：</td>
				<td class="textTd">
					<input  type="text"   class="required"  name="demandApplyEntity.passWord" id="passWord" value="${demandApplyEntity.passWord}"></input>
				</td>
			</tr>
			<tr>
				<td class="labelTd">电话：</td>
				<td class="textTd">
					<input type="text"  class="required"   name="demandApplyEntity.tel" id="tel" value= "${demandApplyEntity.tel}" ></input>
				</td>
			</tr>
			<tr>
				<td class="labelTd">频次：</td>
				<td class="textTd">
					<input type="text"  class="required"   name="demandApplyEntity.rate" id="rate" value= "${demandApplyEntity.rate}" ></input>
				</td>
			</tr>
			<tr>
				<td class="labelTd">时长：</td>
				<td class="textTd">
					<input type="text"  class="required"   name="demandApplyEntity.hourLength" id="hourLength" value= "${demandApplyEntity.hourLength}" ></input>
				</td>
			</tr>
			<tr>
				<td class="labelTd">居家面积：</td>
				<td class="textTd">
					<input type="text"  class="required"   name="demandApplyEntity.houseArea" id="houseArea" value= "${demandApplyEntity.houseArea}" ></input>
				</td>
			</tr>
			<tr>
				<td class="labelTd">进展说明：</td>
				<td class="textTd">
					<input type="text"  class="required"   name="demandApplyEntity.evolveStatus" id="evolveStatus" value= "${demandApplyEntity.evolveStatus}" ></input>
				</td>
			</tr>
			<tr>
				<td class="labelTd">主需求说明：</td>
				<td class="textTd">
					<textarea  rows="6"  name="demandApplyEntity.demandExplain" id="demandExplain">${demandApplyEntity.demandExplain}</textarea>
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
