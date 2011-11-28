<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<%@ include file="/common/message.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>保姆推荐列表</title>
<script type="text/javascript" language="JavaScript">
	/**
	* 查询
	*/
	function queryServicePerson(){
		var servicePersonForm = document.getElementById("servicePersonForm");
		servicePersonForm.submit(); 
	}

	/**
	* 添加
	*/
	function detailServicePerson(id){		
	    if(id != null && id != ""){
	    	var servicePersonForm = document.getElementById("servicePersonForm");
	    	$("#strChecked").val(id);
	    	servicePersonForm.action = "<c:url value='/servicePerson/findServicePersonById.shtml'/>";
	    	servicePersonForm.submit();
	    }else{		
	  		window.location.href="<c:url value='/module/serviceperson/servicePerson_detail.jsp'/>";
	    }   	   
	}

	/**
	* 删除
	*/
	function deleteServicePerson(){
		 var strChecked = new Array();
		 var checkeds = document.getElementsByName("checkbox");
		 var fileIds = document.getElementsByName("servicePersonId");
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
			 var servicePersonForm = document.getElementById("servicePersonForm");
			 servicePersonForm.action = "<c:url value='/servicePerson/deleteServicePersonByIds.shtml'/>";
			 servicePersonForm.submit();
		 }else{
			 return false;	 
		}			   
	}



	
</script>
</head>

<body>
<form id="servicePersonForm" action="<c:url value="/servicePerson/queryServicePerson.shtml"/>" method="post">
	<input type="hidden" name="strChecked" id="strChecked" />
	<div class="currentLocationStyle"><img src="<c:url value='/images/common/currentLocation.png'/>"/>当前位置>>
		<a href="<c:url value='/servicePerson/queryServicePerson.shtml'/>"><span>家政服务人员管理</span></a>
	</div>
	<div class="findText">
		<li><span>家政服务人员名称：</span><input type="text" name="servicePersonEntity.servicePersonName" id="servicePersonName" value="${servicePersonEntity.servicePersonName}"></input></li>
	</div>	
	<div class="buttonGeneralStyle">
		<span><input type="button"  class="buttonStyle" value="查询" onclick="queryServicePerson()" /></span>
		<span><input type="button"  class="buttonStyle" value="增加" onclick="detailServicePerson()"/></span>		
		<span><input type="button"  class="buttonStyle" value="删除" onclick="deleteServicePerson()"/></span>		
	</div>
	
	<table class="tableStyle">
	  <tr  class="firstTr">
	  	<th class="firstTd"><input type="checkbox" name="allChecked" id="checkbox" onclick="checkedAll(this.checked,'servicePersonId')"/></th>
	    <th>服务人员名字</th>
	    <th>联系方式</th>
	    <th>人员状态</th>
	    <th>被推荐次数</th>
	    <th>被否定次数</th>
	    <th>详细信息</th>
	  </tr>
	  <c:forEach items="${pagination.data}" var="servicePersonEntity" >
	  <tr>
	    <td class="firstTd"><input type="checkbox" name="servicePersonEntity.servicePersonId" id="servicePersonId" value="${servicePersonEntity.servicePersonId}" /></td>
	    <td><a href="#" onclick="detailServicePerson('${servicePersonEntity.servicePersonId}')">${servicePersonEntity.servicePersonName}</a></td>
	    <td>${servicePersonEntity.contactMethod}</td>
	    <td>${servicePersonEntity.personStatusEntity.serviceStatusId}</td>
	    <td></td>
	    <td></td>
	    <td><input type="button" onclick="" value="详细信息"/></td>
	  </tr>
	  </c:forEach>
	</table>
	
	<house:page  pagination="${pagination}" formId="servicePersonForm"   pageAction="/servicePerson/queryServicePerson.shtml"/>
</form>

</body>
</html>
