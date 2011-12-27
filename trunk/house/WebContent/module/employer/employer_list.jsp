<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<%@ include file="/common/message.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>雇主管理</title>
<script type="text/javascript" language="JavaScript">
    $(document).ready(function(){
        var errorMessage = $("#errorMessage").val();
        if(errorMessage != null && errorMessage != ""){
			alert(errorMessage);
			return false;
        }
    });

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

	function detailEmployer(id){
		var employerForm = document.getElementById("employerForm");
	    if(id != null && id != ""){
	    	$("#strChecked").val(id);
	    	employerForm.action = "<c:url value='/employer/findEmployerById.shtml'/>";
	    	employerForm.submit();
	    }else{
	    	employerForm.action = "<c:url value='/employer/detailEmployer.shtml'/>";
	    	employerForm.submit();
	    }
	}

	function deleteEmployer(){
		 var strChecked = new Array();
		 var checkeds = document.getElementsByName("checkbox");
		 var fileIds = document.getElementsByName("employerId");
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
			 var employerForm = document.getElementById("employerForm");
			 employerForm.action = "<c:url value='/employer/deleteEmployerByIds.shtml'/>";
			 employerForm.submit();
		 }else{
			 return false;
		}
	}
</script>
</head>
<body>
<form id="employerForm" action="<c:url value="/employer/queryEmployer.shtml"/>" method="post">
	<input type="hidden" name="errorMessage" id="errorMessage" value="${errorMessage}"/>
	<input type="hidden" name="strChecked" id="strChecked" />
	<div class="currentLocationStyle"><img src="<c:url value='/images/common/currentLocation.png'/>"/>当前位置>>
		<span>雇主管理</span>
	</div>
	<div class="findText">
		<li><span>联系人：</span><input type="text" id="linkMan" name="employer.linkMan" value="${employer.linkMan}"/></li>
		<li><span><s:actionmessage/></span></li>
	</div>	
	<div class="buttonGeneralStyle">
		<span><input type="button"  class="buttonStyle" value="查询" onclick="queryEmployer()" /></span>
		<span><input type="button"  class="buttonStyle" value="增加" onclick="detailEmployer()"/></span>	
		<span><input type="button"  class="buttonStyle" value="删除" onclick="deleteEmployer()"/></span>
	</div>
	
	<table class="tableStyle">
	  <tr  class="firstTr">
	  	<th class="firstTd"><input type="checkbox" name="allChecked" id="checkbox" onclick="checkedAll(this.checked,'employerId')"/></th>
	    <th>用户名</th>
	    <th>密码</th>
	    <th>联系电话</th>
	    <th>联系人</th>
	  </tr>
	  <c:forEach items="${pagination.data}" var="employerEntity">
	  <tr>
	    <td class="firstTd"><input type="checkbox" id="employerId" value="${employerEntity.employerId}" /></td>
	    <td><a href="#" onclick="detailEmployer('${employerEntity.employerId}')">${employerEntity.loginName}</a></td>
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
