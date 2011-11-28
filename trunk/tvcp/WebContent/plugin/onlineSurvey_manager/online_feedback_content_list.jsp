<%@page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>客户调查</title>
<script type="text/javascript" src="<c:url value="/script/My97DatePicker/WdatePicker.js"/>"></script>
<script type="text/javascript">
	$(document).ready(function() {
		var message = $("#message").val();
		var questionId = $("#questionId").val();
		if(message != null && message != ""){
			rightFrame.window.location.href = "onlinefeedbackContent.do?dealMethod=&questionId="+questionId;
		}
	});

	//删除按钮
	function button_delete_onclick(ee) {
		var ids = document.getElementById("xx").value;
		if (ids == "" || ids == null) {
			alert("请至少选择一条记录操作！");
			return false;
		}
		if (confirm("你确定要删除吗？")) {
			$("#feedbackIds").val(ids);
			$("#dealMethod").val("delete");
			$("#onlinefeedbackContentForm").submit();
		} else {
			return false;
		}
	}

	function button_return_onclick(){
		var url = "onlineSurveyConcretQuestion.do?dealMethod=answerList";
		rightFrame.window.location.href = url;
	}
</script>
</head>

<body>
	<form id="onlinefeedbackContentForm" action="<c:url value='/onlinefeedbackContent.do'/>"method="post" name="onlinefeedbackContentForm">
	<input type="hidden"  name="questionId" id="questionId"  value="${onlinefeedbackContentForm.questionId}" /> 
	<input type="hidden"  name="dealMethod" id="dealMethod" /> 
	<input type="hidden"  name="feedbackIds" id="feedbackIds" />
	<input type="hidden" name="message" id="message" value="${onlinefeedbackContentForm.infoMessage}" />
	<div class="currLocation">功能单元→ 网上调查→网上信息反馈</div>
	<complat:button buttonlist="delete|0|return" buttonalign="left" />  
	<complat:grid  ids="xx" width="300" head="网上信息反馈"
		page="${onlinefeedbackContentForm.pagination}" form="onlinefeedbackContentForm" 
		action="onlinefeedbackContent.do" />
   </form>
</body>
</html>