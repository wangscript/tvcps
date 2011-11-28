<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>
	<%@include file="/templates/headers/header.jsp"%>
<script type="text/javascript">

function button_add_onclick(ee){	
	var url = "<c:url value='/guestBook.do?dealMethod=showWord'/>";
	win = showWindow("add","新增敏感词",url, 293, 0, 337, 136);	 
}
function button_delete_onclick(ee){	
	var ids = document.getElementById("xx").value;
	if(ids == "" || ids == null){
		alert("请至少选择一条记录操作！");
		return false;
	}
	if(confirm("确定要删除吗？")){
		document.getElementById("strid").value = ids;
	    $("#dealMethod").val("deleteWord");
		$("#guestBookForm").submit();  
	}else
		return false;
	}
	function show(id) {
		win = showWindow("edit","编辑敏感词","<c:url value='/guestBook.do?dealMethod=wordetail&ids="+id+"'/>", 293, 0, 337, 136);
	}
</script>
</head>
<body>
<form action="<c:url value='/guestBook.do'/>" method="post" name="guestBookForm" id="guestBookForm">
<div class="currLocation">功能单元→留言本→基本设置→敏感词</div>
	<input type="hidden" name="ids" id="strid"/>
	<input type="hidden" name="dealMethod" id="dealMethod" value="wordList"/>
	<complat:button  name="button" buttonlist="add|0|delete" buttonalign="left"/>
		<complat:grid ids="xx" width="*,*,*,*"  
		head="敏感词,创建时间,所属网站,操作" 
		element="[1,link,onclick,show][4,button,onclick,show,修改]"
		page="${guestBookForm.pagination}" form="guestBookForm" action="/guestBook.do"/>
        <div id="large"></div> 
</form>
</body>
</html>