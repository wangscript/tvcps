<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<style  type="text/css" media="all">
	
</style>
	<%@include file="/templates/headers/header.jsp"%>
	<script type="text/javascript">
		$(document).ready(function() {
			var readed = document.getElementById("readed").value;
			if(readed == "N") {
				var windd = parent.document.getElementById("topFrame").contentWindow;
				windd.countMessageNum();
			}
		});
	
		function reback(){
			window.location.href = "sitemessage.do?dealMethod=receiveMessageBox";
    	}

		function reply(){
			window.location.href = "sitemessage.do?dealMethod=reply&ids=" + document.getElementById("ids").value;
    	}

		function forward(){
			window.location.href = "sitemessage.do?dealMethod=forward&ids=" + document.getElementById("ids").value;
    	}
	</script> 
</head>
	<body>	
	 <form id="siteMessageForm" name="siteMessageForm" action="<c:url value="/sitemessage.do"/>" method="post" >
	 <div class="currLocation">消息内容：</div>
		<input type="hidden" name="ids" id="strid" value="${siteMessageForm.ids}"/> 
		<input type="hidden" name="readed" id="readed" value="${siteMessageForm.readed}"/> 
		
		<div class="form_div">
			<table style="font:12px; width:700px;">
				
				<tr>
					<td class="td_left" width="80px;"><i>&nbsp;</i>发件人：</td>
					<td  class="td_right"><input type="text" id="contacterName" class="input_text_normal" name="contacterName" style="width:400px;" value="${siteMessageForm.contacterName}" readonly/></td>
				</tr>
				<tr>
					<td class="td_left" width="80px;"><i>&nbsp;</i>发送时间：</td>
					<td  class="td_right"><input type="text" id="createTime" class="input_text_normal" name="createTime" style="width:400px;" value="${siteMessageForm.dateTime}" readonly/></td>
				</tr>
				<tr>
					<td class="td_left" width="80px;"><i>&nbsp;</i>标 　题：</td>
					<td  class="td_right"><input type="text" id="contacterName" class="input_text_normal" name="contacterName" style="width:600px;" value="${siteMessageForm.title}" readonly/></td>
				</tr>
				<tr>
					<td class="td_left" width="80px;"><i>&nbsp;</i>消息内容：</td>
					<td  class="td_right"><textarea id="content" name="content" class="input_textarea_normal" style="height:280px;width:600px;" readonly>${siteMessageForm.content} </textarea></td>
				</tr>
				<tr></tr>
				<tr>
					<td colspan="3"><center>
						<input  type="button"  value="回复" class="btn_normal"  onClick="reply()" />		
						&nbsp;&nbsp;&nbsp;&nbsp;	
						<input  type="button"  value="转发" class="btn_normal"  onClick="forward()" />
						&nbsp;&nbsp;&nbsp;&nbsp;	
						<input  type="button"  value="返回" class="btn_normal"  onClick="reback()" />
						</center>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>


