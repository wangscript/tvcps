<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<%@ include file="/common/message.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>雇员需求添加</title>
<script type="text/javascript" language="JavaScript">

    $(document).ready(function(){
        $("#employerDemandsForm").validate();
    });

    function showDialog(){
    	var returnValue = window.showModalDialog('<c:url value="/employer/chooseEmployer.shtml"/>','','dialogWidth=800px;dialogHeight=500px;status=no;help=no;scrollbars=no');
    	if(returnValue != null){
    		$("#employerId").val(returnValue[0]);
    		$("#linkMan").val(returnValue[1]);
    	}
    }

    function showVillageDialog(){
    	var returnValue = window.showModalDialog('<c:url value="/village/chooseVillage.shtml"/>','','dialogWidth=800px;dialogHeight=500px;status=no;help=no;scrollbars=no');
    	if(returnValue != null){
    		$("#villageId").val(returnValue[0]);
    		$("#villageName").val(returnValue[1]);
    	}
    }
</script>
</head>

<body>
<form id="employerDemandsForm" action="<c:url value="/employerDemands/addEmployerDemands.shtml"/>" method="post">
	<div class="currentLocationStyle"><img src="<c:url value='/images/common/currentLocation.png'/>"/>当前位置>>
		<a href="<c:url value='/employerDemands/queryEmployerDemands.shtml'/>">雇员需求管理</a>>>
		<span>增加雇员需求</span>	
	</div>
	<input type="hidden" name="employerDemandsEntity.emDemandId" value="${employerDemandsEntity.emDemandId}"/>
	<input type="hidden" name="employerDemandsEntity.employer.employerId" id="employerId" value="${employerDemandsEntity.employer.employerId}"/>
	<input type="hidden" name="employerDemandsEntity.village.villageId" id="villageId" value="${employerDemandsEntity.village.villageId}"/>
	<div class="tableDiv" style="text-align: left">
		<table border="0" class="tableDetail">
			<tr>
				<td class="labelTd">联系人：</td>
				<td class="textTd">
					<span>
					    <input type="text"  class="required" id="linkMan" name="employerDemandsEntity.employer.linkMan" value= "${employerDemandsEntity.employer.linkMan }" />
					    <input type="button" class="buttonStyle" value="选择联系人" onclick="showDialog()"/>
					</span>
				</td>
			</tr>
			<tr>
				<td class="labelTd">小区：</td>
				<td class="textTd">
					<input type="text" class="required" id="villageName" name="employerDemandsEntity.village.villageName" value= "${employerDemandsEntity.village.villageName}" />
					<input type="button" class="buttonStyle" value="选择小区" onclick="showVillageDialog()"/>
				</td>
			</tr>
			<tr>
				<td class="labelTd">频次：</td>
				<td class="textTd">
					<input type="text"  class="required"   name="employerDemandsEntity.rate" value= "${employerDemandsEntity.rate}" />
				</td>
			</tr>
			<tr>
				<td class="labelTd">时长：</td>
				<td class="textTd">
					<input type="text"  class="required"   name="employerDemandsEntity.hourLength" value= "${employerDemandsEntity.hourLength}" />
				</td>
			</tr>
			<tr>
				<td class="labelTd">居家面积：</td>
				<td class="textTd">
					<input type="text"  class="required"   name="employerDemandsEntity.houseArea" value= "${employerDemandsEntity.houseArea}" />
				</td>
			</tr>
			<tr>
				<td class="labelTd">进展说明：</td>
				<td class="textTd">
					<input type="text"  class="required"   name="employerDemandsEntity.evolveStatus" value= "${employerDemandsEntity.evolveStatus}" />
				</td>
			</tr>
			<tr>
				<td class="labelTd">主需求说明：</td>
				<td class="textTd">
					<textarea  rows="6"  name="employerDemandsEntity.demandExplain">${employerDemandsEntity.demandExplain}</textarea>
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
