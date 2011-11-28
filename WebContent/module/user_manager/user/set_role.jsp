<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户角色权限设置</title>

<style type="text/css">
 #saveArea{
 	padding:0px 0 20px 220px;
 }
</style>
<script language="javascript"><!--	
	/** 退出 */
	function button_quit_onclick(ee){	
		rightFrame.closeNewChild();
	}
	/** 保存角色权限设置 */
	function button_save_onclick(ee){	
		var checkIds = $("#checkedTreeIds").val();
		 
		var systemchbox = "";	
		if(document.getElementById("systemchbox")){
				
			if(document.getElementById("systemchbox").checked == true){
				systemchbox = $("#systemchbox").val();			
			}
		}
	/**	if((checkIds == null || checkIds == "") && (systemchbox == null || systemchbox == "")){
			alert("请至少选择一个角色!");
			return false;
		}*/
		document.all("dealMethod").value="setRoleSave";
	 	var options = {	 	
 		    	url: "user.do",
 		    success: function(msg) { 		    		
 		    		alert(msg); 		
 		    		rightFrame.closeNewChild();		
 		    } 
 		  };
		
		$('#userForm').ajaxSubmit(options);
	}	
//
-->
</script>
</head>
<body style="background-color: white">
  	<form id="userForm" enctype="multipart/form-data"  action="<c:url value="/user.do"/>" method="post" name="userForm">
		<input type="hidden" name="dealMethod" id="dealMethod" value="setRoleSave"/>
		<!-- 用户ID -->
		<input type="hidden" name="id" id="userid" value="${userForm.id}"/>
		<!-- 获取角色ID -->	               
        <input type="hidden" name="roleid" id="strroleid" value=""/>	
		<div style="HEIGHT: 380px;left:115px; top:16px; VISIBILITY: inherit; WIDTH:430px;background:white; " name="first" id="first">
			<complat:treecheckbox allData="${userForm.allData}" checkData="${userForm.chooseData}" ></complat:treecheckbox>	
		</div>	
		<div id="saveArea" class="form_cls">			
			<ul>
				<li>
				<input  type="button"  name="button_save" class="btn_normal"  value="保存" onClick="javascript:button_save_onclick(this);" >
				<input  type="button"  name="button_quit" class="btn_normal"  value="退出" onClick="javascript:button_quit_onclick(this);" >
				</li>
			</ul>
		</div>
	</form>
</body>
</html>