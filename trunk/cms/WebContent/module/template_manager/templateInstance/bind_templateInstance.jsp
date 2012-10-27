<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>绑定栏目</title>
	<link rel="stylesheet" type="text/css" href="<c:url value="/script/ext/resources/css/ext-all.css"/>"/>
	<script type="text/javascript" src="<c:url value="/script/ext/adapter/ext/ext-base.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/script/ext/ext-all-debug.js"/>"></script>
<style type="text/css" media="all">
	#btn {
		margin: 20px 0 0 80px;
	}
</style>
<script>
	function button_sure_onclick(value) {
		var bind = $("#bind").val();
		// 绑定栏目(或者文章的栏目)
		if(value == "bind") {
			var columnIds = $("#tree_checkedIds").val();
			if(columnIds == $("#bindedIds").val()) {
				closeWindow(rightFrame.getWin());
				return false;
			}
			// 查找要取消绑定的栏目
			var a = columnIds.split(",");
			var b = $("#bindedIds").val().split(",");
			var canceledIds = "";
			if(columnIds != null && columnIds != "") {
				for(var i = 0; i < b.length; i++) {
					var c = 0;
					if(b[i] != "" && b[i] != null) {
						for(var j = 0; j < a.length; j++) {
							if(a[j] != "" && a[j] != null) {
								if(a[j] == b[i]) {
									c = 1; 
									break;
								}
							}
						}
						if(c == 0) {
							if(canceledIds == "") {
								canceledIds = b[i];
							} else {
								canceledIds += "," + b[i];
							}					 
						}
					}
				}
			} else {
				canceledIds = $("#bindedIds").val();
			}
			$("#canceledIds").val(canceledIds);
			$("#bindedIds").val(columnIds);
			$("#dealMethod").val("bind");
			
		// 取消绑定栏目
		} else if(value == "cancelBind") {
			$("#dealMethod").val("cancelBind");
		} 
		var options = {
			url: "<c:url value='/templateInstance.do'/>",
			success: function(msg) {
					var tmp = msg.split("##"); 
					alert(tmp[0]);
					var url = "<c:url value='/templateInstance.do?dealMethod=&templateId='/>"+tmp[2]+"&nodeId="+tmp[1]+"&"+getUrlSuffixRandom();
					rightFrame.window.location.href = url;
					closeWindow(rightFrame.getWin());
				}
		};
		$("#templateInstanceForm").ajaxSubmit(options);
	}
	
	function tree_onclick(node) {	
		document.getElementById("checkid").value = node.id;
		document.getElementById("checkname").value = node.text;
	}
	     
	function button_quit_onclick() {	
		rightFrame.closeChild();
	}
	
</script> 

</head>
<body>
<form action="<c:url value="/templateInstance.do"/>" method="post" name="templateInstanceForm" id="templateInstanceForm">
	<div id="treeboxbox_tree" style="margin-left:10px;width:260; height:300;background-color:#f5f5f5;border :1px solid Silver;scroll:auto;">
		<complat:tree  unique="column" checkbox="true" classname="Column" treeurl="column.do?dealMethod=getCheckBoxTree&nodeId=0&nodeName=null&checkedIds=${templateInstanceForm.bindedIds}"/>
	</div>
	<input type="hidden" name="checkid" id="checkid"/>
	<input type="hidden" name="checkname" id="checkname"/>

	<input type="hidden" name="dealMethod" id="dealMethod" />
	<input type="hidden" name="canceledIds"   id="canceledIds"  value="${templateInstanceForm.canceledIds }" />
	<input type="hidden" name="bind"   id="bind"  value="${templateInstanceForm.bind }" />
	<input type="hidden" name="templateInstanceId"   id="templateInstanceId"  value="${templateInstanceForm.templateInstanceId }" />
	<input type="hidden" name="bindedIds"  id="bindedIds" value="${templateInstanceForm.bindedIds }" />
	<input type="hidden" name="nodeId"  id="nodeId"  value="${templateInstanceForm.nodeId }" />
	<input type="hidden" name="templateId" id="templateId"  value="${templateInstanceForm.templateId }" />
 	<div class="form_cls">
		<ul>
			<li id="btn">
				<input class="btn_normal" type="button" name="button_sure"    value="绑定"     onClick="javascript:button_sure_onclick('bind');" >
				<input class="btn_normal" type="button" name="button_cancel"  value="取消绑定" onClick="javascript:button_sure_onclick('cancelBind');" >
			</li>
		</ul>
	</div>
</form>
</body>
</html>