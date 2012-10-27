<%@page contentType="text/html; charset=utf-8" language="java"	errorPage=""%>
<%@include file="/templates/headers/header.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>用户列表</title>

<script type="text/javascript">
function btnOK(){	
	var ids = document.getElementById("xx").value;
	if(ids == "" || ids == null||ids=="null"){
		alert("请至少选择一条记录操作(提示：只能选择一条记录)");
		return false;
	}
	var id = ids.split(",");
	if(id.length != 1){
		alert("只能选择一个用户");
		return false;
	}else{
		document.getElementById("dispenseId").value = ids;
	    $("#dealMethod").val("dispenseUser");
		var options={
				url:"<c:url value='/guestCategory.do'/>",
				success:function(msg){
					if(msg!=""){
						alert("分发成功");
					}
					top.document.getElementById("rightFrame").src = "<c:url value='/guestCategory.do?dealMethod='/>";
					closeWindow(rightFrame.getWin());
			}};
		$("#guestBookForm").ajaxSubmit(options); 
		
	}
}
</script>
</head>
<body>
<form action="<c:url value='/guestCategory.do'/>" method="post"	name="guestBookForm" id="guestBookForm"><br />
<input type="hidden" name="ids" id="strid"	value="${guestBookForm.ids }" /> 
<input type="hidden" name="dispenseId" id="dispenseId" /> 
<input type="hidden"name="dealMethod" id="dealMethod" value="showAllUser"/> 
<input type="button"  class="btn_normal" value="确定" onclick="btnOK()"/> <br/>
<complat:grid ids="xx" width="*" head="用户"
	page="${guestBookForm.pagination}" form="guestBookForm"
	action="/guestBook.do" />
<div id="large"></div>
</form>
</body>
</html>