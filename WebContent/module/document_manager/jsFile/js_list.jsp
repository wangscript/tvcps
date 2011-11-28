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
			win = showWindow("newjs","上传js脚本","<c:url value='/module/document_manager/jsFile/js_detail.jsp?nodeId=${documentForm.nodeId}'/>",293, 0,475,400);	 
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
		function show(id){
			var url= $("#"+id+"_7").html();
			win = showWindow("editJS","JS编辑器","<c:url value='/js.do?dealMethod=editjs&jspath="+url+"'/>", 293, 0, 1000, 530);
		}
</script>
</head>
<body>
   <div class="currLocation">文档管理→ js脚本类别→ ${documentForm.nodeNameStr }</div>
	<form id="documentForm" action="js.do" method="post" name="documentForm">
		<input type="hidden" name="dealMethod" id="dealMethod"/>
		<input type="hidden" name="nodeId" id="nodeId" value="${documentForm.nodeId }"/>
		<input type="hidden" name="message" id="message" value="${documentForm.infoMessage}"/>
        <input type="hidden" name="ids" id="strid"/>
		<complat:button name="button" buttonlist="add|0|delete" buttonalign="left"/>
		<complat:grid ids="xx" width="100,60,60,150,250,0,0,60" 
		head="脚本名称,脚本类型,脚本大小,创建时间,描述, , ,编辑"
		element="[8,button,onclick,show,编辑]"
		page="${documentForm.pagination}" form="documentForm" action="js.do"/>
	</form>
</body>
</html>