<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>用户管理</title>
<script>
	$(document).ready(function() {
		if(document.getElementById("u1_0_checkbox")){			
			document.getElementById("u1_0_checkbox").disabled = true;
		}		
		var userIds = "${userForm.userIds}"
		var strUserIds = userIds.split(",");
		for(var i = 0 ; i < strUserIds.length ; i++){
			if(document.getElementById(strUserIds[i]+"_0_checkbox")){			
				document.getElementById(strUserIds[i]+"_0_checkbox").disabled = true;
			}
		}
		var dealMethod = $("#dealMethod").val();		
		if(dealMethod == "delete"){			 
			top.document.getElementById("rightFrame").src="<c:url value='/user.do?dealMethod=&"+getUrlSuffixRandom()+"'/>";	
	    	top.reloadAccordion("/${appName}/module/user_manager/user/index.jsp?"+getUrlSuffixRandom()); 	 			 		  	
		}
	});
	function button_add_onclick(ee){	
		win = showWindow("newUser","新增用户","user.do?dealMethod=showDetail&nodeId="+$("#nodeId").val(),0, 0,850,260);	 
	}
	
	function button_delete_onclick(ee){	
		var ids = document.getElementById("xx").value;
		if(ids == "" || ids == null){
			alert("请至少选择一条记录操作！");
			return false;
		}
		if(confirm("你确定要删除吗？")) {
			document.getElementById("strid").value = document.getElementById("xx").value;
			document.all("dealMethod").value="delete";				
		    document.forms[0].submit(); 	
		 //   parent.reloadAccordion("/${appName}/module/user_manager/user/index.jsp");
		} else { 
		 		return false;
		}
	}	
	function userdetail(id){	
		if(id == "u1") {
			alert("不能对超级管理员操作");
			return false;
		}

		win = showWindow("userdetail","用户信息","user.do?dealMethod=detail&id="+id+"",0, 0,850,260);	 
	}
	//设置角色权限
	function setRole(id){
		if(id == "u1") {
			alert("不能对超级管理员操作");
			return false;
		}
		var userIds = "${userForm.userIds}"
		var strUserIds = userIds.split(",");
		for(var i = 0 ; i < strUserIds.length ; i++){
			if(strUserIds[i] == id){			
				alert("不能对网站管理员操作!");
				return false;
			}
		}
		//需要增加已选择的角色
		win = showWindow("setRole","用户角色权限设置","user.do?dealMethod=findRole&id="+id+"",200, 50,600,480);
	}
	//设置栏目权限
	function setColumn(id){
		if(id == "u1") {
			alert("不能对超级管理员操作");
			return false;
		}
		var userIds = "${userForm.userIds}";
			var strUserIds = userIds.split(",");
			for(var i = 0 ; i < strUserIds.length ; i++){
				if(strUserIds[i] == id){			
					alert("不能对网站管理员操作!");
					return false;
				}
			}
		win = showWindow("setColumn","用户栏目权限设置","user.do?dealMethod=findColumn&id="+id+"&siteId=",200, 50,650,500);
	}
	function closeNewChild() {
		closeWindow(win);
	}

</script>
</head>
<body>
	<form id="userForm" action="<c:url value="/user.do"/>" method="post" name="userForm">	
	<div class="currLocation">用户管理<complat:guide className="Organization" nodeId="${userForm.nodeId}" sign="→ " ></complat:guide>
	</div>

		<input type="hidden" name="dealMethod" id="dealMethod" value="${userForm.dealMethod}"/>
		<input type="hidden" name="ids" id="strid"/>
		<input type="hidden" name="nodeId" id="nodeId" value="${userForm.nodeId}"/> 
		<complat:button name="button"  buttonlist="add|0|delete" buttonalign="left" ></complat:button>
		<complat:grid ids="xx" width="*,*,*,*,*,100,100" head="登录名,姓名,所属机构,职务,办公电话,角色权限,栏目权限"  element="[1,link,onclick,userdetail][6,button,onclick,setRole,角色权限][7,button,onclick,setColumn,栏目权限]"  
			page="${userForm.pagination}" form="userForm" action="user.do"/>
	</form>
</body>
</html>
