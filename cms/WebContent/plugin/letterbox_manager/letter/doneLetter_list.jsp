<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>已处理的信件</title>
	<script type="text/javascript">
		
		function button_delete_onclick(ee){
			var ids = document.getElementById("xx").value;
			if(document.getElementById("xx").value == null || document.getElementById("xx").value == "") {
				alert("请至少选择一条记录操作！");
        		return false;
        	} else{			 
				document.getElementById("strid").value = document.getElementById("xx").value;
				$("#dealMethod").val("deleteDoneLetter");
		    	$("#letterForm").submit();
		    }
    	}

		function userLetterDetail(id) {
    		window.location.href = "letter.do?dealMethod=showDealedLetter&ids=" + id ;
        }
	</script> 
</head>
<body>
	<form id="letterForm" action="letter.do" method="post" name="letterForm">
	<div class="currLocation">已处理信件列表</div>
	
		<input type="hidden" name="dealMethod" id="dealMethod" value="done"/>
		<input type="hidden" name="ids" id="strid"/>
		<complat:button name="button" buttonlist="delete" buttonalign ="left"></complat:button>
		<complat:grid ids="xx" width="100, 300, 150, 150" head="信件类别, 标题, 用户名, 日期"  element="[2,link,onclick,userLetterDetail]"
				page="${letterForm.pagination}" form="letterForm" action="letter.do"/>
	</form>
</body>
</html>
