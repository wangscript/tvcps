<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@taglib uri="/WEB-INF/tld/complat.tld" prefix="complat"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" type="text/css" href="css/style.css"/>
<script type="text/javascript" src="script/jquery-1.2.6.js"></script>
<script type="text/javascript" src="script/jquery.form.js" ></script>
<script type="text/javascript" src="script/jquery.blockUI.js"></script>      
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head> 
<script type="text/javascript">
	function commit(){
		var questionIds = document.getElementById("questionId").value;
		if(questionIds == ""){
			alert("没有问题");
			return false;
		}
		var ids = questionIds.split(",");
		var answerId = "";
		var feedbackContent = "";
		var feedIds = "";
		var temp = 0;
		for(var j = 0; j < ids.length; j++){
			var questionId = ids[j];
			var answer = document.getElementsByName("question_"+questionId);
			for(var i = 0; i < answer.length; i++){
				if(answer[i].checked == null){
					// 是文本类型
					temp = 1;	
					var fed = answer[i].value;
					if(fed == null || fed.trim() == ""){
						alert("请填写反馈内容");
						return false;
					}else{
						if(feedIds == ""){
							feedbackContent = answer[i].value;
							feedIds = answer[i].value;
						}else{
							feedbackContent += "###"+answer[i].value;
							feedIds += "," + answer[i].value;
						}
					}		
				}else{
					if(answer[i].checked){
						if(answerId == ""){
							answerId = answer[i].value;
						}else{
							answerId += "," + answer[i].value;
						}
					}
				}
			}
		}
		if(temp == 0){
			if(answerId == ""){
				alert("请选择答案");
				return false;
			}
		}

		$("#questionIds").val(questionId);
		$("#answerIds").val(answerId);
		$("#feedbackContent").val(feedbackContent);
		var options = {	 	
 		    	url: "outOnlineSurvery.do",
 		    success: function(msg) { 
					alert(msg);
 	 		    }
    	};
		$('#onlineSurveyContentForm').ajaxSubmit(options);	


		/*
		$.ajax({
			url: "outOnlineSurvery.do?dealMethod=addAnswerOnlineSurvery&questionId="+questionId+"&answerIds="+answerId+"&feedbackContent="+feedbackContent,
		   type: "post",
		success: function(msg){
				 alert(msg);
			}
		});*/
	}

	String.prototype.trim = function() {
	    return this.replace(/(^\s*)|(\s*$)/g, "");
	}

	function check(){
		var app = document.getElementById("appName").value;
		 var questionId = document.getElementById("questionId").value;
	     var url = "/"+app+"/outOnlineSurvery.do?dealMethod=searchResult&questionId="+questionId;
	     document.getElementById("result").innerHTML = "<object type='text/x-scriptlet'  data='"+url+"' width='100%' height='100%' ></object>";
	}
</script>
<body>
<form action="outOnlineSurvery.do"	name="onlineSurveyContentForm" id="onlineSurveyContentForm" method="post">
	<input type="hidden" id="questionId" value="<%=request.getAttribute("questionId")%>"/>
	<input type="hidden" id="appName" value="<%=request.getParameter("appName")%>"/>	
	<input type="hidden" name="dealMethod" id="dealMethod" value="addAnswerOnlineSurvery"/>
	<input type="hidden" name="questionIds" id="questionIds"/> 
	<input type="hidden" name="answerIds" id="answerIds"/>
	<input type="hidden" name="feedbackContent" id="feedbackContent"/>
	<div style="background-color: white;">
	<div id="result">
		<%=request.getAttribute("content")%>
	</div>
</form>
</body>
</html>