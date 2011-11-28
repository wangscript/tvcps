   <%@page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统图片设置</title>

<script type="text/javascript" > 
$(document).ready(function() {
	if(document.getElementById("message").value == "1"){			
	// alert("图片启动设置成功！")
	 document.getElementById("general").checked=true;
	}else if(document.getElementById("message").value == "2"){
	//	alert("图片禁用成功！")
		document.getElementById("general2").checked=true;
	}else{
		document.getElementById("general2").checked=true;
		}
});
</script>
</head>
<body >  
<div class="currLocation">系统设置→插入图片
	 </div>




</form>
<form action="author.do?dealMetho=&categoryId=${generalSystemSetForm.categoryId}" name="generalSystemSetForm" mehtod=post"">
    <input type="hidden" name="message" id="message" value="${generalSystemSetForm.infoMessage}"/>
    <input type="hidden" name="dealMethod" id="dealMethod" value="picture"/>
  	<input type="hidden" name="categoryId" id="categoryId" value="${generalSystemSetForm.categoryId}"/>
	<table cellspace="0" cellpadding="5" border="0" style="width:770px; font:12px;" >
     <tr height="20px"><td colspan="2"></td></tr>
	<tr align="center" width="250px">
		<td>图片宽度大于500像素，默认选择插入缩略图</td>
		<td align="left"><input name="generalSystemSet.defaultSet" id="general" type="radio" value="1"  />是&nbsp;&nbsp;&nbsp;&nbsp;
		      <input name="generalSystemSet.defaultSet" type="radio" id="general2" value="0"  />否&nbsp;&nbsp;&nbsp;&nbsp;
    	      <input id="btnEdit" class='btn_normal' type='submit' value="设 置" />
		</td>
	</tr>
	<tr><td colspan="2"></td></tr>
</table>
</form>
</body>
</html>
