<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>操作设置</title>
	<script language="javascript">
	<!--	
		$(document).ready(function() {
		//	alert(document.getElementById("message").value);
			if(document.getElementById("message").value!=null &&  document.getElementById("message").value!=""){
			 	alert(document.getElementById("message").value);		
				//var frameobj = parent.document.frames("rightFrame");
				var frameobj = parent.document.all["rightFrame"].contentWindow;
				var url = "<c:url value='/role.do?dealMethod=operationlist&roleid="+document.getElementById("strroleid").value+"&itemid="+parent.document.getElementById("itemid").value+"&operationType=${roleForm.operationType}'/>";			
				window.location.href = url;				
				parent.document.getElementById("message").value= "";
				document.getElementById("message").value= "";
			}
		}); 
	//-->
	</script>
</head>
<body style="background:white">
	<input type="hidden" name="message" id="message" value="${roleForm.infoMessage}"/>
	<input type="hidden" name="roleid" id="strroleid" value="${roleForm.roleid}"/>
	<complat:checkbox ids="checkedIds" allList="${roleForm.alloperationlist}" checkedList="${roleForm.chooseoperationlist}"/>	
</body>
</html>