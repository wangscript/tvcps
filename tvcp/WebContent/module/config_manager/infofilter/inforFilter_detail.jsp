<%@page language="java" contentType="text/html; charset=utf-8"%>
<html>
<head>
<title>新增过滤类型</title>
<%@include file="/templates/headers/header.jsp"%>
<script type="text/javascript"
	src="<c:url value="/script/My97DatePicker/WdatePicker.js"/>"></script>
<script type="text/javascript">
	$(document).ready( function() {
		var categoryid = document.getElementById("categoryId").value;
		document.getElementById("field1").focus();

		//设置文本框是否能编辑
		document.all.field1.disabled = false;
		document.all.field2.disabled = true;  //
		document.all.replaceField1.disabled = true;  //
		document.all.replaceField2.disabled = true;// false
		//设置文本框的背影色
		document.all.field1.style.background = "#ffffff";
		document.all.field2.style.background = "#e7e3e7";
		document.all.replaceField1.style.background = "#e7e3e7";
		document.all.replaceField2.style.background = "#e7e3e7";
		if (document.getElementById("message").value == "0") {
			alert("替换前字段1与替换后字段1的组合已存在！")
			rightFrame.window.location.href = "<c:url value='/infoFilter.do?dealMethod=&categoryId="
					+ categoryid
					+ "&"
					+ getUrlSuffixRandom()
					+ "'/>";
			closeWindow(rightFrame.getWin());
		}
		if (document.getElementById("message").value == "2") {
			alert("替换前字段1与替换前字段2的组合已存在！")
			rightFrame.window.location.href = "<c:url value='/infoFilter.do?dealMethod=&categoryId="
					+ categoryid
					+ "&"
					+ getUrlSuffixRandom()
					+ "'/>";
			closeWindow(rightFrame.getWin());
		}
		if (document.getElementById("message").value == "1") {
			rightFrame.window.location.href = "<c:url value='/infoFilter.do?dealMethod=&categoryId="
					+ categoryid
					+ "&"
					+ getUrlSuffixRandom()
					+ "'/>";
			closeWindow(rightFrame.getWin());
		}
		var columnId = $("#columnId").val();
		if (columnId != "" && columnId != 0) {
			$("#articleId").attr("readonly", true);
		}
	});

	function closeChild() {
		closeWindow(win);
	}

	function back() {
		rightFrame.closeNewChild();
	}

	/**
	 *	[功    能] 过滤的js操作文件
	 **/

	/**
	 * 返回列表页面
	 **/
	function gotoback(url) {
		location.href = encodeURI(url);
	}
	/**
	 * 进入新增页面
	 **/
	function gotonewone(url) {
		location.href = url + "?fn_billstatus=A";
	}

	/*
	 *页面加载时触发
	 */
	function fun(style, state, color) {
		document.all.filterCategoryId.value = style;
	}
	/*
	 *  设置文本框能否输入
	 */
	function setControl(backcolor) {
		if (document.all.filterCategoryId.value == 1)//不替换
		{
			//设置文本框是否能编辑
			document.all.field2.value="";
			document.all.replaceField1.value="";
			document.all.replaceField2.value="";
			
			document.all.field1.disabled = false;
			document.all.field2.disabled = true;  //
			document.all.replaceField1.disabled = true;  //
			document.all.replaceField2.disabled = true;// false
			//设置文本框的背影色
			document.all.field1.style.background = backcolor;
			document.all.field2.style.background = "#e7e3e7";
			document.all.replaceField1.style.background = "#e7e3e7";
			document.all.replaceField2.style.background = "#e7e3e7";

			document.getElementById("field2").focus();
		}
		if (document.all.filterCategoryId.value == 2 || document.all.filterCategoryId.value == 6)//完全替换
		{
			document.all.field2.value="";
			document.all.replaceField2.value="";
			//设置文本框是否能编辑
			document.all.field1.disabled = false;
			document.all.field2.disabled = true;  //
			document.all.replaceField1.disabled = false;  //
			document.all.replaceField2.disabled = true;//
			//设置文本框是否能编辑
			document.all.field1.style.background = backcolor;
			document.all.field2.style.background = "#e7e3e7";
			document.all.replaceField1.style.background = backcolor
			document.all.replaceField2.style.background = "#e7e3e7";
			
		}
		if (document.all.filterCategoryId.value == 3)//前相对替换
		{
			document.all.replaceField2.value="";
			//设置文本框是否能编辑
			document.all.field1.disabled = false;
			document.all.field2.disabled = false;
			document.all.replaceField1.disabled = false;
			document.all.replaceField2.disabled = true;  //
			//设置文本框是否能编辑
			document.all.field1.style.background = backcolor;
			document.all.field2.style.background = backcolor;
			document.all.replaceField1.style.background = backcolor;
			document.all.replaceField2.style.background = "#e7e3e7";
			document.all.replaceField2.style.background = "#e7e3e7";
		}
		if (document.all.filterCategoryId.value == 4)//前相对替换
		{
			//设置文本框是否能编辑
			document.all.field1.disabled = false;
			document.all.field2.disabled = false;
			document.all.replaceField1.disabled = false;
			document.all.replaceField2.disabled = false;
			//设置文本框是否能编辑
			document.all.field1.style.background = backcolor;
			document.all.field2.style.background = backcolor;
			document.all.replaceField1.style.background = backcolor;
			document.all.replaceField2.style.background = backcolor;
		}
		if (document.all.filterCategoryId.value == 5)//不替换的添加
		{
			document.all.field1.value ="";   //
			document.all.field2.value = "";   //
			document.all.replaceField2.value = "";//
			//设置文本框是否能编辑
			document.all.field1.disabled = true;   //
			document.all.field2.disabled = true;   //
			document.all.replaceField1.disabled = false;
			document.all.replaceField2.disabled = true;//
			//设置文本框是否能编辑
			document.all.field1.style.background = "#e7e3e7";
			document.all.field2.style.background = "#e7e3e7";
			document.all.replaceField1.style.background = backcolor;
			document.all.replaceField2.style.background = "#e7e3e7";
		}
	}
	/*
	 *表单提交时检验
	 */
	function checkData() {
		if (document.all.filterCategoryId.value == 1)//不替换
		{
			if (document.all.field1.value.trim() == "") {
				alert("注意：替换前字段1不可为空！");
				document.all.field1.value = "";
				return false;
			}
			return true;
		}
		if (document.all.filterCategoryId.value == 2 || document.all.filterCategoryId.value == 6)//完全替换
		{
			if (document.all.field1.value.trim() == "") {
				alert("注意：替换前字段1不可为空！");
				document.all.field1.value = "";
				return false;
			}
			if (document.all.replaceField1.value.trim() == "") {
				alert("注意：替换后字段1不可为空！");
				document.all.replaceField1.value = "";
				return false;
			}
			return true;
		}
		if (document.all.filterCategoryId.value == 3)//前相对替换
		{
			if (document.all.field1.value.trim() == "") {
				alert("注意：替换前字段1不可为空！");
				document.all.field1.value = "";
				return false;
			}
			if (document.all.field2.value.trim() == "") {
				alert("注意：替换前字段2不可为空！");
				document.all.field2.value = "";
				return false;
			}
			if (document.all.replaceField1.value.trim() == "") {
				alert("注意：替换后字段1不可为空！");
				document.all.replaceField1.value = "";
				return false;
			}
			return true;
		}
		if (document.all.filterCategoryId.value == 4)//完全相对替换
		{
			if (document.all.field1.value.trim() == "") {
				alert("注意：替换前字段1不可为空！");
				document.all.field1.value = "";
				return false;
			}
			if (document.all.field2.value.trim() == "") {
				alert("注意：替换前字段2不可为空！");
				document.all.field2.value = "";
				return false;
			}
			if (document.all.replaceField1.value.trim() == "") {
				alert("注意：替换后字段1不可为空！");
				document.all.replaceField1.value = "";
				return false;
			}
			if (document.all.replaceField2.value.trim() == "") {
				alert("注意：替换后字段2不可为空！");
				document.all.replaceField2.value = "";
				return false;
			}
			return true;
		}
		if (document.all.filterCategoryId.value == 5)//不替换的添加
		{
			if (document.all.replaceField1.value.trim() == "") {
				alert("注意：替换后字段1不可为空！");
				document.all.replaceField1.value = "";
				return false;
			}
			return true;
		}
	}
	function help() {
		var features2 = 'dialogWidth:'
				+ 500
				+ 'px;'
				+ 'dialogHeight:'
				+ 640
				+ 'px;'
				+ 'dialogLeft:'
				+ 500
				+ 'px;'
				+ 'dialogTop:'
				+ 60
				+ 'px;'
				+ 'directories:no; location:no; menubar:no; status=no; toolbar=no;scrollbars:yes;Resizeable=no';
		window.showModalDialog("module/config_manager/infofilter/help.jsp",
				window, features2);
	}
	
	//键盘事件，控制只能输入数字
	function funKeyDown(event) {
		if ((event.keyCode < 48 || event.keyCode > 57) && event.keyCode != 37
				&& event.keyCode != 39 && event.keyCode != 46
				&& event.keyCode != 8 && event.keyCode != 189
				&& event.keyCode != 109
				&& (event.keyCode < 96 || event.keyCode > 105)) {
			return false;
		} else {
			return true;
		}
	}
	function emptyText() {
		var length = document.FormName.elements.length;
		for ( var i = 0; i < length; i++) {
			if (document.FormName.elements[i].type == "text") {
				document.FormName.elements[i].value = "";
			}
		}
	}

	function save() {
        document.getElementById("informationFilter.filterCategoryId").value= document.informationFilter.filterCategoryId.value;
		if (checkData()){
		   var kk=document.getElementById("categoryId").value ;
		   $("#informationFilter").submit();
		}
	}

	function clearSpace(obj){
		obj.value = obj.value.trim();
	}
