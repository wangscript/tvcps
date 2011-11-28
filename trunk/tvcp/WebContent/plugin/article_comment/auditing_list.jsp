<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>未审核文章评论</title> 
<script type="text/javascript">
function button_delete_onclick(){	//删除
	var ids = document.getElementById("xx").value;
	if(ids == "" || ids == null){
		alert("请至少选择一条记录操作！");
		return false;
	}
	if(confirm("确定要放入回收站吗？")){
		document.getElementById("strid").value = document.getElementById("xx").value;
	    $("#dealMethod").val("isDeleted");
	    $("#objContentId").val("1");
	    $("#delTagId").val("auditing");//代表从审核页面删除
		$("#articleCommentForm").submit();  
	}
}
function button_audit_onclick(){//审核
	var ids = document.getElementById("xx").value;
	if(ids == "" || ids == null){
		alert("请至少选择一条记录操作！");
		return false;
	}
	if(confirm("确定审核通过？")){
		document.getElementById("strid").value = document.getElementById("xx").value;
	    $("#dealMethod").val("audited");
	    $("#objContentId").val("1");//代表审核
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
<input type="hidden" name="dealMethod" id="dealMethod" value="Offaudited"/>
<input type="hidden" name="ids" id="strid" />
<input type="hidden" name="objContent" id="objContentId" />
<input type="hidden" name="delTag" id="delTagId" />
<div class="currLocation">功能单元→ 文章评论→ 评论管理→未审核评论</div>
	<complat:button  name="button" buttonlist="audit|0|delete" buttonalign="left"/>
	<complat:grid ids="xx" width="*,*,*,*,*,*,0"  
		head="文章标题,发表人,审核人,发表时间,是否精华,是否置顶,地址"
		coltext="[col:5,true:【精华】,false: ][col:6,true:置顶,false: ]"
		element="[1,link,onclick,showDetail]"
		page="${articleCommentForm.pagination}" form="articleCommentForm" action="/articleComment.do"/>
      <div id="large"></div> 
</form>
</body>
</html>
