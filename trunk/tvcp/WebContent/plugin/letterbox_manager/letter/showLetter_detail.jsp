<%@ page language="java" contentType="text/html; charset=utf-8"%>
<html>
<head>
<title>信件内容</title>
<%@include file="/templates/headers/header.jsp"%>
<style  type="text/css" media="all">
	
</style>
	<script type="text/javascript">
		function change(obj) {
			categoryid = obj.value;	
		}

		function button_return_onclick(ee){
			window.location.href = "letter.do?dealMethod=waitForAccept";
    	}

		function sub() {
			if(document.getElementById('userName').value == null || document.getElementById('userName').value == "") {
        		alert("请输入姓名!");
        		return false;
        	} else if(document.getElementById('mobileTel').value == null || document.getElementById('mobileTel').value == "") {
        		alert("请输入联系电话!");
        		return false;
        	} else if(document.getElementById('title').value == null || document.getElementById('title').value == "") {
        		alert("请输入标题!");
        		return false;
        	} else {
   				$("#dealMethod").val("add");
           		$("#letterForm").submit();
        	}
		}

		//修改信件类别
		function modifyCategory(obj) {
			win = showWindow("modifyCategory","修改信件类别","letter.do?dealMethod=showCategory&ids="+obj, 293, 0,350,400);
        }

		function closeChild() {
			closeWindow(win);
		}
	</script> 

</head>
<body>
	<form id="letterForm" name="letterForm" method="post" action="letter.do" >
	<div class="currLocation">信件内容</div>
	<input type="hidden" id="dealMethod" name="dealMethod" value=""/>
	<input type="hidden" id="letterId" name="letterId" value="${letterForm.letter.id}"/>
	<complat:button name="button" buttonlist="return" buttonalign ="left"></complat:button>
	<div class="form_div">	
		<table style="font:12px; width:900px;">
			<tr>
				<td class="td_left" width="90"><i>&nbsp;</i>姓　　名：</td>
				<td><input type="text" class="input_text_normal" id="userName" name="letter.userName" style="width:260" value="${letterForm.letter.userName}" readonly/></td>
				<td class="td_left" width="90"><i>&nbsp;</i>联系地址：</td>
				<td><input type="text" class="input_text_normal"  id="residence" name="letter.residence" style="width:260" value="${letterForm.letter.residence}" readonly/></td>
			</tr>
			<tr>
				<td class="td_left" width="90"><i>&nbsp;</i>电子信箱：</td>
				<td><input type="text" class="input_text_normal" id="email" name="letter.email" style="width:260" value="${letterForm.letter.email}" readonly/></td>
				<td class="td_left" width="90"><i>&nbsp;</i>写信日期：</td>
				<td><input type="text" class="input_text_normal" id="date" name="date" style="width:260" value="${letterForm.writeDate}" readonly/></td>
				
			</tr>
			<tr>
				<td class="td_left" width="90"><i>&nbsp;</i>发送机构：</td>
				<td><input type="text" class="input_text_normal" id="orgName" name="orgName" style="width:260" value="${letterForm.organization.name}" readonly/></td>
				<td class="td_left" width="90"><i>&nbsp;</i>是否公开：</td>
				<td><input type="text" class="input_text_normal" id="opened" name="opened" style="width:260" value="${letterForm.openStr}" readonly/></td>
			</tr>
			<tr>
				<td class="td_left" width="90"><i>&nbsp;</i>信件类别：</td>
				<td colspan="3">
					<input type="text" class="input_text_normal" id="orgName" name="orgName" style="width:260" value="${letterForm.letterCategory.name}" readonly/>
					<input type="button" class="btn_small" style="margin:0px;" value="修改" onclick="modifyCategory('${letterForm.letter.id}')" />
				</td>
			</tr>
			<tr>
				<td class="td_left" width="90"><i>&nbsp;</i>标 　　题：</td>
				<td colspan="3"><input type="text" class="input_text_normal" id="title" name="letter.title" style="width:400" value="${letterForm.letter.title}" readonly/></td>
			</tr>
			<tr>
				<td class="td_left" width="90"><i>&nbsp;</i>信件内容：</td>
				<td colspan="3"><textarea id="content" class="input_textarea_normal" name="letter.content" style="height:260px;width:600px;" readonly>${letterForm.letter.content}</textarea></td>
			</tr>
		</table>
	</div>
	</form>
</body>
</html>
