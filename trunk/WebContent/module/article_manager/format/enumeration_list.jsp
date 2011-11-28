<%@ page language="java" contentType="text/html; charset=utf-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>格式管理</title>
<%@include file="/templates/headers/header.jsp"%>
<script type="text/javascript">
	$(document).ready(function(){
		if($("#message").val() == "删除失败，删除枚举中某些被引用") {
			alert($("#message").val());
		}
	});


	// 添加格式
	function button_add_onclick(btn) {
		var url = "<c:url value='/enumeration.do?dealMethod=add'/>" ;
	//	var url = "<c:url value='/module/article_manager/format/enumeration_detail.jsp?"+ getUrlSuffixRandom() + "'/>" ;
		win = showWindow("addWin", "添加枚举格式", url, 293, 0, 460, 530);
	}

	// 删除格式
	function button_delete_onclick(btn) {
		var ids = $("#ids").val();
		if (ids.isEmpty()) {
			alert("请至少选择一条记录!");
			return false;
		} else {
			if(confirm("确定删除？")) {
				$("#dealMethod").val("delete");
				document.getElementById("enumerationForm").submit();
			}else{
				return false;
			}
		}
	}

	function showDetail(id) {
		win = showWindow("formatWin","修改枚举类别","enumeration.do?dealMethod=detail&enumerationId="+id,293, 0, 460, 530);
	}

	//格式导入
	function button_import_onclick(ee) {
		
		var url = "<c:url value='/module/article_manager/format/enumeration_import.jsp '/>";
		win = showWindow("importEnum", "枚举导入", url, 250, 90, 500, 300);
	}

	//枚举导出
	function button_export_onclick(ee) {
		var exportEnumIds = $("#ids").val();
		
		if(exportEnumIds == null || exportEnumIds == "" || exportEnumIds == "0") {
			alert("请至少选择一条记录！");
			return false;
		}
		$("#exportEnumIds").val(exportEnumIds);
		window.location.href = "<c:url value='/enumeration.do?dealMethod=export&exportEnumIds="+ exportEnumIds+"'/>"
	}
	
	function closeChild() {
		closeWindow(win);
	}
	
</script>
</head>
<body>
	<div class="currLocation">枚举类型</div>
	<form id="enumerationForm" name="enumerationForm" action="<c:url value="/enumeration.do"/>"  method="post">
		<input type="hidden" name="dealMethod" id="dealMethod"/>
		<input type="hidden" name="message" id="message" value="${enumerationForm.infoMessage }"/>
		<input type="hidden" name="ids" id="ids"/>
		<input type="hidden" name="exportEnumIds" id="exportEnumIds" value="">
		<complat:button name="button"  buttonlist="add|0|delete|0|import|0|export"   buttonalign="left"></complat:button>
		<complat:grid width="300,300,0,300" 
			head="枚举名称 ,创建者, ,创建时间"  
			element="[1,link,onclick,showDetail]"  
			page="${enumerationForm.pagination}" 
			form="enumerationForm" 
			action="enumeration.do"/>
	</form>
</body>
</html>
