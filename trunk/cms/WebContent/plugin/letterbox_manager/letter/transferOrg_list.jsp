<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>转办部门列表</title>
	<script type="text/javascript">

		//选取要转办的机构
		function button_sure_onclick(ee){
			var arr = new Array();
			arr = document.getElementById("xx").value.split(",");
			if(document.getElementById("xx").value == null || document.getElementById("xx").value == "") {
				alert("请至少选择一条记录操作！");
        		return false;
        	} else if(arr.length == 1) {
	    		var newId = document.getElementById("xx").value;	    		
	    		var dd = newId + "_1";
	    		rightFrame.document.getElementById("organizationId").value = newId;
	    		rightFrame.document.getElementById("letterId").value = $("#letterId").val();
	    		rightFrame.getIds();
	    		closeWindow(rightFrame.getWin());
	    		rightFrame.focus();
	    		//rightFrame.closeChild();
	    	} else {
				alert("只能选择一个转办机构!");
				return false;
			}
		}
	</script> 

</head>
<body>
	<form id="letterForm" action="letter.do" method="post" name="letterForm">
	<input type="hidden" name="dealMethod" id="dealMethod" value="showOrgList"/>
	<input type="hidden" name="ids" id="strid"/>
	<input type="hidden" name="flag" id="flag" value="2"/>
	<input type="hidden" name="letterId" id="letterId" value="${letterForm.letterId}"/>
	<div class="currLocation">转办部门</div>
	
		<complat:button name="button" buttonlist="sure" buttonalign ="left"></complat:button>
		<complat:grid ids="xx" width="*" head="机构名称"  page="${letterForm.pagination}"
					  form="letterForm" action="letter.do"/>
	</form>
</body>
</html>
