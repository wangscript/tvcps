<%@page language="java" contentType="text/html; charset=utf-8"%>
<html>
<head>
<title>过滤修改设置</title>
<%@include file="/templates/headers/header.jsp"%>
<script type="text/javascript"
	src="<c:url value="/script/My97DatePicker/WdatePicker.js"/>"></script>
<script type="text/javascript">
//页面加载设置 
	$(document).ready( function() {
			       if(document.getElementById("defaultSet").value=="true"){
		               //    alert("1111111111111111111111");
		        		document.getElementById("defaultOne").checked=true;	  
		           }else{
		                document.getElementById("defaultTwo").checked=true;
		           }
				//alert(document.getElementById("message").value);
					var categoryid = document.getElementById("categoryId").value;
	                 document.informationFilterForm.filterCategoryIdSelect.value=document.getElementById("filterCategoryId").value;
	                  setControl("#ffffff");
					if (document.getElementById("message").value == "1") {
						rightFrame.window.location.href = "<c:url value='/infoFilter.do?dealMethod=&categoryId="
								+ categoryid
								+ "&"
								+ getUrlSuffixRandom()
								+ "'/>";
						closeWindow(rightFrame.getWin());
					}
				});

	

	function setParent(pid, pname) {
		$("#parentid").val(pid);
		$("#pname").val(pname);
	}

	function closeChild() {
		closeWindow(win);
	}



	function back() {
		rightFrame.closeNewChild();
	}



	function help() {
		var features2 = 'dialogWidth:'
				+ 450
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
		document.all.status[state].checked = true;
		setControl(color);
		//funChangeStyle(color);
	}
	/*
	 *表单提交时检验
	 */

	/*
	 *  设置文本框能否输入
	 */
	function setControl(backcolor) {
          	if (document.getElementById("filterCategoryIdSelect").value== 1)//不替换
		{

          		document.all.field2.value="";
    			document.all.replaceField1.value="";
    			document.all.replaceField2.value="";

        		
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

			document.getElementById("field1").focus();
		}
		if (document.getElementById("filterCategoryIdSelect").value == 2 || document.getElementById("filterCategoryIdSelect").value == 6)//完全替换
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
			document.getElementById("field1").focus();
		}
		if (document.getElementById("filterCategoryIdSelect").value  == 3)//前相对替换
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
			document.getElementById("field1").focus();

		}
		if (document.getElementById("filterCategoryIdSelect").value== 4)//前相对替换
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
			document.getElementById("field1").focus();
		
		}
		if (document.getElementById("filterCategoryIdSelect").value == 5)//不替换的添加
		{
			document.all.field1.value = "";  
			document.all.field2.value= "";  
			document.all.replaceField2.value="";
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
			document.getElementById("replaceField1").focus();
		}
	}

	function checkData1() {
		if (document.getElementById("filterCategoryIdSelect").value == 1)//不替换
		{
			if (document.all.field1.value.trim() == "") {
				alert("注意：替换前字段1不可为空！");
				document.all.field1.value = "";
				return false;
			}
			return true;
		}
		if (document.getElementById("filterCategoryIdSelect").value == 2 || document.getElementById("filterCategoryIdSelect").value == 6)//完全替换
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
		if (document.getElementById("filterCategoryIdSelect").value == 3)//前相对替换
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
		if (document.getElementById("filterCategoryIdSelect").value == 4)//完全相对替换
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
		if (document.getElementById("filterCategoryIdSelect").value == 5)//不替换的添加
		{
			if (document.all.replaceField1.value.trim() == "") {
				alert("注意：替换后字段1不可为空！");
				document.all.replaceField1.value = "";
				return false;
			}
			return true;
		}
	}

	
	function update() {
		//document.getElementById("filterCategoryId").value= document.informationFilterForm.filterCategoryIdSelect.value;
		// alert(document.informationFilterForm.filterCategoryIdSelect.value);
		//document.getElementById("filterCategoryId").value=document.getElementById("informationFilter.filterCategoryId").value;
		// document.generalSystemSetForm.action =  "<c:url value='/author.do?dealMethod=K&authorId="+id+"'/>";
		//  document.generalSystemSetForm.submit();//注意submit是小写
		if (checkData1()){
			$("#informationFilterForm").submit();
		}
	}
	

        

	
