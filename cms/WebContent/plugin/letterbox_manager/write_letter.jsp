<%@ page language="java" contentType="text/html; charset=utf-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>办件类别管理</title>
<%@include file="/templates/headers/header.jsp"%>
<style  type="text/css" media="all">
	#container label {
	     width:80px;
	 }
	#btn { 
	 	 padding:20px 0 0 100px;
	 }
</style>
	<script type="text/javascript">
	var categoryid;
		function change(obj) {
		//	alert(obj.value);
			categoryid = obj.value;	
		}

		function sub() {
			$("#dealMethod").val("add");
			if(document.getElementById("letterCategoryId").value == "") {
				return false;
			}
        	$("#letterForm").submit();
		}
	</script> 
</head>
<body>
	<form id="letterForm" name="letterForm" method="post" action="userLetter.do" >
	<input type="hidden" id="organizationId" name="organizationId" value="${letterForm.organizationId}"/>
	<input type="hidden" id="dealMethod" name="dealMethod" value=""/>
	<input type="hidden" id="memberId" name="memberId" value="${letterForm.memberId}"/>
	<div class="form_div">	
		<table style="font:12px; width:740px;">
			<tr>
				<td class="td_left" width="90"><i>*</i>姓　　名：</td>
				<td width="220"><input type="text" class="input_text_normal" id="userName" name="letter.userName" style="width:210" valid="string" tip="所有字符（包括空格）" maxlength="30"/></td>
				<td class="td_left" width="90"><i>*</i>移动电话：</td>			
				<td width="220"><input type="text" class="input_text_normal" id="mobileTel" name="letter.mobileTel" style="width:210" valid="num" tip="如：12345678901" maxlength="14"/></td>
			</tr>
			<tr>
				<td class="td_left" width="90"><i>&nbsp;</i>联系地址：</td>
				<td width="250"><input type="text" class="input_text_normal" id="residence" name="letter.residence" style="width:210" value="" valid="string" tip="所有字符（包括空格）" empty="true" maxlength="50"/></td>
				<td class="td_left" width="90"><i>&nbsp;</i>家庭电话：</td>
				<td width="250"><input type="text" class="input_text_normal" id="homeTel" name="letter.homeTel" style="width:210" value="" valid="num" tip="如：000088888888" empty="true" maxlength="12"/></td>
			</tr>
			<tr>
				<td class="td_left" width="90"><i>&nbsp;</i>电子信箱：</td>
				<td width="250"><input type="text" class="input_text_normal" id="email" name="letter.email" style="width:210" valid="email" tip="请输入合法的email地址" empty="true" /></td>
				<td class="td_left" width="90"><i>&nbsp;</i>发送机构：</td>
				<td width="250"><input type="text" class="input_text_normal" id="orgName" name="orgName" style="width:210" value="${letterForm.orgName}" readonly/></td>
			</tr>
			<tr>
				<td class="td_left" width="90"><i>*</i>信件类别：</td>
				<td colspan="3">
					<select name="letterCategoryId" id="letterCategoryId" style="width: 210px; height:300px;" class="input_select_normal" valid="string" onChange="change(this)">
						<option value="" selected="selected">————请选择类别————</option>
						<c:forEach items="${letterForm.list}" var="list">
						<option value="${list[0]}">${list[1]}</option>
						</c:forEach> 
					</select>
				</td>
			</tr>
			<tr>
				<td class="td_left" width="90"><i>&nbsp;</i>是否公开：</td>
				<td colspan="3">
					<input type="radio" id="openStr" name="openStr" class="input2" value="1" checked>是
					<input type="radio" id="openStr" name="openStr" class="input2" value="0" >否
				</td>
			</tr>
			<tr>
				<td class="td_left" width="90"><i>*</i>标　　题：</td>
				<td colspan="4"><input type="text" class="input_text_normal" id="title" name="letter.title" style="width:580px;" valid="string" tip="所有字符（包括空格）" maxlength="50" value=""/></td>
			</tr>
			<tr>
				<td class="td_left" width="90"><i>&nbsp;</i>信件内容：</td>
				<td colspan="3"><textarea id="content" class="input_textarea_normal" name="letter.content" style="height:200px;width:580px;"></textarea></td>
			</tr>
			<tr><td><li>&nbsp;</li></td></tr>
			<tr>
				<td colspan="4"><center>
					<input type="button"  class="btn_normal" value="提 交" onclick="sub()"> 
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="reset" class="btn_normal" value="重 置"></center> 
				</td>
			</tr>
		</table>
	</div>
	</form>
</body>
</html>
