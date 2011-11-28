<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>办件类别管理</title>
	
	<script type="text/javascript">

	$(document).ready(function(){ 
		var message = document.getElementById("message").value;
		if(message != null && message != "") {
			if(message == "该类别正在使用,不能删除" ){
				alert("该类别已在使用,不能删除");
				window.location.href = "letterCategory.do?dealMethod=find";
			} else {
				window.location.href = "letterCategory.do?dealMethod=find";
			}
		}
	});
	
		function button_add_onclick(ee){	 
			var url = "<c:url value='letterCategory.do?dealMethod=findCatrgoryName&" + getUrlSuffixRandom() + "'/>";
			win = showWindow("categorydetail","添加信件类别",url,293, 0,360,180);
		}
		
		function button_delete_onclick(ee){
			var ids = document.getElementById("xx").value;
			if(document.getElementById("xx").value == null || document.getElementById("xx").value == "") {
        		alert("请至少选择一条记录操作！");
        		return false;
        	} 
        	if(confirm("确定要删除吗？")){
        			document.getElementById("strid").value = document.getElementById("xx").value;
    				$("#dealMethod").val("delete");
    		    	$("#letterCategoryForm").submit();
    		}
    	}

		function categorydetail(id){
			win = showWindow("categorydetail","信件类别信息","letterCategory.do?dealMethod=categoryDetail&ids="+id+"",293, 0,360,180);	 
		}
		
		function closeChild() {
			closeWindow(win);
		}
	</script> 

</head>
<body>
	<form id="letterCategoryForm" action="letterCategory.do" method="post" name="letterCategoryForm">
	<div class="currLocation">信件类别管理</div>	
		<input type="hidden" name="dealMethod" id="dealMethod" value="find"/>
		<input type="hidden" name="ids" id="strid"/>
		<input type="hidden" name="message" id="message" value="${letterCategoryForm.message}"/>
		<complat:button name="button" buttonlist="add|0|delete" buttonalign ="left"></complat:button>
		<complat:grid ids="xx" width="*" head="办件类别名称"  page="${letterCategoryForm.pagination}" element="[1,link,onclick,categorydetail]" 
					  form="letterCategoryForm" action="letterCategory.do"/>
	</form>
</body>
</html>
