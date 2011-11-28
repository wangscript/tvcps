<%@page contentType="text/html;charset=utf-8"%>
<%
	out.println("$.ajax({");
	out.println("url: \"/"+request.getParameter("appName")+"/outOnlineSurvery.do?dealMethod=getNormalDetail&appName="+request.getParameter("appName")+"&unit_unitId="+request.getParameter("unit_unitId")+"&themeId="+request.getParameter("themeId")+"&questionId="+request.getParameter("questionId")+"&display="+request.getParameter("display")+"\",");
	out.println("type: \"post\",");
	out.println("success: function(msg) {"); 
	out.println("var unitId = msg.split(\"###\")[0].trim();");
	out.println("var html = msg.split(\"###\")[1];"); 
	out.println("document.getElementById(unitId).innerHTML = html;");
	out.println("}");
	out.println("});");

	out.println("	function commit(){");
	out.println("		var questionId = \""+request.getParameter("questionId")+"\";");
	out.println("		var answer = document.getElementsByName(\"question_\"+questionId);");
	out.println("		var answerId = \"\";");
	out.println("		var feedbackContent = \"\";");
	out.println("		var temp = 0;");
	out.println("		for(var i = 0; i < answer.length; i++){");
	out.println("			if(answer[i].checked == \"undefined\"){");
	out.println("				// 是文本类型");
	out.println("				temp = 1;	");
	out.println("				feedbackContent = answer[i].text;");
	out.println("				if(feedbackContent == null || feedbackContent.trim() == \"\"){");
	out.println("					alert(\"请填写反馈内容\");");
	out.println("					return false;");
	out.println("				}				");
	out.println("			}else{");
	out.println("				if(answer[i].checked){");
	out.println("					if(answerId == \"\"){");
	out.println("						answerId = answer[i].value;");
	out.println("					}else{");
	out.println("						answerId += \",\" + answer[i].value;");
	out.println("					}");
	out.println("				}");
	out.println("			}");
	out.println("		}");
	out.println("		if(temp == 0){");
	out.println("			if(answerId == \"\"){");
	out.println("				alert(\"请选择答案\");");
	out.println("				return false;");
	out.println("			}");
	out.println("		};");
	out.println("		$.ajax({");
	out.println("			url: \"/"+request.getParameter("appName")+"/outOnlineSurvery.do?dealMethod=addOnlineSurvery&questionId=\"+questionId+\"&answerIds=\"+answerId+\"&feedbackContent=\"+feedbackContent,");
	out.println("		   type: \"post\",");
	out.println("		success: function(msg){");
	out.println("				 alert(msg);");
	out.println("			}");
	out.println("		});");
	out.println("	} ");
	
	out.println("  function getJsp(content){");
	out.println("      document.getElementById(\""+request.getParameter("unit_unitId")+"\").innerHTML = \"<object type='text/x-scriptlet'  data='\"+content+\"' width='100%' height='300px;'></object>\";"); 
	out.println("  }");
	
	out.println(" function check(){");
	out.println("	   var questionId = \""+request.getParameter("questionId")+"\";");
	out.println("      var url = \"/"+request.getParameter("appName")+"/outOnlineSurvery.do?dealMethod=searchResult&questionId=\"+questionId;");
	out.println("      document.getElementById(\""+request.getParameter("unit_unitId")+"\").innerHTML = \"<object type='text/x-scriptlet'  data='\"+url+\"' width='100%' height='500px;' ></object>\";");
	out.println("      ");
	out.println(" }");
%>