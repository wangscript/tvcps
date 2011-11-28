<%@page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>客户调查</title>
<style type="text/css">
	.setColor{
		color:blue;
	}
	.bu {
		cursor:hand;
		background:url(images/onlineSurvey/b_01.gif) repeat-x;
		line-height:27px;
		height:27px;
		vertical-align:center;
		font-weight: bold;
		border:#d6c56a 1px solid;
	}
</style>
<script type="text/javascript" src="<c:url value="/script/My97DatePicker/WdatePicker.js"/>"></script>
<script type="text/javascript">
	$(document).ready(function(){
		
	});

	// 发布在线调查目录
	function publishSurvey(){
		var options = {	 	
 		    	url: "<c:url value='/onlineSurvey.do?dealMethod=publishSurvery'/>",
 		    success: function(msg) { 
 		    	alert(msg);		    		
 		    } 
 		};
		$('#onlineSurveyForm').ajaxSubmit(options);	
	}

	//一般调查
	function button_add_onclick() {
		var url = "onlineSurvey.do?dealMethod=&categoryId=f020001";
		window.location.href = url;
	}

	//问卷调查
	function button_add_onclick2() {
		var url = "onlineSurvey.do?dealMethod=&categoryId=f020002";
		window.location.href = url;
	}

	//调查结果
    function button_add_onclick3() {
       var url = "onlineSurveyConcretQuestion.do?dealMethod=answerList";
       window.location.href = url;
    }
	


	///////////////////////////////////////////////////////////////////
	
	//修改按钮
	function orgdetail(id) {
            var ids=id;
       		var categoryId="f020001" ;
			var url = "<c:url value='/onlineSurvey.do?dealMethod=onlineSurveyUpdate&updateID="
			          +ids+ "&categoryId="+categoryId+"'/>";
		    win = showWindow("onlineSurveyForm", "调查修改", url, 0, 0, 550, 350);
	}
    
	function closeNewChild() {
		closeWindow(win);
	}
	
	//删除按钮
	function button_delete_onclick(ee) {
		var ids = document.getElementById("xx").value;
		if (ids == "" || ids == null) {
			alert("请至少选择一条记录操作！");
			return false;
		}
		if (confirm("你确定要删除吗？")) {
			document.getElementById("id").value = document.getElementById("xx").value;
			document.all("dealMethod").value = "delete";
			document.forms[0].submit();
		} else {
			return false;
		}
	}

	//设置格式属性
	function setAttr(id) {
		document.getElementById("id").value = id;
		var url = "onlineSurveyConcret.do?dealMethod=onlineAnswerQuestions&id="+id+ "";
		window.location.href = url;
	}

	//修改按钮
	function orgdetailupdate(id) {
            var ids=id;
       		var categoryId="f020002" ;
			var url = "<c:url value='/onlineSurvey.do?dealMethod=onlineSurveyUpdate&updateID="
				+ids+ "&categoryId="+categoryId+"'/>";
		    win = showWindow("onlineSurveyForm", "调查修改", url, 250, 120, 450, 450);
	}

	
 </script>
 </head>
<body>          
<p style="line-height:36px;padding:3px; font-weight:700;">
	<img src="images/onlineSurvey/b_03.gif" alt="" class="img_a"/>模块桌面</p>
	<p>模块说明：网上调查CCMS是一个扩展功能，用于在网站前台实现在线调查功能。访问者对正在进行的调查表进行填写和提交，并可以看到投票结果<br>
	
	<a href onclick="button_add_onclick()" class="bu">&nbsp;&nbsp;一般调查&nbsp;&nbsp;</a>&nbsp;&nbsp;
	<a href onclick="button_add_onclick2()" class="bu">&nbsp;&nbsp;问卷调查&nbsp;&nbsp;</a>&nbsp;&nbsp;
	<a href onclick="button_add_onclick3()" class="bu">&nbsp;&nbsp;查看结果&nbsp;&nbsp;</a>&nbsp;&nbsp;
	<a href onclick="publishSurvey()" class="bu">&nbsp;&nbsp;&nbsp;发&nbsp;&nbsp;布&nbsp;&nbsp;&nbsp;</a><br>
	</p>

	
	<form id="onlineSurveyForm" action="<c:url value='/onlineSurvey.do'/>" method="post" name="onlineSurveyForm">
    	<p style="line-height:36px;padding:5px;font-weight:700;"><img src="images/onlineSurvey/b_03.gif" alt="" />进行中的一般调查</p>
		<table class="tb" bordercolor="#97bce3" border="1" cellpadding="3" cellspacing="0" style="border-collapse:collapse;" width="70%">
			<tr>    
			  	<td width="40%" align="left" class="setColor">调查表名称</td>
		      	<td width="30%" align="left" class="setColor">结束时间</td>
			  	<!-- <td width="15%" align="left" class="setColor">设置</td> -->
		   	</tr>
	       	<c:forEach var="onlineSurvey" items="${onlineSurveyForm.list}" >
	      	<tr> 
	            <td width="40%" align="left">${onlineSurvey.name}</td>
				<td width="30%" align="left">&nbsp;${onlineSurvey.description}</td>
				<!-- <td width="15%" align="left">设置</td> --> 
		   	</tr> 
	       	</c:forEach>
     	</table>
 		<p style="line-height:36px;padding:5px;font-weight:700;"><img src="images/onlineSurvey/b_03.gif" alt="" />进行中的问卷调查</p>
  		<table class="tb" bordercolor="#97bce3" border="1" cellpadding="3" cellspacing="0" style="border-collapse:collapse;" width="70%">
			<tr>
				<td width="40%" align="left" class="setColor">调查表名称</td>
		      	<td width="30%" align="left" class="setColor">结束时间</td>
			  	<!-- <td width="15%" align="left" class="setColor">设置</td> -->
			</tr>
	        <c:forEach var="lists" items="${onlineSurveyForm.listOnlineSurvey}">
	        <tr> 
				<td width="40%" align="left">${lists.name}</td>
				<td width="30%" align="left">&nbsp;${lists.description}</td>
				<!-- <td width="15%" align="left">设置</td> --> 
	        </tr>
       		</c:forEach>
        </table>
		<input type="hidden"  name="categoryId" id="categoryId"  value="${onlineSurveyForm.categoryId}" /> 
		<input type="hidden"  name="dealMethod" id="dealMethod"/> 
		<input type="hidden" name="message" id="message" value="${onlineSurveyForm.infoMessage}" />
		<input type="hidden" name="id" id="id" />
	</form>
 	
</body>
</html>
