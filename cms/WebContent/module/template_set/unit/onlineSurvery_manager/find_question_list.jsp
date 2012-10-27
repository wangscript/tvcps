<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>选择信息分类</title>
<%@include file="/templates/headers/header.jsp"%>
<script type="text/javascript">
	var firstWin = top.getWin();		
	var temp = firstWin.split("###");
	var dialogProperty = temp[0];
	var detailFrame = "_DialogFrame_"+dialogProperty;	 		   
	var tempWindow = top.document.getElementById(detailFrame).contentWindow;
	var secondTempWindow = tempWindow.document.getElementById("unitEditArea").contentWindow;		
	var secondWin = secondTempWindow.getWin();		
	var secondtemp = secondWin.split("###");
	var seconddialogProperty = secondtemp[0];
	var seconddetailFrame = "_DialogFrame_"+seconddialogProperty;	
	var secondWindow = top.document.getElementById(seconddetailFrame).contentWindow;
	
	function button_add_onclick(ee) {	
		var questionIds = document.getElementById("xx").value;
		if(questionIds == ""){
			alert("请选择问题");
			return false;
		}
		var str = questionIds.split(",");
		var names = "";
		for(var i = 0; i < str.length; i++){
			if(names == ""){
				names = $("#"+str[i]+"_1").html();
			}else {
				names += ":::"+$("#"+str[i]+"_1").html();
			}
		}
		secondTempWindow.document.getElementById("question").value = questionIds+"###"+names;
		var temp = "";
		for(var j = 0; j < str.length; j++){
			var t = j+1;
			temp += t+""+"."+$("#"+str[j]+"_1").html()+"\n";
		}
		secondTempWindow.document.getElementById("Tquestion").innerText = temp; 
		var unitId = secondTempWindow.document.getElementById("unit_unitId").value;
		var themeId = secondTempWindow.document.getElementById("theme").value;
		var display = "";
		if(str.length > 1){
			display = "none";
		}
		if(str.length == 1){
			var htmlContent = "<table width='95%' align='center'><tr><td align='center'><!--theme--></td></tr><!--for--><tr><td><!--number-->. <!--question--><p style='display:<!--display-->'><!--answer--></p></td></tr><!--/for--><tr><td align='center'><table style='display:<!--display-->'><tr><td><input type='button' value='投票' onclick='commit()'>  <input type='button' value='查看' onclick='check()'></td></tr></table></td></tr></table>";
			secondTempWindow.document.getElementById("htmlContent").innerText = htmlContent;
		}
		var script = "<script src='/"+app+"/plugin/onlineSurvey_manager/out/out_normal_online_detail.jsp?unit_unitId="+unitId+"&themeId="+themeId+"&appName="+app+"&questionId="+questionIds+"&display="+display+"'><\/script>";
		secondTempWindow.document.getElementById("script").innerText = script;
		top.closeWindow(secondWin);	
	}

</script>
</head>
<body>
<div class="currLocation">一般调查问题列表 </div>
<form id="onlineSurveyContentForm" action="<c:url value="/onlineSurveyConcret.do"/>" method="post" name="onlineSurveyContentForm">
	<input type="hidden" name="dealMethod" id="dealMethod" />
	<complat:button buttonlist="add"  buttonalign="left"/>	 
	<complat:grid ids="xx" width="120" head="调查问题"  
		page="${onlineSurveyContentForm.pagination}"  
		form="onlineSurveyContentForm" 
		action="onlineSurveyConcretQuestion.do"/>
</form>
</body>
</html>