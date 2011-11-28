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
	function queryAdminUser(){
		var adminUserForm = document.getElementById("adminUserForm");
		adminUserForm.submit(); 
	}

	/**
	* 添加
	*/
	function detailAdminUser(id){
	    if(id != null && id != ""){
	    	var adminuserForm = document.getElementById("adminUserForm");
	    	$("#strChecked").val(id);
			adminuserForm.action = "<c:url value='/adminuser/findAdminUserById.shtml?id'/>"+id;
			adminuserForm.submit();
	    }else{		
	  		window.location.href="<c:url value='/module/adminuser/adminuser_detail.jsp'/>";
	    }   	   
	}

	/**
	* 删除
	*/
	function deleteAdminUser(){
		 var strChecked = new Array();
		 var checkeds = document.getElementsByName("checkbox");
		 var fileIds = document.getElementsByName("adminuser_id");
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
			 var adminuserForm = document.getElementById("adminUserForm");
			 adminuserForm.action = "<c:url value='/adminuser/deleteAdminUserByIds.shtml'/>";
			 adminuserForm.submit();
		 }else{
			 return false;	 
		}			   
	}



	
</script>
</head>

<body>
<form id="adminUserForm" action="<c:url value="/adminuser/queryAdminUser.shtml"/>" method="post">
	<input type="hidden" name="strChecked" id="strChecked" />
	<div class="currentLocationStyle"><img src="<c:url value='/images/common/currentLocation.png'/>"/>当前位置>>
		<a href="<c:url value='/adminuser/queryAdminUser.shtml'/>"><span>操作员用户管理</span></a>
	</div>
	<div class="findText">
		<li><span>操作员名称：</span><input type="text" name="adminUserEntity.user_name" id="user_name" value="${adminUserEntity.user_name}"></input></li>
	</div>	
	<div class="buttonGeneralStyle">
		<span><input type="button"  class="buttonStyle" value="查询" onclick="queryAdminUser()" /></span>
		<span><input type="button"  class="buttonStyle" value="增加" onclick="detailAdminUser()"/></span>		
		<span><input type="button"  class="buttonStyle" value="删除" onclick="deleteAdminUser()"/></span>		
	</div>
	
	<table class="tableStyle">
	  <tr  class="firstTr">
	  	<th class="firstTd"><input type="checkbox" name="allChecked" id="checkbox" onclick="checkedAll(this.checked,'adminUserId')"/></th>
	    <th>登录名</th>
	    <th>操作员名称</th>
	     <th>创建时间</th>
	  </tr>
	  <c:forEach items="${pagination.data}" var="adminUserEntity" >
	  <tr>
	    <td class="firstTd"><input type="checkbox" name="adminUserEntity.adminuser_id" id="adminuser_id" value="${adminUserEntity.adminuser_id}" /></td>
	    <td><a href="#" onclick="detailAdminUser('${adminUserEntity.adminuser_id}')">${adminUserEntity.loginName}</a></td>
	    <td>${adminUserEntity.user_name}</td>
	    <td>	    	
	    	<fmt:formatDate value="${adminUserEntity.create_time}" pattern="yyyy-MM-dd HH:mm:ss"/>	    	
	    </td>
	  </tr>
	  </c:forEach>
	</table>
	
	<house:page  pagination="${pagination}" formId="adminUserForm"   pageAction="/adminuser/queryAdminUser.shtml"/>
</form>

</body>
</html>
