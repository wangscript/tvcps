<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>已审核文章评论</title>
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
		    $("#delTagId").val("audited");
			$("#articleCommentForm").submit();  
		}
	}
	function button_cancelAssess_onclick(){//撤销审核
		var ids = document.getElementById("xx").value;
		if(ids == "" || ids == null){
			alert("请至少选择一条记录操作！");
			return false;
		}
		if(confirm("确定要标记未审吗？")){
			document.getElementById("strid").value = document.getElementById("xx").value;
		    $("#dealMethod").val("audited");
		    $("#objContentId").val("0");
		    $("#delTagId").val("cancelAudi");
			$("#articleCommentForm").submit();
		}
	}
	function button_essential_onclick(){//设置为精华贴
		var ids = document.getElementById("xx").value;
		if(ids == "" || ids == null){
			alert("请至少选择一条记录操作！");
			return false;
		}
		if(confirm("确定精华?")){
			document.getElementById("strid").value = document.getElementById("xx").value;
		    $("#dealMethod").val("isEssence");
		    $("#objContentId").val("1");
			$("#articleCommentForm").submit();
		}
		
	}
	function button_cancelEssential_onclick(){//撤销精华贴
		var ids = document.getElementById("xx").value;
		if(ids == "" || ids == null){
			alert("请至少选择一条记录操作！");
			return false;
		}
		if(confirm("确定撤销精华?")){
			document.getElementById("strid").value = document.getElementById("xx").value;
		    $("#dealMethod").val("isEssence");
		    $("#objContentId").val("0");
			$("#articleCommentForm").submit();
		}
	}
	function button_toped_onclick(){//置顶
		var ids = document.getElementById("xx").value;
		if(ids == "" || ids == null){
			alert("请至少选择一条记录操作！");
			return false;
		}
		if(confirm("确定置顶")){
			document.getElementById("strid").value = document.getElementById("xx").value;
			alert($("#strid").val());
		    $("#dealMethod").val("isToped");
		    $("#objContentId").val("1");//代表置顶
			$("#articleCommentForm").submit();
		}
	}
	function button_cancelTop_onclick(){//撤销置顶
		var ids = document.getElementById("xx").value;
		if(ids == "" || ids == null){
			alert("请至少选择一条记录操作！");
			return false;
		}
		if(confirm("确定撤销置顶")){
			document.getElementById("strid").value = document.getElementById("xx").value;
			alert($("#strid").val());
		    $("#dealMethod").val("isToped");
		    $("#objContentId").val("0");//代表撤销置顶
			$("#articleCommentForm").submit();
		}
	}
	function showDetail(id){
		win = showWindow("showArticle","评论详细","<c:url value='/articleComment.do?dealMethod=commentDetail&ids="+id+"'/>", 293, 0, 780, 315);
	}
	// 发布文章评论目录
	function button_publish_onclick(){
		var options = {	 	
 		    	url: "<c:url value='/articleComment.do?dealMethod=publishArticleComment'/>",
 		    success: function(msg) { 
 		    	alert(msg);		    		
 		    } 
 		};
		$('#articleCommentForm').ajaxSubmit(options);	
	}
</script>
</head>
<body scroll="auto" oncontextmenu="return true;" topMargin="0" marginwidth="0" marginheight="0">
<form action="<c:url value='/articleComment.do'/>" method="post" name="articleCommentForm" id="articleCommentForm">
<input type="hidden" name="dealMethod" id="dealMethod" value="Onaudited"/>
<input type="hidden" name="ids" id="strid" />
<input type="hidden" name="objContent" id="objContentId" />
<input type="hidden" name="delTag" id="delTagId" />
	<div class="currLocation">功能单元→ 文章评论→ 评论管理→已审核评论</div>
	<complat:button  name="button" buttonlist="essential|0|toped|0|cancelEssential|0|cancelTop|0|cancelAssess|0|delete|0|publish" buttonalign="left"/>
	<complat:grid ids="xx" width="*,*,*,*,*,*,0" 
		head="文章标题,发表人,审核人,发表时间,是否精华,是否置顶,操作" 
		coltext="[col:5,true:【精华】,false: ][col:6,true:置顶,false: ]"
		element="[1,link,onclick,showDetail]"
		page="${articleCommentForm.pagination}" form="articleCommentForm" action="/articleComment.do"/>
      <div id="large"></div> 
</form>
</body>
</html>
