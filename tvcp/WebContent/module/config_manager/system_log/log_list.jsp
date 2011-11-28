<%@page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>日志管理</title>	
<script type="text/javascript" src="<c:url value="/script/ext/adapter/ext/ext-base.js"/>"></script>
<script type="text/javascript" src="<c:url value="/script/ext/ext-all-debug.js"/>"></script>	
<script>

	$(document).ready(function() {
		var message = $("#message").val();
		if(message != "" && message != null && message !="删除日志成功") {
			var url = "<c:url value='/systemLog.do?dealMethod='/>"+"&" + getUrlSuffixRandom();
			parent.changeFrameUrl("rightFrame", url);  
		}
	});

	function button_delete_onclick() { 
		$("#deletedLogIds").val($("#xx").val());
		if($("#xx").val() != "" && $("#xx").val() != null) {
			if(confirm("你确定要删除吗？")) {
				$("#dealMethod").val("delete");
				$("#systemLogForm").submit();
			} else {
				return false;
			}
		} else {
			alert("请至少选择一条记录操作");
			return false;
		}
	}

	function button_export_onclick() {
		var url = "<c:url value='/module/config_manager/system_log/export_log.jsp'/>";
		win = showWindow("exportLogs", "导出日志", url, 250, 90, 500, 370);
	}

	function button_clear_onclick() {
		if(confirm("你确定要清空吗？")) {
			$("#dealMethod").val("clear");
			$("#systemLogForm").submit();
		} else {
			return false;
		}
	}


	// 设置要显示的模块类别
	function button_set_onclick() {
		var url = "<c:url value='/moduleCategory.do?dealMethod='/>";
		win = showWindow("setModuleCategory", "设置模块类别", url, 250, 90, 600, 500);
	}
	
	function closeChild() {
		closeWindow(win);
	}
</script>
</head> 
<body>
	<div class="currLocation">系统设置→日志管理</div>
	<form id="systemLogForm" action="<c:url value="systemLog.do"/>" method="post" name="systemLogForm">
		<input type="hidden" name="dealMethod" id="dealMethod"/>
		<input type="hidden" name="message" id="message" value="${systemLogForm.infoMessage}" />
		<input type="hidden" name="deletedLogIds" id="deletedLogIds"/>
		<complat:button buttonlist="clear|0|delete|0|set|0|export"  buttonalign="left" />
		<complat:grid ids="xx" width="*,*,*,*,*,*"  head="描述,模块名称,用户,创建时间,所属网站,IP地址" 
			page="${systemLogForm.pagination}" form="systemLogForm" action="systemLog.do" />
	</form>
</body>
</html>
