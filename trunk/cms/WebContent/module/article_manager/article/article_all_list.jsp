<%-- 
	功能：显示网站内所有文章
--%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>文章管理</title>
<%@include file="/templates/headers/header.jsp"%>
<script type="text/javascript">

	$(document).ready(function() {

		var formatId = $("#formatId").val();
		$("input[name='6_checkbox']").bind("click", function(){
			var str = $(this).attr("id").split("_");
			var columnId = document.getElementById("columnId").value;
			var isToped = 0;
			if($(this).attr("checked")) {
				isToped = 1;
			}
			window.location.href = "article.do?dealMethod=setTop&formatId="+formatId+"&columnId="+columnId+"&articleId="+str[1]+"&isToped="+isToped+"&operationType=article&" + getUrlSuffixRandom();
		}); 
		var message = document.getElementById("message").value;
		if(message == "添加成功" || message == "修改成功") {
			var url = "<c:url value='/article.do?dealMethod=&formatId="+formatId+"&operationType=article&" + getUrlSuffixRandom() +"'/>";
			window.location.href = url;
		}
	});

	// 显示明细
	function showDetail(id, formatId) {
		$("#formatId").val(formatId);
		$("#dealMethod").val("detail");
		$("#articleId").val(id);
		$("#articleDetailId").val(id);
		$("#articleForm").submit();
	}

	// 转向选择格式的页面
	function button_add_onclick(btn) {
		$("#dealMethod").val("getFormats");
		$("#articleForm").submit();
	}
	
	function button_delete_onclick(btn) {
		var ids = $("#ids").val();
		if (ids.isEmpty()) {
			alert("请至少选择一条记录!");
			return false;
		} else {
			if(confirm("确定删除？")) {
				$("#dealMethod").val("delete");
				$("#articleForm").submit();
			} else {
				return false;
			}
		}
	}

	// 回收站
	function button_recycle_onclick() {
		$("#dealMethod").val("recycle");
		$("#articleForm").submit();
	}

	// 发布
	function button_publish_onclick() {
		$("#columnId").val("0");
		var ids = $("#ids").val();
		if (ids.isEmpty()) {
			alert("请至少选择一条记录!");
			return false;
		} 
		$("#dealMethod").val("publish");
		$("#articleForm").submit();
	}

	// 预览文章
	function preview(id) {
		var formatId = $("#formatId").val();
		var url = "<c:url value='/article.do?dealMethod=preview&articleId="+id+"&formatId="+formatId+"&isfromAll="+document.getElementById("isfromAll").value + "'/>";
		openWindow(url,"预览",8000,5000,0,0);
	}

	function button_draft_onclick(ee) {
		var ids = $("#ids").val();
		var str = ids.split(",");
		if (ids.isEmpty()) {
			alert("请至少选择一条记录!");
		} else {
			if(confirm("确定撤稿？")) {
				$("#dealMethod").val("draft");
				$("#articleForm").submit();
			} else {
				return false;
			}
		}
	}
	
</script>
</head>
<body>
	<div class="currLocation">文章管理<complat:guide className="Column" nodeId="${articleForm.columnId}" sign="→ " ></complat:guide></div>
	
	<form id="articleForm" action="<c:url value="/article.do"/>" method="post">
		<input type="hidden" name="dealMethod" id="dealMethod"/>
		<input type="hidden" id="formatId" name="formatId" value="${articleForm.formatId}"/>
		<input type="hidden" name="columnId" id="columnId" value="0"/>
		<input type="hidden" name="message" id="message" value="${articleForm.infoMessage}">
		<input type="hidden" id="articleId" name="article.id" value=""/>
		<input type="hidden" name="ids" id="ids"/>
		<input type="hidden" name="operationType" value="article" />
		<input type="hidden" name="articleDetailId" id="articleDetailId" value=""/>
		<input type="hidden" name="isfromAll" id="isfromAll" value="yes" />
		<complat:button name="button"  buttonlist="delete|0|publish|0|draft|0|recycle"  buttonalign="left" operationList="${articleForm.operationList}"></complat:button>
		<complat:grid width="*,270,180,90,100,100,50,80,80,40" 
			head=" ,标题,显示时间,所属栏目,录入人,审核人,置顶,审核,发布 ,预览"  
		    coltext="[col:1,true:<font color='red'>引</font>,false: ][col:8,true:已审核,false:未审核][col:9,0:未发布,1:已发布,2:发布中,3:已撤稿]"
		    element="[2,link,onclick,showDetail][7,checkbox,onclick,ch][10,button,onclick,preview,预览]"  
		    param="9"
			page="${articleForm.pagination}" form="articleForm" action="article.do"/>
	</form>
</body>
</html>
