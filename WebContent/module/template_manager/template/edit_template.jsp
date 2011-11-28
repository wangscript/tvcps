 <%--
	功能：用于编辑模板
	作者：曹名科
 --%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<html>
<head>
<%@include file="/templates/headers/header.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>模板编辑</title>
	<script type="text/javascript">
	$(function lodTemp(){
		$("#mess").focus();
		var mess = $("#mess").val();
			if(mess=="没有找到该文件"){
				alert(mess);
			}
	});
	function dkey() 
	{
	  if(event.ctrlKey && window.event.keyCode == 83) saveEditTemp();
	}
	  function saveEditTemp() {
			var options = {	 	
	 		    	url: '<c:url value="/template.do?dealMethod=saveTemp"/>',
	 		    success: function(msg) { 
					var strmsg = msg.split("##");
					var message=strmsg[0];
					if(message != "null" && message != "") {
						alert(message);
						top.document.getElementById("rightFrame").src = "<c:url value='/template.do?nodeId=${templateForm.nodeId}&dealMethod='/>";
						closeWindow(rightFrame.getWin());	
					}		
				}
			};
			$('#templateForm').ajaxSubmit(options);	
			
	  }
	  function backTemp(){
		  closeWindow(rightFrame.getWin());	
	 }
	</script>
</head>
<body>
<form method="post" name="templateForm" id="templateForm" action="<c:url value="/template.do?dealMethod=saveTemp"/>">
			<input type="hidden" name="tempPath" value="${templateForm.tempPath }"/>
			<input type="hidden" name="ids" value="${templateForm.ids }"/>
			<input type="hidden" name="nodeId" id="nodeId" value="${templateForm.nodeId }"/>
			<div><font color="red">提示：</font>请慎重修改此文件，如果不熟悉html语言，尽量不要修改，此文件一旦修改，便不可恢复，还原</div>
		   	<div class="form_div">
			<table id="main" style="font:12px; width:460px;" align="center">
				<tr>
					<td align="center">模板编辑器</td>
				</tr>
				<tr>
					<td><textarea rows="27" cols="120" name="tempContent" id="mess" >${templateForm.tempContent}</textarea></td>
				</tr>
				<tr>
					<td align="center">
						<input type="button" class="btn_normal" onclick="saveEditTemp();" value="保存" />
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		        		<input type="button" class="btn_normal" onclick="backTemp();" value="返回"/>
					</td>
				</tr>
			</table>		
        </div>
	</form>
</body>
</html>