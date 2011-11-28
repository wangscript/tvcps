<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>类别列表</title>
	<%@include file="/templates/headers/header.jsp"%>
<script type="text/javascript">
$(function loadList(){
var mess=$("#infoMessageId").val();
	if(mess!=""){
			alert(mess);
	}
});
function button_add_onclick(ee){	
	var url = "<c:url value='/guestCategory.do?dealMethod=showCategory'/>";
	win = showWindow("add","新增类别",url, 293, 0, 337, 136);	 
}
function button_delete_onclick(ee){	
	var ids = document.getElementById("xx").value;
	if(ids == "" || ids == null){
		alert("请至少选择一条记录操作！");
		return false;
	}
	if(confirm("确定要删除吗？")){
		document.getElementById("strid").value = ids;
	    $("#dealMethod").val("categoryDelete");
		$("#guestBookForm").submit();  
	}else
		return false;
	}
	function show(id) {
		win = showWindow("edit","编辑类别","<c:url value='/guestCategory.do?dealMethod=categoryDetail&ids="+id+"'/>", 293, 0, 337, 136);
	}
</script>
</head>
<body>
<form action="<c:url value='/guestCategory.do'/>" method="post" name="guestBookForm" id="guestBookForm">
<div class="currLocation">功能单元→ 留言本→ 类别管理</div>
	<input type="hidden" name="ids" id="strid"/>
	<input type="hidden" name="infoMessage" id="infoMessageId"value="${guestBookForm.infoMessage }"/>
	<input type="hidden" name="dealMethod" id="dealMethod" value="categoryList"/>
	<complat:button  name="button" buttonlist="add|0|delete" buttonalign="left"/>
		<complat:grid ids="xx" width="*,*,*"  
		head="类别名称,所属网站,操作" 
		element="[1,link,onclick,show][3,button,onclick,show,修改]"
		page="${guestBookForm.pagination}" form="guestBookForm" action="/guestCategory.do"/>
        <div id="large"></div> 
</form>
</body>
</html>