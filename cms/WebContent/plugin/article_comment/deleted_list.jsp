<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>回收站</title>
<link type="text/css" rel="StyleSheet" href="/public/css/style.css" />
<script type="text/javascript">
	function button_revert_onclick(){//还原
			var ids = document.getElementById("xx").value;
			if(ids == "" || ids == null){
				alert("请至少选择一条记录操作！");
				return false;
			}
			if(confirm("确定要还原吗?")){
				document.getElementById("strid").value = document.getElementById("xx").value;
			    $("#dealMethod").val("isDeleted");
			    $("#objContentId").val("0");//还原
			    $("#delTagId").val("Revert");
				$("#articleCommentForm").submit();  
			}
		
	}
	function button_delete_onclick(){//彻底删除
		var ids = document.getElementById("xx").value;
		if(ids == "" || ids == null){
			alert("请至少选择一条记录操作！");
			return false;
		}
		if(confirm("确定要彻底删除吗 ? \n 本操作删除的记录将不可还原")){
			document.getElementById("strid").value = document.getElementById("xx").value;
		    $("#dealMethod").val("delete");
			$("#articleCommentForm").submit();  
		}
	}
	
	function showDetail(id){
		win = showWindow("showArticle","评论详细","<c:url value='/articleComment.do?dealMethod=commentDetail&ids="+id+"'/>", 293, 0, 780, 315);
	}
</script>
</head>
<body scroll="auto" oncontextmenu="return true;" topMargin="0" marginwidth="0" marginheight="0">

<form action="<c:url value='/articleComment.do'/>" method="post" name="articleCommentForm" id="articleCommentForm">
<input type="hidden" name="dealMethod" id="dealMethod" value="Ondelete"/>
<input type="hidden" name="ids" id="strid" />
<input type="hidden" name="objContent" id="objContentId" />
<input type="hidden" name="delTag" id="delTagId" />
<div class="currLocation">功能单元→ 文章评论→评论管理→回收站</div>
	<complat:button  name="button" buttonlist="delete|0|revert" buttonalign="left"/>
	<complat:grid ids="xx" width="*,*,*"  
		head="文章标题,发表人,发表时间" 
		page="${articleCommentForm.pagination}"
		element="[1,link,onclick,showDetail]" 
		form="articleCommentForm" action="/articleComment.do"/>
        <div id="large"></div> 
</form>
</body>
</html>
