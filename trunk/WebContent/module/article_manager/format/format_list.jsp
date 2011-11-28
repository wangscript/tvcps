<%@ page language="java" contentType="text/html; charset=utf-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>格式管理</title>
<%@include file="/templates/headers/header.jsp"%>
<script type="text/javascript">
	
	$(document).ready(function() {
		var message = $("#message").val();
		if(message != "null" && message != "" && message != null && message != "删除格式成功") {
			alert(message);
		}
		if(document.getElementById("f1_0_checkbox") != null){
			// 不允许删除默认格式
			document.getElementById("f1_0_checkbox").disabled = true;
		}
	});
	
	// 显示明细
	function showDetail(id) {
		win = showWindow("formatWin","格式信息","articleFormat.do?dealMethod=detail&id="+id,293, 0, 480, 300);
	}
	
	// 设置格式属性
	function setAttr(id) {
		var fromDefault = document.getElementById("fromDefault").value;
		if(id == "f1") {
			fromDefault = "yes";
		}else{
			fromDefault = "no";
		}
		var formatName = $("#"+id+"_1").html();
		var regex = /<[^<]+>/g;
		formatName = formatName.replace(regex, "");
		
		var url = "<c:url value='articleAttribute.do?dealMethod=&formatId='/>"+id+"&fromDefault="+fromDefault+"&formatName="+ formatName;
		window.location.href = url;
	}

	// 添加格式
	function button_add_onclick(btn) {
		var url = "<c:url value='/module/article_manager/format/format_detail.jsp?"+ getUrlSuffixRandom() + "'/>" ;
		win = showWindow("addWin", "添加格式", url, 293, 0, 480, 300);
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
				document.getElementById("formatForm").submit();
			}else{
				return false;
			}
		}
	}


	//格式导入
	function button_import_onclick(ee) {
		
		var url = "<c:url value='/module/article_manager/format/format_import.jsp '/>";
		win = showWindow("importFormat", "格式导入", url, 250, 90, 500, 300);
	}

	//格式导出
	function button_export_onclick(ee) {
		var exportFormatIds = $("#ids").val();
		
		if(exportFormatIds == null || exportFormatIds == "" || exportFormatIds == "0") {
			alert("请至少选择一条记录！");
			return false;
		}
		$("#exportFormatIds").val(exportFormatIds);
		window.location.href = "<c:url value='/articleFormat.do?dealMethod=export&exportFormatIds="+ exportFormatIds+"'/>"
	}

	function closeChild() {
		closeWindow(win);
	}
	
</script>
</head>
<body>
	<div class="currLocation">格式管理</div>
	<form id="formatForm" action="<c:url value="/articleFormat.do"/>" name="articleFormatForm" method="post">
		<input type="hidden" name="dealMethod" id="dealMethod"/>
		<input type="hidden" name="message" id="message" value="${articleFormatForm.infoMessage }"/>
		<input type="hidden" name="ids" id="ids"/>
		<input type="hidden" name="fromDefault" id="fromDefault" value="no"/>
		<input type="hidden" name="exportFormatIds" id="exportFormatIds" value="">
		<complat:button name="button"  buttonlist="add|0|delete|0|import|0|export"   buttonalign="left"></complat:button>
		<complat:grid width="*,*,*,50" 
			head="格式名称,创建时间,创建者,属性"  
			element="[1,link,onclick,showDetail][4,button,onclick,setAttr,属性]"  
			page="${articleFormatForm.pagination}" 
			form="formatForm" 
			action="articleFormat.do"/>
	</form>
</body>
</html>
