<%-- 
	功能：显示网站内某栏目下所有文章
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
			window.location.href = "<c:url value='/article.do?dealMethod=setTop&formatId="+formatId+"&columnId="+columnId+"&articleId="+str[1]+"&isToped="+isToped + "&operationType=article&" + getUrlSuffixRandom() +"'/>";
		}); 
		
		var message = document.getElementById("message").value;
		var columnId = document.getElementById("columnId").value;
		
		/*if(message == "添加成功" || message == "删除成功" || message == "修改成功" || message == "审核并保存成功" || message == "审核成功") {
			var url = "<c:url value='/article.do?dealMethod=&formatId="+formatId+"&columnId="+columnId+"&operationType=article&" + getUrlSuffixRandom() +"'/>";
			window.location.href = url;
		}*/
		if(message == "导入成功") {
			closeWindow(rightFrame.getWin());
			var url = "<c:url value='/article.do?dealMethod=&formatId="+formatId+"&columnId="+columnId+"&operationType=article&" + getUrlSuffixRandom() +"'/>";
			rightFrame.window.location.href = url;
		}
		if(message == "导入失败") {
			alert("文章格式错误,导入失败！");
			closeWindow(rightFrame.getWin());
			return false;
		}
		
	});

	// 显示明细
	function showDetail(id, formatId) {
		$("#formatId").val(formatId);
		$("#dealMethod").val("getFormats");
		$("#articleId").val(id);
		$("#articleDetailId").val(id);
		$("#articleForm").submit();
	}

	// 转向选择格式的页面
	function button_add_onclick(btn) {
		$("#dealMethod").val("getFormats");
		$("#articleDetailId").val("");
		$("#articleForm").submit();
	}
	
	function button_delete_onclick(btn) {
		var ids = $("#ids").val();
		var str = ids.split(",");
		if (ids.isEmpty()) {
			alert("请至少选择一条记录!");
		} else {
			if(confirm("确定删除？")) {
				$("#dealMethod").val("delete");
				$("#articleForm").submit();
			} else {
				return false;
			}
		}
	}

	function button_recycle_onclick() {
		$("#dealMethod").val("recycle");
		$("#articleForm").submit();
	}

	// 预览文章
	function preview(id) {
		var columnId = $("#columnId").val();
		var formatId = $("#formatId").val();
		var url = "<c:url value='/article.do?dealMethod=preview&articleId='/>"+id+'&columnId='+columnId + "&formatId="+formatId+"&isfromAll="+document.getElementById("isfromAll").value;
		openWindow(url,"预览",8000,5000,0,0);
	}

	// 审核
	function button_audit_onclick() {
		var ids = $("#ids").val();
		if (ids.isEmpty()) {
			alert("请至少选择一条记录!");
		} else {
			$("#dealMethod").val("audit");
			$("#articleForm").submit();
		}
	}

	// 呈送
	function button_present_onclick() {
		var presentArticleIds = $("#ids").val();
		if(presentArticleIds == null || presentArticleIds == "") {
			alert("请选择呈送的文章");
			return false;    
		}  
		var str = presentArticleIds.split(",");
		var i = 0;
		for(i = 0; i < str.length; i++) {
			var id = "#" + str[i] + "_1";
			var htmlCode = $(id).val();
			if(htmlCode == "<font color='red'>引</font>") {
				alert("不能呈送引用的文章");
				return false;
			}
		}
		var columnId = $("#columnId").val();   
		var formatId = $("#formatId").val();
		win = showWindow("presentArticle", "呈送文章", "<c:url value='/article.do?dealMethod=findPresentFormat&formatId="+formatId+"&columnId="+ columnId +"&presentArticleIds="+ presentArticleIds +"'/>", 0, 0, 400, 510);
	}
	
	// 文章转移
	function button_move_onclick() {
		var moveArticleIds = $("#ids").val();
		if(moveArticleIds == null || moveArticleIds == "") {
			alert("请选择转移的文章");
			return false;     
		}      
		var columnId = $("#columnId").val();   
		var formatId = $("#formatId").val();
		win = showWindow("moveArticle", "转移文章", "<c:url value='/article.do?dealMethod=findMoveFormat&formatId="+formatId+"&columnId="+ columnId +"&moveArticleIds="+ moveArticleIds +"'/>", 0, 0, 400, 480);
	}

	// 发布
	function button_publish_onclick() {
		var ids = $("#ids").val();
		if (ids.isEmpty()) {
			alert("请至少选择一条记录！");
			return false;
		} 
		$("#dealMethod").val("publish");
		$("#articleForm").submit();
	}

	
	// 文章排序 
	function button_sort_onclick() {
		var columnId = $("#columnId").val();  
		var formatId = $("#formatId").val();
		win = showWindow("sortArticle", "文章排序", "<c:url value='/article.do?dealMethod=findSortArticle&columnId="+ columnId+"'/>"+ "&formatId="+ formatId, 0, 0, 500, 450);	  
	}

	//文章导入
	function button_import_onclick(ee) {
		var columnId = $("#columnId").val();  
		var formatId = $("#formatId").val();
		var url = "<c:url value='/module/article_manager/article/article_import.jsp?columnId=" + columnId +"&formatId="+formatId+ "'/>";
		win = showWindow("importArticle", "文章导入", url, 250, 90, 500, 300);
	}

	function button_export_onclick(ee) {
		var exportArticleIds = $("#ids").val();
		var formatId = $("#formatId").val();
		if(exportArticleIds == null || exportArticleIds == "" || exportArticleIds == "0") {
			alert("请至少选择一条记录！");
			return false;
		}
		$("#exportArticleIds").val(exportArticleIds);
	//	$("#dealMethod").val("export");
	//	$("#articleForm").submit();
		window.location.href = "<c:url value='/article.do?dealMethod=export&formatId="+formatId+"&exportArticleIds="+ exportArticleIds+"'/>"
	}

	function button_draft_onclick(ee) {
		var ids = $("#ids").val();
		var str = ids.split(",");
		if (ids.isEmpty()) {
			alert("请至少选择一条记录!");
		} else {
			if(confirm("撤稿可能会耽误您几十秒时间,确定撤稿？")) {
				$("#dealMethod").val("draft");
				$("#articleForm").submit();
			} else {
				return false;
			}
		}
	}

	// 关闭修改文章的窗口
	function closeDetailWin() {		
		closeWindow(win);
	}
