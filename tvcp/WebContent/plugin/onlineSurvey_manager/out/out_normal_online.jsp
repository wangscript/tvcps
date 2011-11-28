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
		var questionId = document.getElementById("questionId").value;
		var answer = document.getElementsByName("question_"+questionId);
		var answerId = "";
		var feedbackContent = "";
		var temp = 0;
		for(var i = 0; i < answer.length; i++){
			if(answer[i].checked == null){
				// 是文本类型
				temp = 1;	
				feedbackContent = answer[i].value;	
				if(feedbackContent == null || feedbackContent.trim() == ""){
					alert("请填写反馈内容");
					return false;
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
		if(temp == 0){
			if(answerId == ""){
				alert("请选择答案");
				return false;
			}
		}
		$("#dealMethod").val("addOnlineSurvery");
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
			url: "outOnlineSurvery.do?dealMethod=addOnlineSurvery&questionId="+questionId+"&answerIds="+answerId+"&feedbackContent="+feedbackContent,
		   type: "post",
		success: function(msg){
				 alert(msg);
			}
		});*/
	} 

	/**function check(){
		var questionId = document.getElementById("questionId").value;
		$("#dealMethod").val("checkCommit");
		var options = {	 	
 		    	url: "outOnlineSurvery.do",
 		    success: function(msg) { 
					alert(msg);
 	 		    }
    	};
		$('#onlineSurveyContentForm').ajaxSubmit(options);
	}*/
	
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
</script>
<body>
<form action="<c:url value='outOnlineSurvery.do'/>"	name="onlineSurveyContentForm" id="onlineSurveyContentForm" method="post">
	<input type="hidden" id="questionId" value="<%=request.getParameter("questionId")%>"/>
	<input type="hidden" id="appName" value="<%=request.getParameter("appName")%>"/>
	<input type="hidden" name="dealMethod" id="dealMethod" value="addOnlineSurvery"/>
	<input type="hidden" name="questionIds" id="questionIds"/> 
	<input type="hidden" name="answerIds" id="answerIds"/> 
	<input type="hidden" name="feedbackContent" id="feedbackContent"/>
	<div id="result">
		<%=request.getAttribute("content")%>
	</div> 
</form>
</body>
</html>