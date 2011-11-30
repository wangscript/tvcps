<%@page language="java" contentType="text/html; charset=utf-8"%>
<html>
<head>
<title>调查问题（添加、修改）</title>
<%@include file="/templates/headers/header.jsp"%>
 <script type="text/javascript" src="<c:url value="/script/My97DatePicker/WdatePicker.js"/>"></script>
<script type="text/javascript">
	$(document).ready(function() {	     
		$("#name").focus();
		var message = $("#message").val();
		if(message != ""){
			var  categoryId = $("#categoryId").val();
			var nodeId = $("#nodeId").val();
			var url = "onlineSurveyConcret.do?dealMethod=&nodeId="+ nodeId + "&categoryId="+categoryId;
			rightFrame.window.location.href = url;
			closeWindow(rightFrame.getWin());
			
		}else{
			var view = $("#view").val();
			if(view == "true"){
				$("#view").attr("checked", true);
			}
			var style = $("#style").val();
			if(style == "1"){
				$("#style1").attr("checked", true);
			}else if(style == "2"){
				$("#style2").attr("checked", true);
				$("#tr_feedback").css("display", "none");
			}else{
				$("#style0").attr("checked", true);
			}
			var required = $("#required").val();
			if(required == "true"){
				$("#required").attr("checked", true);
			}
			var feedback = $("#feedback").val();
			if(feedback == "true"){
				$("#feedback").attr("checked", true);
			}
		}
	});

	function save(){
		if($("#view").attr("checked") == true){
			$("#view").val("true");
		}
		if($("#style0").attr("checked") == true){
			$("#style").val("0");
			if($("#feedback").attr("checked") == true){
				$("#feedback").val("true");
			}
		}else if($("#style1").attr("checked") == true){
			if($("#feedback").attr("checked") == true){
				$("#feedback").val("true");
			}
			$("#style").val("1");
		}else{
			$("#style").val("2");
		}
		if($("#required").attr("checked") == true){
			$("#required").val("true");
		}
		var questionId = $("#questionId").val();
		if(questionId == ""){
			$("#dealMethod").val("add");
		}else{
			$("#dealMethod").val("modify");
		}
		$("#onlineSurveyContentForm").submit();
	}
	
	function back(){
		closeWindow(rightFrame.getWin());
	}

	
	function closeChild() {
		closeWindow(win);
	}

	function funCheck(){
		if($("#style0").attr("checked") == true){
			$("#tr_feedback").css("display", "");
		}else if($("#style1").attr("checked") == true){
			$("#tr_feedback").css("display", "");
		}else if($("#style2").attr("checked") == true){
			$("#tr_feedback").css("display", "none");
			$("#feedback").attr("checked", false);
		}
	}
</script>
</head>     
<body>  
<form action="<c:url value="/onlineSurveyConcret.do"/>"  method="post" name="onlineSurveyContentForm" id="onlineSurveyContentForm" >
	<input type="hidden" name="dealMethod" id="dealMethod"/>
    <input type="hidden" name="categoryId" id="categoryId" value="${onlineSurveyContentForm.categoryId}" />
	<input type="hidden" name="questionId" id="questionId" value="${onlineSurveyContentForm.questionId}" />
	<input type="hidden" name="nodeId" id="nodeId" value="${onlineSurveyContentForm.nodeId}" />
	<input type="hidden" name="message" id="message" value="${onlineSurveyContentForm.infoMessage}" /> 
	<div class="form_div">                                           
   	<table width="100%" border="0">
	<tr>
		<td class="td_left" style="width:30%"> 
			<i>*</i>调查问题：
		</td>
		<td>                                                                     
			<input type="text" name="onlineSurveyContent.name" id="name" value="${onlineSurveyContentForm.onlineSurveyContent.name}" class="input_text_normal" style="width:290px"/>
		</td>                                                                       
	</tr>
	<tr>
		<td class="td_left">
			是否显示：
		</td>
		<td>
			<input type="checkbox" name="onlineSurveyContent.viewed" id="view" value="${onlineSurveyContentForm.onlineSurveyContent.viewed}"/>
		</td>
	</tr>
	<tr>
		<td class="td_left">
			显示样式：
		</td>
		<td>		
			<input type="hidden" id="style" name="onlineSurveyContent.style" value="${onlineSurveyContentForm.onlineSurveyContent.style}"/> 		
			<input type="radio" name="onlineSurveyContent.style" id="style0" value="0" onclick="funCheck();">单选 &nbsp;&nbsp;
			<input type="radio" name="onlineSurveyContent.style" id="style1" value="1" onclick="funCheck();">多选 &nbsp;&nbsp;
			<input type="radio" name="onlineSurveyContent.style" id="style2" value="2"  onclick="funCheck();">文本
		</td>
	</tr>
	<tr> 
		<td class="td_left">
			是否必填：
		</td>
		<td>	
			<input type="checkbox" name="onlineSurveyContent.required" id="required" value="${onlineSurveyContentForm.onlineSurveyContent.required}"/>			
		</td>
	</tr>
	<tr id="tr_feedback" name="tr_feedback"   id="tr_feedback" style="display:"> 
		<td class="td_left">
			是否有反馈意见：
		</td>
		<td>	
			<input type="checkbox" name="onlineSurveyContent.feedback" id="feedback" value="${onlineSurveyContentForm.onlineSurveyContent.feedback}"/>			
		</td>
	</tr>
	<tr id="tr_colCount"> 
		<td class="td_left">
			答案显示列数：
		</td>
		<td>	 
			<input type="text" name="onlineSurveyContent.colCount"  value="${onlineSurveyContentForm.onlineSurveyContent.colCount}"  id="colCount" size='2' maxlength="1"  lass="input_text_normal" >&nbsp;列	
		</td>
	</tr>
    <tr>
		<td colspan="2" align="center">
			<input type="button" class="btn_normal" onclick="save()" value="保存" />
			&nbsp;&nbsp; 
			<input type="button" class="btn_normal" onclick="back()" value="退出" />
		</td>
	</tr>
	</table>
	</div>
</form>
</body>
</html>