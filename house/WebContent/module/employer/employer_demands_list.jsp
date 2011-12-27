<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<%@ include file="/common/message.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>雇主需求管理</title>
<script type="text/javascript" language="JavaScript">
    function queryEmployerDemands(){
        var employerForm = document.getElementById("employerDemandsForm");
        employerForm.submit(); 
    }

    function detailEmployerDemands(id){
		var employerDemandsForm = document.getElementById("employerDemandsForm");
	    if(id != null && id != ""){
	    	$("#strChecked").val(id);
	    	employerDemandsForm.action = "<c:url value='/employerDemands/findEmployerDemandsById.shtml'/>";
	    	employerDemandsForm.submit();
	    }else{
	    	employerDemandsForm.action = "<c:url value='/employerDemands/detailEmployerDemands.shtml'/>";
	    	employerDemandsForm.submit();
	    }
	}

	function deleteEmployerDemands(){
		 var strChecked = new Array();
		 var checkeds = document.getElementsByName("checkbox");
		 var fileIds = document.getElementsByName("emDemandId");
		 for(var i = 0 ; i < fileIds.length ; i++){
   		    if(fileIds[i].checked){
   			    strChecked.push(fileIds[i].value);
   		    }
		 }
		 if(strChecked.length == 0){
			 alert("请选择要删除的记录！");
		  	 return false;
		 }
		 var hidden = document.getElementById("strChecked");
		 hidden.value = strChecked.valueOf();
		 if(confirm('是否删除？')){
			 var employerDemandsForm = document.getElementById("employerDemandsForm");
			 employerDemandsForm.action = "<c:url value='/employerDemands/deleteEmployerDemandsByIds.shtml'/>";
			 employerDemandsForm.submit();
		 }else{
			 return false;
		}
	}
</script>
</head>
<body>
<form id="employerDemandsForm" action="<c:url value="/employerDemands/queryEmployerDemands.shtml"/>" method="post">
    <input type="hidden" name="strChecked" id="strChecked" />
	<div class="currentLocationStyle"><img src="<c:url value='/images/common/currentLocation.png'/>"/>当前位置>>
		<span>雇主需求管理</span>
	</div>
	<div class="findText">
		<li><span>联系人：</span><input type="text" name="employer.linkMan" value="${employer.linkMan}"/></li>
	</div>	
	<div class="buttonGeneralStyle">
		<span><input type="button"  class="buttonStyle" value="查询" onclick="queryEmployerDemands()" /></span>
		<span><input type="button"  class="buttonStyle" value="增加" onclick="detailEmployerDemands()"/></span>	
		<span><input type="button"  class="buttonStyle" value="删除" onclick="deleteEmployerDemands()"/></span>
	</div>
	
	<table class="tableStyle">
	  <tr  class="firstTr">
	  	<th class="firstTd"><input type="checkbox" name="allChecked" id="checkbox" onclick="checkedAll(this.checked,'emDemandId')"/></th>
	    <th>联系人</th>
	    <th>小区</th>
	    <th>频次</th>
	    <th>时长</th>
	    <th>居家面积</th>
	    <th>进展情况状态</th>
	    <th>主需求说明</th>
	  </tr>
	  <c:forEach items="${pagination.data}" var="employerDemandsEntity" >
	  <tr>
	    <td class="firstTd"><input type="checkbox" id="emDemandId" value="${employerDemandsEntity.emDemandId}" /></td>
	    <td><a href="#" onclick="detailEmployerDemands('${employerDemandsEntity.emDemandId}')">${employerDemandsEntity.employer.linkMan}</a></td>
	    <td>${employerDemandsEntity.village.villageName}</td>
	    <td>${employerDemandsEntity.rate}</td>
	    <td>${employerDemandsEntity.hourLength}</td>
	    <td>${employerDemandsEntity.houseArea}</td>
	    <td>${employerDemandsEntity.evolveStatus}</td>
	    <td>${employerDemandsEntity.demandExplain}</td>
	  </tr>
	  </c:forEach>
	</table>
	<house:page  pagination="${pagination}" formId="employerDemandsForm" pageAction="/employerDemands/queryEmployerDemands.shtml"/>
</form>
</body>
</html>
