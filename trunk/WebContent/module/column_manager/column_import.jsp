<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>
<html>  
<head>
<title> 栏目导入 </title>
<%@include file="/templates/headers/header.jsp"%>

<script type="text/javascript">
	$(document).ready(function() {
		var message = $("#message").val();
		if(message != null && message != ""){
			if(message == "栏目导入成功" || message == "粘贴成功" || message == "栏目排序成功") {
				closeWindow(rightFrame.getWin());
				top.reloadAccordion("/${appName}/module/column_manager/refresh_Tree.jsp");
				rightFrame.window.location.href="<c:url value='/column.do?dealMethod=&nodeId=${columnForm.nodeId}&localNodeName=${columnForm.localNodeName}&operationType=column'/>";
			} else {
				alert(message);
				closeWindow(rightFrame.getWin());
			}
		}
	});
	
	//获取文件的扩展名
	function GetFileExtension(name) {
		var ext = name.substring(name.lastIndexOf(".") + 1, name.length);
		return ext.toLowerCase();        
	}
	
	// 检查上传的文件是否是excel
	function checkthisform() {
		var check = 0;
		var thisobj = eval("document.all.myfile");
		if (thisobj) {			
			if( thisobj.value != "" ) {
				var ext = document.all.myfile.value = GetFileExtension( thisobj.value );
				if( ext!="xls" ) {
					alert('导入栏目文件格式应为: xls ！');
					return false;
				}
			}
			if(thisobj.value) {
				check = 1;
			}
		}
		if (!check) {
			alert("请选择文件！");
			return false;
		}
		return true;
	}

	function checkImport(ee){
		document.all("dealMethod").value = "import";
		//上传文件时检查是excel时提交表单
		if( checkthisform() ) {
		//	document.forms[0].submit();
			$("#columnForm").submit();
		}
	}

	function backColumnList() {
		rightFrame.closeChild();
	}
</script>
</head> 
<body>
<form action="<c:url value="/column.do"/>" enctype="multipart/form-data" method="post" name="columnForm" id="columnForm">
	<input type="hidden" name="dealMethod" id="dealMethod"/>
	<input type="hidden" name="message" id="message" value="${columnForm.infoMessage}" />
	<input type="hidden" name="nodeId" id="nodeId" value="<%=request.getParameter("nodeId")%>"/>
	<input type="hidden" name="localNodeName" id="localNodeName" value="<%=new String(request.getParameter("localNodeName").getBytes("ISO-8859-1"),"utf-8")%>"/>
	<div class="form_div">
		<table style="font:12px; width:440px;">
			<tr>
				<td class="td_left" width="80px;"></td>
				<td id="description" class="td_left" width="280px;"><center>栏目导入说明</center></td>
			</tr>
			<tr>
				<td class="td_left" width="80px;">□</td>
				<td width="280px;"> 导入栏目文件(文件名后缀： .xls)</td>
			</tr>
			<tr>
				<td class="td_left" width="80px;">□</td>
				<td width="280px;"> Excel文件一行只允许有一条数据</td>
			</tr>
			<tr>
				<td class="td_left" width="80px;">□</td>
				<td width="280px;"> Excel文件不允许有空行，除了第一行</td>
			</tr>
			<tr>
				<td class="td_left" width="80px;">□</td>
				<td width="280px;"> 上传文件大小不能超过10M！</td>
			</tr>
			
			<tr> 
				<td class="td_left" width="80px;"><i>*</i>文件：</td>
				<td width="280px;"><input type="file" id="myfile" name="myfile" class="input_text_normal" style="width:369px;" onkeydown="return false;"/></td>
			</tr>
			<tr><td><i>&nbsp;</i></td></tr> 
			<tr> 
				<td colspan="2" align="center">
					<input type="button" class="btn_normal" value="导入" onclick="checkImport()"/>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" class="btn_normal" value="返回" onclick="backColumnList()"/>
				</td>
			</tr>
		</table>
	</div>
</form>
</body>
</html>