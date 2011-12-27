<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<%@ include file="/common/message.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="pragma" content="no-cache"/> 
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate"/> 
<meta http-equiv="expires" content="Wed, 26 Feb 1997 09:21:57 GMT"/>
<base target="_self"/>
<title>选择小区</title>
<script type="text/javascript" language="JavaScript">
    function queryVillage(){
        var villageForm = document.getElementById("villageForm");
        villageForm.submit(); 
    }
    function chooseVillage(){
		 var strChecked = new Array();
		 $("input[id='villageId']").each(function(i){
             if(this.checked)
            	 strChecked.push($(this).val());
		});
		 if(strChecked.length == 0){
			 alert("请选择一条记录！");
		  	 return false;
		 }else if(strChecked.length > 1){
			 alert("只能选择一条记录！");
		  	 return false;
		 }
		 var villageId = strChecked.valueOf();
		 var arr = new Array();
		 arr[0] = villageId;
		 arr[1] = $("#"+villageId).html();
		 window.returnValue = arr;
		 top.close();
	}
</script>
</head>
<body>
<form id="villageForm" action="<c:url value="/village/chooseVillage.shtml"/>" method="post">
	<input type="hidden" name="strChecked" id="strChecked" />
	<div class="currentLocationStyle"><img src="<c:url value='/images/common/currentLocation.png'/>"/>当前位置>>
		<a href="<c:url value='/village/queryVillage.shtml'/>"><span>小区列表</span></a>
	</div>
	<div class="findText">
		<li><span>小区名称：</span><input type="text" name="villageEntity.villageName" id="villageName" value="${villageEntity.villageName }"></input></li>
	</div>	
	<div class="buttonGeneralStyle">
		<span><input type="button"  class="buttonStyle" value="查询" onclick="queryVillage()" /></span>
		<span><input type="button"  class="buttonStyle" value="选择" onclick="chooseVillage()" /></span>
	</div>
	
	<table class="tableStyle">
	  <tr  class="firstTr">
	  	<th class="firstTd"><input type="checkbox" name="allChecked" id="checkbox" onclick="checkedAll(this.checked,'villageId')"/></th>
	    <th>小区名称</th>
	    <th>创建时间</th>
	  </tr>
	  <c:forEach items="${pagination.data}" var="villageEntity" >
	  <tr>
	    <td class="firstTd"><input type="checkbox" id="villageId" value="${villageEntity.villageId}" /></td>
	    <td id="${villageEntity.villageId}">${villageEntity.villageName}</td>
	    <td>	    	
	    	<fmt:formatDate value="${villageEntity.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
	    </td>
	  </tr>
	  </c:forEach>
	</table>
	
	<house:page  pagination="${pagination}" formId="villageForm"   pageAction="/village/chooseVillage.shtml"/>
</form>

</body>
</html>
