<%@page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>调查结果列表</title>
<script type="text/javascript" src="<c:url value="/script/My97DatePicker/WdatePicker.js"/>"></script>
<script type="text/javascript">

	function setFeedBack(id) {
		var questionId = $("#"+id+"_4").html();
		var url = "onlinefeedbackContent.do?dealMethod=&questionId="+questionId;
		rightFrame.window.location.href = url;
	}
	
	function setChart(id) {
		var questionId = $("#"+id+"_4").html();
		var url = "<c:url value='/onlineSurveyConcretQuestion.do?dealMethod=chart&questionId='/>"+questionId+"&"+getUrlSuffixRandom();
		win = showWindow("onlineSurvey", "柱状图表详情",	url, 0, 0, 550, 350);
	}

	function setPieChart(id){
		var questionId = $("#"+id+"_4").html();
		var url = "<c:url value='/onlineSurveyConcretQuestion.do?dealMethod=piechart&questionId='/>"+questionId+"&"+getUrlSuffixRandom();
		win = showWindow("onlineSurvey", "饼状图详情",	url, 0, 0, 550, 350);
	}
</script>
</head>
<body>
<form id="onlineSurveyContentAnswerForm" action="<c:url value='/onlineSurveyConcretQuestion.do'/>"method="post" name="onlineSurveyContentAnswerForm">
	<input type="hidden" name="dealMethod" id="dealMethod" value="answerList"/>
	<div class="currLocation">功能单元→ 网上调查→调查结果列表</div>
	<complat:grid ids="xx" width="240,300,60,0,100,100,100" head="调查主题,调查问题,总票数, ,反馈详情,柱状图详情,饼状图详情"  
		element="[5,button,onclick,setFeedBack,详情][6,button,onclick,setChart,柱状图][7,button,onclick,setPieChart,饼图]"
		page="${onlineSurveyContentAnswerForm.pagination}" form="onlineSurveyContentAnswerForm" action="onlineSurveyConcretQuestion.do"/>
</form>
</body>
</html>