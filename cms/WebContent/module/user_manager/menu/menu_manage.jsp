<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>菜单排序</title>
<script type="text/javascript">
               
	$(document).ready(function() {
		var obj = document.getElementById('showMess');
		var value = obj.value;
		if(value){
			alert(value);
		}
		var message = $("#message").val();
		if(message != "" && message != null) {
			var url = "<c:url value='/menu.do?dealMethod=findMenu'/>";
			rightFrame.window.location.href = url;
		} 
	});
	function check() { 
		var len = document.getElementById("ObjSelect").options.length;
		var value = "";
		for(var i = 0; i < len; i++) {
			var sort = document.getElementById("ObjSelect").options[i].value;
			if(value == "") {
				value = sort;
			} else {
				value = value + "," + sort;	
			}
		}
		$("#ordersMenu").val(value);
		$("#dealMethod").val("order");
		document.getElementById("menuForm").submit();
	}

	 /* private function */
	function swapOptionProperties(option1, option2) {
		//option1.swapNode(option2);
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


	  
	function moveLeftOrRight(fromObj, toObj) {
		var fromObjOptions = fromObj.options;
		for(var i = 0; i < fromObjOptions.length; i++) {
		   if(fromObjOptions[i].selected) {
               toObj.appendChild(fromObjOptions[i]);
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
	<div class="currLocation">菜单管理</div>
	<form action="<c:url value="/menu.do"/>" method="post" name="menuForm">
		<input type=hidden  name="ids" id="strid">
		<input type="hidden" id="dealMethod" name="dealMethod" />
		<input type="hidden" name="message" id="message" value="${menuForm.infoMessage }" />
		<input type="hidden" name="ordersMenu" id="ordersMenu" />
 
		<c:if test="${menuForm.list != null}">  
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  <label>说明 :&nbsp;左边选框为未选菜单项，可以设定系统缺省菜单并调整显示次序。 </label>
		<br>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<table border="0">
          <tr>
			<td></td>
			<td align="center">未选菜单</td>
			<td></td>
			<td align="center">已选菜单 </td>
			<td></td>
		  </tr>          
		  <tr>
			<td>设定菜单</td>
            <td>
	       		<select name="SrcSelect" size="6"  style="height:250px;width:250px" multiple> 
	            	 <c:forEach var="allmenu" items="${menuForm.json_list}">
						<option value="${allmenu.id }">${allmenu.name }</option>
					</c:forEach>
	       	    </select>
	    	</td> 
	    	<td width="30px">    
	        	<input align="left" type="button" class="btn_normal" value=">"  onclick="moveLeftOrRight(document.menuForm.SrcSelect, document.menuForm.ObjSelect)"    style="width:50px"><br><br>
	        	<input align="left" type="button" class="btn_normal" value="<"  onclick="moveLeftOrRight(document.menuForm.ObjSelect, document.menuForm.SrcSelect)"    style="width:50px"><br><br>
				<input align="left" type="button" class="btn_normal" value=">>" onclick="moveLeftOrRightAll(document.menuForm.SrcSelect, document.menuForm.ObjSelect)" style="width:50px"><br><br>
	       	    <input align="left" type="button" class="btn_normal" value="<<" onclick="moveLeftOrRightAll(document.menuForm.ObjSelect, document.menuForm.SrcSelect)" style="width:50px">
	    	</td>
			<td>       
        		<select name="ObjSelect" id="ObjSelect" style="height:250px;width:250px;line-height:20px;" multiple style="background-color:#FFFFFF"> 
            		 <c:forEach var="menu" items="${menuForm.list}" varStatus="status" begin="0" step="1">						 
							<option value="${menu.id }">${menu.name }</option>					 
					</c:forEach>
        		</select> 
    		</td> 
	    	<td width="30px" align="center">       
	        		<input type="button" value="∧" onclick="moveUp(document.menuForm.ObjSelect)"   class="btn_normal"  style="width:50px"><br><br> 
	        		<input type="button" value="∨" onclick="moveDown(document.menuForm.ObjSelect)" class="btn_normal"  style="width:50px"><br><br>         
	    	</td>
		 </tr>            
		<tr style="padding-top-width:20px;">
            <td></td>
			<td></td>
			<td align="center"> 
				<input type="button" value="保&nbsp;&nbsp;&nbsp;&nbsp;存" onclick="check()" class="btn_normal"/>
			</td>
			<td></td>
		</tr>
	 </table>
	</c:if>
	</form>	
</body>
<input type="hidden" value="${menuForm.infoMessage}" id="showMess">
</html>