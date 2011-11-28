<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>操作设置</title>
	<script language="javascript">
	
		$(document).ready(function() {		
			var setChild = "${userForm.columnExtends}";		 
			if(setChild == "true"){
				document.getElementById("setChildColumn").checked = true;
			}
			if($("#strroleid").val() == null || $("#strroleid").val() == "" ){
				alert("请先设置角色!");
				parent.button_quit_onclick();
				return false;
			}
			if(document.getElementById("message").value!=null &&  document.getElementById("message").value!=""){
				alert(document.getElementById("message").value);		
		//		var frameobj = parent.document.frames("rightFrame");				
		//		var url = "<c:url value='/user.do?dealMethod=operationlist&roleid="+document.getElementById("strroleid").value+"&itemid="+parent.document.getElementById("itemid").value+"'/>";			
				window.location.href = url;				
				parent.document.getElementById("message").value= "";
				document.getElementById("message").value= "";
			}
		}); 

</script>
</head>
<body style="background:white">
	<input type="hidden" name="message" id="message" value="${userForm.infoMessage}"/>
	<input type="hidden" name="roleid" id="strroleid" value="${userForm.roleid}"/>
	<complat:treecheckbox allData="${userForm.allData}" colNum="2" checkData="${userForm.chooseData}" ></complat:treecheckbox>
	<input type="checkbox" id="setChildColumn" name="setChildColumn"><font style="font-size: 12px;font-style: 宋体" ><b>设置下级栏目是否继承当前栏目</b></font>
</body>
</html>