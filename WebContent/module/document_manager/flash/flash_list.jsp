<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>文档管理中的flash管理</title>
<script type="text/javascript">

	function button_add_onclick(ee){	
		win = showWindow("newflash","上传flash","<c:url value='/module/document_manager/flash/flash_detail.jsp?nodeId=${documentForm.nodeId}'/>", 293, 0, 480, 430);		 
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

	function show(id) {
		var dd = id + "_5";
		document.getElementById("url").value = $("#"+dd+"").html();
		win = showWindow("showFlash","预览flash","<c:url value='/module/document_manager/flash/showFlash.jsp'/>", 293, 0, 550, 360);
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
	
</script>
</head>
<body>
   <div class="currLocation">文档管理→ Flash类别→ ${documentForm.nodeNameStr }</div>
	
	<form id="documentForm" action="flash.do" method="post" name="documentForm">
		<input type="hidden" name="dealMethod" id="dealMethod"/>
 		<input type="hidden" name="nodeId" id="nodeId" value="${documentForm.nodeId }"/>
		<input type="hidden" name="message" id="message" value="${documentForm.infoMessage}"/>
        <input type="hidden" name="ids" id="strid"/>
		<input type="hidden" name="url" id="url"/>
		<complat:button name="button" buttonlist="add|0|delete" buttonalign="left"/>
		<complat:grid ids="xx" width="150, 60, 150, 300, 0" head="Flash名称, Flash大小, 创建时间, 描述, 路径" element="[1,link,onclick,show]"
						page="${documentForm.pagination}" form="documentForm" action="flash.do"/>
		<div id="show"></div>
	</form>
</body>
</html>