<%@ page language="java" contentType="text/html; charset=utf-8"%>
<html>
<head>
<%@include file="/templates/headers/header.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>排序文章</title>
<script type="text/javascript">
	var sortOrder = "";

	$(document).ready(function() {
		var message = $("#message").val();
		var formatId = $("#formatId").val();
		if(message != null && message != ""){
			if(message == "排序完成") {
				closeWindow(rightFrame.getWin());
				rightFrame.window.location.href="<c:url value='/article.do?dealMethod=&formatId="+formatId+"&columnId=${articleForm.columnId}&operationType=article&formatId=${articleForm.formatId}'/>";
			} 
		}
	});
	
	function check() { 
		if(sortOrder == "") { 
			rightFrame.closeDetailWin();
			return false;
		}  
		var arr = sortOrder.split(",");
		// 获得要排序的栏目的新顺序
		var len = document.getElementById("sortArticle").options.length;
		var value = "";
		for(var i = 0; i < len; i++) {
			var sort = document.getElementById("sortArticle").options[i].value;
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
		$("#sortArticleIds").val(value);
		$("#dealMethod").val("sort");
		document.getElementById("articleForm").submit();
	}

	function swapOptionProperties(option1, option2) {
		// 获取要排序的文章
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
		rightFrame.closeDetailWin();
	}

	function searchArticle() {
		$("#dealMethod").val("findSortArticle");
		document.getElementById("articleForm").submit();
	}
</script>
</head>
<body>
	<form id="articleForm" action="<c:url value='/article.do'/>" method="post" name="articleForm">
		<input type=hidden  name="ids" id="strid">
		<input type="hidden" id="dealMethod" name="dealMethod" />
		<input type="hidden" name="message" id="message" value="${articleForm.infoMessage }" />
		<input type="hidden" name="sortArticleIds" id="sortArticleIds" />	
		<input type="hidden" name="columnId" id="columnId" value="${articleForm.columnId }" />	
		<input type="hidden" name="formatId" id="formatId" value="${articleForm.formatId }"/>
		<div class="form_div">
		<table width="100%">
			<tr>
				<td width="75%" align="center"> 
					<fieldset style="height:380px;width:380px;">
					<legend>文章排序列表</legend>
					从&nbsp;<input type="text" name="fromCount" style="width:40px;" id="fromCount" class="input_text_normal" value="${articleForm.fromCount }"/>
					&nbsp;&nbsp;到&nbsp;<input type="text" name="toCount"   style="width:40px;" id="toCount"   class="input_text_normal" value="${articleForm.toCount }"/>
	        		&nbsp;&nbsp;<input type="button" value="检索" onclick="searchArticle()" class="btn_small" style="margin:1px 0 10px 0;"/>
					<select id="sortArticle" name="sortArticle"  size="20" style="width:360px;" multiple>  
	            		 <c:forEach var="article" items="${articleForm.json_list}">
								<option value="${article.id }">${article.title }</option>
						</c:forEach>
	        		</select>
					</fieldset>
    			</td> 
	    		<td width="25%" align="center">   
					<table>
						<tr>
							<td>
								<input type="button" class="btn_small" value="∧" onclick="moveUp(document.articleForm.sortArticle)" style="width:50px;"/>
							</td>
						</tr> 
						<tr>
							<td>
								<input type="button" class="btn_small" value="∨" onclick="moveDown(document.articleForm.sortArticle)" style="width:50px;"/>
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