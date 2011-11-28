<%@page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>调查问题答案列表</title>	
<script type="text/javascript"	src="<c:url value="/script/My97DatePicker/WdatePicker.js"/>"></script>
<script type="text/javascript"> 
	$(document).ready(function() {
		var categoryId = $("#categoryId").val();
		var nodeId = $("#nodeId").val();
		var questionId = $("#questionId").val();
		var message = $("#message").val();
		if(message != null && message != ""){
			var url = "onlineSurveyConcretQuestion.do?dealMethod=&categoryId="+categoryId+"&nodeId="+nodeId+"&questionId="+questionId;             
			rightFrame.window.location.href = url;
		}
	});
	
	function button_add_onclick() {	 
		var categoryId = $("#categoryId").val();
		var nodeId = $("#nodeId").val();
		var questionId = $("#questionId").val();
		var url = "onlineSurveyConcretQuestion.do?dealMethod=detail&categoryId="+categoryId+"&nodeId="+nodeId+"&questionId="+questionId;
		win = showWindow("onlineSurveyContentAnswer", "添加问题答案", url, 0, 0, 550, 300);		
	}

	
	function orgdetail(answerId){  
		var categoryId = $("#categoryId").val();
		var nodeId = $("#nodeId").val();
		var questionId = $("#questionId").val();
	    var url = "<c:url value='/onlineSurveyConcretQuestion.do?dealMethod=detail&categoryId="+categoryId+"&nodeId="+nodeId+"&questionId="+questionId+"&answerId="+answerId+"'/>";
	    win = showWindow("generalSystemSet", "调查修改", url, 0, 0, 550, 300);
	}
 
	function button_delete_onclick() {	
   		var ids = document.getElementById("xx").value;
		if(ids == "" || ids == null) {
			alert("请至少选择一条记录操作！");
			return false;
	   	}
		if(confirm("你确定要删除吗？")){
			$("#answerId").val(ids);
			$("#dealMethod").val("delete");	
	    	$("#onlineSurveyContentAnswerForm").submit();
		} else { 
 			return false;
 		}
 	}
 	
	//返回
	function button_return_onclick() {             
		var categoryId = $("#categoryId").val();
		var nodeId = $("#nodeId").val();
		var questionId = $("#questionId").val();                               
		var url = "onlineSurveyConcret.do?dealMethod=&categoryId="+categoryId+"&nodeId="+nodeId;         
		window.location.href = url;
	}
</script>
</head> 
<body>
<form id="onlineSurveyContentAnswerForm" action="<c:url value='/onlineSurveyConcretQuestion.do'/>" method="post" name="onlineSurveyContentAnswerForm">
	<input type="hidden" name="dealMethod" id="dealMethod"/>	
	<input type="hidden" name="message" id="message" value="${onlineSurveyContentAnswerForm.infoMessage}" />
	<input type="hidden" name="categoryId" id="categoryId" value="${onlineSurveyContentAnswerForm.categoryId}"/>	
	<input type="hidden" name="nodeId" id="nodeId" value="${onlineSurveyContentAnswerForm.nodeId}"/>
	<input type="hidden" name="questionId" id="questionId" value="${onlineSurveyContentAnswerForm.questionId}"/>
	<input type="hidden" name="answerId" id="answerId" value="${onlineSurveyContentAnswerForm.answerId}"/>                
	<c:if test="${onlineSurveyContentAnswerForm.categoryId=='f020001'}">
    	<div class="currLocation">功能单元→ 网上调查→一般调查 →调查问题列表→调查问题答案</div>			        
    </c:if>      
	<c:if test="${onlineSurveyContentAnswerForm.categoryId=='f020002'}">
    	<div class="currLocation">功能单元→ 网上调查→问卷调查 →调查问题列表→调查问题答案</div>			        
	</c:if>   
	<complat:button  buttonlist="add|0|delete|return"  buttonalign="left"/>  
	<complat:grid ids="xx" width="*,*" head="调查问题,投票数" 
 		element="[1,link,onclick,orgdetail]"  
		page="${onlineSurveyContentAnswerForm.pagination}" 
		form="onlineSurveyContentAnswerForm" 
		action="onlineSurveyConcretQuestion.do"/>
</form>
</body>
</html>