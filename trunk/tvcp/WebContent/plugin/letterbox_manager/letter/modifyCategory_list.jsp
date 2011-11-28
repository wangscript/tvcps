<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>信件类别列表</title>
	<script type="text/javascript">
		var childDetailOrgWin;
		//选取要转办的机构
		function button_sure_onclick(ee){
			var arr = new Array();
			arr = document.getElementById("xx").value.split(",");
			if(document.getElementById("xx").value == null || document.getElementById("xx").value == "") {
				alert("请至少选择一条记录操作！");
        		return false;
        	} else if(arr.length == 1) {
				document.getElementById("strid").value = document.getElementById("xx").value;
				$("#dealMethod").val("modifyCatagory");
	    		$("#letterForm").submit();
	    		var newId = document.getElementById("xx").value;	    		
	    		var dd = newId + "_1";
	    		rightFrame.document.getElementById("orgName").value = $("#"+dd+"").html();
	    		//closeWindow(rightFrame.getWin());
	    		rightFrame.closeChild();
	    	} else {
				alert("只能选择一个类别!");
				return false;
			}
		}

		function closeChild() {
			closeWindow(win);
		}
	</script> 
</head>
<body>
	<form id="letterForm" action="letter.do" method="post" name="letterForm">
	<div class="currLocation">信件类别列表</div>
	<complat:button name="button" buttonlist="sure" buttonalign ="left"></complat:button>
		<input type="hidden" name="dealMethod" id="dealMethod" value="showCategory"/>
		<input type="hidden" name="letterId" id="letterId" value="${letterForm.letterId}"/>
		<input type="hidden" name="ids" id="strid"/>
		<complat:grid ids="xx" width="*" head="办件类别名称" page="${letterForm.pagination}" form="letterForm" action="letter.do"/>
	</form>
</body>
</html>
