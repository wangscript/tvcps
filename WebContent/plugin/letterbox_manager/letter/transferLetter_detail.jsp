<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>转办信件表单</title>
<style  type="text/css" media="all">
	
</style>
	<script type="text/javascript">

		function button_sure_onclick(ee){
			$("#dealMethod").val("transferRecord");
        	$("#letterForm").submit();
		}
	</script> 

</head>
<body>
	<form id="letterForm" name="letterForm" method="post" action="letter.do" >
	<div class="currLocation">信件转办表单</div>
	<input type="hidden" id="dealMethod" name="dealMethod" />
	<input type="hidden" id="fromOrgId" name="fromOrgId" value="${letterForm.letter.organization.id}"/>
	<input type="hidden" id="toOrgId" name="toOrgId" value="${letterForm.organization.id}"/>
	<input type="hidden" id="transferUserId" name="transferUserId" value="${letterForm.user.id}"/>
	<input type="hidden" id="letterId" name="letterId" value="${letterForm.letter.id}"/>

	<complat:button name="button" buttonlist="sure" buttonalign ="left"></complat:button>
	<div class="form_div">	
		<table style="font:12px; width:780px;">
			<tr>
				<td class="td_left" width="80"><i>&nbsp;</i>来自部门：</td>
				<td><input type="text" class="input_text_normal" id="fromOrg" name="fromOrg" style="150px;" value="${letterForm.letter.organization.name}" readonly></td>
				<td class="td_left" width="80"><i>&nbsp;</i>转至部门：</td>
				<td><input type="text" class="input_text_normal" id="toOrg" name="toOrg" style="150px;" value="${letterForm.organization.name}" readonly></td>
			</tr>
			<tr>
				<td class="td_left" width="80"><i>&nbsp;</i>转办人：</td>
				<td colspan="3"><input type="text" class="input_text_normal" id="transferUser" name="transfer" style="300px;" value="${letterForm.user.name}" readonly></td>
			</tr>
			<tr>
				<td class="td_left" width="80"><i>&nbsp;</i>备注：</td>
				<td colspan="3"><textarea id="note" class="input_textarea_normal" name="transferRecord.note" style="height:160px;width:500px;"></textarea></td>
			</tr>
		</table>
	</div>
	</form>
</body>
</html>
