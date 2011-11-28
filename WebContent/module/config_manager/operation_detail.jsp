<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增操作</title>
<style type="text/css" media="all">
	*{
	 font-size:12px;
	}
	
</style>
	<script type="text/javascript">
		var childOrgWin;
		$(document).ready(function() {
			alert("sdfffffffffff");
			if(document.getElementById("message").value!=null &&  document.getElementById("message").value!=""){
				parent.window.location.href="<c:url value='/operation.do?dealMethod='/>";
			}
		}); 	
		var x = 0;
		function button_save_onclick(ee){	
		//	alert(parent.document.getElementById("xx").value);		
		//	document.getElementById("strids").value = parent.document.getElementById("strid").value;
			<c:if test="${operationForm.id !=null}">
				document.all("dealMethod").value="modify";
				document.all("id").value=${operationForm.id};	
				document.forms[0].submit(); 
		     //   parent.document.execCommand('Refresh');
				parent.closeDetailChild();	
				return false;	
			</c:if>
			document.all("dealMethod").value="insert";
			document.forms[0].submit(); 
		}
		
		
		function closeChild() {
			childOrgWin.close();
		}
		function button_reset_onclick(ee){	
			document.getElementById("operation.name").value="";	
			document.getElementById("operation.description").value="";
		}
	</script>
</head>
<body>

 <form action="<c:url value="/operation.do"/>" method="post" name="operationForm">
	 <input type="hidden" name="dealMethod" id="dealMethod" />
	 <input type="hidden" name="id" id="id"/>
	 ${operationForm.infoMessage}
	 <input type="hidden" name="message" id="message" value="${operationForm.infoMessage}"/>
	 <div class="form_div">
		<table>
			<tr>
				<td class="td_left" width="15%"><i>*</i>操作名称:</td> 
				<td><input type="text" name="operation.name" id="operationName" value="${operationForm.operation.name}"/></td>
			</tr>
			<tr>
				<td class="td_left" width="15%"><i>&nbsp;</i>操作描述:</td>		
				<td><textarea   rows="7" cols="20" class="input_textarea_normal" name="operation.description">${operationForm.operation.description}</textarea></td>
			</tr>
			<tr>
				<td><complat:button name="button" buttonlist="save" buttonalign="center"></complat:button></td>
			</tr>	
			<tr>
				<td><complat:button name="button" buttonlist="reset" buttonalign="center"></complat:button></td>
			</tr>
		</table>	
	</div>
 </form>
</body>
</html>