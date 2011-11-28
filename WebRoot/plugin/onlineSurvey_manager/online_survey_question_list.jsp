<%@page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>调查问题列表</title>
<script type="text/javascript"	src="<c:url value="/script/My97DatePicker/WdatePicker.js"/>"></script>
<script type="text/javascript">
	$(document).ready(function() {				
		var categoryId = $("#categoryId").val();
		var nodeId = $("#nodeId").val();
	   	if($("#message").val() != null && $("#message").val() != ""){
			var url = "onlineSurveyConcret.do?dealMethod=&nodeId="+nodeId+"&categoryId="+categoryId;
			rightFrame.window.location.href = url;
	   	}		
	});

	function button_add_onclick() {	 
	    var nodeId = $("#nodeId").val();             
 	    var categoryId = $("#categoryId").val();
        win = showWindow("onlineSurvey", "添加调查问题", "onlineSurveyConcret.do?dealMethod=detail&nodeId="+nodeId+"&categoryId="+categoryId, 0, 0, 420, 300);		 
 	}
 	
	function orgdetail(id){
		var nodeId = $("#nodeId").val();  
	  	var categoryId = $("#categoryId").val();
     	var url = "<c:url value='/onlineSurveyConcret.do?dealMethod=detail&nodeId="+nodeId+"&questionId="+id+"&categoryId="+categoryId+"'/>";   
     	win = showWindow("onlineSurveyForm", "修改调查问题", url,  250, 90, 450, 300);
 	}

	function button_delete_onclick() {	
 		var ids = document.getElementById("xx").value;
 	 	if(ids == "" || ids == null) {
 			alert("请至少选择一条记录操作！");
 			return false;
 	   	}
 		if(confirm("你确定要删除吗？")){
 			$("#questionId").val(ids);
 			$("#dealMethod").val("delete");
 		    $("#onlineSurveyContentForm").submit(); 
 		} else { 
 	 		return false;
 	 	}
 	}

	function button_return_onclick() {
		var categoryId = $("#categoryId").val();
		var nodeId = $("#nodeId").val();
		var url = "<c:url value='/onlineSurvey.do?dealMethod=&nodeId="+nodeId+"&categoryId="+categoryId+"&" + getUrlSuffixRandom() + "'/>";
		window.location.href = url;
	}
 	
	//设置问题答案
	function setAttr(questionId) {
		var style = $("#"+questionId+"_3").val();
		if(style == "文本"){
			alert("文本样式没有答案");
			return false;
		}
		var categoryId = $("#categoryId").val();
		var nodeId = $("#nodeId").val();
		var url = "onlineSurveyConcretQuestion.do?dealMethod=&categoryId="+categoryId+"&nodeId="+nodeId+"&questionId="+questionId;
        window.location.href = url;
	}	
</script> 
</head> 
<body>
<form id="onlineSurveyContentForm" action="<c:url value="/onlineSurveyConcret.do"/>" method="post" name="onlineSurveyContentForm">
	<input type="hidden" name="dealMethod" id="dealMethod" />
	<input type="hidden" name="categoryId" id="categoryId" value="${onlineSurveyContentForm.categoryId}" />
	<input type="hidden" name="nodeId"     id="nodeId"     value="${onlineSurveyContentForm.nodeId}" />
	<input type="hidden" name="questionId" id="questionId" value="${onlineSurveyContentForm.questionId}" />
	<input type="hidden" name="message"    id="message"    value="${onlineSurveyContentForm.infoMessage}" />
	<c:if test="${onlineSurveyContentForm.categoryId=='f020002'}">
		<div class="currLocation">功能单元→ 网上调查→问卷调查 →问题列表</div>			        
	</c:if>      
	<c:if test="${onlineSurveyContentForm.categoryId=='f020001'}">
		<div class="currLocation">功能单元→ 网上调查→一般调查 →问题列表</div>			        
	</c:if>    
	<complat:button buttonlist="add|0|delete|return"  buttonalign="left" />
	<complat:grid ids="xx" width="120,60,60,150,80,80" head="调查问题,是否显示,显示样式,所属类别,截至日期,设置答案"  
		element="[1,link,onclick,orgdetail][6,button,onclick,setAttr,答案]"  
		coltext="[col:2,true:是,false:否][col:3,0:单选,1:多选,2:文本]"
		page="${onlineSurveyContentForm.pagination}"  
		form="onlineSurveyContentForm" 
		action="onlineSurveyConcretQuestion.do"/>
</form>
</body>
</html>