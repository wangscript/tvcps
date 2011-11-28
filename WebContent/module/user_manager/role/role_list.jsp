<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Expires" CONTENT="0"> 
<meta http-equiv="Cache-Control" CONTENT="no-cache"> 
<meta http-equiv="Pragma" CONTENT="no-cache"> 

<title>角色管理</title>
<script>	

	$(document).ready(function() {		
		var dealMethod = "${roleForm.dealMethod}";
		
		if(dealMethod == "delete"){					 		
			if(document.getElementById("use").value == "true"){
				alert("对不起，该角色已经被使用，您不能删除该角色！");			
			}
		//	top.document.getElementById("rightFrame").src="<c:url value='/role.do?dealMethod=&treeId=${roleForm.siteId}'/>";			
		}
		
		var roleIds = "${roleForm.roleIds}";
		if(roleIds != null && roleIds != ""){
			var strroleid  = roleIds.split(",");
			for(var i = 0 ; i < strroleid.length ; i++){
				if(document.getElementById(strroleid[i]+"_0_checkbox")){				
					document.getElementById(strroleid[i]+"_0_checkbox").disabled = true;
				}
			}
		}
	});
	function button_add_onclick(ee){	
		win = showWindow("newRole","新增角色","role.do?dealMethod=showDetail&siteId="+$("#siteId").val(),0,0,560,320);	 
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
		} else { 
 	 		return false;
 	 	}
	}
	/** 关闭窗口 */
	
	function closeNewChild(){
		closeWindow(win);
	}
	
	
	// 函数名是在grid中[]的第2个参数
	function setAuthority(id){
		var roleIds = "${roleForm.roleIds}";
		if(roleIds != null && roleIds != ""){
			var strroleid  = roleIds.split(",");
			for(var i = 0 ; i < strroleid.length ; i++){
				if(strroleid[i] == id){				
					alert("不能修改系统默认管理员的权限");
					return false;
				}
			}
		}
		win = showWindow("setauthority","设置菜单权限","role.do?dealMethod=findAuthorityData&roleid="+id+"",150, 20,600,560);
	}


	
	function roleDetail(id){
		var roleIds = "${roleForm.roleIds}";
		if(roleIds != null && roleIds != ""){
			var strroleid  = roleIds.split(",");
			for(var i = 0 ; i < strroleid.length ; i++){
				if(strroleid[i] == id){				
					alert("不能修改系统默认管理员的角色！");
					return false;
				}
			}
		}
		win = showWindow("roledetail","角色信息","role.do?dealMethod=detail&siteId="+$("#siteId").val()+"&id="+id+"",0,0,560,320);	 
	}


</script>
</head>
<body>
	<div class="currLocation">角色管理</div>
	<form id="roleForm" action="<c:url value="/role.do"/>" method="post" name="roleForm">
		<input type="hidden" name="info" id="info" value="${roleForm.infoMessage}"/> 
		<input type="hidden" name="dealMethod" id="dealMethod" value="${roleForm.dealMethod}"/>
		<input type="hidden" name="ids" id="strid"/>
		<input type="hidden" name="siteId" id="siteId" value="${roleForm.siteId}"/>
		<input type="hidden" name="treeId" id="treeId" value="${roleForm.siteId}"/>
		<input type="hidden" name="use" id="use" value="${roleForm.use}"/>     
		<complat:button name="button" buttonlist="add|0|delete" buttonalign="left"></complat:button>
		
		<complat:grid 		
			ids="xx" 
			width="260,200,200,180" head="角色名,所属网站,创建时间,设置菜单权限" element="[1,link,onclick,roleDetail][4,button,onclick,setAuthority,设置权限]" 
			page="${roleForm.pagination}" form="roleForm" action="role.do?dealMethod=&treeId=${roleForm.siteId}"/>
	</form>
</body>
</html>