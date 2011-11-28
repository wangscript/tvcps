<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>

<title>文档管理中的附件管理</title>
<style type="text/css"> 
<!-- 
a:link { 
text-decoration: none; 
} 
a:visited { 
text-decoration: none; 
} 
a:hover { 
text-decoration: none; 
} 
a:active { 
text-decoration: none; 
} 
--> 
</style>

<script type="text/javascript">

/*
		$(document).ready(function() {
			if(document.getElementById("message").value == "删除成功") {
				parent.parent.parent.reloadAccordion("/${appName}/module/document_manager/index.jsp");
			}
		});
	**/	
		function button_add_onclick(ee){	
			win = showWindow("newflash","上传附件","<c:url value='/module/document_manager/attachment/attachment_detail.jsp?nodeId=${documentForm.nodeId}'/>",293, 0,500,400);	 
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
				$("#documentForm").submit(); 
			}else{
				return false;
			}
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

		function g(){
			var ee = window.event;
			ee.returnValue = false;
			alert("请右击使用下载工具下载！");
		}
	
</script>
</head>
<body>
   <div class="currLocation">文档管理→ 附件类别→ ${documentForm.nodeNameStr }</div>
	
	<form id="documentForm" action="attachment.do" method="post" name="documentForm">
		<input type="hidden" name="dealMethod" id="dealMethod"/>
		<input type="hidden" name="nodeId" id="nodeId" value="${documentForm.nodeId }"/>
		<input type="hidden" name="message" id="message" value="${documentForm.infoMessage}"/>
        <input type="hidden" name="ids" id="strid"/>
		<complat:button name="button" buttonlist="add|0|delete" buttonalign="left"/>
		<complat:grid ids="xx" width="120,60,60,150,280" head="附件名称,附件类型,附件大小,创建时间,描述"
		page="${documentForm.pagination}" form="documentForm" action="attachment.do"/>
	</form>
</body>
</html>