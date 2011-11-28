<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>格式管理</title>
<script type="text/javascript" src="<c:url value="/script/fckeditor/editor/js/fckfilemanager.js"/>"></script>
<style type="text/css" media="all">
	
</style>
<script type="text/javascript">

	//新增还是修改
	var modifyOrAdd = "add";
	$(document).ready(function() {
		$("#enumerationName").focus();
		var message = $("#message").val();
		if(message == "添加枚举信息成功" || message == "修改枚举信息成功") {
			var url = "<c:url value='/enumeration.do?dealMethod=&" + getUrlSuffixRandom() + "'/>";
			top.document.getElementById("rightFrame").src = url;
			closeWindow(rightFrame.getWin());
		}
		if(message == "查看枚举信息") {
			//修改操作
			modifyOrAdd = "modify";
			var enumValues = document.getElementById("enumValues").value;
			var arr = enumValues.split(",");
			var selObj = document.enumerationForm.enumValuesStr;
			for(var i = 0; i < arr.length; i++) {
				if(selObj.length == 0) {
					selObj.length = 1;
				} else {
					selObj.length++;
				}
				if(arr[i] == "" || arr[i] == null) {
					//清空
					document.getElementById("enumValuesStr").options.length = 0;
				}
				selObj.options[selObj.length-1].value = arr[i];
				selObj.options[selObj.length-1].text = arr[i];
			}	
			$("#valuesStr").val("");
		}
	});



	var sortOrder = "";
	var num = "";
	function add() {
	
		var valuesStr = document.getElementById("valuesStr").value;
		if(valuesStr == "" || valuesStr == null || valuesStr.trim() == "") {
			alert("请输入枚举值");
			return false;
		}
		var selObj = document.enumerationForm.enumValuesStr;

		//判断重复
		var theObjOptions = selObj.options;
		for(var i = 0; i < theObjOptions.length; i++) {
			if(valuesStr == theObjOptions[i].text) {
				alert(valuesStr + "已经存在,不能重复添加");
				return false;
			}
		}
		
		if(selObj.length == 0) {
			selObj.length = 1;
		} else {
			selObj.length++;
		}
		if(valuesStr != "" && valuesStr != null) {
			selObj.options[selObj.length-1].value = valuesStr;
			selObj.options[selObj.length-1].text = valuesStr;
		}
		$("#valuesStr").val("");
	
	}

	function modify() {
		var selObj = document.enumerationForm.enumValuesStr;
		var valuesStr = document.getElementById("valuesStr").value;
		if(valuesStr == "" || valuesStr == null || valuesStr.trim() == "") {
			alert("请输入枚举值");
			return false;
		}
		//判断重复
		var theObjOptions = selObj.options;
		for(var i = 0; i < theObjOptions.length; i++) {
			if(valuesStr == theObjOptions[i].text) {
				alert(valuesStr + "已经存在,不能重复添加");
				return false;
			}
		}
		selObj.options[num].text = valuesStr;
		$("#valuesStr").val("");
	}
	
	function moveUp(selectObj) { 
		var theObjOptions = selectObj.options;
		for(var i = 1; i < theObjOptions.length; i++) {
			if( theObjOptions[i].selected && !theObjOptions[i-1].selected ) {
				swapOptionProperties(theObjOptions[i], theObjOptions[i-1]);
			}
		}
	} 
	
	function moveDown(selectObj) { 
		var theObjOptions = selectObj.options;
		for(var i = theObjOptions.length-2; i > -1; i--) {
			if( theObjOptions[i].selected && !theObjOptions[i+1].selected ) {
				swapOptionProperties(theObjOptions[i], theObjOptions[i+1]);
			}
		}
	}   

	function swapOptionProperties(option1, option2) {
		// 获取要排序的栏目
		var value1 = option1.value;
		var value2 = option2.value;
		var flag1 = 0;
		var flag2 = 0;
		var arr = sortOrder.split(",");
		for(var i = 0; i < arr.length; i++) {
			if(arr[i] == value1) {
				flag1 = 1;
			}
			if(arr[i] == value2) {
				flag2 = 1;
			}
		}	
		if(flag1 == 0) {
			if(sortOrder != "") {
				sortOrder += "," + value1;
			} else {
				sortOrder = value1; 
			}
		}		
		if(flag2 == 0) {
			if(sortOrder != "") {
				sortOrder += "," + value2;
			} else {
				sortOrder = value2;
			}
		}
		
		var tempValue = option1.value;
		option1.value = option2.value;
		option2.value = tempValue;		
		var tempText = option1.text;
		option1.text = option2.text;
		option2.text = tempText;
		var tempSelected = option1.selected;
		option1.selected = option2.selected;
		option2.selected = tempSelected;
	}

	function changeEnumName(selectObj) {
		var theObjOptions = selectObj.options;
		for(var i = theObjOptions.length-1; i > -1; i--) {
			if( theObjOptions[i].selected ) {
				$("#valuesStr").val(theObjOptions[i].text);
				num = i;
				return false;
			}
		}
	}

	function btn_save() {
		if (document.getElementById("enumerationName").value == null
				|| document.getElementById("enumerationName").value == "") {
			alert("请输入枚举名称");
			return false;
		}
		
		//判断枚举名称是否重复
		var name = $("#enumerationName").val();
		var names = $("#allEnumNameStr").val();
		var arr = new Array();
		arr = names.split(",");
		for ( var i = 0; i < arr.length; i++) {
			if (arr[i] == name) {
				alert("该枚举名称已存在");
				return false;
			}
		}
		
		var selectObj = document.enumerationForm.enumValuesStr;
		var theObjOptions = selectObj.options;
		var str = "";
		for(var j = 0; j < theObjOptions.length; j++) {
			str = str + theObjOptions[j].text + ",";
		}
		document.getElementById("enumValues").value = str;
	
		if(modifyOrAdd == "modify") {
			$("#dealMethod").val("modifyEnumValues");
		}
		if(modifyOrAdd == "add") {
			$("#dealMethod").val("addEnumValues");
		}
		$("#enumerationForm").submit();
	}
	
	function deleteEnumName(selectObj) {
		var theObjOptions = selectObj.options;
		//先把值取出来
		var str = "";
		for(var i = 0; i < theObjOptions.length; i++) {
			
			if( !theObjOptions[i].selected ) {
				str = str + theObjOptions[i].text + ",";
			}
		}
		//清空
		document.getElementById("enumValuesStr").options.length = 0;
		//再把值写进去
		var arr = str.split(",");
		var selObj = document.enumerationForm.enumValuesStr;
		for(var j = 0; j < arr.length-1; j++) {
			if(selObj.length == 0) {
				selObj.length = 1;
			} else {
				selObj.length++;
			}
			selObj.options[selObj.length-1].value = arr[j];
			selObj.options[selObj.length-1].text = arr[j];
		}	
		$("#valuesStr").val("");
	}
	
