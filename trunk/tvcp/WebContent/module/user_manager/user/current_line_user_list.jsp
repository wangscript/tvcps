<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Expires" CONTENT="0"> 
<meta http-equiv="Cache-Control" CONTENT="no-cache"> 
<meta http-equiv="Pragma" CONTENT="no-cache"> 

<title>当前在线用户</title>
<script>	
	$(document).ready(function() {		
		var date = new Date();
		var d = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + "&nbsp;" + date.getHours() + ":" + date.getMinutes();
		$("#currentDate").html(d);
	});
	
	/** 关闭窗口 */
	function closeNewChild(){
		closeWindow(win);
	}

	function flush() {
		window.location.reload();
	}	

</script>
</head>
<body>
	
	<form id="userForm" action="<c:url value="/urer.do"/>" method="post" name="userForm">
	<div class="currLocation"><span id="currentDate"></span>&nbsp;&nbsp;在线用户数：<font color="red">${userForm.currentLineCount}</font>&nbsp;位
		<span style="float:right;margin-top:-1.7em;margin-right:1px;text-align:right;"><input type="button" value="刷新" align="right" class="btn_normal" onclick="flush()"/></span>
	</div>
	<br/>
		<table width="100%"  cellspacing="1" bgcolor="#f5f8ff" id="grid_table">
			<tr class="tr_head">
				<td class="tr_head td">
					用户名
				</td>
				<td class="tr_head td">
					所属机构
				</td>
				<td class="tr_head td">
					访问IP
				</td>
			</tr>
			<c:forEach var="list" items="${userForm.currentLineUserList}">
				<tr>
					<td>
						${list[1]}
					</td>
					<td>
						${list[2]}
					</td>
					<td>
						${list[3] }
					</td>
				</tr>
			</c:forEach>
		</table>
	</form> 
</body>
</html>