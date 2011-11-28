<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<%@ page import="com.baize.ccms.biz.columnmanager.web.form.ColumnForm,com.baize.ccms.biz.columnmanager.domain.Column" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>栏目管理</title>
<script type="text/javascript" src="<c:url value="/script/My97DatePicker/WdatePicker.js"/>"></script>
<script language="javascript">

	var sortOrder = "";
	 
	$(document).ready(function() {
		$("#toCount").focus();
		var message = $("#message").val();
		if(message != null && message != ""){
			if(message == "栏目排序成功") {
				closeWindow(rightFrame.getWin());
				top.reloadAccordion("/${appName}/module/column_manager/refresh_Tree.jsp");
				rightFrame.window.location.href="<c:url value='/column.do?dealMethod=&nodeId=${columnForm.nodeId}&localNodeName=${columnForm.localNodeName}&operationType=column'/>";
			} else {
				alert("排序失败");
				closeWindow(rightFrame.getWin());
			}
		}
	});
	
	function checkValue() {
		if(sortOrder == "") { 
			rightFrame.closeChild();
			return false;
		} 
		
		var arr = sortOrder.split(",");
		// 获得要排序的栏目的新顺序
		var len = document.getElementById("sortColumn").options.length;
		var value = "";
		for(var i = 0; i < len; i++) {
			var sort = document.getElementById("sortColumn").options[i].value;
			for(var j = 0; j < arr.length; j++) {
				if(sort == arr[j]) {
					if(value == "") {
						value = sort;	
					} else {
						value = value + "," + sort;	
					}						
				}
			}
		}
		$("#ordersColumn").val(value);
		$("#dealMethod").val("sort");
		var temp = checkSubmit();
		if(temp){		
			document.columnForm.submit();void(0);	
		//	document.getElementById("columnForm").submit();
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

	function button_quit_onclick () {
		rightFrame.closeChild();
	}

	function searchColumn() {
		$("#dealMethod").val("findSortColumn");
		document.getElementById("columnForm").submit();
	}

</script>

</head>
<body>
	<form action="<c:url value="/column.do"/>" method="post" name="columnForm">
		<input type=hidden value="" name="ids" id="ids">
		<input type="hidden" id="dealMethod" name="dealMethod" />
		<input type="hidden" name="nodeId" id="nodeId" value="<%=request.getParameter("nodeId")%>"/>
		<input type="hidden" name="localNodeName" id="localNodeName" value="<%=request.getParameter("localNodeName")%>"/>
		<input type="hidden" name="message" id="message" value="${columnForm.infoMessage }" />
		<input type="hidden" name="ordersColumn" id="ordersColumn" />
		<div class="form_div">
		<table width="100%">
			<tr>
				<td width="75%" align="center"> 
					<fieldset style="height:380px;width:380px;">
					<legend>栏目排序列表</legend>
					从&nbsp;<input type="text" name="fromCount" style="width:40px;" id="fromCount" class="input_text_normal" value="${columnForm.fromCount}"/>
					&nbsp;&nbsp;到&nbsp;<input type="text" name="toCount"   style="width:40px;" id="toCount"   class="input_text_normal" value="${columnForm.toCount}"/>
	        		&nbsp;&nbsp;<input type="button" value="检索" onclick="searchColumn()" class="btn_small" style="margin:1px 0 10px 0;"/>
					<select name="sortColumn" id="sortColumn"  size="20" style="width:360px;" multiple>  
	            		 <c:forEach var="column" items="${columnForm.json_list}">
							<option value="${column.id}">${column.name}</option>
						</c:forEach>
	        		</select> 
					</fieldset>
    			</td> 
	    		<td width="25%" align="center" rowspan="2"> 
					<table>
						<tr>
							<td>
								<input type="button" class="btn_small" value="∧" onclick="moveUp(document.columnForm.sortColumn)" style="width:50px;"/>
							</td>
						</tr> 
						<tr>
							<td>
								<input type="button" class="btn_small" value="∨" onclick="moveDown(document.columnForm.sortColumn)" style="width:50px;"/>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td  colspan="2" align="center">
					<input type="button" class="btn_normal"  name="saveValue"  value="保存" onClick="checkValue()" >
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input class="btn_normal" type="button" name="button_quit"  value="退出" onClick="button_quit_onclick()" >
				</td>
			</tr>
		</table>
	</form>	
</body>
</html>

