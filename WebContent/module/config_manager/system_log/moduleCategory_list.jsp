<%@page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>模块类别管理</title>	
<script type="text/javascript" src="<c:url value="/script/ext/adapter/ext/ext-base.js"/>"></script>
<script type="text/javascript" src="<c:url value="/script/ext/ext-all-debug.js"/>"></script>	
<script>
	
	function check() { 
		var len = document.getElementById("notSelect").options.length;
		var notSelect = "";
		for(var i = 0; i < len; i++) {
			var sort = document.getElementById("notSelect").options[i].value;
			if(notSelect == "") {
				notSelect = sort;
			} else {
				notSelect = notSelect + "," + sort;	
			}
		}
		
		var ObjSelectLen = document.getElementById("ObjSelect").options.length;
		var ObjSelect = "";
		for(var j = 0; j < ObjSelectLen; j++) {
			var sort1 = document.getElementById("ObjSelect").options[j].value;
			if(ObjSelect == "") {
				ObjSelect = sort1;
			} else {
				ObjSelect = ObjSelect + "," + sort1;	
			}
		}
		$("#dealMethod").val("modifyModule");
		$("#selectedIds").val(notSelect);
		$("#notSelectIds").val(ObjSelect);
		var options = {	 
				url: "<c:url value='/moduleCategory.do'/>",
 		    	success: function(msg) {
	 		    	alert(msg);
				}
		};
		$("#moduleCategoryForm").ajaxSubmit(options);	
	}
	
	function swapOptionProperties(option1, option2) {
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
	
	function moveLeftOrRight(fromObj, toObj) {
		var fromObjOptions = fromObj.options;
		for(var i = 0; i < fromObjOptions.length; i++) {
		   if(fromObjOptions[i].selected) {
		
	       	  //toObj.appendChild(fromObjOptions[i]);
	       	  toObj[toObj.length] = new Option(fromObjOptions[i].text,fromObjOptions[i].value) ; 
	       	  fromObjOptions[i] = null; 
	          i--;
		   }
	    }
	}
	
	function moveLeftOrRightAll(fromObj, toObj) {
		var fromObjOptions = fromObj.options;
		for(var i = 0; i < fromObjOptions.length; i++) {
			fromObjOptions[0].selected = true;
			toObj.appendChild(fromObjOptions[i]);
			i--;
		}
	}
</script>
</head> 
<body>
	<div class="currLocation">模块类别管理</div>
	<form action="<c:url value="moduleCategory.do"/>" method="post" name="moduleCategoryForm" id="moduleCategoryForm">
		<input type="hidden" name="dealMethod" id="dealMethod"/>
		<input type="hidden" name="selectedIds" id="selectedIds" />
		<input type="hidden" name="notSelectIds" id="notSelectIds" />  
		<table>
		<tr>
		   	<td align="center">
				功能操作
			</td>
			<td width="30px">
			</td>
			<td align="center">
				记录日志
			</td>
		</tr>
		<tr>
			<td>
	       		<select id="notSelect" name="notSelect"  style="height:350px;width:250px" multiple> 
	            	 <c:forEach var="notSelList" items="${moduleCategoryForm.notSelectedList}">
						<option value="${notSelList.id }">${notSelList.name }</option>
					</c:forEach>
	       	    </select>
    		</td>  
    		<td width="30px">    
	        	<input align="left" type="button" value=">"  onclick="moveLeftOrRight(document.moduleCategoryForm.notSelect, document.moduleCategoryForm.ObjSelect)"    style="width:50px"><br><br>
	        	<input align="left" type="button" value="<"  onclick="moveLeftOrRight(document.moduleCategoryForm.ObjSelect, document.moduleCategoryForm.notSelect)"    style="width:50px"><br><br>
				<input align="left" type="button" value=">>" onclick="moveLeftOrRightAll(document.moduleCategoryForm.notSelect, document.moduleCategoryForm.ObjSelect)" style="width:50px"><br><br>
	       	    <input align="left" type="button" value="<<" onclick="moveLeftOrRightAll(document.moduleCategoryForm.ObjSelect, document.moduleCategoryForm.notSelect)" style="width:50px">
    		</td>  
    		<td> 
        		<select id="ObjSelect" name="ObjSelect" id="ObjSelect" style="height:350px;width:250px" multiple> 
            		 <c:forEach var="selList" items="${moduleCategoryForm.selectedList}">
							<option value="${selList.id }">${selList.name }</option>
					</c:forEach>
        		</select>
    		</td> 
		</tr>
		<tr align="center">
			<td colspan="3">  
				<input type="button" value="保存" onclick="check()" style="width:50px"/>
			</td>
		</tr>
		</table>
	</form>
</body>
</html>
