<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>权限分发</title>
	<%@include file="/templates/headers/header.jsp"%>
<script type="text/javascript">
function btndelete(){	
	var ids = document.getElementById("xx").value;
	if(ids == "" || ids == null){
		alert("请至少选择一条记录操作！");
		return false;
	}
	if(confirm("确定要删除吗？")){
		document.getElementById("strid").value = ids;
	    $("#dealMethod").val("deleteAutority");
		$("#guestBookForm").submit();  
	}else
		return false;
	}
	function show() {
		win = showWindow("set","设置用户","<c:url value='/guestBook.do?dealMethod=autorityDetail'/>", 293, 0, 750, 520);
	}   
</script>
</head>
<body>
<form action="<c:url value='/guestBook.do'/>" method="post" name="guestBookForm" id="guestBookForm">
<div class="currLocation">功能单元→ 留言本→ 分发权限设置</div>
	<input type="hidden" name="ids" id="strid"/>
	<input type="hidden" name="dealMethod" id="dealMethod" value="autorityList"/><br/>
	<input type="button" value="设置" onclick="show();" class="btn_normal"/>&nbsp;&nbsp;<input type="button" value="删除" onclick="btndelete();" class="btn_normal"/><br/>
		<complat:grid ids="xx" width="*"  
		head="用户" 
		page="${guestBookForm.pagination}" form="guestBookForm" action="/guestBook.do"/>
        <div id="large"></div> 
</form>
</body>
</html>