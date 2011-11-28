<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<%@ include file="/common/message.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>家政公司列表</title>
<script type="text/javascript" language="JavaScript">
	/**
	* 查询
	*/
	function queryHouseKeeping(){
		var houseKeepingForm = document.getElementById("houseKeepingForm");
		houseKeepingForm.submit(); 
	}

	/**
	* 添加
	*/
	function detailHouseKeeping(id){		
	    if(id != null && id != ""){
	    	var houseKeepingForm = document.getElementById("houseKeepingForm");
	    	$("#strChecked").val(id);
	    	houseKeepingForm.action = "<c:url value='/houseKeeping/findHouseKeepingById.shtml'/>";
	    	houseKeepingForm.submit();
	    }else{		
	  		window.location.href="<c:url value='/module/housekeeping/houseKeeping_detail.jsp'/>";
	    }   	   
	}

	/**
	* 删除
	*/
	function deleteHouseKeeping(){
		 var strChecked = new Array();
		 var checkeds = document.getElementsByName("checkbox");
		 var fileIds = document.getElementsByName("houseKeepingId");
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
			 var houseKeepingForm = document.getElementById("houseKeepingForm");
			 houseKeepingForm.action = "<c:url value='/houseKeeping/deleteHouseKeepingByIds.shtml'/>";
			 houseKeepingForm.submit();
		 }else{
			 return false;	 
		}			   
	}



	
</script>
</head>

<body>
<form id="houseKeepingForm" action="<c:url value="/houseKeeping/queryHouseKeeping.shtml"/>" method="post">
	<input type="hidden" name="strChecked" id="strChecked" />
	<div class="currentLocationStyle"><img src="<c:url value='/images/common/currentLocation.png'/>"/>当前位置>>
		<a href="<c:url value='/houseKeeping/queryHouseKeeping.shtml'/>"><span>家政公司管理</span></a>
	</div>
	<div class="findText">
		<li><span>家政公司名称：</span><input type="text" name="houseKeepingEntity.companyName" id="user_name" value="${houseKeepingEntity.companyName}"></input></li>
	</div>	
	<div class="buttonGeneralStyle">
		<span><input type="button"  class="buttonStyle" value="查询" onclick="queryHouseKeeping()" /></span>
		<span><input type="button"  class="buttonStyle" value="增加" onclick="detailHouseKeeping()"/></span>		
		<span><input type="button"  class="buttonStyle" value="删除" onclick="deleteHouseKeeping()"/></span>		
	</div>
	
	<table class="tableStyle">
	  <tr  class="firstTr">
	  	<th class="firstTd"><input type="checkbox" name="allChecked" id="checkbox" onclick="checkedAll(this.checked,'houseKeepingId')"/></th>
	    <th>公司名称</th>
	    <th>公司电话</th>
	    <th>联系人</th>
	  </tr>
	  <c:forEach items="${pagination.data}" var="houseKeepingEntity" >
	  <tr>
	    <td class="firstTd"><input type="checkbox" name="houseKeepingEntity.houseKeepingId" id="houseKeepingId" value="${houseKeepingEntity.houseKeepingId}" /></td>
	    <td><a href="#" onclick="detailHouseKeeping('${houseKeepingEntity.houseKeepingId}')">${houseKeepingEntity.companyName}</a></td>
	    <td>${houseKeepingEntity.phone}</td>
	    <td>${houseKeepingEntity.connectPerson}</td>
	  </tr>
	  </c:forEach>
	</table>
	
	<house:page  pagination="${pagination}" formId="houseKeepingForm"   pageAction="/houseKeeping/queryHouseKeeping.shtml"/>
</form>

</body>
</html>
