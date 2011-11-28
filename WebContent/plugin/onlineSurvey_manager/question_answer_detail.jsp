<%@page language="java" contentType="text/html; charset=utf-8"%>
<html>
<head>
<title>内容调查添加</title>
<%@include file="/templates/headers/header.jsp"%>
<script type="text/javascript" src="<c:url value="/script/My97DatePicker/WdatePicker.js"/>"></script>
<script type="text/javascript" src="<c:url value="/script/My97DatePicker/WdatePicker.js"/>"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#answer").focus();
		var message = $("#message").val();
		var categoryId = $("#categoryId").val()
		var nodeId = $("#nodeId").val();
		var questionId = $("#questionId").val();
		if(message != null && message != ""){
			var url = "onlineSurveyConcretQuestion.do?dealMethod=&categoryId="+categoryId+"&nodeId="+nodeId+"&questionId="+questionId;
			rightFrame.window.location.href = url;
			closeWindow(rightFrame.getWin());
		}
	});
	
	function save(){
		if($("#answer").val().trim() == ""){
			alert("请填写答案");
			return false;
		}
		if($("#answerId").val() != null && $("#answerId").val() != ""){
			$("#dealMethod").val("modify");
		}else{
			$("#dealMethod").val("add");
		}
		$("#onlineSurveyContentAnswerForm").submit();
	}

	function back(){
		closeWindow(rightFrame.getWin());
	}
</script>
</head>                                        
<body>
<form action="onlineSurveyConcretQuestion.do"  method="post" name="onlineSurveyContentAnswerForm" id="onlineSurveyContentAnswerForm" >
	<input type="hidden" name="dealMethod" id="dealMethod"/>	
	<input type="hidden" name="message" id="message" value="${onlineSurveyContentAnswerForm.infoMessage}" />	
	<input type="hidden" name="categoryId" id="categoryId" value="${onlineSurveyContentAnswerForm.categoryId}" />	
    <input type="hidden" name="nodeId" id="nodeId" value="${onlineSurveyContentAnswerForm.nodeId}" />
    <input type="hidden" name="questionId" id="questionId" value="${onlineSurveyContentAnswerForm.questionId}"/> 
    <input type="hidden" name="answerId" id="answerId" value="${onlineSurveyContentAnswerForm.answerId}" />
	<div class="form_div">                                           
	<table style="width:100%; font:12px;">
	<tr>
		<td class="td_left"> 
        	<i>*</i>调查问题答案:
        </td>
        <td>
        	<textarea name="onlineSurveyContentAnswer.answer" id="answer" rows="7"  cols="26" value="${onlineSurveyContentAnswerForm.onlineSurveyContentAnswer.answer}" class="input_textarea_normal" valid="string">${onlineSurveyContentAnswerForm.onlineSurveyContentAnswer.answer}</textarea>
		</td>
	</tr> 
	<tr>
		<td class="td_left">
			调查投票数：
		</td>
        <td>
			<c:if test="${onlineSurveyContentAnswerForm.answerId != ''}">
				<input type="text" name="onlineSurveyContentAnswer.voteCount" style="width:50px;" id="voteCount" value="${onlineSurveyContentAnswerForm.onlineSurveyContentAnswer.voteCount}" class="input_text_normal"/>
			</c:if>
			<c:if test="${onlineSurveyContentAnswerForm.answerId == ''}">
				<input type="text" name="onlineSurveyContentAnswer.voteCount" style="width:50px;" id="voteCount" value="0" class="input_text_normal"/>
			</c:if>
		</td>
	</tr>
	<tr>	
		<td colspan="2" align="center">
			<input type="button" class="btn_normal" onclick="save()" value="保存"/>
            &nbsp;&nbsp;
			<input type="button" class="btn_normal" onclick="back()" value="退出"/>
		</td>
	</tr>
	</table>
	</div>
</form>
</body>
</html>