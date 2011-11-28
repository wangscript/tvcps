<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<%@ include file="/common/message.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>小区列表</title>
<script type="text/javascript" language="JavaScript">
	/**
	* 查询
	*/
	function queryHouseKeepType(){
		var houseKeepTypeForm = document.getElementById("houseKeepTypeForm");
		houseKeepTypeForm.submit(); 
	}

	/**
	* 添加
	*/
	function detailHouseKeepType(id){
	    if(id != null && id != ""){
	    	var houseKeepTypeForm = document.getElementById("houseKeepTypeForm");
	    	$("#strChecked").val(id);
	    	houseKeepTypeForm.action = "<c:url value='/houseKeepType/findHouseKeepTypeById.shtml'/>";
	    	houseKeepTypeForm.submit();
	    }else{		   
	  		window.location.href="<c:url value='/module/systemConfig/housekeeptype/housekeeptype_detail.jsp'/>";
	    }   	   
	}

	/**
	* 删除
	*/
	function deleteHouseKeepType(){
		 var strChecked = new Array();
		 var checkeds = document.getElementsByName("checkbox");
		 var fileIds = document.getElementsByName("houseKeepTypeId");
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
			 var houseKeepTypeForm = document.getElementById("houseKeepTypeForm");
			 houseKeepTypeForm.action = "<c:url value='/houseKeepType/deleteHouseKeepTypeByIds.shtml'/>";
			 houseKeepTypeForm.submit();
		 }else{
			 return false;	 
		}			   
	}



	
</script>
</head>

<body>
<form id="houseKeepTypeForm" action="<c:url value="/houseKeepType/queryHouseKeepType.shtml"/>" method="post">
	<input type="hidden" name="strChecked" id="strChecked" />
	<div class="currentLocationStyle"><img src="<c:url value='/images/common/currentLocation.png'/>"/>当前位置>>
		<a href="<c:url value='/houseKeepType/queryHouseKeepType.shtml'/>"><span>家政服务类型管理</span></a>
	</div>
	<div class="buttonGeneralStyle">
		<span><input type="button" class="buttonStyle" value="查询" onclick="queryHouseKeepType()" /></span>
		<span><input type="button" class="buttonStyle" value="增加" onclick="detailHouseKeepType()"/></span>		
		<span><input type="button" class="buttonStyle" value="删除" onclick="deleteHouseKeepType()"/></span>		
	</div>
	
	<table class="tableStyle">
	  <tr  class="firstTr">
	  	<th class="firstTd"><input type="checkbox" name="allChecked" id="checkbox" onclick="checkedAll(this.checked,'houseKeepTypeId')"/></th>
	    <th>类型名称</th>
	    <th>创建时间</th>
	  </tr>
	  <c:forEach items="${pagination.data}" var="houseKeepTypeEntity" >
	  <tr>
	    <td class="firstTd"><input type="checkbox" name="houseKeepTypeEntity.houseKeepTypeId" id="houseKeepTypeId" value="${houseKeepTypeEntity.houseKeepTypeId}" /></td>
	    <td><a href="#" onclick="detailHouseKeepType('${houseKeepTypeEntity.houseKeepTypeId}')">${houseKeepTypeEntity.typeName}</a></td>
	    <td>	    	
	    	<fmt:formatDate value="${houseKeepTypeEntity.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>	    	
	    </td>
	  </tr>
	  </c:forEach>
	</table>
	
	<house:page  pagination="${pagination}" formId="houseKeepTypeForm"   pageAction="/houseKeepType/queryHouseKeepType.shtml"/>
</form>

</body>
</html>
