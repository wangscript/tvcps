<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>
<html>  
<head>
<title> 枚举导入 </title>
<%@include file="/templates/headers/header.jsp"%>
<script type="text/javascript">
	$(document).ready(function() {
		var message = $("#message").val();
		if(message != null && message != ""){
			if(message == "导入成功") {
				closeWindow(rightFrame.getWin());
				rightFrame.window.location.href="<c:url value='/enumeration.do?dealMethod='/>";
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
	
	// 检查上传的文件是否是xml
	function checkthisform() {
		var check = 0;
		var thisobj = eval("document.all.myfile");
		var path = thisobj.value;
		$("#path").val(path);
		if (thisobj) {			
			if( thisobj.value != "" ) {
				var ext = document.all.myfile.value = GetFileExtension( thisobj.value );
				if( ext!="xml" ) {
					alert('导入枚举文件格式应为: xml ！');
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
			document.forms[0].submit();
		}
	}

	function backColumnList() {
		closeWindow(rightFrame.getWin());
	}
</script>
</head> 
<body>
<form action="<c:url value="/enumeration.do"/>" enctype="multipart/form-data" method="post" name="enumerationForm">
	<input type="hidden" name="dealMethod" id="dealMethod"/>
	<input type="hidden" name="message" id="message" value="${enumerationForm.infoMessage}" />
	
	<input type="hidden" name="path" id="path" value="" />
	<div class="form_div">
		<table style="font:12px; width:440px;">
			<tr>
				<td class="td_left" width="80px;"></td>
				<td id="description" class="td_left" width="280px;"><center>格式导入说明</center></td>
			</tr>
			<tr>
				<td class="td_left" width="80px;">□</td>
				<td width="280px;"> 导入枚举文件(文件名后缀： .xml)</td>
			</tr>
			<tr>
				<td class="td_left" width="80px;">□</td>
				<td width="280px;"> 导入格式中若包含枚举值应先导入枚举类型</td>
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