<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>替换词设置</title>
</head>
<script type="text/javascript">

function button_add_onclick(ee){	
	var url = "<c:url value='/plugin/article_comment/add_replace_word.jsp'/>";
	win = showWindow("addReplaceWrod","新增过滤词",url, 293, 0, 337, 136);	 
}
function button_delete_onclick(ee){	
	var ids = document.getElementById("xx").value;
	if(ids == "" || ids == null){
		alert("请至少选择一条记录操作！");
		return false;
	}
	if(confirm("确定要删除吗？")){
		document.getElementById("strid").value = ids;
	    $("#dealMethod").val("deleteReplace");
		$("#articleCommentForm").submit();  
	}else
		return false;
	}
		function show(id) {
			win = showWindow("editReplace","编辑过滤词","<c:url value='/articleComment.do?dealMethod=detailReplace&ids="+id+"'/>", 293, 0, 337, 136);
		}
</script>
<body>
<form action="<c:url value='/articleComment.do'/>" method="post" name="articleCommentForm" id="articleCommentForm">
    <div class="currLocation">功能单元→ 文章评论→ 基本设置→过滤词设置</div>
        <input type="hidden" name="dealMethod" id="dealMethod" value="replaceSetList"/>
    	<input type="hidden" name="ids" id="strid"/>
		<complat:button  name="button" buttonlist="add|0|delete" buttonalign="left"/>
		<complat:grid ids="xx" width="*,*,*"  
		head="过滤词,替换词,操作" 
		element="[1,link,onclick,show][3,button,onclick,show,修改]"
		page="${articleCommentForm.pagination}" form="articleCommentForm" action="/articleComment.do"/>
        <div id="large"></div> 
</form>
</body>
</html>