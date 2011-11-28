<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>常用联系人管理</title>
	<script type="text/javascript">
	
	function button_delete_onclick(ee) {	
		var ids = document.getElementById("xx").value;
		if(ids == "" || ids == null){
			alert("请至少选择一条记录操作！");
			return false;
		}
		if(confirm("确定要删除吗？")) {
			document.getElementById("strid").value = document.getElementById("xx").value;
			$("#dealMethod").val("deleteUsefulContacter");
	    	$("#siteMessageForm").submit();
		} else {
			return false;
		}
	}

	function button_return_onclick(ee){
		$("#dealMethod").val("reback");
    	$("#siteMessageForm").submit();
	}

	</script> 

</head>
<body>
	<form id="siteMessageForm" action="sitemessage.do" method="post" name="siteMessageForm">
	<div class="currLocation">常用联系人管理</div>
		<input type="hidden" name="dealMethod" id="dealMethod" value="contacterList"/>
		<input type="hidden" name="ids" id="strid"/>
		<complat:button name="button"  buttonlist="delete|0|return" buttonalign="left" ></complat:button>
		<complat:grid ids="xx" width="*, *, *, *" head="用户名, 登录名, 所在机构, 职位"  page="${siteMessageForm.pagination}"
					  form="siteMessageForm" action="sitemessage.do"/>
	</form>
</body>
</html>
