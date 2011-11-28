<%@page language="java" contentType="text/html; charset=utf-8"%>
<html>
<head>
<title>内容调查管理(包括修改和添加)</title>
<%@include file="/templates/headers/header.jsp"%>
<script type="text/javascript" src="<c:url value="/script/My97DatePicker/WdatePicker.js"/>"></script>
<script type="text/javascript" src="<c:url value="/script/My97DatePicker/WdatePicker.js"/>"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$("#onlineSurveyname").focus();
	    var message = $("#message").val();
		var categoryId = $("#categoryId").val();
		if(message != null && message != ""){
			if(message == "添加成功" || message == "修改成功"){
				rightFrame.window.location.href = "<c:url value='/onlineSurvey.do?dealMethod=&categoryId="+categoryId+"&" + getUrlSuffixRandom() + "'/>";
				closeWindow(rightFrame.getWin());
			}
		}

		/*
		var categoryid = document.getElementById("categoryId").value;
		if(document.getElementById("message").value =="1"){

		}*/	
	});
	
	//添加、修改
	function save(){
		var nodeId = $("#nodeId").val();
		if(nodeId == ""){
			$("#dealMethod").val("add");			
		}else{
			$("#dealMethod").val("modify");
		}
	    $("#onlineSurveyForm").submit();
	}
	
	function back(){
		closeWindow(rightFrame.getWin());
	}




	function closeChild(){
		closeWindow(win);
	}
	function update(){
		$("#generalSystemSetForm").submit(); 
	}	
</script>
</head>                                        
<body>
<form action="onlineSurvey.do"  method="post" name="onlineSurveyForm" id="onlineSurveyForm" >
	<input type="hidden" name="dealMethod" id="dealMethod" />
    <input type="hidden" name="categoryId" id="categoryId" value="${onlineSurveyForm.categoryId}" />
    <input type="hidden" name="message" id="message" value="${onlineSurveyForm.infoMessage}"/>
    <input type="hidden" name="nodeId" id="nodeId" value="${onlineSurveyForm.nodeId}" />

	<div class="form_div">  
	<table width="100%" border="0">
	<tr>
		<td class="td_left" width="16%">
			<i>*</i>调查主题：
		</td>
		<td>
			<input type="text" name="onlineSurvey.name" id="onlineSurveyname" class="input_text_normal" style="width:230px;"   value="${onlineSurveyForm.onlineSurvey.name}" valid="string" tip="调查内容不能为空" style="width:100%"/> 
		</td>
	</tr>
	<tr>
		<td class="td_left">
			主题描述：
		</td>
		<td>
			<textarea name="onlineSurvey.description" rows="7" cols="26" id="description" style="width:100%" class="input_textarea_normal">${onlineSurveyForm.onlineSurvey.description }</textarea>
		</td>
	</tr>
	<tr>
		<td class="td_left">
			所属类别：
		</td>
      	<c:if test="${onlineSurveyForm.categoryId=='f020002'}">
     	<td>问卷调查</td>
      	</c:if>
      	<c:if test="${onlineSurveyForm.categoryId=='f020001'}">
     	<td>一般调查</td>
      	</c:if>
	<tr>
	<tr>
		<td class="td_left">
			截止时间：
		</td>
		<td>
			<input type="text" name="onlineSurvey.stopTime" id="stopTime" readonly class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${onlineSurveyForm.endTime}"/>
		</td>
	<tr>
	<tr>
		<td colspan="2" align="center">		
			<input type="button" class="btn_normal" onclick="save()" value="保存" />&nbsp;&nbsp; 			
			<input type="button" class="btn_normal" onclick="back()" value="退出" />
		</td>
	</tr>
	</table>
	</div>
</form>
</body>
</html>