<%@ page language="java" contentType="text/html; charset=utf-8"%>
<html>
<head>
<%@include file="/templates/headers/header.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>排序属性</title>
<script type="text/javascript">

	$(document).ready(function() {
		var message = document.getElementById("message").value;
		if(message == "排序成功") {
			var formatId = document.getElementById("formatId").value;
			var fromDefault = document.getElementById("fromDefault").value;
			closeWindow(rightFrame.getWin());
			var url = "<c:url value='/articleAttribute.do?dealMethod=&formatId=" + formatId + "&fromDefault=" + fromDefault + "'/>";
			rightFrame.window.location.href = url;
			
		}
		
		//属性字符串形式：id1,name1,#id2,name2,#...
		var attributeInfoStr = document.getElementById("attributeInfoStr").value;
		var arr1 = attributeInfoStr.split("#");
		var selObj = document.articleAttributeForm.sortAttributes;
		for(var i = 0; i < arr1.length-1; i++) {
			var arr2 = arr1[i].split(",");
			
			if(selObj.length == 0) {
				selObj.length = 1;
			} else {
				selObj.length++;
			}
			selObj.options[selObj.length-1].value = arr2[0];
			selObj.options[selObj.length-1].text = arr2[1];
		}	
		
	});
	
	function check() { 
		// 获得要排序的属性的新顺序
		var len = document.getElementById("sortAttributes").options.length;
		var attributeIdStr = "";
		for(var i = 0; i < len; i++) {
			var sort = document.getElementById("sortAttributes").options[i].value;
			attributeIdStr = attributeIdStr + "," + sort;	
		}
		$("#attributeIdStr").val(attributeIdStr);
		$("#dealMethod").val("sort");
		document.getElementById("articleAttributeForm").submit();
	}

	function swapOptionProperties(option1, option2) {
		// 获取要排序的属性
		var value1 = option1.value;
		var value2 = option2.value;
		var flag1 = 0;
		var flag2 = 0;
		
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
		closeWindow(rightFrame.getWin());
	}

//	function change(obj){
//		alert(obj.value);
//	}
	
</script>
</head>
<body>
	<form id="articleAttributeForm" action="<c:url value='/articleAttribute.do'/>" method="post" name="articleAttributeForm">
		<input type="hidden"  name="attributeIdStr" id="attributeIdStr" value="">
		<input type="hidden" id="dealMethod" name="dealMethod" />
		<input type="hidden" name="message" id="message" value="${articleAttributeForm.infoMessage }" />
		<input type="hidden" name="attributeInfoStr" id="attributeInfoStr" value="${articleAttributeForm.attributeInfoStr }"/>
		<input type="hidden" name="formatId" id="formatId" value="${articleAttributeForm.formatId }" />
		<input type="hidden" id="fromDefault" name="fromDefault" value="${articleAttributeForm.fromDefault}" />
		<div class="form_div">
		<table width="100%">
			<tr>
				<td width="75%" align="center"> 
					<fieldset style="height:380px;width:380px;">
					<legend>属性排序列表</legend>
					<select id="sortAttributes" name="sortAttributes"  size="20" style="width:360px;" multiple >  
	        		</select>
					</fieldset>
    			</td> 
	    		<td width="25%" align="center">   
					<table>
						<tr>
							<td>
								<input type="button" class="btn_small" value="∧" onclick="moveUp(document.articleAttributeForm.sortAttributes)" style="width:50px;"/>
							</td>
						</tr> 
						<tr>
							<td>
								<input type="button" class="btn_small" value="∨" onclick="moveDown(document.articleAttributeForm.sortAttributes)" style="width:50px;"/>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td  colspan="2" align="center">
					<input class="btn_normal" type="button" name="button_sure"  value="保存" onClick="check()" >
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input class="btn_normal" type="button" name="button_quit"  value="退出" onClick="button_quit_onclick()" >
				</td>
			</tr>
		</table>
		</div>
	</form>	
</body>
</html>