</script>
</head>

<body  scroll="no"
	oncontextmenu="return true;" topMargin="0" marginwidth="0"
	marginheight="0">
<form action="infoFilter.do" method="post" name="informationFilterForm" id="informationFilterForm">
   <input type="hidden" name="dealMethod" id="dealMethod" value="update" /> 
<input type="hidden" name="categoryId" id="categoryId"  value="${informationFilterForm.categoryId}" /> 
<input type="hidden" name="message" id="message"  value="${informationFilterForm.infoMessage}" />
<input type="hidden" name="informationFilter.id" id="id"  value="${informationFilterForm.informationFilter.id}"/>
  
<input type="hidden" name="informationFilter.filterCategory.id" id="filterCategoryId"  value="${informationFilterForm.informationFilter.filterCategory.id}" />

<input type ="hidden" name="defaultSet"  id="defaultSet" value="${informationFilterForm.informationFilter.status}"/>
                                                                                        
<TABLE border="0" cellspacing="1" cellpadding="1" align="center">
	<tr height="30px">
		<td></td>
	</tr>
	<tr>
		<td><SPAN id="text_css_12">替换类型</SPAN>&nbsp;</td>
		<td>

   <select name="filterCategoryId" class="input_text_normal" id="filterCategoryIdSelect"
			style="width: 150px" onchange='setControl("#ffffff");'>
			<option value="1">不替换</option>
			<option value="2">完全替换</option>
			<option value="3">前相对替换</option>
			<option value="4">完全相对替换&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
			<option value="5">添加</option>
			<option value="6">链接替换</option>
		</select>
        &nbsp;<IMG style="CURSOR: hand" onclick="help();"
			src='images/help.gif' border=0></td>
	</tr>
	<tr>
		<td><SPAN id="text_css_12">替换前字段1</SPAN>&nbsp;</td>
		<td><INPUT TYPE="text" NAME="informationFilter.field1"
			class="input_text_normal" id="field1" style="width: 230px;"
			value="${informationFilterForm.informationFilter.field1}"
			onchange="return checkNotHigh(this,100);"></td>

	</tr>
	<tr>
		<td><SPAN id="text_css_12">替换前字段2</SPAN>&nbsp;</td>
		<td><INPUT TYPE="text" NAME="informationFilter.field2"
			class="input_text_normal" id="field2" style="width: 230px;"
			value="${informationFilterForm.informationFilter.field2}"
			onchange="return checkNotHigh(this,100);" ></td>

	</tr>
	<tr>
		<td><SPAN id="text_css_12">替换后字段1</SPAN>&nbsp;</td>
		<td><INPUT TYPE="text" NAME="informationFilter.replaceField1"
			class="input_text_normal" id="replaceField1" style="width: 230px;"
			value="${informationFilterForm.informationFilter.replaceField1}"
			onchange="return checkNotHigh(this,100);" ></td>

	</tr>
	<tr>
		<td><SPAN id="text_css_12">替换后字段2</SPAN>&nbsp;</td>
		<td><INPUT TYPE="text" NAME="informationFilter.replaceField2"
			class="input_text_normal" id="replaceField2" style="width: 230px;"
			value="${informationFilterForm.informationFilter.replaceField2}"
			onchange="return checkNotHigh(this,100);" ></td>
	</tr>
	<tr>
		<td><SPAN id="text_css_12">状态</SPAN>&nbsp;</td>
		<td>
       	<INPUT TYPE="radio" NAME="informationFilter.status"   id="defaultOne"    value="1" >启用&nbsp;&nbsp;
		<INPUT TYPE="radio" NAME="informationFilter.status"   id="defaultTwo"    value="0">禁用
		
	</tr><tr></tr><tr></tr>
	<tr align="center">
		<td></td>
		<td align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
         <input type="button" name="saveValue" class="btn_normal"
			onclick="update()" value="修改" /></td>
	</tr>
</TABLE>
<table>
</table>
</form>
</body>
</html>