</script>
</head>
<body>
    <form name="enumerationForm" id="enumerationForm" action="<c:url value="/enumeration.do"/>" method="post">
    <input type="hidden" name="dealMethod" id="dealMethod" />
    <input type="hidden" name="enumerationId" id="enumerationId" value="${enumerationForm.enumerationId}" />
    <input type="hidden" name="message" id="message" value="${enumerationForm.infoMessage}" />
	<input type="hidden" name="enumValues" id="enumValues" value="${enumerationForm.enumValuesStr}" />
	<input type="hidden" name="allEnumNameStr" id="allEnumNameStr" value="${enumerationForm.allEnumNameStr}" />
	<div class="form_div">
		<table width="450px;">
			<tr height="35px;">
				<td class="td_left" width="70px;"><i>*</i>枚举名称：</td>
				<td width="130px;" colspan="2"><input type="text" name="enumerationName" class="input_text_normal" id="enumerationName" value="${enumerationForm.enumerationName}" valid="string" style="width:338px;" empty="false" tip="枚举名称不能为空 "/>
				</td>
			</tr>
			<tr height="35px;">
				<td class="td_left" width="70px;" ><i></i>枚举值：</td>
				<td width="160px;"><input type="text" name="valuesStr" class="input_text_normal" id="valuesStr" value="${enumerationForm.enumeration.name}" valid="string" style="width:200px;" empty="true" />
				</td>	
				<td width="140px;">	
					<input type="button" class="btn_small" value="添加" onclick="add()">
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" class="btn_small" value="修改" onclick="modify()">
				</td>
			</tr>
			<tr>
				<td width="280px;" colspan="2" align="right"> 
					<select name="enumValuesStr"  id="enumValuesStr"  size="20" style="width:200px;" onchange="changeEnumName(document.enumerationForm.enumValuesStr)">  
	        		</select> 
    			</td> 
	    		<td width="80px;" align="center">   
	        		<input type="button" class="btn_small" value="∧" onclick="moveUp(document.enumerationForm.enumValuesStr)" style="width:50px;"/><br/><br/>
	        		<input type="button" class="btn_small" value="∨" onclick="moveDown(document.enumerationForm.enumValuesStr)" style="width:50px;"/> <br/><br/><br/><br/>
					<input type="button" class="btn_small" value="删除" onclick="deleteEnumName(document.enumerationForm.enumValuesStr)" style="width:50px;"/>        
				</td>
			</tr>
			<tr height="50px;">
				<td colspan="3" align="center">
					<input type="button" class="btn_normal" value="保存" onclick="btn_save();"/>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="reset" class="btn_normal" value="重置" />
				</td>
			</tr>
		</table>
	</div>
 </form>
</body>
</html>
