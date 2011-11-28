<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<%@include file="/templates/headers/header.jsp"%>
<style  type="text/css" media="all">
	
</style>
	
	<script type="text/javascript">
		function reback(){
			window.location.href = "sitemessage.do?dealMethod=sendMessageBox";
    	}
	</script> 
</head>
	<body>	 
		<div class="currLocation">消息内容：</div>
		
		<div class="form_div">
			<table style="font:12px; width:700px;">
				<tr>
					<td class="td_left" width="80px;"><i>&nbsp;</i>收件人：</td>
					<td  class="td_right"><input type="text" id="contacterName" class="input_text_normal" name="contacterName" style="width:400px;" value="${siteMessageForm.contacterName}" readonly></td>
				</tr>
				<tr>
					<td class="td_left" width="80px;"><i>&nbsp;</i>发送时间：</td>
					<td  class="td_right"><input type="text" id="createTime" class="input_text_normal" name="createTime" style="width:400px;" value="${siteMessageForm.dateTime}" readonly></td>
				</tr>
				<tr>
					<td class="td_left" width="80px;"><i>&nbsp;</i>标 　题：</td>
					<td  class="td_right"><input type="text" id="contacterName" class="input_text_normal" name="contacterName" style="width:600px;" value="${siteMessageForm.title}" readonly></td>
				</tr>
				<tr>
					<td class="td_left" width="80px;">消息内容：</td>
					<td  class="td_right"><textarea id="content" name="content" class="input_textarea_normal" style="height:300px;width:600px;" readonly>${siteMessageForm.content} </textarea></td>
				</tr>
				<tr>
					<td colspan="3">
						<center><input  type="button"  value="返回" class="btn_normal"  onClick="reback()" /></center>
					</td>
				</tr>
			</table>
		</div>		
	</body>
</html>


