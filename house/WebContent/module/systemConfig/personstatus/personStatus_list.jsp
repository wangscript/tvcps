<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<%@ include file="/common/message.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>服务人员状态列表</title>
<script type="text/javascript" language="JavaScript">
	/**
	* 查询
	*/
	function queryPersonStatus(){
		var personStatusForm = document.getElementById("personStatusForm");
		personStatusForm.submit(); 
	}

	/**
	* 添加
	*/
	function detailPersonStatus(id){
	    if(id != null && id != ""){
	    	var personStatusForm = document.getElementById("personStatusForm");
	    	$("#strChecked").val(id);
	    	personStatusForm.action = "<c:url value='/personStatus/findPersonStatusById.shtml?id='/>"+id;
			personStatusForm.submit();
	    }else{	
	  		window.location.href="<c:url value='/module/systemConfig/personstatus/personStatus_detail.jsp'/>";
	    }   	   
	}

	/**
	* 删除
	*/
	function deletePersonStatus(){
		 var strChecked = new Array();
		 var checkeds = document.getElementsByName("checkbox");
		 var fileIds = document.getElementsByName("serviceStatusId");
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
			 var personStatusForm = document.getElementById("personStatusForm");
			 personStatusForm.action = "<c:url value='/personStatus/deletePersonStatusByIds.shtml'/>";
			 personStatusForm.submit();
		 }else{
			 return false;	 
		}			   
	}



	
</script>
</head>

<body>
<form id="personStatusForm" action="<c:url value="/personStatus/queryPersonStatus.shtml"/>" method="post">
	<input type="hidden" name="strChecked" id="strChecked" />
	<div class="currentLocationStyle"><img src="<c:url value='/images/common/currentLocation.png'/>"/>当前位置>>
		<a href="<c:url value='/personStatus/queryPersonStatus.shtml'/>"><span>人员状态管理列表</span></a>
	</div>
	<div class="findText">
		<li><span>人员状态名称：</span><input type="text" name="personStatusEntity.statusName" id="statusName" value="${personStatusEntity.statusName}"></input></li>
	</div>	
	<div class="buttonGeneralStyle">
		<span><input type="button" class="buttonStyle" value="查询" onclick="queryPersonStatus()" /></span>
		<span><input type="button" class="buttonStyle" value="增加" onclick="detailPersonStatus()"/></span>	
		<span><input type="button" class="buttonStyle" value="删除" onclick="deletePersonStatus()"/></span>		
	</div>
	
	<table class="tableStyle">
	  <tr  class="firstTr">
	  	<th class="firstTd"><input type="checkbox" name="allChecked" id="checkbox" onclick="checkedAll(this.checked,'serviceStatusId')"/></th>
	    <th>服务人员状态名称</th>
	    <th>创建时间</th>
	  </tr>
	  <c:forEach items="${pagination.data}" var="personStatusEntity" >
	  <tr>
	    <td class="firstTd"><input type="checkbox" name="serviceStatusId" id="serviceStatusId" value="${personStatusEntity.serviceStatusId}" /></td>
	    <td><a href="#" onclick="detailPersonStatus(${personStatusEntity.statusName})">${personStatusEntity.statusName}</a></td>
	    <td><fmt:formatDate value="${personStatusEntity.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	  </tr>
	  </c:forEach>
	</table>
	
	<house:page  pagination="${pagination}" formId="formId"   pageAction="/personStatus/queryPersonStatus.shtml"/>
</form>

</body>
</html>
