<%@page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>文档管理中的js脚本中的脚本编辑</title>
<style  type="text/css" media="all">
	
</style>

<script type="text/javascript">
	$(document).ready(function(){
		$('#mess').focus();
	});
	$(function lodJs(){
		var mess = $("#mess").val();
			if(mess=="没有找到该js文件"){
				alert(mess);
			}
	});
	function dkey() 
	{
	  if(event.ctrlKey && window.event.keyCode == 83) saveEditJs();
	}
		document.onkeydown=dkey;
	  function saveEditJs() {
			var options = {	 	
	 		    	url: '<c:url value="/js.do?dealMethod=savejs"/>',
	 		    success: function(msg) { 
					var strmsg = msg.split("###");
					var message=strmsg[0];
					if(message != "null" && message != "") {
						closeWindow(rightFrame.getWin());	
							alert(message);
					}		
				}
			};
			$('#documentForm').ajaxSubmit(options);	
			
	  }
	  function backJs(){
		  closeWindow(rightFrame.getWin());	
		  }
</script>

</head>
<body>
    <form method="post" name="documentForm" id="documentForm" action="<c:url value="/js.do?dealMethod=savejs"/>">
			<input type="hidden" name="jsPath" value="${documentForm.jsPath }"/>
<div><font color="red">提示：</font>请慎重修改此文件，如果不熟悉javascript语言，尽量不要修改，此文件一旦修改，便不可恢复,还原</div>
		   	<div class="form_div">
			<table id="main" style="font:12px; width:460px;" align="center">
				<tr>
					<td align="center">js脚本编辑器</td>
				</tr>
				<tr>
					<td><textarea rows="27" cols="120" name="jsContent" id="mess" >${documentForm.jsContent}</textarea></td>
				</tr>
				<tr>
					<td align="center">
						<input type="button" class="btn_normal" onclick="saveEditJs();" value="保存" />
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		        		<input type="button" class="btn_normal" onclick="backJs();" value="返回"/>
					</td>
				</tr>
			</table>		
        </div>
	</form>
</body>
</html> 




