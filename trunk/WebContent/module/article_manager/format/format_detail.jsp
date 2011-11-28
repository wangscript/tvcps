<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>格式管理</title>
<style type="text/css" media="all">
	
</style>
<script type="text/javascript">
	$(document).ready(function(event) {
		$("#formatName").focus();
		var message = $("#message").val();
		if(!message.isEmpty()) { 
			
			if(message == "添加失败，此格式已经存在" || message == "修改失败，此格式已经存在") {
				alert(message);
			}
			document.getElementById("save").disabled="disabled";
			var url = "<c:url value='/articleFormat.do?dealMethod=&" + getUrlSuffixRandom() + "'/>";
			top.document.getElementById("rightFrame").src = url;
			closeWindow(rightFrame.getWin());
		}
	});

	// 保存
	function btn_confirm(btn) {
		var formatName = $("#formatName").val();
		if(formatName == "" || formatName == null) {
			alert("请输入格式名称");
			return false;	
		}
		if ($("#formatId").val().isEmpty()) {
			$("#dealMethod").val("add");
			document.getElementById("save").disabled="disabled";
		} else {
			if($("#formatId").val() == "f1") {
				alert("不允许修改默认格式");
				return false;
			}
			$("#dealMethod").val("modify");
			document.getElementById("save").disabled="disabled";
		}
		$("#formatForm").submit();
	}

	// 重设
	function button_reset_onclick(btn) {	
		
	}
</script>
</head>
<body>
 <form id="formatForm" action="<c:url value="/articleFormat.do"/>" method="post">
 <input type="hidden" name="dealMethod" id="dealMethod" />
 <input type="hidden" name="format.id" id="formatId" value="${articleFormatForm.format.id}"/>
 <input type="hidden" name="message" id="message" value="${articleFormatForm.infoMessage}" />
	<div class="form_div">
		<table width="500px;">
			<tr>
				<td class="td_left" width="80px;"><i>*</i>名　称：</td>
				<td><input type="text" name="format.name" class="input_text_normal" id="formatName" value="${articleFormatForm.format.name}" valid="string" empty="false" tip="格式名称不能为空 " onkeydown="if(event.keyCode==13){return false;}"/></td>
			</tr>
			<tr>
				<td class="td_left">描　述：</td>
				<td><textarea name="format.description" style="width:300px;" class="input_textarea_normal" id="format.description" >${articleFormatForm.format.description}</textarea></td>
			</tr>
			<tr>
				<td><i>&nbsp;</i></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="button" id="save" class="btn_normal" value="保存" onclick="btn_confirm();" />
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="reset" class="btn_normal" value="重置" />
				</td>
			</tr>
		</table>
	</div>
 </form>
</body>
</html>
