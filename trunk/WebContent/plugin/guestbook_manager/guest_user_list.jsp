<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>用户列表</title>
	<%@include file="/templates/headers/header.jsp"%>
<script type="text/javascript">
function btnOK(){	
	var ids = document.getElementById("xx").value;
	if(ids == "" || ids == null){
		alert("请至少选择一条记录操作！");
		return false;
	}else{
		document.getElementById("strid").value = ids;
	    $("#dealMethod").val("getSelectUser");
		var options={
				url:"<c:url value='/guestBook.do'/>",
				success:function(msg){
					top.document.getElementById("rightFrame").src = "<c:url value='/guestBook.do?dealMethod=autorityList'/>";
					closeWindow(rightFrame.getWin());
			}};
		$("#guestBookForm").ajaxSubmit(options);
	}
}
</script>
</head>
<body>
<form action="<c:url value='/guestBook.do'/>" method="post" name="guestBookForm" id="guestBookForm">
<br/>
<input type="hidden" name="ids" id="strid"/>
<input type="hidden" name="dealMethod" id="dealMethod" value="autorityDetail"/>
&nbsp;&nbsp;<input type="button" value="确定" onclick="btnOK();" class="btn_normal"/>
<br/>
<complat:grid ids="xx" width="*"  
		head="用户" 
		page="${guestBookForm.pagination}" form="guestBookForm" action="/guestBook.do"/>
        <div id="large"></div> 
</form>
</body>
</html>