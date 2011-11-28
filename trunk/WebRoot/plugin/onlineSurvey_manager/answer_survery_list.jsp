<%@page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<html>
<head>
<title>问卷调查列表</title>	
<script type="text/javascript"	src="<c:url value="/script/My97DatePicker/WdatePicker.js"/>"></script>
<script type="text/javascript"> 
	$(document).ready(function() {
	   	if($("#message").val() == "删除成功"){
	   		var categoryId = $("#categoryId").val();
			rightFrame.window.location.href = "<c:url value='/onlineSurvey.do?dealMethod=&categoryId="+categoryId+"&" + getUrlSuffixRandom() + "'/>";
		}	
	});

	//添加一般调查
	function button_add_onclick() {
		var categoryId = $("#categoryId").val();
		win = showWindow("onlineSurvey", "添加问卷调查", "onlineSurvey.do?dealMethod=onlinedetail&categoryId="+categoryId, 0, 0, 550, 350);
	}

	//修改一般调查
	function orgdetail(id) {
		var categoryId = $("#categoryId").val();
	    var url = "<c:url value='/onlineSurvey.do?dealMethod=onlinedetail&nodeId="+id+"&categoryId="+categoryId+"'/>";
		win = showWindow("onlineSurveyForm", "修改问卷调查", url,  0, 0, 550, 350);
	}
	
	//删除一般调查
	function button_delete_onclick(ee) {
		var ids = document.getElementById("xx").value;
		if (ids == "" || ids == null) {
			alert("请至少选择一条记录操作！");
			return false;
		}
		if (confirm("你确定要删除吗？")) {
			$("#nodeId").val(ids);
			$("#dealMethod").val("delete");
			$("#onlineSurveyForm").submit();
		} else {
			return false;
		}
	}
	
	function closeNewChild() {
		closeWindow(win);
	}
	
	//一般调查的调查问题
	function setAttr(id) {
		var categoryId = $("#categoryId").val();
		var url = "onlineSurveyConcret.do?dealMethod=&nodeId="+id+ "&categoryId="+categoryId;         
		window.location.href = url;
	}
</script>
</head> 
<body>
<form id="onlineSurveyForm"  action="<c:url value='/onlineSurvey.do'/>" method="post" name="onlineSurveyForm">
	<input type="hidden"  name="categoryId" id="categoryId"  value="${onlineSurveyForm.categoryId}" /> 
	<input type="hidden"  name="dealMethod" id="dealMethod"/> 
	<input type="hidden" name="message" id="message" value="${onlineSurveyForm.infoMessage}" />
	<input	type="hidden" name="nodeId" id="nodeId" />
	<div class="currLocation">功能单元→ 网上调查→问卷调查 </div>
	<complat:button  buttonlist="add|0|delete"  buttonalign="left" />  
	<complat:grid ids="xx" width="200,120,120,200,100" head="调查主题,创建人,类别,创建时间,调查问题"  
		  	coltext="[col:3,f020001:一般调查,f020002:问卷调查]"
    		element="[1,link,onclick,orgdetail][5,button,onclick,setAttr,调查问题]"  
			page="${onlineSurveyForm.pagination}" 
			form="onlineSurveyForm" 
			action="onlineSurvey.do"/>
</form>
</body>
</html>