</script>
</head>   
<body> 
	<div class="currLocation">文章管理<complat:guide className="Column" nodeId="${articleForm.columnId}" sign="→ " ></complat:guide></div>
	<form id="articleForm" name="articleForm" action="<c:url value="/article.do"/>" method="post">
		<input type="hidden" name="dealMethod" id="dealMethod"/>
		<input type="hidden" name="columnId" id="columnId" value="${articleForm.columnId}">
		<input type="hidden" name="formatId" id="formatId" value="${articleForm.formatId}">
		<input type="hidden" name="message" id="message" value="${articleForm.infoMessage}">
		<input type="hidden" name="exportArticleIds" id="exportArticleIds" value="">
		<input type="hidden" id="articleId" name="article.id" value="">
		<input type="hidden" name="ids" id="ids"/> 
		<input type="hidden" name="isfromAll" id="isfromAll" value="no" />
		<input type="hidden" name="articleDetailId" id="articleDetailId" value=""/> 
		<input type="hidden" name="operationType" value="article" />
		<complat:button type="image" name="button"  buttonlist="add|0|delete|0|recycle|0|audit|0|present|0|move|0|sort|0|import|0|export|0|publish|0|draft" operationList="${articleForm.operationList}" buttonalign="left"></complat:button>
		<complat:grid 
		    width="*,300,240,100,100,50,80,80,0,40"
		    head=" ,标题,显示时间,录入人,审核人,置顶,审核,发布, ,预览"  
			element="[2,link,onclick,showDetail][6,checkbox,onclick,ch][10,button,onclick,preview,预览]"
			param="9" 
			coltext="[col:1,true:<font color='red'>引</font>,false: ][col:7,true:已审核,false:未审核][col:8,0:未发布,1:已发布,2:发布中,3:已撤稿]"  
			page="${articleForm.pagination}"
			form="articleForm" 
			action="article.do"/>
	</form>
</body>
</html>