</script>
</head>

<body  scroll="no"
	oncontextmenu="return true;" topMargin="0" marginwidth="0"
	marginheight="0">
<form action="infoFilter.do" method="post" name="informationFilter"
	id="informationFilter"><input type="hidden" name="dealMethod"
	id="dealMethod" value="add" />
 <input type ="hidden" name="categoryId"  id="categoryId" value="${informationFilterForm.categoryId}"/>
<input type="hidden" name="message" id="message" value="${informationFilterForm.infoMessage}"/>
<input type="hidden" name="informationFilter.filterCategory.id" id="informationFilter.filterCategoryId"  value="${informationFilterForm.informationFilter.filterCategory.id}" />

<TABLE border="0" cellspacing="1" cellpadding="1" align="center">
	<tr height="30px">
		<td></td>
	</tr>
	<tr>
		<td><SPAN id="text_css_12">替换类型</SPAN>&nbsp;</td>
		<td><select name="filterCategoryId" class="input_text_normal"
			style="width: 150px" onchange="setControl('')">
			<option value="1">不替换</option>
			<option value="2">完全替换</option>
			<option value="3">前相对替换</option>
			<option value="4">完全相对替换&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
			<option value="5">添加</option>
			<option value="6">链接替换</option>
		</select>&nbsp;<IMG style="CURSOR: hand" onclick="help();"
			src='images/help.gif' border=0></td>
	</tr>
	<tr>

		<td><SPAN id="text_css_12">替换前字段1</SPAN>&nbsp;</td>
		<td><INPUT TYPE="text" NAME="informationFilter.field1" class="input_text_normal" id="field1"
			style="width: 230px;" value="${informationFilter.field1}"
			onchange="return checkNotHigh(this,100);"  onblur="clearSpace(this)"></td>

	</tr>
	<tr>
		<td><SPAN id="text_css_12">替换前字段2</SPAN>&nbsp;</td>
		<td><INPUT TYPE="text" NAME="informationFilter.field2" class="input_text_normal" id="field2"
			style="width: 230px;" value="${informationFilter.field2}"
			onchange="return checkNotHigh(this,100);"  onblur="clearSpace(this)"></td>

	</tr>
	<tr>
		<td><SPAN id="text_css_12">替换后字段1</SPAN>&nbsp;</td>
		<td><INPUT TYPE="text" NAME="informationFilter.replaceField1" class="input_text_normal" id="replaceField1"
			style="width: 230px;" value="${informationFilter.replaceField1}"
			onchange="return checkNotHigh(this,100);"  onblur="clearSpace(this)"></td>

	</tr>
	<tr>
		<td><SPAN id="text_css_12">替换后字段2</SPAN>&nbsp;</td>
		<td><INPUT TYPE="text" NAME="informationFilter.replaceField2" class="input_text_normal" id="replaceField2"
			style="width: 230px;" value="${informationFilter.replaceField2}"
			onchange="return checkNotHigh(this,100);" onblur="clearSpace(this)"></td>

	</tr>
	<tr>
		<td><SPAN id="text_css_12">状态</SPAN>&nbsp;</td>
		<td>
		<INPUT TYPE="radio" NAME="informationFilter.status" value="1" checked>启用&nbsp;&nbsp;
		<INPUT TYPE="radio" NAME="informationFilter.status" value="0">禁用
	</tr>
	<tr>
		<td></td>
		<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
         <input type="button" name="saveValue" class="btn_normal"
			onclick="save()" value="保存" /></td><td></td>
	</tr>
</TABLE>
<table>
</table>
</form>
</body>
</html>