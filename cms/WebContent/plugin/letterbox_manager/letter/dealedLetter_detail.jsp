<%@ page language="java" contentType="text/html; charset=utf-8"%>
<html>
<head>
<%@include file="/templates/headers/header.jsp"%>
<title>回复信件</title>
<style  type="text/css" media="all">
	
</style>
	<script type="text/javascript">
		var childDetailOrgWin;
		//提交
		function replyLetter() {
			$("#dealMethod").val("reply");
    		$("#letterForm").submit();
		}

		//查看转办记录
		function findTransferRecord() {
			win = showWindow("transferRecordDetail","转办记录","letter.do?dealMethod=viewTransferRecord&letterId=" + document.getElementById("letterId").value,293, 0,500,600);
		}

		function closeChild() {
			closeWindow(win);
		}

		function button_return_onclick(ee){
			window.location.href = "letter.do?dealMethod=done";
    	}
	</script> 

</head>
<body>
	<form id="letterForm" name="letterForm" action="letter.do" method="post" >
	<div class="currLocation">已回复信件内容</div>
	<input type="hidden" name="dealMethod" id="dealMethod" />
	<input type="hidden" name="organizationId" id="organizationId" value="${letterForm.letter.organization.id}"/>
	<input type="hidden" name="letterId" id="letterId" value="${letterForm.letter.id}"/>
	<complat:button name="button" buttonlist="return" buttonalign ="left"></complat:button>
		<div class="form_div">
			<table style="font:12px; width:680px;">
				<tr>
					<td class="td_left" width="90"><i>&nbsp;</i>姓　　名：</td>
					<td><input type="text" class="input_text_normal" id="userName" name="userName"  style="width:210" value="${letterForm.letter.userName}" readonly/></td>
					<td class="td_left" width="90"><i>&nbsp;</i>家庭电话：</td>			
					<td><input type="text" class="input_text_normal" id="homeTel" name="homeTel" style="width:210" value="${letterForm.letter.homeTel}" readonly/></td>
				</tr>
				<tr>
					<td class="td_left" width="90"><i>&nbsp;</i>联系地址：</td>
					<td><input type="text" class="input_text_normal" id="residence" name="letter.residence" style="width:210" value="${letterForm.letter.residence}" readonly/></td>
					<td class="td_left" width="90"><i>&nbsp;</i>电子信箱：</td>
					<td><input type="text" class="input_text_normal" id="email" name="email" style="width:210" value="${letterForm.letter.email}" readonly/></td>
				</tr>
				<tr>
					<td class="td_left" width="90"><i>&nbsp;</i>信件类别：</td>
					<td><input type="text" class="input_text_normal" id="categoryName" name="categoryName" style="width:210" value="${letterForm.letterCategory.name}" readonly/></td>
					<td class="td_left" width="90"><i>&nbsp;</i>是否公开：</td>
					<td><input type="text" class="input_text_normal" id="opened" name="opened" style="width:210" value="${letterForm.openStr}" readonly/></td>
				</tr>
				<tr>
					<td class="td_left" width="90"><i>&nbsp;</i>写信日期：</td>
					<td><input type="text" class="input_text_normal" id="date" name="date" style="width:210" value="${letterForm.writeDate}" readonly/></td>
					<td class="td_left" width="90"><i>&nbsp;</i>回执编号：</td>
					<td><input type="text" class="input_text_normal" id="replyCode" name="replyCode" style="width:210" value="${letterForm.letter.replyCode}" readonly/></td>
				</tr>
				<tr>
					<td class="td_left" width="90"><i>&nbsp;</i>标 　　题：</td>
					<td colspan="3"><input type="text" class="input_text_normal" id="title" name="title" style="width:550" value="${letterForm.letter.title}" readonly/></td>
				</tr>
				<tr>
					<td class="td_left" width="90"><i>&nbsp;</i>来信内容：</td>
					<td colspan="3"><textarea id="content" class="input_textarea_normal" name="content" style="height:200px;width:550px;"  readonly>${letterForm.letter.content}</textarea></td>
				</tr>
				<tr>
					<td class="td_left" width="90"><i>&nbsp;</i>发送部门：</td>
					<td colspan="3"><input type="text" class="input_text_normal" id="fromOrg" name="fromOrg" style="width:210" value="${letterForm.orgFromName}" readonly/></td>
				</tr>
				<tr>
					<td class="td_left" width="90"><i>&nbsp;</i>回复部门：</td>
					<td colspan="3"><input type="text" class="input_text_normal" id="replyCode" name="replyCode" style="width:210" value="${letterForm.orgToName}" readonly/></td>
				</tr>
				<tr>
					<td class="td_left" width="90"><i>&nbsp;</i>回复内容：</td>
					<td colspan="3"><textarea id="replyContent" class="input_textarea_normal" name="replyContent" style="height:200px;width:550px;" readonly>${letterForm.replyContent}</textarea></td>
				</tr>
				<tr><td><i>&nbsp;</i></td></tr>
			</table>
		</div>
	</form>
</body>
</html>
