<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>文档列表</title>
<script type="text/javascript">

		$(document).ready(function() {
			if($("#message").val() == "删除成功") {
	         	top.reloadAccordion("/${appName}/module/document_manager/refresh_Tree.jsp");
	         	window.location.href = "<c:url value='/category.do?nodeId=${categoryForm.nodeId}&dealMethod='/>";
	    	} else if($("#message").val() == "要删除类别前必须先删除类别下的内容") {
				alert("你要删除的类别中，有的类别已有内容,必须先删除类别下的内容!");
				window.location.href = "<c:url value='/category.do?nodeId=${categoryForm.nodeId}&dealMethod='/>";
		    }
		});
		
		function button_add_onclick(ee){	
			var flag = document.getElementById("nodeId").value;
			var titleName = "";
			if(flag == 'f004') {
				titleName = "新增图片类别文档";
			}
			if(flag == 'f005') {
				titleName = "新增flash类别文档";
			}
			if(flag == 'f006') {
				titleName = "新增附件类别文档";
			}else if(flag == 'f007'){
				titleName = "新增js脚本类别文档";
			}
			win = showWindow("newdocument",titleName,"category.do?nodeId=${categoryForm.nodeId}&dealMethod=findCategoryName",300, 0,530,300);	 
			
		}
		
		function button_delete_onclick(ee){	
			var ids = document.getElementById("xx").value;
			if(ids == "" || ids == null){
				alert("请至少选择一条记录操作！");
				return false;
			}
			if(confirm("确定要删除吗？")){
				document.getElementById("strid").value = document.getElementById("xx").value;
			    $("#dealMethod").val("delete");
			 	$("#categoryForm").submit();
		    }
			else 
				return false;
		}

		function closeNewChild() {
			closeWindow(win);
		}
		function closeDetailChild() {
			closeWindow(win);
		}
		function closeSetChild() {		
			closeWindow(win);
		}
		function categorydetail(ids){
			var flag = document.getElementById("nodeId").value;
			var titleName = "";
		
			if(flag == 'f004') {
				titleName = "图片类别文档";
			}
			if(flag == 'f005') {
				titleName = "flash类别文档";
			}
			if(flag == 'f006') {
				titleName = "附件类别文档";
			}else if(flag == 'f007'){
				titleName = "js脚本类别文档";
			}
			win = showWindow("categorydetail",titleName,"category.do?nodeId=${categoryForm.nodeId}&dealMethod=detail&ids="+ids+"",293, 0,530,300);	 
		}
</script>
</head>
<body>
   <div class="currLocation">文档管理${ categoryForm.categoryName}</div>
	<form id="categoryForm" action="category.do" method="post" name="categoryForm">
        <input type="hidden" name="dealMethod" id="dealMethod"/>
        <input type="hidden" name="nodeId" id="nodeId" value="${categoryForm.nodeId }"/>
		<input type="hidden" name="message" id="message" value="${categoryForm.infoMessage}"/>
        <input type="hidden" name="ids" id="strid"/>
		<complat:button name="button" buttonlist="add|0|delete" buttonalign="left"/>
		<complat:grid ids="xx" width="150,150,400" head="文档名称,创建时间,文档描述" 
		element="[1,link,onclick,categorydetail]" 
		page="${categoryForm.pagination}" form="categoryForm" action="category.do"/>
	</form>
</body>
</html>