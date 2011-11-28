<%-- 
	功能：回收站中的文章列表
--%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>文章管理</title>
<%@include file="/templates/headers/header.jsp"%>
<script type="text/javascript">

	$(document).ready(function(){
		var checkbox = document.getElementsByName("tb_idnull");
		var len = checkbox.length;
		var i = 0;
		for(i = 0; i < len; i++) {
			var articleId = checkbox[i].value;
			var ref = document.getElementById(articleId + "_1").value;
			if(ref == "引") {
				document.getElementById(articleId + "_0_checkbox").disabled = false;					
			}
		}
	});

	// 显示明细
	function showDetail(id, formatId) {
		window.location.href = "<c:url value='/module/article_manager/article/article_detail.jsp?dealMethod=detail&id='/>" + id;
	}

	function button_delete_onclick(btn) {
		var ids = $("#ids").val();
		if (ids.isEmpty() || ids == "" || ids == null) {
			alert("请至少选择一条记录!");
			return false;
		} 
		var str = ids.split(",");
		var i = 0;
		if(confirm("确定删除？")) {
			$("#dealMethod").val("clear");
			$("#articleForm").submit();
		} else {
			return false;
		}
	}

	function button_revert_onclick() {
		var ids = $("#ids").val();
		var str = ids.split(",");
		var i = 0;    
		if (ids.isEmpty()) {
			alert("请至少选择一条记录!");
			return false;
		} else {
			// 不能还原被引用的文章
			for(i = 0; i < str.length; i++) {
			var id = "#" + str[i] + "_1";
				var htmlCode = $(id).val();
				if(htmlCode == "<font color='red'>引</font>") {
					alert("不能还原引用的文章");
					return false;
				}
			}
			$("#dealMethod").val("revert");
			$("#articleForm").submit();
		}
	}

	// 预览文章
	function preview(id) {

	}
	
</script>
</head>
<body>
	
	<div class="currLocation">文章管理→ 回收站</div>
	<form id="articleForm" action="<c:url value="/article.do"/>" method="post">
		<input type="hidden" name="dealMethod" id="dealMethod" value="recycle"/>
		<input type="hidden" name="columnId" value="${articleForm.columnId}">
		<input type="hidden" name="formatId" id="formatId" value="${articleForm.formatId}">
		<input type="hidden" name="ids" id="ids"/>
		<input type="hidden" name="isfromAll" id="isfromAll"value="${articleForm.isfromAll }">
		<complat:button name="button"  buttonlist="delete|0|revert" buttonalign="left"></complat:button>
		<complat:grid width="*,*,*,*,*,*,*,*,0" head=" ,标题,显示时间,录入人,审核人,置顶,审核,发布, ," 
			coltext="[col:1,true:<font color='red'>引</font>,false: ][col:6,true:置顶,false: ][col:7,true:已审核,false:未审核][col:8,0:未发布,1:已发布,2:发布中]"
			page="${articleForm.pagination}" form="articleForm" action="article.do"/>
	</form>
</body>
</html>
