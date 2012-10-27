<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>文章呈送</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/script/ext/resources/css/ext-all.css"/>"/>
<script type="text/javascript" src="<c:url value="/script/ext/adapter/ext/ext-base.js"/>"></script>
<script type="text/javascript" src="<c:url value="/script/ext/ext-all-debug.js"/>"></script>
<style type="text/css" media="all">
	
</style>
<script>

	// 选择要呈送到的栏目
	function button_sure_onclick(ee) {
		var columnIds = $("#tree_checkedIds").val();
		if(columnIds == null || columnIds == "") {
			alert("请选择呈送栏目");
			return false;
		}
		var sameFormatColumns = $("#sameFormatColumns").val();
		var ids = columnIds.split(",");
		var arr = sameFormatColumns.split(",");
		var columnId = $("#columnId").val();
		//格式是否冲突
		for(var i = 0; i < ids.length; i++) {
			if(ids[i] == columnId) {
				alert("不能呈送到原来的栏目里面");
				return false;
			}
			for(var j = 0; j < arr.length; j++) {
				if(ids[i] != null && ids[i] != "" && arr[j] != null && arr[j] != "") {
					if(ids[i] == arr[j]) {
						alert("所选栏目中有的与当前栏目格式不一致,请重新选择");
						return false;
					}
				}
			}
		}
		$("#strid").val(columnIds);
		$("#dealMethod").val("present");
		$("#articleForm").submit();
	//	rightFrame.closeDetailWin();
	}
	
	function tree_onclick(node) {
		//新增或修改的栏目格式
		var articleFormatId = document.getElementById("presentFormatId").value;
		//选中的栏目格式
		var formatid = node.attributes.formatid;
		if(formatid != articleFormatId) {
			document.getElementById("fire").innerHTML = "<font color=\"#FF0000\">冲突</font>";
		} else {
			document.getElementById("fire").innerHTML = "<font color=\"#00FF00\">正常</font>";
		}
		return false;
	}

	function button_quit_onclick(ee) {	
		rightFrame.closeDetailWin();
	}
</script>
</head>
<body>
<form name="articleForm" method="post" id="articleForm" action="<c:url value="/article.do"/>">
	<input type="hidden" name="dealMethod" id="dealMethod" /> 
	<input type="hidden" name="columnId" id="columnId" value="${articleForm.columnId}"/>
	<input type="hidden" name="ids" id="strid" />
	<input type="hidden" name="formatId" id="formatId" value="${articleForm.formatId}">
	<input type="hidden" name="presentArticleIds" id="presentArticleIds" value="${articleForm.presentArticleIds}"/>
	<input type="hidden" name="presentFormatId" id="presentFormatId" value="${articleForm.presentFormatId}"/>
	<input type="hidden" name="sameFormatColumns" id="sameFormatColumns" value="${articleForm.sameFormatColumns}"/>
	<table style="font:12px;" >
		<tr>
			<td class="td_left"><i>&nbsp;</i>呈送方式：</td>
			<td><input type="radio" name="presentMethod" value="0">信息链接</td>
			<td><input type="radio" name="presentMethod" value="1" checked>信息实体</td>
		</tr>
		<tr>
			<td class="td_right" colspan="3"><i>&nbsp;</i>文章所在栏目格式：${articleForm.presentFormatName}</td>
		</tr>
		<tr>
			<td class="td_right" colspan="3"><i>&nbsp;</i>点击栏目名称检测是否格式冲突：<a id="fire"></a></td>
		</tr>
	</table>

	<div id="treeboxbox_tree" style="width:360px; height:370px;background-color:#f5f5f5;border :1px solid Silver;scroll:auto;">
		<complat:tree unique="column" checkbox="true"  treeurl="column.do?dealMethod=getTree&nodeId=0&nodeName=null"/>
	</div> 
	
	<table width="360px;">
		<tr>
			<td>
		<center>
			<input  type="button"  value="确定" class="btn_normal"  onClick="javascript:button_sure_onclick(this);" />		
			&nbsp;&nbsp;&nbsp;&nbsp;	
			<input  type="button"  value="退出" class="btn_normal"  name="button_quit" onClick="javascript:button_quit_onclick(this);" >
		</center></td>
		</tr>
	</table>
</form>
</body>
</html>