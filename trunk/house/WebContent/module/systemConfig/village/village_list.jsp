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
	function queryVillage(){
		var villageForm = document.getElementById("villageForm");
		villageForm.submit(); 
	}

	/**
	* 添加
	*/
	function detailVillage(id){
	    if(id != null && id != ""){
	    	var villageForm = document.getElementById("villageForm");
	    	$("#strChecked").val(id);
			villageForm.action = "<c:url value='/village/findVillageById.shtml'/>";
			villageForm.submit();
	    }else{		   
	  		window.location.href="<c:url value='/module/systemConfig/village/village_detail.jsp'/>";
	    }   	   
	}

	/**
	* 删除
	*/
	function deleteVillage(){
		 var strChecked = new Array();
		 var checkeds = document.getElementsByName("checkbox");
		 var fileIds = document.getElementsByName("villageId");
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
			 var villageForm = document.getElementById("villageForm");
			 villageForm.action = "<c:url value='/village/deleteVillageByIds.shtml'/>";
			 villageForm.submit();
		 }else{
			 return false;	 
		}			   
	}



	
</script>
</head>

<body>
<form id="villageForm" action="<c:url value="/village/queryVillage.shtml"/>" method="post">
	<input type="hidden" name="strChecked" id="strChecked" />
	<div class="currentLocationStyle"><img src="<c:url value='/images/common/currentLocation.png'/>"/>当前位置>>
		<a href="<c:url value='/village/queryVillage.shtml'/>"><span>小区管理列表</span></a>
	</div>
	<div class="findText">
		<li><span>小区名称：</span><input type="text" name="villageEntity.villageName" id="villageName" value="${villageEntity.villageName }"></input></li>
	</div>	
	<div class="buttonGeneralStyle">
		<span><input type="button" name="queryVillages" id="queryVillages" class="buttonStyle" value="查询" onclick="queryVillage()" /></span>
		<span><input type="button" name="addVillage" id="addVillage" class="buttonStyle" value="增加" onclick="detailVillage()"/></span>		
		<span><input type="button" name="deleteVillages" id="deleteVillages" class="buttonStyle"  value="删除" onclick="deleteVillage()"/></span>		
	</div>
	
	<table class="tableStyle">
	  <tr  class="firstTr">
	  	<th class="firstTd"><input type="checkbox" name="allChecked" id="checkbox" onclick="checkedAll(this.checked,'villageId')"/></th>
	    <th>小区名称</th>
	    <th>创建时间</th>
	  </tr>
	  <c:forEach items="${pagination.data}" var="villageEntity" >
	  <tr>
	    <td class="firstTd"><input type="checkbox" name="villageId" id="villageId" value="${villageEntity.villageId}" /></td>
	    <td><a href="#" onclick="detailVillage('${villageEntity.villageId}')">${villageEntity.villageName}</a></td>
	    <td>	    	
	    	<fmt:formatDate value="${villageEntity.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>	    	
	    </td>
	  </tr>
	  </c:forEach>
	</table>
	
	<house:page  pagination="${pagination}" formId="villageForm"   pageAction="/village/queryVillage.shtml"/>
</form>

</body>
</html>
