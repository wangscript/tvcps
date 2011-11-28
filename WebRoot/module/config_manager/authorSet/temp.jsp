   <%@page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script type="text/javascript" > 
<!--页面加载时跳转到另一个页面 -->
function  list()
{
     var kk=$("#categoryId").val();
     document.forms[0].submit();
}

function tt(){
	
	list();
}


</script>
</head>
<body onload="tt()" >  
<form action="author.do?dealMethod=${generalSystemSetForm.dealMethod}&categoryId=${generalSystemSetForm.categoryId}&currPage=${generalSystemSetForm.currPage }&rowsPerPage=${generalSystemSetForm.rowsPerPage }" name="generalSystemSetForm" mehtod=post"">
<input type="hidden" name="categoryId" id="categoryId" value="${generalSystemSetForm.categoryId}"/>
<input type="hidden" name="dealMethod" id="dealMethod" value="${generalSystemSetForm.dealMethod}"/>
<input type="hidden" name="currPage" id="currPage" value="${generalSystemSetForm.currPage }">
<input type="hidden" name="rowsPerPage" id="rowsPerPage" value="${generalSystemSetForm.rowsPerPage }">
</form>
</body>
</html>
