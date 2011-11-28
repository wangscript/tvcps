<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<%@ include file="/common/message.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>业务钟点工需求申请列表</title>
<script type="text/javascript" language="JavaScript">
	/**
	* 查询
	*/
	function queryDemandApply(){
		var demandApplyForm = document.getElementById("demandApplyForm");
		demandApplyForm.submit(); 
	}

	/**
	* 添加
	*/
	function detailDemandApply(id){		
	    if(id != null && id != ""){
	    	var demandApplyForm = document.getElementById("demandApplyForm");
	    	$("#strChecked").val(id);
	    	demandApplyForm.action = "<c:url value='/demandApply/findDemandApplyById.shtml'/>";
	    	demandApplyForm.submit();
	    }else{		
	  		window.location.href="<c:url value='/module/demandapply/demandApply_detail.jsp'/>";
	    }   	   
	}

	/**
	* 删除
	*/
	function deleteDemandApply(){
		 var strChecked = new Array();
		 var checkeds = document.getElementsByName("checkbox");
		 var fileIds = document.getElementsByName("demandApplyId");
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
			 var demandApplyForm = document.getElementById("demandApplyForm");
			 demandApplyForm.action = "<c:url value='/demandApply/deleteDemandApplyByIds.shtml'/>";
			 demandApplyForm.submit();
		 }else{
			 return false;	 
		}			   
	}



	
</script>
</head>

<body>
<form id="demandApplyForm" action="<c:url value="/demandApply/queryDemandApply.shtml"/>" method="post">
	<input type="hidden" name="strChecked" id="strChecked" />
	<div class="currentLocationStyle"><img src="<c:url value='/images/common/currentLocation.png'/>"/>当前位置>>
		<a href="<c:url value='/demandApply/queryDemandApply.shtml'/>"><span>钟点工需求申请管理</span></a>
	</div>
	<div class="findText">
		<li><span>联系人：</span><input type="text" name="demandApplyEntity.linkman" id="linkman" value="${demandApplyEntity.linkman}"></input></li>
	</div>	
	<div class="buttonGeneralStyle">
		<span><input type="button"  class="buttonStyle" value="查询" onclick="queryDemandApply()" /></span>
		<span><input type="button"  class="buttonStyle" value="增加" onclick="detailDemandApply()"/></span>		
		<span><input type="button"  class="buttonStyle" value="删除" onclick="deleteDemandApply()"/></span>		
	</div>
	
	<table class="tableStyle">
	  <tr  class="firstTr">
	  	<th class="firstTd"><input type="checkbox" name="allChecked" id="checkbox" onclick="checkedAll(this.checked,'demandApplyId')"/></th>
	    <th>联系人</th>
	    <th>电话</th>
	    <th>频次</th>
	    <th>时长</th>
	    <th>面积</th>
	    <th>进展信息</th>
	  </tr>
	  <c:forEach items="${pagination.data}" var="demandApplyEntity" >
	  <tr>
	    <td class="firstTd"><input type="checkbox" name="demandApplyEntity.demandApplyId" id="demandApplyId" value="${demandApplyEntity.demandApplyId}" /></td>
	    <td><a href="#" onclick="detailDemandApply('${demandApplyEntity.demandApplyId}')">${demandApplyEntity.linkman}</a></td>
	    <td>${demandApplyEntity.tel}</td>
	    <td>${demandApplyEntity.rate}</td>
	    <td>${demandApplyEntity.hourLength}</td>
	    <td>${demandApplyEntity.houseArea}</td>
	    <td><input type="button" onclick="" value="进展信息"/></td>
	  </tr>
	  </c:forEach>
	</table>
	<house:page  pagination="${pagination}" formId="demandApplyForm"   pageAction="/demandApply/queryDemandApply.shtml"/>
</form>

</body>
</html>
